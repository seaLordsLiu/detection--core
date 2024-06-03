package org.sealord.trouble;

import org.sealord.trouble.delegate.HttpTroubleDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 管理功能实现类
 * @author liu xw
 * @since 2024 05-23
 */
public class DefaultTroubleManage implements TroubleManage{

    private final Logger log = LoggerFactory.getLogger(DefaultTroubleManage.class);

    /**
     * 问题内容构造
     */
    private TroubleContentGenerator troubleContentGenerator = new SimpleTroubleContentGenerator();;

    /**
     * 问题委托
     */
    private TroubleDelegate troubleDelegate = new HttpTroubleDelegate();

    /**
     * 执行拦截器
     * 默认返回 FALSE
     */
    private TroubleFilter filter = content -> Boolean.FALSE;


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
            if (Objects.nonNull(filter) && filter.filter(generator)) {
                return;
            }
            // 委托处理程序
            troubleDelegate.delegate(generator);
        }catch (Exception e){
            // 处理异常
            log.error("execute exception. {}", e.getMessage());
        }
    }

    public void setTroubleContentGenerator(TroubleContentGenerator troubleContentGenerator) {
        this.troubleContentGenerator = troubleContentGenerator;
    }

    public void setTroubleDelegate(TroubleDelegate troubleDelegate) {
        this.troubleDelegate = troubleDelegate;
    }

    public void setFilter(List<TroubleFilter> filters){
        this.filter = new TroubleFilterChain(filters);
    }
}
