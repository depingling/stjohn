package com.cleanwise.service.api.pipeline;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.PropertyUtil;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.APIAccess;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import com.cleanwise.service.api.util.PipelineUtil;
import java.util.HashSet;

/**
 * This class is the central location to store order error constantc
 * @author      Yuriy Kupershmidt
 *
 */
public class OrderPipelineBaton implements Serializable
{
  //Return codedes
  public static final String STOP = "STOP_AND_RETURN";
  public static final String GO_NEXT = "GO_NEXT";
  public static final String REPEAT = "REPEAT";
  public static final String GO_FIRST_STEP = "GO_FIRST_STEP";
  public static final String GO_BREAK_POINT = "GO_BREAK_POINT";

  private PipelineData mPipelineData = null;
  private int mStepNum = 0;
  private String mWhatNext = null;
  private int mBatonNumber = -1;
  private String mPipelineTypeCd = null;
  private String mInputType = null;  //EDI, UI
  private String mUserName = "UNKNOWN";
  private String mUserWorkflowRoleCd = "UNKNOWN";
  private Date mCurrentDate = null;
  private boolean mBypassOptional = false;
  private String mBypassWkflRuleActionCd = "";
  private OrderRequestData mOrderRequestData = null;
  private CustomerOrderChangeRequestData mCustomerOrderChangeRequestData = null;
  private PreOrderData mPreOrderData = null;
  private PreOrderAddressDataVector mPreOrderAddressDataVector = null;
  private PreOrderPropertyDataVector mPreOrderPropertyDataVector = null;
  private PreOrderMetaDataVector mPreOrderMetaDataVector = null;
  private PreOrderItemDataVector mPreOrderItemDataVector = null;
  private OrderData mOrderData = null;
  private OrderMetaDataVector mOrderMetaDataVector = null;
  private OrderPropertyDataVector mOrderPropertyDataVector = null;
  private OrderAddressData mBillToData = null;
  private OrderAddressData mShipToData = null;
  private OrderAddressData mCustShipToData = null;
  private OrderAddressData mCustBillToData = null;
  private OrderItemDataVector mOrderItemDataVector = null;
  private HashMap mOrderItemMeta = null;
  private WorkflowQueueDataVector mWorkflowQueueDataVector = null;
  private WorkflowRuleDataVector mWorkflowRuleDataVector = null;
  private boolean mWorkflowRuleFlag = false;
  private ArrayList mResultMessages = null;
  private boolean mSimpleServiceOrderFl=false;
  private boolean  mSimpleProductOrderFl=false;
  private boolean mPendingOrderEmailSent = false;

  private HashMap mCachedProperties = new HashMap();
  private String mFreightType = null;
  private HashSet mEmailReceiversForApproval = new HashSet();
  private HashMap mApproverGroupsHM = new HashMap();
  private HashMap mApproverGroupUsersHM = new HashMap();
  private HashSet mWorkflowRulesCleanedApproversHS = new HashSet();
  private CostCenterDataVector mCostCenterDV = null;

  public PipelineData getPipelineData(){return mPipelineData;}
  public void setPipelineData(PipelineData val){mPipelineData = val;}

  public int getStepNum(){return mStepNum;}
  public void setStepNum(int val){mStepNum = val;}

  public String getWhatNext(){return mWhatNext;}
  public void setWhatNext(String val){mWhatNext = val;}

  public int getBatonNumber(){return mBatonNumber;}
  public void setBatonNumber(int val){mBatonNumber = val;}

  public String getPipelineTypeCd(){return mPipelineTypeCd;}
  public void setPipelineTypeCd(String val){mPipelineTypeCd = val;}

  public String getInputType(){return mInputType;}
  public void setInputType(String val){mInputType = val;}

  public String getUserName(){return mUserName;}
  public void setUserName(String val){mUserName = val;}

  public String getUserWorkflowRoleCd(){return mUserWorkflowRoleCd;}
  public void setUserWorkflowRoleCd(String val){mUserWorkflowRoleCd = val;}

  public Date getCurrentDate(){return mCurrentDate;}
  public void setCurrentDate(Date val){mCurrentDate = val;}

  public boolean getBypassOptional(){return mBypassOptional;}
  public void setBypassOptional(boolean val){mBypassOptional = val;}

