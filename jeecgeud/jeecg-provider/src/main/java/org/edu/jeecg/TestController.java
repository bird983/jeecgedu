package org.edu.jeecg;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/one")
    public String one(){
        return "my port is:" + serverPort;
    }

    @RequestMapping("/showname/{name}")
    public String showname(@PathVariable("name") String name){
        System.out.println("进入方法：showname");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "your name is: "+ name;
    }

    @HystrixCommand(fallbackMethod = "hystrixDefault", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    @RequestMapping("hystrixtest/{name}")
    public String hystrixtest(@PathVariable("name") String name){
        System.out.println("进入方法showname");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "我是正常的返回:" + name;
    }

    private String hystrixDefault(String name){
        return "程序超时:" + name;
    }


    /**
     * 熔断
     *
     * circuitBreaker.enabled   是否开启断路器
     * circuitBreaker.requestVolumeThreshold         熔断触发的最小个数/10s,默认值：20
     * circuitBreaker.errorThresholdPercentage       失败率达到多少百分比后熔断，默认值：50
     * circuitBreaker.sleepWindowInMilliseconds      熔断多少毫秒后去尝试请求默认值：5000
     * @param age
     * @return
     */
    @HystrixCommand(fallbackMethod = "netFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    })
    @RequestMapping("/net/{age}")
    public String net(@PathVariable("age") int age){
        if (age < 18){
            throw new RuntimeException();
        }
        return "正在上网，年龄" + age;
    }
    public String netFallback(@PathVariable("age") int age){
        return "未成年人不允许进入网吧,年龄："+age;
    }


}
