/**
 * Title:        GCJavaObjectsLoaderFactory
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic;

import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsLoaderFromOracle;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsLoaderFromEnterpriseDB;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsLoaderFromXml;


public class GCJavaObjectsLoaderFactory {

    private GCJavaObjectsLoaderFactory() {
        
    }

    public static GCJavaObjectsLoaderFactory GetInstance() {
        if (_instance == null) {
            _instance = new GCJavaObjectsLoaderFactory();
        }
        return _instance;
    }

    public GCJavaObjectsLoader getJavaObjectsLoader(String loaderName) {
        if (loaderName == null) {
            return null;
        }
        if (loaderName.equalsIgnoreCase(GCCodeNames.Loader.FROM_XML)) {
            return new GCJavaObjectsLoaderFromXml();
        }
        else if (loaderName.equalsIgnoreCase(GCCodeNames.Loader.FROM_DB_ORACLE)) {
            return new GCJavaObjectsLoaderFromOracle();
        }
        else if (loaderName.equalsIgnoreCase(GCCodeNames.Loader.FROM_DB_ENTERPRISEDB)) {
            return new GCJavaObjectsLoaderFromEnterpriseDB();
        }
        else if (loaderName.equalsIgnoreCase(GCCodeNames.Loader.FROM_DB_MYSQL)) {
            return null;
        }
        return null;
    }

    private static GCJavaObjectsLoaderFactory _instance = null;
}
