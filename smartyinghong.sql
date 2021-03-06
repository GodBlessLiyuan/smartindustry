SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS am_m_user_authority;
DROP TABLE IF EXISTS am_role_authority;
DROP TABLE IF EXISTS am_authority;
DROP TABLE IF EXISTS am_dept_record;
DROP TABLE IF EXISTS am_role_record;
DROP TABLE IF EXISTS am_user_record;
DROP TABLE IF EXISTS om_outbound_body;
DROP TABLE IF EXISTS si_location_record;
DROP TABLE IF EXISTS sm_storage_body;
DROP TABLE IF EXISTS sm_storage_detail;
DROP TABLE IF EXISTS si_location;
DROP TABLE IF EXISTS si_material_inventory;
DROP TABLE IF EXISTS si_material_record;
DROP TABLE IF EXISTS wo_slurry_material;
DROP TABLE IF EXISTS si_material;
DROP TABLE IF EXISTS dd_measure_unit;
DROP TABLE IF EXISTS si_warehouse_record;
DROP TABLE IF EXISTS sm_storage_forklift;
DROP TABLE IF EXISTS sm_storage_record;
DROP TABLE IF EXISTS sm_storage_head;
DROP TABLE IF EXISTS si_warehouse;
DROP TABLE IF EXISTS dd_warehouse_type;
DROP TABLE IF EXISTS om_outbound_record;
DROP TABLE IF EXISTS si_client_record;
DROP TABLE IF EXISTS si_forklift_record;
DROP TABLE IF EXISTS si_supplier_record;
DROP TABLE IF EXISTS si_supplier;
DROP TABLE IF EXISTS am_user;
DROP TABLE IF EXISTS am_dept;
DROP TABLE IF EXISTS am_role;
DROP TABLE IF EXISTS dd_location_type;
DROP TABLE IF EXISTS om_outbound_forklift;
DROP TABLE IF EXISTS om_outbound_head;
DROP TABLE IF EXISTS si_client;
DROP TABLE IF EXISTS si_forklift;
DROP TABLE IF EXISTS wo_slurry_order;




/* Create Tables */

-- 权限表
CREATE TABLE am_authority
(
	authority_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '权限ID',
	authority_name char(255) NOT NULL COMMENT '权限名称',
	authority_path char(255) NOT NULL COMMENT '权限路径',
	-- 1：菜单   2：按钮
	type tinyint COMMENT '类型 : 1：菜单   2：按钮',
	parent_id bigint unsigned COMMENT '上级权限ID',
	PRIMARY KEY (authority_id),
	UNIQUE (authority_id),
	UNIQUE (authority_path)
) COMMENT = '权限表';


-- 部门表
CREATE TABLE am_dept
(
	dept_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
	parent_id bigint unsigned COMMENT '上级部门id',
	dept_code char(255) COMMENT '部门代码',
	dept_name char(32) NOT NULL COMMENT '部门名称',
	user_id bigint unsigned COMMENT '部门负责人',
	dept_desc char(255) COMMENT '部门描述',
	-- 1 启动
	-- 2 禁用
	status tinyint COMMENT '状态 : 1 启动
2 禁用',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (dept_id),
	UNIQUE (dept_id)
) COMMENT = '部门表';


-- 部门记录表
CREATE TABLE am_dept_record
(
	dept_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '部门记录ID',
	dept_id bigint unsigned NOT NULL COMMENT '部门id',
	user_id bigint unsigned NOT NULL COMMENT '用户ID',
	create_time datetime COMMENT '操作时间',
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (dept_record_id),
	UNIQUE (dept_record_id)
) COMMENT = '部门记录表';


-- 用户权限中间表
CREATE TABLE am_m_user_authority
(
	user_authority_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户权限ID',
	user_id bigint unsigned NOT NULL COMMENT '用户ID',
	authority_id bigint unsigned NOT NULL COMMENT '权限ID',
	PRIMARY KEY (user_authority_id),
	UNIQUE (user_authority_id)
) COMMENT = '用户权限中间表';


