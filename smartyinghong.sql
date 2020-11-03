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
DROP TABLE IF EXISTS om_mix_head;
DROP TABLE IF EXISTS om_outbound_forklift;
DROP TABLE IF EXISTS om_outbound_head;
DROP TABLE IF EXISTS si_client;
DROP TABLE IF EXISTS si_forklift;




/* Create Tables */

-- Ȩ�ޱ�
CREATE TABLE am_authority
(
	authority_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'Ȩ��ID',
	authority_name char(255) NOT NULL COMMENT 'Ȩ������',
	authority_path char(255) NOT NULL COMMENT 'Ȩ��·��',
	-- 1���˵�   2����ť
	type tinyint COMMENT '���� : 1���˵�   2����ť',
	parent_id bigint unsigned COMMENT '�ϼ�Ȩ��ID',
	PRIMARY KEY (authority_id),
	UNIQUE (authority_id),
	UNIQUE (authority_path)
) COMMENT = 'Ȩ�ޱ�';


-- ���ű�
CREATE TABLE am_dept
(
	dept_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '����id',
	parent_id bigint unsigned COMMENT '�ϼ�����id',
	dept_name char(32) NOT NULL COMMENT '��������',
	user_id bigint unsigned COMMENT '���Ÿ�����',
	dept_desc char(255) COMMENT '��������',
	-- 1 ����
	-- 2 ����
	status tinyint COMMENT '״̬ : 1 ����
2 ����',
	create_time datetime COMMENT '����ʱ��',
	update_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (dept_id),
	UNIQUE (dept_id)
) COMMENT = '���ű�';


-- ���ż�¼��
CREATE TABLE am_dept_record
(
	dept_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '���ż�¼ID',
	dept_id bigint unsigned NOT NULL COMMENT '����id',
	user_id bigint unsigned NOT NULL COMMENT '�û�ID',
	create_time datetime COMMENT '����ʱ��',
	operation_name char(255) COMMENT '��������',
	PRIMARY KEY (dept_record_id),
	UNIQUE (dept_record_id)
) COMMENT = '���ż�¼��';


-- �û�Ȩ���м��
CREATE TABLE am_m_user_authority
(
	user_authority_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '�û�Ȩ��ID',
	user_id bigint unsigned NOT NULL COMMENT '�û�ID',
	authority_id bigint unsigned NOT NULL COMMENT 'Ȩ��ID',
	PRIMARY KEY (user_authority_id),
	UNIQUE (user_authority_id)
) COMMENT = '�û�Ȩ���м��';


-- ��ɫ��
CREATE TABLE am_role
(
	role_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��ɫID',
	role_name char(32) NOT NULL COMMENT '��ɫ����',
	role_desc char(255) COMMENT '��ɫ����',
	-- 1������
	-- 2������
	status tinyint COMMENT '״̬ : 1������
2������',
	create_time datetime COMMENT '����ʱ��',
	update_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (role_id),
	UNIQUE (role_id)
) COMMENT = '��ɫ��';


-- ��ɫȨ�ޱ�
CREATE TABLE am_role_authority
(
	role_authority_id bigint NOT NULL AUTO_INCREMENT COMMENT '��ɫ�˵�id',
	role_id bigint unsigned NOT NULL COMMENT '��ɫID',
	authority_id bigint unsigned NOT NULL COMMENT 'Ȩ��ID',
	PRIMARY KEY (role_authority_id),
	UNIQUE (role_authority_id)
) COMMENT = '��ɫȨ�ޱ�';


-- ��ɫ��¼��
CREATE TABLE am_role_record
(
	role_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��ɫ��¼ID',
	role_id bigint unsigned NOT NULL COMMENT '��ɫID',
	user_id bigint unsigned NOT NULL COMMENT '�û�ID',
	create_time datetime COMMENT '����ʱ��',
	operation_name char(255) COMMENT '��������',
	PRIMARY KEY (role_record_id)
) COMMENT = '��ɫ��¼��';


