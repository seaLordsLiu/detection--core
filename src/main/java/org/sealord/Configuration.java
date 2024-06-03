package org.sealord;


import org.sealord.config.TroubleConfig;

/**
 * 客户端配置中心
 * @author liu xw
 * @since 2024 04-16
 */

public class Configuration {

    /**
     * 初始化 ConfigurationWrapper
     * 通过 ConfigurationWrapper 对象保障全局只会有一个有效的 Configuration.
     * @throws IllegalAccessException 构造异常
     */
    public Configuration() throws IllegalAccessException {
        ConfigurationWrapper.initConfiguration(this);
    }

    /**
     * 注册应用的名称
     */
    private String applicationName;

    /**
     * 环境信息
     */
    private String evnLabel;

    /**
     * 远程配置信息
     */
    private String remoteAddress;

    /**
     * 配置中心
     */
    private TroubleConfig trouble = new TroubleConfig();


    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getEvnLabel() {
        return evnLabel;
    }

    public void setEvnLabel(String evnLabel) {
        this.evnLabel = evnLabel;
    }

    public TroubleConfig getTrouble() {
        return trouble;
    }

    public void setTrouble(TroubleConfig trouble) {
        this.trouble = trouble;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {

        this.remoteAddress = remoteAddress;
    }
}
