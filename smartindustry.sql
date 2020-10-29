SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS am_m_user_authority;
DROP TABLE IF EXISTS am_role_authority;
DROP TABLE IF EXISTS wm_work_bench;
DROP TABLE IF EXISTS am_authority;
DROP TABLE IF EXISTS am_dept_record;
DROP TABLE IF EXISTS am_role_record;
DROP TABLE IF EXISTS am_user_record;
DROP TABLE IF EXISTS si_forklift_record;
DROP TABLE IF EXISTS si_forklift;
DROP TABLE IF EXISTS om_mix_body;
DROP TABLE IF EXISTS om_outbound_body;
DROP TABLE IF EXISTS si_material_attribute;
DROP TABLE IF EXISTS si_material_record;
DROP TABLE IF EXISTS si_material_specification;
DROP TABLE IF EXISTS sm_storage_body;
DROP TABLE IF EXISTS si_material;
DROP TABLE IF EXISTS si_supplier_record;
DROP TABLE IF EXISTS si_supplier;
DROP TABLE IF EXISTS dd_cert_status;
DROP TABLE IF EXISTS si_client_record;
DROP TABLE IF EXISTS si_client;
DROP TABLE IF EXISTS dd_client_type;
DROP TABLE IF EXISTS dd_credit_level;
DROP TABLE IF EXISTS dd_currency;
DROP TABLE IF EXISTS dd_humidity_level;
DROP TABLE IF EXISTS dd_life_cycle_state;
DROP TABLE IF EXISTS si_location_record;
DROP TABLE IF EXISTS si_location;
DROP TABLE IF EXISTS dd_location_type;
DROP TABLE IF EXISTS dd_material_level;
DROP TABLE IF EXISTS dd_material_lock;
DROP TABLE IF EXISTS dd_material_property;
DROP TABLE IF EXISTS dd_material_type;
DROP TABLE IF EXISTS dd_material_version;
DROP TABLE IF EXISTS dd_measure_unit;
DROP TABLE IF EXISTS dd_process;
DROP TABLE IF EXISTS dd_produce_loss_level;
DROP TABLE IF EXISTS dd_settle_period;
DROP TABLE IF EXISTS dd_supplier_group;
DROP TABLE IF EXISTS dd_supplier_type;
DROP TABLE IF EXISTS si_warehouse_record;
DROP TABLE IF EXISTS sm_storage_record;
DROP TABLE IF EXISTS sm_storage_head;
DROP TABLE IF EXISTS si_warehouse;
DROP TABLE IF EXISTS dd_warehouse_type;
DROP TABLE IF EXISTS om_outbound_record;
DROP TABLE IF EXISTS si_bom_record;
DROP TABLE IF EXISTS am_user;
DROP TABLE IF EXISTS am_dept;
DROP TABLE IF EXISTS am_role;
DROP TABLE IF EXISTS om_mix_head;
DROP TABLE IF EXISTS om_outbound_head;
DROP TABLE IF EXISTS si_config;




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


-- 认证状态表
CREATE TABLE dd_cert_status
(
	cert_status_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '认证状态ID',
	cert_status_name char(255) NOT NULL COMMENT '认证状态名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (cert_status_id),
	UNIQUE (cert_status_id)
) COMMENT = '认证状态表';


-- 客户类型
CREATE TABLE dd_client_type
(
	client_type_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '客户类型ID',
	client_type_name char(255) NOT NULL COMMENT '客户类型名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (client_type_id),
	UNIQUE (client_type_id)
) COMMENT = '客户类型';


-- 信用等级
CREATE TABLE dd_credit_level
(
	credit_level_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '信用等级id',
	credit_level_name char(255) COMMENT '信用等级名称',
	user_id bigint unsigned COMMENT '创建人ID',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (credit_level_id),
	UNIQUE (credit_level_id)
) COMMENT = '信用等级';


-- 币种
CREATE TABLE dd_currency
(
	currency_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '币种ID',
	currency_name char(255) NOT NULL COMMENT '币种名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (currency_id),
	UNIQUE (currency_id)
) COMMENT = '币种';


-- 湿度等级
CREATE TABLE dd_humidity_level
(
	humidity_level_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '湿度等级ID',
	humidity_level_name char(255) NOT NULL COMMENT '湿度等级名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (humidity_level_id),
	UNIQUE (humidity_level_id)
) COMMENT = '湿度等级';