-- 角色表
CREATE TABLE am_role
(
	role_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
	role_name char(32) NOT NULL COMMENT '角色名称',
	role_desc char(255) COMMENT '角色描述',
	-- 1：启用
	-- 2：禁用
	status tinyint COMMENT '状态 : 1：启用
2：禁用',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	role_code char(255) COMMENT '角色编码',
	PRIMARY KEY (role_id),
	UNIQUE (role_id)
) COMMENT = '角色表';


-- 角色权限表
CREATE TABLE am_role_authority
(
	role_authority_id bigint NOT NULL AUTO_INCREMENT COMMENT '角色菜单id',
	role_id bigint unsigned NOT NULL COMMENT '角色ID',
	authority_id bigint unsigned NOT NULL COMMENT '权限ID',
	PRIMARY KEY (role_authority_id),
	UNIQUE (role_authority_id)
) COMMENT = '角色权限表';


-- 角色记录表
CREATE TABLE am_role_record
(
	role_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '角色记录ID',
	role_id bigint unsigned NOT NULL COMMENT '角色ID',
	user_id bigint unsigned NOT NULL COMMENT '用户ID',
	create_time datetime COMMENT '操作时间',
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (role_record_id)
) COMMENT = '角色记录表';


-- 用户表
CREATE TABLE am_user
(
	user_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
	user_code char(255) COMMENT '用户编码',
	name char(32) NOT NULL COMMENT '用户姓名',
	-- 1 男
	-- 2 女
	sex tinyint COMMENT '性别 : 1 男
2 女',
	dept_id bigint unsigned NOT NULL COMMENT '部门id',
	username char(32) NOT NULL COMMENT '登录名',
	password char(128) NOT NULL COMMENT '密码',
	role_id bigint unsigned NOT NULL COMMENT '角色ID',
	job char(16) COMMENT '所属岗位',
	phone char(16) COMMENT '电话号码',
	email char(255) COMMENT '用户邮箱',
	-- 1 启用
	-- 2 禁用
	status tinyint COMMENT '状态 : 1 启用
2 禁用',
	remark char(255) COMMENT '备注',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (user_id),
	UNIQUE (user_id)
) COMMENT = '用户表';


-- 用户操作记录表
CREATE TABLE am_user_record
(
	user_record_id bigint NOT NULL AUTO_INCREMENT COMMENT '用户记录ID',
	user_id bigint unsigned NOT NULL COMMENT '用户ID',
	operate_id bigint unsigned NOT NULL COMMENT '操作人ID',
	create_time datetime COMMENT '操作时间',
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (user_record_id),
	UNIQUE (user_record_id),
	UNIQUE (operate_id)
) COMMENT = '用户操作记录表';


-- 货位类型
CREATE TABLE dd_location_type
(
	location_type_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '货位类型ID',
	location_type_name char(255) NOT NULL COMMENT '货位类型名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (location_type_id),
	UNIQUE (location_type_id)
) COMMENT = '货位类型';


-- 计量单位
CREATE TABLE dd_measure_unit
(
	measure_unit_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '计量单位ID',
	measure_unit_name char(255) NOT NULL COMMENT '计量单位名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (measure_unit_id),
	UNIQUE (measure_unit_id)
) COMMENT = '计量单位';


-- 仓库类型表
CREATE TABLE dd_warehouse_type
(
	warehouse_type_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '仓库类型ID',
	warehouse_type_name char(64) NOT NULL COMMENT '仓库类型名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (warehouse_type_id),
	UNIQUE (warehouse_type_id)
) COMMENT = '仓库类型表';


-- 出库单表体
CREATE TABLE om_outbound_body
(
	outbound_body_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '出库单表体id',
	outbound_head_id bigint unsigned NOT NULL COMMENT '出库单表头ID',
	material_id bigint unsigned NOT NULL COMMENT '物料ID',
	expect_num decimal(10,2) COMMENT '期望出库数量',
	outbound_num decimal(10,2) COMMENT '已出库数量',
	create_time datetime COMMENT '创建时间',
	outbound_time datetime COMMENT '出库时间',
	-- 1 未删除
	-- 2 已删除
	dr tinyint COMMENT '是否删除 : 1 未删除
2 已删除',
	unit_price decimal(10,2) COMMENT '单价',
	total_price decimal(10,2) COMMENT '金额',
	remark char(255) COMMENT '备注',
	PRIMARY KEY (outbound_body_id),
	UNIQUE (outbound_body_id)
) COMMENT = '出库单表体';


