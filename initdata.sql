insert into dd_material_type(material_type_id, 	material_type_name, user_id, create_time) values(1, "原料", null, null), (2, "半成品", null, null), (3, "成品", null, null);
insert into si_material(material_id, material_no, material_type, material_type_id, material_name, delivery_days,moq, material_model, material_desc, test_type, dr)
values(1, '5101000496', 1, 1, '原料物料001', 30, 'MOQ00001', 'SH0001', '测试物料，原料类型', 1, 1), (2, '5101000497', 2, 2, '半成品物料002', 30, 'MOQ00002', 'SH0002', '测试物料，半成品类型', 2, 1), (3, '5101000498',3, 3, '成品物料003', 30, 'MOQ00003', 'SH0003', '测试物料，成品类型', 1, 1);

insert into si_warehouse(warehouse_id, warehouse_no, warehouse_name, warehouse_type_id, dr) values(1, 'CK00001', '测试仓库1', 1, 1);
insert into si_location(location_id, location_no, location_name, location_type_id, warehouse_id, user_id, dr ) values(1, '8-10', '测试货位1', 1, 1, 1, 1), (2, '8-11', '测试货位2', 1, 1, 1, 1);



-------------------------------------------- 第一期开始-----------------------------------------------------------
INSERT INTO `am_authority` VALUES (1, '入库管理', 'sm', 1, 0);

INSERT INTO `am_authority` VALUES (10, '收料管理', 'sm:receipt', 1, 1);
INSERT INTO `am_authority` VALUES (11, '质量管理', 'sm:quality', 1, 1);
INSERT INTO `am_authority` VALUES (12, '入库管理', 'sm:storage', 1, 1);
INSERT INTO `am_authority` VALUES (15, 'PO收料', 'sm:receipt:poreceipt', 1, 10);
INSERT INTO `am_authority` VALUES (16, '样本收料', 'sm:receipt:sareceipt', 1, 10);
INSERT INTO `am_authority` VALUES (17, '生产退料', 'sm:receipt:proreturn', 1, 10);
INSERT INTO `am_authority` VALUES (20, 'IQC检测', 'sm:quality:iqcdetect', 1, 11);
INSERT INTO `am_authority` VALUES (21, 'IQC异常QE确认', 'sm:quality:iqcqe', 1, 11);
INSERT INTO `am_authority` VALUES (22, 'QE检验', 'sm:quality:qetest', 1, 11);
INSERT INTO `am_authority` VALUES (25, '良品入库单', 'sm:storage:goodslist', 1, 12);
INSERT INTO `am_authority` VALUES (26, '非良品入库单', 'sm:storage:nogoodslist', 1, 12);

INSERT INTO `am_authority` VALUES (30, 'PO收料-查询', 'sm:receipt:poreceipt:query', 2, 15);
INSERT INTO `am_authority` VALUES (31, 'PO收料-新增', 'sm:receipt:poreceipt:insert', 2, 15);
INSERT INTO `am_authority` VALUES (32, 'PO收料-删除', 'sm:receipt:poreceipt:delete', 2, 15);
INSERT INTO `am_authority` VALUES (33, 'PO收料-查看标签', 'sm:receipt:poreceipt:querylabel', 2, 15);

INSERT INTO `am_authority` VALUES (40, '样本收料-查询', 'sm:receipt:sareceipt:query', 2, 16);
INSERT INTO `am_authority` VALUES (41, '样本收料-新增', 'sm:receipt:sareceipt:insert', 2, 16);
INSERT INTO `am_authority` VALUES (42, '样本收料-删除', 'sm:receipt:sareceipt:delete', 2, 16);
INSERT INTO `am_authority` VALUES (43, '样本收料-查看标签', 'sm:receipt:sareceipt:querylabel', 2, 16);


INSERT INTO `am_authority` VALUES (50, '生产退料-查询', 'sm:receipt:proreturn:query', 2, 17);
INSERT INTO `am_authority` VALUES (51, '生产退料-新增', 'sm:receipt:proreturn:insert', 2, 17);
INSERT INTO `am_authority` VALUES (52, '生产退料-删除', 'sm:receipt:proreturn:delete', 2, 17);
INSERT INTO `am_authority` VALUES (53, '生产退料-查看标签', 'sm:receipt:proreturn:querylabel', 2, 17);

