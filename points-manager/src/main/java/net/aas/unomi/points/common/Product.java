package net.aas.unomi.points.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class Product implements Serializable {
    private String productId;
    private Integer price;

    public Product(String productId, Integer price) {
        this.productId = productId;
        this.price = price;
    }
}
