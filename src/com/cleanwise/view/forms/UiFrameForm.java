package com.cleanwise.view.forms;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.DateFormat;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AssetViewVector;
import com.cleanwise.service.api.value.ItemCatalogAggrView;
import com.cleanwise.service.api.value.ItemCatalogAggrViewVector;
import com.cleanwise.service.api.value.NoteAttachmentDataVector;
import com.cleanwise.service.api.value.NoteJoinView;
import com.cleanwise.service.api.value.NoteTextData;
import com.cleanwise.service.api.value.NoteViewVector;
import com.cleanwise.service.api.value.NoteJoinViewVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.UiFrameData;
import com.cleanwise.service.api.value.UiFrameDataVector;
import com.cleanwise.service.api.value.UiFrameViewVector;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;


/**
 * Title:        UiFrameForm
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 */
public class UiFrameForm extends ActionForm {

  private UiFrameViewVector frames = new UiFrameViewVector();
  private UiFrameViewVector templates = new UiFrameViewVector();
  private int selectedTemplateId;
  private int selectedFrameId;
  private String mode = "viewFrames";
  private String mode2 = "viewArticles";

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
  private String mNewsEffDate = null;
  private String mNewsExpDate = null;
  private boolean mForcedR = false;   // Forced Read
  private boolean mForcedEachLogin = false;
  private String dispatch = "";

  private NoteJoinViewVector noteJoinViewVector = new NoteJoinViewVector();
  private int noteTopicId;
  private String noteTopicName;
  private PropertyData noteTopicData;
  private String confirmMessage = null;
  private String localeCd;
  
  public String getConfirmMessage() {
      return confirmMessage;
  }

  public void setConfirmMessage(String confirmMessage) {
      this.confirmMessage = confirmMessage;
  }

  public UiFrameViewVector getFrames() {
    return frames;
  }

  public void setFrames(UiFrameViewVector v) {
    frames = v;
  }

  public UiFrameViewVector getTemplates() {
    return templates;
  }

  public void setTemplates(UiFrameViewVector v) {
    templates = v;
  }

  public int getSelectedTemplateId() {
    return selectedTemplateId;
  }

  public void setSelectedTemplateId(int v) {
    selectedTemplateId = v;
  }

  public int getSelectedFrameId() {
    return selectedFrameId;
  }

  public void setSelectedFrameId(int v) {
    selectedFrameId = v;
  }

  public String getMode() {
      return mode;
  }

  public void setMode(String v) {
    mode = v;
  }

    /**
     * @return Returns the mBusEntityId.
     */
    public int getBusEntityId() {
        return mBusEntityId;
    }

    /**
     * @param busEntityId The mBusEntityId to set.
     */
    public void setBusEntityId(int busEntityId) {
        mBusEntityId = busEntityId;
    }

    /**
     * @return Returns the mBusEntityName.
     */
    public String getBusEntityName() {
        return mBusEntityName;
    }

    /**
     * @param busEntityName The mBusEntityName to set.
     */
    public void setBusEntityName(String busEntityName) {
        mBusEntityName = busEntityName;
    }

    /**
     * @return Returns the mTopicId.
     */
    public int getTopicId() {
        return mTopicId;
    }

    /**
     * @param topicId The mTopicId to set.
     */
    public void setTopicId(int topicId) {
        mTopicId = topicId;
    }

    /**
     * @return Returns the mTopicName.
     */
    public String getTopicName() {
        return mTopicName;
    }

    /**
     * @param topicName The mTopicName to set.
     */
    public void setTopicName(String topicName) {
        mTopicName = topicName;
    }

    public void setSelectFl(int id, boolean pVal) {
        NoteJoinView noteJoinVw = getNoteJoin(id);
        if(noteJoinVw==null) return;
        noteJoinVw.setSelectFl(pVal);
    }

    public boolean getSelectFl(int id) {
        NoteJoinView noteJoinVw = getNoteJoin(id);
        if(noteJoinVw==null) return false;
        return noteJoinVw.getSelectFl();
    }

    private NoteJoinView getNoteJoin(int id) {
        if(noteJoinViewVector==null) return null;
        if(id<0 || id>noteJoinViewVector.size()) return null;
        return (NoteJoinView) noteJoinViewVector.get(id);
    }


    public void setNoteJoinViewVector(NoteJoinViewVector noteJoinVwVector) {
        noteJoinViewVector = noteJoinVwVector;
    }

    /**
     * @return Returns the mode2.
     */
    public String getMode2() {
        return mode2;
    }

    public NoteJoinViewVector getNoteJoinViewVector() {
        return noteJoinViewVector;
    }

    /**
     * @param mode2 The mode2 to set.
     */
    public void setMode2(String mode2) {
        this.mode2 = mode2;
    }

    /**
     * @return Returns the mTopics.
     */
    public PropertyDataVector getTopics() {
        return mTopics;
    }

    /**
     * @param topics The mTopics to set.
     */
    public void setTopics(PropertyDataVector topics) {
        mTopics = topics;
    }

