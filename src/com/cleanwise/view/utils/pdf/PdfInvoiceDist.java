/*
 * PdfInvoice.java
 *
 * Created on May 10, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;
import com.cleanwise.view.logic.StoreVendorInvoiceLogic;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.*;

import java.awt.Color; //used by the lowagie/iText api, we are not actually using the awt toolkit here
import java.util.Locale;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Calendar;
import java.io.OutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.TreeSet;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.text.DateFormat;

/**
 *Constructs a pdf document in the form of a pdf document.  It understands the various
 *propriatary objects, and uses them to construct an invoice document.
 * @author  bstevens
 */
public class PdfInvoiceDist extends PdfSuper {
    //static configuration variables
    //configures the percentage widths of item columns in the document
    private static int itmCoumnWidth[] = {5,7,12,46,10,10,10};
    private Locale mLocale;
    private Document mDocument;
    private DistributorData mDist;
    private int mPageNumber;

    /** Creates a new instance of PdfPurchaseOrder */
    public PdfInvoiceDist() {
        // Reset the normal font to a larger size.
        super.setNormalFontSize(12);
    }



    private void drawInvoiceRightSideHeader(PdfPTable headerInfo, String label, String value)
    throws DocumentException {
        headerInfo.addCell(new Phrase());
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        headerInfo.addCell(new Phrase());
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        headerInfo.addCell(makePhrase(label,smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + value,normal,true));
    }

    private void drawInvoiceHeader(PdfInvoiceDistRequest pInvoice)
    throws DocumentException {
        DateFormat lDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,mLocale);

        InvoiceDistData lInvoiceDist = pInvoice.getInvoice().getInvoiceDist();
        if(lInvoiceDist == null){
            lInvoiceDist = InvoiceDistData.createValue();
        }
        OrderData lOrder = pInvoice.getInvoice().getOrderData();

