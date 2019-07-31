package com.legend.springcloud.controller;

import java.util.List;

import com.legend.springcloud.entity.Dept;
import com.legend.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 一旦调用服务方法失败并抛出了错误信息后
 * 会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法
 *
 * @author legend
 */
@RestController
public class DeptController {

	@Autowired
	private DeptService service;

	//服务发现类
	@Autowired
	private DiscoveryClient client;

	@RequestMapping(value = "/dept/add", method = RequestMethod.POST)
	public boolean add(@RequestBody Dept dept) {
		return service.add(dept);
	}

	@RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "processHystrix_Get") //当程序出现异常的时候调用这个方法  processHystrix_Get返回给调用者信息
	public Dept get(@PathVariable("id") Long id) {
		Dept dept = this.service.get(id);
		if (dept == null){
			throw new RuntimeException("当前ID"+id+"不存在记录,没有对应信息");
		}
		return dept;
	}

	//处理微服务调用异常的处理方法
	public Dept processHystrix_Get(@PathVariable("id") Long id){
		return new Dept().setDeptno(id)
				.setDname("该ID"+id+"没有对应的信息,null----@HystrixCommond")
				.setDb_source("no this record in MYSQL");
	}



	@RequestMapping(value = "/dept/list", method = RequestMethod.GET)
	public List<Dept> list() {
		return service.list();
	}

	
//	@Autowired
//	private DiscoveryClient client;
	//服务发现
	@RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
	public Object discovery() {
		//拥有的微服务列表
		List<String> list = client.getServices();
		System.out.println("**********" + list);

		//或者MICROSERVICECLOUD-DEPT 微服务的获取
		List<ServiceInstance> srvList = client.getInstances("MICROSERVICECLOUD-DEPT");
		for (ServiceInstance element : srvList) {
			System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
					+ element.getUri());
		}
		return this.client;
	}

}
