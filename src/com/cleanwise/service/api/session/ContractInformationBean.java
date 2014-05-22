package com.cleanwise.service.api.session;


import javax.ejb.*;
import java.rmi.*;
import java.util.*;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;
import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.dao.*;

/**
 *  Description of the Class
 *
 *@author     dvieira
 *@created    August 30, 2001
 */
public class ContractInformationBean extends BusEntityServicesAPI {
    /**
     */
    public ContractInformationBean() { }


    /**
     *  Gets the array-like contract vector values to be used by the request.
     *
     *@param  pBusEntityId      the business entity identifier
     *@return                   ContractDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDataVector getContractsCollectionByBusEntity(int pBusEntityId)
             throws RemoteException {
        return new ContractDataVector();
    }


    /**
     *  Gets the array-like contract vector values to be used by the request.
     *
     *@param  pCatalogId
     *@return                   ContractDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDataVector getContractsCollectionByCatalog(int pCatalogId)
             throws RemoteException {
        ContractDataVector cdv = null;
        Connection con = null;
        try {
            con = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(ContractDataAccess.CATALOG_ID, pCatalogId);
            cdv = ContractDataAccess.select(con, dbc);
        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ContractInformationBean.getContractsCollectionByCatalog()"
                     + " Naming Exception");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ContractInformationBean.getContractsCollectionByCatalog()"
                     + " SQL Exception");
        }
        finally {
            try {
                con.close();
            }
            catch (SQLException exc) {
                logError("exc.getMessage");
                exc.printStackTrace();
                throw new RemoteException("Error. ContractInformationBean.getContractsCollectionByCatalog()"
                         + " SQL Exception happened");
            }
        }
        return cdv;
    }


    
    
    /**
     *  Gets the array-like contract vector values to be used by the request.
     *
     *@param  pUserId      the customer identifier
     *@return                   CatalogDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDataVector getContractsCollectionByUser(int pUserId)
        throws RemoteException {

        Connection con = null;
        ContractDataVector contractDV = null;       
        try {
            con = getConnection();
            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
            IdVector busentityids = UserAssocDataAccess.selectIdOnly(con, UserAssocDataAccess.BUS_ENTITY_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, busentityids);
            IdVector catids = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.CATALOG_ID, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(ContractDataAccess.CATALOG_ID, catids);
            contractDV = ContractDataAccess.select(con, dbc);
            
        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ContractInformationBean.getContractsCollectionByUser() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ContractInformationBean.getContractsCollectionByUser() SQL Exception happened");
        }
        finally {
            try {
                con.close();
            }
            catch (SQLException exc) {
                logError("exc.getMessage");
                exc.printStackTrace();
                throw new RemoteException("Error. ContractInformationBean.getContractsCollectionByUser() SQL Exception happened");
            }
        }
        return contractDV;
    }


    /**
     *  Gets the array-like contract description vector values to be used by the request.
     *
     *@param  pUserId      the customer identifier
     *@return                   CatalogDescDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDescDataVector getContractDescsCollectionByUser(int pUserId)
        throws RemoteException {

        Connection con = null;
        ContractDescDataVector contractDescVec = new ContractDescDataVector();       
        try {
            con = getConnection();
            DBCriteria dbc = new DBCriteria();

            dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
            IdVector busentityids = UserAssocDataAccess.selectIdOnly(con, UserAssocDataAccess.BUS_ENTITY_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, busentityids);
            IdVector catids = CatalogAssocDataAccess.selectIdOnly(con, CatalogAssocDataAccess.CATALOG_ID, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(ContractDataAccess.CATALOG_ID, catids);
            ContractDataVector contractVec = ContractDataAccess.select(con, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catids);
            CatalogDataVector catalogV = 
                CatalogDataAccess.select(con, dbc);
            for(int i = 0; i < contractVec.size(); i++) {
                ContractDescData contractDesc   = ContractDescData.createValue();
                ContractData     contract       = (ContractData)contractVec.get(i);
                contractDesc.setContractId(contract.getContractId());
                contractDesc.setContractName(contract.getShortDesc());
                contractDesc.setStatus(contract.getContractStatusCd());
                contractDesc.setCatalogId(contract.getCatalogId());
                for (int j = 0; j < catalogV.size(); j ++) {
                    CatalogData catalog = (CatalogData) catalogV.get(j);
                    if( catalog.getCatalogId() == contract.getCatalogId()) {
                        contractDesc.setCatalogName(catalog.getShortDesc());
                        break;
                    }
                }
                contractDescVec.add(contractDesc);
            }
            
        }
        catch (NamingException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ContractInformationBean.getContractDescsCollectionByUser() Naming Exception happened");
        }
        catch (SQLException exc) {
            logError("exc.getMessage");
            exc.printStackTrace();
            throw new RemoteException("Error. ContractInformationBean.getContractDescsCollectionByUser() SQL Exception happened");
        }
        finally {
            try {
                con.close();
            }
            catch (SQLException exc) {
                logError("exc.getMessage");
                exc.printStackTrace();
                throw new RemoteException("Error. ContractInformationBean.getContractDescsCollectionByUser() SQL Exception happened");
            }
        }
        return contractDescVec;
    }
                
    
    
    /**
     *  Gets the array-like contract description vector values to be used by the request.
     *
     * @param pName a <code>String</code> value with contract name or pattern
     * @param pMatch one of EXACT_MATCH, BEGINS_WITH, EXACT_MATCH_IGNORE_CASE,
     *        BEGINS_WITH_IGNORE_CASE
     *@param  pUserId      the customer identifier
     *@return                   CatalogDescDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDescDataVector getContractDescsCollectionByUser(String pName, int pMatch,
						    int pUserId)
	throws RemoteException {

	ContractDescDataVector contractDescVec = new ContractDescDataVector();

	Connection conn = null;
	try {
	    conn = getConnection();

            DBCriteria dbc = new DBCriteria();            
            dbc.addEqualTo(UserAssocDataAccess.USER_ID, pUserId);
            IdVector busentityids = UserAssocDataAccess.selectIdOnly(conn, UserAssocDataAccess.BUS_ENTITY_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(CatalogAssocDataAccess.BUS_ENTITY_ID, busentityids);
            IdVector catalogIds = CatalogAssocDataAccess.selectIdOnly(conn, CatalogAssocDataAccess.CATALOG_ID, dbc);
            
            dbc = new DBCriteria();

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
		dbc.addLike(ContractDataAccess.SHORT_DESC, pName+"%");
		break;
	    case Contract.BEGINS_WITH_IGNORE_CASE:
		dbc.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
				      pName+"%");
		break;
	    case Contract.CONTAINS:
		dbc.addLike(ContractDataAccess.SHORT_DESC, "%"+pName+"%");
		break;
	    case Contract.CONTAINS_IGNORE_CASE:
		dbc.addLikeIgnoreCase(ContractDataAccess.SHORT_DESC,
				      "%"+pName+"%");
		break;
	    default:
		throw new RemoteException("getContractDescsCollectionByUser: Bad match specification");
	    }
	    dbc.addOrderBy(ContractDataAccess.SHORT_DESC);
            ContractDataVector contractVec = ContractDataAccess.select(conn, dbc);
            
            dbc = new DBCriteria();
            dbc.addOneOf(CatalogDataAccess.CATALOG_ID, catalogIds);
            CatalogDataVector catalogVec = 
                CatalogDataAccess.select(conn, dbc);
            
            for(int i = 0; i < contractVec.size(); i++) {
                ContractDescData contractDesc   = ContractDescData.createValue();
                ContractData     contract       = (ContractData)contractVec.get(i);
                contractDesc.setContractId(contract.getContractId());
                contractDesc.setContractName(contract.getShortDesc());
                contractDesc.setStatus(contract.getContractStatusCd());
                contractDesc.setCatalogId(contract.getCatalogId());
                for (int j = 0; j < catalogVec.size(); j ++) {
                    CatalogData catalog = (CatalogData) catalogVec.get(j);
                    if( catalog.getCatalogId() == contract.getCatalogId()) {
                        contractDesc.setCatalogName(catalog.getShortDesc());
                        break;
                    }
                }
                contractDescVec.add(contractDesc);
            }
            
	} catch (Exception e) {
	    throw new RemoteException("getContractDescsCollectionByUser: " + e.getMessage());
	} finally {
	    try {if (conn != null) conn.close();} catch (Exception ex) {}
	}

	return contractDescVec;
    } 
        
    
    
    /**
     *  Gets the array-like contract vector values to be used by the request
     *  (Search by contract type).
     *
     *@param  pContractTypeCd   the contract type code
     *@return                   ContractDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractDataVector getContractsCollectionByType(String pContractTypeCd)
             throws RemoteException {
        return new ContractDataVector();
    }


    /**
     *  Gets contract information values to be used by the request.
     *
     *@param  pContractId       the contract identifier
     *@return                   ContractData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractData getContract(int pContractId)
             throws RemoteException {
        return ContractData.createValue();
    }


    /**
     *  Gets the array-like contract item vector values to be used by the
     *  request
     *
     *@param  pContractId       Description of Parameter
     *@return                   ContractItemDataVector
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractItemDataVector getContractItemsCollection(int pContractId)
             throws RemoteException {
        return new ContractItemDataVector();
    }


    /**
     *  Gets contract item information values to be used by the request.
     *
     *@param  pContractId       the contract identifier
     *@param  pContractItemId   the contract item identifier
     *@return                   ContractItemData
     *@throws  RemoteException  Required by EJB 1.0
     */
    public ContractItemData getContractItem(int pContractId,
            int pContractItemId)
             throws RemoteException {
        return ContractItemData.createValue();
    }




