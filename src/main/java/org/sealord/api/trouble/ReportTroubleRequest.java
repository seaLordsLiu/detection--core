package org.sealord.api.trouble;

import java.util.Map;

/**
 * 请求远程地址的API
 * @author liu xw
 * @since 2024 05-15
 */
public class ReportTroubleRequest {

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
     * 上报时间
     */
    private Long reportTime;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求体
     * json字符串
     */
    private String body;

    /**
     * url参数，根据 & 进行切割
     * json字符串
     */
    private String param;



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

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
