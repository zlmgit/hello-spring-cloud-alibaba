CREATE TABLE `user` (
`id` int(255) NOT NULL AUTO_INCREMENT,
`name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
`password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;