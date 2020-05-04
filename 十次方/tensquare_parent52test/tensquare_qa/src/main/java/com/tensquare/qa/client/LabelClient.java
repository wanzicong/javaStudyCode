package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.LabelClientImpl;
import entity.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="tensquare‐base",fallback = LabelClientImpl.class)
@Component
public interface LabelClient {
    /**
     * 方法名和资源路径和label一样
     */
    @GetMapping("/label/{id}")
    ResultDTO listById(@PathVariable("id") String id);
}
