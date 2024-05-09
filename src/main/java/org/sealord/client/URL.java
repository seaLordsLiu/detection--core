package org.sealord.client;

/**
 * 配置URL请求地址信息
 * @author liu xw
 * @since 2024 04-11
 */
public class URL {

    /**
     * 远程地址
     */
    private static final String REMOTE = "http://101.34.206.121:18443";

    /**
     * 故障
     */
    public static class Trouble {
        /**
         * 上报
         */
        public static final String RECEIVE = REMOTE + "/trouble-camp-api/receive";

        /**
         * 上报 web请求异常
         */
        public static final String RECEIVE_WEB = REMOTE + "/trouble-camp-api/receive-web";
    }

}
