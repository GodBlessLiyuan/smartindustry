SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS am_m_user_authority;
DROP TABLE IF EXISTS am_role_authority;
DROP TABLE IF EXISTS am_authority;
DROP TABLE IF EXISTS am_dept_record;
DROP TABLE IF EXISTS am_role_record;
DROP TABLE IF EXISTS am_user_record;
DROP TABLE IF EXISTS om_label_recommend;
DROP TABLE IF EXISTS om_pick_body;
DROP TABLE IF EXISTS si_material_record;
DROP TABLE IF EXISTS si_material_specification;
DROP TABLE IF EXISTS om_pick_label;
DROP TABLE IF EXISTS si_label_record;
DROP TABLE IF EXISTS si_storage_label;
DROP TABLE IF EXISTS sm_receipt_label;
DROP TABLE IF EXISTS sm_storage_detail;
DROP TABLE IF EXISTS si_print_label;
DROP TABLE IF EXISTS sm_iqc_detect;
DROP TABLE IF EXISTS sm_qe_confirm;
DROP TABLE IF EXISTS sm_qe_detect;
DROP TABLE IF EXISTS sm_storage_group;
DROP TABLE IF EXISTS sm_storage_record;
DROP TABLE IF EXISTS sm_storage;
DROP TABLE IF EXISTS sm_receipt_body;
DROP TABLE IF EXISTS si_material;
DROP TABLE IF EXISTS si_supplier_record;
DROP TABLE IF EXISTS si_supplier;
DROP TABLE IF EXISTS dd_cert_status;
DROP TABLE IF EXISTS dd_currency;
DROP TABLE IF EXISTS dd_humidity_level;
DROP TABLE IF EXISTS dd_life_cycle_state;
DROP TABLE IF EXISTS si_location_record;
DROP TABLE IF EXISTS si_location;
DROP TABLE IF EXISTS dd_location_type;
DROP TABLE IF EXISTS dd_material_level;
DROP TABLE IF EXISTS dd_material_type;
DROP TABLE IF EXISTS dd_material_version;
DROP TABLE IF EXISTS dd_measure_unit;
DROP TABLE IF EXISTS dd_produce_loss_level;
DROP TABLE IF EXISTS dd_settle_period;
DROP TABLE IF EXISTS dd_supplier_group;
DROP TABLE IF EXISTS dd_supplier_type;
DROP TABLE IF EXISTS si_warehouse_record;
DROP TABLE IF EXISTS si_warehouse;
DROP TABLE IF EXISTS dd_warehouse_type;
DROP TABLE IF EXISTS om_outbound_record;
DROP TABLE IF EXISTS am_user;
DROP TABLE IF EXISTS am_dept;
DROP TABLE IF EXISTS am_role;
DROP TABLE IF EXISTS om_logistics_picture;
DROP TABLE IF EXISTS om_logistics_record;
DROP TABLE IF EXISTS om_outbound;
DROP TABLE IF EXISTS om_pick_check;
DROP TABLE IF EXISTS om_pick_head;
DROP TABLE IF EXISTS sm_receipt_head;




/* Create Tables */

CREATE TABLE am_authority
(
    authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
    authority_name char(255) NOT NULL,
    authority_path char(255) NOT NULL,
    -- 1：菜单   2：按钮
    type tinyint COMMENT '1：菜单   2：按钮',
    parent_id bigint unsigned NOT NULL,
    PRIMARY KEY (authority_id),
    UNIQUE (authority_id),
    UNIQUE (authority_path)
);


CREATE TABLE am_dept
(
    dept_id bigint unsigned NOT NULL AUTO_INCREMENT,
    parent_id bigint unsigned,
    dept_name char(32) NOT NULL,
    user_id bigint unsigned,
    dept_desc char(255),
    -- 1 启动
    -- 2 禁用
    status tinyint COMMENT '1 启动
2 禁用',
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (dept_id),
    UNIQUE (dept_id)
);