-- 生命周期状态
CREATE TABLE dd_life_cycle_state
(
	life_cycle_state_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '生命周期状态ID',
	life_cycle_state_name char(255) NOT NULL COMMENT '生命周期状态名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (life_cycle_state_id),
	UNIQUE (life_cycle_state_id)
) COMMENT = '生命周期状态';


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


-- 物料层级
CREATE TABLE dd_material_level
(
	material_level_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料层级ID',
	material_level_name char(255) NOT NULL COMMENT '物料层级名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (material_level_id),
	UNIQUE (material_level_id)
) COMMENT = '物料层级';


-- 物料锁定
CREATE TABLE dd_material_lock
(
	material_lock_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料锁定ID',
	material_lock_name char(255) NOT NULL COMMENT '物料锁定名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (material_lock_id),
	UNIQUE (material_lock_id)
) COMMENT = '物料锁定';


-- 物料属性
CREATE TABLE dd_material_property
(
	material_property_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料属性ID',
	material_property_name char(255) NOT NULL COMMENT '物料属性名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (material_property_id),
	UNIQUE (material_property_id)
) COMMENT = '物料属性';


-- 物料类型表
CREATE TABLE dd_material_type
(
	material_type_id bigint NOT NULL AUTO_INCREMENT COMMENT '物料类型ID',
	material_type_name char(255) NOT NULL COMMENT '物料类型名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (material_type_id)
) COMMENT = '物料类型表';


-- 物料版本
CREATE TABLE dd_material_version
(
	material_version_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料版本ID',
	material_version_name char(255) NOT NULL COMMENT '物料版本名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (material_version_id),
	UNIQUE (material_version_id)
) COMMENT = '物料版本';


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


-- 工序
CREATE TABLE dd_process
(
	process_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '工序ID',
	process_name char(255) NOT NULL COMMENT '工序名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (process_id),
	UNIQUE (process_id)
) COMMENT = '工序';


-- 生产损耗等级
CREATE TABLE dd_produce_loss_level
(
	produce_loss_level_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '生产损耗等级ID',
	produce_loss_level_name char(255) NOT NULL COMMENT '生产损耗等级名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (produce_loss_level_id),
	UNIQUE (produce_loss_level_id)
) COMMENT = '生产损耗等级';


-- 结算期限表
CREATE TABLE dd_settle_period
(
	settle_period_id bigint NOT NULL AUTO_INCREMENT COMMENT '结算期限ID',
	settle_period_name char(255) NOT NULL COMMENT '结算期限名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (settle_period_id),
	UNIQUE (settle_period_id)
) COMMENT = '结算期限表';


-- 供应商组
CREATE TABLE dd_supplier_group
(
	supplier_group_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '供应商组ID',
	supplier_group_name char(255) NOT NULL COMMENT '供应商组名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (supplier_group_id),
	UNIQUE (supplier_group_id)
) COMMENT = '供应商组';


-- 供应商类型
CREATE TABLE dd_supplier_type
(
	supplier_type_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '供应商类型ID',
	supplier_type_name char(255) NOT NULL COMMENT '供应商类型名称',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (supplier_type_id),
	UNIQUE (supplier_type_id)
) COMMENT = '供应商类型';


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


-- 混料单表体
CREATE TABLE om_mix_body
(
	mix_body_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '混料单表体id',
	mix_head_id bigint unsigned NOT NULL COMMENT '混料单表头id',
	material_id bigint unsigned COMMENT '物料ID',
	plan_num decimal(10,2) COMMENT '计划出库数量',
	create_time datetime COMMENT '创建时间',
	-- 1 未删除
	-- 2 已删除
	dr tinyint COMMENT '是否删除 : 1 未删除
2 已删除',
	PRIMARY KEY (mix_body_id),
	UNIQUE (mix_body_id),
	UNIQUE (mix_head_id)
) COMMENT = '混料单表体';


-- 混料单表头
CREATE TABLE om_mix_head
(
	mix_head_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '混料单表头id',
	mix_no char(128) NOT NULL COMMENT '混料单编码',
	plan_time date COMMENT '计划出库时间',
	create_time datetime COMMENT '创建时间',
	-- 1 未删除
	-- 2 已删除
	dr tinyint COMMENT '是否删除 : 1 未删除
2 已删除',
	PRIMARY KEY (mix_head_id),
	UNIQUE (mix_head_id)
) COMMENT = '混料单表头';


