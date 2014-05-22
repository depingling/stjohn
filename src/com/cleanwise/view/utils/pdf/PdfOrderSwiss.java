/*
 * PdfOrder.java
 *
 * Created on July 15, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;

import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.view.i18n.ClwI18nUtil;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.*;

import java.awt.Color; //used by the lowagie/iText api, we are not actually using the awt toolkit here
import java.util.Locale;
import java.util.Date;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.io.OutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;
import java.text.NumberFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 *Constructs a pdf document in the form of a pdf document.  It understands the various
 *propriatary objects, and uses them to construct a purchase order document.
 * @author  bstevens
 */
public class PdfOrderSwiss extends PdfOrder {

  //static configuration variables
  //configures the percentage widths of item columns in the document
  private int itmColumnWidth[];
  //private static int itmColumnWidthReSale[] = {4,6,27,7,5,4,18,5,8,10,6};
  private static int thirds[] = {25, 38, 37};
  private static int halves[] = {50, 50};
  private Locale mOrderLocale;
  private String mOrderLocaleCd;
  private HttpServletRequest mRequest = null;
  private ClwI18nUtil mFormatter = null;
  private boolean mShowSize = true;
  private boolean mShowMfg = true;
  private boolean mShowMfgSku = true;
  private int mColumnCount = 3;
  private boolean mShowPrice = true;


  /** Creates a new instance of PdfOrder */
  public PdfOrderSwiss() {
  }

  public PdfOrderSwiss(HttpServletRequest pRequest, String pOrderLocaleCd) {
    super();
    init(pRequest, pOrderLocaleCd);
  }

  public void init(HttpServletRequest pRequest, String pOrderLocaleCd) {
    if(pOrderLocaleCd == null){
    	mOrderLocaleCd = "en_US";
    }else{
        mOrderLocaleCd = pOrderLocaleCd;
    }
  	mOrderLocale = new Locale(pOrderLocaleCd);
    mRequest = pRequest;
    try {
        mFormatter = ClwI18nUtil.getInstance(pRequest, pOrderLocaleCd, -1);
    } catch (Exception exc) {}
    ;
    if (mFormatter == null) {
        try {
          mFormatter = ClwI18nUtil.getInstance(pRequest, "en_US", -1);
        } catch (Exception exc) {}
        ;
      }

      try {
        initFontsUnicode();
      } catch (Exception exc) {}

  }

    public static int SKU_COLUMN_WDTH_DEFAULT = 6;
    public static int MFG_SKU_COLUMN_WDTH_DEFAULT = 5;

    public int[] getItemColomnWidths() {
        return itmColumnWidth;
    }


    public int getSkuColumnLength(CleanwiseUser appUser, List items){
        return SKU_COLUMN_WDTH_DEFAULT;
    }

    public int getMfgSkuColumnLength(CleanwiseUser appUser, List items){
        return MFG_SKU_COLUMN_WDTH_DEFAULT;
    }



  private boolean isAuthorizedForResale(CleanwiseUser appUser) {
    if (appUser != null && appUser.getUserAccount() != null && appUser.getUserAccount().isAuthorizedForResale()) {
      return true;
    }
    return false;
  }