-- �û���
CREATE TABLE am_user
(
	user_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '�û�ID',
	name char(32) NOT NULL COMMENT '�û�����',
	-- 1 ��
	-- 2 Ů
	sex tinyint COMMENT '�Ա� : 1 ��
2 Ů',
	dept_id bigint unsigned NOT NULL COMMENT '����id',
	username char(32) NOT NULL COMMENT '��¼��',
	password char(128) NOT NULL COMMENT '����',
	role_id bigint unsigned NOT NULL COMMENT '��ɫID',
	job char(16) COMMENT '������λ',
	phone char(16) COMMENT '�绰����',
	email char(255) COMMENT '�û�����',
	-- 1 ����
	-- 2 ����
	status tinyint COMMENT '״̬ : 1 ����
2 ����',
	remark char(255) COMMENT '��ע',
	create_time datetime COMMENT '����ʱ��',
	update_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (user_id),
	UNIQUE (user_id)
) COMMENT = '�û���';


-- �û�������¼��
CREATE TABLE am_user_record
(
	user_record_id bigint NOT NULL AUTO_INCREMENT COMMENT '�û���¼ID',
	user_id bigint unsigned NOT NULL COMMENT '�û�ID',
	operate_id bigint unsigned NOT NULL COMMENT '������ID',
	create_time datetime COMMENT '����ʱ��',
	operation_name char(255) COMMENT '��������',
	PRIMARY KEY (user_record_id),
	UNIQUE (user_record_id),
	UNIQUE (operate_id)
) COMMENT = '�û�������¼��';


-- ��λ����
CREATE TABLE dd_location_type
(
	location_type_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��λ����ID',
	location_type_name char(255) NOT NULL COMMENT '��λ��������',
	user_id bigint unsigned COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	PRIMARY KEY (location_type_id),
	UNIQUE (location_type_id)
) COMMENT = '��λ����';


-- ������λ
CREATE TABLE dd_measure_unit
(
	measure_unit_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '������λID',
	measure_unit_name char(255) NOT NULL COMMENT '������λ����',
	user_id bigint unsigned COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	PRIMARY KEY (measure_unit_id),
	UNIQUE (measure_unit_id)
) COMMENT = '������λ';


-- �ֿ����ͱ�
CREATE TABLE dd_warehouse_type
(
	warehouse_type_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '�ֿ�����ID',
	warehouse_type_name char(64) NOT NULL COMMENT '�ֿ���������',
	user_id bigint unsigned COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	PRIMARY KEY (warehouse_type_id),
	UNIQUE (warehouse_type_id)
) COMMENT = '�ֿ����ͱ�';


-- ���ϵ�����
CREATE TABLE om_mix_body
(
	mix_body_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '���ϵ�����id',
	mix_head_id bigint unsigned NOT NULL COMMENT '���ϵ���ͷid',
	material_id bigint unsigned COMMENT '����ID',
	plan_num decimal(10,2) COMMENT '�ƻ���������',
	create_time datetime COMMENT '����ʱ��',
	-- 1 δɾ��
	-- 2 ��ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1 δɾ��
2 ��ɾ��',
	PRIMARY KEY (mix_body_id),
	UNIQUE (mix_body_id),
	UNIQUE (mix_head_id)
) COMMENT = '���ϵ�����';


-- ���ϵ���ͷ
CREATE TABLE om_mix_head
(
	mix_head_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '���ϵ���ͷid',
	mix_no char(128) NOT NULL COMMENT '���ϵ�����',
	plan_time date COMMENT '�ƻ�����ʱ��',
	create_time datetime COMMENT '����ʱ��',
	-- 1 δɾ��
	-- 2 ��ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1 δɾ��
2 ��ɾ��',
	PRIMARY KEY (mix_head_id),
	UNIQUE (mix_head_id)
) COMMENT = '���ϵ���ͷ';


