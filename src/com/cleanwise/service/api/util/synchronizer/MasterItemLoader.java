package com.cleanwise.service.api.util.synchronizer;

import java.util.Date;

public class MasterItemLoader extends MasterLoader {

	public MasterItemLoader(String loadingTableName, boolean stageUunmatched,
			String userName) {
		super(loadingTableName, userName, new MaserItemSQLAssemblerStrategy(
				stageUunmatched), TcsLoaderSpecification.ObjectType.MasterItem);
	}

	public MasterItemLoader(int staged_itemId, String userName) {
		super(TEMP_STAGED_PREFIX + "i" + staged_itemId + "_"
				+ String.format("%013d", new Date().getTime()), userName,
				new MchStgMasterItemSQLAssemblerStrategy(false),
				TcsLoaderSpecification.ObjectType.MasterItem);
		this.getSubstitutor().addParameter("#STAGED_ITEM_ID#", staged_itemId);
		this.getSubstitutor().addParameter("#ITEM_ID#", "NULL");
	}

	public MasterItemLoader(int staged_itemId, int itemId, String userName) {
		super(TEMP_STAGED_PREFIX + "i" + staged_itemId + "_"
				+ String.format("%013d", new Date().getTime()), userName,
				new MchStgMasterItemSQLAssemblerStrategy(false),
				TcsLoaderSpecification.ObjectType.MasterItem);
		this.getSubstitutor().addParameter("#STAGED_ITEM_ID#", staged_itemId);
		this.getSubstitutor().addParameter("#ITEM_ID#", itemId);

	}

	protected String getSqlResourceForExistingValidation() {
		return "/com/cleanwise/service/api/util/synchronizer/sql/masterloader/CheckExistingMasterItems.sql";
	}

	protected String[] getUniqueFields() {
		return tcsLoaderSpecification.getUniqueFields();
	}

	@Override
	protected String[] getRequiredFields() {
		return this.tcsLoaderSpecification.getRequiredFields();
	}

}
