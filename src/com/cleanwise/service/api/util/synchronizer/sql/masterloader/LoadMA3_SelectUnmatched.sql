CREATE TABLE tmp_1005
AS
   SELECT   *
     FROM   tmp_1105 n
    WHERE   NOT EXISTS
               (SELECT   *
                  FROM   clw_asset a, clw_asset_assoc aa
                 WHERE       'MASTER_ASSET' = a.asset_type_cd
                         AND a.asset_id = aa.asset_id
                         AND 'ASSET_STORE' = aa.asset_assoc_cd
                         AND aa.bus_entity_id = n.store_id
                         AND LOWER(TRIM(n.manufacturer)) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = a.manuf_id)
                         AND n.mfg_sku = a.manuf_sku);
                         
                        