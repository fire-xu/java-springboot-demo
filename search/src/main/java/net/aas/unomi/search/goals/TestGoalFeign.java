package net.aas.unomi.search.goals;

import feign.*;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.httpclient.ApacheHttpClient;
import net.aas.unomi.search.common.BasicHeaderInterceptor;
import org.apache.unomi.api.Metadata;
import org.apache.unomi.api.goals.Goal;

import java.util.Set;

public class TestGoalFeign {
    public static void main(String[] args) {

        GoalFeign goalFeign = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .client(new ApacheHttpClient())
                .requestInterceptor(new BasicHeaderInterceptor("karaf", "karaf"))
                .target(GoalFeign.class, "http://192.168.6.150:8181/cxs");

        Set<Metadata> goalMetadatas = goalFeign.getGoalMetadatas();
        System.out.println(goalMetadatas);
        goalMetadatas.forEach(
                m -> {
                    System.out.println(m.getId());
                    Goal demogoal = goalFeign.getGoal(m.getId());
                    System.out.println(demogoal.getScope());
                }
        );
    }
}


