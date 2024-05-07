package org.sealord.client.trouble;

import org.sealord.config.Configuration;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author liu xw
 * @date 2024 04-24
 */
public class TroubleContent {

    /**
     * 配置中心
     */
    private Configuration configuration;

    /**
     * 异常信息
     */
    private Throwable throwable;

    /**
     * 异常时间
     */
    private LocalDateTime troubleTime;

    /**
     * 原数据信息
     */
    private Map<String, Object> customData;

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


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public LocalDateTime getTroubleTime() {
        return troubleTime;
    }

    public void setTroubleTime(LocalDateTime troubleTime) {
        this.troubleTime = troubleTime;
    }

    public Map<String, Object> getCustomData() {
        return customData;
    }

    public void setCustomData(Map<String, Object> customData) {
        this.customData = customData;
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
}
