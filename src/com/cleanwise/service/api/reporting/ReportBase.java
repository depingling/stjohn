/*
 * ReportBase.java
 *
 */

package com.cleanwise.service.api.reporting;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.ConnectionContainer;
import java.util.Map;
import java.util.*;
import com.cleanwise.service.api.util.ReportInterf;
/**
  * Title:        ReportBase
  * Description:  Base Class for report development
  * Purpose:      setup default values of properties and interface methods implementation
  * @author       Natalia Guschina
*/
public  class ReportBase  implements GenericReportMulti, ReportInterf {
    private  String repFileName = "report";
    private  String repFileExt = "data";
    private  boolean special = false;
    private String userTypesAutorized="";

    /** Creates a new instance of ReportBase */
    public ReportBase() {}

    /** Should return a populated GenericReportResultView object.  At a minimum the header should
     * be set so an empty report may be generated to the user.
     *
     */

    public GenericReportResultViewVector  process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams)
           throws Exception {
       GenericReportResultView result = GenericReportResultView.createValue();
       result.setRawOutput(getOutputStream());
       GenericReportResultViewVector resultV = new GenericReportResultViewVector();
       resultV.add(result);
       return resultV;
    }

    /**
       *Different reports can overide this method to provide it's own  File Name
       **/
     public String getFileName() {
        return repFileName;
     }
      /**
        *Different reports can overide this method to provide it's own Extention
       **/
     public String getExt() {
        return repFileExt;
     }
     /**
       *Different reports can overide this method to provide it's own format : special or not
      **/
    public boolean isSpecial() {
       return special;
    }
    public String getUserTypesAutorized(){
      return userTypesAutorized;
    }

    protected byte[] getOutputStream() throws Exception {
      return new byte[1];
    }

    protected void setFileName(String pFileName) {
      repFileName = pFileName;
    }

    protected void setExt(String pFileExt) {
      repFileExt = pFileExt;
    }

    protected void setSpecial(boolean pSpecial) {
      special = pSpecial;
    }
    protected void setUserTypesAutorized(String pUserTypesAutorized) {
      userTypesAutorized = pUserTypesAutorized;
    }

}
