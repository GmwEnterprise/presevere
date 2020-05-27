package com.example.pmq.vo;

import com.example.pmq.domain.Product;

public class ProductVO extends Product {

    private String base64qrcode;

    public String getBase64qrcode() {
        return base64qrcode;
    }

    public void setBase64qrcode(String base64qrcode) {
        this.base64qrcode = base64qrcode;
    }
}
