package BranchStJohn;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class getBranchStJohn {
	
    private static String fileName = "build_info.properties";
    private static String branchFileName = "Entries";
    private static Properties props = new Properties();
    
    public static void main(String[] args) {
    	
    	File currentDir = new File(".");
    	String lastPathName = "trunk";
    	int position = -1; 
    	
    	try {
            String Path = currentDir.getCanonicalPath();
            String FilePath = Path + "/buildVersion/" + fileName;
            System.out.println(currentDir + "/CVS/" + branchFileName);
            String branchFilePath = currentDir+"/buildVersion/CVS/" + branchFileName;
                   
            FileInputStream fstream = new FileInputStream(branchFilePath);

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null)   {
              System.out.println (strLine);
              if (strLine.indexOf("build_info.properties") != -1)
            	if (strLine.indexOf("build_info.properties.baseline") == -1){
            	 position = strLine.lastIndexOf("/");
            	 if (position == -1) {
                  int i = 92;
                  String aChar = new Character((char) i).toString(); 
               	  position = strLine.lastIndexOf(aChar);            		
            	 }
            	 if (position != -1) {
        	       System.out.println("position= "+String.valueOf(position));
        	       if ((position+1) < strLine.length()) {
        	         lastPathName = strLine.substring(position+1, strLine.length());      
        	         position = strLine.lastIndexOf("_");
        	         if (position != -1) {
        	           lastPathName = strLine.substring(position+1, strLine.length());	
        	         }
        	       }  
            	 } 
              }
            }
            
            if (position == -1)
              lastPathName = "Unknown";   	
            
            in.close();     
      	    System.out.println("lastPathName= "+lastPathName); 
      	    
          	FileInputStream ins = new FileInputStream(FilePath);
            
          	props.load(ins);
          
          	props.setProperty("build.branch.number", lastPathName);
          	System.out.println("build.branch.number="+props.getProperty("build.branch.number"));
          
          	props.store(new FileOutputStream(FilePath), null);        	    
            
        }
        catch (Exception e) {
          System.out.println("Exception: "+e.getMessage());
          e.printStackTrace();
          System.exit(-1);
        }
    	
    }
    
}
