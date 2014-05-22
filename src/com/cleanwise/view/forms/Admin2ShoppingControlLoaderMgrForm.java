package com.cleanwise.view.forms;
import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

/**
 *
 * @author deping
 */
public class Admin2ShoppingControlLoaderMgrForm  extends StorePortalBaseForm {

    private String mFileNameTempl = null;
    private String mFileName = null;
    private String mNote = null;
    private String[][] mSourceTable = null;
    private FormFile mXlsFile = null;

    private ArrayList mErrors = new ArrayList();
    private boolean mSuccessfullyLoaded = false;
        
    public final static String
			    VERSION = "Version #",
			    ACCOUNT_ID = "Account Id",
			    ACCOUNT_NAME = "Account Name",
                SITE_ID = "Site Id",
                SITE_NAME = "Site Name",
                STORE_SKU = "Store Sku",
                DESCRIPTION = "Description",
                ITEM_SIZE = "Item Size",
                UOM = "UOM",
                PACK = "Pack",
                MAX_ORDER_QTY = "Max Order Qty",
                RESTRICTION_DAYS = "Restriction Days";

    private String[] mShoppingCtrlLoadFields = {
            VERSION,
            ACCOUNT_ID,
            ACCOUNT_NAME,
            SITE_ID,
            SITE_NAME,
            STORE_SKU,
            DESCRIPTION,
            ITEM_SIZE,
            UOM,
            PACK,
            MAX_ORDER_QTY,
            RESTRICTION_DAYS
    };

    private boolean[] mMandatoryFields = {
            true,   //VERSION
            true,   //ACCOUNT_ID
            true,   //ACCOUNT_NAME
            false,  //SITE_ID
            false,  //SITE_NAME
            true,   //STORE_SKU
            false,  //DESCRIPTION
            false,  //ITEM_SIZE
            false,  //UOM
            false,  //PACK
            false,   //MAX_ORDER_QTY
            false,  //RESTRICTION_DAYS
    };


    private int[] mShoppingCtrlLoadFieldMap = new int[mShoppingCtrlLoadFields.length];

    // for export
    boolean mExportFlag = false;

    boolean mDownloadErrorButton = false;


    public void init() {
        setErrors(new ArrayList());
        setSuccessfullyLoaded(false);
        setExportFlag(false);
        setDownloadErrorButton(false);
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
    
    public String[] getShoppingCtrlLoadFields() { return mShoppingCtrlLoadFields;}

    public int[] getShoppingCtrlLoadFieldMap() { return mShoppingCtrlLoadFieldMap;}
    public void setShoppingCtrlLoadFieldMap(int[] v) {mShoppingCtrlLoadFieldMap = v;}
    
    public int getColumnNum() {
        return mShoppingCtrlLoadFields.length;
    }

    public boolean isMandatoryField(int i) {
        return mMandatoryFields[i];
    }

    public String getShoppingCtrlLoadFieldName(int i) {
        return mShoppingCtrlLoadFields[i];
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
                String[] rowErrors = new String[mShoppingCtrlLoadFields.length];
                mErrors.add(rowErrors);
            }
        }
        return (String[])mErrors.get(rowNum-1);
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


 
