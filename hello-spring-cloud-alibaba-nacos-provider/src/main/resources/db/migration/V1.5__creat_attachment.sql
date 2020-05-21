CREATE TABLE `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件名',
  `type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件类型',
  `size` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '大小',
  `file` blob COMMENT '文件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;