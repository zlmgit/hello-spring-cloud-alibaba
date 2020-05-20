package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.service;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.dao.AttachmentMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    public int insertAttachment(Attachment attachment){
        return attachmentMapper.insertAttachment(attachment);
    }
}
