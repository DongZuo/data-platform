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


Instruction
-----------------------------------

* Create database and tables: execute db.sql
* Change the configuration in jdbc.properties file
* Change application.properties 
* Start command : spring-boot:run 
* Scripts path : DataPlatform/scripts
* Because of the operation with Hive/HDFS, the server need to be started using hdfs user. 


Project Structure
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