    public String getNewsEffDate() {
        if (mNote == null || (mNote.getNote() == null)) {
            return "  /  /    ";
        }
        java.util.Date d = mNote.getNote().getEffDate();
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

//    public String getEffDate() {
//        if (mNote == null || (mNote.getNote() == null)) {
//            return " ";
//        }
//        java.util.Date d = mNote.getNote().getEffDate();
//        if ( null == d  ) {
//            return " ";
//        }
//
//        DateFormat medium = DateFormat.getDateInstance(DateFormat.MEDIUM);
//        return medium.format(d);
//    }

    public void setNewsEffDate(String effDate) {
        if(Utility.isSet(effDate)){
            Date efDate = Utility.parseDate(effDate);
            mNote.getNote().setEffDate(efDate);
        } else {
            mNote.getNote().setEffDate(null);
        }
        mNewsEffDate = effDate;
    }

    public String getNewsExpDate() {
        if (mNote == null || (mNote.getNote() == null)) {
            return "  /  /    ";
        }
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

    public void setNewsExpDate(String expDate) {
        if(Utility.isSet(expDate)){
            Date exDate = Utility.parseDate(expDate);
            mNote.getNote().setExpDate(exDate);
        } else {
            mNote.getNote().setExpDate(null);
        }
        mNewsExpDate = expDate;
    }

    /**
     * @return Returns the mNote.
     */
    public NoteJoinView getNote() {
        return mNote;
    }

    /**
     * @param note The mNote to set.
     */
    public void setNote(NoteJoinView note) {
        mNote = note;
    }

    /**
     * @return Returns the fR.
     */
    public boolean getForcedR() {
        if (mNote == null || (mNote.getNote() == null)) {
            return false;
        }
        int countr = mNote.getNote().getCounter();
        if (countr > 0) {
            mForcedR = true;
        } else {
            mForcedR = false;
        }
        return mForcedR;
    }

    /**
     * @param fr The fR to set.
     */
    public void setForcedR(boolean fr) {
        mForcedR = fr;
        int countr = 0;
        if (fr) countr = 1;
        if (mNote != null && (mNote.getNote() != null)) {
            mNote.getNote().setCounter(countr);
        }
    }
    
    /**
     * @return Returns the mForcedEachLogin
     */
    public boolean getForcedEachLogin() {
        if (mNote == null || (mNote.getNote() == null)) {
            return false;
        }

        String forcedEachLogin = mNote.getNote().getForcedEachLogin();
        if(Utility.isTrue(forcedEachLogin)){
        	mForcedEachLogin = true;
        }else{
        	mForcedEachLogin = false;
        }
        
        return mForcedEachLogin;
    }

    /**
     * @param fr The mForcedEachLogin to set.
     */
    public void setForcedEachLogin(boolean fr) {
    	mForcedEachLogin = fr;
        String forcedEachLogin = "false";
        if(mForcedEachLogin){
        	forcedEachLogin = "true";
        }
        if (mNote != null && (mNote.getNote() != null)) {
            mNote.getNote().setForcedEachLogin(forcedEachLogin);
            if(Utility.isTrue(forcedEachLogin)){
            	mNote.getNote().setCounter(1);
            }
        }
    }

    /**
     * @return Returns the mParagraph.
     */
    public NoteTextData getParagraph() {
//        if (this.mParagraph == null ) {
//            return new NoteTextData();
//        }
        return mParagraph;
    }

    /**
     * @param paragraph The mParagraph to set.
     */
    public void setParagraph(NoteTextData paragraph) {
        mParagraph = paragraph;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // create a new detail object and convert nulls to empty strings
        setForcedR(false);
        setForcedEachLogin(false);
        mSelectorBox = new int[0];
        dispatch = "";
        confirmMessage = null;
    }

    /**
     * @return Returns the mSelectorBox.
     */
    public int[] getSelectorBox() {
        return mSelectorBox;
    }

    /**
     * @param selectorBox The mSelectorBox to set.
     */
    public void setSelectorBox(int[] selectorBox) {
        mSelectorBox = selectorBox;
    }

    /**
     * @return Returns the dispatch.
     */
    public String getDispatch() {
        return dispatch;
    }

  public int getNoteTopicId() {
    return noteTopicId;
  }

  public String getNoteTopicName() {
    return noteTopicName;
  }

  public PropertyData getNoteTopicData() {
    return noteTopicData;
  }

  /**
     * @param dispatch The dispatch to set.
     */
    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

  public void setNoteTopicId(int noteTopicId) {
    this.noteTopicId = noteTopicId;
  }

  public void setNoteTopicName(String noteTopicName) {
    this.noteTopicName = noteTopicName;
  }

  public void setNoteTopicData(PropertyData noteTopicData) {
    this.noteTopicData = noteTopicData;
  }

    public String getLocaleCd() {
        return localeCd;
    }

    public void setLocaleCd(String localeCd) {
        this.localeCd = localeCd;
    }
}
