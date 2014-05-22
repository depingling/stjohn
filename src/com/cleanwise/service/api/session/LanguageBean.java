package com.cleanwise.service.api.session;

/**
 * Title:        LanguageBean
 * Description:  Bean implementation for Language Stateless Session Bean
 * Purpose:      Provides access to the services for managing language information.
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendWise, Inc.
 * @author       Srinivas Chittibomma.
 */

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;

import javax.ejb.CreateException;

import com.cleanwise.service.api.dao.CountryDataAccess;
import com.cleanwise.service.api.dao.LanguageDataAccess;
import com.cleanwise.service.api.framework.UtilityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.value.CountryData;
import com.cleanwise.service.api.value.CountryDataVector;
import com.cleanwise.service.api.value.LanguageData;
import com.cleanwise.service.api.value.LanguageDataVector;
import com.cleanwise.view.utils.Constants;

/**
 * <code>Language</code> stateless session bean.
 */
public class LanguageBean extends UtilityServicesAPI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 420969571868415180L;
	
	private int MAX_LANGUAGES_TO_RETURN = 1000;
	private String ROW_COUNT = "ROWNUM";
	
	/**
     * Creates a new <code>LanguageBean</code> instance.
     *
     */
	public LanguageBean() {}
	
	/**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}
    
    /**
     * Gets all supported languages.
     * @return a <code>TemplateDataVector</code> of languages.
     * @throws RemoteException Required by EJB 1.0
     */
    public LanguageDataVector getAllLanguages() throws RemoteException {
    	LanguageDataVector languageDV;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbC = new DBCriteria();
            dbC.addLessOrEqual(ROW_COUNT, MAX_LANGUAGES_TO_RETURN);
            dbC.addOrderBy(LanguageDataAccess.SHORT_DESC);
            languageDV = LanguageDataAccess.select(conn, dbC);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
        return languageDV;
    }
    
    /**
     * Gets Language data by short description.
     * @param pShortDesc a <code>String</code> value.
     * @return languageData a <code>LanguageData</code> value.
     * @throws RemoteException Required by EJB 1.0
     */
    public LanguageData getLanguageByShortDesc(String pShortDesc) throws RemoteException {

    	LanguageData languageData = null;
    	LanguageDataVector languageDV;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbC = new DBCriteria();
            dbC.addContainsIgnoreCase(LanguageDataAccess.SHORT_DESC, pShortDesc);
            languageDV = LanguageDataAccess.select(conn, dbC);
            
            if (languageDV.size() > 0) {
            	languageData = (LanguageData)languageDV.get(0);
            } 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
        return languageData;
    }
    
    /**
     * Gets Language data by Language Code.
     * @param pShortDesc a <code>String</code> value.
     * @return languageData a <code>LanguageData</code> value.
     * @throws RemoteException Required by EJB 1.0
     */
    public LanguageData getLanguageByLanguageCode(String pLanguageCode) throws RemoteException {

    	LanguageData languageData = null;
    	LanguageDataVector languageDV;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbC = new DBCriteria();
            dbC.addContainsIgnoreCase(LanguageDataAccess.LANGUAGE_CODE, pLanguageCode);
            languageDV = LanguageDataAccess.select(conn, dbC);
            
            if (languageDV.size() > 0) {
            	languageData = (LanguageData)languageDV.get(0);
            } 
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
        return languageData;
    }
    
    public LanguageDataVector getSupportedLanguages() throws RemoteException {
    	LanguageDataVector languageDV;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbC = new DBCriteria();
            dbC.addEqualToIgnoreCase(LanguageDataAccess.SUPPORTED, "true");
            dbC.addOrderBy(LanguageDataAccess.SHORT_DESC);
            languageDV = LanguageDataAccess.select(conn, dbC);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
        return languageDV;
    }
    
    public LanguageDataVector getLanguagesInList(List pIds) throws Exception{
    	LanguageDataVector languageDV;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbC = new DBCriteria();
            dbC.addOneOf(LanguageDataAccess.LANGUAGE_ID, pIds);
            dbC.addOrderBy(LanguageDataAccess.TRANSLATED_NAME);
            languageDV = LanguageDataAccess.select(conn, dbC);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
        return languageDV;
    }
    
    public LanguageData getLanguageById(int pLangId) throws Exception{
    	LanguageData languageD = null;
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbC = new DBCriteria();
            dbC.addEqualTo(LanguageDataAccess.LANGUAGE_ID, pLangId);
            LanguageDataVector lDV = LanguageDataAccess.select(conn, dbC);
            if(lDV!=null){
            	 languageD = (LanguageData)lDV.get(0);
            }
           
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {

            }
        }
        return languageD;
    }

}
