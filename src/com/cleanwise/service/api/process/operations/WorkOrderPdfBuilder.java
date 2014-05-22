package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.process.operations.PdfBuilder.PTable;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.WorkOrderUtil;
import com.cleanwise.service.api.value.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Title:        WorkOrderPdfBuilder
 * Description:  Pdf builder
 * Purpose:      Constructs a pdf document of the work order
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         11.12.2007
 * Time:         18:43:16
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class WorkOrderPdfBuilder extends PdfBuilder {

    Font normalBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, "utf-8", true, 10);
    Font white_heading = FontFactory.getFont(FontFactory.HELVETICA_BOLD, "utf-8", true, 16, 0, Color.white);

    private DateFormat   dateFormatter;
    private NumberFormat numFormatter;

    public void generatePdf(StoreData store,
                            AccountData account,
                            SiteData site,
                            WorkOrderDetailView workOrderDetail,
                            CostCenterData costCenter,
                            AssetData assetD,
                            WarrantyDataVector warranties,
                            ServiceProviderData serviceProvider,
                            WorkOrderDetailDataVector woDetailDV,
                            ByteArrayOutputStream pdfout,
                            String imgPath,
                            UserData user,
                            Locale locale) throws IOException {


        if (store != null) {
            try {
                int pageNumber = 1;

                setup(locale);

                Phrase headPhrase = new Phrase(makeChunk("", normalBold,true));
                String mess = getMessage("userWorkOrder.text.workOrder","Work Order");
                headPhrase.add(makeChunk(mess,normalBold,true));
                headPhrase.add(makeChunk(SPACE,normalBold,true));
                headPhrase.add(makeChunk(account.getBusEntity().getShortDesc(),normalBold,true));

                HeaderFooter header = new HeaderFooter(headPhrase,true);
                header.setAlignment(HeaderFooter.ALIGN_RIGHT);

                Phrase footPhrase = makeStoreFooter(store);
                HeaderFooter footer = new HeaderFooter(footPhrase, false);
                footer.setAlignment(HeaderFooter.ALIGN_CENTER);

                //setup the borders from the header
                //header.setBorder(borderType);
                footer.setBorder(HeaderFooter.TOP);
                //setup the document
                Document document = new Document(PageSize.A4, 10, 15, 15, 15);

                PdfWriter writer = PdfWriter.getInstance(document, pdfout);

                document.setHeader(header);
                document.setFooter(footer);
                document.open();

                drawLogo(document, account, imgPath);

                drawHeader(document, workOrderDetail.getWorkOrder(),writer,account,imgPath);

                drawSummaryBox(document, workOrderDetail.getWorkOrder(), writer, account, imgPath, user, costCenter, locale, store, site);

                document.add(makeBlankLine());
                document.add(makeBlankLine());

                drawAssetInfoBox(document, assetD, warranties, writer,account,imgPath);

                drawServiceProviderBox(document,serviceProvider,writer,account,imgPath);

                document.add(makeBlankLine());
                document.add(makeBlankLine());

                drawLocationBox(document,site,writer,account,imgPath);

                document.add(makeBlankLine());
                document.add(makeBlankLine());

                drawContactBox(writer,document,new ContactInfo(workOrderDetail.getProperties()),account,imgPath);

                document.add(makeBlankLine());
                document.add(makeBlankLine());

                drawWorkOrderScheduleBox(document, workOrderDetail.getWorkOrder(), writer, account, imgPath);

                document.add(makeBlankLine());
                document.add(makeBlankLine());

                drawWorkOrderCostSummaryBox(document, woDetailDV, writer, account, imgPath);

                document.add(makeBlankLine());
                document.add(makeBlankLine());

                drawItemizedServiceBox(document, woDetailDV, locale, writer, account, imgPath);

                document.close();
            } catch (DocumentException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
        }
    }

    private void drawItemizedServiceBox(Document document,
                                        WorkOrderDetailDataVector woDetailDV,
                                        Locale locale,
                                        PdfWriter writer,
                                        AccountData account,
                                        String imgPath) throws DocumentException {

        if (WorkOrderUtil.getItemizedServiceTableActiveCount(woDetailDV) > 0) {
            Table mainBox = new PTable(1);
            mainBox.setWidth(100);
            mainBox.getDefaultCell().setBorder(0);
            drawItemizedServiceHeader(mainBox);

            int size = woDetailDV.size();
            WorkOrderDetailData currentDD;
            for (int i = 0; i < size; i++) {
                currentDD = (WorkOrderDetailData) woDetailDV.get(i);
                if (RefCodeNames.STATUS_CD.ACTIVE.equals(currentDD.getStatusCd())) {
                    Table itemizedServiceLine = buildItemizedServiceLine(currentDD, locale);
                    mainBox.insertTable(itemizedServiceLine);
                    if(writer.fitsPage(mainBox)){
                        document.add(mainBox);
                    } else {
                        newPage(document,account,imgPath);
                        document.add(mainBox);
                    }
                    document.add(makeBlankLine());

                    mainBox = new PTable(1);
                    mainBox.setWidth(100);
                    mainBox.getDefaultCell().setBorder(0);
                }
            }
        }
    }

    private void drawItemizedServiceHeader(Table document) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);
        box.getDefaultCell().setBorder(0);

        Table header = new PTable(1);
        int headerWidths[] = {100};
        header.setWidth(100);
        header.setWidths(headerWidths);
        header.getDefaultCell().setBorder(0);

        String headerMessage = getMessage("userWorkOrder.text.ItemizedService", "Itemized Service");
        header.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        header.addCell(makePhrase(headerMessage, heading, true));

        box.insertTable(header);

        document.insertTable(box);
    }

    private Table buildItemizedServiceLine(WorkOrderDetailData workOrderDD, Locale locale) throws DocumentException {
        String dateFormat = "MM/dd/yyyy";
        SimpleDateFormat sdFormat = new SimpleDateFormat(dateFormat, locale);

        Table box = new PTable(1);
        box.setWidth(100);

        Table itemizedServiceLine = new PTable(1);
        itemizedServiceLine.setWidth(100);
        itemizedServiceLine.getDefaultCell().setBorder(0);

        // Line Header
        Table itemizedServiceLineHeader = new PTable(1);
        int itemizedServiceLineHeaderWidths[] = {100};
        String itemHaderMessage = getMessage("userWorkOrder.text.LineNumber","LN")+"# "+workOrderDD.getLineNum();
        itemizedServiceLineHeader.setWidths(itemizedServiceLineHeaderWidths);
        itemizedServiceLineHeader.setWidth(100);
        itemizedServiceLineHeader.getDefaultCell().setBackgroundColor(Color.black);
        itemizedServiceLineHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        itemizedServiceLineHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        itemizedServiceLineHeader.addCell(makePhrase(itemHaderMessage, white_heading, true));

        //Line Content Header
        Table lineContentHeader = new PTable(9);
        int lineContentHeaderWidths[] = {17, 5, 10, 20, 8, 12, 7, 7, 14};

        lineContentHeader.setWidth(100);
        lineContentHeader.getDefaultCell().setBorder(0);
        lineContentHeader.setWidths(lineContentHeaderWidths);
        lineContentHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String paymentTypeMessage = getSmeColonMessage("userWorkOrder.text.PaymentType","Payment Type");
        String quantityMessage = getSmeColonMessage("userWorkOrder.text.Quantity","Qty");
        String partNumMessage = getSmeColonMessage("userWorkOrder.text.PartNumber","Part Num");
        String descriptionMessage = getSmeColonMessage("userWorkOrder.text.Description","Description");
        String priceEachMessage = getSmeColonMessage("userWorkOrder.text.PriceEach","Price Each");
        String priceSumMessage = getSmeColonMessage("userWorkOrder.text.PartsPriceExtended","Price Sum");
        String laborMessage = getSmeColonMessage("userWorkOrder.text.Labor","Labor");
        String travelMessage = getSmeColonMessage("userWorkOrder.text.Travel","Travel");
        String addedDateMessage = getSmeColonMessage("userWorkOrder.text.note.addDate","Added Date");

        lineContentHeader.addCell(makePhrase(paymentTypeMessage, normalBold, true));
        lineContentHeader.addCell(makePhrase(quantityMessage, normalBold, true));
        lineContentHeader.addCell(makePhrase(partNumMessage, normalBold, true));
        lineContentHeader.addCell(makePhrase(descriptionMessage, normalBold, true));
        lineContentHeader.addCell(makePhrase(priceEachMessage, normalBold, true));
        lineContentHeader.addCell(makePhrase(priceSumMessage, normalBold, true));
        lineContentHeader.addCell(makePhrase(laborMessage, normalBold, true));
        lineContentHeader.addCell(makePhrase(travelMessage, normalBold, true));
        lineContentHeader.addCell(makePhrase(addedDateMessage, normalBold, true));

        //Line Content
        Table lineContent = new PTable(9);
        int lineContentWidths[] = {17, 5, 10, 20, 8, 12, 7, 7, 14};

        lineContent.setWidth(100);
        lineContent.getDefaultCell().setBorder(0);
        lineContent.setWidths(lineContentWidths);
        lineContent.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String paymentTypeValue = workOrderDD.getPaymentTypeCd();
        String quantityValue = String.valueOf(workOrderDD.getQuantity());
        String partNumValue = Utility.strNN(workOrderDD.getPartNumber());
        String descriptionValue = Utility.strNN(workOrderDD.getShortDesc());
        String priceEachValue = workOrderDD.getPartPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String priceSumValue = workOrderDD.getPartPrice().multiply(BigDecimal.valueOf(workOrderDD.getQuantity())).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborValue = workOrderDD.getLabor().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelValue = workOrderDD.getTravel().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String addedDateValue = sdFormat.format(workOrderDD.getAddDate());

        lineContent.addCell(makePhrase(paymentTypeValue, normal, true));
        lineContent.addCell(makePhrase(quantityValue, normal, true));
        lineContent.addCell(makePhrase(partNumValue, normal, true));
        lineContent.addCell(makePhrase(descriptionValue, normal, true));
        lineContent.addCell(makePhrase(priceEachValue, normal, true));
        lineContent.addCell(makePhrase(priceSumValue, normal, true));
        lineContent.addCell(makePhrase(laborValue, normal, true));
        lineContent.addCell(makePhrase(travelValue, normal, true));
        lineContent.addCell(makePhrase(addedDateValue, normal, true));

        //Line Comments Header
        Table lineCommentsHeader = new PTable(1);
        int lineCommentsHeaderWidths[] = {100};
        lineCommentsHeader.setWidth(100);
        lineCommentsHeader.getDefaultCell().setBorder(0);
        lineCommentsHeader.setWidths(lineCommentsHeaderWidths);
        lineCommentsHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        lineCommentsHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        String commentsMessage = getSmeColonMessage("userWorkOrder.text.Comments","Commments");

        lineCommentsHeader.addCell(makePhrase(commentsMessage, normalBold, true));

        //Line Comments Content
        Table lineCommentsContent = new PTable(1);
        int lineCommentsContentWidths[] = {100};
        lineCommentsContent.setWidth(70);
        lineCommentsContent.getDefaultCell().setBorder(0);
        lineCommentsContent.setWidths(lineCommentsContentWidths);
        lineCommentsContent.getDefaultCell().setVerticalAlignment(PdfPCell.TOP);
        lineCommentsContent.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);

        String commentsValue = Utility.strNN(workOrderDD.getComments());

        lineCommentsContent.addCell(makePhrase(commentsValue, normal, true));

