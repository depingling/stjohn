
. /apps/ixtendx/.profile
. ../app.sh.env

if [ -z ${XSUITE_HOME} ]
then
  echo "XSUITE_HOME is not defined."
  exit
fi

# the sqlplus client does not support this env variable
unset NLS_LANG

sqlplus ${XSUITE_DBUSER}/${XSUITE_DBPASS}@${XSUITE_DBSERVER} <<EOF

host echo "start the address load -- "
host echo "... sites that placed orders going to lagasse"

DECLARE
  CURSOR my_cursor IS 
    SELECT distinct bea.bus_entity1_id, bea.bus_entity2_id, orderi.dist_erp_num
  from clw_bus_entity be , clw_bus_entity be2, 
  clw_bus_entity_assoc bea , clw_address ca, 
  clw_order order1, clw_order_item orderi
  where 
    be.bus_entity_id not in ( 
      select distinct i.site_id from dist_address_log i
      where i.site_id =  be.bus_entity_id )
  and  be.bus_entity_type_cd = 'SITE' 
  and  be.bus_entity_status_cd = 'ACTIVE'
  and  bea.bus_entity1_id = be.bus_entity_id 
  and  bea.bus_entity2_id = be2.bus_entity_id 
  and  bea.bus_entity_assoc_cd = 'SITE OF ACCOUNT' 
  and  be.bus_entity_id = ca.bus_entity_id 
  and  order1.order_id = orderi.order_id 
  and  orderi.dist_erp_num in ( '1127' , '1448')
  and  order1.site_id = be.bus_entity_id
  and  order1.account_id = be2.bus_entity_id
  and  order1.add_date > (sysdate - 10);
    my_rec my_cursor%ROWTYPE;
BEGIN
  OPEN my_cursor;
  LOOP
    FETCH my_cursor INTO my_rec;
    EXIT WHEN my_cursor%NOTFOUND;
      INSERT INTO dist_address_log (dist_erp_num,
        account_id, site_id ) VALUES 
       ( my_rec.dist_erp_num, my_rec.bus_entity2_id, my_rec.bus_entity1_id );
    END LOOP;
  CLOSE my_cursor;
END;
/ 

host echo "...3"
update dist_address_log set mod_date = 
  ( select mod_date from clw_bus_entity where 
      dist_address_log.site_id = clw_bus_entity.BUS_ENTITY_ID
  )
/ 

host echo "...4"
update dist_address_log set add_date = 
  ( select add_date from clw_bus_entity where 
      dist_address_log.site_id = clw_bus_entity.BUS_ENTITY_ID
  )
/ 

host echo "...5-- construct the address line for each site"

set serveroutput on size 1000000

select count(*) from dist_address_log
/

DECLARE 
    repline char(300);
    addr_1 varchar2(100);
    addr_2 varchar2(100);
    v_acct_name varchar2(100);
    v_site_name varchar2(100);
    v_account_id number;
  CURSOR my_cursor IS 
    select d.account_id, d.site_id, be2.erp_num, 
 be1.short_desc as site_name, NVL(poname.clw_value,be2.short_desc) as account_name,
 ca.address1 , ca.address2 , ca.address3 , ca.address4 ,
 ca.city , ca.state_province_cd , ca.postal_code 
  from dist_address_log d , clw_address ca, 
    clw_bus_entity be1, clw_bus_entity be2, (select * from clw_property where property_type_cd = 'PURCHASE_ORDER_ACCOUNT_NAME') poname
  where ca.bus_entity_id = d.site_id
    and be1.bus_entity_id = d.site_id
    and be2.bus_entity_id = d.account_id
    and be2.bus_entity_id = poname.bus_entity_id (+)
    and ( 
      d.rep_line is null 
      or d.sent_date is null 
      or d.sent_date < d.mod_date ) ;
    my_rec my_cursor%ROWTYPE;
