SELECT 
dist.short_desc as distributor,
(select short_desc from clw_bus_entity acct, clw_order o where id.order_id = o.order_id and o.account_id = acct.bus_entity_id) as account_name,
id.erp_po_num,(select outbound_po_num from clw_order_item oi where idd.invoice_dist_id = id.invoice_dist_id and idd.order_item_id = oi.order_item_id) as outbound_po_num,
(select max(oi.erp_po_date) from clw_order_item oi 
  where idd.invoice_dist_id = id.invoice_dist_id
  and idd.order_item_id = oi.order_item_id) as po_date,
 id.invoice_num, id.invoice_date, id.ship_to_name,
(select acct.short_desc from clw_bus_entity acct, clw_purchase_order po, clw_order o where o.order_id = po.order_Id and po.outbound_po_num = id.erp_po_num and o.account_id = acct.bus_entity_id) as po_account_name,
id.sub_total as sub_total$, 
idd.dist_item_sku_num, idd.dist_item_short_desc, idd.dist_item_uom, idd.item_sku_num, idd.item_cost as Cleanwise_Cost$, idd.adjusted_cost as Paid$, idd.item_received_cost as Requested_Cost$, idd.dist_item_quantity
 FROM CLW_INVOICE_DIST id, CLW_INVOICE_DIST_DETAIL idd, CLW_BUS_ENTITY dist
WHERE id.invoice_dist_id = idd.invoice_dist_id AND 
item_received_cost IS NOT NULL AND item_received_cost > adjusted_cost
AND id.bus_entity_id = dist.bus_entity_id
AND dist.bus_entity_id = NVL('#DISTRIBUTOR_OPT#',dist.bus_entity_id) 
AND id.add_date BETWEEN TO_DATE('#BEG_DATE#','mm/dd/yyyy') AND TO_DATE('#END_DATE#','mm/dd/yyyy') +1 
AND ('#GROUP_OPT#' is NULL OR dist.bus_entity_id IN (select bus_entity_id from clw_group_assoc where group_id = '#GROUP_OPT#'))
