/*

 * PdfInvoice.java

 *

 * Created on May 10, 2002, 5:36 PM

 */



package com.cleanwise.view.utils.pdf;

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

import java.util.Collections;

import java.util.Comparator;



/**

 *Constructs a pdf document in the form of a pdf document.  It understands the various

 *propriatary objects, and uses them to construct an invoice document.

 * @author  bstevens

 */

public class PdfInvoice extends PdfSuper {

    //static configuration variables

    //configures the percentage widths of item columns in the document

    private static int itmCoumnWidth[] = {5,15,55,10,15};





    /** Creates a new instance of PdfPurchaseOrder */

    public PdfInvoice() {

        // Reset the normal font to a larger size.

        super.setNormalFontSize(12);

    }

    //utility function to make an item Element.

    private Table makeItemElement(InvoiceAbstractionDetailView pItm,Locale pLocale) throws DocumentException{

        NumberFormat nf = NumberFormat.getInstance(pLocale);

        //nf = nf.getCurrencyInstance().getCurrency().;

        nf.setMinimumFractionDigits(2);

        nf.setMaximumFractionDigits(2);

        Table itmTbl = new PTable(5,false);
        itmTbl.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        itmTbl.setWidth(100);

        itmTbl.setWidths(itmCoumnWidth);


        //***********First add the cleanwise sku information and general item information

        itmTbl.addCell(makePhrase(Integer.toString(pItm.getLineNumber()),normal,false));
      
        String uom;

        uom = pItm.getItemUom();

        String pack;



        pack = pItm.getItemPack();

        if (pack == null){

            itmTbl.addCell(makePhrase(Integer.toString(pItm.getItemQuantity()) + SPACE + uom,normal,false));
        
        }else{

            itmTbl.addCell(makePhrase(Integer.toString(pItm.getItemQuantity()) + SPACE + uom + SPACE + pack,normal,false));
        	
        }



        //get and add sku and decription phrase

        Phrase lSku = makePhrase("Item #: " + Integer.toString(pItm.getItemSkuNum()),normal,true);

        lSku.add(makeChunk(pItm.getItemShortDesc(),normal,false));

        itmTbl.addCell(lSku);

        //align these to the right so they line up with the totals at the bottom of the invoice

        itmTbl.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);

        //get and add itemCost

        pItm.getCustContractPrice().setScale(2,BigDecimal.ROUND_HALF_UP);

        String lnCstS = nf.format(pItm.getCustContractPrice());

        itmTbl.addCell(makePhrase(lnCstS,normal,false));



        //get, add, and calculate line item total

        pItm.getLineTotal().setScale(2,BigDecimal.ROUND_HALF_UP);

        String lnTot = nf.format(pItm.getLineTotal());

        itmTbl.addCell(makePhrase(lnTot,normal,false));





        //BigDecimal lnTot = pItm.getDistItemCost();

        //lnTot = lnTot.multiply(new BigDecimal(Integer.toString(pItm.getTotalQuantityOrdered())));

        //String lnTotS = nf.format(lnTot);

        //itmTbl.addCell(makePhrase(lnTotS,normal,false));







        //itmTbl.addCell(makePhrase("",normal,true));

        //itmTbl.addCell(makePhrase("",normal,true));

