CREATE OR REPLACE PROCEDURE USER_LOADER IS
v_user_id NUMBER;
v_first_name VARCHAR2(80);
v_last_name VARCHAR2(80);
v_user_name VARCHAR2(80);
v_password VARCHAR2(80) := '40be4e59b9a2a2b5dffb918c0e86b3d7';
v_user_status_cd VARCHAR2(20) := 'ACTIVE';
v_user_role_cd VARCHAR2(80) := 'SP^CI^OA^OPmt^CC^PR^eST^eBT^';
v_user_type_cd VARCHAR2(20) := 'MULTI-SITE BUYER';
v_pref_locale_cd VARCHAR2(10) := 'zh_CN';
v_workflow_role_cd VARCHAR2(20) := 'UNKNOWN';
v_add_by VARCHAR2(20) := 'Manoj';
v_mod_by VARCHAR2(20) := 'Manoj';
v_property_short_desc_s varchar2(30) := 'DIST_SITE_REFERENCE_NUMBER';
v_sales_rep_code VARCHAR2(40);
v_customer_number VARCHAR2(40);
v_bus_entity_id_store number := 186096;
v_bus_entity_status_cd VARCHAR2(20) := 'ACTIVE';
v_bus_entity_type_cd VARCHAR2(20) := 'ACCOUNT';
v_user_assoc_id NUMBER;
v_user_assoc_cd_s VARCHAR2(40) := 'SITE';
v_user_assoc_cd_store VARCHAR2(40) := 'STORE';
v_user_assoc_cd_a VARCHAR2(20) := 'ACCOUNT';
v_account_id NUMBER := 0;
v_cnt NUMBER := 0;
v_site_id NUMBER;

CURSOR c_user is 
SELECT DISTINCT SALES_REP_CODE FROM PRI_TRANS_SALES_REP where customer_number in
(select customer_number from pri_trans_customer where country in('CN','HK'));

CURSOR c_user_assoc is 
select sales_rep_code,customer_number from pri_trans_sales_rep
where customer_number in
(select customer_number from pri_trans_customer where country in('CN','HK'))
and sales_rep_code = v_user_name;

BEGIN
OPEN c_user;
LOOP
FETCH c_user INTO v_user_name;
EXIT WHEN c_user%NOTFOUND;

SELECT COUNT(*) INTO v_cnt from clw_user where user_name = v_user_name;

IF v_cnt > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Duplicate User');
DBMS_OUTPUT.PUT_LINE('User_Name = ' || v_user_name);

ELSIF v_cnt = 1 THEN
DBMS_OUTPUT.PUT_LINE('USER EXISTS');

select user_id into v_user_id from clw_user where user_name = v_user_name;
DBMS_OUTPUT.PUT_LINE('USER_ID = ' || v_user_id || ' user_name = ' || v_user_name);
OPEN  c_user_assoc;

LOOP

FETCH c_user_assoc into v_sales_rep_code,v_customer_number;
EXIT WHEN c_user_assoc%NOTFOUND;


select count(*) into v_site_id from clw_bus_entity where bus_entity_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_Cd = v_bus_entity_status_cd;

IF v_site_id > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Line found same site for more then 1 account');
DBMS_OUTPUT.PUT_LINE('select * from clw_bus_entity where bus_entity_id in(');
DBMS_OUTPUT.PUT_LINE('select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity1_id in(select bus_entity_id from clw_property');
DBMS_OUTPUT.PUT_LINE('where short_desc ='|| v_property_short_desc_s || ' and clw_value= '|| v_customer_number || ')');
DBMS_OUTPUT.PUT_LINE('and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity2_id ='|| v_bus_entity_id_store || '))');
DBMS_OUTPUT.PUT_LINE('and bus_entity_status_Cd = ' || v_bus_entity_status_cd || ';');

ELSIF v_site_id = 1 THEN
--DBMS_OUTPUT.PUT_LINE('Old Site');
select bus_entity_id into v_site_id from clw_bus_entity where bus_entity_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_Cd = v_bus_entity_status_cd;

select count(*) into v_user_assoc_id
from clw_user_assoc where user_id = v_user_id and bus_entity_id = v_site_id;

if v_user_assoc_id > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Duplicate entry found in user assoc table for');
DBMS_OUTPUT.PUT_LINE('bus_entity_id = '|| v_site_id || ' user_id = ' || v_user_id);

ELSIF v_user_assoc_id = 1 THEN
DBMS_OUTPUT.PUT_LINE('User ' || v_user_name || ' is already associated to site_id ' || v_site_id);

