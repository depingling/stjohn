package com.cleanwise.service.api.util;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.operations.WorkOrderPdfBuilder;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Locale;
import java.util.HashMap;
import java.util.Date;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * Title:        WorkOrderUtil
 * Description:  Utility
 * Purpose:      Access to functions involving the Work Order system.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         10.03.2008
 * Time:         11:00:15
 *
 * @author Alexander Chickin,TrinitySoft, Inc.
 */
public class WorkOrderUtil {
    private static final Logger log = Logger.getLogger(WorkOrderUtil.class);

    public static String kGoodWorkOrderStatusSqlList = "("
            + "'" + RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED + "'" +
            ")";

    public static String getPropertyValue(WorkOrderPropertyDataVector properties, String propertyTypeCd, String shortDesc) {
        if (properties != null && !properties.isEmpty() && Utility.isSet(propertyTypeCd) && Utility.isSet(shortDesc)) {
            Iterator it = properties.iterator();
            while (it.hasNext()) {
                WorkOrderPropertyData workOrderProperty = (WorkOrderPropertyData) it.next();
                if (propertyTypeCd.equals(workOrderProperty.getPropertyCd())
                        && shortDesc.equals(workOrderProperty.getShortDesc())
                        && RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE.equals(workOrderProperty.getStatusCd())) {
                    return workOrderProperty.getValue();
                }
            }
        }
        return null;
    }

    public static WorkOrderPropertyDataVector createEmptyWorkOrderContact() {

        WorkOrderPropertyDataVector contactProperties = new WorkOrderPropertyDataVector();

        WorkOrderPropertyData address1 = WorkOrderPropertyData.createValue();
        address1.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        address1.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        address1.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.ADDRESS1);

