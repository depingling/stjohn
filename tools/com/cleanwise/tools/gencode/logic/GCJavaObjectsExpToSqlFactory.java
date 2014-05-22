/**
 * Title:        GCJavaObjectsExpToSqlFactory
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic;

import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsExpToSqlOracle;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsExpToSqlEnterpriseDB;


public class GCJavaObjectsExpToSqlFactory {

    private GCJavaObjectsExpToSqlFactory() {        
    }

    public static GCJavaObjectsExpToSqlFactory GetInstance() {
        if (_instance == null) {
            _instance = new GCJavaObjectsExpToSqlFactory();
        }
        return _instance;
    }

    public GCJavaObjectsExpToSql getJavaObjectsExpToSql(String name) {
        if (name == null) {
            return null;
        }
        if (name.equalsIgnoreCase(GCCodeNames.ExpToSql.TO_DB_ORACLE)) {
            return new GCJavaObjectsExpToSqlOracle();
        }
        else if (name.equalsIgnoreCase(GCCodeNames.ExpToSql.TO_DB_ENTERPRISEDB)) {
        	//return null;
            return new GCJavaObjectsExpToSqlEnterpriseDB();
        }
        else if (name.equalsIgnoreCase(GCCodeNames.ExpToSql.TO_DB_MYSQL)) {
            return null;
        }
        return null;
    }

    private static GCJavaObjectsExpToSqlFactory _instance = null;
}
