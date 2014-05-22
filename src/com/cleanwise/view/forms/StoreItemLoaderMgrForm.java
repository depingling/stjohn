package com.cleanwise.view.forms;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.SelectableObjects;
import com.cleanwise.view.utils.CategoryUtil;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.*;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.math.BigDecimal;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.utils.StringUtils;

/**
 *
 * @author Ykupershmidt
 */
public class StoreItemLoaderMgrForm  extends StorePortalBaseForm {
    private CatalogDataVector mCatalogs = null;

    private String mFileNameTempl = null;
    private boolean mShowProcessedFl = false;
    private UploadDataVector mTableList = null;

    private String mFileName = null;
    private String mNote = null;
    private String mTableStatus = null;
    private String[][] mSourceTable = null;
    private UploadData mTableHeader = null;
    private UploadValueData[][] mTable = null;
    private FormFile mXlsFile = null;
    private boolean[] mEmptyColumnFl = new boolean[0];
    private int[] mSelectedColumns = new int[0];
    private int[] mSelectedRows = new int[0];

    public final static String EMPTY_PROPERTY_VALUE = "<<< Clear >>>";
    public final static String CATEGORY_PROPERTY = "Category";
    public final static String GREEN_CERTIFIED = "Green Certified";
    public final static String CUSTOMER_SKU = "Cust. Sku";
    public final static String SHIPPING_WEIGHT = "Shipping Weight";
    public final static String WEIGHT_UNIT = "Weight Unit";
    public final static String THUMBNAIL_URL = "Thumbnail URL";
    public final static String SERVICE_FEE_CODE = "Service Fee Code";
    private String[] mItemProperties = {"Sku Num","Short Desc",
            "Long Desc","Other Desc",  "Size", "Pack", "UOM", "Color",
            CATEGORY_PROPERTY,"UNSPSC","NSN","PSN",
            "Manufacturer", "Manuf. Sku", "Manuf. UOM", "Manuf. Pack",
            "Distributor",  "Dist. Sku",  "Dist. UOM",  "Dist. Pack", "Dist. UOM. Mult",
            "Gen. Manufacturer", "Gen. Manuf. Sku",
            "List Price","Catalog Price", "Dist. Cost", "MFCP", "Base Cost","SPL","Tax Exempt",
            GREEN_CERTIFIED, CUSTOMER_SKU, "Cust. Desc",
            "Image URL", "MSDS URL", "DED URL", "Prod Spec URL",
            SHIPPING_WEIGHT, WEIGHT_UNIT, THUMBNAIL_URL, SERVICE_FEE_CODE};

    private int[] mItemPropertiesAbcOrder = null;

    private int[] mItemPropertyMap = new int[mItemProperties.length];

    private String[] mColumnTypes = null;

    private String mColumnToUpdate = null;
    private String mUpdateFilter = null;
    private String[] mUpdateTableValues = null;
    private String[] mUpdateValues = null;
    private UploadSkuView[] mUploadSkus = null;
    private UploadSkuViewVector mItemsToMatch = null;
    private int mEditItemId = 0;

    private DistributorDataVector mStoreDistributors = new DistributorDataVector();
    private ManufacturerDataVector mStoreManufacturers = new ManufacturerDataVector();
    private CatalogCategoryDataVector mStoreCategories = new CatalogCategoryDataVector();
    private BusEntityDataVector mCertifiedCompanies = new BusEntityDataVector();

    private boolean mShowDistCostFl = false; //SVC

    //
    String mFilterMatched = "All";
    String mFilterSku = null;
    String mFilterSkuType = "storeSku";
    String mFilterManuf = null;
    String mFilterDist = null;
    String mFilterName = null;
    String mFilterCateg = null;
    private boolean allowMixedCategoryAndItemUnderSameParent;

    //search
    public String getFileNameTempl() { return mFileNameTempl;}
    public void setFileNameTempl(String pFileNameTempl) {mFileNameTempl = pFileNameTempl;}

    public boolean getShowProcessedFl() { return mShowProcessedFl;}
    public void setShowProcessedFl(boolean pShowProcessedFl) {mShowProcessedFl = pShowProcessedFl;}

    public UploadDataVector getTableList() { return mTableList;}
    public void setTableList(UploadDataVector pTableList) {mTableList = pTableList;}


