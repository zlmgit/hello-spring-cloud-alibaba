package com.zlm.hello.spring.cloud.alibaba.nacos.provider;






import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.ThreadPoolUtil;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

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

            System.out.println("isDaemon："+Thread.currentThread().isDaemon());
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("CompletableFuture");
    }

    /**
     * 而supplyAsync返回的CompletableFuture是由返回值的，下面的代码打印了future的返回值。
     */
    @Test
    public void testSupplyAsync(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("CompletableFuture");
    }
    /**
     * 进行变换
     * public <U> CompletionStage<U> thenApply(Function<? super T,? extends U> fn);
     * public <U> CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn);
     * public <U> CompletionStage<U> thenApplyAsync(Function<? super T,? extends U> fn,Executor executor);
     */
    @Test
    public void thenApply() {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + " world").join();
        System.out.println(result);
    }
    /**
     * 进行消耗
     * public CompletionStage<Void> thenAccept(Consumer<? super T> action);
     * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
     * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action,Executor executor);
     */
    @Test
    public void thenAccept(){
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> System.out.println(s + " world"));
    }
    /**
     * 对上一步的计算结果不关心，执行下一个操作。
     * public CompletionStage<Void> thenRun(Runnable action);
     * public CompletionStage<Void> thenRunAsync(Runnable action);
     * public CompletionStage<Void> thenRunAsync(Runnable action,Executor executor);
     */
    @Test
    public void thenRun(){
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello world"));
        future.join();
    }
    /**
     * 结合两个CompletionStage的结果，进行转化后返回
     * public <U,V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
     * public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
     * public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn,Executor executor);
     */
    @Test
    public void thenCombine() {
        long start = System.currentTimeMillis();
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        long end = System.currentTimeMillis();
        System.out.println(result);
        System.out.println("耗时："+(end-start));
    }
    /**
     * 结合两个CompletionStage的结果，进行消耗
     * public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
     * public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action,     Executor executor);
     */
    @Test
    public void thenAcceptBoth() {
        long start = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2));
        future.join();
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
    }
    /**
     * 在两个CompletionStage都运行完执行。
     * public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,Runnable action);
     * public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action);
     * public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action,Executor executor);
     */
    @Test
    public void runAfterBoth(){
        long start = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        future.join();
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
    }
    /**
     * .两个CompletionStage，谁计算的快，我就用那个CompletionStage的结果进行下一步的转化操作。
     * public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other,Function<? super T, U> fn);
     * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn);
     * public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn,Executor executor);
     */
    @Test
    public void applyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), s -> s).join();
        System.out.println(result);
    }

    /**
     * 两个CompletionStage，谁计算的快，我就用那个CompletionStage的结果进行下一步的消耗操作。
     * public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other,Consumer<? super T> action);
     * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action);
     * public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action,Executor executor);
     */
    @Test
    public void acceptEither() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), System.out::println);
        future.join();
    }
    /**
     * 两个CompletionStage，任何一个完成了都会执行下一步的操作（Runnable）。
     * public CompletionStage<Void> runAfterEither(CompletionStage<?> other,Runnable action);
     * public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action);
     * public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action,Executor executor);
     */
    @Test
    public void runAfterEither() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("s1");
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("s2");
            return "s2";
        }), () -> System.out.println("hello world"));
        future.join();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 当运行时出现了异常，可以通过exceptionally进行补偿。
     * public CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn);
     */
    @Test
    public void exceptionally() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }
    /**
     * 当运行完成时，对结果的记录。这里的完成时有两种情况，一种是正常执行，返回值。
     * 另外一种是遇到异常抛出造成程序的中断。这里为什么要说成记录，因为这几个方法都会返回CompletableFuture，
     * 当Action执行完毕后它的结果返回原始的CompletableFuture的计算结果或者返回异常。所以不会对结果产生任何的作用。
     * public CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action);
     * public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action);
     * public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action,Executor executor);
     */
    @Test
    public void whenComplete() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).whenComplete((s, t) -> {
            System.out.println(s);
            System.out.println(t.getMessage());
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }
    //未出现异常时
    @Test
    public void handle() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).handle((s, t) -> {
            if (t != null) {
                return "hello world";
            }
            return s;
        }).join();
        System.out.println(result);
    }

    @Test
    public void testDaemon() {
        ExecutorService exs = ThreadPoolUtil.createBoundLessFixedThreadPool();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":hello执行");
            return "hello";
        },exs);
        try {
            String result = future.get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
