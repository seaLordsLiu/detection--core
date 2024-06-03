package org.sealord.http;

import java.io.Serializable;

/**
 * @author liu xw
 * @since 2024 04-29
 */
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
    private int maxTotal = 100;

    /**
     * 每个路由最大连接数
     * 默认值：2
     */
    private int maxPreRoute = 100;

    /**
     * 连接存活时长：秒
     */
    private long connectionTimeToLive = 60;


    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxPreRoute() {
        return maxPreRoute;
    }

    public void setMaxPreRoute(int maxPreRoute) {
        this.maxPreRoute = maxPreRoute;
    }

    public long getConnectionTimeToLive() {
        return connectionTimeToLive;
    }

    public void setConnectionTimeToLive(long connectionTimeToLive) {
        this.connectionTimeToLive = connectionTimeToLive;
    }
}
