package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionError;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author Alexander Chikin
 * Date: 15.08.2006
 * Time: 19:05:23
 * Form bean for the add/edit contract page.  This form has the following fields,
 * with default values in square brackets:
 */
public class StoreContractMgrDetailForm extends ActionForm {


  // -------------------------------------------------------- Instance Variables


    private ContractData    _detail = ContractData.createValue();
    private String          _catalogId = new String("");
    private String          _catalogName = new String("");
    private int             _storeId = 0;
    private String          _storeName = new String("");
    private String          _createFrom = new String("Catalog");
    private String          _parentContractId = new String("");
    private String          _parentContractName = new String("");
//    private ArrayList       _itemList;
    private String          _effDate = new String("");
    private String          _expDate = new String("");
    private String          _freightTableId = new String("");
    private String          _freightTableName = new String("");
    private FreightTableData                    _currentFreightTable = null;
    private FreightTableCriteriaDescDataVector  _currentFreightTableCriteria = null;
    //Price enter
    private String[] _distCostS = new String[0];
    private String[] _distBaseCostS = new String[0];
    private String[] _amountS = new String[0];
    private String[] _inputIds = new String[0];


    // Items to be removed.
    private String[]        _selectItemsCollection = { " " };
    private ArrayList       _itemsDetailCollection;
    private ArrayList       _catalogItemsCollection;
    private ContractItemDescDataVector _nonCatalogItems;

  // ---------------------------------------------------------------- Properties


    /**
     * Return the contract detail object
     */
    public ContractData getDetail() {
        return (this._detail);
    }

    /**
     * Set the contract detail object
     */
    public void setDetail(ContractData detail) {
        this._detail = detail;
    }


    public String getEffDate() {
        Date effDate =  this._detail.getEffDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if(null != effDate) {
            this._effDate = simpleDateFormat.format(effDate);
        }
        return this._effDate;
    }


    public void setEffDate(String dateString) {
        this._effDate = dateString;
        if(null != dateString && ! "".equals(dateString)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date effDate =  new Date();
            try {
                effDate  = simpleDateFormat.parse(dateString);
            }
            catch (Exception e) {
                effDate = null;
            }
            this._detail.setEffDate(effDate);
        }
        else {
            this._detail.setEffDate(null);
        }
    }


    public String getExpDate() {
        Date expDate =  this._detail.getExpDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        if (null != expDate) {
            this._expDate = simpleDateFormat.format(expDate);
        }else{
            this._expDate="";
        }
        return this._expDate;
    }


    public void setExpDate(String dateString) {
        this._expDate = dateString;
        if(null != dateString && ! "".equals(dateString)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date expDate = new Date();
            try {
                expDate  = simpleDateFormat.parse(dateString);
            }
            catch (Exception e) {
                expDate = null;
            }
            this._detail.setExpDate(expDate);
        }
        else {
            this._detail.setExpDate(null);
        }
    }


    /**
     *  Gets the CatalogId attribute of the ContractMgrDetailForm object
     *
     *@return    The CatalogId value
     */
    public String getCatalogId() {
        return this._catalogId;
    }


    /**
     *  Sets the CatalogId attribute of the ContractMgrDetailForm object
     *
     *@param  pCatalogId  The new CatalogId value
     */
    public void setCatalogId(String pCatalogId) {
        this._catalogId = pCatalogId;
    }


    /**
     *  Gets the CatalogName attribute of the ContractMgrDetailForm object
     *
     *@return    The CatalogName value
     */
    public String getCatalogName() {
        return this._catalogName;
    }


    /**
     *  Sets the CatalogName attribute of the ContractMgrDetailForm object
     *
     *@param  pCatalogName  The new CatalogName value
     */
    public void setCatalogName(String pCatalogName) {
        this._catalogName = pCatalogName;
    }


    /**
     *  Gets the StoreId attribute of the ContractMgrDetailForm object
     *
     *@return    The StoreId value
     */
    public int getStoreId() {
        return this._storeId;
    }


    /**
     *  Sets the StoreId attribute of the ContractMgrDetailForm object
     *
     *@param  pStoreId  The new StoreId value
     */
    public void setStoreId(int pStoreId) {
        this._storeId = pStoreId;
    }


    /**
     *  Gets the StoreName attribute of the ContractMgrDetailForm object
     *
     *@return    The StoreName value
     */
    public String getStoreName() {
        return this._storeName;
    }


    /**
     *  Sets the StoreName attribute of the ContractMgrDetailForm object
     *
     *@param  pStoreName  The new StoreName value
     */
    public void setStoreName(String pStoreName) {
        this._storeName = pStoreName;
    }



    /**
     *  Gets the CreateFrom attribute of the ContractMgrDetailForm object
     *
     *@return    The CreateFrom value
     */
    public String getCreateFrom() {
        return this._createFrom;
    }


