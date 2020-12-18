package net.aas.unomi.search.common.feign;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import net.aas.unomi.search.common.json.CustomJackson;

/**
 * 创建 Feign客户端
 * 会使用缓存
 *
 * @author wpp
 */
public class FeignFactory {
    FeignCacheRepository cacheRepository;
    JacksonDecoder decoder;
    JacksonEncoder encoder;

    private static final FeignFactory basic = new FeignFactory();

    public static FeignFactory getInstance() {
        return basic;
    }

    public FeignFactory() {
        cacheRepository = new FeignCacheRepository();
        decoder = CustomJackson.getDecoder();
        encoder = CustomJackson.getEncoder();
    }

    public FeignFactory(FeignCacheRepository cacheRepository, JacksonDecoder decoder, JacksonEncoder encoder) {
        this.cacheRepository = cacheRepository;
        this.decoder = decoder;
        this.encoder = encoder;
    }

    /**
     * 创建一个Feign客户端
     *
     * @param apiType
     * @param url
     * @param <T>
     * @return
     */
    public synchronized <T> T create(Class<T> apiType, String url) {
        Object client = cacheRepository.get(apiType, url);
        if (client == null) {
            client = Feign.builder().encoder(getEncoder())
                    .decoder(getDecoder())
                    .target(apiType, url);
            cacheRepository.set(apiType, url, client);
        }
        return (T) client;
    }

    public FeignCacheRepository getCacheRepository() {
        return cacheRepository;
    }

    public void setCacheRepository(FeignCacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public JacksonDecoder getDecoder() {
        return decoder;
    }

    public void setDecoder(JacksonDecoder decoder) {
        this.decoder = decoder;
    }

    public JacksonEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(JacksonEncoder encoder) {
        this.encoder = encoder;
    }


}
