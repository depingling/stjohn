/**
 * Title:        GCJavaObjectsCmpToSqlFactory
 */

package com.cleanwise.tools.gencode.logic;

import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsCmpToSqlOracle;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsCmpToSqlEnterpriseDB;


public class GCJavaObjectsCmpToSqlFactory {

    private GCJavaObjectsCmpToSqlFactory() {

    }

    public static GCJavaObjectsCmpToSqlFactory GetInstance() {
        if (_instance == null) {
            _instance = new GCJavaObjectsCmpToSqlFactory();
        }
        return _instance;
    }

    public GCJavaObjectsCmpToSql getJavaObjectsCmpToSql(String name) {
        if (name == null) {
            return null;
        }
        if (name.equalsIgnoreCase(GCCodeNames.CmpToSql.TO_DB_ORACLE)) {
            return new GCJavaObjectsCmpToSqlOracle();
        }        
        else if (name.equalsIgnoreCase(GCCodeNames.CmpToSql.TO_DB_MYSQL)) {
            return null;
        }
        return null;
    }

    public GCJavaObjectsCmpToSql1 getJavaObjectsCmpToSql1(String name) {
        if (name == null) {
            return null;
        }
       
        if (name.equalsIgnoreCase(GCCodeNames.CmpToSql.TO_DB_ENTERPRISEDB)) {
            return new GCJavaObjectsCmpToSqlEnterpriseDB();
        }        
        return null;
    }
    
    private static GCJavaObjectsCmpToSqlFactory _instance = null;
}
