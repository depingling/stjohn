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

public final class SpendingEstimatorForm extends ActionForm {
  private int mSelectedPage = 0;
  private int mFloorProcessStep = 0;
  private int mPaperPlusProcessStep = 0;
  private int mRestroomProcessStep = 0;
  private int mAllocatedProcessStep = 0;
  private int mOtherProcessStep = 0;
  
  private EstimatorFacilityDataVector mFacilities = null;
  private EstimatorFacilityDataVector mTemplates = null;
  private ArrayList mFacilityGroups = null;
    
  private EstimatorFacilityJoinView mFacilityJoin = null;
  private EstimatorFacilityJoinView mDefaultFacilityJoin = null;
  private EstimatorFacilityData mDefaultFacility = null;
  private OrderGuideItemDescData[] mEstimatorProducts = null;
  private CatalogDataVector mCatalogs = null;
  private int mCatalogId = 0;
  private int mOrderGuideId = 0;
  private int[] mProductFilter = new int[0];
  
  private boolean mTemplateFl = false;
  private String mName = "";
  private String mFacilityGroup = "";
  private String mNewFacilityGroup = "";
  private String mFacilityQty = "";
  private String mWorkingDayYearQty = "";
  private String mFacilityTypeCd = "";
  private String mStationQty = "";
  private String mPersonnelQty = "";
  private String mVisitorQty = "";
  private String mVisitorTrafficCd = "";

  //Bathroom 
  private String mAppearanceStandardCd = "";
  private String mBathroomQty = "";
  private String mToiletBathroomQty = "";
  private String mVisitorBathroomPer = "";
  private String mVisitorToiletTissuePer = "";
  private String mWashHandPerDay = "";
  private String mTissueUsagePerDay = "";
  private String mToiletLinerPerDay = "";
  private String mShowerQty = "";

  //Liners
  private String mSmallLinerPerDay = "";
  private String mCommonAreaLinerPerDay = "";
  private String mLargeLinerPerDay = "";
  private String mLargeLinerCaLinerQty = "";
  
  //Floors
  private String mGrossFootage = "";
  private String mCleanableFootagePercent = "";
  private String mNetCleanableFootage = "";

  private String mBaseboardPercent = "";
  private String mEstimatedItemsFactor = "";
  private BigDecimal mEstimatedItemsAmount = null;
  private String mChemicalUsageModelCd = "";

  private String mFloorMachine = "Rotary";

  private String[] mFloorTypes = {
    RefCodeNames.FLOOR_TYPE_CD.CARPET,
    RefCodeNames.FLOOR_TYPE_CD.CERAMIC_TILE,
    RefCodeNames.FLOOR_TYPE_CD.CONCRETE,
    RefCodeNames.FLOOR_TYPE_CD.TERRAZZO,
    RefCodeNames.FLOOR_TYPE_CD.VCT_TILE,
    RefCodeNames.FLOOR_TYPE_CD.WOOD
  };

  private boolean[] mFloorTypeFl = new boolean[mFloorTypes.length];
  private boolean[] mFloorTypeFlHt = new boolean[mFloorTypes.length];
  private boolean[] mFloorTypeFlMt = new boolean[mFloorTypes.length];
  private boolean[] mFloorTypeFlLt = new boolean[mFloorTypes.length];

  private String[] mFloorTypePercents = new String[mFloorTypes.length];
  private String[] mFloorTypePercentsHt = new String[mFloorTypes.length];
  private String[] mFloorTypePercentsMt = new String[mFloorTypes.length];
  private String[] mFloorTypePercentsLt = new String[mFloorTypes.length];
  
  private EstimatorProdResultViewVector mFloorProdResults = null;
  private EstimatorProdResultViewVector mRestroomProdResults = null;
  private EstimatorProdResultViewVector mPaperPlusProdResults = null;
  private EstimatorProdResultViewVector mOtherProdResults = null;

  private PaperPlusApplViewVector mPaperPlusProducts = null;
  private boolean[] mPaperPlusProductApplyFl = null;
  private PaperPlusApplViewVector mPaperPlusResult = null;
  
