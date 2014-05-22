package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.i18n.ClwI18nUtil;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.log4j.Logger;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <code>SiteMgrLogic</code> implements the logic needed
 * for Multi-Site Buyer (MSB) type functionality.
 *
 * @author     durval
 * @created    August 15, 2001
 */
public class MsbLogic {

    private static final Logger log = Logger.getLogger(MsbLogic.class);

    private final static String className = "MsbLogic";
    private static final String EMPTY = "";
    private static final String SITE_SEARCH_FORM = "SITE_SEARCH_FORM";

    private static boolean isUserOnContract
    (HttpServletRequest request) {
    CleanwiseUser cwuser = (CleanwiseUser)
                           request.getSession().getAttribute(Constants.APP_USER);
    if (null == cwuser) {
      return false;
    }
    return cwuser.isUserOnContract();
  }

  /**
   *  Load the information for the order guide selected.
   *
   *@param  request        Description of Parameter
   *@param  pform          Description of Parameter
   *@return                The OrderGuideInfo value
   *@exception  Exception  Description of Exception
   */
  public static boolean getOrderGuideInfo
    (HttpServletRequest request,
     ActionForm pform) throws Exception {
    HttpSession session = request.getSession();
    OrderGuideDescDataVector ogv = (OrderGuideDescDataVector)
                                   session.getAttribute("msb.orderGuide.vector");
    if (null == ogv) {
      return false;
    }

    String is = (String) request.getParameter("idx");
    if (null == is) {
      return false;
    }

    int idx = Integer.parseInt(is);
    if (idx < 0 || idx > ogv.size()) {
      return false;
    }

    OrderGuideDescData ogd = (OrderGuideDescData) ogv.get(idx);
    session.setAttribute("msb.currentOrderGuide", ogd);

    try {
      APIAccess factory = new APIAccess();
      Site siteBean = factory.getSiteAPI();
      String s = (String)
                 session.getAttribute(Constants.USER_ID);
      int userId = Integer.parseInt(s);

      SiteDataVector sdv =
        siteBean.fetchSitesForOrderGuide(ogd.getOrderGuideId(),
                                         userId,
                                         SessionTool.getCategoryToCostCenterView(session, 0 , ogd.getCatalogId()));
      session.setAttribute("msb.orderGuideSites.vector", sdv);

      if (sdv.size() == 1) {
        // Only one site for this order guide.
        // No point in displaying a list of sites to shop for.
        request.getSession().setAttribute
          ("msb.selectedSites.vector", sdv);
      }

      shopInfo(request);

    } catch (Exception e) {
      return false;
    }
    return true;
  }

  private static boolean shopInfo
    (HttpServletRequest request) throws Exception {

    HttpSession session = request.getSession();
    OrderGuideDescData ogd = (OrderGuideDescData)
                             session.getAttribute("msb.currentOrderGuide");
    if (null == ogd) {
      return false;
    }

    CleanwiseUser appUser = (CleanwiseUser)
                            session.getAttribute(Constants.APP_USER);
    if (null == appUser) {
      return false;
    }
    StoreData store = appUser.getUserStore();
    if (null == store) {
      return false;
    }
    PropertyData storeType = store.getStoreType();
    if (null == storeType) {
      return false;
    }

    MsbShopOrderGuideForm form = (MsbShopOrderGuideForm)
                                 request.getSession().getAttribute("MSB_SHOP_OG_FORM");

    if (form == null) {
      form = new MsbShopOrderGuideForm();
    }

    int contractId = 0;
    contractId = LogOnLogic.fetchContractId(ogd.getCatalogId());

    APIAccess factory = new APIAccess();
    ShoppingServices shopBean = factory.getShoppingServicesAPI();
    ShoppingCartItemDataVector items =
      (ShoppingCartItemDataVector)
      request.getSession().getAttribute("msb.orderGuideItems");

      if (null == items) {
          items = shopBean.getOrderGuidesItems(storeType.getValue(),
                  appUser.getSite(),
                  ogd.getOrderGuideId(),
                  ShopTool.createShoppingItemRequest(request),
                  Constants.ORDER_BY_NAME,
                  SessionTool.getCategoryToCostCenterView(session, appUser.getSite().getSiteId()));
      }

    double lTotal = 0;
    for (int i = 0; i < items.size(); i++) {
      ShoppingCartItemData sd = (ShoppingCartItemData) items.get(i);
      lTotal += sd.getAmount();
    }
    request.getSession().setAttribute("msb.orderGuideItems", items);

    BigDecimal cartAmt = new BigDecimal(lTotal);
    cartAmt = cartAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    form.setItemAmtTotal(cartAmt);

    // freight
    /*
    BigDecimal freightAmt = form.getFreightAmt();
    try {
      freightAmt = shopBean.getFreightAmt
                   (contractId, cartAmt, weightTotal, weightUnit);
    } catch (RemoteException exc) {
      exc.printStackTrace();
      return false;
    }*/

    OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

    for (int ii = 0; ii < items.size(); ii++) {
      ShoppingCartItemData cartItem = (ShoppingCartItemData) items.get(ii);
      OrderHandlingItemView frItem = OrderHandlingItemView.createValue();
      frItem.setItemId(cartItem.getProduct().getProductId());
      BigDecimal priceBD = new BigDecimal(cartItem.getPrice());
      priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
      frItem.setPrice(priceBD);
      frItem.setQty(cartItem.getQuantity());
      frItems.add(frItem);
    }
    OrderHandlingView frOrder = OrderHandlingView.createValue();
    frOrder.setTotalHandling(new BigDecimal(0));
    frOrder.setTotalFreight(new BigDecimal(0));
    frOrder.setContractId(contractId);
    int accountId = appUser.getSite().getAccountBusEntity().getBusEntityId();
    frOrder.setAccountId(accountId);
    frOrder.setSiteId(appUser.getSite().getBusEntity().getBusEntityId());
    frOrder.setAmount(cartAmt);
    frOrder.setWeight(new BigDecimal(0));
    frOrder.setItems(frItems);


    BigDecimal freightAmt = form.getFreightAmt();
    try {
      freightAmt = shopBean.getFreightAmt(contractId,  cartAmt, frOrder) ;
    } catch (RemoteException exc) {
      exc.printStackTrace();
      return false;
    }

    freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
    form.setFreightAmt(freightAmt);
    BigDecimal otot = new BigDecimal
                      (cartAmt.doubleValue() + freightAmt.doubleValue());
    otot = otot.setScale(2, BigDecimal.ROUND_HALF_UP);
    form.setTotal(otot);

    request.getSession().setAttribute("MSB_SHOP_OG_FORM", form);
    return true;
  }

  /**
   * <code>sortSites</code>, sort the <b>msb.site.vector</b>
   * by the column selected.
   *
   *@param  request        a <code>HttpServletRequest</code> value
   *@param  form           an <code>ActionForm</code> value
   *@exception  Exception  if an error occurs
   */
  public static void sortSites(HttpServletRequest request,
                               ActionForm form) throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    SiteViewVector sites =
      (SiteViewVector) session.getAttribute("msb.site.vector");
    if (sites == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(sites, sortField);
  }

  public static void readNote(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String direction = request.getParameter("direction");
        int noteId = Utility.parseInt(request.getParameter("noteId"));
        Date curD = new Date();

        boolean hasPrev = false;
        boolean hasNext = false;

        APIAccess factory = APIAccess.getAPIAccess();
        Note noteBean = factory.getNoteAPI();
        NoteJoinView note = null;
        try {
            if (noteId > 0) {
                note = noteBean.getNote(noteId);
            }
        } catch (Throwable t) {
            note = null;
        }

        if (direction == null && note == null) {
            NoteJoinViewVector notes = (NoteJoinViewVector) request.getSession()
                    .getAttribute("user.notes");
            note = (notes == null && notes.isEmpty()) ? null : (NoteJoinView) notes.remove(0);
            request.getSession().setAttribute("user.notes", notes);
            if (note != null) {
                CleanwiseUser appUser = (CleanwiseUser) request.getSession()
                        .getAttribute(Constants.APP_USER);
                int userId = appUser.getUser().getUserId();
                noteBean.setReadToNote(note.getNote().getNoteId(), userId);
            }
            hasNext = (notes != null && notes.isEmpty() == false);
        } else if (noteId > 0) {
            CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
            int userId = appUser.getUser().getUserId();

            NoteJoinViewVector notes = noteBean.getNoteJoinViewForUser(userId, "INTERSTITIAL_MESSAGE", new DBCriteria());

            Iterator it = notes.iterator();
            while(it.hasNext()){
            	NoteJoinView n = (NoteJoinView)it.next();
        		Date expD =n.getNote().getExpDate();
        		if(curD.after(expD)){
        			it.remove();
        		}
            }

            final int size = notes.size();

            for (int i = 0; notes != null && i < size; i++) {
                NoteJoinView buffer = (NoteJoinView) notes.get(i);
                if (noteId == buffer.getNote().getNoteId()) {
                    if ("prev".equals(direction)) {
                        i--;
                    } else if ("next".equals(direction)) {
                        i++;
                    }
                    buffer = (i >= 0 && i < size) ? (NoteJoinView) notes.get(i) : null;
                    note = buffer;
                    hasPrev = ((i > 0) && (i < size));
                    hasNext = ((i >= 0) && ((i + 1) < size));
                    break;
                }
            }

        }
        if (note != null) {
            Element root = createXmlResponse(request, note, null);
            root.setAttribute("User", String.valueOf(note.getNote()
                            .getAddBy()));
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            root.setAttribute("Date", sdf.format(note.getNote().getEffDate()));
            if (hasPrev) {
                root.setAttribute("HasPrev", "true");
            }
            if (hasNext) {
                root.setAttribute("HasNext", "true");
            }
            response.setContentType("application/xml");
            response.setHeader("Cache-Control", "no-cache");
            OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
            XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
            serializer.serialize(root);
        }

    }

