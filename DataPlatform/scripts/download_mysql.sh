#!/bin/bash
domain=$1
username=$2
password=$3
db=$4
table=$5
filename=$6
log=$7
echo "download_task started" > $log
(mysql -h "$domain" -u$username -p$password "$db" -B -e "select * from \`$table\`;" | sed "s/'/\'/;s/\t/\",\"/g;s/^/\"/;s/$/\"/;s/\n//g" > /tmp/dp/download/result/$filename)2>>$log
echo "download_task finished" >> $log
sleep 2m
rm $log