-- 出库叉车表
CREATE TABLE om_outbound_forklift
(
	outbound_forklift_id bigint NOT NULL AUTO_INCREMENT COMMENT '出库叉车id',
	outbound_head_id bigint unsigned NOT NULL COMMENT '出库单表头ID',
	forklift_id bigint unsigned NOT NULL COMMENT '叉车id',
	rfid char(128) COMMENT 'RFID',
	PRIMARY KEY (outbound_forklift_id),
	UNIQUE (outbound_forklift_id)
) COMMENT = '出库叉车表';


-- 出库单表头
CREATE TABLE om_outbound_head
(
	outbound_head_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '出库单表头ID',
	outbound_no char(128) NOT NULL COMMENT '出库单编码',
	source_no char(128) COMMENT '来源单号',
	-- 1 混料工单
	-- 2 生产工单
	-- 3 销售出库
	-- 4 备料区出库
	source_type tinyint COMMENT '来源类型 : 1 混料工单
2 生产工单
3 销售出库
4 备料区出库',
	client_no char(128) COMMENT '客户编码',
	plan_time date COMMENT '计划出库时间',
	outbound_time datetime COMMENT '完成出库时间',
	expect_num decimal(10,2) COMMENT '期望出库数',
	outbound_num decimal(10,2) COMMENT '已经出库数量',
	extra char(255) COMMENT '备注',
	-- 1：已出库
	-- 2：出库中
	-- 3：待出库
	status tinyint COMMENT '状态 : 1：已出库
2：出库中
3：待出库',
	accept_address char(255) COMMENT '接受地址',
	create_time datetime COMMENT '创建时间',
	sales_id bigint COMMENT '开票员ID',
	sales_date datetime COMMENT '开票时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (outbound_head_id),
	UNIQUE (outbound_head_id)
) COMMENT = '出库单表头';


-- 出库操作记录
CREATE TABLE om_outbound_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '操作记录',
	outbound_head_id bigint unsigned COMMENT '出库单表头ID',
	user_id bigint unsigned COMMENT '操作人ID',
	operation_name char(255) COMMENT '操作名称',
	create_time datetime COMMENT '操作时间',
	PRIMARY KEY (record_id),
	UNIQUE (record_id)
) COMMENT = '出库操作记录';


-- 客户
CREATE TABLE si_client
(
	client_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '客户id',
	client_no char(128) NOT NULL COMMENT '客户代码',
	client_type_id bigint unsigned NOT NULL COMMENT '客户类型ID',
	client_name char(64) NOT NULL COMMENT '客户名称',
	contact char(64) NOT NULL COMMENT '联系人',
	-- 1：男
	-- 2：女
	sex tinyint COMMENT '联系人性别 : 1：男
2：女',
	telephone char(255) COMMENT '电话号码',
	phone char(16) NOT NULL COMMENT '联系电话',
	email char(255) COMMENT '客户邮箱',
	fax char(16) COMMENT '传真',
	url char(64) COMMENT '网址',
	credit_level_id bigint unsigned COMMENT '信用等级id',
	area char(255) COMMENT '地区',
	address char(128) COMMENT '详细地址',
	remark char(255) COMMENT '备注',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1 未删除
	-- 2 已删除
	dr tinyint COMMENT '是否删除 : 1 未删除
2 已删除',
	PRIMARY KEY (client_id),
	UNIQUE (client_id)
) COMMENT = '客户';


-- 客户操作记录表
CREATE TABLE si_client_record
(
	client_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '操作记录ID',
	client_id bigint unsigned NOT NULL COMMENT '客户id',
	user_id bigint unsigned NOT NULL COMMENT '用户ID',
	create_time datetime COMMENT '操作时间',
	type char(255) COMMENT '操作类型',
	PRIMARY KEY (client_record_id),
	UNIQUE (client_record_id),
	UNIQUE (user_id)
) COMMENT = '客户操作记录表';


