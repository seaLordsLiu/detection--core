package org.sealord.trouble.content;

import org.sealord.Configuration;
import org.sealord.trouble.TroubleContent;

import java.time.Instant;
import java.time.LocalDate;

/**
 * 默认的内容实现类
 * @author liu xw
 * @since 2024 05-22
 */
public class SimpleTroubleContent implements TroubleContent {

    /**
     * 故障发生时间戳 (秒), 会有些许偏差, 但是基本可以忽视
     */
    private final Long reportTime;

    /**
     * 异常信息
     */
    private final Throwable throwable;


    public SimpleTroubleContent(Throwable throwable) {
        this.reportTime = Instant.now().getEpochSecond();
        this.throwable = throwable;
    }

    @Override
    public Long reportTime() {
        return this.reportTime;
    }

    @Override
    public Throwable throwable() {
        return this.throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
