
DROP TABLE IF EXISTS `blog`;

/*--------------博客表blog----------------*/

CREATE TABLE `t_blog` (
      `id` bigint(20) UNSIGNED  NOT NULL AUTO_INCREMENT,
      `title` varchar(50) NOT NULL DEFAULT '' COMMENT '博客标题',
      `content` text NOT NULL  COMMENT '博客内容',
      `first_picture` varchar(255) NOT NULL DEFAULT '' COMMENT '博客首图url',
      `flag` char(10)  NOT NULL DEFAULT '' COMMENT '博客标识：原创、转载、翻译',
      `views` bigint(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '博客浏览量（默认为0）',
      `is_appreciate` tinyint(3) UNSIGNED  NOT NULL DEFAULT '1' COMMENT '是否在博客页面开启打赏功能（1:开启, 0:禁止，默认为1，',
      `is_comment` tinyint(3) UNSIGNED  NOT NULL DEFAULT '1' COMMENT '是否在博客页面开启评论功能（1:开启, 0:禁止，默认为1，',
      `share_statement` tinyint(3) UNSIGNED  NOT NULL DEFAULT '1' COMMENT '是否在博客页面开启转载声明功能（1:开启, 0:禁止，默认为1，',
      `status` tinyint(3) UNSIGNED  NOT NULL DEFAULT '0' COMMENT '博客的状态：保存(未发布)、发布（2:发布, 1:仅保存，默认为0，',
      `is_recommend` tinyint(3) UNSIGNED  NOT NULL DEFAULT '1' COMMENT '是否开启推荐功能（1:开启, 0:禁止，默认为1，',
      `is_deleted` tinyint(3) UNSIGNED  NOT NULL DEFAULT '0' COMMENT '逻辑删除，1：已删除，0：存在，默认0；',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='博客表blog';


/*------------------------comment评论表-----------------*/
CREATE TABLE `t_comment` (
      `id` bigint(20) UNSIGNED  NOT NULL AUTO_INCREMENT,
      `nike_name` varchar(50) NOT NULL DEFAULT '' COMMENT '评论人的昵称',
      `blog_id` bigint(20) UNSIGNED  NOT NULL COMMENT '该条评论对应的博客',
      `email` varchar(50) NOT NULL DEFAULT '' COMMENT '评论人的邮箱',
      `reply_name` varchar(50) NOT NULL DEFAULT '' COMMENT '被回复人的昵称',
      `is_blogger` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是管理员评论',
      `content` varchar(255) NOT NULL DEFAULT '' COMMENT '评论人评论的内容',
      `avatar_url` varchar(255) NOT NULL DEFAULT '' COMMENT '评论人的头像url',
      `level` tinyint(10) UNSIGNED  NOT NULL DEFAULT '1' COMMENT '该条评论的级别（默认为1，表示第一层评论者，有自评论者依次加1）',
      `parent_id` bigint(20) UNSIGNED  NOT NULL DEFAULT '0' COMMENT '该条评论的父Id(默认为0，)',
      `is_deleted` tinyint(3) UNSIGNED  NOT NULL DEFAULT '0' COMMENT '逻辑删除，1：已删除，0：存在，默认0；',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='博客-评论表';


/*------------------------博客标签表-----------------*/
CREATE TABLE `t_tag` (
     `id` bigint(20) UNSIGNED  NOT NULL AUTO_INCREMENT,
     `name` varchar(15) NOT NULL DEFAULT '' COMMENT '标签名',
     `blog_count` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '该标签下博客的数量',
     `is_deleted` tinyint(3) UNSIGNED  NOT NULL DEFAULT '0' COMMENT '逻辑删除，1：已删除，0：存在，默认0；',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='博客标签表';


/*------------------------博客类型表-----------------*/
CREATE TABLE `t_type` (
     `id` bigint(20) UNSIGNED  NOT NULL AUTO_INCREMENT,
     `name` varchar(15) NOT NULL DEFAULT '' COMMENT '类型名',
     `is_deleted` tinyint(3) UNSIGNED  NOT NULL DEFAULT '0' COMMENT '逻辑删除，1：已删除，0：存在，默认0；',
     `blog_count` int(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '该分类下博客的数量',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='博客类型表';


/*------------------------用户表-----------------*/
CREATE TABLE `t_user` (
     `id` bigint(20) UNSIGNED  NOT NULL AUTO_INCREMENT,
     `nike_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户的昵称',
     `phone` char(11) NOT NULL DEFAULT '' COMMENT '用户手机号',
     `password` varchar(50) NOT NULL DEFAULT '' COMMENT '用户密码',
     `avatar_url` varchar(255) NOT NULL DEFAULT '' COMMENT '用户的头像url',
     `type` tinyint(3) UNSIGNED  NOT NULL DEFAULT '0' COMMENT ' 用户类型(0:普通用户，1：管理员)，默认为0',
     `is_deleted` tinyint(3) UNSIGNED  NOT NULL DEFAULT '0' COMMENT '逻辑删除，1：已删除，0：存在，默认0；',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='博客-用户表';


/*------------------------博客-标签关联表-----------------*/
CREATE TABLE `t_blog_tag` (
      `id` bigint(20) UNSIGNED  NOT NULL AUTO_INCREMENT,
      `blog_id`  bigint(20) UNSIGNED NOT NULL  COMMENT '博客Id',
      `tag_id`  bigint(20) UNSIGNED NOT NULL  COMMENT '标签Id',
      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='博客-标签关联表';