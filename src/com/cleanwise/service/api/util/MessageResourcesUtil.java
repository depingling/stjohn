/**
 * MessageResourcesUtil.java
 */
package com.cleanwise.service.api.util;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.value.MessageResourceData;
import com.cleanwise.service.api.value.MessageResourceDataVector;
import com.cleanwise.service.api.dao.MessageResourceDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.UserData;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.lang.Exception;



/**
 * @author Ssharma
 *
 */
public class MessageResourcesUtil {

	/*
	 * Get store email message based on user locale
	 */
	public String getStoreEmailMessage(Connection pCon, int pStoreId, OrderData pOrder, UserData pUser )
	throws SQLException{

		String emailMessage = null;
		MessageResourceDataVector mrdv = new MessageResourceDataVector();

		try{
			/* store id eg. 1
			 * user locale eg. ja_JP
			 */
			String uLocaleCd = pUser.getPrefLocaleCd();
			StringTokenizer st = new StringTokenizer(uLocaleCd, "_");
	        String lang = "", country = "";
	        for (int i = 0; st.hasMoreTokens() && i < 2; i++ ) {
	        	if ( i == 0 ) lang = st.nextToken();
	        	if ( i == 1 ) country = st.nextToken();
	        }
	        
	        /*
	         * check with 1_ja_JP
	         */
			DBCriteria pCrit = new DBCriteria();	
			pCrit.addEqualTo(MessageResourceDataAccess.BUS_ENTITY_ID, pStoreId);
			pCrit.addEqualTo(MessageResourceDataAccess.LOCALE, uLocaleCd);	
			pCrit.addEqualToIgnoreCase(MessageResourceDataAccess.NAME, "store.email");

			mrdv = MessageResourceDataAccess.select(pCon, pCrit);

			if(mrdv.size()>0){
				for(int it=0; it<mrdv.size(); it++){
					MessageResourceData mrd = (MessageResourceData)mrdv.get(it);
					emailMessage = mrd.getValue();
				}
				return emailMessage;
			}else{
				/*
				 * check with 1_ja
				 */
				pCrit = new DBCriteria();
				pCrit.addEqualTo(MessageResourceDataAccess.BUS_ENTITY_ID, pStoreId);
				pCrit.addEqualTo(MessageResourceDataAccess.LOCALE, lang);
				pCrit.addEqualToIgnoreCase(MessageResourceDataAccess.NAME, "store.email");
				
				mrdv = MessageResourceDataAccess.select(pCon, pCrit);
			}
			
			if(mrdv.size()>0){
				for(int it=0; it<mrdv.size(); it++){
					MessageResourceData mrd = (MessageResourceData)mrdv.get(it);
					emailMessage = mrd.getValue();
				}
				return emailMessage;
			}else{
				/*
				 * check with ja_JP
				 */
				pCrit = new DBCriteria();
				pCrit.addEqualTo(MessageResourceDataAccess.LOCALE, uLocaleCd);	
				pCrit.addEqualToIgnoreCase(MessageResourceDataAccess.NAME, "store.email");
				
				mrdv = MessageResourceDataAccess.select(pCon, pCrit);
			}
			
			if(mrdv.size()>0){
				for(int it=0; it<mrdv.size(); it++){
					MessageResourceData mrd = (MessageResourceData)mrdv.get(it);
					emailMessage = mrd.getValue();
				}
				return emailMessage;
			}else{
				/*
				 * check with ja
				 */
				pCrit = new DBCriteria();
				pCrit.addEqualTo(MessageResourceDataAccess.LOCALE, lang);	
				pCrit.addEqualToIgnoreCase(MessageResourceDataAccess.NAME, "shop.auto.order.email");
				
				mrdv = MessageResourceDataAccess.select(pCon, pCrit);
			}
			
			if(mrdv.size()>0){
				for(int it=0; it<mrdv.size(); it++){
					MessageResourceData mrd = (MessageResourceData)mrdv.get(it);
					emailMessage = mrd.getValue();
				}
				return emailMessage;
			}else{
				/*
				 * check with default (en_US)
				 */
				pCrit = new DBCriteria();
				pCrit.addEqualTo(MessageResourceDataAccess.LOCALE, "en_US");	
				pCrit.addEqualToIgnoreCase(MessageResourceDataAccess.NAME, "shop.auto.order.email");
				
				mrdv = MessageResourceDataAccess.select(pCon, pCrit);
			}
			
			for(int it=0; it<mrdv.size(); it++){
				MessageResourceData mrd = (MessageResourceData)mrdv.get(it);
				emailMessage = mrd.getValue();
			}
			
			
		} catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("MessageResourcesUtil: error: " + e);
        }
		return emailMessage;

	}
		
}
