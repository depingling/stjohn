/*
 * BaseJDReport.java
 *
 * Created on October 15, 2008, 4:43 PM
 */

package com.cleanwise.service.api.reporting;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
//import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.service.api.util.ClwApiCustomizer;
import java.io.*;
import java.io.*;
import java.util.Map;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 * Picks up orders and agreates it on Sites
 * @params pBeginDate start of the period. pEndDate  end of the period
 * Adapted  to the new GenericReport Framework.
 *
 * @param pBeginDate start of the period,
 * @param pEndDate end of the period,
 * @param pAccountId account Id optionally,
 */
public  class SpecialReportTest extends ReportBase {

    public SpecialReportTest() {
      setFileName("Special Report Test");
      setExt("gif");
      setSpecial(true);
      setUserTypesAutorized(RefCodeNames.USER_TYPE_CD.CUSTOMER+","+RefCodeNames.USER_TYPE_CD.MSB);
    }
    /**
     *Different reports can overide this method to provide it's own File Name
     **/
//    public String getFileName() {
//      return repFileName;
//    }
    /**
      *Different reports can overide this method to provide it's own Extention
     **/
//    public String getExt() {
//      return  repFileExt;
//    }
    /**
     *Different reports can overide this method to provide it's own format : special or not
     **/
//    public boolean isSpecial() {
//      return special;
//   }

    /**
      *Different reports can overide this method to provide it's own Extention
     **/
    protected byte[] getOutputStream()  throws Exception {
      byte[] outputStream;
      String fileName = ClwApiCustomizer.getCustomizeElement("en/images/" + "tourpg11.gif");
      File ioFile = new File(fileName);
      InputStream inputStream = new FileInputStream(ioFile);
      int len = inputStream.available();
      outputStream = new byte[len];
      inputStream.read(outputStream);
      inputStream.close();

     return outputStream;
    }

}
