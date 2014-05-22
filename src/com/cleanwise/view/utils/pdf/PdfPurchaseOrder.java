/*
 * PdfPurchaseOrder.java
 *
 * Created on May 10, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.*;

import java.awt.Color; //used by the lowagie/iText api, we are not actually using the awt toolkit here
import java.math.BigDecimal;
import java.io.OutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.text.DateFormat;

/**
*Constructs a pdf document in the form of a pdf document.  It understands the various
 *propriatary objects, and uses them to construct a purchase order document.
 * @author  bstevens
 */
public class PdfPurchaseOrder extends PdfSuper {
    private static ArrayList INVALID_CUST_PO_NUMS = new ArrayList();
    static{
        INVALID_CUST_PO_NUMS.add("NA");
        INVALID_CUST_PO_NUMS.add("N/A");
        INVALID_CUST_PO_NUMS.add("-");
    }
    private static final BigDecimal ZERO = new BigDecimal(0);
    //static configuration variables
    //configures the percentage widths of item columns in the document
    private static int itmCoumnWidth[] = {5,15,54,13,13};
    //overrides parent (technically this is bad practice as it is hiding parents variable).
    private Font normal = FontFactory.getFont(FontFactory.COURIER_BOLD,12);
    //wheather to use a image logo or text
    boolean useImageLogo=true;
    private StoreData mStore;
    private String mStoreType=null;

    /** Creates a new instance of PdfPurchaseOrder */
    public PdfPurchaseOrder() {
        // Reset the normal font to a larger size.
        super.setNormalFontSize(12);
    }

    private int getPoQty(OrderItemData oi){
        if(oi.getDistItemQuantity() > 0){
            return oi.getDistItemQuantity();
        }else{
            return oi.getTotalQuantityOrdered();
        }
    }

    private BigDecimal getPoCost(OrderItemData oi){
        if(oi.getDistUomConvCost() != null && oi.getDistUomConvCost().compareTo(ZERO) > 0){
            return oi.getDistUomConvCost();
        }else{
            return oi.getDistItemCost();
        }
    }