  //utility function to make an item Element.
  private Table makeItemElement
    (CleanwiseUser appUser, ShoppingCartItemData pItm) throws Exception {

    Table itmTbl = new PTable(mColumnCount);

    itmTbl.setWidth(100);
    itmTbl.setWidths(itmColumnWidth);
    itmTbl.getDefaultCell().setBorderColor(java.awt.Color.black);
    itmTbl.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

    itmTbl.addCell(makePhrase(Integer.toString(pItm.getQuantity()), normal, true));
    itmTbl.addCell(makePhrase(pItm.getActualSkuNum(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getProduct().getCatalogProductShortDesc(), normal, true));
    if (mShowSize) {
      itmTbl.addCell(makePhrase(pItm.getProduct().getSize(), normal, true));
    }
    //itmTbl.addCell(makePhrase(pItm.getProduct().getPack(), normal, true));
    //itmTbl.addCell(makePhrase(pItm.getProduct().getUom(), normal, true));
    /*if (mShowMfg) {
      itmTbl.addCell(makePhrase(pItm.getProduct().getManufacturerName(), normal, true));
    }
    if (mShowMfgSku) {
      itmTbl.addCell(makePhrase(pItm.getProduct().getManufacturerSku(), normal, true));
    } */

    if (mShowPrice) {
      BigDecimal price = new BigDecimal(pItm.getPrice());
      String priceS = mFormatter.priceFormatWithoutCurrency(price);
      itmTbl.addCell(makePhrase(priceS, normal, true));

      BigDecimal amount = new BigDecimal(pItm.getAmount());
      String amountS = mFormatter.priceFormatWithoutCurrency(amount);
      itmTbl.addCell(makePhrase(amountS, normal, true));
    }

    if (isAuthorizedForResale(appUser)) {
      if (pItm.getReSaleItem()) {
        String yStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.y", null);
        itmTbl.addCell(makePhrase(yStr, normal, true));
      } else {
        String nStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.n", null);
        itmTbl.addCell(makePhrase(nStr, normal, true));
      }
    }

    if (appUser.getUserAccount().isShowSPL()) {
      if (pItm.getProduct().getCatalogDistrMapping() != null && Utility.isTrue(pItm.getProduct().getCatalogDistrMapping().getStandardProductList())) {
        String yStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.y", null);
        itmTbl.addCell(makePhrase(yStr, normal, true));
      } else {
        String nStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.n", null);
        itmTbl.addCell(makePhrase(nStr, normal, true));
      }
    }

    return itmTbl;
  }


  private void drawHeader(CleanwiseUser appUser, Document document,
                          int pPageNumber, String pImageName) throws DocumentException {
    //add the logo
    try {
      Image i = Image.getInstance(pImageName);
      float x = document.leftMargin();
      float y = document.getPageSize().getHeight() - i.getHeight() - 5;
      i.setAbsolutePosition(x, y);
      document.add(i);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //deal with header info
    //construct the table/cells with all of the header information
    //must be the pdf specific type or the table will not use the full width of the page.
    //unfortuanately this means we are limited to PDF rendering only.

    //draw a line  XXX this is somewhat of a hack, as there seems to be no way to draw a line in the
    //current iText API that is relative to your current position in the document

    document.add(makeLine());

    //add the item header line
    PdfPTable itemHeader = new PdfPTable(mColumnCount);
    itemHeader.setWidthPercentage(100);
    itemHeader.setWidths(itmColumnWidth);
    itemHeader.getDefaultCell().setBorderWidth(2);
    itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
    itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
    itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
    itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
    String qtyStr =
      ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.qty", null);
    itemHeader.addCell(makePhrase(qtyStr, itemHeading, false));
    String ourSkuNumStr =
      ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.ourSku#", null);
    itemHeader.addCell(makePhrase(ourSkuNumStr, itemHeading, false));
    String productNameStr =
      ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.product<sp>Name", null);
    itemHeader.addCell(makePhrase(productNameStr, itemHeading, false));
    if (mShowSize) {
      String sizeStr =
        ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.size", null);
      itemHeader.addCell(makePhrase(sizeStr, itemHeading, false));
    }
    /*String packStr =
      ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.pack", null);
    itemHeader.addCell(makePhrase(packStr, itemHeading, false));
    String uomStr =
      ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.uom", null);
    itemHeader.addCell(makePhrase(uomStr, itemHeading, false));
    if (mShowMfg) {
      String mfgStr =
        ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.mfg", null);
      itemHeader.addCell(makePhrase(mfgStr, itemHeading, false));
    }
    if (mShowMfgSku) {
      String mfgSkuNumStr =
        ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.mfgSkuNum", null);
      itemHeader.addCell(makePhrase(mfgSkuNumStr, itemHeading, false));
    } */
    CurrencyData curr = ClwI18nUtil.getCurrency(mOrderLocaleCd);
    String currencyCode = "USD";
    if (curr != null) {
       currencyCode = curr.getGlobalCode();
    }
    if (mShowPrice) {
      String priceStr =
        ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.priceIn", new String[]{currencyCode});
      itemHeader.addCell(makePhrase(priceStr, itemHeading, false));
      String amountStr =
        ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.amountIn", new String[]{currencyCode});
      itemHeader.addCell(makePhrase(amountStr, itemHeading, false));
    }

    if (isAuthorizedForResale(appUser)) {
      String reSaleItemStr =
        ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.reSaleItem", null);
      itemHeader.addCell(makePhrase(reSaleItemStr, itemHeading, false));
    }
    if (appUser.getUserAccount().isShowSPL()) {
      String splStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.spl", null);
      itemHeader.addCell(makePhrase(splStr, itemHeading, false));
    }
    document.add(itemHeader);
  }

  public void drawOrderHeader
    (Document document, CleanwiseUser appUser, CheckoutForm sForm,
     String pImageName, OrderJoinData pOrder) throws DocumentException {

    //add the logo
    try {
      Image i = Image.getInstance(pImageName);
      float x = document.leftMargin();
      float y = document.getPageSize().getHeight() - i.getHeight() - 5;
      i.setAbsolutePosition(x, y);
      document.add(i);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //unfortunately this means we are limited to PDF rendering only.
    //get the user's personal info
    String userType = appUser.getUser().getUserTypeCd();

    PdfPTable header = new PdfPTable(1);
    header.setWidthPercentage(100);
    //header.setWidths(halves);
    header.getDefaultCell().setBorder(borderType);
    header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

    //Display the contact info
    PdfPTable orderInfo = new PdfPTable(2);
    orderInfo.setWidthPercentage(50);
    orderInfo.setWidths(halves);
    orderInfo.getDefaultCell().setBorder(borderType);
    orderInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

    ProcessOrderResultData pod = sForm.getOrderResult();
    if (pod.getNext() == null) {
      String orderNumberStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.orderNumber:", null);
      orderInfo.addCell(makePhrase(orderNumberStr + " ", normal, false));
    } else {
      String orderNumbersStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.orderNumbers:", null);
      orderInfo.addCell(makePhrase(orderNumbersStr + " ", normal, false));
    }
    StringBuffer orderNumbers = new StringBuffer(pod.getOrderNum());
    Date orderDate = pod.getOrderDate();
    while (pod.getNext() != null) {
      pod = pod.getNext();
      if (pod.getOrderNum() != null) { //should never be null
        orderNumbers.append(',');
        orderNumbers.append(pod.getOrderNum());
      }
    }
    orderInfo.addCell(makePhrase(orderNumbers.toString(), normal, true));
    String orderDateStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.orderDate:", null);
    orderInfo.addCell(makePhrase(orderDateStr + " ", normal, false));
    String orderDateS = ClwI18nUtil.formatDateInp(mRequest, orderDate);
    orderInfo.addCell(makePhrase(orderDateS, normal, true));
    SiteData siteD = appUser.getSite();
    AccountData accountD = appUser.getUserAccount();
    String v = accountD.getPropertyValue
               (RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY);
    /*
      Do not show a requested ship date field.
      durval, 10/20/2004.
          boolean showDeliveryFl = false;
          if(v!=null && v.length()>=1 && "T".equalsIgnoreCase(v.substring(0,1))){
             showDeliveryFl = true;
          }
          Date reqshipdate = null;
          if (siteD.getNextDeliveryDate() != null && showDeliveryFl) {
            reqshipdate =  siteD.getNextDeliveryDate();
          }
          if ( reqshipdate != null ) {
              orderInfo.addCell(makePhrase("Requested Ship Date: ",normal,false));
              String reqshipdateS = simpleDateFormat.format(reqshipdate);
              orderInfo.addCell(makePhrase(reqshipdateS,normal,true));
          }
     */

    String poNum = sForm.getPoNumber();
    String poNumberStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.poNumber:", null);
    orderInfo.addCell(makePhrase(poNumberStr + " ", normal, false));
    orderInfo.addCell(makePhrase(poNum, normal, true));

    if (Utility.isSet(sForm.getProcessOrderDate())) {
      String processOrderOnStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.processOrderOn:", null);
      orderInfo.addCell(makePhrase(processOrderOnStr + " ", normal, false));
      orderInfo.addCell(makePhrase(sForm.getProcessOrderDate(), normal, true));
    }

    if (userType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) ||
        userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)) {
      String contactName = sForm.getOrderContactName();
      String contactNameStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.contactName:", null);
      orderInfo.addCell(makePhrase(contactNameStr + " ", normal, false));
      orderInfo.addCell(makePhrase(contactName, normal, true));

      // STJ-4606 do not print phone and email for inventory orders
      if (!pOrder.getOrderData().getOrderSourceCd().equals(RefCodeNames.ORDER_SOURCE_CD.INVENTORY)) {
          String contactPhone = sForm.getOrderContactPhoneNum();
          String contactPhoneStr =
            ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.contactPhone:", null);
          orderInfo.addCell(makePhrase(contactPhoneStr + " ", normal, false));
          orderInfo.addCell(makePhrase(contactPhone, normal, true));

          String contactEmail = sForm.getOrderContactEmail();
          String contactEmailStr =
            ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.contactEmail:", null);
          orderInfo.addCell(makePhrase(contactEmailStr + " ", normal, false));
          orderInfo.addCell(makePhrase(contactEmail, normal, true));
      }
      String method = sForm.getOrderOriginationMethod();
      String methodStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.method:", null);
      orderInfo.addCell(makePhrase(methodStr + " ", normal, false));
      orderInfo.addCell(makePhrase(method, normal, true));

    }

    PropertyDataVector properties = sForm.getSite().getDataFieldPropertiesRuntime();
    if (properties.size() > 0) {
      for (int i = 0; i < properties.size(); i++) {
        PropertyData p = (PropertyData) properties.get(i);
        orderInfo.addCell(makePhrase(p.getShortDesc(), normal, false));
        orderInfo.addCell(makePhrase(p.getValue(), normal, true));

      }
    }
    /*
             String comments = sForm.getComments();
             if(comments!=null && comments.trim().length()>0) {
        orderInfo.addCell(makePhrase("Shipping Comments: ",normal,false));
        orderInfo.addCell(makePhrase(comments,normal,true));
             }
     */


    header.addCell(orderInfo);
    document.add(header);

    PdfPTable comments = new PdfPTable(1);
    comments.setWidthPercentage(100);
    //comments.setWidths(halves);
    comments.getDefaultCell().setBorder(borderType);
    comments.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    String shippingCommentsStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.shippingComments:", null);
    comments.addCell(makePhrase(shippingCommentsStr + " " +
                                sForm.getComments(), normal, false));
    document.add(comments);
  }


  private PdfPTable makeSummary(CheckoutForm sForm, CleanwiseUser appUser) throws Exception {
    //NumberFormat nf = NumberFormat.getCurrencyInstance(mOrderLocale);
    //one large table
    PdfPTable resTbl = new PdfPTable(1);
    resTbl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
    resTbl.setWidthPercentage(100);
    PdfPTable summTbl = new PdfPTable(3);
    summTbl.setWidthPercentage(100);
    summTbl.setWidths(thirds);
    //        summTbl.setBorder(borderType);

    //totals table
    PdfPTable totals = new PdfPTable(2);
    totals.setWidths(halves);
    totals.getDefaultCell().setBorder(borderType);
    totals.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_LEFT);

    String summaryStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.summary", null);
    totals.addCell(makePhrase(summaryStr + " ", smallHeading, false));
    totals.addCell(makePhrase("", smallHeading, false));

     BigDecimal cartAmt;

     if (sForm.getOrderServiceFlag()) {
         cartAmt = sForm.getCartServiceAmt();
     } else {
         cartAmt = sForm.getCartAmt();
     }

    String subtotalStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.subtotal:", null);
    totals.addCell(makePhrase(subtotalStr + " ", normal, false));
    String cartAmtS = mFormatter.priceFormat(cartAmt, " ");
    totals.addCell(makePhrase(cartAmtS, normal, false));

    String bigZ = "0.00";
    String z = "0";

    //ProcessOrderResultData pod = sForm.getOrderResult();
    boolean pendingConsolidationFl = false;
    CustomerOrderRequestData orderReq = sForm.getOrderRequest();
    if (RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderReq.getOrderType())) {
      pendingConsolidationFl = true;
    }
    //ProcessOrderResultData orderRes = sForm.getOrderResult();
    //if(orderRes != null){
    //  String sts = orderRes.getOrderStatusCd();
    //  if(sts.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) ) {
    //    pendingConsolidationFl = true;
    //  }
    //}

    BigDecimal freight = sForm.getFreightAmt();
    String f = freight.toString();

    boolean freightHandleFl = false;
    if (!f.equals(z) && !f.equals(bigZ)) {
      freightHandleFl = true;
      String freightStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.freight:", null);
      totals.addCell(makePhrase(freightStr + " ", normal, false));
      if (!pendingConsolidationFl) {
        String freightS = mFormatter.priceFormat(freight, " ");
        totals.addCell(makePhrase(freightS + " " + sForm.getEstimateFreightString(), normal, false));
      } else {
        totals.addCell(makePhrase("*", normal, false));
      }
    }

    BigDecimal handling = sForm.getHandlingAmt();
    String h = handling.toString();

