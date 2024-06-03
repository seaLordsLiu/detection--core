package org.sealord.trouble;

import javax.servlet.http.HttpServletRequest;

/**
 * 故障内容生成器
 * @author liu xw
 * @since 2024 05-17
 */
public interface TroubleContentGenerator {

    /**
     * 内容生成器
     * @param throwable 异常信息
     * @param request 请求
     * @return 内容
     */
    TroubleContent generator(Throwable throwable, HttpServletRequest request);
}