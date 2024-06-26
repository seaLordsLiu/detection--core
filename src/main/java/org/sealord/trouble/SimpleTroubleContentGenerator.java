package org.sealord.trouble;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.sealord.ConfigurationWrapper;
import org.sealord.HttpServletRequestBodyWrapper;
import org.sealord.trouble.content.pojo.HttpContent;
import org.sealord.trouble.content.SimpleTroubleContent;
import org.sealord.trouble.content.SimpleWebTroubleContent;
import org.sealord.util.JacksonUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 构造 TroubleContent 信息
 * @author liu xw
 * @since 2024 05-23
 */
public class SimpleTroubleContentGenerator implements TroubleContentGenerator {

    @Override
    public TroubleContent generator(Throwable throwable, HttpServletRequest request) {
        if (request == null){
            return new SimpleTroubleContent(throwable);
        }
        return new SimpleWebTroubleContent(throwable, this.buildHttpContent(request));
    }

    /**
     * 构造httpContent信息
     * @param request 请求地址
     * @return 结果
     */
    private HttpContent buildHttpContent(HttpServletRequest request){
        HttpContent hc = new HttpContent();
        // 构造 http 请求参数信息
        if (request == null) {
            return hc;
        }
        // 请求地址
        hc.setUrl(request.getRequestURI());

        // 请求方法
        hc.setMethod(request.getMethod());

        // 请求参数（基于URL地址需解码）
        String queryString = request.getQueryString();
        if(Objects.nonNull(queryString)){
            try {
                queryString = URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                // 解码失败, 直接设置原始值数据
                queryString = request.getQueryString();
            }
        }
        hc.setParam(queryString);

        // 请求体 body
        String body = this.readRequestBody(request);
        hc.setBody(body);

        // 请求头
        // 获取所有请求头的名字
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, Object> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        hc.setHeaders(headers);

        return hc;
    }

    /**
     * 读取请求体数据
     * @param request 请求
     * @return 请求体数据
     */
    private String readRequestBody(HttpServletRequest request) {
        String body = "{}";

        // 构造请求参数信息
        final String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

        if (Objects.isNull(contentType)) {
            return body;
        }

        // 表单
        if (contentType.contains(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            // 构造参数信息
            body = JacksonUtils.toJson(parameterMap);
        }

        // 请求体, 但 HttpServletRequest 读取的是流，并且只能读取一次, 所以会读取失败, 需要自定义读取组件
        if (contentType.contains(ContentType.APPLICATION_JSON.getMimeType())) {
            // 构造参数信息 - 补充 PostBodyRequestWrapper 读取请求体数据
            if (request instanceof HttpServletRequestBodyWrapper){
                byte[] bodyBytes = ((HttpServletRequestBodyWrapper) request).bodyInfo();
                if (bodyBytes != null) {
                    body = new String(bodyBytes, StandardCharsets.UTF_8);
                }
            }
        }

        return body;
    }
}