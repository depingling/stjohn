package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.apps.loaders.TabFileParser;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;


public class Inbound855_Wegman extends InboundFlatFile {
    private final static String ENCODING = "UTF-16LE";
    private final static int PO_NUMBER = 0;

    private final static int PO_DESCRIPTION = 1;

    private final static int ORDER_DATE = 2;

    //private final static int ORDER_TYPE = 4;

    private final static int SITE_NUMBER = 3;

    //private final static int ORDER_LINE_NUMBER = 7;

    private final static int ORDER_LINE_QUANTITY = 6;

    private final static int PART_NUM = 7;

    private final static int LINE_AMT = 10;

    private final static int DISTRIBUTOR_SKU = 7;

    protected Logger log = Logger.getLogger(Inbound855_Wegman.class);

    private SimpleDateFormat poDateFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat poDateFormat1 = new SimpleDateFormat("MM/dd/yyyy");

    public void translate(InputStream pIn, String pStreamType) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("translate => BEGIN.");
        try {
            //wegmans will send over corrupt UTF16-LE that looks likeUTF8-BOM on occasion and other time UTF16-LE.  Java does not like UTF8-BOM to begin with:
            //see bug id: 4508058
            //http://bugs.sun.com/view_bug.do?bug_id=4508058
            //the following code works around this encoding and forces the UTF16-LE encoding.
            byte bom[] = new byte[3];
            int n, unread;
            n = pIn.read(bom, 0, bom.length);
            String encoding;
            if (  (bom[0] == (byte)0xEF) && (bom[1] == (byte)0xBB)){
            }else{
                 pIn.reset();
            }
            //done encoding work around.

            TabFileParser parser = new TabFileParser();
            parser.parse(pIn,ENCODING);
            parser.cleanUpResult();
            List<List<String>> list = (List<List<String>>) parser
                    .getParsedStrings();
            process(list);
        } catch (Exception e) {
            log.error("translate => FAILED.Process time at : "
                    + (System.currentTimeMillis() - startTime) + " ms", e);
            setFail(true);
            throw e;
        }
        log.info("translate => END.Process time at : "
                + (System.currentTimeMillis() - startTime) + " ms");
    }

    private void process(List<List<String>> parsedData) throws Exception {
        TradingPartnerData partner = translator.getPartner();
        if (partner == null) {
            appendErrorMsgs("Trading Partner ID cannot be determined");
        }
        // bug # 2763 : Commented out not to fail empty Wegmens file while processing. 
        // Un-commented but switched to 0
        if (parsedData.size() == 0){
        	throw new Exception("Input file empty could be wrong format. Need to save in "+ENCODING+" encoding");
        } if (parsedData.size() == 1 && parsedData.get(0).size() != 11){
                log.info(Utility.toCommaSting(parsedData.get(0)));
                throw new Exception("Input file has only one line which is ok, but the line doesn't look like a header line.  Checked it was not 11 fields in length (it was["+parsedData.get(0).size()+"].  Could be wrong format or encoding. Need to save in "+ENCODING+" encoding");
        }
        checkLine(parsedData);
        final Map<String, List<List<String>>> group = new TreeMap<String, List<List<String>>>();
        final Map<String, AcknowledgeRequestData> ackReqDataMap = new TreeMap<String, AcknowledgeRequestData>();

        // New code. YR
        String matchPoNumType = (String)staticValuesMap.get("MATCH_PO_NUM_TYPE");
        if (matchPoNumType != null) {
            if (!RefCodeNames.MATCH_PO_NUM_TYPE_CD.DEFAULT.equals(matchPoNumType) &&
                !RefCodeNames.MATCH_PO_NUM_TYPE_CD.VENDOR_ORDER_NUM.equals(matchPoNumType) &&
                !RefCodeNames.MATCH_PO_NUM_TYPE_CD.STORE_ERP_PO_NUM.equals(matchPoNumType)) {
                throw new Exception("Trading property MATCH_PO_NUM_TYPE has wrong value: " + matchPoNumType);
            }
        } else {
            matchPoNumType = RefCodeNames.MATCH_PO_NUM_TYPE_CD.VENDOR_ORDER_NUM;
        }
        // End New code

        //NOTE we skip the first line as it is a header
        for (int i = 1; parsedData != null && i < parsedData.size(); i++) {
            List<String> line = (List<String>) parsedData.get(i);
            String dateString = line.get(ORDER_DATE);
            Date poDate = null;
            int ix = dateString.indexOf('-');
            if (ix >0){
            	dateString = Utility.replaceString(dateString,"-","");
            	poDate = poDateFormat.parse(dateString);
            }else{
            	poDate = poDateFormat1.parse(dateString);
            }

            dateString = getLastDayOfWeek(poDate);

            String erpPoNum = line.get(PO_NUMBER);
            String key = erpPoNum+"_"+dateString;

            // order group by order number and order date
            List<List<String>> groupLine = group.get(key);
            if (groupLine == null) {
                groupLine = new ArrayList<List<String>>();
                group.put(key, groupLine);
            }
            groupLine.add(line);

            AcknowledgeRequestData ard = ackReqDataMap.get(key);
            if (ard == null) {
                ard = AcknowledgeRequestData.createValue();
                //ard.setMatchPoNumType(RefCodeNames.MATCH_PO_NUM_TYPE_CD.VENDOR_ORDER_NUM); / Old code. YR
                ard.setMatchPoNumType(matchPoNumType); // New code. YR
                ard.setErpPoNum(erpPoNum);

                ard.setVendorOrderNum(erpPoNum);
                ard.setAckDate(poDate);
                ard.setAckItemDV(new AckItemDataVector());
                ard.setSiteKey(line.get(SITE_NUMBER));
                ard.setRequestCreateOrderIfNotExists(true);
                ackReqDataMap.put(key, ard);
            }
            ard.getAckItemDV().add(prepareTransactionData(ard, line));
        }
        checkGroup(group);
        String errorMessage = getFormatedErrorMsgs();
        if (Utility.isSet(errorMessage)) {
            throw new Exception(errorMessage);
        }
        IntegrationServices isEjb = APIAccess.getAPIAccess()
                .getIntegrationServicesAPI();
        IntegrationRequestsVector irv = new IntegrationRequestsVector();
        for (AcknowledgeRequestData ard : ackReqDataMap.values()) {
        	ard.setPriceFromContract(true);
            irv.addAll(setMatchingOrder(ard));
        }
        int tradingPartnerId = partner.getTradingPartnerId();
        isEjb.processIntegrationRequests(irv, "", tradingPartnerId);
    }

    private void checkLine(List<List<String>> data) {
    	//Per Chris Weatherspoon on 4/21/2009 they cannot send this data
        /*for (int i = 1; data != null && i < data.size(); i++) {
            List<String> line = (List<String>) data.get(i);
            String validateHistorical = line.get(ORDER_TYPE);
            if ("HISTORICAL".equalsIgnoreCase(validateHistorical) == false) {
                appendErrorMsgs("ERROR IN LINE # " + i
                        + ": Order Type must be \"HISTORICAL\"!");
            }
        }*/
    }

    private void checkGroup(Map<String, List<List<String>>> group) {
        for (Map.Entry<String, List<List<String>>> entry : group.entrySet()) {
            final String key = entry.getKey();
            final String errorPrefix = "ERROR IN GROUP \"" + key + "\":";
            List<List<String>> val = entry.getValue();
            String poDescription = val.get(0).get(PO_DESCRIPTION);
            String siteNumber = val.get(0).get(SITE_NUMBER);
            BigDecimal orderTotalN = null;
            for (List<String> line : val) {
                if (poDescription.equals(line.get(PO_DESCRIPTION)) == false) {
                    appendErrorMsgs(errorPrefix
                            + "\"PO Description\" must be equal for every item in one group.");
                    break;
                }
            }
            for (List<String> line : val) {
                if (siteNumber.equals(line.get(SITE_NUMBER)) == false) {
                    appendErrorMsgs(errorPrefix
                            + "\"Site Number\" must be equal for every item in one group.");
                    break;
                }
            }
        }
    }

    private AckItemData prepareTransactionData(
    		AcknowledgeRequestData ard, List<String> line)
            throws Exception {
        log.info("prepareTransactionData trans => " + line);

        AckItemData reqAckItem = AckItemData.createValue();
        //reqAckItem.setLineNum(Utility.parseInt(line.get(ORDER_LINE_NUMBER)));
        reqAckItem.setAction("AC");
        reqAckItem.setDistSkuNum(line.get(DISTRIBUTOR_SKU));
        reqAckItem.setQuantity((int) Double.parseDouble(line
                .get(ORDER_LINE_QUANTITY)));
        String amountStr = Utility.replaceString(line.get(LINE_AMT),",","");
        amountStr = Utility.replaceString(amountStr,"\"","");
        BigDecimal lineAmt = new BigDecimal(amountStr);
        BigDecimal qty = new BigDecimal(line.get(ORDER_LINE_QUANTITY));
        BigDecimal price = lineAmt.divide(qty, 2, BigDecimal.ROUND_HALF_UP);
        reqAckItem.setPrice(price);
        return reqAckItem;
    }

    private String getLastDayOfWeek(Date date) throws ParseException
    {

    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // to get the int value of the day of the week (1-7)
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        int dayDiff = Calendar.SATURDAY - weekDay;
        if (dayDiff > 0){
        	calendar.add(Calendar.DAY_OF_WEEK, dayDiff);
        }

        return poDateFormat.format(calendar.getTime());
    }

    private String getDayOfWeek(int day, Date date) throws ParseException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // to get the int value of the day of the week (1-7)
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        int dayDiff = day - weekDay;
        if (dayDiff != 0){
        	calendar.add(Calendar.DAY_OF_WEEK, dayDiff);
        }
        return poDateFormat.format(calendar.getTime());
    }

    /**
     * Find existing order that matching on request po number and period. If multiple orders match found, match item to
     * each existing web order based on vender sku. For no matching items, they will be inserted to a existing
     * matching EDI order or create a new EDI order if no matching found.
     * E.g. 3 items in AcknowledgeRequestData object. Item1 match web order1, item2 match web order2 and item3
     * found no web order that match. If a EDI order, will assign item 3 to it. So one AcknowledgeRequestData
     * object of 3 item will become 3 AcknowledgeRequestData for processing acknowledgement
     * @param ard
     * @return
     * @throws Exception
     */
    private AcknowledgeRequestDataVector setMatchingOrder(AcknowledgeRequestData ard) throws Exception {
		int orderId = 0;
		int tradingPartnerId = getTranslator().getPartner().getTradingPartnerId();
		String firstDayOfWeek = getDayOfWeek(Calendar.SUNDAY, ard.getAckDate());
		String lastDayOfWeek = getDayOfWeek(Calendar.SATURDAY, ard.getAckDate());
		AcknowledgeRequestDataVector ackList = new AcknowledgeRequestDataVector();

		if (tradingPartnerId > 0) {
			Map assocMap = APIAccess.getAPIAccess().getTradingPartnerAPI().getMapTradingPartnerAssocIds(tradingPartnerId);
			IdVector storeIds = (IdVector) assocMap.get(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE);
			if(storeIds == null || storeIds.size() < 1) {
				throw new IllegalArgumentException("No stores present for current trading partner id = " +
						tradingPartnerId);
			} else if(storeIds.size() > 1) {
				throw new Exception("Multiple stores present for current trading partner id = " +
						tradingPartnerId);
			}
			int storeId = ((Integer)storeIds.get(0)).intValue();
			ard.setStoreId(storeId);
			Connection conn = null;
			try {
				conn = getConnection();
				String sql = "select o.order_id, order_source_cd from clw_property p, clw_bus_entity_assoc a, clw_order o " +
						"where p.bus_entity_id =  a.bus_entity1_id " +
						"and p.short_desc = 'SITE_REFERENCE_NUMBER' " +
						"and p.clw_value = ? " +
						"and p.bus_entity_id = o.site_id " +
						"and o.request_po_num = ? " +
						"and o.original_order_date between TO_DATE('" + firstDayOfWeek + "','" + poDateFormat.toPattern() + "') and TO_DATE('" + lastDayOfWeek + "','" + poDateFormat.toPattern() + "') " +
						"and bus_entity2_id in (" +
						"	select bus_entity1_id from clw_bus_entity_assoc " +
						"	where bus_entity2_id = ? " +
						"	and bus_entity_assoc_cd = 'ACCOUNT OF STORE'" +
						") " +
						"and bus_entity_assoc_cd = 'SITE OF ACCOUNT' " +
						"and o.order_status_cd != 'Cancelled' " +
						"order by order_source_cd desc, order_id";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, ard.getSiteKey());
				pstmt.setString(2, ard.getErpPoNum());
				pstmt.setInt(3, storeId);
				ResultSet rs = pstmt.executeQuery();
				List<OrderIdAndSourceCd> orderIdAndSourceCdList = new ArrayList<OrderIdAndSourceCd>();
				while (rs.next()){
					orderIdAndSourceCdList.add(new OrderIdAndSourceCd(rs.getInt(1), rs.getString(2)));
				}

				if (orderIdAndSourceCdList.size() == 0){
					ard.setOrderId(-1); // set to -1 so that IntegrationService bean will not try to find match PO.
					ackList.add(ard);
				}else if (orderIdAndSourceCdList.size() == 1 &&
					!orderIdAndSourceCdList.get(0).isWebOrder){
					ard.setOrderId(orderIdAndSourceCdList.get(0).orderId);
					ackList.add(ard);
				}else{
					Map orderIdAndAckItemsMap = new HashMap();
					for (int i = 0; i < ard.getAckItemDV().size(); i++){
						AckItemData ackItem = (AckItemData)ard.getAckItemDV().get(i);
						String distSku = ackItem.getDistSkuNum();
						boolean matchFound = false;
						for (int j = 0; j < orderIdAndSourceCdList.size() && !matchFound; j++){
							OrderIdAndSourceCd orderIdAndSourceCd = orderIdAndSourceCdList.get(j);
							if (orderIdAndSourceCd.isWebOrder){
								if (orderMatchByDistSku(conn, distSku, orderIdAndSourceCd.orderId)){
									addAckItemDataToMap(orderIdAndSourceCd.orderId+"", ackItem, orderIdAndAckItemsMap);
									matchFound = true;
								}
							}else{// add to EDI order
								addAckItemDataToMap(orderIdAndSourceCd.orderId+"", ackItem, orderIdAndAckItemsMap);
								matchFound = true;
							}
						}
						if (!matchFound){
							addAckItemDataToMap("-1", ackItem, orderIdAndAckItemsMap);
						}
					}

					Iterator iter = orderIdAndAckItemsMap.keySet().iterator();
					while (iter.hasNext()){
						String orderIdStr = (String) iter.next();
						AckItemDataVector ackItems = (AckItemDataVector) orderIdAndAckItemsMap.get(orderIdStr);
						AcknowledgeRequestData clone = (AcknowledgeRequestData) ard.clone();
						clone.setAckItemDV(ackItems);
						clone.setOrderId(new Integer(orderIdStr).intValue());
						ackList.add(clone);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}finally{
				if (conn != null)
					conn.close();
			}
		} else {
			(new Exception("No store ids were specified")).printStackTrace();
		}
		return ackList;
	}

    private boolean orderMatchByDistSku(Connection conn, String distSku, int orderId) throws SQLException {
    	String sql = "select count(*) from clw_order_item " +
    			"where order_id = ? " +
    			"and dist_item_sku_num = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, orderId);
		pstmt.setString(2, distSku);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		pstmt.close();
		return count > 0;
	}

    public void addAckItemDataToMap(String orderId, AckItemData ackItem, Map orderIdAndAckItemsMap) {
    	AckItemDataVector ackItems = (AckItemDataVector) orderIdAndAckItemsMap.get(orderId);

    	if (ackItems == null){
    		ackItems = new AckItemDataVector();
    		orderIdAndAckItemsMap.put(orderId, ackItems);
    	}

		ackItems.add(ackItem);
	}

	class OrderIdAndSourceCd {
    	int orderId;
    	boolean isWebOrder;
    	OrderIdAndSourceCd(int orderId, String orderSourceCd){
    		this.orderId = orderId;
    		isWebOrder = !orderSourceCd.equals("EDI");
    	}

    }
}
