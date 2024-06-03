package org.sealord.trouble.delegate;

import org.sealord.api.trouble.ReportTroubleRequest;
import org.sealord.trouble.TroubleContent;
import org.sealord.trouble.TroubleWebContent;
import org.sealord.trouble.content.pojo.HttpContent;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 内容的定制处理器
 * @author liu xw
 * @since 2024 04-24
 */
public class HttpTroubleContentCustomizer {

    /**
     * 构造内容信息
     * @param troubleContent 内容信息
     */
    public ReportTroubleRequest buildContent(TroubleContent troubleContent) {
        ReportTroubleRequest request = new ReportTroubleRequest();

        // 处理 throwable
        mappingContentOnThrowable(troubleContent.throwable(), request);

        if (troubleContent instanceof TroubleWebContent) {
            // 补充 web
            mappingContentOnWeb(((TroubleWebContent) troubleContent).httpContent(), request);
        }

        // 上报时间
        request.setReportTime(troubleContent.reportTime());
        return request;
    }


    /**
     * 映射 TroubleContent 内容参数信息
     * @param throwable 异常
     * @param tc 内容
     */
    private void mappingContentOnThrowable(Throwable throwable, ReportTroubleRequest tc){
        // 故障代表（异常类信息）
        tc.setTrouble(throwable.getClass().getName());

        // 故障内容（异常信息）
        tc.setMessage(throwable.getMessage());

        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        // 故障信息（异常栈堆）
        tc.setInformation(stringWriter.toString());
    }

    /**
     * 映射 HTTP 基本信息
     * @param httpContent http请求内容
     * @param tc 内容
     */
    private void mappingContentOnWeb(HttpContent httpContent, ReportTroubleRequest tc){
        // 配置基础的 http 请求信息
        tc.setUrl(httpContent.getUrl());
        tc.setMethod(httpContent.getMethod());
        tc.setParam(httpContent.getParam());
        tc.setBody(httpContent.getBody());
    }
}
