# SpringCloud
## 常见面试题分析
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190724075551715-103431312.png)


## 微服务优缺点


## 微服务技术栈有哪些
- 服务开发
	- springboot、springboot、springmvc

- 服务配置与管理
	- Netflix公司的Archaius、阿里的Diamond等

- 服务注册与发现
	- Eureka、Consul、Zookeeper等

- 服务调用
	- Rest、RPC、gRPC

- 服务熔断器
	- Hystrix、Envoy等

- 负载均衡
	- Ribbon、Nginx等
- 服务接口调用（客户端调用服务的简化工具）
	- Feign等

- 消息队列
	- Kafka、RabbitMQ、ActiveMQ等

- 服务配置中心管理
	- SpringCloudConfig、Chef等

- 服务路由（API网关）
	- Zuul等

- 服务监控
	- Zabbix、Nagios、Metrics、Spectator等

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190724100330881-1421884120.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190724100553501-622441207.png)


## SpringCloud是什么
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190724102845513-1598599169.png)

SpringCloud=分布式微服务架构下的一站式解决方案，是各个微服务架构落地的集合体，俗称微服务i全家桶



### Spring cloud和spring boot是什么关系
boot关注的是微观，是一个一个的微服务
boot是一个一个具体的科室
boot可以单独使用不依赖cloud


cloud关注的是宏观，分布式微服务下一站式解决方案
cloud组成boot将各个组成成来
cloud依赖于boot


```
SpringBoot专注于快速方便的开发单个个体微服务

Spring cloud是关注全局的微服务协调整理框架,它将SpringBoot开发的一个个单体微服务整合并管理起来,为各个微服务之间提供,配置管理、服务发现、断路器、路由、微代理、事件总线、全局锁、决策竞选、分布式会话等等集成服务

SpringBoot可以离开SpringCloud独立使用开发项目,但是SpringCloud离不开SpringBoot,属于依赖关系

Springboot专注于快速、方便的开发单个微服务个体,SpringCloud关注全局的服务治理框架.
```


### Dubbo是怎么到SpringCloud的？哪些优缺点让你技术选型

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190724114634579-1253119549.png)

>最大区别：SpringCLoud抛弃了Dubbo的RPC通信，采用的是基于HTTP的REST方式
>


----

### Eureka
```
	Eureka是一个基于REST的服务，用于定位服务，以实现云端中间层服务发现和故障转移。服务注册与发现对于微服务架构来说是非常重要的，有了服务发现与注册，只需要使用服务的标识符，就可以访问到服务，而不需要修改服务调用的配置文件了。功能类似于dubbo的注册中心，比如Zookeeper。
```

而系统中的其他微服务，使用Eureka的客户端连接到Eureka Server并维持心跳连接。这样系统的维护人员就可以通过Eureka Server 来监控系统中各个微服务是否正常运行。SpringCloud的一些其他模块（比如Zuul）就可以通过Eureka Server 来发现系统中的其他微服务，并执行相关的逻辑。


#### Eureka包含两个组件
```
	Eureka Server和Eureka Client 
	Eureka Server提供服务注册服务,各个节点启动后,会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息,服务节点的信息可以在界面中直观的看到
```

#### Eureka 相关信息
```
	EurekaClient是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询（round-robin）负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳（默认周期为30秒）。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除（默认90秒）
```

#### 如何使用配置Eureka
```
microservicecloud-eureka-7001

eureka服务注册中心Module假如我需要引入cloud的一个新技术组件
	基本上有两步:
		1.1新增一个相关的maven坐标
eureka	
<！--eureka-server服务端-->
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId spring-cloud-starter-eureka-server</artifactId>
</dependency>

	1.2再主启动类上面，标注的启动该新组件技术的相关注解标签
		@EnableEurekaServel

1.3java业务逻辑编码
```


`测试是否成功`

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190726003616092-360827256.png)

