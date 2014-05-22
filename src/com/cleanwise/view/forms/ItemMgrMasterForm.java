/**
 * Title:        ContractMgrDetail
 * Description:  This is the Struts ActionForm class for the contract detail page.
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author
 */

package com.cleanwise.view.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.*;
import javax.servlet.http.HttpSession;
import com.cleanwise.service.api.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.service.api.session.CatalogInformation;
import com.cleanwise.service.api.session.Store;
import java.rmi.RemoteException;
import com.cleanwise.service.api.util.DataNotFoundException;
import java.util.GregorianCalendar;


/**
 * Form bean for the add/edit contract page.  This form has the following fields,
 * with default values in square brackets:
 * <ul>
 * <li><b>detail</b> - A BusEntityData object
 * </ul>
 */
public final class ItemMgrMasterForm extends ActionForm {


    // -------------------------------------------------------- Instance Variables
    private int _catalogId=0;
    private ProductData _product=null;
    private String _skuNum="";
    private String _distributorId="";
    private String _manufacturerId="";
    private String _manufacturerName="";
    private String _manufacturerSku="";
    private String _effDate="";
    private String _expDate="";
    private String _listPrice="";
    private String _costPrice="";
    private String _newDistributorId="";
    private String _newDistributorSku="";
    private String _newDistributorUom="";
    private String _newDistributorPack="";
    private BusEntityData _newDistributor=null;
    private String[] _distributorBox;
    private String _retAction=null;
    private String _outServiceName = "";
    private int _distrListPageSize=6;
    private int _distrListOffset=0;
    private String _uom = new String ("");
    private String _tempDistributorUom = new String("");

    private FormFile _imageFile;
    private FormFile _msdsFile;
    private FormFile _dedFile;
    private FormFile _specFile;
    //Jd Item Attributes
    private String _cas70FtlPrice;
    private String _cas70ListPrice;
    private String _cas80FtlPrice;
    private String _cas80ListPrice;
    private String _ftlUsPrice;
    private String _listUsPrice;

    // ---------------------------------------------------------------- Properties
    public void setCatalogId(int pValue) {_catalogId=pValue;}
    public int getCatalogId() {return _catalogId;}

    public void setProduct(ProductData pValue) {_product=pValue;}
    public ProductData getProduct(){return _product;}

    public int getCategoryListSize() {
	CatalogCategoryDataVector catalogCategoryDV=_product.getCatalogCategories();
	int size=(catalogCategoryDV==null)?0:catalogCategoryDV.size();
	return size;
    }

    public void setSkuNum(String pValue) {_skuNum=pValue;}
    public String getSkuNum() {return _skuNum;}

    public void setEffDate(String pValue) {_effDate=pValue;}
    public String getEffDate(){return _effDate;}

    public void setExpDate(String pValue) {_expDate=pValue;}
    public String getExpDate(){return _expDate;}

    public void setListPrice(String pValue) {_listPrice=pValue;}
    public String getListPrice(){return _listPrice;}

    public void setCostPrice(String pValue) {_costPrice=pValue;}
    public String getCostPrice(){return _costPrice;}


    public void setManufacturerId(String pValue) {_manufacturerId=pValue;}
    public String getManufacturerId(){return _manufacturerId;}

    public void setManufacturerName(String pValue) {_manufacturerName=pValue;}
    public String getManufacturerName(){return _manufacturerName;}

    public void setManufacturerSku(String pValue) {_manufacturerSku=pValue;}
    public String getManufacturerSku(){return _manufacturerSku;}

    public void setNewDistributorId(String pValue) {_newDistributorId=pValue;}
    public String getNewDistributorId(){return _newDistributorId;}

    public void setNewDistributorSku(String pValue) {_newDistributorSku=pValue;}
    public String getNewDistributorSku(){return _newDistributorSku;}

    public void setNewDistributorUom(String pValue) {_newDistributorUom=pValue;}
    public String getNewDistributorUom(){return _newDistributorUom;}
    
    public void setNewDistributorPack(String pValue) {_newDistributorPack=pValue;}
    public String getNewDistributorPack(){return _newDistributorPack;}
    