  public String getBypassWkflRuleActionCd(){return mBypassWkflRuleActionCd;}
  public void setBypassWkflRuleActionCd(String val){mBypassWkflRuleActionCd = val;}

  public OrderRequestData getOrderRequestData(){return mOrderRequestData;}
  public void setOrderRequestData(OrderRequestData val){mOrderRequestData = val;}

  public CustomerOrderChangeRequestData getCustomerOrderChangeRequestData(){return mCustomerOrderChangeRequestData;}
  public void setCustomerOrderChangeRequestData(CustomerOrderChangeRequestData val){
    setOrderData(val.getOrderData());
    setDoNoUpdateOrderStatusCd(val.isDoNotChangeOrderStatusCd());
    mCustomerOrderChangeRequestData = val;
  }

  private boolean doNoUpdateOrderStatusCd = false;
  private String startingOrderStatusCd=null;
  public void setDoNoUpdateOrderStatusCd(boolean value){
    doNoUpdateOrderStatusCd = true;
  }
  public boolean isDoNoUpdateOrderStatusCd(){
    return doNoUpdateOrderStatusCd;
  }

  public String getStartingOrderStatusCd(){
    return startingOrderStatusCd;
  }

  public PreOrderData getPreOrderData(){return mPreOrderData;}
  public void setPreOrderData(PreOrderData val){mPreOrderData = val;}

  public PreOrderAddressDataVector getPreOrderAddressDataVector()
                                            {return mPreOrderAddressDataVector;}
  public void setPreOrderAddressDataVector(PreOrderAddressDataVector val)
                                            {mPreOrderAddressDataVector = val;}

  public PreOrderPropertyDataVector getPreOrderPropertyDataVector()
                                           {return mPreOrderPropertyDataVector;}
  public void setPreOrderPropertyDataVector(PreOrderPropertyDataVector val)
                                            {mPreOrderPropertyDataVector = val;}

  public PreOrderMetaDataVector getPreOrderMetaDataVector(){return mPreOrderMetaDataVector;}
  public void setPreOrderMetaDataVector(PreOrderMetaDataVector val) {mPreOrderMetaDataVector = val;}

  public PreOrderItemDataVector getPreOrderItemDataVector()
                                           {return mPreOrderItemDataVector;}
  public void setPreOrderItemDataVector(PreOrderItemDataVector val)
                                            {mPreOrderItemDataVector = val;}

  public OrderData getOrderData(){return mOrderData;}
  public void setOrderData(OrderData val){
    if(mOrderData == null ){
      this.startingOrderStatusCd=val.getOrderStatusCd();
    }
    mOrderData = val;
  }

  public OrderMetaDataVector getOrderMetaDataVector(){return mOrderMetaDataVector;}
  public void setOrderMetaDataVector(OrderMetaDataVector val){mOrderMetaDataVector = val;}

  public OrderPropertyDataVector getOrderPropertyDataVector(){return mOrderPropertyDataVector;}
  public void setOrderPropertyDataVector(OrderPropertyDataVector val){mOrderPropertyDataVector = val;}
  public void addOrderPropertyData(OrderPropertyData val) {
    if(mOrderPropertyDataVector==null) {
      mOrderPropertyDataVector = new OrderPropertyDataVector();
    }
    mOrderPropertyDataVector.add(val);
  }

  public OrderAddressData getBillToData(){return mBillToData;}
  public void setBillToData(OrderAddressData val){mBillToData = val;}

  public OrderAddressData getShipToData(){return mShipToData;}
  public void setShipToData(OrderAddressData val){mShipToData = val;}

  public OrderAddressData getCustShipToData(){return mCustShipToData;}
  public void setCustShipToData(OrderAddressData val){mCustShipToData = val;}


  public OrderAddressData getCustBillToData(){return mCustBillToData;}
  public void setCustBillToData(OrderAddressData val){mCustBillToData = val;}

  public OrderItemDataVector getOrderItemDataVector(){return mOrderItemDataVector;}
  public void setOrderItemDataVector(OrderItemDataVector val){mOrderItemDataVector = val;}

  public HashMap getOrderItemMeta(){return mOrderItemMeta;}
  public void setOrderItemMeta(HashMap val){mOrderItemMeta = val;}
  public void addOrderItemMetaDataVector(int lineNum, OrderItemMetaDataVector val) {
    if(mOrderItemMeta==null) mOrderItemMeta = new HashMap();
    mOrderItemMeta.put(new Integer(lineNum),val);
  }