        return itmTbl;

    }

    private void drawInvoiceHeader(Document document,InvoiceStruct pInvoice,

    Locale pLocale, int pPageNumber,String pSenderName, String pImageName)

    throws DocumentException {

        DateFormat lDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,pLocale);

        //add the logo

        try{

            Image i = Image.getInstance(pImageName);

            float x = (document.getPageSize().getWidth() / 2) - ((i.getWidth() / 2)+20);

            float y = document.getPageSize().getHeight() - i.getHeight() - 5;

            i.setAbsolutePosition(x,y);

            document.add(i);

        }catch (Exception e){

            e.printStackTrace();

        }

        //Element e = makeChunk("INVOICE", normalBold, false);

        //document.add(e);



        //deal with po header info

        //construct the table/cells with all of the header information

        //must be the pdf specific type or the table will not use the full width of the page.

        //unfortuanately this means we are limited to PDF rendering only.

        PdfPTable headerInfo = new PdfPTable(4);

        headerInfo.setWidthPercentage(100);

        headerInfo.getDefaultCell().setBorder(borderType);

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);











        //add content, reads right to left, not vertically with content

        headerInfo.addCell(makePhrase("Remit To Address: ",smallHeading,true));

        //XXX hard coded for the moment

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        headerInfo.addCell(makePhrase("232639 Momentum Place ",smallHeading,true));

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        headerInfo.addCell(makePhrase("PAGE: ",smallHeading,true));

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        headerInfo.addCell(makePhrase(SPACE + Integer.toString(pPageNumber),normal,true));



        headerInfo.addCell(new Phrase());

        //XXX hard coded for the moment

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        headerInfo.addCell(makePhrase("Chicago, IL 60689-5326",smallHeading,true));



        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        String invType = pInvoice.getInvoiceCust().getInvoiceType();
        if(invType.equals(RefCodeNames.INVOICE_TYPE_CD.IN)){
        	headerInfo.addCell(makePhrase("INVOICE"+": ",smallHeading,true));
        }else if(invType.equals(RefCodeNames.INVOICE_TYPE_CD.CR)){
        	headerInfo.addCell(makePhrase("CREDIT"+": ",smallHeading,true));
        }
        //headerInfo.addCell(makePhrase(pInvoice.getInvoiceCust().getInvoiceType()+": ",smallHeading,true));

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        headerInfo.addCell(makePhrase(SPACE + pInvoice.getInvoiceCust().getInvoiceNum(),normal,true));



        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        headerInfo.addCell(makePhrase("",smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        headerInfo.addCell(makePhrase("",smallHeading,true));

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        headerInfo.addCell(makePhrase("TERMS: ",smallHeading,true));

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        headerInfo.addCell(makePhrase(SPACE + Utility.parseTermsCode(pInvoice.getInvoiceCust().getPaymentTermsCd()),normal,true));



        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        headerInfo.addCell(makePhrase("",smallHeading,true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        headerInfo.addCell(makePhrase("",smallHeading,true));

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        headerInfo.addCell(makePhrase("INVOICE DATE: ",smallHeading,true));

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        Date date = pInvoice.getInvoiceCust().getInvoiceDate();

        String dateS = lDateFormatter.format(date);

        headerInfo.addCell(makePhrase(SPACE + dateS,normal,true));





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

            //XXX Must get account erp number and shipto erp number!

            if(pInvoice.getOrderD() != null){

                addrHeader.addCell(makePhrase("BILL TO: "+pInvoice.getOrderD().getAccountErpNum(),smallHeading,false));

                addrHeader.addCell(makePhrase("SHIP TO: "+pInvoice.getOrderD().getSiteErpNum(),smallHeading,false));

            }else{

                addrHeader.addCell(makePhrase("BILL TO:",smallHeading,false));

                addrHeader.addCell(makePhrase("SHIP TO:",smallHeading,false));

            }

            document.add(addrHeader);



            //deal with the billing and shipping information header

            InvoiceAbstractionView icd = pInvoice.getInvoiceCust();

            PdfPTable addresses = new PdfPTable(2);

            addresses.setWidthPercentage(90);

            addresses.getDefaultCell().setBorder(borderType);

            addresses.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

            addresses.getDefaultCell().setPadding(-1);

            addresses.addCell(makePhrase(icd.getBillToName(),normal,false));

            addresses.addCell(makePhrase(icd.getShippingName(),normal,false));

            addresses.addCell(makePhrase(icd.getBillToAddress1(),normal,false));

            addresses.addCell(makePhrase(icd.getShippingAddress1(),normal,false));

            addresses.addCell(makePhrase(icd.getBillToAddress2(),normal,false));

            addresses.addCell(makePhrase(icd.getShippingAddress2(),normal,false));

            addresses.addCell(makePhrase(icd.getBillToAddress3(),normal,false));

            addresses.addCell(makePhrase(icd.getShippingAddress3(),normal,false));

            addresses.addCell(makePhrase(icd.getBillToAddress4(),normal,false));

            addresses.addCell(makePhrase(icd.getShippingAddress4(),normal,false));



	    // Bill to city, state, zip



	    String

		ts = (icd.getBillToCity()==null)? " ":

		icd.getBillToCity() + SPACE;

	    ts += (icd.getBillToState()==null)? " ":

		icd.getBillToState() + SPACE;

	    ts += (icd.getBillToPostalCode()==null)? " ":

		icd.getBillToPostalCode() + SPACE;



            addresses.addCell(makePhrase(ts,normal,false));





	    // Ship to city, state, zip



	    ts = (icd.getShippingCity()==null)? " ":

		icd.getShippingCity() + SPACE;

	    ts += (icd.getShippingState()==null)? " ":

		icd.getShippingState() + SPACE;

	    ts += (icd.getShippingPostalCode()==null)? " ":

		icd.getShippingPostalCode() + SPACE;



            addresses.addCell(makePhrase(ts,normal,false));



            document.add(addresses);











            document.add(makeLine());



            if(pInvoice.getOrderD() != null){

                PdfPTable misc = new PdfPTable(1);

                misc.setWidthPercentage(85);

                misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_CENTER);

                misc.getDefaultCell().setBorder(borderType);

                misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);

                String m1 = "Currency: " + pInvoice.getOrderD().getCurrencyCd() + ", ";

                m1 = m1 + "ERP Order# " + pInvoice.getOrderD().getErpOrderNum();

                m1 = m1 + ", Conf # " + pInvoice.getOrderD().getOrderNum();

                if(Utility.isSet(pInvoice.getOrderD().getComments())){

                    m1 = m1 + ", PO # " + pInvoice.getOrderD().getComments();

                }

                misc.addCell(makePhrase(m1,normal,true));

                StringBuffer poline = new StringBuffer();

                boolean printPoLine = false;

                if(Utility.isSet(pInvoice.getOrderD().getRequestPoNum())){

                    poline.append(pInvoice.getOrderD().getRequestPoNum());

                    printPoLine = true;

                }

                if(Utility.isSet(pInvoice.getOrderD().getRefOrderNum())){

                    if(printPoLine){

                        poline.append("/");

                    }

                    poline.append(pInvoice.getOrderD().getRefOrderNum());

                    printPoLine = true;

                }

                if(printPoLine){

                    misc.addCell(makePhrase(poline.toString(),normal,false));

                }

                document.add(misc);

            }

        }



        //add the item header line

        PdfPTable itemHeader = new PdfPTable(5);

        itemHeader.setWidthPercentage(100);

        itemHeader.setWidths(itmCoumnWidth);

        itemHeader.getDefaultCell().setBorderWidth(2);

        itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);

        itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);

        itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);

        itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);

        itemHeader.addCell(makePhrase("LINE",itemHeading,false));

        itemHeader.addCell(makePhrase("QTY/UOM/PACK",itemHeading,false));

        itemHeader.addCell(makePhrase("ITEM & DESCRIPTION",itemHeading,false));

        itemHeader.addCell(makePhrase("PRICE",itemHeading,false));

        itemHeader.addCell(makePhrase("NET AMOUNT",itemHeading,false));

        document.add(itemHeader);

    }



    /**returns the trailer so it can be drawn onto the document*/

    private Table makeInvoiceTrailer(int pPageNumber,Locale pLocale, InvoiceStruct pInvoice)

    throws DocumentException {

        NumberFormat nf = NumberFormat.getInstance(pLocale);

        //nf = nf.getCurrencyInstance();

        nf.setMinimumFractionDigits(2);

        nf.setMaximumFractionDigits(2);

        //add trailer information

        Table totalsMain = new PTable(1,false);

        totalsMain.setWidth(100);

        totalsMain.getDefaultCell().setBorder(Rectangle.NO_BORDER);

//        totalsMain.addCell((Cell)(makeLine(totalsMain)));
        totalsMain.insertTable((Table)makeLine(totalsMain));


        Table totals = new PTable(3,false);
        totals.getDefaultCell().setBorder(Rectangle.NO_BORDER);

//        totals.setWidthPercentage(100);
        totals.setWidth(100);

        int width[] = {25,50,25};

        totals.setWidths(width);

        //totals.getDefaultCell().setBorder(borderType);

        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);

        //add dollar totals

        //fuel surcharge

        if(pInvoice.getInvoiceCust().getInvoiceCustData().getFuelSurcharge()  != null && pInvoice.getInvoiceCust().getInvoiceCustData().getFuelSurcharge().compareTo(new BigDecimal(0)) != 0){

	        totals.addCell(makePhrase(SPACE,normal,false));

	        totals.addCell(makePhrase("Fuel Surcharge:",normal,false));

	        BigDecimal fuel = pInvoice.getInvoiceCust().getInvoiceCustData().getFuelSurcharge();

	        String fuelS = nf.format(fuel);

	        totals.addCell(makePhrase(fuelS,normal,false));

        }



        //freight

        totals.addCell(makePhrase(SPACE,normal,false));

        if(pInvoice.getInvoiceCust().getInvoiceType().equals(RefCodeNames.INVOICE_TYPE_CD.CR)

            && pInvoice.getInvoiceCust().getFreight().compareTo(new BigDecimal(0)) > 0){

            totals.addCell(makePhrase("Restocking Fee:",normal,false));

        }else{

            totals.addCell(makePhrase("Freight Amount:",normal,false));



        }

        String frtTot = nf.format(pInvoice.getInvoiceCust().getFreight().add(pInvoice.getInvoiceCust().getMiscCharges()));

        totals.addCell(makePhrase(frtTot,normal,false));



        //gross

        totals.addCell(makePhrase(SPACE,normal,false));

        totals.addCell(makePhrase("Gross Amount:",normal,false));

        BigDecimal gross = pInvoice.getInvoiceCust().getFreight().add(pInvoice.getInvoiceCust().getMiscCharges().add(pInvoice.getInvoiceCust().getSubTotal()));

        String grossS = nf.format(gross);

        totals.addCell(makePhrase(grossS,normal,false));

        //discount

        totals.addCell(makePhrase(SPACE,normal,false));

        totals.addCell(makePhrase("Invoice Discount:",normal,false));

        BigDecimal discount = pInvoice.getInvoiceCust().getDiscounts();

        String discountS = nf.format(discount);

        totals.addCell(makePhrase(discountS,normal,false));

        //sales tax

        totals.addCell(makePhrase(SPACE,normal,false));

        totals.addCell(makePhrase("Tax Amount:",normal,false));

        BigDecimal tax = pInvoice.getInvoiceCust().getSalesTax();

        String taxS = nf.format(tax);

        totals.addCell(makePhrase(taxS,normal,false));

        //net amount due

        totals.addCell(makePhrase(SPACE,normal,false));

        totals.addCell(makePhrase("Net Amount Due:",normal,false));

        BigDecimal due = pInvoice.getInvoiceCust().getNetDue();

        String dueS = nf.format(due);

        totals.addCell(makePhrase(dueS,normal,false));



