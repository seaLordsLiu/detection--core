package org.sealord.trouble.delegate;

import org.sealord.Configuration;
import org.sealord.ConfigurationWrapper;
import org.sealord.RestTemplate;
import org.sealord.api.trouble.ReportTroubleRequest;
import org.sealord.http.ByteResponse;
import org.sealord.http.HttpConfig;
import org.sealord.remote.Header;
import org.sealord.remote.Url;
import org.sealord.trouble.TroubleContent;
import org.sealord.trouble.TroubleDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 基于Http实现的委托方式
 * @author liu xw
 * @since 2024 05-17
 */
public class HttpTroubleDelegate implements TroubleDelegate {

    private final static Logger log = LoggerFactory.getLogger(HttpTroubleDelegate.class);

    /**
     * http配置信息
     */
    private final RestTemplate restTemplate;

    /**
     * http请求参数构造实现类
     */
    private final HttpTroubleContentCustomizer customizer;

    public HttpTroubleDelegate(){
        this(new HttpConfig());
    }

    public HttpTroubleDelegate(HttpConfig httpConfig) {
        this.restTemplate = new RestTemplate(httpConfig);
        this.customizer = new HttpTroubleContentCustomizer();
    }

    @Override
    public void delegate(TroubleContent tCon) {
        String url = this.formatUrl(Url.Trouble.RECEIVE);
        Map<String, Object> header = defaultHeader();
        ReportTroubleRequest bodyRequest = customizer.buildContent(tCon);

        // 执行http请求信息
        ByteResponse entity = restTemplate.postJson(url, header, bodyRequest);
        if (log.isDebugEnabled()){
            log.debug("delegate execute. url: {}. header: {}. body: {}. result: {}", url, header, entity, entity);
        }
    }

    /**
     * 补充
     * @param url 请求地址
     * @return url地址
     */
    private String formatUrl(String url){
        return Url.remote() + url;
    }

    /**
     * 请求头数据信息
     * @return map
     */
    private Map<String, Object> defaultHeader(){
        Configuration configuration = ConfigurationWrapper.getConfiguration();
        Map<String, Object> header = new HashMap<>();
        header.put(Header.X_ENV_LABEL, configuration.getEvnLabel());
        header.put(Header.X_CLIENT_NAME, configuration.getApplicationName());
        return header;
    }

}