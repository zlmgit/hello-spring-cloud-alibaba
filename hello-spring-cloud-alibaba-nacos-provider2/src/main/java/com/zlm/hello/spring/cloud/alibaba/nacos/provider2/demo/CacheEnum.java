package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo;

import lombok.Getter;

@Getter
public class CacheEnum {

  public enum Company {
    RALPH_COMPANY("ralphCompany", "企业缓存");
    private String key;
    private String name;

    private Company(String key, String name) {
      this.key = key;
      this.name = name;
    }
  }
  public enum RalphTaxArea {
    RALPH_TAX_AREA("ralphTaxArea", "报税地缓存");
    private String key;
    private String name;

    private RalphTaxArea(String key, String name) {
      this.key = key;
      this.name = name;
    }
  }

  public enum CompanyConfiguration {
    COMPANYCONY_CONFIGURATION("ralphCompanyConfiguration", "企业配置缓存");
    private String key;
    private String name;

    private CompanyConfiguration(String key, String name) {
      this.key = key;
      this.name = name;
    }
  }

  public enum CompanyTaskTemplate {
    COMPANY_TASK_TEMPLATE("ralphCompanyTaskTemplate", "企业任务模板");
    private String key;
    private String name;

    private CompanyTaskTemplate(String key, String name) {
      this.key = key;
      this.name = name;
    }
  }

  public static void main(String[] args) {
    System.out.println(CompanyTaskTemplate.COMPANY_TASK_TEMPLATE.key);
  }
}