//iText 1.2.2        itemizedServiceLine.addCell(itemizedServiceLineHeader);
//iText 1.2.2        itemizedServiceLine.addCell(lineContentHeader);
//iText 1.2.2        itemizedServiceLine.addCell(lineContent);
//iText 1.2.2        itemizedServiceLine.addCell(lineCommentsHeader);

        itemizedServiceLine.insertTable(itemizedServiceLineHeader);
        itemizedServiceLine.insertTable(lineContentHeader);
        itemizedServiceLine.insertTable(lineContent);
        itemizedServiceLine.insertTable(lineCommentsHeader);

        itemizedServiceLine.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//iText 1.2.2        itemizedServiceLine.addCell(lineCommentsContent);
        itemizedServiceLine.insertTable(lineCommentsContent);

//iText 1.2.2        box.addCell(itemizedServiceLine);
        box.insertTable(itemizedServiceLine);

        return box;
    }

    private void drawItemsDetailBox(PdfWriter writer, Document document, WorkOrderItemDetailViewVector workOrderItems, AccountData account, String imgPath) throws DocumentException {

        if (workOrderItems != null) {

            Table mainBox = new PTable(1);
            mainBox.setWidth(100);
            mainBox.getDefaultCell().setBorder(0);
            drawItemDetailBoxHeader(mainBox);

            Iterator it = workOrderItems.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView itemDetailView = (WorkOrderItemDetailView) it.next();
                Table itemLine = buildItemDetailBox(itemDetailView);
                mainBox.insertTable(itemLine);
                if(writer.fitsPage(mainBox)){
                    document.add(mainBox);
                } else {
                    newPage(document,account,imgPath);
                    document.add(mainBox);
                }

                mainBox= new PTable(1);
                mainBox.setWidth(100);
                mainBox.getDefaultCell().setBorder(0);
            }
        }
    }



    private Table buildItemDetailBox(WorkOrderItemDetailView itemDetailView) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);

        Table item = new PTable(1);
        item.setWidth(100);
        item.getDefaultCell().setBorder(0);

        // Line Header
        Table itemLineHeader = new PTable(1);
        int itemLineHeaderWidths[] = {100};
        String itemHaderMessage = getMessage("userWorkOrder.text.workOrderItems.item","Item")+" #"+itemDetailView.getWorkOrderItem().getWorkOrderItemId();
        itemLineHeader.setWidths(itemLineHeaderWidths);
        itemLineHeader.setWidth(100);
        itemLineHeader.getDefaultCell().setBackgroundColor(Color.black);
        itemLineHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        itemLineHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        itemLineHeader.addCell(makePhrase(itemHaderMessage, white_heading, true));
        item.insertTable(itemLineHeader);

        //Requested Service
        Table itemLine1 = new PTable(2);
        int item1Widths[] = {30, 70};
        itemLine1.setWidth(100);
        itemLine1.getDefaultCell().setBorder(0);
        itemLine1.setWidths(item1Widths);
        itemLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String requestedServiceMessage = getSmeColonMessage("userWorkOrder.text.workOrderItems.description","Requested Service");

        itemLine1.addCell(makePhrase(requestedServiceMessage, normalBold, true));
        itemLine1.addCell(makePhrase(itemDetailView.getWorkOrderItem().getShortDesc(), normal, true));
        item.insertTable(itemLine1);

        if (!itemDetailView.getAssetAssoc().isEmpty()) {

            WorkOrderAssetView woAsset = (WorkOrderAssetView) itemDetailView.getAssetAssoc().get(0);

            //Asset
            Table itemLine2 = new PTable(2);
            int itemLine2Widths[] = {30, 70};
            itemLine2.setWidth(100);
            itemLine2.getDefaultCell().setBorder(0);
            itemLine2.setWidths(itemLine2Widths);
            itemLine2.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String assetMessage = getSmeColonMessage("userAssets.text.asset","Asset");

            itemLine2.addCell(makePhrase(assetMessage, normalBold, true));
            itemLine2.addCell(makePhrase(woAsset.getAssetView().getAssetName(), normal, true));
            item.insertTable(itemLine2);

            Table itemLine3 = new PTable(2);
            int itemLine3Widths[] = {30, 70};
            itemLine3.setWidth(100);
            itemLine3.getDefaultCell().setBorder(0);
            itemLine3.setWidths(itemLine3Widths);
            itemLine3.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String assetModelMessage = getSmeColonMessage("userAssets.text.modelNum","Model");

            itemLine3.addCell(makePhrase(assetModelMessage, normalBold, true));
            itemLine3.addCell(makePhrase(woAsset.getAssetView().getModelNumber(), normal, true));

            PdfPTable itemLine4 = new PdfPTable(2);
            int itemLine4Widths[] = {30, 70};
            itemLine4.setWidthPercentage(100);
            itemLine4.getDefaultCell().setBorder(0);
            itemLine4.setWidths(itemLine4Widths);
            itemLine4.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String assetSerialMessage = getSmeColonMessage("userAssets.shop.text.param.serialnumber","Serial Number");

            itemLine3.addCell(makePhrase(assetSerialMessage, normalBold, true));
            itemLine3.addCell(makePhrase(woAsset.getAssetView().getSerialNumber(), normal, true));
            item.insertTable(itemLine3);

            Table itemLine5 = new PTable(2);
            int itemLine5Widths[] = {30, 70};
            itemLine5.setWidth(100);
            itemLine5.getDefaultCell().setBorder(0);
            itemLine5.setWidths(itemLine5Widths);
            itemLine5.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String warrantyMessage = getSmeColonMessage("userWarranty.text.warranty","Warranty");

            itemLine5.addCell(makePhrase(warrantyMessage, normalBold, true));
            if (itemDetailView.getWarranty() != null) {
                itemLine5.addCell(makePhrase(itemDetailView.getWarranty().getShortDesc(), normal, true));
            }else{
                itemLine5.addCell(makePhrase("", normal, true));
            }  item.insertTable(itemLine5);

        }

        //Line 6
        Table itemLine6 = new PTable(1);
        int itemLine6Widths[] = {100};
        itemLine6.setWidth(100);
        itemLine6.getDefaultCell().setBorder(0);
        itemLine6.setWidths(itemLine6Widths);
        itemLine6.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        itemLine6.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        String reqServDetMessage = getSmeColonMessage("userWorkOrder.text.longDesc","Requested Service Detail");

        itemLine6.addCell(makePhrase(reqServDetMessage, normalBold, true));
        item.insertTable(itemLine6);

        //Line 7
        Table itemLine7 = new PTable(1);
        int itemLine7Widths[] = {100};
        itemLine7.setWidth(100);
        itemLine7.getDefaultCell().setBorder(0);
        itemLine7.setWidths(itemLine7Widths);
        itemLine7.getDefaultCell().setVerticalAlignment(PdfPCell.TOP);
        itemLine7.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);

        itemLine7.addCell(makePhrase(itemDetailView.getWorkOrderItem().getLongDesc(), normal, true));
        item.insertTable(itemLine7);

        //Line 8
        Table itemLine8 = new PTable(4);
        int itemLine8Widths[] = {30, 20,30,20};
        itemLine8.setWidth(100);
        itemLine8.getDefaultCell().setBorder(0);
        itemLine8.setWidths(itemLine8Widths);
        itemLine8.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String estimatedLaborMessage = getSmeColonMessage("userWorkOrder.text.workOrderItems.estimatedLabor","Estimated Labor");
        String estimatedPartMessage  = getSmeColonMessage("userWorkOrder.text.workOrderItems.estimatedPart","Estimated Parts Cost");

        itemLine8.addCell(makePhrase(estimatedLaborMessage, normalBold, true));
        itemLine8.addCell(makePhrase(getAsPrice(itemDetailView.getWorkOrderItem().getEstimateLabor()), normal, true));
        itemLine8.addCell(makePhrase(estimatedPartMessage, normalBold, true));
        itemLine8.addCell(makePhrase(getAsPrice(itemDetailView.getWorkOrderItem().getEstimatePart()), normal, true));
        item.insertTable(itemLine8);

        //Line 9
        Table itemLine9 = new PTable(4);
        int itemLine9Widths[] = {30, 20,30,20};
        itemLine9.setWidth(100);
        itemLine9.getDefaultCell().setBorder(0);
        itemLine9.setWidths(itemLine9Widths);
        itemLine9.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        BigDecimal itemEstimatedTotalCost = Utility.addAmt(itemDetailView.getWorkOrderItem().getEstimatePart(),
                itemDetailView.getWorkOrderItem().getEstimateLabor());
        String estimatedTotalCostMessage = getSmeColonMessage("userWorkOrder.text.workOrderItems.estimateTotalCost","Estimated Total Cost");

        itemLine9.addCell(new Phrase());
        itemLine9.addCell(new Phrase());
        itemLine9.addCell(makePhrase(estimatedTotalCostMessage, normalBold, true));
        itemLine9.addCell(makePhrase(getAsPrice(itemEstimatedTotalCost), normal, true));
        item.insertTable(itemLine9);

        //Line 10
        Table itemLine10 = new PTable(4);
        int itemLine10Widths[] = {30, 20,30,20};
        itemLine10.setWidth(100);
        itemLine10.getDefaultCell().setBorder(0);
        itemLine10.setWidths(itemLine10Widths);
        itemLine10.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String actualLaborMessage = getSmeColonMessage("userWorkOrder.text.workOrderItems.actualLabor","Actual Labor");
        String actualPartMessage  = getSmeColonMessage("userWorkOrder.text.workOrderItems.actualPart","Actual Parts Cost");

        itemLine10.addCell(makePhrase(actualLaborMessage, normalBold, true));
        itemLine10.addCell(makePhrase(getAsPrice(itemDetailView.getWorkOrderItem().getActualLabor()), normal, true));
        itemLine10.addCell(makePhrase(actualPartMessage, normalBold, true));
        itemLine10.addCell(makePhrase(getAsPrice(itemDetailView.getWorkOrderItem().getActualPart()), normal, true));
        item.insertTable(itemLine10);

        //Line 11
        Table itemLine11 = new PTable(4);
        int itemLine11Widths[] = {30, 20,30,20};
        itemLine11.setWidth(100);
        itemLine11.getDefaultCell().setBorder(0);
        itemLine11.setWidths(itemLine11Widths);
        itemLine11.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        BigDecimal itemActualTotalCost = Utility.addAmt(itemDetailView.getWorkOrderItem().getActualPart(),
                itemDetailView.getWorkOrderItem().getActualLabor());
        String actualTotalCostMessage = getSmeColonMessage("userWorkOrder.text.workOrderItems.actualTotalCost","Actual Total Cost");

        itemLine11.addCell(new Phrase());
        itemLine11.addCell(new Phrase());
        itemLine11.addCell(makePhrase(actualTotalCostMessage, normalBold, true));
        itemLine11.addCell(makePhrase(getAsPrice(itemActualTotalCost), normal, true));
        item.insertTable(itemLine11);

        box.insertTable(item);

        return box;
    }

    private void drawWorkOrderScheduleHeader(Table document) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);
        box.getDefaultCell().setBorder(0);

        Table header = new PTable(1);
        int headerWidths[] = {100};
        header.setWidth(100);
        header.setWidths(headerWidths);
        header.getDefaultCell().setBorder(0);

        String headerMessage = getMessage("userWorkOrder.text.WorkOrderSchedule", "Work Order Schedule");
        header.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        header.addCell(makePhrase(headerMessage, heading, true));

        box.insertTable(header);

        document.insertTable(box);
    }

    private void drawItemDetailBoxHeader(Table document) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);
        box.getDefaultCell().setBorder(0);

        Table header = new PTable(1);
        int headerWidths[] = {100};
        header.setWidth(100);
        header.setWidths(headerWidths);
        header.getDefaultCell().setBorder(0);

        String headerMessage = getMessage("userWorkOrder.text.workOrderItems","Items");
        header.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        header.addCell(makePhrase(headerMessage, heading, true));

        box.insertTable(header);

        document.insertTable(box);
    }

    private void setup(Locale locale) {
        dateFormatter         = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        numFormatter          = NumberFormat.getInstance(locale);
        numFormatter.setMinimumFractionDigits(2);
    }

    public String getMessage(String key, Object[] arg, String defaultValue) {
        String message = null;//temporarily
        if (message == null) {
            return defaultValue;
        } else {
            return message;
        }
    }

    private String getMessage(String key, String defVal) {
        return  getMessage(key,null,defVal);
    }

    private String getSmeColonMessage(String key, String defVal){
        return  getMessage(key,null,defVal)+":";
    }

    private void drawContactBox(PdfWriter writer,
                                Document document,
                                ContactInfo contactInfo,
                                AccountData account,
                                String imgPath) throws DocumentException {
        Table box = new PTable(1);
        box.setWidth(100);

        Table contact = new PTable(1);
        contact.setWidth(100);
        contact.getDefaultCell().setBorder(0);

        // Line Header
        Table contactLineHeader = new PTable(1);
        int contactLineHeaderWidths[] = {100};
        String contactHaderMessage = getMessage("userWorkOrder.text.userContactInformatio","Contact Information");
        contactLineHeader.setWidths(contactLineHeaderWidths);
        contactLineHeader.setWidth(100);
        contactLineHeader.getDefaultCell().setBackgroundColor(Color.black);
        contactLineHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        contactLineHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        contactLineHeader.addCell(makePhrase(contactHaderMessage, white_heading, true));

        // Line 1
        Table contactLine1 = new PTable(2);
        int contactLine1Widths[] = {25, 75};
        contactLine1.setWidth(100);
        contactLine1.getDefaultCell().setBorder(0);
        contactLine1.setWidths(contactLine1Widths);
        contactLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String firstNameMessage = getSmeColonMessage("userWorkOrder.text.firstName","First Name");

        contactLine1.addCell(makePhrase(firstNameMessage, normalBold, true));
        contactLine1.addCell(makePhrase(Utility.strNN(contactInfo.firstName.getValue()), normal, true));

        // Line 2
        Table contactLine2 = new PTable(2);
        int contactLine2Widths[] = {25, 75};
        contactLine2.setWidth(100);
        contactLine2.getDefaultCell().setBorder(0);
        contactLine2.setWidths(contactLine2Widths);
        contactLine2.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String lastNameMessage = getSmeColonMessage("userWorkOrder.text.lastName","Last Name");

        contactLine2.addCell(makePhrase(lastNameMessage, normalBold, true));
        contactLine2.addCell(makePhrase(Utility.strNN(contactInfo.lastName.getValue()), normal, true));

        // Line 3
        Table contactLine3 = new PTable(2);
        int contactLine3Widths[] = {25, 75};
        contactLine3.setWidth(100);
        contactLine3.getDefaultCell().setBorder(0);
        contactLine3.setWidths(contactLine3Widths);
        contactLine3.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String address1Message = getSmeColonMessage("userWorkOrder.text.address1","Address 1");

        contactLine3.addCell(makePhrase(address1Message, normalBold, true));
        contactLine3.addCell(makePhrase(Utility.strNN(contactInfo.address1.getValue()), normal, true));

        // Line 4
        Table contactLine4 = new PTable(2);
        int contactLine4Widths[] = {25, 75};
        contactLine4.setWidth(100);
        contactLine4.getDefaultCell().setBorder(0);
        contactLine4.setWidths(contactLine4Widths);
        contactLine4.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String address2Message = getSmeColonMessage("userWorkOrder.text.address2","Address 2");

        contactLine4.addCell(makePhrase(address2Message, normalBold, true));
        contactLine4.addCell(makePhrase(Utility.strNN(contactInfo.address2.getValue()), normal, true));

        // Line 5
        Table contactLine5 = new PTable(2);
        int contactLine5Widths[] = {25, 75};
        contactLine5.setWidth(100);
        contactLine5.getDefaultCell().setBorder(0);
        contactLine5.setWidths(contactLine5Widths);
        contactLine5.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String countryMessage = getSmeColonMessage("userWorkOrder.text.conutry","Country");

        contactLine5.addCell(makePhrase(countryMessage, normalBold, true));
        contactLine5.addCell(makePhrase(Utility.strNN(contactInfo.country.getValue()), normal, true));

        // Line 6
        Table contactLine6 = new PTable(2);
        int contactLine6Widths[] = {25, 75};
        contactLine6.setWidth(100);
        contactLine6.getDefaultCell().setBorder(0);
        contactLine6.setWidths(contactLine6Widths);
        contactLine6.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String cityMessage = getSmeColonMessage("userWorkOrder.text.city","City");

        contactLine6.addCell(makePhrase(cityMessage, normalBold, true));
        contactLine6.addCell(makePhrase(Utility.strNN(contactInfo.city.getValue()), normal, true));

        // Line 7
        Table contactLine7 = new PTable(4);
        int contactLine7Widths[] = {25, 30, 20, 25};
        contactLine7.setWidth(100);
        contactLine7.getDefaultCell().setBorder(0);
        contactLine7.setWidths(contactLine7Widths);
        contactLine7.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String stateMessage = getSmeColonMessage("userWorkOrder.text.state","State");
        String zipMessage   = getSmeColonMessage("userWorkOrder.text.zip","Zip");

        contactLine7.addCell(makePhrase(stateMessage, normalBold, true));
        contactLine7.addCell(makePhrase(Utility.strNN(contactInfo.state.getValue()), normal, true));
        contactLine7.addCell(makePhrase(zipMessage, normalBold, true));
        contactLine7.addCell(makePhrase(Utility.strNN(contactInfo.zip.getValue()), normal, true));

        // Line 8
        Table contactLine8 = new PTable(4);
        int contactLine8Widths[] = {25, 30, 20, 25};
        contactLine8.setWidth(100);
        contactLine8.getDefaultCell().setBorder(0);
        contactLine8.setWidths(contactLine8Widths);
        contactLine8.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String phoneMessage  = getSmeColonMessage("userWorkOrder.text.phone","Phone");
        String mobileMessage = getSmeColonMessage("userWorkOrder.text.mobileNym","Mobile");

        contactLine8.addCell(makePhrase(phoneMessage, normalBold, true));
        contactLine8.addCell(makePhrase(Utility.strNN(contactInfo.phone.getValue()), normal, true));
        contactLine8.addCell(makePhrase(mobileMessage, normalBold, true));
        contactLine8.addCell(makePhrase(Utility.strNN(contactInfo.mobile.getValue()), normal, true));

        // Line 9
        Table contactLine9 = new PTable(4);
        int contactLine9Widths[] = {25, 30, 20, 25};
        contactLine9.setWidth(100);
        contactLine9.getDefaultCell().setBorder(0);
        contactLine9.setWidths(contactLine9Widths);
        contactLine9.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String faxMessage   = getSmeColonMessage("userWorkOrder.text.fax","Fax");
        String emailMessage = getSmeColonMessage("userWorkOrder.text.email","Email");

        contactLine9.addCell(makePhrase(faxMessage, normalBold, true));
        contactLine9.addCell(makePhrase(Utility.strNN(contactInfo.fax.getValue()), normal, true));
        contactLine9.addCell(makePhrase(emailMessage, normalBold, true));
        contactLine9.addCell(makePhrase(Utility.strNN(contactInfo.email.getValue()), normal, true));

        contact.insertTable(contactLineHeader);
        contact.insertTable(contactLine1);
        contact.insertTable(contactLine2);
        contact.insertTable(contactLine3);
        contact.insertTable(contactLine4);
        contact.insertTable(contactLine5);
        contact.insertTable(contactLine6);
        contact.insertTable(contactLine7);
        contact.insertTable(contactLine8);
        contact.insertTable(contactLine9);

        box.insertTable(contact);

        addToDocument(document,box,writer,account,imgPath);
    }

    private void drawLocationBox(Document document, SiteData site, PdfWriter writer, AccountData account, String imgPath) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);

        Table location = new PTable(1);
        location.setWidth(100);
        location.getDefaultCell().setBorder(0);
        // Line Header
        Table locationLineHeader = new PTable(1);
        int locationLineHeaderWidths[] = {100};
        String locationHaderMessage = getMessage("userWorkOrder.text.location","Location");
        locationLineHeader.setWidths(locationLineHeaderWidths);
        locationLineHeader.setWidth(100);
        locationLineHeader.getDefaultCell().setBackgroundColor(Color.black);
        locationLineHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        locationLineHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        locationLineHeader.addCell(makePhrase(locationHaderMessage, white_heading, true));

        //Line 1
        Table locationLine1 = new PTable(2);
        int locationLine1Widths[] = {25, 75};

        locationLine1.setWidth(100);
        locationLine1.getDefaultCell().setBorder(0);
        locationLine1.setWidths(locationLine1Widths);
        locationLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String siteNameMessage = getSmeColonMessage("userlocate.site.text.siteName","Site Name");
        String siteNameValue = site.getBusEntity().getShortDesc();

        locationLine1.addCell(makePhrase(siteNameMessage, normalBold, true));
        locationLine1.addCell(makePhrase(siteNameValue, normal, true));

        //Line2
        Table locationLine2 = new PTable(2);
        int locationLine2Widths[] = {25, 75};

        locationLine2.setWidth(100);
        locationLine2.getDefaultCell().setBorder(0);
        locationLine2.setWidths(locationLine2Widths);
        locationLine2.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String address1Message = getSmeColonMessage("shop.orderStatus.text.address1:","Address 1");
        String address1Value = site.getSiteAddress().getAddress1();

        locationLine2.addCell(makePhrase(address1Message, normalBold, true));
        locationLine2.addCell(makePhrase(address1Value, normal, true));

        //Line3
        Table locationLine3 = new PTable(2);
        int locationLine3Widths[] = {25, 75};

        locationLine3.setWidth(100);
        locationLine3.getDefaultCell().setBorder(0);
        locationLine3.setWidths(locationLine3Widths);
        locationLine3.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String address2Message = getSmeColonMessage("shop.orderStatus.text.address2:","Address 2");
        String address2Value = site.getSiteAddress().getAddress2();

        locationLine3.addCell(makePhrase(address2Message, normalBold, true));
        locationLine3.addCell(makePhrase(address2Value, normal, true));

        //Line4
        Table locationLine4 = new PTable(2);
        int locationLine4Widths[] = {25, 75};

        locationLine4.setWidth(100);
        locationLine4.getDefaultCell().setBorder(0);
        locationLine4.setWidths(locationLine4Widths);
        locationLine4.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String address3Message = getSmeColonMessage("shop.orderStatus.text.address3:","Address 3");
        String address3Value = site.getSiteAddress().getAddress3();

        locationLine4.addCell(makePhrase(address3Message, normalBold, true));
        locationLine4.addCell(makePhrase(address3Value, normal, true));

        //Line5
        Table locationLine5 = new PTable(2);
        int locationLine5Widths[] = {25, 75};

        locationLine5.setWidth(100);
        locationLine5.getDefaultCell().setBorder(0);
        locationLine5.setWidths(locationLine5Widths);
        locationLine5.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String address4Message = getSmeColonMessage("shop.orderStatus.text.address4:","Address 4");
        String address4Value = site.getSiteAddress().getAddress4();

        locationLine5.addCell(makePhrase(address4Message, normalBold, true));
        locationLine5.addCell(makePhrase(address4Value, normal, true));

        //Line6
        Table locationLine6 = new PTable(2);
        int locationLine6Widths[] = {25, 75};

        locationLine6.setWidth(100);
        locationLine6.getDefaultCell().setBorder(0);
        locationLine6.setWidths(locationLine6Widths);
        locationLine6.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String cityMessage = getSmeColonMessage("shop.orderStatus.text.sityStateZip:","City, State, Zip");
        String cityValue = site.getSiteAddress().getCity() + "      " +
                           (Utility.isSet(site.getSiteAddress().getStateProvinceCd()) ?
                            site.getSiteAddress().getStateProvinceCd() + SPACE : "") +
                            site.getSiteAddress().getPostalCode();

        locationLine6.addCell(makePhrase(cityMessage, normalBold, true));
        locationLine6.addCell(makePhrase(cityValue, normal, true));

        location.insertTable(locationLineHeader);
        location.insertTable(locationLine1);
        location.insertTable(locationLine2);
        location.insertTable(locationLine3);
        location.insertTable(locationLine4);
        location.insertTable(locationLine5);
        location.insertTable(locationLine6);

        box.insertTable(location);

        addToDocument(document, box, writer, account, imgPath);
    }

    private void drawItemsBox(PdfWriter writer,
                              Document document,
                              WorkOrderItemDetailViewVector workOrderItems,
                              AccountData account,
                              String imgPath) throws DocumentException {

        if (workOrderItems != null) {

            Table mainBox = new PTable(1);
            mainBox.setWidth(100);
            mainBox.getDefaultCell().setBorder(0);
            drawItemBoxHeader(mainBox);
            drawItemTableHeader(mainBox);

            Iterator it = workOrderItems.iterator();
            while (it.hasNext()) {
                WorkOrderItemDetailView itemDetailView = (WorkOrderItemDetailView) it.next();
                Table itemLine = buildItemDetailLine(itemDetailView);
                mainBox.insertTable(itemLine);
                if(writer.fitsPage(mainBox)){
                    document.add(mainBox);
                } else {
                    newPage(document,account,imgPath);
                    document.add(mainBox);
                }

                mainBox= new PTable(1);
                mainBox.setWidth(100);
                mainBox.getDefaultCell().setBorder(0);
            }
        }
    }

    private Table buildItemDetailLine(WorkOrderItemDetailView itemDetailView) throws DocumentException {
        Table tableBody = new PTable(4);

        int headerWidths[] = {15, 35, 25, 25};
        tableBody.setWidth(100);
        tableBody.setWidths(headerWidths);
        tableBody.getDefaultCell().setBorderWidth(2);
        tableBody.getDefaultCell().setBorderColor(Color.white);
        tableBody.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        String itemNumValue  = String.valueOf(itemDetailView.getWorkOrderItem().getWorkOrderItemId());
        String itemDescValue = itemDetailView.getWorkOrderItem().getShortDesc();
        String assetValue    = getAssetName(itemDetailView.getAssetAssoc());
        String warrantyValue = "";
        if(itemDetailView.getWarranty()!=null) {
            warrantyValue = itemDetailView.getWarranty().getShortDesc();
        }

        tableBody.addCell(makePhrase(itemNumValue, normal, true));
        tableBody.addCell(makePhrase(itemDescValue, normal, true));
        tableBody.addCell(makePhrase(assetValue, normal, true));
        tableBody.addCell(makePhrase(warrantyValue, normal, true));

        return tableBody;
    }

    private String getAssetName(WorkOrderAssetViewVector assetAssoc) {
        if( assetAssoc ==null || assetAssoc.isEmpty()){
            return "";
        } else {
            return ((WorkOrderAssetView)assetAssoc.get(0)).getAssetView().getAssetName();
        }
    }

    private void drawItemTableHeader(Table document) throws DocumentException {

        Table tableHeader = new PTable(4);

        int headerWidths[] = {15, 35, 25, 25};
        tableHeader.setWidth(100);
        tableHeader.setWidths(headerWidths);
        tableHeader.getDefaultCell().setBorderWidth(2);
        tableHeader.getDefaultCell().setBackgroundColor(Color.black);
        tableHeader.getDefaultCell().setBorderColor(Color.white);
        tableHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        String itemNumMessage  = getMessage("userWorkOrder.text.workOrderItems.item","Item");
        String itemDescMessage = getMessage("userWorkOrder.text.workOrderItems.description","Requested Service");
        String assetMessage    = getMessage("userWorkOrder.text.workOrderItems.assets","Asset");
        String warrantyMessage = getMessage("userWorkOrder.text.workOrderItems.warranty","Warranty");

        tableHeader.addCell(makePhrase(itemNumMessage, white_heading, true));
        tableHeader.addCell(makePhrase(itemDescMessage, white_heading, true));
        tableHeader.addCell(makePhrase(assetMessage, white_heading, true));
        tableHeader.addCell(makePhrase(warrantyMessage, white_heading, true));

        document.insertTable(tableHeader);
    }

    private void newPage(Document document, AccountData account, String imgPath) throws DocumentException {
        document.newPage();
        drawLogo(document,account,imgPath);
        document.add(makePhrase(SPACE, white_heading, true));
    }

    private void drawItemBoxHeader(Table document) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);
        box.getDefaultCell().setBorder(0);

        Table header = new PTable(1);
        int headerWidths[] = {100};
        header.setWidth(100);
        header.setWidths(headerWidths);
        header.getDefaultCell().setBorder(0);
        header.getDefaultCell().setBackgroundColor(Color.black);



        String headerMessage = getMessage("userWorkOrder.text.workOrderItems","Items");
        header.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        header.addCell(makePhrase(headerMessage, white_heading, true));

        box.insertTable(header);

        document.insertTable(box);
    }

    private void drawHeader(Document document, WorkOrderData workOrder,PdfWriter writer, AccountData account, String impPath) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);
        box.getDefaultCell().setBorder(0);

        Table header = new PTable(1);
        int headerWidths[] = {100};
        header.setWidth(100);
        header.setWidths(headerWidths);
        header.getDefaultCell().setBorder(0);

        String headerMessage = getMessage("userWorkOrder.text.workOrder","Work Order").toUpperCase()+" "+workOrder.getWorkOrderNum();
        header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        header.addCell(makePhrase(headerMessage, PdfBuilder.heading, true));

        box.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        box.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);

