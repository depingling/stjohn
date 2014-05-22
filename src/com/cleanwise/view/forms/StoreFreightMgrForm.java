package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.CurrencyFormat;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 *
 * @author Ykupershmidt
 */
public final class StoreFreightMgrForm extends StorePortalBaseForm  {

	// Embadded forms
	private LocateStoreCatalogForm catalogForm = null;
	int level = 0;

	// Search
	private String mFreightSearchField = "";
	private String mFreightSearchType = "nameBegins";
	private FreightTableViewVector mResultList = new FreightTableViewVector();

    // Detail
    private FreightTableData    mDetail = FreightTableData.createValue();
    private FreightTableCriteriaDescDataVector  mCriteriaList = new FreightTableCriteriaDescDataVector();
    private FreightTableCriteriaDescDataVector  mOrgCriteriaList = new FreightTableCriteriaDescDataVector();

    private CatalogDataVector mCatalogsAssociatedWithFreightTable = null;

    private int mCriteriaLength = 10;
  
    /**
     * Holds value of property distFilter.
     */
    private DistributorDataVector distFilter;
    private CatalogDataVector mCatalogFilter;
    private boolean isaMLAStore = true;
    
    public String getFreightSearchField() {return (mFreightSearchField);}
    public void setFreightSearchField(String pVal) {mFreightSearchField = pVal; }

    public String getFreightSearchType() {return (mFreightSearchType); }
    public void setFreightSearchType(String pVal) {mFreightSearchType = pVal; }

    public FreightTableViewVector getResultList() {return (mResultList); }
    public void setResultList(FreightTableViewVector pVal) {mResultList = pVal; }
    
    public int getListCount() {
      if (mResultList==null) return 0;
      return (mResultList.size());
    }
    
    /**
     * Holds value of property actionOveride.
     */
    private String actionOveride;

    /**
     * Getter for property actionOveride.
     * @return Value of property actionOveride.
     */
    public String getActionOveride() {

        return this.actionOveride;
    }
    
    /**
     * Setter for property actionOveride.
     * @param actionOveride New value of property actionOveride.
     */
    public void setActionOveride(String actionOveride) {

        this.actionOveride = actionOveride;
    }

    /**
     * Holds value of property lineToDelete.
     */
    private String lineToDelete;

    /**
     * Getter for property lineToDelete.
     * @return Value of property lineToDelete.
     */
    public String getLineToDelete() {

        return this.lineToDelete;
    }

    /**
     * Setter for property lineToDelete.
     * @param lineToDelete New value of property lineToDelete.
     */
    public void setLineToDelete(String lineToDelete) {

        this.lineToDelete = lineToDelete;
    }
    
    public FreightTableData getDetail() {return (mDetail); }
    public void setDetail(FreightTableData detail) {mDetail = detail; }

    public FreightTableCriteriaDescDataVector getCriteriaList() {
        if(mCriteriaList==null) {
           mCriteriaList = new FreightTableCriteriaDescDataVector();
        }
        return (mCriteriaList);
    }
    public void setCriteriaList(FreightTableCriteriaDescDataVector pVal) {mCriteriaList = pVal;}


    public FreightTableCriteriaDescDataVector getOrgCriteriaList() {
        if( mOrgCriteriaList==null) {
            mOrgCriteriaList = new FreightTableCriteriaDescDataVector();
        }
        return (mOrgCriteriaList);
    }
    public void setOrgCriteriaList(FreightTableCriteriaDescDataVector pVal) {mOrgCriteriaList = pVal; }


    
    public FreightTableCriteriaDescData getCriteria(int idx) {
        if (mCriteriaList == null) {
            mCriteriaList = new FreightTableCriteriaDescDataVector();
        }
        while (idx >= mCriteriaList.size()) {
          mCriteriaList.add(FreightTableCriteriaDescData.createValue());
        }    
        
        return (FreightTableCriteriaDescData) mCriteriaList.get(idx);
    }
    

    public int getCriteriaLength() {return (mCriteriaLength);}
    public void setCriteriaLength(int pVal) {mCriteriaLength = pVal;}
      

    /**
     * Getter for property distFilter.
     * @return Value of property distFilter.
     */
    public DistributorDataVector getDistFilter() {

        return this.distFilter;
    }

    /**
     * Setter for property distFilter.
     * @param distFilter New value of property distFilter.
     */
    public void setDistFilter(DistributorDataVector distFilter) {

        this.distFilter = distFilter;
    }

    public CatalogDataVector getCatalogFilter() {
        return mCatalogFilter;
    }

    public void setCatalogFilter(CatalogDataVector catalogFilter) {
        mCatalogFilter = catalogFilter;
    }

