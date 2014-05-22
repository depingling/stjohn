--set serveroutput on;
-- To run the pricing process 
--exec JD_CHINA_LOADER.PRICING_LOADER;
--To run Customer Loader
--exec JD_CHINA_LOADER.CUSTOMER_LOADER;
--To run Item Loader
--exec JD_CHINA_LOADER.ITEM_LOADER;
---Tables pri_trans_pricing,pri_trans_customer,pri_trans_item,PRI_ITEM_MASTER,pri_item_branch,JD_BUS_UNIT_COUNTRY_CD
CREATE OR REPLACE PACKAGE JD_CHINA_LOADER AS
PROCEDURE PRICING_LOADER;
FUNCTION getCatalogID(v_bus_id IN NUMBER)  RETURN VARCHAR2;
--IS v_cat_id number;
FUNCTION getItemID(v_product_sku IN VARCHAR2,v_bus_entity_id_dist IN NUMBER)  RETURN NUMBER;
--IS v_item_id number;
FUNCTION getSiteID(v_short_site_desc IN VARCHAR2,v_customer_number IN VARCHAR2,
v_bus_entity_id_store IN NUMBER)  RETURN NUMBER;
--IS v_site_id number;
PROCEDURE deletePricingItem;
PROCEDURE ITEM_LOADER; --- Load JD China Items 
PROCEDURE CUSTOMER_LOADER; --- Load JD China Customers(Accounts and Sites)

END JD_CHINA_LOADER;
/

CREATE OR REPLACE PACKAGE BODY JD_CHINA_LOADER AS
--DECLARE
PROCEDURE PRICING_LOADER IS
v_customer_number varchar2(20);
v_current_price number;
v_product_sku varchar2(20);
v_catalog_id number :=0;
v_add_by varchar2(20) := 'Manoj';
v_mod_by varchar2(20) := 'Manoj';
v_bus_entity_id_dist number := 186098;
v_bus_entity_id_store number := 186096;
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
v_mod_by_u varchar2(20) := 'Manoj';

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

--EXECUTE IMMEDIATE 'insert into 
--t(CUSTOMER_NUMBER,PRODUCT_SKU,CURRENT_PRICE,CNT) values(:1,:2,:3,:4)'
--USING v_customer_number,v_product_sku,v_current_price,v_cnt;
--commit;
--v_cnt := v_cnt + 1;
v_item_id := JD_CHINA_LOADER.getItemID(v_product_sku,v_bus_entity_id_dist);
v_bus_entity_id := JD_CHINA_LOADER.getSiteID(v_short_site_desc,v_customer_number,v_bus_entity_id_store);
IF v_item_id = 0 OR v_bus_entity_id = 0 THEN
DBMS_OUTPUT.PUT_LINE('Error Item or Site not Found');
DBMS_OUTPUT.PUT_LINE('v_customer_number : ' || v_customer_number);
DBMS_OUTPUT.PUT_LINE('v_product_sku : ' || v_product_sku);
ELSE
v_catalog_id := JD_CHINA_LOADER.getCatalogID(v_bus_entity_id);

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
USING v_current_price,v_current_price,v_mod_by_u,v_contract_id,v_item_id;

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
 
v_order_guide_short_desc := v_customer_number ||'-'|| v_order_guide_id;
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

v_catalog_desc := v_customer_number|| '-' || v_catalog_id;
 
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
 
v_order_guide_short_desc := v_customer_number ||'-'|| v_order_guide_id;
EXECUTE IMMEDIATE 'INSERT INTO CLW_ORDER_GUIDE(order_guide_id,short_desc,catalog_id,order_guide_type_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_order_guide_id,v_order_guide_short_desc,v_catalog_id,v_order_guide_type_cd,v_add_by,v_mod_by;


select clw_order_guide_structure_seq.nextval into v_order_guide_structure_id from dual;

EXECUTE IMMEDIATE 'insert into clw_order_guide_structure(order_guide_structure_id,order_guide_id,item_id,category_item_id,quantity,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,0,sysdate,:5,sysdate,:6)'
USING v_order_guide_structure_id,v_order_guide_id,v_item_id,v_category_item_id,v_add_by,v_mod_by;


END IF;

END IF;

END LOOP;
COMMIT;

close c_pri;
JD_CHINA_LOADER.deletePricingItem;
--delete_pricing_item();

END PRICING_LOADER;

FUNCTION getCatalogID(v_bus_id IN NUMBER)  RETURN VARCHAR2 IS
v_cat_id number;
v_catalog_assoc_cd varchar2(20) := 'CATALOG_SITE';
-- explain INTO and return
BEGIN

select count(*) into v_cat_id from clw_catalog_assoc where bus_entity_id = v_bus_id
and catalog_assoc_cd = v_catalog_assoc_cd;

IF v_cat_id > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Duplicate Catalog Found');
DBMS_OUTPUT.PUT_LINE('select catalog_id into v_cat_id from clw_catalog_assoc where');
DBMS_OUTPUT.PUT_LINE('bus_entity_id ='|| v_bus_id ||'and catalog_assoc_cd = '|| v_catalog_assoc_cd);
v_cat_id := 0;
return v_cat_id;

ELSE
BEGIN

select catalog_id into v_cat_id from clw_catalog_assoc where bus_entity_id = v_bus_id
and catalog_assoc_cd = v_catalog_assoc_cd;

if sql%rowcount=0 then
v_cat_id := 0;
return v_cat_id;
--raise no_data_found;
else
return v_cat_id;
--dbms_output.put_line('Inserted '||sql%rowcount||' records');
end if;

EXCEPTION
WHEN NO_DATA_FOUND THEN
DBMS_OUTPUT.PUT_LINE('Error No catalog Found');
v_cat_id := 0;
return v_cat_id;

END;
--ELSE

--IF v_cat_id > 1 THEN
--DBMS_OUTPUT.PUT_LINE('Error Duplicate Catalog Found');
--DBMS_OUTPUT.PUT_LINE('select catalog_id into v_cat_id from clw_catalog_assoc where');
--DBMS_OUTPUT.PUT_LINE('bus_entity_id ='|| v_bus_id ||'and catalog_assoc_cd = '|| v_catalog_assoc_cd);

END IF;

END getCatalogID;

FUNCTION getItemID(v_product_sku IN VARCHAR2,v_bus_entity_id_dist IN NUMBER)  RETURN NUMBER IS
v_item_id number;

BEGIN
select count(item_id) into v_item_id from clw_item_mapping where item_num = v_product_sku
and bus_entity_id = v_bus_entity_id_dist;

