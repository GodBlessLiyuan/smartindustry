SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS ba_m_user_authority;
DROP TABLE IF EXISTS ba_role_authority;
DROP TABLE IF EXISTS ba_authority;
DROP TABLE IF EXISTS ba_user_role;
DROP TABLE IF EXISTS ba_role;
DROP TABLE IF EXISTS sm_record;
DROP TABLE IF EXISTS sm_storage_detail;
DROP TABLE IF EXISTS sm_print_label;
DROP TABLE IF EXISTS sm_storage_group;
DROP TABLE IF EXISTS sm_storage_location;
DROP TABLE IF EXISTS ba_user;
DROP TABLE IF EXISTS sm_entry_label;
DROP TABLE IF EXISTS sm_iqc_detect;
DROP TABLE IF EXISTS sm_material_storage;
DROP TABLE IF EXISTS sm_qe_confirm;
DROP TABLE IF EXISTS sm_qe_detect;
DROP TABLE IF EXISTS sm_receipt_body;
DROP TABLE IF EXISTS sm_material;
DROP TABLE IF EXISTS sm_receipt_head;




/* Create Tables */

CREATE TABLE ba_authority
(
    authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
    code char(255) NOT NULL,
    name char(255) NOT NULL,
    create_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (authority_id),
    UNIQUE (authority_id),
    UNIQUE (code)
);


CREATE TABLE ba_m_user_authority
(
    user_authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
    user_id bigint unsigned NOT NULL,
    authority_id bigint unsigned NOT NULL,
    code char(255),
    name char(255),
    PRIMARY KEY (user_authority_id),
    UNIQUE (user_authority_id)
);


CREATE TABLE ba_role
(
    role_id bigint unsigned NOT NULL AUTO_INCREMENT,
    code char(255) NOT NULL,
    name char(255) NOT NULL,
    create_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (role_id),
    UNIQUE (role_id),
    UNIQUE (code)
);


CREATE TABLE ba_role_authority
(
    role_authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
    role_id bigint unsigned NOT NULL,
    authority_id bigint unsigned NOT NULL,
    PRIMARY KEY (role_authority_id),
    UNIQUE (role_authority_id),
    UNIQUE (role_id),
    UNIQUE (authority_id)
);


CREATE TABLE ba_user
(
    user_id bigint unsigned NOT NULL AUTO_INCREMENT,
    username char(255) NOT NULL,
    password char(255) NOT NULL,
    create_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (user_id),
    UNIQUE (user_id),
    UNIQUE (username)
);


CREATE TABLE ba_user_role
(
    user_role_id bigint unsigned NOT NULL AUTO_INCREMENT,
    user_id bigint unsigned NOT NULL,
    role_id bigint unsigned NOT NULL,
    PRIMARY KEY (user_role_id),
    UNIQUE (user_role_id)
);


CREATE TABLE sm_entry_label
(
    receipt_body_id bigint unsigned NOT NULL,
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
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


CREATE TABLE sm_material
(
    material_no char(32) NOT NULL,
    material_name char(255) NOT NULL,
    material_type tinyint NOT NULL,
    material_model char(255),
    material_desc char(255),
    test_type tinyint,
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (material_no),
    UNIQUE (material_no)
);


CREATE TABLE sm_material_storage
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
    PRIMARY KEY (storage_id),
    UNIQUE (storage_id),
    UNIQUE (storage_no)
);


CREATE TABLE sm_print_label
(
    print_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_body_id bigint unsigned NOT NULL,
    package_id char(32) NOT NULL,
    produce_date date,
    produce_batch char(64),
    num int,
    -- 1：良品
    -- 2：非良品
    type tinyint COMMENT '1：良品
2：非良品',
    -- 1：扫描
    -- 2：打印
    origin tinyint NOT NULL COMMENT '1：扫描
2：打印',
    material_no char(32) NOT NULL,
    location_no char(32),
    storage_id bigint unsigned,
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


CREATE TABLE sm_qe_confirm
(
    receipt_body_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1：特采
    -- 2：退供应商
    -- 3：不良，待确认
    status tinyint COMMENT '1：特采
2：退供应商
3：不良，待确认',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_qe_detect
(
    receipt_body_id bigint unsigned NOT NULL,
    -- 1：允许良品
    -- 3：未检验
    status tinyint COMMENT '1：允许良品
3：未检验',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_receipt_body
(
    receipt_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_head_id bigint unsigned NOT NULL,
    order_no char(32),
    -- 1：PO单收料
    -- 2：样品采购
    -- 3：生产退料
    order_type tinyint COMMENT '1：PO单收料
2：样品采购
3：生产退料',
    receipt_no char(32) NOT NULL,
    material_no char(32) NOT NULL,
    order_total int,
    accept_num int,
    accept_date date,
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
    logistics_no char(32),
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


CREATE TABLE sm_record
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
    location_no char(32),
    PRIMARY KEY (storage_group_id),
    UNIQUE (storage_group_id)
);


CREATE TABLE sm_storage_location
(
    location_no char(32) NOT NULL,
    location_code char(32),
    location_name char(255),
    location_type tinyint,
    user_id bigint unsigned NOT NULL,
    create_time datetime,
    update_time datetime,
    -- 1：未删除
    -- 2：已删除
    dr tinyint COMMENT '1：未删除
2：已删除',
    PRIMARY KEY (location_no),
    UNIQUE (location_no)
);



/* Create Foreign Keys */

ALTER TABLE ba_m_user_authority
    ADD FOREIGN KEY (authority_id)
        REFERENCES ba_authority (authority_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE ba_role_authority
    ADD FOREIGN KEY (authority_id)
        REFERENCES ba_authority (authority_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE ba_role_authority
    ADD FOREIGN KEY (role_id)
        REFERENCES ba_role (role_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE ba_user_role
    ADD FOREIGN KEY (role_id)
        REFERENCES ba_role (role_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE ba_m_user_authority
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE ba_user_role
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_record
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_location
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_print_label
    ADD FOREIGN KEY (material_no)
        REFERENCES sm_material (material_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_receipt_body
    ADD FOREIGN KEY (material_no)
        REFERENCES sm_material (material_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_print_label
    ADD FOREIGN KEY (storage_id)
        REFERENCES sm_material_storage (storage_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_record
    ADD FOREIGN KEY (storage_id)
        REFERENCES sm_material_storage (storage_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_group
    ADD FOREIGN KEY (storage_id)
        REFERENCES sm_material_storage (storage_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_print_label
    ADD FOREIGN KEY (relate_label_id)
        REFERENCES sm_print_label (print_label_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_detail
    ADD FOREIGN KEY (print_label_id)
        REFERENCES sm_print_label (print_label_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_entry_label
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_iqc_detect
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_material_storage
    ADD FOREIGN KEY (receipt_body_id)
        REFERENCES sm_receipt_body (receipt_body_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_print_label
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


ALTER TABLE sm_record
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


ALTER TABLE sm_storage_detail
    ADD FOREIGN KEY (storage_group_id)
        REFERENCES sm_storage_group (storage_group_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_print_label
    ADD FOREIGN KEY (location_no)
        REFERENCES sm_storage_location (location_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_group
    ADD FOREIGN KEY (location_no)
        REFERENCES sm_storage_location (location_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;



