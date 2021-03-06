insert into dd_material_type(material_type_id, 	material_type_name, user_id, create_time) values(1, "原料", null, null), (2, "半成品", null, null), (3, "成品", null, null);

insert into si_warehouse(warehouse_id, warehouse_no, warehouse_name, warehouse_type_id, dr) values(1, 'CK00001', '测试仓库1', 1, 1);
insert into si_location(location_id, location_no, location_name, location_type_id, warehouse_id, user_id, dr ) values(1, '8-10', '测试货位1', 1, 1, 1, 1), (2, '8-11', '测试货位2', 1, 1, 1, 1);




INSERT INTO `am_authority` VALUES (1, '入库管理', 'sm', 1, 1000);
INSERT INTO `am_authority` VALUES (2, '出库管理', 'om', 1, 1000);
INSERT INTO `am_authority` VALUES (3, '库内管理', 'im', 1, 1000);
INSERT INTO `am_authority` VALUES (4, '基础信息管理', 'bm', 1, 1000);
INSERT INTO `am_authority` VALUES (5, '系统配置', 'cm', 1, 4000);
INSERT INTO `am_authority` VALUES (10, '收料管理', 'sm:rm', 1, 1);
INSERT INTO `am_authority` VALUES (11, '质量管理', 'sm:qm', 1, 1);
INSERT INTO `am_authority` VALUES (12, '物料入库管理', 'sm:ms', 1, 1);
INSERT INTO `am_authority` VALUES (15, 'PO收料', 'sm:rm:porc', 1, 10);
INSERT INTO `am_authority` VALUES (16, '样本收料', 'sm:rm:src', 1, 10);
INSERT INTO `am_authority` VALUES (17, '生产退料', 'sm:rm:prm', 1, 10);
INSERT INTO `am_authority` VALUES (20, 'IQC检测', 'sm:qm:iqctest', 1, 11);
INSERT INTO `am_authority` VALUES (21, 'IQC异常QE确认', 'sm:qm:iqcqe', 1, 11);
INSERT INTO `am_authority` VALUES (22, 'QE检验', 'sm:qm:qetest', 1, 11);
INSERT INTO `am_authority` VALUES (25, '采购入库单', 'sm:ms:gpwl', 1, 12);
INSERT INTO `am_authority` VALUES (26, '生产入库单', 'sm:ms:ndgr', 1, 12);
INSERT INTO `am_authority` VALUES (27, '生产退料入库单', 'sm:ms:prr', 1, 12);
INSERT INTO `am_authority` VALUES (28, '其他入库单', 'sm:ms:owr', 1, 12);
INSERT INTO `am_authority` VALUES (30, 'PO收料-查询', 'sm:rm:porc:query', 2, 15);
INSERT INTO `am_authority` VALUES (31, 'PO收料-新增', 'sm:rm:porc:insert', 2, 15);
INSERT INTO `am_authority` VALUES (32, 'PO收料-删除', 'sm:rm:porc:delete', 2, 15);
INSERT INTO `am_authority` VALUES (33, 'PO收料-查看标签', 'sm:rm:porc:querylabel', 2, 15);
INSERT INTO `am_authority` VALUES (34, 'PO收料-录入标签', 'sm:rm:porc:entrylabel', 2, 15);
INSERT INTO `am_authority` VALUES (40, '样本收料-查询', 'sm:rm:src:query', 2, 16);
INSERT INTO `am_authority` VALUES (41, '样本收料-新增', 'sm:rm:src:insert', 2, 16);
INSERT INTO `am_authority` VALUES (42, '样本收料-删除', 'sm:rm:src:delete', 2, 16);
INSERT INTO `am_authority` VALUES (43, '样本收料-查看标签', 'sm:rm:src:querylabel', 2, 16);
INSERT INTO `am_authority` VALUES (44, '样本收料-录入标签', 'sm:rm:src:entrylabel', 2, 16);
INSERT INTO `am_authority` VALUES (50, '生产退料-查询', 'sm:rm:prm:query', 2, 17);
INSERT INTO `am_authority` VALUES (51, '生产退料-新增', 'sm:rm:prm:insert', 2, 17);
INSERT INTO `am_authority` VALUES (52, '生产退料-删除', 'sm:rm:prm:delete', 2, 17);
INSERT INTO `am_authority` VALUES (53, '生产退料-查看标签', 'sm:rm:prm:querylabel', 2, 17);
INSERT INTO `am_authority` VALUES (54, '生产退料-录入标签', 'sm:rm:prm:entrylabel', 2, 17);
INSERT INTO `am_authority` VALUES (60, 'IQC检测-查询', 'sm:qm:iqctest:query', 2, 20);
INSERT INTO `am_authority` VALUES (61, 'IQC检测-IQC检验', 'sm:qm:iqctest:iqc', 2, 20);
INSERT INTO `am_authority` VALUES (62, 'IQC检测-生成入库单', 'sm:qm:iqctest:storage', 2, 20);
INSERT INTO `am_authority` VALUES (63, 'IQC检测-录入标签', 'sm:qm:iqctest:entrylabel', 2, 20);
INSERT INTO `am_authority` VALUES (70, 'IQC异常QE确认-查询', 'sm:qm:iqcqe:query', 2, 21);
INSERT INTO `am_authority` VALUES (71, 'IQC异常QE确认-QE确认', 'sm:qm:iqcqe:qeconfirm', 2, 21);
INSERT INTO `am_authority` VALUES (72, 'IQC异常QE确认-生成入库单', 'sm:qm:iqcqe:storage', 2, 21);
INSERT INTO `am_authority` VALUES (73, 'IQC异常QE确认-打印标签', 'sm:qm:iqcqe:printlabel', 2, 21);
INSERT INTO `am_authority` VALUES (80, 'QE检验-查询', 'sm:qm:qetest:query', 2, 22);
INSERT INTO `am_authority` VALUES (81, 'QE检验-QE检测', 'sm:qm:qetest:qetest', 2, 22);
INSERT INTO `am_authority` VALUES (82, 'QE检验-生成入库单', 'sm:qm:qetest:storage', 2, 22);
INSERT INTO `am_authority` VALUES (83, 'QE检验-打印标签', 'sm:qm:qetest:printlabel', 2, 22);
INSERT INTO `am_authority` VALUES (90, '采购入库单-查询', 'sm:ms:gpwl:query', 2, 25);
INSERT INTO `am_authority` VALUES (91, '采购入库单-扫码入库', 'sm:ms:gpwl:scan', 2, 25);
INSERT INTO `am_authority` VALUES (92, '采购入库单-查看', 'sm:ms:gpwl:queryinfo', 2, 25);
INSERT INTO `am_authority` VALUES (100, '生产入库单-查询', 'sm:ms:ndgr:query', 2, 26);
INSERT INTO `am_authority` VALUES (101, '生产入库单-扫码入库', 'sm:ms:ndgr:scan', 2, 26);
INSERT INTO `am_authority` VALUES (102, '生产入库单-查看', 'sm:ms:ndgr:queryinfo', 2, 26);
INSERT INTO `am_authority` VALUES (104, '生产退料入库单-查询', 'sm:ms:prr:query', 2, 27);
INSERT INTO `am_authority` VALUES (105, '生产退料入库单-扫码入库', 'sm:ms:prr:scan', 2, 27);
INSERT INTO `am_authority` VALUES (106, '生产退料入库单-查看', 'sm:ms:prr:queryinfo', 2, 27);
INSERT INTO `am_authority` VALUES (107, '其他入库单-查询', 'sm:ms:owr:query', 2, 28);
INSERT INTO `am_authority` VALUES (108, '其他入库单-扫码入库', 'sm:ms:owr:scan', 2, 28);
INSERT INTO `am_authority` VALUES (109, '其他入库单-查看', 'sm:ms:owr:queryinfo', 2, 28);
INSERT INTO `am_authority` VALUES (110, '拣货单管理', 'om:pm', 1, 2);
INSERT INTO `am_authority` VALUES (111, '质量管理', 'om:qm', 1, 2);
INSERT INTO `am_authority` VALUES (112, '物料出库', 'om:mo', 1, 2);
INSERT INTO `am_authority` VALUES (115, '工单拣货单', 'om:pm:wopick', 1, 110);
INSERT INTO `am_authority` VALUES (116, '销售拣货单', 'om:pm:fppick', 1, 110);
INSERT INTO `am_authority` VALUES (117, '工单拣货单审核', 'om:pm:check', 1, 110);
INSERT INTO `am_authority` VALUES (118, '调拨拣货单', 'om:pm:transfer', 1, 110);
INSERT INTO `am_authority` VALUES (120, 'OQC检验', 'om:qm:oqctest', 1, 111);
INSERT INTO `am_authority` VALUES (125, '工单出库单', 'om:mo:woout', 1, 112);
INSERT INTO `am_authority` VALUES (126, '销售出库单', 'om:mo:fpout', 1, 112);
INSERT INTO `am_authority` VALUES (127, '其他出库单', 'om:mo:otherout', 1, 112);
INSERT INTO `am_authority` VALUES (130, '工单拣货单-查询', 'om:pm:wopick:query', 2, 115);
INSERT INTO `am_authority` VALUES (131, '工单拣货单-打印拣货单', 'om:pm:wopick:print', 2, 115);
INSERT INTO `am_authority` VALUES (132, '工单拣货单-扫码拣货', 'om:pm:wopick:scan', 2, 115);
INSERT INTO `am_authority` VALUES (133, '工单拣货单-查看详情', 'om:pm:wopick:queryinfo', 2, 115);
INSERT INTO `am_authority` VALUES (140, '销售拣货单-查询', 'om:pm:fppick:query', 2, 116);
INSERT INTO `am_authority` VALUES (141, '销售拣货单-打印拣货单', 'om:pm:fppick:print', 2, 116);
INSERT INTO `am_authority` VALUES (142, '销售拣货单-扫码拣货', 'om:pm:fppick:scan', 2, 116);
INSERT INTO `am_authority` VALUES (143, '销售拣货单-查看详情', 'om:pm:fppick:queryinfo', 2, 116);
INSERT INTO `am_authority` VALUES (150, '工单拣货单审核-查询', 'om:pm:check:query', 2, 117);
INSERT INTO `am_authority` VALUES (151, '工单拣货单审核-审核', 'om:pm:check:check', 2, 117);
INSERT INTO `am_authority` VALUES (153, '调拨拣货单-查询', 'om:pm:transfer:query', 2, 118);
INSERT INTO `am_authority` VALUES (154, '调拨拣货单-打印拣货单', 'om:pm:transfer:print', 2, 118);
INSERT INTO `am_authority` VALUES (155, '调拨拣货单-扫码拣货', 'om:pm:transfer:scan', 2, 118);
INSERT INTO `am_authority` VALUES (156, '调拨拣货单-查看详情', 'om:pm:transfer:queryinfo', 2, 118);
INSERT INTO `am_authority` VALUES (160, 'OQC检验-查询', 'om:qm:oqctest:query', 2, 120);
INSERT INTO `am_authority` VALUES (161, 'OQC检验-审核', 'om:qm:oqctest:check', 2, 120);
INSERT INTO `am_authority` VALUES (170, '工单出库单-查询', 'om:mo:woout:query', 2, 125);
INSERT INTO `am_authority` VALUES (171, '工单出库单-出库', 'om:mo:woout:out', 2, 125);
INSERT INTO `am_authority` VALUES (172, '工单出库单-查看详情', 'om:mo:woout:queryinfo', 2, 125);
INSERT INTO `am_authority` VALUES (180, '销售出库单-查询', 'om:mo:fpout:query', 2, 126);
INSERT INTO `am_authority` VALUES (181, '销售出库单-出库', 'om:mo:fpout:out', 2, 126);
INSERT INTO `am_authority` VALUES (182, '销售出库单-查看详情', 'om:mo:fpout:queryinfo', 2, 126);
INSERT INTO `am_authority` VALUES (185, '其他出库单-查询', 'om:mo:otherout:query', 2, 127);
INSERT INTO `am_authority` VALUES (186, '其他出库单-出库', 'om:mo:otherout:out', 2, 127);
INSERT INTO `am_authority` VALUES (187, '其他出库单-查看详情', 'om:mo:otherout:queryinfo', 2, 127);
INSERT INTO `am_authority` VALUES (190, '库存信息', 'im:info', 1, 3);
INSERT INTO `am_authority` VALUES (195, '物料库存统计', 'im:info:mtotal', 1, 190);
INSERT INTO `am_authority` VALUES (197, '物料库存明细查询', 'im:info:mquery', 1, 190);
INSERT INTO `am_authority` VALUES (198, '成品库存明细查询', 'im:info:gquery', 1, 190);
INSERT INTO `am_authority` VALUES (205, '物料库存统计-查询', 'im:info:mtotal:query', 2, 195);
INSERT INTO `am_authority` VALUES (206, '物料库存统计-设置安全库存', 'im:info:mtotal:setsafe', 2, 195);
INSERT INTO `am_authority` VALUES (210, '成品库存明细查询-查询', 'im:info:gquery:query', 2, 198);
INSERT INTO `am_authority` VALUES (211, '成品库存明细查询-物料锁定', 'im:info:gquery:lock', 2, 198);
INSERT INTO `am_authority` VALUES (212, '成品库存明细查询-物料解锁', 'im:info:gquery:unlock', 2, 198);
INSERT INTO `am_authority` VALUES (215, '物料库存明细查询-查询', 'im:info:mquery:query', 2, 197);
INSERT INTO `am_authority` VALUES (216, '物料库存明细查询-物料锁定', 'im:info:mquery:lock', 2, 197);
INSERT INTO `am_authority` VALUES (217, '物料库存明细查询-物料解锁', 'im:info:mquery:unlock', 2, 197);
INSERT INTO `am_authority` VALUES (220, '物料管理', 'bm:mm', 1, 4);
INSERT INTO `am_authority` VALUES (221, '供应商管理', 'bm:sm', 1, 4);
INSERT INTO `am_authority` VALUES (222, '仓库管理', 'bm:wm', 1, 4);
INSERT INTO `am_authority` VALUES (223, '系统管理', 'bm:am', 1, 5);
INSERT INTO `am_authority` VALUES (224, '客户管理', 'bm:cm', 1, 4);
INSERT INTO `am_authority` VALUES (225, '物料主数据管理', 'bm:mm:main', 1, 220);
INSERT INTO `am_authority` VALUES (226, '物料清单管理', 'bm:mm:items', 1, 220);
INSERT INTO `am_authority` VALUES (230, '供应商基础信息管理', 'bm:sm:info', 1, 221);
INSERT INTO `am_authority` VALUES (233, '客户基础信息管理', 'bm:cm:basis', 1, 224);
INSERT INTO `am_authority` VALUES (235, '仓库档案', 'bm:wm:whfile', 1, 222);
INSERT INTO `am_authority` VALUES (236, '货位档案', 'bm:wm:clinfo', 1, 222);
INSERT INTO `am_authority` VALUES (240, '部门管理', 'bm:am:dept', 1, 223);
INSERT INTO `am_authority` VALUES (241, '角色管理', 'bm:am:role', 1, 223);
INSERT INTO `am_authority` VALUES (242, '用户管理', 'bm:am:user', 1, 223);
INSERT INTO `am_authority` VALUES (243, '个人中心', 'bm:am:userinfo', 1, 223);
INSERT INTO `am_authority` VALUES (244, '系统配置', 'bm:am:sys', 1, 223);
INSERT INTO `am_authority` VALUES (250, '部门管理-新增', 'bm:am:dept:insert', 2, 240);
INSERT INTO `am_authority` VALUES (251, '部门管理-启用', 'bm:am:dept:enable', 2, 240);
INSERT INTO `am_authority` VALUES (252, '部门管理-禁用', 'bm:am:dept:disable', 2, 240);
INSERT INTO `am_authority` VALUES (253, '部门管理-删除', 'bm:am:dept:delete', 2, 240);
INSERT INTO `am_authority` VALUES (254, '部门管理-查询', 'bm:am:dept:query', 2, 240);
INSERT INTO `am_authority` VALUES (255, '部门管理-查看', 'bm:am:dept:queryinfo', 2, 240);
INSERT INTO `am_authority` VALUES (256, '部门管理-编辑', 'bm:am:dept:update', 2, 240);
INSERT INTO `am_authority` VALUES (260, '角色管理-新增', 'bm:am:role:insert', 2, 241);
INSERT INTO `am_authority` VALUES (261, '角色管理-启用', 'bm:am:role:enable', 2, 241);
INSERT INTO `am_authority` VALUES (262, '角色管理-禁用', 'bm:am:role:disable', 2, 241);
INSERT INTO `am_authority` VALUES (263, '角色管理-删除', 'bm:am:role:delete', 2, 241);
INSERT INTO `am_authority` VALUES (264, '角色管理-查询', 'bm:am:role:query', 2, 241);
INSERT INTO `am_authority` VALUES (265, '角色管理-查看', 'bm:am:role:queryinfo', 2, 241);
INSERT INTO `am_authority` VALUES (266, '角色管理-编辑', 'bm:am:role:update', 2, 241);
INSERT INTO `am_authority` VALUES (267, '角色管理-权限设置', 'bm:am:role:setting', 2, 241);
INSERT INTO `am_authority` VALUES (270, '用户管理-新增', 'bm:am:user:insert', 2, 242);
INSERT INTO `am_authority` VALUES (271, '用户管理-启用', 'bm:am:user:enable', 2, 242);
INSERT INTO `am_authority` VALUES (272, '用户管理-禁用', 'bm:am:user:disable', 2, 242);
INSERT INTO `am_authority` VALUES (273, '用户管理-删除', 'bm:am:user:delete', 2, 242);
INSERT INTO `am_authority` VALUES (274, '用户管理-查询', 'bm:am:user:query', 2, 242);
INSERT INTO `am_authority` VALUES (275, '用户管理-查看', 'bm:am:user:queryinfo', 2, 242);
INSERT INTO `am_authority` VALUES (276, '用户管理-编辑', 'bm:am:user:update', 2, 242);
INSERT INTO `am_authority` VALUES (277, '用户管理-重置', 'bm:am:user:reset', 2, 242);


