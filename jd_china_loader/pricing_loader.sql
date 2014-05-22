set serveroutput on;
DECLARE
v_customer_number varchar2(20);
v_current_price number;
v_product_sku varchar2(20);
v_catalog_id number;
v_add_by varchar2(20) := 'Manoj';
v_mod_by varchar2(20) := 'MAnoj';
v_bus_entity_id_dist number := 278774;
v_bus_entity_id_store number := 278772;
v_bus_entity_id_ac number;
v_catalog_desc varchar2(40);
v_catalog_status_cd varchar2(20) := 'ACTIVE';
v_catalog_type_cd varchar2(20) := 'SHOPPING';
v_catalog_assoc_id number;
v_bus_entity_id number;
v_catalog_assoc_cd_si varchar2(20) := 'CATALOG_SITE';
v_catalog_assoc_cd_st varchar2(20) := 'CATALOG_STORE';
v_catalog_assoc_cd_ac varchar2(20) := 'CATALOG_ACCOUNT';
v_item_id number;
v_catalog_structure_id number;
v_catalog_structure_cd varchar2(20) := 'CATALOG_PRODUCT';
v_catalog_structure_status varchar2(20) := 'ACTIVE'; 
v_test number;

v_contract_id number;
v_ref_contract_num number := 0;
v_contract_short_desc varchar2(40);
v_contract_status_Cd varchar2(20) := 'ACTIVE';
v_contract_type_cd varchar2(20) := 'UNKNOWN';
v_contract_items_only_ind number := 0;
v_hide_pricing_ind number := 0;
v_locale_cd varchar2(10) := 'zh_CN';
v_rank_weight number := 0;
v_contract_item_id number;


v_order_guide_id number;
v_order_guide_short_desc varchar2(40);
v_order_guide_type_cd varchar2(20) := 'ORDER_GUIDE_TEMPLATE';
v_order_guide_structure_id number;
v_category_item_id number;

v_catalog_str_ct number;
v_contract_item_ct number;
v_order_guide_str_ct number;

v_short_site_desc varchar2(30) := 'DIST_SITE_REFERENCE_NUMBER';
v_cnt number := 0;

cursor c_pri is
--select customer_number,product_sku,current_price from pri_trans_pricing;
select customer_number,product_sku,current_price from pri_trans_pricing where customer_number in(
select customer_number from pri_trans_customer where country = 'CN');

BEGIN
--DBMS_OUTPUT.PUT_LINE('pppp');
OPEN c_pri;
LOOP
FETCH c_pri into v_customer_number,v_product_sku,v_current_price;
EXIT WHEN c_pri%NOTFOUND;

--Multiply price and cost by 1.17
v_current_price := v_current_price * 1.17;

EXECUTE IMMEDIATE 'insert into 
t(CUSTOMER_NUMBER,PRODUCT_SKU,CURRENT_PRICE,CNT) values(:1,:2,:3,:4)'
USING v_customer_number,v_product_sku,v_current_price,v_cnt;
commit;
v_cnt := v_cnt + 1;

--DBMS_OUTPUT.PUT_LINE('customer_code : ' || v_customer_number || 'product_code: ' ||v_product_sku);

--select item_id into v_item_id from clw_item_mapping where item_num = v_product_sku and bus_entity_id = v_bus_entity_id_dist;
select item_id into v_item_id from clw_item_mapping where item_num = v_product_sku and bus_entity_id = v_bus_entity_id_dist;
--EXECUTE IMMEDIATE 'select item_id into v_item_id from clw_item_mapping where item_num = :1 and bus_entity_id = :2'
--USING v_product_sku,v_bus_entity_id_dist;
--DBMS_OUTPUT.PUT_LINE('item_id = ' || v_item_id);
--select bus_entity_id into v_bus_entity_id from clw_property where short_Desc = v_short_site_desc and clw_value = v_customer_number;
select bus_entity_id into v_bus_entity_id from clw_property where short_Desc = v_short_site_desc
and clw_value = v_customer_number and bus_entity_id in(
select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id in(
select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store));

--DBMS_OUTPUT.PUT_LINE('bus_entity_id = ' || v_bus_entity_id);

--if v_bus_entity_id >= 1 then

--select catalog_id into v_catalog_id from clw_catalog_assoc where bus_entity_id = v_bus_entity_id
--and catalog_assoc_cd = v_catalog_assoc_cd_si;
--(select bus_entity_id from clw_property where short_Desc = v_short_site_desc and clw_value = v_customer_number)
--DBMS_OUTPUT.PUT_LINE('bus_entity_id = ' || v_bus_entity_id);

--else
--v_catalog_id := 0;
--end if;
v_catalog_id := getCatalogID(v_bus_entity_id);

