/*
 * AggregateItemLogic.java
 *
 * Created on December 2, 2003, 4:02 PM
 */

package com.cleanwise.view.logic;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import java.math.BigDecimal;
/**
 * Manages items accross catalogs and contracts based off a set of filters
 * @author  bstevens
 */
public class AggregateItemLogic {
    private static final BigDecimal ZERO = new BigDecimal(0);
    /**
     *Converts an AggregateItemView such as to be able to be viewed through the UI (creates the string
     *objects from the BigDecimals etc).
     */
    private static void convertForUi(AggregateItemView pView){
        pView.setDistIdStr(Integer.toString(pView.getDistId()));
        if(pView.getItemCost() != null){
            pView.setItemCostStr(pView.getItemCost().toString());
        }
        if(pView.getItemPrice() != null){
            pView.setItemPriceStr(pView.getItemPrice().toString());
        }
        if(pView.getDistBaseCost() != null){
            pView.setDistBaseCostStr(pView.getDistBaseCost().toString());
        }
    }

    /**
     *Converts an AggregateItemViewVector such as to be able to be viewed through the UI (creates the string
     *objects from the BigDecimals etc).
     */
    private static void convertForUi(AggregateItemViewVector pView){
        Iterator it = pView.iterator();
        while(it.hasNext()){
            convertForUi((AggregateItemView) it.next());
        }
    }