    /**
     * Getter for property mlaStore.
     * @return Value of property mlaStore.
     */
    public boolean getMlaStore() {

        return this.isaMLAStore;
    }

    /**
     * Setter for property mlaStore.
     * @param value New value of property mlaStore.
     */
    public void setMlaStore(boolean value) {

        this.isaMLAStore = value;
    }
    
    
    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }


    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * Required fields are:
     *   freightTable name
     *   tax ID
     *   contact first and last names
     *   contact email address
     *
     * Additionally, a freightTable's name is checked for uniqueness.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
     public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {

        // Retrieve the action. We only want to validate on save.
        String action = request.getParameter("action");
        if (action == null) action = "";
        if (!action.equals("Save")) {
            return null;
        }
        
        String change = request.getParameter("change");
        if(null != change && "type".equals(change)) {
            return null;
        }
        
        

        // Perform the save validation.
        ActionErrors errors = new ActionErrors();
        if (mDetail.getShortDesc() == null || 
            mDetail.getShortDesc().trim().length() < 1) {
            errors.add("freightTablename", new ActionError("variable.empty.error", "Table Name"));
        }
        if ( mDetail.getFreightTableTypeCd() == null ||  
             mDetail.getFreightTableTypeCd().trim().length() < 1) {
            errors.add("freightTablename", new ActionError("variable.empty.error", "Table Type"));
        }
        if (mDetail.getFreightTableStatusCd() == null || 
            mDetail.getFreightTableStatusCd().trim().length() < 1) {
            errors.add("freightTablename", 
                    new ActionError("variable.empty.error", "Table Status"));
        }

        for(int i = 0; i < mCriteriaList.size(); i++) {
            FreightTableCriteriaDescData criteria = 
                    (FreightTableCriteriaDescData) mCriteriaList.get(i); 
            boolean hasError = false; 
            if(criteria.getLowerAmount().trim().length() > 0) {
                // check that it is a valid amount format
                try {
                    BigDecimal lowerAmt = 
                        CurrencyFormat.exactParse(criteria.getLowerAmount());
                    criteria.setLowerAmount(CurrencyFormat.formatAsNumber(lowerAmt));
                } catch (ParseException pe) {
                    errors.add("criteriaAmount", 
				                     new ActionError("error.invalidNumberAmount",
						      "Criteria [" + (i+1) + "]'s Begin Amount"));
                    hasError = true;
                }
            }
                
            if(criteria.getHigherAmount().trim().length() > 0) {
                // check that it is a valid amount format
                try {
                    BigDecimal higherAmt = 
                        CurrencyFormat.exactParse(criteria.getHigherAmount());
                    criteria.setHigherAmount(CurrencyFormat.formatAsNumber(higherAmt));
                } catch (ParseException pe) {
                    errors.add("criteriaAmount", 
				               new ActionError("error.invalidNumberAmount",
						      "Criteria [" + (i+1) + "]'s End Amount"));
                    hasError = true;
                }
            }
                
            if (criteria.getFreightAmount().trim().length() > 0) {
                // check that it is a valid amount format
                try {
                    BigDecimal freightAmt = 
                        CurrencyFormat.exactParse(criteria.getFreightAmount());
                    if (freightAmt.doubleValue() < 0) {
                        errors.add("criteriaAmount", 
                            new ActionError("error.simpleGenericError",
                                "Freight Amount (for Criteria [" + (i+1) + "]) cannot be negative"));
                    } else {
                        criteria.setFreightAmount(CurrencyFormat.formatAsNumber(freightAmt));
                    }
                } catch (ParseException pe) {
                    errors.add("criteriaAmount", 
                        new ActionError("error.invalidNumberAmount",
                            "Criteria [" + (i+1) + "]'s Freight Amount"));
                    hasError = true;
                }
            }

            if (criteria.getHandlingAmount().trim().length() > 0) {
                // check that it is a valid amount format
                try {
                    BigDecimal handlingAmt = 
                        CurrencyFormat.exactParse(criteria.getHandlingAmount());
                    if (handlingAmt.doubleValue() < 0) {
                        errors.add("criteriaAmount", 
                            new ActionError("error.simpleGenericError",
                                "Handling Amount (for Criteria [" + (i+1) + "]) cannot be negative"));
                    } else {
                        criteria.setHandlingAmount(CurrencyFormat.formatAsNumber(handlingAmt));
                    }
                } catch (ParseException pe) {
                    errors.add("criteriaAmount", 
                        new ActionError("error.invalidNumberAmount",
                            "Criteria [" + (i+1) + "]'s Handling Amount"));
                    hasError = true;
                }
            }

            if( ! getMlaStore() ) {

                // only validate the following fieds if not a MLA store
                if (criteria.getLowerAmount().trim().length() > 0 ||
                    criteria.getHigherAmount().trim().length() > 0 ||
                    criteria.getFreightAmount().trim().length() > 0 ||
                    criteria.getHandlingAmount().trim().length() > 0 ||
                    criteria.getShortDesc().trim().length() > 0 ||   
                    criteria.getFreightHandlerId().trim().length() > 0 ||
                    criteria.getFreightCriteriaTypeCd().trim().length() > 0 ||
                    criteria.getRuntimeTypeCd().trim().length() > 0  ) {

                    if( null == criteria.getShortDesc() ||
                        criteria.getShortDesc().trim().length() == 0) {
                        errors.add("criteriaAmount", 
                	    new ActionError("variable.empty.error",
						             "Criteria [" + (i+1) + "]'s Description"));
                        hasError = true;
                    }
                    if( null == criteria.getFreightCriteriaTypeCd() ||
                        criteria.getFreightCriteriaTypeCd().trim().length() == 0) {
                        errors.add("criteriaAmount", 
                            new ActionError("variable.empty.error",
						             "Criteria [" + (i+1) + "]'s Freight Criteria Type"));
                        hasError = true;
                    }
                    if( null == criteria.getRuntimeTypeCd() ||
                        criteria.getRuntimeTypeCd().trim().length() == 0) {
                        errors.add("criteriaAmount", 
                            new ActionError("variable.empty.error",
						             "Criteria [" + (i+1) + "]'s Runtime Type"));
                        hasError = true;
                    }
                
                    // when charge type=Default only Handling or Freight can have value
                    String chargeType = criteria.getChargeCd();
                    if (!Utility.isSet(chargeType)) {  // Default value
                        if (Utility.isSet(criteria.getHandlingAmount()) &&
                            Utility.isSet(criteria.getFreightAmount())) {
                            errors.add("criteriaAmount",
                                new ActionError("error.simpleGenericError",
                                         "Freight and handling charges must be entered on separate lines"));
                            hasError = true;
                        }
                    }
                    if (RefCodeNames.CHARGE_CD.FUEL_SURCHARGE.equals(chargeType) ||
                        RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE.equals(chargeType)) {

                        if ( Utility.isSet(criteria.getFreightAmount())) {
                            errors.add("criteriaAmount",
                                new ActionError("error.simpleGenericError",
                                         chargeType + " amount must be placed in Handling field"));
                            hasError = true;
                        }
                    }

                    if(criteria.getFreightHandlerId().trim().length() > 0) {
                        // check that it is a valid amount format
                        try {
                            int freightHandlerId = Integer.parseInt(criteria.getFreightHandlerId());
                        } catch (Exception pe) {
                            errors.add("criteriaAmount", 
				                 new ActionError("error.invalidNumber",
						             "Criteria [" + (i+1) + "]'s Freight Handler ID"));
                            hasError = true;
                        }
                    }
                    
                    if(criteria.getUIOrder().trim().length() == 0) {                        
                        errors.add("criteriaAmount", 
			                 new ActionError("variable.empty.error",
					             "Criteria [" + (i+1) + "]'s UI Order"));
                        hasError = true;
                    }
                    else if(criteria.getUIOrder().trim().length() > 0) {
                        // check that it is a valid amount format
                        try {
                            int uiOrder = Integer.parseInt(criteria.getUIOrder());
                        } catch (Exception pe) {
                            errors.add("criteriaAmount", 
				                 new ActionError("error.invalidNumber",
						             "Criteria [" + (i+1) + "]'s UI Order"));
                            hasError = true;
                        }
                    }

                    /// Validation for amount interval intersection
                    for (int j = i + 1; j < mCriteriaList.size(); ++j) {
                        FreightTableCriteriaDescData criteria2 = 
                            (FreightTableCriteriaDescData) mCriteriaList.get(j);
                        if (isEqualFreightTableCriterias(criteria, criteria2)) {
                            if (isIntersectFreightTableCriteriasByAmount(criteria, criteria2)) {
                                errors.add("criteriaAmount", new ActionError("error.simpleGenericError",
                                    "An amount intervals for the criterias '" + 
                                    criteria.getShortDesc() + "' and '" + criteria2.getShortDesc() + 
                                    "' is intersected"));
                            }
                        }
                    }
                }
            } else { // MLA store
                // Handling OR Freight (NOT both at the same time) can have value for ONE entry (line) of the Freight Table
                
                    if (Utility.isSet(criteria.getHandlingAmount()) &&
                        Utility.isSet(criteria.getFreightAmount())) {
                        errors.add("criteriaAmount",
                            new ActionError("error.simpleGenericError",
                                     "Freight and handling charges must be entered on separate lines"));
                        hasError = true;
                    }
            }

            if( true != hasError ) {
               mCriteriaList.set(i, criteria);
            }
                        
        } // for
        
        return errors;

    }

    private static boolean isEqualFreightTableCriterias(FreightTableCriteriaDescData o1, 
        FreightTableCriteriaDescData o2) {
        if (o1 == null || o2 == null) {
            return false;
        }
        if ((Utility.isSet(o1.getFreightHandlerId()) && !Utility.isSet(o2.getFreightHandlerId())) ||
            (Utility.isSet(o2.getFreightHandlerId()) && !Utility.isSet(o1.getFreightHandlerId()))) {
            return false;
        }
        if ((Utility.isSet(o1.getFreightCriteriaTypeCd()) && !Utility.isSet(o2.getFreightCriteriaTypeCd())) ||
            (Utility.isSet(o2.getFreightCriteriaTypeCd()) && !Utility.isSet(o1.getFreightCriteriaTypeCd()))) {
            return false;
        }
        if ((Utility.isSet(o1.getRuntimeTypeCd()) && !Utility.isSet(o2.getRuntimeTypeCd())) ||
            (Utility.isSet(o2.getRuntimeTypeCd()) && !Utility.isSet(o1.getRuntimeTypeCd()))) {
            return false;
        }
        if ((Utility.isSet(o1.getChargeCd()) && !Utility.isSet(o2.getChargeCd())) ||
            (Utility.isSet(o2.getChargeCd()) && !Utility.isSet(o1.getChargeCd()))) {
            return false;
        }

        if (Utility.isSet(o1.getFreightHandlerId()) && Utility.isSet(o2.getFreightHandlerId())) {
            if (!o1.getFreightHandlerId().equalsIgnoreCase(o2.getFreightHandlerId())) {
                return false;
            }
        }

        if (Utility.isSet(o1.getFreightCriteriaTypeCd()) && Utility.isSet(o2.getFreightCriteriaTypeCd())) {
            if (!o1.getFreightCriteriaTypeCd().equalsIgnoreCase(o2.getFreightCriteriaTypeCd())) {
                return false;
            }
        }

        boolean isO1Selectable = false;
        boolean isO1Implied = false;
        boolean isO2Selectable = false;
        boolean isO2Implied = false;

        if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.SELECTABLE.equals(o1.getRuntimeTypeCd())) {
            isO1Selectable = true;
        } else if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equals(o1.getRuntimeTypeCd())) {
            isO1Implied = true;
        }
        if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.SELECTABLE.equals(o2.getRuntimeTypeCd())) {
            isO2Selectable = true;
        } else if (RefCodeNames.FREIGHT_CRITERIA_RUNTIME_TYPE.IMPLIED.equals(o2.getRuntimeTypeCd())) {
            isO2Implied = true;
        }

        if (Utility.isSet(o1.getRuntimeTypeCd()) && Utility.isSet(o2.getRuntimeTypeCd())) {
            if (!o1.getRuntimeTypeCd().equalsIgnoreCase(o2.getRuntimeTypeCd())) {
                if ( !( (isO1Selectable && isO2Implied) || (isO2Selectable && isO1Implied) ) ) {
                return false;
            }
            } else {
                if (isO1Selectable) {
                    return false;
                }
            }
        }

        if (isO1Selectable) {
            if (RefCodeNames.CHARGE_CD.FUEL_SURCHARGE.equals(o2.getChargeCd()) ||
                RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE.equals(o2.getChargeCd())) {
                return false;
            }
        }
        if (isO2Selectable) {
            if (RefCodeNames.CHARGE_CD.FUEL_SURCHARGE.equals(o1.getChargeCd()) ||
                RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE.equals(o1.getChargeCd())) {
                return false;
            }
        }

        if (isO1Selectable && isO2Implied) {
            if (!RefCodeNames.CHARGE_CD.FUEL_SURCHARGE.equals(o2.getChargeCd()) &&
                !RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE.equals(o2.getChargeCd())) {
                return true;
            }
        }
        if (isO2Selectable && isO1Implied) {
            if (!RefCodeNames.CHARGE_CD.FUEL_SURCHARGE.equals(o1.getChargeCd()) &&
                !RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE.equals(o1.getChargeCd())) {
                return true;
            }
        }

        if (Utility.isSet(o1.getChargeCd()) && Utility.isSet(o2.getChargeCd())) {
            if (!o1.getChargeCd().equalsIgnoreCase(o2.getChargeCd())) {
                return false;
            }
        }


        return true;
    }

    private static boolean isIntersectFreightTableCriteriasByAmount(FreightTableCriteriaDescData o1, 
        FreightTableCriteriaDescData o2) {
        if (o1 == null || o2 == null) {
            return false;
        }
        if (!Utility.isSet(o1.getLowerAmount()) ||
            !Utility.isSet(o2.getLowerAmount())) {
            return false;
        }

        // freight and handler may be intersected
        if ( Utility.isSet(o1.getFreightAmount()) && !Utility.isSet(o1.getHandlingAmount()) &&
                !Utility.isSet(o2.getFreightAmount()) && Utility.isSet(o2.getHandlingAmount()) ||

            !Utility.isSet(o1.getFreightAmount()) && Utility.isSet(o1.getHandlingAmount()) &&
                Utility.isSet(o2.getFreightAmount()) && !Utility.isSet(o2.getHandlingAmount()) ) {
            return false;
        }

        final BigDecimal epsilon = new BigDecimal(0.00001);
        BigDecimal loAmount1 = null;
        BigDecimal hiAmount1 = null;
        BigDecimal loAmount2 = null;
        BigDecimal hiAmount2 = null;
        try {
            loAmount1 = CurrencyFormat.exactParse(o1.getLowerAmount());
        } catch (Exception ex) {
            return false;
        }
        try {
            loAmount2 = CurrencyFormat.exactParse(o2.getLowerAmount());
        } catch (Exception ex) {
            return false;
        }
        if (Utility.isSet(o1.getHigherAmount())) {
            try {
                hiAmount1 = CurrencyFormat.exactParse(o1.getHigherAmount());
            } catch (Exception ex) {
                return false;
            }
        } else {
            hiAmount1 = new BigDecimal(0);
        }
        if (Utility.isSet(o2.getHigherAmount())) {
            try {
                hiAmount2 = CurrencyFormat.exactParse(o2.getHigherAmount());
            } catch (Exception ex) {
                return false;
            }
        } else {
            hiAmount2 = new BigDecimal(0);
        }
        if (hiAmount1.doubleValue() == 0 && hiAmount2.doubleValue() == 0) {
            return true;
        }
        if (hiAmount1.doubleValue() == 0) {
            BigDecimal bound2 = loAmount2.max(hiAmount2);
            if (loAmount1.compareTo(bound2) > 0 && 
                loAmount1.subtract(bound2).doubleValue() > epsilon.doubleValue()) {
                return false;
            }
                return true;
            }
        if (hiAmount2.doubleValue() == 0) {
            BigDecimal bound1 = loAmount1.max(hiAmount1);
            if (loAmount2.compareTo(bound1) > 0 &&
                loAmount2.subtract(bound1).doubleValue() > epsilon.doubleValue()) {
            return false;
        }
                return true;
            }
        BigDecimal lo1 = loAmount1.min(hiAmount1);
        BigDecimal hi1 = loAmount1.max(hiAmount1);
        BigDecimal lo2 = loAmount2.min(hiAmount2);
        BigDecimal hi2 = loAmount2.max(hiAmount2);
        if ((hi1.compareTo(lo2) < 0 && lo2.subtract(hi1).doubleValue() > epsilon.doubleValue()) ||
            (lo1.compareTo(hi2) > 0 && lo1.subtract(hi2).doubleValue() > epsilon.doubleValue())) {
            return false;
        }
        return true;
    }

    public CatalogDataVector getCatalogsAssociatedWithFreightTable() {
        return mCatalogsAssociatedWithFreightTable;
    }

    public void setCatalogsAssociatedWithFreightTable(CatalogDataVector catalogs) {
        mCatalogsAssociatedWithFreightTable = catalogs;
    }
	
    public LocateStoreCatalogForm getLocateStoreCatalogForm() {
		return catalogForm;
	}

    public void setLocateStoreCatalogForm(LocateStoreCatalogForm locateStoreCatalogForm) {
		this.catalogForm = locateStoreCatalogForm;
	}
	
    public int getLevel() {
		return level;
	}
	
    public void setLevel(int level) {
		this.level = level;
	}
	
    public void setEmbeddedForm(Object pVal) {
        if(pVal instanceof LocateStoreCatalogForm) {
        	catalogForm = (LocateStoreCatalogForm) pVal;
        }
      }

    private CatalogDataVector filterCatalogs = null;
	public CatalogDataVector getFilterCatalogs() {return (this.filterCatalogs); }
	public void setFilterCatalogs(CatalogDataVector pVal) { this.filterCatalogs = pVal; }
    
}
