package org.sealord.remote;

import org.sealord.ConfigurationWrapper;

/**
 * 远程地址配置
 * @author liu xw
 * @since 2024 05-24
 */
public class Url {

    /**
     * 重制远程地址信息
     */
    public static String remote(){
        String remoteAddress = ConfigurationWrapper.getConfiguration().getRemoteAddress();
        if (remoteAddress == null){
            throw new IllegalArgumentException("remote address is null");
        }
        return remoteAddress;
    }

    /**
     * 故障
     */
    public static class Trouble {
        /**
         * 上报
         */
        public static final String RECEIVE = "/detection/trouble-api/upload";
    }
}
