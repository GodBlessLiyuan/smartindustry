insert into dd_material_type(material_type_id, 	material_type_name, user_id, create_time) values(1, "原料", null, null), (2, "半成品", null, null), (3, "成品", null, null);
insert into si_material(material_id, material_no, material_type_id, material_name, delivery_days,moq, material_model, material_desc, test_type, dr)
values(1, '5101000496', 1, '原料物料001', 30, 'MOQ00001', 'SH0001', '测试物料，原料类型', 1, 1), (2, '5101000497', 2, '半成品物料002', 30, 'MOQ00002', 'SH0002', '测试物料，半成品类型', 2, 1), (3, '5101000498', 3, '成品物料003', 30, 'MOQ00003', 'SH0003', '测试物料，成品类型', 1, 1);

insert into si_warehouse(warehouse_id, warehouse_no, warehouse_name, warehouse_type_id, dr) values(1, 'CK00001', '测试仓库1', 1, 1);
insert into si_location(location_id, location_no, location_name, warehouse_id, user_id, dr ) values(1, '8-10', '测试货位1', 1, 1, 1), (2, '8-11', '测试货位2', 1, 1, 1);

