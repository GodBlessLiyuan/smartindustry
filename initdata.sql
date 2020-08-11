insert into dd_material_type(material_type_id, 	material_type_name, user_id, create_time) values(1, "原料", null, null), (2, "半成品", null, null), (3, "成品", null, null);
insert into si_material(material_id, material_no, material_type, material_type_id, material_name, delivery_days,moq, material_model, material_desc, test_type, dr)
values(1, '5101000496', 1, 1, '原料物料001', 30, 'MOQ00001', 'SH0001', '测试物料，原料类型', 1, 1), (2, '5101000497', 2, 2, '半成品物料002', 30, 'MOQ00002', 'SH0002', '测试物料，半成品类型', 2, 1), (3, '5101000498',3, 3, '成品物料003', 30, 'MOQ00003', 'SH0003', '测试物料，成品类型', 1, 1);

insert into si_warehouse(warehouse_id, warehouse_no, warehouse_name, warehouse_type_id, dr) values(1, 'CK00001', '测试仓库1', 1, 1);
insert into si_location(location_id, location_no, location_name, location_type_id, warehouse_id, user_id, dr ) values(1, '8-10', '测试货位1', 1, 1, 1, 1), (2, '8-11', '测试货位2', 1, 1, 1, 1);

INSERT INTO `am_authority` VALUES (1, '系统管理', 'am', 1, 0);
INSERT INTO `am_authority` VALUES (2, '部门管理', 'am:dept', 1, 1);
INSERT INTO `am_authority` VALUES (3, '角色管理', 'am:role', 1, 1);
INSERT INTO `am_authority` VALUES (4, '用户管理', 'am:user', 1, 1);
INSERT INTO `am_authority` VALUES (5, '个人中心', 'am:userinfo', 1, 1);
INSERT INTO `am_authority` VALUES (6, '部门查询', 'am:dept:query', 2, 2);
INSERT INTO `am_authority` VALUES (7, '部门禁用', 'am:dept:disable', 2, 2);
INSERT INTO `am_authority` VALUES (8, '部门启用', 'am:dept:enable', 2, 2);
INSERT INTO `am_authority` VALUES (9, '部门新增', 'am:dept:insert', 2, 2);
INSERT INTO `am_authority` VALUES (10, '部门修改', 'am:dept:update', 2, 2);
INSERT INTO `am_authority` VALUES (11, '部门删除', 'am:dept:delete', 2, 2);
INSERT INTO `am_authority` VALUES (12, '角色查询', 'am:role:query', 2, 3);
INSERT INTO `am_authority` VALUES (13, '角色禁用', 'am:role:disable', 2, 3);
INSERT INTO `am_authority` VALUES (14, '角色启用', 'am:role:enable', 2, 3);
INSERT INTO `am_authority` VALUES (15, '角色新增', 'am:role:insert', 2, 3);
INSERT INTO `am_authority` VALUES (16, '角色修改', 'am:role:update', 2, 3);
INSERT INTO `am_authority` VALUES (17, '角色删除', 'am:role:delete', 2, 3);
INSERT INTO `am_authority` VALUES (18, '角色权限设置', 'am:role:site', 2, 3);
INSERT INTO `am_authority` VALUES (19, '用户查询', 'am:user:query', 2, 4);
INSERT INTO `am_authority` VALUES (20, '用户禁用', 'am:user:disable', 2, 4);
INSERT INTO `am_authority` VALUES (21, '用户启用', 'am:user:enable', 2, 4);
INSERT INTO `am_authority` VALUES (22, '用户新增', 'am:user:insert', 2, 4);
INSERT INTO `am_authority` VALUES (23, '用户修改', 'am:user:update', 2, 4);
INSERT INTO `am_authority` VALUES (24, '用户删除', 'am:user:delete', 2, 4);
INSERT INTO `am_authority` VALUES (25, '用户密码更新', 'am:user:password', 2, 4);
INSERT INTO `am_authority` VALUES (26, '基本资料修改', 'am:userinfo:update', 2, 5);
INSERT INTO `am_authority` VALUES (27, '登入密码修改', 'am:userinfo:password', 2, 5);

INSERT INTO `am_user`(`user_id`, `name`, `sex`, `dept_id`, `username`, `password`, `role_id`, `job`, `phone`, `email`, `status`, `remark`, `create_time`, `update_time`, `dr`) VALUES (1, 'admin', 1, 1, 'admin', '$2a$10$2TDU3UozsFbb2TLgGSxc5ej10Ja42yBjYoJkdiuaH/zs24Md9nZY2', 1, '1', '18167772222', '601064569@qq.com', 1, '1', '2020-08-10 16:11:03', '2020-08-10 16:12:26', 1);
INSERT INTO `am_dept`(`dept_id`, `parent_id`, `dept_name`, `user_id`, `dept_desc`, `status`, `create_time`, `update_time`, `dr`) VALUES (1, NULL, '东南院', 1, '事业单位', 1, '2020-07-30 10:52:57', '2020-07-30 10:52:59', 1);