#### 配置eureka服务端
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190726005144244-255476347.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190726005241137-44807561.png)

`application.yml`
```
server: 
  port: 7001
 
eureka: 
  instance:
    hostname: localhost #eureka服务端的实例名称
  client: 
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: 
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
```

`主配置类、引入依赖`
```
@EnableEurekaServer


<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>
```

#### 配置Eureka的客户端
`Pom文件`
```
<dependency>
            <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```



`主配置类`
```
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient //服务发现
```

#### info信息内容构建（左下角显示ip地址,点击服务名称显示具体内容）
`在父工程添加编译插件`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727112147658-1372967749.png)

`在微服务项目添加信息`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727112233750-349945087.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190726005726282-81740580.png)

#### Eureka自我保护机制
```
某时刻某一个微服务不可用,eureka不会立刻清理,依旧会对该微服务的信息进行保存
```

```
默认情况下，如果EurekaServer在一定时间内没有接收到某个微服务实例的心跳，EurekaServer将会注销该实例（默认90秒）。
但是当网络分区故障发生时，微服务与EurekaServer之间无法正常通信，以上行为可能变得非常危险了——因为微服务本身其实是健康的，此时本不应该注销这个微服务。Eureka通过“自我保护模式”来解决这个问题——当EurekaServer节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。一旦进入该模式，EurekaServer就会保护服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。当网络故障恢复后，该Eureka Server节点会自动退出自我保护模式。
```

```
在自我保护模式中，Eureka Server会保护服务注册表中的信息，不再注销任何服务实例。当它收到的心跳数重新恢复到闽值以上时，该Eureka Server节点就会自动退出自我保护模式。它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例。
```

`禁用自我保护模式`
```
可以使用eureka.server.enable-self-preservation=false
```

#### Eureka服务发现
对于注册进eureka里面的微服务,可以通过服务发现来获得该发现的服务信息

`在提供者中添加服务发现`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727115239572-698232162.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727115320805-1356534260.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727113704260-910808393.png)


#### Eureka集群配置
`创建3个eureka服务端`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727224741963-416952521.png)


`配置3个eureka的yml配置文件`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727224842096-344187103.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727224859029-705510953.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727224912040-722362474.png)

>注意：集群的是除了自己以外其他的eureka客户端的默认空间地址(暴露的地址)


`测试`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727225230383-2036862041.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190727230317203-1554046290.png)

`Eureka遵守AP,zookeeper遵守CP`

- 传统的ACID分别是什么?
	- A Atomicity 原子性
	- C Consistency 一致性
	- I Isolation 独立性
	-  D Druability  持久性

- CAP
	- C Consistency 强一致性
	- A Avaliability 可用性
	- P Partition tolerance 分区容错性

- CAP 三进二
```
CAP理论的核心是：一个分布式系统不可能同时很好的满足一致性，可用性和分区容错性这三个需求，因此，根据CAP原理将NoSQL数据库分成了满足CA原则、满足CP原则和满足AP原则三大类：
CA-单点集群，满足一致性，可用性的系统，通常在可扩展性上不太强大。
CP-满足一致性，分区容忍必的系统，通常性能不是特别高。
AP-满足可用性，分区容忍性的系统，通常可能对一致性要求低一些。
```

#### 作为服务注册中心,Eureka比Zookeeper好在哪里?
     Zookeeper保证CP当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的注册信息，但不能接受服务直接down掉不可用。也就是说，服务注册功能对可用性的要求要高于一致性。但是zk会出现这样一种情况，当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30~120s，且选举期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因网络问题使得zk集群失去master节点是较大概率会发生的事，虽然服务能够最终恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的。

 Eureka保证AP Eureka看明白了这一点，因此在设计时就优先保证可用性。Eureka各个节点都是平等的，几个节点挂掉不会影响正常节点的工作，剩余的节点依然可以提供注册和查询服务。而Eureka的客户端在向某个Eureka注册或时如果发现连接失败，则会自动切换至其它节点，只要有一台Eureka还在，就能保证注册服务可用（保证可用性），只不过查到的信息可能不是最新的（不保证强一致性）。除此之外，Eureka还有一种自我保护机制，如果在15分钟内超过85%的节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，此时会出现以下几种情况：
