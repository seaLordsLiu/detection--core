package org.sealord;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取 post 的 body 内容信息
 * 当 request type is json
 * @author liu xw
 * @since 2024 05-07
 */
public interface HttpServletRequestBodyWrapper extends HttpServletRequest {

    /**
     * 获取body内容信息
     */
    byte[] bodyInfo();
}
