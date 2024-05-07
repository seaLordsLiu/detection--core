package org.sealord.client.trouble;

import org.apache.hc.core5.http.HttpStatus;
import org.sealord.client.trouble.handler.HttpTroubleHandler;
import org.sealord.client.trouble.handler.TroubleHandler;
import org.sealord.config.Configuration;
import org.sealord.config.TroubleConfig;
import org.sealord.http.ByteEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

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
     * 内容处理器i
     */
    private final ErrorContentCustomizer customizer;

    public TroubleClientTemplate(Configuration configuration) {
        TroubleConfig tc = configuration.getTrouble();

        this.configuration = configuration;
        this.troubleHandler = new HttpTroubleHandler(tc.getHttp());
        this.customizer = new ErrorContentCustomizer();

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
    public void reportTrouble(Throwable throwable, HttpServletRequest request) {
        // 检测是否需要过滤
        if (filter(throwable)) {
            log.debug("report trouble exclude throwable name: {}", throwable.getClass().getName());
            return;
        }

        // 构造参数
        ErrorContent tc = customizer.buildContent(this.configuration, request, throwable);
        // 调用故障处理器处理故障
        if (tpe != null) {
            tpe.execute(() -> executeHandler(tc));
            return;
        }
        executeHandler(tc);
    }

    /**
     * 过滤故障
     * @param tc 故障信息
     * @return 过滤执行
     */
    private void executeHandler(ErrorContent tc){
        long start = Instant.now().getEpochSecond();
        try {
            ByteEntity handler = troubleHandler.handler(tc);
            log.debug("report trouble success. handler: [{}]. time: {}. result: {}", troubleHandler.getClass().getSimpleName(), Instant.now().getEpochSecond() - start, new String(handler.getResponseBytes()));
        }catch (Exception e){
            log.error("report trouble error. handler: [{}]. ", troubleHandler.getClass().getSimpleName(), e);
//            return new ByteEntity(HttpStatus.SC_SERVER_ERROR, e.getMessage());
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
        List<Class<? extends Throwable>> ignoreErrors = configuration.getTrouble().getIgnoreError();
        Class<? extends Throwable> exceptionToMatch = throwable.getClass();
        for (Class<? extends Throwable> ie : ignoreErrors) {
            if (exceptionToMatch.equals(ie)) {
                if (log.isDebugEnabled()){
                    log.debug("ignore throwable. name: {}", exceptionToMatch.getName());
                }
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}