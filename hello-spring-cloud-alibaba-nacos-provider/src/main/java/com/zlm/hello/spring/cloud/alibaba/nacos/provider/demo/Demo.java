package com.zlm.hello.spring.cloud.alibaba.nacos.provider.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Person;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {
        System.out.println(formatToNumber(new BigDecimal("3.435")));
        System.out.println(formatToNumber(new BigDecimal(0)));
        System.out.println(formatToNumber(new BigDecimal("0.00")));
        System.out.println(formatToNumber(new BigDecimal("0.001")));
        System.out.println(formatToNumber(new BigDecimal("0.006")));
        System.out.println(formatToNumber(new BigDecimal("0.206")));
        List<User> list = new ArrayList<>();
        list.add(new User(1,"Zlm","zxcsdv"));
        list.add(new User(1,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(1,"hhh","dhvuodcbuodbcvuott"));
        list.add(new User(4,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(5,"Zlm","111"));
        list.forEach(item->{
            if (item.getId()==4) {
                return;
            }
            System.out.println(item);
        });
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
        list.add(new User(1,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(1,"hhh","dhvuodcbuodbcvuott"));
        list.add(new User(4,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(5,"Zlm","111"));
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
        System.out.println(StringUtils.isEmpty("baba "));
        System.err.println(StringUtils.isBlank(null));
        System.err.println(StringUtils.isBlank(""));
        System.err.println(StringUtils.isBlank("  "));
        System.err.println(StringUtils.isBlank("bbjh"));
        System.err.println(StringUtils.isBlank("Bab "));*/
        final User user = list.stream().max((c1, c2) -> {
            return c1.getId().compareTo(c2.getId());
        }).get();
        System.out.println(user);

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
}
