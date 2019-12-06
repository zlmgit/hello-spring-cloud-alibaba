package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
@Data
@Document(collection = "ralph_service_log")
public class ServiceLog implements Serializable{

  private static final long serialVersionUID = -2100033036297532284L;
  
  @Id
  @Field("id")
  private String id;

  @Field("service_name")
  private String serviceName;

  @Field("service_desc")
  private String serviceDesc;

  @Field("channel")
  private String channel;

  /**
   * 提现的信息来源 1-api接口 2-pc web
   */
  @Field("entryWay")
  private String entryWay;

  @Field("param")
  private String param;

  @Field("result")
  private Integer result;

  @Field("msg")
  private String msg;

  @Field("service_date")
  private Date serviceDate;

  @Field("data")
  private String data;

  @Field("fail_reasons")
  private String failReasons;

  /**
   * 投递状态
   */
  @Field("send_state")
  private Integer sendState;

  /**
   * 投递次数
   */
  @Field("send_num")
  private Integer sendNum;
  /**
   * 校验后数据
   */
  @Field("validate_after_param")
  private String validateAfterParam;

  /**
   * 下次重试时间
   */
  @Field("next_retry")
  private Date nextRetry;

  /**
   * 父亲Id
   */
  @Field("parent_id")
  private String parentId;

  /**
   * 数据大小
   */
  @Field("data_size")
  private Integer dataSize;
  /**
   * list_index
   * 数据下标
   */
  @Field("data_index")
  private Integer dataIndex;


}