INSERT INTO `am_authority` VALUES (280, '系统配置-数据字典-查看', 'bm:am:sys:dquery', 2, 244);
INSERT INTO `am_authority` VALUES (281, '系统配置-数据字典-新增', 'bm:am:sys:insert', 2, 244);
INSERT INTO `am_authority` VALUES (282, '系统配置-流程配置-查看', 'bm:am:sys:query', 2, 244);
INSERT INTO `am_authority` VALUES (283, '系统配置-流程配置-修改', 'bm:am:sys:update', 2, 244);

INSERT INTO `am_authority` VALUES (285, '个人中心-查看', 'bm:am:userinfo:query', 2, 243);
INSERT INTO `am_authority` VALUES (286, '个人中心-修改', 'bm:am:userinfo:update', 2, 243);

INSERT INTO `am_authority` VALUES (290, '物料主数据管理-查询', 'bm:mm:main:query', 2, 225);
INSERT INTO `am_authority` VALUES (291, '物料主数据管理-新增', 'bm:mm:main:insert', 2, 225);
INSERT INTO `am_authority` VALUES (292, '物料主数据管理-查看', 'bm:mm:main:queryinfo', 2, 225);
INSERT INTO `am_authority` VALUES (293, '物料主数据管理-修改', 'bm:mm:main:update', 2, 225);
INSERT INTO `am_authority` VALUES (294, '物料主数据管理-删除', 'bm:mm:main:delete', 2, 225);