if v_catalog_id >= 1 THEN

--DBMS_OUTPUT.PUT_LINE('Old Catalog : ' || v_catalog_id);


--check 1 to see if item is already in catalog_Structure
select count(*) into v_catalog_str_ct from clw_catalog_structure where catalog_id = v_catalog_id and item_id = v_item_id;

IF v_catalog_str_ct >= 1 THEN

--DBMS_OUTPUT.PUT_LINE('Old Catalog structure Item');
v_test := 0;

ELSE

--DBMS_OUTPUT.PUT_LINE('New Catalog structure Item');

select clw_catalog_structure_seq.nextval into v_catalog_structure_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO CLW_CATALOG_STRUCTURE(catalog_structure_id,catalog_id,catalog_structure_cd,item_id,eff_date,status_cd,add_date,add_by,
mod_date,mod_by) VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6,sysdate,:7)'
USING v_catalog_structure_id,v_catalog_id,v_catalog_structure_cd,v_item_id,v_catalog_structure_status,v_add_by,v_mod_by;
				   
END IF;
------end check 1

--Check  2 to see if contract item exists

select count(*) into v_contract_id from clw_contract where catalog_id = v_catalog_id;

IF v_contract_id >= 1 THEN

select contract_id into v_contract_id from clw_contract where catalog_id = v_catalog_id;

select count(*) into v_contract_item_ct from clw_contract_item where contract_id = v_contract_id and item_id = v_item_id;

IF v_contract_item_ct >= 1 THEN
--DBMS_OUTPUT.PUT_LINE('Old Contract Item item_id: ' || v_item_id);

EXECUTE IMMEDIATE 'UPDATE clw_contract_item set amount = :1,dist_cost = :2,mod_by = :3,mod_date = sysdate 
where contract_id = :4 and item_id = :5'
USING v_current_price,v_current_price,v_mod_by,v_contract_id,v_item_id;

ELSE

--DBMS_OUTPUT.PUT_LINE('New Contract Item :' || v_item_id );

select clw_contract_item_seq.nextval into v_contract_item_id from dual;
EXECUTE IMMEDIATE 'insert into clw_contract_item
(contract_item_id,contract_id,item_id,eff_date,add_date,add_by,mod_date,mod_by,amount,dist_cost)
values(:1,:2,:3,sysdate,sysdate,:4,sysdate,:5,:6,:7)'
USING v_contract_item_id,v_contract_id,v_item_id,v_add_by,v_mod_by,v_current_price,v_current_price;

END IF;

ELSE

select clw_contract_seq.nextval into v_contract_id from dual;

v_contract_short_desc := v_customer_number || '-'|| v_contract_id;

EXECUTE IMMEDIATE 'INSERT INTO CLW_CONTRACT(contract_id,ref_contract_num,short_Desc,catalog_id,contract_status_Cd,contract_type_cd,eff_date,contract_items_only_ind,
hide_pricing_ind,locale_cd,rank_weight,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,:5,:6,sysdate,:7,:8,:9,:10,sysdate,:11,sysdate,:12)'
USING v_contract_id,v_ref_contract_num,v_contract_short_desc,v_catalog_id,v_contract_status_Cd,v_contract_type_cd,
v_contract_items_only_ind,v_hide_pricing_ind,v_locale_cd,v_rank_weight,v_add_by,v_mod_by;

select clw_contract_item_seq.nextval into v_contract_item_id from dual;

EXECUTE IMMEDIATE 'insert into clw_contract_item
(contract_item_id,contract_id,item_id,eff_date,add_date,add_by,mod_date,mod_by,amount,dist_cost)
values(:1,:2,:3,sysdate,sysdate,:4,sysdate,:5,:6,:7)'
USING v_contract_item_id,v_contract_id,v_item_id,v_add_by,v_mod_by,v_current_price,v_current_price;


END IF;

--End of check 2

--Check 3 see if order_guide_structure exists 
select count(*) into v_order_guide_id from clw_order_guide where catalog_id = v_catalog_id;

IF v_order_guide_id >= 1 THEN

select order_guide_id into v_order_guide_id from clw_order_guide where catalog_id = v_catalog_id;

select count(*) into v_order_guide_str_ct from clw_order_guide_structure where order_guide_id = v_order_guide_id and item_id = v_item_id;

IF v_order_guide_str_ct >= 1 THEN

--DBMS_OUTPUT.PUT_LINE('Old Order Guide Item');
v_test := 0;

ELSE

--DBMS_OUTPUT.PUT_LINE('New Order Guide Item');

select clw_order_guide_structure_seq.nextval into v_order_guide_structure_id from dual;
EXECUTE IMMEDIATE 'insert into clw_order_guide_structure(order_guide_structure_id,order_guide_id,item_id,category_item_id,quantity,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,0,sysdate,:5,sysdate,:6)'
USING v_order_guide_structure_id,v_order_guide_id,v_item_id,v_category_item_id,v_add_by,v_mod_by;