1.Eureka不再从注册列表中移除因为长时间没收到心跳而应该过期的服务2.Eureka仍然能够接受新服务的注册和查询请求，但是不会被同步到其它节点上（即保证当前节点依然可用）
3.当网络稳定时，当前实例新的注册信息会被同步到其它节点中
因此，Eureka可以很好的应对因网络教障导致部分节点失去联系的情况，而不会像zookeeper那样使整个注册服务瘫痪。


### Ribbon
#### Nginx、Ribbon、Feign三个负载均衡的区别


#### Ribbon是什么？
Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法，将Netflix的中间层服务连接在一起。
Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer（简称LB）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器。我们也很容易使用Ribbon实现自定义的负载均衡算法。

简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法，将Netflix的中间层服务连接在一起。
Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer（简称LB）尼面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器。我们也很容易使用Ribbon实现自定义的负载均衡算法。

#### LB(负载均衡)
LB,即负载均衡（Load Balance）,在微服务或分布式集群中经常用的一种应用.
负载均衡简单的说就是将用户的请求平摊的分配到多个服务上,从而达到系统的HA.
常见的负载均衡有软件Nginx,LVS,硬件F5等.
相应的在中间件,例如:dubbo和SpringCloud中均给我们提供了负载均衡,SpringCloud的负载均衡算法可以自定义.

- 集中式LB  (偏硬件)
```
即在服务的消费方和提供方之间使用独立的LB设施（可以是硬件，如F5，也可以是软件，如nginx），由该设施负责把访问请求通过某种策略转发至服务的提供方；
```

- 进程内LB  (偏软件)
```
将LB逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。
Ribbon就属于进程内LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址。
```

----
可以读一下 [Ribbon官网资料](https://github.com/Netflix/ribbon/wiki) 
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728101503452-739481733.png)


#### Ribbon配置
- 添加GAV坐标(Maven)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728102020226-1425502012.png)
>因为Ribbon需要Eurka

- 修改 microservicecloud-consumer-dept-80 工程
	- yml
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728102801973-192738434.png)

- 
	- ConfigBean
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728102906744-1475924141.png)

- 
	- 主启动类添加Eureka客户端
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728103350730-1303447561.png)

- 
	- Controller 修改访问路径修改成微服务的名字
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728103409424-898730741.png)

- 测试
	- 先启动3个Eureka进群服务端
	- 再启动 microservicecloud-provider-dept-8001 注册进去eureka 
	- 启动microservicecloud-consumer-dept-80


`http://localhost/consumer/dept/get/1`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728104120073-209549257.png)


`http://localhost/consumer/dept/list`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728104320183-861914866.png)

`http://localhost/consumer/dept/add?dname=大数据`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728104752388-2039719454.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728104828805-1547826015.png)

>Ribbon和Eureka整合后Consumer可以直接调用服务而不用再关心地址和端口号



#### Ribbon负载均衡
`Ribbon在工作时分成两步`
```
第一步先选择EurekaServer，它优先选择在同一个区域内负载较少的server.
第二步再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址。
```


- 创建另外两个提供者工程
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728111332847-279014659.png)

- 添加两个工程和8001的一样配置
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728111507973-185794220.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728111555888-184054065.png)

- 测试
	- 启动3个eureka集群配置
	- 启动3个Dept微服务并各自测试通过
	- 启动 microservicecloud-consumer-dept-80
	- 客户端通过Ribbon完成负载均衡并访问上一步的Dept微服务

`http://localhost:8003/dept/list`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728112044175-1339923434.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728112055759-1842038871.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728112110301-617806506.png)


