package org.sealord.client.trouble;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import org.sealord.config.Configuration;
import org.sealord.http.wrapper.PostBodyRequestWrapper;
import org.sealord.util.JacksonUtils;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * 内容的定制处理器
 * @author liu xw
 * @since 2024 04-24
 */
public class ErrorContentCustomizer {

    /**
     * 构造内容信息
     * @param configuration 配置中心
     * @param request 请求（可为空）
     * @param th 异常
     * @return 内容信息
     */
    public ErrorContent buildContent(Configuration configuration, @Nullable HttpServletRequest request, Throwable th) {
        ErrorContent content = init(configuration);

        // 为 TroubleContent 补充 HttpServletRequest 信息
        mappingContentOnRequest(request, content);

        // 构造异常信息
        mappingContentOnThrowable(th, content);


        return content;
    }


    /**
     * 根据 Configuration衍生出故障内容信息
     * @param c 配置中心
     * @return 内容
     */
    private ErrorContent init(Configuration c){
        ErrorContent content = new ErrorContent();
        content.setApplicationName(c.getApplicationName());
        content.setEvnLabel(c.getEvnLabel());
        return content;
    }

    /**
     * 映射 HttpServletRequest 内容参数信息
     * @param request 请求
     * @param tc 内容
     */
    private void mappingContentOnRequest(@Nullable HttpServletRequest request, ErrorContent tc){
        // 构造 http 请求参数信息
        if (request == null) {
            return;
        }
        // 请求地址
        tc.setUrl(request.getRequestURI());
        // 请求方法
        tc.setMethod(request.getMethod());
        // 请求参数（需解码）
        String queryString = request.getQueryString();
        if(Objects.nonNull(queryString)){
            try {
                queryString = URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                // 解码失败, 直接设置原始值数据
                queryString = request.getQueryString();
            }
        }
        tc.setUrlParam(queryString);

        String body = this.readRequestParam(request);
        tc.setParam(body);
    }

    /**
     * 读取请求体数据
     * @param request 请求
     * @return 请求体数据
     */
    private String readRequestParam(HttpServletRequest request) {
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
            body = JacksonUtils.toJson(parameterMap.toString());
        }

        // JSON, 但 HttpServletRequest 读取的是流，并且只能读取一次, 所以会读取失败, 需要自定义读取组件
        if (contentType.contains(ContentType.APPLICATION_JSON.getMimeType())) {
            // 构造参数信息 - 补充 PostBodyRequestWrapper 读取请求体数据
            if (request instanceof PostBodyRequestWrapper){
                PostBodyRequestWrapper wrapper = (PostBodyRequestWrapper) request;
                body = new String(wrapper.getBody(), StandardCharsets.UTF_8);
            }
        }

        return body;
    }


    /**
     * 映射 TroubleContent 内容参数信息
     * @param throwable 异常
     * @param tc 内容
     */
    private void mappingContentOnThrowable(Throwable throwable, ErrorContent tc){
        // 故障代表（异常类信息）
        tc.setTrouble(throwable.getClass().getName());

        // 故障内容（异常信息）
        tc.setMessage(throwable.getMessage());

        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        // 故障信息（异常栈堆）
        tc.setInformation(stringWriter.toString());
    }
}
