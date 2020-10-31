SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS am_m_user_authority;
DROP TABLE IF EXISTS am_role_authority;
DROP TABLE IF EXISTS am_authority;
DROP TABLE IF EXISTS am_dept_record;
DROP TABLE IF EXISTS am_role_record;
DROP TABLE IF EXISTS am_user_record;
DROP TABLE IF EXISTS om_mix_body;
DROP TABLE IF EXISTS om_outbound_body;
DROP TABLE IF EXISTS si_location_record;
DROP TABLE IF EXISTS sm_storage_detail;
DROP TABLE IF EXISTS sm_storage_body;
DROP TABLE IF EXISTS si_location;
DROP TABLE IF EXISTS si_material_record;
DROP TABLE IF EXISTS si_material;
DROP TABLE IF EXISTS dd_measure_unit;
DROP TABLE IF EXISTS si_warehouse_record;
DROP TABLE IF EXISTS pda_storage_forklift;
DROP TABLE IF EXISTS sm_storage_record;
DROP TABLE IF EXISTS sm_storage_head;
DROP TABLE IF EXISTS si_warehouse;
DROP TABLE IF EXISTS dd_warehouse_type;
DROP TABLE IF EXISTS om_outbound_record;
DROP TABLE IF EXISTS si_client_record;
DROP TABLE IF EXISTS si_forklift_record;
DROP TABLE IF EXISTS pda_outbound_forklift;
DROP TABLE IF EXISTS si_forklift;
DROP TABLE IF EXISTS si_supplier_record;
DROP TABLE IF EXISTS si_supplier;
DROP TABLE IF EXISTS am_user;
DROP TABLE IF EXISTS am_dept;
DROP TABLE IF EXISTS am_role;
DROP TABLE IF EXISTS dd_location_type;
DROP TABLE IF EXISTS om_mix_head;
DROP TABLE IF EXISTS om_outbound_head;
DROP TABLE IF EXISTS si_client;




/* Create Tables */

CREATE TABLE am_authority
(
	authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
	authority_name char(255) NOT NULL,
	authority_path char(255) NOT NULL,
	-- 1£º²Ëµ¥   2£º°´Å¥
	type tinyint COMMENT '1£º²Ëµ¥   2£º°´Å¥',
	parent_id bigint unsigned,
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
	-- 1 Æô¶¯
	-- 2 ½ûÓÃ
	status tinyint COMMENT '1 Æô¶¯
2 ½ûÓÃ',
	create_time datetime,
	update_time datetime,
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
	PRIMARY KEY (dept_id),
	UNIQUE (dept_id)
);


CREATE TABLE am_dept_record
(
	dept_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
	dept_id bigint unsigned NOT NULL,
	user_id bigint unsigned NOT NULL,
	create_time datetime,
	operation_name char(255),
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
	-- 1£ºÆôÓÃ
	-- 2£º½ûÓÃ
	status tinyint COMMENT '1£ºÆôÓÃ
2£º½ûÓÃ',
	create_time datetime,
	update_time datetime,
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
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
	operation_name char(255),
	PRIMARY KEY (role_record_id)
);


CREATE TABLE am_user
(
	user_id bigint unsigned NOT NULL AUTO_INCREMENT,
	name char(32) NOT NULL,
	-- 1 ÄÐ
	-- 2 Å®
	sex tinyint COMMENT '1 ÄÐ
2 Å®',
	dept_id bigint unsigned NOT NULL,
	username char(32) NOT NULL,
	password char(128) NOT NULL,
	role_id bigint unsigned NOT NULL,
	job char(16),
	phone char(16),
	email char(255),
	-- 1 ÆôÓÃ
	-- 2 ½ûÓÃ
	status tinyint COMMENT '1 ÆôÓÃ
2 ½ûÓÃ',
	remark char(255),
	create_time datetime,
	update_time datetime,
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
	PRIMARY KEY (user_id),
	UNIQUE (user_id)
);


