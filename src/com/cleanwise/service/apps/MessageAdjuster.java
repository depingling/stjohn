/*
 * MessageAdjuster.java
 *
 * Created on July 17, 2006, 5:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.cleanwise.service.apps;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Ykupershmidt
 */
public class MessageAdjuster {
  private static final Logger log = Logger.getLogger(MessageAdjuster.class);
  private static final String LINE_END = "\r\n";
  /** Creates a new instance of MessageLoader */
  public MessageAdjuster() {
  }
  public int adjust(String fileName1, String fileName2, String fileName3) 
  throws Exception {
    // Check file existence
     File file1 = new File(fileName1);
     if(!file1.exists() || !file1.isFile()) {
       log.info("Error. Can't find file: "+fileName1);
       return 2;
     }
     File file2 = new File(fileName2);
     if(!file2.exists() || !file2.isFile()) {
       log.info("Error. Can't find file: "+fileName2);
       return 2;
     }
     //File file3 = new File(fileName3);
     //if(file3.exists() && file3.isFile()) {
     //  log.info("Error. File already exists: "+fileName3);
     //  return 2;
     //}
    
     FileOutputStream fos = new FileOutputStream(fileName3);
	   Writer out = new OutputStreamWriter(fos, "UTF8");
     BufferedWriter bwr = null;
     HashMap messHM2 = new HashMap();
     HashMap lineHM2 = new HashMap();
     LinkedList keys2 = new LinkedList();     
     FileInputStream fis2 = new FileInputStream(file2);
     BufferedReader rdr2 = new BufferedReader(new InputStreamReader(fis2,"UTF-8"));
     String line = rdr2.readLine();
     int count = 0;
     while(line!=null) {
       count++;
if(count>1000) {
  break;
}
       MessageResourceData mD = getMessage(line);
       if(mD!=null) {
         messHM2.put(mD.getName(),line);
         keys2.add(mD.getName());
       }
       line = rdr2.readLine();
     }

     FileInputStream fis1 = new FileInputStream(file1);
     BufferedReader rdr1 = new BufferedReader(new InputStreamReader(fis1,"UTF-8"));
     String line1 = rdr1.readLine();
     while(line1!=null) {     
       count++;
       MessageResourceData mD = getMessage(line1);
       if(mD==null) {
         out.write(line1);         
         out.write(LINE_END);
       } else {
         String key = mD.getName();
         String value = (String) messHM2.get(key);
         if(value!=null) {
           out.write(value);
           out.write(LINE_END);
           messHM2.remove(key);
         } else {
           String newVal = key+"=????????"+mD.getValue();
           out.write(newVal);
           out.write(LINE_END);
         }
       }
       line1 = rdr1.readLine();
     }

     Set entrySet = messHM2.entrySet();
     if(!entrySet.isEmpty()) {
       out.write("########## Extra Properties ##############");
       out.write(LINE_END);
       for(Iterator iter = entrySet.iterator(); iter.hasNext();) {
         Map.Entry me = (Map.Entry) iter.next();
         out.write((String) me.getValue());
         out.write(LINE_END);
       }
     }
     out.close();
     return 0;
  }

  private MessageResourceData getMessage(String pStr) {
    if(!Utility.isSet(pStr)) {
      return null;
    }
    
    pStr = pStr.trim();
    if(pStr.startsWith("#")) {
      return null;
    }
    int ind = pStr.indexOf("=");
    if(ind<0) {
      return null;
    }
    String key = pStr.substring(0,ind);
    if(!Utility.isSet(key)) {
      return null;
    }
    key=key.trim();
    
    String value = "";
    if(pStr.length()>ind+1) {
        value = pStr.substring(ind+1).trim();
    }
    MessageResourceData mD = MessageResourceData.createValue();
    mD.setName(key);
    mD.setValue(value);
    return mD;
  }
  
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
    // TODO code application logic here
    log.info("Cleanwise Message Loader. Parameters: ");
    log.info(" - source file name");
    log.info(" - adjusted file name");
    log.info(" - result file name");
    if(args.length<3) {
      log.info("Error. Only "+args.length+" parameters found");
      return;
    }
    MessageAdjuster madj = new MessageAdjuster();
    madj.adjust(args[0],args[1],args[2]);
    return;
  }
  
}
