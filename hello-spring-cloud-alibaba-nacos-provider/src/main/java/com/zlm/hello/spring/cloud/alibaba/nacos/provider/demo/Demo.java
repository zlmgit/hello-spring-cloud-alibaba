package com.zlm.hello.spring.cloud.alibaba.nacos.provider.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Person;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {
        Set set = new HashSet();
        set.addAll(new HashSet());
        System.out.println(set.size());
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
        list.add(new User(1,"Zlm","zxcsdv"));
        list.add(new User(2,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(1,"hhh","dhvuodcbuodbcvuott"));
        list.add(new User(4,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(5,"Zlm","dhvuodcbuodbcvuott"));
        Map<String,Object> map = new HashMap();
        map.put("name","Zlm");map.put("age",10);
        Map<String,Object> map1 = new HashMap();
        map1.put("name","hhh");map1.put("age",20);
        List<Map<String,Object>> lists = new ArrayList<>();
        lists.add(map);lists.add(map1);
        List<String> names = lists.stream().map(item -> (String) item.get("name")).collect(Collectors.toList());
        List<User> users = names.stream().map(item ->
                list.stream().filter(user -> item.equals(user.getName()))
                        .findAny()
                        .orElse(null)
        ).filter(Objects::nonNull).collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        //System.out.println(users);
        /*System.out.println(StringUtils.isEmpty(null));
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(" "));
        System.out.println(StringUtils.isEmpty("baba"));
        System.out.println(StringUtils.isEmpty("baba "));*/
        System.err.println(StringUtils.isBlank(null));
        System.err.println(StringUtils.isBlank(""));
        System.err.println(StringUtils.isBlank("  "));
        System.err.println(StringUtils.isBlank("bbjh"));
        System.err.println(StringUtils.isBlank("Bab "));
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
}
