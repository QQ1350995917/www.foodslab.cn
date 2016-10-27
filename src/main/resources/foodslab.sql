-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2016-10-27 06:25:41
-- 服务器版本： 5.6.22
-- PHP Version: 5.5.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `foodslab`
--

-- --------------------------------------------------------

--
-- 表的结构 `activity`
--

CREATE TABLE IF NOT EXISTS `activity` (
  `activityId` varchar(36) NOT NULL,
  `imageId` varchar(36) NOT NULL,
  `imagePath` varchar(512) NOT NULL,
  `status` int(1) NOT NULL,
  `clickable` int(1) NOT NULL,
  `link` varchar(512) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `image`
--

CREATE TABLE IF NOT EXISTS `image` (
  `imageId` varchar(36) NOT NULL,
  `filePath` varchar(512) NOT NULL,
  `level` int(4) NOT NULL,
  `queue` int(4) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '默认1，标示正常可用状态',
  `trunkId` varchar(36) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图片树，树形结构';

-- --------------------------------------------------------

--
-- 表的结构 `link`
--

CREATE TABLE IF NOT EXISTS `link` (
  `linkId` varchar(36) NOT NULL COMMENT '链接ID',
  `label` varchar(32) NOT NULL COMMENT '链接名称',
  `href` varchar(512) DEFAULT NULL COMMENT '链接地址',
  `pid` varchar(36) NOT NULL,
  `weight` int(4) NOT NULL DEFAULT '0' COMMENT '权重，值越大，权重越大',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '-1标示删除，1标示禁用，2标示正常',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='pid=id的为分类';

-- --------------------------------------------------------

--
-- 表的结构 `manager`
--

CREATE TABLE IF NOT EXISTS `manager` (
  `managerId` varchar(36) NOT NULL,
  `loginName` varchar(36) NOT NULL COMMENT '登录名',
  `username` varchar(36) NOT NULL COMMENT '用户名',
  `password` varchar(256) NOT NULL,
  `level` int(4) NOT NULL DEFAULT '1' COMMENT '正整数',
  `queue` int(4) NOT NULL DEFAULT '1' COMMENT '同一个level下的排序，正整数',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '-1标示删除，1标示禁用，2标示正常',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `manager_menu`
--

CREATE TABLE IF NOT EXISTS `manager_menu` (
  `managerId` varchar(36) NOT NULL,
  `menuId` varchar(36) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员以及对应能看到的菜单';

-- --------------------------------------------------------

--
-- 表的结构 `menu`
--

CREATE TABLE IF NOT EXISTS `menu` (
  `menuId` varchar(36) NOT NULL,
  `label` varchar(32) NOT NULL COMMENT '显示名称',
  `flag` varchar(36) NOT NULL COMMENT '标记点击事件调用方法字段',
  `queue` int(4) NOT NULL COMMENT '菜单的排序字段',
  `category` int(1) NOT NULL COMMENT '菜单的种类，正整数',
  `status` int(11) NOT NULL COMMENT '-1删除状态，1，禁用状态，2正常状态，3该菜单除了超级管理员不可授予其他人',
  `actionKey` varchar(512) NOT NULL COMMENT '在该菜单下可以执行的接口集合，使用英文都好分割',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台菜单表，树形结构';

-- --------------------------------------------------------

--
-- 表的结构 `meta_address`
--

CREATE TABLE IF NOT EXISTS `meta_address` (
  `code` varchar(12) NOT NULL,
  `label` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `level` int(4) NOT NULL,
  `queue` int(4) NOT NULL,
  `status` int(1) NOT NULL,
  `pcode` varchar(12) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `meta_unit`
--

CREATE TABLE IF NOT EXISTS `meta_unit` (
  `unitId` varchar(36) NOT NULL,
  `label` varchar(16) NOT NULL,
  `name` varchar(36) NOT NULL,
  `queue` int(11) NOT NULL,
  `description` text,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='只读表，预定义一些常用单位符号，数据可常驻内存。';

-- --------------------------------------------------------

--
-- 表的结构 `poster`
--

CREATE TABLE IF NOT EXISTS `poster` (
  `posterId` varchar(36) NOT NULL,
  `name` varchar(36) DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '-1标示删除，1标示禁用，2标示正常',
  `clickable` int(1) NOT NULL DEFAULT '1' COMMENT '1不可点击，2可以点击',
  `href` varchar(512) DEFAULT NULL COMMENT '点击后打开的网页地址',
  `fileId` varchar(36) DEFAULT NULL COMMENT '附件的ID',
  `weight` int(1) DEFAULT '0' COMMENT '权重',
  `start` timestamp NULL DEFAULT NULL COMMENT '显示的开始时间',
  `end` timestamp NULL DEFAULT NULL COMMENT '显示的结束时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- 表的结构 `product_format`
--

CREATE TABLE IF NOT EXISTS `product_format` (
  `formatId` varchar(36) NOT NULL,
  `label` varchar(32) NOT NULL,
  `meta` varchar(8) NOT NULL COMMENT '单位',
  `amount` int(11) NOT NULL COMMENT '该规格下的产品数量',
  `amountMeta` varchar(8) NOT NULL COMMENT '该规格下的产品数量单位',
  `price` float NOT NULL COMMENT '额定定价',
  `priceMeta` varchar(8) NOT NULL COMMENT '数量单位',
  `postage` int(4) NOT NULL COMMENT '邮费',
  `postageMeta` varchar(8) NOT NULL COMMENT '邮费单位',
  `pricing` float DEFAULT NULL COMMENT '现价',
  `pricingDiscount` float NOT NULL DEFAULT '1' COMMENT '折扣',
  `pricingStart` bigint(20) DEFAULT NULL COMMENT '折扣和价格的开始时间',
  `pricingEnd` bigint(20) DEFAULT NULL COMMENT '折扣和价格的结束时间',
  `pricingStatus` int(1) NOT NULL DEFAULT '1' COMMENT '折扣/现价的状态，1禁用，2启用',
  `expressCount` int(4) DEFAULT NULL COMMENT '包邮数量',
  `expressName` varchar(32) DEFAULT NULL COMMENT '包邮快递公司名称',
  `expressStart` bigint(20) DEFAULT NULL COMMENT '包邮开始时间',
  `expressEnd` bigint(20) DEFAULT NULL COMMENT '包邮结束时间',
  `expressStatus` int(1) NOT NULL DEFAULT '1' COMMENT '包邮的状态，1禁用，2启用',
  `giftCount` int(4) DEFAULT NULL COMMENT '满?赠',
  `giftLabel` varchar(32) DEFAULT NULL COMMENT '满赠产品名称',
  `giftId` varchar(36) DEFAULT NULL COMMENT '满赠产品的ID',
  `giftStart` bigint(20) DEFAULT NULL COMMENT '满赠开始时间',
  `giftEnd` bigint(20) DEFAULT NULL COMMENT '满赠结束时间',
  `giftStatus` int(1) NOT NULL DEFAULT '1' COMMENT '满赠的状，1禁用，2启用',
  `queue` int(4) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `weight` int(4) NOT NULL DEFAULT '0' COMMENT '全局权重，推荐相关，小于0为推荐，越小权重越高',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '-1标示删除，1标示禁用，2标示正常',
  `typeId` varchar(36) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- 表的结构 `product_series`
--

CREATE TABLE IF NOT EXISTS `product_series` (
  `seriesId` varchar(36) NOT NULL,
  `label` varchar(16) NOT NULL COMMENT '显示名称',
  `queue` int(4) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '-1标示删除，1标示禁用，2标示正常',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `product_type`
--

CREATE TABLE IF NOT EXISTS `product_type` (
  `typeId` varchar(36) NOT NULL,
  `label` varchar(32) NOT NULL COMMENT '显示名称',
  `summary` varchar(512) DEFAULT NULL COMMENT '简介',
  `directions` text COMMENT '详细说明',
  `queue` int(4) NOT NULL DEFAULT '0' COMMENT '排序字段',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '-1标示删除，1标示禁用，2标示正常',
  `seriesId` varchar(36) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `userId` varchar(36) NOT NULL,
  `status` int(1) NOT NULL DEFAULT '2' COMMENT '-1标示删除，1标示禁用，2标示正常',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user_account`
--

CREATE TABLE IF NOT EXISTS `user_account` (
  `accountId` varchar(36) NOT NULL,
  `identity` varchar(36) NOT NULL COMMENT '电话号码或openId',
  `password` varchar(256) NOT NULL COMMENT 'identity+password的sha1摘要',
  `nickName` varchar(32) DEFAULT '' COMMENT '账户昵称',
  `gender` int(1) DEFAULT NULL COMMENT '1男性，2女性',
  `address` varchar(200) DEFAULT '未知地址' COMMENT '用户地址',
  `portrait` varchar(500) DEFAULT NULL COMMENT '用户头像',
  `birthday` timestamp NULL DEFAULT NULL COMMENT '生日',
  `source` int(1) DEFAULT '1' COMMENT '1电话注册，2微信注册，3QQ注册',
  `userId` varchar(36) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------

--
-- 表的结构 `user_cart`
--

CREATE TABLE IF NOT EXISTS `user_cart` (
  `mappingId` varchar(36) NOT NULL,
  `accountId` varchar(36) DEFAULT NULL COMMENT '为空标记匿名购物',
  `formatId` varchar(36) NOT NULL,
  `amount` int(4) NOT NULL COMMENT '支付前=购物车的数量，支付后=购买的数量',
  `pricing` float DEFAULT NULL COMMENT '支付时候的单价',
  `orderId` varchar(36) DEFAULT NULL COMMENT '已经支付的订单ID',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '-1删除，1购物车状态，2已经支状态',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user_order`
--

CREATE TABLE IF NOT EXISTS `user_order` (
  `orderId` varchar(36) NOT NULL,
  `accountId` varchar(36) DEFAULT NULL COMMENT '匿名购买可以为空',
  `senderName` varchar(36) DEFAULT NULL,
  `senderPhone` varchar(11) DEFAULT NULL,
  `receiverId` varchar(36) NOT NULL,
  `cost` float NOT NULL COMMENT '订单中的商品总价格',
  `postage` float NOT NULL DEFAULT '0' COMMENT '该订单需要支付的邮费',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '-1删除，0禁用，1未发货，2已经发货，3已经完成',
  `expressLabel` varchar(32) DEFAULT NULL,
  `expressNumber` varchar(36) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `user_receiver`
--

CREATE TABLE IF NOT EXISTS `user_receiver` (
  `receiverId` varchar(36) NOT NULL,
  `name` varchar(36) NOT NULL COMMENT '姓名',
  `phone0` varchar(11) NOT NULL COMMENT '电话号码',
  `phone1` varchar(11) DEFAULT NULL COMMENT '备用电话',
  `province` varchar(50) NOT NULL COMMENT '省级单位',
  `city` varchar(50) NOT NULL COMMENT '市区单位',
  `county` varchar(50) NOT NULL COMMENT '县区单位',
  `town` varchar(50) NOT NULL COMMENT '乡镇单位',
  `village` varchar(64) DEFAULT NULL COMMENT '村级单位，为空标示无村级单位',
  `append` varchar(100) DEFAULT NULL COMMENT '用户追加的地址',
  `status` int(1) NOT NULL DEFAULT '2' COMMENT '-1标示删除，1标示禁用，2标示正常，3标示默认收货人；目前没有为1的状态情况',
  `accountId` varchar(36) DEFAULT NULL COMMENT '为空则表示为匿名用户提交的收货人',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`imageId`);

--
-- Indexes for table `link`
--
ALTER TABLE `link`
  ADD PRIMARY KEY (`linkId`);

--
-- Indexes for table `manager`
--
ALTER TABLE `manager`
  ADD PRIMARY KEY (`managerId`);

--
-- Indexes for table `manager_menu`
--
ALTER TABLE `manager_menu`
  ADD PRIMARY KEY (`managerId`,`menuId`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menuId`);

--
-- Indexes for table `meta_address`
--
ALTER TABLE `meta_address`
  ADD PRIMARY KEY (`code`);

--
-- Indexes for table `meta_unit`
--
ALTER TABLE `meta_unit`
  ADD PRIMARY KEY (`unitId`);

--
-- Indexes for table `poster`
--
ALTER TABLE `poster`
  ADD PRIMARY KEY (`posterId`);

--
-- Indexes for table `product_format`
--
ALTER TABLE `product_format`
  ADD PRIMARY KEY (`formatId`);

--
-- Indexes for table `product_series`
--
ALTER TABLE `product_series`
  ADD PRIMARY KEY (`seriesId`);

--
-- Indexes for table `product_type`
--
ALTER TABLE `product_type`
  ADD PRIMARY KEY (`typeId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userId`);

--
-- Indexes for table `user_account`
--
ALTER TABLE `user_account`
  ADD PRIMARY KEY (`accountId`);

--
-- Indexes for table `user_cart`
--
ALTER TABLE `user_cart`
  ADD PRIMARY KEY (`mappingId`);

--
-- Indexes for table `user_order`
--
ALTER TABLE `user_order`
  ADD PRIMARY KEY (`orderId`);

--
-- Indexes for table `user_receiver`
--
ALTER TABLE `user_receiver`
  ADD PRIMARY KEY (`receiverId`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
