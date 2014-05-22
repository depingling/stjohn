
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        NoteJoinView
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
 * <code>NoteJoinView</code> is a ViewObject class for UI.
 */
public class NoteJoinView
extends ValueObject
{
   
    private static final long serialVersionUID = 394305328280699200L;
    private NoteData mNote;
    private NoteTextDataVector mNoteText;
    private NoteAttachmentDataVector mNoteAttachment;
    private boolean mSelectFl;
    

    /**
     * Constructor.
     */
    public NoteJoinView ()
    {
    }

    /**
     * Constructor. 
     */
    public NoteJoinView(NoteData parm1, NoteTextDataVector parm2, NoteAttachmentDataVector parm3)
    {
        mNote = parm1;
        mNoteText = parm2;
        mNoteAttachment = parm3;
        
    }

    /**
     * Creates a new NoteJoinView
     *
     * @return
     *  Newly initialized NoteJoinView object.
     */
    public static NoteJoinView createValue () 
    {
        NoteJoinView valueView = new NoteJoinView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this NoteJoinView object
     */
    public String toString()
    {
        return "[" + "Note=" + mNote + ", NoteText=" + mNoteText + ", NoteAttachment=" + mNoteAttachment + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("NoteJoin");
	root.setAttribute("Id", String.valueOf(mNote));

	Element node;

        node = doc.createElement("NoteText");
        node.appendChild(doc.createTextNode(String.valueOf(mNoteText)));
        root.appendChild(node);

        node = doc.createElement("NoteAttachment");
        node.appendChild(doc.createTextNode(String.valueOf(mNoteAttachment)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public NoteJoinView copy()  {
      NoteJoinView obj = new NoteJoinView();
      obj.setNote(mNote);
      obj.setNoteText(mNoteText);
      obj.setNoteAttachment(mNoteAttachment);
      
      return obj;
    }

    
    /**
     * Sets the Note property.
     *
     * @param pNote
     *  NoteData to use to update the property.
     */
    public void setNote(NoteData pNote){
        this.mNote = pNote;
    }
    /**
     * Retrieves the Note property.
     *
     * @return
     *  NoteData containing the Note property.
     */
    public NoteData getNote(){
        return mNote;
    }


    /**
     * Sets the NoteText property.
     *
     * @param pNoteText
     *  NoteTextDataVector to use to update the property.
     */
    public void setNoteText(NoteTextDataVector pNoteText){
        this.mNoteText = pNoteText;
    }
    /**
     * Retrieves the NoteText property.
     *
     * @return
     *  NoteTextDataVector containing the NoteText property.
     */
    public NoteTextDataVector getNoteText(){
        return mNoteText;
    }


    /**
     * Sets the NoteAttachment property.
     *
     * @param pNoteAttachment
     *  NoteAttachmentDataVector to use to update the property.
     */
    public void setNoteAttachment(NoteAttachmentDataVector pNoteAttachment){
        this.mNoteAttachment = pNoteAttachment;
    }
    /**
     * Retrieves the NoteAttachment property.
     *
     * @return
     *  NoteAttachmentDataVector containing the NoteAttachment property.
     */
    public NoteAttachmentDataVector getNoteAttachment(){
        return mNoteAttachment;
    }


         /**
          * @return Returns the mSelectFl.
          */
         public boolean getSelectFl() {
             return mSelectFl;
         }

         /**
          * @param selectFl The mSelectFl to set.
          */
         public void setSelectFl(boolean selectFl) {
             mSelectFl = selectFl;
         }

}
