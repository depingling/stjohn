package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class Inbound855_StopShop extends InboundFlatFile { 
    protected Logger log = Logger.getLogger(Inbound855_StopShop.class);
    private static final int FILE_TYPE_1 = 1;
    private static final int FILE_TYPE_2 = 2;
    private static final int FILE_TYPE_3 = 3;
    private int fileType = 0;
    private Map<String, AcknowledgeRequestData> ackPoMap = new HashMap<String, AcknowledgeRequestData>();
    private static final String[] PO_PERIOD_DATES_2009 = {"", "20090124","20090221","20090321","20090418","20090516","20090613",
    												"20090711","20090808","20090905","20091003","20091031","20091128", "20091226"} ;
    private List<String> datesYMDByPeriod = new ArrayList<String>();
    private static final SimpleDateFormat poDateFormatYMD = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat dateFormatYMD = new SimpleDateFormat("MM/dd/yy");
    
    
    private static Map<String, Date> dateMap = new HashMap<String, Date>();    
    
    /**
	 *passed in the parsed line will preform the necessary logic of populating the object
	 */
	public void parseLine(List pParsedLine) throws Exception{
		if(isEmpty(pParsedLine)){
			log.info("empty line");
			return;
		}
		if (pParsedLine.size() == 1)
			return;
		
		if(currentLineNumber == 0){
			parseHeaderLine(pParsedLine);
		}else{
			parseDetailLine(pParsedLine);
		}
		currentLineNumber++;
	}
	
	protected void parseHeaderLine(List pParsedLine) throws Exception{		
		Integer lastColumnIndex = new Integer(pParsedLine.size()-1);
		if (pParsedLine.size() == 5){
			fileType = FILE_TYPE_3;
		}else {
			String columnName = pParsedLine.get(lastColumnIndex).toString();
			if (columnName.startsWith("Order Date"))
				fileType = FILE_TYPE_2;
			else if (columnName.startsWith("Period")){
				fileType = FILE_TYPE_1;
				for (int i = 14; i < pParsedLine.size(); i++){
					String temp = pParsedLine.get(new Integer(i)).toString();
					temp = temp.substring("Period".length(), temp.indexOf(',')).trim();
					datesYMDByPeriod.add(PO_PERIOD_DATES_2009[new Integer(temp).intValue()]);
				}
			}else {
				fileType = FILE_TYPE_1;
				for (int i = 14; i < pParsedLine.size(); i++){
					String temp = pParsedLine.get(new Integer(i)).toString();
					temp = temp.substring(temp.indexOf('-')+1, temp.indexOf("Quantity")).trim();
					Date date = dateFormatYMD.parse(temp);
					temp = poDateFormatYMD.format(date);
					datesYMDByPeriod.add(temp);
				}
			}
			
		}
	}

	protected void parseDetailLine(List pParsedLine) throws ParseException{
		String siteRefNum = null;
		String productSKU = null;
		String uom = null;
		int quantity = 0;
		AckItemData reqAckItem = AckItemData.createValue();
		reqAckItem.setAction("AC");        
		
		if (fileType == FILE_TYPE_3){
			siteRefNum = getString(pParsedLine.get(0));
			Date poDate = getDate(pParsedLine.get(1));
			String orderDateStr = poDateFormatYMD.format(poDate);// yyyyMMdd
			productSKU = getString(pParsedLine.get(2));
			quantity = getInt(pParsedLine.get(4));
			reqAckItem.setDistSkuNum(productSKU);
			reqAckItem.setQuantity(quantity);
			addAckItemToOrder(reqAckItem, siteRefNum, orderDateStr);
		}else{
			siteRefNum = getString(pParsedLine.get(9));
			productSKU = getString(pParsedLine.get(5));
			uom = getString(pParsedLine.get(8));
			reqAckItem.setDistSkuNum(productSKU);
			reqAckItem.setUom(uom);
			if (fileType == FILE_TYPE_1){
				for (int i = 0; i < datesYMDByPeriod.size(); i++){
					quantity = getInt(pParsedLine.get(14+i));
					if (quantity > 0){
						addAckItemToOrder(cloneAckItemData(reqAckItem, quantity), siteRefNum, datesYMDByPeriod.get(i));
					}
				}
			} else if (fileType == FILE_TYPE_2){
				Date poDate = getDate(pParsedLine.get(1));
				String orderDateStr = poDateFormatYMD.format(poDate);// yyyyMMdd
				quantity = getInt(pParsedLine.get(15));
				reqAckItem.setQuantity(quantity);
				addAckItemToOrder(reqAckItem, siteRefNum, orderDateStr);
			}	
		}		
	}

	private AckItemData cloneAckItemData(AckItemData ackItem, int quantity){
		AckItemData reqAckItem = AckItemData.createValue();
		reqAckItem.setAction(ackItem.getAction());
		reqAckItem.setDistSkuNum(ackItem.getDistSkuNum());
		reqAckItem.setUom(ackItem.getUom());
		reqAckItem.setQuantity(quantity);
		return reqAckItem;
	}
	
	private void addAckItemToOrder(AckItemData ackItem, String siteRefNum, String orderDate) 
	throws ParseException{
		AcknowledgeRequestData ard = getAckRequestData(siteRefNum, orderDate);
		ard.setPriceFromContract(true);
		ard.getAckItemDV().add(ackItem);
	}
	private AcknowledgeRequestData getAckRequestData(String siteRefNum,
			String orderDate) throws ParseException {
		String erpPoNum = siteRefNum + "_" + orderDate + "_"+ fileType;
		
		AcknowledgeRequestData ard = ackPoMap.get(erpPoNum);
		if (ard == null) {
            ard = AcknowledgeRequestData.createValue();
            ard.setMatchPoNumType(RefCodeNames.MATCH_PO_NUM_TYPE_CD.VENDOR_ORDER_NUM);
            ackPoMap.put(erpPoNum, ard);
            ard.setErpPoNum(erpPoNum);
            ard.setVendorOrderNum(erpPoNum);
            ard.setAckDate(getDate(orderDate));
            ard.setAckItemDV(new AckItemDataVector());
            ard.setSiteKey(siteRefNum);
            ard.setRequestCreateOrderIfNotExists(true);
            appendIntegrationRequest(ard);
        }
		return ard;
	}
	
	private static Date getDate(String dateStr) throws ParseException{
    	Date date = dateMap.get(dateStr);
    	if (date == null){
    		date = poDateFormatYMD.parse(dateStr);
    		dateMap.put(dateStr, date);
    	}
    	return date;
    }
	
	private static String getString(Object o){
		if (o != null)
			return o.toString().trim();
		return null;
	}
	
	private static int getInt(Object o){
		if (o != null){
			String temp = o.toString().trim();
			int ix = temp.indexOf('.');
			if (ix > 0){
				temp = temp.substring(0, ix);
				return Integer.parseInt(temp);
			}
			return Integer.parseInt(temp);
		}
		return 0;		
	}	
	
	private static Date getDate(Object o){
		if (o != null){
			if(o instanceof Double){
				return HSSFDateUtil.getJavaDate(((Double)o).doubleValue());
			}else if(o instanceof Integer){
				return HSSFDateUtil.getJavaDate(((Integer)o).doubleValue());
			}
		}
		return null;
	}
	
}
