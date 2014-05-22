
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        EstimatorFacilityData
 * Description:  This is a ValueObject class wrapping the database table CLW_ESTIMATOR_FACILITY.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ValueObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.dao.EstimatorFacilityDataAccess;
import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Blob;

import org.w3c.dom.*;

/**
 * <code>EstimatorFacilityData</code> is a ValueObject class wrapping of the database table CLW_ESTIMATOR_FACILITY.
 */
public class EstimatorFacilityData extends ValueObject implements Cloneable, TableObject
{
    private static final long serialVersionUID = 109696903810799814L;
    private int mEstimatorFacilityId;// SQL type:NUMBER, not null
    private int mCatalogId;// SQL type:NUMBER
    private String mName;// SQL type:VARCHAR2
    private int mFacilityQty;// SQL type:NUMBER
    private int mWorkingDayYearQty;// SQL type:NUMBER
    private String mFacilityTypeCd;// SQL type:VARCHAR2
    private int mStationQty;// SQL type:NUMBER
    private String mAppearanceStandardCd;// SQL type:VARCHAR2
    private int mPersonnelQty;// SQL type:NUMBER
    private int mVisitorQty;// SQL type:NUMBER
    private int mBathroomQty;// SQL type:NUMBER
    private java.math.BigDecimal mToiletBathroomQty;// SQL type:NUMBER
    private int mShowerQty;// SQL type:NUMBER
    private java.math.BigDecimal mVisitorBathroomPercent;// SQL type:NUMBER
    private java.math.BigDecimal mWashHandQty;// SQL type:NUMBER
    private java.math.BigDecimal mTissueUsageQty;// SQL type:NUMBER
    private java.math.BigDecimal mReceptacleLinerRatio;// SQL type:NUMBER
    private String mLinerRatioBaseCd;// SQL type:VARCHAR2
    private java.math.BigDecimal mAdditionalLinerRatio;// SQL type:NUMBER
    private java.math.BigDecimal mToiletLinerRatio;// SQL type:NUMBER
    private java.math.BigDecimal mLargeLinerRatio;// SQL type:NUMBER
    private int mGrossFootage;// SQL type:NUMBER
    private java.math.BigDecimal mCleanableFootagePercent;// SQL type:NUMBER
    private java.math.BigDecimal mEstimatedItemsFactor;// SQL type:NUMBER
    private java.math.BigDecimal mBaseboardPercent;// SQL type:NUMBER
    private String mFacilityStatusCd;// SQL type:VARCHAR2
    private int mProcessStep;// SQL type:NUMBER
    private Date mAddDate;// SQL type:DATE, not null
    private String mAddBy;// SQL type:VARCHAR2
    private Date mModDate;// SQL type:DATE, not null
    private String mModBy;// SQL type:VARCHAR2
    private int mOrderGuideId;// SQL type:NUMBER
    private String mTemplateFl;// SQL type:VARCHAR2
    private int mNetCleanableFootage;// SQL type:NUMBER
    private int mTemplateFacilityId;// SQL type:NUMBER
    private int mCommonAreaReceptacleQty;// SQL type:NUMBER
    private java.math.BigDecimal mVisitorToiletTissuePercent;// SQL type:NUMBER
    private java.math.BigDecimal mLargeLinerCaLinerQty;// SQL type:NUMBER
    private java.math.BigDecimal mSinkBathroomQty;// SQL type:NUMBER
    private String mFacilityGroup;// SQL type:VARCHAR2
    private String mFloorMachine;// SQL type:VARCHAR2

    /**
     * Constructor.
     */
    public EstimatorFacilityData ()
    {
        mName = "";
        mFacilityTypeCd = "";
        mAppearanceStandardCd = "";
        mLinerRatioBaseCd = "";
        mFacilityStatusCd = "";
        mAddBy = "";
        mModBy = "";
        mTemplateFl = "";
        mFacilityGroup = "";
        mFloorMachine = "";
    }

    /**
     * Constructor.
     */
    public EstimatorFacilityData(int parm1, int parm2, String parm3, int parm4, int parm5, String parm6, int parm7, String parm8, int parm9, int parm10, int parm11, java.math.BigDecimal parm12, int parm13, java.math.BigDecimal parm14, java.math.BigDecimal parm15, java.math.BigDecimal parm16, java.math.BigDecimal parm17, String parm18, java.math.BigDecimal parm19, java.math.BigDecimal parm20, java.math.BigDecimal parm21, int parm22, java.math.BigDecimal parm23, java.math.BigDecimal parm24, java.math.BigDecimal parm25, String parm26, int parm27, Date parm28, String parm29, Date parm30, String parm31, int parm32, String parm33, int parm34, int parm35, int parm36, java.math.BigDecimal parm37, java.math.BigDecimal parm38, java.math.BigDecimal parm39, String parm40, String parm41)
    {
        mEstimatorFacilityId = parm1;
        mCatalogId = parm2;
        mName = parm3;
        mFacilityQty = parm4;
        mWorkingDayYearQty = parm5;
        mFacilityTypeCd = parm6;
        mStationQty = parm7;
        mAppearanceStandardCd = parm8;
        mPersonnelQty = parm9;
        mVisitorQty = parm10;
        mBathroomQty = parm11;
        mToiletBathroomQty = parm12;
        mShowerQty = parm13;
        mVisitorBathroomPercent = parm14;
        mWashHandQty = parm15;
        mTissueUsageQty = parm16;
        mReceptacleLinerRatio = parm17;
        mLinerRatioBaseCd = parm18;
        mAdditionalLinerRatio = parm19;
        mToiletLinerRatio = parm20;
        mLargeLinerRatio = parm21;
        mGrossFootage = parm22;
        mCleanableFootagePercent = parm23;
        mEstimatedItemsFactor = parm24;
        mBaseboardPercent = parm25;
        mFacilityStatusCd = parm26;
        mProcessStep = parm27;
        mAddDate = parm28;
        mAddBy = parm29;
        mModDate = parm30;
        mModBy = parm31;
        mOrderGuideId = parm32;
        mTemplateFl = parm33;
        mNetCleanableFootage = parm34;
        mTemplateFacilityId = parm35;
        mCommonAreaReceptacleQty = parm36;
        mVisitorToiletTissuePercent = parm37;
        mLargeLinerCaLinerQty = parm38;
        mSinkBathroomQty = parm39;
        mFacilityGroup = parm40;
        mFloorMachine = parm41;
        
    }

