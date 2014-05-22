DECLARE
v_order_item_action_id number;
v_order_id number;
v_order_num varchar2(255);
v_order_item_id number;
v_item_sku_num varchar2(40);
v_order_line_num number;
v_order_item_status_cd varchar2(40);
v_total_quantity_ordered number;
v_cnt number := 0;
v_add_by varchar2(20) := 'sqlscript';
cursor c_order is
select co.order_id,co.order_num,coi.order_item_id,coi.item_sku_num,coi.order_line_num,coi.order_item_status_cd,
coi.total_quantity_ordered
from clw_order co,clw_order_item coi
where co.order_id = coi.order_id
and co.store_id = 176648 and coi.order_item_status_cd = 'CANCELLED' and co.mod_Date >= trunc(sysdate) - 60;

BEGIN
open c_order;
LOOP
FETCH c_order into v_order_id,v_order_num,v_order_item_id,v_item_sku_num,v_order_line_num,v_order_item_status_cd,
v_total_quantity_ordered;
EXIT WHEN c_order%NOTFOUND;

select count(*) into v_cnt from clw_order_item_action where order_item_id = v_order_item_id and action_cd = 'Canceled';

if v_cnt = 0 then
select clw_order_item_action_seq.nextval into v_order_item_action_id from dual;

v_order_item_status_cd := 'Canceled';
EXECUTE IMMEDIATE 'insert into clw_order_item_action(order_item_action_id,order_id,order_item_id,affected_order_num,
affected_sku,affected_line_item,action_cd,quantity,action_date,action_time,add_date,add_by) 
values(:1,:2,:3,:4,:5,:6,:7,:8,sysdate,sysdate,sysdate,:9)'
USING v_order_item_action_id,v_order_id,v_order_item_id,v_order_num,v_item_sku_num,v_order_line_num,v_order_item_status_cd,
v_total_quantity_ordered,v_add_by;

end if;

END LOOP;
COMMIT;
CLOSE c_order;

END;
/

exit;
