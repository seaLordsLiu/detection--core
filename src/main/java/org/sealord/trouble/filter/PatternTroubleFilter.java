package org.sealord.trouble.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基于一个正则表达式的异常拦截
 * 正则表达式主要判断 Throwable 的异常信息
 * @author liu xw
 * @since 2024 05-31
 */
public class PatternTroubleFilter extends ClassTroubleFilter{

    private final Pattern pattern;

    /**
     * 默认构造方法
     * @param unallowedClass 排除的异常信息
     * @param pStr 正则表达式字符
     */
    public PatternTroubleFilter(Class<? extends Throwable> unallowedClass, String pStr) {
        super(unallowedClass);
        this.pattern = Pattern.compile(pStr);
    }


    @Override
    public boolean condition(Throwable throwable) {
        Matcher matcher = pattern.matcher(throwable.getMessage());
        return matcher.matches();
    }
}
