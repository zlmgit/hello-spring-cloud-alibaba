package com.zlm.hello.spring.cloud.alibaba.nacos.provider.thread;


import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Component
public class FutureTest {

    public void check(List<User> list){
        ExecutorService exs = Executors.newFixedThreadPool(10);
        try {
            List<CompletableFuture> futures = new ArrayList<>();
            for (int i =0 ;i<list.size() ;i++){
                User user = list.get(i);
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    doSomething(user);
                }, exs);
                futures.add(future);
            }
            CompletableFuture[] objects = futures.toArray(new CompletableFuture[]{});
            CompletableFuture.allOf(objects).join();
            System.out.println("执行结束:"+list);
        }finally {
            exs.shutdown();
        }
    }


    private void doSomething(User user){
        Random rand = new Random();
        int i = rand.nextInt(30);
        user.setName(user.getName()+":"+i);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
