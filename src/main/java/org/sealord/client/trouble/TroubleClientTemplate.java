package org.sealord.client.trouble;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpStatus;
import org.sealord.client.trouble.handler.HttpTroubleHandler;
import org.sealord.client.trouble.handler.TroubleHandler;
import org.sealord.config.Configuration;
import org.sealord.config.TroubleConfig;
import org.sealord.http.ByteEntity;
import org.sealord.http.wrapper.PostBodyRequestWrapper;
import org.sealord.util.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * @author liu xw
 * @date 2024 04-12
 */
public class TroubleClientTemplate implements TroubleClient {

    private static final Logger log = LoggerFactory.getLogger(TroubleClientTemplate.class);

    /**
     * 配置中心
     */
    private final Configuration configuration;

    /**
     * 处理器
     */
    private final TroubleHandler troubleHandler;

    /**
     * 异步执行器
     */
    private final ThreadPoolExecutor tpe;

    /**
     * 自定义消费者, 用于自定义处理
     */
    private List<Consumer<Map<String, Object>>> customCons;

    public TroubleClientTemplate(Configuration configuration) {
        this(configuration, Collections.emptyList());
    }

    public TroubleClientTemplate(Configuration configuration, List<Consumer<Map<String, Object>>> customCons) {
        TroubleConfig tc = configuration.getTrouble();

        this.configuration = configuration;
        this.troubleHandler = new HttpTroubleHandler(tc.getHttp());
        this.customCons = customCons;
        if (tc.getAsync()) {
            this.tpe = buildPool(tc.getCorePoolSize());
        } else {
            this.tpe = null;
        }
    }

    @Override
    public void reportTrouble(Throwable throwable) throws IOException {
        this.reportTrouble(throwable, null);
    }

    @Override
    public void reportTrouble(Throwable throwable, HttpServletRequest request) throws IOException {
        // 检测是否需要过滤
        if (filter(throwable)) {
            log.debug("report trouble exclude throwable name: {}", throwable.getClass().getName());
            return;
        }

        // 构造参数
        TroubleContent tc = buildContent(throwable, request);
        // 调用故障处理器处理故障
        if (tpe != null) {
            tpe.execute(() -> {
                executeHandler(tc);
            });
            return;
        }
        executeHandler(tc);
    }

    /**
     * 过滤故障
     * @param tc 故障信息
     * @return 过滤执行
     */
    private ByteEntity executeHandler(TroubleContent tc){
        long start = Instant.now().getEpochSecond();
        try {
            ByteEntity handler = troubleHandler.handler(tc);
            log.debug("report trouble success. handler: [{}]. time: {}. result: {}", troubleHandler.getClass().getSimpleName(), Instant.now().getEpochSecond() - start, new String(handler.getResponseBytes()));
            return handler;
        }catch (Exception e){
            log.error("report trouble error. handler: [{}]. ", troubleHandler.getClass().getSimpleName(), e);
            return new ByteEntity(HttpStatus.SC_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 构建线程池
     * @param coreSize 核心线程数
     *        maximumPoolSize = 2 * coreSize
     * @return 线程池
     */
    private ThreadPoolExecutor buildPool(int coreSize) {
        return new ThreadPoolExecutor(
            coreSize,
            coreSize * 2,
            0L,
            TimeUnit.MILLISECONDS,
            // 执行队列
            new LinkedBlockingQueue<>(coreSize * 4),
            // 执行工程
            Executors.defaultThreadFactory(),
            // 拒绝策略
            // 定义拒绝策略
            (r, e) -> {
                log.error("report trouble queue is full. reject task. task: [{}]. executor: [{}]", r.toString(), e.toString());
            }
        );
    }

    /**
     * 过滤故障
     * @param throwable 故障信息
     * @return 过滤执行
     *         true 不可执行
     *         false 可执行
     */
    private Boolean filter(Throwable throwable) {
        List<String> ignoreError = configuration.getTrouble().getIgnoreError();
        if (ignoreError.contains(throwable.getClass().getName())) {
            if (log.isDebugEnabled()){
                log.debug("ignore throwable. name: {}", throwable.getClass().getName());
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 故障中心构造
     * @param throwable 故障信息
     * @return 故障中心
     */
    private TroubleContent buildContent(Throwable throwable, HttpServletRequest request) throws IOException {
        TroubleContent tc = new TroubleContent();
        tc.setConfiguration(configuration);
        tc.setThrowable(throwable);
        tc.setTroubleTime(LocalDateTime.now());

        // 构造 http 请求参数信息
        if (request != null) {

            tc.setUrl(request.getRequestURI());
            tc.setMethod(request.getMethod());
            String queryString = request.getQueryString();
            if(Objects.nonNull(queryString)){
                queryString = URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8.name());
            }
            tc.setUrlParam(queryString);

            String body = this.readRequestBody(request);
            tc.setParam(body);
        }

        // 构造自定义的参数信息
        Map<String, Object> customData = new HashMap<>();
        if (customCons != null) {
            customCons.forEach(consumer -> {
                try {
                    consumer.accept(customData);
                } catch (Exception e) {
                    // 消费者处理失败
                    log.error("custom consumer error.", e);
                }
            });
        }
        tc.setCustomData(customData);

        return tc;
    }

    /**
     * 读取请求体数据
     * @param request 请求
     * @return 请求体数据
     * @throws IOException 读取异常
     */
    private String readRequestBody(HttpServletRequest request) throws IOException {
        String body = "{}";

        // 构造请求参数信息
        final String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

        if (Objects.isNull(contentType)) {
            if (log.isDebugEnabled()){
                log.debug("request content type is null. url: {}", request.getRequestURI());
            }
            return body;
        }

        // 表单
        if (contentType.contains(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            // 构造参数信息
            body = JacksonUtils.toJson(parameterMap.toString());
        }

        // JSON, 但 HttpServletRequest 读取的是流，并且只能读取一次, 所以会读取失败, 需要自定义读取组件
        if (contentType.contains(ContentType.APPLICATION_JSON.getMimeType())) {
            // 构造参数信息 - 补充 PostBodyRequestWrapper 读取请求体数据
            if (request instanceof PostBodyRequestWrapper){
                PostBodyRequestWrapper wrapper = (PostBodyRequestWrapper) request;
                body = new String(wrapper.getBody(), StandardCharsets.UTF_8);
            }
        }

        return body;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public TroubleHandler getTroubleHandler() {
        return troubleHandler;
    }

    public ThreadPoolExecutor getTpe() {
        return tpe;
    }

    public List<Consumer<Map<String, Object>>> getCustomCons() {
        return customCons;
    }

    public void setCustomCons(List<Consumer<Map<String, Object>>> customCons) {
        this.customCons = customCons;
    }
}