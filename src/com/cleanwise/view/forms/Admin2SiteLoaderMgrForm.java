package com.cleanwise.view.forms;
import org.apache.struts.upload.FormFile;
import java.util.ArrayList;

import com.cleanwise.service.api.value.AccountDataVector;

/**
 *
 * @author veronika
 */
public class Admin2SiteLoaderMgrForm  extends StorePortalBaseForm {

    private String mFileNameTempl = null;
    private String mFileName = null;
    private String mNote = null;
    private String[][] mSourceTable = null;
    private FormFile mXlsFile = null;
   // private boolean mShowInactiveFl = false;

    private ArrayList mErrors = new ArrayList();
    private boolean mSuccessfullyLoaded = false;

    public final static String
                VERSION = "Version #",
                ACCOUNT_ID = "Account ID",
                ACCOUNT_NAME = "Account Name",
                SITE_NAME = "Site Name",
                REF_NUM = "Site Budget Reference Number",
                TAXABLE = "Taxable Value",
                ALLOW_CORP_SCHED_ORDER = "Allow Corp Sched Order",
                SHARE_BUYER_OG = "Share buyer order guides",
                FIRST_NAME = "First Name",
                LAST_NAME = "Last Name",
                ADDRESS_1 = "Address 1",
                ADDRESS_2 = "Address 2",
                ADDRESS_3 = "Address 3",
                ADDRESS_4 = "Address 4",
                CITY = "City",
                STATE = "State",
                POSTAL_CODE = "Postal Code",
                COUNTRY = "Country",
                SHIPPING_MSG = "Shipping Message",
                OG_COMMENTS = "Order Guide Comments",
                SITE_ID = "Site Id";

    private String[] mSiteLoadFields = {
            VERSION,
            ACCOUNT_ID ,
            ACCOUNT_NAME ,
            SITE_NAME,
            REF_NUM,
            TAXABLE,
            ALLOW_CORP_SCHED_ORDER,
            SHARE_BUYER_OG ,
            FIRST_NAME,
            LAST_NAME,
            ADDRESS_1,
            ADDRESS_2,
            ADDRESS_3,
            ADDRESS_4,
            CITY,
            STATE,
            POSTAL_CODE,
            COUNTRY,
            SHIPPING_MSG,
            OG_COMMENTS,
            SITE_ID
    };

    private boolean[] mMandatoryFields = {
            true,  //VERSION
            true,  //ACCOUNT_ID
            false, //ACCOUNT_NAME
            true,  //SITE_NAME
            true,  //REF_NUM
            true,  //TAXABLE
            false, //INV_SHOPPING
            true,  //SHARE_BUYER_OG
            false, //FIRST_NAME
            false, //LAST_NAME
            true,  //ADDRESS_1
            false, //ADDRESS_2
            false, //ADDRESS_3
            false, //ADDRESS_4
            true,  //CITY
            false, //STATE (mandatory only for USA and Canada
            true,  //POSTAL_CODE
            true,  //COUNTRY
            false, //SHIPPING_MSG
            false, //OG_COMMENTS,
            false  //SITE_ID
    };


    private int[] mSiteLoadFieldMap = new int[mSiteLoadFields.length];

    // for export
    boolean mExportFlag = false;

    boolean mDownloadErrorButton = false;


    public void init() {
        setErrors(new ArrayList());
        setSuccessfullyLoaded(false);
        setExportFlag(false);
        setDownloadErrorButton(false);
        setSourceTable(null);
    }

    public String getFileNameTempl() { return mFileNameTempl;}
    public void setFileNameTempl(String pFileNameTempl) {mFileNameTempl = pFileNameTempl;}

    public String[][] getSourceTable() { return mSourceTable;}
    public void setSourceTable(String[][] pSourceTable) {mSourceTable = pSourceTable;}

    public String getFileName() { return mFileName;}
    public void setFileName(String pFileName) {mFileName = pFileName;}

    public String getNote() { return mNote;}
    public void setNote(String pNote) {mNote = pNote;}

    public FormFile getXlsFile() { return mXlsFile;}
    public void setXlsFile(FormFile pXlsFile) {mXlsFile = pXlsFile;}
    
    public String[] getSiteLoadFields() { return mSiteLoadFields;}

    public int[] getSiteLoadFieldMap() { return mSiteLoadFieldMap;}
    public void setSiteLoadFieldMap(int[] v) {mSiteLoadFieldMap = v;}
    
    public int getColumnNum() {
        return mSiteLoadFields.length;
    }

    public boolean isMandatoryField(int i) {
        return mMandatoryFields[i];
    }

    public String getSiteLoadFieldName(int i) {
        return mSiteLoadFields[i];
    }

    public void setErrors(ArrayList v ) {
        mErrors = v;
    }

    public ArrayList getErrors() {
        return mErrors;
    }

    public void addErrors(String[] v) {
        if (mErrors == null) {
          mErrors = new ArrayList();
        }
        mErrors.add(v);
    }

    public String[] getRowErrors(int rowNum) {
        if (mErrors == null) {
          mErrors = new ArrayList();
        }
        if (rowNum > mErrors.size()) {
            while (mErrors.size() < rowNum) {
                String[] rowErrors = new String[mSiteLoadFields.length];
                mErrors.add(rowErrors);
            }
        }
        return (String[])mErrors.get(rowNum);
    }


    public void addError(String v, int rowNum, int colNum) {
        String[] rowErr = getRowErrors(rowNum);
        rowErr[colNum] = v;
    }

    public void setSuccessfullyLoaded(boolean v) {
        mSuccessfullyLoaded = v;
    }

    public boolean getSuccessfullyLoaded() {
        return mSuccessfullyLoaded;
    }

    public void setExportFlag(boolean v) {
        mExportFlag = v;
    }

    public boolean getExportFlag() {
        return mExportFlag;
    }

    public void setDownloadErrorButton(boolean v) {
        mDownloadErrorButton = v;
    }

    public boolean getDownloadErrorButton() {
        return mDownloadErrorButton;
    }

}


 
