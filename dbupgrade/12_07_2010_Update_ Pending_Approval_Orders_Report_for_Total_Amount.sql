UPDATE CLW_GENERIC_REPORT  
SET SQL_TEXT='select 
o.order_num as "Order Num", 
NVL(o.revised_order_date, o.original_order_date) as "Order Date", 
a.short_desc as "Account Name",
 o.order_site_name as "Site Name",
oa.city as "City",
oa.state_province_cd as "State",
o.order_status_cd as "Status",
(o.total_price + o.total_freight_cost + o.total_misc_cost + o.total_tax_cost + o.total_cleanwise_cost)  as "Total Amount",
o.comments as "Comments", 
(select first_name || '' '' || last_name from clw_user u where u.user_name = o.add_by) || '' ('' || o.add_by || '')'' as "Placed By"

from
clw_order o,
 (select * from clw_order_address where address_type_cd = ''SHIPPING'') oa, clw_bus_entity a where 
o.order_status_cd = ''Pending Approval'' and 
o.store_id = #STORE# and 
o.account_id = a.bus_entity_id and 
o.account_id IN  (#ACCOUNT_MULTI_OPT# ) and 
o.order_id = oa.order_id (+)' 

WHERE NAME='Pending Approval Orders Report ' AND REPORT_SCHEMA_CD='MAIN';