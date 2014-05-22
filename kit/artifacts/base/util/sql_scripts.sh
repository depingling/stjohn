. ${XDIR}/app.sh.env


TJCPATH="${TJCPATH}${PSEP}@jdbcFile@${PSEP}."

FILE=/tmp/${0}.tmp.sql

cat > ${FILE} << EOF

-- activate sites at the effective date 
update clw_bus_entity 
  set bus_entity_status_cd='ACTIVE', mod_date = sysdate, 
      mod_by='ChangeStatusScript'
where bus_entity_type_cd = 'SITE'
  and bus_entity_status_cd = 'LIMITED'
  and to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy') >= 
        nvl(eff_date,to_date('01/01/3000','mm/dd/yyyy'));
  
  
-- inactivate sites that have reached the expiration date 
update clw_bus_entity 
  set bus_entity_status_cd='INACTIVE', mod_date = sysdate, 
      mod_by='ChangeStatusScript'
where bus_entity_type_cd = 'SITE'
  and bus_entity_status_cd = 'ACTIVE'
  and to_date(to_char(sysdate,'mm/dd/yyyy'),'mm/dd/yyyy') >=
        nvl(exp_date,to_date('01/01/3000','mm/dd/yyyy'));
		
delete from clw_order_guide_structure where order_guide_id in (
 select order_guide_id from clw_order_guide where ORDER_GUIDE_TYPE_CD = 'SHOPPING_CART'
  and mod_date < (sysdate - 365)
);

delete from  clw_order_guide where ORDER_GUIDE_TYPE_CD = 'SHOPPING_CART'
 and mod_date < (sysdate - 365);

delete from  clw_electronic_transaction where add_date < sysdate - 365;

delete from clw_shopping_info where order_id is null
 and add_date < (sysdate - 36);

delete from clw_shopping_info si where order_id > 0
and item_id not in (select item_id from clw_order_item oi
  where oi.order_id = si.order_id);


UPDATE clw_site_ledger SET budget_year = 2008, budget_period = 12 WHERE order_id  IN (SELECT order_Id FROM clw_order WHERE Nvl(revised_order_date, original_order_date) BETWEEN '29 DEC 2007' AND '31 DEC 2007'
AND store_id = 101250 );

commit;
EOF

. $XDIR/app.sh.env

java -classpath "${TJCPATH}" \
-Dconf="${XDIR}/app.properties" \
-DdbUrl="@dbUrl@" \
-DdbUser=@dbUser@ -DdbPassword=@dbPassword@ \
-DSQLFile="${FILE}" \
com.cleanwise.service.apps.SQLRunner
