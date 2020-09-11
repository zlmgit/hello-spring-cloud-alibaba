package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.service;

import com.google.common.collect.Lists;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.dao.PersonMigrationMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.model.PersonMigration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class PersonMigrationService {

    @Autowired
    private PersonMigrationMapper personMigrationMapper;

    private static String PERSON_FORMAT001 =
            "UPDATE `person` SET   `update_user` = 'hcjy'  WHERE `name` = '%s' AND `cert_no` = '%s' ";

    private static String PATH = "C:\\Users\\zy\\Desktop\\helijian\\";

    public void downSql(){

        String f = "'%s'";
        List<PersonMigration> list = personMigrationMapper.selectSuccess();
        int size = list.size();
        Set<String> set =  new HashSet<>();
        log.info("总共条数：{}",size);
        final List<PersonMigration> collect = list.stream().filter(item -> {
            return set.add(item.getName() + "_" + item.getCertNo());
        }).collect(Collectors.toList());
        int s = collect.size();
        log.info("过滤后条数：{}",s);
        IntStream.range(0,s).boxed().forEach(i->{
            PersonMigration item = collect.get(i);

            String faceRecognitionStatus = item.getFaceRecognitionStatus();
            String taxpayerCode = item.getTaxpayerCode();

            if (StringUtils.isBlank(faceRecognitionStatus)){
                faceRecognitionStatus = "`face_recognition_status`";
            }

            if (StringUtils.isBlank(taxpayerCode)) {
                taxpayerCode = "`taxpayer_code`";
            }else {
                taxpayerCode = String.format(f,taxpayerCode);
            }

            final String personInfoUpdate = String.format(PERSON_FORMAT001,item.getName(), item.getCertNo())+
                    " AND create_date >=str_to_date('2020-07-14 14:31:00', '%Y-%m-%d %H:%i') AND create_date <=str_to_date('2020-07-14 14:32:00', '%Y-%m-%d %H:%i') and update_user is null ;";
            String filePath = PATH+"人员update_user更新-"+(i/1507)+".txt";
            writeFile(filePath,personInfoUpdate);
        });

    }


    public static void writeFile(String file, String conent) {
        BufferedWriter out = null;
        try {
            //Thread.sleep(30);
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(conent+"\r\n");
        } catch (Exception e) {
            log.info("些人文件失败",e);
            log.info("错误内容：{}",conent);
        } finally {
            try {
                if (out!=null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int j  = 0;
        IntStream.range(0,10).boxed().forEach(item->{
            int i = j;
            System.out.println(i++);
        });
    }
}