CREATE TABLE am_dept_record
(
    dept_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    dept_id bigint unsigned NOT NULL,
    user_id bigint unsigned NOT NULL,
    create_time datetime,
    type char(255),
    PRIMARY KEY (dept_record_id),
    UNIQUE (dept_record_id)
);


CREATE TABLE am_m_user_authority
(
    user_authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
    user_id bigint unsigned NOT NULL,
    authority_id bigint unsigned NOT NULL,
    PRIMARY KEY (user_authority_id),
    UNIQUE (user_authority_id)
);


CREATE TABLE am_role
(
    role_id bigint unsigned NOT NULL AUTO_INCREMENT,
    role_name char(32) NOT NULL,
    role_desc char(255),
    -- 1：启用
    -- 2：禁用
    status tinyint COMMENT '1：启用
2：禁用',
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (role_id),
    UNIQUE (role_id)
);


CREATE TABLE am_role_authority
(
    role_authority_id bigint NOT NULL AUTO_INCREMENT,
    role_id bigint unsigned NOT NULL,
    authority_id bigint unsigned NOT NULL,
    PRIMARY KEY (role_authority_id),
    UNIQUE (role_authority_id)
);


CREATE TABLE am_role_record
(
    role_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    role_id bigint unsigned NOT NULL,
    user_id bigint unsigned NOT NULL,
    create_time datetime,
    type char(255),
    PRIMARY KEY (role_record_id)
);


CREATE TABLE am_user
(
    user_id bigint unsigned NOT NULL AUTO_INCREMENT,
    name char(32) NOT NULL,
    -- 1 男
    -- 2 女
    sex tinyint COMMENT '1 男
2 女',
    dept_id bigint unsigned NOT NULL,
    username char(32) NOT NULL,
    password char(32) NOT NULL,
    role_id bigint unsigned NOT NULL,
    job char(16),
    phone char(16),
    email char(255),
    -- 1 启用
    -- 2 禁用
    status tinyint COMMENT '1 启用
2 禁用',
    remark char(255),
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (user_id),
    UNIQUE (user_id)
);


CREATE TABLE am_user_record
(
    user_record_id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint unsigned NOT NULL,
    operate_id bigint unsigned NOT NULL,
    create_time datetime,
    type char(255),
    PRIMARY KEY (user_record_id),
    UNIQUE (user_record_id),
    UNIQUE (operate_id)
);


CREATE TABLE dd_cert_status
(
    cert_status_id bigint unsigned NOT NULL AUTO_INCREMENT,
    cert_status_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (cert_status_id),
    UNIQUE (cert_status_id)
);


CREATE TABLE dd_currency
(
    currency_id bigint unsigned NOT NULL AUTO_INCREMENT,
    currency_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (currency_id),
    UNIQUE (currency_id)
);


CREATE TABLE dd_humidity_level
(
    humidity_level_id bigint unsigned NOT NULL AUTO_INCREMENT,
    humidity_level_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (humidity_level_id),
    UNIQUE (humidity_level_id)
);


CREATE TABLE dd_life_cycle_state
(
    life_cycle_state_id bigint unsigned NOT NULL AUTO_INCREMENT,
    life_cycle_state_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (life_cycle_state_id),
    UNIQUE (life_cycle_state_id)
);


CREATE TABLE dd_location_type
(
    location_type_id bigint unsigned NOT NULL AUTO_INCREMENT,
    location_type_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (location_type_id),
    UNIQUE (location_type_id)
);


CREATE TABLE dd_material_level
(
    material_level_id bigint unsigned NOT NULL AUTO_INCREMENT,
    material_level_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (material_level_id),
    UNIQUE (material_level_id)
);


CREATE TABLE dd_material_type
(
    material_type_id bigint NOT NULL AUTO_INCREMENT,
    material_type_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (material_type_id)
);


CREATE TABLE dd_material_version
(
    material_version_id bigint unsigned NOT NULL AUTO_INCREMENT,
    material_version_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (material_version_id),
    UNIQUE (material_version_id)
);


