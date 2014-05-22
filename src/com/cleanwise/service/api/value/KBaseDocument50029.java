package com.cleanwise.service.api.value;

import com.cleanwise.service.apps.dataexchange.OutboundDocSender;
import com.cleanwise.view.utils.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class KBaseDocument50029 {
 //title
  public static final String  Title="KBaseDocument 50029";  


 //main
 public static final String  S1DocFormat = "FormText v1.20";
 public static final String  S1DocType="850 PO";
 public static final String  S1DocSource="JMCat";
 public static final String  S1CustShipTOCode="001";

 private String mS1CustAcct;
 private String mS1CustName;
 private String mS1PONum;
 private ArrayList mS1Instruct;

 //Option 2
 public static final String S1CPItemCodes_Start="Here";
 public static final String S1CPItemCodes_End="Here";
 private PairViewVector mPairsSkuQty;
 private String mSummary;
 private String mPrimaryKeyword;
 private boolean mPublicKBDoc;
 private String mOtherKeyword;
 private String mModule;
 private Date mEnteredData;
 private String mEnteredBy;
 private Date mLastModifyData;
 private String mLastModifyBy;
 private String mStep;
 private String mVersion;

    public KBaseDocument50029(OutboundDocSender.BuilderRequest request) {

    }

   public void createTestValue()
   {

      mS1CustAcct=String.valueOf(1234);
      //String.valueOf(request.getProfileD().getGroupControlNum());
      mS1CustName="BARNEY'S CORPORATION";
      //request.getPartnerD().getShortDesc();
      mS1PONum="TEST1234";

      mSummary="Submitting New Orders to Step1v6 Admin using Form text format";
      mPrimaryKeyword="FORMTEXT";
      mPublicKBDoc=true;
      mOtherKeyword="ecommerce ecomm";
      mModule="ADMIN";
      mEnteredData=new Date();
      mEnteredBy="Vlasov Eugene";
      mLastModifyData=new Date();
      mLastModifyBy="Vlasov Eugene";
      mStep= String.valueOf(1);
      mVersion= String.valueOf(6);
       mS1Instruct=new ArrayList();
      mPairsSkuQty=new PairViewVector();
      addPairsSkuOty(mPairsSkuQty,"ADV-1234","2");
      addPairsSkuOty(mPairsSkuQty,"RUB-2345","12");
      addPairsSkuOty(mPairsSkuQty,"ADV-6789EA","4");
      setInstruct(mS1Instruct,"Software License.  As long as you comply with the" +
              " terms of this Software License Agreement (this “Agreement”)," +
              " Adobe grants to you a non-exclusive license to Use the Software" +
              " for the purposes described in the Documentation, as further set forth below.");
   }

    private void addPairsSkuOty(PairViewVector mPairsSkuQty, String s, String s1) {
        mPairsSkuQty.add(new PairView(s,s1));
    }

    private void setInstruct(ArrayList mS1Instruct, String s) {
        ArrayList lines= StringUtils.parseDelim(s,' ',30);
        mS1Instruct.addAll(lines);
    }


    public String getS1CustAcct() {
        return mS1CustAcct;
    }

    public void setS1CustAcct(String s1CustAcct) {
        mS1CustAcct = s1CustAcct;
    }

    public String getS1CustName() {
        return mS1CustName;
    }

    public void setS1CustName(String s1CustName) {
        mS1CustName = s1CustName;
    }

    public String getS1PONum() {
        return mS1PONum;
    }

    public void setS1PONum(String s1PONum) {
        mS1PONum = s1PONum;
    }

    public ArrayList getS1Instruct() {
        return mS1Instruct;
    }

    public void setS1Instruct(ArrayList s1Instruct) {
        mS1Instruct = s1Instruct;
    }

    public PairViewVector getPairsSkuQty() {
        return mPairsSkuQty;
    }

    public void setPairsSkuQty(PairViewVector pairsSkuQty) {
        this.mPairsSkuQty = pairsSkuQty;
    }


    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        this.mSummary = summary;
    }

    public String getPrimaryKeyword() {
        return mPrimaryKeyword;
    }

    public void setPrimaryKeyword(String primaryKeyword) {
        this.mPrimaryKeyword = primaryKeyword;
    }

    public boolean getPublicKBDoc() {
        return mPublicKBDoc;
    }

    public void setPublicKBDoc(boolean publicKBDoc) {
        this.mPublicKBDoc = publicKBDoc;
    }

    public String getOtherKeyword() {
        return mOtherKeyword;
    }

    public void setOtherKeyword(String otherKeyword) {
        this.mOtherKeyword = otherKeyword;
    }

    public String getModule() {
        return mModule;
    }

    public void setModule(String module) {
        this.mModule = module;
    }

    public Date getEnteredData() {
        return mEnteredData;
    }

    public void setEnteredData(Date enteredData) {
        this.mEnteredData = enteredData;
    }

     public String getEnteredBy() {
        return mEnteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.mEnteredBy = enteredBy;
    }

    public Date getLastModifyData() {
        return this.mLastModifyData;
    }

    public void setLastModifyData(Date lastModifyData) {
        this.mLastModifyData = lastModifyData;
    }

    public String getLastModifyBy() {
        return this.mLastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.mLastModifyBy = lastModifyBy;
    }

    public String getStep() {
        return mStep;
    }

    public void setStep(String step) {
        this.mStep = step;
    }

    public String getVersion() {
        return mVersion;
    }

    public void setVersion(String version) {
        this.mVersion = version;
    }
}
