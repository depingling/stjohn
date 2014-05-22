SELECT
po.erp_po_num,po.outbound_po_num, po.po_date, po.purchase_order_total, dist.short_desc AS vendor, oa.short_desc AS ship_to, 
oa.address1, oa.address2, oa.address3, oa.address4, oa.city, oa.state_province_cd, oa.postal_code,
oi.erp_po_line_num AS po_line, oi.item_sku_num AS our_sku,oi.dist_item_sku_num, oi.item_short_desc, oi.dist_item_uom,
oi.item_pack, oi.item_size, NVL(dist_uom_conv_cost, dist_item_cost) AS unit_cost, dist_item_quantity AS qty
FROM 
CLW_BUS_ENTITY dist, CLW_ORDER_ITEM oi, CLW_PURCHASE_ORDER po, CLW_ORDER o, CLW_ORDER_ADDRESS oa
WHERE 
oi.order_id = o.order_id AND po.order_id = o.order_id AND po.purchase_order_id = oi.purchase_order_id AND 
oa.order_id = o.order_id AND oa.address_type_cd = 'SHIPPING' AND dist.erp_num = po.dist_erp_num AND
po.po_date >= TRUNC(SYSDATE) - #DAYS_BACK# AND dist.bus_entity_id IN (SELECT bus_entity_id FROM CLW_GROUP_ASSOC WHERE group_id = #GROUP#)
ORDER BY dist.short_desc, po.erp_po_num, oi.erp_po_line_num