//iText 1.2.2        box.addCell(header);
        box.insertTable(header);

        addToDocument(document,box,writer,account,impPath);

    }

    private void drawWorkOrderActualDate(Document document,
                                         WorkOrderData workOrder,
                                         PdfWriter writer,
                                         AccountData account,
                                         String imgPath) throws DocumentException {
        if (workOrder.getActualStartDate() != null ||
            workOrder.getActualFinishDate() != null) {

            Table box = new PTable(1);
            box.setWidth(100);

            Table actual = new PTable(1);
            actual.setWidth(100);
            actual.getDefaultCell().setBorder(0);

            // Line Header
            Table actualLineHeader = new PTable(1);
            int actualLineHeaderWidths[] = {100};
            String actualHaderMessage = getMessage("userWorkOrder.text.workOrderActual","Actual");
            actualLineHeader.setWidths(actualLineHeaderWidths);
            actualLineHeader.setWidth(100);
            actualLineHeader.getDefaultCell().setBackgroundColor(Color.black);
            actualLineHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            actualLineHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            actualLineHeader.addCell(makePhrase(actualHaderMessage, white_heading, true));

            // Line 1
            Table actualLine1 = new PTable(4);
            int actualLine1Widths[] = {25, 30, 20, 25};

            actualLine1.setWidth(100);
            actualLine1.getDefaultCell().setBorder(0);
            actualLine1.setWidths(actualLine1Widths);
            actualLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String actStartDateMessage   = getSmeColonMessage("userWorkOrder.text.actualStartDate","Actual Start Date");
            String actStartDateValue     = getAsDate(workOrder.getActualStartDate());
            String actFinishDateMessage  = getSmeColonMessage("userWorkOrder.text.actualFinishtDate","Actual Finish Date");
            String actFinishDateValue    = getAsDate(workOrder.getActualFinishDate());

            actualLine1.addCell(makePhrase(actStartDateMessage, normalBold, true));
            actualLine1.addCell(makePhrase(actStartDateValue, normal, true));
            actualLine1.addCell(makePhrase(actFinishDateMessage, normalBold, true));
            actualLine1.addCell(makePhrase(actFinishDateValue, normal, true));

//iText 1.2.2            actual.addCell(actualLineHeader);
//iText 1.2.2            actual.addCell(actualLine1);

//iText 1.2.2            box.addCell(actual);

            actual.insertTable(actualLineHeader);
            actual.insertTable(actualLine1);

            box.insertTable(actual);

            addToDocument(document,box,writer,account,imgPath);
        }
    }

    private void drawAssetInfoBox(Document document,
                                  AssetData assetD,
                                  WarrantyDataVector warranties,
                                  PdfWriter writer,
                                  AccountData account,
                                  String imgPath) throws DocumentException {
        if (assetD != null) {
            Table box = new PTable(1);
            box.setWidth(100);

            Table assetInfoTable = new PTable(1);
            assetInfoTable.setWidth(100);
            assetInfoTable.getDefaultCell().setBorder(0);

            // Line Header
            Table assetInfoHeader = new PTable(1);
            int assetInfoHeaderWidths[] = {100};
            String assetInfoHeaderMessage = getMessage("userAssets.text.asset","Asset");
            assetInfoHeader.setWidths(assetInfoHeaderWidths);
            assetInfoHeader.setWidth(100);
            assetInfoHeader.getDefaultCell().setBackgroundColor(Color.black);
            assetInfoHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            assetInfoHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            assetInfoHeader.addCell(makePhrase(assetInfoHeaderMessage, white_heading, true));

            // Line 1
            Table assetInfoLine1 = new PTable(2);
            int assetInfoLine1Widths[] = {25, 75};

            assetInfoLine1.setWidth(100);
            assetInfoLine1.getDefaultCell().setBorder(0);
            assetInfoLine1.setWidths(assetInfoLine1Widths);
            assetInfoLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String assetNameMessage = getSmeColonMessage("userAssets.text.assetName","Asset Name");
            String assetNameValue   = assetD.getShortDesc();

            assetInfoLine1.addCell(makePhrase(assetNameMessage, normalBold, true));
            assetInfoLine1.addCell(makePhrase(assetNameValue, normal, true));

            // Line 2
            Table assetInfoLine2 = new PTable(2);
            int assetInfoLine2Widths[] = {25, 75};

            assetInfoLine2.setWidth(100);
            assetInfoLine2.getDefaultCell().setBorder(0);
            assetInfoLine2.setWidths(assetInfoLine2Widths);
            assetInfoLine2.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String modelNumberMessage = getSmeColonMessage("userAssets.shop.text.param.modelNumber:","Model Number");
            String modelNumberValue   = assetD.getModelNumber();

            assetInfoLine2.addCell(makePhrase(modelNumberMessage, normalBold, true));
            assetInfoLine2.addCell(makePhrase(modelNumberValue, normal, true));

            // Line 3
            Table assetInfoLine3 = new PTable(2);
            int assetInfoLine3Widths[] = {25, 75};

            assetInfoLine3.setWidth(100);
            assetInfoLine3.getDefaultCell().setBorder(0);
            assetInfoLine3.setWidths(assetInfoLine3Widths);
            assetInfoLine3.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String serialNumberMessage = getSmeColonMessage("userAssets.shop.text.param.serialnumber:","Serial Number");
            String serialNumberValue   = assetD.getSerialNum();

            assetInfoLine3.addCell(makePhrase(serialNumberMessage, normalBold, true));
            assetInfoLine3.addCell(makePhrase(serialNumberValue, normal, true));

            assetInfoTable.insertTable(assetInfoHeader);
            assetInfoTable.insertTable(assetInfoLine1);
            assetInfoTable.insertTable(assetInfoLine2);
            assetInfoTable.insertTable(assetInfoLine3);

            if (warranties != null && !warranties.isEmpty()) {

                // Warranties header
                Table warrantyHeader = new PTable(1);

                int warrantyHeaderWidths[] = {100};
                warrantyHeader.setWidth(100);
                warrantyHeader.setWidths(warrantyHeaderWidths);
                warrantyHeader.getDefaultCell().setBorder(0);

                String headerMessage = getMessage("userWorkOrder.text.workOrderItems.warranty", "Warranty");
                warrantyHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                warrantyHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

                warrantyHeader.addCell(makePhrase(headerMessage, heading, true));


                // Warranties Table header
                Table warrantyTableHeader = new PTable(4);
                int warrantyTableHeaderWidths[] = {25, 30, 20, 25};

                warrantyTableHeader.setWidth(100);
                warrantyTableHeader.getDefaultCell().setBorder(0);
                warrantyTableHeader.setWidths(warrantyTableHeaderWidths);
                warrantyTableHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

                String warrantyMessage = getSmeColonMessage("userWarranty.text.warrantyNumber","Warranty#");
                String warrantyNameMessage = getSmeColonMessage("storeWarranty.text.warrantyName","Warranty Name");
                String warrantyDurationTypeMessage = getSmeColonMessage("userWarranty.text.warrantyDurationTypeCd","DurationType");
                String warrantyDurationMessage = getSmeColonMessage("userWarranty.text.warrantyDuration","Duration");

                warrantyTableHeader.addCell(makePhrase(warrantyMessage, normalBold, true));
                warrantyTableHeader.addCell(makePhrase(warrantyNameMessage, normalBold, true));
                warrantyTableHeader.addCell(makePhrase(warrantyDurationTypeMessage, normalBold, true));
                warrantyTableHeader.addCell(makePhrase(warrantyDurationMessage, normalBold, true));

                assetInfoTable.insertTable(warrantyHeader);
                assetInfoTable.insertTable(warrantyTableHeader);

                // Warranties description
                int size = warranties.size();
                WarrantyData currentWarranty;
                Table warrantyLine;
                int warrantyLineWidths[] = {25, 30, 20, 25};

                String warrantyNumberValue;
                String warrantyNameValue;
                String warrantyDurationTypeValue;
                String warrantyDurationValue;

                for (int i = 0; i < size; i++) {
                    currentWarranty = (WarrantyData)warranties.get(i);

                    warrantyLine = new PTable(4);
                    warrantyLine.setWidth(100);
                    warrantyLine.getDefaultCell().setBorder(0);
                    warrantyLine.setWidths(warrantyLineWidths);
                    warrantyLine.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

                    warrantyNumberValue = Utility.strNN(currentWarranty.getWarrantyNumber());
                    warrantyNameValue = Utility.strNN(currentWarranty.getShortDesc());
                    warrantyDurationTypeValue = Utility.strNN(currentWarranty.getDurationTypeCd());
                    warrantyDurationValue = Utility.strNN(Integer.toString(currentWarranty.getDuration()));

                    warrantyLine.addCell(makePhrase(warrantyNumberValue, normal, true));
                    warrantyLine.addCell(makePhrase(warrantyNameValue, normal, true));
                    warrantyLine.addCell(makePhrase(warrantyDurationTypeValue, normal, true));
                    warrantyLine.addCell(makePhrase(warrantyDurationValue, normal, true));

                    assetInfoTable.insertTable(warrantyLine);
                }
            }
            box.insertTable(assetInfoTable);

            addToDocument(document,box,writer,account,imgPath);

            document.add(makeBlankLine());
            document.add(makeBlankLine());
        }
    }

    private void drawServiceProviderBox(Document document, ServiceProviderData providerData, PdfWriter writer, AccountData account, String imgPath) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);

        Table providerTable = new PTable(1);
        providerTable.setWidth(100);
        providerTable.getDefaultCell().setBorder(0);

        // Line Header
        Table providerLineHeader = new PTable(1);
        int providerLineHeaderWidths[] = {100};
        String providerHaderMessage = getMessage("userWorkOrder.text.serviceProvider","Service provider");
        providerLineHeader.setWidths(providerLineHeaderWidths);
        providerLineHeader.setWidth(100);
        providerLineHeader.getDefaultCell().setBackgroundColor(Color.black);
        providerLineHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        providerLineHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        providerLineHeader.addCell(makePhrase(providerHaderMessage, white_heading, true));

        // Line 1
        Table providerLine1 = new PTable(2);
        int providerLine1Widths[] = {25, 75};

        providerLine1.setWidth(100);
        providerLine1.getDefaultCell().setBorder(0);
        providerLine1.setWidths(providerLine1Widths);
        providerLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String providerMessage = getSmeColonMessage("userWorkOrder.text.provider","Provider");
        String providerValue   = Utility.strNN(providerData.getBusEntity().getShortDesc());

        providerLine1.addCell(makePhrase(providerMessage, normalBold, true));
        providerLine1.addCell(makePhrase(providerValue, normal, true));

        // Line 2
        Table providerLine2 = new PTable(4);
        int providerLine2Widths[] = {25, 30, 20, 25};

        providerLine2.setWidth(100);
        providerLine2.getDefaultCell().setBorder(0);
        providerLine2.setWidths(providerLine2Widths);
        providerLine2.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String contactNameMessage = getSmeColonMessage("userWorkOrder.text.serviceProvider.contactName","Contact Name");
        String contactNameValue = Utility.strNN(providerData.getPrimaryAddress().getName1()) +
                " " + Utility.strNN(providerData.getPrimaryAddress().getName2());

        String contactPhoneMessage = getSmeColonMessage("userWorkOrder.text.serviceProvider.contactPhone","Contact Phone");
        String contactPhoneValue = Utility.strNN(providerData.getPrimaryPhone().getPhoneNum());

        providerLine2.addCell(makePhrase(contactNameMessage, normalBold, true));
        providerLine2.addCell(makePhrase(contactNameValue, normal, true));
        providerLine2.addCell(makePhrase(contactPhoneMessage, normalBold, true));
        providerLine2.addCell(makePhrase(contactPhoneValue, normal, true));

        // Line 3
        Table providerLine3 = new PTable(4);
        int providerLine3Widths[] = {25, 30, 20, 25};

        providerLine3.setWidth(100);
        providerLine3.getDefaultCell().setBorder(0);
        providerLine3.setWidths(providerLine3Widths);
        providerLine3.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String contactFaxMessage = getSmeColonMessage("userWorkOrder.text.serviceProvider.contactFax","Contact Fax");
        String contactFaxValue   = Utility.strNN(providerData.getPrimaryFax().getPhoneNum());

        String contactEmailMessage = getSmeColonMessage("userWorkOrder.text.serviceProvider.contactEmail","Contact Email");
        String contactEmailValue   = Utility.strNN(providerData.getPrimaryEmail().getEmailAddress());

        providerLine3.addCell(makePhrase(contactFaxMessage, normalBold, true));
        providerLine3.addCell(makePhrase(contactFaxValue, normal, true));
        providerLine3.addCell(makePhrase(contactEmailMessage, normalBold, true));
        providerLine3.addCell(makePhrase(contactEmailValue, normal, true));

        providerTable.insertTable(providerLineHeader);
        providerTable.insertTable(providerLine1);
        providerTable.insertTable(providerLine2);
        providerTable.insertTable(providerLine3);

        box.insertTable(providerTable);

        addToDocument(document,box,writer,account,imgPath);

    }
    private void addToDocument(Document document, Table box, PdfWriter writer, AccountData account, String imgPath) throws DocumentException {
        if(writer.fitsPage(box,document.bottomMargin())){
            document.add(box);
        } else {
            newPage(document,account,imgPath);
            document.add(box);
        }
    }
