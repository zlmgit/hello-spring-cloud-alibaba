```sql
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### mysql BLOB类型

MySQL中，BLOB是个类型系列，包括：TinyBlob、Blob、MediumBlob、LongBlob，这几个类型之间的唯一区别是在存储文件的最大大小上不同。  
　　MySQL的四种BLOB类型  
　　类型 大小(单位：字节)  
　　TinyBlob 最大 255  
　　Blob 最大 65K  
　　MediumBlob 最大 16M  
　　LongBlob 最大 4G  

linux修改`etc/my.cnf `
 ```sh
[mysqld]  
max_allowed_packet = 16M //不同于[mysqldump]下的max_allowed_packet  
```