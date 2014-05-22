package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.WorkOrderUtil;
import com.cleanwise.service.api.value.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


/**
 */

public class MakeLedgerEntry {
    private static String className = "MakeLedgerEntry";

    public void ledgerUpdate(WorkOrderDetailView workOrderDetail, UserData user) throws Exception {
		ledgerUpdate(workOrderDetail.getWorkOrder(), user.getUserName());
	}

	public void ledgerUpdate(WorkOrderData workOrderData, String userName) throws Exception {

        Account accounEjb = APIAccess.getAPIAccess().getAccountAPI();
        Store storeEjb    = APIAccess.getAPIAccess().getStoreAPI();
        Site siteEjb      = APIAccess.getAPIAccess().getSiteAPI();
        WorkOrder wrkEjb  = APIAccess.getAPIAccess().getWorkOrderAPI();
        Budget budgetEjb  = APIAccess.getAPIAccess().getBudgetAPI();

        int workOrderId = workOrderData.getWorkOrderId();
        WorkOrderData workOrder = wrkEjb.getWorkOrderDetailView(workOrderId).getWorkOrder();

//        Spent:  Rolls up the itemized detail for ALL work orders for either the Account or 
//        Site (based off of Cost Center Type; SITE WORK ORDER BUDGET or ACCOUNT WORK ORDER BUDGET) and, 
//        for the configured period (YTD or current period) if the work order is in the following status:

//        Approved
//		  Sent to Provider
//		  Accepted by Provider
//		  Completed
//		  Closed
        
        if (!(RefCodeNames.WORK_ORDER_STATUS_CD.APPROVED.equals(workOrder.getStatusCd()) ||
   			RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER.equals(workOrder.getStatusCd()) ||
			RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER.equals(workOrder.getStatusCd()) ||
			RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER.equals(workOrder.getStatusCd()) ||
			RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED.equals(workOrder.getStatusCd()) ||
			RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED.equals(workOrder.getStatusCd()) )) 
        {
            ledgerRemove(workOrderId);
            return;
        }

        int siteId = workOrder.getBusEntityId();

        AccountData account;
        try {
            account = accounEjb.getAccountForSite(siteId);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return;
        }

        int accountId = account.getAccountId();

        int storeId;
        try {
            storeId = storeEjb.getStoreIdByAccount(accountId);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return;
        }

        if (siteId <= 0 || storeId <= 0 || accountId <= 0) {
            return;
        }

        //Date orderDate = WorkOrderUtil.getActualWorkOrderDate(workOrder);
        Date orderDate = workOrder.getAddDate();
        HashMap ccOrderSumMap = wrkEjb.getCostCentersOrderSumMap(workOrderId);

        FiscalCalenderData fiscCal = accounEjb.getFiscalCalender(accountId, orderDate);
        int fiscalYear;
        int fiscalCalId;
        //int fiscalPeriod;
        if (fiscCal == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(orderDate);
            fiscalYear = cal.get(Calendar.YEAR);
            fiscalCalId = 0;
        } else {
            fiscalYear = fiscCal.getFiscalYear();
            fiscalCalId = fiscCal.getFiscalCalenderId();
        }

        SiteLedgerDataVector existingLedgers = wrkEjb.getSiteLedgerEntries(workOrderId);

        SiteLedgerDataVector processedLedgers = new SiteLedgerDataVector();

        Iterator it = ccOrderSumMap.keySet().iterator();
        while (it.hasNext()) {

            Integer thisCostCenterIdKey = (Integer) it.next();
            int thisCostCenterId = thisCostCenterIdKey.intValue();
            BigDecimal thisCostCenterSum = (BigDecimal) ccOrderSumMap.get(thisCostCenterIdKey);
            if(thisCostCenterSum == null)
            {
            	thisCostCenterSum = new BigDecimal(0.00);
            }
            int budPeriod = budgetEjb.getAccountBudgetPeriod(accountId, siteId, orderDate);

            SiteLedgerData le = null;
            Iterator sit = existingLedgers.iterator();
            while (sit.hasNext()) {
                SiteLedgerData ale = (SiteLedgerData) sit.next();
                if (ale.getCostCenterId() == thisCostCenterId) {
                    sit.remove(); //we will set any leftovers to 0
                    le = ale;
                }
            }

            
            if (le == null) {
                le = SiteLedgerData.createValue();
                le.setAddBy(userName);
            }
            //reset the budget period if it is not set
            if (le.getBudgetPeriod() <= 0) {
                le.setBudgetPeriod(budPeriod);
            }
            //reset fiscal calender if it is not set
            if (le.getFiscalCalenderId() <= 0) {
                le.setFiscalCalenderId(fiscalCalId);
            }
            //reset budget year if it is not set
            if (le.getBudgetYear() <= 0) {
                le.setBudgetYear(fiscalYear);
            }
            le.setWorkOrderId(workOrderId);
            le.setSiteId(siteId);
            le.setCostCenterId(thisCostCenterId);

            le.setAmount(thisCostCenterSum);
            le.setEntryTypeCd(RefCodeNames.LEDGER_ENTRY_TYPE_CD.WORK_ORDER);
            le.setModBy(userName);


            if (le.getSiteLedgerId() == 0) {

                if (thisCostCenterSum.compareTo(new BigDecimal(0.00)) != 0) {
                    processedLedgers.add(le);
                }
            } else {
                processedLedgers.add(le);
            }
        }

        //loop through any leftover ledger entries and set them to be empty
        it = existingLedgers.iterator();
        while (it.hasNext()) {
            SiteLedgerData le = (SiteLedgerData) it.next();
            le.setAmount(new BigDecimal(0.00));
            processedLedgers.add(le);
        }

        siteEjb.updateSiteLedgerCollection(processedLedgers);
    }

    public void ledgerRemove(WorkOrderDetailView workOrderDetail, UserData user) throws Exception {
		ledgerRemove(workOrderDetail.getWorkOrder().getWorkOrderId());
	}

	public void ledgerRemove(int workOrderId) throws Exception {
        WorkOrder wrkEjb = APIAccess.getAPIAccess().getWorkOrderAPI();
        wrkEjb.removeSiteLedgerEntries(workOrderId);
    }


}

