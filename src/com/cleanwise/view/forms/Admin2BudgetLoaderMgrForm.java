package com.cleanwise.view.forms;
import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

/**
 *
 * @author veronika
 */
public class Admin2BudgetLoaderMgrForm  extends StorePortalBaseForm {

    private String mFileNameTempl = null;
    private String mFileName = null;
    private String mNote = null;
    private String[][] mSourceTable = null;
    private FormFile mXlsFile = null;

    private ArrayList mErrors = new ArrayList();
    private boolean mSuccessfullyLoaded = false;

    public final static String
                VERSION = "Version #",
                SITE_ID = "Site ID",
                SITE_NAME = "Site Name",
                REF_NUM = "Site Budget Reference Number",
                COST_CENTER_NAME = "Cost Center Name",
                COST_CENTER_CODE = "Cost Center Code",
                PERIOD_1 = "Period 1",
                PERIOD_2 = "Period 2",
                PERIOD_3 = "Period 3",
                PERIOD_4 = "Period 4",
                PERIOD_5 = "Period 5",
                PERIOD_6 = "Period 6",
                PERIOD_7 = "Period 7",
                PERIOD_8 = "Period 8",
                PERIOD_9 = "Period 9",
                PERIOD_10 = "Period 10",
                PERIOD_11 = "Period 11",
                PERIOD_12 = "Period 12",
                PERIOD_13 = "Period 13",
                FISCAL_YEAR = "Fiscal Year";

    private String[] mBudgetLoadFields = {
            VERSION,
            SITE_ID,
            SITE_NAME,
            REF_NUM,
            COST_CENTER_NAME,
            COST_CENTER_CODE,
            PERIOD_1,
            PERIOD_2,
            PERIOD_3,
            PERIOD_4,
            PERIOD_5,
            PERIOD_6,
            PERIOD_7,
            PERIOD_8,
            PERIOD_9,
            PERIOD_10,
            PERIOD_11,
            PERIOD_12,
            PERIOD_13,
            FISCAL_YEAR
    };

    private boolean[] mMandatoryFields = {
            true,   //VERSION
            true,   //SITE_ID
            true,   //SITE_NAME
            false,  //REF_NUM
            true,   //COST_CENTER_NAME
            false,  //COST_CENTER_CODE
            false,  //PERIOD_1
            false,  //PERIOD_2
            false,  //PERIOD_3
            false,  //PERIOD_4
            false,  //PERIOD_5
            false,  //PERIOD_6
            false,  //PERIOD_7
            false,  //PERIOD_8
            false,  //PERIOD_9
            false,  //PERIOD_10
            false,  //PERIOD_11
            false,  //PERIOD_12
            false,  //PERIOD_13
            true    //FISCAL_YEAR
    };


    private int[] mBudgetLoadFieldMap = new int[mBudgetLoadFields.length];

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
    
    public String[] getBudgetLoadFields() { return mBudgetLoadFields;}

    public int[] getBudgetLoadFieldMap() { return mBudgetLoadFieldMap;}
    public void setBudgetLoadFieldMap(int[] v) {mBudgetLoadFieldMap = v;}
    
    public int getColumnNum() {
        return mBudgetLoadFields.length;
    }

    public boolean isMandatoryField(int i) {
        return mMandatoryFields[i];
    }

    public String getBudgetLoadFieldName(int i) {
        return mBudgetLoadFields[i];
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
                String[] rowErrors = new String[mBudgetLoadFields.length];
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


 