INSERT INTO `am_authority` VALUES (295, '物料清单管理-删除', 'bm:mm:items:delete', 2, 226);
INSERT INTO `am_authority` VALUES (296, '物料清单管理-查询', 'bm:mm:items:query', 2, 226);
INSERT INTO `am_authority` VALUES (297, '物料清单管理-新增', 'bm:mm:items:insert', 2, 226);
INSERT INTO `am_authority` VALUES (298, '物料清单管理-查看', 'bm:mm:items:queryinfo', 2, 226);
INSERT INTO `am_authority` VALUES (299, '物料清单管理-编辑', 'bm:mm:items:edit', 2, 226);

INSERT INTO `am_authority` VALUES (300, '供应商基础信息管理-查询', 'bm:sm:info:query', 2, 230);
INSERT INTO `am_authority` VALUES (301, '供应商基础信息管理-新增', 'bm:sm:info:insert', 2, 230);
INSERT INTO `am_authority` VALUES (302, '供应商基础信息管理-查看', 'bm:sm:info:queryinfo', 2, 230);
INSERT INTO `am_authority` VALUES (303, '供应商基础信息管理-修改', 'bm:sm:info:update', 2, 230);
INSERT INTO `am_authority` VALUES (304, '供应商基础信息管理-删除', 'bm:sm:info:delete', 2, 230);
INSERT INTO `am_authority` VALUES (305, '客户基础信息管理-查看', 'bm:cm:basis:queryinfo', 2, 233);
INSERT INTO `am_authority` VALUES (306, '客户基础信息管理-新增', 'bm:cm:basis:insert', 2, 233);
INSERT INTO `am_authority` VALUES (307, '客户基础信息管理-删除', 'bm:cm:basis:delete', 2, 233);
INSERT INTO `am_authority` VALUES (308, '客户基础信息管理-查询', 'bm:cm:basis:query', 2, 233);
INSERT INTO `am_authority` VALUES (309, '客户基础信息管理-编辑', 'bm:cm:basis:update', 2, 233);
INSERT INTO `am_authority` VALUES (310, '仓库档案-查询', 'bm:wm:whfile:query', 2, 235);
INSERT INTO `am_authority` VALUES (311, '仓库档案-新增', 'bm:wm:whfile:insert', 2, 235);
INSERT INTO `am_authority` VALUES (312, '仓库档案-查看', 'bm:wm:whfile:queryinfo', 2, 235);
INSERT INTO `am_authority` VALUES (313, '仓库档案-修改', 'bm:wm:whfile:update', 2, 235);
INSERT INTO `am_authority` VALUES (314, '仓库档案-删除', 'bm:wm:whfile:delete', 2, 235);
INSERT INTO `am_authority` VALUES (320, '货位档案-查询', 'bm:wm:loc:query', 2, 236);
INSERT INTO `am_authority` VALUES (321, '货位档案-新增', 'bm:wm:loc:insert', 2, 236);
INSERT INTO `am_authority` VALUES (322, '货位档案-查看', 'bm:wm:loc:queryinfo', 2, 236);
INSERT INTO `am_authority` VALUES (323, '货位档案-修改', 'bm:wm:loc:update', 2, 236);
INSERT INTO `am_authority` VALUES (324, '货位档案-删除', 'bm:wm:loc:delete', 2, 236);
INSERT INTO `am_authority` VALUES (1000, 'WMS系统', 'wms', 3, null);
INSERT INTO `am_authority` VALUES (2000, 'MES系统', 'mes', 3, null);
INSERT INTO `am_authority` VALUES (3000, 'ERP系统', 'erp', 3, 0);
INSERT INTO `am_authority` VALUES (4000, 'MDM系统', 'mdm', 3, null);





