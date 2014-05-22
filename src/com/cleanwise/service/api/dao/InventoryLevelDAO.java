package com.cleanwise.service.api.dao;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.Utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Title:        InventoryLevelDAO
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         16.10.2009
 * Time:         16:05:31
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class InventoryLevelDAO {

    private static final Logger log = Logger.getLogger(InventoryLevelDAO.class);

    private static InventoryLevelDetailDataVector getParValues(Connection pCon,
                                                               IdVector pInventoryLevelIds,
                                                               Integer pNumPeriods) throws SQLException {

        InventoryLevelDetailDataVector result = new InventoryLevelDetailDataVector();

        DBCriteria crit = new DBCriteria();

        if (pInventoryLevelIds != null && pInventoryLevelIds.size() > 1) {
            crit.addOneOf(InventoryLevelDetailDataAccess.INVENTORY_LEVEL_ID, pInventoryLevelIds);
        } else if (pInventoryLevelIds != null && pInventoryLevelIds.size() == 1) {
            crit.addEqualTo(InventoryLevelDetailDataAccess.INVENTORY_LEVEL_ID, pInventoryLevelIds.get(0));
        } else if (pInventoryLevelIds != null) {
            return result;
        }

        if (pNumPeriods != null) {
            crit.addGreaterOrEqual(InventoryLevelDetailDataAccess.PERIOD, 1);
            crit.addLessOrEqual(InventoryLevelDetailDataAccess.PERIOD, pNumPeriods);
        }

        result = InventoryLevelDetailDataAccess.select(pCon, crit);

        return result;

    }

    public static InventoryLevelView updateInventoryLevelView(Connection pCon,
                                                              InventoryLevelView pData,
                                                              String pUserName) throws Exception {
        return updateInventoryLevelView(pCon, pData, false, pUserName);
    }

    public static InventoryLevelView updateInventoryLevelView(Connection pCon,
                                                              InventoryLevelView pData,
                                                              boolean pDoNotUpdateParValues,
                                                              String pUserName) throws Exception {

        if (readyForSave(pData)) {

            InventoryLevelData ilData = pData.getInventoryLevelData();

            ilData = updateInvLevelData(pCon, ilData, pUserName);

            if (!pDoNotUpdateParValues) {

                InventoryLevelDetailDataVector newParValues = pData.getParValues();
                InventoryLevelDetailDataVector existParValues = getParValues(pCon, Utility.toIdVector(ilData.getInventoryLevelId()), null);

                newParValues = updateInventoryLevelDetailData(pCon, ilData, newParValues, existParValues, pUserName);

                pData.setParValues(newParValues);
            }

            pData.setInventoryLevelData(ilData);

        } else {
            log.error("updateInventoryLevelView => Can't update data: " + pData);
        }

        return pData;

    }

    private static InventoryLevelData updateInvLevelData(Connection pCon, InventoryLevelData pData, String pUserName) throws SQLException {
        if (pData.getInventoryLevelId() > 0) {
            pData.setModBy(pUserName);
            InventoryLevelDataAccess.update(pCon, pData);
        } else {
            pData.setModBy(pUserName);
            pData.setAddBy(pUserName);
            pData = InventoryLevelDataAccess.insert(pCon, pData);
        }
        return pData;
    }

    private static boolean readyForSave(InventoryLevelView pData) {
        return pData != null && pData.getInventoryLevelData() != null &&
                pData.getInventoryLevelData().getBusEntityId() > 0 &&
                pData.getInventoryLevelData().getItemId() > 0;
    }


    private static InventoryLevelDetailDataVector updateInventoryLevelDetailData(Connection pCon,
                                                                                InventoryLevelData pInventoryLevel,
                                                                                InventoryLevelDetailDataVector pNewDetails,
                                                                                InventoryLevelDetailDataVector pExistsDetails,
                                                                                String pUserName) throws SQLException {
        Iterator existsDetailsIt;
        Iterator newDetailsIt;

        existsDetailsIt = pExistsDetails.iterator();
        while (existsDetailsIt.hasNext()) {
            InventoryLevelDetailData existsDetailData = (InventoryLevelDetailData) existsDetailsIt.next();
            newDetailsIt = pNewDetails.iterator();
            boolean found = false;
            while (newDetailsIt.hasNext()) {
                InventoryLevelDetailData newDetailData = (InventoryLevelDetailData) newDetailsIt.next();
                if (existsDetailData.getPeriod() == newDetailData.getPeriod()) {
                    newDetailData.setInventoryLevelDetailId(existsDetailData.getInventoryLevelDetailId());
                    newDetailData.setAddDate(existsDetailData.getAddDate());
                    newDetailData.setAddBy(existsDetailData.getAddBy());
                    found = true;
                    break;
                }
            }
            if (found) {
                existsDetailsIt.remove();
            }
        }

        existsDetailsIt = pExistsDetails.iterator();
        while (existsDetailsIt.hasNext()) {
            InventoryLevelDetailData existsDetailData = (InventoryLevelDetailData) existsDetailsIt.next();
            InventoryLevelDetailDataAccess.remove(pCon, existsDetailData.getInventoryLevelDetailId());
        }

        newDetailsIt = pNewDetails.iterator();
        while (newDetailsIt.hasNext()) {
            InventoryLevelDetailData newDetailData = (InventoryLevelDetailData) newDetailsIt.next();
            if (newDetailData.isDirty()) {
                newDetailData.setInventoryLevelId(pInventoryLevel.getInventoryLevelId());
                if (newDetailData.getInventoryLevelDetailId() > 0) {
                    newDetailData.setModBy(pUserName);
                    InventoryLevelDetailDataAccess.update(pCon, newDetailData);
                } else {
                    newDetailData.setAddBy(pUserName);
                    newDetailData.setModBy(pUserName);
                    newDetailData = InventoryLevelDetailDataAccess.insert(pCon, newDetailData);
                }
            }
        }

        return pNewDetails;

    }


    public static InventoryLevelViewVector getInvLevelViewCollections(Connection pCon, int pSiteId, IdVector pItems) throws Exception {

        InventoryLevelSearchCriteria criteria = new InventoryLevelSearchCriteria();
        criteria.setSiteIds(Utility.toIdVector(pSiteId));
        criteria.setItems(pItems);

        return getInvLevelViewCollections(pCon, criteria);
    }

    public static InventoryLevelViewVector getInvLevelViewCollections(Connection pCon, InventoryLevelSearchCriteria pSearchCriteria) throws Exception {

        InventoryLevelViewVector result = new InventoryLevelViewVector();

        DBCriteria crit = new DBCriteria();

        if (pSearchCriteria.getSiteIds() != null && pSearchCriteria.getSiteIds().size() > 1) {
            crit.addOneOf(InventoryLevelDataAccess.BUS_ENTITY_ID, pSearchCriteria.getSiteIds());
        } else if (pSearchCriteria.getSiteIds() != null && pSearchCriteria.getSiteIds().size() == 1) {
            crit.addEqualTo(InventoryLevelDataAccess.BUS_ENTITY_ID, pSearchCriteria.getSiteIds().get(0));
        } else if (pSearchCriteria.getSiteIds() != null) {
            return result;
        }

        if (pSearchCriteria.getItems() != null && pSearchCriteria.getItems().size() > 1) {
            crit.addOneOf(InventoryLevelDataAccess.ITEM_ID, pSearchCriteria.getItems());
        } else if (pSearchCriteria.getItems() != null && pSearchCriteria.getItems().size() == 1) {
            crit.addEqualTo(InventoryLevelDataAccess.ITEM_ID, pSearchCriteria.getItems().get(0));
        } else if (pSearchCriteria.getItems() != null) {
            return result;
        }

        InventoryLevelDataVector ildv = InventoryLevelDataAccess.select(pCon, crit);

        if (!ildv.isEmpty()) {

            InventoryLevelDetailDataVector parValues = getParValues(pCon, Utility.toIdVector(ildv), pSearchCriteria.getNumPeriods());

            HashMap<Integer, InventoryLevelDetailDataVector> ildMap = new HashMap<Integer, InventoryLevelDetailDataVector>();
            for (Object oDetail : parValues) {
                InventoryLevelDetailData item = (InventoryLevelDetailData) oDetail;
                InventoryLevelDetailDataVector items = ildMap.get(item.getInventoryLevelId());
                if (items == null) {
                    items = new InventoryLevelDetailDataVector();
                    ildMap.put(item.getInventoryLevelId(), items);
                }
                items.add(item);
            }

            for (Object oInvLevelData : ildv) {
                InventoryLevelData invLevelData = (InventoryLevelData) oInvLevelData;
                InventoryLevelDetailDataVector details = ildMap.get(invLevelData.getInventoryLevelId());
                details = details != null ? details : new InventoryLevelDetailDataVector();
                result.add(new InventoryLevelView(invLevelData, details));
            }
        }

        return result;
    }

}