    public void setNewDistributor(BusEntityData pValue) {_newDistributor=pValue;}
    public BusEntityData getNewDistributor(){return _newDistributor;}
    public String getNewDistributorName() {
	if(_newDistributor==null) return "";
	return _newDistributor.getShortDesc();
    }

    public void setDistributorBox(String[] pValue) {_distributorBox=pValue;}
    public String[] getDistributorBox(){return _distributorBox;}

    public void setRetAction(String pValue) {_retAction=pValue;}
    public String getRetAction(){return _retAction;}

    public void setOutServiceName(String pValue) {_outServiceName=pValue;}
    public String getOutServiceName(){return _outServiceName;}

    public void setDistrListOffset(int pValue) {_distrListOffset=pValue;}
    public int getDistrListOffset(){return _distrListOffset;}

    public void setDistrListPageSize(int pValue) {_distrListPageSize=pValue;}
    public int getDistrListPageSize(){return _distrListPageSize;}

    public LinkedList getDistrListPages() {
	LinkedList pages = new LinkedList();
	BusEntityDataVector distrV = _product.getMappedDistributors();
	if(distrV!=null) {
	    for(int jj=0,ii=0; ii<distrV.size(); ii+=_distrListPageSize, jj++) {
		pages.add(new Integer(jj));
	    }
	}
	return pages;
    }

    public void setImageFile(FormFile pFile) {
     _imageFile=pFile;
    }
    public FormFile getImageFile(){return _imageFile;}

    public void setMsdsFile(FormFile pFile) {_msdsFile=pFile;}
    public FormFile getMsdsFile(){return _msdsFile;}

    public void setDedFile(FormFile pFile) {_dedFile=pFile;}
    public FormFile getDedFile(){return _dedFile;}

    public void setSpecFile(FormFile pFile) {_specFile=pFile;}
    public FormFile getSpecFile(){return _specFile;}

    public Date getCurrentDate() {
	GregorianCalendar cal = new GregorianCalendar();
	cal.setTime(new Date(System.currentTimeMillis()));
	cal = new GregorianCalendar
	    (cal.get(GregorianCalendar.YEAR),
	     cal.get(GregorianCalendar.MONTH),
	     cal.get(GregorianCalendar.DAY_OF_MONTH));
	return cal.getTime();
    }

    public int getCurrentDistrListPage() {
	int currentPage = _distrListOffset/_distrListPageSize;
	return currentPage;
    }

    public int getDistrListCount() {
	int size = _product.getMappedDistributors().size();
	return size;
    }

    public void setHazmat(String Hazmat) {
	_product.setHazmat(Hazmat);
    }

    public String getHazmat() {
	return _product.getHazmat();
    }

    public void setUom(String pValue) {
        _uom = pValue;
        
    }
        
    public String getUom(){
        return _uom;
    }


    public void setTempDistributorUom(String pValue) {_tempDistributorUom=pValue;}
    public String getTempDistributorUom(){return new String("");}

    //Jd extra data
    //private String _cas70FtlPrice;
    public void setCas70FtlPrice(String pValue) {_cas70FtlPrice=pValue;}
    public String getCas70FtlPrice(){return _cas70FtlPrice;}
    //private String _cas70ListPrice;
    public void setCas70ListPrice(String pValue) {_cas70ListPrice=pValue;}
    public String getCas70ListPrice(){return _cas70ListPrice;}
    //private String _cas80FtlPrice;
    public void setCas80FtlPrice(String pValue) {_cas80FtlPrice=pValue;}
    public String getCas80FtlPrice(){return _cas80FtlPrice;}
    //private String _cas80ListPrice;
    public void setCas80ListPrice(String pValue) {_cas80ListPrice=pValue;}
    public String getCas80ListPrice(){return _cas80ListPrice;}
    //private String _ftlUsPrice;
    public void setFtlUsPrice(String pValue) {_ftlUsPrice=pValue;}
    public String getFtlUsPrice(){return _ftlUsPrice;}
    //private String _listUsPrice;
    public void setListUsPrice(String pValue) {_listUsPrice=pValue;}
    public String getListUsPrice(){return _listUsPrice;}

    /**
     * Reset all properties to their default values.
     *
     * @param mapping The mapping used to select this instance
     * @param request The servlet request we are processing
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {

	// create a new detail object and convert nulls to empty strings
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