  public CostCenterDataVector getCostCenterDV(Connection pCon, APIAccess pFactory)
  throws Exception{
    if(mCostCenterDV == null) {
        Account actEjb = pFactory.getAccountAPI();
        OrderData oD = getOrderData();
        if(oD == null) {
			throw new Exception("order is not set");
        }
        if(oD.getAccountId() == 0) {
			throw new Exception("order account is not set");
        }
        mCostCenterDV = actEjb.getAllCostCenters(oD.getAccountId(), Account.ORDER_BY_ID);
	}
    return mCostCenterDV;
  }
  public void setCostCenterDV(CostCenterDataVector val){mCostCenterDV = val;}


  public OrderItemMetaDataVector getOrderItemMetaDataVector(int lineNum) {
    if(mOrderItemMeta==null) return null;
    return (OrderItemMetaDataVector) mOrderItemMeta.get(new Integer(lineNum));
  }
  public void removeItemMetaDataVector(int lineNum) {
    mOrderItemMeta.remove(new Integer(lineNum));
  }

  public WorkflowQueueDataVector getWorkflowQueueDataVector(){
    return mWorkflowQueueDataVector;
  }
  public void setWorkflowQueueDataVector(WorkflowQueueDataVector val){
    mWorkflowQueueDataVector = val;
  }
  public void addWorkflowQueueData(WorkflowQueueData val) {
    if(mWorkflowQueueDataVector==null) {
      mWorkflowQueueDataVector = new WorkflowQueueDataVector();
    }
    mWorkflowQueueDataVector.add(val);
  }

  // New by SVC
  public void addOrderMetaDataVector(OrderMetaDataVector val) {
	    if(mOrderMetaDataVector==null) {
	    	mOrderMetaDataVector = new OrderMetaDataVector();
	    }
	    mOrderMetaDataVector.addAll(val);
  }

  //Workflow rules
  public WorkflowRuleDataVector getWorkflowRuleDataVector(){
    return mWorkflowRuleDataVector;
  }
  public void setWorkflowRuleDataVector(WorkflowRuleDataVector val){
    mWorkflowRuleDataVector = val;
  }
  public WorkflowRuleDataVector getWorkflowRuleDataVector (String pRuleTypeCd) {
    WorkflowRuleDataVector wrDV = new WorkflowRuleDataVector();
    if(mWorkflowRuleDataVector==null || pRuleTypeCd==null) {
      return wrDV;
    }
    for(int ii=0; ii<mWorkflowRuleDataVector.size(); ii++) {
      WorkflowRuleData wrD = (WorkflowRuleData) mWorkflowRuleDataVector.get(ii);
      if(pRuleTypeCd.equals(wrD.getRuleTypeCd())) {
        wrDV.add(wrD);
      }
    }
    return wrDV;
  }

  public boolean getWorkflowRuleFlag(){
    return mWorkflowRuleFlag;
  }
  public void setWorkflowRuleFlag(boolean val){
    mWorkflowRuleFlag = val;
  }

  public void stopWorkflow() {
    mWorkflowRuleDataVector = null;
  }

  public ArrayList getResultMessages(){return mResultMessages;}
  public void setResultMessages(ArrayList val){mResultMessages = val;}
  public void addResultMessage(String val) {
    if(mResultMessages==null) {
      mResultMessages = new ArrayList();
    }
    if (!mResultMessages.contains(val))
    	mResultMessages.add(val);
  }

