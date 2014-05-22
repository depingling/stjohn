/**
 * Title:        GCUtils
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.gencode.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

import com.cleanwise.tools.gencode.GCException;

public class GCUtils {

    static public void WriteToFile(String fileName, Document document)
        throws GCException {
        if (fileName == null) {
            throw new GCException("File anme is not defined", "GCUtils.WriteToFile");
        }
        if (document == null) {
            throw new GCException("XML document is not defined", "GCUtils.WriteToFile");
        }
        File fileXML = null;
        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource(document);
            fileXML = new File(fileName);
            FileOutputStream os = new FileOutputStream(fileXML);
            StreamResult result = new StreamResult(os);
            transformer.transform(source, result);
        }
        catch (SecurityException ex) {
            String fullFileName = (fileXML == null) ? fileName : fileXML.getAbsolutePath();
            throw new GCException("An error occurred at writing of XML document into file '" + 
                fullFileName + "'. " + ex.getMessage(), "GCUtils.WriteToFile");
        }
        catch (FileNotFoundException ex) {
            String fullFileName = (fileXML == null) ? fileName : fileXML.getAbsolutePath();
            String message = "";
            try {
                if (fileXML != null) {
                    boolean dirExists = FileExists(fileXML.getParent());
                    message = "Directory '" + fileXML.getParent() + "' is not found. ";
                }
            }
            catch (Exception exd) {                
            }
            throw new GCException("An error occurred at writing of XML document into file '" + 
                fullFileName + "'. " + message + ex.getMessage(), "GCUtils.WriteToFile");
        }
        catch (Exception ex) {
            String fullFileName = (fileXML == null) ? fileName : fileXML.getAbsolutePath();
            throw new GCException("An error occurred at writing of XML document into file '" + 
                fullFileName + "'. " + ex.getMessage(), "GCUtils.WriteToFile");
        }
    }

    static public void WriteToFile(String fileName, StringReader text) 
        throws GCException {
        if (fileName == null) {
            throw new GCException("File anme is not defined", "GCUtils.WriteToFile");
        }
        if (text == null) {
            throw new GCException("The text to save in the file is not defined", "GCUtils.WriteToFile");
        }
        FileWriter outputStream = null;
        File outFile = null;
        try {
            outFile = new File(fileName);
            outputStream = new FileWriter(outFile);
            int chr;
            while ((chr = text.read()) != -1) {
                outputStream.write(chr);
            }
            outputStream.flush();
        }
        catch (IOException ex) {
            String fullFileName = (outFile == null) ? fileName : outFile.getAbsolutePath();
            throw new GCException("An error occurred at writing into file '" + 
                fullFileName + "'. " + ex.getMessage(), "GCUtils.WriteToFile");
        }
        catch (Exception ex) {
            String fullFileName = (outFile == null) ? fileName : outFile.getAbsolutePath();
            throw new GCException("An error occurred at writing into file '" + 
                fullFileName + "'. " + ex.getMessage(), "GCUtils.WriteToFile");
        }
        finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                }
                catch (Exception ex) {
                }
            }
        }
    }

    static public boolean FileExists(String fileName) {
        if (fileName == null) {
            return false;
        }
        File file = new File(fileName);
        return file.exists();
    }
    
    //bug # 4535: Added to support versioning for the temporary tables.
    public static String getJavaNameByDbTableName(String name, List<String> tablePrefixList) {
    	String tempName = name;
        String javaName = "";
        if (tablePrefixList!=null && tablePrefixList.size()>0) {
        	for(String prefix:tablePrefixList) {
        		if (name.length() > prefix.length() && name.startsWith(prefix)) {
                    tempName = name.substring(prefix.length());
                    break;
                }
        	}
        }
        StringCharacterIterator it = new StringCharacterIterator(tempName);
        char chr = it.first();
        boolean upFlag = true;
        while (chr != CharacterIterator.DONE) {
            if (chr == '_' || chr ==' ' || chr =='/') {
                upFlag = true;
            } 
            else {
                if (upFlag) {
                    javaName += Character.toUpperCase(chr);
                } else {
                    javaName += Character.toLowerCase(chr);
                }
                upFlag = false;
            }
            chr = it.next();
        }
        return javaName;
    }
    
    static public String GetJavaNameByDbName(String name, String prefix) {
        String tempName = name;
        String javaName = "";
        if (!IsEmptyString(prefix)) {
            if (name.length() > prefix.length() && name.startsWith(prefix)) {
                tempName = name.substring(prefix.length());
            }
        }
        StringCharacterIterator it = new StringCharacterIterator(tempName);
        char chr = it.first();
        boolean upFlag = true;
        while (chr != CharacterIterator.DONE) {
            if (chr == '_' || chr ==' ' || chr =='/') {
                upFlag = true;
            } 
            else {
                if (upFlag) {
                    javaName += Character.toUpperCase(chr);
                } else {
                    javaName += Character.toLowerCase(chr);
                }
                upFlag = false;
            }
            chr = it.next();
        }
        return javaName;
    }

    static public String getFullDataJavaName(String javaName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("com.cleanwise.service.api.value.");
        buffer.append(javaName);
        buffer.append("Data");
        return buffer.toString();
    }

    static public String getFullVectorJavaName(String javaName) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("com.cleanwise.service.api.value.");
        buffer.append(javaName);
        buffer.append("DataVector");
        return buffer.toString();
    }

    static public boolean IsEmptyString(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    static public String GetTabString(int tabs) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < tabs; ++i) {
            buffer.append("    ");
        }
        return buffer.toString();
    }

    static public boolean IsEqualsIgnoreCaseStrings(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 == null && str2 != null) {
            return false;
        }
        if (str1 != null && str2 == null) {
            return false;
        }
        return str1.trim().equalsIgnoreCase(str2.trim());
    }

    static public int CompareIgnoreCaseStrings(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return 0;
        }
        if (str1 != null && str2 == null) {
            return 1;
        }
        if (str1 == null && str2 != null) {
            return -1;
        }        
        return str1.trim().compareToIgnoreCase(str2.trim());
    }

}
