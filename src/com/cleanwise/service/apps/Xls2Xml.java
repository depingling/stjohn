package com.cleanwise.service.apps;

import java.math.*;
import java.io.*;
import jxl.*;
import jxl.format.*;
import org.dom4j.*;
import org.apache.log4j.Logger;



/**
 * Convert Xls to Xml
 * First page has header info and should not have more than one row of data.
 * @author YKupershmidt
 */
public class Xls2Xml extends Object {
  private static final Logger log = Logger.getLogger(Xls2Xml.class);
  /**
   * Constructor
   */
  public Xls2Xml() {
  }

  /**
   * main
   * @param args
   */
  public static void main(String[] args) 
    throws Exception
    {

    if(args.length<1) {
      log.info("Error: not enough arguments");
    }
    Xls2Xml xlsXml= new Xls2Xml();
    xlsXml.convertXls(args[0]);
  }


  public void convertXls(String pFileName)
   throws Exception
  {
      
    String outFileName = pFileName;
    int index = outFileName.lastIndexOf(".");
    if(index>0) outFileName = outFileName.substring(0,index);
    outFileName += ".xml";
    File outFile = new File(outFileName);
    
  	Document theXmlDoc = DocumentFactory.getInstance().createDocument();    
    Element rootEl = theXmlDoc.addElement("root");
    
    Workbook workbook = Workbook.getWorkbook(new File(pFileName));
    Sheet[] sheetA = workbook.getSheets();
    Element headerEl = null;
	for(int hh=0; hh<sheetA.length; hh++) {
	   Sheet sheet = sheetA[hh];
       String sheetName = sheet.getName();
       if(hh==0) headerEl = rootEl.addElement(sheetName);
       Element sheetEl =(hh==0)?(headerEl):(headerEl.addElement(sheetName));
       int colQty = sheet.getColumns();
       int rowQty = sheet.getRows();
       if(rowQty<1) {
         String mess = "******** Error. Empty sheet";
         throw new Exception(mess);
       }

       String[] fieldNames = new String[colQty];
       for(int jj=0; jj<colQty; jj++) {
           Cell cNameC = sheet.getCell(jj,0);
           String cName = cNameC.getContents();
           if(cName==null || cName.trim().length()==0) {
               String mess = "******** Error. No column name found. Column: "+ jj;
               throw new Exception(mess);
           }
           fieldNames[jj] = cName;
       }

       //Create xml
       for(int ii=1; ii<rowQty; ii++) {
           Element rowEl = null;           
           if(hh==0) {
               rowEl = sheetEl;
           } else {
                rowEl = sheetEl.addElement(sheetName+"_line");
           }
           for(int jj=0; jj<colQty; jj++) {
               Element cellEl = rowEl.addElement(fieldNames[jj]);
               Cell cValueC = sheet.getCell(jj,ii);
               String cValueS = cValueC.getContents();
               setText(cellEl,cValueS);
           }
       }
    }
    String xml = theXmlDoc.asXML();
    OutputStream os = new FileOutputStream(outFile);
    os.write(xml.getBytes());
    os.flush();
}
    private void setText(Element el, String val){
    	if(val != null){
    		el.setText(val);
    	} else {
            el.setText("");
        }
    }
}

