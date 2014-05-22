update 
CLW_CONTRACT_ITEM ci set
AMOUNT = round(ci.amount, (select cu.DECIMALS from CLW_CURRENCY cu,clw_contract c where cu.LOCALE = c.LOCALE_CD and ci.CONTRACT_ID = c.CONTRACT_ID)),
dist_cost = round(ci.dist_cost, (select cu.DECIMALS from CLW_CURRENCY cu,clw_contract c where cu.LOCALE = c.LOCALE_CD and ci.CONTRACT_ID = c.CONTRACT_ID))
where contract_item_id IN 
(select ci.CONTRACT_ITEM_ID from CLW_CONTRACT_ITEM ci, clw_contract c, CLW_CURRENCY cu where
cu.LOCALE = c.LOCALE_CD and ci.CONTRACT_ID = c.CONTRACT_ID and 
(ci.AMOUNT != round(ci.amount, cu.DECIMALS) or ci.DIST_COST != round(ci.DIST_COST, cu.DECIMALS)))