  private CleaningScheduleJoinViewVector mRestroomCleaningSchedules = null;
  private CleaningScheduleJoinViewVector mFloorCleaningSchedules = null;
  private CleaningScheduleJoinViewVector mPaperPlusSupplySchedules = null;
  private CleaningScheduleJoinViewVector mOtherSchedules = null;
  
  private AllocatedCategoryViewVector mAllocatedCategories = null;
  private BigDecimal mSumProductAmount = null;
  private String mTotalPercent = "";
  
  //Reports 
  private GenericReportViewVector mReports = null;
  private int mReportId = 0;
  private GenericReportData mReport = null;
  private GenericReportControlViewVector mGenericControls = null;
  private String[] mRunForModels = new String[0];
  
  //Methods
  public int getSelectedPage() {return mSelectedPage;}
  public void setSelectedPage(int pVal) {mSelectedPage = pVal;}
  
  public int getFloorProcessStep() {return mFloorProcessStep;}
  public void setFloorProcessStep(int pVal) {mFloorProcessStep = pVal;}
  
  public int getPaperPlusProcessStep() {return mPaperPlusProcessStep;}
  public void setPaperPlusProcessStep(int pVal) {mPaperPlusProcessStep = pVal;}

  public int getRestroomProcessStep() {return mRestroomProcessStep;}
  public void setRestroomProcessStep(int pVal) {mRestroomProcessStep = pVal;}
  
  public int getAllocatedProcessStep() {return mAllocatedProcessStep;}
  public void setAllocatedProcessStep(int pVal) {mAllocatedProcessStep = pVal;}

  public int getOtherProcessStep() {return mOtherProcessStep;}
  public void setOtherProcessStep(int pVal) {mOtherProcessStep = pVal;}
  
  public EstimatorFacilityDataVector getFacilities() {return mFacilities;}
  public void setFacilities(EstimatorFacilityDataVector pVal) {mFacilities = pVal;}

  public EstimatorFacilityDataVector getTemplates() {return mTemplates;}
  public void setTemplates(EstimatorFacilityDataVector pVal) {mTemplates = pVal;}
  
  public ArrayList getFacilityGroups() {return mFacilityGroups;}
  public void setFacilityGroups(ArrayList pVal) {mFacilityGroups = pVal;}
  
  public EstimatorFacilityJoinView getFacilityJoin() {return mFacilityJoin;}
  public void setFacilityJoin(EstimatorFacilityJoinView pVal) {mFacilityJoin = pVal;}
  
  public EstimatorFacilityJoinView getDefaultFacilityJoin() {return mDefaultFacilityJoin;}
  public void setDefaultFacilityJoin(EstimatorFacilityJoinView pVal) {mDefaultFacilityJoin = pVal;}

  public EstimatorFacilityData getDefaultFacility() {return mDefaultFacility;}
  public void setDefaultFacility(EstimatorFacilityData pVal) {mDefaultFacility = pVal;}
  
  public OrderGuideItemDescData[] getEstimatorProducts() {return mEstimatorProducts;}
  public void setEstimatorProducts(OrderGuideItemDescData[] pVal) {mEstimatorProducts = pVal;}
  
  public int[] getProductFilter() {return mProductFilter;}
  public void setProductFilter(int[] pVal) {mProductFilter = pVal;}

  public CatalogDataVector getCatalogs() {return mCatalogs;}
  public void setCatalogs(CatalogDataVector pVal) {mCatalogs = pVal;}

  public int getCatalogId() {return mCatalogId;}
  public void setCatalogId(int pVal) {mCatalogId = pVal;}
  public String getCatalogName(int pCatalogId) {
    if(mCatalogs==null) return "";
    for(Iterator iter=mCatalogs.iterator(); iter.hasNext();) {
      CatalogData cD = (CatalogData) iter.next();
      if(cD.getCatalogId()==pCatalogId) return cD.getShortDesc();
    }
    return "";
  }

