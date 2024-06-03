package org.sealord;

/**
 * 提供了一个全局的 Configuration 配置信息
 * @author liu xw
 * @since 2024 05-24
 */
public class ConfigurationWrapper {

    /**
     * 配置
     */
    private static Configuration configuration = null;

    /**
     * 初始化配置信息
     * @param configuration 配置
     * @throws IllegalAccessException 设置异常
     */
    public static void initConfiguration(Configuration configuration) throws IllegalAccessException {
        if (configuration == null){
            throw new IllegalArgumentException("configuration can not null.");
        }

        if (ConfigurationWrapper.configuration != null){
            throw new IllegalAccessException("ConfigurationWrapper.configuration init is over.");
        }


        ConfigurationWrapper.configuration = configuration;
    }


    /**
     * 获取配置信息
     * @return 配置信息
     */
    public static Configuration getConfiguration() {
        return configuration;
    }
}