IF V_ITEM_ID > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Duplicate Item Found');
DBMS_OUTPUT.PUT_LINE('select item_id from clw_item_mapping where');
DBMS_OUTPUT.PUT_LINE('item_num ='|| v_product_sku ||'and bus_entity_id ='|| v_bus_entity_id_dist);
v_item_id := 0;
return v_item_id;

ELSE
BEGIN
select item_id into v_item_id from clw_item_mapping where item_num = v_product_sku 
and bus_entity_id = v_bus_entity_id_dist;

if sql%rowcount=0 then
v_item_id := 0;
return v_item_id;
--raise no_data_found;
else
return v_item_id;
--dbms_output.put_line('Inserted '||sql%rowcount||' records');
end if;

EXCEPTION
WHEN NO_DATA_FOUND THEN
DBMS_OUTPUT.PUT_LINE('Error No ITEM Found');
DBMS_OUTPUT.PUT_LINE('select item_id into v_item_id from clw_item_mapping where item_num = v_product_sku and bus_entity_id = v_bus_entity_id_dist;');
DBMS_OUTPUT.PUT_LINE('v_product_sku = '|| v_product_sku ||' v_bus_entity_id_dist = ' || v_bus_entity_id_dist);
v_item_id := 0;
return v_item_id;

END;

END IF;
END getItemID;

FUNCTION getSiteID(v_short_site_desc IN VARCHAR2,v_customer_number IN VARCHAR2,
v_bus_entity_id_store IN NUMBER)  RETURN NUMBER IS 
v_site_id NUMBER;

v_item_id number;


BEGIN

select count(bus_entity_id) into v_site_id from clw_property where short_Desc = v_short_site_desc
and clw_value = v_customer_number and bus_entity_id in(
select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id in(
select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store));

IF v_site_id > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Duplicate site Found');
DBMS_OUTPUT.PUT_LINE('select bus_entity_id from clw_property where short_Desc ='|| v_short_site_desc);
DBMS_OUTPUT.PUT_LINE('and clw_value ='|| v_customer_number ||'and bus_entity_id in(');
DBMS_OUTPUT.PUT_LINE('select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id in(');
DBMS_OUTPUT.PUT_LINE('select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity2_id =' ||  v_bus_entity_id_store ||'))');

v_site_id := 0;
return v_site_id;

ELSE

BEGIN

select bus_entity_id into v_site_id from clw_property where short_Desc = v_short_site_desc
and clw_value = v_customer_number and bus_entity_id in(
select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id in(
select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store));

if sql%rowcount=0 then
v_site_id := 0;
return v_site_id;
--raise no_data_found;
else
return v_site_id;
--dbms_output.put_line('Inserted '||sql%rowcount||' records');
end if;

EXCEPTION
WHEN NO_DATA_FOUND THEN
DBMS_OUTPUT.PUT_LINE('Error No SITE Found');
DBMS_OUTPUT.PUT_LINE('select bus_entity_id into v_site_id from clw_property where short_Desc = '); DBMS_OUTPUT.PUT_LINE('v_short_site_desc and clw_value = v_customer_number and bus_entity_id in(');
DBMS_OUTPUT.PUT_LINE('select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id in(');
DBMS_OUTPUT.PUT_LINE('select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id ='); 
DBMS_OUTPUT.PUT_LINE('v_bus_entity_id_store));');
DBMS_OUTPUT.PUT_LINE('v_short_site_desc = '||v_short_site_desc);
DBMS_OUTPUT.PUT_LINE('v_customer_number = ' || v_customer_number);
DBMS_OUTPUT.PUT_LINE('v_bus_entity_id_store = ' || v_bus_entity_id_store);
v_site_id := 0;
return v_site_id;

END;

END IF;
END getSiteID;



PROCEDURE  deletePricingItem IS 
v_bus_entity_id number;
v_customer_number varchar2(40);
v_catalog_id number;
v_contract_id number;
v_dist_id number := 186098;
v_store_id number := 186096;
v_contract_item_id number;
v_short_desc_property VARCHAR2(40) := 'DIST_SITE_REFERENCE_NUMBER';
v_cnt NUMBER := 0;

CURSOR c_pr is
select distinct customer_number from pri_trans_pricing;

BEGIN
OPEN c_pr;
LOOP
FETCH c_pr INTO v_customer_number;
EXIT WHEN c_pr%NOTFOUND;

dbms_output.put_line('more then 1 Records found v_customer' || v_customer_number);

select count(*) into v_cnt from 
(select distinct cp.bus_entity_id,ptp.customer_number,cca.catalog_id,cc.contract_id
from clw_property cp,pri_trans_pricing ptp,clw_catalog_assoc cca,clw_contract cc,
clw_bus_entity cbe,clw_catalog ca
where ptp.customer_number = cp.clw_value
and cbe.bus_entity_id = cp.bus_entity_id and cbe.bus_entity_id = cca.bus_entity_id 
and cca.catalog_id = ca.catalog_id and ca.catalog_id = cc.catalog_id
and cp.short_Desc = v_short_desc_property
and ptp.customer_number = v_customer_number and cbe.bus_entity_id in(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id 
in(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_store_id)));

IF v_cnt > 1 THEN