INSERT INTO `am_authority` VALUES (60, 'IQC检测-查询', 'sm:quality:iqcdetect:query', 2, 20);
INSERT INTO `am_authority` VALUES (61, 'IQC检测-IQC检验', 'sm:quality:iqcdetect:iqc', 2, 20);
INSERT INTO `am_authority` VALUES (62, 'IQC检测-生成入库单', 'sm:quality:iqcdetect:makereceipt', 2, 20);

INSERT INTO `am_authority` VALUES (70, 'IQC异常QE确认-查询', 'sm:quality:iqcqe:query', 2, 21);
INSERT INTO `am_authority` VALUES (71, 'IQC异常QE确认-QE确认', 'sm:quality:iqcqe:qeconfirm', 2, 21);
INSERT INTO `am_authority` VALUES (72, 'IQC异常QE确认-生成入库单', 'sm:quality:iqcqe:makereceipt', 2, 21);

INSERT INTO `am_authority` VALUES (80, 'QE检验-查询', 'sm:quality:qetest:query', 2, 22);
INSERT INTO `am_authority` VALUES (81, 'QE检验-QE检测', 'sm:quality:qetest:qedetect', 2, 22);
INSERT INTO `am_authority` VALUES (82, 'QE检验-生成入库单', 'sm:quality:qetest:makereceipt', 2, 22);

INSERT INTO `am_authority` VALUES (90, '良品入库单-查询', 'sm:storage:goodslist:query', 2, 25);
INSERT INTO `am_authority` VALUES (91, '良品入库单-扫码入库', 'sm:storage:goodslist:scan', 2, 25);
INSERT INTO `am_authority` VALUES (92, '良品入库单-查看', 'sm:storage:goodslist:queryinfo', 2, 25);

INSERT INTO `am_authority` VALUES (100, '非良品入库单-查询', 'sm:storage:nogoodslist:query', 2, 26);
INSERT INTO `am_authority` VALUES (101, '非良品入库单-扫码入库', 'sm:storage:nogoodslist:scan', 2, 26);
INSERT INTO `am_authority` VALUES (102, '非良品入库单-查看', 'sm:storage:nogoodslist:queryinfo', 2, 26);
-------------------------------------------- 第一期结束-----------------------------------------------------------

-------------------------------------------- 第三期开始-----------------------------------------------------------
INSERT INTO `am_authority` VALUES (2, '出库管理', 'om', 1, 0);
INSERT INTO `am_authority` VALUES (110, '拣货单管理', 'om:pickmanage', 1, 2);
INSERT INTO `am_authority` VALUES (111, '质量管理', 'om:qualitymanage', 1, 2);
INSERT INTO `am_authority` VALUES (112, '物料出库', 'om:maoutbound', 1, 2);

INSERT INTO `am_authority` VALUES (115, '工单拣货单', 'om:pickmanage:workorder', 1, 110);
INSERT INTO `am_authority` VALUES (116, '成品拣货单', 'om:pickmanage:goodsorder', 1, 110);
INSERT INTO `am_authority` VALUES (117, '工单拣货单审核', 'om:pickmanage:check', 1, 110);

INSERT INTO `am_authority` VALUES (120, 'OQC检验', 'om:qualitymanage:oqcdetect', 1, 111);

INSERT INTO `am_authority` VALUES (125, '工单出库单', 'om:maoutbound:workorderout', 1, 112);
INSERT INTO `am_authority` VALUES (126, '成品出库单', 'om:maoutbound:goodsorderout', 1, 112);

INSERT INTO `am_authority` VALUES (130, '工单拣货单-查询', 'om:pickmanage:workorder:query', 2, 115);
INSERT INTO `am_authority` VALUES (131, '工单拣货单-打印拣货单', 'om:pickmanage:workorder:print', 2, 115);
INSERT INTO `am_authority` VALUES (132, '工单拣货单-扫码拣货', 'om:pickmanage:workorder:scan', 2, 115);
INSERT INTO `am_authority` VALUES (133, '工单拣货单-查看详情', 'om:pickmanage:workorder:queryinfo', 2, 115);

INSERT INTO `am_authority` VALUES (140, '成品拣货单-查询', 'om:pickmanage:goodsorder:query', 2, 116);
INSERT INTO `am_authority` VALUES (141, '成品拣货单-打印拣货单', 'om:pickmanage:goodsorder:print', 2, 116);
INSERT INTO `am_authority` VALUES (142, '成品拣货单-扫码拣货', 'om:pickmanage:goodsorder:scan', 2, 116);
INSERT INTO `am_authority` VALUES (143, '成品拣货单-查看详情', 'om:pickmanage:goodsorder:queryinfo', 2, 116);

