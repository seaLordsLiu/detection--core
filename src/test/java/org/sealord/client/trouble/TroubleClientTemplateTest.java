package org.sealord.client.trouble;

import org.junit.Before;
import org.junit.Test;
import org.sealord.config.Configuration;
import org.sealord.config.TroubleConfig;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author liu xw
 * @date 2024 04-29
 */
public class TroubleClientTemplateTest {

    private TroubleClientTemplate troubleClientTemplate;

    @Before
    public void setUp() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setApplicationName("test_application");
        configuration.setEvnLabel("test");

        TroubleConfig config = new TroubleConfig();
        configuration.setTrouble(config);

        this.troubleClientTemplate = new TroubleClientTemplate(configuration);
    }

    @Test
    public void testReportTroubleTest() throws IOException {
        int count = 0;
        long start = Instant.now().getEpochSecond();

        while (true){
            if (Instant.now().getEpochSecond() - start > 1) {
                break;
            }
            count++;
            troubleClientTemplate.reportTrouble(new RuntimeException("测试异常信息"));
        }
        System.out.println("一秒可以处理的请求次数: " + count);
    }

    @Test
    public void testReportTrouble() {
        // 创建20个线程进行并发
        ExecutorService pool = Executors.newFixedThreadPool(1);


        long start = System.currentTimeMillis();
        // 20个线程并发处理 1w条数据, 查看需要多长时间
        for (int i = 0; i < 10000; i++) {
            pool.execute(() -> {
                try {
                    troubleClientTemplate.reportTrouble(new RuntimeException("测试异常信息"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        pool.shutdown();
        System.out.println("shutdown()：启动一次顺序关闭，执行以前提交的任务，但不接受新任务。");
        while(true){
            if(pool.isTerminated()){
                System.out.println("所有的子线程都结束了！");
                break;
            }
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}