DBMS_OUTPUT.PUT_LINE('Error Duplicate Contract Found');
DBMS_OUTPUT.PUT_LINE('select distinct cp.bus_entity_id,ptp.customer_number,cca.catalog_id,cc.contract_id INTO v_bus_entity_id,v_customer_number,v_catalog_id,v_contract_id
from clw_property cp,pri_trans_pricing ptp,clw_catalog_assoc cca,clw_contract cc,
clw_bus_entity cbe,clw_catalog ca
where ptp.customer_number = cp.clw_value
and cbe.bus_entity_id = cp.bus_entity_id and cbe.bus_entity_id = cca.bus_entity_id 
and cca.catalog_id = ca.catalog_id and ca.catalog_id = cc.catalog_id
and cp.short_Desc = v_short_desc_property
and ptp.customer_number =' ||v_customer_number|| 'and cbe.bus_entity_id in(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id 
in(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id ='|| v_store_id ||'))');

ELSIF v_cnt = 1 THEN
select distinct cp.bus_entity_id,ptp.customer_number,cca.catalog_id,cc.contract_id INTO v_bus_entity_id,v_customer_number,v_catalog_id,v_contract_id
from clw_property cp,pri_trans_pricing ptp,clw_catalog_assoc cca,clw_contract cc,
clw_bus_entity cbe,clw_catalog ca
where ptp.customer_number = cp.clw_value
and cbe.bus_entity_id = cp.bus_entity_id and cbe.bus_entity_id = cca.bus_entity_id 
and cca.catalog_id = ca.catalog_id and ca.catalog_id = cc.catalog_id
and cp.short_Desc = v_short_desc_property
and ptp.customer_number = v_customer_number and cbe.bus_entity_id in(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id 
in(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_store_id));

dbms_output.put_line('more then 2 Records found v_customer' || v_customer_number);
--EXECUTE IMMEDIATE 'DELETE FROM clw_contract_item where contract_id = :1 and item_id not in
--(select item_id from clw_item_mapping where bus_entity_id = :2 and item_num in
--(select distinct product_sku from pri_trans_pricing where customer_number = :3))'
--USING v_contract_id,v_dist_id,v_customer_id;


DELETE FROM clw_contract_item where contract_id = v_contract_id and item_id not in (select item_id from clw_item_mapping where bus_entity_id = v_dist_id and item_num in
(select distinct product_sku from pri_trans_pricing where customer_number = v_customer_number));


ELSE
DBMS_OUTPUT.PUT_LINE('Error in deletePricingItem No Record Found');
DBMS_OUTPUT.PUT_LINE('v_customer_number = ' || v_customer_number);

END IF;
--select count(contract_item_id) into v_contract_item_id FROM clw_contract_item where contract_id = v_contract_id and item_id not in (select item_id from --clw_item_mapping where bus_entity_id = v_dist_id and item_num in
--(select distinct product_sku from pri_trans_pricing where customer_number = v_customer_number));

--if v_contract_item_id < 1 then
--dbms_output.put_line('no Records');
--else
--dbms_output.put_line('Records Found');
--DBMS_OUTPUT.PUT_LINE('Contract item to Delete :' || v_contract_id 
--|| 'v_customer_number ' || v_customer_number);
--end if;

END LOOP;
COMMIT;
CLOSE c_pr;

END deletePricingItem;





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
--set serveroutput on;
PROCEDURE ITEM_LOADER IS
--DECLARE
v_item_num varchar2(200);
v_description varchar2(255);
v_category varchar2(100);
v_uom varchar2(20);
v_count number;
v_dist_id number := 186098;
--v_bus_entity_id_haz number := 195297;
v_bus_entity_id_manuf number := 203090;
v_mod_by varchar2(20) := 'Manoj';
v_add_by varchar2(20) := 'Manoj';
v_name_value varchar2(4) := 'UOM';
v_item_type_cd varchar2(20) := 'PRODUCT';
v_item_status_cd varchar2(20) := 'ACTIVE';
v_item_id number;
v_item_id_seq number;
v_item_assoc_id number;
v_catalog_id number := 13147;
v_item2_id number := 114138;
v_item_assoc_cd varchar2(40):= 'PRODUCT_PARENT_CATEGORY';
v_item_mapping_cd varchar2(20) := 'ITEM_DISTRIBUTOR';
v_item_mapping_cd1 varchar2(20) := 'ITEM_MANUFACTURER';
v_item_mapping_id number;
v_item_meta_id number;
v_order_guide_structure_id number;
--v_order_guide_id number := 1308100;
v_category_item_id number := 114138;
v_catalog_structure_cd varchar2(20) := 'CATALOG_PRODUCT';
v_tax_exempt varchar2(10) := 'false';
v_catalog_structure_id number;
v_contract_item_id number;
v_sku_num number;
--v_catalog_id_al is alternative catalog or store catalog
v_catalog_id_al number:= 23447;
v_contract_id_al NUMBER:= 23010;
v_amount NUMBER := 0;
v_dist_cost NUMBER := 0;

CURSOR c_item is

select distinct TI.ITEM_NUMBER2,TI.description,TI.uom_primary_pricing from pri_trans_item TI 
where TI.item_number2 in
(select item_number_2 from PRI_ITEM_MASTER where item_master_id in
(select item_master_id from pri_item_branch where business_unit in(
select business_unit from JD_BUS_UNIT_COUNTRY_CD where country_code = 'CN')));
--select ITEM_NUMBER2,description,uom_primary_pricing  from temp_item_loader;
BEGIN
OPEN c_item;
LOOP
FETCH c_item into v_item_num,v_description,v_uom;
EXIT WHEN c_item%NOTFOUND;

--DBMS_OUTPUT.PUT_LINE('v_item_num = ' || v_item_num);
select count(*) into v_count from clw_item_mapping where item_num = v_item_num and bus_entity_id = v_dist_id;


IF v_count > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Duplicate Item Found :');
DBMS_OUTPUT.PUT_LINE('select item_id from clw_item_mapping');
DBMS_OUTPUT.PUT_LINE('where item_num ='|| v_item_num || 'and bus_entity_id ='|| v_dist_id);

ELSIF v_count = 1 THEN
--DBMS_OUTPUT.PUT_LINE('Old Item');

select item_id into v_item_id from clw_item_mapping where item_num = v_item_num and bus_entity_id = v_dist_id;

EXECUTE IMMEDIATE 'update clw_item set short_desc = :1,long_desc = :2,mod_by = :3, mod_date = sysdate  where item_id = :4'
				   USING v_description,v_description,v_mod_by,v_item_id;
				   
EXECUTE IMMEDIATE 'update clw_item_meta set clw_value = :1,mod_by = :2,mod_date = sysdate where item_id = :3 and name_value = :4'
				   USING v_uom,v_mod_by,v_item_id,v_name_value;
ELSE
--DBMS_OUTPUT.PUT_LINE('New Item');


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
USING v_item_mapping_id,v_item_id_seq,v_dist_id,v_item_num,v_uom,v_description,v_item_mapping_cd,v_item_status_cd,v_add_by,v_mod_by;

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
USING v_catalog_structure_id,v_catalog_id,v_dist_id,v_catalog_structure_cd,v_item_id_seq,v_item_status_cd,v_add_by,v_mod_by,v_tax_exempt,v_item_num;

select clw_catalog_structure_seq.nextval into v_catalog_structure_id from dual;
EXECUTE IMMEDIATE 'insert into clw_catalog_structure
(catalog_structure_id,catalog_id,bus_entity_id,catalog_structure_cd,item_id,eff_date,status_cd,add_date,add_by,mod_date,mod_by,tax_exempt,customer_sku_num)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,sysdate,:8,:9,:10)'
USING v_catalog_structure_id,v_catalog_id_al,v_dist_id,v_catalog_structure_cd,v_item_id_seq,v_item_status_cd,v_add_by,v_mod_by,v_tax_exempt,v_item_num;

select clw_contract_item_seq.nextval into v_contract_item_id from dual;
EXECUTE IMMEDIATE 'insert into clw_contract_item
(contract_item_id,contract_id,item_id,eff_date,add_date,add_by,mod_date,mod_by,amount,dist_cost)
values(:1,:2,:3,sysdate,sysdate,:4,sysdate,:5,:6,:7)'
USING v_contract_item_id,v_contract_id_al,v_item_id_seq,v_add_by,v_mod_by,v_amount,v_dist_cost;