    /**
     *  determines if the contract is hide price.
     *
     *@param  pContractId       the contract identifier
     *@return                   true if the HidePriceInd equals true
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean isHidePrice(int pContractId)
             throws RemoteException {
        return true;
    }


    /**
     *  determines if contractItemsOnlyInd is true.
     *
     *@param  pContractId       the contract identifier
     *@return                   true if the ContractItemsOnlyInd equals true
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean isContractItemsOnly(int pContractId)
             throws RemoteException {
        return true;
    }


    /**
     *@exception  CreateException  Description of Exception
     *@exception  RemoteException  Description of Exception
     */
    public void ejbCreate() throws CreateException, RemoteException { }


    /**
     *  determines if the contract effective date is within the current date.
     *
     *@param  pContractId       the contract identifier
     *@param  pEffDate          the user effective date
     *@param  pNow              the current date
     *@return                   true if the contract effective date is equal to
     *      or after the current date.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean checkContractEffDate(int pContractId, Date pEffDate, Date pNow)
             throws RemoteException {
        return true;
    }


    /**
     *  determines if the contract expiration date is within the current date.
     *
     *@param  pContractId       the contract identifier
     *@param  pExpDate          the user expiration date
     *@param  pNow              the current date
     *@return                   true if the contract expiration date is equal to
     *      or before the current date.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean checkContractExpDate(int pContractId, Date pExpDate, Date pNow)
             throws RemoteException {
        return true;
    }


    /**
     *  determines if the contract quote expiration date is within the current
     *  date.
     *
     *@param  pContractId       the contract identifier
     *@param  pExpDate          the contract quote expiration date
     *@param  pNow              the current date
     *@return                   true if the contract expiration date is equal to
     *      or before the current date.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean checkContractQuoteExpDate(int pContractId, Date pExpDate, Date pNow)
             throws RemoteException {
        return true;
    }


    /**
     *  determines if the contract acceptance date is within the current date.
     *
     *@param  pContractId       the contract identifier
     *@param  pAcceptanceDate   the contract acceptance date
     *@param  pNow              the current date
     *@return                   true if the contract acceptance date is equal to
     *      or before the current date.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean checkContractAcceptanceDate(int pContractId,
            Date pAcceptanceDate, Date pNow)
             throws RemoteException {
        return true;
    }


    /**
     *  determines if the contract item effective date is within the current
     *  date.
     *
     *@param  pContractId       the contract identifier
     *@param  pContractItemId   the contract item identifier
     *@param  pEffDate          the contract item effective date
     *@param  pNow              the current date
     *@return                   true if the contract item effective date is
     *      equal to or after the current date.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean checkContractItemEffDate(int pContractId,
            int pContractItemId, Date pEffDate, Date pNow)
             throws RemoteException {
        return true;
    }


    /**
     *  determines if the contract item expiration date is within the current
     *  date.
     *
     *@param  pContractId       the contract identifier
     *@param  pContractItemId   the contract item identifier
     *@param  pExpDate          the contract item expiration date
     *@param  pNow              the current date
     *@return                   true if the contract item expiration date is
     *      equal to or before the current date.
     *@throws  RemoteException  Required by EJB 1.0
     */
    public boolean checkContractItemExpDate(int pContractId,
            int pContractItemId, Date pExpDate, Date pNow)
             throws RemoteException {
        return true;
    }


}

