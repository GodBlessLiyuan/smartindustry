SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS ba_m_user_authority;
DROP TABLE IF EXISTS ba_role_authority;
DROP TABLE IF EXISTS ba_authority;
DROP TABLE IF EXISTS ba_user_role;
DROP TABLE IF EXISTS ba_role;
DROP TABLE IF EXISTS sm_record;
DROP TABLE IF EXISTS ba_user;
DROP TABLE IF EXISTS sm_entry_label;
DROP TABLE IF EXISTS sm_iqc_detect;
DROP TABLE IF EXISTS sm_metarial_storage;
DROP TABLE IF EXISTS sm_print_label;
DROP TABLE IF EXISTS sm_qe_confirm;
DROP TABLE IF EXISTS sm_qe_detect;
DROP TABLE IF EXISTS sm_receipt_body;
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
    -- 1��δ����
    -- 2��������Ʒ
    -- 3��QE�����ؼ���
    status tinyint COMMENT '1��δ����
2��������Ʒ
3��QE�����ؼ���',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_metarial_storage
(
    storage_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_body_id bigint unsigned NOT NULL,
    storage_no bigint unsigned NOT NULL,
    create_time datetime,
    -- 1�������
    -- 2�������
    -- 3�������
    --
    --
    status tinyint COMMENT '1�������
2�������
3�������

',
    pending_num int,
    stored_num int,
    storage_time datetime,
    -- 1����Ʒ
    -- 2������Ʒ
    type tinyint COMMENT '1����Ʒ
2������Ʒ',
    PRIMARY KEY (storage_id),
    UNIQUE (storage_id)
);


CREATE TABLE sm_print_label
(
    print_label_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_body_id bigint unsigned NOT NULL,
    package_id char(32) NOT NULL,
    produce_date date,
    produce_batch char(64),
    num int,
    -- 1��ɨ��
    -- 2����ӡ
    origin tinyint NOT NULL COMMENT '1��ɨ��
2����ӡ',
    add_time date,
    -- 1����Ʒ
    -- 2������Ʒ
    type tinyint COMMENT '1����Ʒ
2������Ʒ',
    -- 1�������
    -- 2�������
    -- 3�������
    --
    --
    status tinyint COMMENT '1�������
2�������
3�������

',
    relate_label_id bigint unsigned,
    relate_package_id char(32),
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
    PRIMARY KEY (print_label_id),
    UNIQUE (print_label_id),
    UNIQUE (package_id)
);


CREATE TABLE sm_qe_confirm
(
    receipt_body_id bigint unsigned NOT NULL,
    remark char(255),
    -- 1����������ȷ��
    -- 2���ز�
    -- 3���˹�Ӧ��
    status tinyint COMMENT '1����������ȷ��
2���ز�
3���˹�Ӧ��',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_qe_detect
(
    receipt_body_id bigint unsigned NOT NULL,
    -- 1��δ����
    -- 2��������Ʒ
    status tinyint COMMENT '1��δ����
2��������Ʒ',
    PRIMARY KEY (receipt_body_id),
    UNIQUE (receipt_body_id)
);


CREATE TABLE sm_receipt_body
(
    receipt_body_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_head_id bigint unsigned NOT NULL,
    receipt_no char(32) NOT NULL,
    material_no char(32),
    material_type tinyint,
    material_desc char(255),
    order_total int,
    accept_num int,
    accept_date date,
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
    logistics_no char(32),
    receipt_way tinyint,
    remark char(255),
    -- 1��δɾ��
    -- 2����ɾ��
    dr tinyint COMMENT '1��δɾ��
2����ɾ��',
    PRIMARY KEY (receipt_head_id),
    UNIQUE (receipt_head_id)
);


CREATE TABLE sm_record
(
    record_id bigint unsigned NOT NULL AUTO_INCREMENT,
    receipt_body_id bigint unsigned NOT NULL,
    user_id bigint unsigned,
    name char(255),
    type tinyint,
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


ALTER TABLE sm_record
    ADD FOREIGN KEY (user_id)
        REFERENCES ba_user (user_id)
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
;


ALTER TABLE sm_print_label
    ADD FOREIGN KEY (relate_label_id)
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


ALTER TABLE sm_metarial_storage
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



