/*
 * SimpleHeaderDef.java
 *
 * Created on January 23, 2004, 11:58 PM
 */

package com.cleanwise.service.api.reporting;

/**
 *
 * @author  bstevens
 */
class SimpleHeaderDef {
    private String name;
    private String type;
    
    public String getName(){
        return name;
    }
    
    public String getType(){
        return type;
    }
    
    /** Creates a new instance of SimpleHeaderDef. Valid calls to this are:<br>
     *<code>
     *new SimpleHeaderDef("Column A","String");
     *new SimpleHeaderDef("Column A","BigDecimal");
     *new SimpleHeaderDef("Column A","Integer");
     *new SimpleHeaderDef("Column A","Date");
     *</code>
     *@see ReportingUtils.createGenericReportColumnView
     */
    public SimpleHeaderDef(String pName, String pType) {
        name = pName;
        type = pType;
    }
    
}
