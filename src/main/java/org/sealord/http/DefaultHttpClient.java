package org.sealord.http;

import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.pool.PoolStats;
import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 默认实现的客户端
 * @author liu xw
 * @since 2024 04-10
 */
public class DefaultHttpClient {

    private static final Logger log = LoggerFactory.getLogger(DefaultHttpClient.class);

    /**
     * 客户端信息
     */
    private final CloseableHttpClient httpClient;

    /**
     * 初始化客http客户端
     * @param config 配置信息
     */
    public DefaultHttpClient(HttpConfig config) {

        // 补充默认值信息
        if (config == null){
            config = new HttpConfig();
        }

        // 配置请求参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(config.getConnectTimeout()))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(config.getConnectionRequestTimeout()))
                .setResponseTimeout(Timeout.ofMilliseconds(config.getSocketTimeout()))
                .build();


        // 自定义连接池属性信息
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
            .setMaxConnTotal(config.getMaxTotal())
            .setMaxConnPerRoute(config.getMaxPreRoute())
            .build();

        // 构造HttpClient
        // 初始化客户端
        this.httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                // 重试机制
//                .setRetryStrategy(new DefaultHttpRequestRetryStrategy(retryCount, TimeValue.ofSeconds(1)))
                // 开启后台线程清除闲置的连接
                .evictIdleConnections(TimeValue.ofMilliseconds(config.getConnectionTimeToLive()))
                .build();

        // 添加监控线程，创建一个定时线程池
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        // 每隔1s打印连接池信息
        executor.scheduleAtFixedRate(() -> {
            // 关闭过期的
            connectionManager.closeExpired();
            // 获取所有路由的连接池状态
            PoolStats totalStats = connectionManager.getTotalStats();
            log.debug("httpClient time  " + LocalDateTime.now() + " Total status:" + totalStats.toString());
            System.out.println();
        }, 0, 1, TimeUnit.MINUTES);
    }


    public <T extends ByteResponse> T execute(String url, String httpMethod, RequestHttpEntity entity, HttpClientResponseHandler<T> responseHandler) throws IOException {
        // 构造请求信息
        HttpUriRequestBase request = RequestHttpBuilder.build(url, httpMethod, entity);
        // 执行请求
        return httpClient.execute(request, responseHandler);
    }
}