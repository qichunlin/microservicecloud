package com.legend.springcloud.service;

import com.legend.springcloud.entity.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 修改microservicecloud-api工程，根据已经有的DeptClientService接口
 * 一个接口+一个注解
 * 新建
 * 一个实现了FallbackFactory接口的类DeptClientServiceFallbackFactory
 * @author legend
 */
@FeignClient(value = "MICROSERVICECLOUD-DEPT")
public interface DeptClientService {

    @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
    public Dept get(@PathVariable("id") long id);

    @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
    public List<Dept> list();

    @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
    public boolean add(Dept dept);
}




