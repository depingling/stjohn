package com.cleanwise.service.api.session;

/**
 * Title:        ProfilingBean
 * Description:  Bean implementation for Profiling Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving Profiling information.
 * Copyright:    Copyright (c) 2003
 * Company:      Cleanwise, Inc.
 * @author       Brook Stevens, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;

public class ProfilingBean extends ValueAddedServicesAPI {
    /**
     *Creates a new instance.  Requiered for EJB.
     */
    public void ejbCreate() throws CreateException, RemoteException {}
    
    /**
     *Retrieves a ProfileData Object  for the supplied profile id.
     *
     *@param pProfileId the profile id to fetch.
     *@param pStoreIds the list of store ids to restrict it to.
     *@throws RemoteException if an error occurs
     *@returns a ProfileData object matching for the supplied profile id or null.
     */
    public ProfileData getProfile(IdVector storeIds,int pProfileId) throws RemoteException{
        Connection con=null;
        try{
            con = getConnection();
            try{
                ProfileData pd = ProfileDataAccess.select(con,pProfileId);
                if(storeIds != null && !storeIds.isEmpty() && pd != null){
                    DBCriteria crit = new DBCriteria();
                    crit.addJoinTableEqualTo(ProfileAssocDataAccess.CLW_PROFILE_ASSOC, ProfileAssocDataAccess.PROFILE1_ID,pProfileId);
                    crit.addJoinTableOneOf(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeIds);
                    crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                    crit.addJoinCondition(ProfileAssocDataAccess.CLW_PROFILE_ASSOC,ProfileAssocDataAccess.BUS_ENTITY_ID,
                            BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID);
                    crit.addDataAccessForJoin(new ProfileAssocDataAccess());
                    List res = JoinDataAccess.select(con,crit,1);
                    if(res.isEmpty()){
                        return null;
                    }else{
                        return pd;
                    }
                }else{
                    return pd;
                }
            }catch(DataNotFoundException e){
                return null;
            }
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }

    /**
     *Retrieves a ProfileData Object for the supplied profile id and for requested stores.
     *
     *@param pProfileId the profile id to fetch.
     *@param pStoreIds the list of store ids to restrict it to.
     *@throws RemoteException if an error occurs
     *@returns a ProfileData object (matching for the supplied profile id and for requested stores) or null.
     */
    public ProfileData getProfileForStore(IdVector storeIds, int profileId) 
        throws RemoteException {
        
       if (storeIds == null)
           return null;
       if (storeIds.isEmpty())
           return null;
        
        Connection con = null;
        try {
            con = getConnection();
            try {
                DBCriteria crit = new DBCriteria();
                
                crit.addOneOf(ProfileAssocDataAccess.BUS_ENTITY_ID, storeIds);
                crit.addEqualTo(ProfileAssocDataAccess.PROFILE1_ID, profileId);
                crit.addEqualTo(ProfileAssocDataAccess.PROFILE_ASSOC_CD, 
                    RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_STORE);
                
                ProfileAssocDataVector assocVector = 
                    ProfileAssocDataAccess.select(con, crit, 1);
                
                if (assocVector == null)
                    return null;
                if (assocVector.isEmpty())
                    return null;
                    
                return ProfileDataAccess.select(con, profileId);
            }
            catch (DataNotFoundException e) {
                return null;
            }
        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(con);
        }
    }
    
    /**
     *Retrieves a profile data vector of all profiles that match the specified criteria.
     *reference @see SearchCriteria for valid values of pMatch.
     *
     *@param pStoreIds the list of store ids search criteria.
     *@param String pProfileName the name search criteria
     *@param String pProfileType the profile type you wish to get
     *@param int pMatch how to match the name, if pProfileName is left unset this value is ignored
     *   @see SearchCriteria (all search types are not currently implemented, defaults to
     *   name_contains_ignore_case).
     *@returns ProfileDataVector of all the profiles which match the criteria, or an empty
     *  @see ProfileDataVector if there were no matches
     *@throws RemoteException if an error occurs.
     */
    public ProfileDataVector getProfileCollection(IdVector storeIds, String pProfileName,String pProfileType,int pMatch) throws RemoteException{
        Connection con=null;
        DBCriteria crit = new DBCriteria();
        try{
            String profTable = ProfileDataAccess.CLW_PROFILE;
            con = getConnection();
            boolean critSet = false;
            if(Utility.isSet(pProfileName)){
                critSet = true;
                if (pMatch == SearchCriteria.BEGINS_WITH_IGNORE_CASE){
                    crit.addJoinTableLikeIgnoreCase(profTable,ProfileDataAccess.SHORT_DESC, pProfileName + "%");
                }else{
                    crit.addJoinTableLikeIgnoreCase(profTable,ProfileDataAccess.SHORT_DESC, "%" + pProfileName + "%");
                }
            }
            if(Utility.isSet(pProfileType)){
                critSet = true;
                crit.addJoinTableEqualTo(profTable,ProfileDataAccess.PROFILE_TYPE_CD,pProfileType);
            }
            if(storeIds != null && !storeIds.isEmpty()){
                critSet = true; //if we make heavy use of this remove
                crit.addJoinTableOneOf(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY2_ID,storeIds);
                crit.addJoinTableEqualTo(BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC,BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                crit.addJoinTableNotEqualTo(ProfileAssocDataAccess.CLW_PROFILE_ASSOC,ProfileAssocDataAccess.PROFILE_ASSOC_CD,RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_STORE);
                crit.addJoinCondition(ProfileAssocDataAccess.CLW_PROFILE_ASSOC,ProfileAssocDataAccess.BUS_ENTITY_ID,
                            BusEntityAssocDataAccess.CLW_BUS_ENTITY_ASSOC, BusEntityAssocDataAccess.BUS_ENTITY1_ID);
                crit.addJoinCondition(ProfileDataAccess.CLW_PROFILE,ProfileDataAccess.PROFILE_ID,
                            ProfileAssocDataAccess.CLW_PROFILE_ASSOC, ProfileAssocDataAccess.PROFILE1_ID);
                //crit.addJoinTableOneOf(ProfileAssocDataAccess.CLW_PROFILE_ASSOC,ProfileAssocDataAccess.BUS_ENTITY_ID,storeIds);
            }

            if(critSet){
                ProfileDataVector pdv = new ProfileDataVector();
                JoinDataAccess.selectTableInto(new ProfileDataAccess(),pdv,con,crit, 500);
                ProfileDataVector storeProfiles = getProfileCollectionForStore(
                    storeIds, pProfileName, pProfileType, pMatch);
                if (storeProfiles != null) {
                    pdv.addAll(storeProfiles);
                }        
                return pdv;
            }else{
                throw new RemoteException("Criteria Must Be Set");
            }
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
    
    /**
     * Retrieves a profile data vector of all profiles that match the specified 
     * criteria which are related with the specified stores.   
     * 
     *          
     */    
    public ProfileDataVector getProfileCollectionForStore(IdVector storeIds, 
        String profileName, String profileType, int match) 
            throws RemoteException {
        
        if (storeIds == null)
            return new ProfileDataVector();
        if (storeIds.isEmpty())
            return new ProfileDataVector();
        
        Connection con = null;
        DBCriteria crit = new DBCriteria();
        try {
            con = getConnection();
            
            boolean critSet = false;
            if (Utility.isSet(profileName)) {
                critSet = true;
                if (match == SearchCriteria.BEGINS_WITH_IGNORE_CASE) {
                    crit.addJoinTableLikeIgnoreCase(ProfileDataAccess.CLW_PROFILE,
                        ProfileDataAccess.SHORT_DESC, profileName + "%");
                }
                else {
                    crit.addJoinTableLikeIgnoreCase(ProfileDataAccess.CLW_PROFILE,
                        ProfileDataAccess.SHORT_DESC, "%" + profileName + "%");
                }
            }
            if (Utility.isSet(profileType)) {
                critSet = true;
                crit.addJoinTableEqualTo(ProfileDataAccess.CLW_PROFILE,
                    ProfileDataAccess.PROFILE_TYPE_CD, profileType);
            }            
            critSet = true;
            crit.addJoinTableOneOf(ProfileAssocDataAccess.CLW_PROFILE_ASSOC,
                ProfileAssocDataAccess.BUS_ENTITY_ID, storeIds);
            crit.addJoinTableEqualTo(ProfileAssocDataAccess.CLW_PROFILE_ASSOC,
                ProfileAssocDataAccess.PROFILE_ASSOC_CD, 
                RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_STORE);           
            crit.addJoinCondition(ProfileDataAccess.CLW_PROFILE,
                ProfileDataAccess.PROFILE_ID,
                ProfileAssocDataAccess.CLW_PROFILE_ASSOC, 
                ProfileAssocDataAccess.PROFILE1_ID);

            if (critSet) {
                ProfileDataVector pdv = new ProfileDataVector();
                JoinDataAccess.selectTableInto(new ProfileDataAccess(), pdv, con, crit, 500);
                return pdv;
            }
            else {
                throw new RemoteException("Criteria Must Be Set");
            }
        }
        catch (Exception e) {
            throw processException(e);
        }
        finally {
            closeConnection(con);
        }
    }
    
    /**
     *Returns a BusEntityDataVector of the busEntitys that are configured to the supplied profileId.
     *Only direct relationships are returned.
     *@param pProfileId the profile id
     *@param pBusEntityTypeCd the bus entity type to search for.
     *@param pOptionalBusEntityShortDesc if supplied return reults will be restricted to bus entities
     *  with short descriptions containing this String
     *@throws RemoteException if an error occured
     */
    public BusEntityDataVector getBusEntityCollectionForProfile(int pProfileId,String pBusEntityTypeCd,String pOptionalBusEntityShortDesc)
    throws RemoteException{
        Connection con=null;
        try{
            con = getConnection();
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(ProfileAssocDataAccess.PROFILE1_ID,pProfileId);
            IdVector busIds = ProfileAssocDataAccess.selectIdOnly(con,ProfileAssocDataAccess.BUS_ENTITY_ID, crit);
            crit = new DBCriteria();
            crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,busIds);
            crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,pBusEntityTypeCd);
            if(pOptionalBusEntityShortDesc != null){
                crit.addLikeIgnoreCase(BusEntityDataAccess.SHORT_DESC, "%"+pOptionalBusEntityShortDesc+"%");
            }
            return BusEntityDataAccess.select(con,crit);
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
    
    /**
     *Retrieves the profiles that are availiable to the supplied BusEntityId.
     *
     *@param int pBusEntityId the business entity id. This method is smart enough to figure out all
     *  relationships with a busentityid, i.e. if you give it a site id it will give you back site
     *  relationships, account relationships, and store relationships.  Unless pDirectRelationshipsOnly
     *  equals true, in which case it will only give you the direct relationships
     *@param pDirectRelationshipsOnly indicates that only direct relationships should be returned.  That is
     *  if you pass in an account id it will give you only the relationships to the account, and not the
     *  store.
     *@returns ProfileDataVector a populated ProfileDataVector of the Profiles the supplied
     *  BusEntityId is configured for.
     *@throws RemoteException if an error occurs.
     */
    public ProfileDataVector getProfileCollectionForBusEntity(int pBusEntityId,boolean pDirectRelationshipsOnly) throws RemoteException{
        Connection con=null;
        try{
            con = getConnection();
            IdVector ids = new IdVector();
            ids.add(new Integer(pBusEntityId));
            if(!pDirectRelationshipsOnly){
                BusEntityData bed = BusEntityDataAccess.select(con,pBusEntityId);
                IdVector accountIds = null;
                if(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(bed.getBusEntityTypeCd())){
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);
                    crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY1_ID,pBusEntityId);
                    accountIds = BusEntityAssocDataAccess.selectIdOnly(con,BusEntityAssocDataAccess.BUS_ENTITY2_ID,crit);
                    ids.addAll(accountIds);
                }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(bed.getBusEntityTypeCd())){
                    accountIds = new IdVector();
                    accountIds.add(new Integer(pBusEntityId));
                }
                if(accountIds != null && accountIds.size() > 0){
                    DBCriteria crit = new DBCriteria();
                    crit.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
                    crit.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,accountIds);
                    ids.addAll(BusEntityAssocDataAccess.selectIdOnly(con,BusEntityAssocDataAccess.BUS_ENTITY2_ID,crit));
                }
            }
            
            DBCriteria crit = new DBCriteria();
            if(ids.size() > 1){
                crit.addOneOf(ProfileAssocDataAccess.BUS_ENTITY_ID,ids);
            }else{
                crit.addEqualTo(ProfileAssocDataAccess.BUS_ENTITY_ID,(Integer)ids.get(0));
            }
            ArrayList profileAssocTypes = new ArrayList();
            profileAssocTypes.add(RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_BUS_ENTITY);
            profileAssocTypes.add(RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_STORE);
            crit.addOneOf(ProfileAssocDataAccess.PROFILE_ASSOC_CD, profileAssocTypes);
            String inCrit = ProfileAssocDataAccess.getSqlSelectIdOnly(ProfileAssocDataAccess.PROFILE1_ID,crit);
            crit = new DBCriteria();
            crit.addOneOf(ProfileDataAccess.PROFILE_ID,inCrit);
            crit.addEqualTo(ProfileDataAccess.PROFILE_TYPE_CD,RefCodeNames.PROFILE_TYPE_CD.SURVEY);
            logInfo("getProfileCollectionForBusEntity: "+ProfileDataAccess.getSqlSelectIdOnly("*", crit));
            ProfileDataVector pdv = ProfileDataAccess.select(con,crit);
            return pdv;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
    
    /**
     *Retrieves the profile and meta data for supplied ProfileId.  The view objects returned will not
     *have the detail (results) populated.
     *
     *@param int pProfileId the profile id.
     *@throws RemoteException if an error occurs.
     */
    public ProfileView getProfileView(int pProfileId) throws RemoteException{
        return getProfileViewMaster(pProfileId,0,null);
    }
    
    /**
     *Retrieves questions and results for the supplied ProfileId given by the supplied BusEntityId.
     *
     *@param int pProfileId the profile id.
     *@param int pBusEntityId the business entity id.
     *@throws RemoteException if an error occurs.
     */
    public ProfileView getProfileResultsForBusEntity(int pProfileId, int pBusEntityId) throws RemoteException{
        //check type should be any "master" profile, currently that is a survey only
        return getProfileViewMaster(pProfileId,pBusEntityId,RefCodeNames.PROFILE_TYPE_CD.SURVEY);
    }
    
    /**
     *Starts Recursion to retrieve all the questions and the detail for the supplied busEntityId and profileId
     *If the busEntityId is 0 no detail will be retrieved.  If parameter pCheckType is supplied it will be
     *checked against the first retrieved profile.
     */
    private ProfileView getProfileViewMaster(int pProfileId, int pBusEntityId, String pCheckType) throws RemoteException{
        Connection con=null;
        try{
            con = getConnection();
            ProfileView master = ProfileView.createValue();
            ProfileData pd = ProfileDataAccess.select(con,pProfileId);
            master.setProfileData(pd);
            //master.setProfileOrderString(Integer.toString(pd.getProfileOrder()));
            if (pCheckType != null){
                if(pd == null || !pCheckType.equals(pd.getProfileTypeCd())){
                    throw new RemoteException("Invalid profileId for getProfileQuestionsForBusEntity: " + pProfileId +
                    " expecting type: [" + pCheckType + "] found: [" + pd.getProfileTypeCd() + "]");
                }
            }
            master.setChildren(getProfileViewMaster(master, pProfileId,pBusEntityId,con));
            debugProfileView(master,0);
            return master;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
    
    
    static final java.util.Comparator PROFILE_ORDER_COMPARE = new java.util.Comparator() {
        public int compare(Object o1, Object o2)
        {
            int id1 = ((ProfileView)o1).getProfileData().getProfileOrder();
            int id2 = ((ProfileView)o2).getProfileData().getProfileOrder();
            return id1 - id2;
        }
    };
    
    private void debugProfileView(ProfileView prof,int pLvl){
        StringBuffer buf = new StringBuffer();
        for(int i=0;i<pLvl;i++){
            buf.append(">>>>");
        }
        buf.append(prof.getProfileData().getShortDesc());
        buf.append("["+prof.getProfileDetailDataVector().size()+"]");
        if(prof.getProfileDetailDataVector().size() > 0){
            buf.append("::"+prof.getProfileDetailDataVectorElement(0).getValue());
            buf.append("::"+prof.getProfileDetailDataVectorElement(0).getProfileDetailId());
        }
        Iterator it = prof.getChildren().iterator();
        while(it.hasNext()){
            debugProfileView((ProfileView) it.next(), pLvl + 1);
        }
    }
    
    /**
     *Matches up the detail data to the profiles, this is very trivial unless the question is a number
     *question in which case the detail needs to be propgated accross all of the numberd resonses in the 
     *proper order.
     */
    private ProfileViewVector matchDetail(int pBusEntityId, Connection pCon, 
    ProfileView pParent, ProfileView pProfile) throws SQLException{

        ProfileViewVector vec = new ProfileViewVector();
        if(RefCodeNames.PROFILE_QUESTION_TYPE_CD.NUMBER.equals(pParent.getProfileData().getProfileQuestionTypeCd())){
            ProfileDetailDataVector parentDetailV = pParent.getProfileDetailDataVector();
            if(parentDetailV.size() > 1){
                Thread.currentThread().dumpStack();
            }
            
            int loopValue = 0;
            boolean doneWithLoops = false;
            while ( doneWithLoops == false && parentDetailV.size() > 0 ) {
                ProfileDetailData pd = (ProfileDetailData)parentDetailV.get(0);
                
                // Find all the loops for this question.
                
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(ProfileDetailDataAccess.PROFILE_ID,pProfile.getProfileData().getProfileId());
                crit.addEqualTo(ProfileDetailDataAccess.BUS_ENTITY_ID,pBusEntityId);
                crit.addEqualTo(ProfileDetailDataAccess.LOOP_VALUE,loopValue);
                crit.addEqualTo(ProfileDetailDataAccess.PROFILE_DETAIL_PARENT_ID,pd.getProfileDetailId());
                crit.addEqualTo(ProfileDetailDataAccess.PROFILE_DETAIL_STATUS_CD,RefCodeNames.PROFILE_DETAIL_STATUS_CD.ACTIVE);
                crit.addOrderBy(ProfileDetailDataAccess.PROFILE_ID);
                crit.addOrderBy(ProfileDetailDataAccess.LOOP_VALUE);
                ProfileDetailDataVector detail = ProfileDetailDataAccess.select(pCon,crit);
                if   ( detail == null || detail.size() == 0 )
                {
                    doneWithLoops = true;
                } else {
                    
                    //all this should really be cloned!
                    ProfileView newProf = ProfileView.createValue();
                    newProf.setProfileData(pProfile.getProfileData());
                    newProf.setProfileMetaDataVector(pProfile.getProfileMetaDataVector());
                    newProf.setProfileDetailDataVector(detail);
                    vec.add(newProf);
                }
                loopValue++;
                               
            }

            //if there was no detail then just add the question as it is...blank
            if(vec.size() == 0){
                vec.add(pProfile);
            }
            
        }else{
            int parentDetId = 0;
            if(pParent.getProfileDetailDataVector().size() > 0){
                ProfileDetailData parentDet = (ProfileDetailData) pParent.getProfileDetailDataVector().get(0);
                parentDetId = parentDet.getProfileDetailId();
            }
            
            int pid = pProfile.getProfileData().getProfileId();
            
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(ProfileDetailDataAccess.PROFILE_ID,pid);
            crit.addEqualTo(ProfileDetailDataAccess.BUS_ENTITY_ID,pBusEntityId);
            crit.addEqualTo(ProfileDetailDataAccess.PROFILE_DETAIL_STATUS_CD,RefCodeNames.PROFILE_DETAIL_STATUS_CD.ACTIVE);
            crit.addEqualTo(ProfileDetailDataAccess.PROFILE_DETAIL_PARENT_ID,parentDetId);
            pProfile.setProfileDetailDataVector(ProfileDetailDataAccess.select(pCon,crit));
            vec.add(pProfile);
        }
        return vec;
    }
    
    /**
     *Recursive method to retrieve all the questions and the detail for the supplied busEntityId and profileId
     *If the busEntityId is 0 no detail will be retrieved.
     */
    private ProfileViewVector getProfileViewMaster(ProfileView pParent, int pProfileId,int pBusEntityId,Connection pCon) throws Exception{
        
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(ProfileAssocDataAccess.PROFILE2_ID,pProfileId);
        String inCrit = ProfileAssocDataAccess.getSqlSelectIdOnly(ProfileAssocDataAccess.PROFILE1_ID,crit);
        crit = new DBCriteria();
        crit.addOneOf(ProfileDataAccess.PROFILE_ID,"("+inCrit+")");
        ProfileDataVector pdv = ProfileDataAccess.select(pCon,crit);
        ProfileViewVector results = new ProfileViewVector();
        ArrayList resultsList = new ArrayList();
        Iterator it = pdv.iterator();
        while(it.hasNext()){
            ProfileData pd = (ProfileData) it.next();
            if(RefCodeNames.PROFILE_STATUS_CD.ACTIVE.equals(pd.getProfileStatusCd())
            && RefCodeNames.PROFILE_TYPE_CD.QUESTION.equals(pd.getProfileTypeCd())){
                ProfileViewVector loopResults = new ProfileViewVector();
                ProfileView question = ProfileView.createValue();
                question.setProfileData(pd);
                //question.setProfileOrderString(Integer.toString(pd.getProfileOrder()));
                crit = new DBCriteria();
                crit.addEqualTo(ProfileMetaDataAccess.PROFILE_ID,pd.getProfileId());
                crit.addEqualTo(ProfileMetaDataAccess.PROFILE_META_STATUS_CD,RefCodeNames.PROFILE_META_STATUS_CD.ACTIVE);
                //order by the value so we get question answers in alphabetic order
                crit.addOrderBy(ProfileMetaDataAccess.CLW_VALUE);
                question.setProfileMetaDataVector(ProfileMetaDataAccess.select(pCon,crit));
                ProfileViewVector newChildren;
                if(pBusEntityId > 0 && pParent != null){
                    //this is where all the junk was before
                    newChildren = matchDetail(pBusEntityId, pCon, pParent, question);
                }else{
                    newChildren = new ProfileViewVector();
                    newChildren.add(question);
                }
                Iterator itNc = newChildren.iterator();
                while(itNc.hasNext()){
                    ProfileView newChild = (ProfileView) itNc.next();
                    ProfileViewVector childQuestions = getProfileViewMaster(newChild, pd.getProfileId(), pBusEntityId,pCon);
                    newChild.setChildren(childQuestions);
                    loopResults.add(newChild);
                }
                resultsList.add(loopResults);
            }
        }
        
        //this is a bit wierd, to keep everything in order we get a
        //list of the results and then intersperce them so for child
        //questions with answers we get the correct info in
        //alternating order: master
        
        //  childA
        //  childB
        //  childA
        //  childB
        //  ...
        //instead of 
        //master
        //  childA
        //  childA
        //  childB
        //  childB
        //  ...
        int innerCt = 0;
        while(true){
            boolean voteNoBreak = false;
            Iterator itL = resultsList.iterator();
            while(itL.hasNext()){
                ProfileViewVector subRes = (ProfileViewVector) itL.next();
                if(subRes.size() > innerCt){
                    results.add(subRes.get(innerCt));
                    voteNoBreak = true;
                }else{
                    //results.add(null);?
                }
            }
            
            if(!voteNoBreak){
                break;
            }
            innerCt ++;
        }
        
        return results;
    }
    
    
    
    /**
     *Updates a profileView creating all the necessary relationships and updating the profiles themselves.
     *
     *@returns the updated ProfileView (id values set etc).
     *@throws RemoteException if an error occurs.
     */
    public ProfileView updateProfileView(ProfileView pProfileView, String pUserDoingMod,boolean pDetailDataUpdate)
    throws RemoteException{
        Connection con=null;
        
        this.debugProfileView(pProfileView,0);
        try{
            con = getConnection();
            updateProfileView(null,pProfileView, con, pUserDoingMod,pDetailDataUpdate);
            return pProfileView;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
    
    public ProfileView updateProfileViewForStore(ProfileView pProfileView, 
        String pUserDoingMod,boolean pDetailDataUpdate, IdVector storeIds) throws RemoteException {
        Connection con = null;
        
        this.debugProfileView(pProfileView,0);
        try {
            con = getConnection();
            updateProfileViewForStore(null, pProfileView, 
                con, pUserDoingMod, pDetailDataUpdate, storeIds);
            return pProfileView;
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
    
    //removes any entrys from the vector that are not "set"
    private void removeEmptyDetailData(ProfileDetailDataVector pDetail){
        /*
        if (pDetail == null){
            return;
        }
        Iterator it = pDetail.iterator();
        while(it.hasNext()){
            ProfileDetailData o = (ProfileDetailData) it.next();
            if(o == null){
                it.remove();
            }else if((o.getProfileDetailId() == 0) && !Utility.isSet(o.getValue())){
                it.remove();
            }
        }
        */
        return;
    }
    
    //recursive method to actually do the work of updating a ProfileView and creating necessary associations
    //when run from a top level (no parents) pass in null for pParent.
    private void updateProfileView(ProfileView parent, ProfileView profileView, 
        Connection con, String userName,boolean detailDataUpdate) throws Exception {        
        updateProfileView(parent, profileView, con, userName, detailDataUpdate, false, null);
    }
    
    private void updateProfileViewForStore(ProfileView parent, ProfileView profileView, 
        Connection con, String userName, boolean detailDataUpdate, IdVector storeIds) throws Exception {        
        updateProfileView(parent, profileView, con, userName, detailDataUpdate, true, storeIds);
    }
    
    //recursive method to actually do the work of updating a ProfileView and creating necessary associations
    //when run from a top level (no parents) pass in null for pParent.
    private void updateProfileView(ProfileView pParent, ProfileView pProfileView, Connection pCon,
        String pUserName,boolean pDetailDataUpdate, boolean isStore, IdVector storeIds) throws Exception{
        //if we have an empty branch here just return, if it has other data filled in throw exception
        if(pProfileView.getProfileData() == null){
            if(pProfileView.getChildren() != null || pProfileView.getProfileDetailDataVector() != null){
                throw new IllegalStateException("Broken link, null profile data with populated detail and/or children");
            }
            return;
        }
        
        //update the meta data
        if(!pDetailDataUpdate){
            pProfileView.getProfileData().setModBy(pUserName);
            //do update
            if(pProfileView.getProfileData().getProfileId() > 0){
                ProfileDataAccess.update(pCon, pProfileView.getProfileData());
            }else{
                pProfileView.getProfileData().setAddBy(pUserName);
                if (isStore) {
                    if (null == storeIds || storeIds.isEmpty()) {
                        throw new Exception("A stores are not defined to update profile.");
                    }
                    int storeId = ((Integer)storeIds.get(0)).intValue();
                    pProfileView.setProfileData(
                        createNewProfileForStore(pProfileView.getProfileData(), 
                            storeId, pCon, pUserName));
                }
                else {
                    pProfileView.setProfileData(
                        ProfileDataAccess.insert(pCon, 
                            pProfileView.getProfileData()));
                }
            }
            if(pProfileView.getProfileMetaDataVector() != null){
                for(int i=0,len=pProfileView.getProfileMetaDataVector().size();i<len;i++){
                    ProfileMetaData meta = (ProfileMetaData) pProfileView.getProfileMetaDataVector().get(i);
                    meta.setModBy(pUserName);
                    meta.setProfileId(pProfileView.getProfileData().getProfileId());
                    if(meta.getProfileMetaId() > 0){
                        ProfileMetaDataAccess.update(pCon, meta);
                    }else{
                        meta.setAddBy(pUserName);
                        pProfileView.getProfileMetaDataVector().set(i, ProfileMetaDataAccess.insert(pCon, meta));
                    }
                }
            }
            
            //now update/create parental relationships
            if(pParent != null){
                //remove existing associations
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(ProfileAssocDataAccess.PROFILE1_ID,pProfileView.getProfileData().getProfileId());
                crit.addEqualTo(ProfileAssocDataAccess.PROFILE_ASSOC_CD,RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_PROFILE);
                ProfileAssocDataAccess.remove(pCon,crit);
                
                //create new association to parent
                ProfileAssocData newAssoc = ProfileAssocData.createValue();
                newAssoc.setAddBy(pUserName);
                newAssoc.setModBy(pUserName);
                newAssoc.setProfile1Id(pProfileView.getProfileData().getProfileId());
                newAssoc.setProfile2Id(pParent.getProfileData().getProfileId());
                newAssoc.setProfileAssocCd(RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_PROFILE);
                ProfileAssocDataAccess.insert(pCon, newAssoc);
            }
        }else{
            
            //update the detail data
            if(pProfileView.getProfileDetailDataVector() != null){
                removeEmptyDetailData(pProfileView.getProfileDetailDataVector());
                for(int i=0,len=pProfileView.getProfileDetailDataVector().size();i<len;i++){
                    ProfileDetailData detail = (ProfileDetailData) pProfileView.getProfileDetailDataVector().get(i);
                    logDebug("   ---- detail to update=" + detail);
                    if(!Utility.isSet(detail.getProfileDetailStatusCd())){
                        detail.setProfileDetailStatusCd(RefCodeNames.PROFILE_DETAIL_STATUS_CD.ACTIVE);
                    }
                    //set the details parent
                    removeEmptyDetailData(pParent.getProfileDetailDataVector());
                    if(pParent.getProfileDetailDataVector() != null && 
                    pParent.getProfileDetailDataVector().size() > 0 ){
                        int pid = ((ProfileDetailData)pParent.getProfileDetailDataVector().get(0)).getProfileDetailId();
                        if ( pid <= 0 ) {
                            continue;
                        }
                        detail.setProfileDetailParentId(pid);
                    }
                    //if(Utility.isSet(detail.getValue())){
                        detail.setModBy(pUserName);
                        detail.setProfileId(pProfileView.getProfileData().getProfileId());
                        if(detail.getProfileDetailId() > 0){
                            if(Utility.isSet(detail.getValue())){
                                ProfileDetailDataAccess.update(pCon, detail);
                            }else{
                                ProfileDetailDataAccess.remove(pCon, detail.getProfileDetailId());
                            }
                        }else{
                            if(Utility.isSet(detail.getValue())){
                                detail.setAddBy(pUserName);
                                pProfileView.getProfileDetailDataVector().set
                                (i, ProfileDetailDataAccess.insert(pCon, detail));
                            }
                        }
                    //}
                }
            }
        }
        
        
        //recurse through the children
        if(pProfileView.getChildren() != null){
            Iterator it = pProfileView.getChildren().iterator();
            while(it.hasNext()){
                updateProfileView(pProfileView,(ProfileView)it.next(),pCon,pUserName,pDetailDataUpdate);
            }
        }
    }
    
    /**
     *Adds the supplied profiles BusEntity associations.
     *
     *@param pProfileId the profile id to update the associations of.
     *@param pBusEntityIds the busEntityIds to associate with the supplied profileId
     *@param pBusEntityTypeCd the busEntityType we are associating (Account, Site, etc)
     *@param pUser the user doing the update
     *@throws RemoteException if an error occurs.
     */
    public void addProfileBusEntityAssociations(int pProfileId, IdVector pBusEntityIds, String pBusEntityTypeCd, String pUser)
    throws RemoteException{
        updateProfileBusEntityAssociations(pProfileId, pBusEntityIds, pBusEntityTypeCd, pUser, true);
    }
    
    /**
     *Removes the supplied profiles BusEntity associations.
     *
     *@param pProfileId the profile id to update the associations of.
     *@param pBusEntityIds the busEntityIds to associate with the supplied profileId
     *@param pBusEntityTypeCd the busEntityType we are associating (Account, Site, etc)
     *@param pUser the user doing the update
     *@throws RemoteException if an error occurs.
     */
    public void removeProfileBusEntityAssociations(int pProfileId, IdVector pBusEntityIds, String pBusEntityTypeCd, String pUser)
    throws RemoteException{
        updateProfileBusEntityAssociations(pProfileId, pBusEntityIds, pBusEntityTypeCd, pUser, false);
    }
    
    private void updateProfileBusEntityAssociations(int pProfileId, IdVector pBusEntityIds, String pBusEntityTypeCd, String pUser, boolean pAdding)
    throws RemoteException{
        Connection con = null;
        try{
            con = getConnection();
            if(pAdding){
                Iterator it = pBusEntityIds.iterator();
                while(it.hasNext()){
                    ProfileAssocData newAssoc = ProfileAssocData.createValue();
                    newAssoc.setBusEntityId(((Integer) it.next()).intValue());
                    newAssoc.setModBy(pUser);
                    newAssoc.setAddBy(pUser);
                    newAssoc.setProfileAssocCd(RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_BUS_ENTITY);
                    newAssoc.setProfile1Id(pProfileId);
                    ProfileAssocDataAccess.insert(con,newAssoc);
                }
            }else{
                DBCriteria crit = new DBCriteria();
                crit.addEqualTo(ProfileAssocDataAccess.PROFILE1_ID,pProfileId);
                crit.addOneOf(ProfileAssocDataAccess.BUS_ENTITY_ID,pBusEntityIds);
                crit.addEqualTo(ProfileAssocDataAccess.PROFILE_ASSOC_CD,RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_BUS_ENTITY);
                ProfileAssocDataAccess.remove(con,crit);
            }
        }catch (Exception e){
            throw processException(e);
        }finally{
            closeConnection(con);
        }
    }
    
    
    /**
     *Updates the profile results.
     *
     *@throws RemoteException if an error occurs.
     */
    public void updateProfileQuestionResults() throws RemoteException{
        
    }
    
    /**
	Creation of a new profile that have association with proper store
	*
	*@param profile the data for a new profile
	*@param storeId the store for which a new profile will be created 
	*@param con the connection with database
	*@param userName the user name doing the update      	
	*/
	private ProfileData createNewProfileForStore(ProfileData profile, int storeId,
        Connection con, String userName) throws Exception {
      
        // Data validation
		if (profile == null) {
			throw new IllegalStateException("The data for a new profile is not defined");
        }
		if (con == null) {
			throw new IllegalStateException("Connection is not established");
        }
		if (userName == null) {
			throw new IllegalStateException("User name is not defined");
        }		
			
        // Creation of the new profile (creation of the new record in the "PROFILE" table) 
		ProfileData baseProfile = (ProfileData)profile.clone();            
		baseProfile.setAddBy(userName);
		baseProfile.setModBy(userName);
        ProfileData newProfile = ProfileDataAccess.insert(con, baseProfile);
      
        // Creation of the new association between new profile and current user. 
        // A new proper record in the "PROFILE_ASSOC" will be created.
		ProfileAssocData newAssoc = ProfileAssocData.createValue();
        newAssoc.setAddBy(userName);
		newAssoc.setModBy(userName);
		newAssoc.setProfile1Id(newProfile.getProfileId());
		newAssoc.setProfile2Id(0);
		newAssoc.setBusEntityId(storeId);
		newAssoc.setProfileAssocCd(RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_STORE);
		ProfileAssocDataAccess.insert(con, newAssoc);
      
        return newProfile;
    }
    
    
    /**
     * Check correct profile's relations(associations) between profile and
     * store(s), store(s) and account(s).
     * 
     * @param pProfileId
     *            Profile ID.
     * @param pStoreIds
     *            Store IDs.
     * @param pAccountIds
     *            Account IDs.
     * @throws RemoteException
     *             if an error occurs.
     */
    public void checkProfileRelations(int pProfileId, IdVector pStoreIds,
            IdVector pAccountIds) throws RemoteException {
        Connection con = null;
        try {
			// Check for new profile.
            if (pProfileId == 0) {
                return;
            }
            if (pStoreIds == null) {
                throw new DataNotFoundException("PROFILE_ID :" + pProfileId);
            }
            con = getConnection();
            DBCriteria cr = new DBCriteria();
            cr.addOneOf(ProfileAssocDataAccess.BUS_ENTITY_ID, pStoreIds);
            cr.addEqualTo(ProfileAssocDataAccess.PROFILE1_ID, pProfileId);
            cr.addEqualTo(ProfileAssocDataAccess.PROFILE_ASSOC_CD, RefCodeNames.PROFILE_ASSOC_CD.PROFILE_OF_STORE);
            ProfileAssocDataVector list = ProfileAssocDataAccess.select(con, cr);
            if (list.size() == 0) {
                logInfo("Wrong assoc for stores:" + pStoreIds + " and profile:"
                        + pProfileId);
                throw new DataNotFoundException("PROFILE_ID :" + pProfileId);
            }
            if (pAccountIds == null) {
                return;
            }
            cr = new DBCriteria();
            cr.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID, pAccountIds);
            cr.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY2_ID, pStoreIds);
            cr.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            BusEntityAssocDataVector list2 = BusEntityAssocDataAccess.select(con, cr);
            Set<Integer> buffer = new HashSet<Integer>();
            for (int i = 0; i < list2.size(); i++) {
                BusEntityAssocData item = (BusEntityAssocData) list2.get(i);
                buffer.add(item.getBusEntity1Id());
            }
            Set<Integer> wrongAccounts = new TreeSet<Integer>();
            for (int i = 0; i < pAccountIds.size(); i++) {
                int id = (Integer) pAccountIds.get(i);
                if (buffer.contains(id) == false) {
                    wrongAccounts.add(id);
                }
            }
            if (wrongAccounts.size() > 0) {
                logInfo("Wront assoc for stores:" + pStoreIds
                        + " and accounts:" + wrongAccounts);
                throw new DataNotFoundException("PROFILE_ID :" + pProfileId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            closeConnection(con);
        }
    }
}
