package org.sealord.client.trouble.handler;

import org.sealord.RestTemplate;
import org.sealord.client.HEADER;
import org.sealord.client.URL;
import org.sealord.client.trouble.ErrorContent;
import org.sealord.http.ByteEntity;
import org.sealord.http.HttpConfig;
import org.sealord.util.JacksonUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


/**
 * 故障上报HTTP方式实现
 * @author liu xw
 * @since 2024 04-11
 */
public class HttpTroubleHandler implements TroubleHandler {

    /**
     * 请求客户端
     */
    private final RestTemplate restTemplate;


    public HttpTroubleHandler(HttpConfig httpConfig) {
        this.restTemplate = RestTemplate.of(httpConfig);
    }

    @Override
    public ByteEntity handler(ErrorContent content){
        // 构造请求头信息
        Map<String, Object> header = new HashMap<>();
        header.put(HEADER.X_CLIENT_NAME, content.getApplicationName());
        header.put(HEADER.X_ENV_LABEL, content.getEvnLabel());

        // 构造请求体信息
        ReportTroubleApiDTO body = ReportTroubleApiDTO.of(content);

        // 发送请求
        return restTemplate.postJson(URL.Trouble.RECEIVE, header, body);
    }


    /**
     * 请求 API 信息
     */
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



        public static ReportTroubleApiDTO of(ErrorContent tc){
            ReportTroubleApiDTO apiDTO = new ReportTroubleApiDTO();

            // 故障代表（异常类信息）
            apiDTO.setTrouble(tc.getTrouble());
            // 故障内容（异常信息）
            apiDTO.setMessage(tc.getMessage());
            // 故障信息（异常栈堆）
            apiDTO.setInformation(tc.getInformation());
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

        public String getCustomData() {
            return customData;
        }

        public void setCustomData(String customData) {
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
}