CREATE TABLE dd_measure_unit
(
    measure_unit_id bigint unsigned NOT NULL AUTO_INCREMENT,
    measure_unit_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (measure_unit_id),
    UNIQUE (measure_unit_id)
);


CREATE TABLE dd_produce_loss_level
(
    produce_loss_level_id bigint unsigned NOT NULL AUTO_INCREMENT,
    produce_loss_level_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (produce_loss_level_id),
    UNIQUE (produce_loss_level_id)
);


CREATE TABLE dd_settle_period
(
    settle_period_id bigint NOT NULL AUTO_INCREMENT,
    settle_period_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (settle_period_id),
    UNIQUE (settle_period_id)
);


CREATE TABLE dd_supplier_group
(
    supplier_group_id bigint unsigned NOT NULL AUTO_INCREMENT,
    supplier_group_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (supplier_group_id),
    UNIQUE (supplier_group_id)
);


CREATE TABLE dd_supplier_type
(
    supplier_type_id bigint unsigned NOT NULL AUTO_INCREMENT,
    supplier_type_name char(255) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (supplier_type_id),
    UNIQUE (supplier_type_id)
);


CREATE TABLE dd_warehouse_type
(
    warehouse_type_id bigint unsigned NOT NULL AUTO_INCREMENT,
    warehouse_type_name char(64) NOT NULL,
    user_id bigint unsigned,
    create_time datetime,
    PRIMARY KEY (warehouse_type_id),
    UNIQUE (warehouse_type_id)
);


CREATE TABLE om_label_recommend
(
    label_recommend_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_body_id bigint unsigned NOT NULL,
    storage_label_id bigint unsigned NOT NULL,
    PRIMARY KEY (label_recommend_id),
    UNIQUE (label_recommend_id)
);


CREATE TABLE om_logistics_picture
(
    logistics_picture_id bigint unsigned NOT NULL AUTO_INCREMENT,
    logistics_record_id bigint unsigned NOT NULL,
    picture char(255) NOT NULL,
    PRIMARY KEY (logistics_picture_id),
    UNIQUE (logistics_picture_id)
);


CREATE TABLE om_logistics_record
(
    logistics_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    outbound_id bigint unsigned NOT NULL,
    ship_date date,
    logistics_no char(64),
    -- 1：到付
    -- 2：寄付
    ship_way tinyint COMMENT '1：到付
2：寄付',
    remark char(255),
    create_time datetime,
    PRIMARY KEY (logistics_record_id),
    UNIQUE (logistics_record_id),
    UNIQUE (outbound_id)
);


CREATE TABLE om_outbound
(
    outbound_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_head_id bigint unsigned NOT NULL,
    outbound_no char(64) NOT NULL,
    outbound_time datetime,
    ship_time datetime,
    -- 1：已出库
    -- 3：待出库
    status tinyint COMMENT '1：已出库
3：待出库',
    create_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (outbound_id),
    UNIQUE (outbound_id),
    UNIQUE (pick_head_id),
    UNIQUE (outbound_no)
);


CREATE TABLE om_outbound_record
(
    record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_head_id bigint unsigned NOT NULL,
    outbound_id bigint unsigned,
    user_id bigint unsigned,
    name char(255),
    type char(255),
    create_time datetime,
    -- 1：未处理
    -- 5：物料拣货
    -- 10：工单审核|OQC检验
    -- 15：等齐套发货
    -- 20：取消发货，退货仓库
    -- 25：物料出库
    -- 30：完成出库
    -- 35：确认出库
    status tinyint COMMENT '1：未处理
5：物料拣货
10：工单审核|OQC检验
15：等齐套发货
20：取消发货，退货仓库
25：物料出库
30：完成出库
35：确认出库',
    PRIMARY KEY (record_id),
    UNIQUE (record_id)
);


CREATE TABLE om_pick_body
(
    pick_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_head_id bigint unsigned NOT NULL,
    material_id bigint unsigned NOT NULL,
    demand_num int,
    pick_num int,
    create_time datetime,
    exception char(255),
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (pick_body_id),
    UNIQUE (pick_body_id)
);