  public int getOrderGuideId() {return mOrderGuideId;}
  public void setOrderGuideId(int pVal) {mOrderGuideId = pVal;}

  public String getName() {return mName;}
  public void setName(String pVal) {mName = pVal;}

  public String getFacilityGroup() {return mFacilityGroup;}
  public void setFacilityGroup(String pVal) {mFacilityGroup = pVal;}
  
  public String getNewFacilityGroup() {return mNewFacilityGroup;}
  public void setNewFacilityGroup(String pVal) {mNewFacilityGroup = pVal;}
  
  public boolean getTemplateFl() {return mTemplateFl;}
  public void setTemplateFl(boolean pVal) {mTemplateFl = pVal;}

  
  public String getFacilityQty() {return mFacilityQty;}
  public void setFacilityQty(String pVal) {mFacilityQty = pVal;}

  public String getWorkingDayYearQty() {return mWorkingDayYearQty;}
  public void setWorkingDayYearQty(String pVal) {mWorkingDayYearQty = pVal;}

  public String getFacilityTypeCd() {return mFacilityTypeCd;}
  public void setFacilityTypeCd(String pVal) {mFacilityTypeCd = pVal;}

  public String getStationQty() {return mStationQty;}
  public void setStationQty(String pVal) {mStationQty = pVal;}

  public String getVisitorTrafficCd() {return mVisitorTrafficCd;}
  public void setVisitorTrafficCd(String pVal) {mVisitorTrafficCd = pVal;}
  
  public String getAppearanceStandardCd() {return mAppearanceStandardCd;}
  public void setAppearanceStandardCd(String pVal) {mAppearanceStandardCd = pVal;}

  public String getBathroomQty() {return mBathroomQty;}
  public void setBathroomQty(String pVal) {mBathroomQty = pVal;}

  public String getToiletBathroomQty() {return mToiletBathroomQty;}
  public void setToiletBathroomQty(String pVal) {mToiletBathroomQty = pVal;}
 
  public String getVisitorBathroomPer() {return mVisitorBathroomPer;}
  public void setVisitorBathroomPer(String pVal) {mVisitorBathroomPer = pVal;}

  public String getVisitorToiletTissuePer() {return mVisitorToiletTissuePer;}
  public void setVisitorToiletTissuePer(String pVal) {mVisitorToiletTissuePer = pVal;}

  public String getWashHandPerDay() {return mWashHandPerDay;}
  public void setWashHandPerDay(String pVal) {mWashHandPerDay = pVal;}

  public String getTissueUsagePerDay() {return mTissueUsagePerDay;}
  public void setTissueUsagePerDay(String pVal) {mTissueUsagePerDay = pVal;}

  public String getSmallLinerPerDay() {return mSmallLinerPerDay;}
  public void setSmallLinerPerDay(String pVal) {mSmallLinerPerDay = pVal;}

  public String getCommonAreaLinerPerDay() {return mCommonAreaLinerPerDay;}
  public void setCommonAreaLinerPerDay(String pVal) {mCommonAreaLinerPerDay = pVal;}

  public String getLargeLinerPerDay() {return mLargeLinerPerDay;}
  public void setLargeLinerPerDay(String pVal) {mLargeLinerPerDay = pVal;}
  
  public String getLargeLinerCaLinerQty() {return mLargeLinerCaLinerQty;}
  public void setLargeLinerCaLinerQty(String pVal) {mLargeLinerCaLinerQty = pVal;}
  
  public String getToiletLinerPerDay() {return mToiletLinerPerDay;}
  public void setToiletLinerPerDay(String pVal) {mToiletLinerPerDay = pVal;}

  public String getShowerQty() {return mShowerQty;}
  public void setShowerQty(String pVal) {mShowerQty = pVal;}
  
  
  public String getPersonnelQty() {return mPersonnelQty;}
  public void setPersonnelQty(String pVal) {mPersonnelQty = pVal;}

  public String getVisitorQty() {return mVisitorQty;}
  public void setVisitorQty(String pVal) {mVisitorQty = pVal;}

