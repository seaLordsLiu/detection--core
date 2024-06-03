package org.sealord.trouble;

import org.sealord.trouble.content.pojo.HttpContent;

/**
 * TroubleContent扩展, 基于Web的问题, 补充request请求
 * @author liu xw
 * @since 2024 05-22
 */
public interface TroubleWebContent extends TroubleContent {

    /**
     * 获取Http请求内容信息
     * @return http 内容信息
     */
    HttpContent httpContent();

}