-- 出库单表体
CREATE TABLE om_outbound_body
(
	outbound_body_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '出库单表体id',
	outbound_head_id bigint unsigned NOT NULL COMMENT '出库单表头ID',
	material_id bigint unsigned NOT NULL COMMENT '物料ID',
	demand_num decimal(10,2) COMMENT '出库数量',
	create_time datetime COMMENT '创建时间',
	outbound_time datetime COMMENT '出库时间',
	-- 1 未删除
	-- 2 已删除
	dr tinyint COMMENT '是否删除 : 1 未删除
2 已删除',
	PRIMARY KEY (outbound_body_id),
	UNIQUE (outbound_body_id)
) COMMENT = '出库单表体';


-- 出库单表头
CREATE TABLE om_outbound_head
(
	outbound_head_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '出库单表头ID',
	outbound_no char(128) NOT NULL COMMENT '出库单编码',
	source_no char(128) COMMENT '来源单号',
	-- 1 混料
	-- 2 生产
	source_type tinyint COMMENT '来源类型 : 1 混料
2 生产',
	plan_time datetime COMMENT '计划出库时间',
	outbound_time datetime COMMENT '完成出库时间',
	-- 1：已出库
	-- 2：出库中
	-- 3：待出库
	status tinyint COMMENT '状态 : 1：已出库
2：出库中
3：待出库',
	extra char(255) COMMENT '备注',
	create_time datetime COMMENT '创建时间',
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


-- BOM操作记录
CREATE TABLE si_bom_record
(
	bom_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '操作记录ID',
	user_id bigint unsigned NOT NULL COMMENT '用户ID',
	create_time datetime COMMENT '操作时间',
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (bom_record_id),
	UNIQUE (bom_record_id)
) COMMENT = 'BOM操作记录';


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
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (client_record_id),
	UNIQUE (client_record_id)
) COMMENT = '客户操作记录表';


-- 配置表
CREATE TABLE si_config
(
	config_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '配置ID',
	config_key char(255) NOT NULL COMMENT '配置名称',
	config_value char(255) COMMENT '配置值',
	PRIMARY KEY (config_id),
	UNIQUE (config_id),
	UNIQUE (config_key)
) COMMENT = '配置表';


-- 叉车信息
CREATE TABLE si_forklift
(
	forklift_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '叉车id',
	forklift_no char(128) COMMENT '叉车编号',
	forklift_model char(255) COMMENT '叉车型号',
	forklift_brand char(255) COMMENT '品牌',
	supplier_id bigint unsigned NOT NULL COMMENT '供应商ID',
	rfid boolean COMMENT '是否携带RFID设备',
	iom boolean COMMENT '是否携带工业一体机',
	work_area char(255) COMMENT '作业区域',
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
	operation_name char(255) COMMENT '操作名称',
	create_time datetime COMMENT '操作时间',
	PRIMARY KEY (record_id),
	UNIQUE (record_id),
	UNIQUE (forklift_id)
) COMMENT = '叉车操作记录表';


-- 库位表
CREATE TABLE si_location
(
	location_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '库位ID',
	location_no char(128) NOT NULL COMMENT '库位编号',
	location_name char(255) NOT NULL COMMENT '库位名称',
	hold_tray_num int COMMENT '可容纳托盘数',
	location_type_id bigint unsigned NOT NULL COMMENT '货位类型ID',
	warehouse_id bigint unsigned NOT NULL COMMENT '仓库ID',
	remark char(255) COMMENT '备注',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (location_id),
	UNIQUE (location_id)
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
	-- 2：半成品
	-- 3：成品
	material_type tinyint COMMENT '一级物料类型 : 1：原材料
2：半成品
3：成品',
	material_type_id bigint COMMENT '二级物料类型ID',
	humidity_level_id bigint unsigned COMMENT '湿度等级ID',
	material_level_id bigint unsigned COMMENT '物料层级ID',
	measure_unit_id bigint unsigned COMMENT '计量单位ID',
	material_version_id bigint unsigned COMMENT '物料版本ID',
	produce_loss_level_id bigint unsigned COMMENT '生产损耗等级ID',
	life_cycle_state_id bigint unsigned COMMENT '生命周期状态ID',
	material_name char(64) NOT NULL COMMENT '物料名称',
	delivery_days int NOT NULL COMMENT '交期天数',
	moq char(32) NOT NULL COMMENT 'MOQ',
	material_model char(64) COMMENT '规格型号',
	material_draw char(64) COMMENT '物料图号',
	supplier_id bigint unsigned COMMENT '供应商ID',
	material_desc char(255) COMMENT '物料描述',
	test_type tinyint COMMENT '检验类型',
	user_id bigint unsigned COMMENT '创建人',
	create_time datetime COMMENT '创建时间',
	update_time datetime COMMENT '更新时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (material_id),
	UNIQUE (material_id)
) COMMENT = '物料表';


