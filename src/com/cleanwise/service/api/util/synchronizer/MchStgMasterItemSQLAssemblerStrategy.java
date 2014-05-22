package com.cleanwise.service.api.util.synchronizer;

public class MchStgMasterItemSQLAssemblerStrategy extends MaserItemSQLAssemblerStrategy {

	public MchStgMasterItemSQLAssemblerStrategy(boolean stageUunmatched) {
		super(stageUunmatched);
	}

	protected String[] getCommonSqlFiles() {
		return new String[]{
//				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI0.1_CretaeTableFromSelectedStaged.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI0.2_SetMatchedItemId.sql", 				
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI1_Categories.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI2_Manufacturers.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI41_UpdateMatched.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI99.1_DeleteSelectedFromStaged.sql"
				};
	}

	protected String[] getNotStagedUnnathchedSqlFiles() {
		return new String[] {
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI51_CreateUnmatched.sql",
		}; 
	}

	protected String[] getStagedUnmathchedSqlFiles() {
		return new String[] {
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI9_UnmatchedToStaged.sql"
				
		}; 
	}

	@Override
	protected String[] getPrologFiles() {
		return new String[] {"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI0.1_CretaeTableFromSelectedStaged.sql"};

	}
	
}
