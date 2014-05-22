package com.cleanwise.service.api.value;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.pipeline.OrderRouting;
import com.cleanwise.service.api.APIAccess;
import java.math.BigDecimal;

/**
 * <code>AccountOrderPipeline</code> is a value class
 * holding the attributes and methods needed to handle the
 * account level order pipeline processing.
 *
 * @author <a href="mailto:dvieira@DVIEIRA"></a>
 */

public class AccountOrderPipeline {

    private PipelineData pipelineData = null;

    /**
     * Describe <code>setPipelineData</code> method here.
     *
     * @param pipelineData a <code>PipelineData</code> value
     */
    public void setPipelineData(PipelineData pipelineData) {
        this.pipelineData = pipelineData;
    }

    /**
     * Describe <code>getPipelineData</code> method here.
     *
     * @return a <code>PipelineData</code> value
     */
    public PipelineData getPipelineData() {

        if (null == pipelineData) {
            pipelineData = PipelineData.createValue();
            pipelineData.setPipelineStatusCd
		(RefCodeNames.PIPELINE_STATUS_CD.INACTIVE);
            pipelineData.setShortDesc
		(RefCodeNames.PIPELINE_CD.POST_ORDER_CAPTURE);
            pipelineData.setPipelineTypeCd
		(RefCodeNames.PIPELINE_CD.POST_ORDER_CAPTURE);
            pipelineData.setClassname(OrderRouting.class.getName());
        }

        return pipelineData;
    }

    private int accountId = -1;

    /**
     * Creates a new <code>AccountOrderPipeline</code> instance.
     *
     * @param pFactory an <code>APIAccess</code> value
     * @param pAccountId an <code>int</code> value
     */
    public AccountOrderPipeline(APIAccess pFactory, int pAccountId) {
        accountId = pAccountId;
        // Look up the pipeline.
	if ( accountId <= 0 ) {
	    return;
	}

        try {
	    Pipeline pipelineBean = pFactory.getPipelineAPI();
	    PipelineDataVector v = pipelineBean.getPipelinesCollection(pAccountId);
	    for ( int idx = 0; idx < v.size(); idx++ ) {
		pipelineData = (PipelineData)v.get(idx);
		if (pipelineData.getClass().equals(OrderRouting.class.getName())) {
		    break;
		}
	    }
	    // Look up the parameters for the pipeline.
	    PropertyService propBean = pFactory.getPropertyServiceAPI();
	    String maxwv = propBean.checkBusEntityProperty
		(pAccountId, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_ORDER_MAX_WEIGHT);
            String maxcs = propBean.checkBusEntityProperty
                (pAccountId, RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_ORDER_MAX_CUBE_SIZE);
	    if ( null == maxwv ) {
		maxwv = "0";
	    }
            if ( null == maxcs ) {
		maxcs = "0";
	    }
	    maxItemWeight = new BigDecimal(maxwv);
            maxItemCubicSize = new BigDecimal(maxcs);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Describe <code>isPipelineActive</code> method here.
     *
     * @return a <code>boolean</code> value
     */
    /*public boolean isPipelineActive() {

        if (null == pipelineData) {

            return false;
        }

        return pipelineData.getPipelineStatusCd().equals(
							 RefCodeNames.PIPELINE_STATUS_CD.ACTIVE);
    }*/

    /**
     * Describe <code>save</code> method here.
     *
     * @param pFactory an <code>APIAccess</code> value
     * @param pOptionalUserName a <code>String</code> value
     * @exception Exception if an error occurs
     */
    public void save(APIAccess pFactory, String pOptionalUserName) throws Exception {
        if(maxItemWeight == null){
            maxItemWeight = new BigDecimal(0);
        }
        if(maxItemCubicSize == null){
            maxItemCubicSize = new BigDecimal(0);
        }
        PropertyService propBean = pFactory.getPropertyServiceAPI();
        propBean.setBusEntityProperty(accountId, 
				      RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_ORDER_MAX_WEIGHT, maxItemWeight.toString());
        propBean.setBusEntityProperty(accountId, 
				      RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_ORDER_MAX_CUBE_SIZE, maxItemCubicSize.toString());
        Pipeline pipelineBean = pFactory.getPipelineAPI();
        pipelineData.setBusEntityId(accountId);
        pipelineData = pipelineBean.savePipeline(pipelineData, pOptionalUserName);
    }

    /**
     * Describe <code>routeOrderItems</code> method here.
     *
     * @param pFactory an <code>APIAccess</code> value
     * @param pOrderData an <code>OrderData</code> value
     * @param pOrderItems an <code>OrderItemDataVector</code> value
     */
    /*public String routeOrderItems(APIAccess pFactory, 
				  OrderData pOrderData, 
				  OrderItemDataVector pOrderItems) {
	String resp = "routeOrderItems, invoked for pOrderData=" +
	    pOrderData ;

	try {
	    Pipeline orderPipeline = pFactory.getPipelineAPI();
	    resp += orderPipeline.routeOrderItems(pOrderData, pOrderItems, accountId);
	}
	catch ( Exception e ) {
	    resp += " error from pipeline call: " +
		e.getMessage();
	}

	return resp;
	    
    }*/

    private BigDecimal maxItemWeight;

    /**
     * Describe <code>getMaxItemWeight</code> method here.
     *
     * @return an <code>Integer</code> value
     */
    public BigDecimal getMaxItemWeight() {

        return maxItemWeight;
    }

    /**
     * Describe <code>setMaxItemWeight</code> method here.
     *
     * @param v an <code>Integer</code> value
     */
    public void setMaxItemWeight(BigDecimal v) {
        maxItemWeight = v;
    }
    
    
    private BigDecimal maxItemCubicSize;

    /**
     * Describe <code>getMaxItemCubicSize</code> method here.
     *
     * @return an <code>Integer</code> value
     */
    public BigDecimal getMaxItemCubicSize() {

        return maxItemCubicSize;
    }

    /**
     * Describe <code>setMaxItemCubicSize</code> method here.
     *
     * @param v an <code>Integer</code> value
     */
    public void setMaxItemCubicSize(BigDecimal v) {
        maxItemCubicSize = v;
    }
}