INSERT INTO `am_user`(`user_id`, `name`, `sex`, `dept_id`, `username`, `password`, `role_id`, `job`, `phone`, `email`, `status`, `remark`, `create_time`, `update_time`, `dr`) VALUES (1, 'admin', 1, 1, 'admin', '$2a$10$2TDU3UozsFbb2TLgGSxc5ej10Ja42yBjYoJkdiuaH/zs24Md9nZY2', 1, '1', '18167772222', '601064569@qq.com', 1, '1', '2020-08-10 16:11:03', '2020-08-10 16:12:26', 1);
INSERT INTO `am_dept`(`dept_id`, `parent_id`, `dept_name`, `user_id`, `dept_desc`, `status`, `create_time`, `update_time`, `dr`) VALUES (1, NULL, '东南院', 1, '事业单位', 1, '2020-07-30 10:52:57', '2020-07-30 10:52:59', 1);
INSERT INTO `am_role`(`role_id`, `role_name`, `role_desc`, `status`, `create_time`, `update_time`, `dr`) VALUES (1, '超级管理员角色', '超级管理员角色', 1, '2020-09-23 16:22:46', NULL, 1);



INSERT INTO `si_config` VALUES (1, 'pid_relate', 'Y');
INSERT INTO `si_config` VALUES (2, 'storage_quality', 'Y');
INSERT INTO `si_config` VALUES (3, 'outbound_quality', 'Y');