CREATE TABLE om_pick_check
(
    pick_head_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1：同意
    -- 2：驳回-取消发货，退回仓库
    -- 3：待审核
    -- 4：驳回-等齐套发货
    status tinyint COMMENT '1：同意
2：驳回-取消发货，退回仓库
3：待审核
4：驳回-等齐套发货',
    PRIMARY KEY (pick_head_id),
    UNIQUE (pick_head_id)
);


CREATE TABLE om_pick_head
(
    pick_head_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_no char(64) NOT NULL,
    order_no char(32) NOT NULL,
    -- 1：工单
    -- 2：销售订单
    order_type tinyint NOT NULL COMMENT '1：工单
2：销售订单',
    -- 1：待推荐
    -- 5：未处理
    -- 10：物料拣货
    -- 15：工单审核|OQC检验
    -- 20：等齐套发货
    -- 25：取消发货，退货仓库
    -- 30：物料出库
    -- 35：完成出库
    -- 40：确认出库
    material_status tinyint COMMENT '1：待推荐
5：未处理
10：物料拣货
15：工单审核|OQC检验
20：等齐套发货
25：取消发货，退货仓库
30：物料出库
35：完成出库
40：确认出库',
    correspond_project char(255),
    accept_customer char(255),
    accept_address char(255),
    plan_time datetime,
    outbound_time datetime,
    -- 1：全部出库
    -- 2：欠料出库
    -- 3：未出库
    outbound_status tinyint COMMENT '1：全部出库
2：欠料出库
3：未出库',
    create_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (pick_head_id),
    UNIQUE (pick_head_id),
    UNIQUE (pick_no)
);


CREATE TABLE om_pick_label
(
    pick_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_head_id bigint unsigned NOT NULL,
    print_label_id bigint unsigned NOT NULL,
    -- 1：是
    -- 2：否
    recommend tinyint COMMENT '1：是
2：否',
    create_time datetime,
    PRIMARY KEY (pick_label_id),
    UNIQUE (pick_label_id)
);


CREATE TABLE si_label_record
(
    label_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    print_label_id bigint unsigned NOT NULL,
    user_id bigint unsigned NOT NULL,
    name char(255),
    -- 1：入库管理
    -- 2：出库管理
    module tinyint COMMENT '1：入库管理
2：出库管理',
    create_time datetime,
    -- 1：录入标签
    -- 5：IQC检测
    -- 10：QE检测
    -- 15：QE确认
    -- 20：物料入库
    -- 25：入库完成
    status tinyint COMMENT '1：录入标签
5：IQC检测
10：QE检测
15：QE确认
20：物料入库
25：入库完成',
    PRIMARY KEY (label_record_id),
    UNIQUE (label_record_id)
);


CREATE TABLE si_location
(
    location_id bigint unsigned NOT NULL AUTO_INCREMENT,
    location_no char(64) NOT NULL,
    location_name char(255) NOT NULL,
    location_type_id bigint unsigned NOT NULL,
    warehouse_id bigint unsigned NOT NULL,
    remark char(255),
    user_id bigint unsigned,
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (location_id),
    UNIQUE (location_id)
);


CREATE TABLE si_location_record
(
    location_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    location_id bigint unsigned NOT NULL,
    user_id bigint unsigned NOT NULL,
    create_time datetime,
    type char(255),
    PRIMARY KEY (location_record_id),
    UNIQUE (location_record_id)
);


CREATE TABLE si_material
(
    material_id bigint unsigned NOT NULL AUTO_INCREMENT,
    material_no char(64) NOT NULL,
    material_type_id bigint NOT NULL,
    humidity_level_id bigint unsigned,
    material_level_id bigint unsigned,
    measure_unit_id bigint unsigned,
    material_version_id bigint unsigned,
    produce_loss_level_id bigint unsigned,
    life_cycle_state_id bigint unsigned,
    material_name char(64) NOT NULL,
    delivery_days int NOT NULL,
    moq char(32) NOT NULL,
    material_model char(64),
    material_draw char(64),
    supplier_id bigint unsigned,
    material_desc char(255),
    test_type tinyint,
    user_id bigint unsigned,
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (material_id),
    UNIQUE (material_id)
);