-- 叉车信息
CREATE TABLE si_forklift
(
	forklift_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '叉车id',
	forklift_no char(128) COMMENT '叉车编号',
	forklift_name char(64) COMMENT '叉车名称',
	forklift_model char(255) COMMENT '叉车型号',
	forklift_brand char(255) COMMENT '品牌',
	contact char(64) COMMENT '联系人',
	contact_phone char(16) COMMENT '联系电话',
	imei_no char(255) COMMENT '工业一体机号',
	-- 1 原材料区
	-- 2 成品入库区
	-- 3 成品出库区
	work_area tinyint COMMENT '作业区域 : 1 原材料区
2 成品入库区
3 成品出库区',
	supplier_name char(64) COMMENT '供应商名称',
	-- 1 忙碌中
	-- 2 空闲中
	--  
	status tinyint COMMENT '当前状态 : 1 忙碌中
2 空闲中
 ',
	extra char(255) COMMENT '备注',
	create_time datetime COMMENT '创建时间',
	-- 1 未删除
	-- 2 已删除
	dr tinyint COMMENT '是否删除 : 1 未删除
2 已删除',
	PRIMARY KEY (forklift_id),
	UNIQUE (forklift_id)
) COMMENT = '叉车信息';


-- 叉车操作记录表
CREATE TABLE si_forklift_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '操作记录id',
	forklift_id bigint unsigned NOT NULL COMMENT '叉车id',
	user_id bigint unsigned NOT NULL COMMENT '操作人ID',
	operation_name char(255) COMMENT '操作名称',
	create_time datetime COMMENT '操作时间',
	PRIMARY KEY (record_id),
	UNIQUE (record_id),
	UNIQUE (forklift_id),
	UNIQUE (user_id)
) COMMENT = '叉车操作记录表';


-- 库位表
CREATE TABLE si_location
(
	location_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '库位ID',
	warehouse_id bigint unsigned NOT NULL COMMENT '仓库ID',
	material_id bigint unsigned COMMENT '物料ID',
	location_type_id bigint unsigned NOT NULL COMMENT '货位类型ID',
	user_id bigint unsigned COMMENT '创建人',
	location_no char(128) NOT NULL COMMENT '库位编号',
	location_name char(255) NOT NULL COMMENT '库位名称',
	hold_tray_num decimal(10,2) COMMENT '可容纳托盘数',
	-- 现存数量
	exist_num decimal(10,2) COMMENT '现存数量 : 现存数量',
	remark char(255) COMMENT '备注',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (location_id),
	UNIQUE (location_id),
	UNIQUE (location_type_id)
) COMMENT = '库位表';


-- 库位记录表
CREATE TABLE si_location_record
(
	location_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '库位记录ID',
	location_id bigint unsigned NOT NULL COMMENT '库位ID',
	user_id bigint unsigned NOT NULL COMMENT '操作人',
	create_time datetime COMMENT '操作时间',
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (location_record_id),
	UNIQUE (location_record_id)
) COMMENT = '库位记录表';


-- 物料表
CREATE TABLE si_material
(
	material_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料ID',
	material_no char(128) NOT NULL COMMENT '物料编号',
	-- 1：原材料
	-- 2：成品
	material_type tinyint COMMENT '物料类型 : 1：原材料
2：成品',
	material_name char(64) NOT NULL COMMENT '物料名称',
	material_level char(64) COMMENT '物料等级',
	material_model char(64) COMMENT '规格型号',
	measure_unit_id bigint unsigned COMMENT '计量单位ID',
	package_volume decimal(10,2) COMMENT '包装体积',
	supplier_id bigint unsigned COMMENT '供应商ID',
	material_desc char(255) COMMENT '物料描述',
	material_batch char(64) COMMENT '物料批次',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	price decimal(10,2) COMMENT '单价',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (material_id),
	UNIQUE (material_id)
) COMMENT = '物料表';


-- 物料库存信息表
CREATE TABLE si_material_inventory
(
	material_inventory_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料库存信息ID',
	material_id bigint unsigned NOT NULL COMMENT '物料ID',
	lower_limit decimal(10,2) COMMENT '库存下限',
	upper_limit decimal(10,2) COMMENT '库存上限',
	PRIMARY KEY (material_inventory_id),
	UNIQUE (material_inventory_id),
	UNIQUE (material_id)
) COMMENT = '物料库存信息表';