-- ���ⵥ����
CREATE TABLE om_outbound_body
(
	outbound_body_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '���ⵥ����id',
	outbound_head_id bigint unsigned NOT NULL COMMENT '���ⵥ��ͷID',
	material_id bigint unsigned NOT NULL COMMENT '����ID',
	expect_num decimal(10,2) COMMENT '������������',
	outbound_num decimal(10,2) COMMENT '�ѳ�������',
	create_time datetime COMMENT '����ʱ��',
	outbound_time datetime COMMENT '����ʱ��',
	-- 1 δɾ��
	-- 2 ��ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1 δɾ��
2 ��ɾ��',
	PRIMARY KEY (outbound_body_id),
	UNIQUE (outbound_body_id)
) COMMENT = '���ⵥ����';


-- ����泵��
CREATE TABLE om_outbound_forklift
(
	outbound_forklift_id bigint NOT NULL COMMENT '����泵id',
	outbound_head_id bigint unsigned NOT NULL COMMENT '���ⵥ��ͷID',
	forklift_id bigint unsigned NOT NULL COMMENT '�泵id',
	rfid char(128) COMMENT 'RFID',
	PRIMARY KEY (outbound_forklift_id),
	UNIQUE (outbound_forklift_id)
) COMMENT = '����泵��';


-- ���ⵥ��ͷ
CREATE TABLE om_outbound_head
(
	outbound_head_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '���ⵥ��ͷID',
	outbound_no char(128) NOT NULL COMMENT '���ⵥ����',
	source_no char(128) COMMENT '��Դ����',
	-- 1 ԭ���ϳ���
	-- 2 ���۳���
	-- 3 ����������
	source_type tinyint COMMENT '��Դ���� : 1 ԭ���ϳ���
2 ���۳���
3 ����������',
	plan_time date COMMENT '�ƻ�����ʱ��',
	outbound_time datetime COMMENT '��ɳ���ʱ��',
	expect_num decimal(10,2) COMMENT '����������',
	outbound_num decimal(10,2) COMMENT '�Ѿ���������',
	extra char(255) COMMENT '��ע',
	-- 1���ѳ���
	-- 2��������
	-- 3��������
	status tinyint COMMENT '״̬ : 1���ѳ���
2��������
3��������',
	create_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (outbound_head_id),
	UNIQUE (outbound_head_id)
) COMMENT = '���ⵥ��ͷ';


-- ���������¼
CREATE TABLE om_outbound_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '������¼',
	outbound_head_id bigint unsigned COMMENT '���ⵥ��ͷID',
	user_id bigint unsigned COMMENT '������ID',
	operation_name char(255) COMMENT '��������',
	create_time datetime COMMENT '����ʱ��',
	PRIMARY KEY (record_id),
	UNIQUE (record_id)
) COMMENT = '���������¼';


-- �ͻ�
CREATE TABLE si_client
(
	client_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '�ͻ�id',
	client_no char(128) NOT NULL COMMENT '�ͻ�����',
	client_type_id bigint unsigned NOT NULL COMMENT '�ͻ�����ID',
	client_name char(64) NOT NULL COMMENT '�ͻ�����',
	contact char(64) NOT NULL COMMENT '��ϵ��',
	-- 1����
	-- 2��Ů
	sex tinyint COMMENT '��ϵ���Ա� : 1����
2��Ů',
	phone char(16) NOT NULL COMMENT '��ϵ�绰',
	email char(255) COMMENT '�ͻ�����',
	fax char(16) COMMENT '����',
	url char(64) COMMENT '��ַ',
	credit_level_id bigint unsigned COMMENT '���õȼ�id',
	area char(255) COMMENT '����',
	address char(128) COMMENT '��ϸ��ַ',
	remark char(255) COMMENT '��ע',
	create_time datetime COMMENT '����ʱ��',
	update_time datetime COMMENT '����ʱ��',
	-- 1 δɾ��
	-- 2 ��ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1 δɾ��
2 ��ɾ��',
	PRIMARY KEY (client_id),
	UNIQUE (client_id)
) COMMENT = '�ͻ�';


