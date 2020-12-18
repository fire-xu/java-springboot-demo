package net.aas.unomi.search.common.feign;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wpp
 */
public class FeignCacheRepository {
    ConcurrentHashMap<String, Object> repo = new ConcurrentHashMap<>();

    /**
     * 计算出 api和url的key
     *
     * @param api
     * @param url
     * @return
     */
    public static String key(Class api, String url) {
        return api.getName() + "@@" + url;
    }

    public Object get(String key) {
        return repo.get(key);
    }

    public Object get(Class api, String url) {
        return get(key(api, url));
    }

    public void set(String key, Object client) {
        repo.put(key, client);
    }

    public void set(Class api, String url, Object client) {
        set(key(api, url), client);
    }

}
