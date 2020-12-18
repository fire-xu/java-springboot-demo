package net.aas.unomi.points.common;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private List<OrderDetail> products;
//    private Long time;
    private String orderId;
}