    //Upload
    public String[][] getSourceTable() { return mSourceTable;}
    public void setSourceTable(String[][] pSourceTable) {mSourceTable = pSourceTable;}

    public UploadData getTableHeader() { return mTableHeader;}
    public void setTableHeader(UploadData pTableHeader) {mTableHeader = pTableHeader;}

    public UploadValueData[][] getTable() { return mTable;}
    public void setTable(UploadValueData[][] pTable) {mTable = pTable;}

    public CatalogDataVector getCatalogs() { return mCatalogs;}
    public void setCatalogFilter(CatalogDataVector pCatalogs) {mCatalogs = pCatalogs;}

    public String getFileName() { return mFileName;}
    public void setFileName(String pFileName) {mFileName = pFileName;}

    public String getNote() { return mNote;}
    public void setNote(String pNote) {mNote = pNote;}

    public String getTableStatus() { return mTableStatus;}
    public void setTableStatus(String pTableStatus) {mTableStatus = pTableStatus;}

    public FormFile getXlsFile() { return mXlsFile;}
    public void setXlsFile(FormFile pXlsFile) {mXlsFile = pXlsFile;}

    public boolean[] getEmptyColumnFl() { return mEmptyColumnFl;}
    public void setEmptyColumnFl(boolean[] pEmptyColumnFl) {mEmptyColumnFl = pEmptyColumnFl;}

    public int[] getSelectedRows() { return mSelectedRows;}
    public void setSelectedRows(int[] pSelectedRows) {mSelectedRows = pSelectedRows;}

    public int[] getSelectedColumns() { return mSelectedColumns;}
    public void setSelectedColumns(int[] pSelectedColumns) {mSelectedColumns = pSelectedColumns;}

    public String[] getItemProperties() { return mItemProperties;}
    //public void setItemProperties(String[] pItemProperties) {mItemProperties = pItemProperties;}
    public int[] getItemPropertiesAbcOrder() {
      if(mItemPropertiesAbcOrder==null) {
        mItemPropertiesAbcOrder = new int[mItemProperties.length];
        for(int ii=0; ii<mItemPropertiesAbcOrder.length; ii++) {
          mItemPropertiesAbcOrder[ii]=ii;
        }
        String[] wrk = (String[]) mItemProperties.clone();
        for(int ii=0; ii<wrk.length-1; ii++) {
          boolean exitFl = true;
          for(int jj=0; jj<wrk.length-ii-1; jj++) {
            if(wrk[jj].compareTo(wrk[jj+1])>0) {
              exitFl = false;
              String ss = wrk[jj];
              wrk[jj] = wrk[jj+1];
              wrk[jj+1] = ss;
              int ind = mItemPropertiesAbcOrder[jj];
              mItemPropertiesAbcOrder[jj] = mItemPropertiesAbcOrder[jj+1];
              mItemPropertiesAbcOrder[jj+1] = ind;
            }
          }
          if(exitFl) break;
        }
      }
      return mItemPropertiesAbcOrder;
    }

    public int[] getItemPropertyMap() { return mItemPropertyMap;}
    public void setItemPropertyMap(int[] pItemPropertyMap) {mItemPropertyMap = pItemPropertyMap;}


    public String[] getColumnTypes() { return mColumnTypes;}
    public void setColumnTypes(String[] pColumnTypes) {mColumnTypes = pColumnTypes;}
    public String getColumnType(int ii) {
      if(mColumnTypes==null || mColumnTypes.length<ii) {
        return "";
      }
      return mColumnTypes[ii];
    }
    public void setColumnType(int ii, String val) {
      if(mColumnTypes==null || mColumnTypes.length<ii) {
        return;
      }
      mColumnTypes[ii] = val;
    }

    public boolean getShowDistCostFl() {return mShowDistCostFl;} //SVC
    public void setShowDistCostFl(boolean pVal) {mShowDistCostFl = pVal;} //SVC

    //Update
    public String getColumnToUpdate() { return mColumnToUpdate;}
    public void setColumnToUpdate(String pColumnToUpdate) {mColumnToUpdate = pColumnToUpdate;}

    public String getUpdateFilter() { return mUpdateFilter;}
    public void setUpdateFilter(String pUpdateFilter) {mUpdateFilter = pUpdateFilter;}

