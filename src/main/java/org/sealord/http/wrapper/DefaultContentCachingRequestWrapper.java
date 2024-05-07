package org.sealord.http.wrapper;

import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认的消息处理工具
 * 对 ContentCachingRequestWrapper 的扩展
 * @author liu xw
 * @date 2024 05-07
 */
public class DefaultContentCachingRequestWrapper extends ContentCachingRequestWrapper implements PostBodyRequestWrapper{

    public DefaultContentCachingRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public byte[] getBody() {
        return this.getContentAsByteArray();
    }
}
