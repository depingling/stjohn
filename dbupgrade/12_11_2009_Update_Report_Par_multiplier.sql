declare

myClobVar varchar2(32767) :=  
'select mstr2.*,(order_qty_sum * #Par Multiplier#) as revised_sugested_par_usage,(order_qty_sum * #Par Multiplier#)-sum_of_pars as par_adjustment,ceil(order_qty_sum * #Par Multiplier#) as new_par from
(select mstr.* from
(select
MAX(site.short_desc) as site_name, 
MAX(site.bus_entity_type_cd) as status, 
MAX(site.exp_date) as date_inactive,
MAX(p.SHORT_DESC) as GSF,
MAX(i.sku_num) as SKU_NUM, 
MAX(i.short_desc) as description,
MAX(uom.clw_value) as UOM, 
MAX(pack.clw_value) as pack,

    MAX(case ild.PERIOD
        when 1
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_1,
    MAX(case ild.PERIOD
        when 2
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_2,
    MAX(case ild.PERIOD
        when 3
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_3,
    MAX(case ild.PERIOD
        when 4
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_4,
    MAX(case ild.PERIOD
        when 5
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_5,
    MAX(case ild.PERIOD
        when 6
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_6,
    MAX(case ild.PERIOD
        when 7
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_7,
    MAX(case ild.PERIOD
        when 8
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_8,
    MAX(case ild.PERIOD
        when 9
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_9,
    MAX(case ild.PERIOD
        when 10
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_10,
    MAX(case ild.PERIOD
        when 11
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_11,
    MAX(case ild.PERIOD
        when 12
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_12,
    MAX(case ild.PERIOD
        when 13
        then ild.CLW_VALUE
        end 
    ) AS PERIOD_13,

MAX(il.item_id), 
MAX(site.bus_entity_id),
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 1 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period1_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 2 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period2_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 3 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period3_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 4 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period4_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 5 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period5_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 6 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period6_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 7 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period7_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 8 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period8_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 9 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period9_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 10 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period10_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 11 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period11_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 12 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period12_order_qty,
MAX((select sum(total_quantity_ordered) from clw_Order_item oi, clw_order o, clw_fiscal_calender_flat fc where nvl(o.revised_order_date, o.original_order_date) between fc.start_date and fc.end_date and fc.fiscal_year = (select to_char(sysdate,''YYYY'') from dual) and fc.period = 13 and  oi.item_id = il.item_id and fc.bus_entity_id = o.account_id and o.order_id = oi.order_id and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') and o.site_id = site.bus_entity_id and oi.order_item_status_cd != ''CANCELLED'')) as period13_order_qty,
MAX((nvl(par_value1,0)+nvl(par_value2,0)+nvl(par_value3,0)+nvl(par_value4,0)+nvl(par_value5,0)+nvl(par_value6,0)+nvl(par_value7,0)+nvl(par_value8,0)+nvl(par_value9,0)+nvl(par_value10,0)+nvl(par_value11,0)+nvl(par_value12,0)+nvl(par_value13,0))) as sum_of_pars,
MAX(
    (select sum(total_quantity_ordered) 
    from clw_Order_item oi, clw_order o 
    where nvl(o.revised_order_date, o.original_order_date) 
    between fcend.start_date and fcend.end_date 
    and  oi.item_id = il.item_id 
    and o.order_id = oi.order_id 
    and o.order_status_cd IN (''ERP Released'',''Invoiced'',''Ordered'') 
    and o.site_id = site.bus_entity_id 
    and oi.order_item_status_cd != ''CANCELLED'') ) as Order_qty_sum

from CLW_BUS_ENTITY site
INNER JOIN CLW_INVENTORY_LEVEL il
    ON il.bus_entity_id = site.bus_entity_id OR il.bus_entity_id IS NULL
INNER JOIN CLW_INVENTORY_LEVEL_DETAIL ild
    ON ild.INVENTORY_LEVEL_ID = il.INVENTORY_LEVEL_ID
INNER JOIN clw_item i
    ON il.item_id = i.item_id
    AND (i.item_id = 3333 OR i.item_id IN (SELECT item_id FROM clw_inventory_items WHERE bus_entity_id = 94010))
INNER JOIN clw_bus_entity_assoc ba
    ON ba.bus_entity1_id = site.bus_entity_id
LEFT OUTER JOIN (select * from clw_item_meta where name_value = ''UOM'') uom
    ON uom.item_id = i.item_id 
LEFT OUTER JOIN clw_item_meta pack
    ON pack.item_id = i.item_id AND pack.name_value = ''PACK''
INNER JOIN 
(select bus_entity_id, add_months(fcend.end_date,-12) as start_date, fcend.end_date, fcend.start_date as start_date1 from clw_fiscal_calender_flat fcend) fcend
    ON fcend.bus_entity_id = ba.bus_entity2_id
INNER JOIN CLW_PROPERTY p
    ON site.BUS_ENTITY_ID = p.BUS_ENTITY_ID
    AND p.SHORT_DESC = ''Gross Square Footage''
where exists
(select * from clw_fiscal_calender_flat fcinner where fcend.bus_entity_id = fcinner.bus_entity_id and fcinner.start_date -2 between fcend.start_date1 and fcend.end_date and fcinner.bus_entity_id = ba.bus_entity2_id and fcinner.start_date < sysdate and fcinner.end_date > sysdate) 
and ba.bus_entity2_id = #ACCOUNT# 
and ba.bus_entity_assoc_cd = ''SITE OF ACCOUNT'' 
GROUP BY il.INVENTORY_LEVEL_ID
)  
mstr)mstr2';

BEGIN

UPDATE clw_generic_report 
SET SQL_TEXT = myClobVar

WHERE NAME = 'Par multiplier';

end;