  //Error keys
  public static final String GENERIC_ERROR = "GENERIC_ERROR";
  public static final String CREDIT_CARD_ERROR = "CREDIT_CARD_ERROR";
  public static final String DUPLICATED_ORDER = "DUPLICATED_ORDER";
  public static final String NO_STORE_FOUND = "NO_STORE_FOUND";
  public static final String NO_ACCOUNT_FOUND = "NO_ACCOUNT_FOUND";
  public static final String ACCOUNT_FIND_PROBLEM = "ACCOUNT_FIND_PROBLEM";
  public static final String NO_SITE_FOUND = "NO_SITE_FOUND";
  public static final String NO_USER_FOUND = "NO_USER_FOUND";
  public static final String NO_ACTIVE_CATALOG_FOUND = "NO_ACTIVE_CATALOG_FOUND";
  public static final String NO_CONTRACT_FOUND = "NO_CONTRACTS_FOUND";
  public static final String SITE_CONTRACT_PROBLEM = "SITE_CONTRACT_PROBLEM";
  public static final String NO_CONTACT_NAME = "NO_CONTACT_NAME";
  public static final String NO_ITEM_FOUND = "NO_ITEM_FOUND";
  public static final String ERROR_GETTING_PRODUCT_DATA = "ERROR_GETTING_PRODUCT_DATA";
  public static final String NO_MANUFACTURER_SKU = "NO_MANUFACTURER_SKU";
  public static final String DUPLICATED_LINE_ITEM = "DUPLICATED_LINE_ITEM";
  public static final String DELAYED_SHIPMENT_REQUEST = "DELAYED_SHIPMENT_REQUEST";
  public static final String SHIP_TO_ADDRESS_PROBLEM = "SHIP_TO_ADDRESS_PROBLEM";
  public static final String REQ_SHIP_TO_ADDRESS_PROBLEM = "REQ_SHIP_TO_ADDRESS_PROBLEM";
  public static final String REQ_BILL_TO_ADDRESS_PROBLEM = "REQ_BILL_TO_ADDRESS_PROBLEM";
  public static final String NO_DISTRIBUTOR_FOR_SKU = "NO_DISTRIBUTOR_FOR_SKU";
  public static final String MISSING_DIST_SKU = "MISSING_DIST_SKU";
  public static final String MISSING_DIST_COST = "MISSING_DIST_COST";
  public static final String MISSING_DIST_UOM = "MISSING_DIST_UOM";
  public static final String NO_CONTRACT_SKU_PRICE = "NO_CONTRACT_SKU_PRICE";
  public static final String INVALID_SKU_PRICE = "INVALID_SKU_PRICE";
  public static final String NO_BILL_TO = "NO_BILL_TO";
  public static final String BILL_TO_ADDRESS_PROBLEM = "BILL_TO_ADDRESS_PROBLEM";
  public static final String NO_SHIP_TO = "NO_SHIP_TO";
  public static final String SHIP_DATE_REQUST = "SHIP_DATE_REQUST";
  public static final String TRADING_PARTNER_ERROR = "TRADING_PARTNER_ERROR";
  public static final String WRONG_HOLD_UNTIL_DATE = "WRONG_HOLD_UNTIL_DATE";
  public static final String WORKFLOW_RULE_ALARM = "WORKFLOW_RULE_ALARM";
  public static final String WORKFLOW_RULE_WARNING = "WORKFLOW_RULE_WARNING"; //not prevents order to be placed
  public static final String CREDIT_CARD_ORDER = "CREDIT_CARD_ORDER";
  public static final String UOM_CONVERSION_PROBLEM = "UOM_CONVERSION_PROBLEM";
  public static final String ACCOUNT_NOT_ACTIVE = "ACCOUNT_NOT_ACTIVE";
  public static final String NO_ASSET_INFO = "NO_ASSET_INFO";
  public static final String ASSET_NOT_SET = "ASSET_NOT_SET";
  public static final String NOT_SUPPORT_ORDER_TYPE="NOT_SUPPORT_ORDER_TYPE";
  public static final String ERROR_GETTING_TAX_EXEMPT_FLAG ="ERROR_GETTING_TAX_EXEMPT_FLAG" ;

    public static final String[] orderStatusRank = {
    RefCodeNames.ORDER_STATUS_CD.RECEIVED,
    RefCodeNames.ORDER_STATUS_CD.ORDERED,
    RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION,
    RefCodeNames.ORDER_STATUS_CD.WAITING_FOR_ACTION,
    RefCodeNames.ORDER_STATUS_CD.PENDING_DATE,
    RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL,
    RefCodeNames.ORDER_STATUS_CD.SENDING_TO_ERP,
    RefCodeNames.ORDER_STATUS_CD.PROCESS_ERP_PO,
    RefCodeNames.ORDER_STATUS_CD.PENDING_ORDER_REVIEW,
    RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW,
    RefCodeNames.ORDER_STATUS_CD.REJECTED,
    RefCodeNames.ORDER_STATUS_CD.ERP_REJECTED,
    RefCodeNames.ORDER_STATUS_CD.CANCELLED
  };
  private int statusIndex =0;
  private int errorIndexMin = 7; //Error when >=
  private HashMap errors = new HashMap();

