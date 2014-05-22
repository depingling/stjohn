package com.cleanwise.view.utils.pdf;

import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.utils.ContactUsInfo;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.lowagie.text.*;
import com.lowagie.text.List;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.math.BigDecimal;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;

import org.apache.log4j.*;
/**
 *
 *Constructs a pdf document in the form of a pdf document.  It
 *understands the various propriatary objects, and uses them to
 *construct a purchase order document.
 * @author  bstevens
 */
public class PdfCatalogProductSwiss extends PdfCatalogProduct {
  //static configuration variables
  //configures the percentage widths of item columns in the document
  private static final Logger log = Logger.getLogger(PdfCatalogProductSwiss.class);
  private static int halves[] = {62, 38};
  private static int sizes[] = {30, 70};
  private boolean catalogFl = false;
  private boolean printXpedxCols = false;
  private boolean mShowSize = true;
  private boolean mShowPrice = true;
  private int mColumnCount = 4;
  private int itmColumnWidth[];
  private Locale mCatalogLocale;

  private boolean isPhysicalCountSheet = false;


  /** Creates a new instance of PdfOrderGuide */
  public PdfCatalogProductSwiss() {
  }

 public void init(HttpServletRequest pRequest, String pCatalogLocaleCd) {
    if (!Utility.isSet(pCatalogLocaleCd)) {
        pCatalogLocaleCd = "fr_CH";
    }
    super.init(pRequest, pCatalogLocaleCd);
  }
  //utility function to make an item Element.
  private Table makeItemElement(ShoppingCartItemData pItm, AccountData pAccount) throws DocumentException {

    Table itmTbl = new PTable(mColumnCount);
    itmTbl.setWidth(100);
    itmTbl.setWidths(itmColumnWidth);
    itmTbl.getDefaultCell().setBorderColor(java.awt.Color.black);
    itmTbl.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

    if (!catalogFl) {
      String t0 = "";
      if (pItm.getIsaInventoryItem()) {
        t0 = "i";
      }

      if (null != mSiteData &&
          mSiteData.isAnInventoryAutoOrderItem
          (pItm.getProduct().getProductId())) {
        t0 += "a";
      }

      Cell tpc0 = new Cell
                      (makePhrase(t0, small, true));
      if (!pItm.getIsaInventoryItem()) {
        tpc0.setBorder(0);
      }

      itmTbl.addCell(tpc0);
    }

    if(!printXpedxCols){
    Cell tpc01 = new Cell
                     (makePhrase("", normal, true));
    itmTbl.addCell(tpc01);
    }
    String t = "";
    if (pItm.getProduct().isPackProblemSku()) {
      t += "*";
    }
    if (t.length() > 0) t += " ";
    if(printXpedxCols){
    	if(isPhysicalCountSheet){
    		itmTbl.addCell(makePhrase("", normal, true));
    	}else{
    		itmTbl.addCell(makePhrase(pItm.getInventoryQtyOnHandString(), normal, true));
    	}
    	if(pItm.getMaxOrderQty() < 0 ){
    		itmTbl.addCell(makePhrase("", normal, true));
    	}else{
    		itmTbl.addCell(makePhrase(Integer.toString(pItm.getMaxOrderQty()), normal, true));
    	}
    	if(!isPhysicalCountSheet){
    		itmTbl.addCell(makePhrase(pItm.getQuantityString(), normal, true));
    	}
    }
    Cell tpc1 = new Cell
                    (makePhrase(t + pItm.getActualSkuNum(), normal, true));
    itmTbl.addCell(tpc1);

    itmTbl.addCell(makePhrase(pItm.getProduct().getCatalogProductShortDesc(), normal, true));
    if (mShowSize) {
      itmTbl.addCell(makePhrase(pItm.getProduct().getSize(), normal, true));
    }

    if (mShowPrice) {
      BigDecimal price = new BigDecimal(pItm.getPrice());
      String priceStr = "";
      try {
        priceStr = getFormatter().priceFormatWithoutCurrency(price);
      } catch (Exception exc) {
        exc.printStackTrace();
      }
      Cell pcell = new Cell(makePhrase(priceStr, normal, true));
      pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      itmTbl.addCell(pcell);
    }

    if (pAccount.isShowSPL()) {
      if (pItm.getProduct() != null && pItm.getProduct().getCatalogDistrMapping() != null &&
          Utility.isTrue(pItm.getProduct().getCatalogDistrMapping().getStandardProductList())) {
        String yStr =
          ClwI18nUtil.getMessage(getRequest(), "shoppingItems.text.y", null);
        itmTbl.addCell(makePhrase(yStr, normal, true));
      } else {
        String nStr =
          ClwI18nUtil.getMessage(getRequest(), "shoppingItems.text.n", null);
        itmTbl.addCell(makePhrase(nStr, normal, true));
      }
    }

    if (!catalogFl) {
      //BigDecimal amount = new BigDecimal(pItm.getAmount());
      itmTbl.addCell(makePhrase("", normal, true));
    }

    return itmTbl;
  }


