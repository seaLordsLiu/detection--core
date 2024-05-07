package org.sealord.config;

import lombok.Getter;
import lombok.Setter;
import org.sealord.http.HttpConfig;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 故障上报配置
 * @author liu xw
 * @date 2024 04-12
 */
@Getter
@Setter
public class TroubleConfig {

    /**
     * 是否启用异步执行
     */
    private Boolean async = Boolean.TRUE;

    /**
     * 异步核心线程数
     * async = true 时生效
     */
    private int corePoolSize = 50;

    /**
     * http 配置中心
     */
    private HttpConfig http = new HttpConfig();

    /**
     * 排除的异常信息
     * com.qzh.ServiceException
     */
    private List<String> ignoreError = new ArrayList<>();

}