package org.sealord.client.trouble.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sealord.RestTemplate;
import org.sealord.client.HEADER;
import org.sealord.client.URL;
import org.sealord.client.trouble.TroubleContent;
import org.sealord.config.Configuration;
import org.sealord.http.ByteEntity;
import org.sealord.http.HttpConfig;
import org.sealord.util.JacksonUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


/**
 * 故障上报HTTP方式实现
 * @author liu xw
 * @date 2024 04-11
 */
public class HttpTroubleHandler implements TroubleHandler {

    /**
     * 请求客户端
     */
    @Getter
    private final RestTemplate restTemplate;


    public HttpTroubleHandler(HttpConfig httpConfig) {
        this.restTemplate = RestTemplate.of(httpConfig);
    }

    @Override
    public ByteEntity handler(TroubleContent content){
        // 构造请求头信息
        Map<String, Object> header = buildHeader(content.getConfiguration());

        // 构造请求体信息
        ReportTroubleApiDTO body = ReportTroubleApiDTO.of(content);

        // 发送请求
        return restTemplate.postJson(URL.Trouble.RECEIVE, header, body);
    }

    /**
     * 构造请求头信息
     * @return 结果
     */
    private Map<String, Object> buildHeader(Configuration configuration){
        Map<String, Object> header = new HashMap<>();
        header.put(HEADER.X_CLIENT_NAME, configuration.getApplicationName());
        header.put(HEADER.X_ENV_LABEL, configuration.getEvnLabel());
        return header;
    }


    /**
     * 参数类信息
     */
    @ToString
    @Getter
    @Setter
    private static class ReportTroubleApiDTO {
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
         * 原数据信息
         */
        private String customData;

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



        public static ReportTroubleApiDTO of(TroubleContent tc){
            ReportTroubleApiDTO apiDTO = new ReportTroubleApiDTO();

            // 异常数据处理
            Throwable throwable = tc.getThrowable();

            // 故障代表（异常类信息）
            apiDTO.setTrouble(throwable.getClass().getName());
            // 故障内容（异常信息）
            apiDTO.setMessage(throwable.getMessage());
            StringWriter stringWriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringWriter));
            // 故障信息（异常栈堆）
            apiDTO.setInformation(stringWriter.toString());
            // 上报时间
            apiDTO.setReportTime(Instant.now().getEpochSecond());

            // 补充 url参数信息
            apiDTO.setUrl(tc.getUrl());
            apiDTO.setMethod(tc.getMethod());
            apiDTO.setUrlParam(tc.getUrlParam());
            apiDTO.setParam(tc.getParam());

            // 自定义参数
            apiDTO.setCustomData(JacksonUtils.toJson(tc.getCustomData()));
            return apiDTO;
        }
    }
}