  private String mSkuTag;
  // controls the order qty or requested qty column
  private boolean mForInventoryShopping = false;

  private void drawHeader(Document document,
                          int pPageNumber, String pSenderName, String pImageName,
                          boolean pInventoryHeader, AccountData pAccount) throws DocumentException {
    //add the logo
    try {
      Image i = Image.getInstance(pImageName);
      float x = document.leftMargin();
      float y = document.getPageSize().getHeight() - i.getHeight() - 5;
      i.setAbsolutePosition(x, y);
      document.add(i);
    } catch (Exception e) {
      //e.printStackTrace();
    }
    //deal with header info construct the table/cells with all of
    //the header information must be the pdf specific type or the
    //table will not use the full width of the page.
    //unfortuanately this means we are limited to PDF rendering
    //only.

    //draw a line XXX this is somewhat of a hack, as there seems
    //to be no way to draw a line in the current iText API that is
    //relative to your current position in the document

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
    if(printXpedxCols){
    	String qtyOnHandStr =
            ClwI18nUtil.getMessage(getRequest(), "shop.og.table.header.qtyOnHand", null);
          itemHeader.addCell(makePhrase(qtyOnHandStr, itemHeading, false));

          String maxQtyStr =
        	  ClwI18nUtil.getMessage(getRequest(),"shop.og.table.header.maxQty", null);
          itemHeader.addCell(makePhrase(maxQtyStr, itemHeading, false));

          if(!isPhysicalCountSheet){
        	  String requestedQtyStr =
        		  ClwI18nUtil.getMessage(getRequest(), "shop.og.text.requestedQty", null);
        	  itemHeader.addCell(makePhrase(requestedQtyStr, itemHeading, false));
          }
    } else {
    if (!catalogFl) {
      String c1 = "";
      if (pInventoryHeader) {
        String qtyOnHandStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.table.header.qtyOnHand", null);
        itemHeader.addCell(makePhrase(qtyOnHandStr, itemHeading, false));
        String requestedQtyStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.requestedQty", null);
        itemHeader.addCell(makePhrase(requestedQtyStr, itemHeading, false));
        mForInventoryShopping = true;
      } else {
        itemHeader.addCell(makePhrase(" ", itemHeading, false));
        if (mForInventoryShopping) {
          c1 = ClwI18nUtil.getMessage(getRequest(), "shop.og.text.requestedQty", null);
        } else {
          c1 = ClwI18nUtil.getMessage(getRequest(), "shop.og.text.orderQty", null);
        }
        itemHeader.addCell(makePhrase(c1, itemHeading, false));
      }
    }
    if (catalogFl) {
      String c1 = ClwI18nUtil.getMessage(getRequest(), "shop.og.text.orderQty", null);
      itemHeader.addCell(makePhrase(c1, itemHeading, false));
    }

    }//end else printXpedxCols

    itemHeader.addCell(makePhrase(mSkuTag, itemHeading, false));
    String c2 = "";
    if (pInventoryHeader) {
      c2 = ClwI18nUtil.getMessage(getRequest(), "shop.og.text.inventoryProductName", null);
    } else {
      c2 = ClwI18nUtil.getMessage(getRequest(), "shop.og.text.productName", null);
    }
    itemHeader.addCell(makePhrase(c2, itemHeading, false));
    if (mShowSize) {
      String sizeStr =
        ClwI18nUtil.getMessage(getRequest(), "shoppingItems.text.size", null);
      itemHeader.addCell(makePhrase(sizeStr, itemHeading, false));
    }
    CurrencyData curr = ClwI18nUtil.getCurrency(getCatalogLocale().toString());
    String currencyCode = "CHF";
    if (curr != null) {
        currencyCode = curr.getGlobalCode();
    }


    if (mShowPrice) {
      String priceStr =
        ClwI18nUtil.getMessage(getRequest(), "shoppingItems.text.priceIn", new String[]{currencyCode});
      itemHeader.addCell(makePhrase(priceStr, itemHeading, false));
    }
    if (pAccount.isShowSPL()) {
      String splStr =
        ClwI18nUtil.getMessage(getRequest(), "shoppingItems.text.spl", null);
      itemHeader.addCell(makePhrase(splStr, itemHeading, false));
    }
    if (!catalogFl) {
      String amountStr =
        ClwI18nUtil.getMessage(getRequest(), "shoppingItems.text.amount", null);
      itemHeader.addCell(makePhrase(amountStr, itemHeading, false));
    }
    document.add(itemHeader);

  }

