declare
v_order_item_id number;
v_order_status_cd varchar2(40);
v_cnt number := 0;
v_order_item_status_cd varchar2(100);

cursor c_i is
select order_item_id,order_item_status_cd from clw_order_item where order_id in
(select order_id from clw_order where account_id in(548010,548009,548008,89417,100,168880,548014)
and add_date >=trunc(sysdate) - 10 and order_status_cd = 'ERP Released');

begin
open c_i;
loop
fetch c_i into v_order_item_id,v_order_item_status_cd;
exit when c_i%notfound;

select count(*) into v_cnt from CLW_ORDER_ITEM_ACTION where order_item_id = v_order_item_id and action_cd like 'ACK%';

if v_cnt = 0 then

if v_order_item_status_cd = 'CANCELLED' then

insert into CLW_ORDER_ITEM_ACTION(ORDER_ITEM_ACTION_id,ORDER_ID, ORDER_ITEM_ID, AFFECTED_SKU, quantity,ACTION_CD, STATUS,action_date,action_time,
add_date,add_by)
select CLW_ORDER_ITEM_ACTION_seq.nextval,order_id,order_item_id,dist_item_sku_num, nvl(total_quantity_ordered,total_quantity_shipped),
'ACK Rejected','',sysdate,sysdate,sysdate,'SQLScript'
from clw_order_item where order_item_id in(v_order_item_id);

else

insert into CLW_ORDER_ITEM_ACTION(ORDER_ITEM_ACTION_id,ORDER_ID, ORDER_ITEM_ID, AFFECTED_SKU, quantity,ACTION_CD, STATUS,action_date,action_time,
add_date,add_by)
select CLW_ORDER_ITEM_ACTION_seq.nextval,order_id,order_item_id,dist_item_sku_num, nvl(total_quantity_ordered,total_quantity_shipped),
'ACK Accepted','',sysdate,sysdate,sysdate,'SQLScript'
from clw_order_item where order_item_id in(v_order_item_id);

end if;
end if;
end loop;
commit;
close c_i;
end;
/
quit;