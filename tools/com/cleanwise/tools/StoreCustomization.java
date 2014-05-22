package com.cleanwise.tools;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.sql.*;
import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.apache.tools.ant.taskdefs.Copy;
/**
 * Tag library which will create the customized store directories.  It will query the database
 * for a listing of all the store prefixes, it will be given the base directory to use as the
 * source (default) files, and it will be given the driectory to search for customized files.
 * That is to say given:
 *<pre>
 *Source:
 * /install_dir/docroot/default_jsp
 *Custom files:
 * /install_dir/docroot/custom_jsp
 *                                /jwp
 *                                /clw
 *The result will be:
 * Destination:
 * /run_dir/docroot
 *                  /jwp
 *                  /clw
 *                  /jci
 *                  /etc
 *if jci and etc are defined as stores in the database.
 *</pre>
 * @author  bstevens
 */
public class StoreCustomization extends Task {
    
    private String driver;
    private String url;
    private String userid;
    private String password;
    private Path classpath;
    private String defaultSourceDirectory;
    private String destDirectory;
    private String customFilesDirectory;
    private boolean filtering;
    private ArrayList filesets;
    private PatternSet patternSet;
    private String mStores;
    
    /**
     * Property that can be used to only configure specific stores.
     * Useful for developing.  Takes a coma seperated list
     */
    public void setStores(String pStores){
    	mStores = pStores;
    }
    /**
     * Property that can be used to only configure specific stores.
     * Useful for developing.  Takes a coma seperated list
     */
    public String getStores(){
    	return mStores;
    }
    
    /**
     *The source of all the default files.  Anything in the directory will be copied to
     *a sub directory of the dest directory plus the store prefix.  For example if we have
     *a store with the configured prefix of ABC and a file called foo.txt in the default source
     *directory and the configured destination directory is /bar the result will be:
     * /bar/ABC/foo.txt
     */
    public void setDefaultSourceDirectory(String directory){
        defaultSourceDirectory=directory;
    }
    
    /**
     *The source directory of any files that have been customized.  These files are copied
     *to the source directory if they are in a folder that is named after the store prefix.
     *That is if there is a subfolder ABC and CBA and the only store in the database is ABC
     *the files in CBA are ignored and the files in ABC are copied.
     */
    public void setCustomFilesDirectory(String directory){
        customFilesDirectory=directory;
    }
    
    /*public PatternSet createDefaultSourcePatternSet(){
        log("68 called");
        return new PatternSet();
    }
    
    public PatternSet createCustomFilesPatternSet(){
        log("73 called");
        return new PatternSet();
    }*/
    
    /**
     *The patternset to use while copying the files
     */
    public void addPatternSet(PatternSet ps){
        log("78 called");
        if(patternSet == null){
            patternSet = new PatternSet();
        }
        patternSet = ps;
    }
    
    
    /**
     *The destination to copy all of the customized and default files too.
     */
    public void setDestDirectory(String directory){
        destDirectory=directory;
    }
    
    
    
    /**
     *The Database driver we are going to use to connect to the database
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    /**
     *The URL for the Database we are going to use to connect to the database
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    /**
     *The userid for the Database we are going to use to connect to the database
     */
    public void setUserid(String userid) {
        this.userid = userid.toUpperCase();
    }
    
    /**
     *The password for the Database we are going to use to connect to the database
     */
    public void setPassword(String password) {
        this.password = password.toUpperCase();
    }
    
    
    public void setClasspath(Path classpath) {
        if (this.classpath == null) {
            this.classpath = classpath;
        } else {
            this.classpath.append(classpath);
        }
    }
    
    public Path createClasspath() {
        if (this.classpath == null) {
            this.classpath = new Path(project);
        }
        return this.classpath.createPath();
    }
    
    public void setClasspathRef(Reference r) {
        createClasspath().setRefid(r);
    }
    
    /**
     *Passed in to a Copy task
     * @see Copy.setFiltering
     */
    public void setFiltering(boolean pFiltering) {
        filtering = pFiltering;
    }

    
    /**
     *Passed in to a Copy task
     * @see Copy.addFileset
     */
    public void addFileset(FileSet set) {
        filesets.add(set);
    }
    
