package net.aas.unomi.points.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class Profile {
    private String name;
    private List<Order> orders;
    private Set<String> collects;

    public Profile(String name) {
        this.name = name;
        this.orders = new ArrayList<>();
        this.collects = new HashSet<>();
    }
}
