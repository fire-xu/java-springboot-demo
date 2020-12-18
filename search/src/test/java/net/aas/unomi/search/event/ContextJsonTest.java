package net.aas.unomi.search.event;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import net.aas.unomi.search.common.json.CustomJackson;
import org.apache.unomi.api.ContextRequest;
import org.apache.unomi.api.ContextResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.ArrayList;
import java.util.List;


public class ContextJsonTest {

    ObjectMapper mapper = new ObjectMapper();

    @Rule
    public TestRule rule = new BenchmarkRule();

    @Test
    @BenchmarkOptions(warmupRounds = 10, concurrency = 10, benchmarkRounds = 100)
    public void contextJson() throws JsonProcessingException {

        ContextJson contextJson = Feign.builder()
                .encoder(CustomJackson.getEncoder())
                .decoder(CustomJackson.getDecoder())
                .target(ContextJson.class, "http://192.168.6.150:8181");


        ContextRequest request = new ContextRequest();
        List<String> list = new ArrayList<String>() {
        };
        list.add("*");
        request.setRequiredProfileProperties(list);
        request.setRequiredSessionProperties(list);
        request.setSessionId("1");
        ContextResponse c1 = contextJson.contextJson(request);


//        System.out.println(mapper.writeValueAsString(c1));
    }
}