END IF;

END LOOP;
commit;
END ITEM_LOADER;




---*******************************************************************************************************************------
--Customer Loader
--********************************************************************************************************************

PROCEDURE CUSTOMER_LOADER IS
v_bus_entity_id_a number;
v_bus_entity_id_s number;
v_bus_entity_id_store number := 186096;
v_customer_name varchar2(255);
v_bus_entity_type_cd_a varchar2(30) := 'ACCOUNT';
v_bus_entity_type_cd_s varchar2(30) := 'SITE';
v_bus_entity_status_cd varchar2(30) := 'ACTIVE';
v_workflow_role_cd varchar2(30) := 'UNKNOWN';
v_locale_cd varchar2(30) := 'zh_CN';
v_add_by varchar2(30) := 'Manoj';
v_mod_by varchar2(30) := 'Manoj';
v_customer_number varchar2(40);
v_parent_customer_number number;
v_first_name varchar2(80);
v_address1 varchar2(255);
v_address2 varchar2(255);
v_address3 varchar2(255);
v_address4 varchar2(255);
v_city varchar2(80);
v_state varchar2(80);
v_country_cd varchar2(80);
v_country varchar2(20) := 'CHINA';
v_postal_code varchar2(80);
v_phone_number varchar2(80);
v_fax_number varchar2(80);

V_PROPERTY_ID number;

v_bus_entity_assoc_id number;
v_bus_entity_assoc_cd_a varchar2(30) := 'ACCOUNT OF STORE';
v_bus_entity_assoc_cd_s varchar2(30) := 'SITE OF ACCOUNT';

v_address_id number;
v_address_status_cd varchar2(10) := 'ACTIVE';
v_address_type_cd_p varchar2(20) := 'PRIMARY CONTACT';
v_primary_ind number := 1;
v_address_type_cd_b varchar2(20) := 'BILLING';
v_address_type_cd_s varchar2(20) := 'SHIPPING';
v_phone_id number;
v_phone_type_cd varchar2(10) := 'PHONE';
v_phone_status_cd varchar2(10) := 'ACTIVE';

v_property_short_desc_a varchar2(30) := 'DIST_ACCT_REF_NUM';
v_property_type_cd_a varchar2(30) := 'DIST_ACCT_REF_NUM';
v_property_short_desc_s varchar2(30) := 'DIST_SITE_REFERENCE_NUMBER';
v_property_type_cd_s varchar2(30) := 'SITE_REFERENCE_NUMBER';
v_property_status_cd varchar2(10) := 'ACTIVE';
v_property_short_desc_bu varchar2(30) := 'BUDGET_ACCRUAL_TYPE_CD';
v_property_type_cd_bu varchar2(30) := 'BUDGET_ACCRUAL_TYPE_CD';
c_value_b varchar2(20) := 'BY_PERIOD';
v_c_acc_site_cnt number := 0;
v_c_acc_cnt number := 0;
v_c_site_cnt number := 0;

v_prop_short_desc varchar2(255);
v_prop_clw_value varchar2(4000);
v_prop_status_cd varchar2(40);
v_prop_type_Cd varchar2(40);

v_erp_num varchar2(40);


v_catalog_id number;
v_catalog_desc varchar2(40);
v_catalog_status_cd varchar2(20) := 'ACTIVE';
v_catalog_type_cd varchar2(20) := 'SHOPPING';
v_catalog_assoc_id number;
v_catalog_assoc_cd_si varchar2(20) := 'CATALOG_SITE';
v_catalog_assoc_cd_st varchar2(20) := 'CATALOG_STORE';
v_catalog_assoc_cd_ac varchar2(20) := 'CATALOG_ACCOUNT';
v_bus_entity_id_ac number;

v_c_acc_up number := 0;
v_c_site_up number := 0;
v_template_id NUMBER := 372700;

cursor c_acc is
select customer_name,customer_number,parent_customer_number,contact_name,address_line_1,address_line_2,address_line_3,address_line_4,
city,state,country,postal_code,phone_number,fax_number
from pri_trans_customer where customer_type = 'ACCOUNT' and country in('CN','HK');
--and customer_number = '6640287';
--and connection_status in('unprocessed','updated');


cursor c_acc_site is
select customer_name,customer_number,parent_customer_number,contact_name,address_line_1,address_line_2,address_line_3,address_line_4,
city,state,country,postal_code,phone_number,fax_number
from pri_trans_customer where customer_type = 'ACCOUNT_AND_SITE' and country in('CN','HK');
--and customer_number = '6640287';
--and connection_status in('unprocessed','updated');


cursor c_site is
select customer_name,customer_number,parent_customer_number,contact_name,address_line_1,address_line_2,address_line_3,address_line_4,
city,state,country,postal_code,phone_number,fax_number
from pri_trans_customer where customer_type = 'SITE' and country in('CN','HK');
--and customer_number = '6640287';
--and parent_customer_number not --in(6200,6220,21194633,21235597,21445404,21445640,21482408,21483056,21522650,21522730,21522895,21560322,21599753,21600099,21678283,21678822)
--and connection_status in('unprocessed','updated');


cursor c_template is 
select short_Desc,clw_value,property_status_cd,property_type_Cd 
from clw_property where bus_entity_id = v_template_id;

BEGIN

--Open Cursor for customer_type = ACCOUNT_AND_SITE
--DBMS_OUTPUT.PUT_LINE('Load Accounts And Sites');
open c_acc_site;
LOOP
--DBMS_OUTPUT.PUT_LINE('v_customer_name,v_customer_number ' || v_customer_name || v_customer_number);
FETCH c_acc_site into v_customer_name,v_customer_number,v_parent_customer_number,v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country_cd,v_postal_code,v_phone_number,v_fax_number;
EXIT WHEN c_acc_site%NOTFOUND;


---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Start ACCOUNT INFORMATION 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--select count(*) into v_c_acc_site_cnt from clw_property where short_desc = 'DIST_ACCT_REF_NUM' and clw_value = v_customer_number;

select count(*) into v_c_acc_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_a and clw_value = v_customer_number) and bus_entity2_id = v_bus_entity_id_store;

IF v_c_acc_up >= 1 THEN
--DBMS_OUTPUT.PUT_LINE('Old Account');
select bus_entity1_id into v_c_acc_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_a and clw_value = v_customer_number) and bus_entity2_id = v_bus_entity_id_store;


EXECUTE IMMEDIATE 'UPDATE CLW_BUS_ENTITY SET short_desc = :1, long_desc = :2,mod_by = :3,mod_date = sysdate
where bus_entity_id = :4'
USING v_customer_name,v_customer_name,v_mod_by,v_c_acc_up;