![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728112335282-424551850.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728112341984-1090585601.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728112349178-1355309943.png)

`架构说明`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728112453067-1046530085.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728112619702-1144742658.png)

>总结：Ribbon其实就是一个软负载均衡的客户端组件，他可以和其他所需请求的客户端结合使用，和eureka结合只是其中的一个实例。


#### Ribbon核心组件IRule
```
根据特定算法中从服务列表中选取一个要访问的服务
```
- RoundRobinRule轮询
- RandomRule随机
- AvailabilityFilteringRule
	- 会先过滤掉由于多次访问故障而处于断路器跳还有并发的连接数量超过阔值的服务，然后对剩余的服务列表按照轮询政策进行访问
- WeightedResponseTimeRule
	- 根据平均响应时间计算所有服务的权重，响应时间越快服务权重越大被选中的概率越高。刚启动时如果统计信息不足，则使用RoundRobinRule策略等信息统计够，会切换到WeightedResponse TimeRule 
- RetryRule
	- 先按照RoundRobinRule的策略获取服务，如果获取服务则在指定时间内进行重试,获取可用服务
- BestAvailableRule
	- 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务然后选择一个并发量最小的服务
- ZoneAvoidanceRule默认规则，复合判断server所在区域的性能和serve的可用性选择服务器

`在消费者的配置类中添加`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728212712556-1902521571.png)

#### Ribbon自定义负载均衡策略
`@RibbonClient(name = "MICROSERVICECLOUD-DEPT",configuration = MySeleRule.class )`
在启动该微服务的时候就能去加载我们的自定义Ribbon配置类，从而使配置生效，


`官方文档明确给出了警告`
这个自定义配置类不能放在@ComponentScan所扫描的当前包下以及子包下，否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，也就是说我们达不到特殊化定制的目的了。


![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728215150261-1214882692.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190728215208922-1273422977.png)

#### 自定义规则深度解析
```
package com.legend.myrule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 自定义负载均衡策略
 */
public class RandomRule_ZY extends AbstractLoadBalancerRule {

    //total=0   调用次数记录,当total==5 以后,我们的指针才往下走
    //index=0   当前对外提供服务的服务器地址
    //total需要重新置为0,但是已经达到过一个5次,我们的index=1
    private int total = 0;//总共被调用的次数,目前要求每条被调用5次
    private int currentIndex = 0;//当前提供服务的机器号



    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                /*
                 * No servers. End regardless of pass, because subsequent passes
                 * only get more restrictive.
                 */
                return null;
            }

            //随机上面的源代码
            //int index = chooseRandomInt(serverCount);
            //server = upList.get(index);

            //自定义策略方式
            if (total < 5){
                server = upList.get(currentIndex);
                total++;
            } else {
                total = 0;//
                currentIndex++;//第一台机器结束
                //防止超出我们的服务机器,需要重新重0开始
                if (currentIndex >= upList.size()){
                    currentIndex = 0;
                }
            }


            if (server == null) {
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }

    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}
```

- 修改配置类 
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190729085206623-11861087.png)

### Feign
#### Feign定义
```
	Feign是一个声明式的Web服务客户端，使得编写Web服务客户端变得非常容易，只需要创建一个接口，然后在上面添加注解即可。
```


#### Feign能干什么
Feign旨在使编写Java Http客户端变得更容易。
前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注解的方式来配置它（以前是Dao接口上面标注Mapper注解，现在是一个微服务接口上面标注一个Feign注解即可），即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。

#### Feign集成了Ribbon
利用Ribbon维护了MicroServiceCloud-Dept的服务列表信息，并且通过轮询实现了客户端的负载均衡。而与Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用

#### Feign配置
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190729220828821-704142279.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190729220940417-168331661.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190729221255789-1000714821.png)
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190729221331562-1499297179.png)

