package org.sealord.trouble;

import java.util.Map;

/**
 * 内容信息类
 * 1. 故障的基本信息
 * 2. web的
 * @author liu xw
 * @since 2024 05-17
 */
public interface TroubleContent {

    /**
     * 故障时间
     * @return 故障时间
     */
    Long reportTime();

    /**
     * 提供出现错误的异常信息
     * @return 异常信息
     */
    Throwable throwable();


}