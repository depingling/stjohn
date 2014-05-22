package com.cleanwise.service.api.pipeline;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Order;

import java.sql.Connection;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;

import java.math.BigDecimal;
import java.util.Date;
import java.rmi.RemoteException;

import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.OrderMetaData;

import javax.naming.NamingException;

public abstract class StoreOrderPipeline implements OrderPipeline {

      public abstract OrderPipelineBaton process(OrderPipelineBaton pBaton,
                                                 OrderPipelineActor pActor,
                                                 Connection pCon,
                                                 APIAccess pFactory)
          throws PipelineException;

      /**
       *calculate handling charges
       *@param pOrder order
       */
      protected void setHandlingCharges(OrderData pOrder,
                                        BigDecimal pFreight,
                                        BigDecimal pMisc) {

        BigDecimal freight = pFreight;
        BigDecimal misc = pMisc;

        freight = freight.setScale(2, BigDecimal.ROUND_HALF_UP);
        misc = misc.setScale(2, BigDecimal.ROUND_HALF_UP);

        //Update order if necessary
        BigDecimal orderFreight = (pOrder.getTotalFreightCost() == null) ?
            (new BigDecimal(0)) : pOrder.getTotalFreightCost().abs();

        BigDecimal orderMisc = (pOrder.getTotalMiscCost() == null) ?
            (new BigDecimal(0)) : pOrder.getTotalMiscCost().abs();

        if (orderFreight.subtract(freight).abs().doubleValue() > 0.005 ||
            orderMisc.subtract(misc).abs().doubleValue() > 0.005) {
          pOrder.setTotalFreightCost(freight);
          pOrder.setTotalMiscCost(misc);
        }
      }


    /**
     * calculate FuelSurcharge & SmallOrderFee
     *
     * @param pOrder order
     */
    protected void setMetaCharges(OrderData pOrder,
                                  OrderMetaDataVector pOrderMeta,
                                  BigDecimal pSmallOrderFee,
                                  BigDecimal pFuelSurcharge,
                                  String userName) throws Exception {
        setMetaCharges(pOrder, pOrderMeta, pSmallOrderFee, pFuelSurcharge, null, userName);
    }

    protected void setMetaCharges(OrderData pOrder,
                                  OrderMetaDataVector pOrderMeta,
                                  BigDecimal pSmallOrderFee,
                                  BigDecimal pFuelSurcharge,
                                  BigDecimal pDiscount,
                                  String userName) throws Exception {

        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();

        BigDecimal smallOrderFee = pSmallOrderFee;
        if (smallOrderFee != null) {
            smallOrderFee = smallOrderFee.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        BigDecimal fuelSurcharge = pFuelSurcharge;
        if (fuelSurcharge != null) {
            fuelSurcharge = fuelSurcharge.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        BigDecimal discount = pDiscount;
        if (discount != null) {
            discount = discount.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        BigDecimal orderSmallOrderFee = null;
        OrderMetaData mSmallOrderFee = getMetaObject(pOrder, pOrderMeta, RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
        if (mSmallOrderFee != null && Utility.isSet(mSmallOrderFee.getValue())) {
            orderSmallOrderFee = new BigDecimal(mSmallOrderFee.getValue());
        }

        BigDecimal orderFuelsurcharge = null;
        OrderMetaData mFuelsurcharge = getMetaObject(pOrder, pOrderMeta, RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
        if (mFuelsurcharge != null && Utility.isSet(mFuelsurcharge.getValue())) {
            orderFuelsurcharge = new BigDecimal(mFuelsurcharge.getValue());
        }

        BigDecimal orderDiscount = null;
        OrderMetaData mDiscount = getMetaObject(pOrder, pOrderMeta, RefCodeNames.CHARGE_CD.DISCOUNT);
        if (mDiscount != null && Utility.isSet(mDiscount.getValue())) {
        	String dVal = mDiscount.getValue();
        	if(dVal.startsWith("-")){
        		dVal = dVal.replaceAll("-", "");
        	}
            orderDiscount = new BigDecimal(dVal);
        }

        if (smallOrderFee != null) {
            if ((orderSmallOrderFee != null && orderSmallOrderFee.subtract(smallOrderFee).abs().doubleValue() > 0.005) ||
                    orderSmallOrderFee == null) {
                updateMetaAttribute(orderEjb, pOrder.getOrderId(), RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE, smallOrderFee.toString(), userName);
            }
        } else if (orderSmallOrderFee != null) {
            removeMetaAttribute(orderEjb, pOrder.getOrderId(), RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE, userName);
        }

        if (fuelSurcharge != null) {
            if ((orderFuelsurcharge != null && orderFuelsurcharge.subtract(fuelSurcharge).abs().doubleValue() > 0.005) ||
                    orderFuelsurcharge==null) {
                updateMetaAttribute(orderEjb, pOrder.getOrderId(), RefCodeNames.CHARGE_CD.FUEL_SURCHARGE, fuelSurcharge.toString(), userName);
            }
        } else if (orderFuelsurcharge != null) {
              removeMetaAttribute(orderEjb, pOrder.getOrderId(), RefCodeNames.CHARGE_CD.FUEL_SURCHARGE,userName);
        }

        if (discount != null) {
            if ((orderDiscount != null && orderDiscount.subtract(discount).abs().doubleValue() > 0.005) ||
                    orderDiscount == null) {
            	String dVal = discount.toString();
            	if(dVal.indexOf("-")>=0){
            		updateMetaAttribute(orderEjb, pOrder.getOrderId(), RefCodeNames.CHARGE_CD.DISCOUNT, dVal, userName);
            	}else{
            		updateMetaAttribute(orderEjb, pOrder.getOrderId(), RefCodeNames.CHARGE_CD.DISCOUNT, "-"+dVal, userName);
            	}
            }
        } else if (orderDiscount != null) {
              removeMetaAttribute(orderEjb, pOrder.getOrderId(), RefCodeNames.CHARGE_CD.DISCOUNT, userName);
        }
    }

    private void removeMetaAttribute( Order orderEjb,
                                      int pOrderId,
                                      String pMetaDataShortDesc,
                                      String pUser) throws RemoteException {
        orderEjb.removeMetaAttribute(pOrderId,pMetaDataShortDesc,pUser);
    }

    public OrderMetaData getMetaObject(OrderData orderData, OrderMetaDataVector orderMeta, String pName) {
        return Utility.getMetaObject(orderData, orderMeta, pName);
    }

    public void updateMetaAttribute(
            Order orderEjb,
            int pOrderId,
            String pMetaDataShortDesc,
            String pValue,
            String pUser) throws RemoteException {

        orderEjb.setMetaAttribute(pOrderId, pMetaDataShortDesc, pValue, pUser);
    }

}