CREATE TABLE am_user_record
(
	user_record_id bigint NOT NULL AUTO_INCREMENT,
	user_id bigint unsigned NOT NULL,
	operate_id bigint unsigned NOT NULL,
	create_time datetime,
	operation_name char(255),
	PRIMARY KEY (user_record_id),
	UNIQUE (user_record_id),
	UNIQUE (operate_id)
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


CREATE TABLE dd_measure_unit
(
	measure_unit_id bigint unsigned NOT NULL AUTO_INCREMENT,
	measure_unit_name char(255) NOT NULL,
	user_id bigint unsigned,
	create_time datetime,
	PRIMARY KEY (measure_unit_id),
	UNIQUE (measure_unit_id)
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


CREATE TABLE om_mix_body
(
	mix_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
	mix_head_id bigint unsigned NOT NULL,
	material_id bigint unsigned,
	plan_num decimal(10,2),
	create_time datetime,
	-- 1 Î´É¾³ý
	-- 2 ÒÑÉ¾³ý
	dr tinyint COMMENT '1 Î´É¾³ý
2 ÒÑÉ¾³ý',
	PRIMARY KEY (mix_body_id),
	UNIQUE (mix_body_id),
	UNIQUE (mix_head_id)
);


CREATE TABLE om_mix_head
(
	mix_head_id bigint unsigned NOT NULL AUTO_INCREMENT,
	mix_no char(128) NOT NULL,
	plan_time date,
	create_time datetime,
	-- 1 Î´É¾³ý
	-- 2 ÒÑÉ¾³ý
	dr tinyint COMMENT '1 Î´É¾³ý
2 ÒÑÉ¾³ý',
	PRIMARY KEY (mix_head_id),
	UNIQUE (mix_head_id)
);


CREATE TABLE om_outbound_body
(
	outbound_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
	outbound_head_id bigint unsigned NOT NULL,
	material_id bigint unsigned NOT NULL,
	expect_num decimal(10,2),
	outbound_num decimal(10,2),
	create_time datetime,
	outbound_time datetime,
	-- 1 Î´É¾³ý
	-- 2 ÒÑÉ¾³ý
	dr tinyint COMMENT '1 Î´É¾³ý
2 ÒÑÉ¾³ý',
	PRIMARY KEY (outbound_body_id),
	UNIQUE (outbound_body_id)
);


CREATE TABLE om_outbound_head
(
	outbound_head_id bigint unsigned NOT NULL AUTO_INCREMENT,
	outbound_no char(128) NOT NULL,
	source_no char(128),
	--  
	source_type tinyint COMMENT ' ',
	plan_time date,
	outbound_time datetime,
	expect_num decimal(10,2),
	outbound_num decimal(10,2),
	extra char(255),
	-- 1£ºÒÑ³ö¿â
	-- 2£º³ö¿âÖÐ
	-- 3£º´ý³ö¿â
	status tinyint COMMENT '1£ºÒÑ³ö¿â
2£º³ö¿âÖÐ
3£º´ý³ö¿â',
	create_time datetime,
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
	PRIMARY KEY (outbound_head_id),
	UNIQUE (outbound_head_id)
);


CREATE TABLE om_outbound_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT,
	outbound_head_id bigint unsigned,
	user_id bigint unsigned,
	operation_name char(255),
	create_time datetime,
	PRIMARY KEY (record_id),
	UNIQUE (record_id)
);


CREATE TABLE pda_outbound_forklift
(
	outbound_forklift_id bigint NOT NULL,
	outbound_head_id bigint unsigned NOT NULL,
	forklift_id bigint unsigned NOT NULL,
	rfid char(128),
	PRIMARY KEY (outbound_forklift_id),
	UNIQUE (outbound_forklift_id),
	UNIQUE (outbound_head_id),
	UNIQUE (forklift_id)
);


CREATE TABLE pda_storage_forklift
(
	storage_forklift_id bigint NOT NULL,
	storage_head_id bigint unsigned NOT NULL,
	forklift_id bigint unsigned NOT NULL,
	rfid char(128),
	PRIMARY KEY (storage_forklift_id),
	UNIQUE (storage_forklift_id),
	UNIQUE (storage_head_id),
	UNIQUE (forklift_id)
);


CREATE TABLE si_client
(
	client_id bigint unsigned NOT NULL AUTO_INCREMENT,
	client_no char(128) NOT NULL,
	client_type_id bigint unsigned NOT NULL,
	client_name char(64) NOT NULL,
	contact char(64) NOT NULL,
	-- 1£ºÄÐ
	-- 2£ºÅ®
	sex tinyint COMMENT '1£ºÄÐ
2£ºÅ®',
	phone char(16) NOT NULL,
	email char(255),
	fax char(16),
	url char(64),
	credit_level_id bigint unsigned,
	area char(255),
	address char(128),
	remark char(255),
	create_time datetime,
	update_time datetime,
	-- 1 Î´É¾³ý
	-- 2 ÒÑÉ¾³ý
	dr tinyint COMMENT '1 Î´É¾³ý
2 ÒÑÉ¾³ý',
	PRIMARY KEY (client_id),
	UNIQUE (client_id)
);


CREATE TABLE si_client_record
(
	client_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
	client_id bigint unsigned NOT NULL,
	user_id bigint unsigned NOT NULL,
	create_time datetime,
	type char(255),
	PRIMARY KEY (client_record_id),
	UNIQUE (client_record_id),
	UNIQUE (user_id)
);


CREATE TABLE si_forklift
(
	forklift_id bigint unsigned NOT NULL AUTO_INCREMENT,
	forklift_no char(128),
	forklift_model char(255),
	forklift_brand char(255),
	supplier_id bigint unsigned NOT NULL,
	contact char(64),
	contact_phone char(16),
	imei_no char(255),
	-- 1 Ô­²ÄÁÏÇø
	-- 2 Éú²ú²úÇø
	-- 3 ³ÉÆ·Çø
	work_area tinyint COMMENT '1 Ô­²ÄÁÏÇø
2 Éú²ú²úÇø
3 ³ÉÆ·Çø',
	-- 1 Ã¦ÂµÖÐ
	-- 2 ¿ÕÏÐÖÐ
	-- 3 ²»ÔÚÏß
	status tinyint COMMENT '1 Ã¦ÂµÖÐ
2 ¿ÕÏÐÖÐ
3 ²»ÔÚÏß',
	extra char(255),
	create_time datetime,
	-- 1 Î´É¾³ý
	-- 2 ÒÑÉ¾³ý
	dr tinyint COMMENT '1 Î´É¾³ý
2 ÒÑÉ¾³ý',
	PRIMARY KEY (forklift_id),
	UNIQUE (forklift_id)
);


CREATE TABLE si_forklift_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT,
	forklift_id bigint unsigned NOT NULL,
	user_id bigint unsigned NOT NULL,
	operation_name char(255),
	create_time datetime,
	PRIMARY KEY (record_id),
	UNIQUE (record_id),
	UNIQUE (forklift_id),
	UNIQUE (user_id)
);


