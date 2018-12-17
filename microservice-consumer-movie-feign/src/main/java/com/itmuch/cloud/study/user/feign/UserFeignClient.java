package com.itmuch.cloud.study.user.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itmuch.cloud.study.user.entity.User;


//人家Feign Client会在底层根据你的注解，跟你指定的服务建立连接、构造请求、发起靕求、获取响应、解析响应，等等。这一系列脏活累活，人家Feign全给你干了。
//那么问题来了，Feign是如何做到这么神奇的呢？很简单，Feign的一个关键机制就是使用了动态代理。


/**
 * 使用@FeignClient("microservice-provider-user")注解绑定microservice-provider-user服务，还可以使用url参数指定一个URL。
 * @author eacdy
 */
@FeignClient(name = "microservice-provider-user")
public interface UserFeignClient {
  @RequestMapping("/{id}")
  public User findByIdFeign(@RequestParam("id") Long id);
}