    /**
     *  Sets the CreateFrom attribute of the ContractMgrDetailForm object
     *
     *@param  pCreateFrom  The new CreateFrom value
     */
    public void setCreateFrom(String pCreateFrom) {
        this._createFrom = pCreateFrom;
    }


    /**
     *  Gets the ParentContractId attribute of the ContractMgrDetailForm object
     *
     *@return    The ParentContractId value
     */
    public String getParentContractId() {
        return this._parentContractId;
    }

    /**
     *  Gets the ParentContractName attribute of the ContractMgrDetailForm object
     *
     *@return    The ParentContractId value
     */
    public String getParentContractName() {
        return this._parentContractName;
    }


    /**
     *  Sets the ParentContractId attribute of the ContractMgrDetailForm object
     *
     *@param  pParentContractId  The new ParentContractId value
     */
    public void setParentContractId(String pParentContractId) {
        this._parentContractId = pParentContractId;
    }

    /**
     *  Sets the ParentContractName attribute of the ContractMgrDetailForm object
     *
     *@param  pParentContractName
     */
    public void setParentContractName(String pParentContractName) {
        this._parentContractName = pParentContractName;
    }


    /**
     *  Gets the FreightTableId attribute of the ContractMgrDetailForm object
     *
     *@return    The FreightTableId value
     */
    public String getFreightTableId() {
        return this._freightTableId;
    }


    /**
     *  Sets the FreightTableId attribute of the ContractMgrDetailForm object
     *
     *@param  pFreightTableId  The new FreightTableId value
     */
    public void setFreightTableId(String pFreightTableId) {
        this._freightTableId = pFreightTableId;
    }


    /**
     *  Gets the FreightTableName attribute of the ContractMgrDetailForm object
     *
     *@return    The FreightTableName value
     */
    public String getFreightTableName() {
        return this._freightTableName;
    }


    /**
     *  Sets the FreightTableName attribute of the ContractMgrDetailForm object
     *
     *@param  pFreightTableName  The new FreightTableName value
     */
    public void setFreightTableName(String pFreightTableName) {
        this._freightTableName = pFreightTableName;
    }


    /**
     *  Gets the CurrentFreightTable attribute of the ContractMgrDetailForm object
     *
     *@return    The CurrentFreightTable value
     */
    public FreightTableData getCurrentFreightTable() {
        return this._currentFreightTable;
    }


    /**
     *  Sets the CurrentFreightTable attribute of the ContractMgrDetailForm object
     *
     *@param  pFreightTable  The new FreightTable value
     */
    public void setCurrentFreightTable(FreightTableData pFreightTable) {
        this._currentFreightTable = pFreightTable;
    }


    /**
     *  Gets the CurrentFreightTableCriteria attribute of the ContractMgrDetailForm object
     *
     *@return    The CurrentFreightTableCriteria value
     */
    public FreightTableCriteriaDescDataVector getCurrentFreightTableCriteria() {
        return this._currentFreightTableCriteria;
    }


    /**
     *  Sets the CurrentFreightTableCriteria attribute of the ContractMgrDetailForm object
     *
     *@param  pFreightTableCriteria  The new FreightTableCriteriaDescDataVector value
     */
    public void setCurrentFreightTableCriteria(FreightTableCriteriaDescDataVector pFreightTableCriteria) {
        this._currentFreightTableCriteria = pFreightTableCriteria;
    }


    /**
     * <code>getItemList</code> method.
     *
     * @return a <code>List</code> value
     */
/*
    public ArrayList getItemList() {
        if( null == this._itemList) {
            this._itemList = new ArrayList();
        }
        return (this._itemList);
    }
*/
    /**
     * <code>setItemList</code> method.
     *
     * @param pVal a <code>List</code> value
     */
/*
    public void setItemList(ArrayList pVal) {
        this._itemList = pVal;
    }

*/
    public ArrayList getItemsDetailCollection() {
        if(null == _itemsDetailCollection) {
             _itemsDetailCollection = new ArrayList();
        }

        return _itemsDetailCollection;
    }

    public void setItemsDetailCollection(ArrayList pCol) {
        _itemsDetailCollection = pCol;
    }

    public ContractItemDescDataVector getNonCatalogItems() {
        if(null == _nonCatalogItems) {
             _nonCatalogItems = new ContractItemDescDataVector();
        }

        return _nonCatalogItems;
    }

    public void setNonCatalogItems(ContractItemDescDataVector pCol) {
        _nonCatalogItems = pCol;
    }

    public ArrayList getCatalogItemsCollection() {
        if(null == _catalogItemsCollection) {
             _catalogItemsCollection = new ArrayList();
        }
        return _catalogItemsCollection;
    }

    public void setCatalogItemsCollection(ArrayList pCol) {
        _catalogItemsCollection = pCol;
    }

    public ContractItemDescData getItemDesc(int idx) {

        if (_itemsDetailCollection == null) {
            _itemsDetailCollection = new ArrayList();
            // test comment
        }
        while (idx >= _itemsDetailCollection.size()) {
            _itemsDetailCollection.add(new ContractItemDescData());
        }

        return (ContractItemDescData) _itemsDetailCollection.get(idx);
    }