    /**
     *Does the bulk of the work.  Called by ant during the build process
     */
    public void execute() throws BuildException {
        
        
        log("Starting Store Customization");
        
        try {
            AntClassLoader loader = new AntClassLoader(project, classpath, false);
            Class.forName(driver);
        } catch (Exception e) {
            throw new BuildException("ClassNotFoundException: " + e.getMessage(), e, location);
        }
        
        List storePrefixList = new ArrayList();
        String storeS = System.getProperty("stores");
        storeS = mStores;
        if(storeS!=null && !(storeS.startsWith("${") && storeS.endsWith("}"))){
        	StringTokenizer tok = new StringTokenizer(storeS,",");
        	while(tok.hasMoreTokens()){
        		String storePrefix = tok.nextToken().trim();
        		storePrefixList.add(storePrefix);
        		log("   Found store prefix from 'stores' param: "+storePrefix);
        	}
        }
        
        if(storePrefixList.isEmpty()){
        	log("no stores parameter passed in (-Dstores=YYY), configuring all stores");
	        //do the database work
	        Connection conn=null;
	        try {
	            conn = DriverManager.getConnection(url, userid, password);
	            
	            String sql = "SELECT DISTINCT clw_value FROM clw_bus_entity store, clw_property prop "+
	            "WHERE bus_entity_type_cd = 'STORE' AND prop.bus_entity_id = store.bus_entity_id AND prop.property_type_cd = 'STORE PREFIX' "+
	            "AND prop.property_status_cd = 'ACTIVE' AND store.bus_entity_status_cd = 'ACTIVE'";
	            
	            Statement stmt = conn.createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            while(rs.next()){
	                String storePrefix = rs.getString("clw_value");
	                log("   Found store prefix: "+storePrefix);
	                storePrefixList.add(storePrefix);
	            }
	            conn.close();
	        } catch (SQLException e) {
	            throw new BuildException("SQLException: " + e.getMessage(),e, location);
	        }finally {
	            try {
	                if(conn!= null){
	                    conn.close();
	                }
	            }catch (SQLException e) {
	                log("Could not close database connection");
	            }
	        }
        }else{
        	log("Skipping DB as stores param was passed in (-Dstores=YYY)");	
        }
        
        //do the file manipulation
        try{
            
            Iterator it = storePrefixList.iterator();
            File defaultSrcDirFile =  new File(defaultSourceDirectory);
            while(it.hasNext()){
                String prefix = (String) it.next();
                log("   Processing store prefix: "+prefix);
                File destDirectoryFile = new File(destDirectory+"/"+prefix);
                //create the directories if they  do not already exist
                destDirectoryFile.mkdirs();
                log("   Created directory: "+destDirectoryFile.getAbsolutePath());
                //copy the default set of files over
                doCopy(destDirectoryFile,defaultSrcDirFile,false);
                log("   Copied default files");
                //now copy over the custom ones
                File customFilesDirectoryFile = new File(customFilesDirectory +"/"+ prefix);
                if(customFilesDirectoryFile.exists()){
                    doCopy(destDirectoryFile,customFilesDirectoryFile,true);
                    log("   Copied custom files");
                }
            }
            
            
            if(false){
                throw new IOException("asdf");
            }
        }catch(IOException e){
            throw new BuildException("IOException: " + e.getMessage(),e, location);
        }
    }
    
    /**
     *Uses the built in ant copy task to preform the actual copy
     */
    private void doCopy(File dest, File fromDir,boolean overwrite)throws BuildException{
        
        Copy copyTask =(Copy) project.createTask("copy");
        if(copyTask == null){
            throw new BuildException("Could not create assumed built in task copy");
        }
        FileSet fileSet = new FileSet();
        fileSet.createPatternSet().append(patternSet,project);
        fileSet.setDir(fromDir);
        
        copyTask.addFileset(fileSet);
        copyTask.setTodir(dest);
        copyTask.setFiltering(filtering);
        copyTask.setOverwrite(overwrite);
        copyTask.execute();
    }
}
