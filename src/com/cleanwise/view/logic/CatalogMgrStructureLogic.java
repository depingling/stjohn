package com.cleanwise.view.logic;

import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.CatalogNode;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.value.AddressData;
import com.cleanwise.service.api.value.PhoneData;

import com.cleanwise.view.forms.CatalogMgrDetailForm;
import com.cleanwise.view.forms.CatalogMgrSearchForm;
import com.cleanwise.view.forms.CatalogMgrStructureForm;
import com.cleanwise.view.forms.ItemMgrSearchForm;

//import com.cleanwise.view.utils.*;
import com.cleanwise.view.utils.Constants;

import java.rmi.RemoteException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.view.utils.SessionTool;


/**
 * <code>CatalogMgrLogic</code> implements the logic needed to
 * manipulate catalog records.
 *
 * @author yuriy
 */
public class CatalogMgrStructureLogic
{

    /**
   * <code>init</code> method.
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
    public static void init(HttpServletRequest request, ActionForm form)
                     throws Exception
    {

        CatalogMgrSearchForm sForm = (CatalogMgrSearchForm)form;
        sForm.setSearchType("catalogId");

        return;
    }

    public static void initCatalogTree(HttpServletRequest request,
                                       ActionForm form)
                                throws Exception
    {

        HttpSession session = request.getSession();
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        sForm.getCatalogTree().clear();

        //get major categories
        ItemDataVector majorCatDV = catalogInformationEjb.getMajorCategories();
        sForm.setMajorCategories(majorCatDV);



        //get top cataegories
        CatalogCategoryDataVector catDV = catalogInformationEjb.getTopCatalogCategories(
                                                  catalogId);
        catDV.sort("CatalogCategoryShortDesc");

        List nodes = catalogInformationEjb.getCatalogNodes(catalogId, catDV, 0);
        sForm.getCatalogTree().addAll(nodes);

        //get top products
        CatalogItemViewVector prodDV = catalogInformationEjb.getTopCatalogItems(
                                               catalogId);
        prodDV.sort("Name");

        for (int ii = 0; ii < prodDV.size(); ii++)
        {

            CatalogItemView prodD = (CatalogItemView)prodDV.get(ii);
            int prodId = prodD.getItemId();
            CatalogNode node = new CatalogNode(prodD, 0, null, null);
            sForm.addToCatalogTree(node);
        }

        sForm.setSourceNodes(new String[0]);
    }

    /**
  * Expands or Collapses tree nodes
  * @param index of the node in tree vector
  *
  */
    public static void nodeExpCol(HttpServletRequest request, ActionForm form,
                                  int treeIndex)
                           throws Exception
    {

        HttpSession session = request.getSession();
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        Vector tree = (Vector)sForm.getCatalogTree();
        int currentIndex = treeIndex;
        CatalogNode node = (CatalogNode)tree.get(treeIndex);
        int level = node.getLevel();

        if (node.getHasSub())
        {

            if (node.getCollapsed())
            {

                // expand node
                CatalogCategoryDataVector ccDV = (CatalogCategoryDataVector)node.getSubCat();

                if (ccDV != null)
                {
                    ccDV.sort("CatalogCategoryShortDesc");

                    List subNodes = catalogInformationEjb.getCatalogNodes(
                                            catalogId, ccDV, level + 1);

                    for (int ii = 0; ii < subNodes.size(); ii++)
                    {

                        CatalogNode subNode = (CatalogNode)subNodes.get(ii);
                        tree.add(++currentIndex, subNode);
                    }
                }

                CatalogItemViewVector prodDV = (CatalogItemViewVector)node.getSubProd();
                prodDV.sort("Name");

                if (prodDV != null)
                {

                    for (int ii = 0; ii < prodDV.size(); ii++)
                    {

                        CatalogItemView prodD = (CatalogItemView)prodDV.get(ii);
                        CatalogNode treeNode = new CatalogNode(prodD,
                                                               level + 1, null,
                                                               null);
                        tree.add(++currentIndex, treeNode);
                    }
                }

                node.setCollapsed(false);
            }
            else
            {

                //Collapse node
                int startLevel = node.getLevel();

                for (int ii = treeIndex + 1; ii < tree.size();)
                {

                    CatalogNode curNode = (CatalogNode)tree.get(ii);
                    int curLevel = curNode.getLevel();

                    if (curLevel <= startLevel)
                    {

                        break;
                    }

                    tree.remove(ii);
                }

                node.setCollapsed(true);
            }

            sForm.setSourceNodes(new String[0]);
        }
    }

