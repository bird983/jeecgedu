package org.edu.jeecg.service;

import org.springframework.stereotype.Component;

@Component
public class FeignServiceFallback implements FeignService {
    @Override
    public String getNameString(String name) {
        return "我是降级方法" + name;
    }
}
