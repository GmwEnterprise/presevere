package com.example.pmq.controller;

import com.example.pmq.base.Page;
import com.example.pmq.domain.Product;
import com.example.pmq.service.ProductService;
import com.example.pmq.vo.ProductVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public Product getProductByProductId(@PathVariable String productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    public Page<ProductVO> products(@RequestParam(required = false) String productId,
                                    @RequestParam(required = false) String productName,
                                    // 分页信息
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize) {
        Product condition = new Product();
        condition.setProductId(productId);
        condition.setProductName(productName);
        if (pageNum < 1) {
            pageNum = 1; // 默认第1页开始
        }
        if (pageSize < 1) {
            pageSize = 10; // 默认10条一页
        }
        return productService.getProduct(condition, pageNum, pageSize);
    }

    @PostMapping
    public void add(@RequestBody Product product) throws Exception {
        productService.saveProduct(product);
    }
}
