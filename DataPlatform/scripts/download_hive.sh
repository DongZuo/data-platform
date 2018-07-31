#!/bin/bash
cmd=$1
log=$2
dir=$3
csv=$4

hive -e "$cmd" 2>&1| tee $log
cat $dir/* > $csv 2>&1| tee -a $log
rm -r $dir
echo "Convert Hive table to CSV file : $csv" >> $log
echo "download_task finished" >> $log
sleep 2m
rm $log
