package org.sealord.client.trouble;

import lombok.Data;
import org.sealord.config.Configuration;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author liu xw
 * @date 2024 04-24
 */
@Data
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

}