-- �ͻ�������¼��
CREATE TABLE si_client_record
(
	client_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '������¼ID',
	client_id bigint unsigned NOT NULL COMMENT '�ͻ�id',
	user_id bigint unsigned NOT NULL COMMENT '�û�ID',
	create_time datetime COMMENT '����ʱ��',
	type char(255) COMMENT '��������',
	PRIMARY KEY (client_record_id),
	UNIQUE (client_record_id),
	UNIQUE (user_id)
) COMMENT = '�ͻ�������¼��';


-- �泵��Ϣ
CREATE TABLE si_forklift
(
	forklift_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '�泵id',
	forklift_no char(128) COMMENT '�泵���',
	forklift_name char(64) COMMENT '�泵����',
	forklift_model char(255) COMMENT '�泵�ͺ�',
	forklift_brand char(255) COMMENT 'Ʒ��',
	contact char(64) COMMENT '��ϵ��',
	contact_phone char(16) COMMENT '��ϵ�绰',
	imei_no char(255) COMMENT '��ҵһ�����',
	-- 1 ԭ������
	-- 2 ��Ʒ�����
	-- 3 ��Ʒ������
	work_area tinyint COMMENT '��ҵ���� : 1 ԭ������
2 ��Ʒ�����
3 ��Ʒ������',
	supplier_name char(64) COMMENT '��Ӧ������',
	-- 1 æµ��
	-- 2 ������
	--  
	status tinyint COMMENT '��ǰ״̬ : 1 æµ��
2 ������
 ',
	extra char(255) COMMENT '��ע',
	create_time datetime COMMENT '����ʱ��',
	-- 1 δɾ��
	-- 2 ��ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1 δɾ��
2 ��ɾ��',
	PRIMARY KEY (forklift_id),
	UNIQUE (forklift_id)
) COMMENT = '�泵��Ϣ';


-- �泵������¼��
CREATE TABLE si_forklift_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '������¼id',
	forklift_id bigint unsigned NOT NULL COMMENT '�泵id',
	user_id bigint unsigned NOT NULL COMMENT '������ID',
	operation_name char(255) COMMENT '��������',
	create_time datetime COMMENT '����ʱ��',
	PRIMARY KEY (record_id),
	UNIQUE (record_id),
	UNIQUE (forklift_id),
	UNIQUE (user_id)
) COMMENT = '�泵������¼��';


-- ��λ��
CREATE TABLE si_location
(
	location_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��λID',
	warehouse_id bigint unsigned NOT NULL COMMENT '�ֿ�ID',
	material_id bigint unsigned COMMENT '����ID',
	location_type_id bigint unsigned NOT NULL COMMENT '��λ����ID',
	user_id bigint unsigned COMMENT '������',
	location_no char(128) NOT NULL COMMENT '��λ���',
	location_name char(255) NOT NULL COMMENT '��λ����',
	hold_tray_num int COMMENT '������������',
	-- �ִ�����
	exist_num int COMMENT '�ִ����� : �ִ�����',
	remark char(255) COMMENT '��ע',
	create_time datetime COMMENT '����ʱ��',
	update_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (location_id),
	UNIQUE (location_id),
	UNIQUE (location_type_id)
) COMMENT = '��λ��';


-- ��λ��¼��
CREATE TABLE si_location_record
(
	location_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��λ��¼ID',
	location_id bigint unsigned NOT NULL COMMENT '��λID',
	user_id bigint unsigned NOT NULL COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	operation_name char(255) COMMENT '��������',
	PRIMARY KEY (location_record_id),
	UNIQUE (location_record_id)
) COMMENT = '��λ��¼��';


