/*
 * PdfOrderGuide.java
 *
 * Created on July 15, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;

import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.i18n.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.service.api.value.OrderItemData;
import com.cleanwise.service.api.value.OrderItemDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.ContactUsInfo;


import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
//import com.lowagie.text.pdf.PdfWriter;
//import com.lowagie.text.pdf.PdfContentByte;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfPCell;

import java.awt.Color; //used by the lowagie/iText api, we are not actually using the awt toolkit here
import java.util.Locale;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Calendar;
import java.io.OutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import javax.servlet.http.HttpServletRequest;

/**
 *
 *Constructs a pdf document in the form of a pdf document.  It
 *understands the various propriatary objects, and uses them to
 *construct a purchase order document.
 * @author  bstevens
 */
public class PdfInvoiceDetail extends PdfSuper {
  //static configuration variables
  //configures the percentage widths of item columns in the document
  // private static int itmColumnWidth[] = {8,8,10,32,10,6,6,12,8};
  // private static int itmCatalogColumnWidth[] = {10,12,36,20,4,3,12,3};
  private static int halves[] = {62, 38};
  private static int sizes[] = {30, 70};
  private boolean catalogOnly = false;
  private Locale mCatalogLocale;
  private HttpServletRequest mRequest = null;
  private ClwI18nUtil mFormatter = null;
  private boolean mShowSize = true;
  private boolean mShowPrice = true;
  private int mColumnCount = 13;
  private int itmColumnWidth[];
  private Locale mLocale;

  /** Creates a new instance of PdfOrderGuide */
  public PdfInvoiceDetail() {
      super();
  }

  public PdfInvoiceDetail(HttpServletRequest pRequest, String pCatalogLocaleCd) {
    super();
    mCatalogLocale = new Locale(pCatalogLocaleCd);
    mRequest = pRequest;
    try {
      mFormatter = ClwI18nUtil.getInstance(pRequest, pCatalogLocaleCd, -1);
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
    } catch (Exception exc) {
      exc.printStackTrace();
    }

  }

  public void init (HttpServletRequest pRequest, String pCatalogLocaleCd) {
    mCatalogLocale = new Locale(pCatalogLocaleCd);
    mRequest = pRequest;
    try {
      mFormatter = ClwI18nUtil.getInstance(pRequest, pCatalogLocaleCd, -1);
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
    } catch (Exception exc) {
      exc.printStackTrace();
    }

  }

  //utility function to make an item Element.
  private Table makeItemElement(InvoiceNetworkServiceData pItm) throws DocumentException {

    Table itmTbl = new PTable(mColumnCount);
    itmTbl.setWidth(100);
    itmTbl.setWidths(itmColumnWidth);
    itmTbl.getDefaultCell().setBorderColor(java.awt.Color.black);
    itmTbl.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);
    //--- 0 --

    String t = "";
    if (t.length() > 0) t += " ";
    Cell tpc1 = new Cell (makePhrase(t + pItm.getLineNumber(), normal, true));
    itmTbl.addCell(tpc1);