/*
    private void addToDocument(Document document, PdfPTable box, PdfWriter writer, AccountData account, String imgPath) throws DocumentException {
        if(writer.fitsPage(box,document.bottomMargin())){
            document.add(box);
        } else {
            newPage(document,account,imgPath);
            document.add(box);
        }
    }
*/
    private void drawSummaryBox(Document document,
                                WorkOrderData workOrder,
                                PdfWriter writer,
                                AccountData account,
                                String imgPath,
                                UserData user,
                                CostCenterData costCenter,
                                Locale locale, 
                                StoreData store, 
                                SiteData site) throws DocumentException {
    	

    	boolean displayDistributorAccountReferenceNumber = false;    
    	boolean displayDistributorSiteReferenceNumber = false;    
        PropertyDataVector miscProperties = store.getMiscProperties();
        for (int i = 0; i < miscProperties.size(); i++) {
            PropertyData propertyData = (PropertyData) miscProperties.get(i);
            String propType = propertyData.getPropertyTypeCd();
            String propValue = propertyData.getValue();
            if (RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_ACCT_REF_NUM.equals(propType)) {
            	displayDistributorAccountReferenceNumber = Utility.isTrue(propValue);    
            } else if (RefCodeNames.PROPERTY_TYPE_CD.DISPLAY_DISTR_SITE_REF_NUM.equals(propType)) {
            	displayDistributorSiteReferenceNumber = Utility.isTrue(propValue);    
            }
        }
    	

        Table box = new PTable(1);
        box.setWidth(100);

        Table summary = new PTable(1);
        summary.setWidth(100);
        summary.getDefaultCell().setBorder(0);

        // Line Header
        Table summaryLineHeader = new PTable(1);
        int summaryLineHeaderWidths[] = {100};
        String summaryHeaderMessage = getMessage("userWorkOrder.text.workOrderPlan","Summary");
        summaryLineHeader.setWidths(summaryLineHeaderWidths);
        summaryLineHeader.setWidth(100);
        summaryLineHeader.getDefaultCell().setBackgroundColor(Color.black);
        summaryLineHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        summaryLineHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        summaryLineHeader.addCell(makePhrase(summaryHeaderMessage, white_heading, true));
        summary.insertTable(summaryLineHeader);

        // Line 1
        Table summaryLine1 = new PTable(4);
        int summaryLine1Widths[] = {25, 30, 20, 25};

        summaryLine1.setWidth(100);
        summaryLine1.getDefaultCell().setBorder(0);
        summaryLine1.setWidths(summaryLine1Widths);
        summaryLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String poNumberMessage = getSmeColonMessage("userWorkOrder.text.PONumber","PO Number");
        String numberValue   = workOrder.getPoNumber();


        summaryLine1.addCell(makePhrase(poNumberMessage, normalBold, true));
        summaryLine1.addCell(makePhrase(numberValue, normal, true));

        summary.insertTable(summaryLine1);

        // Line 2
        if(displayDistributorAccountReferenceNumber)
        {
	        Table summaryLine2 = new PTable(4);
	        int summaryLine2Widths[] = {25, 30, 20, 25};
	
	        summaryLine2.setWidth(100);
	        summaryLine2.getDefaultCell().setBorder(0);
	        summaryLine2.setWidths(summaryLine2Widths);
	        summaryLine2.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	
	        String distributorAccountNumberMessage = getSmeColonMessage("userWorkOrder.text.DistributorAccountNumber","Distributor Account Number");
	        String distributorAccountNumberValue = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.DIST_ACCT_REF_NUM);
	
	        summaryLine2.addCell(makePhrase(distributorAccountNumberMessage, normalBold, true));
	        summaryLine2.addCell(makePhrase(distributorAccountNumberValue, normal, true));
	
	        summary.insertTable(summaryLine2);
        }


        // Line 3
        if(displayDistributorSiteReferenceNumber)
        {
	        Table summaryLine3 = new PTable(4);
	        int summaryLine3Widths[] = {25, 30, 20, 25};
	
	        summaryLine3.setWidth(100);
	        summaryLine3.getDefaultCell().setBorder(0);
	        summaryLine3.setWidths(summaryLine3Widths);
	        summaryLine3.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	
	        String distributorShipToLocationNumberMessage = getSmeColonMessage("userWorkOrder.text.DistributorShipToLocationNumber", "Distributor Ship To Location Number");
	        PropertyData prop = site.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.DIST_SITE_REFERENCE_NUMBER);
			String distributorShipToLocationNumberValue = prop != null ? prop.getValue() : "";
	
	
	        summaryLine3.addCell(makePhrase(distributorShipToLocationNumberMessage, normalBold, true));
	        summaryLine3.addCell(makePhrase(distributorShipToLocationNumberValue, normal, true));
	        summary.insertTable(summaryLine3);
        }
        
        // Line 3a
        Table summaryLine3a = new PTable(4);
        int summaryLine3aWidths[] = {25, 30, 20, 25};

        summaryLine3a.setWidth(100);
        summaryLine3a.getDefaultCell().setBorder(0);
        summaryLine3a.setWidths(summaryLine3aWidths);
        summaryLine3a.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String reqServiceMessage = getSmeColonMessage("userWorkOrder.text.requestedService","Requested Service");
        String reqServiceValue = workOrder.getShortDesc();
        summaryLine3a.addCell(makePhrase(reqServiceMessage, normalBold, true));
        summaryLine3a.addCell(makePhrase(reqServiceValue, normal, true));
        summary.insertTable(summaryLine3a);
        
        
        // Line 3b
        Table summaryLine3b = new PTable(4);
        int summaryLine3bWidths[] = {25, 30, 20, 25};

        summaryLine3b.setWidth(100);
        summaryLine3b.getDefaultCell().setBorder(0);
        summaryLine3b.setWidths(summaryLine3bWidths);
        summaryLine3b.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

		String actionMessage = getSmeColonMessage("userWorkOrder.text.actionCd","Action");
		String actionValue = getMessage("userWorkOrder.text.actionCd."+workOrder.getActionCd(),workOrder.getActionCd());
		summaryLine3b.addCell(makePhrase(actionMessage, normalBold, true));
		summaryLine3b.addCell(makePhrase(actionValue, normal, true));
        summary.insertTable(summaryLine3b);

        //Line 4
        Table summaryLine4 = new PTable(4);
        int summaryLine4Widths[] = {25, 30, 20, 25};

        summaryLine4.setWidth(100);
        summaryLine4.getDefaultCell().setBorder(0);
        summaryLine4.setWidths(summaryLine4Widths);
        summaryLine4.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String typeMessage = getSmeColonMessage("userWorkOrder.text.type","Type");
        String typeValue = getMessage("userWorkOrder.text.type."+workOrder.getTypeCd(),workOrder.getTypeCd());

        summaryLine4.addCell(makePhrase(typeMessage, normalBold, true));
        summaryLine4.addCell(makePhrase(typeValue, normal, true));
        summary.insertTable(summaryLine4);

        //Line 5
        Table summaryLine5 = new PTable(2);
        int summaryLine5Widths[] = {25, 75};

        summaryLine5.setWidth(100);
        summaryLine5.getDefaultCell().setBorder(0);
        summaryLine5.setWidths(summaryLine5Widths);
        summaryLine5.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String priorityMessage = getSmeColonMessage("serWorkOrder.text.priority","Priority");
        String priorityValue = getMessage("userWorkOrder.text.priority."+workOrder.getPriority(),workOrder.getPriority());

        summaryLine5.addCell(makePhrase(priorityMessage, normalBold, true));
        summaryLine5.addCell(makePhrase(priorityValue, normal, true));
        summary.insertTable(summaryLine5);

        //Line 6
        Table summaryLine6 = new PTable(2);
        int summaryLine6Widths[] = {25, 75};
        summaryLine6.setWidth(100);
        summaryLine6.getDefaultCell().setBorder(0);
        summaryLine6.setWidths(summaryLine6Widths);
        summaryLine6.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        if (costCenter != null) {
            String costCenterMessage = getSmeColonMessage("userWorkOrder.text.costCenter","Cost Center");
            String costCenterValue = costCenter.getShortDesc();

            summaryLine6.addCell(makePhrase(costCenterMessage, normalBold, true));
            summaryLine6.addCell(makePhrase(costCenterValue, normal, true));
        }
        summary.insertTable(summaryLine6);
        
        //Line 6a
        Table summaryLine6a = new PTable(2);
        int summaryLine6aWidths[] = {25, 75};
        summaryLine6a.setWidth(100);
        summaryLine6a.getDefaultCell().setBorder(0);
        summaryLine6a.setWidths(summaryLine6aWidths);
        summaryLine6a.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String nteMessage = getSmeColonMessage("userWorkOrder.text.NTE","NTE (not to exceed threshold)");
        String nteFormattedValue = "";
        if (Utility.isSet(workOrder.getNte())) {
            nteFormattedValue = String.format("%.2f USD", workOrder.getNte());
        }

        summaryLine6a.addCell(makePhrase(nteMessage, normalBold, true));
		summaryLine6a.addCell(makePhrase(
        		nteFormattedValue, normal, true)); 
        summary.insertTable(summaryLine6a);

        //Line 7
        Table summaryLine7= new PTable(1);
        int summaryLine7Widths[] = {100};
        summaryLine7.setWidth(100);
        summaryLine7.getDefaultCell().setBorder(0);
        summaryLine7.setWidths(summaryLine7Widths);
        summaryLine7.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
        summaryLine7.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

        String reqServDetMessage = getSmeColonMessage("userWorkOrder.text.longDesc","Requested Service Detail");

        summaryLine7.addCell(makePhrase(reqServDetMessage, normalBold, true));
        document.add(makeBlankLine());
        summary.insertTable(summaryLine7);

        //Line 8
        Table summaryLine8 = new PTable(1);
        int summaryLine8Widths[] = {100};
        summaryLine8.setWidth(70);
        summaryLine8.getDefaultCell().setBorder(0);
        summaryLine8.setWidths(summaryLine8Widths);
        summaryLine8.getDefaultCell().setVerticalAlignment(PdfPCell.TOP);
        summaryLine8.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);

        String reqServDetValue = workOrder.getLongDesc();

        summaryLine8.addCell(makePhrase(reqServDetValue, normal, true));
        document.add(makeBlankLine());
        summary.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        summary.insertTable(summaryLine8);
        
        
        //Column 2
        int lineNo = 1;
        PTable summaryLine;
        //Line 1
        summaryLine = (PTable)summary.getElement(lineNo++, 0);
        String dateTimeMessage = getSmeColonMessage("userWorkOrder.text.AddDateTime","Add Date/Time");
        String dateTimeFormat = "MMM dd yyyy HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat, locale);
        String dateTimeValue   = sdf.format(workOrder.getAddDate());
		summaryLine.addCell(makePhrase(dateTimeMessage, normalBold, true));
        summaryLine.addCell(makePhrase(dateTimeValue, normal, true));

		
        //Line 2
        summaryLine = (PTable)summary.getElement(lineNo++, 0);
        String statusMessage = getMessage("userWorkOrder.text.status","Status");
        String statusValue   = getMessage("userWorkOrder.text.status."+workOrder.getStatusCd(),workOrder.getStatusCd());
        summaryLine.addCell(makePhrase(statusMessage, normalBold, true));
        summaryLine.addCell(makePhrase(statusValue, normal, true));

        //Line 3
        summaryLine = (PTable)summary.getElement(lineNo++, 0);;
        String userNameMessage = getSmeColonMessage("shop.userProfile.text.userName","User Name");
        String userNameValue = user.getFirstName() + " " + user.getLastName();
        summaryLine.addCell(makePhrase(userNameMessage, normalBold, true));
        summaryLine.addCell(makePhrase(userNameValue, normal, true));
        

        ////