-- ���ϱ�
CREATE TABLE si_material
(
	material_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '����ID',
	material_no char(128) NOT NULL COMMENT '���ϱ��',
	-- 1��ԭ����
	-- 2����Ʒ
	material_type tinyint COMMENT '�������� : 1��ԭ����
2����Ʒ',
	material_name char(64) NOT NULL COMMENT '��������',
	material_level char(64) COMMENT '���ϵȼ�',
	material_model char(64) COMMENT '����ͺ�',
	measure_unit_id bigint unsigned COMMENT '������λID',
	package_volume decimal(10,2) COMMENT '��װ���',
	supplier_id bigint unsigned COMMENT '��Ӧ��ID',
	material_desc char(255) COMMENT '��������',
	user_id bigint unsigned COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	update_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (material_id),
	UNIQUE (material_id)
) COMMENT = '���ϱ�';


-- ���ϼ�¼��
CREATE TABLE si_material_record
(
	material_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '���ϼ�¼ID',
	material_id bigint unsigned NOT NULL COMMENT '����ID',
	user_id bigint unsigned NOT NULL COMMENT '������ID',
	create_time datetime COMMENT '����ʱ��',
	type char(255) COMMENT '��������',
	PRIMARY KEY (material_record_id),
	UNIQUE (material_record_id),
	UNIQUE (material_id),
	UNIQUE (user_id)
) COMMENT = '���ϼ�¼��';


-- ��Ӧ�̱�
CREATE TABLE si_supplier
(
	supplier_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��Ӧ��ID',
	supplier_no char(64) NOT NULL COMMENT '��Ӧ�̱���',
	supplier_name char(64) NOT NULL COMMENT '��Ӧ������',
	contact_name char(64) COMMENT '��ϵ������',
	phone char(16) COMMENT '��ϵ�绰',
	fax char(16) COMMENT '����',
	site char(64) COMMENT '��ַ',
	mail char(64) COMMENT '����',
	area char(64) COMMENT '����',
	address char(128) COMMENT '��ϸ��ַ',
	remark char(255) COMMENT '��ע',
	user_id bigint unsigned COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	update_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (supplier_id),
	UNIQUE (supplier_id)
) COMMENT = '��Ӧ�̱�';


-- ��Ӧ�̼�¼��
CREATE TABLE si_supplier_record
(
	supplier_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��Ӧ�̼�¼ID',
	supplier_id bigint unsigned NOT NULL COMMENT '��Ӧ��ID',
	user_id bigint unsigned NOT NULL COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	operation_name char(255) COMMENT '��������',
	PRIMARY KEY (supplier_record_id),
	UNIQUE (supplier_record_id)
) COMMENT = '��Ӧ�̼�¼��';


-- �ֿ��
CREATE TABLE si_warehouse
(
	warehouse_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '�ֿ�ID',
	warehouse_no char(128) NOT NULL COMMENT '�ֿ���',
	warehouse_name char(64) NOT NULL COMMENT '�ֿ�����',
	warehouse_type_id bigint unsigned NOT NULL COMMENT '�ֿ�����ID',
	principal char(32) COMMENT '������',
	phone char(16) COMMENT '��ϵ�绰',
	area char(64) COMMENT '����',
	address char(128) COMMENT '��ϸ��ַ',
	remark char(255) COMMENT '��ע',
	user_id bigint unsigned COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	update_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (warehouse_id),
	UNIQUE (warehouse_id)
) COMMENT = '�ֿ��';


-- �ֿ��¼��
CREATE TABLE si_warehouse_record
(
	warehouse_record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '�ֿ��¼��',
	warehouse_id bigint unsigned NOT NULL COMMENT '�ֿ�ID',
	user_id bigint unsigned NOT NULL COMMENT '������',
	create_time datetime COMMENT '����ʱ��',
	operation_name char(255) COMMENT '��������',
	PRIMARY KEY (warehouse_record_id),
	UNIQUE (warehouse_record_id)
) COMMENT = '�ֿ��¼��';


