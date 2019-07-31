package com.legend.springcloud.service;

import com.legend.springcloud.entity.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 修改microservicecloud-api工程，根据已经有的DeptClientService接口
 * 新建
 *      一个实现了FallbackFactory接口的类DeptclientServiceFallbackFactory
 *
 *   备选响应
 *
 *   每一个熔断机制统一放在 DeptClientServiceFallbackFactory类里面处理
 */
@Component //不要忘记添加
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public Dept get(long id) {
                return new Dept().setDeptno(id)
                        .setDname("该ID："+ id + "没有对应的信息,Consumer客户端提供的降级信息，此刻服务Provider已经关闭”）")
                        .setDb_source("no this record in MYSQL");
            }

            @Override
            public List<Dept> list() {
                return null;
            }

            @Override
            public boolean add(Dept dept) {
                return false;
            }
        };
    }
}
