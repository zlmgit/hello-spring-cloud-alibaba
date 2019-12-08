package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.demo;








import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureMethod {
    /**
     * 方法名	                       描述
     * runAsync(Runnable runnable)	使用ForkJoinPool.commonPool()作为它的线程池执行异步代码。
     * runAsync(Runnable runnable, Executor executor)	使用指定的thread pool执行异步代码。
     * supplyAsync(Supplier<U> supplier)	使用ForkJoinPool.commonPool()作为它的线程池执行异步代码，异步操作有返回值
     * supplyAsync(Supplier<U> supplier, Executor executor)	使用指定的thread pool执行异步代码，异步操作有返回值
     */
    //runAsync 和 supplyAsync 方法的区别是runAsync返回的CompletableFuture是没有返回值的。
    @Test
    public void testRunAsync(){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Hello");
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("CompletableFuture");
    }
}
