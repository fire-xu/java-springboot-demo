package net.aas.unomi.search.common;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicHeaderInterceptor implements RequestInterceptor {
    private String code;

    public BasicHeaderInterceptor(String username, String password) {
        this.code = basic(username, password);
    }

    private String basic(String username, String password) {
        String token = username + ":" + password;
        byte[] bytes = token.getBytes(StandardCharsets.ISO_8859_1);
        String encoded = Base64.getEncoder().encodeToString(bytes);
        return "Basic " + encoded;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", code);
    }
}
