
/*
 * FileUpload.java
 *
 * Created on November 15, 2002, 3:57 PM
 */

package com.cleanwise.service.api.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Blob;

import org.apache.struts.upload.FormFile;

import org.apache.log4j.Logger;

/**
 *Utility class to handle the common methods involved in dealing with files and IOStreams
 * @author  bstevens
 */
public class IOUtilities {
    private static final Logger log = Logger.getLogger(IOUtilities.class);
    
    public static void loadWebFileToOutputStream(FormFile file, OutputStream pOut)
    throws IOException{
        InputStream stream = file.getInputStream();
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
            pOut.write(buffer, 0, bytesRead);
        }
        pOut.flush();
        pOut.close();
        stream.close();
    }
    
    public static void copyStream(InputStream in, OutputStream out)
    throws IOException{
        byte[] buffer = new byte[8 * 1024];
        int count = 0;
        do {
            out.write(buffer, 0, count);
            count = in.read(buffer, 0, buffer.length);
        } while (count != -1);
        out.flush();
    }
    
    /**
     *"Fail safe" move which will move a file to the specified destination directory
     *with the optional suffix String.  If the destination directory does not exsist 
     *it will be created.  If the destination file exsists, as determined by:
     *<code>
     *(new File(destDir.getCanonicalFile(),fromFile.getName() + suffix)).exists();
     *</code>
     *It will attempt to rename the file with a .n after it, and continue to do so until
     *it can actually move the file.
     *@param fromFile the file to move
     *@param destDir the directory to move the file to
     *@param suffix optional suffix to append to moved file
     *@throws IOException if it cannot find a file to move the supplied file to
     *  or any problems accessing the file system occur.
     */
    public static void moveFile(File fromFile, File destDir, String suffix)
    throws IOException{
        File destFile = getAvailiableFileHandle(fromFile.getName(), destDir, suffix,0);
        if(!fromFile.renameTo(destFile)){
            createDirs(destFile.getParentFile());
            if(!fromFile.renameTo(destFile)){
                throw new IOException("Could not move file");
            }
        }
    }
    
    private static void createDirs(File theFile) throws IOException{
        if(theFile.isFile()){
            throw new IOException("could not create directory::" + theFile.getAbsolutePath() + " it is exsists and is not a file");
        }
        if (!theFile.exists()){
            if(!theFile.mkdirs()){
                String lDirCreateErrorMsg = "could not create directory::" + theFile.getAbsolutePath();
                throw new IOException(lDirCreateErrorMsg);
            }
        }
    }
    
    /**
     *If the destination file exsists, as determined by:
     *<code>
     *(new File(destDir.getCanonicalFile(),fromFile.getName() + suffix)).exists();
     *</code>
     *It will attempt to apend a n after it, and continue to do so until
     *it finds a file that does not exsist.
     *@param reqFileName the file name that this file should be called
     *@param destDir the directory to examine
     *@param suffix optional suffix to append to file
     *@returns a File handle to a non-exsistant file
     *@throws IOException if it cannot find a file that does not exsist matching the criteria specified
     *  or any problems accessing the file system occur.
     */
    public static File getAvailiableFileHandle(String reqFileName, File destDir, String suffix)
    throws IOException{
        return getAvailiableFileHandle(reqFileName, destDir, suffix,0);
    }
    
    //recursive method to do the work of finding an availiable filename conforming to the requested
    //paramaters.  Will continue to call itself until it finds a file that does not exsist.
    private static File getAvailiableFileHandle(String reqFileName, File destDir, String suffix,int attemptNumber)
    throws IOException{
        if(attemptNumber >= 100){
            throw new IOException("Could not move file after 100 atempts (all files exsist)! " + destDir.getCanonicalPath() + "/" + reqFileName);
        }
        if(suffix == null){
            suffix = "";
        }
        String attemptNumberString;
        if(attemptNumber == 0){
            attemptNumberString = "";
        }else{
            attemptNumberString = Integer.toString(attemptNumber);
        }
        File destFile = new File(destDir.getCanonicalFile(),reqFileName + suffix + attemptNumberString);
        if(destFile.exists()){
            attemptNumber++;
            return getAvailiableFileHandle(reqFileName, destDir, suffix,attemptNumber);
        }else{
            return destFile;
        }
    }
    
    /**
     *Reads in a file and spits out a string.  Does not do any validation that the
     *file is actually ascii or exists.
     *@param pFile the file to read
     */
    public  static String loadFile(File pFile) throws IOException{
    	BufferedReader rdr = new BufferedReader(new FileReader(pFile));
        return loadFile(rdr);
    }
    
    /**
     *Reads in an InputStream and spits out a string.  Does not do any validation that the
     *stream is actually ascii.
     *@param pInput the InputStream
     */
    public  static String loadStream(InputStream pInput) throws IOException{
    	BufferedReader rdr = new BufferedReader(new InputStreamReader(pInput));
    	return loadFile(rdr);
    }
    
    /**
     *Reads in a buffered reader and spits out a string. 
     *@param pReader the reader
     */
    private static String loadFile(BufferedReader pReader) throws IOException{
    	String line = pReader.readLine();
		StringBuffer data = new StringBuffer();
        while(line != null){
            data.append(line);
            line = pReader.readLine();
        }
        return data.toString();
    }
    
    
    public static int renameFile(String currentName,String futureName)
    {
        try {
            File file=new File(currentName);
            file.renameTo(new File(futureName));
        } catch (Exception e) {
            log.info("[Utillity::renameFile] "+e.getMessage());
            return -1;
        }
        return 0;
    }

    public static int renameStoreFile(String currentName, String futureName) {

        String currentPathFile = System.getProperty("webdeploy") + "/" + currentName;
        String futurePathFile = System.getProperty("webdeploy") + "/" + futureName;

        return renameFile(currentPathFile, futurePathFile);
    }

    public static String convertToTempFile(Blob binaryData, String fileName) {
        File dir = getImageDir();
        return writeDataFileToDir(binaryData, dir, fileName);
    }

    public static String convertToTempFile(byte[] binaryData, String fileName) {
        File dir = getImageDir();
        return writeDataFileToDir(binaryData, dir, fileName);
    }

    public static String convertToTempFile(byte[] binaryData, String fileName, String prefix) {
        File dir = getImageDir();
        return writeDataFileToDir(binaryData, dir, fileName, prefix);
    }

    private static String writeDataFileToDir(Blob binaryData, File dir, String fileName) {
        try {
            return writeDataFileToDir(binaryData.getBytes(1L, (int) binaryData.length()), dir, fileName);
        } catch (Exception e) {
            return null;
        }
    }

    private static String writeDataFileToDir(byte[] binaryData, File dir, String fileName) {
        return writeDataFileToDir(binaryData, dir, fileName, null);
    }

    private static String writeDataFileToDir(byte[] binaryData, File dir, String fileName, String prefix) {

        try {
            if (!Utility.isSet(prefix)) {
                prefix = "Interstitial";
            }
            File imgTempFile = File.createTempFile(prefix, getFileExt(fileName), dir);
            FileOutputStream fos = new FileOutputStream(imgTempFile);
            fos.write(binaryData);
            fos.flush();
            fos.close();
            return imgTempFile.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File getImageDir() {
        String dir = System.getProperty("webdeploy") + "/en/images/tmp";
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static String getFileExt(String fileName) {
        String fileExt = null;

        int i = fileName.lastIndexOf(".");
        if (i < 0) {
            fileExt = "";
        } else {
            fileExt = fileName.substring(i);
        }
        return fileExt;
    }
    
    public static byte[] toBytes(InputStream pIn) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            copyStream(pIn, baos);
            return baos.toByteArray();
        } finally {
            try {
            	baos.close();
            } catch (IOException e) {
                log.error(e, e);
            }
        }
    }

}
