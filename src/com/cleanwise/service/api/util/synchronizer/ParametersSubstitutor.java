package com.cleanwise.service.api.util.synchronizer;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;
import java.util.Iterator;
import java.util.Map.Entry;
import com.cleanwise.service.api.util.Utility;

public class ParametersSubstitutor {
//	private static final String PARENT_ASSET_ID = "(?:\\s|^)#(?:\\s*\\w*\\s*)*(?:\\w*\\.)*asset_id = current-parent-asset-id#";
	private static final String PARENT_ASSET_ID = "(?:\\s|^)#(?:.)*asset_id = current-parent-asset-id(?:.)*#";
	private static final Pattern fINITIAL_A = Pattern.compile(PARENT_ASSET_ID,
			Pattern.CASE_INSENSITIVE);

	int parentStoreId;
	int childStoreId;
	int masterAssetId;
	String userName;
	String loadingTableName;
	int assetId;
	int itemId;
	long transactionTime;
	
	public ParametersSubstitutor(int parentStoreId, int childStoreId,
			int masterAssetId, String userName) {
		super();
		this.parentStoreId = parentStoreId;
		this.childStoreId = childStoreId;
		this.masterAssetId = masterAssetId;
		this.userName = userName;
	}

	public ParametersSubstitutor(String loadingTableName, String userName) {
		this.loadingTableName =loadingTableName;
		this.userName = userName;
	}
	public String resolveParameters(String sql) {
		long transactionTime = new Date().getTime();
		return resolveParameters(sql, transactionTime);
	}	
	public String resolveParameters(String sql, long transactionTime) {
		this.transactionTime = transactionTime;
		
		sql = addRandomRootToTempTables(sql, transactionTime);

		sql = sql.replaceAll("#TRAN_TIME#", transactionTime + "");
		if (childStoreId != 0) {
			sql = sql.replaceAll("#CHILD_STORE_ID#", childStoreId + "");
		}
		if (parentStoreId != 0) {
			sql = sql.replaceAll("#PARENT_STORE_ID#", parentStoreId + "");
		}
		sql = sql.replaceAll("#USER_NAME#", userName);
		sql = sql.replaceAll("#LOADING_TABLE_NAME#", loadingTableName);

		sql = getEditedText(sql);
		
		Enumeration paramEnum = parameters.keys();
		while(paramEnum.hasMoreElements())
		{
			final Object key = paramEnum.nextElement();
			Object valueObj = parameters.get(key);
			final String keyStr = (String)key;
			if( valueObj instanceof Integer )
			{
				sql = sql.replaceAll(keyStr.toLowerCase(), ((Integer)valueObj).toString());
				sql = sql.replaceAll(keyStr.toUpperCase(), ((Integer)valueObj).toString());
			}	
			if( valueObj instanceof String )
			{
				String valueStr = (String) valueObj;
				if(!valueStr.equals("NULL") )
				{
					valueStr = "'" + valueStr + "'";
				}
					
				sql = sql.replaceAll(keyStr.toLowerCase(), valueStr);
				sql = sql.replaceAll(keyStr.toUpperCase(), valueStr);
			}	
		}
		return sql;
	}

	public long getTransactionTime() {
		return transactionTime;
	}

	public static String addRandomRootToTempTables(String sql, long transactionTime) {
	    String tablePrefix = getTablePrefix("tmp_");
		return sql.replace("tmp_", tablePrefix
				+ String.format("%013d", transactionTime) + "_");
	}	

	private String getEditedText(String aText) {
		StringBuffer result = new StringBuffer();
		Matcher matcher = fINITIAL_A.matcher(aText);
		while (matcher.find()) {
			matcher.appendReplacement(result, getReplacement(matcher));
		}
		matcher.appendTail(result);
		return result.toString();
	}

	private String getReplacement(Matcher aMatcher) {
		if (masterAssetId == 0) {
			return "";
		} else {
			return aMatcher.group(0).replace("current-parent-asset-id",
					masterAssetId + "").replace("#", "");
		}
	}
	
	private static String getTablePrefix(String pref) {
		String tablePrefix = pref;        
		String tempUser = System.getProperty("clw.loader.tempUser");		
		if(Utility.isSet(tempUser) && !tempUser.trim().startsWith("@")) {
			tablePrefix = tempUser.trim()+"."+tablePrefix;
		}
		return tablePrefix;
	}

	public static String resolveParameters(String pSql, HashMap pParams, String pSchema, String pUnique) {

	    String tablePrefix = getTablePrefix("tmp_");
		if(Utility.isSet(pUnique)) {
			tablePrefix = tablePrefix+pUnique+"_";
		}
		String sql = pSql;
		sql = sql.replace("tmp_", tablePrefix);
		Set entrySet = pParams.entrySet();
		for(Iterator iter=entrySet.iterator(); iter.hasNext();) {
			java.util.Map.Entry entry = (java.util.Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
	        sql = sql.replaceAll(key,val);
		}

		return sql;
	}

	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	Hashtable parameters = new Hashtable();
	public void addParameter(String key, int value) {
		parameters.put(key, new Integer(value));
	}

	public void addParameter(String key, Object obj) {
		parameters.put(key, obj);
	}

	public Hashtable getParameters() {
		return this.parameters;
	}

}
