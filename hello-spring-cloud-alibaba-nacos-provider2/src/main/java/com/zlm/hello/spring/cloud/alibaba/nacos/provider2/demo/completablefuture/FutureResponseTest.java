package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo.completablefuture;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName: FutureResponseTest
 * @Description: 根据交易请求的额度和方式，进行扣款汇总计算
 * 计算交易一共扣款的数额：
 * 		利用Callable和Future来计算，future.get()会获取任务结果，如果没有获取到会一直阻塞直到任务结束。
 *
 * CompletionService，对于计算完成后获得结果，这种程序由很好的支持。
 * 		ExecutorCompletionService的实现非常简单。在构造函数中创建一个BlockingQueue来保存计算完成的结果。
 * 当计算完成时，调用FutureTask中的done方法。当提交某个任务时，该任务将首先包装为一个QueueingFuture，这是FutureTask的一个子类，
 * 然后再改写子类的done方法，并将结果放入BlockingQueue中，
 *
 * @author CC
 * @date 2018年12月6日 上午10:44:20
 * @version V1.0
 */
public class FutureResponseTest {
    //线程池 -- 任务执行委托给executor
    private final ExecutorService executor ;//= ;

    FutureResponseTest(ExecutorService executor){
        this.executor = executor;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    //调度请求，获得返回结果，并进行汇总处理
    public static void main(String[] args) throws Exception {
        final String[] methodStr = new String[] {"WeiXin","ZhiFuBao","WangYin"};
        final String[] serviceStr = new String[] {"TaoBao","JingDong","TianMao"};

        //为了方便，我们将请求先初始化完毕
        final List<Request> requestList = new ArrayList<Request>();
        for (int i = 0; i < 20; i++) {
            Request request = new Request();
            request.setMethod(methodStr[(int) (Math.random() * 3)]);
            request.setParam((int) (Math.random() * 300));
            request.setServieName(serviceStr[(int) (Math.random() * 3)]);
            requestList.add(request);
        }

        long startTime = System.currentTimeMillis();//开始时间

        //创建完成服务CompletionService
        FutureResponseTest test = new FutureResponseTest(Executors.newCachedThreadPool());
        CompletionService<Double> completionService = new ExecutorCompletionService<Double>(test.getExecutor());//相当于一组计算的句柄，计算执行部分委托给Executor

        //累积计算所有请求的总扣款数--计算任务提前开始且每个都是分开的不相互影响
        for (int i = 0; i < requestList.size(); i++) {
            Request request = requestList.get(i);
            completionService.submit(new Callable<Double>() {//提交任务
                @Override
                public Double call() throws Exception {
                    return  requestForService(request);
                }
            });
        }

        try {
            //求扣款之和
            BigDecimal sum = BigDecimal.ZERO;//同理  double计算结果不精确
            for (int i = 0; i < requestList.size(); i++) {
                Future<Double> f = completionService.take();//获得结果，未获得前会阻塞
                double payMent = f.get();
                sum = sum.add(new BigDecimal(String.valueOf(payMent)));
            }
            System.out.println("一共扣款了多少钱？" + sum.doubleValue());
        } catch (InterruptedException e) {
            // TODO: 任务调用get的线程在获得结果之前被中断
            //重新设置线程的中断状态
            Thread.currentThread().interrupt();
            System.out.println("任务调用get的线程在获得结果之前被中断！" + e);
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        } finally {
            test.getExecutor().shutdown();
        }

        long endTime = System.currentTimeMillis();//结束时间
        System.out.println("消耗时间：" + (endTime - startTime) + "毫秒！");
    }

    //模拟第三方服务
    public static double requestForService(Request request) throws InterruptedException, Exception{
        if(null == request) {
            throw new Exception("请求为空！");
        }
        if(request.getParam() <= 0) {
            throw new Exception("参数小于0，无法进行扣款！" + request);
        }

        System.out.println("开始处理请求...");

        //为了简便直接返回一个结果即可
        double result = 0.0;
        if("WeiXin".equals(request.getMethod())) {
            System.out.println("微信支付扣3%");
//			result = request.getParam() * 0.03;//double类型计算结果不准确  例如17 * 0.05 返回  扣款数  0.8500000000000001
            result = new BigDecimal(String.valueOf(request.getParam())).multiply(new BigDecimal("0.03")).doubleValue();
        }else {
            System.out.println("其他支付直接扣5%");
            result = new BigDecimal(String.valueOf(request.getParam())).multiply(new BigDecimal("0.05")).doubleValue();
        }

        //模拟-使消耗时间长一些
        Thread.sleep(3000);
        System.out.println(request + " 返回扣款结果：" + result);
        return result;
    }

    /**
     * @Title: launderThrowable
     * @Description: 任务执行过程中遇到异常，根据包装的ExecutionException重新抛出异常，并打印异常信息
     * @param @param cause
     * @param @return
     * @return Exception
     * @throws
     * @author CC
     * @date 2018年12月7日 上午9:36:27
     * @version V1.0
     */
    private static Exception launderThrowable(Throwable cause) {
        //抛出
        Exception exception = new Exception("任务执行过程中遇到异常！" + cause);
        //打印
        cause.printStackTrace();
        return exception;
    }


}


