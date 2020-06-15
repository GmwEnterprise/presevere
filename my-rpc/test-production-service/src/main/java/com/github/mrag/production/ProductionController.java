package com.github.mrag.production;

import com.github.mrag.modules.api.ProductionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProductionController {

    @Resource
    ProductionService productionService;

    @GetMapping("/test")
    public void test() {
        System.out.println(productionService.owner(1));
    }
}
