/**
 *
 */
package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.value.*;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.ConnectionContainer;
import com.cleanwise.service.api.session.IntegrationServicesBean;

import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.sql.*;

import org.apache.log4j.Logger;
/**
 * @author Manoj
 *03/02/2009
 */
public class SalesItemAccountDist implements GenericReportMulti {
	
	private static final Logger log = Logger.getLogger(IntegrationServicesBean.class);

	public GenericReportResultViewVector process(ConnectionContainer pCons,
			GenericReportData pReportData,
			Map pParams) throws Exception {

		Connection con = pCons.getDefaultConnection();
		GenericReportResultViewVector resultV = new GenericReportResultViewVector();
//		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    	String startDateS = (String) ReportingUtils.getParam(pParams, "BEG_DATE");
		String endDateS = (String) ReportingUtils.getParam(pParams, "END_DATE");
//	    df = new SimpleDateFormat("dd-MMM,yyyy");
        log.info("pParams.get(BEG_DATE)::" + ReportingUtils.getParam(pParams,"BEG_DATE"));
        log.info("pParams.get(END_DATE)::"+ ReportingUtils.getParam(pParams,"END_DATE"));
        log.info("Date format Start :" + startDateS);
        log.info("Date format End :" + endDateS);
	    String storeIdS = (String)ReportingUtils.getParam(pParams,"STORE"); 
		String accountIdS = (String)ReportingUtils.getParam(pParams,"ACCOUNT_OPT");
	    String itemIdS = (String)ReportingUtils.getParam(pParams,"ITEM_OPT");
	    String distributorIdS = (String)ReportingUtils.getParam(pParams,"DISTRIBUTOR_OPT");
        
	     Statement stmt = null;
	     ResultSet rs = null;
		try {

			
			String sql = "SELECT o.account_erp_num, acct.short_desc as account, i.dist_erp_num,dist.short_desc as distributor, " +
					"i.item_sku_num,TO_CHAR(TRUNC(o.original_order_date,'MM'),'Month YYYY') AS month, " +
					" MAX(i.item_short_desc) AS short_desc,SUM(total_quantity_ordered) AS Total_Qty, count(*) as Number_of_Orders" +
							" FROM CLW_ORDER_ITEM i, CLW_ORDER o, CLW_BUS_ENTITY dist, clw_bus_entity acct" +
							" WHERE i.order_id = o.order_id AND (o.original_order_date BETWEEN TO_DATE('"+startDateS+"','mm/dd/yyyy') " +
							" AND TO_DATE('"+endDateS+"','mm/dd/yyyy')) AND o.store_id = "+storeIdS+"" +
							" AND i.order_Item_status_cd != 'CANCELLED' " +
							" AND o.order_status_cd != 'CANCELLED' AND o.order_status_cd != 'Cancelled' " +
							" AND o.order_status_cd != 'Duplicated' and o.account_Id = acct.bus_entity_id " +
							" AND dist.erp_num = i.dist_erp_num AND dist.bus_entity_type_cd = 'DISTRIBUTOR' ";
			
	//		if(storeIdS.trim().length() >0){
	//		sql += " AND o.store_id in("+storeIdS+") ";
	//		}
	//		else {
	//			sql += " AND o.store_id in(1) ";
	//		}
			
			if(distributorIdS.trim().length() >0 )
				{
				sql += " AND dist.bus_entity_id in("+distributorIdS+") ";
				}
			if(accountIdS.trim().length() >0){
				sql += " AND o.account_id in("+accountIdS+") ";
			}
			if(itemIdS.trim().length() >0){
				sql += " AND i.item_id in("+itemIdS+") ";
			}	
			
			sql += " GROUP BY o.account_erp_num,acct.short_desc, i.dist_erp_num,dist.short_desc, i.item_sku_num," +
					" TRUNC(o.original_order_date,'MM')";

									
			stmt = con.createStatement();
	        rs = stmt.executeQuery(sql);
	        ArrayList backorders = new ArrayList();
	        while(rs.next()) {
	        	SalesItemAccountDistDetail bod = new SalesItemAccountDistDetail();
                bod.accountErpNum = rs.getString("account_erp_num");
                bod.account = rs.getString("account");
                bod.distErpNum = rs.getString("dist_erp_num");
                bod.dist = rs.getString("distributor");
                bod.itemSku = rs.getString("item_sku_num");
                bod.month = rs.getString("month");
                bod.itemDesc = rs.getString("short_desc");
                bod.totalQty = rs.getInt("Total_Qty");
                bod.orderQty = rs.getInt("Number_of_Orders");
                              
                backorders.add(bod);
            }
            rs.close();
            stmt.close();

            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            for (Iterator iter1=backorders.iterator(); iter1.hasNext();) {
            	SalesItemAccountDistDetail det = (SalesItemAccountDistDetail) iter1.next();
                result.getTable().add(det.toList());
            }

            GenericReportColumnViewVector boHeader = getSalesItemAccountDistHeader();
            result.setColumnCount(boHeader.size());
            result.setHeader(boHeader);
            result.setName("Item Sales Report");
            result.setPaperSize(RefCodeNames.REPORT_PAPER_SIZE.LETTER);
            result.setPaperOrientation(RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE);
            resultV.add(result);

			return resultV;
		} catch (NumberFormatException e) {
			throw new Exception(e.toString());
			
		}
		finally{
			  if(rs != null){
			      rs.close();
			  }
			  if( stmt != null){
			      stmt.close();
			  }
			}
	}

	private GenericReportColumnViewVector getSalesItemAccountDistHeader() {
		GenericReportColumnViewVector header = new GenericReportColumnViewVector();

		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "accountErpNum", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "account", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "distErpNum", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "dist", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "itemSku", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "month", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "itemDesc", 0, 255, "VARCHAR2", "30", false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "totalQty", 0,38, "NUMBER","10",false));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "orderQty", 0,38, "NUMBER","10",false));
		
        return header;
	}

	private class SalesItemAccountDistDetail {

		String accountErpNum;
        String account;
        String distErpNum;
        String dist;
        String itemSku;
        String month;
        String itemDesc; 
        int totalQty;
        int orderQty;
		
		private ArrayList toList() {

    		ArrayList list = new ArrayList();
    		list.add(accountErpNum);
    		list.add(account);
            list.add(distErpNum);
            list.add(dist);
            list.add(itemSku);
            list.add(month);
            list.add(itemDesc);
            list.add(new Integer(totalQty));
            list.add(new Integer(orderQty));
            return list;
    	}

	}
}