  /**
   * Holds value of property orderCreditCardData.
   */
  private OrderCreditCardData orderCreditCardData;
  private CreditCardTransData creditCardTransData;
  private AccCategoryToCostCenterView categToCCView;

  public void setErrors(HashMap pErrors) {errors = pErrors;}
  public HashMap getErrors() {return errors;}

  public String  addError(Connection pCon, String pError,
          String pOrderStatus, int  pLineNum, int pWorkflowRuleId) {
    return addError(pCon, pError,null, pOrderStatus, pLineNum, pWorkflowRuleId);
  }

  public String  addError(Connection pCon, String pError, String pShortDesc,
          String pOrderStatus, int pLineNum, int pWorkflowRuleId) {
    return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                  null, null, null, null, null, null, null, null, null);
}

public String  addError(Connection pCon, String pError, String pShortDesc,
        String pOrderStatus, int pLineNum, int pWorkflowRuleId,
        String pMessageKey) {
  return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                pMessageKey, null, null, null, null, null, null, null, null);
}

 public String  addError(Connection pCon, String pError,
         String pOrderStatus, int pLineNum, int pWorkflowRuleId,
         String pMessageKey) {
  return addError(pCon, pError, null, pOrderStatus, pLineNum, pWorkflowRuleId,
                  pMessageKey, null, null, null, null, null, null, null, null);
}

