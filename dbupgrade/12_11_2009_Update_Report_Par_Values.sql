

UPDATE clw_generic_report 
SET SQL_TEXT = 'select
s.short_desc as site_name
, to_char(i.sku_num) as sku_num
, i.item_desc as description, i.uom, i.pack
, il.par_value1 , il.par_value2 , il.par_value3 , il.par_value4
, il.par_value5 , il.par_value6 , il.par_value7 , il.par_value8
, il.par_value9 , il.par_value10 , il.par_value11 , il.par_value12,
(select sum(total_quantity_ordered) from clw_Order o, clw_order_item oi where o.order_id = oi.order_id and o.site_id = s.bus_entity_id and il.item_id = oi.item_id and
    o.order_status_cd IN (''ERP Released'',''Invoiced'') and to_char(NVL(o.revised_order_date, o.original_order_date),''YYYY'') = (to_char(sysdate,''YYYY'') -1)) as last_yr_qty,
(select sum(total_quantity_ordered) from clw_Order o, clw_order_item oi 
where o.order_id = oi.order_id and o.site_id = s.bus_entity_id
and il.item_id = oi.item_id and o.order_status_cd IN (''ERP Released'',''Invoiced'') 
and NVL(o.revised_order_date, o.original_order_date) >= trunc(sysdate) -365) as last_12_months
from (Select il.inventory_level_id as inventory_level_id,il.item_id as item_id ,il.bus_entity_id as bus_entity_id,
ild1.clw_value as par_value1,
ild2.clw_value as par_value2, 
ild3.clw_value as par_value3,
ild4.clw_value as par_value4,  
ild5.clw_value as par_value5,
ild6.clw_value as par_value6,
ild7.clw_value as par_value7,
ild8.clw_value as par_value8,
ild9.clw_value as par_value9,
ild10.clw_value as par_value10,
ild11.clw_value as par_value11,
ild12.clw_value as par_value12,
ild13.clw_value as par_value13
from  clw_inventory_level il 
left join clw_inventory_level_detail ild1 on  il.inventory_level_id=ild1.inventory_level_id and ild1.period = 1 
left join clw_inventory_level_detail ild2 on  il.inventory_level_id=ild2.inventory_level_id and ild2.period = 2
left join clw_inventory_level_detail ild3 on  il.inventory_level_id=ild3.inventory_level_id and ild3.period = 3
left join clw_inventory_level_detail ild4 on  il.inventory_level_id=ild4.inventory_level_id and ild4.period = 4
left join clw_inventory_level_detail ild5 on  il.inventory_level_id=ild5.inventory_level_id and ild5.period = 5
left join clw_inventory_level_detail ild6 on  il.inventory_level_id=ild6.inventory_level_id and ild6.period = 6
left join clw_inventory_level_detail ild7 on  il.inventory_level_id=ild7.inventory_level_id and ild7.period = 7
left join clw_inventory_level_detail ild8 on  il.inventory_level_id=ild8.inventory_level_id and ild8.period = 8
left join clw_inventory_level_detail ild9 on  il.inventory_level_id=ild9.inventory_level_id and ild9.period = 9
left join clw_inventory_level_detail ild10 on  il.inventory_level_id=ild10.inventory_level_id and ild10.period = 10
left join clw_inventory_level_detail ild11 on  il.inventory_level_id=ild11.inventory_level_id and ild11.period = 11
left join clw_inventory_level_detail ild12 on  il.inventory_level_id=ild12.inventory_level_id and ild12.period = 12
left join clw_inventory_level_detail ild13 on  il.inventory_level_id=ild13.inventory_level_id and ild13.period = 13) il, clv_item_info i, clw_bus_entity s
where 
s.bus_entity_status_cd != ''INACTIVE'' and
s.bus_entity_id = il.bus_entity_id 
and ( par_value1 + par_value2 + par_value3 + par_value4 + par_value5 + 
  par_value6 + par_value7 + par_value8 + par_value9 + par_value10 + 
  par_value11 + par_value12 + par_value13 ) >= 0 
and i.item_id = il.item_id
and s.bus_entity_id in
(
 select bus_entity_id from clw_user_assoc where user_id = #CUSTOMER#
 union
 select bus_entity1_id from clw_bus_entity_assoc ba, clw_user u
 where  user_id = #CUSTOMER#
 and u.USER_TYPE_CD not in
  (''CUSTOMER'',
   ''MULTI-SITE BUYER'')
 and bus_entity2_id = #ACCOUNT#
)
order by 1, 2'

WHERE NAME = 'Par Values report';