package org.sealord.trouble;

import org.sealord.trouble.TroubleContent;

/**
 * 执行拦截器.
 * @author liu xw
 * @since 2024 05-31
 */
@FunctionalInterface
public interface TroubleFilter {

    /**
     * 拦截异常信息
     * @param content 异常内容
     * @return 拦截结果
     *         true: 不可执行
     *         false: 可执行
     */
    boolean filter(TroubleContent content);
}
