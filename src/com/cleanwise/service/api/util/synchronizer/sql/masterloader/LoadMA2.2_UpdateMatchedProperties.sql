CREATE TABLE tmp_1305
AS
   (SELECT   asset_id, long_description
      FROM   clw_asset a, tmp_1205 m
     WHERE   
             LOWER(TRIM(m.manufacturer)) IN 
                            	(SELECT LOWER(alias) 
                            	   FROM tmp_mnf_alias 
                            	  WHERE tmp_mnf_alias.manuf_id = a.manuf_id)
     		 AND m.mfg_sku = a.manuf_sku);

DELETE FROM   clw_asset_property
      WHERE   asset_id IN (     SELECT   asset_id FROM tmp_1305);

INSERT   ALL
  INTO   clw_asset_property
         (
             asset_property_id,
             asset_id,
             asset_property_cd,
             clw_value,
             add_by,
             add_date,
             mod_by,
             mod_date
         )
VALUES
         (
             clw_asset_property_seq.NEXTVAL,
             asset_id,
             'LONG_DESC',
             long_description,
             '#USER_NAME#',
             CURRENT_DATE,
             '#USER_NAME#',
             CURRENT_DATE
         )
   SELECT   asset_id, long_description FROM tmp_1305;
   
