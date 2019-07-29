package legend.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * boot -->spring   applicationContext.xml --- @Configuration配置   ConfigBean = applicationContext.xml
 */
@Configuration
public class ConfigBean{

	/**
	 * Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端 负载均衡的工具。 开启注解 @LoadBalanced
	 * 获取的REST时注入Ribbon的配置
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}


	/**
	 * 使用Ribbon实现随机算法(默认是轮询算法)
	 * @return
	 */
	@Bean
	public IRule myRule() {
		//只需要new 写好的Rule就好
		return new RoundRobinRule();
		//return new RandomRule();//达到的目的，用我们重新选择的随机算法替代默认的轮询。
		//return new RetryRule();
	}
}

//@Bean
//public UserServcie getUserServcie()
//{
//	return new UserServcieImpl();
//}

// applicationContext.xml == ConfigBean(@Configuration)
//<bean id="userServcie" class="com.atguigu.tmall.UserServiceImpl">