INSERT INTO `am_authority` VALUES (150, '工单拣货单审核-查询', 'om:pickmanage:check:query', 2, 117);
INSERT INTO `am_authority` VALUES (151, '工单拣货单审核-审核', 'om:pickmanage:check:check', 2, 117);

INSERT INTO `am_authority` VALUES (160, 'OQC检验-查询', 'om:qualitymanage:oqcdetect:query', 2, 120);
INSERT INTO `am_authority` VALUES (161, 'OQC检验-审核', 'om:qualitymanage:oqcdetect:check', 2, 120);

INSERT INTO `am_authority` VALUES (170, '工单出库单-查询', 'om:maoutbound:workorderout:query', 2, 125);
INSERT INTO `am_authority` VALUES (171, '工单出库单-出库', 'om:maoutbound:workorderout:out', 2, 125);
INSERT INTO `am_authority` VALUES (172, '工单出库单-查看详情', 'om:maoutbound:workorderout:queryinfo', 2, 125);

INSERT INTO `am_authority` VALUES (180, '成品出库单-查询', 'om:maoutbound:goodsorderout:query', 2, 126);
INSERT INTO `am_authority` VALUES (181, '成品出库单-出库', 'om:maoutbound:goodsorderout:out', 2, 126);
INSERT INTO `am_authority` VALUES (182, '成品出库单-查看详情', 'om:maoutbound:goodsorderout:queryinfo', 2, 126);
-------------------------------------------- 第四期结束-----------------------------------------------------------


-------------------------------------------- 第四期开始-----------------------------------------------------------
INSERT INTO `am_authority` VALUES (3, '库内管理', 'lm', 1, 0);
INSERT INTO `am_authority` VALUES (190, '库存信息', 'lm:inventoryinfo', 1, 3);
INSERT INTO `am_authority` VALUES (191, '物料清单', 'lm:matriallist', 1, 3);

INSERT INTO `am_authority` VALUES (195, '物料库存统计', 'lm:inventoryinfo:matotal', 1, 190);
INSERT INTO `am_authority` VALUES (196, '货位库存统计', 'lm:inventoryinfo:goodstotal', 1, 190);
INSERT INTO `am_authority` VALUES (197, '物料库存明细查询', 'lm:inventoryinfo:mainfoquery', 1, 190);
INSERT INTO `am_authority` VALUES (198, '成品库存明细查询', 'lm:inventoryinfo:goodsinfoquery', 1, 190);

INSERT INTO `am_authority` VALUES (200, '物料清单管理', 'lm:matriallist:malistmanage', 1, 191);

INSERT INTO `am_authority` VALUES (205, '物料库存统计-查询', 'lm:inventoryinfo:matotal:query', 2, 195);
INSERT INTO `am_authority` VALUES (206, '物料库存统计-设置安全库存', 'lm:inventoryinfo:matotal:setsafe', 2, 195);

INSERT INTO `am_authority` VALUES (210, '货位库存统计-查询', 'lm:inventoryinfo:goodstotal:query', 2, 196);

INSERT INTO `am_authority` VALUES (215, '物料库存明细查询-查询', 'lm:inventoryinfo:mainfoquery:query', 2, 197);
INSERT INTO `am_authority` VALUES (216, '物料库存明细查询-设置安全库存', 'lm:inventoryinfo:mainfoquery:setsafe', 2, 197);
INSERT INTO `am_authority` VALUES (217, '物料库存明细查询-生成入库单', 'lm:inventoryinfo:mainfoquery:makereceipt', 2, 197);
-------------------------------------------- 第四期结束-----------------------------------------------------------




INSERT INTO `am_authority` VALUES (4, '基础信息管理', 'bm', 1, 0);
INSERT INTO `am_authority` VALUES (220, '物料管理', 'bm:mamanage', 1, 4);
INSERT INTO `am_authority` VALUES (221, '供应商管理', 'bm:promanage', 1, 4);
INSERT INTO `am_authority` VALUES (222, '仓库管理', 'bm:whmanage', 1, 4);
INSERT INTO `am_authority` VALUES (223, '系统管理', 'bm:am', 1, 4);

INSERT INTO `am_authority` VALUES (225, '物料主数据管理', 'bm:mamanage:maindata', 1, 220);

INSERT INTO `am_authority` VALUES (230, '供应商基础信息管理', 'bm:promanage:info', 1, 221);

