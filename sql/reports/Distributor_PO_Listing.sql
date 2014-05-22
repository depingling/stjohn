SELECT 
b.short_desc AS distributor,
act.short_desc as account_name,
o.order_site_name, o.site_id, o.total_price AS total_cust_order_tot, (o.total_freight_cost + o.total_misc_cost) AS frt_to_cust,
po.erp_po_num, po.outbound_po_num,po.po_date,  po.purchase_order_total
, (select fh.short_desc from clw_bus_entity fh where bus_entity_id =
    ( select max(FREIGHT_HANDLER_ID) from clw_order_item oi where oi.erp_po_num = po.erp_po_num
    and po.order_id = o.order_id )
	  ) freight_handler
FROM clw_order o, clw_purchase_order po, clw_bus_entity b
, clw_bus_entity act
 WHERE
po.order_id = o.order_id 
and act.bus_entity_id = o.account_id
AND o.order_status_cd IN ('ERP Released','Invoiced')
AND po.dist_erp_num = b.erp_num AND b.bus_entity_type_cd = 'DISTRIBUTOR'
AND b.bus_entity_id = #DISTRIBUTOR# AND po.po_date BETWEEN TO_DATE('#BEG_DATE#','mm/dd/yyyy')  AND TO_DATE('#END_DATE#','mm/dd/yyyy') 