-- 物料记录表
CREATE TABLE si_material_record
(
	material_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料记录ID',
	material_id bigint unsigned NOT NULL COMMENT '物料ID',
	user_id bigint unsigned NOT NULL COMMENT '操作人ID',
	create_time datetime COMMENT '操作时间',
	type char(255) COMMENT '操作类型',
	PRIMARY KEY (material_record_id),
	UNIQUE (material_record_id),
	UNIQUE (material_id),
	UNIQUE (user_id)
) COMMENT = '物料记录表';


-- 供应商表
CREATE TABLE si_supplier
(
	supplier_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
	supplier_no char(64) NOT NULL COMMENT '供应商编码',
	supplier_name char(64) NOT NULL COMMENT '供应商名称',
	contact_name char(64) COMMENT '联系人名称',
	phone char(16) COMMENT '联系电话',
	fax char(16) COMMENT '传真',
	site char(64) COMMENT '网址',
	mail char(64) COMMENT '邮箱',
	area char(64) COMMENT '地区',
	address char(128) COMMENT '详细地址',
	remark char(255) COMMENT '备注',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (supplier_id),
	UNIQUE (supplier_id)
) COMMENT = '供应商表';


-- 供应商记录表
CREATE TABLE si_supplier_record
(
	supplier_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '供应商记录ID',
	supplier_id bigint unsigned NOT NULL COMMENT '供应商ID',
	user_id bigint unsigned NOT NULL COMMENT '操作人',
	create_time datetime COMMENT '操作时间',
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (supplier_record_id),
	UNIQUE (supplier_record_id)
) COMMENT = '供应商记录表';


-- 仓库表
CREATE TABLE si_warehouse
(
	warehouse_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
	warehouse_no char(128) NOT NULL COMMENT '仓库编号',
	warehouse_name char(64) NOT NULL COMMENT '仓库名称',
	warehouse_type_id bigint unsigned NOT NULL COMMENT '仓库类型ID',
	principal char(32) COMMENT '负责人',
	phone char(16) COMMENT '联系电话',
	area char(64) COMMENT '地区',
	address char(128) COMMENT '详细地址',
	remark char(255) COMMENT '备注',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (warehouse_id),
	UNIQUE (warehouse_id)
) COMMENT = '仓库表';


-- 仓库记录表
CREATE TABLE si_warehouse_record
(
	warehouse_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '仓库记录表',
	warehouse_id bigint unsigned NOT NULL COMMENT '仓库ID',
	user_id bigint unsigned NOT NULL COMMENT '操作人',
	create_time datetime COMMENT '操作时间',
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (warehouse_record_id),
	UNIQUE (warehouse_record_id)
) COMMENT = '仓库记录表';


-- 入库单表体
CREATE TABLE sm_storage_body
(
	storage_body_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '入库单表体ID',
	storage_head_id bigint unsigned NOT NULL COMMENT '入库单表头ID',
	material_id bigint unsigned COMMENT '物料ID',
	location_id bigint unsigned COMMENT '库位ID',
	car_brand  char(255) COMMENT '车牌信息',
	expect_num decimal(10,2) COMMENT '期望入库数量',
	storage_num decimal(10,2) COMMENT '已经入库数量',
	accept_time datetime COMMENT '接受日期',
	create_time datetime COMMENT '创建时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	unit_price decimal(10,2) COMMENT '单价',
	sum_price decimal(10,2) COMMENT '金额',
	unit_price_notax decimal(10,2) COMMENT '不含税单价',
	sum_price_notax decimal(10,2) COMMENT '不含税金额',
	PRIMARY KEY (storage_body_id),
	UNIQUE (storage_body_id)
) COMMENT = '入库单表体';