EXECUTE IMMEDIATE 'UPDATE CLW_ADDRESS SET name1 = :1,address1 = :2,address2 = :3,address3 = :4,address4 = :5,
city = :6,state_province_cd = :7,country_cd = :8,postal_code = :9,mod_by = :10,mod_date = sysdate
where bus_entity_id = :11'
USING v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,v_postal_code,
v_mod_by,v_c_acc_up;

EXECUTE IMMEDIATE 'UPDATE clw_phone set phone_num = :1 ,mod_by = :2,mod_date = sysdate
where bus_entity_id = :3'
USING v_phone_number,v_mod_by,v_c_acc_up;

ELSE
--Create new entry in clw_bus_entity with Account Information
SELECT clw_bus_entity_seq.nextval into v_bus_entity_id_a from dual;

v_erp_num := '#'|| v_bus_entity_id_a;

EXECUTE IMMEDIATE 'insert into clw_bus_entity
(bus_entity_id,short_desc,long_desc,eff_date,bus_entity_type_cd,bus_entity_status_cd,workflow_role_cd,locale_cd,add_date,
add_by,mod_date,mod_by,erp_num) 
values(:1,:2,:3,sysdate,:4,:5,:6,:7,sysdate,:8,sysdate,:9,:10)'
USING v_bus_entity_id_a,v_customer_name,v_customer_name,v_bus_entity_type_cd_a,v_bus_entity_status_cd,v_workflow_role_cd,v_locale_cd,v_add_by,v_mod_by,v_erp_num;


----Create new entry in clw_bus_entity_Assoc with Account Information
select clw_bus_entity_assoc_seq.nextval into v_bus_entity_assoc_id from dual;

EXECUTE IMMEDIATE 'insert into clw_bus_entity_assoc(bus_entity_assoc_id,bus_entity1_id,bus_entity2_id,bus_entity_assoc_cd,add_Date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_bus_entity_assoc_id,v_bus_entity_id_a,v_bus_entity_id_store,v_bus_entity_assoc_cd_a,v_add_by,v_mod_by;


--Create new entry in clw_address with Account Information
--one for address_status_cd = PRIMARY_CONTACT
select clw_Address_seq.nextval into v_address_id from dual;
EXECUTE IMMEDIATE 'insert into clw_address(address_id,bus_entity_id,name1,address1,address2,address3,address4,city,state_province_cd,country_cd,
postal_code,address_status_cd,address_type_cd,add_date,add_by,mod_Date,mod_by,primary_ind) values(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,sysdate,:15,sysdate,:17,:18)'
USING v_address_id,v_bus_entity_id_a,v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,
v_postal_code,v_address_status_cd,v_address_type_cd_p,v_add_by,v_mod_by,v_primary_ind;

--one for address_status_cd = BILLING
select clw_Address_seq.nextval into v_address_id from dual;
EXECUTE IMMEDIATE 'insert into clw_address(address_id,bus_entity_id,name1,address1,address2,address3,address4,city,state_province_cd,country_cd,postal_code,
address_status_cd,address_type_cd,add_date,add_by,mod_Date,mod_by) 
values(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,sysdate,:15,sysdate,:17)'
USING v_address_id,v_bus_entity_id_a,v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,
v_postal_code,v_address_status_cd,v_address_type_cd_b,v_add_by,v_mod_by;


--Create new entry in clw_phone with Account Information
select clw_phone_seq.nextval into v_phone_id from dual;
EXECUTE IMMEDIATE 'insert into clw_phone(phone_id,bus_entity_id,phone_country_cd,phone_num,phone_type_cd,phone_status_cd,add_date,add_by,
mod_date,mod_by,primary_ind)
values(:1,:2,:3,:4,:5,:6,sysdate,:7,sysdate,:8,:9)'
USING v_phone_id,v_bus_entity_id_a,v_country_cd,v_phone_number,v_phone_type_cd,v_phone_status_cd,v_add_by,v_mod_by,v_primary_ind;

--Create new entry in clw_property with Account Information
select clw_property_seq.nextval into v_property_id from dual;
EXECUTE IMMEDIATE 'insert into clw_property(property_id,bus_entity_id,short_desc,clw_value,property_status_cd,add_date,add_by,mod_date,mod_by,property_type_cd)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,:8)'
USING v_property_id,v_bus_entity_id_a,v_property_short_desc_a,v_customer_number,v_property_status_cd,v_add_by,v_mod_by,v_property_type_cd_a;

select clw_property_seq.nextval into v_property_id from dual;

EXECUTE IMMEDIATE 'insert into clw_property(property_id,bus_entity_id,short_desc,clw_value,property_status_cd,add_date,add_by,mod_date,mod_by,property_type_cd)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,:8)'
USING v_property_id,v_bus_entity_id_a,v_property_short_desc_bu,c_value_b,v_property_status_cd,v_add_by,v_mod_by,v_property_type_cd_bu;



OPEN c_template;
LOOP
FETCH c_template into v_prop_short_desc,v_prop_clw_value,v_prop_status_cd,v_prop_type_Cd;
EXIT WHEN c_template%NOTFOUND;

select clw_property_seq.nextval into v_property_id from dual;

EXECUTE IMMEDIATE 'insert into clw_property(property_id,bus_entity_id,short_desc,clw_value,property_status_cd,property_type_cd,
add_date,add_by,mod_date,mod_by) VALUES(:1,:2,:3,:4,:5,:6,sysdate,:7,sysdate,:8)'
USING v_property_id,v_bus_entity_id_a,v_prop_short_desc,v_prop_clw_value,v_prop_status_cd,v_prop_type_Cd,v_add_by,v_mod_by;

END LOOP;
CLOSE c_template;

END IF;

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- END ACCOUNT INFORMATION 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
---START SITE INFORMATION
----------------------------------------------------------------------------------------------------
select count(*) into v_c_site_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store);

IF v_c_site_up > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Line found same site for more then 1 account');
DBMS_OUTPUT.PUT_LINE('select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity1_id in(select bus_entity_id from clw_property');
DBMS_OUTPUT.PUT_LINE('where short_desc ='|| v_property_short_desc_s || 'and clw_value='|| v_customer_number);DBMS_OUTPUT.PUT_LINE('and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity2_id ='|| v_bus_entity_id_store);

ELSIF v_c_site_up = 1 THEN
--DBMS_OUTPUT.PUT_LINE('Old Site');

select bus_entity1_id into v_c_site_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store);

EXECUTE IMMEDIATE 'UPDATE CLW_BUS_ENTITY SET short_desc = :1, long_desc = :2,mod_by = :3,mod_date = sysdate
where bus_entity_id = :4'
USING v_customer_name,v_customer_name,v_mod_by,v_c_site_up;

