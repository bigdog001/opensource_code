-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.55-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema tuliao
--

CREATE DATABASE IF NOT EXISTS tuliao;
USE tuliao;

--
-- Definition of table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `longitude` double DEFAULT '116.24',
  `latitude` double DEFAULT '39.55',
  `sex` tinyint(1) NOT NULL,
  `city` varchar(45) DEFAULT '无',
  `region` varchar(45) DEFAULT '无',
  `mood` varchar(45) DEFAULT '这个家伙很懒，什么都没有留下！',
  `head` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gb2312;

--
-- Dumping data for table `t_user`
--

/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` (`id`,`name`,`password`,`longitude`,`latitude`,`sex`,`city`,`region`,`mood`,`head`) VALUES 
 (1,'李鸿','a3aca2964e72000eea4c56cb341002a4',116.24,39.55,1,'无','无','这个家伙很懒，什么都没有留下！',62),
 (2,'美女','a3aca2964e72000eea4c56cb341002a4',116.24,39.55,0,'无','无','这个家伙很懒，什么都没有留下！',77);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