    public String[] getUpdateTableValues() { return mUpdateTableValues;}
    public void setUpdateTableValues(String[] pUpdateTableValues) {mUpdateTableValues = pUpdateTableValues;}

    public String[] getUpdateValues() { return mUpdateValues;}
    public void setUpdateValues(String[] pUpdateValues) {mUpdateValues = pUpdateValues;}
    public String getUpdateValue(int ii) {
      if(mUpdateValues==null || mUpdateValues.length-1<ii) {
        return "";
      }
      return mUpdateValues[ii];
    }
    public void setUpdateValue(int ii, String pUpdateValue) {
      if(mUpdateValues==null || mUpdateValues.length-1<ii) {
        return;
      }
      mUpdateValues[ii] = pUpdateValue;
    }

    public UploadSkuView[] getUploadSkus() {return mUploadSkus;}
    public void setUploadSkus(UploadSkuView[] pUploadSkus) {mUploadSkus = pUploadSkus; }


    public boolean getSelectToCreate(int ii) {
      if(mUploadSkus==null || mUploadSkus.length<ii) {
        return false;
      }
      return mUploadSkus[ii].getSelectFlag();
    }
    public void setSelectToCreate(int ii, boolean val) {
      if(mUploadSkus==null || mUploadSkus.length<ii) {
        return;
      }
      mUploadSkus[ii].setSelectFlag(val);
    }

    public boolean getSelectToAssign(int ii) {
      if(mItemsToMatch==null || mItemsToMatch.size()<ii) {
        return false;
      }
      UploadSkuView upVw = (UploadSkuView) mItemsToMatch.get(ii);
      return upVw.getSelectFlag();
    }
    public void setSelectToAssign(int ii, boolean val) {
      if(mItemsToMatch==null || mItemsToMatch.size()<ii) {
        return;
      }
      UploadSkuView upVw = (UploadSkuView) mItemsToMatch.get(ii);
      upVw.setSelectFlag(val);
    }


    public UploadSkuViewVector getItemsToMatch() {return mItemsToMatch;}
    public void setItemsToMatch(UploadSkuViewVector pItemsToMatch) {mItemsToMatch = pItemsToMatch; }

    public int getEditItemId() {return mEditItemId;}
    public void setEditItemId(int pEditItemId) {mEditItemId = pEditItemId; }

    public  DistributorDataVector getStoreDistributors() {return mStoreDistributors;}
    public void setStoreDistributors( DistributorDataVector pVal) {mStoreDistributors = pVal;}

    public  ManufacturerDataVector getStoreManufacturers() {return mStoreManufacturers;}
    public void setStoreManufacturers( ManufacturerDataVector pVal) {mStoreManufacturers = pVal;}

    public CatalogCategoryDataVector getStoreCategories() {return mStoreCategories;}
    public void setStoreCategories(CatalogCategoryDataVector pVal) {mStoreCategories = pVal;}

    public BusEntityDataVector getCertifiedCompanies() {return mCertifiedCompanies;}
    public void setCertifiedCompanies(BusEntityDataVector pVal) {mCertifiedCompanies = pVal;}

