package com.cleanwise.service.api.dao;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;

import com.cleanwise.service.api.session.User;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.UserAssocData;
import com.cleanwise.service.api.value.UserDataVector;
import com.cleanwise.service.api.value.UserSearchCriteriaData;

public class UserDAO {
	private static final Category log = Category.getInstance(UserDAO.class);
	
	/**
     *Returns the users that match the selected search criteria.
     *
     *@param pSearchCriteria    the UserSearchCriteria.
     *@param conn				A open @see java.sql.Connection object
     *@return                   UserDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public static UserDataVector getUsersCollectionByCriteria(Connection conn, UserSearchCriteriaData pSearchCriteria)
    throws SQLException {
        // will not return null vector unless exception thrown
        UserDataVector udv = null;

        DBCriteria crit = convertToDBCriteria(pSearchCriteria);        

        //Fix reporting user security hole
        if (pSearchCriteria.getUserAccountIds() != null && !pSearchCriteria.getUserAccountIds().isEmpty()) {
        	log.info("UserBean.getUserCollectionByCriteria SQL 1a: "+UserDataAccess.getSqlSelectIdOnly("*",crit));            
            IdVector userIds = UserDataAccess.selectIdOnly(conn, crit);
            String subSql = "SELECT \n" +
                    "  A.USER_ID\n" +
                    "FROM \n" +
                    "  (SELECT * FROM CLW_USER_ASSOC UA1 WHERE UA1.USER_ID \n" +
                    "  IN (SELECT UA.USER_ID FROM CLW_USER_ASSOC UA WHERE UA.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "'\n" +
                    "   AND UA.USER_ID IN (" +  Utility.toCommaSting(userIds) + ")\n" +
                    "   AND UA.BUS_ENTITY_ID IN  (" + Utility.toCommaSting(pSearchCriteria.getUserAccountIds()) + "))\n" +
                    "     AND  UA1.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "') A, \n" +
                    "  (SELECT * FROM CLW_USER_ASSOC UA2 WHERE UA2.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "'  \n" +
                    "   AND UA2.USER_ID IN (" +  Utility.toCommaSting(userIds) + ")\n" +
                    "  AND  UA2.BUS_ENTITY_ID \n" +
                    "  IN (" + Utility.toCommaSting(pSearchCriteria.getUserAccountIds()) + ")) B  \n" +
                    "WHERE A.USER_ASSOC_ID = B.USER_ASSOC_ID (+)\n" +
                    "GROUP BY A.USER_ID  \n" +
                    "HAVING COUNT(*) = COUNT(B.USER_ID)";
            crit = new DBCriteria(); //replace the old one as we have results of that in this
            crit.addOneOf(UserDataAccess.USER_ID, subSql);
        }

        int max = 1000;
        
        //Fix reporting user security hole
        if (pSearchCriteria.getUserStoreIds() != null && !pSearchCriteria.getUserStoreIds().isEmpty()) {
        	log.info("UserBean.getUserCollectionByCriteria SQL 1b: "+UserDataAccess.getSqlSelectIdOnly("*",crit));    
        	
        	IdVector userIds = UserDataAccess.selectIdOnly(conn, crit);
        	
        	log.info("userIds.size() = " + userIds.size());
        	//log.info("*****userIds = " + userIds);
        	
        	if(userIds != null && !userIds.isEmpty()){
        		for (int i = 0; i < userIds.size(); i+=max) {
                    int end = (i + max > userIds.size()) ? userIds.size() : i + max;
                    List subuserIds = userIds.subList(i,end);
                	log.info("subuserIds.size() = " + subuserIds.size());
                    
                	//log.info("*****subuserIds = " + subuserIds);
                	
	        		String ua1 = Utility.toSqlInClause("UA.USER_ID",subuserIds);
	        		String ua2 = Utility.toSqlInClause("UA2.USER_ID",subuserIds);
	        		
		            String subSql = "SELECT \n" +
		                    "  A.USER_ID\n" +
		                    "FROM \n" +
		                    "  (SELECT * FROM CLW_USER_ASSOC UA1 WHERE UA1.USER_ID \n" +
		                    "  IN (SELECT UA.USER_ID FROM CLW_USER_ASSOC UA WHERE UA.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.STORE +  "'\n" +
		                    "   AND "+ua1+"\n" +
		                    "   AND UA.BUS_ENTITY_ID IN  (" + Utility.toCommaSting(pSearchCriteria.getUserStoreIds()) + "))\n" +
		                    "     AND  UA1.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.STORE + "') A, \n" +
		                    "  (SELECT * FROM CLW_USER_ASSOC UA2 WHERE UA2.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.STORE + "'  \n" +
		                    "   AND "+ua2+"\n" +
		                    "  AND  UA2.BUS_ENTITY_ID \n" +
		                    "  IN (" + Utility.toCommaSting(pSearchCriteria.getUserStoreIds()) + ")) B  \n" +
		                    "WHERE A.USER_ASSOC_ID = B.USER_ASSOC_ID (+)\n" +
		                    "GROUP BY A.USER_ID  \n" +
		                    "HAVING COUNT(*) = COUNT(B.USER_ID)";
		            crit = new DBCriteria(); //replace the old one as we have results of that in this
		            crit.addOneOf(UserDataAccess.USER_ID, subSql);
		            
		            //log.info("before calling UserDataAccess.select(conn, crit, max)");
		            
		            UserDataVector udvwork = UserDataAccess.select(conn, crit, max);
		            if(udv == null){
		            	udv = udvwork;
		            }else{
		            	udv.addAll(udvwork);
		            }
		            if(udv.size() >= max){
		            	List tempList = udv.subList(0, max);
		            	udv = new UserDataVector();
		            	udv.addAll(tempList);
		            	break;
		            }
        		}
        	}else{
        		//no users matched the query so just return.
        		return new UserDataVector();
        	}
        }else{
	        //log.info("UserBean.getUserCollectionByCriteria SQL 2: "+UserDataAccess.getSqlSelectIdOnly("*",crit));    
	        udv = UserDataAccess.select(conn, crit, max);
        }
        
        return udv;
    }
      
    public static UserDataVector getUsersCollectionByCriteriaMod(Connection conn, UserSearchCriteriaData pSearchCriteria)
    throws SQLException {
        // will not return null vector unless exception thrown
        UserDataVector udv = null;

        DBCriteria crit = convertToDBCriteria(pSearchCriteria);        

        //Fix reporting user security hole
        if (pSearchCriteria.getUserAccountIds() != null && !pSearchCriteria.getUserAccountIds().isEmpty()) {
        	log.info("UserBean.getUserCollectionByCriteriaMod SQL 1a: "+UserDataAccess.getSqlSelectIdOnly("*",crit));            
            IdVector userIds = UserDataAccess.selectIdOnly(conn, crit);
            String subSql = "SELECT \n" +
                    "  A.USER_ID\n" +
                    "FROM \n" +
                    "  (SELECT * FROM CLW_USER_ASSOC UA1 WHERE UA1.USER_ID \n" +
                    "  IN (SELECT UA.USER_ID FROM CLW_USER_ASSOC UA WHERE UA.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "'\n" +
                    "   AND UA.USER_ID IN (" +  Utility.toCommaSting(userIds) + ")\n" +
                    "   AND UA.BUS_ENTITY_ID IN  (" + Utility.toCommaSting(pSearchCriteria.getUserAccountIds()) + "))\n" +
                    "     AND  UA1.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "') A, \n" +
                    "  (SELECT * FROM CLW_USER_ASSOC UA2 WHERE UA2.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.ACCOUNT + "'  \n" +
                    "   AND UA2.USER_ID IN (" +  Utility.toCommaSting(userIds) + ")\n" +
                    "  AND  UA2.BUS_ENTITY_ID \n" +
                    "  IN (" + Utility.toCommaSting(pSearchCriteria.getUserAccountIds()) + ")) B  \n" +
                    "WHERE A.USER_ASSOC_ID = B.USER_ASSOC_ID (+)\n" +
                    "GROUP BY A.USER_ID  \n" +
                    "HAVING COUNT(*) = COUNT(B.USER_ID)";
            crit = new DBCriteria(); //replace the old one as we have results of that in this
            crit.addOneOf(UserDataAccess.USER_ID, subSql);
        }

        int max = 1000;
        
        //Fix reporting user security hole
        if (pSearchCriteria.getUserStoreIds() != null && !pSearchCriteria.getUserStoreIds().isEmpty()) {
        	log.info("UserBean.getUserCollectionByCriteriaMod SQL 1b: "+UserDataAccess.getSqlSelectIdOnly("*",crit));    
        	
        	IdVector userIds = UserDataAccess.selectIdOnly(conn, crit);
        	
        	log.info("userIds.size() = " + userIds.size());
        	//log.info("*****userIds = " + userIds);        	
			
        	if(userIds != null && !userIds.isEmpty()){
        		for (int i = 0; i < userIds.size(); i+=max) {
                    int end = (i + max > userIds.size()) ? userIds.size() : i + max;
        			log.info("i = " + i + "  end = " + end);
                    List subuserIds = userIds.subList(i,end);
                	log.info("subuserIds.size() = " + subuserIds.size());
                    
                	//log.info("*****subuserIds = " + subuserIds);
                	
	        		String ua1 = Utility.toSqlInClause("UA.USER_ID",subuserIds);
	        		String ua2 = Utility.toSqlInClause("UA2.USER_ID",subuserIds);
	        		
		            String subSql = "SELECT \n" +
		                    "  A.USER_ID\n" +
		                    "FROM \n" +
		                    "  (SELECT * FROM CLW_USER_ASSOC UA1 WHERE UA1.USER_ID \n" +
		                    "  IN (SELECT UA.USER_ID FROM CLW_USER_ASSOC UA WHERE UA.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.STORE +  "'\n" +
		                    "   AND "+ua1+"\n" +
		                    "   AND UA.BUS_ENTITY_ID IN  (" + Utility.toCommaSting(pSearchCriteria.getUserStoreIds()) + "))\n" +
		                    "     AND  UA1.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.STORE + "') A, \n" +
		                    "  (SELECT * FROM CLW_USER_ASSOC UA2 WHERE UA2.USER_ASSOC_CD = '" + RefCodeNames.USER_ASSOC_CD.STORE + "'  \n" +
		                    "   AND "+ua2+"\n" +
		                    "  AND  UA2.BUS_ENTITY_ID \n" +
		                    "  IN (" + Utility.toCommaSting(pSearchCriteria.getUserStoreIds()) + ")) B  \n" +
		                    "WHERE A.USER_ASSOC_ID = B.USER_ASSOC_ID (+)\n" +
		                    "GROUP BY A.USER_ID  \n" +
		                    "HAVING COUNT(*) = COUNT(B.USER_ID)";
		            crit = new DBCriteria(); //replace the old one as we have results of that in this
		            crit.addOneOf(UserDataAccess.USER_ID, subSql);
		            
		            //log.info("before calling UserDataAccess.select(conn, crit, max)");
		            
		            UserDataVector udvwork = UserDataAccess.select(conn, crit, max);
		            if(udv == null){
		            	udv = udvwork;
		            }else{
		            	udv.addAll(udvwork);
		            }
		            /*
		            if(udv.size() >= max){
		            	List tempList = udv.subList(0, max);
		            	udv = new UserDataVector();
		            	udv.addAll(tempList);
		            	break;
		            }
		            */
        		}
        	}else{
        		//no users matched the query so just return.
        		return new UserDataVector();
        	}
        }else{
	        //log.info("UserBean.getUserCollectionByCriteria SQL 2: "+UserDataAccess.getSqlSelectIdOnly("*",crit));    
	        udv = UserDataAccess.select(conn, crit, max);
        }
        log.info("udv2.size() = " + udv.size());
        //log.info("udv2 = " + udv);
        
        return udv;
    }
    
    public static DBCriteria convertToDBCriteria(UserSearchCriteriaData pSearchCriteria)
    throws SQLException {
        // will not return null vector unless exception thrown
    	IdVector udv = new IdVector();

        DBCriteria crit = new DBCriteria();

        if (0 < pSearchCriteria.getUserName().trim().length()) {
            String pName = pSearchCriteria.getUserName();
            int pMatch = pSearchCriteria.getUserNameMatch();
            switch (pMatch) {
            case User.NAME_BEGINS_WITH:
                crit.addLike(UserDataAccess.USER_NAME, pName + "%");
                break;
            case User.NAME_BEGINS_WITH_IGNORE_CASE:
                crit.addLikeIgnoreCase(UserDataAccess.USER_NAME, pName + "%");
                break;
            case User.NAME_CONTAINS:
                crit.addLike(UserDataAccess.USER_NAME, "%" + pName + "%");
                break;
            case User.NAME_CONTAINS_IGNORE_CASE:
                crit.addLikeIgnoreCase(UserDataAccess.USER_NAME, "%" + pName
                        + "%");
                break;
            case User.NAME_EXACT:
                crit.addEqualTo(UserDataAccess.USER_NAME, pName);
                break;
            case User.NAME_EXACT_IGNORE_CASE:
                crit.addEqualToIgnoreCase(UserDataAccess.USER_NAME, pName);
                break;
            default:
                throw new SQLException("bad match");
            }
        } else if (Utility.isSet(pSearchCriteria.getUserId())) {
            crit.addEqualTo(UserDataAccess.USER_ID, pSearchCriteria.getUserId()
                    .trim());
        }

        if (pSearchCriteria.getUserIds() != null && !pSearchCriteria.getUserIds().isEmpty()) {
            crit.addOneOf(UserDataAccess.USER_ID, pSearchCriteria.getUserIds());
        }
        
        if (Utility.isSet(pSearchCriteria.getFirstName())) {
            crit.addLikeIgnoreCase(UserDataAccess.FIRST_NAME, "%"
                    + pSearchCriteria.getFirstName().trim() + "%");
        }
        if (Utility.isSet(pSearchCriteria.getLastName())) {
            crit.addLikeIgnoreCase(UserDataAccess.LAST_NAME, "%"
                    + pSearchCriteria.getLastName().trim() + "%");
        }
        if(!pSearchCriteria.getIncludeInactiveFl()) {
          crit.addNotEqualTo(UserDataAccess.USER_STATUS_CD,
                  RefCodeNames.USER_STATUS_CD.INACTIVE);
        }
        if (Utility.isSet(pSearchCriteria.getUserTypeCd())) {
            if (pSearchCriteria.getUserTypeCd().equals(
                    RefCodeNames.USER_CLASS_CD.END_USER)) {
                StringBuffer buf = new StringBuffer();
                buf.append(UserDataAccess.USER_TYPE_CD);
                buf.append(" IN ( ");
                String utypes = " " + " '" + RefCodeNames.USER_TYPE_CD.CUSTOMER
                        + "'" + ",'" + RefCodeNames.USER_TYPE_CD.MSB + "'"
                        + ",'" + RefCodeNames.USER_TYPE_CD.REPORTING_USER + "'";
                buf.append(utypes + ")");
                crit.addCondition(buf.toString());
            } else {
                crit.addEqualToIgnoreCase(UserDataAccess.USER_TYPE_CD,
                        pSearchCriteria.getUserTypeCd().trim());
            }
        }
        if (pSearchCriteria.getUserTypes()!=null) {
          crit.addOneOf(UserDataAccess.USER_TYPE_CD, pSearchCriteria.getUserTypes());
        }
        if (0 < pSearchCriteria.getAccountId()) {
            DBCriteria subCrit = new DBCriteria();
            subCrit.addEqualTo(UserAssocDataAccess.BUS_ENTITY_ID,pSearchCriteria.getAccountId());
            crit.addOneOf(UserDataAccess.USER_ID,UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.USER_ID,subCrit));
        }
        if (Utility.isSet(pSearchCriteria.getCustomerSystemKey())){
            DBCriteria subCrit = new DBCriteria();
            subCrit.addEqualTo(PropertyDataAccess.CLW_VALUE,pSearchCriteria.getCustomerSystemKey());
            subCrit.addEqualTo(PropertyDataAccess.SHORT_DESC,RefCodeNames.PROPERTY_TYPE_CD.CUSTOMER_SYSTEM_KEY);
            crit.addOneOf(UserDataAccess.USER_ID,PropertyDataAccess.getSqlSelectIdOnly(PropertyDataAccess.USER_ID,subCrit));
        }
        if (pSearchCriteria.getStoreIds()!= null && !pSearchCriteria.getStoreIds().isEmpty()) {
        	//produces a SQL statement like the following:
        	//exists (SELECT USER_ID FROM CLW_USER_ASSOC cua WHERE cua.user_id = user_id and cua.BUS_ENTITY_ID IN (<STOREIDS>)) 
        	String cua = UserAssocDataAccess.CLW_USER_ASSOC;
        	String cu = UserDataAccess.CLW_USER;
        	crit.addCondition(" exists (select "+cua+"."+UserAssocDataAccess.USER_ID+" from "+cua+" WHERE "
        			+cua+"."+UserAssocDataAccess.USER_ID+" = "+cu+"."+UserDataAccess.USER_ID + " AND "
        			+cua+"."+UserAssocDataAccess.BUS_ENTITY_ID+" IN ("+Utility.toCommaSting(pSearchCriteria.getStoreIds())+"))");
        }

        if (pSearchCriteria.getAccountIds() != null && !pSearchCriteria.getAccountIds().isEmpty()) {
        	//produces a SQL statement like the following:
        	//AND exists (SELECT USER_ID FROM CLW_USER_ASSOC cuaa WHERE cuaa.user_id = user_id and cuaa.BUS_ENTITY_ID IN
        	//		   (<ACCOUNT_IDS>)
        	//		   AND cuaa.USER_ASSOC_CD = 'ACCOUNT') 
        	String cua = UserAssocDataAccess.CLW_USER_ASSOC;
        	String cu = UserDataAccess.CLW_USER;
        	crit.addCondition(" exists (select "+cua+"."+UserAssocDataAccess.USER_ID+" from "+cua+" WHERE "
        			+cua+"."+UserAssocDataAccess.USER_ID+" = "+cu+"."+UserDataAccess.USER_ID + " AND "
        			+cua+"."+UserAssocDataAccess.BUS_ENTITY_ID+" IN ("+Utility.toCommaSting(pSearchCriteria.getAccountIds())+") AND " 
        			+cua+"."+UserAssocDataAccess.USER_ASSOC_CD + "='"+RefCodeNames.USER_ASSOC_CD.ACCOUNT+"')");
        }

        if (pSearchCriteria.getSiteIds() != null && !pSearchCriteria.getSiteIds().isEmpty()) {
            DBCriteria subCrit = new DBCriteria();
            subCrit.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID, pSearchCriteria.getSiteIds());
            subCrit.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD, RefCodeNames.USER_ASSOC_CD.SITE);
            crit.addOneOf(UserDataAccess.USER_ID, UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.USER_ID, subCrit));
        }

        return crit;
    }
    

    
    /**
     *Associates specified bus entity ids to the supplied user.
     *@param conn			 a valid @see Connection object
     *@param pUserId         the user id
     *@param busEntityIds    the bus entity ids to associate to the user
     *@param busEntityTypeCd used to determine the association type, no actual checking is done
     */
    public static void addBusEntityAssociations(Connection conn, int pUserId, IdVector busEntityIds, String busEntityTypeCd, String userName)
    throws SQLException, RemoteException{
        
            
        String userAssocCode = translateBusEntityTypeCdToAssocCd(busEntityTypeCd);
        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(UserAssocDataAccess.USER_ID,pUserId);
        crit.addOneOf(UserAssocDataAccess.BUS_ENTITY_ID,busEntityIds);
        IdVector existingBusEntityIds = UserAssocDataAccess.selectIdOnly(conn, UserAssocDataAccess.BUS_ENTITY_ID, crit);
        IdVector newList = new IdVector();
        newList.addAll(busEntityIds);
        if (!existingBusEntityIds.isEmpty()){
        	newList.removeAll(existingBusEntityIds);
        }
        
        Iterator it = newList.iterator();
        while(it.hasNext()){
            Integer bid = (Integer) it.next();
            if(bid != null && bid.intValue() != 0){
                UserAssocData uad = UserAssocData.createValue();
                uad.setUserId(pUserId);
                uad.setAddBy(userName);
                uad.setModBy(userName);
                uad.setBusEntityId(bid.intValue());
                uad.setUserAssocCd(userAssocCode);
                UserAssocDataAccess.insert(conn, uad);
            }
        }
	
    }
    
    
    
    /**
     *Translates a bus entity type code into a user association code.  In practice these are generally identicale, but the logic
     *is centralized here.  Also checks that the bus entity code does actaully translate (users don't have a relationship to
     *manufacturers for example).
     *@throws RemoteException is the supplied bus entity type code does not map to a valid user type code
     */
    public static String translateBusEntityTypeCdToAssocCd(String busEntityTypeCd) throws RemoteException{
        String userAssocCode;
        if(RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT.equals(busEntityTypeCd)){
            userAssocCode = RefCodeNames.USER_ASSOC_CD.ACCOUNT;
        }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR.equals(busEntityTypeCd)){
            userAssocCode = RefCodeNames.USER_ASSOC_CD.DISTRIBUTOR;
        }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.SITE.equals(busEntityTypeCd)){
            userAssocCode = RefCodeNames.USER_ASSOC_CD.SITE;
        }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.STORE.equals(busEntityTypeCd)){
            userAssocCode = RefCodeNames.USER_ASSOC_CD.STORE;
        }else if(RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER.equals(busEntityTypeCd)){
            userAssocCode = RefCodeNames.USER_ASSOC_CD.SERVICE_PROVIDER;
        }else{
            throw new RemoteException("Bad bus entity type code to associate to user: "+busEntityTypeCd);
        }
        return userAssocCode;
    }
}
