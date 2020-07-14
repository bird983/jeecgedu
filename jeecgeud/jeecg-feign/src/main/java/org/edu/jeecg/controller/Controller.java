package org.edu.jeecg.controller;

import org.edu.jeecg.service.FeignService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("feign")
@RestController
public class Controller {

    @Resource
   private FeignService feignService;

    @GetMapping("/show/{name}")
    public String showname(@PathVariable("name") String name){
        return feignService.getNameString(name);
    }
}
