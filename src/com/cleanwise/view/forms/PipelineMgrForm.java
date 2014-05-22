/**
 * Title:        PipelineMgrForm
 * Description:  This is the Struts ActionForm class to 
 * administrate CLW_PIPELINE table
 * Purpose:      Create, modify and delete pipeline entries
 * Copyright:    Copyright (c) 2005
 * Company:      CleanWise, Inc.
 * @author       YKupershmidt
 */

package com.cleanwise.view.forms;

import java.util.Date;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

public final class PipelineMgrForm extends ActionForm {
    private String mAction = null;
    private PipelineDataVector mPipelineRecords = null;
    private PipelineDataVector mSelectedRecords = null;
    private RefCdDataVector mPipelineTypes = null;
    private String mPipelineTypeFilter = "";
    private boolean mActiveOnlyFilter = false;
    private int[] mSelected = new int[0];

    public String getAction(){return mAction;}
    public void setAction(String pValue) {mAction = pValue;}

    public PipelineDataVector getPipelineRecords(){return mPipelineRecords;}
    public void setPipelineRecords(PipelineDataVector pValue) {mPipelineRecords = pValue;}
    
    public int getPipelineOrder(int ind){
     return ((PipelineData) mPipelineRecords.get(ind)).getPipelineOrder();
    }
    public void setPipelineOrder(int ind, int pValue) {
      ((PipelineData) mPipelineRecords.get(ind)).setPipelineOrder(pValue);    
    }

    public String getClassname(int ind){
     return ((PipelineData) mPipelineRecords.get(ind)).getClassname();
    }
    public void setClassname(int ind, String pValue) {
      ((PipelineData) mPipelineRecords.get(ind)).setClassname(pValue);    
    }

    public int getBusEntityId(int ind){
     return ((PipelineData) mPipelineRecords.get(ind)).getBusEntityId();
    }
    public void setBusEntityId(int ind, int pValue) {
      ((PipelineData) mPipelineRecords.get(ind)).setBusEntityId(pValue);    
    }

    public String getPipelineStatusCd(int ind){
     return ((PipelineData) mPipelineRecords.get(ind)).getPipelineStatusCd();
    }
    public void setPipelineStatusCd(int ind, String pValue) {
      ((PipelineData) mPipelineRecords.get(ind)).setPipelineStatusCd(pValue);    
    }

    
    
    public PipelineDataVector getSelectedRecords(){return mSelectedRecords;}
    public void setSelectedRecords(PipelineDataVector pValue) {mSelectedRecords = pValue;}

    public RefCdDataVector getPipelineTypes(){return mPipelineTypes;}
    public void setPipelineTypes(RefCdDataVector pValue) {mPipelineTypes = pValue;}

    public String getPipelineTypeFilter(){return mPipelineTypeFilter;}
    public void setPipelineTypeFilter(String pValue) {mPipelineTypeFilter = pValue;}

    public boolean  getActiveOnlyFilter(){return mActiveOnlyFilter;}
    public void setActiveOnlyFilter(boolean  pValue) {mActiveOnlyFilter = pValue;}

    public int[] getSelected(){return mSelected;}
    public void setSelected(int[] pValue) {mSelected = pValue;}

    /*
    public %% get**(){return m**;}
    public void set**(%% pValue) {m** = pValue;}
    */
    
   /**
     * <code>reset</code> method
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      mSelected = new int[0];
    }

    /**
     * <code>validate</code> method is a stub.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     * @return an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request) {
        
        // Perform the run validation.
        ActionErrors errors = new ActionErrors();
        return errors;
    }

    
}


