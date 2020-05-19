package com.example.pmq.service.impl;

import com.example.pmq.base.Page;
import com.example.pmq.domain.PmqParam;
import com.example.pmq.domain.Product;
import com.example.pmq.generator.QRCodeGenerator;
import com.example.pmq.mapper.PmqParamMapper;
import com.example.pmq.mapper.ProductMapper;
import com.example.pmq.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.pmq.mapper.ProductDynamicSqlSupport.productId;
import static com.example.pmq.mapper.ProductDynamicSqlSupport.productName;
import static com.example.pmq.mapper.PmqParamDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isLikeWhenPresent;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;

    @Resource
    PmqParamMapper paramMapper;

    @Override
    public Product getProduct(String productIdVal) {
        return productMapper.selectOne(dsl -> dsl
                .where(productId, isEqualTo(productIdVal)))
                .orElse(null);
    }

    @Override
    public Page<Product> getProduct(Product condition, Integer pageNum, Integer pageSize) {
        Page<Product> page = new Page<>();

        if (StringUtils.isEmpty(condition.getProductName())) {
            condition.setProductId(null);
        }
        if (StringUtils.isEmpty(condition.getProductName())) {
            condition.setProductName(null);
        }

        long count = productMapper.count(dsl -> dsl
                .where(productId, isLikeWhenPresent(condition.getProductId()))
                .and(productName, isLikeWhenPresent(condition.getProductName())));
        if (count == 0) {
            // 没有满足条件的数据，直接返回
            page.setHasData(false);
            return page;
        }

        // 有数据
        List<Product> products = productMapper.select(dsl -> dsl
                .where(productId, isLikeWhenPresent(condition.getProductId()))
                .and(productName, isLikeWhenPresent(condition.getProductName()))
                .limit(pageSize.longValue()).offset((pageNum - 1) * pageSize)
        );
        page.setHasData(true);
        page.setData(products);
        int totalPage = (int) ((count + pageSize - 1) / pageSize);
        page.setTotalPageSize(totalPage);
        if (totalPage == 1) {
            // 只有一页数据
            page.setCurrentPage(1);
            page.setHasNext(false);
            page.setHasPrev(false);
        } else if (pageNum == 1) {
            page.setCurrentPage(1);
            page.setHasPrev(false);
            page.setHasNext(true);
        } else if (pageNum == totalPage) {
            page.setCurrentPage(pageNum);
            page.setHasNext(false);
            page.setHasPrev(true);
        } else {
            page.setCurrentPage(pageNum);
            page.setHasNext(true);
            page.setHasPrev(true);
        }
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(Product product) throws Exception {
        PmqParam qrcodeLinkPrefix = paramMapper.selectOne(dsl -> dsl
                .where(paramCode, isEqualTo("QRCODE_LINK_PREFIX")))
                .orElseThrow();
        if (product.getId() == null) {
            // 新增
            String httpUrl = qrcodeLinkPrefix + product.getProductId();
            byte[] bytes = QRCodeGenerator.content2Bytes(httpUrl);
            product.setProductQrcode(bytes);
            if (product.getProductCreatedTime() == null) {
                product.setProductCreatedTime(LocalDateTime.now());
            }
            productMapper.insert(product);
        } else {
            // 修改
            if (product.getProductId() != null) {
                // 修改产品标识符
                String httpUrl = qrcodeLinkPrefix + product.getProductId();
                byte[] bytes = QRCodeGenerator.content2Bytes(httpUrl);
                product.setProductQrcode(bytes);
            }
            product.setProductId(null);
            product.setProductCreatedTime(null);
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
