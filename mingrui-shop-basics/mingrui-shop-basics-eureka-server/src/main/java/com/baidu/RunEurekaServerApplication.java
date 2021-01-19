package com.baidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 2 * @ClassName RunEurekaServerApplication
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/1/19
 * 6 * @Version V1.0
 * 7
 **/
@SpringBootApplication
@EnableEurekaServer
public class RunEurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunEurekaServerApplication.class);
    }
}
