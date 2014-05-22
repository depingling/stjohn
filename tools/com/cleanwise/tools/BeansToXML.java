/*
 * BeansToXML.java
 *
 * Created on October 27, 2003, 12:01 PM
 */

package com.cleanwise.tools;
import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author  bstevens
 */
public class BeansToXML extends Task{
    
    private File outfile;
    private File beansDirectory;
    private PrintStream out;
    
    public void setOutfile(File outfile) {
        this.outfile = outfile;
    }
    
    public void setBeansDirectory(File beansDirectory) {
        this.beansDirectory = beansDirectory;
    }
    
    
    public void execute() throws BuildException {
        
        log("Generating XML representation of beans to:");
        log("[" + outfile + "]");
        log("from source dir:");
        log("[" + beansDirectory + "]");
        
        try {
            if (outfile != null) {
                out = new PrintStream(new BufferedOutputStream(new FileOutputStream(outfile)));
            } else {
                out = System.out;
            }
            
            
            out.println("<?xml version =\"1.0\"?>");
            out.println("<beans>");
            
            File[] files = beansDirectory.listFiles();
            for(int i=0;i<files.length;i++){
                String bean = null;
                if(files[i].getName().endsWith("Home.java")){
                    bean = files[i].getName().substring(0,files[i].getName().length() - 9);
                }else{
                    continue;
                }
                if(bean != null){
                    out.println("   <bean javaname=\"" + bean + "\" JINDIname=\""+getJNDIName(bean)+"\">");
                    out.println("   </bean>");
                }
            }
            out.println("</beans>");
            
        } catch (IOException ioe) {
            throw new BuildException("Error writing " + outfile.getAbsolutePath(),
            ioe, location);
        }finally{
            out.flush();
            if (out != null && out != System.out) {
                out.close();
            }
        }
    }
    
    
    private String getJNDIName(String pString){
        StringBuffer ret = new StringBuffer();
        char[] chars = pString.toCharArray();
        for(int i=0;i<chars.length;i++){
            if(i>0 && Character.isUpperCase(chars[i])){
                ret.append("_");
            }
            ret.append(Character.toUpperCase(chars[i]));
        }
        return ret.toString();
    }
    
    
    
}
