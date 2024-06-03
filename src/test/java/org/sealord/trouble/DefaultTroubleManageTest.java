package org.sealord.trouble;

import org.junit.Before;
import org.junit.Test;
import org.sealord.Configuration;
import org.sealord.config.TroubleConfig;
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


        DefaultTroubleManage troubleManage = new DefaultTroubleManage();
        // 补充拦截器
        PatternTroubleFilter patternTroubleFilter = new PatternTroubleFilter(RuntimeException.class, ".*test.*");
        troubleManage.setFilter(Collections.singletonList(patternTroubleFilter));

        this.troubleManage = troubleManage;

    }

    @Test
    public void troubleTest() throws IOException {
        troubleManage.trouble(new RuntimeException("hello"));
    }
}