//        summary.insertTable(summaryLineHeader);
//        summary.insertTable(summaryLine1);
//        summary.insertTable(summaryLine2);
//        summary.insertTable(summaryLine3);
//        summary.insertTable(summaryLine3a);
//        summary.insertTable(summaryLine3b);
//        summary.insertTable(summaryLine4);
//        summary.insertTable(summaryLine5);
//        summary.insertTable(summaryLine6);
//        document.add(makeBlankLine());
//        summary.insertTable(summaryLine7);
//        document.add(makeBlankLine());
//        summary.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
//        summary.insertTable(summaryLine8);
        
        
        

        box.insertTable(summary);

        addToDocument(document,box,writer,account,imgPath);

    }

    private void drawWorkOrderCostSummaryBox( Document document,
                                 WorkOrderDetailDataVector woDetailDV,
                                 PdfWriter writer,
                                 AccountData account,
                                 String imgPath) throws DocumentException {
        Table box = new PTable(1);
        box.setWidth(100);

        //drawWorkOrderCostSummaryHeader(box);

        Table costSummaryBox = new PTable(1);
        costSummaryBox.setWidth(100);
        costSummaryBox.getDefaultCell().setBorder(0);

        // Line Header
        Table costSummaryHeader = new PTable(1);
        int costSummaryHeaderWidths[] = {100};
        String assetInfoHeaderMessage = getMessage("userWorkOrder.text.WorkOrderCostSummary","Work Order Cost Summary");
        costSummaryHeader.setWidths(costSummaryHeaderWidths);
        costSummaryHeader.setWidth(100);
        costSummaryHeader.getDefaultCell().setBackgroundColor(Color.black);
        costSummaryHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        costSummaryHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        costSummaryHeader.addCell(makePhrase(assetInfoHeaderMessage, white_heading, true));

        // Line 1
        Table costSummaryLine1 = new PTable(5);
        int costSummaryLine1Widths[] = {20, 20, 20, 20, 20};

        costSummaryLine1.setWidth(100);
        costSummaryLine1.getDefaultCell().setBorder(0);
        costSummaryLine1.setWidths(costSummaryLine1Widths);
        costSummaryLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String billedServiceMessage = getSmeColonMessage("userWorkOrder.text.BilledService","Billed Service");
        String warrantyMessage = getSmeColonMessage("userWorkOrder.text.workOrderItems.warranty","Warranty");
        String pmContractMessage = getSmeColonMessage("userWorkOrder.text.PMContract","P.M.Contract");
        String totalMessage = getSmeColonMessage("userWorkOrder.text.Total","Total");

        costSummaryLine1.addCell(makePhrase("", normal, true));
        costSummaryLine1.addCell(makePhrase(billedServiceMessage, normalBold, true));
        costSummaryLine1.addCell(makePhrase(warrantyMessage, normalBold, true));
        costSummaryLine1.addCell(makePhrase(pmContractMessage, normalBold, true));
        costSummaryLine1.addCell(makePhrase(totalMessage, normalBold, true));

        ArrayList partsCost = WorkOrderUtil.getPartsCostSum(woDetailDV);
        ArrayList laborCost =  WorkOrderUtil.getLaborCostSum(woDetailDV);
        ArrayList travelCost = WorkOrderUtil.getTravelCostSum(woDetailDV);

        String partsBilledServiceValue = ((BigDecimal)partsCost.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String partsWarrantyValue = ((BigDecimal)partsCost.get(1)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String partsPMContractValue = ((BigDecimal)partsCost.get(2)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String partsTotalValue = ((BigDecimal)partsCost.get(3)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborBilledServiceValue = ((BigDecimal)laborCost.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborWarrantyValue = ((BigDecimal)laborCost.get(1)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborPMContractValue = ((BigDecimal)laborCost.get(2)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String laborTotalValue = ((BigDecimal)laborCost.get(3)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelBilledServiceValue = ((BigDecimal)travelCost.get(0)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelWarrantyValue = ((BigDecimal)travelCost.get(1)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelPMContractValue = ((BigDecimal)travelCost.get(2)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String travelTotalValue = ((BigDecimal)travelCost.get(3)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        String totalBilledServiceValue = (((BigDecimal)partsCost.get(0)).
                                            add(((BigDecimal)laborCost.get(0))).
                                            add(((BigDecimal)travelCost.get(0)))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String totalWarrantyValue =      (((BigDecimal)partsCost.get(1)).
                                            add(((BigDecimal)laborCost.get(1))).
                                            add(((BigDecimal)travelCost.get(1)))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String totalPMContractValue =    (((BigDecimal)partsCost.get(2)).
                                            add(((BigDecimal)laborCost.get(2))).
                                            add(((BigDecimal)travelCost.get(2)))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String totalTotalValue =         (((BigDecimal)partsCost.get(3)).
                                            add(((BigDecimal)laborCost.get(3))).
                                            add(((BigDecimal)travelCost.get(3)))).setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        // Line 2
        Table costSummaryLine2 = new PTable(5);
        int costSummaryLine2Widths[] = {20, 20, 20, 20, 20};

        costSummaryLine2.setWidth(100);
        costSummaryLine2.getDefaultCell().setBorder(0);
        costSummaryLine2.setWidths(costSummaryLine2Widths);
        costSummaryLine2.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String partsMessage = getSmeColonMessage("userWorkOrder.text.Parts", "Parts");

        costSummaryLine2.addCell(makePhrase(partsMessage, normalBold, true));
        costSummaryLine2.addCell(makePhrase(partsBilledServiceValue, normal, true));
        costSummaryLine2.addCell(makePhrase(partsWarrantyValue, normal, true));
        costSummaryLine2.addCell(makePhrase(partsPMContractValue, normal, true));
        costSummaryLine2.addCell(makePhrase(partsTotalValue, normal, true));

        // Line 3
        Table costSummaryLine3 = new PTable(5);
        int costSummaryLine3Widths[] = {20, 20, 20, 20, 20};

        costSummaryLine3.setWidth(100);
        costSummaryLine3.getDefaultCell().setBorder(0);
        costSummaryLine3.setWidths(costSummaryLine3Widths);
        costSummaryLine3.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String laborMessage = getSmeColonMessage("userWorkOrder.text.Labor", "Labor");

        costSummaryLine3.addCell(makePhrase(laborMessage, normalBold, true));
        costSummaryLine3.addCell(makePhrase(laborBilledServiceValue, normal, true));
        costSummaryLine3.addCell(makePhrase(laborWarrantyValue, normal, true));
        costSummaryLine3.addCell(makePhrase(laborPMContractValue, normal, true));
        costSummaryLine3.addCell(makePhrase(laborTotalValue, normal, true));

        // Line 4
        Table costSummaryLine4 = new PTable(5);
        int costSummaryLine4Widths[] = {20, 20, 20, 20, 20};

        costSummaryLine4.setWidth(100);
        costSummaryLine4.getDefaultCell().setBorder(0);
        costSummaryLine4.setWidths(costSummaryLine4Widths);
        costSummaryLine4.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String travelMessage = getSmeColonMessage("userWorkOrder.text.Travel", "Travel");

        costSummaryLine4.addCell(makePhrase(travelMessage, normalBold, true));
        costSummaryLine4.addCell(makePhrase(travelBilledServiceValue, normal, true));
        costSummaryLine4.addCell(makePhrase(travelWarrantyValue, normal, true));
        costSummaryLine4.addCell(makePhrase(travelPMContractValue, normal, true));
        costSummaryLine4.addCell(makePhrase(travelTotalValue, normal, true));

        // Line 5
        Table costSummaryLine5 = new PTable(5);
        int costSummaryLine5Widths[] = {20, 20, 20, 20, 20};

        costSummaryLine5.setWidth(100);
        costSummaryLine5.getDefaultCell().setBorder(0);
        costSummaryLine5.setWidths(costSummaryLine5Widths);
        costSummaryLine5.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

        String totalLineMessage = getSmeColonMessage("userWorkOrder.text.Total", "Total");

        costSummaryLine5.addCell(makePhrase(totalLineMessage, normalBold, true));
        costSummaryLine5.addCell(makePhrase(totalBilledServiceValue, normal, true));
        costSummaryLine5.addCell(makePhrase(totalWarrantyValue, normal, true));
        costSummaryLine5.addCell(makePhrase(totalPMContractValue, normal, true));
        costSummaryLine5.addCell(makePhrase(totalTotalValue, normal, true));

/*  iTable 1.2.2        costSummaryBox.addCell(costSummaryHeader);
        costSummaryBox.addCell(costSummaryLine1);
        costSummaryBox.addCell(costSummaryLine2);
        costSummaryBox.addCell(costSummaryLine3);
        costSummaryBox.addCell(costSummaryLine4);
        costSummaryBox.addCell(costSummaryLine5);

        box.addCell(costSummaryBox);
*/
        costSummaryBox.insertTable(costSummaryHeader);
        costSummaryBox.insertTable(costSummaryLine1);
        costSummaryBox.insertTable(costSummaryLine2);
        costSummaryBox.insertTable(costSummaryLine3);
        costSummaryBox.insertTable(costSummaryLine4);
        costSummaryBox.insertTable(costSummaryLine5);

        box.insertTable(costSummaryBox);

        addToDocument(document,box,writer,account,imgPath);
    }

    private void drawWorkOrderScheduleBox(  Document document,
                                            WorkOrderData workOrder,
                                            PdfWriter writer,
                                            AccountData account,
                                            String imgPath) throws DocumentException {

        Table scheduleBox = new PTable(1);
        scheduleBox.setWidth(100);
        scheduleBox.getDefaultCell().setBorder(0);

        if (workOrder.getEstimateStartDate() != null ||
            workOrder.getEstimateFinishDate() != null ||
            workOrder.getActualStartDate() != null ||
            workOrder.getActualFinishDate() != null) {

            drawWorkOrderScheduleHeader(scheduleBox);
            addToDocument(document, scheduleBox, writer, account, imgPath);

            drawWorkOrderQuotedDate(document, workOrder, writer, account, imgPath);

            document.add(makeBlankLine());
            document.add(makeBlankLine());

            drawWorkOrderActualDate(document, workOrder, writer, account, imgPath);
        }
    }

    private void drawWorkOrderQuotedDate(Document document,
                                         WorkOrderData workOrder,
                                         PdfWriter writer,
                                         AccountData account,
                                         String imgPath) throws DocumentException {
        if (workOrder.getEstimateStartDate() != null ||
            workOrder.getEstimateFinishDate() != null) {

            Table box = new PTable(1);
            box.setWidth(100);

            Table quoted = new PTable(1);
            quoted.setWidth(100);
            quoted.getDefaultCell().setBorder(0);

            // Line Header
            Table quotedLineHeader = new PTable(1);
            int quotedLineHeaderWidths[] = {100};
            String quotedHaderMessage = getMessage("userWorkOrder.text.workOrderQuoted","Quoted");
            quotedLineHeader.setWidths(quotedLineHeaderWidths);
            quotedLineHeader.setWidth(100);
            quotedLineHeader.getDefaultCell().setBackgroundColor(Color.black);
            quotedLineHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            quotedLineHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            quotedLineHeader.addCell(makePhrase(quotedHaderMessage, white_heading, true));

            // Line 1
            Table quotedLine1 = new PTable(4);
            int quotedLine1Widths[] = {25, 30, 20, 25};

            quotedLine1.setWidth(100);
            quotedLine1.getDefaultCell().setBorder(0);
            quotedLine1.setWidths(quotedLine1Widths);
            quotedLine1.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);

            String estStartDateMessage   = getSmeColonMessage("userWorkOrder.text.quotedStartDate","Quoted Start Date");
            String estStartDateValue     = getAsDate(workOrder.getEstimateStartDate());
            String estFinishDateMessage  = getSmeColonMessage("userWorkOrder.text.quotedFinishDate","Quoted Finish Date");
            String estFinishDateValue    = getAsDate(workOrder.getEstimateFinishDate());

            quotedLine1.addCell(makePhrase(estStartDateMessage, normalBold, true));
            quotedLine1.addCell(makePhrase(estStartDateValue, normal, true));
            quotedLine1.addCell(makePhrase(estFinishDateMessage, normalBold, true));
            quotedLine1.addCell(makePhrase(estFinishDateValue, normal, true));

//            quoted.addCell(quotedLineHeader);
//            quoted.addCell(quotedLine1);

//            box.addCell(quoted);
            quoted.insertTable(quotedLineHeader);
            quoted.insertTable(quotedLine1);

            box.insertTable(quoted);

            addToDocument(document,box,writer,account,imgPath);
        }

    }

    private String getAsDate(Date date) {
        if(date==null){
            return "";
        } else{
            return dateFormatter.format(date);
        }
    }

    private String getAsPrice(BigDecimal value) {
        if(value==null){
            return "";
        } else{
            return numFormatter.format(value);
        }
    }

    private void drawLogo(Document document, AccountData account, String imgPath) throws DocumentException {

        PdfPTable absoluteHeader = new PdfPTable(1);
        absoluteHeader.setWidthPercentage(33);
        absoluteHeader.getDefaultCell().setBorder(borderType);

        absoluteHeader.addCell(makePhrase(SPACE, normalBold, true));

        absoluteHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        document.add(absoluteHeader);

        try {
            Image i = Image.getInstance(imgPath);
            float x = document.leftMargin();
            float y = document.getPageSize().getHeight() - i.getHeight() - 5;
            i.setAbsolutePosition(x, y);
            document.add(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void drawNotesBox(PdfWriter writer,
                              Document document,
                              WorkOrderNoteDataVector notes,
                              AccountData account,
                              String imgPath) throws DocumentException {

        if (notes != null) {

            Table mainBox = new PTable(1);
            mainBox.setWidth(100);
            mainBox.getDefaultCell().setBorder(0);
            drawNoteBoxHeader(mainBox);
            drawNoteTableHeader(mainBox);

            Iterator it = notes.iterator();
            while (it.hasNext()) {
                WorkOrderNoteData note = (WorkOrderNoteData) it.next();
                Table noteLine = buildNoteDetailLine(document, note);
//iText 1.2.2                mainBox.addCell(noteLine);
                mainBox.insertTable(noteLine);
                if (writer.fitsPage(mainBox)) {
                    document.add(mainBox);
                } else {
                    newPage(document, account, imgPath);
                    document.add(mainBox);
                }

                mainBox = new PTable(1);
                mainBox.setWidth(100);
                mainBox.getDefaultCell().setBorder(0);
            }
        }
    }

    private Table buildNoteDetailLine(Document document, WorkOrderNoteData note) throws DocumentException {

        Table tableBody = new PTable(4);

        int headerWidths[] = {25, 45, 15, 15};
        tableBody.setWidth(100);
        tableBody.setWidths(headerWidths);
        tableBody.getDefaultCell().setBorderWidth(2);
        tableBody.getDefaultCell().setBorderColor(Color.white);
        tableBody.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        String noteDescValue  = note.getShortDesc();
        String noteLongDescValue = note.getNote();
        String noteAddByValue    = note.getAddBy();
        String noteAddDateValue = getAsDate(note.getAddDate());

        tableBody.addCell(makePhrase(noteDescValue, normal, true));
        tableBody.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED);
        tableBody.addCell(makePhrase(noteLongDescValue, normal, true));
        tableBody.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        tableBody.addCell(makePhrase(noteAddByValue, normal, true));
        tableBody.addCell(makePhrase(noteAddDateValue, normal, true));

        return tableBody;
    }

    private void drawNoteTableHeader(Table document) throws DocumentException {

        Table tableHeader = new PTable(4);

        int headerWidths[] = {25, 45, 15, 15};
        tableHeader.setWidth(100);
        tableHeader.setWidths(headerWidths);
        tableHeader.getDefaultCell().setBorderWidth(2);
        tableHeader.getDefaultCell().setBackgroundColor(Color.black);
        tableHeader.getDefaultCell().setBorderColor(Color.white);
        tableHeader.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        String noteDescMessage     = getMessage("userWorkOrder.text.note.description","Description");
        String noteLongDescMessage = getMessage("userWorkOrder.text.note.longDesc","Long Description");
        String noteAddByMessage    = getMessage("userWorkOrder.text.note.addBy","Add By");
        String noteAddDateMessage  = getMessage("userWorkOrder.text.note.addDate","Add Date");

        tableHeader.addCell(makePhrase(noteDescMessage, white_heading, true));
        tableHeader.addCell(makePhrase(noteLongDescMessage, white_heading, true));
        tableHeader.addCell(makePhrase(noteAddByMessage, white_heading, true));
        tableHeader.addCell(makePhrase(noteAddDateMessage, white_heading, true));

//iText 1.2.2        document.addCell(tableHeader);
        document.insertTable(tableHeader);
    }

    private void drawNoteBoxHeader(Table document) throws DocumentException {

        Table box = new PTable(1);
        box.setWidth(100);
        box.getDefaultCell().setBorder(0);

        Table header = new PTable(1);
        int headerWidths[] = {100};
        header.setWidth(100);
        header.setWidths(headerWidths);
        header.getDefaultCell().setBorder(0);
        header.getDefaultCell().setBackgroundColor(Color.black);



        String headerMessage = getMessage("userWorkOrder.text.toolbar.workOrderNotes","Notes");
        header.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        header.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        header.addCell(makePhrase(headerMessage, white_heading, true));

//iText 1.2.2        box.addCell(header);
        box.insertTable(header);

        document.insertTable(box);
    }

    private class ContactInfo {

        public WorkOrderPropertyData firstName;
        public WorkOrderPropertyData phone;
        public WorkOrderPropertyData lastName;
        public WorkOrderPropertyData fax;
        public WorkOrderPropertyData address1;
        public WorkOrderPropertyData mobile;
        public WorkOrderPropertyData address2;
        public WorkOrderPropertyData email;
        public WorkOrderPropertyData city;
        public WorkOrderPropertyData country;
        public WorkOrderPropertyData state;
        public WorkOrderPropertyData zip;


        public ContactInfo(WorkOrderPropertyDataVector properties) {
            createValue();
            if (properties != null && !properties.isEmpty()) {
                Iterator it = properties.iterator();
                while (it.hasNext()) {
                    WorkOrderPropertyData workOrderProperty = (WorkOrderPropertyData) it.next();
                    if (RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO.equals(workOrderProperty.getPropertyCd())) {
                        if (RefCodeNames.WORK_ORDER_CONTACT.ADDRESS1.equals(workOrderProperty.getShortDesc())) {
                            address1 = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.ADDRESS2.equals(workOrderProperty.getShortDesc())) {
                            address2 = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.CITY.equals(workOrderProperty.getShortDesc())) {
                            city = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.COUNTRY.equals(workOrderProperty.getShortDesc())) {
                            country = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.FAX.equals(workOrderProperty.getShortDesc())) {
                            fax = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.PHONE.equals(workOrderProperty.getShortDesc())) {
                            phone = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.MOBILE.equals(workOrderProperty.getShortDesc())) {
                            mobile = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.FIRST_NAME.equals(workOrderProperty.getShortDesc())) {
                            firstName = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.LAST_NAME.equals(workOrderProperty.getShortDesc())) {
                            lastName = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.STATE.equals(workOrderProperty.getShortDesc())) {
                            state = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.ZIP.equals(workOrderProperty.getShortDesc())) {
                            zip = workOrderProperty;
                        } else if (RefCodeNames.WORK_ORDER_CONTACT.EMAIL.equals(workOrderProperty.getShortDesc())) {
                            email = workOrderProperty;
                        }
                    }
                }
            }
        }

        public void createValue() {

            address1 = WorkOrderPropertyData.createValue();
            address1.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            address1.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            address1.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.ADDRESS1);

            address2 = WorkOrderPropertyData.createValue();
            address2.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            address2.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            address2.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.ADDRESS2);

            firstName = WorkOrderPropertyData.createValue();
            firstName.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            firstName.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            firstName.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.FIRST_NAME);

            lastName = WorkOrderPropertyData.createValue();
            lastName.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            lastName.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            lastName.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.LAST_NAME);

            city = WorkOrderPropertyData.createValue();
            city.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            city.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            city.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.CITY);

            country = WorkOrderPropertyData.createValue();
            country.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            country.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            country.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.COUNTRY);

            state = WorkOrderPropertyData.createValue();
            state.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            state.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            state.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.STATE);

            zip = WorkOrderPropertyData.createValue();
            zip.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            zip.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            zip.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.ZIP);

            phone = WorkOrderPropertyData.createValue();
            phone.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            phone.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            phone.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.PHONE);

            fax = WorkOrderPropertyData.createValue();
            fax.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            fax.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            fax.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.FAX);

            mobile = WorkOrderPropertyData.createValue();
            mobile.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            mobile.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            mobile.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.MOBILE);

            email = WorkOrderPropertyData.createValue();
            email.setPropertyCd(RefCodeNames.WORK_ORDER_PROPERTY_TYPE_CD.CONTACT_INFO);
            email.setStatusCd(RefCodeNames.WORK_ORDER_PROPERTY_STATUS_CD.ACTIVE);
            email.setShortDesc(RefCodeNames.WORK_ORDER_CONTACT.EMAIL);


        }

    }

}
