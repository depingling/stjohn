/**
 * Title:        ContractMgrDetail
 * Description:  This is the Struts ActionForm class for the contract detail page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author
 */
package com.cleanwise.view.forms;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.CatalogNode;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.value.*;

import java.rmi.RemoteException;

import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


/**
 * Form bean for the add/edit contract page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>detail</b> - A BusEntityData object
 * </ul>
 */
public final class CatalogMgrStructureForm
    extends ActionForm
{

    // -------------------------------------------------------- Instance Variables
    private int _catalogId = 0;
    private CatalogData _catalog = CatalogData.createValue();
    private int _storeId = 0;
    private String _storeName = "";
    private Collection _catalogTree = (Collection)new Vector();
    private String _backBox;
    private String _wrkField = "";
    private ItemDataVector _majorCategories = new ItemDataVector();
    private int _majorCategoryId = 0;
    private int _destChoise;
    private String _forwardParam;
    private String[] SourceNodes = { " " };
 

    public String[] getSourceNodes()
    {

        return SourceNodes;
    }

    public void setSourceNodes(String[] v)
    {
        SourceNodes = v;
    }

    // ---------------------------------------------------------------- Properties
    public void setWrkField(String pStr)
    {
        _wrkField = pStr;
    }

    public String getWrkField()
    {

        return _wrkField;
    }

    
    //private ItemDataVector _majorCategories = new ItemDataVector();
    public void setMajorCategories(ItemDataVector pValue)
    {
        _majorCategories = pValue;
    }
 
    public ItemDataVector getMajorCategories()
    {
        return _majorCategories;
    }

    //private int _majorCategoryId = 0;
    public void setMajorCategoryId(int pValue)
    {
        _majorCategoryId = pValue;
    }
 
    public int getMajorCategoryId()
    {
        return _majorCategoryId;
    }


    public void setCatalogId(int pValue)
    {
        _catalogId = pValue;
    }

    public int getCatalogId()
    {

        return _catalogId;
    }

    public void setBackBox(String pSelected)
    {
        _backBox = pSelected;
    }

    public String getBackBox()
    {

        return _backBox;
    }

    public void setDestChoise(int pItemId)
    {
        _destChoise = pItemId;
    }

    public int getDestChoise()
    {

        return _destChoise;
    }

    public void setForwardParam(String pValue)
    {
        _forwardParam = pValue;
    }

    public String getForwardParam()
    {

        return _forwardParam;
    }

    /**
   * Return the contract detail object
   */
    public CatalogData getCatalog()
    {

        return (this._catalog);
    }

    /**
   * Set the contract Catalog object
   */
    public void setCatalog(CatalogData detail)
    {
        this._catalog = detail;
    }

    public int getStoreId()
    {

        return _storeId;
    }

    public void setStoreId(int pStoreId)
    {
        _storeId = pStoreId;
    }

    public String getStoreName()
    {

        return _storeName;
    }

    public void setStoreName(String pStoreName)
    {
        _storeName = pStoreName;
    }

    public Collection getCatalogTree()
    {

        return _catalogTree;
    }

    public void setCatalogTree(Collection pCatalogTree)
    {
        _catalogTree = pCatalogTree;
    }

    public void addToCatalogTree(CatalogNode pCatalogNode)
    {
        _catalogTree.add(pCatalogNode);
    }

    public int getCatalogTreeSize()
    {

        return _catalogTree.size();
    }

    // ------------------------------------------------------------ Public Methods

    /**
   * Reset all properties to their default values.
   *
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
    public void reset(ActionMapping mapping, HttpServletRequest request)
    {
        SourceNodes = new String[0];
    }

    /**
   * So far nothing to validate
   * @param mapping The mapping used to select this instance
   * @param request The servlet request we are processing
   */
    public ActionErrors validate(ActionMapping mapping, 
                                 HttpServletRequest request)
    {

        return null;
    }
}
