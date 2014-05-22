/*
 * DeveloperFileBackup.java
 *
 * Created on July 23, 2003, 10:11 AM
 */

package com.cleanwise.tools;
import java.io.*;
import java.util.Iterator;
import java.util.ArrayList;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.IOUtilities;
/**
 * Does a CVS update and uses the resulting output to create a backup copy of any changed files 
 * or files CVS does not know about in the specified destination directory, preserving the file 
 * structure.
 * @author  bstevens
 */
public class DeveloperFileBackup {
    private static String srcDirStr;
    private static String destDirStr;
    private static File srcDir;
    private static File destDir;
    private static String srcDirStrAbsPath;
    private static String destDirStrAbsPath;
    
    /** Creates a new instance of DeveloperFileBackup */
    public DeveloperFileBackup() {
    }
    
    private static boolean isValidDirectory(File pFile){
        boolean returnVal = true;
        if(!pFile.exists()){
            System.out.println(pFile.getAbsolutePath() + " does not exist");
            returnVal = false;
        }
        if(!pFile.isDirectory()){
            System.out.println(pFile.getAbsolutePath() + " is not a directory");
            returnVal = false;
        }
        if(!pFile.canRead()){
            System.out.println(pFile.getAbsolutePath() + " is not readable");
            returnVal = false;
        }
        if(!pFile.canWrite()){
            System.out.println(pFile.getAbsolutePath() + " is not writable");
            returnVal = false;
        }
        return returnVal;
    }
    
    private static void copyDirectory(File pFromDir){
        File[] files = pFromDir.listFiles();
        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory()){
                copyDirectory(files[i]);
            }else{
                try{
                    copyFile(files[i]);
                }catch(Exception e){
                    System.out.println("failed to backup file: "+files[i].getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     *Copies the specified file to our backup location
     */
    private static void copyFile(File pFile) throws IOException{
        //use absolute paths as the cvs list may be a different dir then we are running in
        StringBuffer abPath = new StringBuffer(pFile.getAbsolutePath());
        Utility.replaceString(abPath, srcDirStrAbsPath, destDirStrAbsPath);
        copyFile(pFile,new File(abPath.toString()));
    }
    
    private static void copyFile(File pFrom, File pTo) throws IOException{
        pTo = pTo.getAbsoluteFile();
        pFrom = pFrom.getAbsoluteFile();
        if(pTo.exists()){
            pTo.delete();
        }
        if(pTo.getParentFile() != null){
            pTo.getParentFile().mkdirs();
        }
        
        if(pFrom.isDirectory()){
            copyDirectory(pFrom);
        }else{
            pTo.createNewFile();
            System.out.println("copying: "+pFrom+ " to: " +pTo);
            IOUtilities.copyStream(new FileInputStream(pFrom), new FileOutputStream(pTo));
        }
    }
    
    private static void copyFile(String pFileName) throws IOException{
        StringBuffer fileNameBuf = new StringBuffer(pFileName);
        File fromFile = new File(srcDir + "/" + pFileName);
        File toFile = new File(destDirStr + "/" + pFileName);
        copyFile(fromFile,toFile);
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 2 || args[0].equals("-h") || args[0].indexOf("help") >= 0){
            System.out.println("Usage: [java "+DeveloperFileBackup.class.getName()+" <src directory> <dest directory>]");
            return;
        }
        
        System.out.println("");
        System.out.println("Started");
        System.out.println("");
        
        srcDirStr = args[0];
        destDirStr = args[1];
        try{
            srcDir = new File(srcDirStr);
            srcDirStrAbsPath = srcDir.getAbsolutePath();
            destDir = new File(destDirStr);
            destDirStrAbsPath = destDir.getAbsolutePath();
            //if the destination does not exist create it
            if(!destDir.exists()){
                destDir.mkdir();
            }
            //if everything is valid proceed
            if(!(isValidDirectory(srcDir) && isValidDirectory(destDir))){
                return;
            }
            if(System.getProperty("HOME") == null){
                System.setProperty("HOME", srcDirStr);
            }
            Process proc = Runtime.getRuntime().exec("cvs -q update -d",null,srcDir);
            BufferedReader rdr = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            
            ArrayList toBackup = new ArrayList();
            String line;
            while((line = rdr.readLine()) != null){
                System.out.println("[JAVA] "+line);
                //if the line's second char is a space and it does not start with U or P then
                //we are interested in it.
                char first = line.charAt(0);
                char second = line.charAt(1);
                if((second == ' ') && !(first=='U' || first=='P')){
                    toBackup.add(line.substring(2));
                }
            }
            
            Iterator it = toBackup.iterator();
            while(it.hasNext()){
                String fileName = null;
                try{
                    fileName = (String) it.next();
                    copyFile(fileName);
                }catch(Exception e){
                    System.out.println("failed to backup file: "+fileName);
                    e.printStackTrace();
                }
            }
            System.out.println("finished");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
