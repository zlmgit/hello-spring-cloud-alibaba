package com.zlm.hello.spring.cloud.alibaba.nacos.provider.config;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@EnableAsync
@Configuration
public class AsyncConfig implements ApplicationContextAware {

  @Value("${async.core_pool_size}")
  private int corePoolSize;

  @Value("${async.max_pool_size}")
  private int maxPoolSize;

  @Value("${async.queue_capacity}")
  private int queueCapacity;

  @Value("${async.keep_alive_seconds}")
  private int keepAliveSeconds;

  @Value("${async.await_termination_seconds}")
  private int awaitTerminationSeconds;

  private static ApplicationContext applicationContext;

  private static final String THREAD_NAME_PREFIX = "AsyncExecutorThread-";

  private static final String WORK_FOR_SEND_MAIL = "send_mail";


  @Bean(name = "asyncExecutor")
  public Executor asyncExecutor() {

    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setKeepAliveSeconds(keepAliveSeconds);
    executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
    //executor.initialize();
    return executor;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (AsyncConfig.applicationContext == null) {
      AsyncConfig.applicationContext = applicationContext;
      setBean();
    }
  }

  private void setBean() {
    DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext
      .getAutowireCapableBeanFactory();
    List<String> list = Lists.newArrayList();
    list.add(AsyncConfig.WORK_FOR_SEND_MAIL);

    list.forEach(beanName -> {
      beanFactory.registerSingleton(beanName, asyncExecutorForAutoReviewTask(beanName));
    });
  }

  private Executor asyncExecutorForAutoReviewTask(String threadName) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadFactory(new ThreadFactory() {
      private final AtomicInteger threadNumber = new AtomicInteger(1);

      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, StringUtils.join(threadName, "-",
          threadNumber.incrementAndGet()));
      }
    });
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setKeepAliveSeconds(keepAliveSeconds);
    executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
    executor.initialize();
    return executor;
  }
}
