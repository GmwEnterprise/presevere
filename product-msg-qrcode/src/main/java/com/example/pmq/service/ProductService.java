package com.example.pmq.service;

import com.example.pmq.base.Page;
import com.example.pmq.domain.Product;

import java.util.List;

/**
 * 产品信息服务
 */
public interface ProductService {

    /**
     * 获取单个产品信息
     * @param productId
     * @return
     */
    Product getProduct(String productId);

    /**
     * 获取多个产品信息
     * @param condition
     * @param pageStart
     * @param pageSize
     * @return
     */
    Page<Product> getProduct(Product condition, Integer pageStart, Integer pageSize);

    /**
     * 保存/更新单个产品信息
     * @param product
     */
    void saveProduct(Product product) throws Exception;

    /**
     * 批量保存产品信息
     * @param products
     */
    void saveProduct(List<Product> products);

    /**
     * 删除产品信息
     * @param productId
     */
    void removeProduct(String productId);

    /**
     * 删除产品信息，永不恢复
     * @param productId
     */
    void removeProductForever(String productId);
}
