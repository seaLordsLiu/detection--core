package org.sealord.http;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author liu xw
 * @date 2024 04-29
 */
@Getter
@Setter
public class HttpConfig implements Serializable {

    /**
     * 从连接管理器请求连接时使用的超时时间（单位/毫秒）
     * 默认值： -1，为无限超时。
     */
    private int connectionRequestTimeout = 1000;

    /**
     * 确定建立连接之前的超时时间（单位/毫秒）
     * 默认值： -1，为无限超时。
     */
    private int connectTimeout = 1000;

    /**
     * 等待数据的超时（单位/毫秒）
     * 默认值： -1，为无限超时。
     */
    private int socketTimeout = 2000;

    /**
     * 连接池最大连接数
     * 默认值：20
     */
    private int maxTotal = 50;

    /**
     * 每个路由最大连接数
     * 默认值：2
     */
    private int maxPreRoute = 50;

    /**
     * 连接存活时长：秒
     */
    private long connectionTimeToLive = 60;
}
