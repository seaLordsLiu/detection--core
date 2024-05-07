package org.sealord.http.wrapper;

import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取 post 的 body 内容信息
 * 当 request type is json
 * @author liu xw
 * @date 2024 05-07
 */
public interface PostBodyRequestWrapper extends HttpServletRequest {

    /**
     * 获取body内容信息
     */
    @NonNull byte[] getBody();
}