  public String getGrossFootage() {return mGrossFootage;}
  public void setGrossFootage(String pVal) {mGrossFootage = pVal;}

  public String getCleanableFootagePercent() {return mCleanableFootagePercent;}
  public void setCleanableFootagePercent(String pVal) {mCleanableFootagePercent = pVal;}

  public String getNetCleanableFootage() {return mNetCleanableFootage;}
  public void setNetCleanableFootage(String pVal) {mNetCleanableFootage = pVal;}

  public String getBaseboardPercent() {return mBaseboardPercent;}
  public void setBaseboardPercent(String pVal) {mBaseboardPercent = pVal;}

  public String getEstimatedItemsFactor() {return mEstimatedItemsFactor;}
  public void setEstimatedItemsFactor(String pVal) {mEstimatedItemsFactor = pVal;}

  public BigDecimal getEstimatedItemsAmount() {return mEstimatedItemsAmount;}
  public void setEstimatedItemsAmount(BigDecimal pVal) {mEstimatedItemsAmount = pVal;}

  public String getChemicalUsageModelCd() {return mChemicalUsageModelCd;}
  public void setChemicalUsageModelCd(String pVal) {mChemicalUsageModelCd = pVal;}

  public String getFloorMachine() {return mFloorMachine;}
  public void setFloorMachine(String pVal) {mFloorMachine = pVal;}
  
  //Floor types
  public String[] getFloorTypes() {return mFloorTypes;}
  public void setFloorTypes(String[] pVal) {mFloorTypes = pVal;}
  public String getFloorTypeEl(int pInd) {return mFloorTypes[pInd];}
  public void setFloorTypeEl(int pInd, String pVal) {mFloorTypes[pInd] = pVal;}

  public boolean[] getFloorTypeFl() {return mFloorTypeFl;}
  public void setFloorTypeFl(boolean[] pVal) {mFloorTypeFl = pVal;}

  public boolean[] getFloorTypeFlHt() {return mFloorTypeFlHt;}
  public void setFloorTypeFlHt(boolean[] pVal) {mFloorTypeFlHt = pVal;}

  public boolean[] getFloorTypeFlMt() {return mFloorTypeFlMt;}
  public void setFloorTypeFlMt(boolean[] pVal) {mFloorTypeFlMt = pVal;}

  public boolean[] getFloorTypeFlLt() {return mFloorTypeFlLt;}
  public void setFloorTypeFlLt(boolean[] pVal) {mFloorTypeFlLt = pVal;}

  
  public String[] getFloorTypePercents() {return mFloorTypePercents;}
  public void setFloorTypePercents(String[] pVal) {mFloorTypePercents = pVal;}
  public String getFloorTypePercentEl(int pInd) {return mFloorTypePercents[pInd];}
  public void setFloorTypePercentEl(int pInd, String pVal) {mFloorTypePercents[pInd] = pVal;}

  public String[] getFloorTypePercentsHt() {return mFloorTypePercentsHt;}
  public void setFloorTypePercentsHt(String[] pVal) {mFloorTypePercentsHt = pVal;}
  public String getFloorTypePercentHtEl(int pInd) {return mFloorTypePercentsHt[pInd];}
  public void setFloorTypePercentHtEl(int pInd, String pVal) {mFloorTypePercentsHt[pInd] = pVal;}

  public String[] getFloorTypePercentsMt() {return mFloorTypePercentsMt;}
  public void setFloorTypePercentsMt(String[] pVal) {mFloorTypePercentsMt = pVal;}
  public String getFloorTypePercentMtEl(int pInd) {return mFloorTypePercentsMt[pInd];}
  public void setFloorTypePercentMtEl(int pInd, String pVal) {mFloorTypePercentsMt[pInd] = pVal;}

  public String[] getFloorTypePercentsLt() {return mFloorTypePercentsLt;}
  public void setFloorTypePercentsLt(String[] pVal) {mFloorTypePercentsLt = pVal;}
  public String getFloorTypePercentLtEl(int pInd) {return mFloorTypePercentsLt[pInd];}
  public void setFloorTypePercentLtEl(int pInd, String pVal) {mFloorTypePercentsLt[pInd] = pVal;}
  