    public String getSkuNumInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      usVw = (UploadSkuView) mItemsToMatch.get(ind);
      return usVw.getUploadSku().getSkuNum();
    }

    public void setSkuNumInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setSkuNum(pVal);
      return;
    }

    public String getShortDescInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      usVw = (UploadSkuView) mItemsToMatch.get(ind);
      return usVw.getUploadSku().getShortDesc();
    }

    public void setShortDescInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setShortDesc(pVal);
      return;
    }

    public String getLongDescInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getLongDesc();
    }
    public void setLongDescInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setLongDesc(pVal);
      return;
    }

    public String getOtherDescInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getOtherDesc();
    }
    public void setOtherDescInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setOtherDesc(pVal);
      return;
    }

    public String getUnspscCodeInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getUnspscCode();
    }
    public void setUnspscCodeInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setUnspscCode(pVal);
      return;
    }

    public String getNsnInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getNsn();
    }
    public void setNsnInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setNsn(pVal);
      return;
    }

    public String getPsnInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getPsn();
    }
    public void setPsnInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setPsn(pVal);
      return;
    }

    public String getSkuColorInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getSkuColor();
    }
    public void setSkuColorInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setSkuColor(pVal);
      return;
    }

    public String getSkuPackInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getSkuPack();
    }
    public void setSkuPackInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setSkuPack(pVal);
      return;
    }

    public String getSkuSizeInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getSkuSize();
    }
    public void setSkuSizeInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setSkuSize(pVal);
      return;
    }

    public String getSkuUomInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getSkuUom();
    }
    public void setSkuUomInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setSkuUom(pVal);
      return;
    }

    public String getCategoryInp(int ind) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if(usVw == null)
            return null;
        String categoryName = usVw.getUploadSku().getCategory();
        String adminCategoryName = usVw.getUploadSku().getAdminCategory();
        return CategoryUtil.encodeCategoryNames(categoryName, adminCategoryName);
    }

    public void setCategoryInp(int ind, String pVal) {
        if (!Utility.isSet(pVal))
            return;
        if (EMPTY_PROPERTY_VALUE.equals(pVal))
            pVal="";
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if (usVw == null)
            return;
        String[] names = CategoryUtil.decodeCategoryNames(pVal);
        if (names == null || names.length == 0) {
            usVw.getUploadSku().setCategory(pVal);
        }
        else {
            usVw.getUploadSku().setCategory(names[0]);
            if (names.length > 1) {
                usVw.getUploadSku().setAdminCategory(names[1]);
            }
        }
        return;
    }

    public String getManufNameInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getManufName();
    }
    public void setManufNameInp(int ind, String pVal) {
      if(!Utility.isSet(pVal)) return;
      if("<<< Clear >>>".equals(pVal)) pVal="";
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setManufName(pVal);
      return;
    }

    public String getManufSkuInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getManufSku();
    }
    public void setManufSkuInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setManufSku(pVal);
      return;
    }

    public String getManufPackInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getManufPack();
    }
    public void setManufPackInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setManufPack(pVal);
      return;
    }

    public String getManufUomInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getManufUom();
    }
    public void setManufUomInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setManufUom(pVal);
      return;
    }

    public String getDistNameInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getDistName();
    }
    public void setDistNameInp(int ind, String pVal) {
      if(!Utility.isSet(pVal)) return;
      if("<<< Clear >>>".equals(pVal)) pVal="";
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setDistName(pVal);
      return;
    }

    public String getDistSkuInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getDistSku();
    }
    public void setDistSkuInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setDistSku(pVal);
      return;
    }

    public String getDistPackInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getDistPack();
    }
    public void setDistPackInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setDistPack(pVal);
      return;
    }

    public String getDistUomInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getDistUom();
    }
    public void setDistUomInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setDistUom(pVal);
      return;
    }
    public String getGenManufNameInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getGenManufName();
    }
    public void setGenManufNameInp(int ind, String pVal) {
      if(!Utility.isSet(pVal)) return;
      if("<<< Clear >>>".equals(pVal)) pVal="";
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setGenManufName(pVal);
      return;
    }

    public String getGenManufSkuInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getGenManufSku();
    }

    public void setGenManufSkuInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setGenManufSku(pVal);
      return;
    }

    public String getListPriceInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getListPrice();
    }
    public void setListPriceInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setListPrice(pVal);
      return;
    }

    public String getDistCostInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getDistCost();
    }
    public void setDistCostInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setDistCost(pVal);
      return;
    }

    public String getDistUomMultInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getDistUomMult();
    }
    public void setDistUomMultInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setDistUomMult(pVal);
      return;
    }

    public String getMfcpInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getMfcp();
    }
    public void setMfcpInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setMfcp(pVal);
      return;
    }

    public String getBaseCostInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getBaseCost();
    }
    public void setBaseCostInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setBaseCost(pVal);
      return;
    }

    public String getSplInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getSpl();
    }
    public void setSplInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setSpl(pVal);
      return;
    }

    public String getTaxExemptInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getTaxExempt();
    }
    public void setTaxExemptInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setTaxExempt(pVal);
      return;
    }

    public String getCatalogPriceInp(int ind) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return null;
      return usVw.getUploadSku().getCatalogPrice();
    }
    public void setCatalogPriceInp(int ind, String pVal) {
      UploadSkuView usVw = getUploadSkuByInd(ind);
      if(usVw==null) return;
      usVw.getUploadSku().setCatalogPrice(pVal);
      return;
    }
    public String getGreenCertifiedInp(int ind) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if(usVw==null) return null;
        return usVw.getUploadSku().getGreenCertified();
    }
    public void setGreenCertifiedInp(int ind, String pVal) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if(usVw==null) return;
        usVw.getUploadSku().setGreenCertified(pVal);
        return;
    }
    public String getCustomerSkuInp(int ind) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if(usVw==null) return null;
        return usVw.getUploadSku().getCustomerSkuNum();
    }
    public void setCustomerSkuInp(int ind, String pVal) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if(usVw==null) return;
        usVw.getUploadSku().setCustomerSkuNum(pVal);
        return;
    }
    public String getShippingWeightInp(int ind) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if (usVw == null)
            return null;
        return usVw.getUploadSku().getShipWeight();
    }
    public void setShippingWeightInp(int ind, String pVal) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if (usVw == null)
            return;
        usVw.getUploadSku().setShipWeight(pVal);
    }
    public String getWeightUnitInp(int ind) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if (usVw == null)
            return null;
        return usVw.getUploadSku().getWeightUnit();
    }
    public void setWeightUnitInp(int ind, String pVal) {
        UploadSkuView usVw = getUploadSkuByInd(ind);
        if (usVw == null)
            return;
        usVw.getUploadSku().setWeightUnit(pVal);
    }
   private UploadSkuView getUploadSkuByInd(int ind) {
      if(mUploadSkus==null || mUploadSkus.length<ind) {
        return null;
      }
      UploadSkuView usVw = mUploadSkus[ind];
      return usVw;
   }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
      mSelectedColumns = new int[0];
      mSelectedRows = new int[0];
      mShowProcessedFl = false;
      if(mUploadSkus!=null) {
        for(int ii=0; ii<mUploadSkus.length; ii++) {
          mUploadSkus[ii].setSelectFlag(false);
        }
      }
      if(mItemsToMatch!=null) {
        for(Iterator iter=mItemsToMatch.iterator(); iter.hasNext();){
          UploadSkuView usVw = (UploadSkuView) iter.next();
          usVw.setSelectFlag(false);
        }
      }
    }

    //filter
    public String getFilterMatched() {return mFilterMatched;}
    public void setFilterMatched(String pFilterMatched) {mFilterMatched = pFilterMatched; }

    public String getFilterSku() {return mFilterSku;}
    public void setFilterSku(String pFilterSku) {mFilterSku = pFilterSku; }

    public String getFilterSkuType() {return mFilterSkuType;}
    public void setFilterSkuType(String pFilterSkuType) {mFilterSkuType = pFilterSkuType; }

    public String getFilterManuf() {return mFilterManuf;}
    public void setFilterManuf(String pFilterManuf) {mFilterManuf = pFilterManuf; }

    public String getFilterDist() {return mFilterDist;}
    public void setFilterDist(String pFilterDist) {mFilterDist = pFilterDist; }

    public String getFilterName() {return mFilterName;}
    public void setFilterName(String pFilterName) {mFilterName = pFilterName; }

    public String getFilterCateg() {return mFilterCateg;}
    public void setFilterCateg(String pFilterCateg) {mFilterCateg = pFilterCateg; }



  static public void setSkuProperty(UploadSkuData usD, String property, String value) {
    if(value!=null) value = value.trim();
    if("Sku Num".equals(property)) {
      usD.setSkuNum(value);
    }
    if("Short Desc".equals(property)) {
      usD.setShortDesc(value);
    }
    else if("Long Desc".equals(property)) {
      usD.setLongDesc(value);
    }
    else if("Other Desc".equals(property)) {
      usD.setOtherDesc(value);
    }
    else if("UNSPSC".equals(property)) {
      usD.setUnspscCode(value);
    }
    else if("NSN".equals(property)) {
      usD.setNsn(value);
    }
    else if("PSN".equals(property)) {
      usD.setPsn(value);
    }
    else if("Color".equals(property)) {
      usD.setSkuColor(value);
    }
    else if("Pack".equals(property)) {
      usD.setSkuPack(value);
    }
    else if("Size".equals(property)) {
      usD.setSkuSize(value);
    }
    else if("UOM".equals(property)) {
      usD.setSkuUom(value);
    }
    else if(CATEGORY_PROPERTY.equals(property)) {
        String[] names = CategoryUtil.decodeCategoryNames(value);
        if (names == null || names.length == 0) {
            usD.setCategory(value);
        }
        else {
            usD.setCategory(names[0]);
            if (names.length > 1) {
                usD.setAdminCategory(names[1]);
            }
        }
    }
    else if("Manufacturer".equals(property)) {
      usD.setManufName(value);
    }
    else if("Manuf. Sku".equals(property)) {
      usD.setManufSku(value);
    }
    else if("Manuf. Pack".equals(property)) {
      usD.setManufPack(value);
    }
    else if("Manuf. UOM".equals(property)) {
      usD.setManufUom(value);
    }
    else if("Distributor".equals(property)) {
      usD.setDistName(value);
    }
    else if("Dist. Sku".equals(property)) {
      usD.setDistSku(value);
    }
    else if("Dist. Pack".equals(property)) {
      usD.setDistPack(value);
    }
    else if("Dist. UOM".equals(property)) {
      usD.setDistUom(value);
    }
    else if("Gen. Manufacturer".equals(property)) {
      usD.setGenManufName(value);
    }
    else if("Gen. Manuf. Sku".equals(property)) {
      usD.setGenManufSku(value);
    }
    else if("List Price".equals(property)) {
      usD.setListPrice(value);
    }
    else if("Dist. Cost".equals(property)) {
      usD.setDistCost(value);
    }
    else if("MFCP".equals(property)) {
      usD.setMfcp(value);
    }
    else if("Base Cost".equals(property)) {
      usD.setBaseCost(value);
    }
    else if("Dist. UOM. Mult".equals(property)) {
      usD.setDistUomMult(value);
    }
    else if("SPL".equals(property)) {
      usD.setSpl(value);
    }
    else if("Tax Exempt".equals(property)) {
      usD.setTaxExempt(value);
    }
    else if("Catalog Price".equals(property)) {
      usD.setCatalogPrice(value);
    }
    else if ("Image URL".equals(property)) {
      usD.setImageUrl(value);
    }
    else if ("MSDS URL".equals(property)) {
      usD.setMsdsUrl(value);
    }
    else if ("DED URL".equals(property)) {
      usD.setDedUrl(value);
    }
    else if ("Prod Spec URL".equals(property)) {
      usD.setProdSpecUrl(value);
    }
    else if (GREEN_CERTIFIED.equals(property)) {
      usD.setGreenCertified(value);
    }
    else if (CUSTOMER_SKU.equals(property)) {
      usD.setCustomerSkuNum(value);
    }
    else if (SHIPPING_WEIGHT.equals(property)) {
        usD.setShipWeight(value);
    }
    else if (WEIGHT_UNIT.equals(property)) {
        usD.setWeightUnit(value);
    }
    else if ("Cust. Desc".equals(property)) {
        usD.setCustomerDesc(value);
    }
    else if (THUMBNAIL_URL.equals(property)) {
      usD.setThumbnailUrl(value);
    }
    else if (SERVICE_FEE_CODE.equals(property)) {
        usD.setServiceFeeCode(value);
    }
    return;
  }

  static public String getSkuProperty(UploadSkuData usD, String property) {
    String valS = null;
    if("Sku Num".equals(property)) {
      valS = usD.getSkuNum();
    }
    if("Short Desc".equals(property)) {
      valS = usD.getShortDesc();
    }
    else if("Long Desc".equals(property)) {
      valS = usD.getLongDesc();
    }
    else if("Other Desc".equals(property)) {
      valS = usD.getOtherDesc();
    }
    else if("UNSPSC".equals(property)) {
      valS = usD.getUnspscCode();
    }
    else if("NSN".equals(property)) {
      valS = usD.getNsn();
    }
    else if("PSN".equals(property)) {
      valS = usD.getPsn();
    }
    else if("Color".equals(property)) {
      valS = usD.getSkuColor();
    }
    else if("Pack".equals(property)) {
      valS = usD.getSkuPack();
    }
    else if("Size".equals(property)) {
      valS = usD.getSkuSize();
    }
    else if("UOM".equals(property)) {
      valS = usD.getSkuUom();
    }
    else if(CATEGORY_PROPERTY.equals(property)) {
        String categoryName = usD.getCategory();
        String adminCategoryName = usD.getAdminCategory();
        valS = CategoryUtil.encodeCategoryNames(categoryName, adminCategoryName);
    }
    else if("Manufacturer".equals(property)) {
      valS = usD.getManufName();
    }
    else if("Manuf. Sku".equals(property)) {
      valS = usD.getManufSku();
    }
    else if("Manuf. Pack".equals(property)) {
      valS = usD.getManufPack();
    }
    else if("Manuf. UOM".equals(property)) {
      valS = usD.getManufUom();
    }

    else if("Distributor".equals(property)) {
      valS = usD.getDistName();
    }
    else if("Dist. Sku".equals(property)) {
      valS = usD.getDistSku();
    }
    else if("Dist. Pack".equals(property)) {
      valS = usD.getDistPack();
    }
    else if("Dist. UOM".equals(property)) {
      valS = usD.getDistUom();
    }
    else if("Gen. Manufacturer".equals(property)) {
      valS = usD.getGenManufName();
    }
    else if("Gen. Manuf. Sku".equals(property)) {
      valS = usD.getGenManufSku();
    }

    else if("List Price".equals(property)) {
      valS = strBDstr(usD.getListPrice());
    }
    else if("Dist. Cost".equals(property)) {
      valS = strBDstr(usD.getDistCost());
    }
    else if("MFCP".equals(property)) {
      valS = strBDstr(usD.getMfcp());
    }
    else if("Base Cost".equals(property)) {
      valS = strBDstr(usD.getBaseCost());
    }
    else if("Dist. UOM. Mult".equals(property)) {
      valS = strBDstr(usD.getDistUomMult());
    }
    else if("SPL".equals(property)) {
      valS = usD.getSpl();
    }
    else if("Tax Exempt".equals(property)) {
      valS = usD.getTaxExempt();
    }
    else if("Catalog Price".equals(property)) {
      valS = strBDstr(usD.getCatalogPrice());
    }
    else if("Image URL".equals(property)) {
      valS = usD.getImageUrl();
    }
    else if("MSDS URL".equals(property)) {
      valS = usD.getMsdsUrl();
    }
    else if("DED URL".equals(property)) {
      valS = usD.getDedUrl();
    }
    else if("Prod Spec URL".equals(property)) {
      valS = usD.getProdSpecUrl();
    }
    else if (GREEN_CERTIFIED.equals(property)) {
      valS = usD.getGreenCertified();
    }
    else if (CUSTOMER_SKU.equals(property)) {
        valS = usD.getCustomerSkuNum();
    }
    else if (SHIPPING_WEIGHT.equals(property)) {
        valS = usD.getShipWeight();
    }
    else if (WEIGHT_UNIT.equals(property)) {
        valS = usD.getWeightUnit();
    }
    else if ("Cust. Desc".equals(property)) {
        valS = usD.getCustomerDesc();
    }
    else if (THUMBNAIL_URL.equals(property)) {
        valS = usD.getThumbnailUrl();
    }
    else if (SERVICE_FEE_CODE.equals(property)) {
        valS = usD.getServiceFeeCode();
    }
    return valS;
  }


  private static String strBDstr(String pVal) {
    String retVal = pVal;
    if(!Utility.isSet(pVal)) return retVal;
    int scale = StringUtils.getDecimalPoints(pVal);

    try {
      double valDb = Double.parseDouble(pVal);
      BigDecimal valBD = (new BigDecimal(valDb)).setScale(scale, BigDecimal.ROUND_HALF_UP);
      retVal = valBD.toString();
    } catch(Exception exc) {
      return retVal;
    }
    return retVal;
  }

    public static String[] getWeightUnitValues() {
        String values[] = new String[4];
        values[0] = RefCodeNames.WEIGHT_UNIT.OUNCE;
            values[1] = RefCodeNames.WEIGHT_UNIT.POUND;
            values[2] = RefCodeNames.WEIGHT_UNIT.KILOGRAMME;
            values[3] = RefCodeNames.WEIGHT_UNIT.GRAMME;
            return values;
    }
    
    public void setAllowMixedCategoryAndItemUnderSameParent(boolean pAllowMixedCategoryAndItemUnderSameParent) {
        this.allowMixedCategoryAndItemUnderSameParent = pAllowMixedCategoryAndItemUnderSameParent;
    }

    public boolean getAllowMixedCategoryAndItemUnderSameParent() {
        return allowMixedCategoryAndItemUnderSameParent;
    }
}



