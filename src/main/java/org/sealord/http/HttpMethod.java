package org.sealord.http;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;

/**
 * 集成方法
 * 用于构造请求方法
 * @author liu xw
 * @date 2024 04-10
 */
public class HttpMethod {

    public static final String GET = HttpGet.METHOD_NAME;

    public static final String POST = HttpPost.METHOD_NAME;

    /**
     * 初始化
     * @param url 请求地址
     * @param method 请求方法
     * @return 请求基类
     */
    public static HttpUriRequestBase build(String url, String method){
        if (GET.equalsIgnoreCase(method)) {
            return new HttpGet(url);
        }
        else if (POST.equalsIgnoreCase(method)) {
            return new HttpPost(url);
        }
        throw new UnsupportedOperationException("Unsupported http method: " + method);
    }
}
