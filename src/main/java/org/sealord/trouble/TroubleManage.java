package org.sealord.trouble;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 故障管理器
 * @author liu xw
 * @since 2024 05-17
 */
public interface TroubleManage {

    /**
     * 上报故障
     * @param throwable 异常
     */
    void trouble(Throwable throwable) throws IOException;

    /**
     * 上报故障
     * @param throwable 异常
     * @param request 客户端请求
     */
    void trouble(Throwable throwable, HttpServletRequest request) throws IOException;
}
