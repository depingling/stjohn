package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;

public class Ack997Data extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -5751937383264912583L;
  //-------------------------------------
  // properties
  //-------------------------------------
  // property groupSender
  String mGroupSender;
  public String getGroupSender () {
    return mGroupSender;
  }
  public void setGroupSender (String pGroupSender) {
    if (mGroupSender != pGroupSender) {
      mGroupSender = pGroupSender;
    }
  }

  // property GroupReceiver
  String mGroupReceiver;
  public String getGroupReceiver () {
    return mGroupReceiver;
  }
  public void setGroupReceiver (String pGroupReceiver) {
    if (mGroupReceiver != pGroupReceiver) {
      mGroupReceiver = pGroupReceiver;
    }
  }

  // property errorLevel
  String mErrorLevel;
  public String getErrorLevel () {
    return mErrorLevel;
  }
  public void setErrorLevel (String pErrorLevel) {
    if (mErrorLevel != pErrorLevel) {
      mErrorLevel = pErrorLevel;
    }
  }

  // property groupType
  String mGroupType;
  public String getGroupType () {
    return mGroupType;
  }
  public void setGroupType (String pGroupType) {
    if (mGroupType != pGroupType) {
      mGroupType = pGroupType;
    }
  }

  // property setType
  String mSetType;
  public String getSetType () {
    return mSetType;
  }
  public void setSetType (String pSetType) {
    if (mSetType != pSetType) {
      mSetType = pSetType;
    }
  }

  // property groupControlNumber
  int mGroupControlNumber;
  public int getGroupControlNumber () {
    return mGroupControlNumber;
  }
  public void setGroupControlNumber (int pGroupControlNumber) {
    if (mGroupControlNumber != pGroupControlNumber) {
      mGroupControlNumber = pGroupControlNumber;
    }
  }

  // property setControlNumber
  int mSetControlNumber;
  public int getSetControlNumber () {
    return mSetControlNumber;
  }
  public void setSetControlNumber (int pSetControlNumber) {
    if (mSetControlNumber != pSetControlNumber) {
      mSetControlNumber = pSetControlNumber;
    }
  }

  // property ackCode
  String mAckCode;
  public String getAckCode () {
    return mAckCode;
  }
  public void setAckCode (String pAckCode) {
    if (mAckCode != pAckCode) {
      mAckCode = pAckCode;
    }
  }

  // property ackError
  String mAckError;
  public String getAckError () {
    return mAckError;
  }
  public void setAckError (String pAckError) {
    if (mAckError != pAckError) {
      mAckError = pAckError;
    }
  }

    /**
     * Creates a new Ack997Data
     *
     * @return
     *  Newly initialized Ack997Data object.
     */
    public static Ack997Data createValue ()
    {
        Ack997Data valueData = new Ack997Data();

        return valueData;
    }

  //-------------------------------------
  // toString method
  //-------------------------------------
  public String toString ()
  {
    StringBuffer buf = new StringBuffer ();
    buf.append (getClass ().getName ());
    buf.append ("(");
    buf.append ("groupSender: " + getGroupSender() + "  ");
    buf.append ("groupReceiver: " + getGroupReceiver() + "  ");
    buf.append ("errLevel: " + getErrorLevel() + "  ");
    buf.append ("groupType: " + getGroupType() + "  ");
    buf.append ("setType: " + getSetType() + "  ");
    buf.append ("groupControlNumber: " + getGroupControlNumber() + "  ");
    buf.append ("setControlNumber: " + getSetControlNumber() + "  ");
    buf.append ("ackCode: " + getAckCode() + "  ");
    buf.append ("ackErr: " + getAckError() + "  ");
    buf.append (")");
    return buf.toString ();
  }
}
