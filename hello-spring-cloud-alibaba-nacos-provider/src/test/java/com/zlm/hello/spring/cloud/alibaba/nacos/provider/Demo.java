package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import cn.hutool.core.bean.BeanUtil;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Order;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Arrays;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class Demo {
    public static void main(String[] args) {
        String test = "abcc";
        char[] chars = test.toCharArray();
        StringBuffer str = new StringBuffer();
        Arrays.asList(chars).stream().collect(Collectors.groupingBy(a -> a)).values().forEach(item -> {
            if (item.size() == 1) {
                str.append(item.get(0));
            } else {
                str.append(item.get(0)).append((item.size() - 1));
            }
        });
        System.out.println(str);
    }

    @Test
    public void testLambda() {
        List<User> list = new ArrayList<>();
        list.add(new User(1, "Zlm", null));
        list.add(new User(2, "Zlm", "456"));
        list.add(new User(3, "Zlm", "789"));
        list.add(new User(4, "Zlm", "321"));
        list.add(new User(1, "zs", "987"));
        Map<Integer, List<User>> collect = list.parallelStream().collect(Collectors.groupingBy(User::getId));
        //System.out.println(collect);
        list.sort(Comparator.comparing(u -> Optional.ofNullable(u.getPassword()).orElse("")));
        System.out.println(list);
    }

    @Test
    public void test() {
        List<Integer> list1 = new ArrayList<>();
        IntStream.range(0, 10000).forEach(list1::add);
        long start = System.currentTimeMillis();
        List collect = IntStream.range(0, 5).boxed()
                .parallel()
                .map(item -> {
                    if (item == 1) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (item == 2) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (item == 3) {
                        try {
                            Thread.sleep(900);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return "rw" + item;
                })
                //.sorted()
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + collect);


        int i = 0;
        for (int j = 0; j < 5; j++) {
            i++;
            System.out.println(i - 1);
        }
    }

    @Test
    public void testDecimal() {
        BigDecimal decimal = new BigDecimal("2sdd");
       /* BigDecimal decimal1 = new BigDecimal("2.00");
        BigDecimal decimal2 = new BigDecimal("0.018");
        BigDecimal setScale = decimal.multiply(decimal1).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.err.println(decimal.compareTo(decimal1)!=0);*/
        ReentrantLock lock = new ReentrantLock();
    }

    //任务之间的顺序执行
    @Test
    public void test1() {
        CompletableFuture.runAsync(() -> {
        }).thenRun(() -> {
        });
        CompletableFuture.runAsync(() -> {
        }).thenAccept(resultA -> {
        });
        CompletableFuture.runAsync(() -> {
        }).thenApply(resultA -> "resultB");

        CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {
        });
        CompletableFuture.supplyAsync(() -> "resultA").thenAccept(resultA -> {
        });
        CompletableFuture.supplyAsync(() -> "resultA").thenApply(resultA -> resultA + " resultB");
        //前面 3 行代码演示的是，任务 A 无返回值，所以对应的，第 2 行和第 3 行代码中，resultA 其实是 null。
        //第 4 行用的是 thenRun(Runnable runnable)，任务 A 执行完执行 B，并且 B 不需要 A 的结果。
        //第 5 行用的是 thenAccept(Consumer action)，任务 A 执行完执行 B，B 需要 A 的结果，但是任务 B 不返回值。
        //第 6 行用的是 thenApply(Function fn)，任务 A 执行完执行 B，B 需要 A 的结果，同时任务 B 有返回值。
        //这一小节说完了，如果任务 B 后面还有任务 C，往下继续调用 .thenXxx() 即可。
    }

    //任务之间的顺序执行的异常处理1
    @Test
    public void test2() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException();
        })
                .exceptionally(ex -> "errorResultA")
                .thenApply(resultA -> resultA + " resultB")
                .thenApply(resultB -> resultB + " resultC")
                .thenApply(resultC -> resultC + " resultD");

        System.out.println(future.join());
    }

    //任务之间的顺序执行的异常处理1
    @Test
    public void test3() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(resultA -> resultA + " resultB")
                // 任务 C 抛出异常
                .thenApply(resultB -> {
                    throw new RuntimeException();
                })
                // 处理任务 C 的返回值或异常
                .handle((re, e) -> {
                    if (e != null) {
                        return "errorResultC";
                    }
                    return re;
                })
                .thenApply(resultC -> resultC + " resultD");

        System.out.println(future.join());
    }

    //取两个任务的结果
    @Test
    public void test4() {
        CompletableFuture<String> cfA = CompletableFuture.supplyAsync(() -> "resultA");
        CompletableFuture<String> cfB = CompletableFuture.supplyAsync(() -> "resultB");

        cfA.thenAcceptBoth(cfB, (resultA, resultB) -> {
        });
        cfA.thenCombine(cfB, (resultA, resultB) -> "result A + B");
        cfA.runAfterBoth(cfB, () -> {
        });
    }

    @Test
    public void test5() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<String> a1 = CompletableFuture.supplyAsync(() -> {
            log.info("线程1{}", "开始");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程1{}", "结束");
            return "1";
        }, executorService);
        CompletableFuture<String> a2 = CompletableFuture.supplyAsync(() -> {
            log.info("线程2{}", "开始");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("线程2{}", "结束");
            return "2";
        }, executorService);
        CompletableFuture<String> a = a1.thenCombineAsync(a2, (s1, s2) -> {
            log.info("组合线程s1:{},s2:{}",s1, s2);
            return s1 + s2;
        },executorService);

        System.out.println(a.join());

    }

    @Test
    public void test6(){
        NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用
        NumberFormat percent = NumberFormat.getPercentInstance();  //建立百分比格式化引用
        percent.setMaximumFractionDigits(4); //百分比小数点最多3位

        BigDecimal loanAmount = new BigDecimal("15000.48"); //贷款金额
        BigDecimal interestRate = new BigDecimal("0.008"); //利率
        BigDecimal interest = loanAmount.multiply(interestRate); //相乘

        System.out.println("贷款金额:\t" + currency.format(loanAmount));
        System.out.println("利率:\t" + percent.format(interestRate));
        System.out.println("利息:\t" + currency.format(interest));
    }




}