//iText 1.2.2        totalsMain.addCell(totals);
        totalsMain.insertTable(totals);
        //now add misc end of po info

        Table misc = new PTable(1,false);
        
        misc.setWidth(100);

        misc.getDefaultCell().setBorder(Rectangle.NO_BORDER);

        misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);

      //  misc.addCell(makePhrase("End of Invoice: " + pInvoice.getInvoiceCust().getInvoiceType() + SPACE + pInvoice.getInvoiceCust().getInvoiceNum(),normal,false));

        misc.addCell(makePhrase("Total Number Of Pages: " + pPageNumber,normal,false));



//iText 1.2.2        totalsMain.addCell(misc);
        totalsMain.insertTable(misc);

        return totalsMain;

    }



    /**Takes in an instance of @see OutboundEDIRequestData, and @see StoreData

     *and converts them into an outbound invoice pdf.  This document

     *looks very similar to the output when printing directly from Lawson.

     *@Param p810s the invoices to translate.

     *@Param pStore the store this invoice is comming from.

     *@Param pOut the output stream to write the pdf to.

     *@Param pImageName the image to be placed in the upper left corner, if null left off pdf.

     *@throws IOException if any error occurs.

     */

    public void constructPdfInvoice(OutboundEDIRequestDataVector p810s, StoreData pStore,OutputStream pOut,String pImageName)

    throws IOException{

        if (p810s.size() == 0){

            throw new IllegalArgumentException("OutboundEDIRequestDataVector size cannot be emtpy");

        }

        ArrayList lInvoice = new ArrayList();

        for(int i=0,len=p810s.size();i<len;i++){

            OutboundEDIRequestData ediReq = (OutboundEDIRequestData) p810s.get(i);

            //InvoiceCustDetailDataVector icvec = new InvoiceCustDetailDataVector();

            //icvec.addAll(ediReq.getInvoiceDetailDV());

            PdfInvoice.InvoiceStruct inv =

            new InvoiceStruct(ediReq.getOrderD(),ediReq.getInvoiceCustD(),ediReq.getInvoiceDetailDV());

            lInvoice.add(inv);

        }

        generatePdf(lInvoice,pStore,pOut,pImageName);

    }



    /**Takes in an instance of

     *@see OrderData @see InvoiceAbstractionView,@see InvoiceCustDetailDataVector

     *and @see StoreData and converts them into an outbound invoice pdf.  This document

     *looks very similar to the output when printing directly from Lawson.

     *@Param pInvoice the invoice to translate.

     *@Param pInvoiceDetail the invoice detail belonging to the supplied invoice.

     *@Param pStore the store this po is comming from.

     *@Param pOut the output stream to write the pdf to.

     *@Param pImageName the image to be placed in the upper left corner, if null left off pdf.

     *@throws IOException if any error occurs.

     */

    public void constructPdfInvoice(

    OrderData pOrderData,InvoiceCustData pInvoice,List pInvoiceDetail,

    StoreData pStore,OutputStream pOut,String pImageName)

    throws IOException{

        ArrayList lInvoice = new ArrayList();

        lInvoice.add(new InvoiceStruct(pOrderData,

         new InvoiceAbstractionView(pInvoice),

        pInvoiceDetail)

        );

        generatePdf(lInvoice,pStore,pOut,pImageName);

    }



    /**Takes in an instance of @see InvoiceCustViewVector

     *and converts them it an outbound invoice pdf.  This document

     *looks very similar to the output when printing directly from Lawson.

     *@Param pInvoices the invoices to translate.

     *@Param pOut the output stream to write the pdf to.

     *@Param pImageName the image to be placed in the upper left corner, if null left off pdf.

     *@throws IOException if any error occurs.

     */

    public void constructPdfInvoice(InvoiceCustViewVector pInvoices,StoreData pStore,OutputStream pOut,String pImageName)

    throws IOException{

        ArrayList lInvoices = new ArrayList();

        Iterator it = pInvoices.iterator();

        while(it.hasNext()){

            InvoiceCustView icv = (InvoiceCustView) it.next();

            InvoiceStruct stct = new InvoiceStruct(icv.getOrderData(), new InvoiceAbstractionView(icv.getInvoiceCustData()), icv.getInvoiceCustDetailDataVector());

            lInvoices.add(stct);

        }

        generatePdf(lInvoices,pStore,pOut,pImageName);

    }



    //main worker method.  The public methods will call this method

    //to generate the pdf after the object has been converted.

    private void generatePdf(ArrayList p850s,StoreData pStore,OutputStream pOut,String pImageName)

    throws IOException{



        try{

            //we keep track of the page number rather than useing the built in pdf functionality

            //as we have to maintain pages between invoices, so we may be on the 3rd invoice, but on the

            //first page of that invoice, and on the 9th page of the pdf.

            int pageNumber = 1;

            //create the header and footer

            Phrase headPhrase = new Phrase(makeChunk("INVOICE",heading,true));

            HeaderFooter header = new HeaderFooter(headPhrase,false);

            header.setAlignment(HeaderFooter.ALIGN_LEFT);



            Phrase footPhrase= makeStoreFooter (pStore);



            HeaderFooter footer = new HeaderFooter(footPhrase,false);

            footer.setAlignment(HeaderFooter.ALIGN_CENTER);



            //setup the borders from the header

            header.setBorder(borderType);

            footer.setBorder(HeaderFooter.TOP);



            //setup the document

            Document document = new Document(PageSize.A4, 10, 15, 30, 15);

            //Document document = new Document();

            PdfWriter writer = PdfWriter.getInstance(document, pOut);

            document.setHeader(header);

            document.setFooter(footer);

            document.open();



            for (int j=0,len2=p850s.size();j<len2;j++){

                InvoiceStruct lInvoice = (InvoiceStruct) p850s.get(j);



                Collections.sort(lInvoice.getInvoiceCustDetail(), INVOICE_DETAIL_LINE_NUM_COMPARE);

                //XXX po locale, or distributor locale?

                Locale lLocale = Utility.parseLocaleCode("en_US");

                if(lInvoice.getOrderD() != null){

                    lLocale = Utility.parseLocaleCode(lInvoice.getOrderD().getLocaleCd());

                }



                drawInvoiceHeader(document,lInvoice,lLocale,pageNumber,

                pStore.getStoreBusinessName().getValue(),pImageName);

                //now add the items

                BigDecimal poTotal = new BigDecimal(0);

                for (int i=0,len=lInvoice.getInvoiceCustDetail().size();i<len;i++){

                    document.add(makeBlankLine());



                    InvoiceAbstractionDetailView itm = (InvoiceAbstractionDetailView) lInvoice.getInvoiceCustDetail().get(i);

                    Table itmTable = makeItemElement(itm,lLocale);

                    //if the item data will not fit onto the page, make a new page, and redraw

                    //the header.

                    if (writer.fitsPage(itmTable,document.bottomMargin())){

                        document.add(itmTable);

                    } else {

                        document.newPage();

                        pageNumber = pageNumber + 1;

                        drawInvoiceHeader(document,lInvoice,lLocale,pageNumber,pStore.getStoreBusinessName().getValue(),pImageName);

                        document.add(makeBlankLine());

                        document.add(itmTable);

                    }

                }





                //creates the trailer

                Table trailer = makeInvoiceTrailer(pageNumber,lLocale,lInvoice);

                Table tempTrailer = new PTable(1,false);

//iText 1.2.2                tempTrailer.setWidthPercentage(100);
                tempTrailer.setWidth(100);

//iText 1.2.2                tempTrailer.addCell(trailer);
                tempTrailer.insertTable(trailer);

                tempTrailer.insertTable((Table)makeLine(tempTrailer));

                tempTrailer.insertTable((Table)makeLine(tempTrailer));



                //if it fits on the current page add it

                if (writer.fitsPage(trailer)){

                    /*while(writer.fitsPage(tempTrailer)){

                        document.add(makeBlankLine());

                    }
*/
                    document.add(trailer);

                }else{

                    //create a brand new page just for the trailer information if it

                    //doesn't fit on the page

                    document.newPage();

                    pageNumber = pageNumber + 1;

                    drawInvoiceHeader(document,lInvoice,lLocale,pageNumber,pStore.getStoreBusinessName().getValue(),pImageName);

                    //regenerate the trailer to get the correct page number

                    /*while(writer.fitsPage(tempTrailer)){

                        document.add(makeBlankLine());

                    }*/

                    //redraw the trailer as the page number will have changed

                    trailer = makeInvoiceTrailer(pageNumber,lLocale,lInvoice);

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

            throw new IOException(e.getMessage());

        }

    }



    /**

     *Private iner class to represent the data the po needs to print, and the

     *relationship between the data.  This makes converting between different

     *objects (outboundEdiRequests, and PurchaseOrderDesc/orderItemVectors, etc.)

     *easier

     */

    public class InvoiceStruct{

        private OrderData mOrderD;

        private InvoiceAbstractionView invoiceCust;

        private List invoiceCustDetail;



        //pInvoiceCustDetail may contain either InvoiceCustDetailRequestData or InvoiceCustDetailData

        public InvoiceStruct(OrderData pOrderD, InvoiceAbstractionView pInvoiceCust, List pInvoiceCustDetail) {

            invoiceCust = pInvoiceCust;

            mOrderD = pOrderD;

            List icdd = new ArrayList();

            for (int i=0;i<pInvoiceCustDetail.size();i++){

                Object itmRef = pInvoiceCustDetail.get(i);

                InvoiceAbstractionDetailView itm = null;

                if(itmRef instanceof InvoiceCustDetailRequestData){

                    itm = ((InvoiceCustDetailRequestData) itmRef).getInvoiceDetailD();

                }else{

                    itm = new InvoiceAbstractionDetailView((InvoiceCustDetailData) itmRef);

                }

                icdd.add(itm);

            }

            invoiceCustDetail = icdd;

        }



        /** Getter for property orderD.

         * @return Value of property orderD.

         *

         */

        private OrderData getOrderD(){

            return mOrderD;

        }

        /** Getter for property invoiceCust.

         * @return Value of property invoiceCust.

         *

         */

        public InvoiceAbstractionView getInvoiceCust() {

            return this.invoiceCust;

        }





        /** Getter for property invoiceCustDetail.

         * @return Value of property invoiceCustDetail.

         *

         */

        public List getInvoiceCustDetail() {

            return this.invoiceCustDetail;

        }

    }



    static final Comparator INVOICE_DETAIL_LINE_NUM_COMPARE = new Comparator() {

	    public int compare(Object o1, Object o2)

	    {

		int int1 = ((InvoiceAbstractionDetailView)o1).getLineNumber();

		int int2 = ((InvoiceAbstractionDetailView)o2).getLineNumber();

		return int1 - int2;

	    }

	};

}