    public String [] getSelectItems() {
        return _selectItemsCollection;
    }

    public void setSelectItems(String[] pItemIds) {
        _selectItemsCollection = pItemIds;
    }

    //price erter fields
    public void setInputIds(String[] pValue) {_inputIds = pValue;}
    public String[] getInputIds(){return _inputIds;}
    public void setInputId(int ind, String inputId) {
      if(_inputIds.length>ind) {
        _inputIds[ind]=inputId;
      }
    }
    public String getInputId(int ind) {
      String ret = "";
      if(_inputIds.length>ind) {
        ret = _inputIds[ind];
      }
      return ret;
    }

    public void setDistCosts(String[] pValue) {_distCostS = pValue; }
    public String[] getDistCosts() {return _distCostS;}
    public void setDistCost(int ind, String pDistCostS) {
      if(_distCostS.length>ind) {
        _distCostS[ind]=pDistCostS;
      }
    }
    public String getDistCost(int ind) {
      String ret = "";
      if(_distCostS.length>ind) {
        ret = _distCostS[ind];
      }
      return ret;
    }

    public void setDistBaseCosts(String[] pValue) {_distBaseCostS = pValue; }
    public String[] getDistBaseCosts() {return _distBaseCostS;}
    public void setDistBaseCost(int ind, String pDistBaseCostS) {
      if(_distBaseCostS.length>ind) {
        _distBaseCostS[ind]=pDistBaseCostS;
      }
    }
    public String getDistBaseCost(int ind) {
      String ret = "";
      if(_distBaseCostS.length>ind) {
        ret = _distBaseCostS[ind];
      }
      return ret;
    }


    public void setAmounts(String[] pValue) {_amountS = pValue; }
    public String[] getAmounts() {return _amountS;}
    public void setAmount(int ind, String pAmountS) {
      if(_amountS.length>ind) {
        _amountS[ind]=pAmountS;
      }
    }
    public String getAmount(int ind) {
      String ret = "";
      if(_amountS.length>ind) {
        ret = _amountS[ind];
      }
      return ret;
    }


  // ------------------------------------------------------------ Public Methods


    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        _selectItemsCollection = new String[0];
    }


    /**
     * Validate the properties that have been set from this HTTP request,
     * and return an <code>ActionErrors</code> object that encapsulates any
     * validation errors that have been found.  If no errors are found, return
     * <code>null</code> or an <code>ActionErrors</code> object with no
     * recorded error messages.
     *
     * Required fields are:
     *   contract name
     *   tax ID
     *   contact first and last names
     *   contact email address
     *
     * Additionally, a contract's name is checked for uniqueness.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {

        // Retrieve the action. We only want to validate on save.
        String action = request.getParameter("action");
        if (action == null) action = "";
        if (!action.equals("Save")) {
            return null;
        }

        // Is there a currently logged on user?
        HttpSession currentSession = request.getSession();
        if ((currentSession == null) || (currentSession.getAttribute(Constants.APIACCESS) == null)
            || (currentSession.getAttribute(Constants.APP_USER) == null)) {
            return null;
        }


        // Perform the save validation.
        ActionErrors errors = new ActionErrors();

        // catalogId or parentContractId can't be empty
        if("Catalog".equals(_createFrom)) {
            if (null == _catalogId || _catalogId.trim().length() < 1 ) {
                errors.add("contract", new ActionError("variable.empty.error", "Catalog Id"));
            }
        }
        else if("Contract".equals(_createFrom)) {
            if ( null == _parentContractId || _parentContractId.trim().length() < 1 ) {
                errors.add("contract", new ActionError("variable.empty.error", "Contract Id"));
            }
        }

        if ((_detail.getShortDesc() == null) || (_detail.getShortDesc().trim().length() < 1))
            errors.add("contractname", new ActionError("variable.empty.error", "Contract Name"));

        if ((_detail.getContractStatusCd() == null) || (_detail.getContractStatusCd().trim().length() < 1))
            errors.add("contractStatusCd", new ActionError("variable.empty.error", "Status"));

        if ((_effDate == null) || (_effDate.trim().length() < 1)) {
            errors.add("contracteffdate", new ActionError("variable.empty.error", "Contract Active Date"));
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date effDate = new Date();
            try {
                effDate  = simpleDateFormat.parse(_effDate);
            }
            catch (Exception e) {
                errors.add("contracteffdate", new ActionError("variable.date.format.error", "Contract Active Date"));
                effDate = null;
            }
        }

        if ((_expDate == null) || (_expDate.trim().length() < 1)) {
        }
        else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date expDate = new Date();
            try {
                expDate  = simpleDateFormat.parse(_expDate);
            }
            catch (Exception e) {
                errors.add("contractexpdate", new ActionError("variable.date.format.error", "Contract Inactive Date"));
                expDate = null;
            }
        }


        return errors;

  }


}