CREATE TABLE si_location
(
	location_id bigint unsigned NOT NULL AUTO_INCREMENT,
	location_no char(128) NOT NULL,
	location_name char(255) NOT NULL,
	hold_tray_num int,
	warehouse_id bigint unsigned NOT NULL,
	material_id bigint unsigned,
	location_type_id bigint unsigned NOT NULL,
	remark char(255),
	user_id bigint unsigned,
	create_time datetime,
	update_time datetime,
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
	PRIMARY KEY (location_id),
	UNIQUE (location_id),
	UNIQUE (location_type_id)
);


CREATE TABLE si_location_record
(
	location_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
	location_id bigint unsigned NOT NULL,
	user_id bigint unsigned NOT NULL,
	create_time datetime,
	operation_name char(255),
	PRIMARY KEY (location_record_id),
	UNIQUE (location_record_id)
);


CREATE TABLE si_material
(
	material_id bigint unsigned NOT NULL AUTO_INCREMENT,
	material_no char(128) NOT NULL,
	-- 1£ºÔ­²ÄÁÏ
	-- 2£º³ÉÆ·
	material_type tinyint COMMENT '1£ºÔ­²ÄÁÏ
2£º³ÉÆ·',
	material_name char(64) NOT NULL,
	material_level char(64),
	material_model char(64),
	measure_unit_id bigint unsigned,
	package_volume decimal(10,2),
	supplier_id bigint unsigned,
	material_desc char(255),
	user_id bigint unsigned,
	create_time datetime,
	update_time datetime,
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
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
	UNIQUE (material_record_id),
	UNIQUE (material_id),
	UNIQUE (user_id)
);


