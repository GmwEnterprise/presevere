package com.example.pmq.controller;

import com.example.pmq.domain.Product;
import com.example.pmq.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/page")
public class ToPageController {
    @Resource
    ProductService productService;

    @RequestMapping(value = "/product/{productId}")
    public String productPage(Model m, @PathVariable String productId) {
        Product product = productService.getProduct(productId);
        if (product != null && product.getProductId() != null) {
            m.addAttribute("product", product);
            return "product";
        }
        return "404-not-found";
    }
}
