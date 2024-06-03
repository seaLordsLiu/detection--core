package org.sealord.trouble;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * 拦截链表
 * @author liu xw
 * @since 2024 05-31
 */
public class TroubleFilterChain implements TroubleFilter {

    private final static Logger log = LoggerFactory.getLogger(TroubleFilterChain.class);

    /**
     * 拦截器
     * 通过 Collections.unmodifiableList 最终实现为一个不可改变的链表信息
     */
    private final List<TroubleFilter> filters;

    public TroubleFilterChain(List<TroubleFilter> filters) {
        if (filters == null){
            filters = Collections.emptyList();
        }
        this.filters = Collections.unmodifiableList(filters);
    }

    @Override
    public boolean filter(TroubleContent content) {
        for (TroubleFilter filter : filters) {
            try {
                if (filter.filter(content)){
                    log.debug("filter [{}] take effect", filter.getClass());
                    return Boolean.TRUE;
                }
            }catch (Exception e){
                log.error("filter [{}] error, exception [{}]",filter.getClass(), e.getMessage(), e);
            }
        }
        return Boolean.FALSE;
    }
}
