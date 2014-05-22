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


public final class StoreDiscountFreightMgrForm extends StorePortalBaseForm  {
    // Search
    private String mFreightSearchField = "";
    private String mFreightSearchType = "nameBegins";
    private FreightTableViewVector mResultList = new FreightTableViewVector();

    // Detail
    private FreightTableData mDetail = FreightTableData.createValue();
    private FreightTableCriteriaDescDataVector mCriteriaList = new FreightTableCriteriaDescDataVector();
    private FreightTableCriteriaDescDataVector mOrgCriteriaList = new FreightTableCriteriaDescDataVector();

    private CatalogDataVector mCatalogsAssociatedWithFreightTable = null;

    private int mCriteriaLength = 10;
  
    /// Holds value of property mDistFilter.
    private DistributorDataVector mDistFilter;
    private CatalogDataVector mCatalogFilter;
    private boolean mIsaMLAStore = true;

    /// Holds value of property mLineToDelete.
    private String mLineToDelete;

    /// Holds value of property mActionOveride.
    private String mActionOveride;

    public String getFreightSearchField() {
        return mFreightSearchField;
    }

    public void setFreightSearchField(String pVal) {
        mFreightSearchField = pVal; 
    }

    public String getFreightSearchType() {
        return mFreightSearchType; 
    }

    public void setFreightSearchType(String pVal) {
        mFreightSearchType = pVal; 
    }

    public FreightTableViewVector getResultList() {
        return mResultList; 
    }

    public void setResultList(FreightTableViewVector pVal) {
        mResultList = pVal; 
    }

    public int getListCount() {
        if (mResultList == null) { 
            return 0;
        }
        return mResultList.size();
    }

    public String getActionOveride() {
        return mActionOveride;
    }

    public void setActionOveride(String actionOveride) {
        mActionOveride = actionOveride;
    }

    public String getLineToDelete() {
        return mLineToDelete;
    }

    public void setLineToDelete(String lineToDelete) {
        mLineToDelete = lineToDelete;
    }

    public FreightTableData getDetail() {
        return mDetail; 
    }

    public void setDetail(FreightTableData detail) {
        mDetail = detail; 
    }

    public FreightTableCriteriaDescDataVector getCriteriaList() {
        if (mCriteriaList == null) {
           mCriteriaList = new FreightTableCriteriaDescDataVector();
        }
        return mCriteriaList;
    }

    public void setCriteriaList(FreightTableCriteriaDescDataVector pVal) {
        mCriteriaList = pVal;
    }

    public FreightTableCriteriaDescDataVector getOrgCriteriaList() {
        if( mOrgCriteriaList==null) {
            mOrgCriteriaList = new FreightTableCriteriaDescDataVector();
        }
        return mOrgCriteriaList;
    }

    public void setOrgCriteriaList(FreightTableCriteriaDescDataVector pVal) {
        mOrgCriteriaList = pVal; 
    }

    public FreightTableCriteriaDescData getCriteria(int idx) {
        if (mCriteriaList == null) {
            mCriteriaList = new FreightTableCriteriaDescDataVector();
        }
        while (idx >= mCriteriaList.size()) {
            mCriteriaList.add(FreightTableCriteriaDescData.createValue());
        }    
        return (FreightTableCriteriaDescData) mCriteriaList.get(idx);
    }

    public int getCriteriaLength() {
        return mCriteriaLength;
    }

    public void setCriteriaLength(int pVal) {
        mCriteriaLength = pVal;
    }

    public DistributorDataVector getDistFilter() {
        return mDistFilter;
    }

    public void setDistFilter(DistributorDataVector distFilter) {
        mDistFilter = distFilter;
    }

    public CatalogDataVector getCatalogFilter() {
        return mCatalogFilter;
    }

    public void setCatalogFilter(CatalogDataVector catalogFilter) {
        mCatalogFilter = catalogFilter;
    }

    public boolean getMlaStore() {
        return mIsaMLAStore;
    }

