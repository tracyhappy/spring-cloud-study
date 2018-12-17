package com.itmuch.cloud.study.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itmuch.cloud.study.user.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 *   这里代码里讲的是熔断。
 *   另外其实还有服务降级，前提是比如积分服务已经熔断,不能成功访问了。
 *   咱们就来个降级：每次调用积分服务，你就在数据库里记录一条消息，说给某某用户增加了多少积分，因为积分服务挂了，导致没增加成功！这样等积分服务恢复了，你可以根据这些记录手工加一下积分。这个过程，就是所谓的降级。
 *
 */
@Service
public class RibbonHystrixService {
  @Autowired
  private RestTemplate restTemplate;
  private static final Logger LOGGER = LoggerFactory.getLogger(RibbonHystrixService.class);

  /**
   * 使用@HystrixCommand注解指定当该方法发生异常时调用的方法
   * @param id id
   * @return 通过id查询到的用户
   */
  @HystrixCommand(fallbackMethod = "fallback")
  public User findById(Long id) {
    return this.restTemplate.getForObject("http://microservice-provider-user/" + id, User.class);
  }

  /**
   * hystrix fallback方法
   * @param id id
   * @return 默认的用户
   */
  public User fallback(Long id) {
	//log, throw Exception
    RibbonHystrixService.LOGGER.info("异常发生，进入fallback方法，接收的参数：id = {}", id);
    User user = new User();
    user.setId(-1L);
    user.setUsername("default username");
    user.setAge(0);
    return user;
  }
}
