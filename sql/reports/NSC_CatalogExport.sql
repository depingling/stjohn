select distinct
'1' as "Version Number",
catstr.status_cd as "Status",
bus_store.bus_entity_id as "Store ID",
bus_store.short_desc as "Store Name",
bus_account.short_desc as "Account Name",
nvl(bus_account.erp_num,'#'||bus_account.bus_entity_id) as "Account Ref Num",
c.loader_field as "Catalog Key",
c.short_desc   as "Catalog Name",
og.short_desc as "Order Guide Name",
contract.locale_cd as "Locale",

catstr.catalog_id,
catstr.item_id,

imap_distr.item_num as "Dist SKU",
bus_distr.short_desc as "Distributor",
mpack.clw_value as "Pack",
muom.clw_value as "UOM",
mprice.clw_value as "Cost",
mprice.clw_value as "Price",
catstr.customer_sku_num as "Customer SKU",
'' as "ServiceCode",
(select subcat1.short_desc from clw_item subcat1 where subcat1.item_id = categ.item_id)
        as "Category Name",
categ.sort_order as "Category Order",
(select subcat1.short_desc from clw_item subcat1 where subcat1.item_id = categ1.item_id)                
        as "SubCat1",
categ1.sort_order as "SubCat1 Order",
(select subcat1.short_desc from clw_item subcat1 where subcat1.item_id = categ2.item_id)                
        as "SubCat2",
categ2.sort_order  as "SubCat2 Order",
(select subcat1.short_desc from clw_item subcat1 where subcat1.item_id = categ3.item_id)                
        as "SubCat3",
categ3.sort_order  as "SubCat3 Order",
decode(og.order_guide_type_cd, 'ORDER_GUIDE_TEMPLATE', 'TRUE', 'FALSE') as "Order Guide",
cc.short_desc as "CostCenter Key",
frt.short_desc as "Freight Table Key"

from clw_catalog_structure catstr

inner join clw_catalog c
on c.catalog_id = catstr.catalog_id

inner join clw_catalog_assoc cat_assoc
on cat_assoc.catalog_id = catstr.catalog_id

inner join clw_bus_entity_assoc ba
on ba.bus_entity1_id = cat_assoc.bus_entity_id
and ba.bus_entity_assoc_cd = 'ACCOUNT OF STORE'

inner join clw_bus_entity bus_store
on bus_store.bus_entity_id = ba.bus_entity2_id
and bus_store.bus_entity_id = #STORE#

inner join clw_bus_entity bus_account
on bus_account.bus_entity_id = ba.bus_entity1_id

inner join clw_order_guide og
on og.catalog_id = catstr.catalog_id
and og.order_guide_type_cd in ('ORDER_GUIDE_TEMPLATE','SITE_ORDER_GUIDE_TEMPLATE')

left join clw_contract contract
on contract.catalog_id = catstr.catalog_id

left join clw_item_mapping imap_distr
on imap_distr.item_id = catstr.item_id
and imap_distr.item_mapping_cd = 'ITEM_DISTRIBUTOR'

join clw_bus_entity bus_distr
on bus_distr.bus_entity_id = imap_distr.bus_entity_id

left join clw_item_meta mpack
on mpack.item_id = catstr.item_id
and mpack.name_value = 'PACK'
left join clw_item_meta muom
on muom.item_id = catstr.item_id
and muom.name_value = 'UOM'
left join clw_item_meta mprice
on mprice.item_id = catstr.item_id
and mprice.name_value = 'LIST_PRICE'

join clw_item_assoc ia
on ia.item1_id = catstr.item_id
and ia.catalog_id = c.catalog_id


left join clw_catalog_structure categ
on categ.item_id = ia.item2_id
and categ.catalog_structure_cd = 'CATALOG_CATEGORY'
and categ.catalog_id = catstr.catalog_id

left join clw_item_assoc ia1
on ia1.item1_id = categ.item_id
and ia1.catalog_id = catstr.catalog_id

left join clw_catalog_structure categ1
on categ1.item_id = ia1.item2_id
and categ1.catalog_structure_cd = 'CATALOG_CATEGORY'
and categ1.catalog_id = catstr.catalog_id

left join clw_item_assoc ia2
on ia2.item1_id = categ1.item_id
and ia2.catalog_id = catstr.catalog_id

left join clw_catalog_structure categ2
on categ2.item_id = ia2.item2_id
and categ2.catalog_structure_cd = 'CATALOG_CATEGORY'
and categ2.catalog_id = catstr.catalog_id

left join clw_item_assoc ia3
on ia3.item1_id = categ2.item_id
and ia3.catalog_id = catstr.catalog_id

left join clw_catalog_structure categ3
on categ3.item_id = ia3.item2_id
and categ3.catalog_structure_cd = 'CATALOG_CATEGORY'
and categ3.catalog_id = catstr.catalog_id

left join clw_cost_center cc
on cc.cost_center_id = catstr.cost_center_id

left join clw_freight_table frt
on frt.freight_table_id = contract.freight_table_id

where  catstr.catalog_structure_cd = 'CATALOG_PRODUCT'
