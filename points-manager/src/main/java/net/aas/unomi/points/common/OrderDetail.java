package net.aas.unomi.points.common;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderDetail {
    private String productId;
    private Integer price;
    private Integer count;
}
