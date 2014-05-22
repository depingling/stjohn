/*
 *
 */

package com.cleanwise.service.api.framework;

import java.sql.*;
import java.util.Iterator;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;

public abstract class BusEntityServicesAPI extends ApplicationServicesAPI
{
    protected BusEntityData getSingleRelatedBusEntity
        (Connection pCon,String pRelationShipType, int pBusEntityId)
        throws SQLException, DataNotFoundException
    {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                       pRelationShipType);
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,
                       pBusEntityId);
        dbc.addOrderBy(BusEntityAssocDataAccess.BUS_ENTITY1_ID);
        IdVector relatedIds = BusEntityAssocDataAccess.selectIdOnly
        (pCon, BusEntityAssocDataAccess.BUS_ENTITY1_ID,dbc);
        if ( relatedIds == null || relatedIds.size() == 0 ) 
        {
            return null;
        }
        int relatedId = ((Integer)relatedIds.get(0)).intValue();
        if ( relatedIds.size() > 1 )
        {
            logError("getSingleRelatedBusEntity, "
            + " pRelationShipType=" + pRelationShipType
            + " pBusEntityId=" + pBusEntityId
            + " got " + relatedIds.size() + " related ids, expected 1."
            );
        }
        return BusEntityDataAccess.select(pCon, relatedId);
    }
    
    protected BusEntityDataVector getAllBusEntities
        (Connection pCon,String pType)
        throws SQLException
    {
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                       pType);
        dbc.addOrderBy(BusEntityDataAccess.SHORT_DESC);
        return BusEntityDataAccess.select(pCon, dbc);
    }
    
    protected PhoneDataVector getPhoneDataForBusEntities
        (Connection pCon,IdVector pBusEntityIds)
        throws SQLException
    {
        DBCriteria dbc = new DBCriteria();
        dbc.addOneOf(PhoneDataAccess.BUS_ENTITY_ID,
                     pBusEntityIds);
        return PhoneDataAccess.select(pCon, dbc);
    }

    protected BuildingServicesContractorView
        getBSCForSite(Connection pCon,int pSiteId)
        throws SQLException, DataNotFoundException
    {
        BusEntityDataVector v = new BusEntityDataVector();
        BusEntityData bsc = getSingleRelatedBusEntity
        (pCon,RefCodeNames.BUS_ENTITY_ASSOC_CD.BSC_FOR_SITE,pSiteId);
        if ( bsc == null ) return null;
        
        v.add(bsc);
        BuildingServicesContractorViewVector bscv
         = mkBSCDetail(pCon, v);
         if ( null == bscv || bscv.size() == 0 ) return null;
         return (BuildingServicesContractorView)bscv.get(0);
    }
    
    protected BuildingServicesContractorViewVector
        mkBSCDetail(Connection pCon,BusEntityDataVector pBscs)
        throws SQLException
    {
        BuildingServicesContractorViewVector
            bscvl = new BuildingServicesContractorViewVector();
        IdVector ids = new    IdVector();
        
        for ( int i = 0; pBscs != null && i < pBscs.size(); i++ )
        {
            BusEntityData bed = (BusEntityData)pBscs.get(i);
            BuildingServicesContractorView
                bscv = BuildingServicesContractorView.createValue();
            bscv.setBusEntityData(bed);
            bscvl.add(bscv);
            ids.add(new Integer(bed.getBusEntityId()));
        }
        
        PhoneDataVector pdv = getPhoneDataForBusEntities(pCon, ids);
        for ( int i = 0; pdv != null && i < pdv.size(); i++ )
        {
            PhoneData phd = (PhoneData)pdv.get(i);
            for ( int i2 = 0; bscvl != null && i2 < bscvl.size(); i2++ )
            {
                BuildingServicesContractorView
                    bscv = (BuildingServicesContractorView)bscvl.get(i2);
                if (phd.getBusEntityId() ==
                    bscv.getBusEntityData().getBusEntityId())
                {
                    bscv.setFaxNumber(phd);
                }
            }
        }
        
        return bscvl;
    }
    
    protected void
        setBSCForSite(Connection pCon,SiteData pSiteData)
        throws SQLException, DataNotFoundException
    {
        logDebug("pSiteData.hasBSC() =" + pSiteData.hasBSC() );
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
        RefCodeNames.BUS_ENTITY_ASSOC_CD.BSC_FOR_SITE
                       );
        dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,
                       pSiteData.getSiteId());
        if (pSiteData.hasBSC() == false)
        {
            BusEntityAssocDataAccess.remove(pCon, dbc);
            return;
        }
        
        BusEntityAssocDataVector assoc = BusEntityAssocDataAccess.select
        (pCon, dbc);
        if ( assoc == null || assoc.size() == 0 ) 
        {
            // Insert an association entry
            BusEntityAssocData insbad = BusEntityAssocData.createValue();
            insbad.setBusEntityAssocCd(RefCodeNames.BUS_ENTITY_ASSOC_CD.BSC_FOR_SITE);
            insbad.setBusEntity1Id(pSiteData.getBSC().getBusEntityData().getBusEntityId());
            insbad.setBusEntity2Id(pSiteData.getSiteId());
            insbad.setAddBy(pSiteData.getBusEntity().getModBy());
            BusEntityAssocDataAccess.insert(pCon, insbad);
            return;
        }
        
        BusEntityAssocData newbad = (BusEntityAssocData)assoc.get(0);
        newbad.setBusEntity1Id(pSiteData.getBSC().getBusEntityData().getBusEntityId());
        newbad.setModBy(pSiteData.getBusEntity().getModBy());
        
        BusEntityAssocDataAccess.update(pCon, newbad);

    }

    protected int setBSCDetail
        (Connection pCon,
         String pBusEntityId,
         String pBSCName,
         String pBSCDesc,
         String pBSCFaxNumber,
         Integer pStoreId,
         String pUser)
        throws SQLException, DataNotFoundException
    {
        int bid = 0;
        if ( null != pBusEntityId && pBusEntityId.length() > 0 )
        {
            bid = Integer.parseInt(pBusEntityId);
        }
        if ( bid <= 0 )
        {
            BusEntityData bed = mkBusEntity
                (pCon,
                 pBSCName, pBSCDesc,
                 RefCodeNames.BUS_ENTITY_TYPE_CD.BUILDING_SVC_CONTRACTOR,
                 pUser);
            bid = bed.getBusEntityId();
            
            mkPhoneEntry
                (pCon,
                 bed.getBusEntityId(),
                 RefCodeNames.PHONE_TYPE_CD.ORDERFAX,
                 pBSCFaxNumber,
                 pUser);
            saveBusEntAssociation(true, bid,pStoreId.intValue(), RefCodeNames.BUS_ENTITY_ASSOC_CD.BSC_STORE,pCon);
        }
        else
        {
            BusEntityData bed = BusEntityDataAccess.select(pCon, bid);
            bed.setShortDesc(pBSCName);
            bed.setLongDesc(pBSCDesc);
            bed.setModBy(pUser);
            BusEntityDataAccess.update(pCon, bed);
            
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(PhoneDataAccess.BUS_ENTITY_ID, bid); 
            dbc.addEqualTo(PhoneDataAccess.PHONE_TYPE_CD,
                           RefCodeNames.PHONE_TYPE_CD.ORDERFAX);
            PhoneDataVector phones =
            PhoneDataAccess.select(pCon, dbc);
            if ( phones == null || phones.size() == 0 )
            {
                mkPhoneEntry
                    (pCon,
                     bed.getBusEntityId(),
                     RefCodeNames.PHONE_TYPE_CD.ORDERFAX,
                     pBSCFaxNumber,
                     pUser);
            }
            else
            {
                for ( int i = 0; phones != null && i < phones.size(); i++ )
                {
                    PhoneData phd = (PhoneData)phones.get(i);
                    phd.setPhoneNum(pBSCFaxNumber);
                    phd.setModBy(pUser);
                    PhoneDataAccess.update(pCon, phd);
                }
            }
            
            saveBusEntAssociation(true, bid,pStoreId.intValue(), RefCodeNames.BUS_ENTITY_ASSOC_CD.BSC_STORE,pCon);
        }
        
        return bid;
    }
    
    protected PhoneData mkPhoneEntry
        (Connection pCon,
         int pBusEntityId,
         String pTypeCd,
         String pFaxNumber,
         String pUserName)
        throws SQLException 
    {
        PhoneData p = PhoneData.createValue();
        p.setBusEntityId(pBusEntityId);
        p.setPhoneTypeCd(pTypeCd);
        p.setPhoneStatusCd(RefCodeNames.PHONE_STATUS_CD.ACTIVE);
        p.setPhoneNum(pFaxNumber);
        p.setAddBy(pUserName);
        p.setModBy(pUserName);
        return PhoneDataAccess.insert(pCon, p);
    }

    protected BusEntityData mkBusEntity
        (Connection pCon,
         String pName,
         String pLongDesc,
         String pTypeCd,
         String pUserName)
        throws SQLException
    {
        BusEntityData b = BusEntityData.createValue();
        b.setShortDesc(pName);
        b.setLongDesc(pLongDesc);
        b.setBusEntityTypeCd(pTypeCd);
        b.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        b.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
        b.setLocaleCd("en-US");
        b.setAddBy(pUserName);
        b.setModBy(pUserName);
        return BusEntityDataAccess.insert(pCon, b);
    }
    
    
    /**
      *Returns an IdVector of the ids that this bus entity is associated with based
      *off the association type.   Returns parents i.e. everything upstream of the
      *queried id.  So all the accounts that a site belongs to.
      */
     public static IdVector getBusEntityAssoc2Ids(Connection pCon, int pBusEntityId, String pBusEntityAssocCd)
     throws java.sql.SQLException{
         DBCriteria crit = new DBCriteria();
         crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pBusEntityId);
         crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,pBusEntityAssocCd);
         return BusEntityAssocDataAccess.selectIdOnly(pCon, BusEntityAssocDataAccess.BUS_ENTITY2_ID, crit);
     }
     
     
     /**
      *Creates or updates an association between 2 bus entities 
      * @param isSingular if there can only be only a single association
      *     That is a distributor can belong to 1 store.
      */
     public static void saveBusEntAssociation(
             boolean isSingular, int primaryBusEntity, int secondaryBusEntity, String assocCode, Connection pConn)
             throws SQLException
     {
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,primaryBusEntity);
            crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,assocCode);
            if(!isSingular){
                crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY2_ID,secondaryBusEntity);
            }
            if(secondaryBusEntity != 0){
                BusEntityAssocDataVector assocV = BusEntityAssocDataAccess.select(pConn,crit);
                Iterator it = assocV.iterator();
                int ct = 0;
                while(it.hasNext()){
                    BusEntityAssocData existing = (BusEntityAssocData)it.next();
                    if(ct == 0){
                        if(existing.getBusEntity2Id() != secondaryBusEntity){
                            existing.setBusEntity2Id(secondaryBusEntity);
                            BusEntityAssocDataAccess.update(pConn, existing);
                        }
                    }else{
                        //clean up
                        if(isSingular){
                            BusEntityAssocDataAccess.remove(pConn,existing.getBusEntityAssocId());
                        }
                    }
                    ct++;
                }
                if(ct == 0){
                    //insert a new one 
                    BusEntityAssocData newAssoc = BusEntityAssocData.createValue();
                    newAssoc.setBusEntity1Id(primaryBusEntity);
                    newAssoc.setBusEntity2Id(secondaryBusEntity);
                    newAssoc.setBusEntityAssocCd(assocCode);
                    BusEntityAssocDataAccess.insert(pConn, newAssoc);
                }
            }else{
                throw new SQLException("Trying to associate "+primaryBusEntity+" with bus entity id 0");
            }
     }
}
