package org.sealord.client.trouble;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 内容信息
 * @author liu xw
 * @date 2024 04-24
 */
public class ErrorContent {

    /**
     * 注册应用的名称
     */
    private String applicationName;

    /**
     * 环境信息
     */
    private String evnLabel;

    /**
     * 异常时间
     */
    private LocalDateTime troubleTime;




    /**
     * 故障代表（异常类信息）
     */
    private String trouble;

    /**
     * 故障内容（异常信息）
     */
    private String message;

    /**
     * 故障信息（异常栈堆）
     */
    private String information;




    /**
     * 请求URL地址信息 - 针对WEB请求
     */
    private String url;

    /**
     * 请求方法 - 针对WEB请求
     */
    private String method;

    /**
     * 请求参数 - url后拼接参数 - 针对WEB请求
     */
    private String urlParam;

    /**
     * 请求参数 - 表单/json - 针对WEB请求
     * request dateformat
     * request json
     */
    private String param;





    /**
     * 原数据信息
     */
    private Map<String, Object> customData;



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

    public LocalDateTime getTroubleTime() {
        return troubleTime;
    }

    public void setTroubleTime(LocalDateTime troubleTime) {
        this.troubleTime = troubleTime;
    }

    public String getTrouble() {
        return trouble;
    }

    public void setTrouble(String trouble) {
        this.trouble = trouble;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Map<String, Object> getCustomData() {
        return customData;
    }

    public void setCustomData(Map<String, Object> customData) {
        this.customData = customData;
    }
}
