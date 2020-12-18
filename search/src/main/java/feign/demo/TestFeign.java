package feign.demo;

import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;

public class TestFeign {

    public static void main(String[] args) {
        GitHub gitHub = Feign.builder()
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");

        List<Contributor> contributors = gitHub.contributors("OpenFeign", "feign");
        contributors.forEach(contributor -> {
            System.out.println(contributor.getLogin() + "   " + contributor.getContributions());
        });
    }
}