    if (!h.equals(z) && !h.equals(bigZ)) {
      freightHandleFl = true;
      String handlingStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.handling:", null);
      totals.addCell(makePhrase(handlingStr + " ", normal, false));
      if (!pendingConsolidationFl) {
        String handlingS = mFormatter.priceFormat(handling, " ");
        totals.addCell(makePhrase(handlingS, normal, false));
      } else {
        totals.addCell(makePhrase("*", normal, false));
      }
    }

//    String rushAmt = sForm.getRushChargeAmtString();
//    BigDecimal rushCharge = new BigDecimal(0);
//
//    if (rushAmt != null && rushAmt.length() > 0) {
//      rushCharge = new BigDecimal(rushAmt);
//      String rushChargeS = mFormatter.priceFormat(rushCharge, " ");
//      String rushOrderChargeStr =
//        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.rushOrderCharge:", null);
//      totals.addCell(makePhrase(rushOrderChargeStr + " ", normal, false));
//      totals.addCell(makePhrase(rushChargeS, normal, false));
//    }

      BigDecimal smallOrderFeeAmt = sForm.getSmallOrderFeeAmt();
      if (smallOrderFeeAmt!=null && smallOrderFeeAmt.doubleValue()>0) {
          String smallOrderFeeStr = ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.smallOrderFee:", null);
          totals.addCell(makePhrase(smallOrderFeeStr + " ", normal, false));
          totals.addCell(makePhrase(mFormatter.priceFormat(smallOrderFeeAmt, " "), normal, false));

      }

      BigDecimal fuelSurchargeAmt = sForm.getFuelSurchargeAmt();
      if (fuelSurchargeAmt!=null && fuelSurchargeAmt.doubleValue()>0) {
          String fuelSurchargeStr = ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.fuelSurcharge:", null);
          totals.addCell(makePhrase(fuelSurchargeStr + " ", normal, false));
          totals.addCell(makePhrase(mFormatter.priceFormat(fuelSurchargeAmt, " "), normal, false));

      }

      BigDecimal discountBD = sForm.getDiscountAmt();
      if (discountBD != null && discountBD.abs().doubleValue() > 0) {
          if (discountBD.doubleValue() > 0) {
              discountBD.negate();
          }
          String discountStr = ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.discount:", null);
          totals.addCell(makePhrase(discountStr + " ", normal, false));
          totals.addCell(makePhrase(mFormatter.priceFormat(discountBD, SPACE), normal, false));
      }

    BigDecimal tax = sForm.getSalesTax();
    if (tax != null) {
      String taxStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.tax:", null);
      totals.addCell(makePhrase(taxStr + " ", normal, false));
      String taxS = mFormatter.priceFormat(tax, " ");
      totals.addCell(makePhrase(taxS, normal, false));
    }

    BigDecimal total = cartAmt.add(freight);
    total = total.add(handling);
//    total = total.add(rushCharge);
    total = total.add(tax);
    total = total.add(fuelSurchargeAmt);
    total = total.add(smallOrderFeeAmt);
    if (discountBD != null) {
        total = total.add(discountBD); // discount value already negative here
    }
    if (!freightHandleFl || !pendingConsolidationFl) {
      String totalStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.totalExcludingVOC:", null);
      totals.addCell(makePhrase(totalStr + " ", normal, false));
      String totalAmtS = mFormatter.priceFormat(total, " ");
      totals.addCell(makePhrase(totalAmtS + sForm.getEstimateFreightString(), normal, false));
    }