    //----------------------------------------------------------------------------
    static private CatalogItemViewVector orderItems(CatalogItemViewVector pItems)
    {

        Object[] itemArray = pItems.toArray();
        int size = itemArray.length;

        for (int ii = 0; ii < size - 1; ii++)
        {

            boolean exit = true;

            for (int jj = 1; jj < size - ii; jj++)
            {

                CatalogItemView item1 = (CatalogItemView)itemArray[jj - 1];
                String name1 = item1.getName();

                if (name1 == null)
                    name1 = "";

                CatalogItemView item2 = (CatalogItemView)itemArray[jj];
                String name2 = item2.getName();

                if (name2 == null)
                    name2 = "";

                if (name1.compareToIgnoreCase(name2) > 0)
                {
                    itemArray[jj - 1] = item2;
                    itemArray[jj] = item1;
                    exit = false;
                }

                if (exit)

                    break;
            }
        }

        pItems = new CatalogItemViewVector();

        for (int ii = 0; ii < size; ii++)
        {
            pItems.add(itemArray[ii]);
        }

        return pItems;
    }

    /**
  * Expands tree brunches, which have selected product
  */
    public static ActionErrors expandForProduct(HttpServletRequest request,
                                                ActionForm form)
                                         throws Exception
    {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        Vector tree = (Vector)sForm.getCatalogTree();
        ItemMgrSearchForm searchForm = (ItemMgrSearchForm)session.getAttribute(
                                               "ITEM_SEARCH_FORM");

        if (searchForm == null)
        {
            ae.add("error",
                   new ActionError("catalog.structure.nothing_to_expand"));

            return ae;
        }

        IdVector productIdV = searchForm.getSelectedProductIds();

        if (productIdV == null || productIdV.size() == 0)
        {
            ae.add("error",
                   new ActionError("catalog.structure.nothing_to_expand"));

            return ae;
        }

        IdVector ancestorV = catalogInfEjb.getAllAncestors(catalogId,
                                                           productIdV);
        Vector expand = new Vector();

        //Take a picture of the tree
        for (int ii = 0; ii < tree.size(); ii++)
        {

            CatalogNode curNode = (CatalogNode)tree.get(ii);

            if (curNode.getCollapsed() == false)
            {
                expand.add(new Integer(curNode.getId()));
            }
        }

        expand.addAll(ancestorV);
        refreshTree(catalogInfEjb, catalogId, tree, expand);
        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    public static ActionErrors deleteSubTree(HttpServletRequest request,
                                             ActionForm form)
                                      throws Exception
    {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        Vector tree = (Vector)sForm.getCatalogTree();
        String[] snodes = sForm.getSourceNodes();

        for (int idx = 0; idx < snodes.length; idx++)
        {

            String ss = snodes[idx];

            if (ss != null && ss.trim().length() > 0)
            {

                int choise = Integer.parseInt(ss);

                if (choise >= 0 && choise < tree.size())
                {

                    CatalogNode node = (CatalogNode)tree.get(choise);
                    IdVector idv = new IdVector();
                    idv.add(new Integer(node.getId()));
                    catalogEjb.removeCatalogSubTrees(catalogId, idv, user);
                }
                else
                {
                    ae.add("error",
                           new ActionError("catalog.structure.no_subtree"));
                }
            }
            else
            {
                ae.add("error",
                       new ActionError("catalog.structure.no_subtree"));
            }
        }

        refreshTree(catalogInfEjb, catalogId, tree);
        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    public static ActionErrors addCategory(HttpServletRequest request,
                                           ActionForm form)
                                    throws Exception
    {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInformationEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        String backBox = sForm.getBackBox();

        if (backBox == null || backBox.trim().length() == 0)
        {
            backBox = "-1";
        }

        int backBoxItemId = 0;
        int choise = Integer.parseInt(backBox);
        String categoryName = sForm.getWrkField();
        int majorCategoryId = sForm.getMajorCategoryId();
        if(majorCategoryId==0) {
           String errorMess = "No major category provided";
           ae.add("error",new ActionError("error.simpleGenericError",errorMess));
           return ae;
        }

        CatalogCategoryViewVector catalogCategoryVwV =
           catalogInformationEjb.getMismatchMajorCategories(categoryName, majorCategoryId);
        if(catalogCategoryVwV.size()>0) {
          CatalogCategoryView ccVw = (CatalogCategoryView) catalogCategoryVwV.get(0);
          String errorMess = "The category "+categoryName+" has another major category. "+
          "Catalog: "+ccVw.getCatalogName()+" ("+ ccVw.getCatalogId()+
             ") Major Category: "+ccVw.getMajorCategoryName();
           ae.add("error",new ActionError("error.simpleGenericError",errorMess));
           return ae;

        }


        if (categoryName != null && categoryName.trim().length() > 0)
        {

            CatalogCategoryData catalogCategoryD = new CatalogCategoryData();
            catalogCategoryD.setCatalogCategoryShortDesc(categoryName);
            ItemDataVector majorCatDV = sForm.getMajorCategories();
            for(int ii=0; ii<majorCatDV.size(); ii++) {
              ItemData iD = (ItemData) majorCatDV.get(ii);
              if(iD.getItemId()==majorCategoryId) {
                catalogCategoryD.setMajorCategory(iD);
              }
            }
            catalogCategoryD = catalogEjb.saveCatalogCategory(catalogId,
                                                              catalogCategoryD,
                                                              user);

            int newCategoryId = catalogCategoryD.getCatalogCategoryId();
            Vector tree = (Vector)sForm.getCatalogTree();

            if (choise != -1)
            {

                CatalogNode node = (CatalogNode)tree.get(choise);
                int itemId = node.getId();
                ItemAssocData itemAssocD = catalogEjb.addCatalogTreeNode(
                                                   catalogId, itemId,
                                                   newCategoryId, user);
                backBoxItemId = newCategoryId;
                node.setCollapsed(false);
            }

            refreshTree(catalogInformationEjb, catalogId, tree);

            //Set dest choise
            for (int ii = 0; ii < tree.size(); ii++)
            {

                CatalogNode node = (CatalogNode)tree.get(ii);

                if (node.getId() == backBoxItemId)
                {
                    sForm.setBackBox(new Integer(ii).toString());
                }
            }

            sForm.setWrkField("");
            sForm.setMajorCategoryId(0);
        }
        else
        {
            ae.add("error", new ActionError("catalog.structure.no_text"));
        }

        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    public static ActionErrors prepareToEdit(HttpServletRequest request,
                                             ActionForm form)
                                      throws Exception
    {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        Vector tree = (Vector)sForm.getCatalogTree();
        String[] snodes = sForm.getSourceNodes();

        for (int idx = 0; idx < snodes.length; idx++)
        {

            String ss = snodes[idx];

            if (ss != null && ss.trim().length() > 0)
            {

                int choise = Integer.parseInt(ss);

                if (choise >= 0)
                {

                    CatalogNode node = (CatalogNode)tree.get(choise);
                    String type = node.getType();

                    if (type.equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY))
                    {
                        sForm.setWrkField(node.getName());
                        ItemData iD = node.getMajorCategory();
                        if(iD!=null) {
                          sForm.setMajorCategoryId(iD.getItemId());
                        } else {
                          sForm.setMajorCategoryId(0);
                        }
                        sForm.setForwardParam(null);
                    }
                    else
                    {
                        sForm.setWrkField("");
                        sForm.setMajorCategoryId(0);
                        sForm.setForwardParam("itemId=" + node.getId());
                    }
                }
                else
                {
                    ae.add("error",
                           new ActionError("catalog.structure.no_node"));
                }
            }
            else
            {
                ae.add("error", new ActionError("catalog.structure.no_node"));
            }
        }

        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    /**
  * Replases name of selected category from wrk window
  */
    public static ActionErrors replaceCategory(HttpServletRequest request,
                                               ActionForm form)
                                        throws Exception
    {

        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        Vector tree = (Vector)sForm.getCatalogTree();
        String[] snodes = sForm.getSourceNodes();

        for (int idx = 0; idx < snodes.length; idx++)
        {

            String ss = snodes[idx];

            if (ss != null && ss.trim().length() > 0)
            {

                int choise = Integer.parseInt(ss);

                if (choise >= 0)
                {

                    CatalogNode node = (CatalogNode)tree.get(choise);

                    if (node.getType().equals(
                                RefCodeNames.ITEM_TYPE_CD.CATEGORY))
                    {

                        CatalogCategoryData catD = (CatalogCategoryData)node.getObject();
                        String ss1 = sForm.getWrkField();

                        if (ss1 != null && ss1.trim().length() > 0)
                        {
                            int majorCatId = sForm.getMajorCategoryId();
                            if(majorCatId<=0) {
                              String errorMess = "No major category provided";
                              ae.add("error",new ActionError("error.simpleGenericError",errorMess));
                              return ae;
                            }
                            ItemData mjcD = catD.getMajorCategory();
                            int mjcIdOld = 0;
                            if(mjcD!=null) {
                              mjcIdOld = mjcD.getItemId();
                            }
                            if(mjcIdOld!=majorCatId) {
                              ItemDataVector mjcDV = sForm.getMajorCategories();
                              for(int ii=0; ii<mjcDV.size(); ii++) {
                                ItemData mjcD1 = (ItemData) mjcDV.get(ii);
                                if(mjcD1.getItemId()==majorCatId) {
                                  catD.setMajorCategory(mjcD1);
                                }
                              }
                            }

                            catD.setCatalogCategoryShortDesc(ss1);
                            catD = catalogEjb.saveCatalogCategory(catalogId,
                                                                  catD, user);
                            node.setObject(catD);
                        }
                        else
                        {
                            ae.add("error",
                                   new ActionError("catalog.structure.no_text"));
                        }
                    }
                    else
                    {
                        ae.add("error",
                               new ActionError("catalog.structure.no_category"));
                    }
                }
                else
                {
                    ae.add("error",
                           new ActionError("catalog.structure.no_category"));
                }
            }
            else
            {
                ae.add("error",
                       new ActionError("catalog.structure.no_category"));
            }
        }

        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    /**
  * Attaches selected subtree to selected node
  */
    public static ActionErrors copySubTree(HttpServletRequest request,
                                           ActionForm form)
                                    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        Vector tree = (Vector)sForm.getCatalogTree();
        String[] snodes = sForm.getSourceNodes();

        for (int idx = 0; idx < snodes.length; idx++)
        {

            String ss = snodes[idx];

            if (ss != null && ss.trim().length() > 0)
            {

                int choiseSrc = Integer.parseInt(ss);

                if (choiseSrc >= 0)
                {

                    CatalogNode nodeSrc = (CatalogNode)tree.get(choiseSrc);
                    String ss1 = sForm.getBackBox();

                    if (ss1 != null && ss1.trim().length() > 0)
                    {

                        int choiseDest = Integer.parseInt(ss1);

                        if (choiseDest >= 0)
                        {

                            CatalogNode nodeDest = (CatalogNode)tree.get(
                                                           choiseDest);
                            ItemAssocData itemAssocD = catalogEjb.addCatalogTreeNode(
                                                               catalogId,
                                                               nodeDest.getId(),
                                                               nodeSrc.getId(),
                                                               user);
                            nodeDest.setCollapsed(false);
                        }
                        else
                        {
                            ae.add("error",
                                   new ActionError("catalog.structure.top"));
                        }
                    }
                    else
                    {
                        ae.add("error",
                               new ActionError("catalog.structure.no_super"));
                    }
                }
                else
                {

                    //Error Subtree is not selected
                    ae.add("error",
                           new ActionError("catalog.structure.no_subtree"));
                }
            }
            else
            {

                //Error Subtree is not selected
                ae.add("error",
                       new ActionError("catalog.structure.no_subtree"));
            }
        }

        refreshTree(catalogInfEjb, catalogId, tree);
        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    /**
  * Breaks relations between node and all ancestor nodes and ties to destination node
  */
    public static ActionErrors moveSubTree(HttpServletRequest request,
                                           ActionForm form)
                                    throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int catalogId = sForm.getCatalog().getCatalogId();
        Vector tree = (Vector)sForm.getCatalogTree();
        String[] snodes = sForm.getSourceNodes();

        for (int idx = 0; idx < snodes.length; idx++)
        {

            String ss = snodes[idx];

            if (ss != null && ss.trim().length() > 0)
            {

                int choiseSrc = Integer.parseInt(ss);

                if (choiseSrc >= 0)
                {

                    CatalogNode nodeSrc = (CatalogNode)tree.get(choiseSrc);
                    int srcId = nodeSrc.getId();
                    catalogEjb.untieCatalogSubTree(catalogId, srcId, user);

                    String ss1 = sForm.getBackBox();

                    if (ss1 != null && ss1.trim().length() > 0)
                    {

                        int choiseDest = Integer.parseInt(ss1);

                        if (choiseDest >= 0)
                        {

                            CatalogNode nodeDest = (CatalogNode)tree.get(
                                                           choiseDest);
                            ItemAssocData itemAssocD = catalogEjb.addCatalogTreeNode(
                                                               catalogId,
                                                               nodeDest.getId(),
                                                               srcId, user);
                            nodeDest.setCollapsed(false);
                        }
                    }
                }
                else
                {

                    //Error Subtree is not selected
                    ae.add("error",
                           new ActionError("catalog.structure.no_subtree"));
                }
            }
            else
            {

                //Error Subtree is not selected
                ae.add("error",
                       new ActionError("catalog.structure.no_subtree"));
            }
        }

        refreshTree(catalogInfEjb, catalogId, tree);
        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    /**
  * Ties new item to category if selected
  */
    public static ActionErrors tieNewItem(HttpServletRequest request,
                                          ActionForm form)
                                   throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        String user = (String)session.getAttribute(Constants.USER_NAME);
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        Vector tree = (Vector)sForm.getCatalogTree();
        int catalogId = sForm.getCatalog().getCatalogId();
        String backBox = sForm.getBackBox();

        if (backBox == null || backBox.trim().length() == 0)
        {
            refreshTree(catalogInfEjb, catalogId, tree);

            return ae;
        }

        int choiseDest = Integer.parseInt(backBox);

        if (choiseDest == -1)
        {
            refreshTree(catalogInfEjb, catalogId, tree);

            return ae;
        }

        CatalogNode node = (CatalogNode)tree.get(choiseDest);
        String nodeType = node.getType();

        if (nodeType.equals(RefCodeNames.ITEM_TYPE_CD.CATEGORY) == false)
        {
            refreshTree(catalogInfEjb, catalogId, tree);

            return ae;
        }

        //add to category
        try
        {

            String itemIdS = request.getParameter("itemId");
            int itemId = Integer.parseInt(itemIdS);
            ProductData productD = catalogInfEjb.getCatalogClwProduct(
                                           catalogId, itemId,0,0,
                                     SessionTool.getCategoryToCostCenterView(session, 0, catalogId ));
            catalogEjb.untieCatalogSubTree(catalogId, productD.getProductId(),
                                           user);

            String ss1 = sForm.getBackBox();
            ItemAssocData itemAssocD = catalogEjb.addCatalogTreeNode(catalogId,
                                                                     node.getId(),
                                                                     itemId,
                                                                     user);
            node.setCollapsed(false);
        }
        catch (DataNotFoundException exc)
        {
        }
        catch (NumberFormatException exc)
        {
        }

        refreshTree(catalogInfEjb, catalogId, tree);
        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    /**
  * Expands the whole tree
  */
    public static ActionErrors expandTree(HttpServletRequest request,
                                          CatalogMgrStructureForm pForm)
                                   throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute(Constants.USER_NAME);
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        int catalogId = pForm.getCatalog().getCatalogId();
        IdVector allProductsV = catalogInfEjb.searchCatalogProducts(catalogId,
                                                                    null);
        IdVector ancestorV = catalogInfEjb.getAllAncestors(catalogId,
                                                           allProductsV);
        Vector expand = new Vector();
        expand.addAll(ancestorV);

        Vector tree = (Vector)pForm.getCatalogTree();
        refreshTree(catalogInfEjb, catalogId, tree, expand);

        return ae;
    }

    /**
  * Refreshes the tree in case if some items were added or deleted on other pages
  */
    public static ActionErrors refresh(HttpServletRequest request,
                                       ActionForm form)
                                throws Exception
    {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)form;
        String user = (String)session.getAttribute(Constants.USER_NAME);
        APIAccess factory = (APIAccess)session.getAttribute(
                                    Constants.APIACCESS);

        if (factory == null)
        {
            throw new Exception("No APIAccess.");
        }

        Catalog catalogEjb = factory.getCatalogAPI();
        CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
        Vector tree = (Vector)sForm.getCatalogTree();
        int catalogId = sForm.getCatalog().getCatalogId();

        //refresh
        refreshTree(catalogInfEjb, catalogId, tree);
        sForm.setSourceNodes(new String[0]);

        return ae;
    }

    private static void refreshTree(CatalogInformation pCatalogInfEjb,
                                    int pCatalogId, Vector pTree)
                             throws RemoteException, DataNotFoundException
    {

        Vector expand = new Vector();

        //Take a picture of the tree
        for (int ii = 0; ii < pTree.size(); ii++)
        {

            CatalogNode curNode = (CatalogNode)pTree.get(ii);

            if (curNode.getCollapsed() == false)
            {
                expand.add(new Integer(curNode.getId()));
            }
        }

        refreshTree(pCatalogInfEjb, pCatalogId, pTree, expand);
    }

    private static void refreshTree(CatalogInformation pCatalogInfEjb,
                                    int pCatalogId, Vector pTree,
                                    Vector pExpand)
                             throws RemoteException, DataNotFoundException
    {

        //Clear the tree
        pTree.clear();

        //Recreate the tree from database
        //.Create top level nodes
        //..Categories
        CatalogCategoryDataVector catDV = pCatalogInfEjb.getTopCatalogCategories(
                                                  pCatalogId);
        catDV.sort("CatalogCategoryShortDesc");

        List nodes = pCatalogInfEjb.getCatalogNodes(pCatalogId, catDV, 0);

        for (int ii = 0; ii < nodes.size(); ii++)
        {

            CatalogNode node = (CatalogNode)nodes.get(ii);
            pTree.add(node);

            int jj = 0;

            for (; jj < pExpand.size(); jj++)
            {

                int idToExpand = ((Integer)pExpand.get(jj)).intValue();

                if (idToExpand == node.getId())

                    break;
            }

            if (jj < pExpand.size())
            {
                expandSubTree(pCatalogInfEjb, pCatalogId, pTree,
                              pTree.size() - 1, pExpand);
            }
        }

        //..Products
        CatalogItemViewVector prodDV = pCatalogInfEjb.getTopCatalogItems(
                                               pCatalogId);
        prodDV.sort("Name");

        for (int ii = 0; ii < prodDV.size(); ii++)
        {

            CatalogItemView prodD = (CatalogItemView)prodDV.get(ii);
            CatalogNode node = new CatalogNode(prodD, 0, null, null);
            pTree.add(node);
        }
    }

    private static void expandSubTree(CatalogInformation pCatalogInfEjb,
                                      int pCatalogId, Vector pTree, int pIndex,
                                      Vector pExpand)
                               throws RemoteException, DataNotFoundException
    {

        CatalogNode node = (CatalogNode)pTree.get(pTree.size() - 1);
        int itemId = node.getId();
        int ff = 0;

        for (; ff < pExpand.size(); ff++)
        {

            int id = ((Integer)pExpand.get(ff)).intValue();

            if (id == itemId)

                break;
        }

        if (ff == pExpand.size())
        {

            return;
        }

        int level = node.getLevel();
        int currentIndex = pIndex;
        CatalogCategoryDataVector ccDV = (CatalogCategoryDataVector)node.getSubCat();

        if (ccDV != null)
        {
            ccDV.sort("CatalogCategoryShortDesc");

            List subNodes = pCatalogInfEjb.getCatalogNodes(pCatalogId, ccDV,
                                                           level + 1);

            for (int ii = 0; ii < subNodes.size(); ii++)
            {

                CatalogNode subNode = (CatalogNode)subNodes.get(ii);
                pTree.add(subNode);
                expandSubTree(pCatalogInfEjb, pCatalogId, pTree, currentIndex,
                              pExpand);
            }
        }

        CatalogItemViewVector prodDV = (CatalogItemViewVector)node.getSubProd();

        if (prodDV != null)
        {
            prodDV.sort("Name");

            for (int ii = 0; ii < prodDV.size(); ii++)
            {

                CatalogItemView prodD = (CatalogItemView)prodDV.get(ii);
                CatalogNode treeNode = new CatalogNode(prodD, level + 1, null,
                                                       null);
                pTree.add(treeNode);

                //            expandSubTree(pCatalogInfEjb, pCatalogId, pTree, 0 /*currentIndex*/, pExpand);
            }
        }

        node.setCollapsed(false);
    }

    public static void clearSelectorBox(ActionForm pForm)
    {

        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)pForm;
        Vector catalogTree = (Vector)sForm.getCatalogTree();
        sForm.setSourceNodes(new String[0]);
    }

    public static void clearBackBox(ActionForm pForm)
    {

        CatalogMgrStructureForm sForm = (CatalogMgrStructureForm)pForm;
        Vector catalogTree = (Vector)sForm.getCatalogTree();
        sForm.setBackBox(null);
    }
}
