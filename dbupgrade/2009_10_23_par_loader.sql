
Update clw_trading_property_map set property_type_cd = 'amount'
where trading_property_map_id in (
SELECT  trading_property_map_id FROM  clw_trading_property_map
WHERE trading_profile_id in ( Select trading_profile_id from clw_trading_property_map
 where hard_value  like '%ParDefinitionRequest%')
   and property_type_cd in ('amount1','amount2','amount3','amount4','amount5','amount6','amount7','amount8','amount9','amount10','amount11', 'amount12', 'amount13')
);

Update clw_trading_property_map set property_type_cd = 'period'
where trading_property_map_id in (
SELECT  trading_property_map_id FROM  clw_trading_property_map
WHERE trading_profile_id in ( Select trading_profile_id from clw_trading_property_map
 where hard_value  like '%ParLoaderRequest%')
   and property_type_cd in ('period1','period2','period3','period4','period5','period6','period7','period8','period9','period10','period11', 'period12', 'period13')
);
