package dcrays.controller;

import java.util.List;

import cn.dcrays.entities.Dept;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import dcrays.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeptController {
	@Autowired
	private DeptService service;
	@Autowired
	private DiscoveryClient client;

	@RequestMapping(value = "/dept/add", method = RequestMethod.POST)
	public boolean add(@RequestBody Dept dept) {
		boolean result = service.add(dept);
		return result;
	}


	@RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "MyHystrixGet")//一旦调用服务方法抛出异常后就调用fallbackMethod指定方法
	public Dept get(@PathVariable("id") Long id) {
		Dept dept = service.get(id);
		if (dept==null){
			throw  new RuntimeException("没有这个id对应的信息");
		}
		return dept;
	}

	//断路器需要执行的方法
	public Dept MyHystrixGet(@PathVariable("id") Long id) {
		return new Dept().setDeptno(id).setDname("该id没有对应的信息=====Hystrix").setDb_source("lalalla");
	}

	@RequestMapping(value = "/dept/list", method = RequestMethod.GET)
	public List<Dept> list() {
		List<Dept> list = service.list();

		return list;
	}

}
//	@Autowired
//	private DiscoveryClient client;
/*	@RequestMapping(value = "/dept/discovery", method = RequestMethod.GET)
	public Object discovery()
	{
		List<String> list = client.getServices();
		System.out.println("**********" + list);

		List<ServiceInstance> srvList = client.getInstances("CLOUD-DEPT");
		for (ServiceInstance element : srvList) {
			System.out.println(element.getServiceId() + "\t" + element.getHost() + "\t" + element.getPort() + "\t"
					+ element.getUri());
		}
		return this.client;
	}
}*/
