package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OrderData;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Title:        MultiOrderPipelineBaton
 * Description:  Container object for MultiOrderPipelineBaton objects
 * Purpose:      Provides container storage for MultiOrderPipelineBaton objects.
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date: 20.11.2006
 * Time: 14:26:52
 * @author       Alexander Chickin, TrinitySoft, Inc.
 */
public class MultiOrderPipelineBaton extends java.util.ArrayList implements Serializable {
  //Return codes
  public static final String STOP = "STOP_AND_RETURN";
  public static final String GO_NEXT = "GO_NEXT";
  public static final String REPEAT = "REPEAT";
  public static final String GO_FIRST_STEP = "GO_FIRST_STEP";
  public static final String GO_BREAK_POINT = "GO_BREAK_POINT";


  private OrderDataVector mOrderDataVector = null;
  private String mPipelineTypeCd=null;
  private String mUserName="UNKNOWN";
  private boolean mBypassOptional=false;
  private int mStepNum=0;
  private String mWhatNext=null;
  private Hashtable parametrs=null;


    public OrderDataVector getOrderDataVector(){  return mOrderDataVector;  }
    public void setOrderDataVector(OrderDataVector val){
	mOrderDataVector= val;
   }


    public String getPipelineTypeCd() { return this.mPipelineTypeCd; }
    public void setPipelineTypeCd(String pipelineTypeCd) { this.mPipelineTypeCd = pipelineTypeCd; }

    public String getUserName() { return mUserName; }
    public void setUserName(String userName) {this.mUserName = userName;}

    public boolean getBypassOptional() { return this.mBypassOptional;}
    public void setBypassOptional(boolean bypassOptional) {this.mBypassOptional = bypassOptional;}

    public void setStepNum(int stepNum) {  this.mStepNum = stepNum; }
    public int getStepNum() { return this.mStepNum ; }

    public String getWhatNext(){return mWhatNext;}
    public void setWhatNext(String val){mWhatNext = val;}

    public void setParametrs(Hashtable parametrs) { this.parametrs = parametrs;}
    public Hashtable getParametrs() { return parametrs; }
}
