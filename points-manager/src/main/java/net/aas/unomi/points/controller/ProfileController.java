package net.aas.unomi.points.controller;

import lombok.Data;
import net.aas.unomi.points.common.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @GetMapping
    public Resp listProfiles() {
        return new Resp("ok", 200, Db.getProfiles());
    }

    @GetMapping("/{name}")
    public Resp getProfile(@PathVariable("name") String name) {
        return new Resp("ok", 200, Db.getProfile(name));
    }

    @PostMapping("/{name}/collect")
    public Resp addCollect(@PathVariable("name") String name, @RequestBody Pid pid) {
        Profile profile = Db.getProfile(name);
        if (profile.getCollects().contains(pid.getPid())) {
            return new Resp("Exist!", 100, null);
        }
        profile.getCollects().add(pid.getPid());
        return new Resp("ok", 200, null);
    }

    @DeleteMapping("/{name}/collect/{pid}")
    public Resp removeCollect(@PathVariable("name") String name, @PathVariable("pid") String pid) {
        Profile profile = Db.getProfile(name);
        if (profile.getCollects().contains(pid)) {
            profile.getCollects().remove(pid);
            return new Resp("ok", 200, null);
        }
        return new Resp("No Exist!", 100, null);
    }

    @PostMapping("/{name}/orders")
    public Resp addOrder(@PathVariable("name") String name, @RequestBody List<OrderDetail> orderDetails) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            return new Resp("Orders is Empty!", 100, null);
        }

        Profile profile = Db.getProfile(name);
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-", ""));
//        order.setTime(System.currentTimeMillis());
        order.setProducts(orderDetails);
        profile.getOrders().add(order);

        return new Resp("ok", 200, order);
    }

    @DeleteMapping("/{name}/orders/{orderId}")
    public Resp removeOrder(@PathVariable("name") String name, @PathVariable("orderId") String orderId) {
        Profile profile = Db.getProfile(name);
        Iterator<Order> iterator = profile.getOrders().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getOrderId().equals(orderId)) {
                iterator.remove();
                return new Resp("ok", 200, null);
            }
        }
        return new Resp("Not Found", 100, null);
    }

    @Data
    static class Pid {
        private String pid;
    }
}
