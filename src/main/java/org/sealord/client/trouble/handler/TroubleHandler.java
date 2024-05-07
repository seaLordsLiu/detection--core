package org.sealord.client.trouble.handler;

import org.sealord.client.trouble.TroubleContent;
import org.sealord.http.ByteEntity;

/**
 * 处理类
 * @author liu xw
 * @date 2024 04-24
 */
public interface TroubleHandler {

    /**
     * 处理
     * @param content 内容
     * @return 结果
     */
    ByteEntity handler(TroubleContent content);
}
