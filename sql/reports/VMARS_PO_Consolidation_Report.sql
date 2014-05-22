select dist.short_desc Distributor, o.site_id, site.short_desc site, po.erp_po_num, po.outbound_po_num, po.purchase_order_total, po.add_date
from clw_purchase_order po, clw_order o, clw_bus_entity dist, clw_bus_entity site,
(
 select po.dist_erp_num, o.site_id, count(*)
 from clw_purchase_order po, clw_order o
 where po.add_date > sysdate-1/24
 and po.order_id = o.order_id
 and o.account_id in (#ACCOUNT_MULTI_OPT#)
 group by po.dist_erp_num, o.site_id
 having count(*) > 1
) t
where po.add_date > sysdate-1/24
and po.order_id = o.order_id
and dist.erp_num = po.dist_erp_num
and dist.bus_entity_type_cd = 'DISTRIBUTOR'
and site.bus_entity_id = o.site_id
and dist.bus_entity_id = NVL('#DISTRIBUTOR_OPT#', dist.bus_entity_id)
and po.dist_erp_num = t.dist_erp_num
and o.site_id = t.site_id
order by 1,2