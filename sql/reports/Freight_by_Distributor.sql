SELECT po.erp_po_num,po.outbound_po_num, po.po_date, id.invoice_date, id.sub_total as invoice_sub_total, id.invoice_num, NVL(id.freight,0) AS freight FROM clw_purchase_order po, clw_invoice_dist id
WHERE po.outbound_po_num = id.erp_po_num AND id.bus_entity_id = #DISTRIBUTOR#
AND po.po_date Between TO_DATE('#BEG_DATE#','mm/dd/yyyy')  and TO_DATE('#END_DATE#','mm/dd/yyyy')  ORDER BY po_date