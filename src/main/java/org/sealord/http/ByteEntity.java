package org.sealord.http;

import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.NullEntity;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * 记录服务端返回的结果信息
 * @author liu xw
 * @date 2024 04-19
 */
public class ByteEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 返回结果
     */
    private final byte[] responseBytes;


    public ByteEntity(Integer code, String data) {
        this.code = code;
        this.responseBytes = Objects.isNull(data) ? new byte[]{} : data.getBytes();
    }

    /**
     * @param code 状态码
     *             {@link org.apache.hc.core5.http.HttpStatus}
     * @param entity 请求体内容
     */
    public ByteEntity(int code, HttpEntity entity) {
        this.code = code;
        if (entity == null || entity instanceof NullEntity){
            this.responseBytes = null;
        }else {
            try {
                this.responseBytes = EntityUtils.toByteArray(entity);
            } catch (IOException e) {
                throw new IllegalArgumentException("read entity error: " + e.getMessage(), e);
            }
        }
    }

    public Integer getCode() {
        return code;
    }

    public byte[] getResponseBytes() {
        return responseBytes;
    }
}
