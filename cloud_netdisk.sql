-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2021-03-13 16:52:57
-- 服务器版本： 10.4.17-MariaDB
-- PHP 版本： 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `clouddisk`
--

-- --------------------------------------------------------

--
-- 表的结构 `file`
--

CREATE TABLE `file` (
  `fileId` varchar(255) NOT NULL,
  `fileName` varchar(255) NOT NULL,
  `pre_directory` varchar(255) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  `md5` varchar(40) DEFAULT NULL,
  `fileUserName` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `userName` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL,
  `capacity` int(11) DEFAULT 50,
  `phone` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- 转储表的索引
--

--
-- 表的索引 `file`
--
ALTER TABLE `file`
  ADD PRIMARY KEY (`fileId`) USING BTREE,
  ADD KEY `FKsf5kygofvdmo7jpbtbwyvp6rf` (`fileUserName`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userName`) USING BTREE;

--
-- 限制导出的表
--

--
-- 限制表 `file`
--
ALTER TABLE `file`
  ADD CONSTRAINT `FKsf5kygofvdmo7jpbtbwyvp6rf` FOREIGN KEY (`fileUserName`) REFERENCES `user` (`userName`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
