SELECT 
o.original_order_date, po.po_date, po.erp_po_num, po.outbound_po_num, i.mod_date AS item_last_mod_date, o.mod_date AS order_last_mod_date,
po.purchase_order_status_cd, o.order_status_cd, i.order_item_status_cd, i.dist_item_sku_num, i.item_sku_num, i.item_short_desc, i.dist_item_cost, i.total_quantity_ordered
FROM CLW_ORDER_ITEM i, CLW_PURCHASE_ORDER po, CLW_ORDER o, CLW_BUS_ENTITY dist WHERE
dist.bus_entity_type_cd = 'DISTRIBUTOR' AND dist.bus_entity_id = #DISTRIBUTOR# AND dist.erp_num = i.dist_erp_num AND
po.purchase_order_id = i.purchase_order_id
AND o.order_id = i.order_id
AND (i.order_item_status_cd IN ('CANCELLED') OR o.order_status_cd IN ('Cancelled','Rejected','ERP Cancelled'))