  public EstimatorProdResultViewVector getFloorProdResults() {return mFloorProdResults;}
  public void setFloorProdResults(EstimatorProdResultViewVector pVal) {mFloorProdResults = pVal;}

  public EstimatorProdResultViewVector getRestroomProdResults() {return mRestroomProdResults;}
  public void setRestroomProdResults(EstimatorProdResultViewVector pVal) {mRestroomProdResults = pVal;}
  
  public EstimatorProdResultViewVector getPaperPlusProdResults() {return mPaperPlusProdResults;}
  public void setPaperPlusProdResults(EstimatorProdResultViewVector pVal) {mPaperPlusProdResults = pVal;}
  
  public EstimatorProdResultViewVector getOtherProdResults() {return mOtherProdResults;}
  public void setOtherProdResults(EstimatorProdResultViewVector pVal) {mOtherProdResults = pVal;}

  
  
  public CleaningScheduleJoinViewVector getRestroomCleaningSchedules() 
                                           {return mRestroomCleaningSchedules;}
  public void setRestroomCleaningSchedules(CleaningScheduleJoinViewVector pVal) 
                                           {mRestroomCleaningSchedules = pVal;}
  public String getRestroomCleaningProcFrequency(int pInd) {
    if(mRestroomCleaningSchedules==null || pInd>=mRestroomCleaningSchedules.size()) {
      return "";
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mRestroomCleaningSchedules.get(pInd);
    return csjVw.getFrequency();
  }
  public void setRestroomCleaningProcFrequency(int pInd,String pVal) {
    if(mRestroomCleaningSchedules==null || pInd>=mRestroomCleaningSchedules.size()) {
      return;
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mRestroomCleaningSchedules.get(pInd);
    csjVw.setFrequency(pVal);
  }
  public String getRestroomCleaningProcTimePeriodCd(int pInd) {
    if(mRestroomCleaningSchedules==null || pInd>=mRestroomCleaningSchedules.size()) {
      return "";
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mRestroomCleaningSchedules.get(pInd);
    return csjVw.getTimePeriodCd();
  }
  public void setRestroomCleaningProcTimePeriodCd(int pInd,String pVal) {
    if(mRestroomCleaningSchedules==null || pInd>=mRestroomCleaningSchedules.size()) {
      return;
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mRestroomCleaningSchedules.get(pInd);
    csjVw.setTimePeriodCd(pVal);
  }

  public String getRestroomProcProdDilution(int pInd) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getDilutionRate();
    return val;
  }
  public void setRestroomProcProdDilution(int pInd,String pVal) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setDilutionRate(pVal);
    return;
  }

  public String getRestroomProcProdSharing(int pInd) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getSharingPercent();
    return val;
  }
  public void setRestroomProcProdSharing(int pInd,String pVal) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setSharingPercent(pVal);
    return;
  }
  
