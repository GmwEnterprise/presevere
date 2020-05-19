package com.example.pmq.service;

import com.example.pmq.domain.PmqParam;

import java.util.List;

public interface SystemParamService {

    void save(PmqParam record);

    List<PmqParam> params();
}
