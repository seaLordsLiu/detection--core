package org.sealord.trouble.content;

import org.sealord.Configuration;
import org.sealord.trouble.TroubleWebContent;
import org.sealord.trouble.content.pojo.HttpContent;

/**
 * @author liu xw
 * @since 2024 05-23
 */
public class SimpleWebTroubleContent extends SimpleTroubleContent implements TroubleWebContent {

    /**
     * http 信息
     */
    private final HttpContent httpContent;


    public SimpleWebTroubleContent(Throwable throwable, HttpContent hc) {
        super(throwable);
        this.httpContent = hc;
    }

    @Override
    public HttpContent httpContent() {
        return this.httpContent;
    }
}
