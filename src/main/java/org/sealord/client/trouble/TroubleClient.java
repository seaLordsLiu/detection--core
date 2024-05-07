package org.sealord.client.trouble;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 故障上报客户端
 * @author liu xw
 * @date 2024 04-11
 */
public interface TroubleClient {

    /**
     * 上报故障
     * @param throwable 异常
     */
    void reportTrouble(Throwable throwable) throws IOException;

    /**
     * 上报故障
     * @param throwable 异常
     * @param request 客户端请求
     */
    void reportTrouble(Throwable throwable, HttpServletRequest request) throws IOException;
}
