package com.baidu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 2 * @ClassName RunSearchServerApplication
 * 3 * @Description: TODO
 * 4 * @Author jiahang
 * 5 * @Date 2021/3/4
 * 6 * @Version V1.0
 * 7
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients
public class RunSearchServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunSearchServerApplication.class);
    }
}
