package org.sealord.config;

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


    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
}