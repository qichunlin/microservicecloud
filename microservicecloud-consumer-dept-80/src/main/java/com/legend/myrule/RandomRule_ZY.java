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