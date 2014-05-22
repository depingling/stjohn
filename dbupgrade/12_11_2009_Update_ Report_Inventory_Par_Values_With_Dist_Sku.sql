
UPDATE clw_generic_report 
SET SQL_TEXT = 'SELECT
MAX(s.short_desc) as site_name,
MAX(i.ITEM_DESC) AS DESCRIPTION,
MAX(i.UOM), 
MAX(i.PACK),
MAX(im.ITEM_NUM) as dist_sku,
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
    ) AS PERIOD_13
 
from clw_inventory_level il 
INNER JOIN clv_item_info i
ON i.item_id = il.item_id
INNER JOIN CLW_BUS_ENTITY s
ON s.BUS_ENTITY_ID = il.BUS_ENTITY_ID
INNER JOIN CLW_INVENTORY_LEVEL_DETAIL ild
ON ild.INVENTORY_LEVEL_ID = il.INVENTORY_LEVEL_ID

INNER JOIN CLW_CATALOG_STRUCTURE cs
ON cs.ITEM_ID = i.ITEM_ID
INNER JOIN CLW_CATALOG_ASSOC ca
ON ca.BUS_ENTITY_ID = s.BUS_ENTITY_ID AND ca.CATALOG_ID = cs.CATALOG_ID
INNER JOIN CLW_ITEM_MAPPING im
ON im.BUS_ENTITY_ID = cs.BUS_ENTITY_ID AND im.ITEM_MAPPING_CD = ''ITEM_DISTRIBUTOR'' AND im.ITEM_ID = cs.ITEM_ID

where
s.bus_entity_id in
(
 select ua.bus_entity_id from clw_user_assoc ua where user_id = 42957
 union
 select ba.BUS_ENTITY1_ID from clw_bus_entity_assoc ba, clw_user u
 where  u.user_id = 42957
 and u.USER_TYPE_CD not in
  (''CUSTOMER'',
   ''MULTI-SITE BUYER'')
 and bus_entity2_id = 94010
)

GROUP BY ild.INVENTORY_LEVEL_ID
HAVING
SUM(ild.CLW_VALUE) >= 0
order by 1, 2
'

WHERE NAME = 'Inventory Par Values With Dist Sku';