-- ��ⵥ����
CREATE TABLE sm_storage_body
(
	storage_body_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��ⵥ����ID',
	storage_head_id bigint unsigned NOT NULL COMMENT '��ⵥ��ͷID',
	material_id bigint unsigned COMMENT '����ID',
	location_id bigint unsigned COMMENT '��λID',
	car_brand  char(255) COMMENT '������Ϣ',
	expect_num decimal(10,2) COMMENT '�����������',
	storage_num decimal(10,2) COMMENT '�Ѿ��������',
	accept_time datetime COMMENT '��������',
	create_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (storage_body_id),
	UNIQUE (storage_body_id)
) COMMENT = '��ⵥ����';


-- �����ϸ��
CREATE TABLE sm_storage_detail
(
	storage_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��ⵥID',
	storage_body_id bigint unsigned NOT NULL COMMENT '��ⵥ����ID',
	location_id bigint unsigned NOT NULL COMMENT '��λID',
	material_id bigint unsigned NOT NULL COMMENT '����ID',
	storage_num decimal(10,2) COMMENT '�����',
	storage_time datetime COMMENT '���ʱ��',
	rfid char(128) NOT NULL COMMENT 'ջ��RFID',
	-- ���״̬��1.�����  2.�ѳ���
	storage_status tinyint COMMENT '���״̬ : ���״̬��1.�����  2.�ѳ���',
	PRIMARY KEY (storage_id),
	UNIQUE (storage_id),
	UNIQUE (material_id),
	UNIQUE (rfid)
) COMMENT = '�����ϸ��';


-- ���泵��
CREATE TABLE sm_storage_forklift
(
	storage_forklift_id bigint NOT NULL COMMENT '���泵id',
	storage_head_id bigint unsigned NOT NULL COMMENT '��ⵥ��ͷID',
	forklift_id bigint unsigned NOT NULL COMMENT '�泵id',
	rfid char(128) COMMENT '��ǰ������RFID',
	PRIMARY KEY (storage_forklift_id),
	UNIQUE (storage_forklift_id)
) COMMENT = '���泵��';


-- ��ⵥ��ͷ
CREATE TABLE sm_storage_head
(
	storage_head_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '��ⵥ��ͷID',
	warehouse_id bigint unsigned NOT NULL COMMENT '�ֿ�ID',
	storage_no char(128) NOT NULL COMMENT '��ⵥ���',
	source_no char(128) COMMENT '��Դ����',
	-- 1 ԭ�������
	-- 2 �������
	-- 3 ���������
	source_type tinyint COMMENT '��Դ���� : 1 ԭ�������
2 �������
3 ���������',
	storage_time datetime COMMENT '���ʱ��',
	expect_num decimal(10,2) COMMENT '���������',
	storage_num decimal(10,2) COMMENT '���������',
	extra char(255) COMMENT '��ע',
	-- 1�������
	-- 2�������
	-- 3�������
	-- 
	-- 
	status tinyint COMMENT '���״̬ : 1�������
2�������
3�������

',
	create_time datetime COMMENT '����ʱ��',
	-- 1��δɾ��
	-- 2����ɾ��
	dr tinyint COMMENT '�Ƿ�ɾ�� : 1��δɾ��
2����ɾ��',
	PRIMARY KEY (storage_head_id),
	UNIQUE (storage_head_id)
) COMMENT = '��ⵥ��ͷ';


-- ��������¼��
CREATE TABLE sm_storage_record
(
	record_id bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '������¼',
	storage_head_id bigint unsigned COMMENT '��ⵥ��ͷID',
	user_id bigint unsigned COMMENT '������ID',
	operation_name char(255) COMMENT '��������',
	create_time datetime COMMENT '����ʱ��',
	PRIMARY KEY (record_id),
	UNIQUE (record_id)
) COMMENT = '��������¼��';



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


ALTER TABLE sm_storage_detail
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


ALTER TABLE sm_storage_body
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



