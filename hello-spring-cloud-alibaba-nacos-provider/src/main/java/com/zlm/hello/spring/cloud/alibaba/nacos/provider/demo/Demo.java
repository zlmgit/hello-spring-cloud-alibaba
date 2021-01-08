package com.zlm.hello.spring.cloud.alibaba.nacos.provider.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Person;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        /*ArrayList<Integer> integers = Lists.newArrayList(1, 2, 3, 4, 5);
        final Map<Boolean, List<Integer>> listMap = integers.stream().collect(Collectors.partitioningBy(num -> num.equals(6)));
        final List<Integer> falseList = listMap.get(Boolean.FALSE);
        final List<Integer> trueList = listMap.get(Boolean.TRUE);
        System.out.println("falseList:"+falseList);
        System.out.println("trueList:"+trueList);*/
        testLambda();

        //demo01();
    }


    /**
     *  通过遍历两个List中按id属性相等的归结到resultList中
     * @param oneList
     * @param twoList
     */
    public static List<Map<Object, Object>> compareListHitData(List<Map<Object, Object>> oneList, List<Map<Object, Object>> twoList) {
        List<Map<Object, Object>> resultList = oneList.stream().map(map -> twoList.stream()
                .filter(m -> Objects.equals(m.get("id"), map.get("id")))
                .findFirst().map(m -> {
                    map.putAll(m);
                    return map;
                }).orElse(null))
                .filter(Objects::nonNull).collect(Collectors.toList());
        return resultList;
    }

    public static void testLambda(){
        List<User> list = new ArrayList<>();
        list.add(new User(1,"xx","zxcsdv"));
        list.add(new User(1,"ls","y6"));
        list.add(new User(1,"hhh","y4"));
        list.add(new User(5,"zs","111"));
        list.add(new User(4,"xx","ytry"));
        List<User> list2 = new ArrayList<>();
        list2.add(new User(4,"xx","dg",10));
        list2.add(new User(5,"zs","ee",20));
        Map<String, User> map = list2.stream().collect(Collectors.toMap(User::getName, u -> u));
        list2.get(0).setAge(50);
        System.out.println(list2.get(0));
        System.out.println(map.get("xx"));

        List<User> collect = list.stream().filter(user -> {
            return list2.stream().anyMatch(user2 ->
            {
                if (user2.getName().equals(user.getName())){
                    user.setAge(user2.getAge());
                    return true;
                }
                return false;
            });
        }).collect(Collectors.toList());
        System.out.println(collect);
    }

    public static void distinct1(){

        List<Person> persons = new ArrayList<>();

        persons.add(new Person("1","zs","nan"));
        persons.add(new Person("2","zs","nan"));
        persons.add(new Person("3","ls","nan"));

        // 根据name去重
        List<Person> unique = persons.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getName))), ArrayList::new)
        );
        System.out.println(unique);

        //根据name sex两个属性去重
        List<Person> unique1 = persons.stream().collect(
                Collectors. collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName() + ";" + o.getSex()))), ArrayList::new)
        );
    }

    public void test(){
        List<User> list = new ArrayList<>();
        list.add(new User(1,"Zlm","zxcsdv"));
        list.add(new User(2,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(3,"hhh","dhvuodcbuodbcvuott"));
        list.add(new User(4,"Zlm","dhvuodcbuodbcvuott"));
        //list.add(new User(5,"Zlm","dhvuodcbuodbcvuott"));
        int size = list.size();
        int i = size/2;
        List<User> collect = list.stream().skip(i).collect(Collectors.toList());
        System.err.println(collect);
        List<User> collect1 = list.stream().limit(i).collect(Collectors.toList());
        System.err.println(collect1);
    }

    /**
     * @desc 1.0~1之间的BigDecimal小数，格式化后失去前面的0,则前面直接加上0。
     * 2.传入的参数等于0，则直接返回字符串"0.00"
     * 3.大于1的小数，直接格式化返回字符串
     * @param obj传入的小数
     * @return
     */
    public static String formatToNumber(BigDecimal obj) {
        DecimalFormat df = new DecimalFormat("#.00");
        if(obj.compareTo(BigDecimal.ZERO)==0) {
            return "0.00";
        }else if(obj.compareTo(BigDecimal.ZERO)>0&&obj.compareTo(new BigDecimal(1))<0){
            return "0"+df.format(obj);
        }else {
            return df.format(obj);
        }
    }

    public static void demo01(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        for (int i=1;i<9;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("id",i);
            map.put("name","张三丰"+i);
            list.add(map);
        }
        Stream<Map<String, Object>> s1 = list.stream();
        list.stream().forEach(map-> System.out.println(map));

        List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
        for (int i=1;i<5;i++){
            Map<String,Object> map2 = new HashMap<>();
            map2.put("id",i);
            map2.put("grade",i+60);
            list2.add(map2);
        }
        list2.stream().forEach(s-> System.out.println(s));

        List<Map<String, Object>> resultList2 = list.stream().map(m->{
            m.put("grade",0);
            list2.stream().filter(m2->Objects.equals(m.get("id"), m2.get("id"))).forEach(s-> m.put("grade",s.get("grade")));
            return m;
        }).collect(Collectors.toList());
        resultList2.stream().forEach(s-> System.out.println(s));
    }
}