    public static void readNote(HttpServletRequest request, ActionForm form, HttpServletResponse response) throws Exception {

        String noteId = request.getParameter("userNoteId");
        String actionType = request.getParameter("actionType");


        CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);
        int userId = appUser.getUser().getUserId();

        APIAccess factory = APIAccess.getAPIAccess();
        Note noteBean = factory.getNoteAPI();
        NoteJoinViewVector notes = (NoteJoinViewVector) request.getSession().getAttribute("user.notes");
        NoteJoinView note = findAndRemoveNote(notes, Integer.parseInt(noteId));

        request.getSession().setAttribute("user.notes", notes);
        if (note != null) {
            noteBean.setReadToNote(note.getNote().getNoteId(), userId);
            if ("ajax".equals(actionType)) {
                NoteJoinView nextNote = getNextNote(notes);
                if (nextNote != null) {
                    Element rootEl = createXmlResponse(request, nextNote, notes);
                    response.setContentType("application/xml");
                    response.setHeader("Cache-Control", "no-cache");
                    //response.getWriter().write(rootEl.toString());
                    OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                    XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                    serializer.serialize(rootEl);
                }
            }
        }
    }

    private static Element createXmlResponse(HttpServletRequest request, NoteJoinView nextNote, NoteJoinViewVector notes) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document doc = docBuilder.getDOMImplementation().createDocument("", "Notes", null);

        Element root = doc.getDocumentElement();
        root.setAttribute("Id", String.valueOf(nextNote.getNote().getNoteId()));
        root.setAttribute("Title", String.valueOf(nextNote.getNote().getTitle()));

        Iterator it = nextNote.getNoteText().iterator();
        while(it.hasNext()){
            NoteTextData notetxt = (NoteTextData) it.next();
            Element node = doc.createElement("Text");
            node.appendChild(doc.createTextNode(String.valueOf(notetxt.getNoteText().replaceAll("'", "&#39;"))));
            root.appendChild(node);
        }

        Iterator it2 = nextNote.getNoteAttachment().iterator();
        while(it2.hasNext()){
            NoteAttachmentData noteImg = (NoteAttachmentData) it2.next();
            String path = ClwCustomizer.getTemplateImgRelativePath() + IOUtilities.convertToTempFile(noteImg.getBinaryData(), noteImg.getFileName());
            if(Utility.isSet(path)){
                Element node = doc.createElement("Image");
                node.appendChild(doc.createTextNode(path.replace((char) 92, '/')));
                root.appendChild(node);
            }
        }

        if(notes==null || notes.size()==0){
            Element node = doc.createElement("AjaxAction");
            node.appendChild(doc.createTextNode(String.valueOf("Close")));
            root.appendChild(node);
        }else if(notes.size()==1){
            Element node = doc.createElement("AjaxAction");
            node.appendChild(doc.createTextNode(String.valueOf("LastNote")));
            root.appendChild(node);
        } else{
            Element node = doc.createElement("AjaxAction");
            node.appendChild(doc.createTextNode(String.valueOf("Next")));
            root.appendChild(node);
        }

        return root;
    }

    private static NoteJoinView getNextNote(NoteJoinViewVector notes) {
        if(notes!=null && !notes.isEmpty()){
            return (NoteJoinView) notes.get(0);
        } else {
            return null;
        }
    }

    private static NoteJoinView findAndRemoveNote(NoteJoinViewVector notes, int noteId) {
        if(notes!=null){
            Iterator it=notes.iterator();
            for(int i=0;i<notes.size();i++){
                NoteJoinView noteJV = (NoteJoinView) notes.get(i);
                if(noteJV.getNote().getNoteId()==noteId){
                    return (NoteJoinView) notes.remove(i);
                }
            }
        }
        return null;
    }
  /**
   * <code>sortOrderGuideSites</code> , sort the
   * <b>msb.orderGuideSites.vector</b> by the column specified.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void sortOrderGuideSites(HttpServletRequest request,
                                         ActionForm form) throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    SiteDataVector sites =
      (SiteDataVector) session.getAttribute
      ("msb.orderGuideSites.vector");
    if (sites == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(sites, sortField);
  }


  /**
   * <code>sortOrderGuides</code> sort the
   * <b>msb.orderGuide.vector</b> by the column specified.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param form an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void sortOrderGuides(HttpServletRequest request,
                                     ActionForm form) throws Exception {

    // Get a reference to the admin facade
    HttpSession session = request.getSession();
    OrderGuideDescDataVector v = (OrderGuideDescDataVector)
                                 session.getAttribute("msb.orderGuide.vector");
    if (v == null) {
      return;
    }
    String sortField = request.getParameter("sortField");
    DisplayListSort.sort(v, sortField);
  }

  /**
   * <code>loadSites</code> , fetch the sites for the buyer.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param pform an <code>ActionForm</code> value
   * @exception Exception if an error occurs
   */
  public static void loadSites(HttpServletRequest request,
                               ActionForm pform) throws Exception {

    if (null == request.getSession().getAttribute("msb.site.vector")) {
      String s = (String)
                 request.getSession().getAttribute(Constants.USER_ID);
      int userId = Integer.parseInt(s);
      SiteViewVector sdv
        = RelatedLogic.findUserSites2(request, userId, null);
      request.getSession().
        setAttribute("msb.site.vector", sdv);
    }
  }

  public static void findSites(HttpServletRequest request,
                               ActionForm pform) throws Exception {

    CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute("ApplicationUser");
    String type = appUser.getUser().getUserTypeCd();
    int userId = appUser.getUser().getUserId();

    if (RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(type) ||
        RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(type)) {
      SiteMgrLogic.search(request, pform);
      HttpSession session = request.getSession();
      SiteViewVector sdv =
        (SiteViewVector) session.getAttribute("Site.found.vector");
      session.setAttribute("msb.site.vector", sdv);
      session.removeAttribute("Site.found.vector");
    } else {
      SiteViewVector sdv
        = RelatedLogic.findUserSites2(request, userId, pform);
      request.getSession().
        setAttribute("msb.site.vector", sdv);
    }
  }

  public static void findOnlySites(HttpServletRequest request,
          ActionForm pform) throws Exception {

	CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute("ApplicationUser");
	String type = appUser.getUser().getUserTypeCd();
	int userId = appUser.getUser().getUserId();

	if (RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(type) ||
			RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(type)) {
		SiteMgrLogic.search(request, pform);
		HttpSession session = request.getSession();
		SiteViewVector sdv =
			(SiteViewVector) session.getAttribute("Site.found.vector");
		session.setAttribute("msb.site.vector", sdv);
		session.removeAttribute("Site.found.vector");
	} else {
		//SiteViewVector sdv
		//= RelatedLogic.findUserSites2(request, userId, pform);
		APIAccess factory = new APIAccess();
        Site sbean = factory.getSiteAPI();
        SiteViewVector sitev = new SiteViewVector();
        QueryRequest req = new QueryRequest();
        req.filterByUserId(userId);
        req.filterBySiteStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        req.orderBySiteName(true);

        if (null != pform) {

            SiteMgrSearchForm sForm = (SiteMgrSearchForm)pform;
            String fieldSearchType = sForm.getSearchType();
            String fieldValue = sForm.getSearchField();
            fieldValue = fieldValue.trim();

            String f = "";
            /*req.filterByOnlySiteName(fieldValue,
                        QueryRequest.CONTAINS_IGNORE_CASE);
*/
            if (fieldSearchType.equals("nameBegins") && fieldValue.length() > 0) {
            	req.filterByOnlySiteName(fieldValue,QueryRequest.BEGINS_IGNORE_CASE);

            } else if (fieldSearchType.equals("nameContains") && fieldValue.length() > 0) {
            	req.filterByOnlySiteName(fieldValue,QueryRequest.CONTAINS_IGNORE_CASE);

            } else {
            	req.filterByOnlySiteName(fieldValue,QueryRequest.CONTAINS_IGNORE_CASE);

            }

            f = sForm.getCity().trim();

            if (sForm.getCity().trim().length() > 0) {
                req.filterByCity(f, QueryRequest.BEGINS_IGNORE_CASE);
            }

            f = sForm.getState().trim();

            if (sForm.getState().trim().length() > 0) {
                req.filterByState(f, QueryRequest.BEGINS_IGNORE_CASE);
            }

            f = sForm.getCountry().trim();

            if (sForm.getCountry().trim().length() > 0) {
            	if(f.equalsIgnoreCase("USA")){
            		f = "United States";
            	}
                req.filterByCountry(f, QueryRequest.BEGINS_IGNORE_CASE);
            }

            f = sForm.getPostalCode().trim();

            if (sForm.getPostalCode().trim().length() > 0) {
                req.filterByZip(f, QueryRequest.EXACT);
            }
        }

        sitev = sbean.getSiteCollection(req);

		request.getSession().setAttribute("msb.site.vector", sitev);
	}
}


  /**
   *  Make a purchase for a site.
   *
   *@param  request
   *@param  pform
   *@return
   *@exception  Exception
   */
  public static boolean siteShop(HttpServletRequest request,
                                 ActionForm pform) throws Exception {

    // Get the site id selected.
    String s = (String) request.getParameter("siteId");
    int selectedId = 0;
    if (null == s) {
      SiteData sd = ShopTool.getCurrentSite(request);
      if (sd == null) {
        return false;
      }
      selectedId = sd.getSiteId();
      if (selectedId <= 0) {
        return false;
      }
    } else {
      selectedId = Integer.parseInt(s);
    }

    //if user is crc, dump the cart every time they enter a site

    CleanwiseUser appUser = (CleanwiseUser) request.getSession().getAttribute("ApplicationUser");
    String type = appUser.getUser().getUserTypeCd();

    if (type.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
        type.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
      request.getSession().setAttribute(Constants.SHOPPING_CART, null);
    }
    HttpSession session = request.getSession();
    //Remove contact info (if was any)
    String contactName = (String) session.getAttribute("OrderContactName");
    if (Utility.isSet(contactName)) {
      session.removeAttribute("OrderContactName");
    }

    String contactPhone = (String) session.getAttribute("OrderContactPhone");
    if (Utility.isSet(contactPhone)) {
      session.removeAttribute("OrderContactPhone");
    }

    String contactEmail = (String) session.getAttribute("OrderContactEmail");
    if (Utility.isSet(contactEmail)) {
      session.removeAttribute("OrderContactEmail");
    }

    String orderMethod = (String) session.getAttribute("OrderMethod");
    if (Utility.isSet(orderMethod)) {
      session.removeAttribute("OrderMethod");
    }

    return LogOnLogic.siteShop(request, selectedId);
  }

  /**
   * <code>recalculateAmounts</code>, recalculate the order
   * guide amount since the quantity of items may have changed.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param pform an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   */
  public static ActionErrors recalculateAmounts
    (HttpServletRequest request, ActionForm pform) {
    ActionErrors ae = new ActionErrors();

    MsbShopOrderGuideForm form =
      (MsbShopOrderGuideForm) pform;

    ArrayList qtyarr = form.getItemQtyCollection();
    ShoppingCartItemDataVector items =
      (ShoppingCartItemDataVector)
      request.getSession().getAttribute("msb.orderGuideItems");

    if (null == items) {
      ae.add("error", new ActionError
             ("error.systemError", "No items present."));
      return ae;
    }

    for (int i = 0; i < items.size(); i++) {

      // Update with the quantities specified.
      // By setting the quantity, the amount per item
      // will reflect the new total price (quantity * price).

      ShoppingCartItemData sd = (ShoppingCartItemData) items.get(i);
      FormArrayElement newqty = (FormArrayElement) qtyarr.get(i);
      sd.setQuantity(Integer.parseInt(newqty.getValue()));
    }

    try {
      shopInfo(request);
    } catch (Exception e) {
      ae.add("error", new ActionError
             ("error.systemError", "Recalculate: " + e));
    }
    return ae;
  }


  /*
    Get the attributes that must be collected
    for this site.
   */
  public static void getOrderAttributes(HttpServletRequest request,
                                        SiteData pSiteData,
                                        MsbShopOrderGuideForm pForm) {

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)
                        session.getAttribute(Constants.APIACCESS);
    if (factory == null) {
      return;
    }
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (Exception exc) {
      return;
    }
    ShoppingServices shoppingServEjb = null;
    try {
      shoppingServEjb = factory.getShoppingServicesAPI();
    } catch (Exception exc) {
      return;
    }

    CostCenterDataVector costCenters = null;
    try {
      costCenters = accountEjb.getAllCostCenters
                    (pSiteData.getAccountBusEntity().getBusEntityId(),
                     accountEjb.ORDER_BY_NAME);
      session.setAttribute("msb.costcenters.vector", costCenters);
    } catch (Exception exc) {
      System.err.println
        ("MsbLogic.getOrderAttributes exception: " + exc);
    }

    return ;
  }

  public static ActionErrors placeOrder
    (HttpServletRequest request,
     ActionForm pform) throws Exception {

    ActionErrors ae = new ActionErrors();
    MsbShopOrderGuideForm form = (MsbShopOrderGuideForm) pform;
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess)
                        session.getAttribute(Constants.APIACCESS);
    if (factory == null) {
      ae.add("error", new ActionError
             ("error.systemError", "No Ejb access"));
      return ae;
    }

    SiteDataVector sdv = (SiteDataVector)
                         request.getSession().
                         getAttribute("msb.selectedSites.vector");

    if (sdv == null) {
      ae.add("error", new ActionError
             ("error.systemError", "No sites available."));
      return ae;
    }

    int siteId = form.getSiteId();

    for (int i = 0; i < sdv.size(); i++) {
      SiteData sd = (SiteData) sdv.get(i);
      if (siteId == sd.getBusEntity().getBusEntityId()) {

        form.setSite(sd);
        ae = callOrderCapture(request, form);
        if (ae.size() > 0) {
          return ae;
        }
        sdv.remove(i);
      }
    }
    return ae;

  }

  /**
   *  Shop for a MSB through an order guide.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param pform an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   * @exception Exception if an error occurs
   */
  public static ActionErrors setupOrder(HttpServletRequest request,
                                        ActionForm pform) throws Exception {

    ActionErrors ae = new ActionErrors();
    MsbShopOrderGuideForm form = (MsbShopOrderGuideForm) pform;
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)
                        session.getAttribute(Constants.APIACCESS);
    if (factory == null) {
      ae.add("error", new ActionError
             ("error.systemError", "No Ejb access"));
      return ae;
    }
    SiteDataVector sdv = (SiteDataVector)
                         request.getSession().
                         getAttribute("msb.selectedSites.vector");

    if (sdv == null) {
      ae.add("error", new ActionError
             ("error.systemError", "No sites available."));
      return ae;
    }

    String s = request.getParameter("siteId");
    if (null == s) {
      ae.add("error", new ActionError
             ("error.systemError", "No site id provided."));
      return ae;
    }
    Integer siteId = new Integer(s);

    for (int i = 0; i < sdv.size(); i++) {
      SiteData sd = (SiteData) sdv.get(i);
      if (siteId.intValue() == sd.getBusEntity().getBusEntityId()) {

        // Set the current site for which an order
        // is being placed.
        form.setSite(sd);
        getOrderAttributes(request, sd, form);
      }
    }

    return ae;
  }

  public static ActionErrors orderGuideShop
    (HttpServletRequest request,
     ActionForm pform) throws Exception {

    ActionErrors ae = new ActionErrors();
    try {
      MsbShopOrderGuideForm form = (MsbShopOrderGuideForm) pform;
      String ostr = form.getOperation();
      if (null == ostr) {
        ostr = "---";
      }
      if (ostr.equals("purchase")) {
        return MsbLogic.placeOrder(request, form);
      }
      return initOrderGuides(request, pform);
    } catch (Exception e) {
      ae.add("error", new ActionError
             ("error.systemError", e));
      return ae;
    }
  }

  public static ActionErrors initOrderGuides
    (HttpServletRequest request,
     ActionForm pform) throws Exception {

    ActionErrors ae = new ActionErrors();
    MsbShopOrderGuideForm form = (MsbShopOrderGuideForm) pform;
    String[] sites = form.getSitesSelected();
    if (sites == null) {
      return ae;
    }

    if (request.getParameter("recalc.x") != null) {
      // recalculate the prices for the order guide items.
      return recalculateAmounts(request, pform);
    }

    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)
                        session.getAttribute(Constants.APIACCESS);
    if (factory == null) {
      ae.add("error", new ActionError
             ("error.systemError", "No Ejb access"));
      return ae;
    }

    SiteDataVector sdv = (SiteDataVector)
                         request.getSession().
                         getAttribute("msb.orderGuideSites.vector");
    if (sdv == null) {
      ae.add("error", new ActionError
             ("error.systemError", "No sites available."));
      return ae;
    }

    SiteDataVector selectedSdv = new SiteDataVector();
    for (int i = 0; i < sites.length; i++) {

      int selectedId = Integer.parseInt(sites[i]);
      for (int i2 = 0; i2 < sdv.size(); i2++) {
        SiteData sd = (SiteData) sdv.get(i2);
        if (selectedId == sd.getBusEntity().getBusEntityId()) {
          selectedSdv.add(sd);
        }
      }
    }
    request.getSession().setAttribute
      ("msb.selectedSites.vector", selectedSdv);

    // Remove the sites for this order guide to force the
    // user to go back to the main order guides page.
    request.getSession().setAttribute
      ("msb.orderGuideSites.vector", new SiteDataVector());

    return ae;
  }


  /**
   * <code>loadOrderGuides</code>, fetch the order guides
   * for a buyer.  The order guides will be stored in the
   * <b>msb.orderGuide.vector</b> session variable.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param pform an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   */
  public static ActionErrors loadOrderGuides
    (HttpServletRequest request,
     ActionForm pform) {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    if (null == session.getAttribute("msb.orderGuide.vector")) {

      APIAccess factory = (APIAccess)
                          session.getAttribute(Constants.APIACCESS);
      if (factory == null) {
        ae.add("error", new ActionError
               ("error.systemError", "No Ejb access"));
        return ae;
      }
      String s = (String)
                 session.getAttribute(Constants.USER_ID);

      try {
        int userId = Integer.parseInt(s);
        OrderGuide ogBean = factory.getOrderGuideAPI();
        OrderGuideDescDataVector uogv =
          new OrderGuideDescDataVector();
        // Get the order guides for this user.
        uogv = ogBean.getCollectionByUser(userId,
                                          OrderGuide.TYPE_BUYER);
        OrderGuideDescDataVector togv =
          new OrderGuideDescDataVector();
        // Get the templates available to this user.
        togv = ogBean.getTemplateCollectionByUser(userId);
        // Put all the order guides found into one vector.
        for (int i = 0; i < uogv.size(); i++) {
          togv.add(uogv.get(i));
        }
        session.setAttribute("msb.orderGuide.vector", togv);

      } catch (Exception e) {
        ae.add("error", new
               ActionError("error.systemError",
                           e.getMessage()));
      }
    }

    return ae;
  }

  /**
   * <code>init</code>, clear out the session attributes:
   * <br><b>MSB_SHOP_OG_FORM</b>
   * <br><b>msb.selectedSites.vector</b>
   * set
   * <br><b>msb.expand.items</b> to String("true")
   *
   * <br> Load the order guides for the user.
   *
   * @param request a <code>HttpServletRequest</code> value
   * @param pform an <code>ActionForm</code> value
   * @return an <code>ActionErrors</code> value
   */
  public static ActionErrors init
    (HttpServletRequest request,
     ActionForm pform) throws Exception {

    ActionErrors ae = new ActionErrors();
    MsbShopOrderGuideForm sform = new MsbShopOrderGuideForm();
    request.getSession().setAttribute("MSB_SHOP_OG_FORM", sform);
    HttpSession session = request.getSession();
    CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

    APIAccess factory = (APIAccess)
                        request.getSession().getAttribute(Constants.APIACCESS);
    if (factory == null) {
      ae.add("error", new ActionError
             ("error.systemError", "No Ejb access"));
      return ae;
    }

    request.getSession().setAttribute(Constants.APIACCESS, factory);
    if (session.getAttribute("country.vector") == null) {
      Country countryBean = factory.getCountryAPI();
      CountryDataVector countriesv = countryBean.getAllCountries();
      session.setAttribute("country.vector", countriesv);
    }

      {

          AccountData account = appUser.getUserAccount();
          if (account == null) {
              ae.add("error", new ActionError("error.systemError", "No account information found"));
              return ae;
          }

          if (Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MODERN_SHOPPING), false)) {

              UserShopForm userShopForm = new UserShopForm();
              userShopForm.setShopMethod(Constants.SHOP_BY_CATEGORY);
              UserShopLogic.init(request, userShopForm);
              request.getSession().setAttribute("SHOP_FORM", userShopForm);

              OrderGuideDataVector allOGs = new OrderGuideDataVector();
              //Get catalog + site + user order guides

              OrderGuideDataVector templateOrderGuideDV = null;
              OrderGuideDataVector userOrderGuideDV = null;

              int accountId = appUser.getSite().getAccountId();
              int siteId = appUser.getSite().getBusEntity().getBusEntityId();

              Integer catalogIdI = (Integer) session.getAttribute(
                      Constants.CATALOG_ID);

              if (catalogIdI == null) {
                  ae.add("error",
                          new ActionError("error.systemError",
                          "No session " + Constants.CATALOG_ID +
                          " object found "));

                  return ae;
              }

              int catalogId = catalogIdI.intValue();

              int userId = appUser.getUserId();

              ShoppingServices sEjb = factory.getShoppingServicesAPI();

              templateOrderGuideDV = sEjb.getTemplateOrderGuides(catalogId, siteId);
              for(int i=0; i<templateOrderGuideDV.size(); i++){
            	  OrderGuideData tOG = (OrderGuideData)templateOrderGuideDV.get(i);
            	  allOGs.add(tOG);
              }

              userOrderGuideDV = sEjb.getUserOrderGuides(userId, catalogId, siteId);
              for(int i=0; i<userOrderGuideDV.size(); i++){
            	  OrderGuideData uOG = (OrderGuideData)userOrderGuideDV.get(i);
            	  allOGs.add(uOG);
              }

              OrderGuideDataVector customOrderGuideDV = sEjb.getCustomOrderGuides(accountId, siteId);
              for(int i=0; i<customOrderGuideDV.size(); i++){
            	  OrderGuideData uOG = (OrderGuideData)customOrderGuideDV.get(i);
            	  allOGs.add(uOG);
              }

              if(allOGs.size()>0 && !allOGs.isEmpty()){

            	  DisplayListSort.sort(allOGs, "short_desc");
            	  request.getSession().setAttribute(SessionAttributes.ORDER_GUIDE.ALL_ORDER_GUIDES, allOGs);
              }else{
            	  request.getSession().setAttribute(SessionAttributes.ORDER_GUIDE.ALL_ORDER_GUIDES, new OrderGuideDataVector());
              }

              MsbSiteForm msbSiteForm = new MsbSiteForm();
              init(request,msbSiteForm);

          }
      }

      return ae;
  }

    public static ActionErrors init(HttpServletRequest request, MsbSiteForm msbSiteForm) {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        msbSiteForm = new MsbSiteForm();

        MenuItemView trackOrderPopup  = createTrackOrderPopup(request);
        MenuItemView maintenancePopup = createMaintenancePopup(request);
        MenuItemView profilePopup = createProfilePopup(request);

        msbSiteForm.setTrackOrderPopup(trackOrderPopup);
        msbSiteForm.setMaintenancePopup(maintenancePopup);
        msbSiteForm.setProfilePopup(profilePopup);

        session.setAttribute("MSB_SITE_FORM", msbSiteForm);

        return ae;
    }

    private static MenuItemView createMaintenancePopup(HttpServletRequest request) {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        MenuItemView root = new MenuItemView();

        root.setKey(Constants.ROOT);
        root.setName(Constants.ROOT);
        root.setDisplayStatus(MenuItemView.DISPLAY_STATUS.OPEN);

        MenuItemViewVector rootSubItems = new MenuItemViewVector();

        if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MAINTENANCE_NEWS)) {
            MenuItemView item = new MenuItemView();
            item.setName(ClwI18nUtil.getMessage(request, "shop.menu.main.news", null));
            item.setLink("../userportal/maintenanceNews.do");
            rootSubItems.add(item);
        }

        if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MAINTENANCE_TEMPLATE)) {
            MenuItemView item = new MenuItemView();
            item.setName(ClwI18nUtil.getMessage(request, "shop.menu.main.template", null));
            item.setLink("../userportal/maintenanceTemplate.do");
            rootSubItems.add(item);
        }

        if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_PAR_VALUES)) {
            MenuItemView item = new MenuItemView();
            item.setName(ClwI18nUtil.getMessage(request, "shop.menu.main.editPars", null));
            item.setLink("../userportal/siteInventoryEdit.do?action=inventory_update");
            rootSubItems.add(item);
        }

        if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.MAINTENANCE_FAQ)) {
            MenuItemView item = new MenuItemView();
            item.setName(ClwI18nUtil.getMessage(request, "shop.menu.main.item.faq", null));
            item.setLink("../userportal/maintenanceFAQ.do?action=initFAQ");
            rootSubItems.add(item);
        }

        root.setSubItems(rootSubItems);

        return root;
    }

    private static MenuItemView createTrackOrderPopup(HttpServletRequest request) {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        MenuItemView root = new MenuItemView();
        root.setKey(Constants.ROOT);
        root.setName(Constants.ROOT);
        root.setDisplayStatus(MenuItemView.DISPLAY_STATUS.OPEN);


        String prefix = null;
        if (appUser.getUserStore().getPrefix() != null) {
            prefix = appUser.getUserStore().getPrefix().getValue();
        }

        MenuItemViewVector rootSubItems = new MenuItemViewVector();

        String[] orderStatusList = Constants.xpedxShoppingOrderStatusList;
        for (int i = 0; i < orderStatusList.length; i++) {
            MenuItemView item = new MenuItemView();
            if (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(orderStatusList[i])) {
                item.setName(com.cleanwise.view.utils.ShopTool.i18nStatus(prefix, orderStatusList[i], request));
                item.setLink("../store/orderstatus.do?action=initXPEDXListOrders&bkdte=true&orderStatus=" + orderStatusList[i]);
            } else {
                item.setName(com.cleanwise.view.utils.ShopTool.i18nStatus(prefix, orderStatusList[i], request));
                item.setLink("../store/orderstatus.do?action=initXPEDXListOrders&orderStatus=" + orderStatusList[i]);
            }
            rootSubItems.add(item);
        }

        MenuItemView item = new MenuItemView();
        item.setName(com.cleanwise.view.utils.ShopTool.i18nStatus(prefix, Constants.ORDER_STATUS_ALL, request));
        item.setLink("../store/orderstatus.do?action=initXPEDXListOrders&orderStatus=" + Constants.ORDER_STATUS_ALL);
        rootSubItems.add(item);

        root.setSubItems(rootSubItems);

        return root;

    }

    private static MenuItemView createProfilePopup(HttpServletRequest request) {

        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        MenuItemView root = new MenuItemView();

        root.setKey(Constants.ROOT);
        root.setName(Constants.ROOT);
        root.setDisplayStatus(MenuItemView.DISPLAY_STATUS.OPEN);

        MenuItemViewVector rootSubItems = new MenuItemViewVector();

        if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.USER_PROFILE_ACCESS)) {
            MenuItemView item = new MenuItemView();
            item.setName(ClwI18nUtil.getMessage(request, "shop.menu.main.userProfile", null));
            item.setLink("../userportal/customerAccountManagementProfileNewXpedx.do");
            rootSubItems.add(item);
        }
        if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.STORE_PROFILE_ACCESS)) {
            MenuItemView item = new MenuItemView();
            item.setName(ClwI18nUtil.getMessage(request, "shop.menu.main.storeProfile", null));
            item.setLink("../userportal/customerStoreManagementProfileNewXpedx.do");
            rootSubItems.add(item);
        }
        root.setSubItems(rootSubItems);

        return root;
    }

  private static ActionErrors callOrderCapture
    (HttpServletRequest request,
     MsbShopOrderGuideForm pForm) {

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess)
                        session.getAttribute(Constants.APIACCESS);
    if (factory == null) {
      ae.add("error",
             new ActionError("error.systemError",
                             "No Ejb access"));
      return ae;
    }
    Account accountEjb = null;
    try {
      accountEjb = factory.getAccountAPI();
    } catch (Exception exc) {
      ae.add("error",
             new ActionError("error.systemError", exc));
      return ae;
    }

    String costCenterName = "";
    int costCenterId = pForm.getCostCenterId();
    if (costCenterId > 0) {
      try {
        CostCenterData costCenter =
          accountEjb.getCostCenter(costCenterId, 0);
        costCenterName = costCenter.getShortDesc();
        pForm.setCostCenterName(costCenterName);
      } catch (Exception exc) {
        ae.add("error",
               new ActionError("error.systemError", exc));
        return ae;
      }
    } else {
      ae.add("error",
             new ActionError("error.systemError",
                             "No cost center specified"));
    }

    ShoppingCartItemDataVector cartItems =
      (ShoppingCartItemDataVector)
      request.getSession().getAttribute("msb.orderGuideItems");

    //Check user
    CleanwiseUser appUser = (CleanwiseUser)
                            session.getAttribute(Constants.APP_USER);
    int userId = appUser.getUser().getUserId();

    //Check site
    int siteId = pForm.getSiteId();

    //Get Account
    AccountData account = appUser.getUserAccount();
    if (account == null) {
      ae.add("error", new ActionError
             ("error.systemError",
              "No account information found"));
      return ae;
    }

    CustomerOrderRequestData orderReq =
      new CustomerOrderRequestData();
    orderReq.setCostCenterId(costCenterId);

    //Check PO number presence
    if (appUser.getPoNumRequired()) {
      String poNum = pForm.getPoNumber();
      if (poNum == null || poNum.trim().length() == 0) {
        ae.add("error",
               new ActionError("variable.empty.error", "P.O. Number"));
        return ae;
      }
    }

    orderReq.setUserId(userId);
    orderReq.setSiteId(siteId);
    orderReq.setAccountId
      (account.getBusEntity().getBusEntityId());
    orderReq.setCustomerPoNumber(pForm.getPoNumber());
    orderReq.setCustomerComments(pForm.getComments());
    orderReq.setCostCenterId(pForm.getCostCenterId());

    ShoppingServices shoppingServeicesEjb = null;
    try {
      shoppingServeicesEjb = factory.getShoppingServicesAPI();
    } catch (com.cleanwise.service.api.APIServiceAccessException exc) {
      ae.add("error", new ActionError
             ("error.systemError", "No shopping cervices API Access"));
      return ae;
    }

    OrderGuideDescData ogd = (OrderGuideDescData)
                             session.getAttribute("msb.currentOrderGuide");
    if (null == ogd) {
      ae.add("error",
             new ActionError
             ("error.systemError",
              "No OrderGuide information found"));
      return ae;
    }

    for (int ii = 0; ii < cartItems.size(); ii++) {
      ShoppingCartItemData cartItem = (ShoppingCartItemData)
                                      cartItems.get(ii);
      int itemId = cartItem.getProduct().getItemData().getItemId();
      int clw_skunum = cartItem.getProduct().getItemData().getSkuNum();
      int qty = cartItem.getQuantity();
      double price = cartItem.getPrice();
      orderReq.addItemEntry(ii + 1, itemId, clw_skunum, qty, price,
                            cartItem.getProduct().getUom(),
                            cartItem.getProduct().getShortDesc(),
                            cartItem.getProduct().getPack());
    }

    IntegrationServices isvcEjb = null;

    try {
      isvcEjb = factory.getIntegrationServicesAPI();
      ProcessOrderResultData orderRes;
      orderReq.setAccountId(account.getBusEntity().getBusEntityId());
      orderReq.setSiteId(siteId);

      orderRes = isvcEjb.processOrderRequest(orderReq);
      ArrayList oresv = (ArrayList) session.getAttribute
                        ("msb.order.response.vector");
      if (null == oresv) {
        oresv = new ArrayList();
      }
      oresv.add(orderRes);
      session.setAttribute("msb.order.response.vector", oresv);

    } catch (Exception exc) {
      ae.add("error", new ActionError("error.systemError", exc));
      exc.printStackTrace();
      return ae;
    }

    return ae;
  }

  public static ActionErrors addCustomerShipTo
    (HttpServletRequest request, ActionForm pform) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)
                        session.getAttribute(Constants.APIACCESS);
    Site siteb = factory.getSiteAPI();
    User userb = factory.getUserAPI();
    CatalogInformation catinfob = factory.getCatalogInformationAPI();
    Catalog catb = factory.getCatalogAPI();

    CleanwiseUser cwu = ShopTool.getCurrentUser(request);
    CustomerAddressForm addrf = (CustomerAddressForm) pform;
    AddressData ad = addrf.getAddress();
    ad.setName1(cwu.getUser().getFirstName());
    ad.setName2(cwu.getUser().getLastName());
    ad.setAddressStatusCd
      (RefCodeNames.ADDRESS_STATUS_CD.ACTIVE);
    ad.setAddressTypeCd
      (RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING);
    SiteData sd = SiteData.createValue();
    BusEntityData bed = sd.getBusEntity();
    bed.setAddBy(cwu.getUserName());
    bed.setModBy(cwu.getUserName());
    String t = ad.getAddress1() + " " + ad.getPostalCode();
    if (t.length() > 30) {
      t = t.substring(0, 30);
    }
    bed.setShortDesc(t);
    bed.setBusEntityStatusCd
      (RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
    bed.setWorkflowRoleCd
      (RefCodeNames.WORKFLOW_ROLE_CD.CUSTOMER);
    bed.setLocaleCd("en_US");
    sd.setSiteAddress(ad);
    try {
      CatalogDataVector catv = catinfob.getCatalogsCollectionByBusEntity
                               (cwu.getUserAccount().getBusEntity().getBusEntityId(),
                                RefCodeNames.CATALOG_TYPE_CD.GENERIC_SHOPPING);
      boolean readyToShop = false;
      if (catv == null || catv.size() == 0) {
        // Before users can define their own ship tos
        // the account must have a generic shopping catalog.
        String errorMess = "This account is not ready to allow new ship to locations."
                           + "  A generic shopping catalog is missing";
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
      sd = siteb.addSite(sd, cwu.getUserAccount().getBusEntity().getBusEntityId());
      // Pick the most recent active generic shopping catalog.
      for (int i = catv.size() - 1; i >= 0; i--) {
        CatalogData cd = (CatalogData) catv.get(i);
        if (cd.getCatalogStatusCd() != null &&
            cd.getCatalogStatusCd().equals
            (RefCodeNames.CATALOG_STATUS_CD.ACTIVE)) {
          catb.addCatalogAssoc(cd.getCatalogId(), sd.getSiteId(), cwu.getUserName());
          readyToShop = true;
          sd.setSiteCatalogId(cd.getCatalogId());
          break;
        }
      }
      if (readyToShop == false) {
        String errorMess = "This account is not ready to allow new ship to locations."
                           + "  A generic shopping catalog is missing (2)";
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
        return ae;
      }
      userb.addUserAssoc(cwu.getUser().getUserId(), sd.getSiteId(),
                         RefCodeNames.USER_ASSOC_CD.SITE);
      addrf.setAddress(sd.getSiteAddress());
      cwu.setSite(sd);
      cwu.setSiteNumber(cwu.getSiteNumber() + 1);
    } catch (com.cleanwise.service.api.util.DuplicateNameException dup) {
      String errorMess = "This ship to is already defined.";
      ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      return ae;
    }

    ae = SelectShippingAddressLogic.setShoppingSessionObjects(session, cwu);

    return ae;
  }

  public static ActionErrors updateCustomerShipTo
    (HttpServletRequest request, ActionForm pform) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)
                        session.getAttribute(Constants.APIACCESS);
    Site siteb = factory.getSiteAPI();

    CleanwiseUser cwu = ShopTool.getCurrentUser(request);
    CustomerAddressForm addrf = (CustomerAddressForm) pform;
    AddressData ad = addrf.getAddress();
    ad.setModBy(cwu.getUserName());

    SiteData sd = ShopTool.getCurrentSite(request);
    sd.setSiteAddress(ad);
    try {
      sd = siteb.updateSiteAddress(sd);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return ae;
  }

  public static ActionErrors removeCustomerShipTo
    (HttpServletRequest request, ActionForm pform) throws Exception {
    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();
    APIAccess factory = (APIAccess)
                        session.getAttribute(Constants.APIACCESS);
    Site siteb = factory.getSiteAPI();
    User userb = factory.getUserAPI();

    CleanwiseUser cwu = ShopTool.getCurrentUser(request);
    CustomerAddressForm addrf = (CustomerAddressForm) pform;
    AddressData ad = addrf.getAddress();

    SiteData sd = ShopTool.getCurrentSite(request);
    if (!sd.getSiteAddress().getAddressTypeCd().equals
        (RefCodeNames.ADDRESS_TYPE_CD.CUSTOMER_SHIPPING)
      ) {
      String errorMess = "This location cannot be removed.";
      ae.add("error",
             new ActionError("error.simpleGenericError", errorMess)
        );
      return ae;
    }
    try {
      userb.removeUserAssoc(cwu.getUser().getUserId(), sd.getSiteId());
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      siteb.removeSite(sd);
    } catch (Exception e) {
      e.printStackTrace();
    }

    cwu.setSite(SiteData.createValue());
    session.setAttribute("msb.site.vector",
                         new ArrayList());

    return ae;
  }

  public static ActionErrors prepareReorder(HttpServletRequest request) throws Exception {

    log.info("prepareReorder()=> BEGIN");

    ActionErrors ae = new ActionErrors();
    HttpSession session = request.getSession();

    APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);

    Contract contractEjb = factory.getContractAPI();
    CatalogInformation catalogInfEjb = factory.getCatalogInformationAPI();
    ShoppingServices shoppingServEjb = factory.getShoppingServicesAPI();

    String siteIdS = (String) request.getParameter("siteId");
    int siteId = Integer.parseInt(siteIdS);

    log.info("prepareReorder()=> Go To SiteID: "+siteId);

    session.setAttribute(Constants.SHOPPING_CART, null);

    boolean logOnResult = LogOnLogic.siteShop(request, siteId);
    if (!logOnResult) {
      String errorMess = "Logon Error";
      ae.add("error", new ActionError("error.systemError", errorMess));
      return ae;
    }

    CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    StoreData store = appUser.getUserStore();
    SiteData site = appUser.getSite();

    ShoppingCartForm scf = (ShoppingCartForm) session.getAttribute(Constants.SHOPPING_CART_FORM);

    ShoppingCartData shoppingCartD = scf.getShoppingCart();
    ProductBundle productBundle = null;

    CheckoutForm checkoutForm = (CheckoutForm) session.getAttribute("CHECKOUT_FORM");
    ShoppingCartItemDataVector lastOrderItems = checkoutForm.getItems();
    IdVector itemIdV = new IdVector();
    for (Iterator iter = lastOrderItems.iterator(); iter.hasNext(); ) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) iter.next();
      int itemId = sciD.getProduct().getProductId();
      itemIdV.add(new Integer(itemId));
    }
    int contractId = shoppingCartD.getContractId();
    int catalogId = shoppingCartD.getCatalogId();
    ProductDataVector pDV = catalogInfEjb.getCatalogClwProductCollection(catalogId, itemIdV, siteId, SessionTool.getCategoryToCostCenterView(session, siteId, catalogId));
    for (Iterator iter = lastOrderItems.iterator(); iter.hasNext(); ) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) iter.next();
      int itemId = sciD.getProduct().getProductId();
      boolean foundFl = false;
      for (Iterator iter1 = pDV.iterator(); iter1.hasNext(); ) {
        ProductData pD = (ProductData) iter1.next();
        if (itemId == pD.getProductId()) {
          foundFl = true;
          sciD.setProduct(pD);
        }
      }
      if (!foundFl) {
        String errorMess = "Sku " + sciD.getActualSkuNum() + " is not found in the catalog";
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      }
    }
    if (ae.size() > 0) {
      return ae;
    }

    ContractItemDataVector ciDV = contractEjb.getContractItemCollectionByItem(contractId, itemIdV);

    if (Utility.isSet(site.getProductBundle())) {
        String storeType = store.getStoreType() != null ? store.getStoreType().getValue() : null;
        productBundle = shoppingServEjb.getProductBundle(storeType, siteId,  lastOrderItems.getProducts());
    }

    for (Iterator iter = lastOrderItems.iterator(); iter.hasNext(); ) {
      ShoppingCartItemData sciD = (ShoppingCartItemData) iter.next();
      int itemId = sciD.getProduct().getProductId();
      boolean foundFl = false;
      for (Iterator iter1 = ciDV.iterator(); iter1.hasNext(); ) {
        ContractItemData ciD = (ContractItemData) iter1.next();
        if (itemId == ciD.getItemId()) {

          foundFl = true;

          BigDecimal distCostBD = null;
          BigDecimal priceBD = null;

          if (productBundle != null) {
              PriceValue price = productBundle.getPriceValue(itemId);
              log.info("prepareReorder()=> set price: "+price+" for item "+itemId);
              if (price != null) {
                  distCostBD = price.getValue();
                  priceBD = price.getValue();
              }
          } else {
              distCostBD = ciD.getDistCost();
              priceBD = ciD.getAmount();
          }

          if (distCostBD == null || priceBD == null) {
            String errorMess = "Sku " + sciD.getActualSkuNum() + " is not configured properly";
            ae.add("error", new ActionError("error.systemError", errorMess));
          }

          sciD.setDiscPrice(distCostBD.doubleValue());
          sciD.setPrice(priceBD.doubleValue());
        }
      }
      if (!foundFl) {
        String errorMess = "Sku " + sciD.getActualSkuNum() + " is not found in the contract";
        ae.add("error", new ActionError("error.simpleGenericError", errorMess));
      }

    }

    if (ae.size() > 0) {
      return ae;
    }

    shoppingCartD.setItems(lastOrderItems);

    ae = ShoppingCartLogic.saveShoppingCart(session);
    if (ae.size() > 0) {
      return ae;
    }

    appUser = (CleanwiseUser) request.getSession().getAttribute(Constants.APP_USER);

    UserData userD = appUser.getUser();
    String userTypeCd = userD.getUserTypeCd();
    if (RefCodeNames.USER_TYPE_CD.CRC_MANAGER.equals(userTypeCd) ||
        RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE.equals(userTypeCd)) {
      String contactName = checkoutForm.getOrderContactName();
      if (Utility.isSet(contactName)) {
        session.setAttribute("OrderContactName", contactName);
      }
      String contactPhone = checkoutForm.getOrderContactPhoneNum();
      if (Utility.isSet(contactPhone)) {
        session.setAttribute("OrderContactPhone", contactPhone);
      }
      String contactEmail = checkoutForm.getOrderContactEmail();
      if (Utility.isSet(contactEmail)) {
        session.setAttribute("OrderContactEmail", contactEmail);
      }
      String orderMethod = checkoutForm.getOrderOriginationMethod();
      if (Utility.isSet(orderMethod)) {
        session.setAttribute("OrderMethod", orderMethod);
      }
    }

    log.info("prepareReorder()=> END.");

    return ae;
  }


    public static ActionErrors homePageInit(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        AccountData account = appUser.getUserAccount();
        if (account == null) {
            ae.add("error", new ActionError("error.systemError", "No account information found"));
            return ae;
        }

        if (Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_MODERN_SHOPPING), false)) {
            UserShopForm userShopForm = (UserShopForm) session.getAttribute("SHOP_FORM");

            if (userShopForm == null) {
                userShopForm = new UserShopForm();
                userShopForm.setShopMethod(Constants.SHOP_BY_CATEGORY);
                UserShopLogic.init(request, userShopForm);
            } else {
                //reset variables
                MenuItemView menu = userShopForm.getCatalogMenu();
                UserShopLogic.refreshMenuStatus(menu, new ArrayList());

                userShopForm.setCatalogMenu(menu);
                userShopForm.setCategoryPath(new ArrayList());
                userShopForm.setShopNavigatorInfo(UserShopLogic.createShopNavigationInfo());
            }

            session.setAttribute("SHOP_FORM", userShopForm);

            MsbSiteForm msbSiteForm = (MsbSiteForm) session.getAttribute("MSB_SITE_FORM");
            ae = init(request, msbSiteForm);
            if (ae.size() > 0) {
                return ae;
            } else {
                session.setAttribute("MSB_SITE_FORM", msbSiteForm);
            }
        }
        return ae;

    }

    public static ActionErrors changeCountry(HttpServletRequest request, ActionForm form, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        SiteMgrSearchForm sForm = (SiteMgrSearchForm) form;

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Site siteEjb = factory.getSiteAPI();

        int userId    = appUser.getUser().getUserId();

        String newCountry = request.getParameter("newCountry");

        List stateValueList;
        if (Utility.isSet(newCountry)) {
            stateValueList = Arrays.asList(((HashSet) ((HashMap) sForm.getCountryAndStateLinks()[0]).get(newCountry)).toArray());
        } else {
            stateValueList = Arrays.asList(((HashMap) sForm.getCountryAndStateLinks()[1]).keySet().toArray());
        }
        Collections.sort(stateValueList);

        if (Utility.isSet(sForm.getState()) && !stateValueList.contains(sForm.getState())) {
            sForm.setState(EMPTY);
            sForm.setSiteId(EMPTY);
        }

        /*PairViewVector siteValPairs;
        if (!Utility.isSet(sForm.getState())) {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, newCountry, sForm.getState(), RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, Constants.MAX_DYNAMIC_LIST_SITES + 1);
            if (siteValPairs.size() > Constants.MAX_DYNAMIC_LIST_SITES) {
                siteValPairs.clear();
            }
        } else {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, newCountry, sForm.getState(), RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, 0);
        }*/

        PairViewVector siteValPairs;
        if (Utility.isSet(sForm.getState()) || stateValueList.size() == 0) {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, newCountry, sForm.getState(), RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, 0);
        } else {
            siteValPairs = new PairViewVector();
        }

        IdVector siteIds = getObject1AsId(siteValPairs);
        if (Utility.isSet(sForm.getSiteId()) && !siteIds.contains(new Integer(sForm.getSiteId()))) {
            sForm.setSiteId(EMPTY);
        }

        sForm.setCountry(newCountry);
        sForm.setStateValueList(stateValueList);
        sForm.setSiteValuePairs(siteValPairs);

        String responseStr = prepareLocationCriteriaResJson(sForm,false,true,true);

        response.setContentType("json-comment-filtered");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseStr);

        session.setAttribute(SITE_SEARCH_FORM, sForm);

        return ae;
    }

    public static ActionErrors changeState(HttpServletRequest request, ActionForm form, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        SiteMgrSearchForm sForm = (SiteMgrSearchForm) form;

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Site siteEjb = factory.getSiteAPI();

        int userId = appUser.getUser().getUserId();

        String newState = request.getParameter("newState");

        /*PairViewVector siteValPairs;
        if (!Utility.isSet(newState)) {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, sForm.getCountry(), newState, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, Constants.MAX_DYNAMIC_LIST_SITES + 1);
            if (siteValPairs.size() > Constants.MAX_DYNAMIC_LIST_SITES) {
                siteValPairs.clear();
            }
        } else {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, sForm.getCountry(), newState, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, 0);
        }*/

        PairViewVector siteValPairs;
        if (Utility.isSet(newState) || sForm.getStateValueList().size() == 0) {
            siteValPairs = siteEjb.getSiteIdAndName(userId, 0, sForm.getCountry(), newState, RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE, 0);
        } else {
            siteValPairs = new PairViewVector();
        }

        IdVector siteIds = getObject1AsId(siteValPairs);
        if (Utility.isSet(sForm.getSiteId()) && !siteIds.contains(new Integer(sForm.getSiteId()))) {
            sForm.setSiteId(EMPTY);
        }

        sForm.setState(newState);
        sForm.setSiteValuePairs(siteValPairs);

        String responseStr = prepareLocationCriteriaResJson(sForm,false,false,true);

        response.setContentType("json-comment-filtered");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseStr);

        session.setAttribute(SITE_SEARCH_FORM, sForm);

        return ae;
    }

    public static boolean changeSite(HttpServletRequest request, ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        SiteMgrSearchForm sForm = (SiteMgrSearchForm) form;

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return false;
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int siteId = appUser.getSite().getSiteId();

        if (Utility.isSet(sForm.getSiteId())) {
            int newSiteId = 0;
            try {
            	String emptyMessage = ClwI18nUtil.getMessage(request,"shop.orderStatus.label.selectaStore",null);
            	if (!sForm.getSiteId().equals(emptyMessage)) {
            		newSiteId = Integer.parseInt(sForm.getSiteId());
            	}
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }

            if (siteId != newSiteId) {
                if (!siteShop(request, sForm)) {
                    return false;
                } else {
                    sForm = new SiteMgrSearchForm();
                }

            }
        }

        session.setAttribute(SITE_SEARCH_FORM, sForm);
        return true;
    }

    private static String prepareLocationCriteriaResJson(SiteMgrSearchForm sForm,
                                                         boolean redrawCountries,
                                                         boolean redrawStates,
                                                         boolean redrawSites) {

        StringBuffer responseJson = new StringBuffer();

        responseJson.append("{\"response\":{");

        {
            appendSiteCriteria(responseJson, sForm.getSiteValuePairs(), sForm.getSiteId(), redrawSites);

            responseJson.append(",");
            appendStateCriteria(responseJson, sForm.getStateValueList(), sForm.getState(), redrawStates);

            responseJson.append(",");
            appendCountryCriteria(responseJson, sForm.getCountryValueList(), sForm.getCountry(), redrawCountries);
        }

        responseJson.append("}}");

        return responseJson.toString();

    }

    private static void appendSiteCriteria(StringBuffer responseJson, PairViewVector siteValuePairs, String currentValue, boolean redraw) {

        StringBuffer sb = new StringBuffer();

        sb.append("\"sites\":");

        sb.append("{\"redraw\":");
        sb.append(Boolean.toString(redraw));

        if (redraw && siteValuePairs != null && !siteValuePairs.isEmpty()) {

            sb.append(",\"selected\":");
            sb.append("\"");
            sb.append(Utility.strNN(currentValue));
            sb.append("\"");
            sb.append(",");

            sb.append("\"list\":[");
            int i = 0;
            for (Object siteValuePair : siteValuePairs) {
                PairView psiteVal = (PairView) siteValuePair;
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("{\"id\":");
                sb.append(psiteVal.getObject1().toString());
                sb.append(",\"val\":\"");
                sb.append(psiteVal.getObject2().toString());
                sb.append("\"}");
                i++;
            }
            sb.append("]");
        }

        sb.append("}");

        responseJson.append(sb);
    }


    private static void appendStateCriteria(StringBuffer responseJson, List stateValueList, String currentValue, boolean redraw) {

        StringBuffer sb = new StringBuffer();

        sb.append("\"states\":");

        sb.append("{\"redraw\":");
        sb.append(Boolean.toString(redraw));

        if (redraw && stateValueList != null && !stateValueList.isEmpty()) {

            sb.append(",\"selected\":");
            sb.append("\"");
            sb.append(Utility.strNN(currentValue));
            sb.append("\"");
            sb.append(",");

            sb.append("\"list\":[");
            int i = 0;
            for (Object oState : stateValueList) {
                String state = (String) oState;
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("{\"val\":");
                sb.append("\"");
                sb.append(state);
                sb.append("\"}");
                i++;
            }
            sb.append("]");

        }

        sb.append("}");

        responseJson.append(sb);
    }

    private static void appendCountryCriteria(StringBuffer responseJson,
                                              List countryValueList,
                                              String currentValue,
                                              boolean redraw) {

        StringBuffer sb = new StringBuffer();

        sb.append("\"countries\":");

        sb.append("{\"redraw\":");
        sb.append(Boolean.toString(redraw));

        if (redraw && countryValueList != null && !countryValueList.isEmpty()) {

            sb.append(",\"selected\":");
            sb.append("\"");
            sb.append(Utility.strNN(currentValue));
            sb.append("\"");
            sb.append(",");

            sb.append("\"list\":[");
            int i = 0;
            for (Object oCountry : countryValueList) {
                String country = (String) oCountry;
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("{\"val\":");
                sb.append("\"");
                sb.append(country);
                sb.append("\"}");
                i++;
            }
            sb.append("]");
        }

        sb.append("}");

        responseJson.append(sb);
    }

    /**
     * @deprecated  was replaced on prepareLocationCriteriaResJson  (29.10.2008)
     */
    private static Element prepareLocationCriteriaResXml(SiteMgrSearchForm sForm) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {


        }
        Document responseXml = docBuilder.getDOMImplementation().createDocument("", "LocationCriteria", null);

        Element root = responseXml.getDocumentElement();
        root.setAttribute("time", String.valueOf(System.currentTimeMillis()));

        appendSiteCriteria(responseXml, root, sForm.getSiteValuePairs(), sForm.getSiteId());
        appendCountryCriteria(responseXml, root, sForm.getCountryValueList(), sForm.getCountry());
        appendStateCriteria(responseXml, root, sForm.getStateValueList(), sForm.getState());

        return root;

    }

    /**
     * @deprecated should be used appendStateCriteria which uses JSON format (29.10.2008)
     */
    private static void appendStateCriteria(Document responseXml,
                                            Element root,
                                            List stateValueList,
                                            String currentValue) {

        if (stateValueList != null && !stateValueList.isEmpty()) {

            Element stateNode = responseXml.createElement("StateValueList");
            stateNode.setAttribute("selected", Utility.strNN(currentValue));

            Element node;
            Iterator it = stateValueList.iterator();
            while (it.hasNext()) {
                String stateVal = (String) it.next();

                node = responseXml.createElement("Value");
                node.setAttribute("Code", stateVal);

                node.appendChild(responseXml.createTextNode(stateVal));

                stateNode.appendChild(node);
            }
            root.appendChild(stateNode);
        }
    }

    /**
     * @deprecated should be used appendCountryCriteria which uses JSON format (29.10.2008)
     */
    private static void appendCountryCriteria(Document responseXml,
                                              Element root,
                                              List countryValueList,
                                              String currentValue) {

        if (countryValueList != null && !countryValueList.isEmpty()) {

            Element countryNode = responseXml.createElement("CountryValueList");
            countryNode.setAttribute("selected", Utility.strNN(currentValue));

            Element node;
            Iterator it = countryValueList.iterator();
            while (it.hasNext()) {
                String countryVal = (String) it.next();

                node = responseXml.createElement("Value");
                node.setAttribute("Code", countryVal);

                node.appendChild(responseXml.createTextNode(countryVal));

                countryNode.appendChild(node);
            }
            root.appendChild(countryNode);
        }
    }

    /**
     * @deprecated should be used appendSiteCriteria which uses JSON format (29.10.2008)
     */
    private static void appendSiteCriteria(Document responseXml,
                                           Element root,
                                           PairViewVector siteValuePairs,
                                           String currentValue) {

        if (siteValuePairs != null && !siteValuePairs.isEmpty()) {

            Element siteNode = responseXml.createElement("SiteValueList");
            siteNode.setAttribute("selected", Utility.strNN(currentValue));

            Element node;
            Iterator it = siteValuePairs.iterator();
            while (it.hasNext()) {
                PairView psiteVal = (PairView) it.next();

                node = responseXml.createElement("Value");
                node.setAttribute("Code", psiteVal.getObject1().toString());
                node.appendChild(responseXml.createTextNode(psiteVal.getObject2().toString()));

                siteNode.appendChild(node);
            }
            root.appendChild(siteNode);
        }
    }

    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm form) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        if (form == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }

        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }

        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No clw user info"));
            return ae;
        }

        if (appUser.getUser() == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }

        if (appUser.getUserStore() == null) {
            ae.add("error", new ActionError("error.systemError", "No store info"));
            return ae;
        }

        if (appUser.getUserAccount() == null) {
            ae.add("error", new ActionError("error.systemError", "No account info"));
            return ae;
        }

        if (appUser.getSite() == null) {
            ae.add("error", new ActionError("error.systemError", "No site info"));
            return ae;
        }

        return ae;
    }

    public static ActionErrors initLocationCriteria(HttpServletRequest request, ActionForm form) throws Exception {
        HttpSession session = request.getSession();
        SiteMgrSearchForm sForm = (SiteMgrSearchForm) form;

        sForm = new SiteMgrSearchForm();

        ActionErrors ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

        Country countryEjb = factory.getCountryAPI();
        //Site siteEjb = factory.getSiteAPI();

        int userId = appUser.getUserId();

        String emptyMsg = "Select a Location";
        PairViewVector siteValPairs = new PairViewVector();//siteEjb.getSiteIdAndName(userId,0,null,null,RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE,Constants.MAX_DYNAMIC_LIST_SITES+1);
        //siteValPairs.add(emptyMsg);

        Object[] countryAndStateLinks = countryEjb.getCountryAndValidStateLinks(userId);

        List countryValList = Arrays.asList(((HashMap)countryAndStateLinks[0]).keySet().toArray());
        Collections.sort(countryValList);

        List stateValList = Arrays.asList(((HashMap)countryAndStateLinks[1]).keySet().toArray());
        Collections.sort(stateValList);

        /* if (siteValPairs.size() > Constants.MAX_DYNAMIC_LIST_SITES) {
            siteValPairs.clear();
        }*/

        sForm.setCity(EMPTY);
        sForm.setState(EMPTY);
        sForm.setPostalCode(EMPTY);
        sForm.setCountry(EMPTY);
        //sForm.setSiteId(EMPTY);
        sForm.setSiteId("0");

        sForm.setStateValueList(stateValList);
        sForm.setSiteValuePairs(siteValPairs);
        sForm.setCountryValueList(countryValList);
        sForm.setCountryAndStateLinks(countryAndStateLinks);

        session.setAttribute(SITE_SEARCH_FORM, sForm);

        return ae;
    }

    private static IdVector getObject1AsId(PairViewVector sites) {
        IdVector ids = new IdVector();
        if (sites != null) {
            for (Object oSite : sites) {
                PairView site = (PairView) oSite;
                ids.add(site.getObject1());
            }
        }
        return ids;
    }


}


