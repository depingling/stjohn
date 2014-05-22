<tab>
<name>Report Parameters</name>
<sql>
select #DISTRIBUTOR# DISTRIBUTOR, '#BEG_DATE#' start_order_date, '#END_DATE#' end_order_date
from dual
</sql>
</tab>

<tab>
<name>Freight per PO</name>
<sql>

select order_num, po_num,outbound_po_num, vendor, dist_name 
     , sum(product_total) po_product_total, sum(frt) po_freight   
     , original_order_DATE,site_name  , address1, address2, city,st, zip 
from ( 
 select /*+ INDEX(clw_order order_date_i) */  po_num, outbound_po_num,vendor, dist.short_desc dist_name, product_total, 
  (base_inv_amt - product_total) as frt, o.order_num   ,o.original_order_DATE, s.short_desc site_name, oa.address1 
  , oa.address2, oa.city, oa.state_province_cd st, oa.postal_code zip  from law_apinvoice linv, clw_purchase_order po 
  , clw_order o, clw_order_address oa,clw_bus_entity dist, clw_bus_entity s   where base_inv_amt >0 and 
    po.erp_po_ref_num = linv.po_num and po.order_id = o.order_id   and oa.order_id = o.order_id and 
    oa.address_type_cd = 'SHIPPING'  
    and o.order_id in ( select x.order_id from clw_order x where 
      x.original_order_DATE between to_date('#BEG_DATE#', 'mm/dd/yyyy') and to_date('#END_DATE#', 'mm/dd/yyyy') 
      and x.order_status_cd in ('ERP Released','Invoiced')
    )  
    and o.site_id = s.bus_entity_id   
    and dist.erp_num = linv.vendor and dist.bus_entity_type_cd = 'DISTRIBUTOR'   
    and dist.bus_entity_id = #DISTRIBUTOR#
  ) rpt 
group by po_num,outbound_po_num, vendor, dist_name ,  order_num 
,original_order_DATE,site_name  , address1, address2, city,st, zip 
order by order_num, po_num,outbound_po_num

	
</sql>
</tab>
