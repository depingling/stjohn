package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.APIAccess;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.session.Account;
import java.util.Iterator;

import org.apache.log4j.Logger;


public class InboundJDChinaCustomerPoNums extends InboundFlatFile {
	private List<CustomerPoNumbers> customerPoNumberList = new ArrayList<CustomerPoNumbers>();
	
	public InboundJDChinaCustomerPoNums() {
        super.setSepertorChar('|');
    }
	
	protected void init(){}
	
	protected void parseDetailLine(List pParsedLine) throws Exception{
		if (pParsedLine.size() < 2)
			throw new RuntimeException("Expecting 3 PO Number but found " + pParsedLine.size());
		
		CustomerPoNumbers poNumbers = new CustomerPoNumbers();
		customerPoNumberList.add(poNumbers);
		Iterator it = pParsedLine.iterator();
		poNumbers.jdOrderNum = (String) it.next();
		poNumbers.connextionPONum = (String) it.next();
		if (it.hasNext())
			poNumbers.customerPONum = (String) it.next();
	}


   protected void doPostProcessing() {         
       if (customerPoNumberList.size() == 0)
    	   return;
       Connection conn = null;
       PreparedStatement pstmt = null;
       try {
    	   conn = getConnection(); 	   
    	   
    	   pstmt = conn.prepareStatement("UPDATE CLW_ORDER SET REF_ORDER_NUM = ?, REQUEST_PO_NUM = ? WHERE ORDER_ID = ?");
    	   PreparedStatement pstmt1 = conn.prepareStatement("SELECT O.ORDER_ID FROM CLW_ORDER O, CLW_PURCHASE_ORDER PO WHERE O.ORDER_ID = PO.ORDER_ID AND O.STORE_ID = ? AND ERP_PO_NUM = ?");
    	   PreparedStatement pstmt2 = conn.prepareStatement("SELECT ORDER_ID FROM CLW_ORDER WHERE STORE_ID = ? AND REF_ORDER_NUM = ?");
		   
		   
    	   int storeId = getTranslator().getStoreId();
    	   Iterator i = customerPoNumberList.iterator();
    	   for  (CustomerPoNumbers poNumbers : customerPoNumberList){
    		   int orderId = 0;
    		   String connextionOrderNum = poNumbers.connextionPONum;    		   
    		   if (Utility.isSet(connextionOrderNum)){
    			   pstmt1.setInt(1, storeId);
    			   pstmt1.setString(2, connextionOrderNum);
    			   ResultSet rs = pstmt1.executeQuery();
    			   if (rs.next())
    				   orderId = rs.getInt(1);
    			   
    		   }
    		   if (orderId == 0 && Utility.isSet(poNumbers.jdOrderNum)){
    			   pstmt2.setInt(1, storeId);
    			   pstmt2.setString(2, poNumbers.jdOrderNum);
    			   ResultSet rs = pstmt2.executeQuery();
    			   if (rs.next())
    				   orderId = rs.getInt(1);
    			   
    		   }
    		   if (orderId > 0){
    			   pstmt.setString(1, poNumbers.jdOrderNum);
        		   pstmt.setString(2, poNumbers.customerPONum);
        		   pstmt.setInt(3, orderId);
        		   pstmt.addBatch();
    		   }    		   
    	   }
    	   pstmt.executeBatch();
    	   pstmt.close();
    	   pstmt1.close();
    	   pstmt2.close();    	   
       } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException(e.getMessage());
       } finally {
    	   if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
       }
   }
   
   class CustomerPoNumbers {
	   String jdOrderNum;
	   String connextionPONum;
	   String customerPONum;
   }
}