    /**
     *Converts an AggregateItemViewVector such as to be able to be sent to the db for updating.
     *Strings to their BigDecimal equivilent etc.  Does error checking.
     */
    private static void convertFromUi(APIAccess factory,ActionErrors ae,AggregateItemViewVector pView,
    boolean pItemsToCatalog,boolean pItemsToContract,boolean pItemsToOrderGuide)
    throws Exception{
        IdVector distIds = new IdVector();
        Iterator it = pView.iterator();
        while(it.hasNext()){
            convertFromUi(ae,(AggregateItemView) it.next(),pItemsToCatalog,pItemsToContract,pItemsToOrderGuide,distIds);
        }
        if(!distIds.isEmpty()){
            //XXX could be optimized to not grab the entire distributor data object when
            //we just need the id
            DistributorDataVector dists = factory.getDistributorAPI().getDistributorCollectionByIdList(distIds);
            it = dists.iterator();
            while(it.hasNext()){
                DistributorData dd = (DistributorData) it.next();
                Iterator subIt = distIds.iterator();
                while(subIt.hasNext()){
                    int id = ((Integer) subIt.next()).intValue();
                    if(id == dd.getBusEntity().getBusEntityId()){
                        subIt.remove();
                        continue;
                    }
                }
            }
        }
        it = distIds.iterator();
        while(it.hasNext()){
            Integer distId = (Integer) it.next();
            if(distId.intValue() > 0){
                ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("admin.aggregate.items.bad.distributor",distId));
            }
        }
    }


    /**
     *Converts an AggregateItemView such as to be able to be sent to the db for updating.
     *Strings to their BigDecimal equivilent etc.  Does error checking.
     */
    private static void convertFromUi(ActionErrors ae,AggregateItemView pView,
    boolean pItemsToCatalog,boolean pItemsToContract,boolean pItemsToOrderGuide,
    IdVector distIds){
        try{
            Integer distId = new Integer(pView.getDistIdStr());
            pView.setDistId(distId.intValue());
            if(!distIds.contains(distId)){
                distIds.add(distId);
            }
        }catch(RuntimeException e){
            //only error if we are adding to a catalog
            if(pItemsToCatalog){
                ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","Distributor Id"));
            }
        }
        if(pItemsToCatalog && pView.getDistId()==0){
            //ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("catalog.distributors.no_distributor"));
        }

        try{
            pView.setItemCost(new BigDecimal(pView.getItemCostStr()));
        }catch(RuntimeException e){
            //only error if we are adding to a contract
            if(pItemsToContract){
                ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","Item Cost"));
            }
        }
        if(pItemsToContract && (pView.getItemCost() == null ||pView.getItemCost().compareTo(ZERO) < 0)){
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidAmount","Item Cost"));
        }

        try{
            pView.setItemPrice(new BigDecimal(pView.getItemPriceStr()));
        }catch(RuntimeException e){
            //only error if we are adding to a contract
            if(pItemsToContract){
                ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","Item Price"));
            }
        }
        if(pItemsToContract && (pView.getItemPrice() == null || pView.getItemPrice().compareTo(ZERO) < 0)){
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidAmount","Item Price"));
        }

        try{
            pView.setDistBaseCost(new BigDecimal(pView.getDistBaseCostStr()));
        }catch(RuntimeException e){
            //only error if we are adding to a contract
            if(pItemsToContract){
                ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidNumber","Distributor Base Cost"));
            }
        }
        if(pItemsToContract && (pView.getDistBaseCost() == null || pView.getDistBaseCost().compareTo(ZERO) < 0)){
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.invalidAmount","Distributor Base Cost"));
        }
    }

    /**
     *Initializes the wizard and anything else that the aggregate item manger needs.  Should only be
     *called once upon entering the wizard or the state will be lost.
     */
    public static void init(HttpServletRequest request,ActionForm form, boolean catalogSearchOveride)throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        if(sForm.getAccounts() != null){
            sForm.getAccounts().resetState();
        }
        if(sForm.getDistributors() != null){
            sForm.getDistributors().resetState();
        }
        if(sForm.getStores() != null){
            sForm.getStores().resetState();
        }
        if(sForm.getDistributorsNewlySelected() != null){
            sForm.getDistributorsNewlySelected().clear();
        }
        if(sForm.getAccountsNewlySelected() != null){
            sForm.getAccountsNewlySelected().clear();
        }
        if(sForm.getStoresNewlySelected() != null){
            sForm.getStoresNewlySelected().clear();
        }
        sForm.setWizardStep(null);
        sForm.setUsingCatalogSearch(catalogSearchOveride);
    }

    /**
     *Searches for distributors to filter off of
     */
    public static void searchDistributors(HttpServletRequest request,ActionForm form)throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        String fieldValue = sForm.getDistSearchField();
        String fieldSearchType = sForm.getDistSearchType();
        String searchState = sForm.getDistSearchState();
        String searchCity = null;
        String searchCounty = sForm.getDistSearchCounty();
        String searchPostalCode = sForm.getDistSearchPostalCode();
        String searchGroupId = sForm.getDistSearchGroupId();
        DistributorDataVector dv = DistMgrLogic.search(request, fieldValue,fieldSearchType,searchCity, searchState,
        searchCounty,searchPostalCode,searchGroupId);
        sForm.setDistributors(new SelectableObjects(sForm.getDistributorsNewlySelected(),dv,null));
    }

    /**
     *Searches for stores to filter off of
     */
    public static void searchStores(HttpServletRequest request,ActionForm form)throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        String fieldValue = sForm.getStoreSearchField();
        String fieldSearchType = sForm.getStoreSearchType();
        String searchGroupId = sForm.getStoreSearchGroupId();
        StoreDataVector dv = StoreMgrLogic.search(request, fieldValue, fieldSearchType,searchGroupId);
        sForm.setStores(new SelectableObjects(sForm.getStoresNewlySelected(),dv,null));
    }


    /**
     *Searches for accounts to filter off of
     */
    public static void searchAccounts(HttpServletRequest request,ActionForm form)throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        String fieldValue = sForm.getAcctSearchField();
        String fieldSearchType = sForm.getAcctSearchType();
        String searchGroupId = sForm.getAcctSearchGroupId();
        AccountDataVector dv = AccountMgrLogic.search(request, fieldValue,fieldSearchType,searchGroupId);
        sForm.setAccounts(new SelectableObjects(sForm.getAccountsNewlySelected(),dv,null));
    }

    /**
     *Searches for accounts to filter off of
     */
    public static void searchCatalogs(HttpServletRequest request,ActionForm form)throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        String fieldValue = sForm.getCatalogSearchField();
        String fieldSearchType = sForm.getCatalogSearchType();
        CatalogDataVector dv = CatalogMgrLogic.search(request,fieldValue,fieldSearchType);
        sForm.setCatalogs(new SelectableObjects(sForm.getCatalogsNewlySelected(),dv,null));
    }

    /**
     *Saves the search state based off where we are in the wizard step.
     */
    public static void saveSearchState(HttpServletRequest request,ActionForm form){
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        String currentWizardStep = sForm.getWizardStep();
        if(sForm.getDistributors() != null && currentWizardStep.indexOf("dist") > -1){
            sForm.setDistributorsNewlySelected(sForm.getDistributors().getNewlySelected());
        }
        if(sForm.getAccounts() != null  && currentWizardStep.indexOf("acc") > -1){
            sForm.setAccountsNewlySelected(sForm.getAccounts().getNewlySelected());
        }
        if(sForm.getCatalogs() != null && currentWizardStep.indexOf("cat") > -1){
            sForm.setCatalogsNewlySelected(sForm.getCatalogs().getNewlySelected());
        }
        if(sForm.getStores() != null && currentWizardStep.indexOf("store") > -1){
            sForm.setStoresNewlySelected(sForm.getStores().getNewlySelected());
        }
    }

    /**
     *Searches for the items we wish to manage
     */
    public static ActionErrors searchItems(HttpServletRequest request,ActionForm form)throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        ActionErrors ae = new ActionErrors();
        String category = sForm.getItemSearchCategory();
        String shortDesc = sForm.getItemSearchShortDesc();
        String longDesc = sForm.getItemSearchLongDesc();
        String itemPropName = sForm.getItemSearchItemPropertyName();
        String itemPropTempl = sForm.getItemSearchItemProperty();
        String manuIdS = sForm.getItemSearchManuId();
        String skuNumber = sForm.getItemSearchSku();
        String skuType = sForm.getItemSearchSkuType();

        //find the items
        IdVector itemIds = new IdVector();
        ProductDataVector dv = new ProductDataVector();
        ItemMgrSearchLogic.searchForItem(request,ae,category,shortDesc,longDesc,itemPropName,itemPropTempl,
        manuIdS,null,skuNumber,skuType,ItemMgrSearchLogic.ITEM_SEARCH_MASTER_TYPE,
        dv,itemIds,null,0);

        sForm.setItems(dv);
        return ae;
    }

    /**
     *Updates the selected aggregate items
     */
    public static ActionErrors updateAggregateItems(HttpServletRequest request,ActionForm form)
    throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        CleanwiseUser user = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (factory==null) {
            throw new Exception("No APIAccess.");
        }
        ItemInformation itemEjb = factory.getItemInformationAPI();

        AggregateItemViewVector aggItems = new AggregateItemViewVector();
        aggItems.addAll(sForm.getAggregateItems().getNewlySelected());

        if(!sForm.isTickItemsToCatalog() && !sForm.isTickItemsToContract() && !sForm.isTickItemsToOrderGuide()){
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.no.action.specified"));
        }
        if(aggItems.size() == 0){
            ae.add("aggregateItems",new ActionError("error.nothing.selected"));
        }
        if(ae.size() > 0){
            return ae;
        }
        convertFromUi(factory,ae,aggItems,sForm.isTickItemsToCatalog(),sForm.isTickItemsToContract(),sForm.isTickItemsToOrderGuide());
        if(ae.size() > 0){
            return ae;
        }

        itemEjb.updateAggregateItemViewList(aggItems,sForm.getCurrManagingItem().getProductId(),user.getUserName(),sForm.isTickItemsToCatalog(),sForm.isTickItemsToContract(),sForm.isTickItemsToOrderGuide());
        //refetch the items
        fetchAggregateItems(request, form);
        return ae;
    }

    /**
     *Removes the selected rows from their catalogs
     */
    public static ActionErrors  removeAggregateItems(HttpServletRequest request,ActionForm form)
    throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (factory==null) {
            throw new Exception("No APIAccess.");
        }
        ItemInformation itemEjb = factory.getItemInformationAPI();

        AggregateItemViewVector aggItems = new AggregateItemViewVector();
        aggItems.addAll(sForm.getAggregateItems().getNewlySelected());

        if(!sForm.isTickItemsToCatalog() && !sForm.isTickItemsToContract() && !sForm.isTickItemsToOrderGuide()){
            ae.add(ActionErrors.GLOBAL_ERROR,new ActionError("error.no.action.specified"));
        }
        if(aggItems.size() == 0){
            ae.add("aggregateItems",new ActionError("error.nothing.selected"));
        }
        if(ae.size() > 0){
            return ae;
        }
        itemEjb.removeAggregateItemViewList(aggItems,sForm.isTickItemsToCatalog(),sForm.isTickItemsToContract(),sForm.isTickItemsToOrderGuide());
        //refetch the items
        fetchAggregateItems(request, form);
        return ae;
    }

    /**
     *Retrieves the item id
     */
    private static int getItemId(HttpServletRequest request,AggregateItemMgrForm sForm,ActionErrors ae){
        int itemId =0;
        String itemIdStr = request.getParameter("itemId");

        if(!Utility.isSet(itemIdStr)){
            if(sForm.getCurrManagingItem() != null){
                itemId = sForm.getCurrManagingItem().getProductId();
            }
        }
        if(itemId == 0){
            if(!Utility.isSet(itemIdStr)){
                ae.add("itemId",new ActionError("error.badRequest","itemId"));
            }else{
                try{
                    itemId = Integer.parseInt(itemIdStr);
                }catch(NumberFormatException e){
                    ae.add("itemId",new ActionError("error.invalidNumber","itemId"));
                }
            }
        }
        if(!(itemId > 0)){
            ae.add("itemId",new ActionError("error.badRequest","itemId"));
        }
        return itemId;
    }

    /**
     *Fetches the item contract catalog views to be updated based off the populated form object
     */
    public static ActionErrors fetchAggregateItems(HttpServletRequest request,ActionForm form)
    throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (factory==null) {
            throw new Exception("No APIAccess.");
        }
        ItemInformation itemEjb = factory.getItemInformationAPI();
        CatalogInformation catEjb = factory.getCatalogInformationAPI();

        //get search criteria
        List sdv;
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        if(!RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR.equals(appUser.getUser().getUserTypeCd())){
            sdv = new StoreDataVector();
            sdv.add(appUser.getUserStore());
        }else{
            sdv = sForm.getStoresNewlySelected();
        }
        List adv = sForm.getAccountsNewlySelected();
        List ddv = sForm.getDistributorsNewlySelected();
        List cdv = sForm.getCatalogsNewlySelected();
        int itemId = getItemId(request, sForm, ae);


        if(ae.size() > 0){
            return ae;
        }

        //preform the search for the items
        AggregateItemViewVector items;
        if(sForm.isUsingCatalogSearch()){
            items = itemEjb.getAggregateItemViewList(itemId, Utility.toIdVector(cdv));
        }else{
            items = itemEjb.getAggregateItemViewList(itemId, Utility.toIdVector(sdv), Utility.toIdVector(adv),Utility.toIdVector(ddv));
        }
        convertForUi(items);
        sForm.setAggregateItems(new SelectableObjects(null, items, null));

        //get the categories that we can add this item to
        IdVector uniqueCatalogs = getUniqueCatalogIds(items);


        ItemDataVector categories = catEjb.getCatalogCategories(uniqueCatalogs);
        Iterator it = categories.iterator();
        HashSet catNames = new HashSet();
        RefCdDataVector cats = new RefCdDataVector();
        while(it.hasNext()){
            ItemData i = (ItemData) it.next();
            if(!catNames.contains(i.getShortDesc())){
                catNames.add(i.getShortDesc());
                RefCdData r =RefCdData.createValue();
                r.setValue(i.getShortDesc());
                cats.add(r);
            }
        }
        session.setAttribute("aggregate.item.available.categories", cats);
        //aggregate.item.available.categories

        //get the item for display
        it = sForm.getItems().iterator();
        if(!(sForm.getCurrManagingItem() != null && itemId == sForm.getCurrManagingItem().getProductId())){
            ProductData currManagingItem = null;
            while(it.hasNext()){
                ProductData searchedProd = (ProductData) it.next();
                if(searchedProd.getItemData().getItemId() == itemId){
                    currManagingItem = searchedProd;
                    break;
                }
            }
            if(currManagingItem == null){
                currManagingItem = catEjb.getProduct(itemId);
            }
            sForm.setCurrManagingItem(currManagingItem);
        }
        return ae;
    }


    /**
     *Adds the selected items to the category specified in the form.  Optionally will add the category
     *to the catalog if it does not already exist.
     */
    private static ActionErrors addItemsToCategories(HttpServletRequest request,ActionForm form, boolean addCats)
    throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        CleanwiseUser user = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

        if(!Utility.isSet(sForm.getCategory())){
            ae.add("category", new ActionError("variable.empty.error","category"));
            return(ae);
        }

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (factory==null) {
            throw new Exception("No APIAccess.");
        }
        CatalogInformation catEJB = factory.getCatalogInformationAPI();
        int itemId = sForm.getCurrManagingItem().getProductId();
        IdVector catIds = getUniqueCatalogIdsOnlyInCatalog(sForm.getAggregateItems().getNewlySelected());
        if(catIds.size() == 0){
            ae.add("aggregateItems",new ActionError("error.nothing.selected"));
        }
        int catCostCtr = 0;
        if(addCats){
            if(Utility.isSet(sForm.getCategoryCostCenter())){
                try{
                    catCostCtr = Integer.parseInt(sForm.getCategoryCostCenter().trim());
                }catch(NumberFormatException e){
                    ae.add("categoryCostCenter",new ActionError("error.invalidNumber","Category Cost Center"));
                }
            }else{
                ae.add("categoryCostCenter",new ActionError("variable.empty.error","Category Cost Center"));
            }
        }
        if(ae.size() > 0){
            return ae;
        }

        String catName = sForm.getCategory();
        String userName = user.getUserName();
        catEJB.addItemToCategories(itemId,catIds,catName,catCostCtr,addCats,userName);
        fetchAggregateItems(request, form);
        return ae;
    }

    /**
     *Parses out all of the Catalog Ids from all of the agregate items in the List provide (assumes
     *the list only contains Aggreagate Items).   Returns an IdVector of all the catalog ids found
     *in the aggreagate items without any duplicates.
     */
    private static IdVector getUniqueCatalogIds(List vw){
        return getUniqueCatalogIdsWorker(vw,false);
    }

    private static IdVector getUniqueCatalogIdsWorker(List vw,boolean onlyInCatalog){
        IdVector retVal = new IdVector();
        Iterator it = vw.iterator();
        while(it.hasNext()){
            AggregateItemView i = (AggregateItemView) it.next();
            boolean addit = false;
            if(onlyInCatalog){
                if(i.getCatalogStructureId() > 0){
                    addit = true;
                }
            }else{
                addit = true;
            }
            if(addit){
                Integer catId = new Integer(i.getCatalogId());
                if(!retVal.contains(catId)){
                    retVal.add(catId);
                }
            }
        }
        return retVal;
    }

    /**
     *Parses out all of the Catalog Ids from all of the agregate items in the List provide (assumes
     *the list only contains Aggreagate Items) where the aggregate item is in the catalog.
     *Returns an IdVector of all the catalog ids found in the aggreagate items without any duplicates.
     */
    private static IdVector getUniqueCatalogIdsOnlyInCatalog(List vw){
        return getUniqueCatalogIdsWorker(vw,true);
    }

    /**
     *Adds the selected items to the category specified in the form.  Optionally will add the category
     *to the catalog if it does not already exist.
     */
    private static ActionErrors removeItemsFromCategories(HttpServletRequest request,ActionForm form, boolean removeFromAllCategories)
    throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        CleanwiseUser user = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (factory==null) {
            throw new Exception("No APIAccess.");
        }
        CatalogInformation catEJB = factory.getCatalogInformationAPI();

        if(!removeFromAllCategories){
            if(!Utility.isSet(sForm.getCategory())){
                ae.add("category", new ActionError("variable.empty.error","category"));
                return(ae);
            }
        }
        int itemId = sForm.getCurrManagingItem().getProductId();
        IdVector catIds = getUniqueCatalogIds(sForm.getAggregateItems().getNewlySelected());
        if(catIds.size() == 0){
            ae.add("aggregateItems",new ActionError("error.nothing.selected"));
            return ae;
        }
        String catName = sForm.getCategory();
        String userName = user.getUserName();
        catEJB.removeItemFromCategories(itemId,catIds,catName,removeFromAllCategories);
        fetchAggregateItems(request, form);
        return ae;
    }

    /**
     *Entry point to preform category modifications
     */
    public static ActionErrors preformCategoryModification(HttpServletRequest request,ActionForm form)
    throws Exception {
        AggregateItemMgrForm sForm = (AggregateItemMgrForm) form;
        if(sForm.getCategoryAction().equals("admin.aggregate.items.addItemsToCat")){
            return addItemsToCategories(request, form, false);
        }else if(sForm.getCategoryAction().equals("admin.aggregate.items.addItemsAndCatToCat")){
            return addItemsToCategories(request, form, true);
        }else if(sForm.getCategoryAction().equals("admin.aggregate.items.remItemsFromCat")){
            removeItemsFromCategories(request, form, false);
        }else if(sForm.getCategoryAction().equals("admin.aggregate.items.remItemsFromAllCats")){
            removeItemsFromCategories(request, form, true);
        }
        return new ActionErrors();
    }
}
