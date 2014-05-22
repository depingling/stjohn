set serveroutput on;
DECLARE
v_bus_entity_id_a number;
v_bus_entity_id_s number;
v_bus_entity_id_store number := 278772;
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

cursor c_acc is
select customer_name,customer_number,parent_customer_number,contact_name,address_line_1,address_line_2,address_line_3,address_line_4,
city,state,country,postal_code,phone_number,fax_number
from pri_trans_customer where customer_type = 'ACCOUNT' and country = 'CN'
and customer_number in(26861677,20764294,25072300,6686980);

cursor c_acc_site is
select customer_name,customer_number,parent_customer_number,contact_name,address_line_1,address_line_2,address_line_3,address_line_4,
city,state,country,postal_code,phone_number,fax_number
from pri_trans_customer where customer_type = 'ACCOUNT_AND_SITE' and country = 'CN'
and customer_number in(26861677,20764294,25072300,6686980);

cursor c_site is
select customer_name,customer_number,parent_customer_number,contact_name,address_line_1,address_line_2,address_line_3,address_line_4,
city,state,country,postal_code,phone_number,fax_number
from pri_trans_customer where customer_type = 'SITE' and country = 'CN'
and customer_number in(26861677,20764294,25072300,6686980);

cursor c_template is 
select short_Desc,clw_value,property_status_cd,property_type_Cd from clw_property where bus_entity_id = 253678;

BEGIN

--Open Cursor for customer_type = ACCOUNT_AND_SITE
open c_acc_site;
LOOP
FETCH c_acc_site into v_customer_name,v_customer_number,v_parent_customer_number,v_first_name,v_address1,v_address2,v_address3,v_address4,v_city,v_state,v_country_cd,v_postal_code,v_phone_number,v_fax_number;
EXIT WHEN c_acc_site%NOTFOUND;


---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
-- Start ACCOUNT INFORMATION 
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--select count(*) into v_c_acc_site_cnt from clw_property where short_desc = 'DIST_ACCT_REF_NUM' and clw_value = v_customer_number;

select count(*) into v_c_acc_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_a and clw_value = v_customer_number) and bus_entity2_id = v_bus_entity_id_store;

IF v_c_acc_up >= 1 THEN
DBMS_OUTPUT.PUT_LINE('Old Account');
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

IF v_c_site_up >= 1 THEN
DBMS_OUTPUT.PUT_LINE('Old Site');

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

--Close Cursor for customer_type = ACCOUNT_AND_SITE
CLOSE c_acc_site;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------END SITE INFORMATION
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------Start  ACCOUNT INFORMATION  c_acc
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
open c_acc;
LOOP
FETCH c_acc into v_customer_name,v_customer_number,v_parent_customer_number,v_first_name,v_address1,v_address2,v_address3,v_address4,
v_city,v_state,v_country_cd,v_postal_code,v_phone_number,v_fax_number;
EXIT WHEN c_acc%NOTFOUND;

--select count(*) into v_c_acc_cnt from clw_property where short_desc = 'DIST_ACCT_REF_NUM' and clw_value = v_customer_number;

select count(*) into v_c_acc_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_a and clw_value = v_customer_number) and bus_entity2_id = v_bus_entity_id_store;

IF v_c_acc_up >= 1 THEN
DBMS_OUTPUT.PUT_LINE('Old Account');

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

close c_acc;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------END  ACCOUNT INFORMATION  c_acc
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
----------Start  SITE INFORMATION  c_site
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------


open c_site;
LOOP
FETCH c_site into v_customer_name,v_customer_number,v_parent_customer_number,v_first_name,v_address1,v_address2,v_address3,v_address4,
v_city,v_state,v_country_cd,v_postal_code,v_phone_number,v_fax_number;
EXIT WHEN c_site%NOTFOUND;


--select count(*) into v_c_site_cnt from clw_property where short_desc = 'SITE_REFERENCE_NUMBER' and clw_value = v_customer_number;
select count(*) into v_c_site_up from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store);

IF v_c_site_up >= 1 THEN
DBMS_OUTPUT.PUT_LINE('Old Site');

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



END IF;
END LOOP;

--Close Cursor for customer_type = ACCOUNT_AND_SITE
CLOSE c_site;

commit;

END;
/