  private void drawOGHeader(Document document, UserShopForm sForm,
                            String pImageName, boolean personal) throws DocumentException {

    //add the logo
    try {
      Image i = Image.getInstance(pImageName);
      float x = document.leftMargin();
      float y = document.getPageSize().getHeight() - i.getHeight() - 5;
      i.setAbsolutePosition(x, y);
      document.add(i);
    } catch (Exception e) {
      log.info("error adding document image!");
      //e.printStackTrace();
    }

    if (catalogFl && !personal) {
        return;
    }

    //unfortuanately this means we are limited to PDF rendering only.
    //get the user's personal info
    PdfPTable personalInfo;
    if (personal) {
      personalInfo = getPersonalized(sForm);
    } else {
      personalInfo = getNonPersonalized(sForm);
    }

    if (null != sForm.getAppUser().getUserAccount() &&
        null != sForm.getAppUser().getUserAccount().getSkuTag()) {
      String t = sForm.getAppUser().
                 getUserAccount().getSkuTag().getValue();
      if (t != null && t.length() > 0) {
        mSkuTag = t;
      }
    }

    //Display the contact info
    PdfPTable contactInfo = new PdfPTable(2);
    contactInfo.setWidthPercentage(50);
    contactInfo.setWidths(sizes);
    contactInfo.getDefaultCell().setBorder(borderType);
    contactInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    String orderOnlineStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.orderOnLine:", null);
    contactInfo.addCell(makePhrase(orderOnlineStr + " ", smallHeading, false));
    contactInfo.addCell(makePhrase(sForm.getAppUser().getUserStore().getStorePrimaryWebAddress().getValue(), smallHeading, true));

    ContactUsInfo contact = null;
    if (sForm.getAppUser().getContactUsList().size() == 1) {
      contact = (ContactUsInfo) sForm.getAppUser().getContactUsList().get(0);

    }

    String orderFaxNumber = null, bscdesc = null;
    if (sForm.getAppUser().getSite().getBSC() != null &&
        sForm.getAppUser().getSite().
        getBSC().getFaxNumber() != null &&
        sForm.getAppUser().getSite().
        getBSC().getFaxNumber().getPhoneNum() != null) {
      orderFaxNumber = sForm.getAppUser().getSite().
                       getBSC().getFaxNumber().getPhoneNum();
      bscdesc = sForm.getAppUser().getSite().
                getBSC().getBusEntityData().getLongDesc();
    }
    if (orderFaxNumber == null && contact != null) {
      orderFaxNumber = contact.getFax();
    }

    if (orderFaxNumber != null) {
      String faxOrderStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.og.text.faxOrder:", null);
      contactInfo.addCell(makePhrase(faxOrderStr + " ", smallHeading, false));
      contactInfo.addCell(makePhrase(orderFaxNumber, smallHeading, true));
    }

    if (null != bscdesc && bscdesc.length() > 0) {
      // if there is a BSC descripton set, then show
      // that information.
      PdfPCell cell =
        new PdfPCell(new Paragraph(bscdesc, smallHeading));
      cell.disableBorderSide(PdfPCell.LEFT);
      cell.disableBorderSide(PdfPCell.RIGHT);
      cell.disableBorderSide(PdfPCell.TOP);
      cell.disableBorderSide(PdfPCell.BOTTOM);
      cell.setColspan(2);
      cell.setPaddingTop(6);
      contactInfo.addCell(cell);
    } else {

      // otherwise show the standard contact us information
      // request for dmsi - dhl for eric.  durval 11/1/2005.

      if (contact != null) {
        contactInfo.addCell(makePhrase(" ", smallHeading, false));
        contactInfo.addCell(makePhrase(" ", smallHeading, true));
        String contactUsStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.contactUs", null);
        contactInfo.addCell(makePhrase(contactUsStr + " ", smallHeading, false));
        contactInfo.addCell(makePhrase("", smallHeading, true));

        String phoneStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.phone:", null);
        contactInfo.addCell(makePhrase(phoneStr + " ", smallHeading, false));
        contactInfo.addCell(makePhrase("" + contact.getPhone() + "  " +
                                       contact.getCallHours(), smallHeading, true));

        String emailStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.email:", null);
        contactInfo.addCell(makePhrase(emailStr + " ", smallHeading, false));
        contactInfo.addCell(makePhrase(contact.getEmail(), smallHeading, true));
      }
    }

    //make the two tables into one
    PdfPTable wholeTable = new PdfPTable(2);
    wholeTable.setWidthPercentage(100);

    wholeTable.setWidths(halves);
    wholeTable.getDefaultCell().setBorder(borderType);

    wholeTable.addCell(personalInfo);
    wholeTable.addCell(contactInfo);
    document.add(wholeTable);

    String ogcomments = "";

    if (null != sForm.getAppUser().getSite() &&
        null != sForm.getAppUser().getSite().getComments()) {
      ogcomments = sForm.getAppUser().getSite().getComments().getValue();
    }

    if (null == ogcomments || ogcomments.length() == 0) {
      if (null != sForm.getAppUser().getUserAccount()) {
        ogcomments = sForm.getAppUser().
                     getUserAccount().getComments().getValue();
      }
    }

    //add the comments if there are any
    if (ogcomments != null && ogcomments.length() > 0) {
      document.add(makeBlankLine());
      String commentsStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.og.text.comments:", null);
      document.add(makePhrase(commentsStr + " " + ogcomments, smallHeading, true));
    }

    document.add(makeBlankLine());
    String commentsStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.comments:", null);
    document.add(makePhrase
                 (commentsStr + " "
                  + "__________________________________________"
                  + "__________________________________________"
                  + "__________",
                  smallHeading, true));
    document.add(makePhrase
                 ("__________"
                  + "__________________________________________"
                  + "__________________________________________"
                  + "__________",
                  smallHeading, true));
    document.add(makePhrase
                 ("__________"
                  + "__________________________________________"
                  + "__________________________________________"
                  + "__________",
                  smallHeading, true));

    // Add an order guide note in the next page if a note is present.
    if (null != sForm.getAppUser().getUserAccount() &&
        null != sForm.getAppUser().getUserAccount().getOrderGuideNote()) {
      String t = sForm.getAppUser().
                 getUserAccount().getOrderGuideNote().getValue();
      if (t != null && t.length() > 0) {
        document.newPage();
        pageNumber = pageNumber + 1;
        //document.add(makeBlankLine());
        document.add(makePhrase(t, smallHeading, true));
      }
    }

    PdfPTable sort = new PdfPTable(1);
    sort.setWidthPercentage(100);
    sort.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
    sort.getDefaultCell().setBorder(borderType);

    //if personal - in this case used with meaning
    //if action is "pdfPrintCatalogCatalogPers" - then
    //always use "shop.og.text.sortedBy:Category"
    if ((sForm.getOrderBy() == Constants.ORDER_BY_CATEGORY) || personal){
      String sortedByCategoryStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.og.text.sortedBy:Category", null);
      sort.addCell(makePhrase(sortedByCategoryStr + " ", smallHeading, false));
    } else if (sForm.getOrderBy() == Constants.ORDER_BY_CUST_SKU) {
      String sortedByOurSkuNumStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.og.text.sortedBy:OurSkuNum", null);
      sort.addCell(makePhrase(sortedByOurSkuNumStr, smallHeading, false));
    } else {
      String sortedByProductNameStr =
        ClwI18nUtil.getMessage(getRequest(), "shop.og.text.sortedBy:ProductName", null);
      sort.addCell(makePhrase(sortedByProductNameStr, smallHeading, false));
    }
    document.add(sort);

  }

