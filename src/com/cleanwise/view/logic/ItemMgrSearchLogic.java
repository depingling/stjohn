
package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import java.rmi.RemoteException;


import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

/**
 * <code>ItemMgrSearchLogic</code> implements the logic needed to
 * add items to the catalog.
 *
 * @author yuriy
 */
public class ItemMgrSearchLogic {
    static final int ITEM_SEARCH_CATALOG_TYPE = 100;
    static final int ITEM_SEARCH_MASTER_TYPE = 101;
    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    static LinkedList mCurrentTree = new LinkedList();
    static int mCurrentCatalogId = 0;

    public static void init(HttpServletRequest request,
			    ItemMgrSearchForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
	CatalogMgrDetailForm detailForm =
	    (CatalogMgrDetailForm) session.getAttribute("CATALOG_DETAIL_FORM");
	int catalogId=detailForm.getDetail().getCatalogId();
	pForm.setCatalogId(catalogId);
	pForm.setSelectorBox(new String[0]);
	//pForm.setProducts(new ProductDataVector());
	pForm.setListIds(new IdVector());

	// Get the latest catalog structure.
	APIAccess factory = (APIAccess)session.getAttribute
	    (Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}
	CatalogInformation catalogInformationEjb   =
	    factory.getCatalogInformationAPI();

	//get top categories
	mCurrentTree = null;
	mCurrentTree = new LinkedList();
	mCurrentCatalogId = catalogId;
	CatalogCategoryDataVector catDV =
	    catalogInformationEjb.getTopCatalogCategories(catalogId);
	for(int ii=0; ii<catDV.size(); ii++) {
	    CatalogCategoryData catD = (CatalogCategoryData)catDV.get(ii);
	    catD.setTreeLevel(1);
	    mCurrentTree.add(catD);
	    loadTreeNode( catalogInformationEjb,2,catD );
	}

	pForm.setCategoryTree(mCurrentTree);

	return;
    }

    private static void loadTreeNode( CatalogInformation pCatInfoEjb,
				      int pTreeLevel,
				      CatalogCategoryData pCatData)
    {
	try {
	    int catId = pCatData.getCatalogCategoryId();
	    CatalogCategoryDataVector subCatDV =
		pCatInfoEjb.getCatalogChildCategories
		(mCurrentCatalogId, catId);
	    for ( int jj = 0; jj < subCatDV.size(); jj++ ) {
		CatalogCategoryData catD2 =
		    (CatalogCategoryData)subCatDV.get(jj);
		catD2.setParentCategory(pCatData.getItemData());
		catD2.setTreeLevel(pTreeLevel);
		mCurrentTree.add(catD2);
		loadTreeNode(pCatInfoEjb, pTreeLevel + 1, catD2);
	    }
	}
	catch (Exception e) {
	    System.err.println("ItemMgrSearchLogic.loadTreeNode: " + e);
	}
    }

