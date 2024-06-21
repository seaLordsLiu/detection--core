package org.sealord.http;

import org.apache.hc.core5.pool.PoolStats;

/**
 *
 * 连接管理状态监控程序
 * @author liu xw
 * @since 2024 06-03
 */
@FunctionalInterface
public interface ConnectionManagerStateTracker {

    public void tracker(PoolStats poolStats);
}
