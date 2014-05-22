/*
 * PdfOrder.java
 *
 * Created on July 15, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;

import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;


import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.*;

import java.awt.Color; //used by the lowagie/iText api, we are not actually using the awt toolkit here
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.OutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.text.DateFormat;
import javax.servlet.http.HttpServletRequest;

/**
 *Constructs a pdf document in the form of a pdf document.  It understands the various
 *propriatary objects, and uses them to construct a purchase order document.
 * @author  bstevens
 */
public class PdfOrderStatusSwiss extends PdfOrderStatus {

    //static configuration variables
    //configures the percentage widths of item columns in the document
    private static int thirds[] = {33,34,33};
    private static int halves[] = {50,50};
    private static int subHeaderWidths[] = {20,20,60};
    //private NumberFormat currencyFormatter;
    private Locale mOrderLocale;
    private String mOrderLocaleCd;
    private HttpServletRequest mRequest = null;
    private ClwI18nUtil mFormatter = null;
    private boolean mShowSize = true;
    private boolean mShowMfg = true;
    private boolean mShowMfgSku = true;
    private boolean mShowPar = true;
    private int[] itmColumnWidth;

    private static int SKU_COLUMN_WDTH_DEFAULT = 6;
    private static int MFG_SKU_COLUMN_WDTH_DEFAULT = 7;

    public int[] getItemColomnWidths() {
        return itmColumnWidth;
    }


    public int getSkuColumnLength(CleanwiseUser appUser, OrderItemDescDataVector items, StoreData pStore){
        return SKU_COLUMN_WDTH_DEFAULT;
    }

    public int getMfgSkuColumnLength(CleanwiseUser appUser, OrderItemDescDataVector items, StoreData pStore){
        return MFG_SKU_COLUMN_WDTH_DEFAULT;
    }

