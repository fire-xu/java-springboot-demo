package net.aas.unomi.search.common.json;

import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

public class CustomJackson {
    public static JacksonEncoder getEncoder() {
        return Holder.encoder;
    }

    public static JacksonDecoder getDecoder() {
        return Holder.decoder;
    }

    private static class Holder {
        static final JacksonEncoder encoder = new JacksonEncoder(CustomObjectMapper.getObjectMapper());
        static final JacksonDecoder decoder = new JacksonDecoder(CustomObjectMapper.getObjectMapper());
    }
}