--Account Check Start
select count(*) into v_account_id from clw_bus_entity where bus_entity_id in
(select bus_entity2_id from clw_bus_entity_assoc where bus_entity1_id = v_site_id
and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_Assoc 
where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_cd = v_bus_entity_status_cd and bus_entity_type_cd = v_bus_entity_type_cd;

IF v_account_id > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Two Accounts for 1 Site');
DBMS_OUTPUT.PUT_LINE('Site_id = ' || v_site_id);

ELSIF v_account_id = 1 THEN

select bus_entity_id into v_account_id from clw_bus_entity where bus_entity_id in
(select bus_entity2_id from clw_bus_entity_assoc where bus_entity1_id = v_site_id
and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_Assoc 
where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_cd = v_bus_entity_status_cd and bus_entity_type_cd = v_bus_entity_type_cd;


select count(*) into v_cnt from clw_user_assoc 
where user_id = v_user_id and bus_entity_id = v_account_id;

IF v_cnt >= 1 THEN
DBMS_OUTPUT.PUT_LINE('Account Already Associated');

ELSE

SELECT clw_user_assoc_seq.nextval into v_user_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_user_assoc
(user_assoc_id,user_id,bus_entity_id,user_assoc_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING 
v_user_assoc_id,v_user_id,v_account_id,v_user_assoc_cd_a,v_add_by,v_mod_by;

END IF;


ELSE
DBMS_OUTPUT.PUT_LINE('Account Does Not Exists');
DBMS_OUTPUT.PUT_LINE('Site_id = ' || v_site_id);

END IF;
--Account Check End



ELSE
SELECT clw_user_assoc_seq.nextval into v_user_assoc_id from dual;
EXECUTE IMMEDIATE 'INSERT INTO clw_user_assoc
(user_assoc_id,user_id,bus_entity_id,user_assoc_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING 
v_user_assoc_id,v_user_id,v_site_id,v_user_assoc_cd_s,v_add_by,v_mod_by;


--Account Check Start
select count(*) into v_account_id from clw_bus_entity where bus_entity_id in
(select bus_entity2_id from clw_bus_entity_assoc where bus_entity1_id = v_site_id
and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_Assoc 
where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_cd = v_bus_entity_status_cd and bus_entity_type_cd = v_bus_entity_type_cd;

IF v_account_id > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Two Accounts for 1 Site');
DBMS_OUTPUT.PUT_LINE('Site_id = ' || v_site_id);

ELSIF v_account_id = 1 THEN

select bus_entity_id into v_account_id from clw_bus_entity where bus_entity_id in
(select bus_entity2_id from clw_bus_entity_assoc where bus_entity1_id = v_site_id
and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_Assoc 
where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_cd = v_bus_entity_status_cd and bus_entity_type_cd = v_bus_entity_type_cd;


select count(*) into v_cnt from clw_user_assoc 
where user_id = v_user_id and bus_entity_id = v_account_id;

IF v_cnt >= 1 THEN
DBMS_OUTPUT.PUT_LINE('Account Already Associated');

ELSE

SELECT clw_user_assoc_seq.nextval into v_user_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_user_assoc
(user_assoc_id,user_id,bus_entity_id,user_assoc_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING 
v_user_assoc_id,v_user_id,v_account_id,v_user_assoc_cd_a,v_add_by,v_mod_by;

END IF;


ELSE
DBMS_OUTPUT.PUT_LINE('Account Does Not Exists');
DBMS_OUTPUT.PUT_LINE('Site_id = ' || v_site_id);

END IF;
--Account Check End



END IF;

ELSE

DBMS_OUTPUT.PUT_LINE('Error Site does not exists customer_number = ' || v_customer_number); 

END IF;

END LOOP;
COMMIT;
CLOSE c_user_assoc;

ELSE
DBMS_OUTPUT.PUT_LINE('New User');

v_first_name := v_sales_rep_code|| '-' || v_user_id;
v_last_name := v_sales_rep_code|| '-' || v_user_id;


SELECT clw_user_seq.nextval into v_user_id from dual;

EXECUTE IMMEDIATE 'insert into clw_user 
(user_id,first_name,last_name,user_name,password,eff_date,user_status_cd,user_role_cd,user_type_cd,
last_activity_date,pref_locale_cd,workflow_role_cd,add_Date,add_by,mod_date,mod_by) 
VALUES(:1,:2,:3,:4,:5,sysdate,:6,:7,:8,sysdate,:9,:10,sysdate,:11,sysdate,:12)'
USING v_user_id,v_first_name,v_last_name,v_user_name,v_password,v_user_status_cd,v_user_role_cd,
v_user_type_cd,v_pref_locale_cd,v_workflow_role_cd,v_add_by,v_mod_by;

SELECT clw_user_assoc_seq.nextval into v_user_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_user_assoc
(user_assoc_id,user_id,bus_entity_id,user_assoc_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING 
v_user_assoc_id,v_user_id,v_bus_entity_id_store,v_user_assoc_cd_store,v_add_by,v_mod_by;


OPEN  c_user_assoc;
LOOP
FETCH c_user_assoc into v_sales_rep_code,v_customer_number;
EXIT WHEN c_user_assoc%NOTFOUND;

select count(*) into v_site_id from clw_bus_entity where bus_entity_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_Cd = v_bus_entity_status_cd;

IF v_site_id > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Line found same site for more then 1 account');
DBMS_OUTPUT.PUT_LINE('select * from clw_bus_entity where bus_entity_id in(');
DBMS_OUTPUT.PUT_LINE('select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity1_id in(select bus_entity_id from clw_property');
DBMS_OUTPUT.PUT_LINE('where short_desc ='|| v_property_short_desc_s || ' and clw_value= '|| v_customer_number || ')');
DBMS_OUTPUT.PUT_LINE('and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_assoc');
DBMS_OUTPUT.PUT_LINE('where bus_entity2_id ='|| v_bus_entity_id_store || '))');
DBMS_OUTPUT.PUT_LINE('and bus_entity_status_Cd = ' || v_bus_entity_status_cd || ';');

ELSIF v_site_id = 1 THEN

select bus_entity_id into v_site_id from clw_bus_entity where bus_entity_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity1_id in
(select bus_entity_id from clw_property where short_desc = v_property_short_desc_s 
and clw_value = v_customer_number) and bus_entity2_id in
(select bus_entity1_id from clw_bus_entity_assoc where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_Cd = v_bus_entity_status_cd;

SELECT clw_user_assoc_seq.nextval into v_user_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_user_assoc
(user_assoc_id,user_id,bus_entity_id,user_assoc_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING 
v_user_assoc_id,v_user_id,v_site_id,v_user_assoc_cd_s,v_add_by,v_mod_by;




select count(*) into v_account_id from clw_bus_entity where bus_entity_id in
(select bus_entity2_id from clw_bus_entity_assoc where bus_entity1_id = v_site_id
and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_Assoc 
where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_cd = v_bus_entity_status_cd and bus_entity_type_cd = v_bus_entity_type_cd;

IF v_account_id > 1 THEN
DBMS_OUTPUT.PUT_LINE('Error Two Accounts for 1 Site');
DBMS_OUTPUT.PUT_LINE('Site_id = ' || v_site_id);

ELSIF v_account_id = 1 THEN

select bus_entity_id into v_account_id from clw_bus_entity where bus_entity_id in
(select bus_entity2_id from clw_bus_entity_assoc where bus_entity1_id = v_site_id
and bus_entity2_id in(select bus_entity1_id from clw_bus_entity_Assoc 
where bus_entity2_id = v_bus_entity_id_store))
and bus_entity_status_cd = v_bus_entity_status_cd and bus_entity_type_cd = v_bus_entity_type_cd;


select count(*) into v_cnt from clw_user_assoc 
where user_id = v_user_id and bus_entity_id = v_account_id;

IF v_cnt >= 1 THEN
DBMS_OUTPUT.PUT_LINE('Account Already Associated');

ELSE

SELECT clw_user_assoc_seq.nextval into v_user_assoc_id from dual;

EXECUTE IMMEDIATE 'INSERT INTO clw_user_assoc
(user_assoc_id,user_id,bus_entity_id,user_assoc_cd,add_date,add_by,mod_date,mod_by)
VALUES(:1,:2,:3,:4,sysdate,:5,sysdate,:6)'
USING 
v_user_assoc_id,v_user_id,v_account_id,v_user_assoc_cd_a,v_add_by,v_mod_by;

END IF;


ELSE
DBMS_OUTPUT.PUT_LINE('Account Does Not Exists');
DBMS_OUTPUT.PUT_LINE('Site_id = ' || v_site_id);

END IF;



ELSE

DBMS_OUTPUT.PUT_LINE('Error Site does not exists customer_number = ' || v_customer_number); 

END IF;

END LOOP;
COMMIT;
CLOSE c_user_assoc;


END IF;

END LOOP;
CLOSE c_user;
COMMIT;
END;
/