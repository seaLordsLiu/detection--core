package org.sealord.trouble;

import org.junit.Before;
import org.junit.Test;
import org.sealord.Configuration;
import org.sealord.config.TroubleConfig;
import org.sealord.trouble.delegate.HttpTroubleDelegate;
import org.sealord.trouble.filter.PatternTroubleFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * @author liu xw
 * @since 2024 05-31
 */

public class DefaultTroubleManageTest {


    private TroubleManage troubleManage;

    @Before
    public void before() throws IllegalAccessException {
        // 1. 初始化 Configuration 信息
        Configuration configuration = new Configuration();
        configuration.setApplicationName("test");
        configuration.setEvnLabel("test");
        configuration.setRemoteAddress("http://101.34.206.121:18443");

        TroubleConfig troubleConfig = new TroubleConfig();
        configuration.setTrouble(troubleConfig);


        // 数据构造器
        TroubleContentGenerator generator = new SimpleTroubleContentGenerator();
        // 请求委托器（HTTP）
        TroubleDelegate delegate = new HttpTroubleDelegate();
        // 补充拦截器
        PatternTroubleFilter filter = new PatternTroubleFilter(RuntimeException.class, ".*test.*");
        // 异常管理工具
        this.troubleManage = new DefaultTroubleManage(generator, delegate, Collections.singletonList(filter));

    }

    @Test
    public void troubleTest() throws IOException {
        troubleManage.trouble(new RuntimeException("hello"));
    }
}