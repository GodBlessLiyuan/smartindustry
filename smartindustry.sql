SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS ba_m_user_authority;
DROP TABLE IF EXISTS ba_role_authority;
DROP TABLE IF EXISTS ba_authority;
DROP TABLE IF EXISTS ba_user_role;
DROP TABLE IF EXISTS ba_role;
DROP TABLE IF EXISTS om_outbound_record;
DROP TABLE IF EXISTS si_label_record;
DROP TABLE IF EXISTS om_pick_label;
DROP TABLE IF EXISTS om_label_recommend;
DROP TABLE IF EXISTS si_storage_label;
DROP TABLE IF EXISTS sm_receipt_label;
DROP TABLE IF EXISTS sm_storage_detail;
DROP TABLE IF EXISTS si_print_label;
DROP TABLE IF EXISTS sm_storage_group;
DROP TABLE IF EXISTS si_location;
DROP TABLE IF EXISTS sm_storage_record;
DROP TABLE IF EXISTS ba_user;
DROP TABLE IF EXISTS om_logistics_picture;
DROP TABLE IF EXISTS om_logistics_record;
DROP TABLE IF EXISTS om_outbound;
DROP TABLE IF EXISTS om_pick_body;
DROP TABLE IF EXISTS om_pick_check;
DROP TABLE IF EXISTS om_pick_head;
DROP TABLE IF EXISTS sm_iqc_detect;
DROP TABLE IF EXISTS sm_qe_confirm;
DROP TABLE IF EXISTS sm_qe_detect;
DROP TABLE IF EXISTS sm_storage;
DROP TABLE IF EXISTS sm_receipt_body;
DROP TABLE IF EXISTS si_material;
DROP TABLE IF EXISTS sm_receipt_head;




/* Create Tables */

CREATE TABLE ba_authority
(
    authority_id bigint unsigned NOT NULL AUTO_INCREMENT,
    code char(255) NOT NULL,
    name char(255) NOT NULL,
    create_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
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
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
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
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
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
    -- 1������
    -- 2���ĸ�
    ship_way tinyint COMMENT '1������
2���ĸ�',
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
    -- 1���ѳ���
    -- 3��������
    status tinyint COMMENT '1���ѳ���
3��������',
    create_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
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
    -- 1��δ����
    -- 5�����ϼ��
    -- 10���������|OQC����
    -- 15�������׷���
    -- 20��ȡ���������˻��ֿ�
    -- 25�����ϳ���
    -- 30����ɳ���
    -- 35��ȷ�ϳ���
    status tinyint COMMENT '1��δ����
5�����ϼ��
10���������|OQC����
15�������׷���
20��ȡ���������˻��ֿ�
25�����ϳ���
30����ɳ���
35��ȷ�ϳ���',
    PRIMARY KEY (record_id),
    UNIQUE (record_id)
);


CREATE TABLE om_pick_body
(
    pick_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_head_id bigint unsigned NOT NULL,
    material_no char(32) NOT NULL,
    demand_num int,
    pick_num int,
    create_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
    PRIMARY KEY (pick_body_id),
    UNIQUE (pick_body_id)
);


CREATE TABLE om_pick_check
(
    pick_head_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1��ͬ��
    -- 2������-ȡ���������˻زֿ�
    -- 3�������
    -- 4������-�����׷���
    status tinyint COMMENT '1��ͬ��
2������-ȡ���������˻زֿ�
3�������
4������-�����׷���',
    PRIMARY KEY (pick_head_id),
    UNIQUE (pick_head_id)
);