  private PdfPTable getPersonalized(UserShopForm sForm) throws DocumentException {

    PdfPTable personalInfo = new PdfPTable(2);
    personalInfo.setWidthPercentage(50);
    personalInfo.setWidths(sizes);
    personalInfo.getDefaultCell().setBorder(borderType);
    personalInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

    String dateStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.date:", null);
    personalInfo.addCell(makePhrase(dateStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ",
                                    smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String submittedByStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.submittedBy:", null);
    personalInfo.addCell(makePhrase(submittedByStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    String companyNameStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.companyName:", null);
    personalInfo.addCell(makePhrase(companyNameStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase(" " + sForm.getAppUser().getUserAccount().getBusEntity().getShortDesc(),
                                    smallHeading, true));
    String siteNumStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.site#:", null);
    personalInfo.addCell(makePhrase(siteNumStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase(" " + sForm.getAppUser().getSite().getBusEntity().getShortDesc(),
                                    smallHeading, true));

    String address1Str =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.address1:", null);
    personalInfo.addCell(makePhrase(address1Str + " ", smallHeading, false));
    personalInfo.addCell(makePhrase(" " + sForm.getAppUser().getSite().getSiteAddress().getAddress1(),
                                    smallHeading, true));

    if (sForm.getAppUser().getSite().getSiteAddress().getAddress2() != null) {
      String address2Str =
        ClwI18nUtil.getMessage(getRequest(), "shop.og.text.address2:", null);
      personalInfo.addCell(makePhrase(address2Str + " ", smallHeading, false));
      personalInfo.addCell(makePhrase(" " + sForm.getAppUser().getSite().getSiteAddress().getAddress2(),
                                      smallHeading, true));
    }

    if (sForm.getAppUser().getSite().getSiteAddress().getAddress3() != null) {
      String address3Str =
        ClwI18nUtil.getMessage(getRequest(), "shop.og.text.address3:", null);
      personalInfo.addCell(makePhrase(address3Str + " ", smallHeading, false));
      personalInfo.addCell(makePhrase(" " + sForm.getAppUser().getSite().getSiteAddress().getAddress3(),
                                      smallHeading, true));
    }

    String cityStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.city:", null);
    personalInfo.addCell(makePhrase(cityStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase(" " + sForm.getAppUser().getSite().getSiteAddress().getCity(), smallHeading, true));

    if (sForm.getAppUser().getUserStore().isStateProvinceRequired()) {
      String state = sForm.getAppUser().getSite().getSiteAddress().getStateProvinceCd();
      if (Utility.isSet(state)) {
        String stateStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.state:", null);
        personalInfo.addCell(makePhrase(stateStr + " ", smallHeading, false));
        personalInfo.addCell(makePhrase(" " + state, smallHeading, true));
      }
    }

    String postalCodeStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.postalCode:", null);
    personalInfo.addCell(makePhrase(postalCodeStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase(" " + sForm.getAppUser().getSite().getSiteAddress().getPostalCode(), smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String phoneNumStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.phone#:", null);
    personalInfo.addCell(makePhrase(phoneNumStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String orderPlacedByStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.orderPlacedBy:", null);
    personalInfo.addCell(makePhrase(orderPlacedByStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    return personalInfo;

  }

  private PdfPTable getNonPersonalized(UserShopForm sForm) throws DocumentException {

    PdfPTable personalInfo = new PdfPTable(2);
    personalInfo.setWidthPercentage(50);
    personalInfo.setWidths(sizes);
    personalInfo.getDefaultCell().setBorder(borderType);

    String dateStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.date:", null);
    personalInfo.addCell(makePhrase(dateStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String submittedByStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.submittedBy:", null);
    personalInfo.addCell(makePhrase(submittedByStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String companyNameStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.companyName:", null);
    personalInfo.addCell(makePhrase(companyNameStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String siteNumStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.site#:", null);
    personalInfo.addCell(makePhrase(siteNumStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String address1Str =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.address1:", null);
    personalInfo.addCell(makePhrase(address1Str + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String address2Str =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.address2:", null);
    personalInfo.addCell(makePhrase(address2Str + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String address3Str =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.address3:", null);
    personalInfo.addCell(makePhrase(address3Str + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String cityStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.city:", null);
    personalInfo.addCell(makePhrase(cityStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    if (sForm.getAppUser().getUserStore().isStateProvinceRequired()) {
      String state = sForm.getAppUser().getSite().getSiteAddress().getStateProvinceCd();
      if (Utility.isSet(state)) {
        String stateStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.state:", null);
        personalInfo.addCell(makePhrase(stateStr + " ", smallHeading, false));
        personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));
      }
    }

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String postalCodeStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.postalCode:", null);
    personalInfo.addCell(makePhrase(postalCodeStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String phoneNumStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.phone#:", null);
    personalInfo.addCell(makePhrase(phoneNumStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    personalInfo.addCell(makePhrase("", smallHeading, false));
    personalInfo.addCell(makePhrase("", smallHeading, true));

    String orderPlacedByStr =
      ClwI18nUtil.getMessage(getRequest(), "shop.og.text.orderPlacedBy:", null);
    personalInfo.addCell(makePhrase(orderPlacedByStr + " ", smallHeading, false));
    personalInfo.addCell(makePhrase("_____________________________________________ ", smallHeading, true));

    return personalInfo;

  }

  //we keep track of the page number rather than using the
  //built in pdf functionality as we have to maintain pages
  //between pos, so we may be on the 3rd po, but on the
  //first page of that po, and on the 9th page of the pdf.
  private int pageNumber = 1;

  //main worker method.  The public methods will call this method
  //to generate the pdf after the object has been converted.
  private SiteData mSiteData = null;

  public void generatePdf(UserShopForm sForm, java.util.List pItems, StoreData pStore,
          OutputStream pOut, String pImageName, boolean personal, boolean pcatalogFl)throws IOException {
	  try{
		  generatePdf(sForm,pItems,pStore,pOut,pImageName,personal,pcatalogFl,false);
	  }catch (Exception e) {
		  e.printStackTrace();
		  throw new IOException(e.getMessage());
	  }
  }
  public void generatePdf(UserShopForm sForm, java.util.List pItems, StoreData pStore,
                          OutputStream pOut, String pImageName, boolean personal, boolean pcatalogFl,
                          boolean pInventoryOnly)
    throws IOException {
    mSkuTag = ClwI18nUtil.getMessage(getRequest(), "shop.og.text.ourSkuNum", null);
    try {
      catalogFl = pcatalogFl;

      mSiteData = sForm.getAppUser().getSite();
      AccountData mAccount = sForm.getAppUser().getUserAccount();
      //Check config.txt to set specific columns for xpedx
      Properties configProps = new Properties();
      String fileName = ClwCustomizer.getAbsFilePath(getRequest(),"config.txt");
      if(Utility.isSet(fileName)) {
    	  FileInputStream configIS = new FileInputStream(fileName);
          configProps.load(configIS);
          String xpedxCols = configProps.getProperty("PrintOnHandReqQtyCols");
    	  if("true".equals(xpedxCols)){
    		  printXpedxCols = true;
    	  }
      }

      isPhysicalCountSheet = pInventoryOnly;

      initColumnsAdnWidths(sForm.getAppUser());
      //loop through the items to check if the footer needs to have the pack
      //Disclaimer printed on each page.
      boolean
        printProblemPackDisclaimer = false;
      boolean  invItems = false, invAutoOrderItems = false;
      //  nonInvItems = false, addNewPage = false;
     /*
      for (int i = 0; i < pItems.size(); i++) {
        ShoppingCartItemData sci = (ShoppingCartItemData) pItems.get(i);
        if (sci.getProduct().isPackProblemSku()) {
          printProblemPackDisclaimer = true;
        }
        if (sci.getIsaInventoryItem()) {
          invItems = true;
          if (null != mSiteData &&
              mSiteData.isAnInventoryAutoOrderItem
              (sci.getProduct().getProductId())) {
            invAutoOrderItems = true;
          }
        }
        //else {
        //  nonInvItems = true;
        //  if (invItems) addNewPage = true;
        //}

      }
     */
      Phrase headPhrase = new Phrase(makeChunk("", heading, true));
      if (catalogFl) {
        String catalogStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.catalog", null);
        headPhrase.add(makeChunk(catalogStr, heading, true));
      } else {
        if (sForm.getAppUser().getSite() != null
            && sForm.getAppUser()
            .getSite().getBSC() != null
            && sForm.getAppUser()
            .getSite().getBSC().getBusEntityData() != null
            && sForm.getAppUser()
            .getSite().getBSC().getBusEntityData().getShortDesc() != null
          ) {
          String subname = sForm.getAppUser().getSite().
                           getBSC().getBusEntityData().getShortDesc() + " ";
          headPhrase.add(makeChunk(subname, heading, true));
          headPhrase.add(makeChunk(" ", heading, true));
        }
        String orderGuideStr =
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.orderGuide", null);
        headPhrase.add(makeChunk(orderGuideStr, heading, true));
        headPhrase.add(makeChunk(" ", heading, true));
        headPhrase.add(makeChunk
                       (sForm.getAppUser().
                        getUserAccount().getBusEntity().getShortDesc(),
                        heading, true)
          );
      }
      AccountData account = sForm.getAppUser().getUserAccount();

      HeaderFooter header = new HeaderFooter(headPhrase, true);
      header.setAlignment(HeaderFooter.ALIGN_RIGHT);

      //setup the document
      // pageSize,  marginLeft, marginRight,  marginTop, marginBottom
      Document document = new Document(PageSize.A4, 10, 15, 30, 15);
      PdfWriter writer = PdfWriter.getInstance(document, pOut);

      String addr = pStore.getPrimaryAddress().getAddress1() + " " +
                    pStore.getPrimaryAddress().getAddress2();
      Chunk disclaimer = null;
      String fmsg = "";
      //if (invItems) {
      //  fmsg += ClwI18nUtil.getMessage(getRequest(), "shop.og.text.i-inventoryItem", null) + " ";
      //}
      //if (invAutoOrderItems) {
      //  fmsg += ClwI18nUtil.getMessage(getRequest(), "shop.og.text.a-autoOrderItem", null) + " ";
      //}
      if (printProblemPackDisclaimer) {
        fmsg +=
          ClwI18nUtil.getMessage(getRequest(), "shop.og.text.*packAndOrUomMayDifferByRegion", null);
      }
      if (fmsg.length() > 0) {
        disclaimer = makeChunk(fmsg, smallItalic, true);
      }

      Phrase footPhrase = makeStoreFooter(pStore, disclaimer, null);

      HeaderFooter footer = new HeaderFooter(footPhrase, false);
      footer.setAlignment(HeaderFooter.ALIGN_CENTER);

      //setup the borders from the header
      header.setBorder(borderType);
      footer.setBorder(HeaderFooter.TOP);

      document.setHeader(header);
      document.setFooter(footer);
      document.open();
      String vocMessage = ClwI18nUtil.getMessage(getRequest(), "shop.message.vocNotIncluded", null);
      document.add(makePhrase(vocMessage, smallHeading, true));
      document.add(makePhrase(null, smallHeading, true));
      drawOGHeader(document, sForm, pImageName, personal);
//      if (invItems) {
        drawHeader(document, pageNumber,
                   pStore.getStoreBusinessName().getValue(),
                   pImageName, true,account);
        document.add(makeBlankLine());
        String prevCat = null;
        for (int i = 0; i < pItems.size(); i++) {
          ShoppingCartItemData sci = (ShoppingCartItemData) pItems.get(i);
          //if (sci.getIsaInventoryItem() == false) {
          //  continue;
          //}
          //if (sci.getInventoryParValuesSum() <= 0) {
            // This is an inventory item that is not
            // allowed for this location.
          //  continue;
          //}
          Table itmTable = makeItemElement(sci,account);

          //if the item data will not fit onto the page,
          //make a new page, and redraw the header.
          String costCenterName = sci.getProduct().getCostCenterName();
          if(!sForm.isGroupByCostCenter()){
        	  costCenterName = sci.getCategoryName();
          }
          if (writer.fitsPage(itmTable, document.bottomMargin() + 10)) {
            if (null == prevCat ||!prevCat.equals(costCenterName)) {
                document.add(makePhrase(costCenterName,smallHeading, true));
            }
              prevCat = costCenterName;
          }

          if (!writer.fitsPage(itmTable, document.bottomMargin() + 10)) {
            document.newPage();
            pageNumber = pageNumber + 1;
            drawHeader(document, pageNumber,
                       pStore.getStoreBusinessName().getValue(),
                       pImageName, true, account);
            document.add(makeBlankLine());
            document.add(makePhrase(costCenterName,
                                      smallHeading, true));
            prevCat = costCenterName;
          }
          document.add(itmTable);
        }

      //close out the document
      document.close();
    } catch (DocumentException e) {
      e.printStackTrace();
      throw new IOException(e.getMessage());
    }
  }


  private void initColumnsAdnWidths(CleanwiseUser appUser) {
    mShowPrice = appUser.getShowPrice();
    AccountData account = appUser.getUserAccount();
    String showSize = getRequest().getParameter("showSize");
    if (Utility.isSet(showSize) && !Utility.isTrue(showSize)) {
      mShowSize = false;
    }
    if (!catalogFl) {
      mColumnCount++;
    }

    if(printXpedxCols){
    	if(isPhysicalCountSheet){
    		mColumnCount = mColumnCount+1;
    	}else{
    		mColumnCount = mColumnCount+2;
    	}
    }

    if (mShowSize) {
      mColumnCount++;
    }
    if (mShowPrice) {
      mColumnCount++;
    }

    	if(!account.isShowSPL()){
    		mColumnCount --;
    	}
    itmColumnWidth = new int[mColumnCount];
    int i = 0;

    if(printXpedxCols){
    	if(account.isShowSPL()){
	    	itmColumnWidth[i++] = 5;
	    	itmColumnWidth[i++] = 5;
	    	if(!isPhysicalCountSheet){
	    		itmColumnWidth[i++] = 6;
	    	}
	    	itmColumnWidth[i++] = 10;
	    	itmColumnWidth[i++] = 32;
	    	if (mShowSize) {
	    		itmColumnWidth[i++] = 10;
	    	}
	    	if (mShowPrice) {
	    		itmColumnWidth[i++] = 12;
	    	}
	    	itmColumnWidth[i++] = 8;
    	}else{
    		itmColumnWidth[i++] = 5;
	    	itmColumnWidth[i++] = 5;
	    	if(!isPhysicalCountSheet){
	    		itmColumnWidth[i++] = 11;
	    	}
	    	itmColumnWidth[i++] = 10;
	    	itmColumnWidth[i++] = 35;
	    	if (mShowSize) {
	    		itmColumnWidth[i++] = 10;
	    	}
	    	if (mShowPrice) {
	    		itmColumnWidth[i++] = 12;
	    	}
    	}
    }else{
	    if (!catalogFl) {
	    	if(account.isShowSPL()){
		      itmColumnWidth[i++] = 8;
		      itmColumnWidth[i++] = 8;
		      itmColumnWidth[i++] = 10;
		      itmColumnWidth[i++] = 32;
		      if (mShowSize) {
		        itmColumnWidth[i++] = 10;
		      }
		      if (mShowPrice) {
		        itmColumnWidth[i++] = 12;
		      }
		      itmColumnWidth[i++] = 8;
	    	}else{
	    	  itmColumnWidth[i++] = 8;
	  	      itmColumnWidth[i++] = 8;
	  	      itmColumnWidth[i++] = 18;
	  	      itmColumnWidth[i++] = 32;
	  	      if (mShowSize) {
	  	        itmColumnWidth[i++] = 10;
	  	      }
	  	      if (mShowPrice) {
	  	        itmColumnWidth[i++] = 12;
	  	      }
	    	}
	    } else {
	    	if(account.isShowSPL()){
		      itmColumnWidth[i++] = 10;
		      itmColumnWidth[i++] = 12;
		      itmColumnWidth[i++] = 36;
		      if (mShowSize) {
		        itmColumnWidth[i++] = 20;
		      }
		      if (mShowPrice) {
		        itmColumnWidth[i++] = 12;
		      }
		      itmColumnWidth[i++] = 3;
	    	}else{
	    		itmColumnWidth[i++] = 13;
			      itmColumnWidth[i++] = 12;
			      itmColumnWidth[i++] = 36;
			      if (mShowSize) {
			        itmColumnWidth[i++] = 20;
			      }
			      if (mShowPrice) {
			        itmColumnWidth[i++] = 12;
			      }

	    	}
	    }
    }
  }

}