    /**
     * Searches for item.
     * Populates the actionErrors param, the ProductDataVector param, and the IdVector param
     */
    public static void searchForItem (HttpServletRequest request,ActionErrors ae,
    String category,String shortDesc,String longDesc,String itemPropName,String itemPropTempl,
    String manuIdS,String distrIdS,String skuNumber, String skuType,
    int searchType, ProductDataVector productDV, IdVector itemIds, ItemMgrSearchForm pItemMgrForm,
    int catalogId)
    throws Exception{
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	Distributor distributorEjb = factory.getDistributorAPI();
	Manufacturer manufacturerEjb = factory.getManufacturerAPI();

        //Create a set of filters
	Vector searchConditions = new Vector();
	//Category
	if(category!=null && category.trim().length()>0){
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.CATALOG_CATEGORY,
			       SearchCriteria.CONTAINS_IGNORE_CASE,category);
	    searchConditions.add(sc);
	}
	//Short Desc
	if(shortDesc!=null && shortDesc.trim().length()>0){
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.PRODUCT_SHORT_DESC,
			       SearchCriteria.CONTAINS_IGNORE_CASE,shortDesc);
	    searchConditions.add(sc);
	}

	//Long Desc
	if(longDesc!=null && longDesc.trim().length()>0){
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.PRODUCT_LONG_DESC,
			       SearchCriteria.CONTAINS_IGNORE_CASE,longDesc);
	    searchConditions.add(sc);
	}

	//Item Meta Property
	if(itemPropName!=null && itemPropName.trim().length()>0 &&
           itemPropTempl!=null && itemPropTempl.trim().length()>0){
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.ITEM_META+itemPropName,
			       SearchCriteria.CONTAINS_IGNORE_CASE,itemPropTempl);
	    searchConditions.add(sc);
	}

	//Manufacturer
	if(manuIdS!=null && manuIdS.trim().length()>0){
	    int manuId = 0;
	    try{
		manuId = Integer.parseInt(manuIdS);
		ManufacturerData manuD = manufacturerEjb.getManufacturer(manuId);
                if(pItemMgrForm != null){
                    pItemMgrForm.setManuName(manuD.getBusEntity().getShortDesc());
                }
	    }catch(NumberFormatException exc) {
		ae.add("error",new ActionError("item.search.wrong_manufacturer"));
		return;
	    }catch(DataNotFoundException de) {
		// no such manufacturer - treat not as an error,
		// but as a search with no results
		return;
	    }
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.MANUFACTURER_ID,
			       SearchCriteria.EXACT_MATCH,new Integer(manuId));
	    searchConditions.add(sc);
	}

	//distributor
	if(distrIdS!=null && distrIdS.trim().length()>0){
	    int distrId = 0;
	    try{
		distrId = Integer.parseInt(distrIdS);
                if(distrId!=0){
		  DistributorData distrD = distributorEjb.getDistributor(distrId);
                  if(pItemMgrForm != null){
                    pItemMgrForm.setDistributorName(distrD.getBusEntity().getShortDesc());
                  }
                }
	    }catch(NumberFormatException exc) {
		ae.add("error",new ActionError("item.search.wrong_distributor"));
		return;
	    }catch(DataNotFoundException de) {
		// no such distributor - treat not as an error,
		// but as a search with no results
		return;
	    }
	    SearchCriteria sc = new
		SearchCriteria(SearchCriteria.DISTRIBUTOR_ID,
			       SearchCriteria.EXACT_MATCH,new Integer(distrId));
	    searchConditions.add(sc);
	}

	//sku
	if(skuNumber!=null && skuNumber.trim().length()>0){
	    if(skuType == null || skuType.equals("SystemCustomer")) {
              if(searchType == ITEM_SEARCH_MASTER_TYPE) {
		SearchCriteria sc = new
		    SearchCriteria(SearchCriteria.CLW_SKU_NUMBER,
				   SearchCriteria.EXACT_MATCH,
				   skuNumber);
                    searchConditions.add(sc);
              } else {
		SearchCriteria sc = new
		    SearchCriteria(SearchCriteria.CLW_CUST_SKU_NUMBER,
				   SearchCriteria.EXACT_MATCH,
				   skuNumber);
                    searchConditions.add(sc);
              }

	    }else if(skuType.equals("System")){
		SearchCriteria sc = new
		    SearchCriteria(SearchCriteria.CLW_SKU_NUMBER,
				   SearchCriteria.EXACT_MATCH,
				   skuNumber);
		searchConditions.add(sc);

            }else if(skuType.equals("Customer")){
		SearchCriteria sc = new
		    SearchCriteria(SearchCriteria.CUST_SKU_NUMBER,
				   SearchCriteria.EXACT_MATCH,
				   skuNumber);
		searchConditions.add(sc);

	    }else if(skuType.equals("Manufacturer")){
		SearchCriteria sc = new
		    SearchCriteria(SearchCriteria.MANUFACTURER_SKU_NUMBER,
				   SearchCriteria.EXACT_MATCH,
				   skuNumber);
		searchConditions.add(sc);

	    }else if(skuType.equals("Distributor")){
		SearchCriteria sc = new
		    SearchCriteria(SearchCriteria.DISTRIBUTOR_SKU_NUMBER,
				   SearchCriteria.EXACT_MATCH,
				   skuNumber);
		searchConditions.add(sc);
	    }else if(skuType.equals("Id")){
		SearchCriteria sc = new
		    SearchCriteria(SearchCriteria.ITEM_ID,
				   SearchCriteria.EXACT_MATCH,
				   skuNumber);
		searchConditions.add(sc);
	    }
	}



	if(searchType == ITEM_SEARCH_MASTER_TYPE) {
            List l = catalogInfEjb.searchProducts(searchConditions);
            if(l!=null){
                itemIds.addAll(l);
            }
	} else {
	    List l = catalogInfEjb.searchCatalogProducts(catalogId,searchConditions);
            if(l!=null){
                itemIds.addAll(l);
            }
	}


	//Populate data
	ProductDataVector tmpProductDV = new ProductDataVector();
	if(searchType == ITEM_SEARCH_CATALOG_TYPE) {
            if(catalogId == 0){
	        tmpProductDV = catalogInfEjb.getProductCollection(itemIds);
            } else {
	        tmpProductDV = catalogInfEjb.getCatalogClwProductCollection(catalogId,itemIds, 0, SessionTool.getCategoryToCostCenterView(session, 0, catalogId));
            }
	} else {
	    tmpProductDV = catalogInfEjb.getCatalogClwProductCollection(catalogId,itemIds, 0, SessionTool.getCategoryToCostCenterView(session, 0, catalogId));
	}
        productDV.addAll(tmpProductDV);
    }


    /**
     * Searches for item. Takes conditions form the form
     *
     */
    public static ActionErrors searchForItem (HttpServletRequest request,
					      ItemMgrSearchForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
	ActionErrors ae = new ActionErrors();

        CatalogMgrDetailForm catalogDetail = (CatalogMgrDetailForm)session.getAttribute("CATALOG_DETAIL_FORM");
        int catalogId=catalogDetail.getDetail().getCatalogId();

	//Clear the search results
	pForm.setListIds(new IdVector());
	pForm.setProducts(new ProductDataVector());

        //get the search data from the form
	String category = pForm.getCategoryTempl();
	String shortDesc = pForm.getShortDescTempl();
	String longDesc = pForm.getLongDescTempl();
	String itemPropName = pForm.getItemPropertyName();
        String itemPropTempl = pForm.getItemPropertyTempl();
	String manuIdS = pForm.getManuId();
	String distrIdS = pForm.getDistributorId();
	String catalogType =catalogDetail.getDetail().getCatalogTypeCd();
	String skuNumber = pForm.getSkuTempl();
        String skuType = pForm.getSkuType();

        //find the items
        IdVector itemIds = new IdVector();
        ProductDataVector productDV = new ProductDataVector();
	if(catalogType.equals(RefCodeNames.CATALOG_TYPE_CD.SYSTEM)) {
	    searchForItem(request,ae,category,shortDesc,longDesc,itemPropName,itemPropTempl,
            manuIdS,distrIdS,skuNumber,skuType,ITEM_SEARCH_MASTER_TYPE,
            productDV,itemIds,pForm,catalogId);
	} else {
	    searchForItem(request,ae,category,shortDesc,longDesc,itemPropName,itemPropTempl,
            manuIdS,distrIdS,skuNumber,skuType,ITEM_SEARCH_CATALOG_TYPE,
            productDV,itemIds,pForm,catalogId);
	}

	//setup the form
	pForm.setSelectorBox(new String[0]);
	pForm.setResultSource("");
        pForm.setListIds(itemIds);
	pForm.setProducts(productDV);
	return ae;
    }

    /**
     * Refreshes item list
     *
     */
    public static void refreshListOfItems (HttpServletRequest request,
					   ItemMgrSearchForm pForm)
	throws Exception
    {

	HttpSession session = request.getSession();
	APIAccess factory = (APIAccess)
	    session.getAttribute(Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}

	CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	pForm.setSelectorBox(new String[0]);
	String productIdS = request.getParameter("itemId");

	if(productIdS!=null) {
	    try{
		int productId = Integer.parseInt(productIdS);
		ProductDataVector productDV = pForm.getProducts();

		for(int ii=0; ii<productDV.size(); ii++) {
		    ProductData productD = (ProductData) productDV.get(ii);
		    if(productD.getProductId()==productId) {
			productD = catalogInfEjb.getProduct(productId);
			productDV.remove(ii);
			productDV.add(ii, productD);
			break;
		    }
		}
	    }catch(NumberFormatException exc) {
		//Just do nothing. Theoreticaly should not happen
	    }catch(DataNotFoundException exc) {
		//Just do nothing. Theoreticaly should not happen
	    }
	}
    }
    /**
     * Prepare to show items on tree
     *
     */
    public static ActionErrors prepareToShow (HttpServletRequest request,
					      ItemMgrSearchForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
	ActionErrors ae = new ActionErrors();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}
	CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	String[] selected = pForm.getSelectorBox();
	if(pForm.getProducts()==null) {
	    ae.add("error", new ActionError("item.search.nothing_selected"));
	    return ae;
	}
	if(selected ==null || selected.length==0) {
	    ae.add("error", new ActionError("item.search.nothing_selected"));
	    return ae;
	}

	ProductDataVector productDV = pForm.getProducts();
	int size=productDV.size();
	IdVector selectedIdV = new IdVector();
	for(int ii=0; ii<selected.length; ii++){
	    int ind = Integer.parseInt(selected[ii]);
	    if(ind>=size) {
		continue;
	    }
	    ProductData pD = (ProductData) productDV.get(ind);
	    selectedIdV.add(new Integer(pD.getProductId()));
	}
	if(selectedIdV.size()==0) {
	    ae.add("error", new ActionError("item.search.nothing_selected"));
	}
	pForm.setSelectedProductIds(selectedIdV);
	return ae;
    }

    /**
     * Removes items from catalog
     *
     */
    public static ActionErrors removeFromCatalog (HttpServletRequest request,
						  ItemMgrSearchForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
	ActionErrors ae = new ActionErrors();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}
	Catalog catalogEjb = factory.getCatalogAPI();
        Contract contractEjb = factory.getContractAPI();
        OrderGuide orderGuideEjb = factory.getOrderGuideAPI();
	CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	String[] selected = pForm.getSelectorBox();
	if(pForm.getProducts()==null) {
	    ae.add("error", new ActionError("item.search.nothing_selected"));
	    return ae;
	}
	if(selected ==null || selected.length==0) {
	    ae.add("error", new ActionError("item.search.nothing_selected"));
	    return ae;
	}

	ProductDataVector productDV = pForm.getProducts();
	int size=productDV.size();
	IdVector selectedIdV = new IdVector();
	for(int ii=0; ii<selected.length; ii++){
	    int ind = Integer.parseInt(selected[ii]);
	    if(ind>=size) {
		continue;
	    }
	    ProductData pD = (ProductData) productDV.get(ind);
	    selectedIdV.add(new Integer(pD.getProductId()));
	}
	if(selectedIdV.size()==0) {
	    ae.add("error", new ActionError("item.search.nothing_selected"));
	}
	catalogEjb.removeCatalogSubTrees(pForm.getCatalogId(),selectedIdV,user);
	IdVector listIds = pForm.getListIds();
	for(int ii=0; ii<selectedIdV.size(); ii++) {
	    int id = ((Integer) selectedIdV.get(ii)).intValue();
	    for(int jj=0; jj<listIds.size(); jj++) {
		if(((Integer)listIds.get(jj)).intValue() == id) {
		    listIds.remove(jj);
		    break;
		}
	    }
	}

	//Create page list
	productDV = catalogInfEjb.getCatalogClwProductCollection
	    (pForm.getCatalogId(),listIds, 0, SessionTool.getCategoryToCostCenterView(session, 0, pForm.getCatalogId()));
	pForm.setProducts(productDV);
	pForm.setSelectorBox(new String[0]);
	return ae;
    }

    public static ActionErrors addCategoryToItem(HttpServletRequest request,
						 ItemMgrSearchForm pForm)
	throws Exception {

	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
	ActionErrors ae = new ActionErrors();
	APIAccess factory = (APIAccess)
	    session.getAttribute(Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}

	String [] ids_sel = pForm.getSelectorBox();
	ProductDataVector productDV = pForm.getProducts();

	Catalog catalogEjb = factory.getCatalogAPI();
	for (int idx = 0; idx < ids_sel.length; idx++ ) {
	    int pid = Integer.parseInt(ids_sel[idx]);
	    ProductData pD = (ProductData) productDV.get(pid);
	    pD = catalogEjb.addProductCategory
		(pForm.getCatalogId(),
		 pForm.getSelectedCategoryId(),
		 pD.getProductId(),
		 user,
                SessionTool.getCategoryToCostCenterView(session, 0, pForm.getCatalogId()));
	    productDV.remove(pid);
	    productDV.add(pid, pD);

	}

	pForm.setProducts(productDV);
	pForm.setSelectorBox(new String[0]);

	return ae;
    }

    public static ActionErrors removeCategoryFromItem
	(HttpServletRequest request,
	 ItemMgrSearchForm pForm)
	throws Exception {

	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
	ActionErrors ae = new ActionErrors();
	APIAccess factory = (APIAccess)
	    session.getAttribute(Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}

	String s = (String)request.getParameter("catalogId");
	if ( s == null || s.length() == 0 ) {
	    ae.add("clear item category",
		   new ActionError("catalogId is missing"));
	    return ae;
	}
	int catalogId = Integer.parseInt(s);

	s = (String)request.getParameter("itemId");
	if ( s == null || s.length() == 0 ) {
	    ae.add("clear item category",
		   new ActionError("itemId is missing"));
	    return ae;
	}
	int itemId = Integer.parseInt(s);

	s = (String)request.getParameter("categoryId");
	if ( s == null || s.length() == 0 ) {
	    ae.add("clear item category",
		   new ActionError("itemId is missing"));
	    return ae;

	}
	int catalogCategoryId = Integer.parseInt(s);

	Catalog catalogEjb = factory.getCatalogAPI();
	CatalogInformation catalogInfEjb =
	    factory.getCatalogInformationAPI();
	catalogEjb.removeCategoryFromItem(catalogId,
					  catalogCategoryId,
					  itemId,
					  user);

	// Get the updated info.
	ProductDataVector productDV = pForm.getProducts();
	ProductData productD =
	    catalogInfEjb.getCatalogClwProduct(catalogId,itemId,0,0,SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
	for ( int idx = 0; idx < productDV.size(); idx++ ) {
	    ProductData tProdD = (ProductData)productDV.get(idx);
	    if ( tProdD.getProductId() == itemId ) {
		productDV.remove(idx);
		productDV.add(idx, productD);
	    }
	}
	pForm.setProducts(productDV);

	return ae;
    }
    /**
     * Adds items to catalog from from pop-up window.
     * Ignores item if it already exists in the catalog
     *
     */
    public static ActionErrors addFromLookup (HttpServletRequest request,
					      ItemMgrSearchForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
	ActionErrors ae = new ActionErrors();
	APIAccess factory = (APIAccess)
	    session.getAttribute(Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}
	Catalog catalogEjb = factory.getCatalogAPI();
	CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();

	LinkedList addObjects = (LinkedList)
	    session.getAttribute("lookupValue");
	session.setAttribute("lookupValue", new LinkedList());
	if(addObjects == null) {
	    addObjects = new LinkedList();
	}

	CatalogMgrStructureForm structureForm =
	    (CatalogMgrStructureForm)
	    session.getAttribute("CATALOG_STRUCTURE_FORM");

	int parentId = 0;
	CatalogMgrItemAddForm sessionItemForm = (CatalogMgrItemAddForm)
	    session.getAttribute("CATALOG_ITEM_ADD_FORM");
	if ( null != sessionItemForm) {
	    parentId = sessionItemForm.getSelectedCategoryId();
	}

	IdVector selectedIdV = new IdVector();
	for(int ii=0; ii<addObjects.size(); ii++) {
	    ProductData productD = (ProductData) addObjects.get(ii);
	    selectedIdV.add(new Integer(productD.getProductId()));
	}

	catalogEjb.addCatalogElements(pForm.getCatalogId(),
				      parentId,
				      selectedIdV, user);

	// Lookup the products just added.
	ProductDataVector products = new ProductDataVector();
	for(int ii = 0; ii< selectedIdV.size(); ii++) {
	    Integer itemIdI = (Integer)selectedIdV.get(ii);
	    int itemId = itemIdI.intValue();
	    ProductData productD = catalogInfEjb.getCatalogClwProduct
		(pForm.getCatalogId(), itemId,0,0,SessionTool.getCategoryToCostCenterView(session, 0, pForm.getCatalogId() ));
	    products.add(productD);
	}

	pForm.setProducts(products);
	pForm.setListIds(selectedIdV);
	pForm.setSelectorBox(new String[0]);

	session.setAttribute("ITEM_SEARCH_FORM", pForm);

	return ae;
    }

    /**
     * Adds new just created item (System catalog only)
     *
     */
    public static ActionErrors tieNewItem (HttpServletRequest request,
					   ItemMgrSearchForm pForm)
	throws Exception
    {
	HttpSession session = request.getSession();
	String user =(String)session.getAttribute(Constants.USER_NAME);
	ActionErrors ae = new ActionErrors();
	APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}
	Catalog catalogEjb = factory.getCatalogAPI();
	CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
	CatalogMgrStructureForm structureForm =
	    (CatalogMgrStructureForm) session.getAttribute("CATALOG_STRUCTURE_FORM");
	int parentId = 0;
	if(structureForm!=null) {
	    String indexS = structureForm.getBackBox();
	    if(indexS!=null) {
		int index = Integer.parseInt(indexS);
		Vector tree = (Vector) structureForm.getCatalogTree();
		if(tree!=null && index>=0 && index<tree.size()) {
		    CatalogTreeNode node = (CatalogTreeNode) tree.get(index);
		    node.setCollapsed(false);
		    parentId = node.getId();
		}
	    }
	}
	IdVector itemIdV = new IdVector();
	String itemIdS = request.getParameter("itemId");
	try {
	    Integer itemIdI = new Integer(itemIdS);
	    itemIdV.add(itemIdI);
	    catalogEjb.addCatalogElements(pForm.getCatalogId(), parentId, itemIdV, user);

	    IdVector itemIds = pForm.getListIds();
	    itemIds.add(0,itemIdI);
	    pForm.setListIds(itemIds);

	    ProductDataVector products = new ProductDataVector();
	    ProductData productD = catalogInfEjb.getProduct(itemIdI.intValue());
	    products.add(productD);

	    ProductDataVector prodOnScreen = pForm.getProducts();
	    for(int jj=0; jj<prodOnScreen.size(); jj++) {
		products.add(prodOnScreen.get(jj));
	    }

	    pForm.setProducts(products);
	} catch (NumberFormatException exc) {}
	pForm.setSelectorBox(new String[0]);
	return ae;
    }

    /**
     *  <code>sort</code>
     *
     *@param  request        a <code>HttpServletRequest</code> value
     *@param  form           an <code>ActionForm</code> value
     *@exception  Exception  if an error occurs
     */
    public static void sort(HttpServletRequest request,
			    ItemMgrSearchForm form)
	throws Exception {

        // Get a reference to the admin facade
        HttpSession session = request.getSession();
	ProductDataVector products = form.getProducts();
	if (products == null) {
	    return;
	}
	String sortField = request.getParameter("sortField");
	DisplayListSort.sort(products, sortField);
    }

    public static void getExternalProductInfo(HttpServletRequest request) {

        // Get the product information.
        ProductData pd = new ProductData();
        HttpSession session = request.getSession();
try {

        String itemid = request.getParameter("itemId");
        String accountid = request.getParameter("accountId");

	APIAccess factory = new APIAccess();
	if (factory==null) {
	    throw new Exception("No APIAccess.");
	}
	ProductInformation prodEjb = factory.getProductInformationAPI();
    pd = prodEjb.getProduct(Integer.parseInt(itemid));
}
catch (Exception e) {
e.printStackTrace();
}
        session.setAttribute("ProductData", pd);

    }

    public static ActionErrors resetItemDistributor
        (HttpServletRequest request,
         ItemMgrSearchForm pForm)
	throws Exception
    {

        HttpSession session = request.getSession();
        String user =(String)session.getAttribute(Constants.USER_NAME);
        ActionErrors ae = new ActionErrors();
        APIAccess factory = (APIAccess)
            session.getAttribute(Constants.APIACCESS);
        if (factory==null) {
            throw new Exception("No APIAccess.");
        }

        String [] ids_sel = pForm.getSelectorBox();
        ProductDataVector productDV = pForm.getProducts();

        Catalog catalogEjb = factory.getCatalogAPI();
        for (int idx = 0; idx < ids_sel.length; idx++ ) {
            int pid = Integer.parseInt(ids_sel[idx]);
            ProductData pD = (ProductData) productDV.get(pid);
            int oldDistId = 0;
            if (pD.getCatalogDistributor() != null){
                oldDistId = pD.getCatalogDistributor().getBusEntityId();
            }

            pD.setCatalogDistributor(null);
            catalogEjb.updateProductDistributor
                (pForm.getCatalogId(), 0, oldDistId,
                 pD.getProductId(),user);
            productDV.remove(pid);
            productDV.add(pid, pD);

        }

        pForm.setProducts(productDV);
        pForm.setSelectorBox(new String[0]);

        return ae;
    }

}



