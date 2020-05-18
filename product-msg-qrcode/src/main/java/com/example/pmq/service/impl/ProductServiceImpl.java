package com.example.pmq.service.impl;

import com.example.pmq.domain.Product;
import com.example.pmq.generator.QRCodeGenerator;
import com.example.pmq.mapper.ProductMapper;
import com.example.pmq.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.pmq.mapper.ProductDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;

    @Override
    public Product getProduct(String productIdVal) {
        return productMapper.selectOne(dsl -> dsl.where(productId, isEqualTo(productIdVal))).orElse(null);
    }

    @Override
    public List<Product> getProduct(Product condition) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(Product product) throws Exception {
        if (product.getId() == null) {
            // 新增
            String httpUrl = "http://localhost:8080/v1/product/" + product.getProductId();
            byte[] bytes = QRCodeGenerator.content2Bytes(httpUrl);
            product.setProductQrcode(bytes);
            if (product.getPruductCreatedTime() == null) {
                product.setPruductCreatedTime(LocalDateTime.now());
            }
            productMapper.insert(product);
        } else {
            // 修改
            if (product.getProductId() != null) {
                // 修改产品标识符
                String httpUrl = "http://localhost:8080/v1/product/" + product.getProductId();
                byte[] bytes = QRCodeGenerator.content2Bytes(httpUrl);
                product.setProductQrcode(bytes);
            }
            product.setProductId(null);
            product.setPruductCreatedTime(null);
            productMapper.updateByPrimaryKeySelective(product);
        }
    }

    @Override
    public void saveProduct(List<Product> products) {

    }

    @Override
    public void removeProduct(String productId) {

    }

    @Override
    public void removeProductForever(String productId) {

    }
}
