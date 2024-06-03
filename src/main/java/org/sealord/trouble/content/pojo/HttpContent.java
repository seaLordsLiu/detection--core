package org.sealord.trouble.content.pojo;


import java.util.Map;

/**
 * http 信息
 * @author liu xw
 * @since 2024 05-22
 */
public class HttpContent {

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
     * url参数
     */
    private String param;

    /**
     * 请求头数据
     */
    private Map<String, Object> headers;


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

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }
}
