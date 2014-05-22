DELETE FROM clw_staged_item_assoc 
	WHERE staged_item_id IN (SELECT staged_item_id FROM #LOADING_TABLE_NAME#);
DELETE FROM clw_staged_item 
	WHERE staged_item_id IN (SELECT staged_item_id FROM #LOADING_TABLE_NAME#);

   