BEGIN
  OPEN my_cursor;
  LOOP
    FETCH my_cursor INTO my_rec;
    EXIT WHEN my_cursor%NOTFOUND;

    addr_1 := ' ';
    addr_2 := ' ';
    v_acct_name := my_rec.account_name;
    v_site_name := my_rec.site_name;
    v_account_id := my_rec.account_id;

      IF v_account_id = 94010 THEN
        v_account_id := 99;
      END IF;

      IF v_account_id = 89417 THEN
        v_account_id := 100;
      END IF;

      IF v_account_id = 100 THEN
        v_acct_name := 'USPS';
        v_site_name := my_rec.site_name ;
      END IF;

      IF v_account_id = 99 THEN
         addr_1 := my_rec.address1;
         addr_2 := my_rec.address2;
	 IF UPPER(SUBSTR(my_rec.address3,0,4)) = 'WORK' THEN
           v_acct_name := my_rec.account_name || ' ' || replace(my_rec.address3, 'WORK ORDER', 'WO');
           v_acct_name := replace(v_acct_name, 'work order', 'WO');
           v_acct_name := replace(v_acct_name, 'WO #', 'WO#');
         END IF;
      ELSIF v_account_id = 101 THEN
         IF SUBSTR(my_rec.address1,0,9) = 'JC Penney' THEN
           addr_1 := my_rec.address2;
           addr_2 := my_rec.address3;
         ELSE
           addr_1 := my_rec.address1;
           addr_2 := my_rec.address2 || ' ' || my_rec.address3;
         END IF;
      ELSIF my_rec.address1 = 'COPE' THEN
         addr_1 := my_rec.address1 || ' ' || my_rec.address2;
         addr_2 := my_rec.address3;
      ELSIF my_rec.address3 = 'USPS' THEN
         addr_1 := my_rec.address1;
         addr_2 := my_rec.address2 || ' ' || my_rec.address4;
      ELSIF SUBSTR(my_rec.address2,0,6) = 'PO BOX' THEN
         addr_1 := my_rec.address1;
         addr_2 := my_rec.address3;
      ELSIF SUBSTR(my_rec.address3,0,6) = 'PO BOX' THEN
         addr_1 := my_rec.address1;
         addr_2 := my_rec.address2;
      ELSIF my_rec.address1 = 'POSTMASTER' THEN
         addr_1 := my_rec.address2;
         addr_2 := my_rec.address3 || ' ' || my_rec.address4;
      ELSIF my_rec.address3 IS NULL THEN
         addr_1 := my_rec.address1;
         addr_2 := my_rec.address2 || ' ' || my_rec.address4;
      ELSIF my_rec.address4 IS NULL THEN
         addr_1 := my_rec.address1;
         addr_2 := my_rec.address2 || ' ' || my_rec.address3;
      ELSE
         addr_1 := my_rec.address1 || ' ' || my_rec.address2;
         addr_2 := my_rec.address3 || ' ' || my_rec.address4;
      END IF;

      addr_2 := replace(addr_2, 'WORK ORDER', 'WO');

      IF length(addr_1) > 30 THEN
         dbms_output.put_line('-- addr_1 30 + ' || v_account_id || ' ' || my_rec.site_id );
         dbms_output.put_line(' addr_1 ' || addr_1);
         dbms_output.put_line(' addr_2 ' || addr_2);
      END IF;
      IF length(addr_2) > 30 THEN
         dbms_output.put_line('-- addr_2 30 + ' || v_account_id || ' ' || my_rec.site_id);
         dbms_output.put_line(' addr_1 ' || addr_1);
         dbms_output.put_line(' addr_2 ' || addr_2);
      END IF;

      addr_1 := substr(addr_1, 0, 30);
      addr_2 := substr(addr_2, 0, 30);

      IF length(addr_1) > 30 THEN
         dbms_output.put_line('addr_1 30 + ' || addr_1);
      END IF;
      IF length(addr_2) > 30 THEN
         dbms_output.put_line('addr_2 30 + ' || addr_2);
      END IF;
      repline := '"' || v_account_id || my_rec.site_id || '","' || my_rec.erp_num || '","' || v_site_name || '","' || v_acct_name || '","' || addr_1 || '","' || addr_2 || '","' || my_rec.city || '","' || my_rec.state_province_cd || '","' || my_rec.postal_code || '"';
      update dist_address_log set rep_line = repline
        where site_id = my_rec.site_id and
              account_id = my_rec.account_id;
  END LOOP;
  CLOSE my_cursor;
END;
/ 

host echo "...Done"
quit;

EOF




