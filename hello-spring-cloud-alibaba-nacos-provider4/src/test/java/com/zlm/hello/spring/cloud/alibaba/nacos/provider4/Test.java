package com.zlm.hello.spring.cloud.alibaba.nacos.provider4;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j(topic = "c.test")
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        String str = "[\n" +
                "    {\n" +
                "        \"name\":\"甘肃省\",\n" +
                "        \"pid\":0,\n" +
                "        \"id\":1\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\":\"天水市\",\n" +
                "        \"pid\":1,\n" +
                "        \"id\":2\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\":\"秦州区\",\n" +
                "        \"pid\":2,\n" +
                "        \"id\":3\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\":\"北京市\",\n" +
                "        \"pid\":0,\n" +
                "        \"id\":4\n" +
                "    },\n" +
                "    {\n" +
                "        \"name\":\"昌平区\",\n" +
                "        \"pid\":4,\n" +
                "        \"id\":5\n" +
                "    }\n" +
                "]";

        List<TreeMenuNode> menuNodes = JSONObject.parseArray(str, TreeMenuNode.class);

        List<TreeMenuNode> result = menuNodes.stream()
                .filter(permission -> permission.getPid().equals("0"))
                .map(permission -> covert(permission, menuNodes)).collect(Collectors.toList());
        //System.out.println(JSONObject.toJSONString(result));

        //test1();
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            log.info("開始");
            final Object o = guardedObject.get(2000L);
            log.info("結果：{}",o);
        },"t1").start();

        new Thread(()->{
            try {
                log.info("開始");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardedObject.complete("hello");
        },"t2").start();

        AtomicInteger atomicInteger = new AtomicInteger(100);
        final int i = atomicInteger.get();
        int update = i-20;
        atomicInteger.compareAndSet(i,update);
        System.err.println(atomicInteger.get());
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    public static TreeMenuNode covert(TreeMenuNode permission, List<TreeMenuNode> permissionList) {
        TreeMenuNode node = new TreeMenuNode();
        BeanUtils.copyProperties(permission, node);
        List<TreeMenuNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission, permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     * NEW
     */
    public static void test4(){
        final Thread thread = new Thread(() -> {

        },"t1");
        log.info("t1 state {}",thread.getState());
    }

    /**
     * RUNNABLE
     */
    public static void test5(){
        final Thread thread = new Thread(() -> {

        },"t2");
        thread.start();
        log.info("t2 state {}",thread.getState());
    }

    /**
     * BLOCKED
      * @throws InterruptedException
     */
    public static void test6() throws InterruptedException {
        Object o = new Object();
        final Thread thread = new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o) {

            }
        },"t3");
        thread.start();
        final Thread thread1 = new Thread(() -> {

            synchronized (o) {

                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t4");
        thread1.start();
        TimeUnit.SECONDS.sleep(2);
        log.info("t3 state {}",thread.getState());
    }

    /**
     * TIMED_WAITING
     */
    @SneakyThrows
    public static void test7(){
        final Thread thread = new Thread(() -> {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"t4");
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        log.info("t4 state {}",thread.getState());
    }


    public static void test1() {
        List<Student> menu = Arrays.asList(
                new Student("刘一", 721, true, Student.GradeType.THREE),
                new Student("陈二", 637, true, Student.GradeType.THREE)
               );

        List<Student> menu1 = Arrays.asList(
                new Student("张三", 666, true, Student.GradeType.THREE),
                new Student("李四", 531, true, Student.GradeType.TWO)
              );

        List<Student> menu3 = Arrays.asList(
                new Student("王五", 483, false, Student.GradeType.THREE),
                new Student("赵六", 367, true, Student.GradeType.THREE),
                new Student("孙七", 499, false, Student.GradeType.ONE));

        List<List<Student>> list = new ArrayList<>();
        list.add(menu);
        list.add(menu1);
        list.add(menu3);

        final List<Student> collect = list.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(collect);
    }

    public static void test3() throws InterruptedException {
        final Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("被打断了停下来");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.info("做一些事情");
                } catch (InterruptedException e) {
                    log.info("被打断了重设置打断标记");
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        TimeUnit.SECONDS.sleep(6);
        thread.interrupt();
        log.info("打断，{}",thread.isInterrupted());
    }


}

class GuardedObject{

    private Object response;

    public Object get(Long timeOut){
        synchronized(this){
            long startTime = System.currentTimeMillis();
            long passTime = 0;
            while (response==null){
                long waitTime  = timeOut - passTime ;
                if (waitTime <=0 ) {
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passTime = System.currentTimeMillis() - startTime;
            }
        }

        return response;
    }


    public void complete(Object response){
        synchronized (this) {
            this.response = response;
            this.notify();
        }
    }

}