CREATE TABLE om_pick_head
(
    pick_head_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_no char(64) NOT NULL,
    order_no char(32) NOT NULL,
    -- 1������
    -- 2�����۶���
    order_type tinyint NOT NULL COMMENT '1������
2�����۶���',
    -- 1�����Ƽ�
    -- 5��δ����
    -- 10�����ϼ��
    -- 15���������|OQC����
    -- 20�������׷���
    -- 25��ȡ���������˻��ֿ�
    -- 30�����ϳ���
    -- 35����ɳ���
    -- 40��ȷ�ϳ���
    material_status tinyint COMMENT '1�����Ƽ�
5��δ����
10�����ϼ��
15���������|OQC����
20�������׷���
25��ȡ���������˻��ֿ�
30�����ϳ���
35����ɳ���
40��ȷ�ϳ���',
    correspond_project char(255),
    plan_time datetime,
    outbound_time datetime,
    -- 1��ȫ������
    -- 2��Ƿ�ϳ���
    -- 3��δ����
    outbound_status tinyint COMMENT '1��ȫ������
2��Ƿ�ϳ���
3��δ����',
    create_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
    PRIMARY KEY (pick_head_id),
    UNIQUE (pick_head_id),
    UNIQUE (pick_no)
);


CREATE TABLE om_pick_label
(
    pick_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    pick_head_id bigint unsigned NOT NULL,
    print_label_id bigint unsigned NOT NULL,
    -- 1����
    -- 2����
    recommend tinyint COMMENT '1����
2����',
    PRIMARY KEY (pick_label_id),
    UNIQUE (pick_label_id)
);


CREATE TABLE si_label_record
(
    label_record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    print_label_id bigint unsigned NOT NULL,
    user_id bigint unsigned NOT NULL,
    name char(255),
    -- 1��������
    -- 2���������
    module tinyint COMMENT '1��������
2���������',
    create_time datetime,
    PRIMARY KEY (label_record_id),
    UNIQUE (label_record_id)
);


CREATE TABLE si_location
(
    location_no char(32) NOT NULL,
    location_code char(32),
    location_name char(255),
    location_type tinyint,
    user_id bigint unsigned NOT NULL,
    create_time datetime,
    update_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
    PRIMARY KEY (location_no),
    UNIQUE (location_no)
);


CREATE TABLE si_material
(
    material_no char(32) NOT NULL,
    material_name char(255) NOT NULL,
    -- 1��ԭ�ϣ�2�����Ʒ��3����Ʒ
    material_type tinyint NOT NULL COMMENT '1��ԭ�ϣ�2�����Ʒ��3����Ʒ',
    material_model char(255),
    material_desc char(255),
    test_type tinyint,
    create_time datetime,
    update_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
    PRIMARY KEY (material_no),
    UNIQUE (material_no)
);


CREATE TABLE si_print_label
(
    print_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    package_id char(32) NOT NULL,
    produce_date char(32),
    produce_batch char(32),
    num int,
    -- 1����Ʒ
    -- 2������Ʒ
    type tinyint COMMENT '1����Ʒ
2������Ʒ',
    -- 1��ɨ��
    -- 2����ӡ
    origin tinyint NOT NULL COMMENT '1��ɨ��
2����ӡ',
    material_no char(32) NOT NULL,
    location_no char(32),
    relate_label_id bigint unsigned,
    relate_package_id char(32),
    create_time datetime,
    -- 1��δ����
    -- 2���ѷ���
    dr tinyint COMMENT '1��δ����
2���ѷ���',
    PRIMARY KEY (print_label_id),
    UNIQUE (print_label_id),
    UNIQUE (package_id)
);


CREATE TABLE si_storage_label
(
    storage_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    location_no char(32) NOT NULL,
    material_no char(32) NOT NULL,
    print_label_id bigint unsigned NOT NULL,
    package_id char(32),
    order_no char(32),
    -- 1��PO������
    -- 2����Ʒ�ɹ�
    -- 3����������
    order_type tinyint COMMENT '1��PO������
2����Ʒ�ɹ�
3����������',
    -- 1����Ʒ
    -- 2������Ʒ
    type tinyint COMMENT '1����Ʒ
2������Ʒ',
    storage_num int,
    storage_time datetime,
    PRIMARY KEY (storage_label_id),
    UNIQUE (storage_label_id),
    UNIQUE (print_label_id)
);


