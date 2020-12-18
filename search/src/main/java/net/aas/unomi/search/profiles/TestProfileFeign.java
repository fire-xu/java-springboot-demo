package net.aas.unomi.search.profiles;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.jackson.JacksonDecoder;

import java.net.URI;
import java.net.URISyntaxException;

public class TestProfileFeign {
    public static void main(String[] args) throws URISyntaxException {
//        ProfileFeign profileFeign = UnomiFeignFactory.create(ProfileFeign.class);
//        long allProfilesCount = profileFeign.getAllProfilesCount();
//        System.out.println(allProfilesCount);


//        ProfileFeign profileFeign = Feign.builder().decoder(new GsonDecoder())
//                .target(ProfileFeign.class, "http://192.168.6.150:8181/cxs");
//        long allProfilesCount = profileFeign.getAllProfilesCount("a2FyYWY6a2FyYWY=");
//        System.out.println(allProfilesCount);

//        ProfileFeign profileFeign = Feign.builder().decoder(new JacksonDecoder())
//                .target(ProfileFeign.class, "http://localhost:8181/cxs");
//
//        URI uri = new URI("http://192.168.6.150:8181/cxs");
//
//        long allProfilesCount = profileFeign.getAllProfilesCount(uri, "a2FyYWY6a2FyYWY=");
//        System.out.println(allProfilesCount);


    }
}