-- 入库详细表
CREATE TABLE sm_storage_detail
(
	storage_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '入库单ID',
	storage_head_id bigint unsigned COMMENT '入库单表头ID',
	location_id bigint unsigned COMMENT '库位ID',
	material_id bigint unsigned COMMENT '物料ID',
	storage_num decimal(10,2) COMMENT '入库数',
	storage_time datetime COMMENT '入库时间',
	rfid char(128) COMMENT '栈板RFID',
	-- 1 在库
	-- 
	-- 2 销售出库
	-- 
	-- 3 待入库
	-- 
	-- 4 已出库
	storage_status tinyint COMMENT '入库状态 : 1 在库

2 销售出库

3 待入库

4 已出库',
	-- 1 不是备料区
	-- 2 在备料区
	preparation tinyint COMMENT '是否备料区 : 1 不是备料区
2 在备料区',
	PRIMARY KEY (storage_id),
	UNIQUE (storage_id)
) COMMENT = '入库详细表';


-- 入库叉车表
CREATE TABLE sm_storage_forklift
(
	storage_forklift_id bigint NOT NULL AUTO_INCREMENT COMMENT '入库叉车id',
	storage_head_id bigint unsigned COMMENT '入库单表头ID',
	forklift_id bigint unsigned COMMENT '叉车id',
	rfid char(128) COMMENT '当前运作的RFID',
	PRIMARY KEY (storage_forklift_id),
	UNIQUE (storage_forklift_id)
) COMMENT = '入库叉车表';


-- 入库单表头
CREATE TABLE sm_storage_head
(
	storage_head_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '入库单表头ID',
	warehouse_id bigint unsigned COMMENT '仓库ID',
	supplier_id bigint unsigned NOT NULL COMMENT '供应商ID',
	storage_no char(128) COMMENT '入库单编号',
	source_no char(128) COMMENT '来源单号',
	-- 1 原材料入库
	-- 2 生产入库
	-- 3 备料区入库
	source_type tinyint COMMENT '来源类型 : 1 原材料入库
2 生产入库
3 备料区入库',
	storage_time datetime COMMENT '入库时间',
	expect_num decimal(10,2) COMMENT '期望入库数',
	storage_num decimal(10,2) COMMENT '已入库数量',
	remark char(255) COMMENT '备注',
	-- 1：已入库
	-- 2：入库中
	-- 3：待入库
	-- 
	-- 
	status tinyint COMMENT '入库状态 : 1：已入库
2：入库中
3：待入库

',
	create_time datetime COMMENT '创建时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	-- 1. 现金
	-- 2. 欠款
	pay_method tinyint COMMENT '付款方式 : 1. 现金
2. 欠款',
	PRIMARY KEY (storage_head_id),
	UNIQUE (storage_head_id),
	UNIQUE (supplier_id)
) COMMENT = '入库单表头';


-- 入库操作记录表
CREATE TABLE sm_storage_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '操作记录',
	storage_head_id bigint unsigned COMMENT '入库单表头ID',
	user_id bigint unsigned COMMENT '操作人ID',
	operation_name char(255) COMMENT '操作名称',
	create_time datetime COMMENT '操作时间',
	PRIMARY KEY (record_id),
	UNIQUE (record_id)
) COMMENT = '入库操作记录表';


-- 料浆原材料
CREATE TABLE wo_slurry_material
(
	slurry_material_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '料浆原材料ID',
	slurry_id bigint unsigned NOT NULL COMMENT '料浆工单ID',
	material_id bigint unsigned NOT NULL COMMENT '物料ID',
	need_num decimal(10,2) COMMENT '需求量',
	plan_num decimal(10,2) COMMENT '计划出库数量',
	PRIMARY KEY (slurry_material_id),
	UNIQUE (slurry_material_id),
	UNIQUE (material_id)
) COMMENT = '料浆原材料';


-- 料浆制作工单
CREATE TABLE wo_slurry_order
(
	slurry_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '料浆工单ID',
	slurry_no char(255) COMMENT '工单编号',
	plan_date date COMMENT '计划制作日期',
	-- 1. 待下发
	-- 2. 下发中
	-- 3. 待完成
	-- 4. 已完成
	status tinyint COMMENT '状态 : 1. 待下发
2. 下发中
3. 待完成
4. 已完成',
	finish_time datetime COMMENT '完成时间',
	remark char(255) COMMENT '备注',
	create_time datetime COMMENT '创建时间',
	creator bigint COMMENT '创建人',
	finisher bigint COMMENT '完成人',
	update_time datetime COMMENT '更新时间',
	-- 1 未删除
	-- 2 已删除
	dr tinyint COMMENT '是否删除 : 1 未删除
2 已删除',
	PRIMARY KEY (slurry_id),
	UNIQUE (slurry_id)
) COMMENT = '料浆制作工单';



