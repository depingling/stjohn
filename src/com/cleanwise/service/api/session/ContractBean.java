package com.cleanwise.service.api.session;

/**
 * Title:        ContractBean
 * Description:  Bean implementation for Contract Stateless Session Bean
 * Purpose:      Provides access to the methods for maintaining and retrieving contract information.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       CleanWise, Inc.
 */

import javax.ejb.*;
import java.sql.*;
import java.rmi.*;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.math.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import org.apache.log4j.Logger;

public class ContractBean extends CatalogServicesAPI {
  private static final Logger log = Logger.getLogger(ContractBean.class);

  /**
   *
   */
  public ContractBean() {}


  /**
   *
   */
  public void ejbCreate() throws CreateException, RemoteException {}

  /**
   *
   */
  public void ejbCreate(int pContractId) throws CreateException,
    RemoteException {}


  /**
   * Describe <code>getContract</code> method here.
   *
   * @param pContractId an <code>int</code> value
   * @return a <code>ContractData</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractData getContract(int pContractId) throws RemoteException,
    DataNotFoundException {

    ContractData contract = null;

    Connection conn = null;
    try {
      conn = getConnection();
      contract =
        ContractDataAccess.select(conn, pContractId);
    } catch (DataNotFoundException e) {
      //throw e;
    } catch (Exception e) {
      throw new RemoteException("getContract: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contract;
  }

  public ContractDescData getContractDesc(int pContractId) throws
  RemoteException, DataNotFoundException {
	  return getContractDesc(pContractId,0);
  }
  /**
   * Describe <code>getContractDesc</code> method here.
   *
   * @param pContractId an <code>int</code> value
   * @return a <code>ContractData</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractDescData getContractDesc(int pContractId, int pStoreId) throws
    RemoteException, DataNotFoundException {

    ContractDescData contractDesc = null;

    Connection conn = null;
    try {
      conn = getConnection();

      if(pStoreId>0){
	      DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);
	      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

	      CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(conn,dbc);
	      IdVector catalogIds = new IdVector();

	      for(int i=0; i<caDV.size(); i++){
	    	  CatalogAssocData caD = (CatalogAssocData)caDV.get(i);
	    	  catalogIds.add(new Integer(caD.getCatalogId()));
	      }

	      dbc = new DBCriteria();
	      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogIds);
	      CatalogDataVector catalogVec = CatalogDataAccess.select(conn, dbc);

	      dbc = new DBCriteria();
	      dbc.addOneOf(ContractDataAccess.CATALOG_ID, catalogIds);
	      dbc.addEqualTo(ContractDataAccess.CONTRACT_ID, pContractId);
	      ContractDataVector contractVec = ContractDataAccess.select(conn, dbc);

	      if(contractVec!=null && contractVec.size()>0){
	    	  ContractData contract = (ContractData)contractVec.get(0);
	    	  if (null != contract) {
	    		  CatalogData catalog = CatalogDataAccess.select(conn,
	    				  contract.getCatalogId());
	    		  contractDesc = ContractDescData.createValue();
	    		  contractDesc.setContractId(contract.getContractId());
	    		  contractDesc.setContractName(contract.getShortDesc());
	    		  contractDesc.setStatus(contract.getContractStatusCd());
	    		  contractDesc.setCatalogId(contract.getCatalogId());
	    		  contractDesc.setCatalogName(catalog.getShortDesc());
	    	  }
	      }
      }else{
    	  ContractData contract =
    	        ContractDataAccess.select(conn, pContractId);
    	  if (null != contract) {
    		  CatalogData catalog = CatalogDataAccess.select(conn,
    				  contract.getCatalogId());
    		  contractDesc = ContractDescData.createValue();
    		  contractDesc.setContractId(contract.getContractId());
    		  contractDesc.setContractName(contract.getShortDesc());
    		  contractDesc.setStatus(contract.getContractStatusCd());
    		  contractDesc.setCatalogId(contract.getCatalogId());
    		  contractDesc.setCatalogName(catalog.getShortDesc());
    	  }
      }
    } catch (DataNotFoundException e) {
      //throw e;
    } catch (Exception e) {
      throw new RemoteException("getContract: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractDesc;
  }


  /**
   * Get all contracts that match the given name.  The arguments specify
   * whether the name is interpreted as a pattern or exact match.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @return a <code>ContractDataVector</code> of matching contracts
   * @exception RemoteException if an error occurs
   */
  public ContractDataVector getContractByName(String pName, int pMatch) throws
    RemoteException {

    ContractDataVector contractVec = new ContractDataVector();

    Connection conn = null;
    try {
      conn = getConnection();

      DBCriteria crit = new DBCriteria();
      switch (pMatch) {
      case Contract.EXACT_MATCH:
        crit.addEqualTo(ContractDataAccess.SHORT_DESC, pName);
        break;
      case Contract.EXACT_MATCH_IGNORE_CASE:
        crit.addEqualToIgnoreCase(ContractDataAccess.SHORT_DESC,
                                  pName);
        break;
      case Contract.BEGINS_WITH:
        crit.addLike(ContractDataAccess.SHORT_DESC, pName + "%");
        break;
      case Contract.BEGINS_WITH_IGNORE_CASE:
        crit.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                               pName + "%");
        break;
      case Contract.CONTAINS:
        crit.addLike(ContractDataAccess.SHORT_DESC, "%" + pName + "%");
        break;
      case Contract.CONTAINS_IGNORE_CASE:
        crit.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                               "%" + pName + "%");
        break;
      default:
        throw new RemoteException("getContractByName: Bad match specification");
      }
      crit.addOrderBy(ContractDataAccess.SHORT_DESC);
      contractVec =
        ContractDataAccess.select(conn, crit);

    } catch (Exception e) {
      throw new RemoteException("getContractByName: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractVec;
  }


  public ContractDescDataVector getContractDescByName(String pName,int pMatch) throws
  RemoteException {
	  return getContractDescByName(pName,0, pMatch);
  }
  /**
   * Get all contract descs that match the given name.  The arguments specify
   * whether the name is interpreted as a pattern or exact match.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @return a <code>ContractDataVector</code> of matching contracts
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getContractDescByName(String pName,int pStoreId, int pMatch) throws
    RemoteException {

    ContractDescDataVector contractDescVec = new ContractDescDataVector();

    Connection conn = null;
    try {
      conn = getConnection();
      IdVector catalogIds = new IdVector();
      if(pStoreId>0){
	      DBCriteria dbc = new DBCriteria();
	      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);
	      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

	      CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(conn,dbc);

	      for(int i=0; i<caDV.size(); i++){
	    	  CatalogAssocData caD = (CatalogAssocData)caDV.get(i);
	    	  catalogIds.add(new Integer(caD.getCatalogId()));
	      }

      }
      DBCriteria crit = new DBCriteria();
      switch (pMatch) {
      case Contract.EXACT_MATCH:
        crit.addEqualTo(ContractDataAccess.SHORT_DESC, pName);
        break;
      case Contract.EXACT_MATCH_IGNORE_CASE:
        crit.addEqualToIgnoreCase(ContractDataAccess.SHORT_DESC,
                                  pName);
        break;
      case Contract.BEGINS_WITH:
        crit.addLike(ContractDataAccess.SHORT_DESC, pName + "%");
        break;
      case Contract.BEGINS_WITH_IGNORE_CASE:
        crit.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                               pName + "%");
        break;
      case Contract.CONTAINS:
        crit.addLike(ContractDataAccess.SHORT_DESC, "%" + pName + "%");
        break;
      case Contract.CONTAINS_IGNORE_CASE:
        crit.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                               "%" + pName + "%");
        break;
      default:
        throw new RemoteException("getContractByName: Bad match specification");
      }
      ContractDataVector contractVec = new ContractDataVector();
      if(catalogIds!=null && catalogIds.size()>0){
    	  crit.addOneOf(ContractDataAccess.CATALOG_ID, catalogIds);
      }
      contractVec =ContractDataAccess.select(conn, crit);

      IdVector ids =
        ContractDataAccess.selectIdOnly(conn,
                                        ContractDataAccess.CATALOG_ID, crit);
      crit = new DBCriteria();
      crit.addOneOf(CatalogDataAccess.CATALOG_ID, ids);
      CatalogDataVector catalogV =
        CatalogDataAccess.select(conn, crit);
      for (int i = 0; i < contractVec.size(); i++) {
        ContractDescData contractDesc = ContractDescData.createValue();
        ContractData contract = (ContractData) contractVec.get(i);
        contractDesc.setContractId(contract.getContractId());
        contractDesc.setContractName(contract.getShortDesc());
        contractDesc.setStatus(contract.getContractStatusCd());
        contractDesc.setCatalogId(contract.getCatalogId());
        for (int j = 0; j < catalogV.size(); j++) {
          CatalogData catalog = (CatalogData) catalogV.get(j);
          if (catalog.getCatalogId() == contract.getCatalogId()) {
            contractDesc.setCatalogName(catalog.getShortDesc());
            break;
          }
        }
        contractDescVec.add(contractDesc);
      }

    } catch (Exception e) {
      throw new RemoteException("getContractByName: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractDescVec;
  }


  /**
   * Get all the contracts.
   *
   * @return a <code>ContractDataVector</code> with all contracts.
   * @exception RemoteException if an error occurs
   */
  public ContractDataVector getAllContracts() throws RemoteException {

    ContractDataVector contractVec = new ContractDataVector();

    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();

      contractVec =
        ContractDataAccess.select(conn, crit);

    } catch (Exception e) {
      throw new RemoteException("getAllContracts: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractVec;
  }


  /**
   * Get all the contract descs.
   *
   * @return a <code>ContractDescDataVector</code> with all contracts.
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getAllContractDescs() throws RemoteException {

    ContractDescDataVector contractDescVec = new ContractDescDataVector();

    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();
      ContractDataVector contractVec =
        ContractDataAccess.select(conn, crit);
      IdVector ids =
        ContractDataAccess.selectIdOnly(conn,
                                        ContractDataAccess.CATALOG_ID, crit);
      crit = new DBCriteria();
      crit.addOneOf(CatalogDataAccess.CATALOG_ID, ids);
      CatalogDataVector catalogV =
        CatalogDataAccess.select(conn, crit);
      for (int i = 0; i < contractVec.size(); i++) {
        ContractDescData contractDesc = ContractDescData.createValue();
        ContractData contract = (ContractData) contractVec.get(i);
        contractDesc.setContractId(contract.getContractId());
        contractDesc.setContractName(contract.getShortDesc());
        contractDesc.setStatus(contract.getContractStatusCd());
        contractDesc.setCatalogId(contract.getCatalogId());
        for (int j = 0; j < catalogV.size(); j++) {
          CatalogData catalog = (CatalogData) catalogV.get(j);
          if (catalog.getCatalogId() == contract.getCatalogId()) {
            contractDesc.setCatalogName(catalog.getShortDesc());
            break;
          }
        }
        contractDescVec.add(contractDesc);
      }

    } catch (Exception e) {
      throw new RemoteException("getAllContractDescs: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractDescVec;
  }


  /**
   * Describe <code>getContractsByCatalog</code> method here.
   *
   * @param pCatalogId an <code>int</code> value
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractDataVector getContractsByCatalog(int pCatalogId) throws
    RemoteException {

    ContractDataVector contractVec = new ContractDataVector();

    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);
      crit.addOrderBy(ContractDataAccess.SHORT_DESC);
      contractVec = ContractDataAccess.select(conn, crit);

    } catch (Exception e) {
      throw processException(e);
    } finally {
      closeConnection(conn);
    }

    return contractVec;
  }


  /**
   * Describe <code>getContractDescsByCatalog</code> method here.
   *
   * @param pCatalogId an <code>int</code> value
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractDescDataVector getContractDescsByCatalog(int pCatalogId) throws
    RemoteException {

    ContractDescDataVector contractDescVec = new ContractDescDataVector();

    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();

      crit.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);
      crit.addOrderBy(ContractDataAccess.SHORT_DESC);
      ContractDataVector contractVec =
        ContractDataAccess.select(conn, crit);

      CatalogData catalog =
        CatalogDataAccess.select(conn, pCatalogId);
      for (int i = 0; i < contractVec.size(); i++) {
        ContractDescData contractDesc = ContractDescData.createValue();
        ContractData contract = (ContractData) contractVec.get(i);
        contractDesc.setContractId(contract.getContractId());
        contractDesc.setContractName(contract.getShortDesc());
        contractDesc.setStatus(contract.getContractStatusCd());
        contractDesc.setCatalogId(contract.getCatalogId());
        contractDesc.setCatalogName(catalog.getShortDesc());
        contractDescVec.add(contractDesc);
      }

    } catch (Exception e) {
      throw new RemoteException("getContractDescsByCatalog: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractDescVec;
  }


  /**
   * Describe <code>getContractDescsByCatalog</code> method here.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @param pCatalogId an <code>int</code> value
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   * @exception DataNotFoundException if an error occurs
   */
  public ContractDescDataVector getContractDescsByCatalog(String pName,
    int pMatch,
    int pCatalogId) throws RemoteException {

    ContractDescDataVector contractDescVec = new ContractDescDataVector();

    Connection conn = null;
    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();

      crit.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);
      crit.addOrderBy(ContractDataAccess.SHORT_DESC);
      switch (pMatch) {
      case Contract.EXACT_MATCH:
        crit.addEqualTo(ContractDataAccess.SHORT_DESC, pName);
        break;
      case Contract.EXACT_MATCH_IGNORE_CASE:
        crit.addEqualToIgnoreCase(ContractDataAccess.SHORT_DESC,
                                  pName);
        break;
      case Contract.BEGINS_WITH:
        crit.addLike(ContractDataAccess.SHORT_DESC, pName + "%");
        break;
      case Contract.BEGINS_WITH_IGNORE_CASE:
        crit.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                               pName + "%");
        break;
      case Contract.CONTAINS:
        crit.addLike(ContractDataAccess.SHORT_DESC, "%" + pName + "%");
        break;
      case Contract.CONTAINS_IGNORE_CASE:
        crit.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                               "%" + pName + "%");
        break;
      default:
        throw new RemoteException(
          "getContractByCatalog: Bad match specification");
      }

      ContractDataVector contractVec =
        ContractDataAccess.select(conn, crit);

      CatalogData catalog =
        CatalogDataAccess.select(conn, pCatalogId);
      for (int i = 0; i < contractVec.size(); i++) {
        ContractDescData contractDesc = ContractDescData.createValue();
        ContractData contract = (ContractData) contractVec.get(i);
        contractDesc.setContractId(contract.getContractId());
        contractDesc.setContractName(contract.getShortDesc());
        contractDesc.setStatus(contract.getContractStatusCd());
        contractDesc.setCatalogId(contract.getCatalogId());
        contractDesc.setCatalogName(catalog.getShortDesc());
        contractDescVec.add(contractDesc);
      }

    } catch (Exception e) {
      throw new RemoteException("getContractDescsByCatalog: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractDescVec;
  }


  /**
   * Gets all contracts for a given account.
   *
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDataVector getContractsByAccount(int pAccountId) throws
    RemoteException {

    ContractDataVector contractVec = new ContractDataVector();

    Connection conn = null;
    try {
      conn = getConnection();

      // first get all the account catalogs (ideally we just get the
      // ids, but the CatalogInformation EJB has no such method yet)
      APIAccess factory = new APIAccess();
      CatalogInformation catalogAPI = factory.getCatalogInformationAPI();
      CatalogDataVector catalogVec =
        catalogAPI.getCatalogsByAccountId(pAccountId);

      IdVector catalogIds = new IdVector();
      Iterator catalogI = catalogVec.iterator();
      while (catalogI.hasNext()) {
        CatalogData catalog = (CatalogData) catalogI.next();
        if(RefCodeNames.CATALOG_TYPE_CD.SHOPPING.equals(catalog.getCatalogTypeCd())){
            catalogIds.add(new Integer(catalog.getCatalogId()));
        }
      }
      if(catalogIds.isEmpty()){
          return contractVec;
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ContractDataAccess.CATALOG_ID, catalogIds);
//123      dbc.addEqualTo(ContractDataAccess.CONTRACT_TYPE_CD, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
      dbc.addOrderBy(ContractDataAccess.SHORT_DESC);
      contractVec = ContractDataAccess.select(conn, dbc);
    } catch (Exception e) {
      throw new RemoteException("getContractByAccount: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractVec;
  }


  /**
   * Gets  contract for a given account and contractId.
   *
   * @param pContractId a contract id
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractData getContractByAccount(int pContractId, int pAccountId) throws
    RemoteException {

    ContractData contractD = null;
    ContractDataVector contractVec = new ContractDataVector();

    Connection conn = null;
    try {
      conn = getConnection();

      // first get all the account catalogs (ideally we just get the
      // ids, but the CatalogInformation EJB has no such method yet)
      APIAccess factory = new APIAccess();
      CatalogInformation catalogAPI = factory.getCatalogInformationAPI();
      CatalogDataVector catalogVec =
        catalogAPI.getCatalogsByAccountId(pAccountId);

      IdVector catalogIds = new IdVector();
      Iterator catalogI = catalogVec.iterator();
      while (catalogI.hasNext()) {
        CatalogData catalog = (CatalogData) catalogI.next();
        catalogIds.add(new Integer(catalog.getCatalogId()));
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ContractDataAccess.CATALOG_ID, catalogIds);
      dbc.addEqualTo(ContractDataAccess.CONTRACT_ID, pContractId);
      contractVec = ContractDataAccess.select(conn, dbc);
      if (null != contractVec && 0 < contractVec.size()) {
        contractD = (ContractData) contractVec.get(0);
      }

    } catch (Exception e) {
      throw new RemoteException("getContractByAccount: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractD;
  }


  /**
   * Gets all contract descs for a given account.
   *
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getContractDescsByAccount(int pAccountId) throws
    RemoteException {

    ContractDescDataVector contractDescVec = new ContractDescDataVector();

    Connection conn = null;
    try {
      conn = getConnection();

      // first get all the account catalogs (ideally we just get the
      // ids, but the CatalogInformation EJB has no such method yet)
      APIAccess factory = new APIAccess();
      CatalogInformation catalogAPI = factory.getCatalogInformationAPI();
      CatalogDataVector catalogVec =
        catalogAPI.getCatalogsByAccountId(pAccountId);

      IdVector catalogIds = new IdVector();
      Iterator catalogI = catalogVec.iterator();
      while (catalogI.hasNext()) {
        CatalogData catalog = (CatalogData) catalogI.next();
        catalogIds.add(new Integer(catalog.getCatalogId()));
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ContractDataAccess.CATALOG_ID, catalogIds);
      dbc.addOrderBy(ContractDataAccess.SHORT_DESC);
      ContractDataVector contractVec = ContractDataAccess.select(conn, dbc);

      for (int i = 0; i < contractVec.size(); i++) {
        ContractDescData contractDesc = ContractDescData.createValue();
        ContractData contract = (ContractData) contractVec.get(i);
        contractDesc.setContractId(contract.getContractId());
        contractDesc.setContractName(contract.getShortDesc());
        contractDesc.setStatus(contract.getContractStatusCd());
        contractDesc.setCatalogId(contract.getCatalogId());
        for (int j = 0; j < catalogVec.size(); j++) {
          CatalogData catalog = (CatalogData) catalogVec.get(j);
          if (catalog.getCatalogId() == contract.getCatalogId()) {
            contractDesc.setCatalogName(catalog.getShortDesc());
            break;
          }
        }
        contractDescVec.add(contractDesc);
      }

    } catch (Exception e) {
      throw new RemoteException("getContractsDescByAccount: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractDescVec;
  }

  /**
   * Gets all contract descs for a given store.
   *
   * @param pStoreId a store id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getContractDescsByStore(int pStoreId) throws
    RemoteException {

    ContractDescDataVector contractDescVec = new ContractDescDataVector();

    Connection conn = null;
    try {
      conn = getConnection();

      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pStoreId);
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_STORE);

      CatalogAssocDataVector caDV = CatalogAssocDataAccess.select(conn,dbc);
      IdVector catalogIds = new IdVector();

      for(int i=0; i<caDV.size(); i++){
    	  CatalogAssocData caD = (CatalogAssocData)caDV.get(i);
    	  catalogIds.add(new Integer(caD.getCatalogId()));
      }

      dbc = new DBCriteria();
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogIds);
      CatalogDataVector catalogVec = CatalogDataAccess.select(conn, dbc);

      dbc = new DBCriteria();
      dbc.addOneOf(ContractDataAccess.CATALOG_ID, catalogIds);
      dbc.addOrderBy(ContractDataAccess.SHORT_DESC);
      ContractDataVector contractVec = ContractDataAccess.select(conn, dbc);

      for (int i = 0; i < contractVec.size(); i++) {
        ContractDescData contractDesc = ContractDescData.createValue();
        ContractData contract = (ContractData) contractVec.get(i);
        contractDesc.setContractId(contract.getContractId());
        contractDesc.setContractName(contract.getShortDesc());
        contractDesc.setStatus(contract.getContractStatusCd());
        contractDesc.setCatalogId(contract.getCatalogId());
        for (int j = 0; j < catalogVec.size(); j++) {
          CatalogData catalog = (CatalogData) catalogVec.get(j);
          if (catalog.getCatalogId() == contract.getCatalogId()) {
            contractDesc.setCatalogName(catalog.getShortDesc());
            break;
          }
        }
        contractDescVec.add(contractDesc);
      }

    } catch (Exception e) {
      throw new RemoteException("getContractsDescByAccount: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractDescVec;
  }


  /**
   * Gets contracts by name for a given account.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDataVector getContractsByAccount(String pName, int pMatch,
                                                  int pAccountId) throws
    RemoteException {

    ContractDataVector contractVec = new ContractDataVector();

    Connection conn = null;
    try {
      conn = getConnection();

      // first get all the account catalogs (ideally we just get the
      // ids, but the CatalogInformation EJB has no such method yet)
      APIAccess factory = new APIAccess();
      CatalogInformation catalogAPI = factory.getCatalogInformationAPI();
      CatalogDataVector catalogVec =
        catalogAPI.getCatalogsByAccountId(pAccountId);

      IdVector catalogIds = new IdVector();
      Iterator catalogI = catalogVec.iterator();
      while (catalogI.hasNext()) {
        CatalogData catalog = (CatalogData) catalogI.next();
        catalogIds.add(new Integer(catalog.getCatalogId()));
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ContractDataAccess.CATALOG_ID, catalogIds);
      switch (pMatch) {
      case Contract.EXACT_MATCH:
        dbc.addEqualTo(ContractDataAccess.SHORT_DESC, pName);
        break;
      case Contract.EXACT_MATCH_IGNORE_CASE:
        dbc.addEqualToIgnoreCase(ContractDataAccess.SHORT_DESC,
                                 pName);
        break;
      case Contract.BEGINS_WITH:
        dbc.addLike(ContractDataAccess.SHORT_DESC, pName + "%");
        break;
      case Contract.BEGINS_WITH_IGNORE_CASE:
        dbc.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                              pName + "%");
        break;
      case Contract.CONTAINS:
        dbc.addLike(ContractDataAccess.SHORT_DESC, "%" + pName + "%");
        break;
      case Contract.CONTAINS_IGNORE_CASE:
        dbc.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                              "%" + pName + "%");
        break;
      default:
        throw new RemoteException(
          "getContractByAccount: Bad match specification");
      }
      dbc.addOrderBy(ContractDataAccess.SHORT_DESC);
      contractVec = ContractDataAccess.select(conn, dbc);
    } catch (Exception e) {
      throw new RemoteException("getContractByAccount: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractVec;
  }


  /**
   * Gets contract descs by name for a given account.
   *
   * @param pName a <code>String</code> value with contract name or pattern
   * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
   *        BEGINS_WITH_IGNORE_CASE
   * @param pAccountId an account id
   * @return a <code>ContractDataVector</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractDescDataVector getContractDescsByAccount(String pName,
    int pMatch,
    int pAccountId) throws RemoteException {

    ContractDescDataVector contractDescVec = new ContractDescDataVector();

    Connection conn = null;
    try {
      conn = getConnection();

      // first get all the account catalogs (ideally we just get the
      // ids, but the CatalogInformation EJB has no such method yet)
      APIAccess factory = new APIAccess();
      CatalogInformation catalogAPI = factory.getCatalogInformationAPI();
      CatalogDataVector catalogVec =
        catalogAPI.getCatalogsByAccountId(pAccountId);

      IdVector catalogIds = new IdVector();
      Iterator catalogI = catalogVec.iterator();
      while (catalogI.hasNext()) {
        CatalogData catalog = (CatalogData) catalogI.next();
        catalogIds.add(new Integer(catalog.getCatalogId()));
      }

      DBCriteria dbc = new DBCriteria();
      dbc.addOneOf(ContractDataAccess.CATALOG_ID, catalogIds);
      switch (pMatch) {
      case Contract.EXACT_MATCH:
        dbc.addEqualTo(ContractDataAccess.SHORT_DESC, pName);
        break;
      case Contract.EXACT_MATCH_IGNORE_CASE:
        dbc.addEqualToIgnoreCase(ContractDataAccess.SHORT_DESC,
                                 pName);
        break;
      case Contract.BEGINS_WITH:
        dbc.addLike(ContractDataAccess.SHORT_DESC, pName + "%");
        break;
      case Contract.BEGINS_WITH_IGNORE_CASE:
        dbc.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                              pName + "%");
        break;
      case Contract.CONTAINS:
        dbc.addLike(ContractDataAccess.SHORT_DESC, "%" + pName + "%");
        break;
      case Contract.CONTAINS_IGNORE_CASE:
        dbc.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
                              "%" + pName + "%");
        break;
      default:
        throw new RemoteException(
          "getContractByAccount: Bad match specification");
      }
      dbc.addOrderBy(ContractDataAccess.SHORT_DESC);
      ContractDataVector contractVec = ContractDataAccess.select(conn, dbc);

      for (int i = 0; i < contractVec.size(); i++) {
        ContractDescData contractDesc = ContractDescData.createValue();
        ContractData contract = (ContractData) contractVec.get(i);
        contractDesc.setContractId(contract.getContractId());
        contractDesc.setContractName(contract.getShortDesc());
        contractDesc.setStatus(contract.getContractStatusCd());
        contractDesc.setCatalogId(contract.getCatalogId());
        for (int j = 0; j < catalogVec.size(); j++) {
          CatalogData catalog = (CatalogData) catalogVec.get(j);
          if (catalog.getCatalogId() == contract.getCatalogId()) {
            contractDesc.setCatalogName(catalog.getShortDesc());
            break;
          }
        }
        contractDescVec.add(contractDesc);
      }

    } catch (Exception e) {
      throw new RemoteException("getContractDescsByAccount: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return contractDescVec;
  }


  /**
   * Describe <code>addContract</code> method here.
   *
   * @param pContractData a <code>ContractData</code> value
   * @return a <code>ContractData</code> value
   * @exception RemoteException if an error occurs
   */
  public ContractData addContract(ContractData pContractData) throws
    RemoteException {
    return updateContract(pContractData);
  }

  /**
   * Updates the contract information values to be used by the request.
   * @param pContractData  the ContractData contract data.
   * @return a <code>ContractData</code> value
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractData updateContract(ContractData pContractData) throws
    RemoteException {
    Connection conn = null;

    try {
      conn = getConnection();

      ContractData contract = pContractData;
      if (contract.isDirty()) {
        if (contract.getContractId() == 0) {
          ContractDataAccess.insert(conn, contract);
        } else {
          ContractDataAccess.update(conn, contract);
        }
      }
      int contractId = pContractData.getContractId();

    } catch (Exception e) {
      throw new RemoteException("updateContract: " + e.getMessage());
    } finally {
      try {
        if (conn != null) conn.close();
      } catch (Exception ex) {}
    }

    return pContractData;
  }

  /**
   * <code>removeContract</code> may be used to remove an 'unused' contract.
   * An unused contract is a contract with no database references other than
   * the default primary address, phone numbers, email addresses and
   * properties.  Attempting to remove a contract that is used will
   * result in a failure initially reported as a SQLException and
   * consequently caught and rethrown as a RemoteException.
   *
   * @param pContractData a <code>ContractData</code> value
   * @return none
   * @exception RemoteException if an error occurs
   */
  public void removeContract(ContractData pContractData) throws RemoteException {
    Connection conn = null;
    try {
      conn = getConnection();
      pContractData.setContractStatusCd
        (RefCodeNames.CONTRACT_STATUS_CD.DELETED);
      // Update the contract to a deleted status.
      ContractDataAccess.update(conn, pContractData);
    } catch (Exception e) {
      throw new RemoteException("removeContract: " +
                                pContractData +
                                " error: " + e);
    } finally {
      closeConnection(conn);
    }
  }


  /**
   *  Description of the Method
   *
   *@param  pContractData        Description of Parameter
   *@return                      Description of the Returned Value
   *@exception  RemoteException  Description of Exception
   */
  public ContractData createFromCatalog(ContractData
                                        pContractData) throws RemoteException {
    ContractData contract = ContractData.createValue();

    // Get the items in the catalog.
    IdVector itemids;
    try {
      APIAccess apiAccess = getAPIAccess();
      CatalogInformation cati =
        apiAccess.getCatalogInformationAPI();
      // Get all the items.
      itemids = cati.
                searchCatalogProducts(pContractData.getCatalogId());
    } catch (Exception e) {
      String msg = "createFromCatalog: " + e.getMessage();
      logError(msg);
      throw new RemoteException(msg);
    }

    contract = addContract(pContractData);
    int logid = contract.getContractId();
    // Add the items.
    ContractItemDataVector cidv =
      new ContractItemDataVector();
    for (int i = 0; i < itemids.size(); i++) {
      Integer itemid = (Integer) itemids.get(i);
      ContractItemData cidata =
        addItem(logid, pContractData.getCatalogId(),
                itemid.intValue(), pContractData.getAddBy());
      cidv.add(cidata);
    }
    return contract;
  }


  /**
   *  Description of the Method
   *
   *@param  pContractData        Description of Parameter
   *@param  pParentContractId    Description of Parameter
   *@return                      Description of the Returned Value
   *@exception  RemoteException  Description of Exception
   */
  public ContractData createFromContract(ContractData pContractData,
                                         int pParentContractId) throws
    RemoteException {

    ContractData contract = ContractData.createValue();

    // Get the items in the contract.
    ContractItemDataVector itemdv;
    try {
      // Get all the items.
      itemdv = getItems(pParentContractId);
    } catch (Exception e) {
      String msg = "createFromCatalog: " + e.getMessage();
      logError(msg);
      throw new RemoteException(msg);
    }

    contract = addContract(pContractData);
    int logid = contract.getContractId();
    // Add the items.
    ContractItemDataVector cidv =
      new ContractItemDataVector();
    for (int i = 0; i < itemdv.size(); i++) {
      ContractItemData itemd = (ContractItemData) itemdv.get(i);
      ContractItemData cidata =
        copyItem(logid, itemd);
      cidv.add(cidata);
    }
    return contract;
  }


  public ContractItemData addItem(int pContractId,
                                  int pItemId) throws RemoteException {
    Connection conn = null;
    ContractItemData cidata =
      ContractItemData.createValue();
    try {

      conn = getConnection();

      // Check to see if the item is already there.
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      crit.addEqualTo(ContractItemDataAccess.ITEM_ID,
                      pItemId);

      ContractItemDataVector civ =
        ContractItemDataAccess.select(conn, crit);
      if (civ.size() > 0) {
        cidata = (ContractItemData) civ.get(0);
      } else {
        cidata.setContractId(pContractId);
        cidata.setItemId(pItemId);
        cidata.setCurrencyCd("USD");

        ContractItemDataAccess.insert(conn, cidata);
      }
    } catch (Exception e) {
      throw new RemoteException("addItem: " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return cidata;
  }


  public ContractItemData addItem(int pContractId,
                                  int pCatalogId,
                                  int pItemId, String pUder) throws RemoteException {
    Connection conn = null;
    ContractItemData cidata =
      ContractItemData.createValue();
    try {

      conn = getConnection();

      DBCriteria dbC1 = new DBCriteria();
      dbC1.addEqualTo(ItemMetaDataAccess.ITEM_ID, pItemId);
      dbC1.addEqualTo(ItemMetaDataAccess.NAME_VALUE, "LIST_PRICE");
      ItemMetaDataVector itemMetaDV = ItemMetaDataAccess.select(conn, dbC1);
      BigDecimal listPrice = null;
      if (itemMetaDV.size() > 0){
    	  ItemMetaData imD = (ItemMetaData) itemMetaDV.get(0);
    	  listPrice = new BigDecimal(imD.getValue());
      }else{
    	  listPrice = new BigDecimal(0);
      }

      //check currency locale for number of digits allowed after decimal point
      ContractData contractD = ContractDataAccess.select(conn, pContractId);
      String locale = contractD.getLocaleCd();

      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(CurrencyDataAccess.LOCALE, locale);
      CurrencyDataVector currDV = CurrencyDataAccess.select(conn, crit);
      int decimalPlaces = 0;
      List<String> errorL = new ArrayList<String>();
      if(currDV!=null){
    	  CurrencyData currD = (CurrencyData)currDV.get(0);
    	  decimalPlaces = currD.getDecimals();
      }

      if(listPrice.compareTo(new BigDecimal(0))>0){

    	  if(listPrice.scale()>decimalPlaces){
    		  String errorMess = "The listPrice for itemId "+pItemId+" has too many decimal points.  " +
    		  "It can only have "+decimalPlaces+" decimal points for this currency";
    		  if (!errorL.contains(errorMess)) {
    			  errorL.add(errorMess);
        	  }
    	  }
      }
      if(errorL.size()>0){
    	  throw new Exception(makeErrorString(errorL));
      }

      // Check to see if the item is already there.
      crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      crit.addEqualTo(ContractItemDataAccess.ITEM_ID,
                      pItemId);

      ContractItemDataVector civ =
        ContractItemDataAccess.select(conn, crit);
      if (civ.size() > 0) {
        cidata = (ContractItemData) civ.get(0);
      } else {
        cidata.setContractId(pContractId);
        cidata.setItemId(pItemId);
        cidata.setCurrencyCd("USD");
        cidata.setAmount(listPrice);
        cidata.setDistCost(listPrice);
        cidata.setAddBy(pUder);
        cidata.setModBy(pUder);
        ContractItemDataAccess.insert(conn, cidata);
      }
    } catch (Exception e) {
      throw new RemoteException("addItem: " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return cidata;
  }

  private String makeErrorString(List pErrors) {
	  String errorMess = "^clw^";
	  for (int ii = 0; ii < pErrors.size(); ii++) {
		  if (ii != 0) errorMess += "; ";
		  errorMess += pErrors.get(ii);
	  }
	  errorMess += "^clw^";
	  return errorMess;
  }

  public ContractItemDataVector getItems(int pContractId) throws
    RemoteException, DataNotFoundException {
    Connection conn = null;
    ContractItemDataVector cidv;
    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      cidv = ContractItemDataAccess.select(conn, crit);

    } catch (Exception e) {
      throw new RemoteException("getItem: " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return cidv;
  }


  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId               Description of Parameter
   *@param  pItemId                   Description of Parameter
   *@return                            The ContractItem value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDataVector getContractItemCollectionByItem(int pContractId,
    int pItemId) throws RemoteException, DataNotFoundException {
    Connection conn = null;
    ContractItemDataVector cidv = new ContractItemDataVector();

    if (0 == pContractId || 0 == pItemId) {
      return cidv;
    }

    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      crit.addEqualTo(ContractItemDataAccess.ITEM_ID,
                      pItemId);

      cidv = ContractItemDataAccess.select(conn, crit);
    } catch (Exception e) {
      throw new RemoteException("getContractItemCollectionByItemItem: " +
                                e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return cidv;
  }


  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId               Description of Parameter
   *@param  pItemIdCollection         Description of Parameter
   *@return                            The ContractItem value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDataVector getContractItemCollectionByItem(int pContractId,
    IdVector pItemIdCollection) throws RemoteException, DataNotFoundException {
    Connection conn = null;
    ContractItemDataVector cidv = new ContractItemDataVector();

    if (0 == pContractId || 0 == pItemIdCollection.size()) {
      return cidv;
    }

    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      crit.addOneOf(ContractItemDataAccess.ITEM_ID,
                    pItemIdCollection);

      cidv = ContractItemDataAccess.select(conn, crit);
    } catch (Exception e) {
      throw new RemoteException("getContractItemCollectionByItemItem(1) : " +
                                e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return cidv;
  }


  /**
   *  Gets the ItemIds attribute of the ContractBean object
   *
   *@param  pContractId               Description of Parameter
   *@return                            The ItemIds value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public IdVector getItemIdCollectionByContract(int pContractId) throws
    RemoteException, DataNotFoundException {
    Connection conn = null;
    IdVector cidv = new IdVector();

    if (0 == pContractId) {
      return cidv;
    }

    try {
      conn = getConnection();
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);

      cidv = ContractItemDataAccess.selectIdOnly(conn,
                                                 ContractItemDataAccess.ITEM_ID,
                                                 crit);
    } catch (Exception e) {
      throw new RemoteException("getItemIdCollectionByContract() : " +
                                e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return cidv;
  }


  /**
   *  Gets the Item's distributor Id attribute of the ContractBean object
   *
   *@param  pContractId                Description of Parameter
   *@param  pItemId                    Description of Parameter
   *@return                            The distributor Id value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public int getItemDistributorId(int pContractId, int pItemId) throws
    RemoteException, DataNotFoundException {
    Connection conn = null;
    int distId = 0;

    if (0 == pContractId) {
      return distId;
    }

    try {
      conn = getConnection();

      // make sure the contract exist
      ContractData contractD = ContractDataAccess.select(conn,
        pContractId);
      if (null == contractD) {
        return distId;
      }

      // make sure the item is in the contract
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      crit.addEqualTo(ContractItemDataAccess.ITEM_ID,
                      pItemId);
      IdVector cidv = ContractItemDataAccess.selectIdOnly(conn,
        ContractItemDataAccess.ITEM_ID, crit);
      if (null == cidv || 0 == cidv.size()) {
        return distId;
      }

      crit = new DBCriteria();
      crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,
                      contractD.getCatalogId());
      crit.addEqualTo(CatalogStructureDataAccess.ITEM_ID,
                      pItemId);
      crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                      RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      IdVector didv = CatalogStructureDataAccess.selectIdOnly(conn,
        CatalogStructureDataAccess.BUS_ENTITY_ID, crit);
      if (null != didv && 0 < didv.size()) {
        distId = ((Integer) didv.get(0)).intValue();
      }
    } catch (Exception e) {
      throw new RemoteException("getItemDistributorId() : " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return distId;
  }


    /**
     * Gets the service's distributor Id attribute of the ContractBean object
     *
     * @param pContractId Description of Parameter
     * @param pServiceId     Description of Parameter
     * @return The distributor Id value
     * @throws RemoteException       Description of Exception
     * @throws DataNotFoundException Description of Exception
     */
    public int getServiceDistributorId(int pContractId, int pItemId) throws  RemoteException, DataNotFoundException {
        Connection conn = null;
        int distId = 0;

        if (0 == pContractId) {
            return distId;
        }
        try {
            conn = getConnection();
            // make sure the contract exist
            ContractData contractD = ContractDataAccess.select(conn,pContractId);
            if (null == contractD) {
                return distId;
            }
            // make sure the item is in the contract
            DBCriteria crit = new DBCriteria();
            crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,pContractId);
            crit.addEqualTo(ContractItemDataAccess.ITEM_ID,pItemId);
            IdVector cidv = ContractItemDataAccess.selectIdOnly(conn,
                    ContractItemDataAccess.ITEM_ID, crit);
            if (null == cidv || 0 == cidv.size()) {
                return distId;
            }
            crit = new DBCriteria();
            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,contractD.getCatalogId());
            crit.addEqualTo(CatalogStructureDataAccess.ITEM_ID,pItemId);
            crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_SERVICE);
            IdVector didv = CatalogStructureDataAccess.selectIdOnly(conn,CatalogStructureDataAccess.BUS_ENTITY_ID, crit);
            if (null != didv && 0 < didv.size()) {
                distId = ((Integer) didv.get(0)).intValue();
            }
        } catch (Exception e) {
            throw new RemoteException("getServiceDistributorId() : " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
            }
        }

        return distId;
  }


  private int locateItemSku(int pItemId, ItemDataVector idv) {
    if (null == idv || 0 == idv.size())return 0;
    for (int i = 0; i < idv.size(); i++) {
      ItemData thisitem = (ItemData) idv.get(i);
      if (thisitem.getItemId() == pItemId) {
        return thisitem.getSkuNum();
      }
    }
    return 0;
  }

  /**
   *  Gets the Item's distributor Id attribute of the ContractBean object
   *
   *@param  pContractId                Description of Parameter
   *@return                            The distributor Id value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemPriceViewVector getItemDistributorIdCollection(int
    pContractId) throws RemoteException, DataNotFoundException {
    Connection conn = null;
    ContractItemPriceViewVector cItemPriceViewV = new
                                                  ContractItemPriceViewVector();

    if (0 == pContractId) {
      return cItemPriceViewV;
    }

    try {
      conn = getConnection();

      // make sure the contract exist
      ContractData contractD = ContractDataAccess.select(conn,
        pContractId);
      if (null == contractD) {
        return cItemPriceViewV;
      }

      // make sure the item is in the contract
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      IdVector cidv = ContractItemDataAccess.selectIdOnly(conn,
        ContractItemDataAccess.ITEM_ID, crit);

      String baseCond =
        " in  ( SELECT DISTINCT "
        + ContractItemDataAccess.ITEM_ID + " FROM "
        + ContractItemDataAccess.CLW_CONTRACT_ITEM
        + " WHERE "
        + ContractItemDataAccess.CONTRACT_ID
        + " = " + pContractId + " ) ";

      String itemsCond = ItemDataAccess.ITEM_ID + baseCond,
                         catStructCond = CatalogStructureDataAccess.ITEM_ID +
                                         baseCond;

      DBCriteria itemscrit = new DBCriteria();
      itemscrit.addCondition(itemsCond);
      ItemDataVector idv =
        ItemDataAccess.select(conn, itemscrit);
      ContractItemDataVector contractItemV =
        ContractItemDataAccess.select(conn, crit);

      if (null == cidv || 0 == cidv.size()
          || null == contractItemV || 0 == contractItemV.size()) {
        return cItemPriceViewV;
      }

      crit = new DBCriteria();
      crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,
                      contractD.getCatalogId());
      crit.addCondition(catStructCond);
      crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                      RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      CatalogStructureDataVector structureItemV =
        CatalogStructureDataAccess.select(conn, crit);
      if (null != structureItemV && 0 < structureItemV.size()) {
        for (int i = 0; i < contractItemV.size(); i++) {
          ContractItemPriceView cItemPriceViewD = ContractItemPriceView.
                                                  createValue();
          ContractItemData contractItemD = (ContractItemData) contractItemV.get(
            i);
          cItemPriceViewD.setContractId(contractItemD.getContractId());
          cItemPriceViewD.setItemId(contractItemD.getItemId());
          cItemPriceViewD.setItemSku(locateItemSku(contractItemD.getItemId(),
            idv));
          cItemPriceViewD.setDistCost(contractItemD.getDistCost());
          cItemPriceViewD.setPrice(contractItemD.getAmount());

          for (int j = 0; j < structureItemV.size(); j++) {
            CatalogStructureData structureItemD = (CatalogStructureData)
                                                  structureItemV.get(j);
            if (cItemPriceViewD.getItemId() == structureItemD.getItemId()) {
              cItemPriceViewD.setDistId(structureItemD.getBusEntityId());
              break;
            }
          }
          cItemPriceViewV.add(cItemPriceViewD);
        }
      }
    } catch (Exception e) {
      throw new RemoteException("getItemDistributorIdCollection() : " +
                                e.getMessage());
    } finally {
      closeConnection(conn);
    }

    return cItemPriceViewV;
  }


  /**
   *  Gets the Item's distributor Id attribute of the ContractBean object
   *
   *@param  pContractId                Description of Parameter
   *@param  pItemIdV                   Description of Parameter
   *@return                            The distributor Id value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemPriceViewVector getItemDistributorIdCollection(int
    pContractId,
    IdVector pItemIdV) throws RemoteException, DataNotFoundException {
    Connection conn = null;
    ContractItemPriceViewVector cItemPriceViewV = new
                                                  ContractItemPriceViewVector();

    if (0 == pContractId) {
      return cItemPriceViewV;
    }

    try {
      conn = getConnection();

      // make sure the contract exist
      ContractData contractD = ContractDataAccess.select(conn,
        pContractId);
      if (null == contractD) {
        return cItemPriceViewV;
      }

      // make sure the item is in the contract
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      crit.addOneOf(ContractItemDataAccess.ITEM_ID,
                    pItemIdV);
      IdVector cidv = ContractItemDataAccess.selectIdOnly(conn,
        ContractItemDataAccess.ITEM_ID, crit);
      ContractItemDataVector contractItemV =
        ContractItemDataAccess.select(conn, crit);

      if (null == cidv || 0 == cidv.size()
          || null == contractItemV || 0 == contractItemV.size()) {
        return cItemPriceViewV;
      }

      crit = new DBCriteria();
      crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,
                      contractD.getCatalogId());
      crit.addOneOf(CatalogStructureDataAccess.ITEM_ID,
                    cidv);
      crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                      RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      CatalogStructureDataVector structureItemV =
        CatalogStructureDataAccess.select(conn, crit);
      if (null != structureItemV && 0 < structureItemV.size()) {
        for (int i = 0; i < contractItemV.size(); i++) {
          ContractItemPriceView cItemPriceViewD = ContractItemPriceView.
                                                  createValue();
          ContractItemData contractItemD = (ContractItemData) contractItemV.get(
            i);
          cItemPriceViewD.setContractId(contractItemD.getContractId());
          cItemPriceViewD.setItemId(contractItemD.getItemId());
          cItemPriceViewD.setDistCost(contractItemD.getDistCost());
          cItemPriceViewD.setPrice(contractItemD.getAmount());
          for (int j = 0; j < structureItemV.size(); j++) {
            CatalogStructureData structureItemD = (CatalogStructureData)
                                                  structureItemV.get(j);
            if (cItemPriceViewD.getItemId() == structureItemD.getItemId()) {
              cItemPriceViewD.setDistId(structureItemD.getBusEntityId());
              break;
            }
          }
          cItemPriceViewV.add(cItemPriceViewD);
        }
      }
    } catch (Exception e) {
      throw new RemoteException("getItemDistributorIdCollection2() : " +
                                e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return cItemPriceViewV;
  }


  /**
   *  Gets the distributor collection for the contract attribute of the ContractBean object
   *
   *@param  pContractId                Description of Parameter
   *@return                            The distributor Id value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public BusEntityDataVector getContractDistCollection(int pContractId) throws
    RemoteException, DataNotFoundException {
    Connection conn = null;
    BusEntityDataVector distV = new BusEntityDataVector();

    if (0 == pContractId) {
      return distV;
    }

    try {
      conn = getConnection();

      // make sure the contract exist
      ContractData contractD = ContractDataAccess.select(conn,
        pContractId);
      if (null == contractD) {
        return distV;
      }

      // make sure the item is in the contract
      DBCriteria crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.CONTRACT_ID,
                      pContractId);
      IdVector cidv = ContractItemDataAccess.selectIdOnly(conn,
        ContractItemDataAccess.ITEM_ID, crit);

      if (null == cidv || 0 == cidv.size()) {
        return distV;
      }

      crit = new DBCriteria();
      crit.addEqualTo(CatalogStructureDataAccess.CATALOG_ID,
                      contractD.getCatalogId());
      crit.addOneOf(CatalogStructureDataAccess.ITEM_ID,
                    cidv);
      crit.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                      RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      IdVector distIdV = CatalogStructureDataAccess.selectIdOnly(conn,
        CatalogStructureDataAccess.BUS_ENTITY_ID, crit);
      if (null != distIdV && 0 < distIdV.size()) {
        crit = new DBCriteria();
        crit.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distIdV);
        crit.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);

        distV = BusEntityDataAccess.select(conn, crit);
      }

    } catch (Exception e) {
      throw new RemoteException("getContractDistCollection() : " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return distV;
  }


  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId              Description of Parameter
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */

  /*
      public ContractItemDescDataVector getContractItemsDesc
              (int pContractId)
               throws RemoteException, DataNotFoundException {

          ContractData contractdata = getContract(pContractId);

          ContractItemDescDataVector v = new
                  ContractItemDescDataVector();

          Connection conn = null;
          try {
              ContractItemDataVector itemv = getItems(pContractId);

              conn = getConnection();
              for (int i = 0; i < itemv.size(); i++) {
                  // Get the basic description for this item.
                  ContractItemData o =
                      (ContractItemData) itemv.get(i);

                  // Get the product description.
                  ContractItemDescData n = fillProductDesc
                      (conn, contractdata.getCatalogId(), o.getItemId());

                  n.setContractItemId(o.getContractItemId());
                  n.setContractId(o.getContractId());
                  n.setItemId(o.getItemId());
                  n.setDistributorCost(o.getDistCost());
                  n.setAmount(o.getAmount());
                  n.setDiscountAmount(o.getDiscountAmount());
                  n.setCurrencyCd(o.getCurrencyCd());
                  v.add(n);
              }
          }
          catch (Exception e) {
              throw new RemoteException("getContractItemsDesc: " + e);
          }
          finally {
              try { if ( conn != null ) { conn.close(); } }
              catch (Exception e) {}
          }

          return v;
      }
   */


  public ContractItemData copyItem(int pContractId,
                                   ContractItemData pItemd) throws
    RemoteException {
    Connection conn = null;
    ContractItemData cidata =
      ContractItemData.createValue();
    try {

      conn = getConnection();

      cidata = pItemd;
      cidata.setContractId(pContractId);
      cidata.setContractItemId(0);

      ContractItemDataAccess.insert(conn, cidata);
    } catch (Exception e) {
      throw new RemoteException("copyItem: " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return cidata;
  }


  /**
   *  Description of the Method
   *
   *@param  pContractItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeItem(int pContractItemId) throws RemoteException {

    Connection conn = null;

    try {
      conn = getConnection();
      //Remove item from Order Guides
      ContractItemData contractItemD = ContractItemDataAccess.select(conn,
        pContractItemId);
      int itemId = contractItemD.getItemId();
      int contractId = contractItemD.getContractId();
      ContractData contractD = ContractDataAccess.select(conn, contractId);
      int catalogId = contractD.getCatalogId();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideDataAccess.CATALOG_ID, catalogId);
      ArrayList types = new ArrayList();
      types.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE);
      types.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.SHOPPING_CART);
      types.add(RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE);
      dbc.addOneOf(OrderGuideDataAccess.ORDER_GUIDE_TYPE_CD, types);
      IdVector orderGuideIdV =
        OrderGuideDataAccess.selectIdOnly(conn,
                                          OrderGuideDataAccess.ORDER_GUIDE_ID,
                                          dbc);
      dbc = new DBCriteria();
      dbc.addEqualTo(OrderGuideStructureDataAccess.ITEM_ID, itemId);
      dbc.addOneOf(OrderGuideStructureDataAccess.ORDER_GUIDE_ID, orderGuideIdV);
      int removedQty = OrderGuideStructureDataAccess.remove(conn, dbc);

      //Remove contract item
      ContractItemDataAccess.remove(conn, pContractItemId,true);

    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException("removeItem: " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }
    return;
  }


  /**
   *  Description of the Method
   *
   *@param  pCatalogId  Description of Parameter
   *@param  pItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(int pCatalogId,
                                            int pItemId) throws RemoteException {

    Connection conn = null;

    try {
      conn = getConnection();
      removeCatalogItemFromContract(conn, pCatalogId, pItemId);
    } catch (Exception e) {
      throw new RemoteException("removeCatalogItemFromContract: " +
                                e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }
    return;
  }

  /**
   *  Description of the Method
   *
   *@param pCon DB connection
   *@param  pCatalogId  Description of Parameter
   *@param  pItemId  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(Connection pCon,
                                            int pCatalogId,
                                            int pItemId) throws RemoteException,
    Exception {
    DBCriteria crit = new DBCriteria();

    IdVector contractIdV = new IdVector();
    crit.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);
    contractIdV = ContractDataAccess.selectIdOnly(pCon,
                                                  ContractDataAccess.
                                                  CONTRACT_ID, crit);

    if (null != contractIdV && 0 < contractIdV.size()) {
      crit = new DBCriteria();
      crit.addEqualTo(ContractItemDataAccess.ITEM_ID, pItemId);
      crit.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIdV);
      ContractItemDataAccess.remove(pCon, crit);

      crit = new DBCriteria();
      crit.addEqualTo(ContractItemSubstDataAccess.ITEM_ID, pItemId);
      crit.addOneOf(ContractItemSubstDataAccess.CONTRACT_ID, contractIdV);
      ContractItemSubstDataAccess.remove(pCon, crit);

      crit = new DBCriteria();
      crit.addEqualTo(ContractItemSubstDataAccess.SUBST_ITEM_ID, pItemId);
      crit.addOneOf(ContractItemSubstDataAccess.CONTRACT_ID, contractIdV);
      ContractItemSubstDataAccess.remove(pCon, crit);
    }
    return;
  }


  /**
   *  Description of the Method
   *
   *@param  pCatalogId  Description of Parameter
   *@param  pItemIdV  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(int pCatalogId,
                                            IdVector pItemIdV) throws
    RemoteException {

    Connection conn = null;

    try {
      conn = getConnection();
      removeCatalogItemFromContract(conn, pCatalogId, pItemIdV);
    } catch (Exception e) {
      throw new RemoteException("removeCatalogItemFromContract(1): " +
                                e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }
    return;
  }

  /**
   *  Description of the Method
   *
   *@param  pCon DB Connection
   *@param  pCatalogId  Description of Parameter
   *@param  pItemIdV  Description of Parameter
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(Connection pCon,
                                            int pCatalogId,
                                            IdVector pItemIdV) throws
    RemoteException, Exception {

    DBCriteria crit = new DBCriteria();

    IdVector contractIdV = new IdVector();
    crit.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);
    contractIdV = ContractDataAccess.selectIdOnly(pCon,
                                                  ContractDataAccess.
                                                  CONTRACT_ID, crit);

    if (null != contractIdV && 0 < contractIdV.size()
        && null != pItemIdV && 0 < pItemIdV.size()) {
      crit = new DBCriteria();
      crit.addOneOf(ContractItemDataAccess.ITEM_ID, pItemIdV);
      crit.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIdV);
      ContractItemDataAccess.remove(pCon, crit);

      crit = new DBCriteria();
      crit.addOneOf(ContractItemSubstDataAccess.ITEM_ID, pItemIdV);
      crit.addOneOf(ContractItemSubstDataAccess.CONTRACT_ID, contractIdV);
      ContractItemSubstDataAccess.remove(pCon, crit);

      crit = new DBCriteria();
      crit.addOneOf(ContractItemSubstDataAccess.SUBST_ITEM_ID, pItemIdV);
      crit.addOneOf(ContractItemSubstDataAccess.CONTRACT_ID, contractIdV);
      ContractItemSubstDataAccess.remove(pCon, crit);
    }
    return;
  }

  /**
   *  Removes items from contracts
   *
   *@param  pCon DB Connection
   *@param  pCatalogIdV Vector of catalog ids
   *@param  pItemIdV  Vector of item ids
   *@param  pUser User login name
   *@exception  RemoteException     Description of Exception
   */
  public void removeCatalogItemFromContract(Connection pCon,
                                            IdVector pCatalogIdV,
                                            IdVector pItemIdV,
                                            String pUser) throws
    RemoteException, Exception {

    DBCriteria crit = new DBCriteria();

    IdVector contractIdV = new IdVector();
    crit.addOneOf(ContractDataAccess.CATALOG_ID, pCatalogIdV);
    contractIdV = ContractDataAccess.selectIdOnly(pCon,
                                                  ContractDataAccess.
                                                  CONTRACT_ID, crit);

    if (null != contractIdV && 0 < contractIdV.size()
        && null != pItemIdV && 0 < pItemIdV.size()) {
      crit = new DBCriteria();
      crit.addOneOf(ContractItemDataAccess.ITEM_ID, pItemIdV);
      crit.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIdV);
      ContractItemDataAccess.remove(pCon, crit);

      crit = new DBCriteria();
      crit.addOneOf(ContractItemSubstDataAccess.ITEM_ID, pItemIdV);
      crit.addOneOf(ContractItemSubstDataAccess.CONTRACT_ID, contractIdV);
      ContractItemSubstDataAccess.remove(pCon, crit);

      crit = new DBCriteria();
      crit.addOneOf(ContractItemSubstDataAccess.SUBST_ITEM_ID, pItemIdV);
      crit.addOneOf(ContractItemSubstDataAccess.CONTRACT_ID, contractIdV);
      ContractItemSubstDataAccess.remove(pCon, crit);
    }
    return;
  }

  /**
   *  Sets the ProductDesc attributes for the given catalog item.
   *
   *@param  pItemId  The new ProductDesc value
   *@param  pCatId   Description of Parameter
   *@return          Description of the Returned Value
   */
  private ContractItemDescData fillProductDesc
    (Connection pConn, int pCatId, int pItemId) {

    try {
      APIAccess apiAccess = getAPIAccess();
      CatalogInformation cati = apiAccess.getCatalogInformationAPI();

      ContractItemDescData n = ContractItemDescData.createValue();
      CatalogInformationBean catBean = new CatalogInformationBean();
      ProductData p = catBean.s_productData
                      (pConn, pCatId, pItemId);

      n.setCwSKU(String.valueOf(p.getSkuNum()));
      n.setShortDesc(p.getShortDesc());
      n.setPackDesc(p.getPack());
      n.setSizeDesc(p.getSize());
      n.setUomDesc(p.getUom());
      n.setColorDesc(p.getColor());
      n.setManufacturerCd(p.getManufacturer().getShortDesc());
      n.setManufacturerSKU(p.getManufacturerSku());
      CatalogCategoryDataVector catdv = p.getCatalogCategories();
      String categories = "";
      for (int i1 = 0; i1 < catdv.size(); i1++) {
        CatalogCategoryData catdata =
          (CatalogCategoryData) catdv.get(i1);
        String thiscat = catdata.getCatalogCategoryShortDesc() + " ";
        categories += thiscat;
      }
      n.setCategoryDesc(categories);
      n.setPrice(new java.math.BigDecimal(p.getListPrice()));

      return n;
    } catch (Exception e) {
      String msg = "fillProductDesc: " + e.getMessage();
      logError(msg);
    }

    return null;
  }

  /**
   *  Gets the ContractItemDataVector for the supplied contract id
   *
   *@param  pContractId                The contract id to filter on
   *@return                            The ContractItemsVector for the supplied contract
   *@exception  RemoteException        On any error
   */
  public ContractItemDataVector getContractItems(int pContractId) throws
    RemoteException {
    Connection con = null;
    try {
      con = getConnection();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
      String contractItemReq = ContractItemDataAccess.getSqlSelectIdOnly(
        ContractItemDataAccess.ITEM_ID, dbc);
      dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
      ContractItemDataVector contractItemDV = ContractItemDataAccess.select(con,
        dbc);
      return contractItemDV;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException(e.getMessage());
    } finally {
      closeConnection(con);
    }
  }

  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId              Description of Parameter
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDescDataVector getContractItemsDesc
    (int pContractId) throws RemoteException, DataNotFoundException {
    return getContractItemsDesc(pContractId, true);
  }

  /**
   *  Gets the ContractItems attribute of the ContractBean object
   *
   *@param  pContractId              Description of Parameter
   *@param  pCatalogOnlyFl          Filters out non catalog items if true
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDescDataVector getContractItemsDesc
    (int pContractId, boolean pCatalogOnlyFl) throws RemoteException,
    DataNotFoundException {
    Date dateSt = new Date();
    ContractData contractdata = getContract(pContractId);
    ContractItemDescDataVector itemDescDV = new
                                            ContractItemDescDataVector();

    Connection con = null;
    try {
      con = getConnection();
      ContractData cD = ContractDataAccess.select(con, pContractId);
      //Pick up contract items
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
      String contractItemReq = ContractItemDataAccess.getSqlSelectIdOnly(
        ContractItemDataAccess.ITEM_ID, dbc);
      dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
      Date dateReqSt = new Date();
      ContractItemDataVector contractItemDV = ContractItemDataAccess.select(con,
        dbc);
      //Pick up contract items
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, contractItemReq);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      dateReqSt = new Date();
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);

      // get the items from the catalog
      DBCriteria dbc2 = new DBCriteria();

      ContractData cd = ContractDataAccess.select(con, pContractId);
      dbc2.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, cd.getCatalogId());
      dbc2.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      dateReqSt = new Date();
      CatalogStructureDataVector catItemDV = CatalogStructureDataAccess.select(
        con, dbc2);
      //Pick up item meta information
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, contractItemReq);
      LinkedList itemPropNames = new LinkedList();
      itemPropNames.add("UOM");
      itemPropNames.add("PACK");
      itemPropNames.add("COLOR");
      itemPropNames.add("SIZE");
      itemPropNames.add("LIST_PRICE");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, itemPropNames);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      dateReqSt = new Date();
      ItemMetaDataVector itemPropDV = ItemMetaDataAccess.select(con, dbc);
      //Pick up item manufacturers
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, contractItemReq);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
      dateReqSt = new Date();
      ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con,
        dbc);
      IdVector itemMfgIds = new IdVector();
      for (int ii = 0; ii < itemMappingDV.size(); ii++) {
        ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ii);
        itemMfgIds.add(new Integer(imD.getBusEntityId()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);
      dateReqSt = new Date();
      BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(con, dbc);
      //Pick up item categories
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, contractItemReq);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, cD.getCatalogId());
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      dateReqSt = new Date();
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);
      IdVector itemCategoryIds = new IdVector();
      for (int ii = 0; ii < itemAssocDV.size(); ii++) {
        ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(ii);
        itemCategoryIds.add(new Integer(iaD.getItem2Id()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, itemCategoryIds);
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.CATEGORY);
      dateReqSt = new Date();
      ItemDataVector categories = ItemDataAccess.select(con, dbc);
      //Combine altogether
      dateReqSt = new Date();
      for (int ii = 0, kk = 0; ii < contractItemDV.size(); ii++) {
        int jj = 0, ff = 0, mm = 0, uu = 0, cc = 0;
        ContractItemData ciD = (ContractItemData) contractItemDV.get(ii);
        int itemId = ciD.getItemId();
        ContractItemDescData cidD = ContractItemDescData.createValue();
        cidD.setContractItemId(ciD.getContractItemId());
        cidD.setContractId(ciD.getContractId());
        cidD.setItemId(ciD.getItemId());
        cidD.setDistributorCost(ciD.getDistCost());
        cidD.setDistributorBaseCost(ciD.getDistBaseCost());
        cidD.setAmount(ciD.getAmount());
        cidD.setDiscountAmount(ciD.getDiscountAmount());
        cidD.setCurrencyCd(ciD.getCurrencyCd());

        //Catalog matching
        boolean found = false;
        for (; kk < catItemDV.size(); kk++) {
          CatalogStructureData cat = (CatalogStructureData) catItemDV.get(kk);
          if (cat.getItemId() == ciD.getItemId()) {
            found = true;
            kk++;
            break;
          }
          if (cat.getItemId() > ciD.getItemId()) {
            break;
          }
        }
        if (found) {
          cidD.setCatalogId(cD.getCatalogId());
        } else {
          cidD.setCatalogId(0);
          if (pCatalogOnlyFl)continue;
        }

        //Item data
        while (jj < itemDV.size()) {
          ItemData iD = (ItemData) itemDV.get(jj);
          if (iD.getItemId() == itemId) {
            cidD.setCwSKU("" + iD.getSkuNum());
            cidD.setShortDesc(iD.getShortDesc());
            break;
          }
          jj++;
        }
        //Meta data
        while (uu < itemPropDV.size()) {
          ItemMetaData imD = (ItemMetaData) itemPropDV.get(uu);
          if (imD.getItemId() == itemId) {
            if ("UOM".equals(imD.getNameValue())) {
              cidD.setUomDesc(imD.getValue());
            } else if ("SIZE".equals(imD.getNameValue())) {
              cidD.setSizeDesc(imD.getValue());
            } else if ("PACK".equals(imD.getNameValue())) {
              cidD.setPackDesc(imD.getValue());
            } else if ("COLOR".equals(imD.getNameValue())) {
              cidD.setColorDesc(imD.getValue());
            } else if ("LIST_PRICE".equals(imD.getNameValue())) {
              String priceS = imD.getValue();
              BigDecimal price = new BigDecimal(0);
              try {
                price = new BigDecimal(priceS);
              } catch (Exception exc) {}
              cidD.setPrice(price);
            }
          }
          uu++;
        }
        //Manufacturer data
        for (; ff < itemMappingDV.size(); ff++) {
          ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ff);
          if (imD.getItemId() == itemId) {
            int beId = imD.getBusEntityId();
            cidD.setManufacturerSKU(imD.getItemNum());
            for (int bb = 0; bb < itemMfgDV.size(); bb++) {
              BusEntityData beD = (BusEntityData) itemMfgDV.get(bb);
              if (beD.getBusEntityId() == beId) {
                cidD.setManufacturerCd(beD.getShortDesc());
                break;
              }
            }
            break;
          }
        }
        //Categories
        String categoryString = "";
        for (; cc < itemAssocDV.size(); cc++) {
          ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(cc);
          if (iaD.getItem1Id() == itemId) {
            int catId = iaD.getItem2Id();
            for (int rr = 0; rr < categories.size(); rr++) {
              ItemData iD = (ItemData) categories.get(rr);
              if (catId == iD.getItemId()) {
                categoryString += iD.getShortDesc() + " ";
                break;
              }
            }
          }
        }
        cidD.setCategoryDesc(categoryString);
        //add to vector
        itemDescDV.add(cidD);
      }
      } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException("getContractItemsDesc: " + e);
    } finally {
      try {
        if (con != null) {
          con.close();
        }
      } catch (Exception e) {}
    }

    return itemDescDV;
  }

///////////////////////////////////////////////////////////////////
  private BigDecimal calcDuration(java.util.Date pFinish, java.util.Date pStart) {
    long duration = pFinish.getTime() - pStart.getTime();
    BigDecimal durBD = new BigDecimal(duration);
    durBD = durBD.multiply(new BigDecimal(.001));
    durBD.setScale(2, BigDecimal.ROUND_HALF_UP);
    return durBD;
  }

  /**
   *  Gets the CatalogItems attribute of the ContractBean object
   *
   *@param  pContractId              Description of Parameter
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  /*
      public ContractItemDescDataVector getCatalogItems
              (int pContractId)
               throws RemoteException, DataNotFoundException {

          ContractData contractdata = getContract(pContractId);
          CatalogInformation cati = null;
          // Get the items in the catalog.
          IdVector itemids;
          Connection conn = null;
          try {
              APIAccess apiAccess = getAPIAccess();
              cati = apiAccess.getCatalogInformationAPI();
              // Get all the items.
              itemids = cati.searchCatalogProducts
                      (contractdata.getCatalogId());


              // Get the items from the catalog.
              conn = getConnection();
              ContractItemDescDataVector ov =
                  new ContractItemDescDataVector();
              for (int i = 0; i < itemids.size(); i++) {
                  Integer itemid = (Integer) itemids.get(i);
                  ContractItemDescData n = fillProductDesc
                      (conn, contractdata.getCatalogId(),
                       itemid.intValue());
                  n.setContractItemId(0);
                  n.setContractId(contractdata.getContractId());
                  n.setItemId(itemid.intValue());
                  n.setQuantity(0);
                  ov.add(n);
              }
              return ov;
          }
          catch (Exception e) {
              String msg = "getCatalogItems: " + e.getMessage();
              logError(msg);
              throw new RemoteException(msg);
          }
          finally {
              try { if ( conn != null ) { conn.close(); } }
              catch (Exception e) {}
          }

      }
   */
  /**
   *  Gets the catalog items, which does not belong to contract
   *
   *@param  pContractId              Description of Parameter
   *@return                            The CatalogItems value
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public ContractItemDescDataVector getCatalogItems(int pContractId) throws
    RemoteException, DataNotFoundException {
    ContractData contractdata = getContract(pContractId);
    ContractItemDescDataVector v = new ContractItemDescDataVector();
    Connection con = null;
    try {
      con = getConnection();
      ContractData cD = ContractDataAccess.select(con, pContractId);
      //Catalog ids
      int catalogId = cD.getCatalogId();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);
      dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                     RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
      dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
      IdVector catalogItemIdsAll = CatalogStructureDataAccess.selectIdOnly(con,
        CatalogStructureDataAccess.ITEM_ID, dbc);
      //Pick up contract items
      dbc = new DBCriteria();
      dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
      dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
      IdVector contractItemIds = ContractItemDataAccess.selectIdOnly(con,
        ContractItemDataAccess.ITEM_ID, dbc);
      //Items to add
      IdVector catalogItemIds = new IdVector();
      for (int ii = 0, jj = 0; ii < catalogItemIdsAll.size(); ii++) {
        Integer catalogItemIdI = (Integer) catalogItemIdsAll.get(ii);
        int catalogItemId = catalogItemIdI.intValue();
        for (; jj < contractItemIds.size(); ) {
          Integer contractItemIdI = (Integer) contractItemIds.get(jj);
          int contractItemId = contractItemIdI.intValue();
          if (contractItemId < catalogItemId) {
            jj++;
            continue;
          } else if (contractItemId == catalogItemId) {
            jj++;
            break;
          } else {
            catalogItemIds.add(catalogItemIdI);
            break;
          }
        }
        if (jj == contractItemIds.size()) {
          catalogItemIds.add(catalogItemIdI);
        }
      }
      //Pick up contract items
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, catalogItemIds);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      //Pick up item meta information
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, catalogItemIds);
      LinkedList itemPropNames = new LinkedList();
      itemPropNames.add("UOM");
      itemPropNames.add("PACK");
      itemPropNames.add("COLOR");
      itemPropNames.add("SIZE");
      itemPropNames.add("LIST_PRICE");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, itemPropNames);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemMetaDataVector itemPropDV = ItemMetaDataAccess.select(con, dbc);
      //Pick up item manufacturers
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, catalogItemIds);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
      ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con,
        dbc);
      IdVector itemMfgIds = new IdVector();
      for (int ii = 0; ii < itemMappingDV.size(); ii++) {
        ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ii);
        itemMfgIds.add(new Integer(imD.getBusEntityId()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);
      BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(con, dbc);
      //Pick up item categories
      dbc = new DBCriteria();
      dbc.addOneOf(ItemAssocDataAccess.ITEM1_ID, catalogItemIds);
      dbc.addEqualTo(ItemAssocDataAccess.ITEM_ASSOC_CD,
                     RefCodeNames.ITEM_ASSOC_CD.PRODUCT_PARENT_CATEGORY);
      dbc.addEqualTo(ItemAssocDataAccess.CATALOG_ID, cD.getCatalogId());
      dbc.addOrderBy(ItemAssocDataAccess.ITEM1_ID);
      ItemAssocDataVector itemAssocDV = ItemAssocDataAccess.select(con, dbc);
      IdVector itemCategoryIds = new IdVector();
      for (int ii = 0; ii < itemAssocDV.size(); ii++) {
        ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(ii);
        itemCategoryIds.add(new Integer(iaD.getItem2Id()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, itemCategoryIds);
      dbc.addEqualTo(ItemDataAccess.ITEM_TYPE_CD,
                     RefCodeNames.ITEM_TYPE_CD.CATEGORY);
      ItemDataVector categories = ItemDataAccess.select(con, dbc);
      //Combine altogether
      for (int ii = 0, ff = 0, mm = 0, uu = 0, cc = 0; ii < itemDV.size(); ii++) {
        ContractItemDescData cidD = ContractItemDescData.createValue();
        ItemData iD = (ItemData) itemDV.get(ii);
        int itemId = iD.getItemId();
        cidD.setContractId(pContractId);
        cidD.setItemId(itemId);
        cidD.setDistributorCost(new BigDecimal(0));
        cidD.setDistributorBaseCost(new BigDecimal(0));
        cidD.setAmount(new BigDecimal(0));
        cidD.setDiscountAmount(new BigDecimal(0));
        //cidD.setCurrencyCd(ciD.getCurrencyCd());
        cidD.setCwSKU("" + iD.getSkuNum());
        cidD.setShortDesc(iD.getShortDesc());
        //Meta data
        while (uu < itemPropDV.size()) {
          ItemMetaData imD = (ItemMetaData) itemPropDV.get(uu);
          if (imD.getItemId() == itemId) {
            uu++;
            if ("UOM".equals(imD.getNameValue())) {
              cidD.setUomDesc(imD.getValue());
            } else if ("SIZE".equals(imD.getNameValue())) {
              cidD.setSizeDesc(imD.getValue());
            } else if ("PACK".equals(imD.getNameValue())) {
              cidD.setPackDesc(imD.getValue());
            } else if ("COLOR".equals(imD.getNameValue())) {
              cidD.setColorDesc(imD.getValue());
            } else if ("LIST_PRICE".equals(imD.getNameValue())) {
              String priceS = imD.getValue();
              BigDecimal price = new BigDecimal(0);
              try {
                price = new BigDecimal(priceS);
              } catch (Exception exc) {}
              cidD.setPrice(price);
            }
            continue;
          }
          if (imD.getItemId() > itemId) {
            break;
          }
        }
        //Manufacturer data
        for (; ff < itemMappingDV.size(); ff++) {
          ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ff);
          if (imD.getItemId() > itemId) {
            break;
          }
          if (imD.getItemId() == itemId) {
            int beId = imD.getBusEntityId();
            cidD.setManufacturerSKU(imD.getItemNum());
            for (int bb = 0; bb < itemMfgDV.size(); bb++) {
              BusEntityData beD = (BusEntityData) itemMfgDV.get(bb);
              if (beD.getBusEntityId() == beId) {
                cidD.setManufacturerCd(beD.getShortDesc());
                break;
              }
            }
          }
        }
        //Categories
        String categoryString = "";
        for (; cc < itemAssocDV.size(); cc++) {
          ItemAssocData iaD = (ItemAssocData) itemAssocDV.get(cc);
          if (iaD.getItem1Id() > itemId) {
            break;
          }
          if (iaD.getItem1Id() == itemId) {
            int catId = iaD.getItem2Id();
            for (int rr = 0; rr < categories.size(); rr++) {
              ItemData catiD = (ItemData) categories.get(rr);
              if (catId == catiD.getItemId()) {
                categoryString += catiD.getShortDesc() + " ";
                break;
              }
            }
          }
        }
        cidD.setCategoryDesc(categoryString);
        //add to vector
        v.add(cidD);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RemoteException("getContractItemsDesc: " + e);
    } finally {
      try {
        if (con != null) {
          con.close();
        }
      } catch (Exception e) {}
    }
    return v;
  }


  /**
   *  Description of the Method
   *
   *@param  pContractItemId           Description of Parameter
   *@param  pNew                      Description of Parameter
   *@param  pModUserName               Description of Parameter
   *@exception  RemoteException        Description of Exception
   *@exception  DataNotFoundException  Description of Exception
   */
  public void updateItem(int pContractItemId,
                         ContractItemData pNew, String pModUserName) throws
    RemoteException, DataNotFoundException {

    setDebugOn();
    Connection conn = null;
    try {
      conn = getConnection();
      ContractItemData o = null;
      try {
        o = ContractItemDataAccess.select(conn,
                                          pContractItemId);
        logDebug("::: original contract item info: " + o);
      } catch (Exception e) {
        logError("updateItem: " + e.getMessage());
      }

      if (o != null) {
        o.setAmount(pNew.getAmount());
        o.setDistCost(pNew.getDistCost());
        o.setDistBaseCost(pNew.getDistBaseCost());
        o.setModBy(pModUserName);
        ContractItemDataAccess.update(conn, o);
        logDebug("::: update contract item info: " + o);
      }
    } catch (Exception e) {
      throw new RemoteException("updateItem: " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {
      }
    }

    return;
  }

  /**
   *  Description of the Method
   *
   *@param  pNew                      CotrtractItemDescData object
   *@param  pModUserName              the user login name
   *@exception  RemoteException
   *@exception  DataNotFoundException thrown if contract item does not exist
   */
  public void updateItem(ContractItemDescData pNew, String pModUserName) throws
    RemoteException, DataNotFoundException {
    setDebugOn();
    Connection conn = null;
    try {
      conn = getConnection();
      ContractItemData o = null;
      int contractItemId = pNew.getContractItemId();
      try {
        o = ContractItemDataAccess.select(conn, contractItemId);
        logDebug("::: original contract item info: " + o);
      } catch (Exception e) {
        logError("updateItem: " + e.getMessage());
      }
      if (o != null) {
        o.setAmount(pNew.getAmount());
        o.setDistCost(pNew.getDistributorCost());
        o.setDistBaseCost(pNew.getDistributorBaseCost());
        o.setModBy(pModUserName);
        ContractItemDataAccess.update(conn, o);
        logDebug("::: update contract item info: " + o);
      }
    } catch (Exception e) {
      throw new RemoteException("updateItem: " + e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception ex) {}
    }
    return;
  }


  /**
   * Adds the contract information values to be used by the request.
   * @param pContract  the contract data.
   * @param request  the contract request data.
   * @return new ContractRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractRequestData addContract(ContractData pContract,
                                         ContractRequestData request) throws
    RemoteException {
    return new ContractRequestData();
  }

  /**
   * Updates the contract information values to be used by the request.
   * @param pUpdateContractData  the contract data.
   * @param pContractId the contract identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateContract(ContractData pUpdateContractData,
                             int pContractId) throws RemoteException {

  }

  /**
   * Adds the contract Item information values to be used by the request.
   * @param pContract  the contract item data.
   * @param request  the contract item request data.
   * @return new ContractItemRequestData()
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractItemRequestData addContractItem(ContractItemData pContract,
                                                 ContractItemRequestData
                                                 request) throws
    RemoteException {
    return new ContractItemRequestData();
  }

  /**
   * Updates the contract item information values to be used by the request.
   * @param pUpdateContractItemData  the contract item data.
   * @param pContractId the contract identifier.
   * @param pItemId the contract item identifier.
   * @return none
   * @throws            RemoteException Required by EJB 1.0
   */
  public void updateContractItem(ContractItemData pUpdateContractItemData,
                                 int pContractId, int pItemId) throws
    RemoteException {

	  Connection con = null;
	     if (pUpdateContractItemData == null) {
	       return;
	     }
	     try {
	       DBCriteria dbc;
	       con = getConnection();

	       dbc = new DBCriteria();
	       dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, pItemId);
	       dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
	       ContractItemDataVector ciDV = ContractItemDataAccess.select(con, dbc);
	       if (ciDV.size() == 0) {
	         return;
	       }
	       if (ciDV.size() > 1) {
	         throw new RemoteException(
	           "More than one contract item object found. Contract id: " +
	           pContractId + " Item id: " + pItemId);
	       }
	       ContractItemData ciD = (ContractItemData) ciDV.get(0);
	       BigDecimal price = pUpdateContractItemData.getAmount();
	       BigDecimal distCost = pUpdateContractItemData.getDistCost();
	       if (price == null) {
	         throw new RemoteException(
	           "Undefined price for contract item. Contract id: " +
	           pContractId + " Item id: " + pItemId);
	       }
	       if (distCost == null) {
	         throw new RemoteException(
	           "Undefined distributor cost for contract item. Contract id: " +
	           pContractId + " Item id: " + pItemId);
	       }
	       if (!price.equals(ciD.getAmount()) || !distCost.equals(ciD.getDistCost())) {
	         ciD.setAmount(price);
	         ciD.setDistCost(distCost);

	         ContractItemDataAccess.update(con, ciD);
	       }
	     } catch (javax.naming.NamingException exc) {
	       String em =
	         "Error. ContractBean.updateCotractItemPrice() Naming Exception happened. " +
	         exc.getMessage();
	       logError(em);
	       throw new RemoteException(em);
	     } catch (SQLException exc) {
	       String em =
	         "Error. ContractBean.updateCotractItemPrice() SQL Exception happened. " +
	         exc.getMessage();
	       logError(em);
	       throw new RemoteException(em);
	     } finally {
	       try {
	         if (con != null) con.close();
	       } catch (SQLException exc) {
	         String em =
	           "Error. ContractBean.updateCotractItemPrice() SQL Exception happened. " +
	           exc.getMessage();
	         logError(em);
	         throw new RemoteException(em);
	       }
	     }
	     return;

  }


  /**
   * Gets substitutions for the contract.
   * @param pItemIds  the vector of item identifiers.
   * @param pContractId the contract identifier.
   * @param pIncludeNull the flag, which indicates to include contract item with no substitutions
   * @return vector of ContractItemSubstView object
   * @throws            RemoteException Required by EJB 1.0
   */
  public ContractItemSubstViewVector getSubstitutions(IdVector pItemIds,
    int pContractId, boolean pIncludeNull) throws RemoteException {
    Connection con = null;
    ContractItemSubstViewVector substitutions = new ContractItemSubstViewVector();
    try {
      DBCriteria dbc;
      con = getConnection();
      //Contract filter
      dbc = new DBCriteria();
      dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
      String contractItemReq = ContractItemDataAccess.getSqlSelectIdOnly(
        ContractItemDataAccess.ITEM_ID, dbc);
      //Substitutions
      dbc = new DBCriteria();
      dbc.addOneOf(ContractItemSubstDataAccess.ITEM_ID, pItemIds);
      dbc.addEqualTo(ContractItemSubstDataAccess.CONTRACT_ID, pContractId);
      String itemSubstReq = ContractItemSubstDataAccess.getSqlSelectIdOnly(
        ContractItemSubstDataAccess.ITEM_ID, dbc);
      String substItemReq = ContractItemSubstDataAccess.getSqlSelectIdOnly(
        ContractItemSubstDataAccess.SUBST_ITEM_ID, dbc);
      dbc.addOrderBy(ContractItemSubstDataAccess.ITEM_ID);
      ContractItemSubstDataVector contractItemSubstDV =
        ContractItemSubstDataAccess.select(con, dbc);
      //Substitute items
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, substItemReq);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector substItemDV = ItemDataAccess.select(con, dbc);
      //Substitute uom,size,pack
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, substItemReq);
      LinkedList prop = new LinkedList();
      prop.add("UOM");
      prop.add("SIZE");
      prop.add("PACK");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, prop);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemMetaDataVector substItemUomDV = ItemMetaDataAccess.select(con, dbc);
      //Items to substitute
      dbc = new DBCriteria();
      if (pIncludeNull) { //select all contract items
        dbc.addOneOf(ItemDataAccess.ITEM_ID, pItemIds);
      } else { //select only items with substitutions
        dbc.addOneOf(ItemDataAccess.ITEM_ID, itemSubstReq);
      }
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      //Item uom, size, pack
      dbc = new DBCriteria();
      if (pIncludeNull) { //select all contract items
        dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, pItemIds);
      } else { //select only items with substitutions
        dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, itemSubstReq);
      }
      prop = new LinkedList();
      prop.add("UOM");
      prop.add("SIZE");
      prop.add("PACK");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, prop);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemMetaDataVector itemUomDV = ItemMetaDataAccess.select(con, dbc);
      //Combine altogether
      for (int ii = 0, jj = 0, uu = 0; ii < itemDV.size(); ii++) {
        ContractItemSubstView itemSubstView = null;
        ContractItemSubstView itemSubstViewWrk = ContractItemSubstView.
                                                 createValue();
        itemSubstViewWrk.setContractId(pContractId);
        ItemData iD = (ItemData) itemDV.get(ii);
        int itemId = iD.getItemId();
        itemSubstViewWrk.setItemId(itemId);
        itemSubstViewWrk.setItemDesc(iD.getShortDesc());
        itemSubstViewWrk.setItemSku(iD.getSkuNum());
        while (uu < itemUomDV.size()) {
          ItemMetaData imD = (ItemMetaData) itemUomDV.get(uu);
          if (imD.getItemId() == itemId) {
            uu++;
            if ("UOM".equals(imD.getNameValue())) {
              itemSubstViewWrk.setItemUom(imD.getValue());
            } else if ("SIZE".equals(imD.getNameValue())) {
              itemSubstViewWrk.setItemSize(imD.getValue());
            } else if ("PACK".equals(imD.getNameValue())) {
              itemSubstViewWrk.setItemPack(imD.getValue());
            }
            continue;
          }
          if (imD.getItemId() > itemId) {
            break;
          }
        }
        boolean flag = true;
        for (; jj < contractItemSubstDV.size(); jj++) {
          ContractItemSubstData cisD = (ContractItemSubstData)
                                       contractItemSubstDV.get(jj);
          int itemId1 = cisD.getItemId();
          if (itemId1 > itemId) {
            break;
          }
          if (itemId1 < itemId) {
            throw new RemoteException(
              "Error. ContractBean.getSubstitutions(). Programm logic error");
          }
          if (itemId1 == itemId) {
            flag = false;
            itemSubstView = ContractItemSubstView.createValue();
            itemSubstView.setContractItemSubstId(cisD.getContractItemSubstId());
            itemSubstView.setContractId(itemSubstViewWrk.getContractId());
            itemSubstView.setItemId(itemSubstViewWrk.getItemId());
            itemSubstView.setItemDesc(itemSubstViewWrk.getItemDesc());
            itemSubstView.setItemSku(itemSubstViewWrk.getItemSku());
            itemSubstView.setItemUom(itemSubstViewWrk.getItemUom());
            itemSubstView.setItemSize(itemSubstViewWrk.getItemSize());
            itemSubstView.setItemPack(itemSubstViewWrk.getItemPack());
            itemSubstView.setSustStatusCd(cisD.getSubstStatusCd());
            int substItemId = cisD.getSubstItemId();
            int substItemId1 = 0;
            int kk = 0;
            for (; kk < substItemDV.size(); kk++) {
              ItemData siD = (ItemData) substItemDV.get(kk);
              substItemId1 = siD.getItemId();
              if (substItemId1 > substItemId) {
                break;
              }
              if (substItemId1 == substItemId) {
                itemSubstView.setSubstItemId(substItemId);
                itemSubstView.setSubstItemDesc(siD.getShortDesc());
                itemSubstView.setSubstItemSku(siD.getSkuNum());
                for (int xx = 0; xx < substItemUomDV.size(); xx++) {
                  ItemMetaData imD = (ItemMetaData) substItemUomDV.get(xx);
                  if (imD.getItemId() == substItemId1) {
                    if ("UOM".equals(imD.getNameValue())) {
                      itemSubstView.setSubstItemUom(imD.getValue());
                    } else if ("SIZE".equals(imD.getNameValue())) {
                      itemSubstView.setSubstItemSize(imD.getValue());
                    } else if ("PACK".equals(imD.getNameValue())) {
                      itemSubstView.setSubstItemPack(imD.getValue());
                    }
                    continue;
                  }
                  if (imD.getItemId() > substItemId1) {
                    break;
                  }
                }
                break;
              }
            }
            if (kk == substItemDV.size() || substItemId1 > substItemId) {
              throw new RemoteException("Error. ContractBean.getSubstitutions(). ContractItemSubst object does not have a substitution item. ContactItemSubstId: " +
                                        cisD.getContractItemSubstId());
            }
            substitutions.add(itemSubstView);
          }
          if (itemId1 > itemId && flag == true) {
            substitutions.add(itemSubstViewWrk);
            break;
          }
        }
        if (flag == true) {
          substitutions.add(itemSubstViewWrk);
        }
      }
    } catch (javax.naming.NamingException exc) {
      String em =
        "Error. ContractBean.getSubstitutions() Naming Exception happened. " +
        exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } catch (SQLException exc) {
      String em =
        "Error. ContractBean.getSubstitutions() SQL Exception happened. " +
        exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } finally {
      try {
        if (con != null) con.close();
      } catch (SQLException exc) {
        String em =
          "Error. ContractBean.getSubstitutions() SQL Exception happened. " +
          exc.getMessage();
        logError(em);
        throw new RemoteException(em);
      }
    }
    return substitutions;
  }

  /**
   * Gets substitutions for the contract.
   * @param pItemIds  the vector of item identifiers.
   = @param pContracts the conllection of ContractData objects
   * @param pIncludeNull the flag, which indicates to include contract item with no substitutions
   * @return vector of ContractItemSubstView object
   * @throws            RemoteException Required by EJB 1.0
   */

  public ContractItemSubstViewVector getSubstitutions(IdVector pItemIds,
    ContractDataVector pContracts, boolean pIncludeNull) throws RemoteException {
    Connection con = null;
    ContractItemSubstViewVector substitutions = new ContractItemSubstViewVector();
    if (pItemIds == null || pItemIds.size() == 0 || pContracts == null ||
        pContracts.size() == 0) {
      return substitutions;
    }
    try {
      Object[] contractA = pContracts.toArray();
      IdVector contractIds = new IdVector();
      for (int ii = 0; ii < contractA.length; ii++) {
        ContractData cD = (ContractData) contractA[ii];
        if (RefCodeNames.CONTRACT_STATUS_CD.ACTIVE.equals(cD.
          getContractStatusCd()) ||
            RefCodeNames.CONTRACT_STATUS_CD.LIVE.equals(cD.getContractStatusCd()))
          contractIds.add(new Integer(cD.getContractId()));
      }
      DBCriteria dbc;
      con = getConnection();

      //Contract filter
      dbc = new DBCriteria();
      dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIds);
      String contractItemReq = ContractItemDataAccess.getSqlSelectIdOnly(
        ContractItemDataAccess.ITEM_ID, dbc);
      //Contract structure
      dbc = new DBCriteria();
      dbc.addOneOf(ContractItemDataAccess.ITEM_ID, pItemIds);
      dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIds);
      dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
      dbc.addOrderBy(ContractItemDataAccess.CONTRACT_ID);
      ContractItemDataVector contractItemDV = ContractItemDataAccess.select(con,
        dbc);
      Object[] contractItemA = contractItemDV.toArray();
      //Substitutions
      dbc = new DBCriteria();
      dbc.addOneOf(ContractItemSubstDataAccess.ITEM_ID, pItemIds);
      dbc.addOneOf(ContractItemSubstDataAccess.CONTRACT_ID, contractIds);
      String itemSubstReq = ContractItemSubstDataAccess.getSqlSelectIdOnly(
        ContractItemSubstDataAccess.ITEM_ID, dbc);
      String substItemReq = ContractItemSubstDataAccess.getSqlSelectIdOnly(
        ContractItemSubstDataAccess.SUBST_ITEM_ID, dbc);
      dbc.addOrderBy(ContractItemSubstDataAccess.ITEM_ID);
      dbc.addOrderBy(ContractItemSubstDataAccess.CONTRACT_ID);
      ContractItemSubstDataVector contractItemSubstDV =
        ContractItemSubstDataAccess.select(con, dbc);
      //Substitute items
      dbc = new DBCriteria();
      dbc.addOneOf(ItemDataAccess.ITEM_ID, substItemReq);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector substItemDV = ItemDataAccess.select(con, dbc);
      //Substitute uom,size,pack
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, substItemReq);
      LinkedList prop = new LinkedList();
      prop.add("UOM");
      prop.add("SIZE");
      prop.add("PACK");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, prop);
      dbc.addOrderBy(ItemMetaDataAccess.ITEM_ID);
      ItemMetaDataVector substItemUomDV = ItemMetaDataAccess.select(con, dbc);
      //Substitute items manufacturers
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, substItemReq);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      ItemMappingDataVector substItemMappingDV = ItemMappingDataAccess.select(
        con, dbc);
      IdVector substItemMfgIds = new IdVector();
      for (int ii = 0; ii < substItemMappingDV.size(); ii++) {
        ItemMappingData imD = (ItemMappingData) substItemMappingDV.get(ii);
        substItemMfgIds.add(new Integer(imD.getBusEntityId()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, substItemMfgIds);
      BusEntityDataVector substItemMfgDV = BusEntityDataAccess.select(con, dbc);
      //Items to substitute
      dbc = new DBCriteria();
      if (pIncludeNull) { //select all contract items
        dbc.addOneOf(ItemDataAccess.ITEM_ID, pItemIds);
      } else { //select only items with substitutions
        dbc.addOneOf(ItemDataAccess.ITEM_ID, itemSubstReq);
      }
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
      //Item uom, size, pack
      dbc = new DBCriteria();
      if (pIncludeNull) { //select all contract items
        dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, pItemIds);
      } else { //select only items with substitutions
        dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, itemSubstReq);
      }
      prop = new LinkedList();
      prop.add("UOM");
      prop.add("SIZE");
      prop.add("PACK");
      dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, prop);
      dbc.addOrderBy(ItemDataAccess.ITEM_ID);
      ItemMetaDataVector itemUomDV = ItemMetaDataAccess.select(con, dbc);
      //Items manufacturers
      dbc = new DBCriteria();
      dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, pItemIds);
      dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                     RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
      dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
      ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con,
        dbc);
      IdVector itemMfgIds = new IdVector();
      for (int ii = 0; ii < itemMappingDV.size(); ii++) {
        ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ii);
        itemMfgIds.add(new Integer(imD.getBusEntityId()));
      }
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);
      BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(con, dbc);
      //Combine altogether
      for (int ii = 0, jj = 0, ff = 0, mm = 0, uu = 0; ii < itemDV.size(); ii++) {
        ItemData iD = (ItemData) itemDV.get(ii);
        int itemId = iD.getItemId();
        ContractItemSubstView itemSubstViewWrk = ContractItemSubstView.
                                                 createValue();
        itemSubstViewWrk.setItemId(itemId);
        itemSubstViewWrk.setItemDesc(iD.getShortDesc());
        itemSubstViewWrk.setItemSku(iD.getSkuNum());
        //Item meta
        while (uu < itemUomDV.size()) {
          ItemMetaData imD = (ItemMetaData) itemUomDV.get(uu);
          if (imD.getItemId() == itemId) {
            uu++;
            if ("UOM".equals(imD.getNameValue())) {
              itemSubstViewWrk.setItemUom(imD.getValue());
            } else if ("SIZE".equals(imD.getNameValue())) {
              itemSubstViewWrk.setItemSize(imD.getValue());
            } else if ("PACK".equals(imD.getNameValue())) {
              itemSubstViewWrk.setItemPack(imD.getValue());
            }
            continue;
          }
          if (imD.getItemId() > itemId) {
            break;
          }
        }
        //Item Mfg
        for (; ff < itemMappingDV.size(); ff++) {
          ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ff);
          if (imD.getItemId() > itemId) {
            break;
          }
          if (imD.getItemId() == itemId) {
            int beId = imD.getBusEntityId();
            itemSubstViewWrk.setItemMfgId(beId);
            itemSubstViewWrk.setItemMfgSku(imD.getItemNum());
            for (int bb = 0; bb < itemMfgDV.size(); bb++) {
              BusEntityData beD = (BusEntityData) itemMfgDV.get(bb);
              if (beD.getBusEntityId() == beId) {
                itemSubstViewWrk.setItemMfgName(beD.getShortDesc());
                break;
              }
            }
          }
        }
        //Scatter items through contracts
        for (; mm < contractItemDV.size(); mm++) {
          ContractItemData ciM = (ContractItemData) contractItemDV.get(mm);
          if (ciM.getItemId() > itemId) {
            break; //next item coming
          }
          if (ciM.getItemId() == itemId) { //Process item - contract
            ContractItemSubstView itemSubstViewWrk1 = ContractItemSubstView.
              createValue();
            itemSubstViewWrk1.setItemId(itemSubstViewWrk.getItemId());
            itemSubstViewWrk1.setItemDesc(itemSubstViewWrk.getItemDesc());
            itemSubstViewWrk1.setItemSku(itemSubstViewWrk.getItemSku());
            itemSubstViewWrk1.setItemUom(itemSubstViewWrk.getItemUom());
            itemSubstViewWrk1.setItemSize(itemSubstViewWrk.getItemSize());
            itemSubstViewWrk1.setItemPack(itemSubstViewWrk.getItemPack());
            itemSubstViewWrk1.setItemMfgId(itemSubstViewWrk.getItemMfgId());
            itemSubstViewWrk1.setItemMfgName(itemSubstViewWrk.getItemMfgName());
            itemSubstViewWrk1.setItemMfgSku(itemSubstViewWrk.getItemMfgSku());
            //Item - Contract
            int contractId = ciM.getContractId();
            itemSubstViewWrk1.setContractId(contractId);
            //Set contract name
            for (int rr = 0; rr < contractA.length; rr++) {
              ContractData cD = (ContractData) contractA[rr];
              if (cD.getContractId() == contractId) {
                itemSubstViewWrk1.setContractName(cD.getShortDesc());
              }
            }
            //Find substitution
            boolean flag = true;
            for (; jj < contractItemSubstDV.size(); jj++) { //Find substitution
              ContractItemSubstData cisD = (ContractItemSubstData)
                                           contractItemSubstDV.get(jj);
              int itemId1 = cisD.getItemId();
              int contractId1 = cisD.getContractId();
              if (itemId1 > itemId || contractId1 > contractId) {
                break;
              }
              if (itemId1 == itemId && contractId1 == contractId) { //Substitution found
                flag = false;
                ContractItemSubstView itemSubstView = ContractItemSubstView.
                  createValue();
                itemSubstView.setItemId(itemSubstViewWrk1.getItemId());
                itemSubstView.setItemDesc(itemSubstViewWrk1.getItemDesc());
                itemSubstView.setItemSku(itemSubstViewWrk1.getItemSku());
                itemSubstView.setItemUom(itemSubstViewWrk1.getItemUom());
                itemSubstView.setItemSize(itemSubstViewWrk1.getItemSize());
                itemSubstView.setItemPack(itemSubstViewWrk1.getItemPack());
                itemSubstView.setContractId(itemSubstViewWrk1.getContractId());
                itemSubstView.setContractName(itemSubstViewWrk1.getContractName());
                itemSubstView.setItemMfgId(itemSubstViewWrk1.getItemMfgId());
                itemSubstView.setItemMfgName(itemSubstViewWrk1.getItemMfgName());
                itemSubstView.setItemMfgSku(itemSubstViewWrk1.getItemMfgSku());
                itemSubstView.setContractItemSubstId(cisD.
                  getContractItemSubstId());
                itemSubstView.setSustStatusCd(cisD.getSubstStatusCd());
                int substItemId = cisD.getSubstItemId();
                int substItemId1 = 0;
                int kk = 0;
                for (; kk < substItemDV.size(); kk++) { //Find substitute item data
                  ItemData siD = (ItemData) substItemDV.get(kk);
                  substItemId1 = siD.getItemId();
                  if (substItemId1 > substItemId) {
                    break;
                  }
                  if (substItemId1 == substItemId) { //Substitute item data found
                    itemSubstView.setSubstItemId(substItemId);
                    itemSubstView.setSubstItemDesc(siD.getShortDesc());
                    itemSubstView.setSubstItemSku(siD.getSkuNum());
                    for (int xx = 0; xx < substItemUomDV.size(); xx++) { //Set substitution meta attributes
                      ItemMetaData imD = (ItemMetaData) substItemUomDV.get(xx);
                      if (imD.getItemId() == substItemId1) {
                        if ("UOM".equals(imD.getNameValue())) {
                          itemSubstView.setSubstItemUom(imD.getValue());
                        } else if ("SIZE".equals(imD.getNameValue())) {
                          itemSubstView.setSubstItemSize(imD.getValue());
                        } else if ("PACK".equals(imD.getNameValue())) {
                          itemSubstView.setSubstItemPack(imD.getValue());
                        }
                        continue;
                      }
                      if (imD.getItemId() > substItemId1) {
                        break;
                      }
                    } //Set substitution meta attributes
                    //Subst Item Mfg
                    mmm:
                      for (int cc = 0; cc < substItemMappingDV.size(); cc++) {
                      ItemMappingData imD = (ItemMappingData)
                                            substItemMappingDV.get(cc);
                      if (imD.getItemId() == substItemId) {
                        int beId = imD.getBusEntityId();
                        itemSubstView.setSubstItemMfgId(beId);
                        itemSubstView.setSubstItemMfgSku(imD.getItemNum());
                        for (int bb = 0; bb < substItemMfgDV.size(); bb++) {
                          BusEntityData beD = (BusEntityData) substItemMfgDV.
                                              get(bb);
                          if (beD.getBusEntityId() == beId) {
                            itemSubstView.setSubstItemMfgName(beD.getShortDesc());
                            break mmm;
                          }
                        }
                      }
                    }
                    //add substitution to vector
                    substitutions.add(itemSubstView);
                    break;
                  } //Substitution item data found
                } //Find substitute item data
              } //Substution found
            } //Find substitution
            if (flag == true && pIncludeNull) {
              substitutions.add(itemSubstViewWrk1);
            }
          } //Process item - contract
        }
      }
    } catch (javax.naming.NamingException exc) {
      String em =
        "Error. ContractBean.getSubstitutions() Naming Exception happened. " +
        exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } catch (SQLException exc) {
      String em =
        "Error. ContractBean.getSubstitutions() SQL Exception happened. " +
        exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } finally {
      try {
        if (con != null) con.close();
      } catch (SQLException exc) {
        String em =
          "Error. ContractBean.getSubstitutions() SQL Exception happened. " +
          exc.getMessage();
        logError(em);
        throw new RemoteException(em);
      }
    }
    return substitutions;
  }

  //*****************************************************************************
   /**
    * Creates new  substitutions for the contract.
    * @param pItemIds  the vector of item identifiers.
    * @param pSubstItemIds  the vector of substitution item identifiers.
    * @param pContractId the contract identifier.
    * @param pUser the user login name
    * @throws            RemoteException Required by EJB 1.0
    */
   public void addSubstitutions(IdVector pItemIds, IdVector pSubstItemIds,
                                int pContractId, String pUser) throws
     RemoteException {
     Connection con = null;
     try {
       DBCriteria dbc;
       con = getConnection();
       //Pick up existing substitutions
       dbc = new DBCriteria();
       dbc.addEqualTo(ContractItemSubstDataAccess.CONTRACT_ID, pContractId);
       dbc.addOneOf(ContractItemSubstDataAccess.ITEM_ID, pItemIds);
       dbc.addOneOf(ContractItemSubstDataAccess.SUBST_ITEM_ID, pSubstItemIds);
       ContractItemSubstDataVector contractItemSubstDV =
         ContractItemSubstDataAccess.select(con, dbc);
       Object[] existSubst = contractItemSubstDV.toArray();
       //Make substitution objects
       ContractItemSubstData[] substitutions = new ContractItemSubstData[
                                               pItemIds.size() *
                                               pSubstItemIds.size()];
       for (int ii = 0; ii < pItemIds.size(); ii++) {
         Integer itemIdI = (Integer) pItemIds.get(ii);
         int itemId = itemIdI.intValue();
         for (int jj = 0; jj < pSubstItemIds.size(); jj++) {
           Integer substItemIdI = (Integer) pSubstItemIds.get(jj);
           int substItemId = substItemIdI.intValue();
           if (itemId == substItemId) {
             continue;
           }
           int kk = 0;
           //Check if pair already exists
           for (; kk < existSubst.length; kk++) {
             ContractItemSubstData cisD = (ContractItemSubstData) existSubst[kk];
             if (itemId == cisD.getItemId() &&
                 substItemId == cisD.getSubstItemId()) {
               break;
             }
           }
           //Create new substitution if not found
           if (kk == existSubst.length) {
             ContractItemSubstData cisD = ContractItemSubstData.createValue();
             cisD.setContractId(pContractId);
             cisD.setItemId(itemId);
             cisD.setSubstItemId(substItemId);
             cisD.setSubstStatusCd(RefCodeNames.SUBST_STATUS_CD.ACTIVE);
             cisD.setAddBy(pUser);
             cisD.setModBy(pUser);
             ContractItemSubstDataAccess.insert(con, cisD);
           }
         }
       }
     } catch (javax.naming.NamingException exc) {
       String em =
         "Error. ContractBean.addSubstitutions Naming Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } catch (SQLException exc) {
       String em =
         "Error. ContractBean.addSubstitutions SQL Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } finally {
       try {
         if (con != null) con.close();
       } catch (SQLException exc) {
         String em =
           "Error. ContractBean.addSubstitutions SQL Exception happened. " +
           exc.getMessage();
         logError(em);
         throw new RemoteException(em);
       }
     }
     return;
   }

  //*****************************************************************************
   /**
    * Creates new  substitutions for the contract.
    * @param pSubstitutions  the collection of ContractItemSubstData objects, with populated itemId, contractId, substItemId fields
    * @param pUser the user login name
    * @throws            RemoteException Required by EJB 1.0
    */
   public void addSubstitutions(ContractItemSubstDataVector pSubstitutions,
                                String pUser) throws RemoteException {
     Connection con = null;
     try {
       DBCriteria dbc;
       con = getConnection();
       //Pick up existing substitutions
       for (int ii = 0; ii < pSubstitutions.size(); ii++) {
         ContractItemSubstData cisD = (ContractItemSubstData) pSubstitutions.
                                      get(ii);
         int itemId = cisD.getItemId();
         int subItemId = cisD.getSubstItemId();
         if (itemId == subItemId) {
           continue;
         }
         dbc = new DBCriteria();
         dbc.addEqualTo(ContractItemSubstDataAccess.ITEM_ID, itemId);
         dbc.addEqualTo(ContractItemSubstDataAccess.CONTRACT_ID,
                        cisD.getContractId());
         dbc.addEqualTo(ContractItemSubstDataAccess.SUBST_ITEM_ID, subItemId);
         ContractItemSubstDataVector cisDV = ContractItemSubstDataAccess.select(
           con, dbc);
         if (cisDV.size() == 0) {
           cisD.setSubstStatusCd(RefCodeNames.SUBST_STATUS_CD.ACTIVE);
           cisD.setAddBy(pUser);
           cisD.setModBy(pUser);
           ContractItemSubstDataAccess.insert(con, cisD);
         }
       }
     } catch (javax.naming.NamingException exc) {
       String em =
         "Error. ContractBean.addSubstitutions Naming Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } catch (SQLException exc) {
       String em =
         "Error. ContractBean.addSubstitutions SQL Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } finally {
       try {
         if (con != null) con.close();
       } catch (SQLException exc) {
         String em =
           "Error. ContractBean.addSubstitutions SQL Exception happened. " +
           exc.getMessage();
         logError(em);
         throw new RemoteException(em);
       }
     }
     return;
   }

  //*****************************************************************************
   /**
    * Creates new  substitutions for the contract.
    * @param pContractItemSubstIds  the vector of contract item substitution identifiers.
    * @param pUser the user login name (not in use now)
    * @throws            RemoteException Required by EJB 1.0
    */
   public void removeSubstitutions(IdVector pContractItemSubstIds, String pUser) throws
     RemoteException {
     Connection con = null;
     try {
       DBCriteria dbc;
       con = getConnection();
       //Pick up existing substitutions
       dbc = new DBCriteria();
       dbc.addOneOf(ContractItemSubstDataAccess.CONTRACT_ITEM_SUBST_ID,
                    pContractItemSubstIds);
       ContractItemSubstDataAccess.remove(con, dbc);
     } catch (javax.naming.NamingException exc) {
       String em =
         "Error. ContractBean.addSubstitutions Naming Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } catch (SQLException exc) {
       String em =
         "Error. ContractBean.addSubstitutions SQL Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } finally {
       try {
         if (con != null) con.close();
       } catch (SQLException exc) {
         String em =
           "Error. ContractBean.addSubstitutions SQL Exception happened. " +
           exc.getMessage();
         logError(em);
         throw new RemoteException(em);
       }
     }
     return;
   }

  //************************************************************************************
   /**
    * Gets account product information.
    * @param pItemIds  the vector of item identifiers.
    = @param pContracts the conllection of ContractData objects
    * @return list of ContractItemPriceView objects
    * @throws            RemoteException Required by EJB 1.0
    */

   public ContractItemPriceViewVector getPriceItems(IdVector pItemIds,
     ContractDataVector pContracts) throws RemoteException {
     Connection con = null;
     ContractItemPriceViewVector priceItems = new ContractItemPriceViewVector();
     if (pItemIds == null || pItemIds.size() == 0 || pContracts == null ||
         pContracts.size() == 0) {
       return priceItems;
     }
     try {
       Object[] contractA = pContracts.toArray();
       IdVector contractIds = new IdVector();
       IdVector catalogIds = new IdVector();
       for (int ii = 0; ii < contractA.length; ii++) {
         ContractData cD = (ContractData) contractA[ii];
         if (RefCodeNames.CONTRACT_STATUS_CD.ACTIVE.equals(cD.
           getContractStatusCd()) ||
             RefCodeNames.CONTRACT_STATUS_CD.LIVE.equals(cD.getContractStatusCd()))
           contractIds.add(new Integer(cD.getContractId()));
         catalogIds.add(new Integer(cD.getCatalogId()));
       }
       DBCriteria dbc;
       con = getConnection();

       //Contract filter
       dbc = new DBCriteria();
       dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIds);
       String contractItemReq = ContractItemDataAccess.getSqlSelectIdOnly(
         ContractItemDataAccess.ITEM_ID, dbc);
       //Contract items
       dbc = new DBCriteria();
       dbc.addOneOf(ContractItemDataAccess.ITEM_ID, pItemIds);
       dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contractIds);
       dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
       dbc.addOrderBy(ContractItemDataAccess.CONTRACT_ID);
       ContractItemDataVector contractItemDV = ContractItemDataAccess.select(
         con, dbc);
       //Items
       dbc = new DBCriteria();
       dbc.addOneOf(ItemDataAccess.ITEM_ID, pItemIds);
       dbc.addOrderBy(ItemDataAccess.ITEM_ID);
       ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
       //Item uom, size, pack
       dbc = new DBCriteria();
       dbc.addOneOf(ItemMetaDataAccess.ITEM_ID, pItemIds);
       LinkedList prop = new LinkedList();
       prop.add("UOM");
       prop.add("SIZE");
       prop.add("PACK");
       dbc.addOneOf(ItemMetaDataAccess.NAME_VALUE, prop);
       dbc.addOrderBy(ItemDataAccess.ITEM_ID);
       ItemMetaDataVector itemPropDV = ItemMetaDataAccess.select(con, dbc);
       //Items manufacturers
       dbc = new DBCriteria();
       dbc.addOneOf(ItemMappingDataAccess.ITEM_ID, pItemIds);
       dbc.addEqualTo(ItemMappingDataAccess.ITEM_MAPPING_CD,
                      RefCodeNames.ITEM_MAPPING_CD.ITEM_MANUFACTURER);
       dbc.addOrderBy(ItemMappingDataAccess.ITEM_ID);
       ItemMappingDataVector itemMappingDV = ItemMappingDataAccess.select(con,
         dbc);
       IdVector itemMfgIds = new IdVector();
       for (int ii = 0; ii < itemMappingDV.size(); ii++) {
         ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ii);
         itemMfgIds.add(new Integer(imD.getBusEntityId()));
       }
       dbc = new DBCriteria();
       dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, itemMfgIds);
       BusEntityDataVector itemMfgDV = BusEntityDataAccess.select(con, dbc);

       //Item Distributors
       dbc = new DBCriteria();
       dbc.addOneOf(CatalogStructureDataAccess.ITEM_ID, pItemIds);
       dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catalogIds);
       IdVector distributorIds = CatalogStructureDataAccess.selectIdOnly(con,
         CatalogStructureDataAccess.BUS_ENTITY_ID, dbc);
       dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
       CatalogStructureDataVector catalogStructureDV =
         CatalogStructureDataAccess.select(con, dbc);
       dbc = new DBCriteria();
       dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distributorIds);
       BusEntityDataVector distributors = BusEntityDataAccess.select(con, dbc);

       //Combine altogether
       for (int ii = 0, ff = 0, mm = 0, uu = 0, dd = 0; ii < itemDV.size(); ii++) {
         ItemData iD = (ItemData) itemDV.get(ii);
         int itemId = iD.getItemId();
         ContractItemPriceView itemPriceViewWrk = ContractItemPriceView.
                                                  createValue();
         itemPriceViewWrk.setItemId(itemId);
         itemPriceViewWrk.setItemDesc(iD.getShortDesc());
         itemPriceViewWrk.setItemSku(iD.getSkuNum());
         //Item meta
         while (uu < itemPropDV.size()) {
           ItemMetaData imD = (ItemMetaData) itemPropDV.get(uu);
           if (imD.getItemId() == itemId) {
             uu++;
             if ("UOM".equals(imD.getNameValue())) {
               itemPriceViewWrk.setItemUom(imD.getValue());
             } else if ("SIZE".equals(imD.getNameValue())) {
               itemPriceViewWrk.setItemSize(imD.getValue());
             } else if ("PACK".equals(imD.getNameValue())) {
               itemPriceViewWrk.setItemPack(imD.getValue());
             }
             continue;
           }
           if (imD.getItemId() > itemId) {
             break;
           }
         }
         //Item Mfg
         for (; ff < itemMappingDV.size(); ff++) {
           ItemMappingData imD = (ItemMappingData) itemMappingDV.get(ff);
           if (imD.getItemId() > itemId) {
             break;
           }
           if (imD.getItemId() == itemId) {
             int beId = imD.getBusEntityId();
             itemPriceViewWrk.setItemMfgId(beId);
             itemPriceViewWrk.setItemMfgSku(imD.getItemNum());
             for (int bb = 0; bb < itemMfgDV.size(); bb++) {
               BusEntityData beD = (BusEntityData) itemMfgDV.get(bb);
               if (beD.getBusEntityId() == beId) {
                 itemPriceViewWrk.setItemMfgName(beD.getShortDesc());
                 break;
               }
             }
           }
         }
         //Scatter items through contracts
         for (; mm < contractItemDV.size(); mm++) {
           ContractItemData ciM = (ContractItemData) contractItemDV.get(mm);
           if (ciM.getItemId() > itemId) {
             break; //next item coming
           }
           if (ciM.getItemId() == itemId) { //Process item - contract
             ContractItemPriceView itemPriceView = itemPriceViewWrk.copy();
             //Item - Contract
             int contractId = ciM.getContractId();
             itemPriceView.setContractId(contractId);
             itemPriceView.setPrice(ciM.getAmount());
             itemPriceView.setDistCost(ciM.getDistCost());
             int catalogId = 0;
             //Set contract name
             for (int rr = 0; rr < contractA.length; rr++) {
               ContractData cD = (ContractData) contractA[rr];
               if (cD.getContractId() == contractId) {
                 itemPriceView.setContractName(cD.getShortDesc());
                 catalogId = cD.getCatalogId();
               }
             }
             //Find distributor
             for (int dd1 = dd; dd1 < catalogStructureDV.size(); dd1++) {
               CatalogStructureData csD = (CatalogStructureData)
                                          catalogStructureDV.get(dd1);
               int catItemId = csD.getItemId();
               if (catItemId > itemId) {
                 dd = dd1;
                 break;
               }
               if (catItemId == itemId) {
                 if (csD.getCatalogId() == catalogId) {
                   // Set the customer SKU and description.
                   String v = csD.getCustomerSkuNum();
                   if (null == v) v = "";
                   itemPriceView.setItemCustSku(v);
                   v = csD.getShortDesc();
                   if (null == v) v = "";
                   itemPriceView.setItemCustDesc(v);
                   int distId = csD.getBusEntityId();
                   itemPriceView.setDistId(distId);
                   for (int jj = 0; jj < distributors.size(); jj++) {
                     BusEntityData dist = (BusEntityData) distributors.get(jj);
                     if (distId == dist.getBusEntityId()) {
                       itemPriceView.setDistName(dist.getShortDesc());
                     }
                   }
                   break;
                 }
               }
             }
             priceItems.add(itemPriceView);
           } //Process item - contract
         }
       }
     } catch (javax.naming.NamingException exc) {
       String em =
         "Error. ContractBean.getPriceItems() Naming Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } catch (SQLException exc) {
       String em =
         "Error. ContractBean.getPriceItems() SQL Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } finally {
       try {
         if (con != null) con.close();
       } catch (SQLException exc) {
         String em =
           "Error. ContractBean.getPriceItems() SQL Exception happened. " +
           exc.getMessage();
         logError(em);
         throw new RemoteException(em);
       }
     }
     return priceItems;
   }

  //************************************************************************************
   /**
    * @param pPriceItems  the list of ContractItemPriceView object.
    = @param pUser the user login name
    * @throws            RemoteException Required by EJB 1.0
    */
   public void updateContractItemCosts(ContractItemPriceViewVector pPriceItems,
                                       String pUser) throws RemoteException {
     Connection con = null;
     ContractItemPriceViewVector priceItems = new ContractItemPriceViewVector();
     ContractItemPriceView cipVw = null;
     if (pPriceItems == null) {
       return;
     }
     try {
       DBCriteria dbc;
       con = getConnection();
       for (int ii = 0; ii < pPriceItems.size(); ii++) {
         cipVw = (ContractItemPriceView) pPriceItems.get(ii);
         int itemId = cipVw.getItemId();
         int contractId = cipVw.getContractId();

         ContractData catD = ContractDataAccess.select(con, contractId);
         String locale = catD.getLocaleCd();
         //get number of digits after decimal point allowed for this locale
         DBCriteria crit = new DBCriteria();
         crit.addEqualTo(CurrencyDataAccess.LOCALE, locale);
         CurrencyDataVector currDV = CurrencyDataAccess.select(con, crit);
         CurrencyData curr = (CurrencyData)currDV.get(0);
         int decimalPlaces = curr.getDecimals();

         int catalogId = catD.getCatalogId();

         dbc = new DBCriteria();
         dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, itemId);
         dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, contractId);
         ContractItemDataVector ciDV = ContractItemDataAccess.select(con, dbc);
         if (ciDV.size() == 0) {
           throw new RemoteException(
             "No contract item object found. Contract id: " + contractId +
             " Item id: " + itemId);
         }
         if (ciDV.size() > 1) {
           throw new RemoteException(
             "More than one contract item object found. Contract id: " +
             contractId + " Item id: " + itemId);
         }
         ContractItemData ciD = (ContractItemData) ciDV.get(0);
         BigDecimal price = cipVw.getPrice();
         BigDecimal distCost = cipVw.getDistCost();
         if (price == null) {
           throw new RemoteException(
             "Undefined price for contract item. Contract id: " +
             contractId + " Item id: " + itemId);
         }
         if (distCost == null) {
           throw new RemoteException(
             "Undefined distributor cost for contract item. Contract id: " +
             contractId + " Item id: " + itemId);
         }
         List<String> errorMessLL = new ArrayList<String>();
         if(price.scale()>decimalPlaces){
        	 String errorMess = "The price for item id "+cipVw.getItemId()+" has too many decimal points.  " +
        	 "It can only have "+decimalPlaces+" decimal points for this currency";
        	 if (!errorMessLL.contains(errorMess)) {
        		 errorMessLL.add(errorMess);
        	 }
         }
         if(distCost.scale()>decimalPlaces){
        	 String errorMess = "The dist cost for item id "+cipVw.getItemId()+" has too many decimal points.  " +
        	 "It can only have "+decimalPlaces+" decimal points for this currency";
        	 if (!errorMessLL.contains(errorMess)) {
        		 errorMessLL.add(errorMess);
        	 }
         }

         if(errorMessLL.size()>0){
        	 throw new RemoteException(makeErrorString(errorMessLL));
         }

         if (!price.equals(ciD.getAmount()) || !distCost.equals(ciD.getDistCost())) {
           ciD.setAmount(price);
           ciD.setDistCost(distCost);
           ciD.setModBy(pUser);
           ContractItemDataAccess.update(con, ciD);
         }
       }
     } catch (DataNotFoundException exc) {
       String em = "updateCotractItemPrice() data not found. " +
                   "\n processing entry: " + cipVw +
                   "\n error: " + exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } catch (javax.naming.NamingException exc) {
       String em =
         "Error. ContractBean.updateCotractItemPrice() Naming Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } catch (SQLException exc) {
       String em =
         "Error. ContractBean.updateCotractItemPrice() SQL Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } finally {
       try {
         if (con != null) con.close();
       } catch (SQLException exc) {
         String em =
           "Error. ContractBean.updateCotractItemPrice() SQL Exception happened. " +
           exc.getMessage();
         logError(em);
         throw new RemoteException(em);
       }
     }
     return;
   }

  public void updateContractItemCustData(ContractItemPriceViewVector
                                         pPriceItems, String pUser) throws
    RemoteException {
    Connection con = null;
    ContractItemPriceViewVector priceItems = new ContractItemPriceViewVector();
    ContractItemPriceView cipVw = null;
    if (pPriceItems == null) {
      return;
    }
    try {
      DBCriteria dbc;
      con = getConnection();
      for (int ii = 0; ii < pPriceItems.size(); ii++) {
        cipVw = (ContractItemPriceView) pPriceItems.get(ii);
        int itemId = cipVw.getItemId();
        int contractId = cipVw.getContractId();

        ContractData catD = ContractDataAccess.select(con, contractId);
        int catalogId = catD.getCatalogId();

        dbc = new DBCriteria();
        dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, itemId);
        dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, contractId);
        ContractItemDataVector ciDV =
          ContractItemDataAccess.select(con, dbc);
        if (ciDV.size() == 0) {
          throw new RemoteException
            ("No contract item object found. Contract id: " +
             contractId + " Item id: " + itemId);
        }
        if (ciDV.size() > 1) {
          throw new RemoteException(
            "More than one contract item object found. Contract id: " +
            contractId + " Item id: " + itemId);
        }

        // Now update the item customer sku or customer description.
        DBCriteria c1 = new DBCriteria();
        c1.addEqualTo(CatalogStructureDataAccess.ITEM_ID, itemId);
        c1.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);
        c1.addEqualTo
          (CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
           RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
        CatalogStructureDataVector csdv =
          CatalogStructureDataAccess.select(con, c1);
        String custSkuReq = cipVw.getItemCustSku();
        String custDescReq = cipVw.getItemCustDesc();
        for (int i1 = 0; i1 < csdv.size(); i1++) {
          CatalogStructureData csd =
            (CatalogStructureData) csdv.get(i1);
          csd.setCustomerSkuNum(custSkuReq);
          csd.setShortDesc(custDescReq);

          CatalogStructureDataAccess.update
            (con, csd);
        }

      }
    } catch (DataNotFoundException exc) {
      String em = "updateContractItemCustData() data not found. " +
                  "\n processing entry: " + cipVw +
                  "\n error: " + exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } catch (javax.naming.NamingException exc) {
      String em = "updateContractItemCustData() Naming Exception happened. " +
                  exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } catch (SQLException exc) {
      String em = "updateContractItemCustData() SQL Exception happened. " +
                  exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } finally {
      try {
        if (con != null) con.close();
      } catch (SQLException exc) {
        String em = "updateContractItemCustData() SQL Exception happened. " +
                    exc.getMessage();
        logError(em);
        throw new RemoteException(em);
      }
    }
    return;
  }

  //************************************************************************************
   /**
    * Gets contract item ids, which are not a part of the catalog
    = @param pContractId the contract id
    * @return vector of contract item ids
    * @throws            RemoteException Required by EJB 1.0
    */
   public IdVector getNonCatalogItems(int pContractId) throws RemoteException {
     Connection con = null;
     IdVector extraItems = new IdVector();
     try {
       DBCriteria dbc;
       con = getConnection();
       ContractData contractD = ContractDataAccess.select(con, pContractId);
       int catalogId = contractD.getCatalogId();
       dbc = new DBCriteria();
       dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_ID, catalogId);
       dbc.addEqualTo(CatalogStructureDataAccess.CATALOG_STRUCTURE_CD,
                      RefCodeNames.CATALOG_STRUCTURE_CD.CATALOG_PRODUCT);
       dbc.addOrderBy(CatalogStructureDataAccess.ITEM_ID);
       IdVector catalogItems =
         CatalogStructureDataAccess.selectIdOnly(con,
                                                 CatalogStructureDataAccess.
                                                 ITEM_ID, dbc);
       dbc = new DBCriteria();
       dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
       dbc.addOrderBy(ContractItemDataAccess.ITEM_ID);
       ContractItemDataVector contractItemDV =
         ContractItemDataAccess.select(con, dbc);
       for (int ii = 0, jj = 0; ii < contractItemDV.size(); ii++) {
         ContractItemData ciD = (ContractItemData) contractItemDV.get(ii);
         int itemId = ciD.getItemId();
         if (jj >= catalogItems.size()) {
           extraItems.add(new Integer(ciD.getContractItemId()));
           continue;
         }
         for (; jj < catalogItems.size(); jj++) {
           Integer catalogItemIdI = (Integer) catalogItems.get(jj);
           if (catalogItemIdI.intValue() == itemId) {
             jj++;
             break;
           }
           if (catalogItemIdI.intValue() > itemId) {
             extraItems.add(new Integer(ciD.getContractItemId()));
             break;
           }
         }
       }
     } catch (DataNotFoundException exc) {
       String msg =
         "ContractBean.getNonCatalogItems. No contract found. Contract id: " +
         pContractId;
       logError(msg);
       throw new RemoteException(msg);
     } catch (javax.naming.NamingException exc) {
       String em =
         "ContractBean.getNonCatalogItems. Naming Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } catch (SQLException exc) {
       String em = "ContractBean.getNonCatalogItems. SQL Exception happened. " +
                   exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } finally {
       try {
         if (con != null) con.close();
       } catch (SQLException exc) {
         String em = "updateContractItemCustData() SQL Exception happened. " +
                     exc.getMessage();
         logError(em);
         throw new RemoteException(em);
       }
     }
     return extraItems;
   }

  //************************************************************************************
   /**
    * Gets contract item cost for account contracts  (all contracts
    * if pDistId = 0 and pDsitErpNum ==null
    * or filetred by distributor)
    = @param pAccoujntId the account identifier
    * @param pItemSkuNum the item sku number
    * @param pDistId the distributor id
    * @param pDistErpNum the distributor erp number (ignores if pDistId is not 0)
    * @return vector of ItemContractCostView objects
    * @throws            RemoteException Required by EJB 1.0
    */
   public ItemContractCostViewVector getItemContractCost(int pAccountId,
     int pItemSkuNum, int pDistId, String pDistErpNum) throws RemoteException {
     Connection con = null;
     ItemContractCostViewVector itemContractCostVwV = new
       ItemContractCostViewVector();
     try {
       DBCriteria dbc;
       con = getConnection();

       //Get Item Data
       dbc = new DBCriteria();
       dbc.addEqualTo(ItemDataAccess.SKU_NUM, pItemSkuNum);
       ItemDataVector itemDV = ItemDataAccess.select(con, dbc);
       if (itemDV.size() == 0) {
         String msg = "No item has the sku number = " + pItemSkuNum;
         throw new Exception(msg);
       }
       if (itemDV.size() > 1) {
         String msg =
           "More than 1 item have the same sku number. Sku number = " +
           pItemSkuNum;
         throw new Exception(msg);
       }
       ItemData itemD = (ItemData) itemDV.get(0);
       int itemId = itemD.getItemId();

       //Distributor Data if distributor id or erp number provided
       int distId = pDistId;
       BusEntityData distBusEntityD = null;
       if (distId == 0 && pDistErpNum != null &&
           pDistErpNum.trim().length() > 0) {
         dbc = new DBCriteria();
         dbc.addEqualTo(BusEntityDataAccess.ERP_NUM, pDistErpNum);
         dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                        RefCodeNames.BUS_ENTITY_TYPE_CD.DISTRIBUTOR);
         dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                        RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
         BusEntityDataVector busEntityDV = BusEntityDataAccess.select(con, dbc);
         if (busEntityDV.size() == 1) {
           distBusEntityD = (BusEntityData) busEntityDV.get(0);
           distId = distBusEntityD.getBusEntityId();
         }
       }

       //Get catalogs
       dbc = new DBCriteria();
       dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                      RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
       dbc.addEqualTo(CatalogAssocDataAccess.BUS_ENTITY_ID, pAccountId);
       String catAssocReq = CatalogAssocDataAccess.getSqlSelectIdOnly(
         CatalogAssocDataAccess.CATALOG_ID, dbc);

       dbc = new DBCriteria();
       dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,
                      RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
       dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD,
                      RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
       dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catAssocReq);
       String catReq = CatalogDataAccess.getSqlSelectIdOnly(CatalogDataAccess.
         CATALOG_ID, dbc);
       Object[] catalogStructDA = null;
       dbc = new DBCriteria();
       dbc.addOneOf(CatalogStructureDataAccess.CATALOG_ID, catReq);
       dbc.addEqualTo(CatalogStructureDataAccess.ITEM_ID, itemId);
       if (distId != 0) {
         dbc.addEqualTo(CatalogStructureDataAccess.BUS_ENTITY_ID, distId);
       }
       catReq = CatalogStructureDataAccess.getSqlSelectIdOnly(
         CatalogStructureDataAccess.CATALOG_ID, dbc);
       CatalogStructureDataVector catalogStructDV = CatalogStructureDataAccess.
         select(con, dbc);
       catalogStructDA = catalogStructDV.toArray();
       catalogStructDV = null;

       //Get contract
       dbc = new DBCriteria();
       dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                      RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
       dbc.addOneOf(ContractDataAccess.CATALOG_ID, catReq);
       String contrReq = ContractDataAccess.getSqlSelectIdOnly(
         ContractDataAccess.CONTRACT_ID, dbc);
       dbc.addOrderBy(ContractDataAccess.CONTRACT_ID);
       ContractDataVector contractDV = ContractDataAccess.select(con, dbc);

       //Get contract items
       dbc = new DBCriteria();
       dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, itemId);
       dbc.addOneOf(ContractItemDataAccess.CONTRACT_ID, contrReq);
       dbc.addOrderBy(ContractItemDataAccess.CONTRACT_ID);
       ContractItemDataVector contractItemDV = ContractItemDataAccess.select(
         con, dbc);

       //Combine data
       ItemContractCostView[] itemContrCostVwA = new ItemContractCostView[
                                                 contractItemDV.size()];
       for (int ii = 0, jj = 0; ii < itemContrCostVwA.length; ii++) {
         itemContrCostVwA[ii] = ItemContractCostView.createValue();
         ContractItemData ciD = (ContractItemData) contractItemDV.get(ii);
         int contractId = ciD.getContractId();
         itemContrCostVwA[ii].setItemId(ciD.getItemId());
         itemContrCostVwA[ii].setContractId(contractId);
         itemContrCostVwA[ii].setItemCost(ciD.getDistCost());
         for (; jj < contractDV.size(); jj++) {
           ContractData cD = (ContractData) contractDV.get(jj);
           if (cD.getContractId() < contractId) {
             continue;
           } else if (cD.getContractId() == contractId) {
             itemContrCostVwA[ii].setContractDesc(cD.getShortDesc());
             int catalogId = cD.getCatalogId();
             itemContrCostVwA[ii].setCatalogId(catalogId);
             for (int kk = 0; kk < catalogStructDA.length; kk++) {
               CatalogStructureData csD = (CatalogStructureData)
                                          catalogStructDA[kk];
               if (csD.getCatalogId() == catalogId) {
                 itemContrCostVwA[ii].setDistId(csD.getBusEntityId());
                 break;
               }
             }
             jj++;
             break;
           } else {
             break;
           }
         }
       }
       //Combine records for the same price and distributor
       for (int ii = 0; ii < itemContrCostVwA.length; ii++) {
         ItemContractCostView iccVw = itemContrCostVwA[ii];
         if (iccVw != null) {
           itemContractCostVwV.add(iccVw);
           iccVw.setCatalogId(0);
           iccVw.setContractId(0);
           BigDecimal cost = iccVw.getItemCost();
           cost.setScale(3, BigDecimal.ROUND_HALF_UP);
           int dId = iccVw.getDistId();
           for (int jj = ii + 1; jj < itemContrCostVwA.length; jj++) {
             ItemContractCostView iccVw1 = itemContrCostVwA[jj];
             if (iccVw1 != null && dId == iccVw1.getDistId()) {
               BigDecimal cost1 = iccVw1.getItemCost();
               cost1.setScale(3, BigDecimal.ROUND_HALF_UP);
               if (cost.equals(cost1)) {
                 String contrStr = iccVw.getContractDesc() + " * " +
                                   iccVw1.getContractDesc();
                 iccVw.setContractDesc(contrStr);
                 itemContrCostVwA[jj] = null;
               }
             }
           }
         }
       }
       IdVector distIdV = new IdVector();
       //Pick up distributors
       for (int ii = 0; ii < itemContractCostVwV.size(); ii++) {
         ItemContractCostView iccVw = (ItemContractCostView)
                                      itemContractCostVwV.get(ii);
         distIdV.add(new Integer(iccVw.getDistId()));
       }

       dbc = new DBCriteria();
       dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, distIdV);
       BusEntityDataVector busEntityDV = BusEntityDataAccess.select(con, dbc);
       for (int ii = 0; ii < itemContractCostVwV.size(); ii++) {
         ItemContractCostView iccVw = (ItemContractCostView)
                                      itemContractCostVwV.get(ii);
         int dId = iccVw.getDistId();
         for (int jj = 0; jj < busEntityDV.size(); jj++) {
           BusEntityData be = (BusEntityData) busEntityDV.get(jj);
           if (be.getBusEntityId() == dId) {
             iccVw.setDistDesc(be.getShortDesc());
             break;
           }
         }
       }

     } catch (Exception exc) {
       String msg = "ContractBean.getItemContracPrice. Error: " +
                    exc.getMessage();
       logError(msg);
       exc.printStackTrace();
       throw new RemoteException(msg);
     } finally {
       try {
         if (con != null) con.close();
       } catch (SQLException exc) {
         String em = "getItemContracPrice() SQL Exception happened. " +
                     exc.getMessage();
         logError(em);
         throw new RemoteException(em);
       }
     }
     return itemContractCostVwV;
   }

  public BusEntityDataVector getItemAccounts(int itemId) throws
    RemoteException
  {
    Connection con = null;
    BusEntityDataVector accounts = new BusEntityDataVector();
    try {
      con = getConnection();

      // 1. contract ids from contract_item by item_id
      IdVector contractIds = new IdVector();
      DBCriteria dbc = new DBCriteria();
      dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, itemId);
      contractIds = ContractItemDataAccess.selectIdOnly(con,
        ContractItemDataAccess.CONTRACT_ID, dbc);

      // 2. catalog ids from contract by contract ids
      dbc = new DBCriteria();
      dbc.addEqualTo(ContractDataAccess.CONTRACT_STATUS_CD,
                     RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
      dbc.addOneOf(ContractDataAccess.CONTRACT_ID, contractIds);
      IdVector catalogIds = ContractDataAccess.selectIdOnly(con,
        ContractDataAccess.CATALOG_ID, dbc);

      // 3. catalog ids for shopping catalogs
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogDataAccess.CATALOG_TYPE_CD,  RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
      dbc.addEqualTo(CatalogDataAccess.CATALOG_STATUS_CD, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
      dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogIds);
      catalogIds = CatalogDataAccess.selectIdOnly(con, dbc);

      // 4. bus_entity_id  from catalog_assoc by catalog ids
      dbc = new DBCriteria();
      dbc.addEqualTo(CatalogAssocDataAccess.CATALOG_ASSOC_CD,
                     RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
      dbc.addOneOf(CatalogAssocDataAccess.CATALOG_ID, catalogIds);
      IdVector accountIds = CatalogAssocDataAccess.selectIdOnly(con,
        CatalogAssocDataAccess.BUS_ENTITY_ID, dbc);

      // 5. accounts
      dbc = new DBCriteria();
      dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, accountIds);
      dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
      accounts = BusEntityDataAccess.select(con, dbc);
    } catch (javax.naming.NamingException exc) {
      String em =
        "Error. ContractBean.getItemAccounts() Naming Exception happened. " +
        exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } catch (SQLException exc) {
      String em =
        "Error. ContractBean.getItemAccounts() SQL Exception happened. " +
        exc.getMessage();
      logError(em);
      throw new RemoteException(em);
    } catch (Exception e) {

    } finally {
      try {
        if (con != null) con.close();
      } catch (SQLException exc) {
        String em =
          "Error. ContractBean.getItemAccounts() SQL Exception happened. " +
          exc.getMessage();
        logError(em);
        throw new RemoteException(em);
      }
    }
    return accounts;
  }

    public BusEntityDataVector getItemAccounts(int itemId, int storeId) throws RemoteException {
        Connection connection = null;
        BusEntityDataVector accounts = new BusEntityDataVector();
        try {
            IdVector accountIds = new IdVector();

            String sql =
                "SELECT DISTINCT " +
                    "catAndAcc.BUS_ENTITY_ID " +
                "FROM " +
                    "CLW_CATALOG_ASSOC catAndAcc, " +
                    "CLW_BUS_ENTITY_ASSOC accAndStore, " +
                    "CLW_CATALOG cat, " +
                    "CLW_CONTRACT contract, " +
                    "CLW_CONTRACT_ITEM item " +
                "WHERE " +
                    "catAndAcc.CATALOG_ASSOC_CD = ? " +
                    "AND catAndAcc.CATALOG_ID = cat.CATALOG_ID " +
                    "AND accAndStore.BUS_ENTITY_ASSOC_CD = ? " +
                    "AND accAndStore.BUS_ENTITY1_ID = catAndAcc.BUS_ENTITY_ID " +
                    "AND accAndStore.BUS_ENTITY2_ID = ? " +
                    "AND cat.CATALOG_STATUS_CD = ? " +
                    "AND cat.CATALOG_TYPE_CD = ? " +
                    "AND cat.CATALOG_ID = contract.CATALOG_ID " +
                    "AND contract.CONTRACT_STATUS_CD = ? " +
                    "AND contract.CONTRACT_ID = item.CONTRACT_ID " +
                    "AND item.ITEM_ID = ?";

            connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, RefCodeNames.CATALOG_ASSOC_CD.CATALOG_ACCOUNT);
            pstmt.setString(2, RefCodeNames.BUS_ENTITY_ASSOC_CD.ACCOUNT_STORE);
            pstmt.setInt(3, storeId);
            pstmt.setString(4, RefCodeNames.CATALOG_STATUS_CD.ACTIVE);
            pstmt.setString(5, RefCodeNames.CATALOG_TYPE_CD.SHOPPING);
            pstmt.setString(6, RefCodeNames.CONTRACT_STATUS_CD.ACTIVE);
            pstmt.setInt(7, itemId);
            ResultSet resSet = pstmt.executeQuery();
            if (resSet != null) {
                while (resSet.next()) {
                    Integer accountId = resSet.getInt(1);
                    if (accountId != null) {
                        if (!accountIds.contains(accountId)) {
                            accountIds.add(accountId);
                        }
                    }
                }
            }
            resSet.close();
            pstmt.close();

            DBCriteria dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID, accountIds);
            dbc.addOrderBy(BusEntityDataAccess.BUS_ENTITY_ID);
            accounts = BusEntityDataAccess.select(connection, dbc);

        } catch (SQLException ex) {
            String msg = "Error. ContractBean.getItemAccounts() SQL Exception happened. " + ex.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } catch (Exception ex) {
            String msg = "Error. ContractBean.getItemAccounts() Exception happened. " + ex.getMessage();
            logError(msg);
            throw new RemoteException(msg);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                String msg = "Error. ContractBean.getItemAccounts() SQL Exception happened. " + ex.getMessage();
                logError(msg);
            }
        }
        return accounts;
    }

  //************************************************************************************
   /**
    * @param pPriceItem  the ContractItemPriceView object.
    * @param pContractId an <code>int</code> value
    = @param pUser the user login name
    * @throws            RemoteException Required by EJB 1.0
    */
   public void updateContractItemCosts(ContractItemPriceView pPriceItem,
                                       int pContractId,
                                       String pUser) throws RemoteException {
     Connection con = null;
     if (pPriceItem == null) {
       return;
     }
     try {
       DBCriteria dbc;
       con = getConnection();
       int itemId = pPriceItem.getItemId();

       dbc = new DBCriteria();
       dbc.addEqualTo(ContractItemDataAccess.ITEM_ID, itemId);
       dbc.addEqualTo(ContractItemDataAccess.CONTRACT_ID, pContractId);
       ContractItemDataVector ciDV = ContractItemDataAccess.select(con, dbc);
       if (ciDV.size() == 0) {
         return;
       }
       if (ciDV.size() > 1) {
         throw new RemoteException(
           "More than one contract item object found. Contract id: " +
           pContractId + " Item id: " + itemId);
       }
       ContractItemData ciD = (ContractItemData) ciDV.get(0);
       BigDecimal price = pPriceItem.getPrice();
       BigDecimal distCost = pPriceItem.getDistCost();
       if (price == null) {
         throw new RemoteException(
           "Undefined price for contract item. Contract id: " +
           pContractId + " Item id: " + itemId);
       }
       if (distCost == null) {
         throw new RemoteException(
           "Undefined distributor cost for contract item. Contract id: " +
           pContractId + " Item id: " + itemId);
       }
       if (!price.equals(ciD.getAmount()) || !distCost.equals(ciD.getDistCost())) {
         ciD.setAmount(price);
         ciD.setDistCost(distCost);
         ciD.setModBy(pUser);
         ContractItemDataAccess.update(con, ciD);
       }
     } catch (javax.naming.NamingException exc) {
       String em =
         "Error. ContractBean.updateCotractItemPrice() Naming Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } catch (SQLException exc) {
       String em =
         "Error. ContractBean.updateCotractItemPrice() SQL Exception happened. " +
         exc.getMessage();
       logError(em);
       throw new RemoteException(em);
     } finally {
       try {
         if (con != null) con.close();
       } catch (SQLException exc) {
         String em =
           "Error. ContractBean.updateCotractItemPrice() SQL Exception happened. " +
           exc.getMessage();
         logError(em);
         throw new RemoteException(em);
       }
     }
     return;
   }

   /**
    * create contract if a contract not exists for a giving catalog
    * otherwise update the contract
    * @param pCatalogId the catalogId
    * @throws            RemoteException Required by EJB 1.0
    */
   public void updateContractByCatalog(int pCatalogId, String pUser, Date effDate) throws RemoteException {
     updateContractByCatalog(pCatalogId,  pUser, effDate, null);
   }
   public void updateContractByCatalog(int pCatalogId, String pUser, Date effDate, AccCategoryToCostCenterView pCategToCostCenterView) throws RemoteException {
	   // Get the items in the catalog.
	   IdVector itemids;
	   CatalogData catalogD;
	   try {
		   APIAccess apiAccess = getAPIAccess();
		   CatalogInformation cati = apiAccess.getCatalogInformationAPI();
		   catalogD = cati.getCatalog(pCatalogId);
		   // Get all the items.
		   itemids = cati.searchCatalogProducts(pCatalogId);
	   } catch (Exception e) {
		   String msg = "updateContractByCatalog: " + e.getMessage();
		   logError(msg);
		   throw new RemoteException(msg);
	   }

	   Connection conn = null;

	   try {
		   conn = getConnection();
		   ContractDataVector contracts = getContractsByCatalog(pCatalogId);
		   if (contracts.size() > 0){
			   ContractData contract = (ContractData) contracts.get(0);

			   ContractItemDataVector contractItems = getContractItems(contract.getContractId());
			   for (int i = 0; i < contractItems.size(); i++) {
				   ContractItemData cItem = (ContractItemData)contractItems.get(i);
				   Integer itemId = new Integer(cItem.getItemId());
				   if (itemids.contains(itemId)){
					   updateItem(cItem, pCatalogId, conn, pCategToCostCenterView);
				   }else{
					   ContractItemDataAccess.remove(conn, cItem.getContractItemId());
				   }
				   itemids.remove(itemId);
			   }

			   // create new contract item
			   for (int i = 0; i < itemids.size(); i++) {
				   ContractItemData newContractItem = ContractItemData.createValue();
				   newContractItem.setContractId(contract.getContractId());
				   newContractItem.setItemId(((Integer)itemids.get(i)).intValue());
				   newContractItem.setCurrencyCd("USD");
				   newContractItem.setAddBy(pUser);
				   newContractItem.setModBy(pUser);
				   newContractItem.setEffDate(effDate);
				   updateItem(newContractItem, pCatalogId, conn, pCategToCostCenterView);
			   }

		   }else{// no contract
			   ContractData contractData = ContractData.createValue();

			   contractData.setCatalogId(pCatalogId);
			   contractData.setContractStatusCd(catalogD.getCatalogStatusCd());
			   contractData.setContractTypeCd("UNKNOWN");
			   contractData.setFreightTableId(0);
			   contractData.setRefContractNum("0");
			   contractData.setLocaleCd("en_US");
			   contractData.setShortDesc(catalogD.getShortDesc());
			   contractData.setAddBy(pUser);
			   contractData.setModBy(pUser);
			   contractData.setEffDate(effDate);
			   ItemMetaDataVector itemMetaDV = new ItemMetaDataVector();
			   createFromCatalog(contractData);
		   }
	   } catch (Exception e) {
		   e.printStackTrace();
		   throw new RemoteException("updateContractByCatalog: " + e.getMessage());
	   } finally {
		   closeConnection(conn);
	   }
   }

   /**
    * create contract item if new, otherwise update with catalog item information
    * @param pUpdateContractItemData the ContractItemData
    * @param pCatalogId the catalogId
    * @throws            Exception
    */
   private ContractItemData updateItem(ContractItemData pUpdateContractItemData,
	          int pCatalogId, Connection conn,AccCategoryToCostCenterView pCategToCostCenterView) throws Exception {
	  APIAccess factory = new APIAccess();
	  CatalogInformation catalogInfoAPI = factory.getCatalogInformationAPI();
	  ProductData product = catalogInfoAPI.getCatalogClwProduct(pCatalogId, pUpdateContractItemData.getItemId(),0,0, pCategToCostCenterView);

	  // if new item
	  if (pUpdateContractItemData.getContractItemId() == 0){
		  pUpdateContractItemData.setAmount(new java.math.BigDecimal
	                         (product.getListPrice()));
		  pUpdateContractItemData.setDistCost(new java.math.BigDecimal
	                           (product.getListPrice()));

	        ContractItemDataAccess.insert(conn, pUpdateContractItemData);
	  }
	  // check if update is needed
	  else if (product.getListPrice() != pUpdateContractItemData.getAmount().doubleValue() ||
			  product.getListPrice() != pUpdateContractItemData.getDistCost().doubleValue()) {
		  pUpdateContractItemData.setAmount(new java.math.BigDecimal
				  (product.getListPrice()));
		  pUpdateContractItemData.setDistCost(new java.math.BigDecimal
				  (product.getListPrice()));

		  ContractItemDataAccess.update(conn, pUpdateContractItemData);
	  }

	  return pUpdateContractItemData;
	}

    public ContractDescDataVector getContractDescsByFreight(int freightId) throws RemoteException {
        ContractDescDataVector result = new ContractDescDataVector();
        Connection connection = null;
        try {
            connection = getConnection();
            DBCriteria criteria = new DBCriteria();
            criteria.addEqualTo(ContractDataAccess.FREIGHT_TABLE_ID, freightId);
            criteria.addOrderBy(ContractDataAccess.SHORT_DESC);

            ContractDataVector contractVec = ContractDataAccess.select(connection, criteria);
            if (contractVec == null || contractVec.size() == 0) {
                return result;
            }

            IdVector ids = new IdVector();
            for (int i = 0; i < contractVec.size(); i++) {
                ContractData contract = (ContractData) contractVec.get(i);
                ids.add(new Integer(contract.getCatalogId()));
            }

            criteria = new DBCriteria();
            criteria.addOneOf(CatalogDataAccess.CATALOG_ID, ids);
            CatalogDataVector catalogV = CatalogDataAccess.select(connection, criteria);

            for (int i = 0; i < contractVec.size(); i++) {
                ContractData contract = (ContractData) contractVec.get(i);
                if (RefCodeNames.CONTRACT_STATUS_CD.ACTIVE.equals(contract.getContractStatusCd()) ||
                    RefCodeNames.CONTRACT_STATUS_CD.ROUTING.equals(contract.getContractStatusCd()) ||
                    RefCodeNames.CONTRACT_STATUS_CD.LIVE.equals(contract.getContractStatusCd())) {

                    ContractDescData contractDesc = ContractDescData.createValue();
                    contractDesc.setContractId(contract.getContractId());
                    contractDesc.setContractName(contract.getShortDesc());
                    contractDesc.setStatus(contract.getContractStatusCd());
                    contractDesc.setCatalogId(contract.getCatalogId());
                    for (int j = 0; j < catalogV.size(); j++) {
                        CatalogData catalog = (CatalogData) catalogV.get(j);
                        if (catalog.getCatalogId() == contract.getCatalogId()) {
                            contractDesc.setCatalogName(catalog.getShortDesc());
                            break;
                        }
                    }
                    result.add(contractDesc);
                }
            }
        } catch (Exception ex) {
            throw new RemoteException("getContractDescsByFreight: " + ex.getMessage());
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    private void updateFreightTableIdForContract(Connection connection,
        String userName, int contractId, int freightTableId) throws RemoteException {
        ContractData contract = null;
        try {
            contract = ContractDataAccess.select(connection, contractId);
        } catch (DataNotFoundException ex) {
            log.info("[updateFreightTableIdForContract] Contract with id: " + contractId + " not found.");
        } catch (Exception ex) {
            throw new RemoteException("[updateFreightTableIdForContract] " + ex.getMessage());
        }
        if (contract == null) {
            return;
        }
        contract.setModBy(userName);
        contract.setFreightTableId(freightTableId);
        try {
            ContractDataAccess.update(connection, contract);
        } catch (Exception ex) {
            throw new RemoteException("[updateFreightTableIdForContract] " + ex.getMessage());
        }
    }

    public void addCatalogFreightRelationship(String userName, int freightId, int catalogId) throws RemoteException {
        Connection connection = null;
        try {
            boolean isContractToUpdate = false;
            int contractIdFromDb = 0;
            int freightIdFromDb = 0;
            String sql = null;
            ResultSet resSet = null;
            PreparedStatement pstmt = null;
            connection = getConnection();
            ///
            sql =
                "SELECT FREIGHT_TABLE_ID FROM CLW_FREIGHT_TABLE " +
                "WHERE FREIGHT_TABLE_STATUS_CD = '" + RefCodeNames.FREIGHT_TABLE_STATUS_CD.ACTIVE + "' " +
                "AND FREIGHT_TABLE_CHARGE_CD = '" + RefCodeNames.FREIGHT_TABLE_CHARGE_CD.FREIGHT + "' " +
                "AND FREIGHT_TABLE_ID = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, freightId);
            resSet = pstmt.executeQuery();
            while (resSet.next()) {
                isContractToUpdate = true;
                break;
            }
            resSet.close();
            pstmt.close();
            if (!isContractToUpdate) {
                log.info("[addCatalogFreightRelationship] Freight table with id " + freightId + " is not found.");
                throw new RemoteException("Freight table with id " + freightId + " is not found.");
            }
            ///
            sql =
                "SELECT CONTRACT_ID, FREIGHT_TABLE_ID FROM CLW_CONTRACT " +
                "WHERE CATALOG_ID = ? AND CONTRACT_STATUS_CD = 'ACTIVE'";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, catalogId);
            resSet = pstmt.executeQuery();
            while (resSet.next()) {
                contractIdFromDb = resSet.getInt(1);
                freightIdFromDb = resSet.getInt(2);
                if (freightIdFromDb == 0) {
                    isContractToUpdate = true;
                    break;
                }
            }
            resSet.close();
            pstmt.close();
            ///
            if (isContractToUpdate) {
                updateFreightTableIdForContract(connection, userName, contractIdFromDb, freightId);
            } else {
                throw new RemoteException("Catalog already has freight table with id " + freightIdFromDb);
            }
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    public void deleteCatalogFreightRelationship(String userName, int contractId) throws RemoteException {
        Connection connection = null;
        try {
            connection = getConnection();
            updateFreightTableIdForContract(connection, userName, contractId, 0);
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    public ContractDescDataVector getContractDescsByDiscountFreight(int freightId) throws RemoteException {
        ContractDescDataVector result = new ContractDescDataVector();
        Connection connection = null;
        try {
            connection = getConnection();
            DBCriteria criteria = new DBCriteria();
            criteria.addEqualTo(ContractDataAccess.DISCOUNT_TABLE_ID, freightId);
            criteria.addOrderBy(ContractDataAccess.SHORT_DESC);

            ContractDataVector contractVec = ContractDataAccess.select(connection, criteria);
            if (contractVec == null || contractVec.size() == 0) {
                log.info("[getContractDescsByDiscountFreight] Contract by discount table id: " + freightId + " not found.");
                return result;
            }

            IdVector ids = new IdVector();
            for (int i = 0; i < contractVec.size(); i++) {
                ContractData contract = (ContractData) contractVec.get(i);
                ids.add(new Integer(contract.getCatalogId()));
            }

            criteria = new DBCriteria();
            criteria.addOneOf(CatalogDataAccess.CATALOG_ID, ids);
            CatalogDataVector catalogV = CatalogDataAccess.select(connection, criteria);

            for (int i = 0; i < contractVec.size(); i++) {
                ContractData contract = (ContractData) contractVec.get(i);
                if (RefCodeNames.CONTRACT_STATUS_CD.ACTIVE.equals(contract.getContractStatusCd()) ||
                    RefCodeNames.CONTRACT_STATUS_CD.ROUTING.equals(contract.getContractStatusCd()) ||
                    RefCodeNames.CONTRACT_STATUS_CD.LIVE.equals(contract.getContractStatusCd())) {

                    ContractDescData contractDesc = ContractDescData.createValue();
                    contractDesc.setContractId(contract.getContractId());
                    contractDesc.setContractName(contract.getShortDesc());
                    contractDesc.setStatus(contract.getContractStatusCd());
                    contractDesc.setCatalogId(contract.getCatalogId());
                    for (int j = 0; j < catalogV.size(); j++) {
                        CatalogData catalog = (CatalogData) catalogV.get(j);
                        if (catalog.getCatalogId() == contract.getCatalogId()) {
                            contractDesc.setCatalogName(catalog.getShortDesc());
                            break;
                        }
                    }
                    result.add(contractDesc);
                }
            }
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    private void updateDiscountFreightTableIdForContract(Connection connection,
        String userName, int contractId, int freightTableId) throws RemoteException {
        ContractData contract = null;
        try {
            contract = ContractDataAccess.select(connection, contractId);
        } catch (DataNotFoundException ex) {
            log.info("[updateDiscountFreightTableIdForContract] Contract with id: " + contractId + " not found.");
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
        if (contract == null) {
            return;
        }
        contract.setModBy(userName);
        contract.setDiscountTableId(freightTableId);
        try {
            ContractDataAccess.update(connection, contract);
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        }
    }

    public void addCatalogAndDiscountFreightRelationship(String userName, int freightId, int catalogId) throws RemoteException {
        Connection connection = null;
        try {
            boolean isContractToUpdate = false;
            int contractIdFromDb = 0;
            int discountFreightIdFromDb = 0;
            String sql = null;
            ResultSet resSet = null;
            PreparedStatement pstmt = null;
            connection = getConnection();
            ///
            sql =
                "SELECT FREIGHT_TABLE_ID FROM CLW_FREIGHT_TABLE " +
                "WHERE FREIGHT_TABLE_STATUS_CD = '" + RefCodeNames.FREIGHT_TABLE_STATUS_CD.ACTIVE + "' " +
                "AND FREIGHT_TABLE_CHARGE_CD = '" + RefCodeNames.FREIGHT_TABLE_CHARGE_CD.DISCOUNT + "' " +
                "AND FREIGHT_TABLE_ID = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, freightId);
            resSet = pstmt.executeQuery();
            while (resSet.next()) {
                isContractToUpdate = true;
                break;
            }
            resSet.close();
            pstmt.close();
            if (!isContractToUpdate) {
                throw new RemoteException("Freight table with id " + freightId + " is not found.");
            }
            ///
            isContractToUpdate = false;
            sql =
                "SELECT CONTRACT_ID, DISCOUNT_TABLE_ID FROM CLW_CONTRACT " +
                "WHERE CATALOG_ID = ? AND CONTRACT_STATUS_CD = 'ACTIVE'";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, catalogId);
            resSet = pstmt.executeQuery();
            while (resSet.next()) {
                contractIdFromDb = resSet.getInt(1);
                discountFreightIdFromDb = resSet.getInt(2);
                if (discountFreightIdFromDb == 0) {
                    isContractToUpdate = true;
                    break;
                }
            }
            resSet.close();
            pstmt.close();
            ///
            if (isContractToUpdate) {
                updateDiscountFreightTableIdForContract(connection, userName, contractIdFromDb, freightId);
            } else {
                throw new RemoteException("Catalog already has freight table with id " + discountFreightIdFromDb);
            }
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        } finally {
            closeConnection(connection);
        }
    }

    public void deleteCatalogAndDiscountFreightRelationship(String userName, int contractId) throws RemoteException {
        Connection connection = null;
        try {
            connection = getConnection();
            updateDiscountFreightTableIdForContract(connection, userName, contractId, 0);
        } catch (Exception ex) {
            throw new RemoteException(ex.getMessage());
        } finally {
            closeConnection(connection);
        }
    }
    
    /**
     * Calculates the amount of items in cart or pending approval
     * status pertaining to a cost center 
     * @param contractId
     * @param idVector -   List of Item Ids and Quantities.
     * @return BigDecimal 
     */
    public BigDecimal getAmountPerCostCenter(int contractId,IdVector idVector)
    throws RemoteException {
    	Connection conn=null;
    	ResultSet rs= null;
    	PreparedStatement preparedStmt = null;
    	BigDecimal totalAmount = new BigDecimal("0");
    	try{
    	conn=getConnection();
    	String sql="SELECT AMOUNT FROM CLW_CONTRACT_ITEM WHERE CONTRACT_ID = ? AND ITEM_ID = ?";

        ArrayList<Integer> itemIds = (ArrayList<Integer>) idVector.get(0);
        ArrayList<Integer> qtyList = (ArrayList<Integer>) idVector.get(1);
        preparedStmt = conn.prepareStatement(sql);
        BigDecimal total = new BigDecimal("0");
        for(int i=0;i<itemIds.size();i++)
        {
        	preparedStmt.setInt(1,contractId);
        	preparedStmt.setInt(2, itemIds.get(i));
        	rs=preparedStmt.executeQuery();
        if (rs.next()) {
        	double amount = rs.getDouble(1);
        	BigDecimal amountBD = new BigDecimal(String.valueOf(amount));
        	int qty = qtyList.get(i);
        	BigDecimal qtyBD = new BigDecimal(String.valueOf(qty));
        	totalAmount = totalAmount.add(new BigDecimal(String.valueOf((amountBD.multiply(qtyBD)).doubleValue())));
        }    
        }
        if(rs!=null)
	        rs.close();
        if(preparedStmt!=null)
	        preparedStmt.close();
    	}
    	catch(Exception e){
    		throw new RemoteException("getAmountInCartPerCostCenter: " + e.getMessage());
    	}
    	finally{
    		closeConnection(conn);
    	}
    	return totalAmount;
    	
    }
}
