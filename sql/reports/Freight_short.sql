SELECT  id.mod_date,dist.short_desc as distributor,
  (select short_desc from clw_bus_entity acct, clw_order o where id.order_id = o.order_id and o.account_id = acct.bus_entity_id)
   as account_name,
  po.po_date, po.erp_po_num AS po_num, po.outbound_po_num as outbound_po_num,    po.line_item_total 
     AS po_total$, id.invoice_num, id.invoice_date, id.freight AS freight_paid$, 
     TO_NUMBER(op.clw_value) AS requested_freight$,
  DECODE((po.line_item_total-NVL(mp.clw_value,0))/(ABS(po.line_item_total-NVL(mp.clw_value,0))),1,'Order Over Minimum Order Size',-1,'') AS over_min_amount$,
  (SELECT DECODE(COUNT(*),0,'',1,'','Backorder ('||(COUNT(*)-1)||')') FROM    CLW_INVOICE_DIST id2 
   WHERE id2.invoice_date <= id.invoice_date AND id2.erp_po_num =    id.erp_po_num) AS backorder,
   NVL((SELECT max( fh.short_desc) FROM CLW_ORDER_ITEM oi, CLW_BUS_ENTITY       fh WHERE oi.order_id = po.order_id AND oi.freight_handler_id =       fh.bus_entity_id),'') AS order_routed,
  (select bus_entity_terr_freight_cd from clw_bus_entity_terr where     id.ship_to_postal_code like postal_code ||'%' and bus_entity_id =     dist.bus_entity_id) as freight_zone
FROM CLW_INVOICE_DIST id, CLW_ORDER_PROPERTY op, 
  CLW_PURCHASE_ORDER po, CLW_BUS_ENTITY dist, CLW_PROPERTY mp
WHERE dist.bus_entity_type_cd = 'DISTRIBUTOR' 
  AND dist.erp_num = po.dist_erp_num 
  and dist.bus_entity_id = id.bus_entity_id 
  AND (NVL(id.store_id,1)=#STORE# OR (id.store_id=0 AND 1=#STORE#))
  AND po.outbound_po_num = id.erp_po_num 
  and op.order_id = po.order_id
  AND op.invoice_dist_id = id.invoice_dist_id
  AND op.order_property_type_cd = 'VENDOR_REQUESTED_FREIGHT'
  AND dist.bus_entity_id = NVL('#DISTRIBUTOR_OPT#',dist.bus_entity_id)
  AND id.add_date BETWEEN TO_DATE('#BEG_DATE#','mm/dd/yyyy') AND TO_DATE('#END_DATE#','mm/dd/yyyy') +1
  AND (freight= 0 OR freight IS NULL)   
  and (op.clw_value is not null and op.clw_value != '0' )
  AND mp.property_type_cd = 'MINIMUM_ORDER_AMOUNT' 
  AND mp.bus_entity_id = dist.bus_entity_id
  AND ('#GROUP_OPT#' is NULL OR dist.bus_entity_id IN (select bus_entity_id from clw_group_assoc where group_id = '#GROUP_OPT#'))
ORDER BY invoice_date, invoice_num, id.mod_date