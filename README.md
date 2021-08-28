# 	SpringBoot 多人博客网站开发

------

> ​	***作者：幽弥狂***

GitHub仓库地址： https://github.com/srg000/blog

Gitee仓库地址： https://gitee.com/novel_dev_team/novel-plus



## 目录结构

简单的springboot单体应用。



##  项目架构

前台web网站架构：Springboot+MybatisPlus+Mysql+Thymeleaf+Layui+semantic-ui+Redis

平台后台管理系统架构 : Springboot+Mybatis+Mysql+Redis+Thymeleaf+Layui



## 前台web网站截图

1、网站首页

![image-20210828194849211](https://github.com/srg000/blog/blob/master/img/image-20210828194849211.png)

2、博客首页

![image-20210828194817474](E:\typora-图片\image-20210828194817474.png)

3、博客详情页

![image-20210828195022763](E:\typora-图片\image-20210828195022763.png)

4、归档页

![image-20210828195135800](E:\typora-图片\image-20210828195135800.png)

5、发布博客

![image-20210828195306247](E:\typora-图片\image-20210828195306247.png)

6、个人中心

![image-20210828195340342](E:\typora-图片\image-20210828195340342.png)

7、个人的博客列表

![image-20210828195420132](E:\typora-图片\image-20210828195420132.png)



##  后台管理系统截图

![image-20210828195515881](E:\typora-图片\image-20210828195515881.png)

![image-20210828195539401](E:\typora-图片\image-20210828195539401.png)

##  安装说明

- 数据库安装：

1. 安装MySQL软件。
2. 修改MySQL`max_allowed_packet `配置（建议100M）。
3. 新建数据库blog:create database blog default character set utf8mb4 collate utf8mb4_general_ci 。
4. 执行blog.sql文件。

- 配置文件修改：

1. 修改application.yml文件中数据库配置。
2. 修改application.yml文件中Redis配置。
3. 修改application.yml文件中文件上传路径配置。

- 本地直接运行或使用maven插件打包成jar文件上传到服务器上。
- `http://ip:port`访问首页。

**喜欢此项目的可以给我的GitHub和Gitee加个Star支持一下 。**

## 演示地址（服务器成本过高，暂停演示地址）

[点击前往](http://www.srgl.ren:9000/)（前台）

(**后台爬虫程序运行会占用大量服务器资源，试用人数过多，服务器压力大，现暂停演示** )



##  代码仓库

GitHub仓库地址： https://github.com/201206030/fiction_house

Gitee仓库地址： https://gitee.com/novel_dev_team/fiction_house
