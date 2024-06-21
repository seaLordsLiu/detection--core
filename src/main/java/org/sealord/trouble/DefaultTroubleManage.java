package org.sealord.trouble;

import org.sealord.ConfigurationWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * 管理功能实现类
 * @author liu xw
 * @since 2024 05-23
 */
public class DefaultTroubleManage implements TroubleManage{

    private static final Logger log = LoggerFactory.getLogger(TroubleManage.class);

    /**
     * 问题内容构造
     */
    private final TroubleContentGenerator troubleContentGenerator;

    /**
     * 问题委托
     */
    private final TroubleDelegate troubleDelegate;

    /**
     * 执行拦截器
     * 默认返回 FALSE
     */
    private final TroubleFilter filter;

    public DefaultTroubleManage(TroubleContentGenerator troubleContentGenerator, TroubleDelegate troubleDelegate, List<TroubleFilter> filters) {
        this.troubleContentGenerator = troubleContentGenerator;
        this.troubleDelegate = new AsyncProxyTroubleDelegate(troubleDelegate, ConfigurationWrapper.getConfiguration().getTrouble().getCorePoolSize());
        this.filter = new TroubleFilterChain(filters);
    }

    @Override
    public void trouble(Throwable throwable) throws IOException {
        trouble(throwable, null);
    }

    @Override
    public void trouble(Throwable throwable, HttpServletRequest request) throws IOException {
        try {
            // 转化请求内容信息
            TroubleContent generator = troubleContentGenerator.generator(throwable, request);

            // 调用拦截器进行
            if (filter.filter(generator)) {
                return;
            }
            // 委托处理程序
            troubleDelegate.delegate(generator);
        }catch (Exception e){
            // 处理异常
            log.error("execute exception. {}", e.getMessage());
        }
    }


    /**
     * 异步扩展的委托扩展, 避免主业务流程被阻塞
     */
    private static class AsyncProxyTroubleDelegate implements TroubleDelegate{

        /**
         * 线程池
         */
        private final ThreadPoolExecutor executor;

        /**
         * 真实的委托对象信息
         */
        private final TroubleDelegate delegate;


        public AsyncProxyTroubleDelegate(TroubleDelegate delegate, Integer core) {
            this.delegate = delegate;
            this.executor = initExecutor(core);
        }

        @Override
        public void delegate(TroubleContent tCon) {
            executor.execute(() -> delegate.delegate(tCon));
        }


        /**
         * 初始化
         * @param core 核心线程数量
         * @return 线程池
         */
        private ThreadPoolExecutor initExecutor(Integer core){
            if (core == null || core == 0){
                core = 10;
            }
            // Create the thread pool
            return new ThreadPoolExecutor(
                    core,                           // Core thread pool size
                    core * 2,                       // Maximum thread pool size
                    60L, TimeUnit.SECONDS,          // Keep-alive time for idle threads
                    new LinkedBlockingQueue<>(1000) // Task queue with a capacity of 1000
            ) {
                @Override
                protected void beforeExecute(Thread t, Runnable r) {
                    super.beforeExecute(t, r);
                    if (log.isDebugEnabled()) {
                        log.debug("Thread [{}] is going to execute task [{}]. thread pool activeCount: [{}]", t, r, executor.getActiveCount());
                    }
                }

                @Override
                protected void afterExecute(Runnable r, Throwable t) {
                    super.afterExecute(r, t);
                    if (t != null){
                        log.error("Task [{}] executed error. thread pool activeCount: [{}]", r, executor.getActiveCount(), t);
                        return;
                    }
                    if (log.isDebugEnabled()){
                        log.debug("Task [{}] executed success. thread pool activeCount: [{}]", r, executor.getActiveCount());
                    }
                }
            };
        }
    }
}
