package com.cleanwise.service.api.util;


import java.util.Iterator;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.api.session.Contract;
import com.cleanwise.service.api.value.ContractData;
import com.cleanwise.service.api.value.ContractDescData;
import com.cleanwise.service.api.value.ContractDescDataVector;
import com.cleanwise.service.api.value.OrderRoutingData;

/**
 * @author Alexander Chikin
 * Date: 24.08.2006
 * Time: 16:34:27
 */
public class OrderRoutingUtil {
	private static final Logger log = Logger.getLogger(OrderRoutingUtil.class);
	
    public static  void  exchangeOrderRoutingData(OrderRoutingData orderRoutingData,boolean isC,boolean isFinalC) throws Exception {

        APIAccess factory = new APIAccess();
        Contract contractEjb = factory.getContractAPI();
        if (isC) {

            boolean isContractDescDV=false;
            int realContractId=-1;
            int excContractId_cat = orderRoutingData.getContractId();
            ContractDescDataVector contractDescDV = contractEjb.getContractDescsByCatalog(excContractId_cat);
            if(contractDescDV!=null&&contractDescDV.size()>0)
                isContractDescDV=true;
            Iterator it = contractDescDV.iterator();
            while (it.hasNext()) {
                ContractDescData contractDescD = (ContractDescData) it.next();
                if (contractDescD.getStatus().equalsIgnoreCase(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE)) {
                    realContractId = contractDescD.getContractId();
                    break;
                }
            }
            if(realContractId>0)
            {  orderRoutingData.setContractId(realContractId);

            }else {
                if(isContractDescDV) {
                    orderRoutingData.setContractId(0);
                    throw new Exception("clw^Problems with selected catalog.^clw^");
                    //throw new Exception("Error! ,Not found Active Contract by Catalog :"+ excContractId_cat);

                }
                else {
                    orderRoutingData.setContractId(0);
                    throw new Exception("^clw^Problems with selected catalog^clw^");
                    //throw new Exception("Not found Contract by Catalog"+ excContractId_cat);

                }


            }

        }
        if (isFinalC) {

            boolean isContractDescDV=false;
            int realContractId=-1;
            int excContractId_cat = orderRoutingData.getFinalContractId();
            ContractDescDataVector contractDescDV = contractEjb.getContractDescsByCatalog(excContractId_cat);
            if(contractDescDV!=null&&contractDescDV.size()>0)
                isContractDescDV=true;
            Iterator it = contractDescDV.iterator();
            while (it.hasNext()) {
                ContractDescData contractDescD = (ContractDescData) it.next();
                if (contractDescD.getStatus().equalsIgnoreCase(RefCodeNames.CONTRACT_STATUS_CD.ACTIVE)) {
                    realContractId = contractDescD.getContractId();
                    break;
                }
            }
            if(realContractId>0)
            {

                orderRoutingData.setFinalContractId(realContractId);

            }       else {
                if(isContractDescDV) {

                    orderRoutingData.setFinalContractId(0);
                    throw new Exception("^clw^Problems with selected final catalog.^clw^");
                    //throw new Exception("Error! ,Not found Active Contract by Final Catalog :"+ excContractId_cat);

                }
                else {
                    orderRoutingData.setFinalContractId(0);
                    throw new Exception("^clw^Problems with selected final catalog^clw^");
                    //throw new Exception("Not found Contract by Final Catalog"+ excContractId_cat);
                }


            }
        }

    }
    public static void backExchangeOrderRoutingData(OrderRoutingData orderRoutingData,boolean isC,boolean isFinalC) throws Exception, APIServiceAccessException {

        APIAccess factory = new APIAccess();
        Contract contractEjb = factory.getContractAPI();
        if(isC) {

                log.info("OrderRoutingUtil.backExchangeOrderRoutingData informs : ");
                int realContractId =orderRoutingData.getContractId();
                ContractData cd= contractEjb.getContract(realContractId);
                int excContractId_cat= cd.getCatalogId();
                if(excContractId_cat<=0)
                {
                    orderRoutingData.setContractId(0);
                    throw new Exception("^clw^The Catalog does not exist.^clw^");

                }
                orderRoutingData.setContractId(excContractId_cat);
        }
        if(isFinalC)
        {
                log.info("<OrderRoutingUtil. backExchangeOrderRoutingData informs :");
                int realContractId =orderRoutingData.getFinalContractId();
                ContractData cd= contractEjb.getContract(realContractId);
                int excContractId_cat= cd.getCatalogId();
                if(excContractId_cat<=0)
                {
                    orderRoutingData.setFinalContractId(0);
                    throw new Exception("^clw^The Final catalog does not exist.^clw^");

                }
                orderRoutingData.setFinalContractId(excContractId_cat);
         }

    }
}