INSERT INTO wm_work_bench( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES ( 20, 'IQC待质检', 1, 1, '/industryfile/icon/iqc_check.png', '/warehousing/quality/IQCCheck', 1);
INSERT INTO wm_work_bench( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES ( 22, 'QE待质检', 1, 1, '/industryfile/icon/qe_check.png', '/warehousing/quality/QECheck', 2);
INSERT INTO wm_work_bench( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES ( 21, 'QE待确认', 1, 1, '/industryfile/icon/qe_confirm.png', '/warehousing/quality/IQCAbnormal', 3);
INSERT INTO wm_work_bench( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES ( 12, '待入库', 1, 1, '/industryfile/icon/storage.png', '/warehousing/materialWarehousing/', 4);
INSERT INTO wm_work_bench( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES ( 110, '待拣货', 1, 1, '/industryfile/icon/pickup.png', '/outbound/delivery/', 5);
INSERT INTO wm_work_bench( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES ( 120, 'OQC待检验', 1, 1, '/industryfile/icon/oqc_check.png', '/outbound/quality/OQCCheck', 6);
INSERT INTO wm_work_bench( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES ( 118, '工单待审核', 1, 1, '/industryfile/icon/order_audit.png', '/outbound/pickinglist/lack', 7);
INSERT INTO wm_work_bench( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES ( 112, '待出库', 1, 1, '/industryfile/icon/outbound.png', '/outbound/delivery/', 8);


INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (10, "收料管理", 2, 1, NULL, "/warehousing/goodsReceipt/", 1);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (11, "入库质检", 2, 1, NULL, "/warehousing/quality/", 2);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (12, "物料入库", 2, 1, NULL, "/warehousing/materialWarehousing/", 3);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (110, "拣货单管理", 2, 1, NULL, "/outbound/delivery/", 4);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (111, "出库质检", 2, 1, NULL, "/outbound/quality/", 5);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (112, "物料出库", 2, 1, NULL, "/outbound/pickinglist/", 6);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (190, "库存信息", 2, 1, '/industryfile/icon/inventory.png', "/library/stock/", 7);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (220, "物料管理", 2, 1, NULL, "/basedata/materiel/", 8);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (221, "供应商管理", 2, 1, NULL, "/basedata/supplier/", 9);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (222, "客户管理", 2, 1, NULL, "/basedata/client/", 10);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (223, "仓库管理", 2, 1, NULL, "/basedata/warehouse/", 11);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (224, "系统管理", 2, 1, '/industryfile/icon/system_manage.png', "/basedata/system/", 12);

INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (222, "物料清单", 2, 2, '/industryfile/icon/material_bill.png', "/basedata/client/", 1);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (223, "计划排产", 2, 2, '/industryfile/icon/plan_product.png', "/basedata/warehouse/", 2);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (224, "设备管理", 2, 2, '/industryfile/icon/device_manage.png', "/basedata/system/", 3);

INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (222, "销售订单", 2, 3, '/industryfile/icon/sale_order.png', "/basedata/client/", 1);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (223, "采购订单", 2, 3, '/industryfile/icon/purchase_order.png', "/basedata/warehouse/", 2);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (224, "生产订单", 2, 3, '/industryfile/icon/product_order.png', "/basedata/system/", 3);

INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (222, "物料管理", 2, 4, '/industryfile/icon/materal_relate.png', "/basedata/client/", 1);
INSERT INTO wm_work_bench ( authority_id, bench_name, bench_type, bench_module, icon_path, url_path,bench_node) VALUES (223, "系统管理", 2, 4, '/industryfile/icon/system_manage.png', "/basedata/warehouse/",2);