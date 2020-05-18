package com.example.pmq.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.annotation.Generated;

public class Product implements Serializable {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String productId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String productName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String productDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private LocalDateTime pruductCreatedTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private byte[] productQrcode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private static final long serialVersionUID = 1L;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getProductId() {
        return productId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getProductName() {
        return productName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getProductDesc() {
        return productDesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public LocalDateTime getPruductCreatedTime() {
        return pruductCreatedTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPruductCreatedTime(LocalDateTime pruductCreatedTime) {
        this.pruductCreatedTime = pruductCreatedTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public byte[] getProductQrcode() {
        return productQrcode;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setProductQrcode(byte[] productQrcode) {
        this.productQrcode = productQrcode;
    }
}