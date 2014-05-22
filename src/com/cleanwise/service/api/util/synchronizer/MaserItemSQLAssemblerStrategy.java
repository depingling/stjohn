package com.cleanwise.service.api.util.synchronizer;

import java.io.IOException;

public class MaserItemSQLAssemblerStrategy extends SQLAssemblerStrategy {
	int staged_itemId = -1;
	
	public MaserItemSQLAssemblerStrategy(boolean stageUunmatched) {
		super(stageUunmatched);
	}
	
	protected String[] getCommonSqlFiles() {
		return new String[]{
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI1_Categories.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI2_Manufacturers.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI3_ItemMatching.sql",
				"/com/cleanwise/service/api/util/synchronizer/sql/masterloader/LoadMI41_UpdateMatched.sql",
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

}
