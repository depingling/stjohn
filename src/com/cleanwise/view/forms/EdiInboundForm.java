package com.cleanwise.view.forms;

import org.apache.struts.upload.FormFile;
import org.apache.struts.action.ActionForm;


/**
 */

public class EdiInboundForm extends ActionForm {

    /**
     * The value of the text the user has sent as form data
     */
    protected String theText;

    /**
     * Whether or not to write to a file
     */
    protected boolean writeFile;

    /**
     * The file that the user has uploaded
     */
    protected FormFile theFile;

    /**
     * The file path to write to
     */
    protected String filePath;



    /**
     * Retrieve the value of the text the user has sent as form data
     */
    public String getTheText() {
	return theText;
    }

    /**
     * Set the value of the form data text
     */
    public void setTheText(String theText) {
	this.theText = theText;
    }

    /**
     * Retrieve a representation of the file the user has uploaded
     */
    public FormFile getTheFile() {
	return theFile;
    }

    /**
     * Set a representation of the file the user has uploaded
     */
    public void setTheFile(FormFile theFile) {
	this.theFile = theFile;
    }

    /**
     * Set whether or not to write to a file
     */
    public void setWriteFile(boolean writeFile) {
	this.writeFile = writeFile;
    }

    /**
     * Get whether or not to write to a file
     */
    public boolean getWriteFile() {
	return writeFile;
    }

    /**
     * Set the path to write a file to
     */
    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

    /**
     * Get the path to write a file to
     */
    public String getFilePath() {
	return filePath;
    }

    public void reset() {
	writeFile = false;
    }
}


