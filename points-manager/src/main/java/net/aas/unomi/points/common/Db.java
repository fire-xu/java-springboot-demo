package net.aas.unomi.points.common;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Db {
    private static Map<String, Profile> profileMap = new HashMap<>();
    private static Map<String, Product> repo = new HashMap<>();
    private static List<String> productIdList = new ArrayList<>();

    public static List<String> getProductIdList() {
        return productIdList;
    }

    static {
        //100个
        IntStream intStream = new Random().ints(10, 1000);
        intStream.limit(100)
                .mapToObj(e -> new Product(UUID.randomUUID().toString().replaceAll("-", ""), e))
                .forEach(product -> {
                    productIdList.add(product.getProductId());
                    repo.put(product.getProductId(), product);
                });
    }

    public static Product getProduct(String pid) {
        return repo.get(pid);
    }

    public static List<Product> genProductList(int count) {
        return new Random()
                .ints(0, repo.size())
                .limit(count)
                .mapToObj(productIdList::get)
                .collect(Collectors.toSet())
                .stream()
                .map(repo::get)
                .collect(Collectors.toList());
    }

    //
    public static List<Profile> getProfiles() {
        return new ArrayList<>(profileMap.values());
    }

    public static Profile getProfile(String name) {
        Profile profile = profileMap.get(name);
        if (profile == null) {
            profileMap.put(name, new Profile(name));
        }
        return profileMap.get(name);
    }

    public static void putProfile(Profile profile) {
        profileMap.put(profile.getName(), profile);
    }

    public static void removeProfile(Profile profile) {
        removeProfile(profile.getName());
    }

    public static void removeProfile(String name) {
        profileMap.remove(name);
    }
}