CREATE TABLE si_material_record
(
    material_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    material_id bigint unsigned NOT NULL,
    user_id bigint unsigned NOT NULL,
    create_time datetime,
    type char(255),
    PRIMARY KEY (material_record_id),
    UNIQUE (material_record_id)
);


CREATE TABLE si_material_specification
(
    material_specification_id bigint unsigned NOT NULL AUTO_INCREMENT,
    material_id bigint unsigned NOT NULL,
    file_name char(255) NOT NULL,
    file_path char(255) NOT NULL,
    create_time datetime,
    PRIMARY KEY (material_specification_id),
    UNIQUE (material_specification_id)
);


CREATE TABLE si_print_label
(
    print_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    package_id char(32) NOT NULL,
    produce_date char(32),
    produce_batch char(32),
    material_id bigint unsigned NOT NULL,
    num int,
    -- 1：良品
    -- 2：非良品
    type tinyint COMMENT '1：良品
2：非良品',
    -- 1：扫描
    -- 2：打印
    origin tinyint NOT NULL COMMENT '1：扫描
2：打印',
    location_id bigint unsigned,
    relate_label_id bigint unsigned,
    relate_package_id char(32),
    create_time datetime,
    -- 1：未废弃
    -- 2：已废弃
    dr tinyint COMMENT '1：未废弃
2：已废弃',
    PRIMARY KEY (print_label_id),
    UNIQUE (print_label_id),
    UNIQUE (package_id)
);


CREATE TABLE si_storage_label
(
    storage_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    print_label_id bigint unsigned NOT NULL,
    material_id bigint unsigned NOT NULL,
    location_id bigint unsigned NOT NULL,
    package_id char(32),
    order_no char(32),
    -- 1：PO单收料
    -- 2：样品采购
    -- 3：生产退料
    order_type tinyint COMMENT '1：PO单收料
2：样品采购
3：生产退料',
    -- 1：良品
    -- 2：非良品
    type tinyint COMMENT '1：良品
2：非良品',
    storage_num int,
    storage_time datetime,
    PRIMARY KEY (storage_label_id),
    UNIQUE (storage_label_id),
    UNIQUE (print_label_id)
);


CREATE TABLE si_supplier
(
    supplier_id bigint unsigned NOT NULL AUTO_INCREMENT,
    supplier_no char(64) NOT NULL,
    supplier_group_id bigint unsigned,
    cert_status_id bigint unsigned,
    supplier_type_id bigint unsigned,
    settle_period_id bigint,
    currency_id bigint unsigned,
    supplier_name char(64) NOT NULL,
    contact_name char(64),
    phone char(16),
    fax char(16),
    site char(64),
    mail char(64),
    area char(64),
    address char(128),
    remark char(255),
    user_id bigint unsigned,
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (supplier_id),
    UNIQUE (supplier_id)
);


CREATE TABLE si_supplier_record
(
    supplier_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    supplier_id bigint unsigned NOT NULL,
    user_id bigint unsigned NOT NULL,
    create_time datetime,
    type char(255),
    PRIMARY KEY (supplier_record_id),
    UNIQUE (supplier_record_id)
);


CREATE TABLE si_warehouse
(
    warehouse_id bigint unsigned NOT NULL AUTO_INCREMENT,
    warehouse_no char(64) NOT NULL,
    warehouse_name char(64) NOT NULL,
    warehouse_type_id bigint unsigned NOT NULL,
    principal char(32),
    phone char(16),
    area char(64),
    address char(128),
    remark char(255),
    user_id bigint unsigned,
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (warehouse_id),
    UNIQUE (warehouse_id)
);


CREATE TABLE si_warehouse_record
(
    warehouse_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    warehouse_id bigint unsigned NOT NULL,
    user_id bigint unsigned NOT NULL,
    create_time datetime,
    type char(255),
    PRIMARY KEY (warehouse_record_id),
    UNIQUE (warehouse_record_id)
);


