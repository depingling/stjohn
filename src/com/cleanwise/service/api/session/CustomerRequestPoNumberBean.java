/*
 * CustomerRequestPoNumberBean.java
 *
 * Created on February 9, 2005, 2:02 PM
 */

package com.cleanwise.service.api.session;

import com.cleanwise.service.api.dao.BlanketPoNumAssocDataAccess;
import com.cleanwise.service.api.dao.BlanketPoNumDataAccess;
import com.cleanwise.service.api.dao.BusEntityDAO;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.JoinDataAccess;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BlanketPoNumAssocData;
import com.cleanwise.service.api.value.BlanketPoNumData;
import com.cleanwise.service.api.value.BlanketPoNumDataVector;
import com.cleanwise.service.api.value.BlanketPoNumDescData;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;
import com.cleanwise.service.api.value.BusEntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import java.sql.Connection;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import javax.ejb.CreateException;
/**
 *
 * @author bstevens
 * 12/2/2008 Sergei V. Cher: added addStoreBusEntityAssociations() method
 * 12/5/2008 Sergei V. Cher: added searchWithFilters() method
 */
public class CustomerRequestPoNumberBean extends BusEntityServicesAPI{
    /**
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException{}
    
    /**
     *Returns all the BlanketPoNumData records
     *@returns BlanketPoNumDataVector populated with the results, or empty if nothing
     *  was found (not null).
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDataVector viewAll()throws RemoteException{
        Connection conn=null;
        try{
            conn = getConnection();
            return BlanketPoNumDataAccess.selectAll(conn);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Retreives all the blanket po number for the supplied bus entity.
     *@param the business entity id to find direct associations to
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDataVector getBlanketPosForBusEntity(int pBusEntityId) throws RemoteException{
        Connection conn=null;
        try{
            conn = getConnection();
            BlanketPoNumDataVector retVal = new BlanketPoNumDataVector();
            DBCriteria lcrit = new DBCriteria();
            lcrit.addJoinCondition(BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM,BlanketPoNumDataAccess.BLANKET_PO_NUM_ID,
                        BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID);
            lcrit.addJoinTableEqualTo(BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,BlanketPoNumAssocDataAccess.BUS_ENTITY_ID, pBusEntityId);
            lcrit.addDataAccessForJoin(new BlanketPoNumDataAccess());
            List bpoRes = JoinDataAccess.select(conn,lcrit,500);
            Iterator it = bpoRes.iterator();
            while(it.hasNext()){
                retVal.add( (BlanketPoNumData) ((List)it.next()).get(0) );
            }
            return retVal;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Searches the account associations
     *@param the populated BusEntitySearchCriteria to use in the search
     *@param the pBlanketPoNumId to have associations linked to
     *@returns a populated BusEntityDataVector, or an empty one if nothing was found. not null.
     *@throws RemoteExcetpion if an error occurs
     */
    public BusEntityDataVector searchAccountAssociations(BusEntitySearchCriteria pCrit, int pBlanketPoNumId) throws RemoteException{
        return searchBusEntityAssociations(pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT,pBlanketPoNumId);
    }
    
    /**
     *Searches the site associations
     *@param the populated BusEntitySearchCriteria to use in the search
     *@param the pBlanketPoNumId to have associations linked to
     *@returns a populated BusEntityDataVector, or an empty one if nothing was found. not null.
     *@throws RemoteExcetpion if an error occurs
     */
    public BusEntityDataVector searchSiteAssociations(BusEntitySearchCriteria pCrit, int pBlanketPoNumId) throws RemoteException{
        return searchBusEntityAssociations(pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.SITE,pBlanketPoNumId);
    }
    
    /**
     *Searches the store associations
     *@param the populated BusEntitySearchCriteria to use in the search
     *@param the pBlanketPoNumId to have associations linked to
     *@returns a populated BusEntityDataVector, or an empty one if nothing was found. not null.
     *@throws RemoteExcetpion if an error occurs
     */
    public BusEntityDataVector searchStoreAssociations(BusEntitySearchCriteria pCrit, int pBlanketPoNumId) throws RemoteException{
        return searchBusEntityAssociations(pCrit, RefCodeNames.BUS_ENTITY_TYPE_CD.STORE,pBlanketPoNumId);
    }
    