    itmTbl.addCell(makePhrase(pItm.getQuantity(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getQuantityUnitOfMeasure(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getUnitPrice(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getExtendedPrice(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getProductNumber(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getProductName(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getPack(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getPackSize(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getManufacturerName(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getManufacturerProductNo(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getUpc(), normal, true));
    itmTbl.addCell(makePhrase(pItm.getDiscounts(), normal, true));


    return itmTbl;
  }


  private String mSkuTag;
  // controls the order qty or requested qty column
  private boolean mForInventoryShopping = false;

  private void drawPageHeader(Document document, int pPageNumber,  String pImageName) throws DocumentException {
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

//    itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest, "shop.og.table.header.qtyOnHand", null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.lineNumber",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.quantity",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.quantityUOM",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.unitPrice",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.extendedPrice",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.productNumber",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.productName",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.pack",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.packSize",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.manufName",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.manufNum",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.upc",null), itemHeading, false));
     itemHeader.addCell(makePhrase(ClwI18nUtil.getMessage(mRequest,"invoice.text.discounts",null), itemHeading, false));

     document.add(itemHeader);

  }

  private void drawDocHeader(Document document, CustAcctMgtInvoiceDetailForm sForm,
                            String pImageName) throws DocumentException {

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


    PdfPTable invoice = new PdfPTable(2);

    // Invoice header
    PdfPTable invHeader1 = new PdfPTable(2);


//    invHeader1.addCell(makePhrase("Distribution Center No: ", smallHeading, false));
    invHeader1.addCell(makePhrase((ClwI18nUtil.getMessage(mRequest,"invoice.text.distributionCenterNo",null) + ": "), smallHeading, false));
    invHeader1.addCell(makePhrase(sForm.getDistributionCenterNo(), smallHeading, true));

    invHeader1.addCell(makePhrase((ClwI18nUtil.getMessage(mRequest,"invoice.text.distributionCenterName",null)+ ": "), smallHeading, false));
    invHeader1.addCell(makePhrase(sForm.getDistributionCenterName(), smallHeading, true));

    invHeader1.addCell(makePhrase((ClwI18nUtil.getMessage(mRequest,"invoice.text.accountNumber",null)+ ": "), smallHeading, false));
    invHeader1.addCell(makePhrase(sForm.getAccountNumber(), smallHeading, true));

    invHeader1.addCell(makePhrase((ClwI18nUtil.getMessage(mRequest,"invoice.text.accountName",null)+ ": "), smallHeading, false));
    invHeader1.addCell(makePhrase(sForm.getAccountName(), smallHeading, true));

    PdfPTable invHeader2 = new PdfPTable(2);

    mLocale = Utility.parseLocaleCode("en_US");
    DateFormat lDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, mLocale);

    invHeader2.addCell(makePhrase((ClwI18nUtil.getMessage(mRequest,"invoice.text.invoiceDate",null) +": "), smallHeading, false));
    invHeader2.addCell(makePhrase(lDateFormatter.format(sForm.getInvoiceDate()), smallHeading, true));

    invHeader2.addCell(makePhrase((ClwI18nUtil.getMessage(mRequest,"invoice.text.invoiceType",null) + ": "), smallHeading, false));
    invHeader2.addCell(makePhrase(sForm.getInvoiceType(), smallHeading, true));

    invHeader2.addCell(makePhrase((ClwI18nUtil.getMessage(mRequest,"invoice.text.taxAmount",null) + ": "), smallHeading, false));
    invHeader2.addCell(makePhrase(sForm.getTaxAmount(), smallHeading, true));

    invHeader2.addCell(makePhrase((ClwI18nUtil.getMessage(mRequest,"invoice.text.freightCharges",null) + ": ") , smallHeading, false));
    invHeader2.addCell(makePhrase(sForm.getFreightCharges(), smallHeading, true));

    PdfPTable wholeTable = new PdfPTable(2);
    wholeTable.setWidthPercentage(100);

    wholeTable.setWidths(halves);
    wholeTable.getDefaultCell().setBorder(borderType);

    wholeTable.addCell(invHeader1);
    wholeTable.addCell(invHeader2);
    document.add(wholeTable);


  }

   //we keep track of the page number rather than using the
  //built in pdf functionality as we have to maintain pages
  //between pos, so we may be on the 3rd po, but on the
  //first page of that po, and on the 9th page of the pdf.
  private int pageNumber = 1;

  //main worker method.  The public methods will call this method
  //to generate the pdf after the object has been converted.
  private SiteData mSiteData = null;

  public void generatePdf(CustAcctMgtInvoiceDetailForm sForm, List pItems, StoreData pStore, OutputStream pOut, String pImageName) throws IOException {
//    mSkuTag = ClwI18nUtil.getMessage(mRequest, "shop.og.text.ourSkuNum", null);

    try {
      initColumnsAdnWidths();
 //     mSiteData = sForm.getAppUser().getSite();
      //loop through the items to check if the footer needs to have the pack
      //Disclaimer printed on each page.
      boolean addNewPage = false;



      Phrase headPhrase = new Phrase(makeChunk("", heading, true));
      //String orderGuideStr =  ClwI18nUtil.getMessage(mRequest, "shop.og.text.orderGuide", null);
      headPhrase.add(makeChunk(ClwI18nUtil.getMessage(mRequest,"invoice.text.details",null), heading, true));
      headPhrase.add(makeChunk(" ", heading, true));
      headPhrase.add(makeChunk(ClwI18nUtil.getMessage(mRequest,"invoice.text.invoiceNumber",null) + ": "+ sForm.getInvoiceNumber(), heading, true));

      Phrase footPhrase = makeStoreFooter(pStore, null, null);

      HeaderFooter header = new HeaderFooter(headPhrase, true);
      header.setAlignment(HeaderFooter.ALIGN_RIGHT);

      //setup the document
      // pageSize,  marginLeft, marginRight,  marginTop, marginBottom
      Document document = new Document(PageSize.A4, 10, 15, 30, 15);
      PdfWriter writer = PdfWriter.getInstance(document, pOut);

      HeaderFooter footer = new HeaderFooter(footPhrase, false);
      footer.setAlignment(HeaderFooter.ALIGN_CENTER);

      //setup the borders from the header
      header.setBorder(borderType);
      footer.setBorder(HeaderFooter.TOP);

      document.setHeader(header);
      document.setFooter(footer);
      document.open();

      drawDocHeader(document, sForm, pImageName);
      drawPageHeader(document, pageNumber, pImageName);

        String prevCat = null;
        for (int i = 0; i < pItems.size(); i++) {
          InvoiceNetworkServiceData detailItem = (InvoiceNetworkServiceData) pItems.get(i);
          Table itmTable = makeItemElement(detailItem);

          //if the item data will not fit onto the page,
          //make a new page, and redraw the header.

          if (!writer.fitsPage(itmTable, document.bottomMargin() + 10)) {
            document.newPage();
            pageNumber = pageNumber + 1;
            drawPageHeader(document, pageNumber, pImageName);
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


  private void initColumnsAdnWidths() {

    itmColumnWidth = new int[mColumnCount];
    int i = 0;

     itmColumnWidth[i++] = 4;
     itmColumnWidth[i++] = 7;
     itmColumnWidth[i++] = 5;
     itmColumnWidth[i++] = 8;
     itmColumnWidth[i++] = 8;
     itmColumnWidth[i++] = 8;
     itmColumnWidth[i++] = 10;
     itmColumnWidth[i++] = 6;
     itmColumnWidth[i++] = 4;
     itmColumnWidth[i++] = 13;
     itmColumnWidth[i++] = 9;
     itmColumnWidth[i++] = 17;
     itmColumnWidth[i++] = 6;


  }

}