- 测试
	- 启动3个eureka集群
	- 启动3个部门微服务8001/8002/8003
	- 启动Feign（Feign自带负载均衡配置项）
	- 访问feign工程的地址


Feign通过接口的方法调用Rest服务（之前是Ribbon+RestTemplate），该请求发送给Eureka服务器（http://MICROSERVICECLOUD-DEPT/dept/list），通过Feign直接找到服务接口，由于在进行服务调用的时候融合了Ribbon技术，所以也支持负载均衡作用。

---

### Hystrix
`熔断器、断路器(也叫我们生活中的保险丝)`

#### 分布式系统面临的问题
`服务雪崩`
`1.`
```
	多个微服务之间调用的时候，假设徽服务A调用微服务B和微服务C，微服务B和微服务C又调用其它的微服务，这就是所谓的“扇出”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的“雪崩效应".
```

`2.`
```
对于高流量的应用来说，单一的后端依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和。比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统。
```

#### Hystrix是什么
```
Hystrix是一个用于处理分布式系统的延迟和容错的开源库,在分布式系统里,许多依赖不可避免的调用失败,比如超时、异常等,Hystrix能够保证在一个依赖出问题的情况下,不会导致整个服务	失败,避免级联故障	,以提高分布式系统弹性.
	“断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝），向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。
```

#### Hystrix能干什么
- 服务降级
- 服务熔断
- 服务限流
- 接近实时的监控

[Hystrix官网资料](https://github.com/Netflix/Hystrix/wikiHystrix)


#### 服务熔断分析
`定义`
```
	熔断机制是应对雪崩效应的一种微服务链路保护机制。
	当扇出链路的某个微服务不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回”错误”的响应信息。当检测到该节点微服务调用响应正常后恢复调用链路。在SpringCloud框架里熔断机制通过Hystrix实现。Hystrix会监控微服务间调用的状况，当失败的调用到一定闽值，缺省是5秒内20次调用失败就会启动熔断机制。
	熔断机制的注解是 @HystrixCommand
```

`使用的注意和重点`
```
	一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法
```


#### 如何配置服务熔断
- 创建一个hystrix工程跟dept服务提供者一样
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731225628715-1253519930.png)

- 在工程中引入hystrix依赖
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731225812241-762686081.png)

- 修改Controller的请求
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731230034448-181695325.png)

- 在主启动类上添加注解
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731230127443-282067835.png)


- 测试
	- 启动3个Eureka
	- 主启动类DeptProvider8001_Hystrix_App
	- Consumer启动 microservicecloud-consumer-dept-80

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731225227863-1559091717.png)

`查看一个存在的记录请求信息`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731225203920-171350642.png)

`查看一个数据库信息不存在的信息`
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731225348445-428330442.png)


---

#### 服务降级
```
	整体资源快不够了，忍痛将某些服务先关掉，待渡过难关，再开启回来。
```
```
解耦和分离,AOP思想,不需要在每一个方法上面添加@HystrixCommand注解
避免代码膨胀
```

#### 服务降级使用
- 修改 microservicecloud-api 工程 service 接口
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731233122472-1317590292.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731233115470-54512027.png)

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731233255185-946304588.png)

>记得每次修改完 microservicecloud-api 工程之后都要mvn clean install
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731233411162-238241068.png)


- 修改 microservicecloud-consumer-dept-feign工程配置文件
![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731233741382-109257242.png)

- 测试
	- 启动3个Eureka
	- 微服务 microservicecloud-provider-dept-8001  启动
	- microservicecloud-consumer-dept-feign 微服务启动
	- 正常访问测试
	- 故意关闭微服务 microservicecloud-provider-dept-8001 
	- 客户端自己调用提示

![](https://img2018.cnblogs.com/blog/1231979/201907/1231979-20190731234430430-965647359.png)

>此时服务端provider已经down了,但是我们做了服务降级处理，让客户端在服务端不可用时也会获得提示信息而不会挂起耗死服务器