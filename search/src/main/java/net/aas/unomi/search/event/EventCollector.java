package net.aas.unomi.search.event;

import feign.Headers;
import feign.RequestLine;
import org.apache.unomi.api.EventsCollectorRequest;

import java.util.Map;

/**
 * 发送到
 *
 * @author wpp
 */
public interface EventCollector {

    /**
     * @param eventsCollectorRequest
     * @return
     */
    @RequestLine("POST /eventcollector")
    @Headers({
            "Content-Type: application/json;"
    })
    Map<String, Integer> eventcollector(EventsCollectorRequest eventsCollectorRequest);
}
