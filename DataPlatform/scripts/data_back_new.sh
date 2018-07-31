#!/bin/bash
#
# Author: liyunxiang
# CreateTime: 2018-06-26
# UpdateTime: 
# Function: 
#   用于追溯数据的脚本       

help() {
  cat <<EOF
Usage: sh data_back.sh [OPTION]

options:
    -s  起始时间[必填]
    -e  结束时间[必填]
    -t  表名[必填]
    -d  库名[必填]
    -p  SQL所在路径[必填]
    -h  显示帮助

description:
   该脚本用来追溯历史数据
      指定需要追溯的表名(-t)
      指定要运行的sql所在的位置 (脚本中的变动的日期用{date}替代)
   该脚本和当前线上运行的python脚本兼容
EOF
}
                                                                                                                                                                            
while getopts :p:s:e:t:d:h opt
do
  case "$opt" in
    s) start_date="$OPTARG" ;;
    e) end_date="$OPTARG" ;;
    t) table="$OPTARG" ;;
    d) db="$OPTARG" ;;
    p) path="$OPTARG" ;;
    h) help
       exit 0 ;;
    *) echo "unknown option: $OPTARG" 
       exit -1 ;;
  esac
done

if [ -z "$start_date" ] || [ -z "$end_date" ] || [ -z "$table" ] || [ -z "$path" ] || [ -z "$db" ]
then
  echo "ERROR: Param Error."
  help
  exit -1
fi

hive_home=/software/servers/hive-1.1.0-cdh5.7.0
echo "参数设置如下："
echo "起始日期：  $start_date"
echo "结束日期：  $end_date"
echo "hive的表：  ${db}.$table"
echo "sql文件：   $path/${table}.sql"

start_ts=`date -d $start_date +%s`
end_ts=`date -d $end_date +%s`

if [ $end_ts -lt $start_ts ]
then
    echo "ERROR: EndDate < StartDate."
    exit 1
fi

start_time_total=$(date +"%Y-%m-%d %H:%M:%S")

current_ts=$start_ts
while (($current_ts<=$end_ts))
do
    dt=`date -d @$current_ts "+%Y-%m-%d"`
    # 替换脚本中的日期
    clearSql="alter table ${db}.$table drop partition (dt='$dt')"
    sql=$(cat "$path/$table.sql" | sed "s/{date}/$dt/g")
    sql+=";"
    echo "----------------------------开始执行 $dt ---------------------------------------------" 
    start_time=$(date +"%Y-%m-%d %H:%M:%S")
    echo "********************************"
    echo "开始时间： $start_time"
    echo "********************************"
    echo "$sql"
    echo "***********dt = $dt ************"
    echo "开始时间： $start_time"
    echo "********************************"
    # 清除分区
    echo $clearSql
    beeline -u "jdbc:hive2://ZW0804-hadoop-96:10000/default;" -n hdfs -p hdfs -e "$clearSql"
    # 执行sql
    #beeline -u "jdbc:hive2://ZW0804-hadoop-96:10000/default;" -n hdfs -p hdfs -e "$sql"
    hive -S -e "$sql"
    retcode=$?
    echo "return code: $retcode"
    if [ $retcode -ne 0 ]
    then
        exit 0
    fi
    end_time=$(date +"%Y-%m-%d %H:%M:%S")
    duration_time_sec=$(($(date +%s -d "$end_time") - $(date +%s -d "$start_time")))
    echo "----------------------------执行结束 $dt ---------------------------------------------" 
    echo 
    echo "*********************************"
    echo "开始时间： $start_time"
    echo "结束时间： $end_time"
    echo "用时: $duration_time_sec 秒" 
    echo "*********************************"
    ((current_ts+=86400))
done

end_time_total=$(date +"%Y-%m-%d %H:%M:%S")
duration_time_total_sec=$(($(date +%s -d "$end_time_total") - $(date +%s -d "$start_time_total")))
echo "*********************************"
echo "参数设置如下："
echo "起始日期：  $start_date"
echo "结束日期：  $end_date"
echo "hive的表：  $table"
echo "sql文件：   $path/$table.sql"
echo 
echo "循环开始时间： $start_time_total"
echo "循环结束时间： $end_time_total"
echo "总共用时: $duration_time_total_sec 秒" 
echo "*********************************"
sleep 10s
rm /tmp/backtrack/hive_$table#$start_date#$end_date