        WorkOrderPropertyData address2 = WorkOrderPropertyData.createValue();
        address2.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        address2.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        address2.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.ADDRESS2);

        WorkOrderPropertyData firstName = WorkOrderPropertyData.createValue();
        firstName.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        firstName.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        firstName.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.FIRST_NAME);

        WorkOrderPropertyData lastName = WorkOrderPropertyData.createValue();
        lastName.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        lastName.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        lastName.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.LAST_NAME);

        WorkOrderPropertyData city = WorkOrderPropertyData.createValue();
        city.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        city.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        city.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.CITY);

        WorkOrderPropertyData country = WorkOrderPropertyData.createValue();
        country.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        country.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        country.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.COUNTRY);

        WorkOrderPropertyData state = WorkOrderPropertyData.createValue();
        state.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        state.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        state.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.STATE);

        WorkOrderPropertyData zip = WorkOrderPropertyData.createValue();
        zip.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        zip.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        zip.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.ZIP);

        WorkOrderPropertyData phone = WorkOrderPropertyData.createValue();
        phone.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        phone.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        phone.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.PHONE);

        WorkOrderPropertyData fax = WorkOrderPropertyData.createValue();
        fax.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        fax.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        fax.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.FAX);

        WorkOrderPropertyData mobile = WorkOrderPropertyData.createValue();
        mobile.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        mobile.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        mobile.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.MOBILE);

        WorkOrderPropertyData email = WorkOrderPropertyData.createValue();
        email.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
        email.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
        email.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.EMAIL);

        contactProperties.add(address1);
        contactProperties.add(address2);
        contactProperties.add(firstName);
        contactProperties.add(lastName);
        contactProperties.add(fax);
        contactProperties.add(email);
        contactProperties.add(phone);
        contactProperties.add(mobile);
        contactProperties.add(city);
        contactProperties.add(zip);
        contactProperties.add(country);
        contactProperties.add(state);

        return contactProperties;
    }

    public static int getAssignedServiceProviderId(WorkOrderAssocDataVector workOrderAssocCollection) {
        if (workOrderAssocCollection != null) {
            Iterator itar = workOrderAssocCollection.iterator();
            while (itar.hasNext()) {
                WorkOrderAssocData woaData = (WorkOrderAssocData) itar.next();
                if (RefCodeNames.WORK_ORDER_ASSOC_CD.WORK_ORDER_PROVIDER.equals(woaData.getWorkOrderAssocCd())) {
                    return woaData.getBusEntityId();
                }
            }
        }
        return 0;
    }

	public static final String SUB_QUERY_FOR_SITES_WITH_DIST_SITE_REF_NUM = 
		"SELECT   site.bus_entity_id " +
		"FROM   clw_bus_entity site " +
		"WHERE  (site.bus_entity_type_cd = '" + RefCodeNames.BUS_ENTITY_TYPE_CD.SITE + "') " +
				"AND EXISTS " +
					"(SELECT * " +
					"FROM	 clw_property prop " +
					"WHERE   (site.bus_entity_id = prop.bus_entity_id) " +
		              		"AND (prop.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER + "') " +
		              		"AND (prop.clw_value LIKE '%s%%')) ";

	public static final String SUB_QUERY_FOR_SITES_WITH_DIST_ACCT_REF_NUM =
		"SELECT   siteOfAccount.bus_entity1_id " +
		  "FROM   clw_bus_entity_assoc siteOfAccount " +
		 "WHERE   (siteOfAccount.bus_entity_assoc_cd = '" + RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT + "') " +
		         "AND EXISTS " +
		               "(SELECT   * " +
		                  "FROM   clw_property prop " +
		                 "WHERE       (siteOfAccount.bus_entity2_id = prop.bus_entity_id) " +
		                         "AND (prop.clw_value LIKE '%s%%') " +
		                         "AND (prop.short_desc = '" + RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM + "')) ";

	public static final String EMAIL = "Email";

	public static final String FAX   = "Fax";		

    public boolean writeWoPdfToStream(WorkOrderDetailView woDetail,
                                      ByteArrayOutputStream pdfout,
                                      Locale localeCd) throws Exception {

        int siteId = woDetail.getWorkOrder().getBusEntityId();

        APIAccess factory = APIAccess.getAPIAccess();

        Store storeEjb = factory.getStoreAPI();
        Account accountEjb = factory.getAccountAPI();
        Site siteEjb = factory.getSiteAPI();
        Service serviceEjb = factory.getServiceAPI();
        PropertyService psEjb = factory.getPropertyServiceAPI();
        Asset assetEjb = factory.getAssetAPI();
        WorkOrder workOrderEjb = factory.getWorkOrderAPI();
        User userEjb = factory.getUserAPI();
        
        SiteData site;
        try {
            site = siteEjb.getSite(siteId);
        } catch (DataNotFoundException e) {
            log.info("writeWoPdfToStream => Error." + e.getMessage());
            return false;
        }

        AccountData account;
        try {
            account = accountEjb.getAccountForSite(siteId);
        } catch (DataNotFoundException e) {
            log.info("writeWoPdfToStream => Error." + e.getMessage());
            return false;
        }
        
        final String pName = RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM;
		final String pValue = psEjb.checkBusEntityProperty(account.getAccountId(), pName);
		if(pValue != null)
		{
			account.setPropertyValue(pName, pValue, null);
		}

        StoreData store;
        try {
            int storeId = storeEjb.getStoreIdByAccount(account.getAccountId());
            store = storeEjb.getStore(storeId);
        } catch (DataNotFoundException e) {
            log.info("writeWoPdfToStream => Error." + e.getMessage());
            return false;
        }

        ServiceProviderData serviceProvider;
        try {
            int providerId = getAssignedServiceProviderId(woDetail.getWorkOrderAssocCollection());
            serviceProvider = serviceEjb.getServiceProvider(providerId);
        } catch (DataNotFoundException e) {
            log.info("getServiceProvider => Error." + e.getMessage());
            return false;
        }

        String imgPath = null;
        try {
            UIConfigData uioptions = psEjb.fetchUIConfigData(store.getStoreId(), localeCd.getLanguage(), 0);
            imgPath = ClwApiCustomizer.getCustomizeImgElement(uioptions.getLogo1());
        } catch (RemoteException e) {
            log.error(e.getMessage(), e);
        }
        
        UserData userD;
        try {
            userD = userEjb.getUserByName(woDetail.getWorkOrder().getAddBy(), store.getStoreId());
        } catch (DataNotFoundException e) {
            log.info("getUserData => Error." + e.getMessage());
            return false;
        }
        
        CostCenterData costCenterD = null;
        try {
            costCenterD = accountEjb.getCostCenter(woDetail.getWorkOrder().getCostCenterId(),0);
        } catch (DataNotFoundException e) {
            log.info("CostCenter not found => Warning." + e.getMessage());
            //return false;
        }
        
        AssetData assetD = null;
        int assetId = 0;
        WorkOrderItemDetailViewVector woItemsV = woDetail.getWorkOrderItems();
        if (woItemsV != null && woItemsV.size() > 0) {
            WorkOrderItemDetailView woItem = (WorkOrderItemDetailView)woItemsV.get(0);
            if (woItem != null) {
                assetId = WorkOrderUtil.getAssignedAssetId(woItem.getAssetAssoc());
            }
        }
        
        WarrantyDataVector warranties = null;
        if (assetId > 0) {
            IdVector assetIds = new IdVector();
            assetIds.add(Integer.valueOf(assetId));
            //AssetDataVector assetDV = assetEjb.getAssetCollectionByIds(assetIds);

            AssetSearchCriteria crit = new AssetSearchCriteria();
            crit.setAssetId(assetId);
            crit.setShowInactive(true);
            AssetDataVector assetDV = assetEjb.getAssetDataByCriteria(crit);

            if (!assetDV.isEmpty()) {
                assetD = (AssetData)assetDV.get(0);
                warranties = workOrderEjb.getWorkOrderWarrantiesForAssets(assetIds, RefCodeNames.WARRANTY_STATUS_CD.ACTIVE);
            }
        }
        
        WorkOrderDetailDataVector woDetailDV = workOrderEjb.getWorkOrderDetails(woDetail.getWorkOrder().getWorkOrderId());
        
        WorkOrderPdfBuilder pdf = new WorkOrderPdfBuilder();

        pdf.generatePdf(store,
                        account,
                        site,
                        woDetail,
                        costCenterD,
                        assetD,
                        warranties,
                        serviceProvider,
                        woDetailDV,
                        pdfout,
                        imgPath,
                        userD,
                        localeCd);

        return true;
    }
    
    public static int getItemizedServiceTableMaxLineNumber(WorkOrderDetailDataVector details) {
        int maxNumber = 0;
        
        if (details != null && !details.isEmpty()) {
            Iterator it = details.iterator();
            WorkOrderDetailData detailD;
            while (it.hasNext()) {
                detailD = (WorkOrderDetailData)it.next();
                if (maxNumber < detailD.getLineNum()) {
                    maxNumber = detailD.getLineNum();
                }
            }
        }
        return maxNumber;
    }
    
    public static int getItemizedServiceTableActiveCount(WorkOrderDetailDataVector details) {
        int quantity = 0;
        
        if (details != null && !details.isEmpty()) {
            Iterator it = details.iterator();
            WorkOrderDetailData detailD;
            while (it.hasNext()) {
                detailD = (WorkOrderDetailData)it.next();
                if (RefCodeNames.STATUS_CD.ACTIVE.equals(detailD.getStatusCd())) {
                    quantity++;
                }
            }
        }
        return quantity;
    }
    
    public static void addEmptyWorkOrderDetailData(WorkOrderDetailDataVector woDetailDV, int woId, int lineNumber, String userName, int quantity) {
        WorkOrderDetailData emptyLine;
        if (quantity > 0) {
            emptyLine = WorkOrderDetailData.createValue();
            emptyLine.setWorkOrderId(woId);
            emptyLine.setLineNum(lineNumber);
            emptyLine.setPaymentTypeCd(RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE);
            emptyLine.setStatusCd(RefCodeNames.STATUS_CD.ACTIVE);
            emptyLine.setPartNumber("");
            emptyLine.setComments("");
            emptyLine.setPartPrice(BigDecimal.valueOf(0.00));
            emptyLine.setLabor(BigDecimal.valueOf(0.00));
            emptyLine.setTravel(BigDecimal.valueOf(0.00));
            emptyLine.setQuantity(0);
            emptyLine.setAddBy(userName);
            emptyLine.setModBy(userName);
            emptyLine.setAddDate(new Date());
            emptyLine.setModDate(new Date());
            woDetailDV.add(emptyLine);
            
            for (int i = 1; i < quantity; i++) {
                emptyLine = (WorkOrderDetailData)emptyLine.clone();
                emptyLine.setLineNum(lineNumber + i);
                woDetailDV.add(emptyLine);
            }
        }
    }
    
    public static int getEmptyLinesCount(WorkOrderDetailDataVector woDetailDV) {
        int count = 0;
        
        int size = woDetailDV.size();
        WorkOrderDetailData currentLine;
        for (int i = 0; i < size; i++) {
            currentLine = (WorkOrderDetailData)woDetailDV.get(i);
            if (RefCodeNames.STATUS_CD.ACTIVE.equals(currentLine.getStatusCd())) {
                if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE.equals(currentLine.getPaymentTypeCd()) &&
                    "".equals(currentLine.getPartNumber()) &&
                    "".equals(currentLine.getComments()) &&
                    BigDecimal.valueOf(0.00).compareTo(currentLine.getPartPrice()) == 0 &&
                    BigDecimal.valueOf(0.00).compareTo(currentLine.getLabor()) == 0 &&
                    BigDecimal.valueOf(0.00).compareTo(currentLine.getTravel()) == 0 &&
                    currentLine.getQuantity() == 0) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * @param details
     * @return an Array list of part's cost sums as a ArrayList
     *  1th position - Billed Service costs sum
     *  2th position - Warranty costs sum
     *  3th position - P.M.Contract costs sum
     *  4th position - Total sum of parts cost
     */
    public static ArrayList getPartsCostSum(WorkOrderDetailDataVector details) {
        ArrayList sumList = new ArrayList(4);
        
        if (details != null && !details.isEmpty()) {
            BigDecimal billedServiceCost = new BigDecimal(0);
            BigDecimal warrantyCost = new BigDecimal(0);
            BigDecimal pmContractCost = new BigDecimal(0);
            
            Iterator it = details.iterator();
            WorkOrderDetailData detailD;
            while (it.hasNext()) {
                detailD = (WorkOrderDetailData)it.next();
                if (RefCodeNames.STATUS_CD.ACTIVE.equals(detailD.getStatusCd())) {
                    if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE.equals(detailD.getPaymentTypeCd())) {
                        billedServiceCost = billedServiceCost.add(detailD.getPartPrice().multiply(BigDecimal.valueOf(detailD.getQuantity())));
                    } else if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.WARRANTY.equals(detailD.getPaymentTypeCd())) {
                        warrantyCost = warrantyCost.add(detailD.getPartPrice().multiply(BigDecimal.valueOf(detailD.getQuantity())));
                    } else if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.PM_CONTRACT.equals(detailD.getPaymentTypeCd())) {
                        pmContractCost = pmContractCost.add(detailD.getPartPrice().multiply(BigDecimal.valueOf(detailD.getQuantity())));
                    }
                }
            }
            sumList.add(billedServiceCost);
            sumList.add(warrantyCost);
            sumList.add(pmContractCost);
            sumList.add(billedServiceCost.add(warrantyCost).add(pmContractCost));
        } else {
            for (int i = 0; i < 4; i++) {
                sumList.add(new BigDecimal(0));
            }
        }
        
        return sumList;
    }
    
    /**
     * @param details
     * @return an Array list of labor's cost sums as a ArrayList
     *  1th position - Billed Service costs sum
     *  2th position - Warranty costs sum
     *  3th position - P.M.Contract costs sum
     *  4th position - Total sum of labor cost
     */
    public static ArrayList getLaborCostSum(WorkOrderDetailDataVector details) {
        ArrayList sumList = new ArrayList(4);
        
        if (details != null && !details.isEmpty()) {
            BigDecimal billedServiceCost = new BigDecimal(0);
            BigDecimal warrantyCost = new BigDecimal(0);
            BigDecimal pmContractCost = new BigDecimal(0);
            
            Iterator it = details.iterator();
            WorkOrderDetailData detailD;
            while (it.hasNext()) {
                detailD = (WorkOrderDetailData)it.next();
                if (RefCodeNames.STATUS_CD.ACTIVE.equals(detailD.getStatusCd())) {
                    if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE.equals(detailD.getPaymentTypeCd())) {
                        billedServiceCost = billedServiceCost.add(detailD.getLabor());
                    } else if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.WARRANTY.equals(detailD.getPaymentTypeCd())) {
                        warrantyCost = warrantyCost.add(detailD.getLabor());
                    } else if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.PM_CONTRACT.equals(detailD.getPaymentTypeCd())) {
                        pmContractCost = pmContractCost.add(detailD.getLabor());
                    }
                }
            }
            sumList.add(billedServiceCost);
            sumList.add(warrantyCost);
            sumList.add(pmContractCost);
            sumList.add(billedServiceCost.add(warrantyCost).add(pmContractCost));
        } else {
            for (int i = 0; i < 4; i++) {
                sumList.add(new BigDecimal(0));
            }
        }
        
        return sumList;
    }
    
    /**
     * @param details
     * @return an Array list of travel's cost sums as a ArrayList
     *  1th position - Billed Service costs sum
     *  2th position - Warranty costs sum
     *  3th position - P.M.Contract costs sum
     *  4th position - Total sum of travel cost
     */
    public static ArrayList getTravelCostSum(WorkOrderDetailDataVector details) {
        ArrayList sumList = new ArrayList(4);
        
        if (details != null && !details.isEmpty()) {
            BigDecimal billedServiceCost = new BigDecimal(0);
            BigDecimal warrantyCost = new BigDecimal(0);
            BigDecimal pmContractCost = new BigDecimal(0);
            
            Iterator it = details.iterator();
            WorkOrderDetailData detailD;
            while (it.hasNext()) {
                detailD = (WorkOrderDetailData)it.next();
                if (RefCodeNames.STATUS_CD.ACTIVE.equals(detailD.getStatusCd())) {
                    if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.BILLED_SERVICE.equals(detailD.getPaymentTypeCd())) {
                        billedServiceCost = billedServiceCost.add(detailD.getTravel());
                    } else if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.WARRANTY.equals(detailD.getPaymentTypeCd())) {
                        warrantyCost = warrantyCost.add(detailD.getTravel());
                    } else if (RefCodeNames.WORK_ORDER_PAYMENT_TYPE_CD.PM_CONTRACT.equals(detailD.getPaymentTypeCd())) {
                        pmContractCost = pmContractCost.add(detailD.getTravel());
                    }
                }
            }
            sumList.add(billedServiceCost);
            sumList.add(warrantyCost);
            sumList.add(pmContractCost);
            sumList.add(billedServiceCost.add(warrantyCost).add(pmContractCost));
        } else {
            for (int i = 0; i < 4; i++) {
                sumList.add(new BigDecimal(0));
            }
        }
        
        return sumList;
    }
    
    /**
     * @param details
     * @return the WorkOrder total cost sum as a BigDecimal value
     */
    public static BigDecimal getWorkOrderTotalCostSum(WorkOrderDetailDataVector details) {
        BigDecimal sum = new BigDecimal(0);
        
        if (details != null && !details.isEmpty()) {
            ArrayList<BigDecimal> partsCost = WorkOrderUtil.getPartsCostSum(details);
            ArrayList<BigDecimal> laborCost = WorkOrderUtil.getLaborCostSum(details);
            ArrayList<BigDecimal> travelCost = WorkOrderUtil.getTravelCostSum(details);
            
            sum = (partsCost.get(3)).add(laborCost.get(3)).add(travelCost.get(3));
        }
        return sum;
    }
    
    public static BigDecimal getEstimatedLaborSum(WorkOrderItemDetailViewVector items) {
        BigDecimal sum = null;
        if (items != null && !items.isEmpty()) {
            Iterator it = items.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView item = (WorkOrderItemDetailView) it.next();
                if (item.getWorkOrderItem() != null &&
                        item.getWorkOrderItem().getEstimateLabor() != null) {
                    if (sum == null) {
                        sum = new BigDecimal(0);
                    }
                    sum = sum.add(item.getWorkOrderItem().getEstimateLabor());
                }
            }
        }
        return sum;
    }

    public static BigDecimal getActualLaborSum(WorkOrderItemDetailViewVector items) {
        BigDecimal sum = null;
        if (items != null && !items.isEmpty()) {
            Iterator it = items.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView item = (WorkOrderItemDetailView) it.next();
                if (item.getWorkOrderItem() != null &&
                        item.getWorkOrderItem().getActualLabor() != null) {
                    if (sum == null) {
                        sum = new BigDecimal(0);
                    }
                    sum = sum.add(item.getWorkOrderItem().getActualLabor());
                }
            }
        }
        return sum;
    }

    public static BigDecimal getActualPartSum(WorkOrderItemDetailViewVector items) {
        BigDecimal sum = null;
        if (items != null && !items.isEmpty()) {
            Iterator it = items.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView item = (WorkOrderItemDetailView) it.next();
                if (item.getWorkOrderItem() != null &&
                        item.getWorkOrderItem().getActualPart() != null) {
                    if (sum == null) {
                        sum = new BigDecimal(0);
                    }
                    sum = sum.add(item.getWorkOrderItem().getActualPart());
                }
            }
        }
        return sum;
    }

    public static BigDecimal getEstimatedPartSum(WorkOrderItemDetailViewVector items) {
        BigDecimal sum = null;
        if (items != null && !items.isEmpty()) {
            Iterator it = items.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView item = (WorkOrderItemDetailView) it.next();
                if (item.getWorkOrderItem() != null &&
                        item.getWorkOrderItem().getEstimatePart() != null) {
                    if (sum == null) {
                        sum = new BigDecimal(0);
                    }
                    sum = sum.add(item.getWorkOrderItem().getEstimatePart());
                }
            }
        }
        return sum;
    }

    public static WorkOrderContentViewVector getContentOnly(WorkOrderItemDetailViewVector items) {
        WorkOrderContentViewVector contents = new WorkOrderContentViewVector();
        if (items != null) {
            Iterator it = items.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView detView = (WorkOrderItemDetailView) it.next();
                if (detView.getContents() != null) {
                    contents.addAll(detView.getContents());
                }
            }
        }
        return contents;
    }

    public static IdVector getContentIds(WorkOrderContentViewVector contents) {
        IdVector contentIds = new IdVector();
        Iterator it = contents.iterator();
        while (it.hasNext()) {
            int contentId = ((WorkOrderContentView) it.next()).getContent().getContentId();
            contentIds.add(new Integer(contentId));
        }
        return contentIds;
    }

    public WorkOrderDetailView clone(WorkOrderDetailView workOrderDetailView) {

        WorkOrderDetailView newWoData = workOrderDetailView.copy();

        newWoData.getWorkOrder().setWorkOrderId(0);
        WorkOrderStatusHistDataVector statusHistory = newWoData.getStatusHistory();
        WorkOrderNoteDataVector notes = newWoData.getNotes();
        WorkOrderPropertyDataVector properties = newWoData.getProperties();
        WorkOrderContentViewVector contents = newWoData.getContents();
        WorkOrderAssocDataVector assoc = newWoData.getWorkOrderAssocCollection();
        WorkOrderItemDetailViewVector items = newWoData.getWorkOrderItems();

        Iterator it;

        it = items.iterator();
        while (it.hasNext()) {
            WorkOrderItemDetailView data = (WorkOrderItemDetailView) it.next();

            WorkOrderAssetViewVector assetAssoc = data.getAssetAssoc();
            WorkOrderContentViewVector itemContents = data.getContents();
            WorkOrderPropertyDataVector itemProperties = data.getProperties();
            WoiStatusHistDataVector itemStatusHistory = data.getStatusHistories();

            data.getWorkOrderItem().setWorkOrderId(0);
            data.getWorkOrderItem().setWorkOrderItemId(0);

            Iterator itemIt;

            itemIt = assetAssoc.iterator();
            while (itemIt.hasNext()) {
                WorkOrderAssetView itemData = (WorkOrderAssetView) itemIt.next();
                itemData.getWorkOrderAssetData().setWorkOrderAssetId(0);
                itemData.getWorkOrderAssetData().setWorkOrderItemId(0);
            }

            itemIt = itemContents.iterator();
            while (itemIt.hasNext()) {
                WorkOrderContentView itemData = (WorkOrderContentView) itemIt.next();
                itemData.getContent().setContentId(0);
                itemData.getWorkOrderContentData().setWorkOrderId(0);
                itemData.getWorkOrderContentData().setWorkOrderItemId(0);
                itemData.getWorkOrderContentData().setWorkOrderContentId(0);
                itemData.getWorkOrderContentData().setContentId(0);
            }

            itemIt = itemProperties.iterator();
            while (itemIt.hasNext()) {
                WorkOrderPropertyData itemData = (WorkOrderPropertyData) itemIt.next();
                itemData.setWorkOrderItemId(0);
                itemData.setWorkOrderId(0);
                itemData.setWorkOrderPropertyId(0);
            }

            itemIt = itemStatusHistory.iterator();
            while (itemIt.hasNext()) {
                WoiStatusHistData itemData = (WoiStatusHistData) itemIt.next();
                itemData.setWoiStatusHistId(0);
                itemData.setWorkOrderItemId(0);
            }

        }

        it = assoc.iterator();
        while (it.hasNext()) {
            WorkOrderAssocData data = (WorkOrderAssocData) it.next();
            data.setWorkOrderId(0);
            data.setWorkOrderAssocId(0);
        }

        it = statusHistory.iterator();
        while (it.hasNext()) {
            WorkOrderStatusHistData data = (WorkOrderStatusHistData) it.next();
            data.setWorkOrderStatusHistId(0);
            data.setWorkOrderId(0);
        }

        it = notes.iterator();
        while (it.hasNext()) {
            WorkOrderNoteData data = (WorkOrderNoteData) it.next();
            data.setWorkOrderNoteId(0);
            data.setWorkOrderId(0);
        }

        it = properties.iterator();
        while (it.hasNext()) {
            WorkOrderPropertyData data = (WorkOrderPropertyData) it.next();
            data.setWorkOrderItemId(0);
            data.setWorkOrderId(0);
            data.setWorkOrderPropertyId(0);
        }

        it = contents.iterator();
        while (it.hasNext()) {
            WorkOrderContentView data = ((WorkOrderContentView) it.next());
            data.getContent().setContentId(0);
            data.getWorkOrderContentData().setWorkOrderId(0);
            data.getWorkOrderContentData().setWorkOrderItemId(0);
            data.getWorkOrderContentData().setWorkOrderContentId(0);
            data.getWorkOrderContentData().setContentId(0);
        }
        return newWoData;
    }

    public static int getAssignedAssetId(WorkOrderAssetViewVector assetAssoc) {
        if (assetAssoc != null) {
            if (!assetAssoc.isEmpty()) {
                return ((WorkOrderAssetView) assetAssoc.get(0)).getAssetView().getAssetId();
            }
        }
        return 0;
    }

    private static void populateWorkOrderContact(WorkOrderPropertyDataVector properties, UserInfoData contact) {
        if (properties != null && !properties.isEmpty()) {
            Iterator it = properties.iterator();
            while (it.hasNext()) {
                WorkOrderPropertyData workOrderProperty = (WorkOrderPropertyData) it.next();
                if (RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO.equals(workOrderProperty.getPropertyCd())) {
                    if (RefCodeNames.WORK_ORDER_CONTACT.ADDRESS1.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getAddressData().getAddress1());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.ADDRESS2.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getAddressData().getAddress2());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.CITY.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getAddressData().getCity());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.COUNTRY.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getAddressData().getCountryCd());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.FAX.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getFax().getPhoneNum());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.PHONE.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getPhone().getPhoneNum());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.MOBILE.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getMobile().getPhoneNum());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.FIRST_NAME.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getAddressData().getName1());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.LAST_NAME.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getAddressData().getName2());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.STATE.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getAddressData().getStateProvinceCd());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.ZIP.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getAddressData().getPostalCode());
                    } else if (RefCodeNames.WORK_ORDER_CONTACT.EMAIL.equals(workOrderProperty.getShortDesc())) {
                        workOrderProperty.setValue(contact.getEmailData().getEmailAddress());
                    }
                }
            }
        }
    }
    
    public static WorkOrderPropertyDataVector getSiteContactDataAsWOPropertyVector(SiteData sd) {
        WorkOrderPropertyDataVector workOrderProperties = WorkOrderUtil.createEmptyWorkOrderContact();
        
        if (sd != null) {
            AddressData addressD = sd.getSiteAddress();
            PhoneDataVector phoneDV = sd.getPhones();
            Iterator it = workOrderProperties.iterator();
            if (addressD != null) {
                while (it.hasNext()) {
                    WorkOrderPropertyData workOrderProperty = (WorkOrderPropertyData) it.next();
                    if(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO.equals(workOrderProperty.getPropertyCd())){
                        if(RefCodeNames.WORK_ORDER_CONTACT.ADDRESS1.equals(workOrderProperty.getShortDesc())){
                            workOrderProperty.setValue(addressD.getAddress1());
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.ADDRESS2.equals(workOrderProperty.getShortDesc())){
                            workOrderProperty.setValue(addressD.getAddress2());
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.CITY.equals(workOrderProperty.getShortDesc())){
                            workOrderProperty.setValue(addressD.getCity());
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.COUNTRY.equals(workOrderProperty.getShortDesc())){
                            workOrderProperty.setValue(addressD.getCountryCd());
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.FAX.equals(workOrderProperty.getShortDesc())){
                            //PhoneData phone = null;
                            for (int i = 0; i < phoneDV.size(); i++) {
                                PhoneData phone = (PhoneData)phoneDV.get(i);
                                if (phone.getPhoneTypeCd().equals(RefCodeNames.PHONE_TYPE_CD.FAX)) {
                                    workOrderProperty.setValue(phone.getPhoneNum());
                                    break;
                                }
                            }
                            //if (phone != null) {
                            //    workOrderProperty.setValue(phone.getPhoneNum());
                            //}
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.PHONE.equals(workOrderProperty.getShortDesc())){
                            //PhoneData phone = null;
                            for (int i = 0; i < phoneDV.size(); i++) {
                                PhoneData phone = (PhoneData)phoneDV.get(i);
                                if (phone.getPhoneTypeCd().equals(RefCodeNames.PHONE_TYPE_CD.PHONE)) {
                                    workOrderProperty.setValue(phone.getPhoneNum());
                                    break;
                                }
                            }
                            //if (phone != null) {
                            //    workOrderProperty.setValue(phone.getPhoneNum());
                            //}
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.MOBILE.equals(workOrderProperty.getShortDesc())){
                            //PhoneData phone = null;
                            for (int i = 0; i < phoneDV.size(); i++) {
                                PhoneData phone = (PhoneData)phoneDV.get(i);
                                if (phone.getPhoneTypeCd().equals(RefCodeNames.PHONE_TYPE_CD.MOBILE)) {
                                    workOrderProperty.setValue(phone.getPhoneNum());
                                    break;
                                }
                            }
                            //if (phone != null) {
                            //    workOrderProperty.setValue(phone.getPhoneNum());
                            //}
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.FIRST_NAME.equals(workOrderProperty.getShortDesc())){
                            workOrderProperty.setValue(addressD.getName1());
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.LAST_NAME.equals(workOrderProperty.getShortDesc())){
                            workOrderProperty.setValue(addressD.getName2());
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.STATE.equals(workOrderProperty.getShortDesc())){
                            workOrderProperty.setValue(addressD.getStateProvinceCd());
                        } else if(RefCodeNames.WORK_ORDER_CONTACT.ZIP.equals(workOrderProperty.getShortDesc())){
                            workOrderProperty.setValue(addressD.getPostalCode());
                        }
                    }
                }
            }
        }
        return workOrderProperties;
    }

    public static WorkOrderNoteData findNote(WorkOrderNoteDataVector workOrderNotes, int workOrderNoteId) {
        if (workOrderNotes != null) {
            Iterator it = workOrderNotes.iterator();
            while (it.hasNext()) {
                WorkOrderNoteData note = (WorkOrderNoteData) it.next();
                if (note.getWorkOrderNoteId() == workOrderNoteId) {
                    return note;
                }
            }
        }
        return null;
    }

    public static WorkOrderContentView findWarrantyContent(WorkOrderContentViewVector wrkContent, int workorderContentIdInt) {
        if (wrkContent != null) {
            Iterator it = wrkContent.iterator();
            while (it.hasNext()) {
                WorkOrderContentView content = (WorkOrderContentView) it.next();
                if (content.getWorkOrderContentData() != null &&
                        content.getWorkOrderContentData().getWorkOrderContentId() == workorderContentIdInt) {
                    return content;
                }
            }
        }
        return null;
    }

    public static AssetDataVector findAssets(AssetDataVector allAssets, int catId) {
        AssetDataVector result = new AssetDataVector();
        if (allAssets != null) {
            Iterator it = allAssets.iterator();
            while (it.hasNext()) {
                AssetData asset = (AssetData) it.next();
                if (asset != null && asset.getParentId() == catId) {
                    if (!result.contains(asset)) {
                        result.add(asset);
                    }
                }
            }
        }
        return result;
    }

    public static AssetData findAsset(AssetDataVector assets, int assetIdInt) {
        if (assets != null) {
            Iterator it = assets.iterator();
            while (it.hasNext()) {
                AssetData asset = (AssetData) it.next();
                if (asset != null && asset.getAssetId() == assetIdInt) {
                    return asset;
                }
            }
        }
        return null;
    }

    public static WorkOrderPropertyDataVector convertUserContact(UserInfoData userInfoData) {
        WorkOrderPropertyDataVector wrkContactProps = createEmptyWorkOrderContact();
        populateWorkOrderContact(wrkContactProps, userInfoData);
        return wrkContactProps;
    }

    /**
     * gets sum by all values
     *
     * @param woTotalCostMap key is identifier of sum,object is BigDecimal total cost
     * @return sum
     */
    public static BigDecimal costSum(HashMap woTotalCostMap) {
        BigDecimal total = null;
        if (woTotalCostMap != null) {
            Iterator it = woTotalCostMap.values().iterator();
            while (it.hasNext()) {
                total = Utility.addAmt(total, (BigDecimal) it.next());
            }
        }
        return total;
    }

    public static WorkOrderItemDetailView findItem(WorkOrderItemDetailViewVector workOrderItems, int workOrderItemId) {
        if (workOrderItems != null) {
            Iterator it = workOrderItems.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView item = (WorkOrderItemDetailView) it.next();
                if (item != null && item.getWorkOrderItem().getWorkOrderItemId() == workOrderItemId) {
                    return item;
                }
            }
        }
        return null;
    }

    public static BigDecimal getWorkOrderAmount(WorkOrderItemDetailViewVector workOrderItems) {
        BigDecimal workOrderAmmount = null;

        BigDecimal actualPartSum = getActualPartSum(workOrderItems);
        if (actualPartSum != null) {
            workOrderAmmount = Utility.addAmt(workOrderAmmount, actualPartSum);
        }

        BigDecimal actualLaborSum = getActualLaborSum(workOrderItems);
        if (actualLaborSum != null) {
            workOrderAmmount = Utility.addAmt(workOrderAmmount, actualLaborSum);
        }

        if (workOrderAmmount == null) {
            BigDecimal estimatePartSum = getEstimatedPartSum(workOrderItems);
            if (estimatePartSum != null) {
                workOrderAmmount = Utility.addAmt(workOrderAmmount, estimatePartSum);
            }

            BigDecimal estimateLaborSum = getEstimatedPartSum(workOrderItems);
            if (estimateLaborSum != null) {
                workOrderAmmount = Utility.addAmt(workOrderAmmount, estimateLaborSum);
            }
        }

        return workOrderAmmount;
    }

    public static BigDecimal getWorkOrderAmount(WorkOrderDetailDataVector workOrderDetailDataVector) {
    	BigDecimal workOrderAmount = new BigDecimal(0);
        Iterator iterator = workOrderDetailDataVector.iterator();
        while (iterator.hasNext()) {
        	WorkOrderDetailData workOrderDetailData = (WorkOrderDetailData) iterator.next();
        	if(workOrderDetailData.getStatusCd().equals(RefCodeNames.STATUS_CD.ACTIVE))
        	{
        		workOrderAmount = workOrderAmount.add(workOrderDetailData.getPartPrice().multiply(
        				new BigDecimal(workOrderDetailData.getQuantity())));
        	}
		}
    	return workOrderAmount;
    }

    public static Date getActualWorkOrderDate(WorkOrderData wrkData) {
        Date actualDate = wrkData.getActualStartDate();
        if (actualDate == null) {
            return wrkData.getEstimateStartDate();
        }
        return actualDate;
    }
}