EXECUTE IMMEDIATE 'UPDATE CLW_ADDRESS SET name1 = :1,address1 = :2,address2 = :3,address3 = :4,address4 = :5,
city = :6,state_province_cd = :7,country_cd = :8,postal_code = :9,mod_by = :10,mod_date = sysdate
where bus_entity_id = :11'
USING v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,v_postal_code,
v_mod_by,v_c_site_up;

EXECUTE IMMEDIATE 'UPDATE clw_phone set phone_num = :1 ,mod_by = :2,mod_date = sysdate
where bus_entity_id = :3'
USING v_phone_number,v_mod_by,v_c_site_up;

ELSE
--DBMS_OUTPUT.PUT_LINE('Error Line 2 v_customer_number ' || v_customer_number);
SELECT clw_bus_entity_seq.nextval into v_bus_entity_id_s from dual;

v_erp_num := '#'|| v_bus_entity_id_s;

--Create new entry in clw_bus_entity with Account Information
EXECUTE IMMEDIATE 'insert into clw_bus_entity(bus_entity_id,short_desc,long_desc,eff_date,bus_entity_type_cd,bus_entity_status_cd,workflow_role_cd,locale_cd,add_date,add_by,mod_date,
mod_by,erp_num) 
values(:1,:2,:3,sysdate,:4,:5,:6,:7,sysdate,:8,sysdate,:9,:10)'
USING v_bus_entity_id_s,v_customer_name,v_customer_name,v_bus_entity_type_cd_s,v_bus_entity_status_cd,v_workflow_role_cd,v_locale_cd,v_add_by,v_mod_by,
v_erp_num;


----Create new entry in clw_bus_entity_Assoc with Account Information
select clw_bus_entity_assoc_seq.nextval into v_bus_entity_assoc_id from dual;

EXECUTE IMMEDIATE 'insert into clw_bus_entity_assoc(bus_entity_assoc_id,bus_entity1_id,bus_entity2_id,bus_entity_assoc_cd,add_Date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_bus_entity_assoc_id,v_bus_entity_id_s,v_bus_entity_id_a,v_bus_entity_assoc_cd_s,v_add_by,v_mod_by;


--Create new entry in clw_address with Account Information

select clw_Address_seq.nextval into v_address_id from dual;
EXECUTE IMMEDIATE 'insert into clw_address(address_id,bus_entity_id,name1,address1,address2,address3,address4,city,state_province_cd,country_cd,postal_code,
address_status_cd,address_type_cd,add_date,add_by,mod_Date,mod_by) 
values(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,sysdate,:15,sysdate,:17)'
USING v_address_id,v_bus_entity_id_s,v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,
v_postal_code,v_address_status_cd,v_address_type_cd_s,v_add_by,v_mod_by;


--Create new entry in clw_phone with Account Information
select clw_phone_seq.nextval into v_phone_id from dual;
EXECUTE IMMEDIATE 'insert into clw_phone(phone_id,bus_entity_id,phone_country_cd,phone_num,phone_type_cd,phone_status_cd,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,:5,:6,sysdate,:7,sysdate,:8)'
USING v_phone_id,v_bus_entity_id_s,v_country_cd,v_phone_number,v_phone_type_cd,v_phone_status_cd,v_add_by,v_mod_by;

--Create new entry in clw_property with Account Information
select clw_property_seq.nextval into v_property_id from dual;
EXECUTE IMMEDIATE 'insert into clw_property(property_id,bus_entity_id,short_desc,clw_value,property_status_cd,add_date,add_by,mod_date,mod_by,property_type_cd)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,:8)'
USING v_property_id,v_bus_entity_id_s,v_property_short_desc_s,v_customer_number,v_property_status_cd,v_add_by,v_mod_by,
v_property_type_cd_s;


--craete catalog and aasociate it with the new site
select clw_catalog_seq.nextval into v_catalog_id from dual;

v_catalog_desc := v_customer_number || '-' || v_catalog_id;
 
EXECUTE IMMEDIATE 'INSERT INTO clw_catalog(catalog_id,short_Desc,catalog_status_cd,catalog_type_Cd,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_id,v_catalog_desc,v_catalog_status_cd,v_catalog_type_cd,v_add_by,v_mod_by;


select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id_s,v_catalog_assoc_cd_si,v_add_by,v_mod_by;

select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id_store,v_catalog_assoc_cd_st,v_add_by,v_mod_by;

select bus_entity2_id into v_bus_entity_id_ac from clw_bus_entity_assoc where bus_entity1_id = v_bus_entity_id_s;

select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id_ac,v_catalog_assoc_cd_ac,v_add_by,v_mod_by;



END IF;
END LOOP;
--DBMS_OUTPUT.PUT_LINE('END Load Accounts And Sites');
--Close Cursor for customer_type = ACCOUNT_AND_SITE
CLOSE c_acc_site;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------END SITE INFORMATION
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------Start  ACCOUNT INFORMATION  c_acc
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
open c_acc;
DBMS_OUTPUT.PUT_LINE('Load Accounts');
LOOP
FETCH c_acc into v_customer_name,v_customer_number,v_parent_customer_number,v_first_name,v_address1,v_address2,v_address3,v_address4,
v_city,v_state,v_country_cd,v_postal_code,v_phone_number,v_fax_number;
EXIT WHEN c_acc%NOTFOUND;

--select count(*) into v_c_acc_cnt from clw_property where short_desc = 'DIST_ACCT_REF_NUM' and clw_value = v_customer_number;

select count(*) into v_c_acc_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_a and clw_value = v_customer_number) and bus_entity2_id = v_bus_entity_id_store;

IF v_c_acc_up >= 1 THEN
--DBMS_OUTPUT.PUT_LINE('Old Account');

select bus_entity1_id into v_c_acc_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_a and clw_value = v_customer_number) and bus_entity2_id = v_bus_entity_id_store;

EXECUTE IMMEDIATE 'UPDATE CLW_BUS_ENTITY SET short_desc = :1, long_desc = :2,mod_by = :3,mod_date = sysdate
where bus_entity_id = :4'
USING v_customer_name,v_customer_name,v_mod_by,v_c_acc_up;

EXECUTE IMMEDIATE 'UPDATE CLW_ADDRESS SET name1 = :1,address1 = :2,address2 = :3,address3 = :4,address4 = :5,
city = :6,state_province_cd = :7,country_cd = :8,postal_code = :9,mod_by = :10,mod_date = sysdate
where bus_entity_id = :11'
USING v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,v_postal_code,
v_mod_by,v_c_acc_up;