/* Create Foreign Keys */

ALTER TABLE am_authority
	ADD FOREIGN KEY (parent_id)
	REFERENCES am_authority (authority_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_m_user_authority
	ADD FOREIGN KEY (authority_id)
	REFERENCES am_authority (authority_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_role_authority
	ADD FOREIGN KEY (authority_id)
	REFERENCES am_authority (authority_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_dept
	ADD FOREIGN KEY (parent_id)
	REFERENCES am_dept (dept_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_dept_record
	ADD FOREIGN KEY (dept_id)
	REFERENCES am_dept (dept_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_user
	ADD FOREIGN KEY (dept_id)
	REFERENCES am_dept (dept_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_role_authority
	ADD FOREIGN KEY (role_id)
	REFERENCES am_role (role_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_role_record
	ADD FOREIGN KEY (role_id)
	REFERENCES am_role (role_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_user
	ADD FOREIGN KEY (role_id)
	REFERENCES am_role (role_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_dept_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_m_user_authority
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_role_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_user_record
	ADD FOREIGN KEY (operate_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE am_user_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_measure_unit
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_warehouse_type
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_outbound_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_client_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_forklift_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_location
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_location_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_supplier
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_supplier_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_warehouse
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_warehouse_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_record
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_location
	ADD FOREIGN KEY (location_type_id)
	REFERENCES dd_location_type (location_type_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (measure_unit_id)
	REFERENCES dd_measure_unit (measure_unit_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_warehouse
	ADD FOREIGN KEY (warehouse_type_id)
	REFERENCES dd_warehouse_type (warehouse_type_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_outbound_body
	ADD FOREIGN KEY (outbound_head_id)
	REFERENCES om_outbound_head (outbound_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_outbound_forklift
	ADD FOREIGN KEY (outbound_head_id)
	REFERENCES om_outbound_head (outbound_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_outbound_record
	ADD FOREIGN KEY (outbound_head_id)
	REFERENCES om_outbound_head (outbound_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_client_record
	ADD FOREIGN KEY (client_id)
	REFERENCES si_client (client_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_outbound_forklift
	ADD FOREIGN KEY (forklift_id)
	REFERENCES si_forklift (forklift_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_forklift_record
	ADD FOREIGN KEY (forklift_id)
	REFERENCES si_forklift (forklift_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_forklift
	ADD FOREIGN KEY (forklift_id)
	REFERENCES si_forklift (forklift_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_location_record
	ADD FOREIGN KEY (location_id)
	REFERENCES si_location (location_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_body
	ADD FOREIGN KEY (location_id)
	REFERENCES si_location (location_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_detail
	ADD FOREIGN KEY (location_id)
	REFERENCES si_location (location_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_outbound_body
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_location
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material_inventory
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material_record
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_body
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_detail
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE wo_slurry_material
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (supplier_id)
	REFERENCES si_supplier (supplier_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_supplier_record
	ADD FOREIGN KEY (supplier_id)
	REFERENCES si_supplier (supplier_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_head
	ADD FOREIGN KEY (supplier_id)
	REFERENCES si_supplier (supplier_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_location
	ADD FOREIGN KEY (warehouse_id)
	REFERENCES si_warehouse (warehouse_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_warehouse_record
	ADD FOREIGN KEY (warehouse_id)
	REFERENCES si_warehouse (warehouse_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_head
	ADD FOREIGN KEY (warehouse_id)
	REFERENCES si_warehouse (warehouse_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_body
	ADD FOREIGN KEY (storage_head_id)
	REFERENCES sm_storage_head (storage_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_detail
	ADD FOREIGN KEY (storage_head_id)
	REFERENCES sm_storage_head (storage_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_forklift
	ADD FOREIGN KEY (storage_head_id)
	REFERENCES sm_storage_head (storage_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE sm_storage_record
	ADD FOREIGN KEY (storage_head_id)
	REFERENCES sm_storage_head (storage_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE wo_slurry_material
	ADD FOREIGN KEY (slurry_id)
	REFERENCES wo_slurry_order (slurry_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