    PdfPTable acctInfo = new PdfPTable(2);
    acctInfo.setWidths(halves);
    acctInfo.getDefaultCell().setBorder(borderType);
    acctInfo.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_LEFT);

    //add any credit card info or if no cc number used, add throws account number
    String ccNumber = sForm.getCcNumberForDisplay();
    if (ccNumber != null && ccNumber.trim().length() > 0) {
      String ccInfoStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.creditCardInformation", null);
      acctInfo.addCell(makePhrase(ccInfoStr, smallHeading, false));
      acctInfo.addCell(makePhrase("", smallHeading, true));

      String ccType = sForm.getCcType();
      String ccTypeView = "";
      String ccTypeStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.cardType:", null);
      acctInfo.addCell(makePhrase(ccTypeStr, normal, false));
      if (ccType.equals(RefCodeNames.PAYMENT_TYPE_CD.VISA)) {
        ccTypeView = "Visa";
      } else if (ccType.equals(RefCodeNames.PAYMENT_TYPE_CD.MASTER_CARD)) {
        ccTypeView = "MasterCard";
      } else if (ccType.equals(RefCodeNames.PAYMENT_TYPE_CD.AMERICAN_EXPRESS)) {
        ccTypeView = "American Express";
      }
      acctInfo.addCell(makePhrase(ccTypeView, normal, true));

      String ccNumberStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.cardNumber:", null);
      acctInfo.addCell(makePhrase(ccNumberStr + " ", normal, false));
      acctInfo.addCell(makePhrase(ccNumber, normal, true));

      String expDateStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.expDate:", null);
      acctInfo.addCell(makePhrase(expDateStr + " ", normal, false));
      String ccExpMonthS = "";
      switch (sForm.getCcExpMonth()) {
      case GregorianCalendar.JANUARY:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.jan", null);
        break;
      case GregorianCalendar.FEBRUARY:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.feb", null);
        break;
      case GregorianCalendar.MARCH:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.mar", null);
        break;
      case GregorianCalendar.APRIL:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.apr", null);
        break;
      case GregorianCalendar.MAY:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.may", null);
        break;
      case GregorianCalendar.JUNE:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.jun", null);
        break;
      case GregorianCalendar.JULY:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.jul", null);
        break;
      case GregorianCalendar.AUGUST:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.aug", null);
        break;
      case GregorianCalendar.SEPTEMBER:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.sep", null);
        break;
      case GregorianCalendar.OCTOBER:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.oct", null);
        break;
      case GregorianCalendar.NOVEMBER:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.nov", null);
        break;
      case GregorianCalendar.DECEMBER:
        ccExpMonthS =
          ClwI18nUtil.getMessage(mRequest, "global.text.month.dec", null);
      }

      acctInfo.addCell(makePhrase(ccExpMonthS + SPACE + sForm.getCcExpYear(), normal, true));
      String nameStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.name:", null);
      acctInfo.addCell(makePhrase(nameStr + " ", normal, false));
      acctInfo.addCell(makePhrase(sForm.getCcPersonName(), normal, true));

      String street1Str =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.street1:", null);
      acctInfo.addCell(makePhrase(street1Str + " ", normal, false));
      acctInfo.addCell(makePhrase(sForm.getCcStreet1(), normal, true));

      String street2Str =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.street2:", null);
      acctInfo.addCell(makePhrase(street2Str + " ", normal, false));
      acctInfo.addCell(makePhrase(sForm.getCcStreet2(), normal, true));

      if (appUser.getUserStore().isStateProvinceRequired()) {
        String cityStateZipStr =
          ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.sityStateZip:", null);
        acctInfo.addCell(makePhrase(cityStateZipStr + " ", normal, false));
        acctInfo.addCell(makePhrase(sForm.getCcCity() + ", " +
                                    (Utility.isSet(sForm.getCcState()) ? sForm.getCcState() + SPACE : "") +
                                    sForm.getCcZipCode(), normal, true));
      } else {
        String cityStateZipStr =
          ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.sityZip:", null);
        acctInfo.addCell(makePhrase(cityStateZipStr + " ", normal, false));
        acctInfo.addCell(makePhrase(sForm.getCcCity() + ", " +
                                    sForm.getCcZipCode(), normal, true));
      }
    } else {
      String billingInformationStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.billingInformation", null);
      acctInfo.addCell(makePhrase(billingInformationStr, smallHeading, false));
      acctInfo.addCell(makePhrase("", smallHeading, true));

      String accountNameStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.accountName:", null);
      acctInfo.addCell(makePhrase(accountNameStr + " ", normal, false));
      acctInfo.addCell(makePhrase(sForm.getAccountShortDesc(), normal, true));

      String address1Str =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.address1:", null);
      acctInfo.addCell(makePhrase(address1Str + " ", normal, false));
      acctInfo.addCell
        (makePhrase
         (sForm.getConfirmBillToAddress().getAddress1(),
          normal, true));

      if (sForm.getConfirmBillToAddress().getAddress2() != null) {
        String address2Str =
          ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.address2:", null);
        acctInfo.addCell(makePhrase(address2Str + " ", normal, false));
        acctInfo.addCell
          (makePhrase
           (sForm.getConfirmBillToAddress().getAddress2(),
            normal, true));
      }

      if (sForm.getConfirmBillToAddress().getAddress3() != null) {
        String address3Str =
          ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.address3:", null);
        acctInfo.addCell(makePhrase(address3Str + " ", normal, false));
        acctInfo.addCell
          (makePhrase
           (sForm.getConfirmBillToAddress().getAddress3(),
            normal, true));
      }

      if (sForm.getConfirmBillToAddress().getAddress4() != null) {
        String address4Str =
          ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.address4:", null);
        acctInfo.addCell(makePhrase(address4Str + " ", normal, false));
        acctInfo.addCell
          (makePhrase
           (sForm.getConfirmBillToAddress().getAddress4(),
            normal, true));
      }

      if (appUser.getUserStore().isStateProvinceRequired()) {
        String cityStateZipStr =
          ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.sityStateZip:", null);
        acctInfo.addCell(makePhrase(cityStateZipStr + " ", normal, false));

        acctInfo.addCell
          (makePhrase
           (sForm.getConfirmBillToAddress().getCity() + ", " +
            (Utility.isSet(sForm.getConfirmBillToAddress().getStateProvinceCd()) ?
             sForm.getConfirmBillToAddress().getStateProvinceCd() + SPACE : "") +
            sForm.getConfirmBillToAddress().getPostalCode(),
            normal, true));
      } else {
        String cityStateZipStr =
          ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.sityZip:", null);
        acctInfo.addCell(makePhrase(cityStateZipStr + " ", normal, false));

        acctInfo.addCell
          (makePhrase
           (sForm.getConfirmBillToAddress().getCity() + ", " +
            sForm.getConfirmBillToAddress().getPostalCode(),
            normal, true));
      }
      String countryStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.country:", null);
      acctInfo.addCell(makePhrase(countryStr + " ", normal, false));
      acctInfo.addCell
        (makePhrase
         (sForm.getConfirmBillToAddress().getCountryCd(),
          normal, true));
    }

    PdfPTable siteInfo = new PdfPTable(2);
    siteInfo.setWidths(halves);
    siteInfo.getDefaultCell().setBorder(borderType);
    siteInfo.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_LEFT);

    String shippingInformationStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.shippingInformation", null);
    siteInfo.addCell(makePhrase(shippingInformationStr + " ", smallHeading, false));
    siteInfo.addCell(makePhrase("", smallHeading, true));

    String nameStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.name:", null);
    siteInfo.addCell(makePhrase(nameStr + " ", normal, false));
    siteInfo.addCell(makePhrase(Utility.strNN(sForm.getSiteName1()) + SPACE + Utility.strNN(sForm.getSiteName2()), normal, true));

    String siteNameStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.siteName:", null);
    siteInfo.addCell(makePhrase(siteNameStr + " ", normal, false));
    siteInfo.addCell(makePhrase(sForm.getSiteShortDesc(), normal, true));

    String address1Str =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.address1:", null);
    siteInfo.addCell(makePhrase(address1Str + " ", normal, false));
    siteInfo.addCell(makePhrase(sForm.getSiteAddress1(), normal, true));

    if (sForm.getSiteAddress2() != null) {
      String address2Str =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.address2:", null);
      siteInfo.addCell(makePhrase(address2Str + " ", normal, false));
      siteInfo.addCell(makePhrase(sForm.getSiteAddress2(), normal, true));
    }

    if (sForm.getSiteAddress3() != null) {
      String address3Str =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.address3:", null);
      siteInfo.addCell(makePhrase(address3Str + " ", normal, false));
      siteInfo.addCell(makePhrase(sForm.getSiteAddress3(), normal, true));
    }

    if (sForm.getSiteAddress4() != null) {
      String address4Str =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.address4:", null);
      siteInfo.addCell(makePhrase(address4Str + " ", normal, false));
      siteInfo.addCell(makePhrase(sForm.getSiteAddress4(), normal, true));
    }

    if (appUser.getUserStore().isStateProvinceRequired()) {
      String cityStateZipStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.sityStateZip:", null);
      siteInfo.addCell(makePhrase(cityStateZipStr + " ", normal, false));
    } else {
      String cityStateZipStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.sityZip:", null);
      siteInfo.addCell(makePhrase(cityStateZipStr + " ", normal, false));
    }

    siteInfo.addCell(makePhrase(sForm.getSiteCity() + ", " +
                  ((appUser.getUserStore().isStateProvinceRequired() &&
                   Utility.isSet(sForm.getSiteStateProvinceCd())) ?
                   sForm.getSiteStateProvinceCd() + SPACE : "") +
                                sForm.getSitePostalCode(), normal, true));

    String countryStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.country:", null);
    siteInfo.addCell(makePhrase(countryStr + " ", normal, false));
    siteInfo.addCell(makePhrase(sForm.getSiteCountryCd(), normal, true));

    summTbl.addCell(totals);
    summTbl.addCell(acctInfo);
    summTbl.addCell(siteInfo);
    resTbl.addCell(summTbl);
    if (freightHandleFl && pendingConsolidationFl) {
      String freightMessStr =
        ClwI18nUtil.getMessage(mRequest,
                               "shop.orderStatus.text.actualHandlingFreightWillBeCalculatedAtTimeOfConsolidatedOrder", null);
      String comm = "* " + freightMessStr;
      resTbl.addCell(makePhrase(comm, normal, false));
    }
    return resTbl;
  }

    public void sortItems(List items) {
        // no sorting by default
    }


  //main worker method.  The public methods will call this method
  //to generate the pdf after the object has been converted.
  public void generatePdf
    (CheckoutForm sForm, CleanwiseUser appUser, StoreData pStore,
     OutputStream pOut, String pImageName, OrderJoinData order, List pItems) throws Exception {
    initColumnsAndWidth(appUser, pItems);
    try {
      //we keep track of the page number rather than useing the built in pdf functionality
      //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
      //first page of that po, and on the 9th page of the pdf.
      int pageNumber = 1;
      //create the header and footer

      Phrase headPhrase = new Phrase(makeChunk("", heading, true));
      String orderConfirmationStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.orderConfirmation", null);
      headPhrase.add(makeChunk(orderConfirmationStr, heading, true));
      headPhrase.add(makeChunk(SPACE, heading, true));
      headPhrase.add(makeChunk(appUser.getUserAccount().getBusEntity().getShortDesc(), heading, true));

      HeaderFooter header = new HeaderFooter(headPhrase, true);
      header.setAlignment(HeaderFooter.ALIGN_RIGHT);

      //setup the document
      Document document = new Document(PageSize.A4, 10, 15, 30, 15);
      PdfWriter writer = PdfWriter.getInstance(document, pOut);

      Phrase footPhrase = makeStoreFooter(pStore);

      HeaderFooter footer = new HeaderFooter(footPhrase, false);
      footer.setAlignment(HeaderFooter.ALIGN_CENTER);

      //setup the borders from the header
      header.setBorder(borderType);
      footer.setBorder(HeaderFooter.TOP);

      document.setHeader(header);
      document.setFooter(footer);
      document.open();

      // voc message
      String vocMessage = ClwI18nUtil.getMessage(mRequest, "shop.message.vocNotIncluded", null);
      document.add(makePhrase(vocMessage, smallHeading, true));
      document.add(makePhrase(null, smallHeading, true));

      drawOrderHeader(document, appUser, sForm, pImageName, order);
      drawHeader(appUser, document, pageNumber, pImageName);
      sortItems(pItems);
      boolean firstCat = true;
      for (int i = 0; i < pItems.size(); i++) {
        if (isShowCategories() && firstCat == true) {
          document.add(makeBlankLine());
        }

        ShoppingCartItemData sci = (ShoppingCartItemData) pItems.get(i);
        if (isShowCategories()) {
            if (sForm.isCategoryChanged(i) || firstCat == true) {
                document.add(makePhrase(sci.getCategoryPath(),
                                  smallHeading, true));
              firstCat = false;
            }
        }
        Table itmTable = makeItemElement(appUser, sci);

        //if the item data will not fit onto the page,
        //make a new page, and redraw
        //the header.

        if (writer.fitsPage(itmTable, document.bottomMargin())) {
          document.add(itmTable);
        } else {
          document.newPage();
          pageNumber = pageNumber + 1;
          drawHeader(appUser, document, pageNumber, pImageName);
          if (isShowCategories()) {
            document.add(makeBlankLine());
            document.add(makePhrase(sci.getCategoryPath(), smallHeading, true));
          }
          document.add(itmTable);
        }
      }
      document.add(makeBlankLine());
      document.add(makeBlankLine());
      PdfPTable summaryTable = makeSummary(sForm, appUser);
      document.add(summaryTable);

      //If Consolidated Order
      ReplacedOrderViewVector replacedOrders = sForm.getReplacedOrders();
      boolean consolidatedOrderFl =
        (replacedOrders != null && replacedOrders.size() > 0) ? true : false;
      if (consolidatedOrderFl) {
        document.add(makeBlankLine());
        document.add(makeBlankLine());
        //Replaced Orders Label
        Table replacedOrdersLabel = makeReplacedOrdersLabel();
        boolean skipFl = false;
        if (!writer.fitsPage(replacedOrdersLabel, document.bottomMargin())) {
          document.newPage();
          pageNumber = pageNumber + 1;
          drawReplacedOdersHeader(appUser, document, pageNumber, pImageName);
          skipFl = true;
          document.add(makeBlankLine());
        }
        document.add(replacedOrdersLabel);
        //Replaced Orders Header
        if (!skipFl) {
          Table replacedOrdersHeader = makeReplacedOrdersHeader();
          if (!writer.fitsPage(replacedOrdersHeader, document.bottomMargin())) {
            document.newPage();
            pageNumber = pageNumber + 1;
            drawReplacedOdersHeader(appUser, document, pageNumber, pImageName);
            document.add(makeBlankLine());
          }
          document.add(replacedOrdersHeader);
        }

        //Replaced Orders Elements
        for (Iterator iter = replacedOrders.iterator(); iter.hasNext(); ) {
          ReplacedOrderView roVw = (ReplacedOrderView) iter.next();
          ReplacedOrderItemViewVector roiVwV = roVw.getItems();
          boolean nextOrder = true;
          for (Iterator iter1 = roiVwV.iterator(); iter1.hasNext(); ) {
            ReplacedOrderItemView roiVw =
              (ReplacedOrderItemView) iter1.next();
            Table replacedOrdersElement =
              makeReplacedOrdersElement(roVw, roiVw, nextOrder);
            nextOrder = false;
            if (!writer.fitsPage(replacedOrdersElement, document.bottomMargin())) {
              document.newPage();
              pageNumber = pageNumber + 1;
              drawReplacedOdersHeader(appUser, document, pageNumber, pImageName);
              document.add(makeBlankLine());
            }
            document.add(replacedOrdersElement);

          }
        }
      }

      //close out the document
      document.close();
    } catch (DocumentException e) {
      e.printStackTrace();
      throw new IOException(e.getMessage());
    }
  }


    //main worker method.  The public methods will call this method
     //to generate the pdf after the object has been converted.
     public void generatePdfforService
       (CheckoutForm sForm, CleanwiseUser appUser, StoreData pStore,
        OutputStream pOut, String pImageName, OrderJoinData order, List pItems) throws Exception {
       initColumnsAndWidth(appUser, pItems);
       try {
         //we keep track of the page number rather than useing the built in pdf functionality
         //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
         //first page of that po, and on the 9th page of the pdf.
         int pageNumber = 1;
         //create the header and footer

         Phrase headPhrase = new Phrase(makeChunk("", heading, true));
         String orderConfirmationStr =
           ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.orderConfirmation", null);
         headPhrase.add(makeChunk(orderConfirmationStr, heading, true));
         headPhrase.add(makeChunk(SPACE, heading, true));
         headPhrase.add(makeChunk(appUser.getUserAccount().getBusEntity().getShortDesc(), heading, true));

         HeaderFooter header = new HeaderFooter(headPhrase, true);
         header.setAlignment(HeaderFooter.ALIGN_RIGHT);

         //setup the document
         Document document = new Document(PageSize.A4, 10, 15, 30, 15);
         PdfWriter writer = PdfWriter.getInstance(document, pOut);

         Phrase footPhrase = makeStoreFooter(pStore);

         HeaderFooter footer = new HeaderFooter(footPhrase, false);
         footer.setAlignment(HeaderFooter.ALIGN_CENTER);

         //setup the borders from the header
         header.setBorder(borderType);
         footer.setBorder(HeaderFooter.TOP);

         document.setHeader(header);
         document.setFooter(footer);
         document.open();

         drawOrderHeader(document, appUser, sForm, pImageName, order);

         drawHeaderforService(appUser, document, pageNumber, pImageName);

         boolean firstCat = true;
         for (int i = 0; i < pItems.size(); i++) {
           if (firstCat == true) {
             document.add(makeBlankLine());
           }

           ShoppingCartServiceData scs = (ShoppingCartServiceData) pItems.get(i);

           if (sForm.isCategoryChanged(i) || firstCat == true) {
             document.add(makePhrase(scs.getCategoryName(),smallHeading, true));
             firstCat = false;
           }

           Table itmTable = makeServiceElement(appUser, scs);

           //if the item data will not fit onto the page,
           //make a new page, and redraw
           //the header.

           if (writer.fitsPage(itmTable, document.bottomMargin())) {
             document.add(itmTable);
           } else {
             document.newPage();
             pageNumber = pageNumber + 1;
             drawHeaderforService(appUser, document, pageNumber, pImageName);
             document.add(makeBlankLine());
             document.add(makePhrase(scs.getCategoryName(), smallHeading, true));
             document.add(itmTable);
           }
         }
         document.add(makeBlankLine());
         document.add(makeBlankLine());
         PdfPTable summaryTable = makeSummary(sForm, appUser);
         document.add(summaryTable);

         //close out the document
         document.close();
       } catch (DocumentException e) {
         e.printStackTrace();
         throw new IOException(e.getMessage());
       }
     }

       private int[] getServiceColumnWidths() {

           int itmColumnWidth[] = new int[5];
           itmColumnWidth[0] = 25; //asset name
           itmColumnWidth[1] = 15; //asset number
           itmColumnWidth[2] = 15; //asset serial number
           itmColumnWidth[3] = 35; // service name
           itmColumnWidth[4] = 10; //price
           return itmColumnWidth;
       }

       private void drawHeaderforService(CleanwiseUser appUser, Document document, int pPageNumber, String pImageName)
               throws DocumentException {
           //add the logo
           try {
               Image i = Image.getInstance(pImageName);
               float x = document.leftMargin();
               float y = document.getPageSize().getHeight() - i.getHeight() - 5;
               i.setAbsolutePosition(x, y);
               document.add(i);
           } catch (Exception e) {
               e.printStackTrace();
           }

           document.add(makeLine());

           //add the item header line
           int numberOfCols = 5;

           PdfPTable itemHeader = new PdfPTable(numberOfCols);
           itemHeader.setWidthPercentage(100);
           itemHeader.setWidths(getServiceColumnWidths());
           itemHeader.getDefaultCell().setBorderWidth(2);
           itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
           itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
           itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
           itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
           String assetNameStr = ClwI18nUtil.getMessage(mRequest, "userAssets.text.assetName", null);
           itemHeader.addCell(makePhrase(assetNameStr, itemHeading, false));
           String assetNumberStr = ClwI18nUtil.getMessage(mRequest, "userAssets.text.assetNumber", null);
           itemHeader.addCell(makePhrase(assetNumberStr, itemHeading, false));
           String assetSerialStr = ClwI18nUtil.getMessage(mRequest, "userAssets.text.assetSerial", null);
           itemHeader.addCell(makePhrase(assetSerialStr, itemHeading, false));
           String serviceNameStr = ClwI18nUtil.getMessage(mRequest, "shoppingServices.text.ourServiceName", null);
           itemHeader.addCell(makePhrase(serviceNameStr, itemHeading, false));
           String priceStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.price", null);
           itemHeader.addCell(makePhrase(priceStr, itemHeading, false));
           document.add(itemHeader);
       }


       //utility function to make an service Element.
       private Table makeServiceElement(CleanwiseUser appUser, ShoppingCartServiceData pService) throws Exception {


           int numberOfCols = 5;

           Table itmTbl = new PTable(numberOfCols);

           itmTbl.setWidth(100);
           itmTbl.setWidths(getServiceColumnWidths());
           itmTbl.getDefaultCell().setBorderColor(java.awt.Color.black);
           itmTbl.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

           String serviceName = "";
           String assetName = "";
           String assetNumber = "";
           String assetSerial = "";


           if (pService != null && pService.getItemData() != null) {
               serviceName = pService.getItemData().getShortDesc();
           }
           if (pService != null && pService.getAssetData() != null) {
               assetName = pService.getAssetData().getShortDesc();
               assetNumber = pService.getAssetData().getAssetNum();
               assetSerial = pService.getAssetData().getSerialNum();
           }

           itmTbl.addCell(makePhrase(assetName, normal, true));
           itmTbl.addCell(makePhrase(assetNumber, normal, true));
           itmTbl.addCell(makePhrase(assetSerial, normal, true));
           itmTbl.addCell(makePhrase(serviceName, normal, true));

           BigDecimal price = null;
           if(pService!=null)
                  price = new BigDecimal(pService.getPrice());

           String priceS = mFormatter.priceFormatWithoutCurrency(price);
           itmTbl.addCell(makePhrase(priceS, normal, true));


           return itmTbl;
       }



  public void generateDistPdf
    (CheckoutForm sForm, CleanwiseUser appUser, StoreData pStore,
     OutputStream pOut, String pImageName, OrderJoinData order, ShoppingCartDistDataVector pCartDistributors) throws
    Exception {
    initColumnsAndWidth(appUser, null);

    try {
      //we keep track of the page number rather than useing the built in pdf functionality
      //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
      //first page of that po, and on the 9th page of the pdf.
      int pageNumber = 1;
      //create the header and footer

      Phrase headPhrase = new Phrase(makeChunk("", heading, true));
      String orderConfirmationStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.orderConfirmation", null);
      headPhrase.add(makeChunk(orderConfirmationStr, heading, true));
      headPhrase.add(makeChunk(SPACE, heading, true));
      headPhrase.add(makeChunk(appUser.getUserAccount().getBusEntity().getShortDesc(), heading, true));

      HeaderFooter header = new HeaderFooter(headPhrase, true);
      header.setAlignment(HeaderFooter.ALIGN_RIGHT);

      //setup the document
      Document document = new Document(PageSize.A4, 10, 15, 30, 15);
      PdfWriter writer = PdfWriter.getInstance(document, pOut);

      Phrase footPhrase = makeStoreFooter(pStore);

      HeaderFooter footer = new HeaderFooter(footPhrase, false);
      footer.setAlignment(HeaderFooter.ALIGN_CENTER);

      //setup the borders from the header
      header.setBorder(borderType);
      footer.setBorder(HeaderFooter.TOP);

      document.setHeader(header);
      document.setFooter(footer);
      document.open();

      drawOrderHeader(document, appUser, sForm, pImageName, order);

      drawHeader(appUser, document, pageNumber, pImageName);

      for (int m = 0; m < pCartDistributors.size(); m++) {

        ShoppingCartDistData cartDist = (ShoppingCartDistData) pCartDistributors.get(m);
        List pItems = cartDist.getShoppingCartItems();
        String distName = cartDist.getDistributorName();

        document.add(makeBlankLine());
        document.add(makePhrase(distName, smallHeading, true));

        boolean firstCat = true;
        for (int i = 0; i < pItems.size(); i++) {
          //if ( firstCat == true ) {
          //    document.add(makeBlankLine());
          //}

          ShoppingCartItemData sci = (ShoppingCartItemData) pItems.get(i);
          if (cartDist.isCategoryChanged(i) || firstCat == true) {
            document.add(makePhrase(SPACE + sci.getCategoryPath(),
                                    smallHeading, true));
            firstCat = false;
          }

          Table itmTable = makeItemElement(appUser, sci);

          //if the item data will not fit onto the page,
          //make a new page, and redraw
          //the header.

          if (writer.fitsPage(itmTable, document.bottomMargin())) {
            document.add(itmTable);
          } else {
            document.newPage();
            pageNumber = pageNumber + 1;
            drawHeader(appUser, document, pageNumber, pImageName);
            document.add(makeBlankLine());
            document.add(makePhrase(SPACE + sci.getCategoryPath(), smallHeading, true));
            document.add(itmTable);
          }
        }
        //document.add(makeBlankLine());

        // print out freight info
        List freightImpliedList = cartDist.getDistFreightImplied();
        if (null != freightImpliedList && 0 < freightImpliedList.size()) {
            for (int i = 0; i < freightImpliedList.size(); i++) {
                FreightTableCriteriaData crit = (FreightTableCriteriaData) freightImpliedList.get(i);
                BigDecimal freightAmt = crit.getFreightAmount();
                BigDecimal handlingAmt = crit.getHandlingAmount();
                if (freightAmt == null) {
                    freightAmt = new BigDecimal(0);
                } else {
                    freightAmt = freightAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                if (handlingAmt != null) {
                    freightAmt = freightAmt.add(handlingAmt.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                String value = null;
                if (RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(crit.getFreightCriteriaTypeCd())) {
                    value = "To Be Determined";
                } else if (freightAmt != null
                        && BigDecimal.ZERO.compareTo(freightAmt) != 0) {
                    value = mFormatter.priceFormat(freightAmt, " ");
                }
                if (Utility.isSet(value)) {
                    document.add(makePhrase(SPACE + crit.getShortDesc()
                            + ": " + value, smallHeading, true));
                }
           }
        }

        String freightVendor = cartDist.getDistSelectableFreightVendorName();
        if (null != freightVendor && 0 < freightVendor.trim().length()) {
          String critStr = "";
          String freightMsg = cartDist.getDistSelectableFreightMsg();
          if (null != freightMsg && 0 < freightMsg.trim().length()) {
            Object[] params = new Object[2];
            params[0] = freightVendor;
            params[1] = freightMsg;
            critStr =
              ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.shippingCostWithDistFreightMess",
                                     params);
          } else {
            BigDecimal distFrCost = new BigDecimal(cartDist.getDistSelectableFreightCost());
            String distFrCostS = mFormatter.priceFormat(distFrCost, " ");
            Object[] params = new Object[2];
            params[0] = freightVendor;
            params[1] = distFrCostS;
            critStr =
              ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.shippingCostNoDistFreightMess",
                                     params);
          }
          document.add(makePhrase(SPACE + critStr, smallHeading, true));
        }

        // print out fuel surcharge info
                List fuelSurchargeList = cartDist.getDistFuelSurchargeList();
                if (null != fuelSurchargeList && 0 < fuelSurchargeList.size()) {
                    for (int i = 0; i < fuelSurchargeList.size(); i++) {
                        FreightTableCriteriaData fuelSurchargeCriteria = (FreightTableCriteriaData) fuelSurchargeList.get(i);
                        String value = null;
                        if (RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(fuelSurchargeCriteria.getFreightCriteriaTypeCd())) {
                            value = "To Be Determined";
                        } else if (fuelSurchargeCriteria.getHandlingAmount() != null
                                && BigDecimal.ZERO.compareTo(fuelSurchargeCriteria.getHandlingAmount()) != 0) {
                            value = mFormatter.priceFormat(fuelSurchargeCriteria.getHandlingAmount(), " ");
                        }
                        if (Utility.isSet(value)) {
                            document.add(makePhrase(SPACE
                                    + fuelSurchargeCriteria.getShortDesc()
                                    + ": " + value, smallHeading, true));
                        }
                    }
                }
        // print out small order fee info
                List smallOrderFeeList = cartDist.getDistSmallOrderFeeList();
                if (null != smallOrderFeeList && 0 < smallOrderFeeList.size()) {
                    for (int i = 0; i < smallOrderFeeList.size(); i++) {
                        FreightTableCriteriaData smallOrderFeeCriteria = (FreightTableCriteriaData) smallOrderFeeList.get(i);
                        String value = null;
                        if (RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(smallOrderFeeCriteria.getFreightCriteriaTypeCd())) {
                            value = "To Be Determined";
                        } else if (smallOrderFeeCriteria.getHandlingAmount() != null
                                && BigDecimal.ZERO.compareTo(smallOrderFeeCriteria.getHandlingAmount()) != 0) {
                            value = mFormatter.priceFormat(smallOrderFeeCriteria.getHandlingAmount(), " ");
                        }
                        if (Utility.isSet(value)) {
                            document.add(makePhrase(SPACE
                                    + smallOrderFeeCriteria.getShortDesc()
                                    + ": " + value, smallHeading, true));
                        }
                    }
                }

        //discount
        List discountList = cartDist.getDistDiscountImplied();
        if(discountList!=null && discountList.size()>0){

        	HashMap discountPerDistHM = sForm.getDiscountAmtPerDist();
        	int frTblId = cartDist.getFrTblId();
    	    int distId = cartDist.getDistId(frTblId);
    	    Integer distIdInt = new Integer( distId );
    	    BigDecimal distIdBD = new BigDecimal(0);
    	    distIdBD = (BigDecimal)discountPerDistHM.get(distIdInt);

    	    if ( distIdBD.compareTo(new BigDecimal(0))>0 ) {
    	    	distIdBD = distIdBD.negate();
    	    }
    	    for (int i = 0; i < discountList.size(); i++) {
                FreightTableCriteriaData discountCriteria = (FreightTableCriteriaData) discountList.get(i);
                if (distIdBD != null && BigDecimal.ZERO.compareTo(distIdBD) != 0) {
                    String discountAmtS = mFormatter.priceFormat(distIdBD, " ");
                    String critStr = discountCriteria.getShortDesc() + ": " + discountAmtS;
                    document.add(makePhrase(SPACE + critStr, smallHeading, true));
                }
    	    }

        }

        if (false) {
          if (null != cartDist.getSalesTax()) {
            String taxS = mFormatter.priceFormat(cartDist.getSalesTax(), " ");
            String taxStr =
              ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.tax:", null);
            document.add(makePhrase(SPACE + taxStr + " " + taxS, smallHeading, true));
          }
        }
      }

      document.add(makeBlankLine());
      document.add(makeBlankLine());
      PdfPTable summaryTable = makeSummary(sForm, appUser);
      document.add(summaryTable);

      //If Consolidated Order
      ReplacedOrderViewVector replacedOrders = sForm.getReplacedOrders();
      boolean consolidatedOrderFl =
        (replacedOrders != null && replacedOrders.size() > 0) ? true : false;
      if (consolidatedOrderFl) {
        document.add(makeBlankLine());
        document.add(makeBlankLine());
        //Replaced Orders Label
        Table replacedOrdersLabel = makeReplacedOrdersLabel();
        boolean skipFl = false;
        if (!writer.fitsPage(replacedOrdersLabel, document.bottomMargin())) {
          document.newPage();
          pageNumber = pageNumber + 1;
          drawReplacedOdersHeader(appUser, document, pageNumber, pImageName);
          skipFl = true;
          document.add(makeBlankLine());
        }
        document.add(replacedOrdersLabel);
        //Replaced Orders Header
        if (!skipFl) {
          Table replacedOrdersHeader = makeReplacedOrdersHeader();
          if (!writer.fitsPage(replacedOrdersHeader, document.bottomMargin())) {
            document.newPage();
            pageNumber = pageNumber + 1;
            drawReplacedOdersHeader(appUser, document, pageNumber, pImageName);
            document.add(makeBlankLine());
          }
          document.add(replacedOrdersHeader);
        }

        //Replaced Orders Elements
        for (Iterator iter = replacedOrders.iterator(); iter.hasNext(); ) {
          ReplacedOrderView roVw = (ReplacedOrderView) iter.next();
          ReplacedOrderItemViewVector roiVwV = roVw.getItems();
          boolean nextOrder = true;
          for (Iterator iter1 = roiVwV.iterator(); iter1.hasNext(); ) {
            ReplacedOrderItemView roiVw =
              (ReplacedOrderItemView) iter1.next();
            Table replacedOrdersElement =
              makeReplacedOrdersElement(roVw, roiVw, nextOrder);
            nextOrder = false;
            if (!writer.fitsPage(replacedOrdersElement, document.bottomMargin())) {
              document.newPage();
              pageNumber = pageNumber + 1;
              drawReplacedOdersHeader(appUser, document, pageNumber, pImageName);
              document.add(makeBlankLine());
            }
            document.add(replacedOrdersElement);

          }
        }
      }

      //close out the document
      document.close();
    } catch (DocumentException e) {
      e.printStackTrace();
      throw new IOException(e.getMessage());
    }
  }


    public void generateDistPdfforService(CheckoutForm sForm, CleanwiseUser appUser, StoreData pStore,
     OutputStream pOut, String pImageName, OrderJoinData order, ShoppingCartDistDataVector pCartDistributors) throws
    Exception {
    initColumnsAndWidth(appUser, null);
    try {
      //we keep track of the page number rather than useing the built in pdf functionality
      //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
      //first page of that po, and on the 9th page of the pdf.
      int pageNumber = 1;
      //create the header and footer

      Phrase headPhrase = new Phrase(makeChunk("", heading, true));
      String orderConfirmationStr =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.orderConfirmation", null);
      headPhrase.add(makeChunk(orderConfirmationStr, heading, true));
      headPhrase.add(makeChunk(SPACE, heading, true));
      headPhrase.add(makeChunk(appUser.getUserAccount().getBusEntity().getShortDesc(), heading, true));

      HeaderFooter header = new HeaderFooter(headPhrase, true);
      header.setAlignment(HeaderFooter.ALIGN_RIGHT);

      //setup the document
      Document document = new Document(PageSize.A4, 10, 15, 30, 15);
      PdfWriter writer = PdfWriter.getInstance(document, pOut);

      Phrase footPhrase = makeStoreFooter(pStore);

      HeaderFooter footer = new HeaderFooter(footPhrase, false);
      footer.setAlignment(HeaderFooter.ALIGN_CENTER);

      //setup the borders from the header
      header.setBorder(borderType);
      footer.setBorder(HeaderFooter.TOP);

      document.setHeader(header);
      document.setFooter(footer);
      document.open();

      drawOrderHeader(document, appUser, sForm, pImageName, order);

      drawHeaderforService(appUser, document, pageNumber, pImageName);

      for (int m = 0; m < pCartDistributors.size(); m++) {

        ShoppingCartDistData cartDist = (ShoppingCartDistData) pCartDistributors.get(m);
        List pServices = cartDist.getShoppingCartServices();
        String distName = cartDist.getDistributorName();

        document.add(makeBlankLine());
        document.add(makePhrase(distName, smallHeading, true));

        boolean firstCat = true;
        for (int i = 0; i < pServices.size(); i++) {
          //if ( firstCat == true ) {
          //    document.add(makeBlankLine());
          //}

          ShoppingCartServiceData scs = (ShoppingCartServiceData) pServices.get(i);
          if (cartDist.isServiceCategoryChanged(i) || firstCat == true) {
            document.add(makePhrase(SPACE + scs.getCategoryName(),
                                    smallHeading, true));
            firstCat = false;
          }

          Table itmTable = makeServiceElement(appUser, scs);

          //if the item data will not fit onto the page,
          //make a new page, and redraw
          //the header.

          if (writer.fitsPage(itmTable, document.bottomMargin())) {
            document.add(itmTable);
          } else {
            document.newPage();
            pageNumber = pageNumber + 1;
            drawHeaderforService(appUser, document, pageNumber, pImageName);
            document.add(makeBlankLine());
            document.add(makePhrase(SPACE + scs.getCategoryName(), smallHeading, true));
            document.add(itmTable);
          }
        }
        //document.add(makeBlankLine());

        // print out freight info
        List freightImpliedList = cartDist.getDistFreightImplied();
        if (null != freightImpliedList && 0 < freightImpliedList.size()) {
          for (int i = 0; i < freightImpliedList.size(); i++) {
            FreightTableCriteriaData crit = (FreightTableCriteriaData) freightImpliedList.get(i);
            BigDecimal freightAmt = crit.getFreightAmount();
            String freightAmtS = mFormatter.priceFormat(freightAmt, " ");
            String critStr = crit.getShortDesc() + ": " + freightAmtS;
            document.add(makePhrase(SPACE + critStr, smallHeading, true));
          }
        }

        String freightVendor = cartDist.getDistSelectableFreightVendorName();
        if (null != freightVendor && 0 < freightVendor.trim().length()) {
          String critStr = "";
          String freightMsg = cartDist.getDistSelectableFreightMsg();
          if (null != freightMsg && 0 < freightMsg.trim().length()) {
            Object[] params = new Object[2];
            params[0] = freightVendor;
            params[1] = freightMsg;
            critStr =
              ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.shippingCostWithDistFreightMess",
                                     params);
          } else {
            BigDecimal distFrCost = new BigDecimal(cartDist.getDistSelectableFreightCost());
            String distFrCostS = mFormatter.priceFormat(distFrCost, " ");
            Object[] params = new Object[2];
            params[0] = freightVendor;
            params[1] = distFrCostS;
            critStr =
              ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.shippingCostNoDistFreightMess",
                                     params);
          }
          document.add(makePhrase(SPACE + critStr, smallHeading, true));
        }

      //discount
        List discountList = cartDist.getDistDiscountImplied();
        if(discountList!=null && discountList.size()>0){

        	HashMap discountPerDistHM = sForm.getDiscountAmtPerDist();
        	int frTblId = cartDist.getFrTblId();
    	    int distId = cartDist.getDistId(frTblId);
    	    Integer distIdInt = new Integer( distId );
    	    BigDecimal distIdBD = new BigDecimal(0);
    	    distIdBD = (BigDecimal)discountPerDistHM.get(distIdInt);

    	    if ( distIdBD.compareTo(new BigDecimal(0))>0 ) {
    	    	distIdBD = distIdBD.negate();
    	    }
    	    for (int i = 0; i < discountList.size(); i++) {
                FreightTableCriteriaData discountCriteria = (FreightTableCriteriaData) discountList.get(i);

                String discountAmtS = mFormatter.priceFormat(distIdBD, " ");
                String critStr = discountCriteria.getShortDesc() + ": " + discountAmtS;
                document.add(makePhrase(SPACE + critStr, smallHeading, true));
    	    }

        }

        if (false) {
          if (null != cartDist.getSalesTax()) {
            String taxS = mFormatter.priceFormat(cartDist.getSalesTax(), " ");
            String taxStr =
              ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.tax:", null);
            document.add(makePhrase(SPACE + taxStr + " " + taxS, smallHeading, true));
          }
        }
      }

      document.add(makeBlankLine());
      document.add(makeBlankLine());
      PdfPTable summaryTable = makeSummary(sForm, appUser);
      document.add(summaryTable);

      //close out the document
      document.close();
    } catch (DocumentException e) {
      e.printStackTrace();
      throw new IOException(e.getMessage());
    }
  }


