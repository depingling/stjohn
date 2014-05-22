package com.cleanwise.tools;

import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.taskdefs.*;
import org.apache.tools.ant.util.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;

public class GZipFileSet extends Task {
	private FileUtils filu = FileUtils.getFileUtils();
	
	private List inputFileSet = new ArrayList();
	/**
	 * @param fileset
	 */
	public void addFileset(FileSet fileset) {
        inputFileSet.add(fileset);
    }

	File outputFolder; 
	/**
	 * @return the outputFolder
	 */
	public File getOutputFolder() {
		return outputFolder;
	}

	/**
	 * @param outputFolder the outputFolder to set
	 */
	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

	String doNotRun = "false";
	public void setDoNotRun(String doNotRun){
		this.doNotRun = doNotRun;
	}
	public String getDoNotRun(){
		return doNotRun;
	}
	
	
	/**
	 * @see org.apache.tools.ant.Task#execute()
	 */	
	public void execute() throws BuildException {
		if("true".equalsIgnoreCase(doNotRun)){
			log("Do not run set, not gziping content");
			return;
		}
		log("set doNotRun variable to skip gzipping components");
		
		
		for (Iterator iter = inputFileSet.iterator(); iter.hasNext();) 
		{
			FileSet fs = (FileSet)iter.next();
			DirectoryScanner ds = fs.getDirectoryScanner(getProject());
			File base  = ds.getBasedir();     
			String[] includedFiles = ds.getIncludedFiles();
			for(int i=0; i<includedFiles.length; i++) {
				includedFiles[i] = includedFiles[i].replace('\\', '/');
				
				String includePrefix = "";
				if(includedFiles[i].lastIndexOf('/') != -1)
				{
					includePrefix = includedFiles[i].substring(0,includedFiles[i].lastIndexOf('/'));
				}
				File found = new File(base, includedFiles[i]);                
                File targetFolder = null;
                if(includePrefix.length()>0)
                {
                	targetFolder = new File(outputFolder,includePrefix);
                }
                else
                {
                	targetFolder = new File(outputFolder.getAbsolutePath());
                }
                log("outputFolder="+outputFolder);
                targetFolder = new File(outputFolder.getAbsolutePath());
                
                //targetFolder.mkdirs();
                File outputFile = null;
                System.out.println(includedFiles[i] +":" + targetFolder.getAbsolutePath());
            	
            		String theFileName = found.getName();
            		outputFile = new File(targetFolder+"/"+includedFiles[i]);                	
            	

            	try {
					doCompression(outputFile.getAbsolutePath());
				} catch (Exception e) {
					log("Error compressing "+found.getAbsolutePath() + ", "+e.getMessage(), Project.MSG_ERR);
					throw new BuildException("Error compressing "+found.getAbsolutePath() + ", "+e.getMessage(), e);
				}
            }
		}
	}
	
	private void doCompression(String toFile) throws Exception{
    	String zipFileS =toFile+".gz";
    	//log("zipping " + toFile + " to " + zipFileS);
    	File zipFile = new File(zipFileS);
    	
    	if(filu.isUpToDate(new File(toFile),zipFile)){
    		//log("skipping as file is up to date");
    		return;
    	}
    	if(zipFile.exists()){
    		zipFile.delete();
    	}
        // Create the GZIP output stream
        GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(zipFileS));
    
        // Open the input file
        FileInputStream in = new FileInputStream(toFile);
    
        // Transfer bytes from the input file to the GZIP output stream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
    
        // Complete the GZIP file
        out.finish();
        out.close();

	}

    

}
