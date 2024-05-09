package org.sealord.http;

import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求实体
 * @author liu xw
 * @since 2024 04-10
 */
public class RequestHttpEntity {

    public RequestHttpEntity(Object body) {
        this.body = body;
        this.headers = new ArrayList<>();
    }

    /**
     * 请求体
     */
    private final Object body;

    /**
     * 请求头信息
     */
    private final List<Header> headers;


    public Object getBody() {
        return body;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public RequestHttpEntity addHeader(Header header) {
        this.headers.add(header);
        return this;
    }

    public RequestHttpEntity addHeader(String key, String value) {
        return addHeader(new BasicHeader(key, value, Boolean.FALSE));
    }
}
