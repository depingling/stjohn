update clw_order_item 
set cust_contract_price=(cust_contract_price/TOTAL_QUANTITY_ORDERED),
dist_item_cost=(dist_item_cost/TOTAL_QUANTITY_ORDERED),
dist_uom_conv_cost=(dist_uom_conv_cost/TOTAL_QUANTITY_ORDERED)
where order_id in (select order_id from clw_order where store_id = 376189) 
and order_item_status_cd != 'CANCELLED';

update clw_order a
set total_price = (
    select (sum(cust_contract_price*TOTAL_QUANTITY_ORDERED)) 
    from clw_order_item 
    where order_id = a.order_id
    and order_item_status_cd != 'CANCELLED'
    group by order_id), 
  original_amount = (
    select (sum(cust_contract_price*TOTAL_QUANTITY_ORDERED)) 
    from clw_order_item 
    where order_id = a.order_id
    and order_item_status_cd != 'CANCELLED'
    group by order_id)
where store_id = 376189;