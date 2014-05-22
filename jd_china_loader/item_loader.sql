--reset store catalog...one time deal
--update clw_catalog set catalog_type_cd = 'STORE' where catalog_id = 2316;

--insert category into store catalog (again right now only once [in future once per category])
--insert into clw_catalog_structure 
--select
--CLW_CATALOG_STRUCTURE_seq.nextval, 2316, null,null, 'CATALOG_CATEGORY', 64602, null, null, sysdate, null, 'ACTIVE', sysdate, 'test',sysdate, 'test','false', null
--from dual;

--insert general  category into the shopping catalog (hard coded id)
-- (again right now only once [in future once per category])
--insert into clw_catalog_structure 
--select
--CLW_CATALOG_STRUCTURE_seq.nextval, 2327, null,null, 'CATALOG_CATEGORY', 64602, null, null, sysdate, null, 'ACTIVE', sysdate, 'test',sysdate, 'test','false', null
--from dual;


set serveroutput on;
DECLARE
v_item_num varchar2(20);
v_description varchar2(255);
v_category varchar2(100);
v_uom varchar2(20);
v_count number;
v_bus_entity_id number := 278774;
--v_bus_entity_id_haz number := 278773;
v_bus_entity_id_manuf number := 279905;
v_mod_by varchar2(20) := 'MAnoj';
v_add_by varchar2(20) := 'Manoj';
v_name_value varchar2(4) := 'UOM';
v_item_type_cd varchar2(20) := 'PRODUCT';
v_item_status_cd varchar2(20) := 'ACTIVE';
v_item_id number;
v_item_id_seq number;
v_item_assoc_id number;
v_catalog_id number := 22809;
v_item2_id number := 133136;
v_item_assoc_cd varchar2(40):= 'PRODUCT_PARENT_CATEGORY';
v_item_mapping_cd varchar2(20) := 'ITEM_DISTRIBUTOR';
v_item_mapping_cd1 varchar2(20) := 'ITEM_MANUFACTURER';
v_item_mapping_id number;
v_item_meta_id number;
v_order_guide_structure_id number;
v_order_guide_id number := 1308100;
v_category_item_id number := 133136;
v_catalog_structure_cd varchar2(20) := 'CATALOG_PRODUCT';
v_tax_exempt varchar2(10) := 'false';
v_catalog_structure_id number;
v_contract_item_id number;
--v_contract_id number := 22362;
v_sku_num number;
--v_catalog_id_al is alternative catalog or store catalog
v_catalog_id_al number:= 22809;

CURSOR c_item is

select distinct TI.ITEM_NUMBER2,TI.description,TI.uom_primary_pricing from pri_trans_item2 TI where TI.item_number2 in
(select item_number_2 from PRI_ITEM_MASTER2 where item_master_id in
(select item_master_id from pri_item_branch2 where business_unit in(
select business_unit from JD_BUS_UNIT_COUNTRY_CD2 where country_code = 'CN')));

BEGIN
OPEN c_item;
LOOP
FETCH c_item into v_item_num,v_description,v_uom;
EXIT WHEN c_item%NOTFOUND;
				   
select count(*) into v_count from clw_item_mapping where item_num = v_item_num and bus_entity_id = v_bus_entity_id;

IF v_count >= 1 THEN
DBMS_OUTPUT.PUT_LINE('Old Item');

select item_id into v_item_id from clw_item_mapping where item_num = v_item_num and bus_entity_id = v_bus_entity_id;

EXECUTE IMMEDIATE 'update clw_item set short_desc = :1,long_desc = :2,mod_by = :3, mod_date = sysdate  where item_id = :4'
				   USING v_description,v_description,v_mod_by,v_item_id;
				   
EXECUTE IMMEDIATE 'update clw_item_meta set clw_value = :1,mod_by = :2,mod_date = sysdate where item_id = :3 and name_value = :4'
				   USING v_uom,v_mod_by,v_item_id,v_name_value;
ELSE
DBMS_OUTPUT.PUT_LINE('New Item');


Select max(sku_num) +1 into v_sku_num from clw_item;

select clw_item_seq.nextval into v_item_id_seq from dual;
EXECUTE IMMEDIATE 'insert into clw_item(item_id,short_Desc,long_desc,eff_date,item_type_cd,item_status_cd,item_order_num,add_date,add_by,mod_date,mod_by,sku_num)
values(:1,:2,:3,sysdate,:4,:5,0,sysdate,:6,sysdate,:7,:8)'
USING v_item_id_seq,v_description,v_description,v_item_type_cd,v_item_status_cd,v_add_by,v_mod_by,v_sku_num;

