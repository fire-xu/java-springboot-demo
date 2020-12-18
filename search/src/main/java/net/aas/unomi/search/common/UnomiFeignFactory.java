package net.aas.unomi.search.common;

import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.httpclient.ApacheHttpClient;

public class UnomiFeignFactory {

    static BasicHeaderInterceptor basicHeaderInterceptor = null;
    static UnomiConfiguration configuration = null;
    static Client client = null;
    static Encoder encoder;
    static Decoder decoder;

    public synchronized static <T> T create(Class<T> apiType) {
        if (basicHeaderInterceptor == null) {
            init();
        }

        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .client(client)
                .requestInterceptor(basicHeaderInterceptor)
                .target(apiType, configuration.getBaseUrl());
    }

    private static void init() {
        configuration = UnomiConfigurationParser.parse();
        basicHeaderInterceptor = new BasicHeaderInterceptor(configuration.getUsername(), configuration.getPassword());
        client = new ApacheHttpClient();
        encoder = new GsonEncoder();
        decoder = new GsonDecoder();
    }
}
