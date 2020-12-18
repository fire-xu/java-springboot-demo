package net.aas.unomi.points.controller;

import net.aas.unomi.points.common.Db;
import net.aas.unomi.points.common.Product;
import net.aas.unomi.points.common.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping("/all")
    public Resp getDBProduct() {
        return new Resp("ok", 200, Db.getProductIdList());
    }

    @GetMapping
    public Resp listAll() {
        List<Product> list = Db.genProductList(10);
        return new Resp("ok", 200, list);
    }

    @GetMapping("/{pid}")
    public Resp getProduct(@PathVariable("pid") String pid) {
        return new Resp("ok", 200, Db.getProduct(pid));
    }
}
