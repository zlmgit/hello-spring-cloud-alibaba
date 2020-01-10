package com.zlm.hello.spring.cloud.alibaba.nacos.provider3;

import com.mongodb.client.result.DeleteResult;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.ServiceLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication3.class)
public class MongoDBServiceTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testSave(){
        ServiceLog serviceLog = new ServiceLog();
        serviceLog.setServiceDate(new Date());
        serviceLog.setServiceName("test");
        serviceLog.setMsg("test_msg");
        mongoTemplate.save(serviceLog);
    }


    @Test
    public void testRemove() throws ParseException {
        Query queryOne = new Query(Criteria.where("id").is("5e101b3248758a3938a4a447"));
        ServiceLog serviceLog = mongoTemplate.findOne(queryOne, ServiceLog.class);
        Query query = new Query(Criteria.where("service_name")
                .is("test")
                .and("msg").is("test-msg")
                .and("service_date").lt(new Date()));
        List<ServiceLog> serviceLogs = mongoTemplate.findAllAndRemove(query, ServiceLog.class);
        System.out.println(serviceLogs);
        long maxMemory = Runtime.getRuntime().maxMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println(maxMemory/(double)1024/1024);
        System.out.println(totalMemory/(double)1024/1024);
        Collection collection = null;
        Executors.newSingleThreadExecutor();
        Executors.newFixedThreadPool(2);
        Executors.newCachedThreadPool();
    }

}
