/**
 * Title:        NoteMgrForm
 * Description:  This is the Struts ActionForm class for the note pages.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.*;

import javax.servlet.http.HttpSession;
import com.cleanwise.service.api.*;
import com.cleanwise.view.utils.*;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.DataNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


/**
 * 
 */
public final class NoteMgrForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables
    private boolean mErrorFl = false;
    private int mBusEntityId = 0;
    private String mBusEntityName = "";
    private int mTopicId = 0;
    private String mTopicName = "";
    private PropertyDataVector mTopics = new PropertyDataVector();
    private PropertyData mTopicToEdit = null;
    private NoteViewVector mNoteList = null;
    private NoteJoinView mNote = null;
    private NoteTextData  mParagraph = null;
    private String mKeyWord = "";
    private boolean mTextAlsoFl = false;
    private FormFile mAttachFile;
    private NoteAttachmentDataVector mAttachedFileList = new NoteAttachmentDataVector();
        
    private int[] mSelectorBox = new int[0];
    private String[] mAttachSelectBox = new String[0];
    private Date mEffDate = null;
    private Date mExpDate = null;   

  // ---------------------------------------------------------------- Properties

    public void setBusEntityId(int pVal){mBusEntityId = pVal;}
    public int getBusEntityId(){return mBusEntityId;}

    public void setBusEntityName(String pVal) {mBusEntityName = pVal;}
    public String getBusEntityName(){return mBusEntityName;}

    public void setTopicId(int pVal){mTopicId = pVal;}
    public int getTopicId(){return mTopicId;}

    public void setTopicName(String pVal) {mTopicName = pVal;}
    public String getTopicName(){return mTopicName;}

    public void setTopics(PropertyDataVector pVal){mTopics = pVal;}
    public PropertyDataVector getTopics(){return mTopics;}

    public void setTopicToEdit(PropertyData pVal){mTopicToEdit = pVal;}
    public PropertyData getTopicToEdit(){return mTopicToEdit;}
    
    public void setNoteList(NoteViewVector pVal){mNoteList = pVal;}
    public NoteViewVector getNoteList(){return mNoteList;}
    
    public void setNote(NoteJoinView pVal){mNote = pVal;}
    public NoteJoinView getNote(){return mNote;}
    
    public String getEffDate() {
        java.util.Date d = mNote.getNote().getEffDate();
        if ( null == d  ) {
        	return "  /  /    ";
//            d = new java.util.Date();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String dstring = "" +
                (calendar.get(Calendar.MONTH) + 1)  + "/" +
                calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                calendar.get(Calendar.YEAR );
        return dstring;    	
    }
    
    public String getExpDate() {
        java.util.Date d = mNote.getNote().getExpDate();
        if ( null == d  ) {
        	return "  /  /    ";
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(d);
        String dstring = "" +
                (calendar.get(Calendar.MONTH) + 1)  + "/" +
                calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                calendar.get(Calendar.YEAR );
        return dstring;    	
    }

    public void setEffDate(String eDVal) {
    	DateFormat mDateFormat = (new SimpleDateFormat("MM/dd/yyyy"));
    	Date effectiveDate = null;
		try {
			effectiveDate = mDateFormat.parse(eDVal);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		NoteData currentNote = mNote.getNote();
		currentNote.setEffDate(effectiveDate);
		mNote.setNote(currentNote);
    	mEffDate = effectiveDate;
    }
    
    public void setExpDate(String eDVal) {
    	DateFormat mDateFormat = (new SimpleDateFormat("MM/dd/yyyy"));
    	Date expirationDate = null;
		try {
			expirationDate = mDateFormat.parse(eDVal);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		NoteData currentNote = mNote.getNote();
		currentNote.setExpDate(expirationDate);
		mNote.setNote(currentNote);
    	mExpDate = expirationDate;
    }
    
    public void setParagraph(NoteTextData pVal){mParagraph = pVal;}
    public NoteTextData getParagraph(){return mParagraph;}

    public void setKeyWord(String pVal) {
      mKeyWord=pVal;
   }
    public String getKeyWord(){return mKeyWord;}

    
    public void setTextAlsoFl(boolean pVal) {mTextAlsoFl=pVal;}
    public boolean getTextAlsoFl(){return mTextAlsoFl;}
    
    public void setAttachFile(FormFile pVal) {
      mAttachFile = (FormFile)pVal;
    }
    public FormFile getAttachFile(){return mAttachFile;}
   
    public void setAttachedFileList(NoteAttachmentDataVector pVal) 
                                      {mAttachedFileList=pVal;}
    public NoteAttachmentDataVector getAttachedFileList()
                                     {return mAttachedFileList;}
   
    public void setErrorFl(boolean pVal) {mErrorFl=pVal;}
    public boolean getErrorFl(){return mErrorFl;}
    
    public void setSelectorBox(int[] pVal) {mSelectorBox=pVal;}
    public int[] getSelectorBox(){return mSelectorBox;}
    
    public void setAttachSelectBox(String[] pVal) {mAttachSelectBox=pVal;}
    public String[] getAttachSelectBox(){return mAttachSelectBox;}

    public void reset(ActionMapping mapping, HttpServletRequest request) {
    // create a new detail object and convert nulls to empty strings
     mTextAlsoFl = false;
     mSelectorBox = new int[0];
     mAttachSelectBox = new String[0];
  }


  /**
   * So far nothing to validate
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request)
  {
    return null;
  }

}