    private BusEntityDataVector searchBusEntityAssociations(BusEntitySearchCriteria pCrit, String pType,int pBlanketPoNumId) throws RemoteException{
        
        Connection conn=null;
        try{
            conn = getConnection();
            DBCriteria lcrit;
            if(pCrit != null){
                lcrit = BusEntityDAO.convertToDBCriteria(pCrit,pType);
            }else{
                lcrit = new DBCriteria();
            }
            lcrit.addJoinCondition(BusEntityDataAccess.CLW_BUS_ENTITY,BusEntityDataAccess.BUS_ENTITY_ID,
                        BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,BlanketPoNumAssocDataAccess.BUS_ENTITY_ID);
            lcrit.addJoinCondition(BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM,BlanketPoNumDataAccess.BLANKET_PO_NUM_ID,
                        BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID);
            lcrit.addJoinTableEqualTo(BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM,BlanketPoNumDataAccess.BLANKET_PO_NUM_ID, pBlanketPoNumId);
            lcrit.addDataAccessForJoin(new BusEntityDataAccess());
            
            List results = JoinDataAccess.select(conn,lcrit,500);
            Iterator it = results.iterator();
            BusEntityDataVector bedv = new BusEntityDataVector();
            while(it.hasNext()){
                BusEntityData bed = (BusEntityData) ((List)it.next()).get(0);
                bedv.add(bed);
            }
            return bedv;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Searches the existing blanket request po object with the supplied criteria
     *@param SearchCriteria the search criteria to use
     *@returns BlanketPoNumDataVector populated with the results, or empty if nothing
     *  was found (not null).
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDataVector search(EntitySearchCriteria pCrit) throws RemoteException{
        Connection conn=null;
        try{
            String bpot = BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM;
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
	        crit.addIsNotNull(BlanketPoNumDataAccess.PO_NUMBER);

            if(Utility.isSet(pCrit.getSearchName())){
                String sd = pCrit.getSearchName();
                if(EntitySearchCriteria.NAME_CONTAINS == pCrit.getSearchNameType()){
                    sd = "%"+sd+"%";
                }else if(EntitySearchCriteria.NAME_STARTS_WITH == pCrit.getSearchNameType()){
                    sd = sd+"%";
                }
                crit.addJoinTableLikeIgnoreCase(bpot, BlanketPoNumDataAccess.SHORT_DESC, sd);
            }
            if(pCrit.getSearchIdAsInt() != 0){
                crit.addJoinTableEqualTo(bpot,BlanketPoNumDataAccess.BLANKET_PO_NUM_ID,pCrit.getSearchIdAsInt());
            }

            if(pCrit.getStoreBusEntityIds() != null &&
	       !pCrit.getStoreBusEntityIds().isEmpty()){
		           crit.addJoinCondition
		    (bpot,
		     BlanketPoNumDataAccess.BLANKET_PO_NUM_ID, 
		     BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,
		     BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID);
                crit.addJoinTableOneOf
		    (BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC, 
		     BlanketPoNumAssocDataAccess.BUS_ENTITY_ID,
		     pCrit.getStoreBusEntityIds());
            }

            BlanketPoNumDataVector toReturn = new BlanketPoNumDataVector();
            JoinDataAccess.selectTableInto(new BlanketPoNumDataAccess(),
					   toReturn,conn,crit, 500);
            return toReturn;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Searches the existing blanket request purchase order (po) object with the 
     *supplied criteria and supplied filter
     *@param SearchCriteria the search criteria to use
     *@param ShowInactiveFl - flag, showing the status of the check box on the screen
     *@returns BlanketPoNumDataVector populated with the results, or empty if nothing
     *  was found (not null).
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDataVector searchWithFilters(EntitySearchCriteria pCrit, boolean pShowInactiveFl) throws RemoteException{
        Connection conn=null;
        try{
            String bpot = BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM;
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
	        crit.addIsNotNull(BlanketPoNumDataAccess.PO_NUMBER);

            if(Utility.isSet(pCrit.getSearchName())){
                String sd = pCrit.getSearchName();
                if(EntitySearchCriteria.NAME_CONTAINS == pCrit.getSearchNameType()){
                    sd = "%"+sd+"%";
                }else if(EntitySearchCriteria.NAME_STARTS_WITH == pCrit.getSearchNameType()){
                    sd = sd+"%";
                }
                crit.addJoinTableLikeIgnoreCase(bpot, BlanketPoNumDataAccess.SHORT_DESC, sd);
            }
            if(pCrit.getSearchIdAsInt() != 0){
                crit.addJoinTableEqualTo(bpot,BlanketPoNumDataAccess.BLANKET_PO_NUM_ID,pCrit.getSearchIdAsInt());
            }

            if(pCrit.getStoreBusEntityIds() != null &&
	       !pCrit.getStoreBusEntityIds().isEmpty()){
		           crit.addJoinCondition
		    (bpot,
		     BlanketPoNumDataAccess.BLANKET_PO_NUM_ID, 
		     BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC,
		     BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID);
                crit.addJoinTableOneOf
		    (BlanketPoNumAssocDataAccess.CLW_BLANKET_PO_NUM_ASSOC, 
		     BlanketPoNumAssocDataAccess.BUS_ENTITY_ID,
		     pCrit.getStoreBusEntityIds());
                
             // new staff, added by svc
             // if ("Show Inactive" checkbox was unchecked on the screen) => pShowInactiveFl="false" =>
             // => Add here "   AND CLW_BLANKET_PO_NUM.STATUS.CD="ACTIVE"   " clause
             if (pShowInactiveFl == false){            	              
                crit.addJoinTableEqualTo
    		    (BlanketPoNumDataAccess.CLW_BLANKET_PO_NUM, 
    		     BlanketPoNumDataAccess.STATUS_CD,
    		     "ACTIVE");
             } //if (pShowInactiveFl...
            }

            BlanketPoNumDataVector toReturn = new BlanketPoNumDataVector();
            JoinDataAccess.selectTableInto(new BlanketPoNumDataAccess(),
					   toReturn,conn,crit, 500);
            
            return toReturn;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }  
    
    /**
     *Returns a fully populated BlanketPoNumDescData object that is identified by 
     *the supplied id.
     *@param int the id of the BlanketPoNum
     *@returns a populated BlanketPoNumDescData object
     *@throws RemoteExcetpion if an error occurs
     *@throws DataNotFoundException if the supplied id does not identify a BlanketPoNum
     */
    public BlanketPoNumDescData getDetail(int BlanketPoNumId)throws RemoteException,DataNotFoundException{
        Connection conn=null;
        try{
            BlanketPoNumDescData bpo = new BlanketPoNumDescData();
            conn = getConnection();
            bpo.setBlanketPoNumData(BlanketPoNumDataAccess.select(conn,BlanketPoNumId));

            return bpo;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    
    /**
     *Adds or updates the BlanketPoNumDescData as necessary.  Updates all of the aux
     *data that is contained in this object.  Returns the BlanketPoNumDescData with 
     *any values that were modified.
     *@param the BlanketPoNumDescData to be added/updated
     *@param String the user doing the action
     *@returns the BlanketPoNumDescData that may or may not have been modified
     *@throws RemoteExcetpion if an error occurs
     */
    public BlanketPoNumDescData addUpdate(BlanketPoNumDescData pDetail, String pUserName)throws RemoteException{
        Connection conn=null;
        
       	try{
            conn = getConnection();
            pDetail.getBlanketPoNumData().setModBy(pUserName);
            if(pDetail.getBlanketPoNumData().getBlanketPoNumId()==0){
                pDetail.getBlanketPoNumData().setAddBy(pUserName);
                pDetail.setBlanketPoNumData(
                    BlanketPoNumDataAccess.insert(conn, pDetail.getBlanketPoNumData()));
            }else{
                BlanketPoNumDataAccess.update(conn, pDetail.getBlanketPoNumData());
            }
            /*BlanketPoNumAssocDataVector newVec = new BlanketPoNumAssocDataVector();
            Iterator it = pDetail.getBlanketPoNumAssocDataVector().iterator();
            while(it.hasNext()){
                BlanketPoNumAssocData assoc = (BlanketPoNumAssocData) it.next();
                assoc.setModBy(pUserName);
                if(assoc.getBlanketPoNumAssocId() == 0){
                    assoc.setAddBy(pUserName);
                    assoc = BlanketPoNumAssocDataAccess.insert(conn,assoc);
                }else{
                    BlanketPoNumAssocDataAccess.update(conn,assoc);
                }
                newVec.add(assoc);
            }
            pDetail.setBlanketPoNumAssocDataVector(newVec);*/
            return pDetail;
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    
    /**
     *Adds associations between the supplied pBlanketPoNumId and the supplied pBusEntityIds
     *@param the pBlanketPoNumId to associate the bus entity ids to
     *@param the pBusEntityIds to associate to the pBlanketPoNumId
     *@throws RemoteExcetpion if an error occurs
     */
    public void addBusEntityAssociations(int pBlanketPoNumId, IdVector pBusEntityIds, String pUserDoingMod)throws RemoteException{
        Connection conn=null;
        
        try{
            conn = getConnection();
            
            Iterator it = pBusEntityIds.iterator();
            while(it.hasNext()){
                Integer id = (Integer) it.next();
                BlanketPoNumAssocData assoc = BlanketPoNumAssocData.createValue();
                assoc.setAddBy(pUserDoingMod);
                assoc.setBlanketPoNumId(pBlanketPoNumId);
                assoc.setBusEntityId(id.intValue());
                assoc.setModBy(pUserDoingMod);
                
                BlanketPoNumAssocDataAccess.insert(conn, assoc);
            }
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
    /**
     *Adds associations between the supplied pBlanketPoNumId and the supplied pBusEntityIds
     *for a store
     *@param the pBlanketPoNumId to associate the bus entity ids to
     *@param the pBusEntityIds to associate to the pBlanketPoNumId
     *@throws RemoteExcetpion if an error occurs
     */
    public void addStoreBusEntityAssociations(int pBlanketPoNumId, IdVector pBusEntityIds, String pUserDoingMod)throws RemoteException{
        Connection conn=null;
        
        try{
            conn = getConnection();
            
            BlanketPoNumDataVector retVal = new BlanketPoNumDataVector();
            DBCriteria crit = new DBCriteria();
            String cond = "BLANKET_PO_NUM_ID = " + pBlanketPoNumId;
            crit.addCondition(cond);
            String pIdName = "BLANKET_PO_NUM_ID";
            IdVector vec = BlanketPoNumAssocDataAccess.selectIdOnly(conn, pIdName, crit);

            if (vec.size() == 0) {
               Iterator it = pBusEntityIds.iterator();
               while(it.hasNext()){
                 Integer id = (Integer) it.next();
                 BlanketPoNumAssocData assoc = BlanketPoNumAssocData.createValue();
                 assoc.setAddBy(pUserDoingMod);
                 assoc.setBlanketPoNumId(pBlanketPoNumId);
                 assoc.setBusEntityId(id.intValue());
                 assoc.setModBy(pUserDoingMod);
                
                //int blanketPoNumId = pDetail.getBlanketPoNumData().getBlanketPoNumId();

                    //pDetail.getBlanketPoNumData().setAddBy(pUserName);
                BlanketPoNumAssocDataAccess.insert(conn, assoc);
               
                } //while
                
                //Connection pCon, BlanketPoNumAssocData pData
                //BlanketPoNumAssocDataAccess.insert(conn, assoc);
            } //if
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    /**
     *removes associations between the supplied pBlanketPoNumId and the supplied pBusEntityIds
     *@param the pBlanketPoNumId to de-associate the bus entity ids to
     *@param the pBusEntityIds to de-associate to the pBlanketPoNumId
     *@throws RemoteExcetpion if an error occurs
     */
    public void removeBusEntityAssociations(int pBlanketPoNumId, IdVector pBusEntityIds)throws RemoteException{
        Connection conn=null;
        try{
            conn = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(BlanketPoNumAssocDataAccess.BLANKET_PO_NUM_ID,pBlanketPoNumId);
            crit.addOneOf(BlanketPoNumAssocDataAccess.BUS_ENTITY_ID,pBusEntityIds);
            BlanketPoNumAssocDataAccess.remove(conn,crit);
        }catch(Exception e){
            throw processException(e);
        }finally{
            closeConnection(conn);
        }
    }
    
}
