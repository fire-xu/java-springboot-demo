package net.aas.unomi.search.event;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import net.aas.unomi.search.common.feign.FeignFactory;
import org.apache.unomi.api.Event;
import org.apache.unomi.api.EventsCollectorRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventCollectorTest {
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();

    @Test
    @BenchmarkOptions(warmupRounds = 0, concurrency = 10, benchmarkRounds = 100)
    public void eventcollector() {
        FeignFactory factory = FeignFactory.getInstance();

        EventCollector collector = factory.create(EventCollector.class, "http://192.168.6.150:8181");

        List<Event> evts = new ArrayList<>();
        Event event = new Event();
        event.setEventType("demo");
        event.setSessionId("1");
        event.setScope("systemscope");
        evts.add(event);
        EventsCollectorRequest e = new EventsCollectorRequest();
        e.setSessionId("1");
        e.setEvents(evts);

//        ObjectMapper mapper = CustomObjectMapper.getObjectMapper();
//        try {
//            String s = mapper.writeValueAsString(e);
//            System.out.println(s);
//        } catch (JsonProcessingException ex) {
//            ex.printStackTrace();
//        }


        Map<String, Integer> r = collector.eventcollector(e);

//        System.out.println(r);
    }
}