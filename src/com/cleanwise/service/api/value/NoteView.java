
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NoteView
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

import java.math.BigDecimal;import java.util.Date;import java.util.ArrayList;


/**
 * <code>NoteView</code> is a ViewObject class for UI.
 */
public class NoteView
extends ValueObject
{
   
    private static final long serialVersionUID = 7243733369321182771L;
    private int mNoteId;
    private int mPropertyId;
    private int mBusEntityId;
    private String mTopic;
    private String mTitle;
    private Date mModDate;
    private int mSearchRate;
    private ArrayList mKeyWords;

    /**
     * Constructor.
     */
    public NoteView ()
    {
        mTopic = "";
        mTitle = "";
    }

    /**
     * Constructor. 
     */
    public NoteView(int parm1, int parm2, int parm3, String parm4, String parm5, Date parm6, int parm7, ArrayList parm8)
    {
        mNoteId = parm1;
        mPropertyId = parm2;
        mBusEntityId = parm3;
        mTopic = parm4;
        mTitle = parm5;
        mModDate = parm6;
        mSearchRate = parm7;
        mKeyWords = parm8;
        
    }

    /**
     * Creates a new NoteView
     *
     * @return
     *  Newly initialized NoteView object.
     */
    public static NoteView createValue () 
    {
        NoteView valueView = new NoteView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NoteView object
     */
    public String toString()
    {
        return "[" + "NoteId=" + mNoteId + ", PropertyId=" + mPropertyId + ", BusEntityId=" + mBusEntityId + ", Topic=" + mTopic + ", Title=" + mTitle + ", ModDate=" + mModDate + ", SearchRate=" + mSearchRate + ", KeyWords=" + mKeyWords + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Note");
	root.setAttribute("Id", String.valueOf(mNoteId));

	Element node;

        node = doc.createElement("PropertyId");
        node.appendChild(doc.createTextNode(String.valueOf(mPropertyId)));
        root.appendChild(node);

        node = doc.createElement("BusEntityId");
        node.appendChild(doc.createTextNode(String.valueOf(mBusEntityId)));
        root.appendChild(node);

        node = doc.createElement("Topic");
        node.appendChild(doc.createTextNode(String.valueOf(mTopic)));
        root.appendChild(node);

        node = doc.createElement("Title");
        node.appendChild(doc.createTextNode(String.valueOf(mTitle)));
        root.appendChild(node);

        node = doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node = doc.createElement("SearchRate");
        node.appendChild(doc.createTextNode(String.valueOf(mSearchRate)));
        root.appendChild(node);

        node = doc.createElement("KeyWords");
        node.appendChild(doc.createTextNode(String.valueOf(mKeyWords)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public NoteView copy()  {
      NoteView obj = new NoteView();
      obj.setNoteId(mNoteId);
      obj.setPropertyId(mPropertyId);
      obj.setBusEntityId(mBusEntityId);
      obj.setTopic(mTopic);
      obj.setTitle(mTitle);
      obj.setModDate(mModDate);
      obj.setSearchRate(mSearchRate);
      obj.setKeyWords(mKeyWords);
      
      return obj;
    }

    
    /**
     * Sets the NoteId property.
     *
     * @param pNoteId
     *  int to use to update the property.
     */
    public void setNoteId(int pNoteId){
        this.mNoteId = pNoteId;
    }
    /**
     * Retrieves the NoteId property.
     *
     * @return
     *  int containing the NoteId property.
     */
    public int getNoteId(){
        return mNoteId;
    }


    /**
     * Sets the PropertyId property.
     *
     * @param pPropertyId
     *  int to use to update the property.
     */
    public void setPropertyId(int pPropertyId){
        this.mPropertyId = pPropertyId;
    }
    /**
     * Retrieves the PropertyId property.
     *
     * @return
     *  int containing the PropertyId property.
     */
    public int getPropertyId(){
        return mPropertyId;
    }


    /**
     * Sets the BusEntityId property.
     *
     * @param pBusEntityId
     *  int to use to update the property.
     */
    public void setBusEntityId(int pBusEntityId){
        this.mBusEntityId = pBusEntityId;
    }
    /**
     * Retrieves the BusEntityId property.
     *
     * @return
     *  int containing the BusEntityId property.
     */
    public int getBusEntityId(){
        return mBusEntityId;
    }


    /**
     * Sets the Topic property.
     *
     * @param pTopic
     *  String to use to update the property.
     */
    public void setTopic(String pTopic){
        this.mTopic = pTopic;
    }
    /**
     * Retrieves the Topic property.
     *
     * @return
     *  String containing the Topic property.
     */
    public String getTopic(){
        return mTopic;
    }


    /**
     * Sets the Title property.
     *
     * @param pTitle
     *  String to use to update the property.
     */
    public void setTitle(String pTitle){
        this.mTitle = pTitle;
    }
    /**
     * Retrieves the Title property.
     *
     * @return
     *  String containing the Title property.
     */
    public String getTitle(){
        return mTitle;
    }


    /**
     * Sets the ModDate property.
     *
     * @param pModDate
     *  Date to use to update the property.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
    }
    /**
     * Retrieves the ModDate property.
     *
     * @return
     *  Date containing the ModDate property.
     */
    public Date getModDate(){
        return mModDate;
    }


    /**
     * Sets the SearchRate property.
     *
     * @param pSearchRate
     *  int to use to update the property.
     */
    public void setSearchRate(int pSearchRate){
        this.mSearchRate = pSearchRate;
    }
    /**
     * Retrieves the SearchRate property.
     *
     * @return
     *  int containing the SearchRate property.
     */
    public int getSearchRate(){
        return mSearchRate;
    }


    /**
     * Sets the KeyWords property.
     *
     * @param pKeyWords
     *  ArrayList to use to update the property.
     */
    public void setKeyWords(ArrayList pKeyWords){
        this.mKeyWords = pKeyWords;
    }
    /**
     * Retrieves the KeyWords property.
     *
     * @return
     *  ArrayList containing the KeyWords property.
     */
    public ArrayList getKeyWords(){
        return mKeyWords;
    }


    
}
