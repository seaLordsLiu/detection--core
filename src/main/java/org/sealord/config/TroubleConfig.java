package org.sealord.config;

import org.sealord.http.HttpConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 故障上报配置
 * @author liu xw
 * @since 2024 04-12
 */
public class TroubleConfig {

    /**
     * 异步核心线程数
     * async = true 时生效
     */
    private int corePoolSize = 50;

    /**
     * 排除的异常信息
     */
    private List<Class<? extends Throwable>> ignoreError = new ArrayList<>();

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public List<Class<? extends Throwable>> getIgnoreError() {
        return ignoreError;
    }

    public void setIgnoreError(List<Class<? extends Throwable>> ignoreError) {
        this.ignoreError = ignoreError;
    }
}