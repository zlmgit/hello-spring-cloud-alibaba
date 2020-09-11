package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.model;


import lombok.Data;


import java.io.Serializable;

@Data
public class PersonMigration  implements Serializable {

  private static final long serialVersionUID = 3425974686555426885L;


  private Long id;


  private String name;


  private String certNo;


  private String mobile;


  private String accountType;


  private String accountNo;


  private String bankNo;

  private String alipayNo;

  private String openId;

  private String appid;


  private String cardImageFront;

  private String cardImageBack;

  private String faceRecognitionStatus;

  private String faceRecognitionUrl;

  private String taxpayerCode;

  private String code;

  private String msg;

}
