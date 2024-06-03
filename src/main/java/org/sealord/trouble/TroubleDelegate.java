package org.sealord.trouble;

/**
 * 麻烦处理器 - 真正用来处理方法信息的
 * @author liu xw
 * @since 2024 05-17
 */
public interface TroubleDelegate {

    /**
     * 委托实现
     * @param tCon 委托内容
     */
    void delegate(TroubleContent tCon);
}
