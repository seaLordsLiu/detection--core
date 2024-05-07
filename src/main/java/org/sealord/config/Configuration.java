package org.sealord.config;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

/**
 * 客户端配置中心
 * @author liu xw
 * @date 2024 04-16
 */

@Data
public class Configuration {

    /**
     * 注册应用的名称
     */
    private String applicationName;

    /**
     * 环境信息
     */
    private String evnLabel;

    /**
     * 配置中心
     */
    private TroubleConfig trouble = new TroubleConfig();

}
