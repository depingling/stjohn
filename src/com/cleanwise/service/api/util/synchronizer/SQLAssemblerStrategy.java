package com.cleanwise.service.api.util.synchronizer;

import java.io.IOException;
import java.sql.Connection;

public abstract class SQLAssemblerStrategy {
	protected boolean stageUunmatched;

	public SQLAssemblerStrategy(boolean stageUunmatched) {
		super();
		this.stageUunmatched = stageUunmatched;
	}
	
	public boolean isStageUunmatched() {
		return stageUunmatched;
	}

	public String gatherSQL(String additionalScripts) throws IOException {

		final String[] commonSql = getCommonSqlFiles();

		final String[] notStagedUnmatched = getNotStagedUnnathchedSqlFiles();

		final String[] stagedUnmatched = getStagedUnmathchedSqlFiles();

		ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
		String sql = "";
		sql = sql + additionalScripts;
		for (int i = 0; i < commonSql.length; i++) {
			sql = sql + loadSqlFile(commonSql[i], resourceLoader);
		}

		if (!this.stageUunmatched) {
			for (int i = 0; i < notStagedUnmatched.length; i++) {
				sql = sql + loadSqlFile(notStagedUnmatched[i], resourceLoader);
			}
		} else {
			for (int i = 0; i < stagedUnmatched.length; i++) {
				sql = sql + loadSqlFile(stagedUnmatched[i], resourceLoader);
			}
		}
		return sql;
	}

	protected abstract String[] getStagedUnmathchedSqlFiles();

	protected abstract String[] getNotStagedUnnathchedSqlFiles();

	protected abstract String[] getCommonSqlFiles();

	private String loadSqlFile(final String sql_fileName,
			ResourceLoaderUtil resourceLoader) throws IOException {
		return
		"\n\n" +
		"-- ---------------------------------------------\n" +
		"-- " + sql_fileName + " BEGIN \n" +
		"-- ---------------------------------------------\n" +
		resourceLoader.loadText(sql_fileName) +
		"\n\n" +
		"-- ---------------------------------------------\n" +
		"-- " + sql_fileName + " END \n" +
		"-- ---------------------------------------------\n";
	}

	public String getPrologSQL() throws Exception {
		String[] prologFiles = this.getPrologFiles(); 
		ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
		String sql = "";
		for (int i = 0; i < prologFiles.length; i++) {
			sql = sql + loadSqlFile(prologFiles[i], resourceLoader);
		}
		return sql;
	}
	protected String[] getPrologFiles() {
		return new String[] {};
	}

}
