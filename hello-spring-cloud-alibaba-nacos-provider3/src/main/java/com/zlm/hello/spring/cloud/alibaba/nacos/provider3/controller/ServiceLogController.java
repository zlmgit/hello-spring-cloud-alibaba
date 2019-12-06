package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.ServiceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class ServiceLogController {

    @Autowired
    private MongoTemplate mongoTemplate;


    //https://www.jianshu.com/p/78b96ca40927
    //https://www.cnblogs.com/shenyixin/p/9453742.html
    @GetMapping("getServiceLogs")
    public Object getServiceLogs(){
        Criteria criteria = new Criteria();
        //criteria.and("send_state").is(0);
        //criteria.and("service_name").is("ralphService.submitWithdraw");
        //criteria.and("next_retry").lt(new Date());
       /* Query query = new Query(Criteria.where("id").is("5de72122eebd06201ce695c5"));
        ServiceLog serviceLog = mongoTemplate.findOne(query, ServiceLog.class);*/
	//分组的依据，根据那些参数进行分组
        Aggregation aggregation;
        aggregation = Aggregation.newAggregation(
                Aggregation.project("parent_id","dataSize"),
                Aggregation.group("parent_id")
                        .first("parent_id").as("parent_id")
                        .count().as("data_size")
        );
        AggregationResults<ServiceLog> aggregate = mongoTemplate.aggregate(aggregation, "ralph_service_log", ServiceLog.class);
        return aggregate.getMappedResults();
    }
    @GetMapping("getServiceLog")
    public Object getServiceLog(){
        Criteria criteria = new Criteria();
        criteria.and("send_state").is(0);
        criteria.and("service_name").is("ralphService.submitWithdraw");
        criteria.and("next_retry").lt(new Date());
        Query query = new Query(Criteria.where("id").is("5de72122eebd06201ce695c5"));
        return mongoTemplate.findOne(query, ServiceLog.class);
    }
}