CREATE TABLE sm_iqc_detect
(
    receipt_body_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1��������Ʒ
    -- 2��QE�����ؼ���
    -- 3��δ����
    status tinyint COMMENT '1��������Ʒ
2��QE�����ؼ���
3��δ����',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_qe_confirm
(
    receipt_body_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1������
    -- 3����������ȷ��
    -- 4���ز�
    -- 5���˹�Ӧ��
    status tinyint COMMENT '1������
3����������ȷ��
4���ز�
5���˹�Ӧ��',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_qe_detect
(
    receipt_body_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1��������Ʒ
    -- 3��δ����
    -- 4���ز�
    -- 5���˹�Ӧ��
    status tinyint COMMENT '1��������Ʒ
3��δ����
4���ز�
5���˹�Ӧ��',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_receipt_body
(
    receipt_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_head_id bigint unsigned NOT NULL,
    order_no char(32),
    -- 1��PO������
    -- 2����Ʒ�ɹ�
    -- 3����������
    order_type tinyint COMMENT '1��PO������
2����Ʒ�ɹ�
3����������',
    receipt_no char(32) NOT NULL,
    material_no char(32) NOT NULL,
    order_total int,
    accept_num int,
    accept_date datetime,
    good_num int,
    bad_num int,
    stock_num int,
    -- 1��¼���ǩ
    -- 5��IQC���
    -- 10��QE���
    -- 15��QEȷ��
    -- 20���������
    -- 25��������
    status tinyint COMMENT '1��¼���ǩ
5��IQC���
10��QE���
15��QEȷ��
20���������
25��������',
    create_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id),
    UNIQUE (receipt_no)
);


CREATE TABLE sm_receipt_head
(
    receipt_head_id bigint unsigned NOT NULL AUTO_INCREMENT,
    order_no char(32) NOT NULL,
    -- 1��PO������
    -- 2����Ʒ�ɹ�
    -- 3����������
    order_type tinyint NOT NULL COMMENT '1��PO������
2����Ʒ�ɹ�
3����������',
    order_date date,
    supplier char(128),
    buyer char(128),
    plan_date date,
    logistics_company char(128),
    logistics_no char(64),
    -- 1������
    -- 2���ĸ�
    receipt_way tinyint COMMENT '1������
2���ĸ�',
    remark char(255),
    create_time datetime,
    update_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
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
    -- 1�������
    -- 2�������
    -- 3�������
    --
    --
    status tinyint COMMENT '1�������
2�������
3�������

',
    -- 1����Ʒ
    -- 2������Ʒ
    type tinyint COMMENT '1����Ʒ
2������Ʒ',
    create_time datetime,
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
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
    location_no char(32),
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
    -- 1��¼���ǩ
    -- 5��IQC���
    -- 10��QE���
    -- 15��QEȷ��
    -- 20���������
    -- 25��������
    status tinyint COMMENT '1��¼���ǩ
5��IQC���
10��QE���
15��QEȷ��
20���������
25��������',
    PRIMARY KEY (record_id),
    UNIQUE (record_id)
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


ALTER TABLE om_outbound_record
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_label_record
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_location
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_record
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
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


ALTER TABLE si_print_label
    ADD FOREIGN KEY (location_no)
        REFERENCES si_location (location_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_storage_label
    ADD FOREIGN KEY (location_no)
        REFERENCES si_location (location_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_storage_group
    ADD FOREIGN KEY (location_no)
        REFERENCES si_location (location_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE om_pick_body
    ADD FOREIGN KEY (material_no)
        REFERENCES si_material (material_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_print_label
    ADD FOREIGN KEY (material_no)
        REFERENCES si_material (material_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE si_storage_label
    ADD FOREIGN KEY (material_no)
        REFERENCES si_material (material_no)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_receipt_body
    ADD FOREIGN KEY (material_no)
        REFERENCES si_material (material_no)
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