        //deal with po header info
        //construct the table/cells with all of the header information
        //must be the pdf specific type or the table will not use the full width of the page.
        //unfortuanately this means we are limited to PDF rendering only.
        PdfPTable headerInfo = new PdfPTable(4);
        headerInfo.setWidthPercentage(100);
        headerInfo.getDefaultCell().setBorder(borderType);
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        //add content, reads right to left, not vertically with content
        headerInfo.addCell(makePhrase("Remit To: ",smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        String remit;
        if(Utility.isSet(lInvoiceDist.getRemitTo())){
            remit = lInvoiceDist.getRemitTo();
        }else{
            remit = "";
        }
        headerInfo.addCell(makePhrase(remit,smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        headerInfo.addCell(makePhrase("PAGE: ",smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + Integer.toString(mPageNumber),normal,true));

        drawInvoiceRightSideHeader(headerInfo,"INVOICE: ", lInvoiceDist.getInvoiceNum());





        if(pInvoice.getInvoice().getOrderData() != null && Utility.isSet(pInvoice.getInvoice().getOrderData().getRequestPoNum())){
            drawInvoiceRightSideHeader(headerInfo,"ENTERED PO: ", pInvoice.getInvoice().getOrderData().getRequestPoNum());
            drawInvoiceRightSideHeader(headerInfo,"SYSTEM PO: ", lInvoiceDist.getErpPoNum());
        }else{
            drawInvoiceRightSideHeader(headerInfo,"ENTERED PO: ", SPACE);
            drawInvoiceRightSideHeader(headerInfo,"SYSTEM PO: ", lInvoiceDist.getErpPoNum());
        }

        Date date = lInvoiceDist.getInvoiceDate();
        if(date != null){
            String dateS = lDateFormatter.format(date);
            drawInvoiceRightSideHeader(headerInfo,"INVOICE DATE: ", dateS);
        }else{
            drawInvoiceRightSideHeader(headerInfo,"INVOICE DATE: ", SPACE);
        }



        //add the content
        mDocument.add(headerInfo);


        mDocument.add(makeLine());

        if ( mPageNumber == 1 ) {
            //deal with the billing and shipping information header
            PdfPTable addrHeader = new PdfPTable(2);
            addrHeader.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            addrHeader.setWidthPercentage(90);
            addrHeader.getDefaultCell().setBorder(borderType);



            addrHeader.addCell(makePhrase("REMIT TO:",smallHeading,false));
            addrHeader.addCell(makePhrase("SHIP TO:",smallHeading,false));
            mDocument.add(addrHeader);



            //deal with the billing and shipping information header
            PdfPTable addresses = new PdfPTable(2);
            addresses.setWidthPercentage(90);
            addresses.getDefaultCell().setBorder(borderType);
            addresses.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            addresses.getDefaultCell().setPadding(-1);
            String value;
            //bill to name
            if(mDist != null && mDist.getBusEntity() != null && mDist.getBusEntity().getShortDesc() != null){
                value = mDist.getBusEntity().getShortDesc();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //ship to name
            if(lInvoiceDist.getShipToName() != null){
                value = lInvoiceDist.getShipToName();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //bill to line 1
            if(mDist != null && mDist.getBusEntity() != null && mDist.getBillingAddress().getAddress1() != null){
                value = mDist.getBillingAddress().getAddress1();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //ship to line 1
            if(lInvoiceDist.getShipToAddress1() != null){
                value = lInvoiceDist.getShipToAddress1();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //bill to line 2
            if(mDist != null && mDist.getBusEntity() != null && mDist.getBillingAddress().getAddress2() != null){
                value = mDist.getBillingAddress().getAddress2();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //ship to line 2
            if(lInvoiceDist.getShipToAddress2() != null){
                value = lInvoiceDist.getShipToAddress2();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //bill to line 3
            if(mDist != null && mDist.getBusEntity() != null && mDist.getBillingAddress().getAddress3() != null){
                value = mDist.getBillingAddress().getAddress3();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //ship to line 3
            if(lInvoiceDist.getShipToAddress3() != null){
                value = lInvoiceDist.getShipToAddress3();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //bill to line 4
            if(mDist != null && mDist.getBusEntity() != null && mDist.getBillingAddress().getAddress4() != null){
                value = mDist.getBillingAddress().getAddress4();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));
            //ship to line 4
            if(lInvoiceDist.getShipToAddress4() != null){
                value = lInvoiceDist.getShipToAddress4();
            }else{
                value = "";
            }
            addresses.addCell(makePhrase(value,normal,false));


            //--------------add city state and zip concatontions-------------
            //bill to line city state and zip
            String ts;
            if(mDist != null && mDist.getBillingAddress() != null){
                 ts = (mDist.getBillingAddress().getCity()==null)? " ":
                    mDist.getBillingAddress().getCity() + SPACE;
                ts += (mDist.getBillingAddress().getStateProvinceCd()==null)? " ":
                    mDist.getBillingAddress().getStateProvinceCd() + SPACE;
                ts += (mDist.getBillingAddress().getPostalCode()==null)? " ":
                    mDist.getBillingAddress().getPostalCode() + SPACE;
            }else{
                ts = "";
            }
            addresses.addCell(makePhrase(ts,normal,false));

            //ship to line city state and zip
            ts = (lInvoiceDist.getShipToCity()==null)? " ":
                lInvoiceDist.getShipToCity() + SPACE;
            ts += (lInvoiceDist.getShipToState()==null)? " ":
                lInvoiceDist.getShipToState() + SPACE;
            ts += (lInvoiceDist.getShipToPostalCode()==null)? " ":
                lInvoiceDist.getShipToPostalCode() + SPACE;
            addresses.addCell(makePhrase(ts,normal,false));


            //add the address information to the document
            mDocument.add(addresses);


            mDocument.add(makeLine());


            //If the order is present add some miscellanious data aout it
            if(lOrder != null && lOrder.getOrderId() > 0){
                PdfPTable misc = new PdfPTable(1);
                misc.setWidthPercentage(85);
                misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                misc.getDefaultCell().setBorder(borderType);
                misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);
                String m1 = "Currency: " + lOrder.getCurrencyCd();
                m1 = m1 + ", Conf # " + lOrder.getOrderNum();
                if(Utility.isSet(lOrder.getComments())){
                    m1 = m1 + ", PO # " + lOrder.getComments();
                }
                misc.addCell(makePhrase(m1,normal,true));
                StringBuffer poline = new StringBuffer();
                boolean printPoLine = false;
                if(Utility.isSet(lOrder.getRequestPoNum())){
                    poline.append(lOrder.getRequestPoNum());
                    printPoLine = true;
                }
                if(Utility.isSet(lOrder.getRefOrderNum())){
                    if(printPoLine){
                        poline.append("/");
                    }
                    poline.append(lOrder.getRefOrderNum());
                    printPoLine = true;
                }
                if(printPoLine){
                    misc.addCell(makePhrase(poline.toString(),normal,false));
                }
                if(Utility.isSet(lOrder.getComments())){
                	misc.addCell(makePhrase("Shipping Comments: "+lOrder.getComments(),normal,false));
                }
                mDocument.add(misc);

            }

            //add notes
             if(pInvoice.getNotes() != null && !pInvoice.getNotes().isEmpty()){
                PdfPTable notes = new PdfPTable(1);
                notes.setWidthPercentage(85);
                notes.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_CENTER);
                notes.getDefaultCell().setBorder(borderType);
                notes.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);
                notes.addCell(makePhrase("Notes:",normalBold,false));
                Iterator it = pInvoice.getNotes().iterator();
                while(it.hasNext()){
                    OrderPropertyData note = (OrderPropertyData) it.next();
                    if(note != null && Utility.isSet(note.getValue())){
                        notes.addCell(makePhrase(note.getValue(),normal,false));
                    }
                }
                mDocument.add(notes);
             }

        } // end hader only printed on page 1


        //add the item header line
        PdfPTable itemHeader = new PdfPTable(itmCoumnWidth.length);
        itemHeader.setWidthPercentage(100);
        itemHeader.setWidths(itmCoumnWidth);
        itemHeader.getDefaultCell().setBorderWidth(2);
        itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
        itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
        itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
        itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
        itemHeader.addCell(makePhrase("LINE",itemHeading,false));
        itemHeader.addCell(makePhrase("ORD QTY",itemHeading,false));
        itemHeader.addCell(makePhrase("QTY/UOM/PACK",itemHeading,false));
        itemHeader.addCell(makePhrase("ITEM",itemHeading,false));
        itemHeader.addCell(makePhrase("PO PRICE",itemHeading,false));
        itemHeader.addCell(makePhrase("PRICE",itemHeading,false));
        itemHeader.addCell(makePhrase("NET",itemHeading,false));
        mDocument.add(itemHeader);
    }


    //utility function to make an item Element.
    private Table makeItemElement(OrderItemDescData pItm) throws DocumentException{
        OrderItemData oItm = pItm.getOrderItem();
        boolean isOnInvoice = true;
        InvoiceDistDetailData iItm = pItm.getWorkingInvoiceDistDetailData();
        if(iItm == null){
            iItm = InvoiceDistDetailData.createValue();
        }
        if(iItm.getDistItemQuantity() == 0){
            isOnInvoice = false;
        }
        String value;

        NumberFormat nf = NumberFormat.getInstance(mLocale);
        //nf = nf.getCurrencyInstance().getCurrency().;
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        Table itmTbl = new PTable(itmCoumnWidth.length);
        itmTbl.setWidth(100);
        itmTbl.setWidths(itmCoumnWidth);
        itmTbl.getDefaultCell().setBorder(borderType);

        //add line information
        if(oItm == null || oItm.getErpPoLineNum() == 0){
            value = SPACE;
        }else{
            value = Integer.toString(oItm.getErpPoLineNum());
        }
        itmTbl.addCell(makePhrase(value,normal,false));

        //add order qty information
        if(oItm != null){
            value = Integer.toString(oItm.getTotalQuantityOrdered());
        }else{
            value = "0";
        }
        itmTbl.addCell(makePhrase(value,normal,false));

        //add qty/uom information
        if(isOnInvoice || iItm.getDistItemQtyReceived() != 0){
            value = Integer.toString(iItm.getDistItemQuantity());
            value += (iItm.getDistItemUom()==null)? "":"/" + iItm.getDistItemUom();
            value += (iItm.getDistItemPack()==null)? "":"/" + iItm.getDistItemPack();
            Phrase lQty;
            if(iItm.getDistItemQuantity() != iItm.getDistItemQtyReceived() && iItm.getDistItemQtyReceived() != 0){
            	if(isOnInvoice){
            		lQty = makePhrase(value,normal,true);
            		lQty.add(makeChunk("Recvd "+Integer.toString(iItm.getDistItemQtyReceived()),normal,false));
            	}else{
            		lQty = makePhrase("Recvd "+Integer.toString(iItm.getDistItemQtyReceived()),normal,false);
            	}
            }else{
                lQty = makePhrase(value,normal,false);
            }
            itmTbl.addCell(lQty);
        }else{
            itmTbl.addCell(makePhrase(SPACE,normal,false));
        }


        //add sku and decription phrase
        Phrase lSku = makePhrase("Item#: " + ((oItm==null||oItm.getItemSkuNum()==0)?SPACE:Integer.toString(oItm.getItemSkuNum())),normal,true);
        lSku.add(makeChunk(iItm.getDistItemShortDesc(),normal,true));
        lSku.add(makeChunk("Ven Item#: " + ((!Utility.isSet(iItm.getDistItemSkuNum()))?SPACE:iItm.getDistItemSkuNum()),normal,true));
        itmTbl.addCell(lSku);



        //align these to the right so they line up with the totals at the bottom of the invoice
        itmTbl.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);

        //add order costing information
        if(oItm == null){
            value = SPACE;
        }else{
            value = nf.format(oItm.getDistItemCost());
        }
        itmTbl.addCell(makePhrase(value,normal,false));

        //add distributor cost information
        if(isOnInvoice || (iItm.getItemReceivedCost() != null && (iItm.getAdjustedCost() == null || iItm.getAdjustedCost().compareTo(iItm.getItemReceivedCost())!=0))){
            value = nf.format(Utility.getBestCostFromInvoiceDistDetail(iItm));
            Phrase cost;
            if(iItm.getItemReceivedCost() != null && (iItm.getAdjustedCost() == null || iItm.getAdjustedCost().compareTo(iItm.getItemReceivedCost())!=0)){
            	if(isOnInvoice){
	                cost = makePhrase(value,normal,true);
	                value = nf.format(iItm.getItemReceivedCost());
	                cost.add(makeChunk("Recvd "+value, normal,true));
            	}else{
            		value = nf.format(iItm.getItemReceivedCost());
            		cost = makePhrase("Recvd "+value, normal,true);
            	}
            }else{
                cost = makePhrase(value,normal,false);
            }
            itmTbl.addCell(cost);

            //get, add, and calculate line item total
            if(iItm.getLineTotal() == null){
                value = nf.format(Utility.getBestCostFromInvoiceDistDetail(iItm).multiply(new BigDecimal(iItm.getDistItemQuantity())));
            }else{
                value = nf.format(iItm.getLineTotal());
            }
            itmTbl.addCell(makePhrase(value,normal,false));
        }else{
            itmTbl.addCell(makePhrase(SPACE,normal,false));
            itmTbl.addCell(makePhrase(SPACE,normal,false));
        }

        itmTbl.addCell(makePhrase("",normal,true));
        itmTbl.addCell(makePhrase("",normal,true));
        return itmTbl;
    }




    /**returns the trailer so it can be drawn onto the document*/
    private Table makeInvoiceTrailer(PdfInvoiceDistRequest pInvoice)
    throws DocumentException {
        InvoiceDistData lInvoice = pInvoice.getInvoice().getInvoiceDist();
        if(lInvoice == null){
            lInvoice = InvoiceDistData.createValue();
        }
        String value;
        NumberFormat nf = NumberFormat.getInstance(mLocale);
        //nf = nf.getCurrencyInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        //add trailer information
        Table totalsMain = new PTable(1);
        totalsMain.setWidth(100);
        totalsMain.getDefaultCell().setBorder(borderType);
//iText 1.2.2        totalsMain.addCell(makeLine());
        totalsMain.insertTable((Table)makeLine(totalsMain));

        Table totals = new PTable(3);
        totals.setWidth(100);
        int width[] = {25,50,25};
        totals.setWidths(width);
        totals.getDefaultCell().setBorder(borderType);
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        //add dollar totals
        BigDecimal netDue = new BigDecimal(0.00);
        //frieght tax etc
        netDue = netDue.add(createTotalCell(lInvoice.getFreight(),"Freight:",totals,nf));
        createTotalCell(pInvoice.getInvoice().getVendorRequestedFreight(), "Requested Freight:",totals,nf);
        netDue = netDue.add(createTotalCell(lInvoice.getMiscCharges(), "Misc Charges:",totals,nf));
        createTotalCell(pInvoice.getInvoice().getVendorRequestedMiscCharges(), "Requested Misc Charges:",totals,nf);
        netDue = netDue.add(createTotalCell(lInvoice.getSalesTax(), "Tax:",totals,nf));
        createTotalCell(pInvoice.getInvoice().getVendorRequestedTax(), "Requested Tax:",totals,nf);
        netDue = netDue.add(createTotalCell(lInvoice.getDiscounts(), "Discount:",totals,nf));
        createTotalCell(pInvoice.getInvoice().getVendorRequestedDiscount(), "Requested Discount:",totals,nf);
        netDue = netDue.add(createTotalCell(lInvoice.getSubTotal(), "Sub Total:",totals,nf));
        createTotalCell(netDue, "Net Amount Due:",totals,nf);


//iText 1.2.2        totalsMain.addCell(totals);
        totalsMain.insertTable(totals);
        //now add misc end of po info
        Table misc = new PTable(1);
        misc.setWidth(100);
        misc.getDefaultCell().setBorder(borderType);
        misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        misc.addCell(makePhrase("End of Invoice: " + lInvoice.getInvoiceNum(),normal,false));
        misc.addCell(makePhrase("Total Number Of Pages: " + mPageNumber,normal,false));

//iText 1.2.2        totalsMain.addCell(misc);
        totalsMain.insertTable(misc);
        return totalsMain;
    }

    /**
     *Helper method to add a total record and keep track of the running total
     */
    private BigDecimal createTotalCell(BigDecimal bdVal, String label,PdfPTable totals, NumberFormat nf){
        String value;
        totals.addCell(makePhrase(SPACE,normal,false));
        totals.addCell(makePhrase(label,normal,false));
        if(bdVal == null){
            bdVal = ZERO;
        }
        value = nf.format(bdVal);
        totals.addCell(makePhrase(value,normal,false));
        return bdVal;
    }
    /**
     *Helper method to add a total record and keep track of the running total
     */
    private BigDecimal createTotalCell(BigDecimal bdVal, String label,Table totals, NumberFormat nf) throws DocumentException{
        String value;
        totals.addCell(makePhrase(SPACE,normal,false));
        totals.addCell(makePhrase(label,normal,false));
        if(bdVal == null){
            bdVal = ZERO;
        }
        value = nf.format(bdVal);
        totals.addCell(makePhrase(value,normal,false));
        return bdVal;
    }

    /**
     *Creates a replicate of the store footer but uses the distributor information
     */
    private Phrase makeDistributorFooter(DistributorData pDistributor) {
        if(pDistributor == null){
            return makeFooter("UNKNOWN",SPACE,SPACE, SPACE, SPACE, SPACE, SPACE,SPACE);
        }
        String addr;
        String city;
        String state;
        String zip;
        if(pDistributor.getBillingAddress() == null){
            addr = "";
            city = "";
            state = "";
            zip = "";
        }else{
            addr = pDistributor.getBillingAddress().getAddress1() + SPACE + pDistributor.getBillingAddress().getAddress2();
            if(pDistributor.getBillingAddress().getAddress1() == null && pDistributor.getBillingAddress().getAddress2() == null){
                addr="";
            }else if(pDistributor.getBillingAddress().getAddress1() != null && pDistributor.getBillingAddress().getAddress2() != null){
                addr=pDistributor.getBillingAddress().getAddress1() + SPACE + pDistributor.getBillingAddress().getAddress2();
            }else if(pDistributor.getBillingAddress().getAddress1() != null){
                addr=pDistributor.getBillingAddress().getAddress1();
            }else{
                addr=pDistributor.getBillingAddress().getAddress2();
            }
            city = pDistributor.getBillingAddress().getCity();
            state = pDistributor.getBillingAddress().getStateProvinceCd();
            zip = pDistributor.getBillingAddress().getPostalCode();
        }

        String name;
        if(pDistributor.getBusEntity() != null && Utility.isSet(pDistributor.getBusEntity().getShortDesc())){
            name = pDistributor.getBusEntity().getShortDesc();
        }else{
            name = "UNKNOWN";
        }

        String phone;
        if(pDistributor.getPrimaryPhone() != null && Utility.isSet(pDistributor.getPrimaryPhone().getPhoneNum())){
            phone = pDistributor.getPrimaryPhone().getPhoneNum();
        }else{
            phone = SPACE;
        }

        String fax;
        if(pDistributor.getPrimaryFax() != null && Utility.isSet(pDistributor.getPrimaryFax().getPhoneNum())){
            fax = pDistributor.getPrimaryFax().getPhoneNum();
        }else{
            fax = SPACE;
        }


        return makeFooter(name,addr,city,state,zip,phone,fax,SPACE);
    }



    /**Takes in an instance of
     *@see OrderData @see InvoiceCustData,@see InvoiceCustDetailDataVector
     *and @see StoreData and converts them into an outbound invoice pdf.  This document
     *looks very similar to the output when printing directly from Lawson.
     *@Param pInvoice the invoice to translate.
     *@Param pInvoiceDetail the invoice detail belonging to the supplied invoice.
     *@Param pStore the store this po is comming from.
     *@Param pOut the output stream to write the pdf to.
     *@Param pImageName the image to be placed in the upper left corner, if null left off pdf.
     *@throws IOException if any error occurs.
     */
    public void constructPdf(PurchaseOrderStatusDescDataView pInvoice,OrderItemDescDataVector pInvoiceItems, OrderPropertyDataVector pInvoiceNotes, DistributorData pDist, OutputStream pOut)
    throws IOException{
        ArrayList lInvoice = new ArrayList();
        lInvoice.add(new PdfInvoiceDistRequest(pInvoice,pInvoiceItems,pInvoiceNotes,pDist));
        generatePdf(lInvoice,pOut);
    }


    /**Takes in a list of PdfInvoiceDistRequest which can be obtained by the
     * @see createPdfInvoiceDistRequest object
     *@throws IOException if any error occurs.
     */
    public void constructPdf(ArrayList pPdfInvoiceDistRequestList, OutputStream pOut)
    throws IOException{
        generatePdf(pPdfInvoiceDistRequestList,pOut);
    }


    public PdfInvoiceDistRequest createPdfInvoiceDistRequest(PurchaseOrderStatusDescDataView pInvoice,OrderItemDescDataVector pInvoiceItems, OrderPropertyDataVector pInvoiceNotes, DistributorData pDist){
    	return new PdfInvoiceDistRequest(pInvoice,pInvoiceItems,pInvoiceNotes,pDist);
    }

    //main worker method.  The public methods will call this method
    //to generate the pdf after the object has been converted.
    private void generatePdf(ArrayList pData, OutputStream pOut)
    throws IOException{
        try{
            //we keep track of the page number rather than useing the built in pdf functionality
            //as we have to maintain pages between invoices, so we may be on the 3rd invoice, but on the
            //first page of that invoice, and on the 9th page of the pdf.
            mPageNumber = 1;
            //create the header and footer
            Phrase headPhrase = new Phrase(makeChunk("REPRINT INVOICE",heading,true));
            HeaderFooter header = new HeaderFooter(headPhrase,false);
            header.setAlignment(HeaderFooter.ALIGN_LEFT);

            //Phrase footPhrase= makeDistributorFooter (pDist);
            //HeaderFooter footer = new HeaderFooter(footPhrase,false);

            //footer.setAlignment(HeaderFooter.ALIGN_CENTER);

            //setup the borders from the header
            header.setBorder(borderType);
            //footer.setBorder(HeaderFooter.TOP);

            //setup the document
            mDocument = new Document(PageSize.A4, 10, 15, 30, 15);
            //Document mDocument = new Document();
            PdfWriter writer = PdfWriter.getInstance(mDocument, pOut);
            mDocument.setHeader(header);
            //mDocument.setFooter(footer);
            mDocument.open();

            for (int j=0,len2=pData.size();j<len2;j++){
                PdfInvoiceDistRequest lInvoice = (PdfInvoiceDistRequest) pData.get(j);
                mDist = lInvoice.getDistributor();
                //assume sorted list
                //DisplayListSort.sort(lInvoice.getInvoiceItems(),"???");

                mLocale = Utility.parseLocaleCode("en_US");
                if(lInvoice.getInvoice().getOrderData() != null && lInvoice.getInvoice().getOrderData().getLocaleCd() != null){
                    mLocale = Utility.parseLocaleCode(lInvoice.getInvoice().getOrderData().getLocaleCd());
                }

                drawInvoiceHeader(lInvoice);
                //now add the items
                BigDecimal poTotal = new BigDecimal(0);
                Iterator it = lInvoice.getInvoiceItems().iterator();
                while(it.hasNext()){
                    OrderItemDescData itm = (OrderItemDescData) it.next();
                    mDocument.add(makeBlankLine());

                    Table itmTable = makeItemElement(itm);
                    //if the item data will not fit onto the page, make a new page, and redraw
                    //the header.
                    if (writer.fitsPage(itmTable,mDocument.bottomMargin())){
                        mDocument.add(itmTable);
                    } else {
                        mDocument.newPage();
                        mPageNumber = mPageNumber + 1;
                        drawInvoiceHeader(lInvoice);
                        mDocument.add(makeBlankLine());
                        mDocument.add(itmTable);
                    }
                }


                //creates the trailer
                Table trailer = makeInvoiceTrailer(lInvoice);
                Table tempTrailer = new PTable(1);
                tempTrailer.setWidth(100);
//iText 1.2.2                tempTrailer.addCell(trailer);
//iText 1.2.2                tempTrailer.addCell(makeLine());
//iText 1.2.2                tempTrailer.addCell(makeLine());
                tempTrailer.insertTable(trailer);
                tempTrailer.insertTable((Table)makeLine(tempTrailer));
                tempTrailer.insertTable((Table)makeLine(tempTrailer));

                //if it fits on the current page add it
                if (writer.fitsPage(trailer)){
                    while(writer.fitsPage(tempTrailer)){
                        mDocument.add(makeBlankLine());
                    }
                    mDocument.add(trailer);
                }else{
                    //create a brand new page just for the trailer information if it
                    //doesn't fit on the page
                    mDocument.newPage();
                    mPageNumber = mPageNumber + 1;
                    drawInvoiceHeader(lInvoice);
                    //regenerate the trailer to get the correct page number
                    while(writer.fitsPage(tempTrailer)){
                        mDocument.add(makeBlankLine());
                    }
                    //redraw the trailer as the page number will have changed
                    trailer = makeInvoiceTrailer(lInvoice);
                    mDocument.add(trailer);
                }

                if (j<len2){
                    mPageNumber = 1;
                    mDocument.newPage();
                }
            }
            //close out the mDocument
            mDocument.close();
        }catch (DocumentException e){
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    /**
     *Private inner class to represent the data the po needs to print, and the
     *relationship between the data.  This simplifies converting between different
     *objects.
     */
    public class PdfInvoiceDistRequest{
        private PurchaseOrderStatusDescDataView mInvoice;
        private OrderItemDescDataVector mInvoiceItems;
        private OrderPropertyDataVector mNotes;
        private DistributorData mDist;

        private PdfInvoiceDistRequest(PurchaseOrderStatusDescDataView pInvoice, OrderItemDescDataVector pInvoiceItems, OrderPropertyDataVector pNotes,DistributorData pDist) {
            mInvoice = pInvoice;
            mInvoiceItems = pInvoiceItems;
            mNotes = pNotes;
            mDist = pDist;
        }

        private PurchaseOrderStatusDescDataView getInvoice(){
            return mInvoice;
        }

        private OrderItemDescDataVector getInvoiceItems(){
            return mInvoiceItems;
        }

        private OrderPropertyDataVector getNotes(){
            return mNotes;
        }

        private DistributorData getDistributor(){
        	return mDist;
        }
    }
}
