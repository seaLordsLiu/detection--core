package org.sealord.config;


/**
 * 客户端配置中心
 * @author liu xw
 * @date 2024 04-16
 */

public class Configuration {

    /**
     * 注册应用的名称
     */
    private String applicationName;

    /**
     * 环境信息
     */
    private String evnLabel;

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
}
