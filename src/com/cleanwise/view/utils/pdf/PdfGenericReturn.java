/*
 * PdfGenericReturn.java
 *
 * Created on May 10, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import java.text.SimpleDateFormat;

import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.*;
import java.util.Locale;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *Constructs a pdf document.  It understands the various
 *propriatary objects, and uses them to construct a return request document.
 * @author  bstevens
 */
public class PdfGenericReturn extends PdfSuper {
    //static configuration variables

    /** Creates a new instance of PdfGenericReturn */
    public PdfGenericReturn() {
    }

    //****************Private Worker Methods*****************
    private void drawHeader(Document document,ReturnPOStruct pReturn, Locale pLocale,String pImageName)
    throws DocumentException {
        //DateFormat lDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,pLocale);
        //add the logo
        try{
            Image i = Image.getInstance(pImageName);
            float x = document.leftMargin();
            float y = document.getPageSize().getHeight() - i.getHeight() - 5;
            i.setAbsolutePosition(x,y);
            document.add(i);
        }catch (Exception e){
            e.printStackTrace();
        }

        ReturnRequestData lReturn = pReturn.getReturnRequestDescData().getReturnRequestData();
        PurchaseOrderData lPo = pReturn.getPurchaseOrderData();

        document.add(makeBlankLine());
        document.add(makeBlankLine());
        document.add(makeBlankLine());
        PdfPTable header = new PdfPTable(1);
        header.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        header.setWidthPercentage(90);
        header.getDefaultCell().setBorder(borderType);
        header.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
        header.addCell(makePhrase("RETURN MERCHANDISE FORM",heading,false));
        if(Utility.isSet(lReturn.getNotesToDistributor())){
            header.addCell(makePhrase(lReturn.getNotesToDistributor(),normalBold,false));
        }
        document.add(header);

        PdfPTable subHeader = new PdfPTable(4);
        subHeader.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        subHeader.setWidthPercentage(90);
        subHeader.getDefaultCell().setBorder(borderType);
        subHeader.addCell(makePhrase("Return Number:",normalBold,false));
        subHeader.addCell(makePhrase(lReturn.getReturnRequestRefNum(),normal,false));
        if(Utility.isSet(lReturn.getDistributorInvoiceNumber())){
            subHeader.addCell(makePhrase("Distributor Invoice:",normalBold,false));
            subHeader.addCell(makePhrase(lReturn.getDistributorInvoiceNumber(),normal,false));
        }else{
            subHeader.addCell(makePhrase(SPACE,normal,false));
            subHeader.addCell(makePhrase(SPACE,normal,false));
        }
        subHeader.addCell(makePhrase("Distributor Name:",normalBold,false));
        subHeader.addCell(makePhrase(pReturn.getDistributor().getBusEntity().getShortDesc(),normal,false));
        subHeader.addCell(makePhrase("Return Request Date:",normalBold,false));
        SimpleDateFormat dateFor = new SimpleDateFormat("MM/dd/yyyy",pLocale);
        subHeader.addCell(makePhrase(dateFor.format(lReturn.getDateRequestRecieved()),normal,false));
        subHeader.addCell(makePhrase("Contact:",normalBold,false));
        subHeader.addCell(makePhrase(lReturn.getSenderContactName(),normal,false));
        subHeader.addCell(makePhrase("Phone:",normalBold,false));
        subHeader.addCell(makePhrase(lReturn.getSenderContactPhone(),normal,false));
        subHeader.addCell(makePhrase("Purchase Order#:",normalBold,false));
        subHeader.addCell(makePhrase(lPo.getErpPoNum(),normal,false));
        subHeader.addCell(makePhrase("Purchase Order Date:",normalBold,false));
        subHeader.addCell(makePhrase(dateFor.format(lPo.getPoDate()),normal,false));
        if(Utility.isSet(lReturn.getDistributorRefNum())){
            subHeader.addCell(makePhrase("Distributor Return Ref Number:",normalBold,false));
            subHeader.addCell(makePhrase(lReturn.getDistributorRefNum(),normal,false));
        }else{
            subHeader.addCell(makePhrase(SPACE,normal,false));
            subHeader.addCell(makePhrase(SPACE,normal,false));
        }
        subHeader.addCell(makePhrase("Return Method:",normalBold,false));
        subHeader.addCell(makePhrase(lReturn.getReturnMethod(),normal,false));
        document.add(subHeader);

        //contains the items
        PdfPTable subHeader2 = new PdfPTable(1);
        subHeader2.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        subHeader2.setWidthPercentage(90);
        subHeader2.getDefaultCell().setBorder(borderType);

        OrderItemDescDataVector lReturnItems = pReturn.getReturnRequestDescData().getReturnRequestOrderItemDescDataVector();
        if(lReturnItems.size() > 1){
            subHeader2.addCell(makePhrase("Items To Return:",normalBold,false));
        }else{
            subHeader2.addCell(makePhrase("Item To Return:",normalBold,false));
        }

        document.add(subHeader2);

        for(int i=0;i<lReturnItems.size();i++){
            OrderItemDescData itm = (OrderItemDescData) lReturnItems.get(i);
            PdfPTable itemsTable = new PdfPTable(6);
            itemsTable.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            itemsTable.setWidthPercentage(90);
            //sets the width in % that the items will be put into (column 1, column2, etc.)
            //must add up to 100
            int[] widths = {20,10,36,7,7,20};
            itemsTable.setWidths(widths);
            itemsTable.getDefaultCell().setBorder(borderType);
            itemsTable.addCell(makePhrase("Dist Sku",normalBold,false));
            itemsTable.addCell(makePhrase("Sku",normalBold,false));
            itemsTable.addCell(makePhrase("Description",normalBold,false));
            itemsTable.addCell(makePhrase("Qty",normalBold,false));
            itemsTable.addCell(makePhrase("Uom",normalBold,false));
            itemsTable.addCell(makePhrase("Pack",normalBold,false));

            if(itm.getReturnRequestDetailData() == null || itm.getReturnRequestDetailData().getQuantityReturned()==0){
                continue;
            }
            if(Utility.isSet(itm.getReturnRequestDetailData().getRecievedDistSku()) || Utility.isSet(itm.getReturnRequestDetailData().getRecievedDistUom()) || Utility.isSet(itm.getReturnRequestDetailData().getRecievedDistPack())){
                itemsTable.addCell(makePhrase("Ordered:",normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
            }
            itemsTable.addCell(makePhrase(itm.getOrderItem().getDistItemSkuNum(),normal,false));
            itemsTable.addCell(makePhrase(Integer.toString(itm.getOrderItem().getItemSkuNum()),normal,false));
            itemsTable.addCell(makePhrase(itm.getOrderItem().getItemShortDesc(),normal,false));
            itemsTable.addCell(makePhrase(Integer.toString(itm.getReturnRequestDetailData().getQuantityReturned()),normal,false));
            itemsTable.addCell(makePhrase(itm.getOrderItem().getDistItemUom(),normal,false));
            itemsTable.addCell(makePhrase(itm.getOrderItem().getDistItemPack(),normal,false));

            //if the recieved info is set write it out.
            if(Utility.isSet(itm.getReturnRequestDetailData().getRecievedDistSku()) || Utility.isSet(itm.getReturnRequestDetailData().getRecievedDistUom()) || Utility.isSet(itm.getReturnRequestDetailData().getRecievedDistPack())){
                itemsTable.addCell(makePhrase("Recieved:",normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));

                itemsTable.addCell(makePhrase(itm.getReturnRequestDetailData().getRecievedDistSku(),normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(SPACE,normal,false));
                itemsTable.addCell(makePhrase(itm.getReturnRequestDetailData().getRecievedDistUom(),normal,false));
                itemsTable.addCell(makePhrase(itm.getReturnRequestDetailData().getRecievedDistPack(),normal,false));
            }
            itemsTable.addCell(makePhrase(SPACE,normal,false));
            itemsTable.addCell(makePhrase(SPACE,normal,false));
            itemsTable.addCell(makePhrase(SPACE,normal,false));
            itemsTable.addCell(makePhrase(SPACE,normal,false));
            itemsTable.addCell(makePhrase(SPACE,normal,false));
            itemsTable.addCell(makePhrase(SPACE,normal,false));
            document.add(itemsTable);
        }

        subHeader2 = new PdfPTable(1);
        subHeader2.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        subHeader2.setWidthPercentage(90);
        subHeader2.getDefaultCell().setBorder(borderType);

        //nested table holds contact info and couple other tidbits
        PdfPTable subHeader3 = new PdfPTable(4);
        subHeader3.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        subHeader3.setWidthPercentage(90);
        subHeader3.getDefaultCell().setBorder(borderType);

        subHeader3.addCell(makePhrase("Reason For Return:",normalBold,false));
        subHeader3.addCell(makePhrase(lReturn.getReason(),normal,false));

        OrderAddressData pickupFrom = pReturn.getReturnRequestDescData().getPickupAddress();
        subHeader3.addCell(makePhrase("Pick Up Contact:",normalBold,false));
        subHeader3.addCell(makePhrase(lReturn.getPickupContactName() + " " + pickupFrom.getPhoneNum(),normal,false));

        subHeader2.addCell(subHeader3);
        subHeader3 = null;

        PdfPTable subHeader3a = new PdfPTable(2);
        subHeader3a.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        int[] subHeader3aWidths = {20,80};
        subHeader3a.setWidths(subHeader3aWidths);
        subHeader3a.setWidthPercentage(90);
        subHeader3a.getDefaultCell().setBorder(borderType);

        subHeader3a.addCell(makePhrase("Pick Up Address:",normalBold,false));
        subHeader3a.addCell(makePhrase(pickupFrom.getShortDesc(),normal,false));

        subHeader3a.addCell(makePhrase(SPACE,normal,false));
        subHeader3a.addCell(makePhrase(pickupFrom.getAddress1(),normal,false));
        if(Utility.isSet(pickupFrom.getAddress2())){
            subHeader3a.addCell(makePhrase(SPACE,normal,false));
            subHeader3a.addCell(makePhrase(pickupFrom.getAddress2(),normal,false));
        }
        if(Utility.isSet(pickupFrom.getAddress3())){
            subHeader3a.addCell(makePhrase(SPACE,normal,false));
            subHeader3a.addCell(makePhrase(pickupFrom.getAddress3(),normal,false));
        }
        if(Utility.isSet(pickupFrom.getAddress4())){
            subHeader3a.addCell(makePhrase(SPACE,normal,false));
            subHeader3a.addCell(makePhrase(pickupFrom.getAddress4(),normal,false));
        }
        subHeader3a.addCell(makePhrase("City:",normal,false));
        subHeader3a.addCell(makePhrase(pickupFrom.getCity(),normal,false));
        String state = pickupFrom.getStateProvinceCd();
        if (Utility.isSet(state)) {
          subHeader3a.addCell(makePhrase("State:", normal, false));
          subHeader3a.addCell(makePhrase(state, normal, false));
        }
        subHeader3a.addCell(makePhrase("Zip:",normal,false));
        subHeader3a.addCell(makePhrase(pickupFrom.getPostalCode(),normal,false));

        subHeader2.addCell(subHeader3a);

        subHeader2.addCell(makePhrase(SPACE,normal,false));
        subHeader2.addCell(makePhrase("Distributor must acknowledge receipt of this request for return "+
            "whthin 24 hours by Fax, along with an estimated pickup date",normalBold,false));
        subHeader2.addCell(makePhrase("",normal,false));
        document.add(subHeader2);

        document.add(makeBlankLine());
        document.add(makeBlankLine());
        PdfPTable estPickup = new PdfPTable(2);
        estPickup.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        estPickup.setWidthPercentage(90);
        estPickup.getDefaultCell().setBorder(borderType);
        estPickup.addCell(makePhrase("ESTIMATED PICK UP DATE:",normalBold,false));
        estPickup.addCell(makeLine());
        document.add(estPickup);
    }

    /**
     */
    public void constructPdf(DistributorData pDist,
        PurchaseOrderData pPurchaseOrderData,ReturnRequestDescDataView pReturnReq,
        StoreData pStore,OutputStream pOut,String pImageName)
    throws IOException{
        if (pReturnReq == null){
            throw new NullPointerException("returnReq cannot be null");
        }

        ArrayList returns = new ArrayList();
        returns.add(new PdfGenericReturn.ReturnPOStruct(pPurchaseOrderData,pReturnReq,pDist));
        generatePdf(pStore,returns,pOut,pImageName);
    }


    //main worker method.  The public methods will call this method
    //to generate the pdf after the object has been converted.
    private void generatePdf(StoreData pStore, ArrayList pReturns,
    OutputStream pOut,String pImageName)
    throws IOException{
        setNormalFontSize(14);
        setNormalFont(FontFactory.HELVETICA);
        try{
            //we keep track of the page number rather than useing the built in pdf functionality
            //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
            //first page of that po, and on the 9th page of the pdf.
            //int pageNumber = 1;
            //create the header and footer
            Phrase footPhrase= makeStoreFooter (pStore);

            HeaderFooter footer = new HeaderFooter(footPhrase,false);
            footer.setAlignment(HeaderFooter.ALIGN_CENTER);

            //setup the borders from the footer
            footer.setBorder(HeaderFooter.TOP);

            //setup the document
            Document document = new Document(PageSize.A4, 10, 15, 30, 15);
            //Document document = new Document();
            PdfWriter.getInstance(document, pOut); //sets the output stream
            document.setFooter(footer);
            document.open();

            //This will be used if there is no locale for this distributor
            Locale defaultLocale = Utility.parseLocaleCode("en_US");
            for (int j=0,len2=pReturns.size();j<len2;j++){

                ReturnPOStruct view = (ReturnPOStruct) pReturns.get(j);
                Locale lLocale;
                if(Utility.isSet(view.getDistributor().getBusEntity().getLocaleCd())){
                    lLocale = Utility.parseLocaleCode(view.getDistributor().getBusEntity().getLocaleCd());
                }else{
                    lLocale = defaultLocale;
                }

                drawHeader(document, view, lLocale, pImageName);
            }

            //close out the document
            document.close();

        }catch (DocumentException e){
            throw new IOException(e.getMessage());
        }
    }

    private class ReturnPOStruct {

        ReturnPOStruct(PurchaseOrderData pPurchaseOrderData,
            ReturnRequestDescDataView pReturnRequestDescData,
            DistributorData pDistributor){
            purchaseOrderData=pPurchaseOrderData;
            returnRequestDescData=pReturnRequestDescData;
            distributor=pDistributor;
        }

        /** Holds value of property purchaseOrderData. */
        private PurchaseOrderData purchaseOrderData;

        /** Holds value of property returnRequestDescData. */
        private ReturnRequestDescDataView returnRequestDescData;

        /** Holds value of property distributor. */
        private DistributorData distributor;

        /** Getter for property purchaseOrderData.
         * @return Value of property purchaseOrderData.
         *
         */
        public PurchaseOrderData getPurchaseOrderData() {
            return this.purchaseOrderData;
        }

        /** Getter for property returnRequestDescData.
         * @return Value of property returnRequestDescData.
         *
         */
        public ReturnRequestDescDataView getReturnRequestDescData() {
            return this.returnRequestDescData;
        }

        /** Getter for property distributor.
         * @return Value of property distributor.
         *
         */
        public DistributorData getDistributor() {
            return this.distributor;
        }
    }

}
