/**
 *  Title: SpendingEstimatorForm Description: This is the Struts ActionForm class
 *  for spending estimator.
 *  Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     YKupershmidt
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.ArrayList;
import com.cleanwise.service.api.util.RefCodeNames;

public final class EstimatorMgrForm extends ActionForm {

  private int mSelectedPage = 0;
  private CleaningProcDataVector mCleaningProcedures = null;
  private int mCleanigProcSelected = 0;
  private ProdApplJoinViewVector mProcedureProducts = null;
  private ProdApplJoinViewVector mBufferedProducts = null;
  private CleaningProcDataVector mBufferedProcedures = null;
  private ProdUomPackJoinViewVector mProducts = null;
  private ProdUomPackJoinViewVector mFilteredProducts = null;
  //private int[] mFilteredProductsIndex = new int[0];
  private int[] mProductSelector = new int[0];
  private int[] mProcProdSelector = new int[0];
  private String mCategoryFilter = "";
  private String mShortDescFilter = "";
  private String mManuFilter = "";
  private String mSkuFilter = "";
  private String mSkuTypeFilter = "System";
  
  
  private String mScheduleTypeFilter = "";
  private CleaningScheduleJoinViewVector mCleaningSchedules = null;
  
  public int getSelectedPage() {return mSelectedPage;}
  public void setSelectedPage(int pVal) {mSelectedPage = pVal;}

  public CleaningProcDataVector getCleaningProcedures() {return mCleaningProcedures;}
  public void setCleaningProcedures(CleaningProcDataVector pVal) {mCleaningProcedures = pVal;}

  public int getCleanigProcSelected() {return mCleanigProcSelected;}
  public void setCleanigProcSelected(int pVal) {mCleanigProcSelected = pVal;}
  
  public ProdApplJoinViewVector getProcedureProducts() {return mProcedureProducts;}
  public void setProcedureProducts(ProdApplJoinViewVector pVal) {mProcedureProducts = pVal;}
   
  public CleaningProcDataVector getBufferedProcedures() {return mBufferedProcedures;}
  public void setBufferedProcedures(CleaningProcDataVector pVal) {mBufferedProcedures = pVal;}

  public ProdApplJoinViewVector getBufferedProducts() {return mBufferedProducts;}
  public void setBufferedProducts(ProdApplJoinViewVector pVal) {mBufferedProducts = pVal;}
  
  public ProdUomPackJoinViewVector getProducts() {return mProducts;}
  public void setProducts(ProdUomPackJoinViewVector pVal) {mProducts = pVal;}

  public ProdUomPackJoinViewVector getFilteredProducts() {return mFilteredProducts;}
  public void setFilteredProducts(ProdUomPackJoinViewVector pVal) {mFilteredProducts = pVal;}
  
  public String getUnitSizeInp(int pInd) {
    if(mFilteredProducts==null && pInd>mFilteredProducts.size()) return "";
    ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) mFilteredProducts.get(pInd);
    return pupJVw.getUnitSizeInp();
  }
  public void setUnitSizeInp(int pInd,String pVal) {
    if(mFilteredProducts==null && pInd>mFilteredProducts.size()) return;
    ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) mFilteredProducts.get(pInd);
    pupJVw.setUnitSizeInp(pVal);
    return;
  }
  public String getUnitCdInp(int pInd) {
    if(mFilteredProducts==null && pInd>mFilteredProducts.size()) return "";
    ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) mFilteredProducts.get(pInd);
    return pupJVw.getUnitCdInp();
  }
  public void setUnitCdInp(int pInd,String pVal) {
    if(mFilteredProducts==null && pInd>mFilteredProducts.size()) return;
    ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) mFilteredProducts.get(pInd);
    pupJVw.setUnitCdInp(pVal);
    return;
  }
  public String getPackQtyInp(int pInd) {
    if(mFilteredProducts==null && pInd>mFilteredProducts.size()) return "";
    ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) mFilteredProducts.get(pInd);
    return pupJVw.getPackQtyInp();
  }
  public void setPackQtyInp(int pInd,String pVal) {
    if(mFilteredProducts==null && pInd>mFilteredProducts.size()) return;
    ProdUomPackJoinView pupJVw = (ProdUomPackJoinView) mFilteredProducts.get(pInd);
    pupJVw.setPackQtyInp(pVal);
    return;
  }

  //public int[] getFilteredProductsIndex() {return mFilteredProductsIndex;}
  //public void setFilteredProductsIndex(int[] pVal) {mFilteredProductsIndex = pVal;}
  
  public int[] getProductSelector() {return mProductSelector;}
  public void setProductSelector(int[] pVal) {mProductSelector = pVal;}
  
  public int[] getProcProdSelector() {return mProcProdSelector;}
  public void setProcProdSelector(int[] pVal) {mProcProdSelector = pVal;}
  
  public String getCategoryFilter() {return mCategoryFilter;}
  public void setCategoryFilter(String pVal) {mCategoryFilter = pVal;}

  public String getShortDescFilter() {return mShortDescFilter;}
  public void setShortDescFilter(String pVal) {mShortDescFilter = pVal;}

  public String getManuFilter() {return mManuFilter;}
  public void setManuFilter(String pVal) {mManuFilter = pVal;}

  public String getSkuFilter() {return mSkuFilter;}
  public void setSkuFilter(String pVal) {mSkuFilter = pVal;}

  public String getSkuTypeFilter() {return mSkuTypeFilter;}
  public void setSkuTypeFilter(String pVal) {mSkuTypeFilter = pVal;}

  public String getScheduleTypeFilter() {return mScheduleTypeFilter;}
  public void setScheduleTypeFilter(String pVal) {mScheduleTypeFilter = pVal;}

  public CleaningScheduleJoinViewVector getCleaningSchedules() {return mCleaningSchedules;}
  public void setCleaningSchedules(CleaningScheduleJoinViewVector pVal) {mCleaningSchedules = pVal;}
   
  public String getCleaningProcFrequency(int pInd) {
    if(mCleaningSchedules==null || pInd>=mCleaningSchedules.size()) {
      return "";
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mCleaningSchedules.get(pInd);
    return csjVw.getFrequency();
  }
  public void setCleaningProcFrequency(int pInd,String pVal) {
    if(mCleaningSchedules==null || pInd>=mCleaningSchedules.size()) {
      return;
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mCleaningSchedules.get(pInd);
    csjVw.setFrequency(pVal);
  }
  public String getCleaningProcTimePeriodCd(int pInd) {
    if(mCleaningSchedules==null || pInd>=mCleaningSchedules.size()) {
      return "";
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mCleaningSchedules.get(pInd);
    return csjVw.getTimePeriodCd();
  }
  public void setCleaningProcTimePeriodCd(int pInd,String pVal) {
    if(mCleaningSchedules==null || pInd>=mCleaningSchedules.size()) {
      return;
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mCleaningSchedules.get(pInd);
    csjVw.setTimePeriodCd(pVal);
  }

  public String getCleaningProcFloorMachine(int pInd) {
    if(mCleaningSchedules==null || pInd>=mCleaningSchedules.size()) {
      return "";
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mCleaningSchedules.get(pInd);
    return csjVw.getFloorMachine();
  }
  public void setCleaningProcFloorMachine(int pInd,String pVal) {
    if(mCleaningSchedules==null || pInd>=mCleaningSchedules.size()) {
      return;
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mCleaningSchedules.get(pInd);
    csjVw.setFloorMachine(pVal);
  }
  
  public String getProcProdDilution(int pInd) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getDilutionRate();
    return val;
  }
  public void setProcProdDilution(int pInd,String pVal) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setDilutionRate(pVal);
    return;
  }
  
  public String getProcProdRate(int pInd) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUsageRate();
    return val;
  }
  public void setProcProdRate(int pInd,String pVal) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUsageRate(pVal);
    return;
  }

  public String getProcProdRateNumerator(int pInd) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdNumerator();
    return val;
  }
  public void setProcProdRateNumerator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdNumerator(pVal);
    return;
  }
  public String getProcProdRateDenominator(int pInd) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator();
    return val;
  }
  public void setProcProdRateDenominator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator(pVal);
    return;
  }
  public String getProcProdRateDenominator1(int pInd) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator1();
    return val;
  }
  public void setProcProdRateDenominator1(int pInd,String pVal) {
    ProdApplJoinView pajVw =getProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator1(pVal);
    return;
  }

  private ProdApplJoinView getProcProduct(int pInd) {
    ProdApplJoinView pajVw = (ProdApplJoinView) mProcedureProducts.get(pInd);
    return pajVw;
  }

  
  ////////////////////////////////////////////////////////////////////////

  ////////////////////////////////////////////////////////////////////////
   /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      mProductSelector = new int[0]; 
      mProcProdSelector = new int[0];
    }

  /**
     *  <code>validate</code> method is a stub.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     *@return          an <code>ActionErrors</code> value
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        // No validation necessary.
        return null;
    }

}

