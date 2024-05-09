package org.sealord.http;

import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;
import org.sealord.util.JacksonUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author liu xw
 * @since 2024 04-10
 */
public class RequestHttpBuilder {

    /**
     * 构造请求信息
     * @param url 请求地址
     * @param httpMethod 请求方法
     * @param entity 请求体内容
     */
    public static HttpUriRequestBase build(String url, String httpMethod, RequestHttpEntity entity){
        // 构造 ClassicHttpRequest 对象信息
        HttpUriRequestBase request = HttpMethod.build(url, httpMethod);

        // 设置请求头
        initHeader(request, entity.getHeaders());

        // 设置请求体内容;
        initBody(request, entity.getBody());

        return request;
    }

    private static void initHeader(HttpUriRequestBase request, List<Header> headers){
        // 设置请求头
        Optional.ofNullable(headers).ifPresent(hs -> {
            for (Header header : hs) {
                if (!request.containsHeader(header.getName())) {
                    request.addHeader(header);
                    continue;
                }
                if (!header.isSensitive()) {
                    continue;
                }
                // 删除覆盖
                request.removeHeaders(header.getName());
                request.addHeader(header);
            }
        });
    }


    private static void initBody(HttpUriRequestBase request, Object body){
        // 设置请求体内容;
        request.setEntity(new StringEntity(body instanceof String ? (String) body : JacksonUtils.toJson(body), ContentType.APPLICATION_JSON));
    }




}