CREATE TABLE si_supplier
(
	supplier_id bigint unsigned NOT NULL AUTO_INCREMENT,
	supplier_no char(64) NOT NULL,
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
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
	PRIMARY KEY (supplier_id),
	UNIQUE (supplier_id)
);


CREATE TABLE si_supplier_record
(
	supplier_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
	supplier_id bigint unsigned NOT NULL,
	user_id bigint unsigned NOT NULL,
	create_time datetime,
	operation_name char(255),
	PRIMARY KEY (supplier_record_id),
	UNIQUE (supplier_record_id)
);


CREATE TABLE si_warehouse
(
	warehouse_id bigint unsigned NOT NULL AUTO_INCREMENT,
	warehouse_no char(128) NOT NULL,
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
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
	PRIMARY KEY (warehouse_id),
	UNIQUE (warehouse_id)
);


CREATE TABLE si_warehouse_record
(
	warehouse_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
	warehouse_id bigint unsigned NOT NULL,
	user_id bigint unsigned NOT NULL,
	create_time datetime,
	operation_name char(255),
	PRIMARY KEY (warehouse_record_id),
	UNIQUE (warehouse_record_id)
);


CREATE TABLE sm_storage_body
(
	storage_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
	storage_head_id bigint unsigned NOT NULL,
	material_id bigint unsigned,
	location_id bigint unsigned,
	car_brand  char(255),
	expect_num decimal(10,2),
	storage_num decimal(10,2),
	accept_time datetime,
	create_time datetime,
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
	PRIMARY KEY (storage_body_id),
	UNIQUE (storage_body_id)
);


CREATE TABLE sm_storage_detail
(
	storage_id bigint unsigned NOT NULL AUTO_INCREMENT,
	storage_body_id bigint unsigned NOT NULL,
	location_id bigint unsigned NOT NULL,
	storage_num decimal(10,2),
	storage_time datetime,
	rfid char(128) NOT NULL,
	PRIMARY KEY (storage_id),
	UNIQUE (storage_id),
	UNIQUE (rfid)
);


CREATE TABLE sm_storage_head
(
	storage_head_id bigint unsigned NOT NULL AUTO_INCREMENT,
	warehouse_id bigint unsigned NOT NULL,
	storage_no char(128) NOT NULL,
	source_no char(128),
	--  
	source_type tinyint COMMENT ' ',
	storage_time datetime,
	expect_num decimal(10,2),
	storage_num decimal(10,2),
	extra char(255),
	-- 1£ºÒÑÈë¿â
	-- 2£ºÈë¿âÖÐ
	-- 3£º´ýÈë¿â
	-- 
	-- 
	status tinyint COMMENT '1£ºÒÑÈë¿â
2£ºÈë¿âÖÐ
3£º´ýÈë¿â

',
	create_time datetime,
	-- 1£ºÎ´É¾³ý
	-- 2£ºÒÑÉ¾³ý
	dr tinyint COMMENT '1£ºÎ´É¾³ý
2£ºÒÑÉ¾³ý',
	PRIMARY KEY (storage_head_id),
	UNIQUE (storage_head_id)
);


CREATE TABLE sm_storage_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT,
	storage_head_id bigint unsigned,
	user_id bigint unsigned,
	operation_name char(255),
	create_time datetime,
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


ALTER TABLE pda_outbound_forklift
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


ALTER TABLE pda_outbound_forklift
	ADD FOREIGN KEY (forklift_id)
	REFERENCES si_forklift (forklift_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE pda_storage_forklift
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


ALTER TABLE si_location
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


ALTER TABLE sm_storage_detail
	ADD FOREIGN KEY (storage_body_id)
	REFERENCES sm_storage_body (storage_body_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE pda_storage_forklift
	ADD FOREIGN KEY (storage_head_id)
	REFERENCES sm_storage_head (storage_head_id)
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