    /**
     * Creates a new EstimatorFacilityData
     *
     * @return
     *  Newly initialized EstimatorFacilityData object.
     */
    public static EstimatorFacilityData createValue ()
    {
        EstimatorFacilityData valueData = new EstimatorFacilityData();

        return valueData;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this EstimatorFacilityData object
     */
    public String toString()
    {
        return "[" + "EstimatorFacilityId=" + mEstimatorFacilityId + ", CatalogId=" + mCatalogId + ", Name=" + mName + ", FacilityQty=" + mFacilityQty + ", WorkingDayYearQty=" + mWorkingDayYearQty + ", FacilityTypeCd=" + mFacilityTypeCd + ", StationQty=" + mStationQty + ", AppearanceStandardCd=" + mAppearanceStandardCd + ", PersonnelQty=" + mPersonnelQty + ", VisitorQty=" + mVisitorQty + ", BathroomQty=" + mBathroomQty + ", ToiletBathroomQty=" + mToiletBathroomQty + ", ShowerQty=" + mShowerQty + ", VisitorBathroomPercent=" + mVisitorBathroomPercent + ", WashHandQty=" + mWashHandQty + ", TissueUsageQty=" + mTissueUsageQty + ", ReceptacleLinerRatio=" + mReceptacleLinerRatio + ", LinerRatioBaseCd=" + mLinerRatioBaseCd + ", AdditionalLinerRatio=" + mAdditionalLinerRatio + ", ToiletLinerRatio=" + mToiletLinerRatio + ", LargeLinerRatio=" + mLargeLinerRatio + ", GrossFootage=" + mGrossFootage + ", CleanableFootagePercent=" + mCleanableFootagePercent + ", EstimatedItemsFactor=" + mEstimatedItemsFactor + ", BaseboardPercent=" + mBaseboardPercent + ", FacilityStatusCd=" + mFacilityStatusCd + ", ProcessStep=" + mProcessStep + ", AddDate=" + mAddDate + ", AddBy=" + mAddBy + ", ModDate=" + mModDate + ", ModBy=" + mModBy + ", OrderGuideId=" + mOrderGuideId + ", TemplateFl=" + mTemplateFl + ", NetCleanableFootage=" + mNetCleanableFootage + ", TemplateFacilityId=" + mTemplateFacilityId + ", CommonAreaReceptacleQty=" + mCommonAreaReceptacleQty + ", VisitorToiletTissuePercent=" + mVisitorToiletTissuePercent + ", LargeLinerCaLinerQty=" + mLargeLinerCaLinerQty + ", SinkBathroomQty=" + mSinkBathroomQty + ", FacilityGroup=" + mFacilityGroup + ", FloorMachine=" + mFloorMachine + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root =  doc.createElement("EstimatorFacility");
        
        Element node;

        root.setAttribute("Id", String.valueOf(mEstimatorFacilityId));

        node =  doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node =  doc.createElement("Name");
        node.appendChild(doc.createTextNode(String.valueOf(mName)));
        root.appendChild(node);

        node =  doc.createElement("FacilityQty");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityQty)));
        root.appendChild(node);

        node =  doc.createElement("WorkingDayYearQty");
        node.appendChild(doc.createTextNode(String.valueOf(mWorkingDayYearQty)));
        root.appendChild(node);

        node =  doc.createElement("FacilityTypeCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityTypeCd)));
        root.appendChild(node);

        node =  doc.createElement("StationQty");
        node.appendChild(doc.createTextNode(String.valueOf(mStationQty)));
        root.appendChild(node);

        node =  doc.createElement("AppearanceStandardCd");
        node.appendChild(doc.createTextNode(String.valueOf(mAppearanceStandardCd)));
        root.appendChild(node);

        node =  doc.createElement("PersonnelQty");
        node.appendChild(doc.createTextNode(String.valueOf(mPersonnelQty)));
        root.appendChild(node);

        node =  doc.createElement("VisitorQty");
        node.appendChild(doc.createTextNode(String.valueOf(mVisitorQty)));
        root.appendChild(node);

        node =  doc.createElement("BathroomQty");
        node.appendChild(doc.createTextNode(String.valueOf(mBathroomQty)));
        root.appendChild(node);

        node =  doc.createElement("ToiletBathroomQty");
        node.appendChild(doc.createTextNode(String.valueOf(mToiletBathroomQty)));
        root.appendChild(node);

        node =  doc.createElement("ShowerQty");
        node.appendChild(doc.createTextNode(String.valueOf(mShowerQty)));
        root.appendChild(node);

        node =  doc.createElement("VisitorBathroomPercent");
        node.appendChild(doc.createTextNode(String.valueOf(mVisitorBathroomPercent)));
        root.appendChild(node);

        node =  doc.createElement("WashHandQty");
        node.appendChild(doc.createTextNode(String.valueOf(mWashHandQty)));
        root.appendChild(node);

        node =  doc.createElement("TissueUsageQty");
        node.appendChild(doc.createTextNode(String.valueOf(mTissueUsageQty)));
        root.appendChild(node);

        node =  doc.createElement("ReceptacleLinerRatio");
        node.appendChild(doc.createTextNode(String.valueOf(mReceptacleLinerRatio)));
        root.appendChild(node);

        node =  doc.createElement("LinerRatioBaseCd");
        node.appendChild(doc.createTextNode(String.valueOf(mLinerRatioBaseCd)));
        root.appendChild(node);

        node =  doc.createElement("AdditionalLinerRatio");
        node.appendChild(doc.createTextNode(String.valueOf(mAdditionalLinerRatio)));
        root.appendChild(node);

        node =  doc.createElement("ToiletLinerRatio");
        node.appendChild(doc.createTextNode(String.valueOf(mToiletLinerRatio)));
        root.appendChild(node);

        node =  doc.createElement("LargeLinerRatio");
        node.appendChild(doc.createTextNode(String.valueOf(mLargeLinerRatio)));
        root.appendChild(node);

        node =  doc.createElement("GrossFootage");
        node.appendChild(doc.createTextNode(String.valueOf(mGrossFootage)));
        root.appendChild(node);

        node =  doc.createElement("CleanableFootagePercent");
        node.appendChild(doc.createTextNode(String.valueOf(mCleanableFootagePercent)));
        root.appendChild(node);

        node =  doc.createElement("EstimatedItemsFactor");
        node.appendChild(doc.createTextNode(String.valueOf(mEstimatedItemsFactor)));
        root.appendChild(node);

        node =  doc.createElement("BaseboardPercent");
        node.appendChild(doc.createTextNode(String.valueOf(mBaseboardPercent)));
        root.appendChild(node);

        node =  doc.createElement("FacilityStatusCd");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityStatusCd)));
        root.appendChild(node);

        node =  doc.createElement("ProcessStep");
        node.appendChild(doc.createTextNode(String.valueOf(mProcessStep)));
        root.appendChild(node);

        node =  doc.createElement("AddDate");
        node.appendChild(doc.createTextNode(String.valueOf(mAddDate)));
        root.appendChild(node);

        node =  doc.createElement("AddBy");
        node.appendChild(doc.createTextNode(String.valueOf(mAddBy)));
        root.appendChild(node);

        node =  doc.createElement("ModDate");
        node.appendChild(doc.createTextNode(String.valueOf(mModDate)));
        root.appendChild(node);

        node =  doc.createElement("ModBy");
        node.appendChild(doc.createTextNode(String.valueOf(mModBy)));
        root.appendChild(node);

        node =  doc.createElement("OrderGuideId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideId)));
        root.appendChild(node);

        node =  doc.createElement("TemplateFl");
        node.appendChild(doc.createTextNode(String.valueOf(mTemplateFl)));
        root.appendChild(node);

        node =  doc.createElement("NetCleanableFootage");
        node.appendChild(doc.createTextNode(String.valueOf(mNetCleanableFootage)));
        root.appendChild(node);

        node =  doc.createElement("TemplateFacilityId");
        node.appendChild(doc.createTextNode(String.valueOf(mTemplateFacilityId)));
        root.appendChild(node);

        node =  doc.createElement("CommonAreaReceptacleQty");
        node.appendChild(doc.createTextNode(String.valueOf(mCommonAreaReceptacleQty)));
        root.appendChild(node);

        node =  doc.createElement("VisitorToiletTissuePercent");
        node.appendChild(doc.createTextNode(String.valueOf(mVisitorToiletTissuePercent)));
        root.appendChild(node);

        node =  doc.createElement("LargeLinerCaLinerQty");
        node.appendChild(doc.createTextNode(String.valueOf(mLargeLinerCaLinerQty)));
        root.appendChild(node);

        node =  doc.createElement("SinkBathroomQty");
        node.appendChild(doc.createTextNode(String.valueOf(mSinkBathroomQty)));
        root.appendChild(node);

        node =  doc.createElement("FacilityGroup");
        node.appendChild(doc.createTextNode(String.valueOf(mFacilityGroup)));
        root.appendChild(node);

        node =  doc.createElement("FloorMachine");
        node.appendChild(doc.createTextNode(String.valueOf(mFloorMachine)));
        root.appendChild(node);

        return root;
    }

    /**
    * creates a clone of this object, the EstimatorFacilityId field is not cloned.
    *
    * @return EstimatorFacilityData object
    */
    public Object clone(){
        EstimatorFacilityData myClone = new EstimatorFacilityData();
        
        myClone.mCatalogId = mCatalogId;
        
        myClone.mName = mName;
        
        myClone.mFacilityQty = mFacilityQty;
        
        myClone.mWorkingDayYearQty = mWorkingDayYearQty;
        
        myClone.mFacilityTypeCd = mFacilityTypeCd;
        
        myClone.mStationQty = mStationQty;
        
        myClone.mAppearanceStandardCd = mAppearanceStandardCd;
        
        myClone.mPersonnelQty = mPersonnelQty;
        
        myClone.mVisitorQty = mVisitorQty;
        
        myClone.mBathroomQty = mBathroomQty;
        
        myClone.mToiletBathroomQty = mToiletBathroomQty;
        
        myClone.mShowerQty = mShowerQty;
        
        myClone.mVisitorBathroomPercent = mVisitorBathroomPercent;
        
        myClone.mWashHandQty = mWashHandQty;
        
        myClone.mTissueUsageQty = mTissueUsageQty;
        
        myClone.mReceptacleLinerRatio = mReceptacleLinerRatio;
        
        myClone.mLinerRatioBaseCd = mLinerRatioBaseCd;
        
        myClone.mAdditionalLinerRatio = mAdditionalLinerRatio;
        
        myClone.mToiletLinerRatio = mToiletLinerRatio;
        
        myClone.mLargeLinerRatio = mLargeLinerRatio;
        
        myClone.mGrossFootage = mGrossFootage;
        
        myClone.mCleanableFootagePercent = mCleanableFootagePercent;
        
        myClone.mEstimatedItemsFactor = mEstimatedItemsFactor;
        
        myClone.mBaseboardPercent = mBaseboardPercent;
        
        myClone.mFacilityStatusCd = mFacilityStatusCd;
        
        myClone.mProcessStep = mProcessStep;
        
        if(mAddDate != null){
                myClone.mAddDate = (Date) mAddDate.clone();
        }
        
        myClone.mAddBy = mAddBy;
        
        if(mModDate != null){
                myClone.mModDate = (Date) mModDate.clone();
        }
        
        myClone.mModBy = mModBy;
        
        myClone.mOrderGuideId = mOrderGuideId;
        
        myClone.mTemplateFl = mTemplateFl;
        
        myClone.mNetCleanableFootage = mNetCleanableFootage;
        
        myClone.mTemplateFacilityId = mTemplateFacilityId;
        
        myClone.mCommonAreaReceptacleQty = mCommonAreaReceptacleQty;
        
        myClone.mVisitorToiletTissuePercent = mVisitorToiletTissuePercent;
        
        myClone.mLargeLinerCaLinerQty = mLargeLinerCaLinerQty;
        
        myClone.mSinkBathroomQty = mSinkBathroomQty;
        
        myClone.mFacilityGroup = mFacilityGroup;
        
        myClone.mFloorMachine = mFloorMachine;
        
        return myClone;
    }

    /**
     * Gets field value
     *
     * @param pFieldName Field name
     * @return Field value
     */
    public Object getFieldValue(String pFieldName) {

        if (EstimatorFacilityDataAccess.ESTIMATOR_FACILITY_ID.equals(pFieldName)) {
            return getEstimatorFacilityId();
        } else if (EstimatorFacilityDataAccess.CATALOG_ID.equals(pFieldName)) {
            return getCatalogId();
        } else if (EstimatorFacilityDataAccess.NAME.equals(pFieldName)) {
            return getName();
        } else if (EstimatorFacilityDataAccess.FACILITY_QTY.equals(pFieldName)) {
            return getFacilityQty();
        } else if (EstimatorFacilityDataAccess.WORKING_DAY_YEAR_QTY.equals(pFieldName)) {
            return getWorkingDayYearQty();
        } else if (EstimatorFacilityDataAccess.FACILITY_TYPE_CD.equals(pFieldName)) {
            return getFacilityTypeCd();
        } else if (EstimatorFacilityDataAccess.STATION_QTY.equals(pFieldName)) {
            return getStationQty();
        } else if (EstimatorFacilityDataAccess.APPEARANCE_STANDARD_CD.equals(pFieldName)) {
            return getAppearanceStandardCd();
        } else if (EstimatorFacilityDataAccess.PERSONNEL_QTY.equals(pFieldName)) {
            return getPersonnelQty();
        } else if (EstimatorFacilityDataAccess.VISITOR_QTY.equals(pFieldName)) {
            return getVisitorQty();
        } else if (EstimatorFacilityDataAccess.BATHROOM_QTY.equals(pFieldName)) {
            return getBathroomQty();
        } else if (EstimatorFacilityDataAccess.TOILET_BATHROOM_QTY.equals(pFieldName)) {
            return getToiletBathroomQty();
        } else if (EstimatorFacilityDataAccess.SHOWER_QTY.equals(pFieldName)) {
            return getShowerQty();
        } else if (EstimatorFacilityDataAccess.VISITOR_BATHROOM_PERCENT.equals(pFieldName)) {
            return getVisitorBathroomPercent();
        } else if (EstimatorFacilityDataAccess.WASH_HAND_QTY.equals(pFieldName)) {
            return getWashHandQty();
        } else if (EstimatorFacilityDataAccess.TISSUE_USAGE_QTY.equals(pFieldName)) {
            return getTissueUsageQty();
        } else if (EstimatorFacilityDataAccess.RECEPTACLE_LINER_RATIO.equals(pFieldName)) {
            return getReceptacleLinerRatio();
        } else if (EstimatorFacilityDataAccess.LINER_RATIO_BASE_CD.equals(pFieldName)) {
            return getLinerRatioBaseCd();
        } else if (EstimatorFacilityDataAccess.ADDITIONAL_LINER_RATIO.equals(pFieldName)) {
            return getAdditionalLinerRatio();
        } else if (EstimatorFacilityDataAccess.TOILET_LINER_RATIO.equals(pFieldName)) {
            return getToiletLinerRatio();
        } else if (EstimatorFacilityDataAccess.LARGE_LINER_RATIO.equals(pFieldName)) {
            return getLargeLinerRatio();
        } else if (EstimatorFacilityDataAccess.GROSS_FOOTAGE.equals(pFieldName)) {
            return getGrossFootage();
        } else if (EstimatorFacilityDataAccess.CLEANABLE_FOOTAGE_PERCENT.equals(pFieldName)) {
            return getCleanableFootagePercent();
        } else if (EstimatorFacilityDataAccess.ESTIMATED_ITEMS_FACTOR.equals(pFieldName)) {
            return getEstimatedItemsFactor();
        } else if (EstimatorFacilityDataAccess.BASEBOARD_PERCENT.equals(pFieldName)) {
            return getBaseboardPercent();
        } else if (EstimatorFacilityDataAccess.FACILITY_STATUS_CD.equals(pFieldName)) {
            return getFacilityStatusCd();
        } else if (EstimatorFacilityDataAccess.PROCESS_STEP.equals(pFieldName)) {
            return getProcessStep();
        } else if (EstimatorFacilityDataAccess.ADD_DATE.equals(pFieldName)) {
            return getAddDate();
        } else if (EstimatorFacilityDataAccess.ADD_BY.equals(pFieldName)) {
            return getAddBy();
        } else if (EstimatorFacilityDataAccess.MOD_DATE.equals(pFieldName)) {
            return getModDate();
        } else if (EstimatorFacilityDataAccess.MOD_BY.equals(pFieldName)) {
            return getModBy();
        } else if (EstimatorFacilityDataAccess.ORDER_GUIDE_ID.equals(pFieldName)) {
            return getOrderGuideId();
        } else if (EstimatorFacilityDataAccess.TEMPLATE_FL.equals(pFieldName)) {
            return getTemplateFl();
        } else if (EstimatorFacilityDataAccess.NET_CLEANABLE_FOOTAGE.equals(pFieldName)) {
            return getNetCleanableFootage();
        } else if (EstimatorFacilityDataAccess.TEMPLATE_FACILITY_ID.equals(pFieldName)) {
            return getTemplateFacilityId();
        } else if (EstimatorFacilityDataAccess.COMMON_AREA_RECEPTACLE_QTY.equals(pFieldName)) {
            return getCommonAreaReceptacleQty();
        } else if (EstimatorFacilityDataAccess.VISITOR_TOILET_TISSUE_PERCENT.equals(pFieldName)) {
            return getVisitorToiletTissuePercent();
        } else if (EstimatorFacilityDataAccess.LARGE_LINER_CA_LINER_QTY.equals(pFieldName)) {
            return getLargeLinerCaLinerQty();
        } else if (EstimatorFacilityDataAccess.SINK_BATHROOM_QTY.equals(pFieldName)) {
            return getSinkBathroomQty();
        } else if (EstimatorFacilityDataAccess.FACILITY_GROUP.equals(pFieldName)) {
            return getFacilityGroup();
        } else if (EstimatorFacilityDataAccess.FLOOR_MACHINE.equals(pFieldName)) {
            return getFloorMachine();
        } else {
            return null;
        }

    }
    /**
    * Gets table name
    *
    * @return Table name
    */
    public String getTable() {
        return EstimatorFacilityDataAccess.CLW_ESTIMATOR_FACILITY;
    }

    
    /**
     * Sets the EstimatorFacilityId field. This field is required to be set in the database.
     *
     * @param pEstimatorFacilityId
     *  int to use to update the field.
     */
    public void setEstimatorFacilityId(int pEstimatorFacilityId){
        this.mEstimatorFacilityId = pEstimatorFacilityId;
        setDirty(true);
    }
    /**
     * Retrieves the EstimatorFacilityId field.
     *
     * @return
     *  int containing the EstimatorFacilityId field.
     */
    public int getEstimatorFacilityId(){
        return mEstimatorFacilityId;
    }

    /**
     * Sets the CatalogId field.
     *
     * @param pCatalogId
     *  int to use to update the field.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
        setDirty(true);
    }
    /**
     * Retrieves the CatalogId field.
     *
     * @return
     *  int containing the CatalogId field.
     */
    public int getCatalogId(){
        return mCatalogId;
    }

    /**
     * Sets the Name field.
     *
     * @param pName
     *  String to use to update the field.
     */
    public void setName(String pName){
        this.mName = pName;
        setDirty(true);
    }
    /**
     * Retrieves the Name field.
     *
     * @return
     *  String containing the Name field.
     */
    public String getName(){
        return mName;
    }

    /**
     * Sets the FacilityQty field.
     *
     * @param pFacilityQty
     *  int to use to update the field.
     */
    public void setFacilityQty(int pFacilityQty){
        this.mFacilityQty = pFacilityQty;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityQty field.
     *
     * @return
     *  int containing the FacilityQty field.
     */
    public int getFacilityQty(){
        return mFacilityQty;
    }

    /**
     * Sets the WorkingDayYearQty field.
     *
     * @param pWorkingDayYearQty
     *  int to use to update the field.
     */
    public void setWorkingDayYearQty(int pWorkingDayYearQty){
        this.mWorkingDayYearQty = pWorkingDayYearQty;
        setDirty(true);
    }
    /**
     * Retrieves the WorkingDayYearQty field.
     *
     * @return
     *  int containing the WorkingDayYearQty field.
     */
    public int getWorkingDayYearQty(){
        return mWorkingDayYearQty;
    }

    /**
     * Sets the FacilityTypeCd field.
     *
     * @param pFacilityTypeCd
     *  String to use to update the field.
     */
    public void setFacilityTypeCd(String pFacilityTypeCd){
        this.mFacilityTypeCd = pFacilityTypeCd;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityTypeCd field.
     *
     * @return
     *  String containing the FacilityTypeCd field.
     */
    public String getFacilityTypeCd(){
        return mFacilityTypeCd;
    }

    /**
     * Sets the StationQty field.
     *
     * @param pStationQty
     *  int to use to update the field.
     */
    public void setStationQty(int pStationQty){
        this.mStationQty = pStationQty;
        setDirty(true);
    }
    /**
     * Retrieves the StationQty field.
     *
     * @return
     *  int containing the StationQty field.
     */
    public int getStationQty(){
        return mStationQty;
    }

    /**
     * Sets the AppearanceStandardCd field.
     *
     * @param pAppearanceStandardCd
     *  String to use to update the field.
     */
    public void setAppearanceStandardCd(String pAppearanceStandardCd){
        this.mAppearanceStandardCd = pAppearanceStandardCd;
        setDirty(true);
    }
    /**
     * Retrieves the AppearanceStandardCd field.
     *
     * @return
     *  String containing the AppearanceStandardCd field.
     */
    public String getAppearanceStandardCd(){
        return mAppearanceStandardCd;
    }

    /**
     * Sets the PersonnelQty field.
     *
     * @param pPersonnelQty
     *  int to use to update the field.
     */
    public void setPersonnelQty(int pPersonnelQty){
        this.mPersonnelQty = pPersonnelQty;
        setDirty(true);
    }
    /**
     * Retrieves the PersonnelQty field.
     *
     * @return
     *  int containing the PersonnelQty field.
     */
    public int getPersonnelQty(){
        return mPersonnelQty;
    }

    /**
     * Sets the VisitorQty field.
     *
     * @param pVisitorQty
     *  int to use to update the field.
     */
    public void setVisitorQty(int pVisitorQty){
        this.mVisitorQty = pVisitorQty;
        setDirty(true);
    }
    /**
     * Retrieves the VisitorQty field.
     *
     * @return
     *  int containing the VisitorQty field.
     */
    public int getVisitorQty(){
        return mVisitorQty;
    }

    /**
     * Sets the BathroomQty field.
     *
     * @param pBathroomQty
     *  int to use to update the field.
     */
    public void setBathroomQty(int pBathroomQty){
        this.mBathroomQty = pBathroomQty;
        setDirty(true);
    }
    /**
     * Retrieves the BathroomQty field.
     *
     * @return
     *  int containing the BathroomQty field.
     */
    public int getBathroomQty(){
        return mBathroomQty;
    }

    /**
     * Sets the ToiletBathroomQty field.
     *
     * @param pToiletBathroomQty
     *  java.math.BigDecimal to use to update the field.
     */
    public void setToiletBathroomQty(java.math.BigDecimal pToiletBathroomQty){
        this.mToiletBathroomQty = pToiletBathroomQty;
        setDirty(true);
    }
    /**
     * Retrieves the ToiletBathroomQty field.
     *
     * @return
     *  java.math.BigDecimal containing the ToiletBathroomQty field.
     */
    public java.math.BigDecimal getToiletBathroomQty(){
        return mToiletBathroomQty;
    }

    /**
     * Sets the ShowerQty field.
     *
     * @param pShowerQty
     *  int to use to update the field.
     */
    public void setShowerQty(int pShowerQty){
        this.mShowerQty = pShowerQty;
        setDirty(true);
    }
    /**
     * Retrieves the ShowerQty field.
     *
     * @return
     *  int containing the ShowerQty field.
     */
    public int getShowerQty(){
        return mShowerQty;
    }

    /**
     * Sets the VisitorBathroomPercent field.
     *
     * @param pVisitorBathroomPercent
     *  java.math.BigDecimal to use to update the field.
     */
    public void setVisitorBathroomPercent(java.math.BigDecimal pVisitorBathroomPercent){
        this.mVisitorBathroomPercent = pVisitorBathroomPercent;
        setDirty(true);
    }
    /**
     * Retrieves the VisitorBathroomPercent field.
     *
     * @return
     *  java.math.BigDecimal containing the VisitorBathroomPercent field.
     */
    public java.math.BigDecimal getVisitorBathroomPercent(){
        return mVisitorBathroomPercent;
    }

    /**
     * Sets the WashHandQty field.
     *
     * @param pWashHandQty
     *  java.math.BigDecimal to use to update the field.
     */
    public void setWashHandQty(java.math.BigDecimal pWashHandQty){
        this.mWashHandQty = pWashHandQty;
        setDirty(true);
    }
    /**
     * Retrieves the WashHandQty field.
     *
     * @return
     *  java.math.BigDecimal containing the WashHandQty field.
     */
    public java.math.BigDecimal getWashHandQty(){
        return mWashHandQty;
    }

    /**
     * Sets the TissueUsageQty field.
     *
     * @param pTissueUsageQty
     *  java.math.BigDecimal to use to update the field.
     */
    public void setTissueUsageQty(java.math.BigDecimal pTissueUsageQty){
        this.mTissueUsageQty = pTissueUsageQty;
        setDirty(true);
    }
    /**
     * Retrieves the TissueUsageQty field.
     *
     * @return
     *  java.math.BigDecimal containing the TissueUsageQty field.
     */
    public java.math.BigDecimal getTissueUsageQty(){
        return mTissueUsageQty;
    }

    /**
     * Sets the ReceptacleLinerRatio field.
     *
     * @param pReceptacleLinerRatio
     *  java.math.BigDecimal to use to update the field.
     */
    public void setReceptacleLinerRatio(java.math.BigDecimal pReceptacleLinerRatio){
        this.mReceptacleLinerRatio = pReceptacleLinerRatio;
        setDirty(true);
    }
    /**
     * Retrieves the ReceptacleLinerRatio field.
     *
     * @return
     *  java.math.BigDecimal containing the ReceptacleLinerRatio field.
     */
    public java.math.BigDecimal getReceptacleLinerRatio(){
        return mReceptacleLinerRatio;
    }

    /**
     * Sets the LinerRatioBaseCd field.
     *
     * @param pLinerRatioBaseCd
     *  String to use to update the field.
     */
    public void setLinerRatioBaseCd(String pLinerRatioBaseCd){
        this.mLinerRatioBaseCd = pLinerRatioBaseCd;
        setDirty(true);
    }
    /**
     * Retrieves the LinerRatioBaseCd field.
     *
     * @return
     *  String containing the LinerRatioBaseCd field.
     */
    public String getLinerRatioBaseCd(){
        return mLinerRatioBaseCd;
    }

    /**
     * Sets the AdditionalLinerRatio field.
     *
     * @param pAdditionalLinerRatio
     *  java.math.BigDecimal to use to update the field.
     */
    public void setAdditionalLinerRatio(java.math.BigDecimal pAdditionalLinerRatio){
        this.mAdditionalLinerRatio = pAdditionalLinerRatio;
        setDirty(true);
    }
    /**
     * Retrieves the AdditionalLinerRatio field.
     *
     * @return
     *  java.math.BigDecimal containing the AdditionalLinerRatio field.
     */
    public java.math.BigDecimal getAdditionalLinerRatio(){
        return mAdditionalLinerRatio;
    }

    /**
     * Sets the ToiletLinerRatio field.
     *
     * @param pToiletLinerRatio
     *  java.math.BigDecimal to use to update the field.
     */
    public void setToiletLinerRatio(java.math.BigDecimal pToiletLinerRatio){
        this.mToiletLinerRatio = pToiletLinerRatio;
        setDirty(true);
    }
    /**
     * Retrieves the ToiletLinerRatio field.
     *
     * @return
     *  java.math.BigDecimal containing the ToiletLinerRatio field.
     */
    public java.math.BigDecimal getToiletLinerRatio(){
        return mToiletLinerRatio;
    }

    /**
     * Sets the LargeLinerRatio field.
     *
     * @param pLargeLinerRatio
     *  java.math.BigDecimal to use to update the field.
     */
    public void setLargeLinerRatio(java.math.BigDecimal pLargeLinerRatio){
        this.mLargeLinerRatio = pLargeLinerRatio;
        setDirty(true);
    }
    /**
     * Retrieves the LargeLinerRatio field.
     *
     * @return
     *  java.math.BigDecimal containing the LargeLinerRatio field.
     */
    public java.math.BigDecimal getLargeLinerRatio(){
        return mLargeLinerRatio;
    }

    /**
     * Sets the GrossFootage field.
     *
     * @param pGrossFootage
     *  int to use to update the field.
     */
    public void setGrossFootage(int pGrossFootage){
        this.mGrossFootage = pGrossFootage;
        setDirty(true);
    }
    /**
     * Retrieves the GrossFootage field.
     *
     * @return
     *  int containing the GrossFootage field.
     */
    public int getGrossFootage(){
        return mGrossFootage;
    }

    /**
     * Sets the CleanableFootagePercent field.
     *
     * @param pCleanableFootagePercent
     *  java.math.BigDecimal to use to update the field.
     */
    public void setCleanableFootagePercent(java.math.BigDecimal pCleanableFootagePercent){
        this.mCleanableFootagePercent = pCleanableFootagePercent;
        setDirty(true);
    }
    /**
     * Retrieves the CleanableFootagePercent field.
     *
     * @return
     *  java.math.BigDecimal containing the CleanableFootagePercent field.
     */
    public java.math.BigDecimal getCleanableFootagePercent(){
        return mCleanableFootagePercent;
    }

    /**
     * Sets the EstimatedItemsFactor field.
     *
     * @param pEstimatedItemsFactor
     *  java.math.BigDecimal to use to update the field.
     */
    public void setEstimatedItemsFactor(java.math.BigDecimal pEstimatedItemsFactor){
        this.mEstimatedItemsFactor = pEstimatedItemsFactor;
        setDirty(true);
    }
    /**
     * Retrieves the EstimatedItemsFactor field.
     *
     * @return
     *  java.math.BigDecimal containing the EstimatedItemsFactor field.
     */
    public java.math.BigDecimal getEstimatedItemsFactor(){
        return mEstimatedItemsFactor;
    }

    /**
     * Sets the BaseboardPercent field.
     *
     * @param pBaseboardPercent
     *  java.math.BigDecimal to use to update the field.
     */
    public void setBaseboardPercent(java.math.BigDecimal pBaseboardPercent){
        this.mBaseboardPercent = pBaseboardPercent;
        setDirty(true);
    }
    /**
     * Retrieves the BaseboardPercent field.
     *
     * @return
     *  java.math.BigDecimal containing the BaseboardPercent field.
     */
    public java.math.BigDecimal getBaseboardPercent(){
        return mBaseboardPercent;
    }

    /**
     * Sets the FacilityStatusCd field.
     *
     * @param pFacilityStatusCd
     *  String to use to update the field.
     */
    public void setFacilityStatusCd(String pFacilityStatusCd){
        this.mFacilityStatusCd = pFacilityStatusCd;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityStatusCd field.
     *
     * @return
     *  String containing the FacilityStatusCd field.
     */
    public String getFacilityStatusCd(){
        return mFacilityStatusCd;
    }

    /**
     * Sets the ProcessStep field.
     *
     * @param pProcessStep
     *  int to use to update the field.
     */
    public void setProcessStep(int pProcessStep){
        this.mProcessStep = pProcessStep;
        setDirty(true);
    }
    /**
     * Retrieves the ProcessStep field.
     *
     * @return
     *  int containing the ProcessStep field.
     */
    public int getProcessStep(){
        return mProcessStep;
    }

    /**
     * Sets the AddDate field. This field is required to be set in the database.
     *
     * @param pAddDate
     *  Date to use to update the field.
     */
    public void setAddDate(Date pAddDate){
        this.mAddDate = pAddDate;
        setDirty(true);
    }
    /**
     * Retrieves the AddDate field.
     *
     * @return
     *  Date containing the AddDate field.
     */
    public Date getAddDate(){
        return mAddDate;
    }

    /**
     * Sets the AddBy field.
     *
     * @param pAddBy
     *  String to use to update the field.
     */
    public void setAddBy(String pAddBy){
        this.mAddBy = pAddBy;
        setDirty(true);
    }
    /**
     * Retrieves the AddBy field.
     *
     * @return
     *  String containing the AddBy field.
     */
    public String getAddBy(){
        return mAddBy;
    }

    /**
     * Sets the ModDate field. This field is required to be set in the database.
     *
     * @param pModDate
     *  Date to use to update the field.
     */
    public void setModDate(Date pModDate){
        this.mModDate = pModDate;
        setDirty(true);
    }
    /**
     * Retrieves the ModDate field.
     *
     * @return
     *  Date containing the ModDate field.
     */
    public Date getModDate(){
        return mModDate;
    }

    /**
     * Sets the ModBy field.
     *
     * @param pModBy
     *  String to use to update the field.
     */
    public void setModBy(String pModBy){
        this.mModBy = pModBy;
        setDirty(true);
    }
    /**
     * Retrieves the ModBy field.
     *
     * @return
     *  String containing the ModBy field.
     */
    public String getModBy(){
        return mModBy;
    }

    /**
     * Sets the OrderGuideId field.
     *
     * @param pOrderGuideId
     *  int to use to update the field.
     */
    public void setOrderGuideId(int pOrderGuideId){
        this.mOrderGuideId = pOrderGuideId;
        setDirty(true);
    }
    /**
     * Retrieves the OrderGuideId field.
     *
     * @return
     *  int containing the OrderGuideId field.
     */
    public int getOrderGuideId(){
        return mOrderGuideId;
    }

    /**
     * Sets the TemplateFl field.
     *
     * @param pTemplateFl
     *  String to use to update the field.
     */
    public void setTemplateFl(String pTemplateFl){
        this.mTemplateFl = pTemplateFl;
        setDirty(true);
    }
    /**
     * Retrieves the TemplateFl field.
     *
     * @return
     *  String containing the TemplateFl field.
     */
    public String getTemplateFl(){
        return mTemplateFl;
    }

    /**
     * Sets the NetCleanableFootage field.
     *
     * @param pNetCleanableFootage
     *  int to use to update the field.
     */
    public void setNetCleanableFootage(int pNetCleanableFootage){
        this.mNetCleanableFootage = pNetCleanableFootage;
        setDirty(true);
    }
    /**
     * Retrieves the NetCleanableFootage field.
     *
     * @return
     *  int containing the NetCleanableFootage field.
     */
    public int getNetCleanableFootage(){
        return mNetCleanableFootage;
    }

    /**
     * Sets the TemplateFacilityId field.
     *
     * @param pTemplateFacilityId
     *  int to use to update the field.
     */
    public void setTemplateFacilityId(int pTemplateFacilityId){
        this.mTemplateFacilityId = pTemplateFacilityId;
        setDirty(true);
    }
    /**
     * Retrieves the TemplateFacilityId field.
     *
     * @return
     *  int containing the TemplateFacilityId field.
     */
    public int getTemplateFacilityId(){
        return mTemplateFacilityId;
    }

    /**
     * Sets the CommonAreaReceptacleQty field.
     *
     * @param pCommonAreaReceptacleQty
     *  int to use to update the field.
     */
    public void setCommonAreaReceptacleQty(int pCommonAreaReceptacleQty){
        this.mCommonAreaReceptacleQty = pCommonAreaReceptacleQty;
        setDirty(true);
    }
    /**
     * Retrieves the CommonAreaReceptacleQty field.
     *
     * @return
     *  int containing the CommonAreaReceptacleQty field.
     */
    public int getCommonAreaReceptacleQty(){
        return mCommonAreaReceptacleQty;
    }

    /**
     * Sets the VisitorToiletTissuePercent field.
     *
     * @param pVisitorToiletTissuePercent
     *  java.math.BigDecimal to use to update the field.
     */
    public void setVisitorToiletTissuePercent(java.math.BigDecimal pVisitorToiletTissuePercent){
        this.mVisitorToiletTissuePercent = pVisitorToiletTissuePercent;
        setDirty(true);
    }
    /**
     * Retrieves the VisitorToiletTissuePercent field.
     *
     * @return
     *  java.math.BigDecimal containing the VisitorToiletTissuePercent field.
     */
    public java.math.BigDecimal getVisitorToiletTissuePercent(){
        return mVisitorToiletTissuePercent;
    }

    /**
     * Sets the LargeLinerCaLinerQty field.
     *
     * @param pLargeLinerCaLinerQty
     *  java.math.BigDecimal to use to update the field.
     */
    public void setLargeLinerCaLinerQty(java.math.BigDecimal pLargeLinerCaLinerQty){
        this.mLargeLinerCaLinerQty = pLargeLinerCaLinerQty;
        setDirty(true);
    }
    /**
     * Retrieves the LargeLinerCaLinerQty field.
     *
     * @return
     *  java.math.BigDecimal containing the LargeLinerCaLinerQty field.
     */
    public java.math.BigDecimal getLargeLinerCaLinerQty(){
        return mLargeLinerCaLinerQty;
    }

    /**
     * Sets the SinkBathroomQty field.
     *
     * @param pSinkBathroomQty
     *  java.math.BigDecimal to use to update the field.
     */
    public void setSinkBathroomQty(java.math.BigDecimal pSinkBathroomQty){
        this.mSinkBathroomQty = pSinkBathroomQty;
        setDirty(true);
    }
    /**
     * Retrieves the SinkBathroomQty field.
     *
     * @return
     *  java.math.BigDecimal containing the SinkBathroomQty field.
     */
    public java.math.BigDecimal getSinkBathroomQty(){
        return mSinkBathroomQty;
    }

    /**
     * Sets the FacilityGroup field.
     *
     * @param pFacilityGroup
     *  String to use to update the field.
     */
    public void setFacilityGroup(String pFacilityGroup){
        this.mFacilityGroup = pFacilityGroup;
        setDirty(true);
    }
    /**
     * Retrieves the FacilityGroup field.
     *
     * @return
     *  String containing the FacilityGroup field.
     */
    public String getFacilityGroup(){
        return mFacilityGroup;
    }

    /**
     * Sets the FloorMachine field.
     *
     * @param pFloorMachine
     *  String to use to update the field.
     */
    public void setFloorMachine(String pFloorMachine){
        this.mFloorMachine = pFloorMachine;
        setDirty(true);
    }
    /**
     * Retrieves the FloorMachine field.
     *
     * @return
     *  String containing the FloorMachine field.
     */
    public String getFloorMachine(){
        return mFloorMachine;
    }


}
