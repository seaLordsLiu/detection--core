package org.sealord.config;

import org.sealord.http.HttpConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 故障上报配置
 * @author liu xw
 * @date 2024 04-12
 */
public class TroubleConfig {

    /**
     * 是否启用异步执行
     */
    private Boolean async = Boolean.TRUE;

    /**
     * 异步核心线程数
     * async = true 时生效
     */
    private int corePoolSize = 50;

    /**
     * http 配置中心
     */
    private HttpConfig http = new HttpConfig();

    /**
     * 排除的异常信息
     */
    private List<Class<? extends Throwable>> ignoreError = new ArrayList<>();


    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public HttpConfig getHttp() {
        return http;
    }

    public void setHttp(HttpConfig http) {
        this.http = http;
    }

    public List<Class<? extends Throwable>> getIgnoreError() {
        return ignoreError;
    }

    public void setIgnoreError(List<Class<? extends Throwable>> ignoreError) {
        this.ignoreError = ignoreError;
    }
}