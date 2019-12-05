package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo.thread;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

public class TestMain {
    private static final int poolSize = 15 ;

    public static void main(String[] args ){
        //domain
        try {
            doTask("forEach");
            doTask("forThread");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void doTask(String type) throws  InterruptedException{
        System.out.println("--------------start doTask for type:"+type+"--------------");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long startMillis = System.currentTimeMillis() ;
        String startDate = sdf.format(new Date(startMillis));
        System.out.println("do "+type+" start time is :"+startDate);
        String[] gifts = {"apple","orange","cat","dog","tomato","monkey","birds","fish","milk","pen"};
        Map<String,String> resultMap = new HashMap<String,String>();
        initData(resultMap);
        Random r = new Random();
        Map<String , String> family = new HashMap<>();
        switch(type){
            case "forEach":
                doForEach(resultMap ,family,gifts);
                break;
            case "forThread":
                doByThread(resultMap ,family,gifts);
                break;
            default:
                break ;
        }
        Long endMillis = System.currentTimeMillis() ;
        String endDate = sdf.format(new Date(endMillis));
        System.out.println("do "+type+" end time is :"+endDate);
        System.out.println("do "+type+" cost time is :"+ (endMillis -startMillis)/1000);
        System.out.println(handleResult(family));
    }

    /**
     * 以循环的方式去执行任务
     * @throws InterruptedException
     */
    public static void doForEach(Map<String,String> resultMap ,Map<String , String> family,String[] gifts) throws  InterruptedException{

        Random r = new Random();
        //将10个礼物依次发出去，并收集感受
        for(String gift : gifts){

            int code = r.nextInt(100);
            code = code%5;
            String result = resultMap.get(code+"") ;
            //System.out.println("the code is "+code+" , the result is "+result);
            family.put(gift,result) ;
            //一个循环设置花2秒
            Thread.sleep(2000);

        }
    }


    /**
     * 以线程的方式去执行任务
     * @throws InterruptedException
     */
    public static void doByThread(Map<String,String> resultMap ,Map<String , String> family,String[] gifts)  throws  InterruptedException {

        Random r = new Random();
        ExecutorService es = Executors.newFixedThreadPool(poolSize);
        System.out.println("the poolSize is "+ poolSize);
        List<Future<String[]>> futureList = new ArrayList<>( );

        //将10个礼物依次发出去，并收集感受
        for(String gift : gifts){

            Future<String[]> future =  es.submit(new SendGift(gift ,resultMap));
            futureList.add(future) ;
            future.isDone();

        }
        es.shutdown();
        while(true){
            if(es.isTerminated()){
                System.out.println("所有线程都执行结束了.");
                break ;

            }
            Thread.sleep(100);
        }

        for(Future<String[]> f :futureList){
            try {
                String[] result = f.get();
                family.put(result[0],result[1]);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 设置收获感受
     * @param resultMap
     */
    private static void initData(Map<String,String> resultMap){
        if(resultMap == null){
            resultMap = new HashMap<String,String>() ;
        }
        resultMap.put("0","sed");
        resultMap.put("1","happy");
        resultMap.put("2","so so");
        resultMap.put("3","wanting others");
        resultMap.put("4","too nice");
    }

    /**
     * 处理打印结果
     * @param resultMap
     * @return
     */
    private  static String handleResult(Map<String ,String> resultMap){

        Iterator it = resultMap.entrySet().iterator();
        StringBuffer sb = new StringBuffer();
        while(it.hasNext()){
            Map.Entry<String,String> entry =  (Map.Entry<String,String>) it.next();
            String key = entry.getKey();
            String val = entry.getValue();
            sb.append("the family who get the "+key +" is think "+val +" ;\n");
        }

        return sb.toString().substring(0,sb.length()-1) ;
    }

}