///////////////////////////////////////////////////////////////////////////////
  private Table makeReplacedOrdersLabel() throws DocumentException {
    Table replacedOrdersLabel = new PTable(1);
    replacedOrdersLabel.setWidth(100);
    replacedOrdersLabel.getDefaultCell().setBorderWidth(2);
    replacedOrdersLabel.getDefaultCell().setBackgroundColor(java.awt.Color.black);
    replacedOrdersLabel.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
    replacedOrdersLabel.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
    replacedOrdersLabel.getDefaultCell().setBorderColor(java.awt.Color.white);
    String replacedOrdersStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.replacedOrders", null);
    replacedOrdersLabel.addCell(makePhrase(replacedOrdersStr, itemHeading, false));
    return replacedOrdersLabel;
  }

  private Table makeReplacedOrdersHeader() throws DocumentException {
    Table replacedOrdersHeader = new PTable(7);
    replacedOrdersHeader.setWidth(100);
    replacedOrdersHeader.setWidths(new int[] {20, 10, 10, 10, 10, 10, 30});
    replacedOrdersHeader.getDefaultCell().setBorderWidth(2);
    replacedOrdersHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
    replacedOrdersHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
    replacedOrdersHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
    replacedOrdersHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
    String shipToStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.shipTo", null);
    replacedOrdersHeader.addCell(makePhrase(shipToStr, itemHeading, false));
    String orderNumStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.order#", null);
    replacedOrdersHeader.addCell(makePhrase(orderNumStr, itemHeading, false));
    String referenceOrderNumStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.referenceOrder#", null);
    replacedOrdersHeader.addCell(makePhrase(referenceOrderNumStr, itemHeading, false));
    String requestPoNumStr =
      ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.requestPo#", null);
    replacedOrdersHeader.addCell(makePhrase(requestPoNumStr, itemHeading, false));
    String orderQtyStr =
      ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.orderQty", null);
    replacedOrdersHeader.addCell(makePhrase(orderQtyStr, itemHeading, false));
    String ourSkuNumStr =
      ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.ourSku#", null);
    replacedOrdersHeader.addCell(makePhrase(ourSkuNumStr, itemHeading, false));
    String productNameStr =
      ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.product<sp>Name", null);
    replacedOrdersHeader.addCell(makePhrase(productNameStr, itemHeading, false));
    return replacedOrdersHeader;

  }

  private Table makeReplacedOrdersElement
    (ReplacedOrderView pReplOrder,
     ReplacedOrderItemView pReplOrderItem,
     boolean pNextOrder) throws DocumentException {

    Table replacedOrdersElement = new PTable(7);
    replacedOrdersElement.setWidth(100);
    replacedOrdersElement.setWidths(new int[] {20, 10, 10, 10, 10, 10, 30});
    replacedOrdersElement.getDefaultCell().setBorderColor(java.awt.Color.black);
    replacedOrdersElement.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

    String shipTo = (pNextOrder) ? pReplOrder.getOrderSiteName() : "";
    String orderNum = (pNextOrder) ? pReplOrder.getOrderNum() : "";
    String refNum = (pNextOrder) ? pReplOrder.getRefOrderNum() : "";
    String requestPoNum = (pNextOrder) ? pReplOrder.getRequestPoNum() : "";
    int orderQty = pReplOrderItem.getQuantity();
    String orderQtyS = "" + orderQty;
    if (orderQty == 0 ||
        RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
        equals(pReplOrderItem.getOrderItemStatusCd())
      ) {
      orderQtyS =
        ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.cancelled", null);
    }
    String ourSku = pReplOrderItem.getCustItemSkuNum();
    if (ourSku == null) {
      ourSku = "" + pReplOrderItem.getItemSkuNum();
    }
    String shortDesc = pReplOrderItem.getCustItemShortDesc();
    if (shortDesc == null) {
      shortDesc = pReplOrderItem.getItemShortDesc();
    }
    replacedOrdersElement.addCell(makePhrase(shipTo, normal, true));
    replacedOrdersElement.addCell(makePhrase(orderNum, normal, true));
    replacedOrdersElement.addCell(makePhrase(refNum, normal, true));
    replacedOrdersElement.addCell(makePhrase(requestPoNum, normal, true));
    replacedOrdersElement.addCell(makePhrase(orderQtyS, normal, true));
    replacedOrdersElement.addCell(makePhrase(ourSku, normal, true));
    replacedOrdersElement.addCell(makePhrase(shortDesc, normal, true));

    return replacedOrdersElement;
  }

  private void drawReplacedOdersHeader(CleanwiseUser appUser, Document document,
                                       int pPageNumber, String pImageName) throws DocumentException {

    drawLogo(appUser, document, pPageNumber, pImageName);
    Table replacedOrdersLabel = makeReplacedOrdersLabel();
    document.add(replacedOrdersLabel);
    Table replacedOrdersHeader = makeReplacedOrdersHeader();
    document.add(replacedOrdersHeader);
  }

  private void drawLogo(CleanwiseUser appUser, Document document,
                        int pPageNumber, String pImageName) throws DocumentException {

    //add the logo
    try {
      Image i = Image.getInstance(pImageName);
      float x = document.leftMargin();
      float y = document.getPageSize().getHeight() - i.getHeight() - 5;
      i.setAbsolutePosition(x, y);
      document.add(i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private static boolean isShowValue(HttpServletRequest request, String pParamName) {
    String value = request.getParameter(pParamName);
    if (Utility.isSet(value) && !Utility.isTrue(value)) {
      return false;
    }
    return true;
  }


  private void initColumnsAndWidth(CleanwiseUser appUser, List items) {

    mShowSize = isShowValue(mRequest, "showSize");
    //mShowMfg = isShowValue(mRequest, "showMfg");
    //mShowMfgSku = isShowValue(mRequest, "showMfgSku");
    mShowPrice = appUser.getShowPrice();

    if (appUser.getUserAccount().isShowSPL()) {
      mColumnCount++;
    }
    if (mShowPrice) {
      mColumnCount = mColumnCount + 2;
    }

    if (isAuthorizedForResale(appUser)) {
      mColumnCount++;
    }

    if (mShowSize) {
      mColumnCount++;
    }
    /*if (mShowMfg) {
      mColumnCount++;
    }
    if (mShowMfgSku) {
      mColumnCount++;
    } */

    itmColumnWidth = new int[mColumnCount];
    int i = 0;
    itmColumnWidth[i++] = 4; //Qty
    itmColumnWidth[i++] = getSkuColumnLength(appUser, items); //Our Sku#
    if (isAuthorizedForResale(appUser)) {
      itmColumnWidth[i++] = 27; //Name
    } else {
      itmColumnWidth[i++] = 30; //Name
    }
    if (mShowSize) {
      itmColumnWidth[i++] = 7; //Size
    }
    //itmColumnWidth[i++] = 5; //Pack
    //itmColumnWidth[i++] = 4; //Uom
    /*if (mShowMfg) {
      itmColumnWidth[i++] = 18; //Maxnufacturer
    }
    if (mShowMfgSku) {
      itmColumnWidth[i++] = getMfgSkuColumnLength(appUser, items); //Mfg.Sku#
    } */
    if (mShowPrice) {
      itmColumnWidth[i++] = 8; //Price
      if (isAuthorizedForResale(appUser)) {
        itmColumnWidth[i++] = 10; //Amount
      } else {
        itmColumnWidth[i++] = 13; //Amount
      }
    }

    if (isAuthorizedForResale(appUser)) {
      itmColumnWidth[i++] = 6;
    }
    if (appUser.getUserAccount().isShowSPL()) {
      itmColumnWidth[i++] = 4; //SPL
    }

  }

    public boolean isShowSize() {
        return mShowSize;
    }

    public boolean isShowMfg() {
        return mShowMfg;
    }

    public boolean isShowMfgSku() {
        return mShowMfgSku;
    }

    public HttpServletRequest getRequest() {
        return mRequest;
    }

    public ClwI18nUtil getFormatter() {
        return mFormatter;
    }

    public int[] getHalves() {
        return halves;
    }

    public boolean isShowCategories() {
        return true;
    }


}