    public void setMlaStore(boolean value) {
        mIsaMLAStore = value;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        // Retrieve the action. We only want to validate on save.
        String action = request.getParameter("action");
        if (action == null) { 
            action = "";
        }
        if (!action.equals("Save")) {
            return null;
        }

        String change = request.getParameter("change");
        if (null != change && "type".equals(change)) {
            return null;
        }

        // Perform the save validation.
        ActionErrors errors = new ActionErrors();
        if (mDetail.getShortDesc() == null || 
            mDetail.getShortDesc().trim().length() < 1) {
            errors.add("freightTablename", new ActionError("variable.empty.error", "FreightTable Name"));
        }
        if (mDetail.getFreightTableTypeCd() == null ||  
             mDetail.getFreightTableTypeCd().trim().length() < 1) {
            errors.add("freightTablename", new ActionError("variable.empty.error", "FreightTable Type"));
        }
        if (mDetail.getFreightTableStatusCd() == null || 
            mDetail.getFreightTableStatusCd().trim().length() < 1) {
            errors.add("freightTablename", 
                    new ActionError("variable.empty.error", "FreightTable Status"));
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
            /// check that it is a valid discount format
            if (criteria.getDiscount() != null && criteria.getDiscount().trim().length() > 0) {
                try {
                	BigDecimal discount = CurrencyFormat.exactParse(criteria.getDiscount());
                    
                	if(mDetail.getFreightTableTypeCd().equals(RefCodeNames.FREIGHT_TABLE_TYPE_CD.DOLLARS_PERCENTAGE)){
                		
                		if(criteria.getDiscount().startsWith("-")){
                			String dVal = criteria.getDiscount();
                			dVal = dVal.replaceAll("-","");
                			BigDecimal d = CurrencyFormat.exactParse(dVal);
                			
                			if(d.compareTo(new BigDecimal(100))>0){
                				errors.add("criteriaAmount", 
                						new ActionError("error.invalidNumberAmount", 
                								"Criteria [" + (i+1) + "]'s Discount: " + criteria.getDiscount()
                								+" (greater than 100%) "));
                				hasError = true;
                				break;
                			}
                		}else{
                			if(discount.compareTo(new BigDecimal(100))>0){
                				errors.add("criteriaAmount", 
                						new ActionError("error.invalidNumberAmount", 
                								"Criteria [" + (i+1) + "]'s Discount: " + criteria.getDiscount()
                								+" (greater than 100%) "));
                				hasError = true;
                				break;
                			}
                		}
                	}
                    
                    if(criteria.getHigherAmount().trim().length() > 0){
                    	BigDecimal higherAmt = 
                            CurrencyFormat.exactParse(criteria.getHigherAmount());
                    	
                    	if(criteria.getDiscount().startsWith("-")){
                    		String dVal = criteria.getDiscount();
                    		dVal = dVal.replaceAll("-","");
                    		BigDecimal d = CurrencyFormat.exactParse(dVal);
                    		
                    		if(higherAmt.compareTo(d)>=0){
                    			criteria.setDiscount(CurrencyFormat.formatAsNumber(discount));
                    		}else{
                    			errors.add("criteriaAmount", 
                    					new ActionError("error.invalidNumberAmount", 
                    							"Criteria [" + (i+1) + "]'s Discount: " + criteria.getDiscount()
                    			+" (greater than End Dollars) "));
                    			hasError = true;
                    		}
                    	}else{
                    		if(higherAmt.compareTo(discount)>=0){
                    			criteria.setDiscount(CurrencyFormat.formatAsNumber(discount));
                    		}else{
                    			errors.add("criteriaAmount", 
                    					new ActionError("error.invalidNumberAmount", 
                    							"Criteria [" + (i+1) + "]'s Discount: " + criteria.getDiscount()
                    			+" (greater than End Dollars) "));
                    			hasError = true;
                    		}
                    	}
                    }
                    
                } catch (ParseException ex) {
                    errors.add("criteriaAmount", 
                        new ActionError("error.invalidNumberAmount", 
                            "Criteria [" + (i+1) + "]'s Discount: " + criteria.getDiscount()));
                    hasError = true;
                }
            }
            if( ! getMlaStore() ) {
                // only validate the following fieds if not a MLA store
                if (criteria.getShortDesc().trim().length() > 0 ||
                    criteria.getLowerAmount().trim().length() > 0 ||
                    criteria.getHigherAmount().trim().length() > 0 ||
                    criteria.getDiscount().trim().length() > 0  ) {

                    if( null == criteria.getShortDesc() ||
                        criteria.getShortDesc().trim().length() == 0) {
                        errors.add("criteriaAmount", 
                            new ActionError("variable.empty.error", "Criteria [" + (i+1) + "]'s Description"));
                        hasError = true;
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
            }

            if( true != hasError ) {
               mCriteriaList.set(i, criteria);
            }
        }

        return errors;
    }

    private static boolean isEqualFreightTableCriterias(FreightTableCriteriaDescData o1, 
        FreightTableCriteriaDescData o2) {
        if (o1 == null || o2 == null) {
            return false;
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

}