EXECUTE IMMEDIATE 'UPDATE clw_phone set phone_num = :1 ,mod_by = :2,mod_date = sysdate
where bus_entity_id = :3'
USING v_phone_number,v_mod_by,v_c_acc_up;

ELSE

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Start ACCOUNT INFORMATION 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--Create new entry in clw_bus_entity with Account Information
SELECT clw_bus_entity_seq.nextval into v_bus_entity_id_a from dual;

v_erp_num := '#'|| v_bus_entity_id_a;

EXECUTE IMMEDIATE 'insert into clw_bus_entity(bus_entity_id,short_desc,long_desc,eff_date,bus_entity_type_cd,bus_entity_status_cd,workflow_role_cd,locale_cd,add_date,add_by,mod_date,
mod_by,erp_num) 
values(:1,:2,:3,sysdate,:4,:5,:6,:7,sysdate,:8,sysdate,:9,:10)'
USING v_bus_entity_id_a,v_customer_name,v_customer_name,v_bus_entity_type_cd_a,v_bus_entity_status_cd,v_workflow_role_cd,v_locale_cd,v_add_by,v_mod_by,
v_erp_num;


----Create new entry in clw_bus_entity_Assoc with Account Information
select clw_bus_entity_assoc_seq.nextval into v_bus_entity_assoc_id from dual;

EXECUTE IMMEDIATE 'insert into clw_bus_entity_assoc(bus_entity_assoc_id,bus_entity1_id,bus_entity2_id,bus_entity_assoc_cd,add_Date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_bus_entity_assoc_id,v_bus_entity_id_a,v_bus_entity_id_store,v_bus_entity_assoc_cd_a,v_add_by,v_mod_by;


--Create new entry in clw_address with Account Information
--one for address_type_cd = PRIMARY CONTACT
select clw_Address_seq.nextval into v_address_id from dual;
EXECUTE IMMEDIATE 'insert into clw_address(address_id,bus_entity_id,name1,address1,address2,address3,address4,city,state_province_cd,country_cd,postal_code,
address_status_cd,address_type_cd,add_date,add_by,mod_Date,mod_by,primary_ind) 
values(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,sysdate,:15,sysdate,:17,:18)'
USING v_address_id,v_bus_entity_id_a,v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,
v_postal_code,v_address_status_cd,v_address_type_cd_p,v_add_by,v_mod_by,v_primary_ind;


--one for address_type_cd = BILLING
select clw_Address_seq.nextval into v_address_id from dual;
EXECUTE IMMEDIATE 'insert into clw_address(address_id,bus_entity_id,name1,address1,address2,address3,address4,city,state_province_cd,country_cd,postal_code,
address_status_cd,address_type_cd,add_date,add_by,mod_Date,mod_by)
values(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,sysdate,:15,sysdate,:17)'
USING v_address_id,v_bus_entity_id_a,v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,
v_postal_code,v_address_status_cd,v_address_type_cd_b,v_add_by,v_mod_by;

--Create new entry in clw_phone with Account Information
select clw_phone_seq.nextval into v_phone_id from dual;
EXECUTE IMMEDIATE 'insert into clw_phone(phone_id,bus_entity_id,phone_country_cd,phone_num,phone_type_cd,phone_status_cd,add_date,add_by,
mod_date,mod_by,primary_ind)
values(:1,:2,:3,:4,:5,:6,sysdate,:7,sysdate,:8,:9)'
USING v_phone_id,v_bus_entity_id_a,v_country_cd,v_phone_number,v_phone_type_cd,v_phone_status_cd,v_add_by,v_mod_by,v_primary_ind;

--Create new entry in clw_property with Account Information
select clw_property_seq.nextval into v_property_id from dual;
EXECUTE IMMEDIATE 'insert into clw_property(property_id,bus_entity_id,short_desc,clw_value,property_status_cd,add_date,add_by,mod_date,mod_by,property_type_cd)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,:8)'
USING v_property_id,v_bus_entity_id_a,v_property_short_desc_a,v_customer_number,v_property_status_cd,v_add_by,v_mod_by,
v_property_type_cd_a;

select clw_property_seq.nextval into v_property_id from dual;

EXECUTE IMMEDIATE 'insert into clw_property(property_id,bus_entity_id,short_desc,clw_value,property_status_cd,add_date,add_by,mod_date,mod_by,property_type_cd)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,:8)'
USING v_property_id,v_bus_entity_id_a,v_property_short_desc_bu,c_value_b,v_property_status_cd,v_add_by,v_mod_by,v_property_type_cd_bu;

OPEN c_template;
LOOP
FETCH c_template into v_prop_short_desc,v_prop_clw_value,v_prop_status_cd,v_prop_type_Cd;
EXIT WHEN c_template%NOTFOUND;

select clw_property_seq.nextval into v_property_id from dual;

EXECUTE IMMEDIATE 'insert into clw_property(property_id,bus_entity_id,short_desc,clw_value,property_status_cd,property_type_cd,
add_date,add_by,mod_date,mod_by) VALUES(:1,:2,:3,:4,:5,:6,sysdate,:7,sysdate,:8)'
USING v_property_id,v_bus_entity_id_a,v_prop_short_desc,v_prop_clw_value,v_prop_status_cd,v_prop_type_Cd,v_add_by,v_mod_by;

END LOOP;

CLOSE c_template;

END IF;

END LOOP;

DBMS_OUTPUT.PUT_LINE('End Load Accounts');

close c_acc;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------END  ACCOUNT INFORMATION  c_acc
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------Start  SITE INFORMATION  c_site
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------


open c_site;

--DBMS_OUTPUT.PUT_LINE('Load Sites');
LOOP
FETCH c_site into v_customer_name,v_customer_number,v_parent_customer_number,v_first_name,v_address1,v_address2,v_address3,v_address4,
v_city,v_state,v_country_cd,v_postal_code,v_phone_number,v_fax_number;
EXIT WHEN c_site%NOTFOUND;


--select count(*) into v_c_site_cnt from clw_property where short_desc = 'SITE_REFERENCE_NUMBER' and clw_value = v_customer_number;
select count(*) into v_c_site_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store);

IF v_c_site_up > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Line found same site for more then 1 account');
DBMS_OUTPUT.PUT_LINE('select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity1_id in(select bus_entity_id from clw_property');
DBMS_OUTPUT.PUT_LINE('where short_desc ='|| v_property_short_desc_s || 'and clw_value='|| v_customer_number);DBMS_OUTPUT.PUT_LINE('and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity2_id ='|| v_bus_entity_id_store);

ELSIF v_c_site_up = 1 THEN
--IF v_c_site_up >= 1 THEN
--DBMS_OUTPUT.PUT_LINE('Old Site');

