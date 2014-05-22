package com.cleanwise.service.api.util.synchronizer;

import java.io.IOException;

public class MchStgMasterAssetSQLAssemblerStrategy extends MaserAssetSQLAssemblerStrategy {

	public MchStgMasterAssetSQLAssemblerStrategy(boolean stageUunmatched) {
		super(stageUunmatched);
	}

	protected String[] getCommonSqlFiles() {
		return new String[]{
//				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA0.1_CretaeTableFromSelectedStaged.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA0.2_SetMatchedItemId.sql", 				
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA1_CatAndManuf.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA2.1.1_UpdateMatchedAssets.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA2.2_UpdateMatchedProperties.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA2.3_UpdateMatchedImage.sql",
	    		"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA3_SelectUnmatched.sql",
	    		"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA4_UnmatchedToClwAsset.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA6_UpdInsDocs.sql",
	    		"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA99.1_DeleteSelectedFromStaged.sql"};
	}

	protected String[] getNotStagedUnnathchedSqlFiles() {
		return new String[] {};
	}

	protected String[] getStagedUnmathchedSqlFiles() {
		return new String[] {};
	}

	@Override
	protected String[] getPrologFiles() {
		return new String[] {"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMA0.1_CretaeTableFromSelectedStaged.sql"};
	}

	
}
