DELETE FROM clw_staged_asset_assoc 
	WHERE staged_asset_id IN (SELECT staged_asset_id FROM #LOADING_TABLE_NAME#);
DELETE FROM clw_staged_asset 
	WHERE staged_asset_id IN (SELECT staged_asset_id FROM #LOADING_TABLE_NAME#);

   
