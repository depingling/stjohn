SELECT 
acct.short_desc as account_name,
site.short_desc as site_name, 
saddr.city, saddr.state_province_cd, saddr.postal_code, 
oi.erp_po_num, oi.outbound_po_num, oi.erp_po_date,
oi.item_sku_num, oi.dist_item_sku_num, oi.item_short_desc, oi.item_uom, oi.item_pack, 
SUM (oi.dist_item_cost * oi.total_quantity_ordered) AS total_cost$, COUNT(*) AS number_of_orders,
SUM(oi.total_quantity_shipped) as total_quantity_shipped,
SUM(oi.total_quantity_ordered) AS total_quantity
FROM CLW_ORDER o, CLW_ORDER_ITEM oi, CLW_BUS_ENTITY dist, 
CLW_BUS_ENTITY site, clw_bus_entity acct, CLW_ADDRESS saddr
WHERE
dist.bus_entity_id = #DISTRIBUTOR# AND
dist.bus_entity_type_cd = 'DISTRIBUTOR' AND
o.account_id = acct.bus_entity_id and
o.order_id = oi.order_id AND
oi.item_id = #ITEM# AND
oi.dist_erp_num = dist.erp_num and
o.site_id = site.bus_entity_id AND 
site.bus_entity_id = saddr.bus_entity_id AND
saddr.address_type_cd = 'SHIPPING' AND
NVL(o.revised_order_date,o.original_order_date) 
 BETWEEN TO_DATE('#BEG_DATE#','mm/dd/yyyy') 
  AND TO_DATE('#END_DATE#','mm/dd/yyyy') AND
o.order_status_cd IN ('Ordered','Invoiced',
                      'Process ERP PO','ERP Released') AND
oi.order_item_status_cd IN ('PENDING_ERP_PO','PENDING_FULFILLMENT',
 'SENT_TO_DISTRIBUTOR',
 'PO_ACK_SUCCESS','PO_ACK_ERROR','PO_ACK_REJECT',
 'SENT_TO_DISTRIBUTOR_FAILED','INVOICED')
GROUP BY 
acct.short_desc, site.short_desc, oi.erp_po_num, oi.outbound_po_num, oi.erp_po_date,
saddr.city, saddr.state_province_cd, saddr.postal_code, 
oi.item_sku_num, oi.dist_item_sku_num, oi.item_short_desc, oi.item_uom, oi.item_pack