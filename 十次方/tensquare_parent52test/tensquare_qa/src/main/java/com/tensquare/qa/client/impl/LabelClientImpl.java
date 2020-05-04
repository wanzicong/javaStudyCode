package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import constants.StatusCode;
import entity.ResultDTO;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public ResultDTO listById(String id) {
        return new ResultDTO(false, StatusCode.ERROR, "熔断器启动了");
    }
}
