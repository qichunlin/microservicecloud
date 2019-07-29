package com.legend.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 自定义Ribbon负载均衡算法
 * 注意:这个类不能放在 @ComponentScan 包扫描及其子包下
 */
@Configuration
public class MySeleRule {

    @Bean
    public IRule myRule(){
        //return new RandomRule();//Ribbon默认是轮询,自定义为随机
        return new RandomRule_ZY();//使用自定义负载均衡策略
    }
}