CREATE TABLE sm_iqc_detect
(
    receipt_body_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1：允许良品
    -- 2：QE驳回重检验
    -- 3：未检验
    status tinyint COMMENT '1：允许良品
2：QE驳回重检验
3：未检验',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_qe_confirm
(
    receipt_body_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1：允许
    -- 3：不良，待确认
    -- 4：特采
    -- 5：退供应商
    status tinyint COMMENT '1：允许
3：不良，待确认
4：特采
5：退供应商',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_qe_detect
(
    receipt_body_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1：允许良品
    -- 3：未检验
    -- 4：特采
    -- 5：退供应商
    status tinyint COMMENT '1：允许良品
3：未检验
4：特采
5：退供应商',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_receipt_body
(
    receipt_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_head_id bigint unsigned NOT NULL,
    material_id bigint unsigned NOT NULL,
    order_no char(32),
    -- 1：PO单收料
    -- 2：样品采购
    -- 3：生产退料
    order_type tinyint COMMENT '1：PO单收料
2：样品采购
3：生产退料',
    receipt_no char(32) NOT NULL,
    order_total int,
    accept_num int,
    accept_date datetime,
    good_num int,
    bad_num int,
    stock_num int,
    -- 1：录入标签
    -- 5：IQC检测
    -- 10：QE检测
    -- 15：QE确认
    -- 20：物料入库
    -- 25：入库完成
    status tinyint COMMENT '1：录入标签
5：IQC检测
10：QE检测
15：QE确认
20：物料入库
25：入库完成',
    create_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id),
    UNIQUE (receipt_no)
);


CREATE TABLE sm_receipt_head
(
    receipt_head_id bigint unsigned NOT NULL AUTO_INCREMENT,
    order_no char(32) NOT NULL,
    -- 1：PO单收料
    -- 2：样品采购
    -- 3：生产退料
    order_type tinyint NOT NULL COMMENT '1：PO单收料
2：样品采购
3：生产退料',
    order_date date,
    supplier char(128),
    buyer char(128),
    plan_date date,
    logistics_company char(128),
    logistics_no char(64),
    -- 1：到付
    -- 2：寄付
    receipt_way tinyint COMMENT '1：到付
2：寄付',
    remark char(255),
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (receipt_head_id),
    UNIQUE (receipt_head_id)
);


CREATE TABLE sm_receipt_label
(
    receipt_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    print_label_id bigint unsigned NOT NULL,
    receipt_body_id bigint unsigned NOT NULL,
    storage_id bigint unsigned,
    PRIMARY KEY (receipt_label_id),
    UNIQUE (receipt_label_id)
);


CREATE TABLE sm_storage
(
    storage_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_body_id bigint unsigned NOT NULL,
    storage_no char(32) NOT NULL,
    pending_num int,
    stored_num int,
    storage_time datetime,
    -- 1：已入库
    -- 2：入库中
    -- 3：待入库
    --
    --
    status tinyint COMMENT '1：已入库
2：入库中
3：待入库

',
    -- 1：良品
    -- 2：非良品
    type tinyint COMMENT '1：良品
2：非良品',
    create_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (storage_id),
    UNIQUE (storage_id),
    UNIQUE (storage_no)
);


CREATE TABLE sm_storage_detail
(
    storage_detail_id bigint unsigned NOT NULL AUTO_INCREMENT,
    storage_group_id bigint unsigned NOT NULL,
    print_label_id bigint unsigned NOT NULL,
    PRIMARY KEY (storage_detail_id),
    UNIQUE (storage_detail_id),
    UNIQUE (print_label_id)
);


CREATE TABLE sm_storage_group
(
    storage_group_id bigint unsigned NOT NULL AUTO_INCREMENT,
    storage_id bigint unsigned NOT NULL,
    location_id bigint unsigned,
    PRIMARY KEY (storage_group_id),
    UNIQUE (storage_group_id)
);


CREATE TABLE sm_storage_record
(
    record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_body_id bigint unsigned NOT NULL,
    storage_id bigint unsigned,
    user_id bigint unsigned,
    name char(255),
    type char(255),
    create_time datetime,
    -- 1：录入标签
    -- 5：IQC检测
    -- 10：QE检测
    -- 15：QE确认
    -- 20：物料入库
    -- 25：入库完成
    status tinyint COMMENT '1：录入标签
5：IQC检测
10：QE检测
15：QE确认
20：物料入库
25：入库完成',
    PRIMARY KEY (record_id),
    UNIQUE (record_id)
);



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


ALTER TABLE si_label_record
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


ALTER TABLE om_logistics_picture
    ADD FOREIGN KEY (logistics_record_id)
        REFERENCES om_logistics_record (logistics_record_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_logistics_record
    ADD FOREIGN KEY (outbound_id)
        REFERENCES om_outbound (outbound_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_outbound_record
    ADD FOREIGN KEY (outbound_id)
        REFERENCES om_outbound (outbound_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_label_recommend
    ADD FOREIGN KEY (pick_body_id)
        REFERENCES om_pick_body (pick_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_outbound
    ADD FOREIGN KEY (pick_head_id)
        REFERENCES om_pick_head (pick_head_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_outbound_record
    ADD FOREIGN KEY (pick_head_id)
        REFERENCES om_pick_head (pick_head_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_pick_body
    ADD FOREIGN KEY (pick_head_id)
        REFERENCES om_pick_head (pick_head_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_pick_check
    ADD FOREIGN KEY (pick_head_id)
        REFERENCES om_pick_head (pick_head_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_pick_label
    ADD FOREIGN KEY (pick_head_id)
        REFERENCES om_pick_head (pick_head_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_location_record
    ADD FOREIGN KEY (location_id)
        REFERENCES si_location (location_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_print_label
    ADD FOREIGN KEY (location_id)
        REFERENCES si_location (location_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_storage_label
    ADD FOREIGN KEY (location_id)
        REFERENCES si_location (location_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_group
    ADD FOREIGN KEY (location_id)
        REFERENCES si_location (location_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_pick_body
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


ALTER TABLE si_print_label
    ADD FOREIGN KEY (material_id)
        REFERENCES si_material (material_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_storage_label
    ADD FOREIGN KEY (material_id)
        REFERENCES si_material (material_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_receipt_body
    ADD FOREIGN KEY (material_id)
        REFERENCES si_material (material_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_pick_label
    ADD FOREIGN KEY (print_label_id)
        REFERENCES si_print_label (print_label_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_label_record
    ADD FOREIGN KEY (print_label_id)
        REFERENCES si_print_label (print_label_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_print_label
    ADD FOREIGN KEY (relate_label_id)
        REFERENCES si_print_label (print_label_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_storage_label
    ADD FOREIGN KEY (print_label_id)
        REFERENCES si_print_label (print_label_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_receipt_label
    ADD FOREIGN KEY (print_label_id)
        REFERENCES si_print_label (print_label_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_detail
    ADD FOREIGN KEY (print_label_id)
        REFERENCES si_print_label (print_label_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_label_recommend
    ADD FOREIGN KEY (storage_label_id)
        REFERENCES si_storage_label (storage_label_id)
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


ALTER TABLE si_warehouse_record
    ADD FOREIGN KEY (warehouse_id)
        REFERENCES si_warehouse (warehouse_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_iqc_detect
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_qe_confirm
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_qe_detect
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_receipt_label
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_record
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_receipt_body
    ADD FOREIGN KEY (receipt_head_id)
        REFERENCES sm_receipt_head (receipt_head_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_receipt_label
    ADD FOREIGN KEY (storage_id)
        REFERENCES sm_storage (storage_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_group
    ADD FOREIGN KEY (storage_id)
        REFERENCES sm_storage (storage_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_record
    ADD FOREIGN KEY (storage_id)
        REFERENCES sm_storage (storage_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_detail
    ADD FOREIGN KEY (storage_group_id)
        REFERENCES sm_storage_group (storage_group_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;