    //utility function to make an item Element.
    private Table makePoItemElement(OrderItemData pItm,Locale pLocale) throws DocumentException{
        NumberFormat nf = NumberFormat.getInstance(pLocale);
        nf.setMinimumFractionDigits(2);


        Table itmTbl = new PTable(5);
        itmTbl.setWidth(100);
        itmTbl.setWidths(itmCoumnWidth);
        itmTbl.getDefaultCell().setBorder(borderType);

        //***********First add the cleanwise sku information and general item information
        itmTbl.addCell(makePhrase(Integer.toString(pItm.getErpPoLineNum()),normal,false));
        String uom;
        if (pItm.getDistItemUom() != null){
            uom = pItm.getDistItemUom();
        }else{
            uom = pItm.getItemUom();
        }
        String pack;
        if(Utility.isSet(pItm.getDistItemPack())){
            pack = pItm.getDistItemPack();
        }else if(Utility.isSet(pItm.getItemPack())){
            pack = pItm.getItemPack();
        }else{
            pack = null;
        }
        //pack = pItm.getItemPack();
        if (pack == null){
            itmTbl.addCell(makePhrase(Integer.toString(getPoQty(pItm)) + SPACE + uom,normal,false));
        }else{
            itmTbl.addCell(makePhrase(Integer.toString(getPoQty(pItm)) + SPACE + uom + SPACE + pack,normal,false));
        }

        //get and add sku and decription phrase
        Phrase lSku = makePhrase("Item #: " + Integer.toString(pItm.getItemSkuNum()),normal,true);
        lSku.add(makeChunk(pItm.getItemShortDesc(),normal,false));
        itmTbl.addCell(lSku);

        //get and add itemCost
        String lnCstS = nf.format(getPoCost(pItm));
        itmTbl.addCell(makePhrase(lnCstS,normal,false));

        //get, add, and calculate line item total
        BigDecimal lnTot = getPoCost(pItm);
        lnTot = lnTot.multiply(new BigDecimal(Integer.toString(getPoQty(pItm))));
        String lnTotS = nf.format(lnTot);
        itmTbl.addCell(makePhrase(lnTotS,normal,false));



        //***********Now add the distributor information if it exists
        itmTbl.addCell(makePhrase("",normal,true));
        String distText;
        if ((pItm.getDistItemSkuNum() == null || (pItm.getDistItemSkuNum().equals("")))){
            itmTbl.addCell(makePhrase(SPACE,normal,true));
            distText=SPACE;
        }else{
            itmTbl.addCell(makePhrase("Vendor Item: ",normal,false));
            distText = pItm.getDistItemSkuNum();
        }
        if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(mStoreType)){
            if(Utility.isTaxExemptOrderItem(pItm) || Utility.isTaxExemptItemSaleTypeCd(pItm)){
                distText+= "**** TAX EXEMPT";
            }
        }
        Phrase distItmInfo = makePhrase(distText,normal,true);
        itmTbl.addCell(distItmInfo);
        itmTbl.addCell(makePhrase("",normal,true));
        itmTbl.addCell(makePhrase("",normal,true));
        return itmTbl;
    }


    //utility function to make an item Element.
    private Table makePoItemElement(OrderItemData pItm, HashMap pAssetInfo, Locale pLocale) throws DocumentException {
        NumberFormat nf = NumberFormat.getInstance(pLocale);
        nf.setMinimumFractionDigits(2);

        Table itmTbl = new PTable(7);
        itmTbl.setWidth(100);
        itmTbl.setWidths(new int[]{20,12,18,5,25,10,10});
        itmTbl.getDefaultCell().setBorder(borderType);

        String assetName = "UNKNOWN";
        String assetNum = "UNKNOWN";
        String assetSerialNum="UNKNOWN";
        if (pAssetInfo != null) {
            AssetData assetData = (AssetData) pAssetInfo.get(String.valueOf(pItm.getAssetId()));
            if (assetData != null)
            {
                assetName = assetData.getShortDesc();
                assetNum=assetData.getAssetNum();
                assetSerialNum=assetData.getSerialNum();
            }
        }
        itmTbl.addCell(makePhrase(assetName, normal, false));
        itmTbl.addCell(makePhrase(assetNum, normal, false));
        itmTbl.addCell(makePhrase(assetSerialNum, normal, false));
        itmTbl.addCell(makePhrase(Integer.toString(pItm.getErpPoLineNum()), normal, false));
        itmTbl.addCell(makePhrase(pItm.getItemShortDesc(), normal, false));

        //get and add itemCost
        String lnCstS = nf.format(getPoCost(pItm));
        itmTbl.addCell(makePhrase(lnCstS, normal, false));

        //get, add, and calculate line item total
        BigDecimal lnTot = getPoCost(pItm);
        lnTot = lnTot.multiply(new BigDecimal(Integer.toString(getPoQty(pItm))));
        String lnTotS = nf.format(lnTot);
        itmTbl.addCell(makePhrase(lnTotS, normal, false));

        //***********Now add the distributor information if it exists
        itmTbl.addCell(makePhrase("", normal, true));
        String distText;
        if ((pItm.getDistItemSkuNum() == null || (pItm.getDistItemSkuNum().equals("")))) {
            itmTbl.addCell(makePhrase(SPACE, normal, true));
            distText = SPACE;
        } else {
            itmTbl.addCell(makePhrase("Vendor Item: ", normal, false));
            distText = pItm.getDistItemSkuNum();
        }
        if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(mStoreType)) {
            if (Utility.isTaxExemptOrderItem(pItm)) {
                distText += "**** TAX EXEMPT";
            }
        }
        Phrase distItmInfo = makePhrase(distText, normal, true);
        itmTbl.addCell(distItmInfo);
        itmTbl.addCell(makePhrase("", normal, true));
        itmTbl.addCell(makePhrase("", normal, true));
        return itmTbl;
    }

    private void drawPoHeader(Document document,PdfPoStruct p850,
    DistributorData pDist,String pErpPoNumber,Date pErpPoDate,Locale pLocale,
    int pPageNumber,String pSenderName, String pImageName, boolean isStateProvinceRequired)
    throws DocumentException {
        DateFormat lDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,pLocale);
        //add the logo
        //create the header
        PdfPTable absoluteHeader = new PdfPTable(2);
        absoluteHeader.setWidthPercentage(100);
        absoluteHeader.getDefaultCell().setBorder(borderType);


        if(useImageLogo){
            absoluteHeader.addCell(makePhrase(SPACE,heading,true));
        }else{
            absoluteHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            absoluteHeader.addCell(makePhrase(p850.getAccountName(),heading,true));
        }
        absoluteHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        absoluteHeader.addCell(makePhrase("Purchase\nOrder",heading,true));
        document.add(absoluteHeader);




        if(useImageLogo){
            try{
                Image i = Image.getInstance(pImageName);
                float x = document.leftMargin();
                float y = document.getPageSize().getHeight() - i.getHeight() - 5;
                i.setAbsolutePosition(x,y);
                document.add(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //deal with po header info
        //construct the table/cells with all of the header information
        //must be the pdf specific type or the table will not use the full width of the page.
        //unfortuanately this means we are limited to PDF rendering only.
        PdfPTable headerInfo = new PdfPTable(4);
        headerInfo.setWidthPercentage(100);
        int headerWidths[] = {15,30,30,25};
        headerInfo.setWidths(headerWidths);
        headerInfo.getDefaultCell().setBorder(borderType);
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        //add content, reads right to left, not vertically with content
        headerInfo.addCell(new Phrase());
        headerInfo.addCell(new Phrase());
        headerInfo.addCell(makePhrase("PAGE: ",smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + Integer.toString(pPageNumber),normal,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(mStoreType)){
            headerInfo.addCell(makePhrase("BILL TO: ",smallHeading,true));
        }else{
            headerInfo.addCell(makePhrase("BUYER NAME: ",smallHeading,true));
        }
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + pSenderName,normal,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        String poOutFin = p850.getPurchaseOrderD().getOutboundPoNum();
        if (!Utility.isSet(poOutFin))
        	poOutFin = pErpPoNumber;

        headerInfo.addCell(makePhrase("PURCHASE ORDER NUMBER: ",smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + poOutFin,normal,true));
        headerInfo.addCell(new Phrase());
        headerInfo.addCell(new Phrase());
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        headerInfo.addCell(makePhrase("PURCHASE ORDER DATE:",smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + lDateFormatter.format(pErpPoDate),normal,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        headerInfo.addCell(makePhrase(SPACE,smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE,smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        // Do not include DUE DAYS.
        // Per Andy 4-28-2003, durval
        //        headerInfo.addCell(makePhrase("DUE DAYS: ",smallHeading,false));
        //headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        //headerInfo.addCell(makePhrase(SPACE + pDist.getPurchaseOrderDueDays().getValue(),normal,false));

        //add the content
        document.add(headerInfo);

        //draw a line  XXX this is somewhat of a hack, as there seems to be no way to draw a line in the
        //current iText API that is relative to your current position in the document
        document.add(makeLine());

        if ( pPageNumber == 1 ) {
        //deal with the billing and shipping information header
        PdfPTable addrHeader = new PdfPTable(2);
        addrHeader.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        addrHeader.setWidthPercentage(90);
        addrHeader.getDefaultCell().setBorder(borderType);
        addrHeader.addCell(makePhrase("VENDOR NAME:",smallHeading,false));
        addrHeader.addCell(makePhrase("SHIP TO:",smallHeading,false));
        document.add(addrHeader);


        ArrayList shipToAddr = new ArrayList();
        if(mStore.isAccountNameInSiteAddress()){
          shipToAddr.add(p850.getAccountName());
        }
      shipToAddr.add(p850.getOrderD().getOrderSiteName());
      shipToAddr.add(p850.getShipAddr().getAddress1());
      shipToAddr.add(p850.getShipAddr().getAddress2());
      shipToAddr.add(p850.getShipAddr().getAddress3());
      shipToAddr.add(p850.getShipAddr().getAddress4());


        //deal with the billing and shipping information header
        PdfPTable addresses = new PdfPTable(2);
        addresses.setWidthPercentage(90);
        addresses.getDefaultCell().setBorder(borderType);
        addresses.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        addresses.addCell(makePhrase(pDist.getBusEntity().getShortDesc(),normal,false));
        addresses.addCell(makePhrase((String)Utility.safeGetListElement(shipToAddr,0,""),normal,false));

        addresses.addCell(makePhrase(pDist.getPrimaryAddress().getAddress1(),normal,false));
        addresses.addCell(makePhrase((String)(String)Utility.safeGetListElement(shipToAddr,1,""),normal,false));

        addresses.addCell(makePhrase(pDist.getPrimaryAddress().getAddress2(),normal,false));
        addresses.addCell(makePhrase((String)Utility.safeGetListElement(shipToAddr,2,""),normal,false));

        addresses.addCell(makePhrase(pDist.getPrimaryAddress().getAddress3(),normal,false));
        addresses.addCell(makePhrase((String)Utility.safeGetListElement(shipToAddr,3,""),normal,false));

        addresses.addCell(makePhrase(pDist.getPrimaryAddress().getAddress4(),normal,false));
        addresses.addCell(makePhrase((String)Utility.safeGetListElement(shipToAddr,4,""),normal,false));

        if(Utility.isSet((String)Utility.safeGetListElement(shipToAddr,5,""))){
            addresses.addCell(SPACE);
            addresses.addCell(makePhrase((String)Utility.safeGetListElement(shipToAddr,5,""),normal,false));
        }
  /*
   * Per Tom and Andy, remove the contact name
   * from the ship to information.
   *
        if(Utility.isSet(p850.getOrderD().getOrderContactName())){
            addresses.addCell(SPACE);
            addresses.addCell(makePhrase("Attn: "+p850.getOrderD().getOrderContactName(),normal,false));
        }
  */

        addresses.addCell(makePhrase(pDist.getPrimaryAddress().getCity() + "      " +
        (isStateProvinceRequired && Utility.isSet(pDist.getPrimaryAddress().getStateProvinceCd())?pDist.getPrimaryAddress().getStateProvinceCd() + SPACE:"") +
        pDist.getPrimaryAddress().getPostalCode(),normal,false));

        addresses.addCell(makePhrase(p850.getShipAddr().getCity() + "      " +
        (isStateProvinceRequired && Utility.isSet(p850.getShipAddr().getStateProvinceCd())?p850.getShipAddr().getStateProvinceCd() + SPACE:"") +
        p850.getShipAddr().getPostalCode(),normal,false));
        document.add(addresses);

  // If a ship via address has been defined
  // add the information to the PO document.
  boolean addShipVia = false;
  if (p850.getShipVia() != null ) {
            addShipVia = true;

        /*for (int i=0,len=p850.getOrderItemDV().size();i<len;i++){
    OrderItemData itm = (OrderItemData)
        p850.getOrderItemDV().get(i);
    if ( itm.getErpPoNum() == null ) {
        continue;
    }
    if ( pErpPoNumber == null ) {
        continue;
    }
    if ( pErpPoNumber.equals(itm.getErpPoNum()) && itm.getFreightHandlerId() > 0 ) {
        // The item(s) in this po have been sent
        // to a freight handler.  Add the ship via
        // address information.
        addShipVia = true;
        break;
    }
      }*/
  }

  if ( addShipVia == true ) {
      PdfPTable shipViaAddrHdr = new PdfPTable(1);
      shipViaAddrHdr.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
      shipViaAddrHdr.setWidthPercentage(90);
      shipViaAddrHdr.getDefaultCell().setBorder(borderType);
      shipViaAddrHdr.addCell
    (makePhrase("SHIP VIA:",smallHeading,false));
      document.add(shipViaAddrHdr);

      PdfPTable shipViaAddr = new PdfPTable(1);
      shipViaAddr.setWidthPercentage(90);
      shipViaAddr.getDefaultCell().setBorder(borderType);
      shipViaAddr.getDefaultCell().setHorizontalAlignment
    (PdfPCell.ALIGN_LEFT);
            FreightHandlerView fh = p850.getShipVia();
      shipViaAddr.addCell
    (makePhrase(fh.getBusEntityData().getShortDesc(),normal,false));
      shipViaAddr.addCell
    (makePhrase(fh.getPrimaryAddress().getAddress1(),normal,false));
      if ( fh.getPrimaryAddress().getAddress2() != null &&
     fh.getPrimaryAddress().getAddress2().length() > 0 ) {
      shipViaAddr.addCell
    (makePhrase(fh.getPrimaryAddress().getAddress2(),normal,false));
      }
      if ( fh.getPrimaryAddress().getAddress3() != null &&
     fh.getPrimaryAddress().getAddress3().length() > 0 ) {
      shipViaAddr.addCell
    (makePhrase(fh.getPrimaryAddress().getAddress3(),normal,false));
      }
      if ( fh.getPrimaryAddress().getAddress4() != null &&
     fh.getPrimaryAddress().getAddress4().length() > 0 ) {
      shipViaAddr.addCell
    (makePhrase(fh.getPrimaryAddress().getAddress4(),normal,false));
      }
      shipViaAddr.addCell
    (makePhrase(fh.getPrimaryAddress().getCity() + "      " +
          (isStateProvinceRequired && Utility.isSet(fh.getPrimaryAddress().getStateProvinceCd())?fh.getPrimaryAddress().getStateProvinceCd() + SPACE:"") +
          fh.getPrimaryAddress().getPostalCode(),normal,false)
     );
      document.add(shipViaAddr);

  }

        //Calendar c = Calendar.getInstance(pLocale);
        //c.add(Calendar.DAY_OF_MONTH,2);
        //Date deliverDate = c.getTime();
        //String deliverDateS = lDateFormatter.format(deliverDate);
        document.add(makeLine());

        PdfPTable misc = new PdfPTable(1);
        misc.setWidthPercentage(90);
        misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        misc.getDefaultCell().setBorder(borderType);
        //misc.addCell(makePhrase("******************Deliver - " + deliverDateS + "******************",normal,true));
        misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);

        String reqshipdate = OrderStatusDescData.getRequestedShipDate
            (p850.getOrderMetaDV());
        if(Utility.isSet(reqshipdate)){
            String s = "Requested Ship Date: " + reqshipdate;
            misc.addCell(makePhrase( s,normal,true));
        }

        if(Utility.isSet(pDist.getPurchaseOrderComments())){
            misc.addCell(makePhrase(pDist.getPurchaseOrderComments(),normal,true));
        }

  StringBuffer m1 = new StringBuffer("Purchase Order Currency: ");
  m1.append(p850.getOrderD().getCurrencyCd());
  m1.append(", Conf # " + p850.getOrderD().getOrderNum());
        if(p850.getOrderD().getSiteId() > 0){
            m1.append(", SID # " + p850.getOrderD().getSiteId());
        }
        if(Boolean.TRUE.equals(pDist.getPrintCustContactOnPurchaseOrder())){
            if(Utility.isSet(p850.getOrderD().getOrderContactPhoneNum()) && Utility.isSet(p850.getOrderD().getOrderContactName())){
                m1.append(", Contact " + p850.getOrderD().getOrderContactName() + ": " + p850.getOrderD().getOrderContactPhoneNum() );
            }else if(Utility.isSet(p850.getOrderD().getOrderContactPhoneNum())){
                m1.append(", Contact " +  p850.getOrderD().getOrderContactPhoneNum());
            }else if(Utility.isSet(p850.getOrderD().getOrderContactName())){
                m1.append(", Contact " + p850.getOrderD().getOrderContactName());
            }
        }
        misc.addCell(makePhrase(m1.toString(),normal,true));


        if(Utility.isSet(p850.getOrderD().getComments())){
          misc.addCell(makeLine());
          String custComments =
                   "Customer Comments: " + p850.getOrderD().getComments();
          misc.addCell(makePhrase(custComments,normal,false));
        }
        if(Utility.isSet(p850.getSiteShipMsg())){
          misc.addCell(makeLine());
          String cwComments =
                   "Shipping Message: " + p850.getSiteShipMsg();
            misc.addCell(makePhrase(cwComments,normal,false));
        }
        StringBuffer poline = new StringBuffer();
        boolean printPoLine = false;
        String custPo = p850.getOrderD().getRequestPoNum();
        if(custPo != null){
            custPo = custPo.trim();
        }
        if(Utility.isSet(custPo) && !INVALID_CUST_PO_NUMS.contains(custPo)){
            poline.append(p850.getOrderD().getRequestPoNum());
            printPoLine = true;
        }
        if(Utility.isSet(p850.getOrderD().getRefOrderNum())){
            if(printPoLine){
                poline.append("/");
            }
            poline.append(p850.getOrderD().getRefOrderNum());
            printPoLine = true;
        }

        if(printPoLine){
            misc.addCell(makeLine());
            misc.addCell(makePhrase("The following line MUST be included on packing slip: ",normal,false));
            misc.addCell(makePhrase(poline.toString(),normal,false));
        }
        document.add(misc);
  }

        //add the item header line
        PdfPTable itemHeader;
        if(p850.isService())
        {
        //add the item header line
        itemHeader = new PdfPTable(7);
        itemHeader.setWidthPercentage(100);
        itemHeader.getDefaultCell().setBorderWidth(2);
        itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
        itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
        itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
        itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
        itemHeader.setWidths(new int[]{20,12,18,5,25,10,10});
        itemHeader.addCell(makePhrase("ASSET",itemHeading,false));
        itemHeader.addCell(makePhrase("NUMBER",itemHeading,false));
        itemHeader.addCell(makePhrase("SERIAL",itemHeading,false));
        itemHeader.addCell(makePhrase("LINE",itemHeading,false));
        itemHeader.addCell(makePhrase("SERVICE & DESCRIPTION",itemHeading,false));
        }
        else{
         //add the item header line
        itemHeader = new PdfPTable(5);
        itemHeader.setWidthPercentage(100);
        itemHeader.getDefaultCell().setBorderWidth(2);
        itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
        itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
        itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
        itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
        itemHeader.setWidths(itmCoumnWidth);
        itemHeader.addCell(makePhrase("LINE",itemHeading,false));
        itemHeader.addCell(makePhrase("QTY/UOM/PACK",itemHeading,false));
        itemHeader.addCell(makePhrase("ITEM & DESCRIPTION",itemHeading,false));
        }
        itemHeader.addCell(makePhrase("PRICE",itemHeading,false));
        itemHeader.addCell(makePhrase("NET AMOUNT",itemHeading,false));
        document.add(itemHeader);

    }

    /**draws the trailer infomration onto the document*/
    private Table makePoTrailer(Document document,
    String pErpPoNumber, int pPageNumber,Locale pLocale, PdfPoStruct p850, DistributorData pDist)
    throws DocumentException {
        //add trailer information
        document.add(makeBlankLine());
        Table totals = new PTable(3);
        totals.setWidth(100);
        totals.getDefaultCell().setBorder(borderType);

        //add dollar totals
        NumberFormat nf = NumberFormat.getInstance(pLocale);
        nf.setMinimumFractionDigits(2);

        //***Start sub total
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        totals.addCell(makePhrase(SPACE,normal,false));
        if (p850.isService()) {
            totals.addCell(makePhrase("Service Total:", normal, false));
        } else {
            totals.addCell(makePhrase("Product Total:", normal, false));
        }
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        String subTot = nf.format(p850.getPurchaseOrderD().getLineItemTotal());
        totals.addCell(makePhrase(subTot,normal,false));
        //***End sub total
        
      //only render tax info for non MLA stores..there is not really a compelling reason to
        //not print it except that it has never been printed and it will never be set so
        //it should not make much difference.
        if(!RefCodeNames.STORE_TYPE_CD.MLA.equals(mStore.getStoreType()) && p850.getPurchaseOrderD().getTaxTotal() != null && mStore.isTaxableIndicator()){
            //***Start tax total
            totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            totals.addCell(makePhrase(SPACE,normal,false));
            totals.addCell(makePhrase("Tax:",normal,false));
            totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            String tax = nf.format(p850.getPurchaseOrderD().getTaxTotal());
            totals.addCell(makePhrase(tax,normal,false));
            //***End tax total
        }

        //Add on charges - discount, small order fee, fuel surcharge
        int orderId = p850.getPurchaseOrderD().getOrderId();
        int distId = pDist.getBusEntity().getBusEntityId();
        BigDecimal chargesTotal = new BigDecimal(0);
        try{
        	APIAccess mApiAccess = new APIAccess();
        	Distributor distEjb = mApiAccess.getDistributorAPI();        	
        	OrderAddOnChargeDataVector addOnCharges = distEjb.getOrderAddOnCharges(distId, orderId);
        	
        	if(addOnCharges!=null && addOnCharges.size()>0){
        		for(int i=0; i<addOnCharges.size(); i++){
        			OrderAddOnChargeData charge = (OrderAddOnChargeData)addOnCharges.get(i);
        			
        			totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                    totals.addCell(makePhrase(SPACE, normal, false));
                    totals.addCell(makePhrase(charge.getDistFeeChargeCd(), normal, false));
                    totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
                    String chargeFee = nf.format((BigDecimal)charge.getAmount());
                    totals.addCell(makePhrase(chargeFee, normal, false));
                    chargesTotal = chargesTotal.add(charge.getAmount());
        		}
        		
        	}
        	
        }catch (Exception exc){
        	exc.printStackTrace();
        }
        
        //***Start total
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        totals.addCell(makePhrase(SPACE,normal,false));
        totals.addCell(makePhrase("Total Amount:",normal,false));
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        BigDecimal tot = p850.getPurchaseOrderD().getPurchaseOrderTotal();
        tot = tot.add(chargesTotal);
        String totS = nf.format(tot);
        totals.addCell(makePhrase(totS,normal,false));
        //***End total

        //now add misc end of po info
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        totals.addCell(makePhrase(SPACE,normal,false));
        totals.addCell(makePhrase("End of Purchase Order: " + pErpPoNumber,normal,false));
        totals.addCell(makePhrase(SPACE,normal,false));
        totals.addCell(makePhrase(SPACE,normal,false));
        totals.addCell(makePhrase("Total Number Of Pages: " + pPageNumber,normal,false));
        totals.addCell(makePhrase(SPACE,normal,false));
        return totals;
    }

    /**Takes in an instance of @see OutboundEDIRequestData, @see DistributorData, and @see StoreData
     *and converts them into an outbound purchase order pdf.  This document
     *looks very similar to the output when printing from directly from Lawson.
     *@Param pDist the distributor this fax is going to.
     *@Param p850s the purchase orders to translate.
     *@Param pStore the store this po is comming from.
     *@Param pOut the output stream to write the pdf to.
     *@Param pImageName the image to be placed in the upper left corner, if null left off pdf.
     *@throws IOException if any error occurs.
     */
    public void constructPdfPO(DistributorData pDist, OutboundEDIRequestDataVector p850s, StoreData pStore,OutputStream pOut,String pImageName)
    throws IOException{
        if (p850s.size() == 0){
            throw new IllegalArgumentException("OutboundEDIRequestDataVector size cannot be emtpy");
        }
        ArrayList lpos = new ArrayList();
        for(int i=0,len=p850s.size();i<len;i++){
            OutboundEDIRequestData ediReq = (OutboundEDIRequestData) p850s.get(i);
            PropertyData ssmProp = null;
            if(ediReq.getSiteProperties()!= null){
                ssmProp = Utility.getProperty(ediReq.getSiteProperties(), RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
            }
            String siteShipMsg=null;
            if(ssmProp != null){
                siteShipMsg = ssmProp.getValue();
            }
            String acctName=ediReq.getAccountName();

            if(acctName==null){
                acctName = "";
            }
            PdfPurchaseOrder.PdfPoStruct po =
            new PdfPurchaseOrder.PdfPoStruct
                (ediReq.getOrderD(),
                 ediReq.getOrderMetaDV(),
                 ediReq.getPurchaseOrderD(),
                 ediReq.getOrderItemDV(),
                 ediReq.getShipAddr(),
                 ediReq.getBillAddr(),
                 acctName,
                 ediReq.getShipVia(),
                 siteShipMsg,
                 ediReq.getAccountProperties(),
                 ediReq.getOrderPropertyDV());
            lpos.add(po);
        }
        generatePdf(pDist,lpos,pStore,pOut,pImageName);
    }

    /**Takes in an instance of
     *@see OrderData @see PurchaseOrderData,@see OrderItemDataVector
     *@see OrderAddressData, @see DistributorData, and @see StoreData
     *and converts them into an outbound purchase order pdf.  This document
     *looks very similar to the output when printing from directly from Lawson.
     *@Param pDist the distributor this fax is going to.
     *@Param p850s the purchase orders to translate.
     *@Param pStore the store this po is comming from.
     *@Param pOut the output stream to write the pdf to.
     *@Param pImageName the image to be placed in the upper left corner, if null left off pdf.
     *@throws IOException if any error occurs.
     */
    public void constructPdfPO(DistributorData pDist,
    OrderData pOrderData,
    OrderMetaDataVector pOrderMetaDataVector,
    PurchaseOrderData pPurchaseOrderData,
    OrderItemDataVector pOrderItemDataVector,
    OrderAddressData pShipAddress,
    OrderAddressData pBillAddress,
    String pAccountName,
    FreightHandlerView pShipVia,
    String pSiteShippingMessage,
    StoreData pStore,OutputStream pOut,String pImageName)
    throws IOException{
        ArrayList lpo = new ArrayList();
        lpo.add(new PdfPurchaseOrder.PdfPoStruct
                (pOrderData,
                 pOrderMetaDataVector,
                 pPurchaseOrderData,
                 pOrderItemDataVector,
                 pShipAddress,
                 pBillAddress,
                 pAccountName,
                 pShipVia,
                 pSiteShippingMessage,
                 null,null)
                );
        generatePdf(pDist,lpo,pStore,pOut,pImageName);
    }


 public void constructPdfPO(DistributorData pDist,
    OrderData pOrderData,
    OrderMetaDataVector pOrderMetaDataVector,
    PurchaseOrderData pPurchaseOrderData,
    OrderItemDataVector pOrderItemDataVector,
    HashMap pAssetInfo,
    OrderAddressData pShipAddress,
    OrderAddressData pBillAddress,
    String pAccountName,
    FreightHandlerView pShipVia,
    String pSiteShippingMessage,
    StoreData pStore,OutputStream pOut,String pImageName,boolean isSimpleService)
    throws IOException{
        ArrayList lpo = new ArrayList();
        lpo.add(new PdfPurchaseOrder.PdfPoStruct
                (pOrderData,
                 pOrderMetaDataVector,
                 pPurchaseOrderData,
                 pOrderItemDataVector,
                 pAssetInfo,
                 pShipAddress,
                 pBillAddress,
                 pAccountName,
                 pShipVia,
                 pSiteShippingMessage,isSimpleService)
                );
        generatePdf(pDist,lpo,pStore,pOut,pImageName);
    }
    /**
     *Returns the buyer name.  This is the store name for an MLA store, but for
     *the Distributor and manufacturer store it is the bill to name, which will
     *be the account name for most accounts, but is the name of the ship to when
     *the make ship to bill to flag is checked at the account level.
     */
    private String getBuyerName(StoreData pStore,PdfPoStruct p850 ){
        String buyerName = null;
        String storeType = pStore.getStoreType().getValue();
        if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)){
                buyerName = p850.getOrderD().getOrderContactName();
                if(Utility.isSet(p850.getOrderD().getOrderContactPhoneNum())){
                    buyerName += "\n" + p850.getOrderD().getOrderContactPhoneNum();
                }
                if(p850.getBillAddr() != null){
              buyerName = p850.getBillAddr().getShortDesc();
                    if(Utility.isSet(p850.getBillAddr().getAddress1())){
                        buyerName += "\n"+ p850.getBillAddr().getAddress1();
                    }
                    if(Utility.isSet(p850.getBillAddr().getAddress2())){
                        buyerName += "\n"+ p850.getBillAddr().getAddress2();
                    }
                    if(Utility.isSet(p850.getBillAddr().getAddress3())){
                        buyerName += "\n"+ p850.getBillAddr().getAddress3();
                    }
                    if(Utility.isSet(p850.getBillAddr().getAddress4())){
                        buyerName += "\n"+ p850.getBillAddr().getAddress4();
                    }
                    buyerName += "\n"+ p850.getBillAddr().getCity();
                    if (pStore.isStateProvinceRequired() && Utility.isSet(p850.getBillAddr().getStateProvinceCd())){
                      buyerName += "      " + p850.getBillAddr().getStateProvinceCd();
                    }
                    buyerName += SPACE + p850.getBillAddr().getPostalCode();
            }
        }
        if(!Utility.isSet(buyerName)){
            buyerName = pStore.getStoreBusinessName().getValue();
        }
        return buyerName;
    }

    //main worker method.  The public methods will call this method
    //to generate the pdf after the object has been converted.
    private void generatePdf(DistributorData pDist, ArrayList p850s,
    StoreData pStore,OutputStream pOut,String pImageName)
    throws IOException{
        mStore = pStore;
        if(mStore != null && mStore.getStoreType() != null){
            mStoreType = mStore.getStoreType().getValue();
        }
        try{
            //we keep track of the page number rather than useing the built in pdf functionality
            //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
            //first page of that po, and on the 9th page of the pdf.
            int pageNumber = 1;


            String storeType = pStore.getStoreType().getValue();
            if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)){
                useImageLogo=false;
            }



            Phrase footPhrase= makeStoreFooter (pStore);

            HeaderFooter footer = new HeaderFooter(footPhrase,false);
            footer.setAlignment(HeaderFooter.ALIGN_CENTER);

            //setup the borders from the header
            //header.setBorder(borderType);
            footer.setBorder(HeaderFooter.TOP);

            //setup the document
            Document document = new Document(PageSize.A4, 10, 15, 15, 15);
            //Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, pOut);
            //document.setHeader(header);
            document.setFooter(footer);
            document.open();

            for (int j=0,len2=p850s.size();j<len2;j++){
                PdfPoStruct l850 = (PdfPoStruct) p850s.get(j);
                //Verify the integrity of the po data
                TreeSet t = new TreeSet();
                for (int i=0,len=l850.getOrderItemDV().size();i<len;i++){
                    OrderItemData itm = (OrderItemData) l850.getOrderItemDV().get(i);
                    if(itm.getErpPoNum() != null){
                        t.add(itm.getErpPoNum());
                    }
                }
                if (t.size() != 1){
                    throw new IllegalStateException("OutboundEDIRequestData contained more than 1 po.");
                }
                DisplayListSort.sort(l850.getOrderItemDV(),"erpPoLineNum");

                //String lErpPoNumber = l850.getPurchaseOrderD().getErpPoNum();
                String lErpPoNumber = l850.getPurchaseOrderD().getOutboundPoNum();

                Date lErpPoDate = ((OrderItemData) l850.getOrderItemDV().get(0)).getErpPoDate();
                //XXX po locale, or distributor locale?
                Locale lLocale = Utility.parseLocaleCode(l850.getOrderD().getLocaleCd());
                drawPoHeader(document,l850,pDist,lErpPoNumber,lErpPoDate,lLocale,
                pageNumber, getBuyerName(pStore, l850),pImageName, pStore.isStateProvinceRequired());
                //now add the items
                BigDecimal poTotal = new BigDecimal(0);
                for (int i=0,len=l850.getOrderItemDV().size();i<len;i++){
                    document.add(makeBlankLine());
                    OrderItemData itm = (OrderItemData) l850.getOrderItemDV().get(i);
                    Table itmTable;
                    if (l850.isService()) {
                        itmTable = makePoItemElement(itm, l850.getAssetInfo(), lLocale);
                    } else {
                        itmTable = makePoItemElement(itm, lLocale);
                    }

                    //if the item data will not fit onto the page, make a new page, and redraw
                    //the header.
                    if (writer.fitsPage(itmTable,document.bottomMargin())){
                        document.add(itmTable);
                    } else {
                        document.newPage();
                        pageNumber = pageNumber + 1;
                        drawPoHeader(document,l850,pDist,lErpPoNumber,lErpPoDate,lLocale,
                        pageNumber,getBuyerName(pStore, l850),pImageName, pStore.isStateProvinceRequired());
                        document.add(makeBlankLine());
                        document.add(itmTable);
                    }
                    BigDecimal lnTot = getPoCost(itm);
                    lnTot = lnTot.multiply(new BigDecimal(Integer.toString(getPoQty(itm))));
                    poTotal = poTotal.add(lnTot);
                }

                //creates the trailer
                Table trailer = makePoTrailer(document,lErpPoNumber, pageNumber,lLocale,l850,pDist);
                //if it fits on the current page add it
                if (writer.fitsPage(trailer)){
                    document.add(trailer);
                }else{
                    //create a brand new page just for the trailer information if it
                    //doesn't fit on the page
                    document.newPage();
                    pageNumber = pageNumber + 1;
                    drawPoHeader(document,l850,pDist,lErpPoNumber,lErpPoDate,lLocale,
                    pageNumber,getBuyerName(pStore, l850),pImageName, pStore.isStateProvinceRequired());
                    //regenerate the trailer to get the correct page number
                    trailer = makePoTrailer(document,lErpPoNumber, pageNumber,lLocale,l850, pDist);
                    document.add(trailer);
                }

                if (j<len2){
                    pageNumber = 1;
                    document.newPage();
                }
            }
            //close out the document
            document.close();
        }catch (DocumentException e){
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    /**
     *Private inner class to represent the data the po needs to print, and the
     *relationship between the data.  This makes converting between different
     *objects (outboundEdiRequests, and PurchaseOrderDesc/orderItemVectors, etc.)
     *easier
     */
    public class PdfPoStruct{
        private PurchaseOrderData mPurchaseOrderD;
        private OrderData mOrderD;
        private OrderItemDataVector mOrderItemDV;
        private OrderAddressData mShipAddr;
        private OrderAddressData mBillAddr;
        private String mAccountName;
        private FreightHandlerView mShipVia;
        private OrderMetaDataVector mOrderMetaDV;
        private String mSiteShipMsg;
        private boolean mService;
        private HashMap mAssetInfo;
        private PropertyDataVector mAcctProps;
        private OrderPropertyDataVector mOrderProps;

        public PdfPoStruct(OrderData pOrderD,
                           OrderMetaDataVector pOmdv,
                           PurchaseOrderData pPurchaseOrderD,
                           OrderItemDataVector pOrderItemDV,
                           OrderAddressData pShipAddr,
                           OrderAddressData pBillAddr,
                           String pAccountName,
                           FreightHandlerView pShipVia,
                           String pSiteShipMsg,
                           PropertyDataVector pAcctProps,
                           OrderPropertyDataVector pOrderProps)
        {
            mPurchaseOrderD = pPurchaseOrderD;
            mOrderD = pOrderD;
            mOrderMetaDV = pOmdv;
            mOrderItemDV = pOrderItemDV;
            mShipAddr = pShipAddr;
            mBillAddr = pBillAddr;
            mAccountName = pAccountName;
            mShipVia = pShipVia;
            mSiteShipMsg = pSiteShipMsg;
            mService=false;
            mAssetInfo=null;
            mAcctProps = pAcctProps;
            mOrderProps = pOrderProps;
        }

         public PdfPoStruct(OrderData pOrderD,
                           OrderMetaDataVector pOmdv,
                           PurchaseOrderData pPurchaseOrderD,
                           OrderItemDataVector pOrderItemDV,
                           HashMap pAssetInfo,
                           OrderAddressData pShipAddr,
                           OrderAddressData pBillAddr,
                           String pAccountName,
                           FreightHandlerView pShipVia,
                           String pSiteShipMsg,boolean isService)
        {
            mPurchaseOrderD = pPurchaseOrderD;
            mOrderD = pOrderD;
            mOrderMetaDV = pOmdv;
            mOrderItemDV = pOrderItemDV;
            mShipAddr = pShipAddr;
            mBillAddr = pBillAddr;
            mAccountName = pAccountName;
            mShipVia = pShipVia;
            mSiteShipMsg = pSiteShipMsg;
            mService=isService;
            mAssetInfo=pAssetInfo;
        }

        private PurchaseOrderData getPurchaseOrderD(){
            return mPurchaseOrderD;
        }
        private OrderData getOrderD(){
            return mOrderD;
        }
        private OrderItemDataVector getOrderItemDV(){
            return mOrderItemDV;
        }
        private OrderMetaDataVector getOrderMetaDV(){
            return mOrderMetaDV;
        }
        private OrderAddressData getShipAddr(){
            return mShipAddr;
        }
        private OrderAddressData getBillAddr(){
            return mBillAddr;
        }
        private String getAccountName(){
            return mAccountName;
        }
        private FreightHandlerView getShipVia(){
            return mShipVia;
        }
        private String getSiteShipMsg(){
            return mSiteShipMsg;
        }

        public boolean isService() {
            return mService;
        }

        public HashMap getAssetInfo() {
            return mAssetInfo;
        }

        /**
         * May return null, only implemented for auto faxing right now
         * @return
         */
        public PropertyDataVector getAccountProperties() {
        	return mAcctProps;
        }

        /**
         * May return null, only implemented for auto faxing right now
         * @return
         */
        public OrderPropertyDataVector getOrderProperties() {
        	return mOrderProps;
        }
    }
}
