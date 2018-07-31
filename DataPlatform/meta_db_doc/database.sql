-- 元数据模块数据库设计

-- meta_db
create table meta_db (db_id int primary key not null auto_increment comment '数据库id', db_eng_name String comment '数据库英文名',
db_chin_name String comment '数据库中文名', db_comment String comment '数据库注释');

-- meta_table
create table meta_table (tb_id int primary key not null auto_increment comment '数据表id', db_id int comment '数据表归属数据库id',
tb_eng_name String comment '数据表英文名字', tb_chin_name String  commnet '数据表中文名字', tb_manager String comment '数据表负责人',
tb_comment String commnet '数据表注释', tb_type String comment '数据表类型', tb_source String comment '数据源',
tb_compress String comment '数据表压缩格式', tb_hdfs_path String comment '数据表hdfs存储路径', etl_path String comment 'ETL程序路径',
tb_start_time String comment '数据表开始时间', tb_update_time String comment '数据表更新时间', tb_label String comment '标签',
tb_statement String comment '建表语句', tb_rela_index String comment '关联指标', tb_status String comment '状态');

-- meta_column
create table meta_column (col_id int primary key not null auto_increment comment '字段的id', tb_id int comment '数据表的id', col_eng_name String comment '字段的英文名字',
col_chin_name String comment '字段的中文名字', col_type String comment '字段的类型', col_comment String comment '字段的注释')

-- meta_tb_dependent
create table meta_tb_dependent (dep_id int primary key not null auto_increment comment '依赖id', tb_id int comment '数据表id', tb_dependent_id int comment '数据表依赖的数据表的id')

-- meta_tb_demander
create table meta_tb_demander (demander_id int primary key not null auto_increment comment '需求id', demander_user_id tamestamp comment '用户id')

-- meta_tb_apply
create table meta_tb_apply (apply_id int primary key not null auto_increment comment '申请id', tb_id int comment '数据表的id', user_id timestamp comment '用户id')



