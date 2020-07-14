package org.edu.jeecg.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "jeecg-provider", path = "/test", fallback = FeignServiceFallback.class)
public interface FeignService {

    @GetMapping("/showname/{name}")
    public String getNameString(@PathVariable("name") String name);

}
