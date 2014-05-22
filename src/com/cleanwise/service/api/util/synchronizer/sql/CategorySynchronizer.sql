MERGE INTO   clw_asset childs
     USING   (SELECT   parent.short_desc, child.asset_id
                FROM   clw_asset_master_assoc assoc,
                       clw_asset child,
                       clw_asset parent
               WHERE       (assoc.child_master_asset_id = child.asset_id)
                       AND (parent.asset_id = assoc.parent_master_asset_id)
                       AND (child.asset_type_cd = 'CATEGORY')
                       AND (assoc.parent_master_asset_id = #ASSET_ID#)
                       AND (parent.asset_type_cd = 'CATEGORY')) source
        ON   (childs.asset_id = source.asset_id)
WHEN MATCHED
THEN
   UPDATE SET 
   childs.short_desc = source.short_desc,
   childs.mod_by = '#USER_NAME#',
   childs.mod_date = CURRENT_DATE;
