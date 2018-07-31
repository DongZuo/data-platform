#!/bin/bash
dbName=$1
tableName=$2
hiveDB=$3
hiveTable=$4
rm /tmp/sqoop/sqoop_$hiveTable.log

hive -e "use $hiveDB" 2>&1| tee /tmp/sqoop/sqoop_$hiveTable.log
echo "Use hive database $hiveDB" >> /tmp/sqoop/sqoop_$hiveTable.log
hive -e "unlock table $hiveTable" 2>&1| tee -a /tmp/sqoop/sqoop_$hiveTable.log
echo "Unlock hive table $hiveDB" >> /tmp/sqoop/sqoop_$hiveTable.log
hive -e "drop table $hiveTable" 2>&1| tee -a /tmp/sqoop/sqoop_$hiveTable.log
echo "Drop old hive table $hiveDB" >> /tmp/sqoop/sqoop_$hiveTable.log
hadoop dfs -rmr /user/data_user/$hiveDB.db/$hiveTable 2>&1| tee -a /tmp/sqoop/sqoop_$hiveTable.log
hadoop dfs -rmr /user/hdfs/$hiveTable 2>&1| tee -a /tmp/sqoop/sqoop_$hiveTable.log
echo "HDFS old data removed" >> /tmp/sqoop/sqoop_$hiveTable.log
echo "Sqoop loading task starting"
sqoop import --connect jdbc:mysql://192.168.1.48:3306/$dbName?zeroDateTimeBehavior=convertToNull --username rrd_sjlc --password Vpov9BBvxReqCZQxw3YS --hive-database $hiveDB --table $tableName --hive-import --hive-table $hiveTable -m 1 -- --default-character-set=utf-8 2>&1| tee -a /tmp/sqoop/sqoop_$hiveTable.log
echo "sqoop_task finished" >> /tmp/sqoop/sqoop_$hiveTable.log
sleep 10s 
rm /tmp/sqoop/sqoop_$hiveTable
