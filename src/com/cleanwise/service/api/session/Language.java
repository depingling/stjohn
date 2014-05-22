package com.cleanwise.service.api.session;

/**
 * Title:        Language
 * Description:  Remote Interface for Language Stateless Session Bean
 * Purpose:      Provides access to the services for managing language information.
 * Copyright:    Copyright (c) 2011
 * Company:      eSpendWise, Inc.
 * @author       Srinivas Chittibomma.
 */

import java.rmi.RemoteException;
import java.util.List;

import com.cleanwise.service.api.value.LanguageData;
import com.cleanwise.service.api.value.LanguageDataVector;

/**
 * Remote interface for the <code>Language</code> stateless session bean.
 */
public interface Language extends javax.ejb.EJBObject {

   /**
    * Gets all supported languages.
    * @return a <code>TemplateDataVector</code> of languages.
    * @throws RemoteException Required by EJB 1.0
    */
	public LanguageDataVector getAllLanguages() throws RemoteException;
	
	 /**
     * Gets Language data by short description.
     * @param pShortDesc a <code>String</code> value.
     * @return languageData a <code>LanguageData</code> value.
     * @throws RemoteException Required by EJB 1.0
     */
    public LanguageData getLanguageByShortDesc(String pShortDesc) throws RemoteException;
    
    /**
     * Gets Language data by Language Code.
     * @param pShortDesc a <code>String</code> value.
     * @return languageData a <code>LanguageData</code> value.
     * @throws RemoteException Required by EJB 1.0
     */
    public LanguageData getLanguageByLanguageCode(String pLanguageCode) throws RemoteException;
    
    public LanguageDataVector getSupportedLanguages() throws RemoteException;
    
    public LanguageDataVector getLanguagesInList(List pIds) throws Exception;
    
    public LanguageData getLanguageById(int pLangId) throws Exception;
}