-- 物料属性
CREATE TABLE si_material_attribute
(
	material_attribute_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料属性ID',
	material_id bigint unsigned NOT NULL COMMENT '物料ID',
	lower_limit decimal(10,2) COMMENT '库存下限',
	upper_limit decimal(10,2) COMMENT '库存上限',
	default_purchase decimal(10,2) COMMENT '默认采购',
	-- 1：是
	-- 2：否
	way tinyint COMMENT '是否在途 : 1：是
2：否',
	warehouse_id bigint unsigned COMMENT '默认仓库',
	location_id bigint unsigned COMMENT '默认库位',
	-- 1：IQC检验
	-- 2：QE检验
	storage_inspect tinyint COMMENT '入库质量检验 : 1：IQC检验
2：QE检验',
	storage_inspect_type tinyint COMMENT '入库质量检验类型',
	storage_sampling_plan tinyint COMMENT '入库抽样计划',
	-- 1：工单审核
	-- 2：OQC检验
	outbound_inspect tinyint COMMENT '出库质量检验 : 1：工单审核
2：OQC检验',
	-- 1：是
	-- 2：否
	pick_split tinyint COMMENT '拣货时不支持分料 : 1：是
2：否',
	PRIMARY KEY (material_attribute_id),
	UNIQUE (material_attribute_id),
	UNIQUE (material_id)
) COMMENT = '物料属性';


-- 物料记录表
CREATE TABLE si_material_record
(
	material_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料记录ID',
	material_id bigint unsigned NOT NULL COMMENT '物料ID',
	user_id bigint unsigned NOT NULL COMMENT '操作人',
	create_time datetime COMMENT '操作时间',
	operation_name char(255) COMMENT '操作名称',
	PRIMARY KEY (material_record_id),
	UNIQUE (material_record_id)
) COMMENT = '物料记录表';


-- 物料规格说明书表
CREATE TABLE si_material_specification
(
	material_specification_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '物料规格说明书表',
	material_id bigint unsigned NOT NULL COMMENT '物料ID',
	file_name char(255) NOT NULL COMMENT '文件名',
	file_path char(255) NOT NULL COMMENT '文件路径',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (material_specification_id),
	UNIQUE (material_specification_id)
) COMMENT = '物料规格说明书表';


