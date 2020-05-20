package com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.impl;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao.UserMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Order;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.OrderService;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.UserService;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RedisUtil redisUtil;

    private static final String HIGH_CURRENT_QUEUE ="HIGH:CURRENT:QUEUE";

    private static final LinkedBlockingQueue<User> queue = new LinkedBlockingQueue();

    @Override
    public User selectUserOne(Integer id) {
        //并发安全的阻塞队列，积攒请求。（每隔N毫秒批量处理一次）
        CompletableFuture<User> future = new CompletableFuture<>();
        User user = new User();
        user.setId(id);
        user.setFuture(future);
        queue.add(user);
        return future.join();
    }
    @PostConstruct
    public void init() {
        // 定时任务线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            int size = queue.size();
            if (size == 0) {
                return;
            }
            List<User> users = IntStream.range(0, size).boxed().map(i -> {
                return queue.poll();
            }).collect(Collectors.toList());
            users.forEach(item -> {
                User user = userMapper.selectUserOne(item.getId());
                item.getFuture().complete(user);
            });
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public int updateUserById(User user) {
        int i = userMapper.updateUserById(user);
        return i;
    }

    @Override
    @Transactional
    public int insertUser(User user) {

        return userMapper.insertUser(user);
    }

    @Override
    public void testTransactional(User user) {
        int result = (int) transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                int i = insertUser(user);
                if (i == 1) {
                    int i1 = updateUserById(user);
                }
                int z = 1 / 0;
                return i;
            }
        });
    }

    @Override
    public void testTransactional2(User user) {
        updateUserById(user);
        insertUser(user);
        int i = 1 / 0;
    }

    @Override
    public int insertForeach() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String string = UUID.randomUUID().toString();
            User user = new User();
            user.setName(string.substring(0, 5) + i);
            user.setPassword(string);
            users.add(user);
        }
        int i = userMapper.insertForeach(users);
        users.forEach(System.err::println);
        return i;
    }

    @Override
    @Transactional
    public void testTransactional() {
        User user = new User();
        user.setName("Transactional");
        user.setPassword(UUID.randomUUID().toString());
        insertUser(user);
        try {
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setName("Transactional");
            orderService.insertOrder(order);
        }catch (Exception e){
            log.error("orderService :",e);
        }
    }
}
