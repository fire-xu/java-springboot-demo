package com.aas.feign;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import org.junit.Test;

import java.util.List;

public class TestGithub {


    interface GitHub {
        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);
    }

    static class Contributor {
        String login;
        int contributions;

        @Override
        public String toString() {
            return "Contributor{" +
                    "login='" + login + '\'' +
                    ", contributions=" + contributions +
                    '}';
        }
    }

    public static class Issue {
        String title;
        String body;
        List<String> assignees;
        int milestone;
        List<String> labels;
    }


    @Test
    public void t1() {
        GitHub gitHub = Feign.builder().decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");
        List<Contributor> contributors = gitHub.contributors("OpenFeign", "feign");
        System.out.println(contributors);
    }
}
