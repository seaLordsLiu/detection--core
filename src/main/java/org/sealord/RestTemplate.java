package org.sealord;

import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.NullEntity;
import org.apache.hc.core5.http.message.BasicHeader;
import org.sealord.http.HttpConfig;
import org.sealord.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 默认客户端实现
 * @author liu xw
 * @since 2024 04-10
 */
public class RestTemplate {

    private final Logger log = LoggerFactory.getLogger(RestTemplate.class);

    /**
     * 执行客户端
     */
    private final DefaultHttpClient httpClient;

    public RestTemplate(HttpConfig config) {
        this.httpClient = new DefaultHttpClient(config);
    }

    /**
     * post请求
     * @param url 请求地址
     * @param headers 请求头
     * @param body 请求体
     */
    public ByteResponse postJson(String url, Map<String, Object> headers, Object body) {
        if (Objects.isNull(headers) || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<>();
        }
        headers.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());
        return execute(url, HttpMethod.POST, headers, body);
    }

    /**
     * 执行请求
     * @param url 请求地址
     * @param method 请求方法
     * @param headers 请求头
     * @param body 请求BODY
     */
    private ByteResponse execute(String url, String method, Map<String, Object> headers, Object body) {
        RequestHttpEntity entity = new RequestHttpEntity(body);
        headers.entrySet().stream().map(e -> new BasicHeader(e.getKey(), e.getValue())).forEach(entity::addHeader);
        return execute(url, method, entity);
    }

    /**
     * 执行请求
     * 问题1: 为什么想要有返回结果
     * 问题2: 返回结果需要有哪些东西
     * - http执行状态信息
     * - 服务端返回的参数信息
     *
     * @param url 请求地址
     * @param method 请求方法
     * @param entity 实体类信息
     */
    private ByteResponse execute(String url, String method, RequestHttpEntity entity) {
        try {
            // 执行结果
            return httpClient.execute(url, method, entity, response -> new ByteResponse(HttpStatus.SC_OK, response.getEntity()));
        }catch (IOException e){
            int HttpSt;
            if (e instanceof ConnectionRequestTimeoutException){
                HttpSt = HttpStatus.SC_GATEWAY_TIMEOUT;
            }else {
                HttpSt = HttpStatus.SC_SERVER_ERROR;
            }
            log.error("execute url: [{}]. method: [{}]. httpStatus: {}. msg: {}", url, method, HttpSt, e.getMessage(), e);
            return new ByteResponse(HttpSt, NullEntity.INSTANCE);
        }
    }

    /**
     * 获取
     */
    public static RestTemplate of(HttpConfig config){
        return new RestTemplate(config);
    }
}