  public String getRestroomProcProdRate(int pInd) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUsageRate();
    return val;
  }
  public void setRestroomProcProdRate(int pInd,String pVal) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUsageRate(pVal);
    return;
  }

  public String getRestroomProcProdRateNumerator(int pInd) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdNumerator();
    return val;
  }
  public void setRestroomProcProdRateNumerator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdNumerator(pVal);
    return;
  }
  public String getRestroomProcProdRateDenominator(int pInd) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator();
    return val;
  }
  public void setRestroomProcProdRateDenominator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator(pVal);
    return;
  }
  public String getRestroomProcProdRateDenominator1(int pInd) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator1();
    return val;
  }
  public void setRestroomProcProdRateDenominator1(int pInd,String pVal) {
    ProdApplJoinView pajVw =getRestroomProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator1(pVal);
    return;
  }

  private ProdApplJoinView getRestroomProcProduct(int pInd) {
    int prodInd = pInd%1000;
    int ind = pInd / 1000;
    int stepInd = ind % 1000;
    int schedInd = ind / 1000;
    if(mRestroomCleaningSchedules==null || schedInd>=mRestroomCleaningSchedules.size()) {
      return null;
    }
    CleaningScheduleJoinView csjVw = 
              (CleaningScheduleJoinView) mRestroomCleaningSchedules.get(schedInd);
    CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
    if(cssjVwV==null || stepInd>= cssjVwV.size()) {
      return null;
    }
    CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) cssjVwV.get(stepInd);
    ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
    if(pajVwV==null || prodInd>=pajVwV.size()) {
      return null;
    }
    ProdApplJoinView pajVw = (ProdApplJoinView) pajVwV.get(prodInd);
    return pajVw;
  }
  
  //Floor care (second version)
  public CleaningScheduleJoinViewVector getFloorCleaningSchedules() 
                                           {return mFloorCleaningSchedules;}
  public void setFloorCleaningSchedules(CleaningScheduleJoinViewVector pVal) 
                                           {mFloorCleaningSchedules = pVal;}
  public String getFloorCleaningProcFrequency(int pInd) {
    if(mFloorCleaningSchedules==null || pInd>=mFloorCleaningSchedules.size()) {
      return "";
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mFloorCleaningSchedules.get(pInd);
    return csjVw.getFrequency();
  }
  public void setFloorCleaningProcFrequency(int pInd,String pVal) {
    if(mFloorCleaningSchedules==null || pInd>=mFloorCleaningSchedules.size()) {
      return;
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mFloorCleaningSchedules.get(pInd);
    csjVw.setFrequency(pVal);
  }
  public String getFloorCleaningProcTimePeriodCd(int pInd) {
    if(mFloorCleaningSchedules==null || pInd>=mFloorCleaningSchedules.size()) {
      return "";
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mFloorCleaningSchedules.get(pInd);
    return csjVw.getTimePeriodCd();
  }
  public void setFloorCleaningProcTimePeriodCd(int pInd,String pVal) {
    if(mFloorCleaningSchedules==null || pInd>=mFloorCleaningSchedules.size()) {
      return;
    }
    CleaningScheduleJoinView csjVw = 
                 (CleaningScheduleJoinView) mFloorCleaningSchedules.get(pInd);
    csjVw.setTimePeriodCd(pVal);
  }

  public String getFloorProcProdDilution(int pInd) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getDilutionRate();
    return val;
  }
  public void setFloorProcProdDilution(int pInd,String pVal) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setDilutionRate(pVal);
    return;
  }

  public String getFloorProcProdSharing(int pInd) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getSharingPercent();
    return val;
  }
  public void setFloorProcProdSharing(int pInd,String pVal) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setSharingPercent(pVal);
    return;
  }
  
  public String getFloorProcProdRate(int pInd) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUsageRate();
    return val;
  }
  public void setFloorProcProdRate(int pInd,String pVal) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUsageRate(pVal);
    return;
  }

  public String getFloorProcProdRateNumerator(int pInd) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdNumerator();
    return val;
  }
  public void setFloorProcProdRateNumerator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdNumerator(pVal);
    return;
  }
  public String getFloorProcProdRateDenominator(int pInd) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator();
    return val;
  }
  public void setFloorProcProdRateDenominator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator(pVal);
    return;
  }
  public String getFloorProcProdRateDenominator1(int pInd) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator1();
    return val;
  }
  public void setFloorProcProdRateDenominator1(int pInd,String pVal) {
    ProdApplJoinView pajVw =getFloorProcProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator1(pVal);
    return;
  }

  private ProdApplJoinView getFloorProcProduct(int pInd) {
    int prodInd = pInd%1000;
    int ind = pInd / 1000;
    int stepInd = ind % 1000;
    int schedInd = ind / 1000;
    if(mFloorCleaningSchedules==null || schedInd>=mFloorCleaningSchedules.size()) {
      return null;
    }
    CleaningScheduleJoinView csjVw = 
              (CleaningScheduleJoinView) mFloorCleaningSchedules.get(schedInd);
    CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
    if(cssjVwV==null || stepInd>= cssjVwV.size()) {
      return null;
    }
    CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) cssjVwV.get(stepInd);
    ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
    if(pajVwV==null || prodInd>=pajVwV.size()) {
      return null;
    }
    ProdApplJoinView pajVw = (ProdApplJoinView) pajVwV.get(prodInd);
    return pajVw;
  }
  
  //Paper plus supply (second version)
  public CleaningScheduleJoinViewVector getPaperPlusSupplySchedules() 
                                           {return mPaperPlusSupplySchedules;}
  public void setPaperPlusSupplySchedules(CleaningScheduleJoinViewVector pVal) 
                                           {mPaperPlusSupplySchedules = pVal;}

  public String getPaperPlusProdSharing(int pInd) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getSharingPercent();
    return val;
  }
  public void setPaperPlusProdSharing(int pInd,String pVal) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return;
    pajVw.setSharingPercent(pVal);
    return;
  }
  
  public String getPaperPlusProdRate(int pInd) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUsageRate();
    return val;
  }
  public void setPaperPlusProdRate(int pInd,String pVal) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUsageRate(pVal);
    return;
  }

  public String getPaperPlusProdRateNumerator(int pInd) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdNumerator();
    return val;
  }
  public void setPaperPlusProdRateNumerator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdNumerator(pVal);
    return;
  }
  public String getPaperPlusProdRateDenominator(int pInd) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator();
    return val;
  }
  public void setPaperPlusProdRateDenominator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator(pVal);
    return;
  }
  public String getPaperPlusProdRateDenominator1(int pInd) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator1();
    return val;
  }
  public void setPaperPlusProdRateDenominator1(int pInd,String pVal) {
    ProdApplJoinView pajVw =getPaperPlusProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator1(pVal);
    return;
  }

  private ProdApplJoinView getPaperPlusProduct(int pInd) {
    int prodInd = pInd%1000;
    int ind = pInd / 1000;
    int stepInd = ind % 1000;
    int schedInd = ind / 1000;
    if(mPaperPlusSupplySchedules==null || schedInd>=mPaperPlusSupplySchedules.size()) {
      return null;
    }
    CleaningScheduleJoinView csjVw = 
              (CleaningScheduleJoinView) mPaperPlusSupplySchedules.get(schedInd);
    CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
    if(cssjVwV==null || stepInd>= cssjVwV.size()) {
      return null;
    }
    CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) cssjVwV.get(stepInd);
    ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
    if(pajVwV==null || prodInd>=pajVwV.size()) {
      return null;
    }
    ProdApplJoinView pajVw = (ProdApplJoinView) pajVwV.get(prodInd);
    return pajVw;
  }

  //Other products
  public CleaningScheduleJoinViewVector getOtherSchedules() 
                                           {return mOtherSchedules;}
  public void setOtherSchedules(CleaningScheduleJoinViewVector pVal) 
                                           {mOtherSchedules = pVal;}

  public String getOtherProdSharing(int pInd) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getSharingPercent();
    return val;
  }
  public void setOtherProdSharing(int pInd,String pVal) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return;
    pajVw.setSharingPercent(pVal);
    return;
  }
  
  public String getOtherProdRate(int pInd) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUsageRate();
    return val;
  }
  public void setOtherProdRate(int pInd,String pVal) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUsageRate(pVal);
    return;
  }

  public String getOtherProdRateNumerator(int pInd) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdNumerator();
    return val;
  }
  public void setOtherProdRateNumerator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdNumerator(pVal);
    return;
  }
  public String getOtherProdRateDenominator(int pInd) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator();
    return val;
  }
  public void setOtherProdRateDenominator(int pInd,String pVal) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator(pVal);
    return;
  }
  public String getOtherProdRateDenominator1(int pInd) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return "";
    String val = pajVw.getUnitCdDenominator1();
    return val;
  }
  public void setOtherProdRateDenominator1(int pInd,String pVal) {
    ProdApplJoinView pajVw =getOtherProduct(pInd);
    if(pajVw==null) return;
    pajVw.setUnitCdDenominator1(pVal);
    return;
  }

  private ProdApplJoinView getOtherProduct(int pInd) {
    int prodInd = pInd%1000;
    int ind = pInd / 1000;
    int stepInd = ind % 1000;
    int schedInd = ind / 1000;
    if(mOtherSchedules==null || schedInd>=mOtherSchedules.size()) {
      return null;
    }
    CleaningScheduleJoinView csjVw = 
              (CleaningScheduleJoinView) mOtherSchedules.get(schedInd);
    CleaningSchedStructJoinViewVector cssjVwV = csjVw.getStructure();
    if(cssjVwV==null || stepInd>= cssjVwV.size()) {
      return null;
    }
    CleaningSchedStructJoinView cssjVw = (CleaningSchedStructJoinView) cssjVwV.get(stepInd);
    ProdApplJoinViewVector pajVwV = cssjVw.getProducts();
    if(pajVwV==null || prodInd>=pajVwV.size()) {
      return null;
    }
    ProdApplJoinView pajVw = (ProdApplJoinView) pajVwV.get(prodInd);
    return pajVw;
  }

  
  public AllocatedCategoryViewVector getAllocatedCategories() 
                                           {return mAllocatedCategories;}
  public void setAllocatedCategories(AllocatedCategoryViewVector pVal) 
                                           {mAllocatedCategories = pVal;}
  public String getAllocatedCategoryPer(int pInd) {
    String retstr = "";
    if(mAllocatedCategories==null || mAllocatedCategories.size()<=pInd) {
      return retstr;
    }
    AllocatedCategoryView acVw = 
                       (AllocatedCategoryView) mAllocatedCategories.get(pInd);
    retstr = acVw.getAllocatedPercent();
    if(retstr==null) retstr = "";
    return retstr;
  }

  public void setAllocatedCategoryPer(int pInd, String pVal) {
    if(mAllocatedCategories==null || mAllocatedCategories.size()<=pInd) {
      return;
    }
    AllocatedCategoryView acVw = 
                       (AllocatedCategoryView) mAllocatedCategories.get(pInd);
    acVw.setAllocatedPercent(pVal);
    return;
  }

  public BigDecimal getSumProductAmount() {return mSumProductAmount;}
  public void setSumProductAmount(BigDecimal pVal) {mSumProductAmount = pVal;}

  public String getTotalPercent() {return mTotalPercent;}
  public void setTotalPercent(String pVal) {mTotalPercent = pVal;}


  //Reports
  public GenericReportViewVector getReports() {return mReports;}
  public void setReports(GenericReportViewVector pVal) {mReports = pVal;}

  public int getReportId() {return mReportId;}
  public void setReportId(int pVal) {mReportId = pVal;}

  public GenericReportData getReport() {return mReport;}
  public void setReport(GenericReportData pVal) {mReport = pVal;}

  public GenericReportControlViewVector getGenericControls() {return mGenericControls;}
  public void setGenericControls(GenericReportControlViewVector pVal) {mGenericControls = pVal;}
    //Generic controls individual access
  public String getGenericControlValue(int index) {
      if(mGenericControls==null || index>=mGenericControls.size()) {
          return null;
      }
      GenericReportControlView grcVw = 
                         (GenericReportControlView) mGenericControls.get(index);
      String value = grcVw.getValue();
      return value ;
  }

  public void setGenericControlValue(int index, String value) {
      if(mGenericControls==null || index>=mGenericControls.size()) {
          return;
      }
      GenericReportControlView grcVw = 
                       (GenericReportControlView) mGenericControls.get(index);
      grcVw.setValue(value);

  }

  
  public String[] getRunForModels() {return mRunForModels;}
  public void setRunForModels(String[] pVal) {mRunForModels = pVal;}
  
  /**
     * <code>reset</code> method, set the search fiels to null.
     *
     * @param mapping an <code>ActionMapping</code> value
     * @param request a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      mProductFilter = new int[0];
      mTemplateFl = false;
      mNewFacilityGroup = "";
      mRunForModels = new String[0];
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