-- 供应商表
CREATE TABLE si_supplier
(
	supplier_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
	supplier_no char(64) NOT NULL COMMENT '供应商编码',
	supplier_group_id bigint unsigned COMMENT '供应商组ID',
	cert_status_id bigint unsigned COMMENT '认证状态ID',
	supplier_type_id bigint unsigned COMMENT '供应商类型ID',
	settle_period_id bigint COMMENT '结算期限ID',
	currency_id bigint unsigned COMMENT '币种ID',
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


-- 采购入库单表体
CREATE TABLE sm_storage_body
(
	storage_body_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '入库单表体ID',
	storage_head_id bigint unsigned NOT NULL COMMENT '入库单表头ID',
	material_id bigint unsigned COMMENT '物料ID',
	location_id bigint unsigned COMMENT '库位ID',
	car_brand  char(255) COMMENT '车牌信息',
	accept_num int COMMENT '接受数量',
	accept_time datetime COMMENT '接受日期',
	create_time datetime COMMENT '创建时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (storage_body_id),
	UNIQUE (storage_body_id)
) COMMENT = '采购入库单表体';


-- 采购入库单表头
CREATE TABLE sm_storage_head
(
	storage_head_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '入库单表头ID',
	warehouse_id bigint unsigned NOT NULL COMMENT '仓库ID',
	storage_no char(128) NOT NULL COMMENT '入库单编号',
	storage_time datetime COMMENT '入库时间',
	-- 1：已入库
	-- 2：入库中
	-- 3：待入库
	--
	--
	status tinyint COMMENT '入库状态 : 1：已入库
2：入库中
3：待入库

',
	extra char(255) COMMENT '备注',
	create_time datetime COMMENT '创建时间',
	-- 1：未删除
	-- 2：已删除
	dr tinyint COMMENT '是否删除 : 1：未删除
2：已删除',
	PRIMARY KEY (storage_head_id),
	UNIQUE (storage_head_id)
) COMMENT = '采购入库单表头';


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


-- 工作台权限模块
CREATE TABLE wm_work_bench
(
	work_bench_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '工作台权限ID',
	authority_id bigint unsigned NOT NULL COMMENT '权限ID',
	bench_name char(64) COMMENT '工作台权限模块名称',
	-- 1. 待办工作
	-- 2. 快捷入口
	bench_type tinyint COMMENT '权限类型 : 1. 待办工作
2. 快捷入口',
	-- 1. WMS
	-- 2. MES
	-- 3. ERP
	-- 4. MDM
	bench_module tinyint COMMENT '权限模块 : 1. WMS
2. MES
3. ERP
4. MDM',
	icon_path char(255) COMMENT '图标路径',
	url_path char(255) COMMENT '访问路径',
	background_color char(12) COMMENT '背景色',
	bench_node tinyint NOT NULL COMMENT '工作台节点',
	PRIMARY KEY (work_bench_id),
	UNIQUE (work_bench_id)
) COMMENT = '工作台权限模块';



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


ALTER TABLE wm_work_bench
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


ALTER TABLE dd_cert_status
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_client_type
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_credit_level
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_currency
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_humidity_level
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_life_cycle_state
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_location_type
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_material_level
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_material_lock
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_material_property
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_material_type
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_material_version
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


ALTER TABLE dd_process
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_produce_loss_level
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_settle_period
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_supplier_group
	ADD FOREIGN KEY (user_id)
	REFERENCES am_user (user_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE dd_supplier_type
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


ALTER TABLE si_bom_record
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


ALTER TABLE si_supplier
	ADD FOREIGN KEY (cert_status_id)
	REFERENCES dd_cert_status (cert_status_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_client
	ADD FOREIGN KEY (client_type_id)
	REFERENCES dd_client_type (client_type_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_client
	ADD FOREIGN KEY (credit_level_id)
	REFERENCES dd_credit_level (credit_level_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_supplier
	ADD FOREIGN KEY (currency_id)
	REFERENCES dd_currency (currency_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (humidity_level_id)
	REFERENCES dd_humidity_level (humidity_level_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (life_cycle_state_id)
	REFERENCES dd_life_cycle_state (life_cycle_state_id)
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
	ADD FOREIGN KEY (material_level_id)
	REFERENCES dd_material_level (material_level_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (material_type_id)
	REFERENCES dd_material_type (material_type_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (material_version_id)
	REFERENCES dd_material_version (material_version_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (measure_unit_id)
	REFERENCES dd_measure_unit (measure_unit_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material
	ADD FOREIGN KEY (produce_loss_level_id)
	REFERENCES dd_produce_loss_level (produce_loss_level_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_supplier
	ADD FOREIGN KEY (settle_period_id)
	REFERENCES dd_settle_period (settle_period_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_supplier
	ADD FOREIGN KEY (supplier_group_id)
	REFERENCES dd_supplier_group (supplier_group_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_supplier
	ADD FOREIGN KEY (supplier_type_id)
	REFERENCES dd_supplier_type (supplier_type_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_warehouse
	ADD FOREIGN KEY (warehouse_type_id)
	REFERENCES dd_warehouse_type (warehouse_type_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_mix_body
	ADD FOREIGN KEY (mix_head_id)
	REFERENCES om_mix_head (mix_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_outbound_body
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


ALTER TABLE si_forklift_record
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


ALTER TABLE si_material_attribute
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


ALTER TABLE om_mix_body
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE om_outbound_body
	ADD FOREIGN KEY (material_id)
	REFERENCES si_material (material_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material_attribute
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


ALTER TABLE si_material_specification
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


ALTER TABLE si_forklift
	ADD FOREIGN KEY (supplier_id)
	REFERENCES si_supplier (supplier_id)
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


ALTER TABLE si_location
	ADD FOREIGN KEY (warehouse_id)
	REFERENCES si_warehouse (warehouse_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE si_material_attribute
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


ALTER TABLE sm_storage_record
	ADD FOREIGN KEY (storage_head_id)
	REFERENCES sm_storage_head (storage_head_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



