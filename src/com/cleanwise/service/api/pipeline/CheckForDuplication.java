package com.cleanwise.service.api.pipeline;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.PreOrderDataAccess;
import com.cleanwise.service.api.dao.PreOrderItemDataAccess;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.PipelineException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.PreOrderItemData;
import com.cleanwise.service.api.value.PreOrderItemDataVector;


/**
 * Pipeline class that checks duplicate orders.
 */
public class CheckForDuplication implements OrderPipeline {
    private final static int TIME_PERIOD_IN_MINUTES = 60;

    private final static SimpleDateFormat DATE_FORMAT_4_SQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public OrderPipelineBaton process(OrderPipelineBaton pBaton,
            OrderPipelineActor pActor, Connection pCon, APIAccess pFactory)
            throws PipelineException {
        try {
            pBaton.setWhatNext(OrderPipelineBaton.GO_NEXT);
            Map<Integer, Set<String>> existItems = getExistPreOrderItems(pCon, pBaton, -TIME_PERIOD_IN_MINUTES);
            Set<String> newItems = getNewPreOrderItems(pBaton);
            Integer existPreOrderId = isExistPreOrder(existItems, newItems);
            if (existPreOrderId != null) {
                DBCriteria  cr= new DBCriteria();
                cr.addEqualTo(OrderDataAccess.PRE_ORDER_ID, existPreOrderId);
                OrderDataVector orderDataV = OrderDataAccess.select(pCon, cr);
                String orderNum = ((OrderData) orderDataV.get(0)).getOrderNum();
                pBaton.addError(pCon, OrderPipelineBaton.DUPLICATED_ORDER, null, RefCodeNames.ORDER_STATUS_CD.PENDING_REVIEW, 0, 0, "error.simpleGenericError", "Duplicate for order # "
                        + orderNum, "java.lang.String");
            }
            return pBaton;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PipelineException(e.getMessage());
        }
    }

    private static Set<String> getNewPreOrderItems(OrderPipelineBaton pBaton)
            throws Exception {
        Set<String> result = new TreeSet<String>();
        PreOrderItemDataVector pReqItems = pBaton.getPreOrderItemDataVector();
        if (pReqItems != null && pReqItems.size() > 0) {
            for (int i = 0; i < pReqItems.size(); i++) {
                PreOrderItemData reqItem = (PreOrderItemData) pReqItems.get(i);
                result.add(reqItem.getItemId() + "_" + reqItem.getQuantity());
            }
        }
        return result;
    }

    private static Map<Integer, Set<String>> getExistPreOrderItems(
            Connection pCon, OrderPipelineBaton pBaton, int pDiffMinutes)
            throws Exception {
        final Map<Integer, Set<String>> result = new TreeMap<Integer, Set<String>>();
        DBCriteria crItems = generateDbItemsCriteria(pBaton);
        if (crItems == null) {
            return result;
        }
        DBCriteria cr = new DBCriteria();
        int siteId = pBaton.getPreOrderData().getSiteId();
        int curPreOrderId = pBaton.getPreOrderData().getPreOrderId();
        Date curPreOrderAddDate = pBaton.getPreOrderData().getAddDate();
        String addBy = pBaton.getPreOrderData().getAddBy();
        cr.addJoinTable(PreOrderDataAccess.CLW_PRE_ORDER);
        cr.addJoinCondition(PreOrderItemDataAccess.CLW_PRE_ORDER_ITEM, PreOrderItemDataAccess.PRE_ORDER_ID, PreOrderDataAccess.CLW_PRE_ORDER, PreOrderDataAccess.PRE_ORDER_ID);
        cr.addJoinTableEqualTo(PreOrderDataAccess.CLW_PRE_ORDER, PreOrderDataAccess.SITE_ID, siteId);
        if(addBy!=null){
        	cr.addJoinTableEqualTo(PreOrderDataAccess.CLW_PRE_ORDER, PreOrderDataAccess.ADD_BY, addBy);
        }else{
        	cr.addJoinTableIsNull(PreOrderDataAccess.CLW_PRE_ORDER, PreOrderDataAccess.ADD_BY);
        }
        cr.addJoinTableNotEqualTo(PreOrderDataAccess.CLW_PRE_ORDER, PreOrderDataAccess.PRE_ORDER_ID, curPreOrderId);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, pDiffMinutes);
        Date addDate = calendar.getTime();
        String sql = "SELECT CLW_PRE_ORDER_ITEM.PRE_ORDER_ID,CLW_PRE_ORDER_ITEM.ITEM_ID,CLW_PRE_ORDER_ITEM.QUANTITY FROM CLW_PRE_ORDER_ITEM,CLW_PRE_ORDER";
        if (crItems != null) {
            //cr.addIsolatedCriterita(crItems);
        }
        String whereDate = PreOrderDataAccess.CLW_PRE_ORDER + "."
                + PreOrderDataAccess.ADD_DATE + " >= TO_DATE('"
                + DATE_FORMAT_4_SQL.format(addDate)
                + "', 'YYYY-MM-DD HH24:MI:SS') AND " +
                PreOrderDataAccess.ADD_DATE + " < TO_DATE('"
                + DATE_FORMAT_4_SQL.format(curPreOrderAddDate)
                + "', 'YYYY-MM-DD HH24:MI:SS') ";
        String where = whereDate + " AND " + cr.getSqlClause();
        sql += " WHERE " + where;
        Statement st = null;
        ResultSet rs = null;
        try {
            st = pCon.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                int preOrderId = rs.getInt(1);
                int itemId = rs.getInt(2);
                int qty = rs.getInt(3);
                Set<String> val = result.get(preOrderId);
                if (val == null) {
                    val = new TreeSet<String>();
                    result.put(preOrderId, val);
                }
                val.add(itemId + "_" + qty);
            }
            return result;
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
        }
    }

    private static DBCriteria generateDbItemsCriteria(OrderPipelineBaton pBaton) {
        DBCriteria crResult = null;
        PreOrderItemDataVector pReqItems = pBaton.getPreOrderItemDataVector();
        if (pReqItems != null && pReqItems.size() > 0) {
            crResult = new DBCriteria();
            for (int i = 0; i < pReqItems.size(); i++) {
                PreOrderItemData reqItem = (PreOrderItemData) pReqItems.get(i);
                DBCriteria crItem = new DBCriteria();
                crItem.addEqualTo(PreOrderItemDataAccess.QUANTITY, reqItem.getQuantity());
                crItem.addEqualTo(PreOrderItemDataAccess.ITEM_ID, reqItem.getItemId());
                if (i == 0) {
                    crResult.addIsolatedCriterita(crItem);
                } else {
                    crResult.addOrCriteria(crItem);
                }
            }
        }
        return crResult;
    }

    private Integer isExistPreOrder(Map<Integer, Set<String>> pExistItems,
            Set<String> pNewItems) {
        for (Map.Entry<Integer, Set<String>> entry : pExistItems.entrySet()) {
            Integer preOrderId = entry.getKey();
            Set<String> existItems = entry.getValue();
            int newInExistCount = 0;
            int existInNewCount = 0;
            for (String newItem : pNewItems) {
                if (existItems.contains(newItem)) {
                    newInExistCount++;
                }
            }
            for (String existItem : existItems) {
                if (pNewItems.contains(existItem) == true) {
                    existInNewCount++;
                }
            }
            if (newInExistCount != 0 && newInExistCount == existInNewCount &&
                newInExistCount == pNewItems.size()  &&
                pNewItems.size() == existItems.size()) {
                return preOrderId;
            }
        }
        return null;
    }
}
