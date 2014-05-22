package com.cleanwise.service.api.util.synchronizer;


public class MaserAssetSQLAssemblerStrategy extends SQLAssemblerStrategy {
	
	public MaserAssetSQLAssemblerStrategy(boolean stageUunmatched) {
		super(stageUunmatched);
	}

	protected String[] getCommonSqlFiles() {
		return new String[]{
				
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA1_CatAndManuf.sql",
	
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA2.1_UpdateMatchedAssets.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA2.2_UpdateMatchedProperties.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA2.3_UpdateMatchedImage.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA3_SelectUnmatched.sql" };
	}

	protected String[] getNotStagedUnnathchedSqlFiles() {
		return new String[] { 
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA4_UnmatchedToClwAsset.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA6_UpdInsDocs.sql",
				};
	}

	protected String[] getStagedUnmathchedSqlFiles() {
		return new String[] { 
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA6_UpdInsDocs.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA5_UnmatchedToStaged.sql" };
	}

}