select bus_entity1_id into v_c_site_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store);

EXECUTE IMMEDIATE 'UPDATE CLW_BUS_ENTITY SET short_desc = :1, long_desc = :2,mod_by = :3,mod_date = sysdate
where bus_entity_id = :4'
USING v_customer_name,v_customer_name,v_mod_by,v_c_site_up;

EXECUTE IMMEDIATE 'UPDATE CLW_ADDRESS SET name1 = :1,address1 = :2,address2 = :3,address3 = :4,address4 = :5,
city = :6,state_province_cd = :7,country_cd = :8,postal_code = :9,mod_by = :10,mod_date = sysdate
where bus_entity_id = :11'
USING v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,v_postal_code,
v_mod_by,v_c_site_up;

EXECUTE IMMEDIATE 'UPDATE clw_phone set phone_num = :1 ,mod_by = :2,mod_date = sysdate
where bus_entity_id = :3'
USING v_phone_number,v_mod_by,v_c_site_up;


ELSE


select count(bus_entity1_id) into v_bus_entity_id_a from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_Desc = v_property_short_desc_a 
and clw_value = v_parent_customer_number)
and bus_entity2_id = v_bus_entity_id_store;

IF v_bus_entity_id_a = 1 THEN

SELECT clw_bus_entity_seq.nextval into v_bus_entity_id_s from dual;

v_erp_num := '#'|| v_bus_entity_id_s;

--Create new entry in clw_bus_entity with Site Information
EXECUTE IMMEDIATE 'insert into clw_bus_entity(bus_entity_id,short_desc,long_desc,eff_date,bus_entity_type_cd,bus_entity_status_cd,workflow_role_cd,locale_cd,add_date,add_by,mod_date,mod_by,erp_num) 
values(:1,:2,:3,sysdate,:4,:5,:6,:7,sysdate,:8,sysdate,:9,:10)'
USING v_bus_entity_id_s,v_customer_name,v_customer_name,v_bus_entity_type_cd_s,v_bus_entity_status_cd,v_workflow_role_cd,v_locale_cd,v_add_by,v_mod_by,v_erp_num;


----Create new entry in clw_bus_entity_Assoc with Site Information
select clw_bus_entity_assoc_seq.nextval into v_bus_entity_assoc_id from dual;

--select bus_entity_id into v_bus_entity_id_a from clw_property where short_Desc = 'DIST_ACCT_REF_NUM' 
--and clw_value = v_parent_customer_number;  
--DBMS_OUTPUT.PUT_LINE('v_parent_customer_number ' || v_parent_customer_number);

select bus_entity1_id into v_bus_entity_id_a from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_Desc = v_property_short_desc_a 
and clw_value = v_parent_customer_number)
and bus_entity2_id = v_bus_entity_id_store;

EXECUTE IMMEDIATE 'insert into clw_bus_entity_assoc(bus_entity_assoc_id,bus_entity1_id,bus_entity2_id,bus_entity_assoc_cd,add_Date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_bus_entity_assoc_id,v_bus_entity_id_s,v_bus_entity_id_a,v_bus_entity_assoc_cd_s,v_add_by,v_mod_by;


--Create new entry in clw_address with Site Information

select clw_Address_seq.nextval into v_address_id from dual;
EXECUTE IMMEDIATE 'insert into clw_address(address_id,bus_entity_id,name1,address1,address2,address3,address4,city,state_province_cd,country_cd,postal_code,
address_status_cd,address_type_cd,add_date,add_by,mod_Date,mod_by) 
values(:1,:2,:3,:4,:5,:6,:7,:8,:9,:10,:11,:12,:13,sysdate,:15,sysdate,:17)'
USING v_address_id,v_bus_entity_id_s,v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country,
v_postal_code,v_address_status_cd,v_address_type_cd_s,v_add_by,v_mod_by;


--Create new entry in clw_phone with Site Information
select clw_phone_seq.nextval into v_phone_id from dual;
EXECUTE IMMEDIATE 'insert into clw_phone(phone_id,bus_entity_id,phone_country_cd,phone_num,phone_type_cd,phone_status_cd,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,:5,:6,sysdate,:7,sysdate,:8)'
USING v_phone_id,v_bus_entity_id_s,v_country_cd,v_phone_number,v_phone_type_cd,v_phone_status_cd,v_add_by,v_mod_by;

--Create new entry in clw_property with Site Information
select clw_property_seq.nextval into v_property_id from dual;
EXECUTE IMMEDIATE 'insert into clw_property(property_id,bus_entity_id,short_desc,clw_value,property_status_cd,add_date,add_by,mod_date,mod_by,property_type_cd)
values(:1,:2,:3,:4,:5,sysdate,:6,sysdate,:7,:8)'
USING v_property_id,v_bus_entity_id_s,v_property_short_desc_s,v_customer_number,v_property_status_cd,v_add_by,v_mod_by,
v_property_type_cd_s;


--craete catalog and aasociate it with the new site
select clw_catalog_seq.nextval into v_catalog_id from dual;

v_catalog_desc := v_customer_number || '-' || v_catalog_id;
 
EXECUTE IMMEDIATE 'INSERT INTO clw_catalog(catalog_id,short_Desc,catalog_status_cd,catalog_type_Cd,add_date,add_by,mod_date,mod_by)
values(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_id,v_catalog_desc,v_catalog_status_cd,v_catalog_type_cd,v_add_by,v_mod_by;


select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id_s,v_catalog_assoc_cd_si,v_add_by,v_mod_by;

select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id_store,v_catalog_assoc_cd_st,v_add_by,v_mod_by;

select bus_entity2_id into v_bus_entity_id_ac from clw_bus_entity_assoc where bus_entity1_id = v_bus_entity_id_s;

select clw_catalog_assoc_seq.nextval into v_catalog_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_catalog_assoc(catalog_assoc_id,catalog_id,bus_entity_id,catalog_assoc_Cd,add_Date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING v_catalog_assoc_id,v_catalog_id,v_bus_entity_id_ac,v_catalog_assoc_cd_ac,v_add_by,v_mod_by;

ELSE

DBMS_OUTPUT.PUT_LINE('ERROR v_parent_customer_number NOT FOUND ' || v_parent_customer_number);
DBMS_OUTPUT.PUT_LINE('ERROR v_customer_number = ' || v_customer_number);

END IF;

END IF;
END LOOP;
DBMS_OUTPUT.PUT_LINE('End Load Sites');
--Close Cursor for customer_type = ACCOUNT_AND_SITE
CLOSE c_site;

commit;

END CUSTOMER_LOADER;
--********************************************************************************************************************
---END Customer Loader
--********************************************************************************************************************

--commit;
END JD_CHINA_LOADER;
/























