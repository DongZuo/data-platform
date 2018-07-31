简介
-----------------------------------
采用SpringBoot构建整个项目框架,apacheShiro权限验证，mybatis+druid数据持久化。
前端所在路径:DataPlatform/src/main/resources/static/  

功能说明
-----------------------------------

* 登陆验证
* 日报查询下载
* MySQL <-> Hive 数据传输 
* 渠道管理
* 数据回溯
* 预算
* 数据回溯
* 指标管理
* 接口管理
* 元数据管理
* 权限管理 


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
│  ├──conf---------------项目环境配置文件
│  │   ├─beta ------测试环境配置文件
│  │   ├─prd -------生产环境配置文件
│  │
│  ├──java
│  │   │
│  │   └─com.yxb.cms----------------项目主代码
│  │             │
│  │             ├─architect----------------项目装配目录
│  │             │    │
│  │             │    ├─conf----------------项目配置路径
│  │             │    │
│  │             │    ├─constant------------项目常量枚举路径
│  │             │    │
│  │             │    ├─filter--------------项目过滤器路径
│  │             │    │
│  │             │    ├─interceptor---------项目拦截器路径
│  │             │    │
│  │             │    ├─properties----------项目属性文件读取路径
│  │             │    │
│  │             │    ├─realm---------------自定义shiroRealm路径
│  │             │    │
│  │             │    ├─utils----------------项目帮助类路径
│  │             │
│  │             ├─controller----------------项目controller路径
│  │             │
│  │             ├─dao-----------------------项目dao层路径
│  │             │
│  │             ├─domain--------------------项目domain层路径
│  │             │
│  │             ├─model--------------------项目data model路径
│  │             │
│  │             ├─service-------------------项目service层路径
│  │             │
│  │             └─Application ------springBoot 启动类
│  │
│  ├─resources----------------项目资源文件
│  │     │
│  │     ├─mapper----------------mybatis映射文件路径
│  │     │
│  │     ├─application.properties-----springBoot核心配置
│  │     │
│  │     ├─ehcache-shiro.xml----shiro缓存配置
│  │     │
│  │     ├─log4j.properties------log4j配置
│  │     │
│  │     ├─mybatis-config.xml-------mybatis配置
│  │
│  │
│  └─webapp----------------web页面和静态资源存放的目录 （纯后端项目不需要此package）

│
│
```