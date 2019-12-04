package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo.thread;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;

public  class SendGift implements Callable<String[]> {

    private String gift ;
    private Map<String,String> resultMap ;

    public SendGift(String gift ,Map<String,String> resultMap ){
        this.gift = gift ;
        this.resultMap = resultMap ;
    }

    @Override
    public String[] call() throws Exception {
        Random r = new Random();
        int code = r.nextInt(100);
        code = code%5;
        String result = resultMap.get(code+"") ;
        //System.out.println("the code is "+code+" , the result is "+result);
        //,为了让测试结果明显一点，一个循环设置花2秒
        Thread.sleep(2000);
        String[] resultArray  = {this.gift ,result};
        return resultArray ;
    }
}