    public void setItemColomnWidths(CleanwiseUser appUser, OrderItemDescDataVector items, StoreData pStore){
      int count = getNumberOfColumns(appUser);
      //int itmColumnWidth[] = new int[count];
      itmColumnWidth = new int[count];

      int i = 0;
      if (mShowPar) {
        itmColumnWidth[i++] = 4; //Par
      }
      itmColumnWidth[i++] = 5; //On Hand
      itmColumnWidth[i++] = getSkuColumnLength(appUser, items, pStore); //Our Sku#
      itmColumnWidth[i++] = 16; //Name
      if (mShowSize) {
        itmColumnWidth[i++] = 7; //Size
      }
      //itmColumnWidth[i++] = 5;//Pack
      //itmColumnWidth[i++] = 4;//Uom
      /*if (mShowMfg) {
        itmColumnWidth[i++] = 14; //Manufacturer
      }
      if (mShowMfgSku) {
        itmColumnWidth[i++] = getMfgSkuColumnLength(appUser, items, pStore); //Mfg.Sku#
      } */
      itmColumnWidth[i++] = 12;//Price
      itmColumnWidth[i++] = 4;//Order Qty
      if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING)) {
        itmColumnWidth[i++] = 4;//Recvd Qty
      }
      itmColumnWidth[i++] = 9;//Extended Price
                               //Status
      if (appUser.getUserAccount().isShowSPL()) {
        itmColumnWidth[i++] = 4;//SPL
      }
    }

      private int[] getServiceColomnWidths(){

      int itmColumnWidth[] = new int[5];
      itmColumnWidth[0] = 25; //asset name
      itmColumnWidth[1] = 15; //asset number
      itmColumnWidth[2] = 15; //asset serial number
      itmColumnWidth[3] = 35; // service name
      itmColumnWidth[4] = 10; //price
      return itmColumnWidth;
    }

    /** Creates a new instance of PdfOrder */
    public PdfOrderStatusSwiss() {
      super();
    }
    public PdfOrderStatusSwiss(HttpServletRequest pRequest, String pOrderLocaleCd) {
      super();
      init(pRequest, pOrderLocaleCd);
    }

    public void init(HttpServletRequest pRequest, String pOrderLocaleCd) {
    	if(!Utility.isSet(pOrderLocaleCd)){
    		mOrderLocaleCd = "fr_CH";
    	}else{
    	    mOrderLocaleCd = pOrderLocaleCd;
    	}
  		mOrderLocale = new Locale(pOrderLocaleCd);
        mRequest = pRequest;
        try {
          mFormatter = ClwI18nUtil.getInstance(pRequest,pOrderLocaleCd,-1);
        } catch(Exception exc) {};
        if(mFormatter==null) {
          try {
            mFormatter = ClwI18nUtil.getInstance(pRequest,"fr_CH",-1);
          } catch(Exception exc) {};
        }

        try {
          initFontsUnicode();
        } catch(Exception exc) {}
        
    }

    public Table makeSubHeader(OrderItemDescData oidD) throws Exception{
    	int numberOfCols = 3;
        Table table = new PTable(numberOfCols);

        table.setWidth(100);
        table.getDefaultCell().setBorderColor(java.awt.Color.black);
        table.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

        Phrase p;
        String distStr = ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.distributor",null);
        p=makePhrase(distStr,normalBold,true);

        String distName = oidD.getDistRuntimeDisplayName();
        if(distName != null){
        	p.add(makePhrase(SPACE + distName,normal,true));
        }
        table.addCell(p);

        String  purchOrderStr = ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.purchaseOrder",null);
        p=makePhrase(purchOrderStr,normalBold,true);

        String poNum = oidD.getOrderItem().getErpPoNum();
        if(poNum != null){
          p.add(makePhrase(SPACE + poNum,normal,true));
        }
        table.addCell(p);

        Phrase pCharges= new Phrase();
        //freight
        String frtName=null;
        BigDecimal frtAmt = new BigDecimal(0);
        if(oidD.getOrderFreight() != null){
        	frtName = oidD.getOrderFreight().getShortDesc();
        	frtAmt = oidD.getOrderFreight().getAmount();
        }
        if(frtName != null){
        	pCharges.add(makePhrase(frtName,normalBold,false));
            if(frtAmt != null){
            	pCharges.add(makePhrase(SPACE + mFormatter.priceFormat(frtAmt,SPACE),normal,true));
            }

        }

        //discount
        String discountName=null;
        BigDecimal discountAmt = new BigDecimal(0);
        if(oidD.getOrderDiscount() != null){
        	discountName = oidD.getOrderDiscount().getShortDesc();
        	discountAmt = oidD.getOrderDiscount().getAmount();
        }
        if(discountName != null){
        	pCharges.add(makePhrase(discountName,normalBold,false));
            if(discountAmt != null){
            	pCharges.add(makePhrase(SPACE + mFormatter.priceFormat(discountAmt,SPACE),normal,true));
            }

        }

        //fuel surcharge
        String fsName=null;
        BigDecimal fsAmt = new BigDecimal(0);
        if(oidD.getOrderFuelSurcharge() != null){
        	fsName = oidD.getOrderFuelSurcharge().getShortDesc();
        	fsAmt = oidD.getOrderFuelSurcharge().getAmount();
        }
        if(fsName != null){
        	pCharges.add(makePhrase(fsName,normalBold,false));
            if(fsAmt != null){
            	pCharges.add(makePhrase(SPACE + mFormatter.priceFormat(fsAmt,SPACE),normal,true));
            }
        }

        //small order fee
        String sofName=null;
        BigDecimal sofAmt = new BigDecimal(0);
        if(oidD.getOrderSmallOrderFee() != null){
        	sofName = oidD.getOrderSmallOrderFee().getShortDesc();
        	sofAmt = oidD.getOrderSmallOrderFee().getAmount();
        }
        if(sofName != null){
        	pCharges.add(makePhrase(sofName,normalBold,false));
            if(sofAmt != null){
            	pCharges.add(makePhrase(SPACE + mFormatter.priceFormat(sofAmt,SPACE),normal,true));
            }

        }
        table.addCell(pCharges);
        return table;
    }

    //utility function to make a sub header
    public Table makeSubHeader(String distName,String poNum, String frieghtInfName, BigDecimal frieghtInfAmt)
    throws Exception{
    	int numberOfCols = 3;
        Table table = new PTable(numberOfCols);

        table.setWidth(100);
        //table.setWidths(subHeaderWidths);
        table.getDefaultCell().setBorderColor(java.awt.Color.black);
        table.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

        Phrase p;
        String distStr = ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.distributor",null);
        p=makePhrase(distStr,normalBold,true);
        if(distName != null){
          p.add(makePhrase(SPACE + distName,normal,true));
        }
        table.addCell(p);
        String  purchOrderStr = ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.purchaseOrder",null);
        p=makePhrase(purchOrderStr,normalBold,true);
        if(poNum != null){
          p.add(makePhrase(SPACE + poNum,normal,true));
        }
        table.addCell(p);
        if(frieghtInfName != null){
          String  freightOptionStr = ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.freightOption",null);
          p = makePhrase(freightOptionStr,normalBold,false);
          p.add(makePhrase(SPACE + frieghtInfName,normal,true));
          String  amountStr = ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.amount",null);
          p.add(makePhrase(amountStr,normalBold,false));
          if(frieghtInfAmt != null){
            p.add(makePhrase(SPACE + mFormatter.priceFormat(frieghtInfAmt,SPACE),normal,true));
          }
          table.addCell(p);
        }else{
          table.addCell(makePhrase(SPACE,normal,true));
          //table.addCell(makePhrase(SPACE,normal,true));
          //table.addCell(makePhrase(SPACE,normal,true));
          //table.addCell(makePhrase(SPACE,normal,true));
        }

        return table;
    }

    //utility function to make an item Element.
    private Table makeItemElement
    (CleanwiseUser appUser, OrderItemDescData pOrderItemDesc,StoreData pStore)
    throws DocumentException{

        int numberOfCols = getNumberOfColumns(appUser);
        Table itmTbl = new PTable(numberOfCols);

        itmTbl.setWidth(100);
        itmTbl.setWidths(getItemColomnWidths());
        itmTbl.getDefaultCell().setBorderColor(java.awt.Color.black);
        itmTbl.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

        String par = "";
        String onHand = "";
        if(pOrderItemDesc.getIsAnInventoryItem()) {
          par = "i"+pOrderItemDesc.getOrderItem().getInventoryParValue();
          onHand = ""+pOrderItemDesc.getOrderItem().getInventoryQtyOnHand();
        }
        if (mShowPar) {
          itmTbl.addCell(makePhrase(par, normal, true));
        }
        itmTbl.addCell(makePhrase(onHand,normal,true));
        String sku = Utility.getActualSkuNumber(pStore.getStoreType().getValue(),pOrderItemDesc.getOrderItem());

        Cell skuCell = new Cell(makePhrase(""+sku,normal,true));
        //skuCell.setNoWrap(true);
        itmTbl.addCell(skuCell);
        //itmTbl.addCell(makePhrase(""+sku,normal,true));

        itmTbl.addCell(makePhrase(pOrderItemDesc.getOrderItem().getItemShortDesc(),normal,true));
        if (mShowSize) {
          itmTbl.addCell(makePhrase(pOrderItemDesc.getOrderItem().getItemSize(), normal, true));
        }
        //itmTbl.addCell(makePhrase(pOrderItemDesc.getOrderItem().getItemPack(),normal,true));
        //itmTbl.addCell(makePhrase(pOrderItemDesc.getOrderItem().getItemUom(),normal,true));
        /*if (mShowMfg) {
          itmTbl.addCell(makePhrase(pOrderItemDesc.getOrderItem().getManuItemShortDesc(), normal, true));
        }
        if (mShowMfgSku) {
            Cell mfgSkuCell = new Cell(makePhrase(pOrderItemDesc.getOrderItem().getManuItemSkuNum(), normal, true));
            //mfgSkuCell.setNoWrap(true);
            itmTbl.addCell(mfgSkuCell);
          //itmTbl.addCell(makePhrase(pOrderItemDesc.getOrderItem().getManuItemSkuNum(), normal, true));
        } */
        BigDecimal price = new BigDecimal(0.0);
        AccountData accountD = appUser.getUserAccount();
        String addServiceFee = accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
        if(addServiceFee.equals("true")){
    		BigDecimal thisFee = pOrderItemDesc.getOrderItem().getServiceFee();
    		if(thisFee!=null){
    			price = pOrderItemDesc.getOrderItem().getCustContractPrice().subtract(thisFee);
    		}else{
    			price = pOrderItemDesc.getOrderItem().getCustContractPrice();
    		}
    	}else{
    		price = pOrderItemDesc.getOrderItem().getCustContractPrice();
    	}
        //BigDecimal price = pOrderItemDesc.getOrderItem().getCustContractPrice();
        if(price == null){
          price = new BigDecimal(0);
        }
        String priceS = null;
        try {
          priceS = mFormatter.priceFormatWithoutCurrency(price);
        }catch(Exception exc) {
          exc.printStackTrace();
          NumberFormat cnf = NumberFormat.getCurrencyInstance(mOrderLocale);
          priceS = cnf.format(price.doubleValue());
        }
        itmTbl.addCell(makePhrase(priceS,normal,true));

        if(!RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
              equals(pOrderItemDesc.getOrderItem().getOrderItemStatusCd())) {
          int qty = pOrderItemDesc.getOrderItem().getTotalQuantityOrdered();
          itmTbl.addCell(makePhrase(""+qty,normal,true));
          if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING)){
              int qtyRecvd = pOrderItemDesc.getOrderItem().getTotalQuantityReceived();
              itmTbl.addCell(makePhrase(""+qtyRecvd,normal,true));
          }
          BigDecimal amount = price.multiply(new BigDecimal(qty));
          String amountS = null;
          try {
            amountS = mFormatter.priceFormatWithoutCurrency(amount);
          }catch(Exception exc) {
            exc.printStackTrace();
            NumberFormat cnf = NumberFormat.getCurrencyInstance(mOrderLocale);
            amountS = cnf.format(amount.doubleValue());
          }
          itmTbl.addCell(makePhrase(amountS,normal,true));
        } else {
          itmTbl.addCell(makePhrase("0",normal,true));
          itmTbl.addCell(makePhrase("Cancelled",normal,true));
        }
        // spl
        if (appUser.getUserAccount().isShowSPL()) {
          if (Utility.isTrue(pOrderItemDesc.getStandardProductList())) {
            String yStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.y", null);
            itmTbl.addCell(makePhrase(yStr, normal, true));
          } else {
            String nStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.n", null);
            itmTbl.addCell(makePhrase(nStr, normal, true));
          }
        }

        Iterator it = pOrderItemDesc.getOrderItemActionList().iterator();
        while(it.hasNext()){
            OrderItemActionData act = (OrderItemActionData) it.next();
            String actioncd =act.getActionCd();
            if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED.equals(actioncd)){
                actioncd = "Invoiced";
            }
            if(appUser.getUserAccount().getRuntimeDisplayOrderItemActionTypes().contains(act.getActionCd())){
                //the par value and inventory values should be blank to make it easier to use
                itmTbl.getDefaultCell().setBorder(borderType);
                int ct = 0;
                itmTbl.addCell("");
                ct++;
                itmTbl.addCell("");
                ct++;
                itmTbl.addCell("");
                ct++;
                //reset the border type to be outlined
                itmTbl.getDefaultCell().setBorder(Rectangle.BOX);
                itmTbl.addCell(makePhrase(actioncd,normal,false));
                ct++;

                //itmTbl.addCell("");
                //ct++;
                //itmTbl.addCell("");
                //ct++;
                itmTbl.addCell("");
                ct++;
                String lDate;
                if(act.getActionDate() == null){
                    lDate = "";
                }else{
                    lDate = ClwI18nUtil.formatDateInp(mRequest,act.getActionDate());
                }
                itmTbl.addCell(makePhrase(lDate,normal,false));
                ct++;
                //itmTbl.addCell("");
                //ct++;
                //itmTbl.addCell("");
                //ct++;
                itmTbl.addCell(makePhrase(act.getQuantity()+"",normal,false));
                ct++;
                //the par value and inventory values should be blank to make it easier to use
                itmTbl.getDefaultCell().setBorder(borderType);
                int itemColomnWidths[] = getItemColomnWidths();
                while(ct < itemColomnWidths.length){
                    itmTbl.addCell("");
                    ct++;
                }
            }
        }

        return itmTbl;
    }

    //utility function to make an service Element.
    private Table makeServiceElement(CleanwiseUser appUser,
                                         OrderItemDescData pOrderItemDesc,
                                         StoreData pStore)  throws DocumentException {

        int numberOfCols = 5;

        Table itmTbl = new PTable(numberOfCols);

        itmTbl.setWidth(100);
        itmTbl.setWidths(getServiceColomnWidths());
        itmTbl.getDefaultCell().setBorderColor(java.awt.Color.black);
        itmTbl.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

        String serviceName = "";
        String assetName = "";
        String assetNumber = "";
        String assetSerial = "";
        BigDecimal price = null;
        if (pOrderItemDesc.getItemInfo() != null) {
            serviceName = pOrderItemDesc.getItemInfo().getShortDesc();
        }
        if (pOrderItemDesc.getAssetInfo() != null) {
            assetName = pOrderItemDesc.getAssetInfo().getShortDesc();
            assetNumber = pOrderItemDesc.getAssetInfo().getAssetNum();
            assetSerial = pOrderItemDesc.getAssetInfo().getSerialNum();
        }
        if (pOrderItemDesc.getOrderItem() != null) {
            price = pOrderItemDesc.getOrderItem().getCustContractPrice();
        }
        itmTbl.addCell(makePhrase(assetName, normal, true));
        itmTbl.addCell(makePhrase(assetNumber, normal, true));
        itmTbl.addCell(makePhrase(assetSerial, normal, true));
        itmTbl.addCell(makePhrase(serviceName, normal, true));

        if (price == null) {
            price = new BigDecimal(0);
        }
        String priceS = null;
        try {
            priceS = mFormatter.priceFormat(price, " ");
        } catch (Exception exc) {
            exc.printStackTrace();
            NumberFormat cnf = NumberFormat.getCurrencyInstance(mOrderLocale);
            priceS = cnf.format(price.doubleValue());
        }
        itmTbl.addCell(makePhrase(priceS, normal, true));

        return itmTbl;
    }

    private void drawLogo(CleanwiseUser appUser, Document document,
                            int pPageNumber, String pImageName)
    throws DocumentException {
        //add the logo
        try
        {
            Image i = Image.getInstance(pImageName);
            float x = document.leftMargin();
            float y = document.getPageSize().getHeight() - i.getHeight() - 5;
            i.setAbsolutePosition(x,y);
            document.add(i);
        }
        catch (java.io.FileNotFoundException e) {
            //e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawHeader(CleanwiseUser appUser, Document document,
                            int pPageNumber, String pImageName)
    throws DocumentException {

       drawLogo(appUser,document, pPageNumber, pImageName);

        //deal with header info
        //construct the table/cells with all of the header information
        //must be the pdf specific type or the table will not use the full width of the page.
        //unfortuanately this means we are limited to PDF rendering only.

        //draw a line  XXX this is somewhat of a hack, as there seems to be no way to draw a line in the
        //current iText API that is relative to your current position in the document

        document.add(makeLine());

        //add the item header line

        int numberOfCols = getNumberOfColumns(appUser);
        PdfPTable itemHeader = new PdfPTable(numberOfCols);
        itemHeader.setWidthPercentage(100);
        itemHeader.setWidths(getItemColomnWidths());
        itemHeader.getDefaultCell().setBorderWidth(2);
        itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
        itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
        itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
        itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
        if (mShowPar) {
          String parStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.par", null);
          itemHeader.addCell(makePhrase(parStr, itemHeading, false));
        }
        String  onHandStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.onHand",null);
        itemHeader.addCell(makePhrase(onHandStr,itemHeading,false));
        String  ourSkuStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.ourSku#",null);
        itemHeader.addCell(makePhrase(ourSkuStr,itemHeading,false));
        String  productNameStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.product<sp>Name",null);
        itemHeader.addCell(makePhrase(productNameStr,itemHeading,false));
        if (mShowSize) {
          String sizeStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.size", null);
          itemHeader.addCell(makePhrase(sizeStr, itemHeading, false));
        }
        //String  packStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.pack",null);
        //itemHeader.addCell(makePhrase(packStr,itemHeading,false));
        //String  uomStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.uom",null);
        //itemHeader.addCell(makePhrase(uomStr,itemHeading,false));
        /*if (mShowMfg) {
          String mfgStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.mfg", null);
          itemHeader.addCell(makePhrase(mfgStr, itemHeading, false));
        }
        if (mShowMfgSku) {
          String mfgSkuStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.mfgSkuNum", null);
          itemHeader.addCell(makePhrase(mfgSkuStr, itemHeading, false));
        } */


        CurrencyData curr = ClwI18nUtil.getCurrency(mOrderLocaleCd);
        String currencyCode = "USD";
        if (curr != null) {
            currencyCode = curr.getGlobalCode();
        }

        String  priceStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.priceIn",new String[]{currencyCode});
        itemHeader.addCell(makePhrase(priceStr,itemHeading,false));
        String  ordQtyStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.ordQty",null);
        itemHeader.addCell(makePhrase(ordQtyStr,itemHeading,false));
        String  recQtyStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.recQty",null);
        if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING)){
            itemHeader.addCell(makePhrase(recQtyStr,itemHeading,false));
        }
        String  extendedPriceStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.extendedPriceIn",new String[]{currencyCode});
        itemHeader.addCell(makePhrase(extendedPriceStr,itemHeading,false));
        if (appUser.getUserAccount().isShowSPL()) {
          String splStr = ClwI18nUtil.getMessage(mRequest, "shoppingItems.text.spl", null);
          itemHeader.addCell(makePhrase(splStr, itemHeading, false));
        }
        document.add(itemHeader);
    }

    public void drawOrderHeader
    (Document document, CleanwiseUser appUser, OrderOpDetailForm sForm,
     String pImageName, OrderStatusDescData pOrderStatusDesc)
    throws DocumentException {

        //add the logo
        try
        {
            Image i = Image.getInstance(pImageName);
            float x = document.leftMargin();
            float y = document.getPageSize().getHeight() - i.getHeight() - 5;
            i.setAbsolutePosition(x,y);
            document.add(i);
        }
        catch (Exception e)
        {
            //e.printStackTrace();
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
        orderInfo.setWidthPercentage(100);
        orderInfo.setWidths(new int[] {20,80});
        orderInfo.getDefaultCell().setBorder(borderType);
        orderInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        String  orderNumberStr = ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.orderNumber:",null);
        orderInfo.addCell(makePhrase(orderNumberStr+" ",normal,false));
        OrderData orderD = pOrderStatusDesc.getOrderDetail();
        String orderNumber = orderD.getOrderNum();
        orderInfo.addCell(makePhrase(orderNumber,normal,true));

        OrderData consolidatedToOrderD = pOrderStatusDesc.getConsolidatedOrder();
        if(consolidatedToOrderD!=null) {
          String  consolidatedToOrderStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.consolidatedToOrder:",null);
          orderInfo.addCell(makePhrase(consolidatedToOrderStr+" ",normal,false));
          String consolidatedByOrderNumber = consolidatedToOrderD.getOrderNum();
          orderInfo.addCell(makePhrase(consolidatedByOrderNumber,normal,true));

        }

        OrderData refOrderD = pOrderStatusDesc.getRefOrder();
        if(refOrderD!=null){
          String refOrderNum = refOrderD.getOrderNum();
          String  referenceOrderNumStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.referenceOrderNum:",null);
          orderInfo.addCell(makePhrase(referenceOrderNumStr+" ",normal,false));
          orderInfo.addCell(makePhrase(refOrderNum,normal,true));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        //String dateS = simpleDateFormat.format(orderD.getOriginalOrderDate());
        Date date = orderD.getOriginalOrderDate();
        Date time = orderD.getOriginalOrderTime();
        Date orderDate = Utility.getDateTime(date, time);
        TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());

        //String dateS = ClwI18nUtil.formatDateInp(mRequest,orderD.getOriginalOrderDate());
        String dateS = ClwI18nUtil.formatDateInp(mRequest, orderDate, timeZone.getID() );
        String  orderDateStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.orderDate:",null);
        orderInfo.addCell(makePhrase(orderDateStr+" ",normal,false));
        orderInfo.addCell(makePhrase(dateS,normal,true));

        //String currentDateS = simpleDateFormat.format(new Date());
        String currentDateS = ClwI18nUtil.formatDateInp(mRequest,new Date(),timeZone.getID());
        String  currentDateStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.currentDate:",null);
        orderInfo.addCell(makePhrase(currentDateStr+" ",normal,false));
        orderInfo.addCell(makePhrase(currentDateS,normal,true));

        String reqShipDateSU = pOrderStatusDesc.getRequestedShipDate();
        if ( reqShipDateSU != null && reqShipDateSU.trim().length() > 0 ) {
            String reqShipDateS = null;
            try {
              Date reqShipDate = sdf.parse(reqShipDateSU);
              reqShipDateS = ClwI18nUtil.formatDateInp(mRequest,reqShipDate);
            } catch (Exception exc){
              exc.printStackTrace();
              reqShipDateS = reqShipDateSU;
            }
           String  requestedShipDateStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.requestedShipDate:",null);
            orderInfo.addCell(makePhrase(requestedShipDateStr+" ",normal,false));
            orderInfo.addCell(makePhrase(reqShipDateS,normal,true));
        }

        String  poNumberStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.poNumber:",null);
        orderInfo.addCell(makePhrase(poNumberStr+" ",normal,false));
        orderInfo.addCell(makePhrase(orderD.getRequestPoNum(),normal,true));

        String  methodStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.method:",null);
        orderInfo.addCell(makePhrase(methodStr+" ",normal,false));
        orderInfo.addCell(makePhrase(orderD.getOrderSourceCd(),normal,true));


        String  processOrderOnStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.processOrderOn:",null);
        String processOnSU = pOrderStatusDesc.getPendingDate();
        if(Utility.isSet(processOnSU)) {
          String processOnS = null;
          try {
            Date processOn = sdf.parse(processOnSU);
            processOnS = ClwI18nUtil.formatDateInp(mRequest,processOn);
          } catch (Exception exc){
            exc.printStackTrace();
            processOnS = processOnSU;
          }
          orderInfo.addCell(makePhrase(processOrderOnStr,normal,false));
          orderInfo.addCell(makePhrase(processOnS,normal,true));
        }

        String  contactNameStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.contactName:",null);
        orderInfo.addCell(makePhrase(contactNameStr+" ",normal,false));
        orderInfo.addCell(makePhrase(orderD.getOrderContactName(),normal,true));

        // STJ-4606 do not print phone and email for inventory orders
        if (!orderD.getOrderSourceCd().equals(RefCodeNames.ORDER_SOURCE_CD.INVENTORY)) {
            String  contactPhoneStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.contactPhone:",null);
            orderInfo.addCell(makePhrase(contactPhoneStr+" ",normal,false));
            orderInfo.addCell(makePhrase(orderD.getOrderContactPhoneNum(),normal,true));

            String  contactEmailStr =
                  ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.contactEmail:",null);
            orderInfo.addCell(makePhrase(contactEmailStr+" ",normal,false));
            orderInfo.addCell(makePhrase(orderD.getOrderContactEmail(),normal,true));
        }
        String  placedByStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.placedBy:",null);
        orderInfo.addCell(makePhrase(placedByStr+" ",normal,false));
        if(Utility.isSet(sForm.getOrderPlacedBy())){
            StringBuffer placedBy = new StringBuffer();
            placedBy.append(sForm.getOrderPlacedBy());
            placedBy.append(" (");
            placedBy.append(orderD.getAddBy());
            placedBy.append(")");
            orderInfo.addCell(makePhrase(placedBy.toString(),normal,true));
        }else{
            orderInfo.addCell(makePhrase(orderD.getAddBy(),normal,true));
        }
        String  commentsStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.comments:",null);
        orderInfo.addCell(makePhrase(commentsStr+" ",normal,false));
        orderInfo.addCell(makePhrase(orderD.getComments(),normal,true));

    }


    private Table makeSummary(OrderOpDetailForm sForm, CleanwiseUser appUser) throws Exception{
        //one large table
        Table resTbl = new PTable(1);
        resTbl.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        resTbl.setWidth(100);

        Table summTbl = new PTable(3);
        summTbl.setWidth(100);
        summTbl.setWidths(thirds);
        //        summTbl.setBorder(borderType);

        //totals table
        Table totals = new PTable(2);
        totals.setWidths(halves);
        totals.getDefaultCell().setBorder(borderType);
        totals.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_LEFT);

        String  summaryStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.summary",null);
        totals.addCell(makePhrase(summaryStr+" ", smallHeading, false));
        totals.addCell(makePhrase("", smallHeading, false));

        String  subtotalStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.subtotal:",null);
        totals.addCell(makePhrase(subtotalStr+" ", normal, false));
        double subTotal = sForm.getSubTotal();
        String subTotalS = null;
        subTotalS = mFormatter.priceFormat(new Double(subTotal)," ");
        totals.addCell(makePhrase(subTotalS, normal, false));

        BigDecimal serviceFee = sForm.getServiceFeeAmt();
        if(serviceFee!=null && serviceFee.compareTo(new BigDecimal(0.0))>0){
        	String  serviceFeeStr ="Service Fee:";
        	totals.addCell(makePhrase(serviceFeeStr+" ", normal, false));

        	String serviceFeeS = null;
        	serviceFeeS = mFormatter.priceFormat(serviceFee," ");
        	totals.addCell(makePhrase(serviceFeeS, normal, false));
        }

        OrderStatusDescData orderStatusDesc = sForm.getOrderStatusDetail();
        OrderData orderD = orderStatusDesc.getOrderDetail();

        String orderType = orderD.getOrderTypeCd();
        boolean replacedOrderFl =
         (RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderType))?
         true:false;

        OrderData consolidatedToOrderD = orderStatusDesc.getConsolidatedOrder();
        boolean freightHandlFl = false;
        BigDecimal freight = sForm.getTotalFreightCost();
        if (freight!=null && freight.abs().doubleValue()>0.001 &&
            consolidatedToOrderD==null) {
          String  freightStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.freight:",null);
          totals.addCell(makePhrase(freightStr+" ", normal, false));
          freightHandlFl = true;
          if(!replacedOrderFl) {
            String freightS = null;
            freightS = mFormatter.priceFormat(freight,SPACE);
            totals.addCell(makePhrase(freightS, normal, false));
          } else {
            totals.addCell(makePhrase("*", normal, false));
          }
        }


        BigDecimal miscCost = sForm.getTotalMiscCost();
        if (miscCost!=null && miscCost.abs().doubleValue()>0.001 &&
            consolidatedToOrderD==null) {
          String  handlingStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.handling:",null);
          totals.addCell(makePhrase(handlingStr+" ", normal, false));
          freightHandlFl = true;
          if(!replacedOrderFl) {
            String miscCostS = null;
            miscCostS = mFormatter.priceFormat(miscCost,SPACE);

            totals.addCell(makePhrase(miscCostS, normal, false));
          } else {
            totals.addCell(makePhrase("*", normal, false));
          }
        }

        BigDecimal smallOrderFeeBD = sForm.getSmallOrderFeeAmt();
        if (smallOrderFeeBD != null && smallOrderFeeBD.doubleValue() > 0) {
            String smallOrderFeeStr = ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.smallOrderFee:", null);
            totals.addCell(makePhrase(smallOrderFeeStr + " ", normal, false));
            totals.addCell(makePhrase(mFormatter.priceFormat(smallOrderFeeBD, SPACE), normal, false));
        }

        BigDecimal fuelSurchargeBD = sForm.getFuelSurchargeAmt();
        if (fuelSurchargeBD != null && fuelSurchargeBD.doubleValue() > 0) {
            String fuelSurchargeStr = ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.fuelSurcharge:", null);
            totals.addCell(makePhrase(fuelSurchargeStr + " ", normal, false));
            totals.addCell(makePhrase(mFormatter.priceFormat(fuelSurchargeBD, SPACE), normal, false));
        }


        OrderMetaData oDiscount = sForm.getOrderStatusDetail().getMetaObject(RefCodeNames.CHARGE_CD.DISCOUNT);
        if (oDiscount != null) {
            BigDecimal discountBD;
            try {
             discountBD = new BigDecimal(oDiscount.getValue());
            } catch (Exception e) {
             discountBD = new BigDecimal(0);
            }
            if (discountBD.abs().doubleValue() > 0) {
                if (discountBD.doubleValue() > 0) {
                    discountBD.negate();
                }
                String discountStr = ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.discount:", null);
                totals.addCell(makePhrase(discountStr + " ", normal, false));
                totals.addCell(makePhrase(mFormatter.priceFormat(discountBD, SPACE), normal, false));
            }
        }

        double totalTax = sForm.getTotalTaxCost();
        if (Math.abs(totalTax)>0.001) {
          String  taxStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.tax:",null);
          totals.addCell(makePhrase(taxStr+" ", normal, false));
          String totalTaxS = null;
          totalTaxS = mFormatter.priceFormat(new Double(totalTax),SPACE);
          totals.addCell(makePhrase(totalTaxS, normal, false));
        }

        String  totalStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.totalExcludingVOC:",null);
        double totalAmount = sForm.getTotalAmount();
        if(consolidatedToOrderD==null && (!replacedOrderFl || !freightHandlFl)) {
          totals.addCell(makePhrase(totalStr+" ", normal, false));
          String totalAmountS = null;
          totalAmountS = mFormatter.priceFormat(new Double(totalAmount),SPACE);
          totals.addCell(makePhrase(totalAmountS, normal, false));
        }

        String  prefix  = null;
        if(appUser.getUserStore().getPrefix()!=null){
            prefix = appUser.getUserStore().getPrefix().getValue();
        }
        String orderStatus =  com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDesc,mRequest,prefix);
        if(consolidatedToOrderD!=null) {
          orderStatus += " "+
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.consolidated",null);
        }
        String  orderStatusStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.orderStatus:",null);
        totals.addCell(makePhrase(orderStatusStr+" ", normal, false));
        totals.addCell(makePhrase(orderStatus, normal, false));


        Table acctInfo = new PTable(1);
        //acctInfo.setWidths(halves);
        acctInfo.getDefaultCell().setBorder(borderType);
        acctInfo.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_LEFT);

        String  billingInformationStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.billingInformation:",null);
        acctInfo.addCell(makePhrase(billingInformationStr, smallHeading, false));
        //acctInfo.addCell(makePhrase("", smallHeading, true));

        OrderAddressData billTo = orderStatusDesc.getBillTo();

  if ( orderStatusDesc.hasCreditCard() ) {
      OrderCreditCardData ccod =
    orderStatusDesc.getOrderCreditCardData();
      acctInfo.addCell(makePhrase(ccod.getName(), normal, true));
      acctInfo.addCell(makePhrase(ccod.getCreditCardNumberDisplay(), normal, true));
      acctInfo.addCell(makePhrase(ccod.getCreditCardType(), normal, true));
      acctInfo.addCell(makePhrase(ccod.getAddress1(), normal, true));
      if ( ccod.getAddress2() != null ) {
      acctInfo.addCell(makePhrase(ccod.getAddress2(), normal, true));
      }
      if ( ccod.getAddress3() != null ) {
      acctInfo.addCell(makePhrase(ccod.getAddress3(), normal, true));
      }
      if ( ccod.getAddress4() != null ) {
      acctInfo.addCell(makePhrase(ccod.getAddress4(), normal, true));
      }

      acctInfo.addCell(makePhrase(ccod.getCity(), normal, true));
      if (appUser.getUserStore().isStateProvinceRequired() &&
          Utility.isSet(ccod.getStateProvinceCd())) {
        acctInfo.addCell(makePhrase(ccod.getStateProvinceCd(), normal, true));
      }
      acctInfo.addCell(makePhrase(ccod.getPostalCode(), normal, true));
      acctInfo.addCell(makePhrase(ccod.getCountryCd(), normal, true));
  } else {
        //acctInfo.addCell(makePhrase("Address 1: ", normal, false));
        acctInfo.addCell(makePhrase(billTo.getAddress1(), normal, true));

        if(billTo.getAddress2() != null)
        {
            //acctInfo.addCell(makePhrase("Address 2: ", normal, false));
            acctInfo.addCell(makePhrase(billTo.getAddress2(), normal, true));
        }

        if(billTo.getAddress3() != null)
        {
            //acctInfo.addCell(makePhrase("Address 3: ", normal, false));
            acctInfo.addCell(makePhrase(billTo.getAddress3(), normal, true));
        }

        if(billTo.getAddress4() != null)
        {
            //acctInfo.addCell(makePhrase("Address 4: ", normal, false));
            acctInfo.addCell(makePhrase(billTo.getAddress4(), normal, true));
        }

        //acctInfo.addCell(makePhrase("City, State, Zip: ", normal, false));
        acctInfo.addCell(makePhrase
                         (billTo.getCity()+", "+
                          (appUser.getUserStore().isStateProvinceRequired() &&
                           Utility.isSet(billTo.getStateProvinceCd()) ?
                           billTo.getStateProvinceCd()+SPACE:"") +
                          billTo.getPostalCode(), normal, true));

        //acctInfo.addCell(makePhrase("Country: ", normal, false));
        acctInfo.addCell(makePhrase(billTo.getCountryCd(), normal, true));
  }

        OrderAddressData shipTo = orderStatusDesc.getShipTo();

        Table siteInfo = new PTable(1);
        //siteInfo.setWidths(halves);
        siteInfo.getDefaultCell().setBorder(borderType);
        siteInfo.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_LEFT);


        String  shippingInformationStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.shippingInformation:",null);
        siteInfo.addCell(makePhrase(shippingInformationStr+" ", smallHeading, false));
        //siteInfo.addCell(makePhrase("", smallHeading, true));

        //siteInfo.addCell(makePhrase("Name: ", normal, false));
        siteInfo.addCell(makePhrase(orderD.getOrderSiteName(), normal, true));

        //siteInfo.addCell(makePhrase("Address 1: ", normal, false));
        siteInfo.addCell(makePhrase(shipTo.getAddress1(), normal, true));

        if(shipTo.getAddress2() != null)
        {
            //siteInfo.addCell(makePhrase("Address 2: ", normal, false));
            siteInfo.addCell(makePhrase(shipTo.getAddress2(), normal, true));
        }

        if(shipTo.getAddress3() != null)
        {
            //siteInfo.addCell(makePhrase("Address 3: ", normal, false));
            siteInfo.addCell(makePhrase(shipTo.getAddress3(), normal, true));
        }

        if(shipTo.getAddress4() != null)
        {
            //siteInfo.addCell(makePhrase("Address 4: ", normal, false));
            siteInfo.addCell(makePhrase(shipTo.getAddress4(), normal, true));
        }

        //siteInfo.addCell(makePhrase("City, State, Zip: ", normal, false));
        siteInfo.addCell(makePhrase(shipTo.getCity()+", "+
                (appUser.getUserStore().isStateProvinceRequired() &&
                 Utility.isSet(shipTo.getStateProvinceCd())?shipTo.getStateProvinceCd()+" ":"")+
                shipTo.getPostalCode(), normal, true));

        //siteInfo.addCell(makePhrase("Country: ", normal, false));
        siteInfo.addCell(makePhrase(shipTo.getCountryCd(), normal, true));


