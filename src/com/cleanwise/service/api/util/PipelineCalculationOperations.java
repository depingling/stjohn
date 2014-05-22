package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.wrapper.BudgetViewWrapper;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.session.AccountBean;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.pipeline.OrderPipelineBaton;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Title:        PipelineCalculationOperations
 * Description: Class contains methods  which calculates summ for order, items and so on
 *
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * User: Vlasov Evgeny
 * Date: 27.12.2006
 * Time: 23:19:58
 */
public class PipelineCalculationOperations {

    public static BigDecimal getTotalAmountWhithALLOperations(Connection pCon, OrderData orderD,OrderPipelineBaton pBaton) throws Exception {
        //Calculate the order total
        BigDecimal orderTotal = new BigDecimal(0.00);
        HashMap ccSum = getTotalAmountForCostCenter(pCon,orderD,pBaton);
        Iterator it = ccSum.keySet().iterator();
        while(it.hasNext()){
            Integer thisCostCenterIdKey = (Integer) it.next();
            BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(thisCostCenterIdKey);
            orderTotal = orderTotal.add(thisCostCenterSum);
        }

        return orderTotal;
    }

    public static HashMap getTotalAmountForCostCenter(Connection pCon, OrderData orderD,OrderPipelineBaton pBaton) throws Exception {

        IdVector ids = new IdVector();
        ids.add(new Integer(orderD.getOrderId()));
        //HashMap hmCostCentrData = loadCostCentersInfo(ids, pCon);

        HashMap ccSum = new HashMap();
        //ArrayList ccParams = (ArrayList)hmCostCentrData.get(String.valueOf(orderD.getOrderId()));

        ArrayList ccParams = loadCostCentersInfo(pBaton);
        if(ccParams!=null){
        	ccSum = recalculteCostCenterInfo(pCon,orderD,ccParams,true) ;
        }

        return ccSum;
    }