END IF;

ELSE

select clw_order_guide_seq.nextval into v_order_guide_id from dual;
 
v_order_guide_short_desc := 'v_customer_number-'|| v_order_guide_id;
EXECUTE IMMEDIATE 'INSERT INTO CLW_ORDER_GUIDE(order_guide_id,short_desc,catalog_id,order_guide_type_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_order_guide_id,v_order_guide_short_desc,v_catalog_id,v_order_guide_type_cd,v_add_by,v_mod_by;


select clw_order_guide_structure_seq.nextval into v_order_guide_structure_id from dual;

EXECUTE IMMEDIATE 'insert into clw_order_guide_structure(order_guide_structure_id,order_guide_id,item_id,category_item_id,quantity,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,0,sysdate,:5,sysdate,:6)'
USING v_order_guide_structure_id,v_order_guide_id,v_item_id,v_category_item_id,v_add_by,v_mod_by;

END IF;

--End of Check 3




ELSE
select clw_catalog_seq.nextval into v_catalog_id from dual;

v_catalog_desc := 'JD CHINA TEST-'|| v_catalog_id;
 
EXECUTE IMMEDIATE 'INSERT INTO clw_catalog(catalog_id,short_Desc,catalog_status_cd,catalog_type_Cd,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_id,v_catalog_desc,v_catalog_status_cd,v_catalog_type_cd,v_add_by,v_mod_by;


select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id,v_catalog_assoc_cd_si,v_add_by,v_mod_by;

select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id_store,v_catalog_assoc_cd_st,v_add_by,v_mod_by;

select bus_entity2_id into v_bus_entity_id_ac from clw_bus_entity_assoc where bus_entity1_id = v_bus_entity_id;

select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id_ac,v_catalog_assoc_cd_ac,v_add_by,v_mod_by;


select clw_catalog_structure_seq.nextval into v_catalog_structure_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO CLW_CATALOG_STRUCTURE(catalog_structure_id,catalog_id,catalog_structure_cd,item_id,eff_date,status_cd,add_date,add_by,
mod_date,mod_by) VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6,sysdate,:7)'
USING v_catalog_structure_id,v_catalog_id,v_catalog_structure_cd,v_item_id,v_catalog_structure_status,v_add_by,v_mod_by;


select clw_contract_seq.nextval into v_contract_id from dual;

v_contract_short_desc := v_customer_number || '-'|| v_contract_id;

EXECUTE IMMEDIATE 'INSERT INTO CLW_CONTRACT(contract_id,ref_contract_num,short_Desc,catalog_id,contract_status_Cd,contract_type_cd,eff_date,contract_items_only_ind,
hide_pricing_ind,locale_cd,rank_weight,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,:5,:6,sysdate,:7,:8,:9,:10,sysdate,:11,sysdate,:12)'
USING v_contract_id,v_ref_contract_num,v_contract_short_desc,v_catalog_id,v_contract_status_Cd,v_contract_type_cd,
v_contract_items_only_ind,v_hide_pricing_ind,v_locale_cd,v_rank_weight,v_add_by,v_mod_by;

select clw_contract_item_seq.nextval into v_contract_item_id from dual;

EXECUTE IMMEDIATE 'insert into clw_contract_item
(contract_item_id,contract_id,item_id,eff_date,add_date,add_by,mod_date,mod_by,amount,dist_cost)
values(:1,:2,:3,sysdate,sysdate,:4,sysdate,:5,:6,:7)'
USING v_contract_item_id,v_contract_id,v_item_id,v_add_by,v_mod_by,v_current_price,v_current_price;


select clw_order_guide_seq.nextval into v_order_guide_id from dual;
 
v_order_guide_short_desc := 'JD CHINA TEST-'|| v_order_guide_id;
EXECUTE IMMEDIATE 'INSERT INTO CLW_ORDER_GUIDE(order_guide_id,short_desc,catalog_id,order_guide_type_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_order_guide_id,v_order_guide_short_desc,v_catalog_id,v_order_guide_type_cd,v_add_by,v_mod_by;


select clw_order_guide_structure_seq.nextval into v_order_guide_structure_id from dual;

EXECUTE IMMEDIATE 'insert into clw_order_guide_structure(order_guide_structure_id,order_guide_id,item_id,category_item_id,quantity,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,0,sysdate,:5,sysdate,:6)'
USING v_order_guide_structure_id,v_order_guide_id,v_item_id,v_category_item_id,v_add_by,v_mod_by;


END IF;



END LOOP;
COMMIT;
close c_pri;
END;
/























