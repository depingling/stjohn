/*
 * FaxConfig.java
 *
 * Created on May 7, 2002, 2:50 PM
 */

package com.cleanwise.view.utils.fax;
import java.util.Locale;
/**
 * common configuration settings that the fax classes will use.  
 * @author  bstevens
 */
public class FaxConfig {

    /** Holds value of property faxServer. */
    private String faxServer;
    
    /** Holds value of property debug. */
    private boolean debug;
    
    /** Holds value of property coverPage. */
    private String coverPage = "";
    
    /** Holds value of property imageDirectoryPath. */
    private String baseDirectoryPath;
    
    /** Creates a new instance of FaxConfig with all of the requiered properties set*/
    public FaxConfig(String pFaxServer) {
        this.faxServer = pFaxServer;
    }

    /** Getter for property faxServer.
     * @return Value of property faxServer.
     */
    public String getFaxServer() {
        return this.faxServer;
    }
    
    /** Setter for property faxServer.
     * @param faxServer New value of property faxServer.
     */
    public void setFaxServer(String faxServer) {
        this.faxServer = faxServer;
    }
    
    /** Getter for property debug.
     * @return Value of property debug.
     */
    public boolean isDebug() {
        return this.debug;
    }
    
    /** Setter for property debug.
     * @param debug New value of property debug.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    /** Getter for property coverPage.
     * @return Value of property coverPage.
     */
    public String getCoverPage() {
        return this.coverPage;
    }
    
    /** Setter for property coverPage.
     * @param coverPage New value of property coverPage.
     */
    public void setCoverPage(String coverPage) {
        this.coverPage = coverPage;
    }
        
    /** Getter for property baseDirectoryPath.
     * @return Value of property baseDirectoryPath.
     */
    public String getBaseDirectoryPath() {
        return this.baseDirectoryPath;
    }
    
    /** Setter for property baseDirectoryPath.
     * @param baseDirectoryPath New value of property imageDirectoryPath.
     */
    public void setBaseDirectoryPath(String baseDirectoryPath) {
        if (baseDirectoryPath.endsWith("\\") || baseDirectoryPath.endsWith("/")){
            this.baseDirectoryPath = baseDirectoryPath;
        } else {
            this.baseDirectoryPath = baseDirectoryPath + "/";
        }
    }
    
    /**To String method, returns a string of all the properties in a 
     *human readable format suitable for debugging.*/
    public String toString() {
        return this.getClass().toString() + 
            " coverPage: " + this.coverPage +
            " debug: " + this.debug +
            " baseDirectoryPath: " + this.baseDirectoryPath +
            " faxServer: " + this.faxServer;
    }
}
