package com.cleanwise.service.api.util.synchronizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.lang.StringBuffer;

public class ExistingMasterAssetValidator {
	ParametersSubstitutor substitutor;
	public ExistingMasterAssetValidator(ParametersSubstitutor substitutor) {
		this.substitutor = substitutor;
	}
	public void validate(Connection connection) throws Exception {
		ResourceLoaderUtil resourceLoader = new ResourceLoaderUtil();
		final String resourcePathName = "/com/cleanwise/service/api/util/synchronizer/sql/masterloader/CheckExistingMasterAssets.sql";
		String sql = resourceLoader.loadText(resourcePathName);
		sql = substitutor.resolveParameters(sql); 

        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        boolean errors = false;
        StringBuffer buffer = new StringBuffer();
        int maxCount = 100;
        int count = 0;
        while (rs.next()) {
    		if(count == maxCount)
    		{
    			buffer.append("and more..\n");
    			break;
    		}
        	if(!errors)
        	{
        		buffer.append("\nThere are existing master assets in the parent store of current record store.\n");
        		buffer.append("The following columns are " +
        				"\"Parent store id\"" +
        				", \"Manufacturer\"" +
        				", \"Manuf SKU\"" +
        				", Store id\"" +
        				"\n:\n");
        		errors = true;
        	}
    		buffer.append(
    				rs.getInt(1)    + "," +
    				rs.getString(2) + "," +
    				rs.getString(3) + "," +
    				rs.getInt(4) + 
    				"\n");
    		count++;
        }
        rs.close();
        stmt.close();
        if(errors)
        {
        	throw new Exception("ExistingMasterAssetValidator:\n" +
        			buffer.toString()); 
        }

	}

}
