package org.sealord.http;

import org.apache.hc.core5.pool.PoolStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础日志打印
 * @author liu xw
 * @since 2024 06-03
 */
public class LogConnectionManagerStateTracker implements ConnectionManagerStateTracker{

    private final static Logger log = LoggerFactory.getLogger(ConnectionManagerStateTracker.class);

    @Override
    public void tracker(PoolStats poolStats) {
        log.debug("http client connection state: {}", poolStats);
    }
}
