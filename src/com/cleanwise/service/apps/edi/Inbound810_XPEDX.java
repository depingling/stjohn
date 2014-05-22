package com.cleanwise.service.apps.edi;



import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;


import com.americancoders.edi.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityAssocDataAccess;
import com.cleanwise.service.api.dao.PriceRuleDataAccess;
import com.cleanwise.service.api.session.ShoppingServices;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;

import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PriceRuleDataVector;
import com.cleanwise.service.api.value.PriceRuleDescView;
import com.cleanwise.service.api.value.PriceRuleDescViewVector;
import com.cleanwise.service.api.value.PriceRuleDetailData;
import com.cleanwise.service.api.value.PriceRuleDetailDataVector;
import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.apps.dataexchange.InboundTranslate;

/** code to parse 810 transaction
 *<br>class 810 Advanced Ship Notice
 *<br>
 *@author Deping
 */

public class Inbound810_XPEDX extends Inbound810Super
{	
	protected Logger log = Logger.getLogger(this.getClass());
	private static String[] excludedProductionSKUs = {"686017", "686018"};
	private static String[] excludedTestSKUs = {"688101", "688100"};
	//private static String[] freightSKUsL = {"686018","686017","1234","444","8569","7896","132","3584"};	       
	private static String[] freightSKUsL = new String[1000];
	
    public ArrayList getAllServiceFeeCodes() throws Exception{
    	
    	Connection con = this.getConnection();
    	DBCriteria crit = new DBCriteria();
    	ArrayList allServiceFeeCodes = new ArrayList();
    	TradingPartnerData partner = this.getTranslator().getPartner();
    	if(partner == null) {
    		throw new IllegalArgumentException("Trading Partner ID cann't be determined");       
    	}        
    	int tradingPartnerId = partner.getTradingPartnerId(); 
    	
    	Map assocMap = APIAccess.getAPIAccess().getTradingPartnerAPI().getMapTradingPartnerAssocIds(tradingPartnerId); 
    	IdVector storeIds = (IdVector) assocMap.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);   
    	int storeId=0;
    	if(storeIds!=null && storeIds.size()>0){
    		storeId = ((Integer)storeIds.get(0)).intValue();
    	}
    	
    	try{
    		ShoppingServices shopEjb = APIAccess.getAPIAccess().getShoppingServicesAPI();
    		allServiceFeeCodes = shopEjb.getAllServiceFeeCodesForStore(storeId);
    		
    	} catch (Exception ex) {
            throw new Exception(ex);
        } finally {
            closeConnection(con);
        }

    	return allServiceFeeCodes; 	
    }
	
	public Inbound810_XPEDX(){
		distSkuType = "VP";		
	}
	/**
	 * Extract all segments included in ST - SE Segment
	 */
	public void extract()
	throws OBOEException
	{
		
		excludedSKUs = this.getTranslator().getProfile().getTestIndicator().equals("T") ? 
				excludedTestSKUs : excludedProductionSKUs;
		
		try{
			ArrayList allCodes = getAllServiceFeeCodes();
			for(int a=0; a<allCodes.size(); a++){
				freightSKUsL[a] = (String)allCodes.get(a);
			}
			this.freightSKUs = freightSKUsL;
			
		}
		catch(Exception exc){
			exc.printStackTrace();
			//throw new OBOEException();
		}
		super.extract();
	}
}


