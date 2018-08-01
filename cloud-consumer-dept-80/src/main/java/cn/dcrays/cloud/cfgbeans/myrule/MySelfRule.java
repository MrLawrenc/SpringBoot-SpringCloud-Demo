package cn.dcrays.cloud.cfgbeans.myrule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;


/**
 * @Description: 自定义的负载均衡算法
 * @Param:
 * @return:
 * @Author: Liu Ming
 */
@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule() {
        //这里也可以使用Ribbon内置的几种负载均衡算法
        //return new RoundRobinRule();// Ribbon默认是轮询，我自定义为随机

        return new RandomRule_LMY();// 我自定义为每台机器5次
    }
}
