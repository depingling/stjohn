/**
 * Title:        GCJavaObjectsExpToXmlFactory
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.logic;

import com.cleanwise.tools.gencode.GCCodeNames;
import com.cleanwise.tools.gencode.logic.spec.GCJavaObjectsExpToXmlMain;


public class GCJavaObjectsExpToXmlFactory {

    private GCJavaObjectsExpToXmlFactory() {        
    }

    public static GCJavaObjectsExpToXmlFactory GetInstance() {
        if (_instance == null) {
            _instance = new GCJavaObjectsExpToXmlFactory();
        }
        return _instance;
    }

    public GCJavaObjectsExpToXml getJavaObjectsExpToXml(String name) {
        if (name == null) {
            return null;
        }
        if (name.equalsIgnoreCase(GCCodeNames.ExpToXml.MAIN_XML)) {
            return new GCJavaObjectsExpToXmlMain();
        }
        return null;
    }

    private static GCJavaObjectsExpToXmlFactory _instance = null;
}
