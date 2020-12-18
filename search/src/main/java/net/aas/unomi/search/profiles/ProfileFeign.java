package net.aas.unomi.search.profiles;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.net.URI;

public interface ProfileFeign {
    @RequestLine("GET /profiles/count")
    long getAllProfilesCount();

    @RequestLine("GET /profiles/count")
    @Headers(value = {
            "Authorization: Basic {token}"
    })
    long getAllProfilesCount(@Param("token") String token);

    @RequestLine("GET /profiles/count")
    @Headers(value = {
            "Authorization: Basic {token}"
    })
    long getAllProfilesCount(URI host, @Param("token") String token);
}
