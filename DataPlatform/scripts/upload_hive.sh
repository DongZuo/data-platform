#!/bin/bash
cmd=$1
hiveDB=$2
hiveTable=$3
path=$4
log=$5

rm $log

hive -e "use $hiveDB" 2>&1| tee $log
echo "use database $hiveDB" >> $log
hive -e "drop table $hiveDB.$hiveTable" 2>&1| tee -a $log
echo "old table $hiveDB.$hiveTable dropped" >> $log
hive -e "$cmd" 2>&1| tee -a $log
echo "table $hiveDB.$hiveTable created " >> $log
hive -e "LOAD DATA LOCAL INPATH '$path' OVERWRITE INTO TABLE $hiveDB.$hiveTable" 2>&1| tee -a $log
echo "csv data file loaded, upload_task finished" >> $log
sleep 20s
rm $path