INSERT INTO `am_authority` VALUES (235, '仓库档案', 'bm:whmanage:whfile', 1, 222);
INSERT INTO `am_authority` VALUES (236, '货位档案', 'bm:whmanage:clinfo', 1, 222);

INSERT INTO `am_authority` VALUES (240, '部门管理', 'bm:am:dept', 1, 223);
INSERT INTO `am_authority` VALUES (241, '角色管理', 'bm:am:role', 1, 223);
INSERT INTO `am_authority` VALUES (242, '用户管理', 'bm:am:user', 1, 223);
INSERT INTO `am_authority` VALUES (243, '个人中心', 'bm:am:userinfo', 1, 223);
INSERT INTO `am_authority` VALUES (244, '系统配置', 'bm:am:sys', 1, 223);

INSERT INTO `am_authority` VALUES (250, '部门查询', 'bm:am:dept:query', 2, 240);
INSERT INTO `am_authority` VALUES (251, '部门禁用', 'bm:am:dept:disable', 2, 240);
INSERT INTO `am_authority` VALUES (252, '部门启用', 'bm:am:dept:enable', 2, 240);
INSERT INTO `am_authority` VALUES (253, '部门新增', 'bm:am:dept:insert', 2, 240);
INSERT INTO `am_authority` VALUES (254, '部门修改', 'bm:am:dept:update', 2, 240);
INSERT INTO `am_authority` VALUES (255, '部门删除', 'bm:am:dept:delete', 2, 240);

INSERT INTO `am_authority` VALUES (260, '角色查询', 'bm:am:role:query', 2, 241);
INSERT INTO `am_authority` VALUES (261, '角色禁用', 'bm:am:role:disable', 2, 241);
INSERT INTO `am_authority` VALUES (262, '角色启用', 'bm:am:role:enable', 2, 241);
INSERT INTO `am_authority` VALUES (263, '角色新增', 'bm:am:role:insert', 2, 241);
INSERT INTO `am_authority` VALUES (264, '角色修改', 'bm:am:role:update', 2, 241);
INSERT INTO `am_authority` VALUES (265, '角色删除', 'bm:am:role:delete', 2, 241);
INSERT INTO `am_authority` VALUES (266, '角色权限设置', 'bm:am:role:site', 2, 241);

INSERT INTO `am_authority` VALUES (270, '用户查询', 'bm:am:user:query', 2, 242);
INSERT INTO `am_authority` VALUES (271, '用户禁用', 'bm:am:user:disable', 2, 242);
INSERT INTO `am_authority` VALUES (272, '用户启用', 'bm:am:user:enable', 2, 242);
INSERT INTO `am_authority` VALUES (273, '用户新增', 'bm:am:user:insert', 2, 242);
INSERT INTO `am_authority` VALUES (274, '用户修改', 'bm:am:user:update', 2, 242);
INSERT INTO `am_authority` VALUES (275, '用户删除', 'bm:am:user:delete', 2, 242);
INSERT INTO `am_authority` VALUES (276, '用户密码更新', 'bm:am:user:password', 2, 242);

INSERT INTO `am_authority` VALUES (280, '基本资料修改', 'bm:am:userinfo:update', 2, 243);
INSERT INTO `am_authority` VALUES (281, '登入密码修改', 'bm:am:userinfo:password', 2, 243);

INSERT INTO `am_authority` VALUES (285, '系统配置-新增', 'bm:am:sys:insert', 2, 244);
INSERT INTO `am_authority` VALUES (286, '系统配置-流程配置', 'bm:am:sys:set', 2, 244);







INSERT INTO `am_user`(`user_id`, `name`, `sex`, `dept_id`, `username`, `password`, `role_id`, `job`, `phone`, `email`, `status`, `remark`, `create_time`, `update_time`, `dr`) VALUES (1, 'admin', 1, 1, 'admin', '$2a$10$2TDU3UozsFbb2TLgGSxc5ej10Ja42yBjYoJkdiuaH/zs24Md9nZY2', 1, '1', '18167772222', '601064569@qq.com', 1, '1', '2020-08-10 16:11:03', '2020-08-10 16:12:26', 1);
INSERT INTO `am_dept`(`dept_id`, `parent_id`, `dept_name`, `user_id`, `dept_desc`, `status`, `create_time`, `update_time`, `dr`) VALUES (1, NULL, '东南院', 1, '事业单位', 1, '2020-07-30 10:52:57', '2020-07-30 10:52:59', 1);