/*
 * ConnectionContainer.java
 *
 * Created on February 5, 2003, 10:43 AM
 */

package com.cleanwise.service.api.util;
import java.sql.Connection;
/**
 * Holds the various connection objects that can be passed around in one object.
 * @author  bstevens
 */
public class ConnectionContainer {
    
    /**
     *Picks up the non null connection.   If there are multiple an IllegalStateException is thrown
     */
    public Connection getDefaultConnection(){
        Connection conn = null;
        int conCt = 0;
        if(lawsonConnection != null){
            conCt++;
            conn = lawsonConnection;
        }
        if(reportConnection != null){
            conCt++;
            conn = reportConnection;
        }
        if(stagedInfoConnection != null){
            conCt++;
            conn = stagedInfoConnection;
        }
        if(mainConnection != null){
            conCt++;
            conn = mainConnection;
        }
        if(conCt == 0){
            return null;
        }
        if(conCt == 1){
            return conn;
        }
        throw new IllegalStateException("Cannot ask for the defult connection if more than one connection is set");
    }
    
    /** Holds value of property lawsonConnection. */
    private Connection lawsonConnection;
    
    /** Holds value of property reportConnection. */
    private Connection reportConnection;
    
    /** Holds value of property mainConnection. */
    private Connection mainConnection;
    private Connection stagedInfoConnection;

    /** Creates a new instance of ConnectionContainer */
    public ConnectionContainer() {
    }
    
    /** Creates a new instance of ConnectionContainer */
    public ConnectionContainer(Connection pLawsonConnection, Connection pReportConnection,Connection pMainConnection, Connection pStagedInfoConnection) {
        lawsonConnection = pLawsonConnection;
        reportConnection = pReportConnection;
        mainConnection = pMainConnection;
	stagedInfoConnection = pStagedInfoConnection;
    }

    
    /** Getter for property lawsonConnection.
     * @return Value of property lawsonConnection.
     *
     */
    public Connection getLawsonConnection() {
        return this.lawsonConnection;
    }
    
    /** Setter for property lawsonConnection.
     * @param lawsonConnection New value of property lawsonConnection.
     *
     */
    public void setLawsonConnection(Connection lawsonConnection) {
        this.lawsonConnection = lawsonConnection;
    }
    
    /** Getter for property reportConnection.
     * @return Value of property reportConnection.
     *
     */
    public Connection getReportConnection() {
        return this.reportConnection;
    }
    
    /** Setter for property reportConnection.
     * @param reportConnection New value of property reportConnection.
     *
     */
    public void setReportConnection(Connection reportConnection) {
        this.reportConnection = reportConnection;
    }
    
    /** Getter for property mainConnection.
     * @return Value of property mainConnection.
     *
     */
    public Connection getMainConnection() {
        return this.mainConnection;
    }
    
    /** Setter for property mainConnection.
     * @param mainConnection New value of property mainConnection.
     *
     */
    public void setMainConnection(Connection mainConnection) {
        this.mainConnection = mainConnection;
    }

    /** Getter for property stagedInfoConnection.
     * @return Value of property stagedInfoConnection.
     *
     */
    public Connection getStagedInfoConnection() {
        return this.stagedInfoConnection;
    }
    
    /** Setter for property stagedInfoConnection.
     * @param stagedInfoConnection New value of property stagedInfoConnection.
     *
     */
    public void setStagedInfoConnection(Connection stagedInfoConnection) {
        this.stagedInfoConnection = stagedInfoConnection;
    }
    
}