//iText 1.2.2        summTbl.addCell(totals);
//iText 1.2.2        summTbl.addCell(acctInfo);
//iText 1.2.2        summTbl.addCell(siteInfo);
//iText 1.2.2        resTbl.addCell(summTbl);
        summTbl.insertTable(totals);
        summTbl.insertTable(acctInfo);
        summTbl.insertTable(siteInfo);
        resTbl.insertTable(summTbl);

        if(consolidatedToOrderD==null && replacedOrderFl && freightHandlFl) {
          String  comm = "* "+
            ClwI18nUtil.getMessage(mRequest,
              "shop.orderStatus.text.actualHandlingFreightWillBeCalculatedAtTimeOfConsolidatedOrder",null);
          resTbl.addCell(makePhrase(comm, normal, false));
        }
        return resTbl;
    }

    public void sortItems(OrderItemDescDataVector items) {
        // no sorting by default
    }

    //main worker method.  The public methods will call this method
    //to generate the pdf after the object has been converted.
    public void generatePdf
    (OrderOpDetailForm sForm, CleanwiseUser appUser, StoreData pStore,
     OutputStream pOut,String pImageName)
    throws IOException{

      try
        {
        //we keep track of the page number rather than useing the built in pdf functionality
        //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
        //first page of that po, and on the 9th page of the pdf.
        OrderStatusDescData orderStatusDesc = sForm.getOrderStatusDetail();
        OrderItemDescDataVector items = sForm.getOrderItemDescList();
        AccountData accountD = appUser.getUserAccount();
        String addServiceFee = accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
        int pageNumber = 1;
        //Locale lLocale = Utility.parseLocaleCode(pStore.getBusEntity().getLocaleCd());
        //currencyFormatter = NumberFormat.getCurrencyInstance(lLocale);
        //create the header and footer

        setItemColomnWidths(appUser, items, pStore);

        Phrase headPhrase = new Phrase(makeChunk("", heading,true));
        String mess = ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.orderStatusReport", null);
        headPhrase.add(makeChunk(mess,heading,true));
        headPhrase.add(makeChunk(SPACE,heading,true));
        headPhrase.add(makeChunk(orderStatusDesc.getAccountName(),heading,true));

        HeaderFooter header = new HeaderFooter(headPhrase,true);
        header.setAlignment(HeaderFooter.ALIGN_RIGHT);

        //setup the document
        Document document = new Document(PageSize.A4, 10, 15, 30, 15);
        PdfWriter writer = PdfWriter.getInstance(document, pOut);

        Phrase footPhrase= makeStoreFooter (pStore);

            HeaderFooter footer = new HeaderFooter(footPhrase,false);
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
            
            drawOrderHeader(document, appUser, sForm, pImageName, orderStatusDesc);
            drawHeader(appUser,document, pageNumber,pImageName);


            BigDecimal totServiceFee = new BigDecimal(0.0);
        	BigDecimal subTot = new BigDecimal(sForm.getSubTotal());
            BigDecimal calcSubTotal = new BigDecimal(0.00);
        	Double tot = sForm.getTotalAmount();
            boolean firstCat = true;
            int lastDist = 0;
            sortItems(items);
            for(Iterator iter = items.iterator(); iter.hasNext();)
            {
              OrderItemDescData oidD = (OrderItemDescData) iter.next();
              if(!RefCodeNames.STORE_TYPE_CD.MLA.equals(pStore.getStoreType().getValue())){
                if(oidD.getDistId() != lastDist){
                  String frtName = null;
                  BigDecimal frtAmt = null;
                  if(oidD.getOrderFreight() != null){
                    //draw frieght info
                    frtName = oidD.getOrderFreight().getShortDesc();
                    frtAmt = oidD.getOrderFreight().getAmount();
                  }
                  lastDist = oidD.getDistId();
                  //Table table =makeSubHeader(oidD.getDistRuntimeDisplayName(),oidD.getOrderItem().getErpPoNum(),frtName,frtAmt);
                  Table table = makeSubHeader(oidD);

                  if(!writer.fitsPage(table,document.bottomMargin())){
                      document.newPage();
                      pageNumber = pageNumber + 1;
                      drawHeader(appUser, document, pageNumber,pImageName);
                      document.add(makeBlankLine());
                    }
                    document.add(table);
                }
              }
              Table itmTable = makeItemElement(appUser,oidD,pStore);

              if(!writer.fitsPage(itmTable,document.bottomMargin())){
                document.newPage();
                pageNumber = pageNumber + 1;
                drawHeader(appUser, document, pageNumber,pImageName);
                document.add(makeBlankLine());
              }
              document.add(itmTable);

                if (addServiceFee.equals("true")) {

                    BigDecimal thisQty = new BigDecimal(oidD.getOrderItem().getTotalQuantityOrdered());
                    BigDecimal custItemPrice = new BigDecimal(oidD.getOrderItem().getCustContractPrice().doubleValue());

                    BigDecimal thisFee = oidD.getOrderItem().getServiceFee();
                    BigDecimal res = new BigDecimal(0);
                    if (thisFee != null) {
                        res = (thisFee).multiply(thisQty);
                        custItemPrice = custItemPrice.subtract(thisFee);
                    }
                    if (res != null && res.compareTo(new BigDecimal(0.0)) > 0) {
                        totServiceFee = totServiceFee.add(res);
                    }

                    calcSubTotal = Utility.addAmt(calcSubTotal, custItemPrice.multiply(thisQty).setScale(2, BigDecimal.ROUND_HALF_UP));
                }

            }

            if(addServiceFee.equals("true")){
            	if(totServiceFee.compareTo(new BigDecimal(0.0))>0){
            		sForm.setSubTotal(calcSubTotal.doubleValue());
            		sForm.setServiceFeeAmt(totServiceFee);
            		sForm.setTotalAmount(tot);
            	}
            }

            // Summary
            document.add(makeBlankLine());
            document.add(makeBlankLine());
            Table summaryTable = makeSummary(sForm, appUser);
            if(!writer.fitsPage(summaryTable,document.bottomMargin())){
                document.newPage();
                pageNumber = pageNumber + 1;
                drawHeader(appUser, document, pageNumber,pImageName);
                document.add(makeBlankLine());
            }
            document.add(summaryTable);


            //If Consolidated Order
            if(RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.
                    equals(orderStatusDesc.getOrderDetail().getOrderTypeCd())) {
              document.add(makeBlankLine());
              document.add(makeBlankLine());
              //Replaced Orders Label
              Table replacedOrdersLabel = makeReplacedOrdersLabel();
              boolean skipFl = false;
              if(!writer.fitsPage(replacedOrdersLabel,document.bottomMargin())){
                document.newPage();
                pageNumber = pageNumber + 1;
                drawReplacedOdersHeader(appUser, document, pageNumber,pImageName);
                skipFl = true;
                document.add(makeBlankLine());
              }
              document.add(replacedOrdersLabel);
              //Replaced Orders Header
              if(!skipFl) {
                Table replacedOrdersHeader = makeReplacedOrdersHeader();
                if(!writer.fitsPage(replacedOrdersHeader,document.bottomMargin())){
                  document.newPage();
                  pageNumber = pageNumber + 1;
                  drawReplacedOdersHeader(appUser, document, pageNumber,pImageName);
                  document.add(makeBlankLine());
                }
                document.add(replacedOrdersHeader);
              }
              //Replaced Orders Elements
              ReplacedOrderViewVector replOrderVwV =
                                           orderStatusDesc.getReplacedOrders();
              for(Iterator iter=replOrderVwV.iterator(); iter.hasNext();) {
                ReplacedOrderView roVw = (ReplacedOrderView) iter.next();
                ReplacedOrderItemViewVector roiVwV = roVw.getItems();
                boolean nextOrder = true;
                for(Iterator iter1=roiVwV.iterator(); iter1.hasNext();) {
                  ReplacedOrderItemView roiVw =
                                          (ReplacedOrderItemView) iter1.next();
                  Table replacedOrdersElement =
                           makeReplacedOrdersElement(roVw,roiVw,nextOrder);
                  nextOrder = false;
                  if(!writer.fitsPage(replacedOrdersElement,document.bottomMargin())){
                    document.newPage();
                    pageNumber = pageNumber + 1;
                    drawReplacedOdersHeader(appUser, document, pageNumber,pImageName);
                    document.add(makeBlankLine());
                  }
                  document.add(replacedOrdersElement);

                }
              }
            }
            //close out the document
            document.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    //main worker method.  The public methods will call this method
    //to generate the pdf after the object has been converted.
    public void generatePdfforService (OrderOpDetailForm sForm,
                                       CleanwiseUser appUser,
                                       StoreData pStore,
                                       OutputStream pOut,
                                       String pImageName)  throws IOException {

        try {
            //we keep track of the page number rather than useing the built in pdf functionality
            //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
            //first page of that po, and on the 9th page of the pdf.
            OrderStatusDescData orderStatusDesc = sForm.getOrderStatusDetail();
            OrderItemDescDataVector items = sForm.getOrderItemDescList();
            int pageNumber = 1;
            //Locale lLocale = Utility.parseLocaleCode(pStore.getBusEntity().getLocaleCd());
            //currencyFormatter = NumberFormat.getCurrencyInstance(lLocale);
            //create the header and footer
            Phrase headPhrase = new Phrase(makeChunk("", heading, true));
            String mess = ClwI18nUtil.getMessage(mRequest, "shop.orderStatus.text.orderStatusReport", null);
            headPhrase.add(makeChunk(mess, heading, true));
            headPhrase.add(makeChunk(SPACE, heading, true));
            headPhrase.add(makeChunk(orderStatusDesc.getAccountName(), heading, true));

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
            drawOrderHeader(document, appUser, sForm, pImageName, orderStatusDesc);
            drawHeaderforService(appUser, document, pageNumber, pImageName);


            boolean firstCat = true;
            int lastDist = 0;
            for (Iterator iter = items.iterator(); iter.hasNext();) {
                OrderItemDescData oidD = (OrderItemDescData) iter.next();
                if (!RefCodeNames.STORE_TYPE_CD.MLA.equals(pStore.getStoreType().getValue())) {
                    if (oidD.getDistId() != lastDist) {
                        String frtName = null;
                        BigDecimal frtAmt = null;
                        if (oidD.getOrderFreight() != null) {
                            //draw frieght info
                            frtName = oidD.getOrderFreight().getShortDesc();
                            frtAmt = oidD.getOrderFreight().getAmount();
                        }
                        lastDist = oidD.getDistId();
                        //Table table = makeSubHeader(oidD.getDistRuntimeDisplayName(), oidD.getOrderItem().getErpPoNum(), frtName, frtAmt);
                        Table table = makeSubHeader(oidD);

                        if (!writer.fitsPage(table, document.bottomMargin())) {
                            document.newPage();
                            pageNumber = pageNumber + 1;
                            drawHeaderforService(appUser, document, pageNumber, pImageName);
                            document.add(makeBlankLine());
                        }
                        document.add(table);
                    }
                }

                Table itmTable = null;
                itmTable = makeServiceElement(appUser, oidD, pStore);

                if (!writer.fitsPage(itmTable, document.bottomMargin())) {
                    document.newPage();
                    pageNumber = pageNumber + 1;
                    drawHeaderforService(appUser, document, pageNumber, pImageName);
                    document.add(makeBlankLine());
                }
                document.add(itmTable);
            }

            // Summary
            document.add(makeBlankLine());
            document.add(makeBlankLine());
            Table summaryTable = makeSummary(sForm, appUser);
            if (!writer.fitsPage(summaryTable, document.bottomMargin())) {
                document.newPage();
                pageNumber = pageNumber + 1;
                drawHeaderforService(appUser, document, pageNumber, pImageName);
                document.add(makeBlankLine());
            }
            document.add(summaryTable);

            //close out the document
            document.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    private void drawHeaderforService(CleanwiseUser appUser, Document document, int pPageNumber, String pImageName)
            throws DocumentException {
        drawLogo(appUser,document, pPageNumber, pImageName);
        document.add(makeLine());
        //add the item header line
        int numberOfCols = 5;

        PdfPTable itemHeader = new PdfPTable(numberOfCols);

        itemHeader.setWidthPercentage(100);
        itemHeader.setWidths(getServiceColomnWidths());
        itemHeader.getDefaultCell().setBorderWidth(2);
        itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
        itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
        itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
        itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);

        String  assetNameStr = ClwI18nUtil.getMessage(mRequest,"userAssets.text.assetName",null);
        itemHeader.addCell(makePhrase(assetNameStr,itemHeading,false));
        String  assetNumberStr = ClwI18nUtil.getMessage(mRequest,"userAssets.text.assetNumber",null);
        itemHeader.addCell(makePhrase(assetNumberStr,itemHeading,false));
        String  assetSerialStr = ClwI18nUtil.getMessage(mRequest,"userAssets.text.assetSerial",null);
        itemHeader.addCell(makePhrase(assetSerialStr,itemHeading,false));
        String  serviceNameStr = ClwI18nUtil.getMessage(mRequest,"shoppingServices.text.ourServiceName",null);
        itemHeader.addCell(makePhrase(serviceNameStr,itemHeading,false));
        String  priceStr = ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.price",null);
        itemHeader.addCell(makePhrase(priceStr,itemHeading,false));
        document.add(itemHeader);
    }




  private Table makeReplacedOrdersLabel()
  throws DocumentException
  {
     Table replacedOrdersLabel =  new PTable(1);
     replacedOrdersLabel.setWidth(100);
     replacedOrdersLabel.getDefaultCell().setBorderWidth(2);
     replacedOrdersLabel.getDefaultCell().setBackgroundColor(java.awt.Color.black);
     replacedOrdersLabel.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
     replacedOrdersLabel.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
     replacedOrdersLabel.getDefaultCell().setBorderColor(java.awt.Color.white);
     String  replacedOrdersStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.replacedOrders",null);
     replacedOrdersLabel.addCell(makePhrase(replacedOrdersStr,itemHeading,false));
     return replacedOrdersLabel;
  }

  private Table makeReplacedOrdersHeader()
  throws DocumentException
  {
    Table replacedOrdersHeader = new PTable(7);
    replacedOrdersHeader.setWidth(100);
    replacedOrdersHeader.setWidths(new int[]{20,10,10,10,10,10,30});
    replacedOrdersHeader.getDefaultCell().setBorderWidth(2);
    replacedOrdersHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
    replacedOrdersHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
    replacedOrdersHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
    replacedOrdersHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
    String shipToStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.shipTo",null);
    replacedOrdersHeader.addCell(makePhrase(shipToStr,itemHeading,false));
    String orderNumStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.order#",null);
    replacedOrdersHeader.addCell(makePhrase(orderNumStr,itemHeading,false));
    String refOrderNumStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.referenceOrder#",null);
    replacedOrdersHeader.addCell(makePhrase(refOrderNumStr,itemHeading,false));
    String requestPoNumStr =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.requestPo#",null);
    replacedOrdersHeader.addCell(makePhrase(requestPoNumStr,itemHeading,false));
    String orderQtyStr =
              ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.orderQty",null);
    replacedOrdersHeader.addCell(makePhrase(orderQtyStr,itemHeading,false));
    String ourSkuNumStr =
              ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.ourSku#",null);
    replacedOrdersHeader.addCell(makePhrase(ourSkuNumStr,itemHeading,false));
    String productNameStr =
              ClwI18nUtil.getMessage(mRequest,"shoppingItems.text.product<sp>Name",null);
    replacedOrdersHeader.addCell(makePhrase(productNameStr,itemHeading,false));
    return replacedOrdersHeader;

  }

    private Table makeReplacedOrdersElement
                   (ReplacedOrderView pReplOrder,
                    ReplacedOrderItemView pReplOrderItem ,
                    boolean pNextOrder)
    throws DocumentException
    {

      Table replacedOrdersElement = new PTable(7);
      replacedOrdersElement.setWidth(100);
      replacedOrdersElement.setWidths(new int[]{20,10,10,10,10,10,30});
      replacedOrdersElement.getDefaultCell().setBorderColor(java.awt.Color.black);
      replacedOrdersElement.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);

      String shipTo = (pNextOrder)? pReplOrder.getOrderSiteName():"";
      String orderNum = (pNextOrder)? pReplOrder.getOrderNum():"";
      String refNum =  (pNextOrder)? pReplOrder.getRefOrderNum():"";
      String requestPoNum =  (pNextOrder)? pReplOrder.getRequestPoNum():"";
      int orderQty = pReplOrderItem.getQuantity();
      String orderQtyS = ""+orderQty;
      if(orderQty==0 ||
         RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
                                  equals(pReplOrderItem.getOrderItemStatusCd())
        ) {
        orderQtyS =
              ClwI18nUtil.getMessage(mRequest,"shop.orderStatus.text.cancelled",null);
      }
      String ourSku = pReplOrderItem.getCustItemSkuNum();
      if(ourSku==null) {
        ourSku = ""+pReplOrderItem.getItemSkuNum();
      }
      String shortDesc = pReplOrderItem.getCustItemShortDesc();
      if(shortDesc==null) {
        shortDesc = pReplOrderItem.getItemShortDesc();
      }
      replacedOrdersElement.addCell(makePhrase(shipTo,normal,true));
      replacedOrdersElement.addCell(makePhrase(orderNum,normal,true));
      replacedOrdersElement.addCell(makePhrase(refNum,normal,true));
      replacedOrdersElement.addCell(makePhrase(requestPoNum,normal,true));
      replacedOrdersElement.addCell(makePhrase(orderQtyS,normal,true));
      replacedOrdersElement.addCell(makePhrase(ourSku,normal,true));
      replacedOrdersElement.addCell(makePhrase(shortDesc,normal,true));

      return replacedOrdersElement;
    }

        private void drawReplacedOdersHeader(CleanwiseUser appUser, Document document,
                            int pPageNumber, String pImageName)
    throws DocumentException {
        drawLogo(appUser,document,pPageNumber, pImageName);
        Table replacedOrdersLabel = makeReplacedOrdersLabel();
        document.add(replacedOrdersLabel);
        Table replacedOrdersHeader = makeReplacedOrdersHeader();
        document.add(replacedOrdersHeader);
   }

   private static boolean isShowValue(HttpServletRequest request, String pParamName) {
     String value = request.getParameter(pParamName);
     if (Utility.isSet(value) && !Utility.isTrue(value)) {
       return false;
     }
     return true;
   }

   private void setShowValues() {
     mShowSize = isShowValue(mRequest, "showSize");
     mShowMfg = isShowValue(mRequest, "showMfg");
     mShowMfgSku = isShowValue(mRequest, "showMfgSku");
     mShowPar = isShowValue(mRequest, "showPar");
   }

   protected int getNumberOfColumns(CleanwiseUser appUser) {
     setShowValues();
     int numberOfCols = 6;
     if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING)) {
       numberOfCols++;
     }
     if (appUser.getUserAccount().isShowSPL()) {
       numberOfCols++;
     }
     if (mShowSize) {
       numberOfCols++;
     }
     /*if (mShowMfg) {
       numberOfCols++;
     }
     if (mShowMfgSku) {
       numberOfCols++;
     }*/
     if (mShowPar) {
       numberOfCols++;
     }
     return numberOfCols;
   }

    public boolean isShowSize() {
        return mShowSize;
    }

    public boolean isShowPar() {
        return mShowPar;
    }

    public boolean isShowMfg() {
        return mShowMfg;
    }

    public boolean isShowMfgSku() {
        return mShowMfgSku;
    }

    public int[] getSubHeaderWidths() {
        return subHeaderWidths;
    }

    public HttpServletRequest getRequest() {
        return mRequest;
    }

    public ClwI18nUtil getFormatter() {
        return mFormatter;
    }

}