    public static HashMap loadCostCentersInfo(IdVector oIds,Connection pCon) throws Exception {

        int lNumException=0;
        String queryOrderIds=new String();
        HashMap hmCostCentrData=new HashMap();
        int pi=0;
        IdVector pIds=new IdVector();
        for(int j=0;j<=(int)(oIds.size()/1000);j++)
        {
            pi=0;
            queryOrderIds=new String();
            for(int idx=0;idx <(j==(int)(oIds.size()/1000)?(oIds.size()%1000):1000);idx++)
            {
                pIds.add(oIds.get((j*1000)+idx));
            }
            Iterator it=pIds.iterator();
            for(int i=0;it.hasNext();i++)
            {
                if(i!=0) queryOrderIds+=",";
                queryOrderIds+=((Integer)it.next()).toString();
            }
            // Make a ledger entry for each cost center in the order.
            // Get the cost centers and amounts spent in this order.
            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(OrderItemDataAccess.ORDER_ID,pIds);
            OrderItemDataVector orderItemDV = OrderItemDataAccess.select(pCon,dbc);

            String query = "select oi.cost_center_id,oi.order_id," +
                    " sum(oi.cust_contract_price * oi.total_quantity_ordered), " +
                    " sale_type_cd ,sum(tax_amount) from clw_order_item oi " +
                    " where oi.cost_center_id != 0 AND oi.order_id in ("+queryOrderIds+ ") " +
                    " and ( order_item_status_cd != 'CANCELLED' or order_item_status_cd is null ) group by oi.cost_center_id,oi.order_id, sale_type_cd";


            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = pCon.createStatement();
                rs = stmt.executeQuery(query);

                // log.logln(" =cI="+"==Oi="+"=s+="+"=sT=");
                while (rs.next()) {
                    String param[]=new String[5];
                    Integer thisCostCenterId = new Integer(rs.getInt(1));
                    Integer thisOrderId=new Integer(rs.getInt(2));
                    BigDecimal thisCostCenterSum = new BigDecimal(rs.getDouble(3));
                    String saleType = rs.getString(4);
                    BigDecimal taxAmount = rs.getBigDecimal(5);

                    param[0]=String.valueOf(thisCostCenterId);
                    param[1]=String.valueOf(thisOrderId);
                    param[2]=thisCostCenterSum.toString();
                    param[3]=saleType;
                    param[4]=taxAmount==null?new BigDecimal(0).toString():taxAmount.toString();
                    if(hmCostCentrData.containsKey(param[1]))
                    {
                        ArrayList arrayParams = (ArrayList) hmCostCentrData.get(param[1]);
                        arrayParams.add(param);
                        hmCostCentrData.put(param[1],arrayParams);
                    }
                    else{
                        ArrayList arrayParams = new ArrayList();
                        arrayParams.add(param);
                        hmCostCentrData.put(param[1],arrayParams);
                    }
                    pi++;
                }

            }  catch (Exception e) {
                if(lNumException>=((int)(oIds.size()/1000)))  throw new Exception("many data can't be processed");
            }

        }
        return hmCostCentrData;
    }

    public static HashMap recalculteCostCenterInfo(Connection pCon, OrderData orderData,
                                                ArrayList params, Boolean pIncludeNoBudgetFl) 
		throws TaxCalculationException, SQLException, PipelineException,Exception {

        HashMap ccSum = new HashMap();
        HashMap ccTaxSum = new HashMap();
        BigDecimal taxTotal = new BigDecimal(0.00);
        Iterator it = params.iterator();
        while (it.hasNext()) {
            String[] param = (String[]) it.next();

            Integer thisCostCenterId = new Integer(param[0]);
            //Integer thisOrderId=new Integer(param[1]);
            BigDecimal thisCostCenterSum = new BigDecimal(param[2]);
            if (ccSum.containsKey(thisCostCenterId)) {
                BigDecimal toAdd = (BigDecimal) ccSum.get(thisCostCenterId);
                thisCostCenterSum = thisCostCenterSum.add(toAdd);
            }

            BigDecimal taxAdd = new BigDecimal(param[4]);
            taxTotal = taxTotal.add(taxAdd);
            if(ccTaxSum.containsKey(thisCostCenterId)){
                 BigDecimal ccTax = (BigDecimal) ccTaxSum.get(thisCostCenterId);
                 ccTaxSum.put(thisCostCenterId,Utility.addAmt(ccTax,taxAdd));
             }
             else
             {
                 ccTaxSum.put(thisCostCenterId,taxAdd);
             }

            ccSum.put(thisCostCenterId, thisCostCenterSum);
        }

        // now add in freight and sales tax

        BigDecimal frt = Utility.addAmt(orderData.getTotalMiscCost(), orderData.getTotalFreightCost());

        AccountBean actEjb = new AccountBean(); //Not a good pracice. Have to change. YK !!!!!!!!!
        CostCenterDataVector ccdv = actEjb.getAllCostCenters(orderData.getAccountId(), Account.ORDER_BY_ID, pCon);

        it=ccdv.iterator();

        boolean foundFreightCC = false;
        boolean foundDiscountCC = false;
        while(it.hasNext()){
            CostCenterData ccd = (CostCenterData) it.next();
            Integer key = new Integer(ccd.getCostCenterId());

            if(Utility.isTrue(ccd.getNoBudget(), true)){
			    if(!pIncludeNoBudgetFl) { // Clean amount if not interested  in NO BUDGET cost centers ( I did not change condition above no minimize impact) YK
					BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
					if(thisCostCenterSum != null) ccSum.put(key, new BigDecimal(0));
				}
            	continue;
            }
            //******************first check on freight******************

            if(Utility.isTrue(ccd.getAllocateFreight())){
                if(foundFreightCC){
                    throw new PipelineException("account has multiple cost centers setup to allocate freight to");
                }
                foundFreightCC = true;
                BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
                thisCostCenterSum = Utility.addAmt(thisCostCenterSum, frt);
                thisCostCenterSum  = Utility.addAmt(thisCostCenterSum, orderData.getTotalRushCharge());
                ccSum.put(key,thisCostCenterSum);
            }//end Utility.isTrue


            if (Utility.isTrue(ccd.getAllocateDiscount())) {
                if (foundDiscountCC) {
                    throw new PipelineException("Account has multiple cost centers setup to allocate discount.");
                }
                BigDecimal discount = OrderDAO.getDiscountAmt(pCon, orderData.getOrderId());
                BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
//                thisCostCenterSum = Utility.addAmt(thisCostCenterSum, discount);
                //Bug STJ-6271 -dsicount value should be considered as negative value while calculating the caluculated amount 
                thisCostCenterSum = Utility.subtractAmt(thisCostCenterSum, discount);
                ccSum.put(key, thisCostCenterSum);
                foundDiscountCC = true;
            }


            if(RefCodeNames.COST_CENTER_TAX_TYPE.ALLOCATE_PRODUCT_SALES_TAX.equals(ccd.getCostCenterTaxType())){
                BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
                BigDecimal taxCostCenterSum = (BigDecimal) ccTaxSum.get(key);
                thisCostCenterSum = Utility.addAmt(taxCostCenterSum, thisCostCenterSum);
                ccSum.put(key,thisCostCenterSum);
            }else if(RefCodeNames.COST_CENTER_TAX_TYPE.MASTER_SALES_TAX_COST_CENTER.equals(ccd.getCostCenterTaxType())){
                BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(key);
                thisCostCenterSum = Utility.addAmt(taxTotal, thisCostCenterSum);
                ccSum.put(key,thisCostCenterSum);
            }   //else don't do anything with the sales tax

        }
        return ccSum;
    }

    public static BigDecimal getTotalAmountWhithALLOperations(Connection pCon, OrderPipelineBaton pBaton, SiteData siteD)
    throws Exception {
        //Calculate the order total
    	BigDecimal orderTotal = new BigDecimal(0.00);
        ArrayList ccParams = loadCostCentersInfo(pBaton);
        HashMap ccSum = new HashMap();
        OrderData orderD = pBaton.getOrderData();
        if(ccParams!=null && !ccParams.isEmpty()){                
        	ccSum = recalculteCostCenterInfo(pCon,orderD,ccParams,false) ;
        	Iterator it = ccSum.keySet().iterator();
            while(it.hasNext()){
                Integer thisCostCenterIdKey = (Integer) it.next();
                if (thisCostCenterIdKey.intValue() == 0)
                	continue;
                if (siteD.isBudgetUnlimited(thisCostCenterIdKey.intValue()))
                	continue;
                BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(thisCostCenterIdKey);
                orderTotal = orderTotal.add(thisCostCenterSum);
            }
        }
        return orderTotal;
    }

    public static ArrayList loadCostCentersInfo(OrderPipelineBaton pBaton) throws Exception {
        OrderItemDataVector orderItemDV = pBaton.getOrderItemDataVector();
        return loadCostCentersInfo(orderItemDV);
    }

    public static ArrayList loadCostCentersInfo(OrderItemDataVector orderItemDV) throws Exception {


            ArrayList costCenterAL = new ArrayList();

            if(orderItemDV==null) {
                return costCenterAL;
            }
            //Group by cost center
            HashMap orderItemGroupHM = new HashMap();

            for(Iterator iter = orderItemDV.iterator(); iter.hasNext();) {
                OrderItemData oiD = (OrderItemData) iter.next();
                int ccId = oiD.getCostCenterId();
                String saleTypeCd = oiD.getSaleTypeCd();
                if(saleTypeCd == null) saleTypeCd = "";
                String key = String.valueOf(ccId)+"@"+saleTypeCd;
                OrderItemDataVector oiDV = (OrderItemDataVector) orderItemGroupHM.get(key);
                if(oiDV == null) {
                    oiDV = new OrderItemDataVector();
                    orderItemGroupHM.put(key, oiDV);
                }
                oiDV.add(oiD);
            }
            Set keys = orderItemGroupHM.keySet();
            for(Iterator iter=keys.iterator(); iter.hasNext();) {
                String key = (String) iter.next();
                OrderItemDataVector oiDV =
                       (OrderItemDataVector) orderItemGroupHM.get(key);
                double sumAmount = 0;
                BigDecimal taxAmount =new BigDecimal(0);
                String param[]=new String[5];
                int ind = key.indexOf("@");
                param[0] = key.substring(0,ind);
                param[1] = "0";
                param[2] = null;
                param[3]=(ind<key.length()-1)?key.substring(ind+1):null;

                for(Iterator iter1=oiDV.iterator();iter1.hasNext();) {
                    OrderItemData oiD = (OrderItemData) iter1.next();
                    BigDecimal contrPriceBD = oiD.getCustContractPrice();

                    if(contrPriceBD!=null){
                       sumAmount += contrPriceBD.doubleValue()*oiD.getTotalQuantityOrdered();
                    }

                    taxAmount=Utility.addAmt(taxAmount,oiD.getTaxAmount());

               }

                param[2] =
                    (new BigDecimal(sumAmount)).setScale(2,BigDecimal.ROUND_HALF_UP).toString();

                param[4] =taxAmount.toString();

                costCenterAL.add(param);
            }
            return costCenterAL;
        }

     public static BigDecimal getTotalAmountWhithALLOperations(Connection pCon, OrderItemDataVector orderItemDV,OrderData orderD)
     throws Exception {
        //Calculate the order total
        BigDecimal orderTotal = new BigDecimal(0.00);
        {
            ArrayList ccParams = loadCostCentersInfo(orderItemDV);
            HashMap ccSum = new HashMap();
            if(ccParams!=null && !ccParams.isEmpty()){
            	ccSum = recalculteCostCenterInfo(pCon,orderD,ccParams,true) ;
            	Iterator it = ccSum.keySet().iterator();
                while(it.hasNext()){
                    Integer thisCostCenterIdKey = (Integer) it.next();
                    BigDecimal thisCostCenterSum = (BigDecimal) ccSum.get(thisCostCenterIdKey);
                    orderTotal = orderTotal.add(thisCostCenterSum);
                }
            }
        }

        return orderTotal;
    }

}
