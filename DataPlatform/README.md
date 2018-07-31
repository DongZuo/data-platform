Introduce
-----------------------------------
The project used SpringBoot as backend framwork, used apacheShiro for user authentication and authorization.Mybatis and Druid are used for data ORM and persistance.
Front end source code path : DataPlatform/src/main/resources/static/  

Main Features
-----------------------------------

* User Role Management
* Report Download
* MySQL <-> Hive Data transformation
* Data bacatracking
* Indicator Management
* Interface Management


使用说明
-----------------------------------

* 导入项目根目录下:db.sql文件到mysql数据库
* 导入项目到Eclipse或Idea.
* 修改数据库配置文件jdbc.properties中的账号密码.
* 修改application.properties 中的jdbc相关配置 
* 配置maven命令:spring-boot:run启动项目,管理员yuezhiyue@youxin.com 密码:111111
* 脚本执行绝对路径 : /home/hdfs/zuodong/dp/   另外在DataPlatform目录下有脚本源码的备份 
* 由于涉及到Hive相关操作,项目服务器需要hdfs用户启动 



平台目录结构说明
-----------------------------------
```
├─main
│  │
│  ├──conf--------------Main configuration
│  │   ├─beta ------test environment config
│  │   ├─prd -------product environment config
│  │
│  ├──java
│  │   │
│  │   └─com.yxb.cms----------------project code
│  │             │
│  │             ├─architect
│  │             │    │
│  │             │    ├─conf
│  │             │    │
│  │             │    ├─constant
│  │             │    │
│  │             │    ├─filter
│  │             │    │
│  │             │    ├─interceptor
│  │             │    │
│  │             │    ├─properties
│  │             │    │
│  │             │    ├─realm
│  │             │    │
│  │             │    ├─utils
│  │             │
│  │             ├─controller
│  │             │
│  │             ├─dao
│  │             │
│  │             ├─domain
│  │             │
│  │             ├─model
│  │             │
│  │             ├─service
│  │             │
│  │             └─Application
│  │
│  ├─resources
│  │     │
│  │     ├─mapper
│  │     │
│  │     ├─application.properties
│  │     │
│  │     ├─ehcache-shiro.xml
│  │     │
│  │     ├─log4j.properties
│  │     │
│  │     ├─mybatis-config.xml
│  │
│  │
│  └─webapp


```