select clw_item_assoc_seq.nextval into v_item_assoc_id from dual;
EXECUTE IMMEDIATE 'insert into
clw_item_assoc(item_assoc_id,item1_id,item2_id,catalog_id,item_assoc_cd,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,:5,sysdate,:5,sysdate,:6)'
USING v_item_assoc_id,v_item_id_seq,v_item2_id,v_catalog_id,v_item_assoc_cd,v_add_by,v_mod_by;

select clw_item_mapping_seq.nextval into v_item_mapping_id from dual;
EXECUTE IMMEDIATE 'insert into clw_item_mapping(item_mapping_id,item_id,bus_entity_id,item_num, 	item_uom,short_desc,item_mapping_cd,eff_date,status_cd,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,:5,:6,:7,sysdate,:8,sysdate,:9,sysdate,:10)'
USING v_item_mapping_id,v_item_id_seq,v_bus_entity_id,v_item_num,v_uom,v_description,v_item_mapping_cd,v_item_status_cd,v_add_by,v_mod_by;

select clw_item_mapping_seq.nextval into v_item_mapping_id from dual;
EXECUTE IMMEDIATE 'insert into clw_item_mapping(item_mapping_id,item_id,bus_entity_id,item_num, 	item_uom,short_desc,item_mapping_cd,eff_date,status_cd,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,:5,:6,:7,sysdate,:8,sysdate,:9,sysdate,:10)'
USING v_item_mapping_id,v_item_id_seq,v_bus_entity_id_manuf,v_item_num,v_uom,v_description,v_item_mapping_cd1,v_item_status_cd,v_add_by,v_mod_by;

select clw_item_meta_seq.nextval into v_item_meta_id from dual;
EXECUTE IMMEDIATE 'insert into clw_item_meta(item_meta_id,item_id,value_id,name_value,clw_value,add_date,add_by,mod_date,mod_by)
values(:1,:2,0,:3,:4,sysdate,:5,sysdate,:6)'
USING v_item_meta_id,v_item_id_seq,v_name_value,v_uom,v_add_by,v_mod_by;

--select clw_order_guide_structure_seq.nextval into v_order_guide_structure_id from dual;
--EXECUTE IMMEDIATE 'insert into clw_order_guide_structure(order_guide_structure_id,order_guide_id,item_id,category_item_id,quantity,add_date,add_by,mod_date,mod_by)
--values(:1,:2,:3,:4,0,sysdate,:5,sysdate,:6)'
--USING v_order_guide_structure_id,v_order_guide_id,v_item_id_seq,v_category_item_id,v_add_by,v_mod_by;


select clw_catalog_structure_seq.nextval into v_catalog_structure_id from dual;
EXECUTE IMMEDIATE 'insert into clw_catalog_structure
(catalog_structure_id,catalog_id,bus_entity_id,catalog_structure_cd,item_id,eff_date,status_cd,add_date,add_by,mod_date,mod_by,tax_exempt,customer_sku_num)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,sysdate,:8,:9,:10)'
USING v_catalog_structure_id,v_catalog_id,v_bus_entity_id,v_catalog_structure_cd,v_item_id_seq,v_item_status_cd,v_add_by,v_mod_by,v_tax_exempt,v_item_num;

select clw_catalog_structure_seq.nextval into v_catalog_structure_id from dual;
EXECUTE IMMEDIATE 'insert into clw_catalog_structure
(catalog_structure_id,catalog_id,bus_entity_id,catalog_structure_cd,item_id,eff_date,status_cd,add_date,add_by,mod_date,mod_by,tax_exempt,customer_sku_num)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,sysdate,:8,:9,:10)'
USING v_catalog_structure_id,v_catalog_id_al,v_bus_entity_id,v_catalog_structure_cd,v_item_id_seq,v_item_status_cd,v_add_by,v_mod_by,v_tax_exempt,v_item_num;

--select clw_contract_item_seq.nextval into v_contract_item_id from dual;
--EXECUTE IMMEDIATE 'insert into clw_contract_item
--(contract_item_id,contract_id,item_id,eff_date,add_date,add_by,mod_date,mod_by)
--values(:1,:2,:3,sysdate,sysdate,:4,sysdate,:5)'
--USING v_contract_item_id,v_contract_id,v_item_id_seq,v_add_by,v_mod_by;

END IF;

END LOOP;
commit;
END;
/