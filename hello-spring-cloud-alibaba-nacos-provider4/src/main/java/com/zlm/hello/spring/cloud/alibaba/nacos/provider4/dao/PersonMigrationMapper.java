package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.dao;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.model.PersonMigration;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PersonMigrationMapper  {

  void batchSave(List<PersonMigration> list);

  List<PersonMigration> selectPersonMigration();

  int updateFlag(@Param("id") Long id, @Param("flag") Integer flag);

  List<PersonMigration> selectSuccess();

  int updatePersonMigration(PersonMigration personMigration);

}