public String  addError(Connection pCon, String pError, String pShortDesc,
        String pOrderStatus, int pLineNum, int pWorkflowRuleId,
        String pMessageKey, String pArg0, String pArg0TypeCd) {
 return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                 pMessageKey, pArg0, pArg0TypeCd, null, null, null, null, null, null);
}

  public String  addError(Connection pCon, String pError, String pShortDesc,
          String pOrderStatus, int pLineNum, int pWorkflowRuleId,
          String pMessageKey,
          String pArg0, String pArg0TypeCd,
          String pArg1, String pArg1TypeCd) {
   return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                   pMessageKey, pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, null, null, null, null);
  }

  public String  addError(Connection pCon, String pError, String pShortDesc,
          String pOrderStatus, int pLineNum, int pWorkflowRuleId,
          String pMessageKey,
          String pArg0, String pArg0TypeCd,
          String pArg1, String pArg1TypeCd,
          String pArg2, String pArg2TypeCd
          ) {
    return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                    pMessageKey, pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, pArg2, pArg2TypeCd, null, null);
  }

  public String  addError(Connection pCon, String pError, String pShortDesc,
          String pOrderStatus, int pLineNum, int pWorkflowRuleId,
          String pMessageKey,
          Object[] pArgs,
          String[] pArgsTypes
          ) {
    Object[] args = new Object[6];
    String[] types = new String[6];
    if (pArgs != null) {
      for (int i=0; i<pArgs.length; i++) {
        args[i] = pArgs[i];
      }
      for (int i=pArgs.length; i<args.length; i++) {
        args[i] = null;
      }
    }
    if (pArgsTypes != null) {
      for (int i=0; i<pArgsTypes.length; i++) {
        types[i] = pArgsTypes[i];
      }
      for (int i=pArgsTypes.length; i<types.length; i++) {
        types[i] = null;
      }
    }

    return addError(pCon, pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                    pMessageKey, args[0], types[0], args[1], types[1], args[2], types[2], args[3], types[3]);
  }



  public String  addError(Connection pCon, String pError, String pShortDesc,
          String pOrderStatus, int pLineNum, int pWorkflowRuleId,
          String pMessageKey,
          Object pArg0, String pArg0TypeCd,
          Object pArg1, String pArg1TypeCd,
          Object pArg2, String pArg2TypeCd,
          Object pArg3, String pArg3TypeCd
          ) {
    for(int ii=0; ii<orderStatusRank.length; ii++) {
      if(orderStatusRank[ii].equals(pOrderStatus)) {
        if(ii>statusIndex) statusIndex = ii;
        break;
      }
    }
    if(errors==null) errors = new HashMap();
    OrderError oe = new OrderError(pError, pShortDesc, pOrderStatus, pLineNum, pWorkflowRuleId,
                    pMessageKey, pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, pArg2, pArg2TypeCd, pArg3, pArg3TypeCd);
  //STJ-5604
    oe.text = PipelineUtil.translateMessage(pMessageKey,"en_US",
                                            //this.getOrderData()!=null?this.getOrderData().getLocaleCd():null,
                                            pArg0, pArg0TypeCd, pArg1, pArg1TypeCd, pArg2, pArg2TypeCd,
                                            pArg3, pArg3TypeCd);
    if(!Utility.isSet(oe.text)) {
        oe.text = pMessageKey;
    }
    ArrayList errorAL = (ArrayList) errors.get(pError);
    if(errorAL==null) {
      errorAL = new ArrayList();
      errorAL.add(oe);
      errors.put(pError, errorAL);
    } else {
      errorAL.add(oe);
    }
    return null;
  }


  public boolean hasError(String pError) {
    OrderError oe = (OrderError) errors.get(pError);
    if(oe==null) return false;
    return true;
  }

  public ArrayList getError(String pError) {
    ArrayList oe = (ArrayList) errors.get(pError);
    return oe;
  }

  public void setOrderStatus(String pOrderStatus)
  throws Exception
  {
    int ii=0;
    for(; ii<orderStatusRank.length; ii++) {
      if(orderStatusRank[ii].equals(pOrderStatus)) {
        statusIndex = ii;
        break;
      }
    }
    if(ii==orderStatusRank.length) {
      String mess = "Unknown order status: "+pOrderStatus;
      throw new Exception(mess);
    }
  }

  public String getOrderStatus() {
    return orderStatusRank[statusIndex];
  }

  public boolean hasErrors() {
    return (statusIndex>=errorIndexMin)? true:false;
  }

  public OrderPropertyDataVector getErrorsAsProperties() {
    OrderPropertyDataVector  orderPropertyDV = new   OrderPropertyDataVector();
    Collection errorVals = errors.values();
    Iterator errorIter = errorVals.iterator();
    while (errorIter.hasNext()) {
      ArrayList errorAL = (ArrayList) errorIter.next();
      Iterator it = errorAL.iterator();
      while (it.hasNext()) {
        OrderError oe = (OrderError) it.next();
        if(Utility.isSet(oe.text)) {
          OrderPropertyData opD = OrderPropertyData.createValue();
          opD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
          opD.setValue(oe.text);
          if(Utility.isSet(oe.shortDesc)) opD.setShortDesc(oe.shortDesc);
          opD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
          if(oe.lineNum!=0) {
            for(int ii=0; ii<mOrderItemDataVector.size(); ii++) {
              OrderItemData oiD = (OrderItemData) mOrderItemDataVector.get(ii);
              if(oiD.getOrderLineNum()==oe.lineNum) {
                opD.setOrderItemId(oiD.getOrderItemId());
              }
            }
          }
          opD.setWorkflowRuleId(oe.workflowRuleId);
          // veronika: add message_key and arguments in order_property
          opD.setMessageKey(oe.messageKey);
          opD.setArg0(oe.arg0);
          opD.setArg0TypeCd(oe.arg0TypeCd);
          opD.setArg1(oe.arg1);
          opD.setArg1TypeCd(oe.arg1TypeCd);
          opD.setArg2(oe.arg2);
          opD.setArg2TypeCd(oe.arg2TypeCd);
          opD.setArg3(oe.arg3);
          opD.setArg3TypeCd(oe.arg3TypeCd);
          //
          orderPropertyDV.add(opD);
        }
      }
    }
    return orderPropertyDV;
  }

  public OrderPipelineBaton copy()
  throws java.io.IOException, ClassNotFoundException
  {
    java.io.ByteArrayOutputStream oStream = new java.io.ByteArrayOutputStream();
    java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(oStream);
    os.writeObject(this);
    os.flush();
    os.close();
    byte[] byteImage = oStream.toByteArray();
    oStream.close();
    java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(byteImage);
    java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
    OrderPipelineBaton nBaton = (OrderPipelineBaton) is.readObject();
    is.close();
    iStream.close();
    return nBaton;
  }
  public class OrderError implements Serializable {
    public String error;
    public String shortDesc;
    public String text;
    public String orderStatus;
    public int lineNum;
    public int workflowRuleId;
    // veronika
    public String messageKey;
    public String arg0;
    public String arg0TypeCd;
    public String arg1;
    public String arg1TypeCd;
    public String arg2;
    public String arg2TypeCd;
    public String arg3;
    public String arg3TypeCd;

    public OrderError(String pError, String pShortDesc,
            String pOrderStatus, int pLineNum, int pWorkflowRuleId,
            String pMessageKey,
            Object pArg0, String pArg0TypeCd,
            Object pArg1, String pArg1TypeCd,
            Object pArg2, String pArg2TypeCd,
            Object pArg3, String pArg3TypeCd) {
      shortDesc = pShortDesc;
      error = pError;
      orderStatus = pOrderStatus;
      lineNum = pLineNum;
      workflowRuleId = pWorkflowRuleId;
      messageKey = pMessageKey;
      arg0 = PipelineUtil.catStr(pArg0, PipelineUtil.ARG_LENGTH);
      arg0TypeCd = pArg0TypeCd;
      arg1 = PipelineUtil.catStr(pArg1, PipelineUtil.ARG_LENGTH);
      arg1TypeCd = pArg1TypeCd;
      arg2 = PipelineUtil.catStr(pArg2, PipelineUtil.ARG_LENGTH);
      arg2TypeCd = pArg2TypeCd;
      arg3 = PipelineUtil.catStr(pArg3, PipelineUtil.ARG_LENGTH);
      arg3TypeCd = pArg3TypeCd;
    }


    public String toString(){
      String errorS = "";
      errorS += "[error]: "+error+",";
      errorS += "[shortDesc]: "+shortDesc+",";
      errorS += "[text]: "+text+",";
      errorS += "[orderStatus]: "+orderStatus+",";
      errorS += "[messageKey]: "+messageKey+",";
      return errorS;
    }
  }
  public String toString()  {
    String batonS = "";
    batonS +="[mStepNum]: "+this.mStepNum+",";
    batonS +="[mWhatNext]: "+this.mWhatNext+",";
    batonS +="[mBatonNumber]: "+this.mBatonNumber+",";
    batonS +="[mPipelineTypeCd]: "+this.mPipelineTypeCd+",";
    batonS +="[mInputType]: "+this.mInputType+",";
    batonS +="[mUserName]: "+this.mUserName+",";
    batonS +="[mBypassOptional]: "+this.mBypassOptional+",";
    batonS +="[mOrderRequestData]: "+this.mOrderRequestData+",";
    batonS +="[mOrderData]: "+mOrderData+",";
    batonS +="[mOrderMetaDataVector]: "+this.mOrderMetaDataVector+",";
    batonS +="[mOrderPropertyDataVector]: "+this.mOrderPropertyDataVector+",";
    batonS +="[mBillToData]: "+this.mBillToData+",";
    batonS +="[mShipToData]: "+this.mShipToData+",";
    batonS +="[mCustShipToData]: "+this.mCustShipToData+",";
    batonS +="[mOrderItemDataVector]: "+this.mOrderItemDataVector+",";
    batonS +="[mOrderItemMeta]: "+this.mOrderItemMeta+",";
    batonS +="[mWorkflowQueueDataVector]: "+this.mWorkflowQueueDataVector+",";
    batonS +="[mWorkflowRuleDataVector]: "+this.mWorkflowRuleDataVector+",";
    batonS +="[mResultMessages]: "+this.mResultMessages+",";
    batonS +="[statusIndex]: "+this.statusIndex+",";
    batonS +="[errors]: "+this.errors;
    return batonS;
  }


  public OrderCreditCardData getOrderCreditCardData() {
      return this.orderCreditCardData;
  }
  public void setOrderCreditCardData(OrderCreditCardData orderCreditCardData) {
      this.orderCreditCardData = orderCreditCardData;
  }

  public CreditCardTransData getCreditCardTransData() {
      return this.creditCardTransData;
  }

  public AccCategoryToCostCenterView getCategToCCView() {
    return categToCCView;
  }

  public void setCreditCardTransData(CreditCardTransData creditCardTransData) {
      this.creditCardTransData = creditCardTransData;
  }

  public void setCategToCCView(AccCategoryToCostCenterView categToCCView) {
    this.categToCCView = categToCCView;
  }

  /**
   *Caches busEntities for this run of the OrderPipeline.
   */
  public String getBusEntityPropertyCached(int pBusEntityId, String pName, Connection pCon) throws RemoteException{
      //get propes for this busEnt
      Object key = new Integer(pBusEntityId);
      Map bep = (Map) mCachedProperties.get(key);
      if(bep == null){
          bep = new HashMap();
          mCachedProperties.put(key, bep);
      }
      key = pName;
      String result;
      if(bep.containsKey(key)){
        result = (String) bep.get(key);
      }else{
        PropertyUtil pru = new PropertyUtil(pCon);
        result = pru.fetchValueIgnoreMissing(0, pBusEntityId, pName);
        bep.put(key, result);
      }
      return result;
  }


  public boolean getSimpleServiceOrderFl() {
      return mSimpleServiceOrderFl;
  }

  public void setSimpleServiceOrderFl(boolean simpleServiceOrderFl) {
      this.mSimpleServiceOrderFl = simpleServiceOrderFl;
  }

  public boolean getSimpleProductOrderFl() {
      return !mSimpleServiceOrderFl || mSimpleProductOrderFl; //YK Temporary fix !!!!!!!!
  }

  public void setSimpleProductOrderFl(boolean simpleProductOrderFl) {
      this.mSimpleProductOrderFl = simpleProductOrderFl;
  }

  public boolean isPendingOrderEmailSent() {
      return mPendingOrderEmailSent;
  }

  public void setPendingOrderEmailSent(boolean pendingOrderEmailSent) {
      this.mPendingOrderEmailSent = pendingOrderEmailSent;
  }

  public String getFreightType(){return mFreightType;}
  public void setFreightType(String val){mFreightType = val;}


  public boolean isHistoricalOrderOrderProps(){
	  boolean historicalOrder = false;
	  if(this.getOrderPropertyDataVector() != null){
		Iterator it = getOrderPropertyDataVector().iterator();
		while (it.hasNext()) {
			OrderPropertyData pop = (OrderPropertyData) it.next();
			if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.HISTORICAL_ORDER.equals(pop.getShortDesc())) {
				if (Utility.isTrue(pop.getValue())) {
					historicalOrder = true;
				}
			}
		}
	  }
	  return historicalOrder;
  }

  public boolean isHistoricalOrderPreOrderProps(){
	  boolean historicalOrder = false;
	  if (this.getPreOrderPropertyDataVector() != null) {
		Iterator it = getPreOrderPropertyDataVector().iterator();
		while (it.hasNext()) {
			PreOrderPropertyData pop = (PreOrderPropertyData) it.next();
			if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.HISTORICAL_ORDER.equals(pop.getShortDesc())) {
				if (Utility.isTrue(pop.getValue())) {
					historicalOrder = true;
				}
			}
		}
      }
	  return historicalOrder;
  }

  public boolean isSkipDuplicatedOrderValidationProps(){
	  boolean skipDuplicatedOrderValidation = false;
	  if (this.getPreOrderPropertyDataVector() != null) {
		Iterator it = getPreOrderPropertyDataVector().iterator();
		while (it.hasNext()) {
			PreOrderPropertyData pop = (PreOrderPropertyData) it.next();
			if (RefCodeNames.ORDER_PROPERTY_TYPE_CD.SKIP_DUPLICATED_ORDER_VALIDATION.equals(pop.getShortDesc())) {
				if (Utility.isTrue(pop.getValue())) {
					skipDuplicatedOrderValidation = true;
				}
			}
		}
      }
	  return skipDuplicatedOrderValidation;
  }
  public void setEmailReceiversForApproval(HashSet pReceivers){
    mEmailReceiversForApproval = pReceivers;
  }
  public HashSet getEmailReceiversForApproval(){
    return mEmailReceiversForApproval;
  }
  public void setApproverGroupsHM(HashMap pApproverGroups){
    mApproverGroupsHM = pApproverGroups;
  }
  public HashMap getApproverGroupsHM(){
    return mApproverGroupsHM;
  }
  public void setApproverGroupUsersHM(HashMap pApproverGroupUsers){
    mApproverGroupUsersHM = pApproverGroupUsers;
  }
  public HashMap getApproverGroupUsersHM(){
    return mApproverGroupUsersHM;
  }
  public void setWorkflowRulesCleanedApproversHS(HashSet pWorkflowRulesCleanedApprovers){
     mWorkflowRulesCleanedApproversHS = pWorkflowRulesCleanedApprovers;
   }
  public HashSet getWorkflowRulesCleanedApproversHS(){
     return mWorkflowRulesCleanedApproversHS;
  }


}
