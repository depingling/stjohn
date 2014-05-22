/*
 * EstoreClientCommunicationForm.java
 *
 * Created on November 15, 2002, 4:18 PM
 */

package com.cleanwise.view.forms;

import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author  bstevens
 */
public class EstoreClientCommunicationForm extends ActionForm {
    
    /** Holds value of property theFile. */
    private FormFile theFile;
    
    /** Holds value of property encrypted. */
    private String encrypted;
    
    /** Creates a new instance of EstoreClientCommunicationForm */
    public EstoreClientCommunicationForm() {
    }
    
    /** Getter for property theFile.
     * @return Value of property theFile.
     *
     */
    public FormFile getTheFile() {
        return this.theFile;
    }
    
    /** Setter for property theFile.
     * @param theFile New value of property theFile.
     *
     */
    public void setTheFile(FormFile theFile) {
        this.theFile = theFile;
    }
    
    /** Getter for property encrypted.
     * @return Value of property encrypted.
     *
     */
    public String getEncrypted() {
        return this.encrypted;
    }
    
    /** Setter for property encrypted.
     * @param encrypted New value of property encrypted.
     *
     */
    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }
    
}
