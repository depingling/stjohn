package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.process.operations.XlsBuilder;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.session.Site;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;



public class OutboundXpedxProMotionOrders extends InterchangeOutboundSuper {
	protected Logger log = Logger.getLogger(this.getClass());
	short mRowCounter = 0;
    ArrayList poTable;
    SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
    Map siteNameMap = new HashMap();
            
    public void buildInterchangeHeader() throws Exception {		
        poTable = new ArrayList();
        super.buildInterchangeHeader();
	}
	
	public void buildTransactionContent()
    throws Exception {
		log.info("buildTransactionContent. START");
		currOrder = currOutboundReq.getOrderD();
        items = currOutboundReq.getOrderItemDV();
        String siteName = getSiteName(currOrder.getSiteId(), siteNameMap);    	
            
        java.util.Iterator<OrderItemData> it = items.iterator();
        while(it.hasNext()){
        	currItem = it.next();
        	ArrayList row = new ArrayList();
        	row.add(siteName);
        	row.add(currItem.getDistItemSkuNum());
        	row.add(currItem.getDistItemQuantity());
        	row.add(currOrder.getOrderNum());
        	row.add(currItem.getItemShortDesc());
        	row.add(sdf.format(currOrder.getOriginalOrderDate()));        	
        	row.add(currItem.getItemSize());
			row.add(currItem.getItemPack());
        	poTable.add(row);
        	        
        	currItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR);
        	appendIntegrationRequest(currItem);
        }
        currOutboundReq.getPurchaseOrderD().setPurchaseOrderStatusCd(RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR);
        appendIntegrationRequest(currOutboundReq.getPurchaseOrderD());
        
        log.info("buildTransactionContent. END");
	}
		
	
	private static GenericReportColumnViewVector generateHeader()
        throws Exception
{
    try {
        GenericReportColumnViewVector header = new GenericReportColumnViewVector();
        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Site Name",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Distributor SKU",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Quantity Ordered",0,255,"VARCHAR2"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Number",0,255,"VARCHAR2"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Description",0,255,"VARCHAR2"));        
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Order Date",0,255,"VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Size",0,255,"VARCHAR2"));
		header.add(ReportingUtils.createGenericReportColumnView("java.lang.String","Item Pack",0,255,"VARCHAR2"));
        
        return header;
    } catch (Exception e) {
        e.printStackTrace();
        throw new Exception(e.getMessage());
    }
    
}
	
	public void buildInterchangeTrailer() throws Exception {
		// write the excel to the output stream
		GenericReportResultView xlog = GenericReportResultView.createValue();
		xlog.setHeader(generateHeader());
        xlog.setColumnCount(xlog.getHeader().size());
        xlog.setName("Purchase Order");
        xlog.setTable(poTable);
        GenericReportResultViewVector excel = new GenericReportResultViewVector();
        excel.add(xlog);
        XlsBuilder xlsBuilder = new XlsBuilder();
        xlsBuilder.writeExcelReportMulti(excel, getTranslator().getOutputStream());
        super.buildInterchangeTrailer();		
	}
	
	public String getFileExtension() throws Exception {
		return ".xls";
	}
    /**
	 * From the specification:
	 * 
	 * "File Naming Convention:
	 * Our standard would be the word �order� with a time stamp on it.  For example:
	 * xpedx_orders20081013103744.txt"
	 */
	public String getFileName()throws Exception{
		SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String timestamp = timeStampFormat.format(new Date());
		return "xpedx_orders"+timestamp+".xls";
	}
	
	private static String getSiteName(int siteId, Map siteNameMap) throws Exception {
		if (siteNameMap.get(siteId) == null){
			Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();
	    	DBCriteria crit = new DBCriteria();
	        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_ID, siteId);
	        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
	        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,RefCodeNames.BUS_ENTITY_TYPE_CD.SITE);
	        BusEntityDataVector siteBusV = siteEjb.getSiteBusEntityByCriteria(crit);
	        if (siteBusV.size() > 0)
	        	siteNameMap.put(siteId, ((BusEntityData) siteBusV.get(0)).getShortDesc());
	        else
	        	throw new Exception("Site not found for site id=" + siteId);
		}
        return (String) siteNameMap.get(siteId);
    }
}
