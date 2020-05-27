package com.example.pmq.domain;

import java.io.Serializable;
import javax.annotation.Generated;

public class PmqParam implements Serializable {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String paramCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String paramDesc;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String paramValue;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private static final long serialVersionUID = 1L;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getParamCode() {
        return paramCode;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getParamDesc() {
        return paramDesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getParamValue() {
        return paramValue;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}