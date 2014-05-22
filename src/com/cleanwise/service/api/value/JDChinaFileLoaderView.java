
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        JDChinaFileLoaderView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;




/**
 * <code>JDChinaFileLoaderView</code> is a ViewObject class for UI.
 */
public class JDChinaFileLoaderView
extends ValueObject
{
   
    private static final long serialVersionUID = -1L;
    private String mFileName;
    private byte[] mFileContent;
    private String mDbUrl;
    private String mDbUser;
    private String mDbPassword;

    /**
     * Constructor.
     */
    public JDChinaFileLoaderView ()
    {
        mFileName = "";
        mDbUrl = "";
        mDbUser = "";
        mDbPassword = "";
    }

    /**
     * Constructor. 
     */
    public JDChinaFileLoaderView(String parm1, byte[] parm2, String parm3, String parm4, String parm5)
    {
        mFileName = parm1;
        mFileContent = parm2;
        mDbUrl = parm3;
        mDbUser = parm4;
        mDbPassword = parm5;
        
    }

    /**
     * Creates a new JDChinaFileLoaderView
     *
     * @return
     *  Newly initialized JDChinaFileLoaderView object.
     */
    public static JDChinaFileLoaderView createValue () 
    {
        JDChinaFileLoaderView valueView = new JDChinaFileLoaderView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this JDChinaFileLoaderView object
     */
    public String toString()
    {
        return "[" + "FileName=" + mFileName + ", FileContent=" + mFileContent + ", DbUrl=" + mDbUrl + ", DbUser=" + mDbUser + ", DbPassword=" + mDbPassword + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("JDChinaFileLoader");
	root.setAttribute("Id", String.valueOf(mFileName));

	Element node;

        node = doc.createElement("FileContent");
        node.appendChild(doc.createTextNode(String.valueOf(mFileContent)));
        root.appendChild(node);

        node = doc.createElement("DbUrl");
        node.appendChild(doc.createTextNode(String.valueOf(mDbUrl)));
        root.appendChild(node);

        node = doc.createElement("DbUser");
        node.appendChild(doc.createTextNode(String.valueOf(mDbUser)));
        root.appendChild(node);

        node = doc.createElement("DbPassword");
        node.appendChild(doc.createTextNode(String.valueOf(mDbPassword)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public JDChinaFileLoaderView copy()  {
      JDChinaFileLoaderView obj = new JDChinaFileLoaderView();
      obj.setFileName(mFileName);
      obj.setFileContent(mFileContent);
      obj.setDbUrl(mDbUrl);
      obj.setDbUser(mDbUser);
      obj.setDbPassword(mDbPassword);
      
      return obj;
    }

    
    /**
     * Sets the FileName property.
     *
     * @param pFileName
     *  String to use to update the property.
     */
    public void setFileName(String pFileName){
        this.mFileName = pFileName;
    }
    /**
     * Retrieves the FileName property.
     *
     * @return
     *  String containing the FileName property.
     */
    public String getFileName(){
        return mFileName;
    }


    /**
     * Sets the FileContent property.
     *
     * @param pFileContent
     *  byte[] to use to update the property.
     */
    public void setFileContent(byte[] pFileContent){
        this.mFileContent = pFileContent;
    }
    /**
     * Retrieves the FileContent property.
     *
     * @return
     *  byte[] containing the FileContent property.
     */
    public byte[] getFileContent(){
        return mFileContent;
    }


    /**
     * Sets the DbUrl property.
     *
     * @param pDbUrl
     *  String to use to update the property.
     */
    public void setDbUrl(String pDbUrl){
        this.mDbUrl = pDbUrl;
    }
    /**
     * Retrieves the DbUrl property.
     *
     * @return
     *  String containing the DbUrl property.
     */
    public String getDbUrl(){
        return mDbUrl;
    }


    /**
     * Sets the DbUser property.
     *
     * @param pDbUser
     *  String to use to update the property.
     */
    public void setDbUser(String pDbUser){
        this.mDbUser = pDbUser;
    }
    /**
     * Retrieves the DbUser property.
     *
     * @return
     *  String containing the DbUser property.
     */
    public String getDbUser(){
        return mDbUser;
    }


    /**
     * Sets the DbPassword property.
     *
     * @param pDbPassword
     *  String to use to update the property.
     */
    public void setDbPassword(String pDbPassword){
        this.mDbPassword = pDbPassword;
    }
    /**
     * Retrieves the DbPassword property.
     *
     * @return
     *  String containing the DbPassword property.
     */
    public String getDbPassword(){
        return mDbPassword;
    }


    
}
