package com.cleanwise.service.api.session;

/**
 * Title:        KeywordBean
 * Description:  Bean implementation for Keyword Stateless Session Bean
 * Purpose:      Provides access to keyword add and retrieval of information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.*;
import java.sql.*;
import java.util.Hashtable;

public class KeywordBean extends UtilityServicesAPI
{

  /**
   *
   */
   public void ejbCreate() throws CreateException, RemoteException {}

  /**
   * Gets the keywords for the domain and locale. Adds default domain data for the locale
   * @param pDomainName domain name
   * @param pLocale locale
   * @return Hashtable
   * @throws            RemoteException Required by EJB 1.0
   */
   /*
   public Hashtable getKeys(String pDomainName, String pLocale) 
   throws RemoteException
   {
     Connection conn = null;
     try {
        conn = getConnection();
        Hashtable keys = new Hashtable();
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(KeywordDataAccess.DOMAIN, pDomainName);
        dbc.addEqualTo(KeywordDataAccess.LOCALE, pLocale);
        KeywordDataVector keywordDV = KeywordDataAccess.select(conn,dbc);
        for(Iterator iter=keywordDV.iterator(); iter.hasNext();) {
          KeywordData kD = (KeywordData) iter.next();
          String name = kD.getKeyName();
          String value = kD.getKeyValue();
          if(value!=null) {
            keys.put(name, value);
          }
        }

        dbc = new DBCriteria();
        dbc.addIsNull(KeywordDataAccess.DOMAIN);
        dbc.addEqualTo(KeywordDataAccess.LOCALE, pLocale);
        keywordDV = KeywordDataAccess.select(conn,dbc);
        for(Iterator iter=keywordDV.iterator(); iter.hasNext();) {
          KeywordData kD = (KeywordData) iter.next();
          String name = kD.getKeyName();
          if(!keys.containsKey(name)) {
            String value = kD.getKeyValue();
            if(value!=null) {
              keys.put(name, value);
            }
          }
        }

        return keys;
     }catch (Exception e) {
       e.printStackTrace();
       throw new RemoteException(e.getMessage());  
     } finally {
       closeConnection(conn);
     }

    }
 */ 
  
  /**
   * Gets the keyword vector information values to be used by the request.
   * @param pKeyword the keyword.
   * @return KeywordDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public KeywordDataVector getKeywordValuesCollection(String pKeyword)
      throws RemoteException
  {
    return new KeywordDataVector();
  }

  /**
   * Gets the keyword vector information values to be used by the request.
   * @param pKeyword the keyword.
   * @return KeywordDataVector
   * @throws            RemoteException Required by EJB 1.0
   */
  public KeywordDataVector getKeywordValuesCollectionByType(String pKeyword)
      throws RemoteException
  {
    return new KeywordDataVector();
  }

  /**
   * Gets the keyword information values to be used by the request.
   * @param pKeyword the keyword.
   * @return KeywordData
   * @throws            RemoteException Required by EJB 1.0
   */
  public KeywordData getKeywordValue(String pKeyword)
      throws RemoteException
  {
    return KeywordData.createValue();
  }

  /**
   * Adds the keyword information values to be used by the request.
   * @param pKeyword  the keyword data.
   * @param request  the keyword request data.
   * @return new KeywordRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public KeywordRequestData addKeyword(KeywordData pKeyword,
                KeywordRequestData request)
      throws RemoteException
  {
    return new KeywordRequestData();
  }

  /**
   * Updates the keyword information values to be used by the request.
   * @param pUpdateKeywordData  the keyword data.
   * @param pKeyword the keyword.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateKeyword(KeywordData pUpdateKeywordData,
                String pKeyword)
      throws RemoteException
  {

  }      

  /**
   * Removes all messages for found store-locale pairs and adds provided messages if name is not empty
   * object with empty name could be used to remove all messages for the store-locale
   * @param pMessages set of MessageResourceData objects
   * @throws            RemoteException Required by EJB 1.0
   */
   public void loadMessages(MessageResourceDataVector pMessages) 
   throws RemoteException
   {
     Connection conn = null;
     try {
        conn = getConnection();
        HashMap storeLocaleHM = new HashMap();
        LinkedList storeNoLocaleLL = new LinkedList();
        boolean globalMessFl = false;
        for(Iterator iter=pMessages.iterator(); iter.hasNext();) {
          MessageResourceData mD = (MessageResourceData) iter.next();
          int storeId = mD.getBusEntityId();
          Integer storeIdI = new Integer(storeId);
          String locale = mD.getLocale();
          if(Utility.isSet(locale)) {
            LinkedList localeLL = (LinkedList) storeLocaleHM.get(storeIdI);
            if(localeLL==null) {
              localeLL = new LinkedList();
              storeLocaleHM.put(storeIdI,localeLL);
            }
            if(!localeLL.contains(locale)) {
              localeLL.add(locale);
            }
          } else {
            if(storeId!=0) {
            if(storeNoLocaleLL.contains(storeIdI)) {
              storeNoLocaleLL.add(storeIdI);
            }
            } else {
              globalMessFl = true;
            }
          }
        }
        
        if(globalMessFl) {
          DBCriteria dbc = new DBCriteria();
          dbc.addIsNull(MessageResourceDataAccess.BUS_ENTITY_ID);
          dbc.addIsNull(MessageResourceDataAccess.LOCALE);
          MessageResourceDataAccess.remove(conn,dbc);
        }
        if(storeNoLocaleLL.size()>0) {
          DBCriteria dbc = new DBCriteria();
          dbc.addOneOf(MessageResourceDataAccess.BUS_ENTITY_ID,storeNoLocaleLL);
          dbc.addIsNull(MessageResourceDataAccess.LOCALE);
          MessageResourceDataAccess.remove(conn,dbc);
        }
        Set keys = storeLocaleHM.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); ) {
          Integer storeIdI = (Integer) iter.next();
          LinkedList localeLL = (LinkedList) storeLocaleHM.get(storeIdI);
          if(storeIdI.intValue()==0) {
            DBCriteria dbc = new DBCriteria();
            dbc.addIsNull(MessageResourceDataAccess.BUS_ENTITY_ID);
            dbc.addOneOf(MessageResourceDataAccess.LOCALE,localeLL);
            MessageResourceDataAccess.remove(conn,dbc);
          } else {
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(MessageResourceDataAccess.BUS_ENTITY_ID,storeIdI.intValue());
            dbc.addOneOf(MessageResourceDataAccess.LOCALE,localeLL);
            MessageResourceDataAccess.remove(conn,dbc);
          }
        }
        for(Iterator iter=pMessages.iterator(); iter.hasNext();) {
          MessageResourceData mD = (MessageResourceData) iter.next();
          if(mD.getName()!=null && mD.getValue()!=null) {
            logDebug("KeywordBean inserting mD="+ mD);
            mD = MessageResourceDataAccess.insert(conn,mD);   
            logDebug("KeywordBean inserted mD="+ mD);
          }
        }
        
        
     }catch (Exception e) {
       e.printStackTrace();
       throw new RemoteException(e.getMessage());  
     } finally {
       closeConnection(conn);
     }

    }

}
