package com.cleanwise.service.api.util.synchronizer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.cleanwise.service.api.util.synchronizer.TcsLoaderSpecification.ObjectType;

public abstract class MasterLoader {
    private static final Logger log = Logger.getLogger(MasterLoader.class);

	protected static String TEMP_STAGED_PREFIX = "tmp_stg_";

	protected String loadingTableName;
	protected String userName;
	private SQLAssemblerStrategy sqlAssembler;
	protected ParametersSubstitutor substitutor;
	protected TcsLoaderSpecification tcsLoaderSpecification;
	
	protected ParametersSubstitutor getSubstitutor() {
		return substitutor;
	}

	public MasterLoader(String loadingTableName,
			String userName, SQLAssemblerStrategy sqlAssembler, ObjectType objectType) {
		super();
		this.loadingTableName = loadingTableName;
		this.userName = userName;
		this.sqlAssembler = sqlAssembler;
		substitutor = new ParametersSubstitutor(
				loadingTableName, userName);
		tcsLoaderSpecification = new TcsLoaderSpecification(objectType);

	}

	public void load(Connection connection) throws Exception {
		log.info("\n\n" +
				this.getClass().getName() +
				", " + this.loadingTableName +
				", " + this.userName +
				", " + this.sqlAssembler.getClass().getName() +
				", StageUunmatched=" + this.sqlAssembler.isStageUunmatched() +
				"\n\n");
		
		log.info("\n\nParameters:\n" +
				substitutor.getParameters().toString() +
				"\n\n");
		

		String sql = getProlog();
		SQLBatchExecutor.executeBatch(connection, sql);
		
		if(!this.loadingTableName.startsWith(TEMP_STAGED_PREFIX)) {
    		validateData(connection);
        }
		
		sql = getSqlPackage(connection);
		SQLBatchExecutor.executeBatch(connection, sql);
		
		String tableNamePrefix = "TMP_" + this.substitutor.getTransactionTime() + "_";
		sql = DropTmpTablesSqlGenerator.generate(connection, tableNamePrefix);
		SQLBatchExecutor.executeBatch(connection, sql);
		
	}

	public void validateData(Connection connection) throws Exception {
		StringBuffer errors = new StringBuffer();

		VersionNumberValidator versionNumberValidator =
			new VersionNumberValidator(loadingTableName);
		errors.append( versionNumberValidator.validate(connection) );
		
		RequiredFieldValidator requiredFieldValidator =
			new RequiredFieldValidator(loadingTableName, substitutor,
					getRequiredFields());
		errors.append( requiredFieldValidator.validate(connection) );
		
		
		Validator dublicateValidator = 
			new DublicateInputRowsValidator(loadingTableName, substitutor,
					getUniqueFields()); 
		errors.append( dublicateValidator.validate(connection) );
		
		ExistingMasterObjectsValidator existValidator = 
			new ExistingMasterObjectsValidator(loadingTableName, substitutor, 
					getSqlResourceForExistingValidation());
		errors.append( existValidator.validate(connection) );

		CertifiedCompaniesValidator certCompValidator = 
			new CertifiedCompaniesValidator(CertifiedCompaniesCreateTableScriptGenerator.generateForLoader(connection, loadingTableName), loadingTableName);
		errors.append( certCompValidator.validate(connection) );
		
		errors.trimToSize();
		if( errors.toString().length() > 0) {
			throw new Exception(errors.toString());
		}
	}


	public String getSqlPackage(Connection connection) throws IOException,
			Exception, SQLException {
		String sql = sqlAssembler.gatherSQL(
				CertifiedCompaniesCreateTableScriptGenerator.generateForLoader(connection, loadingTableName) + 
				ManufAliasesCreateTableScriptGenerator.generateForLoader(connection, loadingTableName));
		sql = substitutor.resolveParameters(sql);
		return sql;
	}


	private String getProlog() throws Exception{
		String sql =  sqlAssembler.getPrologSQL();
		sql = substitutor.resolveParameters(sql);
		return sql;
	}

	protected abstract String[] getRequiredFields();
	protected abstract String[] getUniqueFields();
	protected abstract String getSqlResourceForExistingValidation();

}
