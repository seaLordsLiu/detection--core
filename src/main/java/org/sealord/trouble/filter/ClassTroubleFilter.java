package org.sealord.trouble.filter;

import org.sealord.trouble.TroubleContent;
import org.sealord.trouble.TroubleFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 继承 {@link TroubleFilter} 让他具备了拦截的权限.
 * 仅通过 Class 信息来进行拦截, 对细节的把控太差了, 这样是不好的。
 * @author liu xw
 * @since 2024 05-31
 */
public class ClassTroubleFilter implements TroubleFilter {

    /**
     * 排除异常
     */
    private final Class<? extends Throwable> unallowedClass;


    public ClassTroubleFilter(Class<? extends Throwable> unallowedClass) {
        this.unallowedClass = unallowedClass;
    }

    @Override
    public boolean filter(TroubleContent content) {
        // 判断类信息
        if (content.throwable().getClass().equals(unallowedClass)) {
            return this.condition(content.throwable());
        }
        return Boolean.FALSE;
    }


    /**
     * 额外的拦截调整.
     * @param throwable 异常内容
     * @return 调整结果
     *         true: 判断成功（符合要求, 进行拦截）
     *         false: 判断失败（不符合要求, 不拦截）
     */
    public boolean condition(Throwable throwable){
        return Boolean.TRUE;
    }
}
