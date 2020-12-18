package net.aas.unomi.search.event;

import feign.Headers;
import feign.RequestLine;
import org.apache.unomi.api.ContextRequest;
import org.apache.unomi.api.ContextResponse;

/**
 * 发送数据到
 * /context.json
 *
 * @author wpp
 */
public interface ContextJson {

    /**
     * 发送到 /context.json
     *
     * @param request
     * @return
     */
    @RequestLine("POST /context.json")
    @Headers({
            "Content-Type: application/json;"
    })
    ContextResponse contextJson(ContextRequest request);
}
