package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;
import java.util.Date;

public class MasterAssetLoader extends MasterLoader {
	public MasterAssetLoader(String loadingTableName, boolean stageUunmatched,
			String userName) {
		super(loadingTableName, userName, new MaserAssetSQLAssemblerStrategy(
				stageUunmatched), TcsLoaderSpecification.ObjectType.MasterAsset);
	}

	public MasterAssetLoader(int staged_assetId, String userName) {
		super(TEMP_STAGED_PREFIX + "a" + staged_assetId + "_"
				+ String.format("%013d", new Date().getTime()), userName,
				new MchStgMasterAssetSQLAssemblerStrategy(false),
				TcsLoaderSpecification.ObjectType.MasterAsset);
		this.getSubstitutor().addParameter("#STAGED_ASSET_ID#", staged_assetId);
		this.getSubstitutor().addParameter("#ASSET_ID#", "NULL");
	}

	public MasterAssetLoader(int staged_assetId, int assetId, String userName) {
		super(TEMP_STAGED_PREFIX + "a" + staged_assetId + "_"
				+ String.format("%013d", new Date().getTime()), userName,
				new MchStgMasterAssetSQLAssemblerStrategy(false),
				TcsLoaderSpecification.ObjectType.MasterAsset);
		this.getSubstitutor().addParameter("#STAGED_ASSET_ID#", staged_assetId);
		this.getSubstitutor().addParameter("#ASSET_ID#", assetId);
	}

	protected String getSqlResourceForExistingValidation() {
		return "/com/cleanwise/service/api/util/synchronizer/sql/masterloader/CheckExistingMasterAssets.sql";
	}

	protected String[] getUniqueFields() {
		return tcsLoaderSpecification.getUniqueFields();
	}

	@Override
	protected String[] getRequiredFields() {
		return tcsLoaderSpecification.getRequiredFields();
	}
}
