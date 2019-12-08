package com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class ThreadPoolUtil {

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ZLM" + "-%d").setDaemon(true).build();

    /**
     * 创建一个 8 个线程的固定数量线程池，
     * 因为这里核心线程数等于最大线程数（核心加临时）所以线程存活时间没有作用，所以设置为 0，
     * 使用了 ArrayBlockingQueue 作为等待队列，设置长度为 10 ，最多允许10个等待任务，
     * 超过的任务会执行默认的 AbortPolicy 策略，也就是直接抛异常 ,
     * 容易抛异常程序中断
     * @return ExecutorService
     */

    public static ExecutorService createBoundedFixedThreadPool() {
        int poolSize = 8;
        int queueSize = 10;
        ExecutorService executorService = new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        return executorService;
    }

    /**
     * 创建一个 8 个线程的固定数量线程池，
     * 使用了 LinkedBlockingDeque 作为无界队列
     * 该队列无需指定超出任务执行策略
     * 除非任务不会无限制增加，无界队列容易造成OOM
     * @return
     */
    public static ExecutorService createBoundLessFixedThreadPool() {
        int poolSize = 10;
        ExecutorService executorService = new ThreadPoolExecutor(poolSize, poolSize, 0L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(), threadFactory);
        return executorService;
    }

    /**
     * 缓存型线程池，在核心线程达到最大值之前，有任务进来就会创建新的核心线程，并加入核心线程池，
     * 即时有空闲的线程，也不会复用。达到最大核心线程数后，新任务进来，如果有空闲线程，则直接拿来使用，
     * 如果没有空闲线程，则新建临时线程。并且线程的允许空闲时间都很短，如果超过空闲时间没有活动，则销毁临时线程。
     * 关键点就在于它使用 SynchronousQueue 作为等待队列，它不会保留任务，新任务进来后，直接创建临时线程处理，
     * 这样一来，也就容易造成无限制的创建线程，造成 OOM。
     * @return
     */
    public static ExecutorService createCacheThreadPool(){
        int coreSize = 10;
        int maxSize = 20;
        return new ThreadPoolExecutor(coreSize, maxSize, 10L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    //正确的创建计划型线 线程池的做法是
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2, threadFactory);
        executorService.scheduleAtFixedRate(task,0L,5L, TimeUnit.SECONDS);
        latch.await();
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "executing");
        }
    }
}
