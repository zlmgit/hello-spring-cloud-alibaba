package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.dao;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.Attachment;
import org.apache.ibatis.annotations.Param;


public interface AttachmentMapper {

    int insertAttachment(Attachment attachment);

    Attachment selectAttachmentById(@Param("id") Integer id);
}
