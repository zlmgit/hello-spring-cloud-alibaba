CREATE TABLE `myshop`.`attachment`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NULL COMMENT '文件名',
  `type` varchar(255) NULL COMMENT '文件类型',
  `size` varchar(255) NULL COMMENT '大小',
  `file` blob NULL COMMENT '文件',
  PRIMARY KEY (`id`)
);