
package com.cleanwise.service.api.process.operations;


import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;

import java.util.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.OutputStream;

import com.cleanwise.service.api.session.Distributor;
import com.cleanwise.service.api.APIAccess;

import com.cleanwise.view.utils.Constants;
import org.apache.commons.lang.StringUtils;

import com.espendwise.service.api.util.AddressFormat;
import com.espendwise.service.api.util.MessageResource;

/**
 * Title:        Outbound850PDFBuilder
 * Description:  Constructs a pdf document in the form of a pdf document.It understands the various
 *               propriatary objects, and uses them to construct a purchase order document.
 * Purpose:      Constructs a pdf document.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         23.08.2007
 * Time:         18:25:44
 *
 * @author       Alexander Chickin, TrinitySoft, Inc.
 *
 * Note: Main part it the copy from com.cleanwise.view.utils.pdf.PdfOrder
 * Difference between achievement of classes it the scope level into the system packages.
 */
public class Outbound850PDFBuilder extends PdfBuilder {

    private static final String className="Outbound850PDFBuilder";

    private static ArrayList INVALID_CUST_PO_NUMS = new ArrayList();
    private static ArrayList MESSAGE_LIST = new ArrayList();
    private static String MESSAGE_PREFIX = "pdf850";
    //HashMap mResourceMap = null;
    static {
        INVALID_CUST_PO_NUMS.add("NA");
        INVALID_CUST_PO_NUMS.add("N/A");
        INVALID_CUST_PO_NUMS.add("-");
    }
    /*
    static {
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.itemNum");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.taxExempt");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.vendorItem");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.PurchasenOrder");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.page");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.billTo");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.buyerName");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.purchaseOrderNumber");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.transRefNumber");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.purchaseOrderDate");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.vendorName");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.shipTo");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.shipVia");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.requestedShipDate");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.purchaseOrderCurrency");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.customerComments");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.shippingMessage");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.lineMustBeIncluded");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.asset");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.number");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.serial");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.line");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.serviceAndDescripton");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.qtyUomPack");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.itemDescription");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.price");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.netAmount");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.serviceTotal");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.productTotal");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.tax");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.totalAmount");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.endOfPO");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.totalNumberOfPages");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.conf");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.SID");
        MESSAGE_LIST.add(MESSAGE_PREFIX+".text.contact");


    }
    */

    private  static final Comparator ORDER_ITEM_ERP_PO_LINE_NUM_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2) {
            int val1 = ((OrderItemData)o1).getErpPoLineNum();
            int val2 = ((OrderItemData)o2).getErpPoLineNum();
            return val1 - val2;
        }
    };

    private static final BigDecimal ZERO = new BigDecimal(0);
    //static configuration variables
    //configures the percentage widths of item columns in the document
    private static int itmCoumnWidth[] = {5, 15, 54, 13, 13};
    //overrides parent (technically this is bad practice as it is hiding parents variable).
    private Font normal = FontFactory.getFont(FontFactory.COURIER_BOLD, 12);
    //wheather to use a image logo or text
    boolean useImageLogo = true;
    private StoreData mStore;
    private String mStoreType = null;
    //STJ-5642 Holds user's language, country and store prefix as variant.
    private Locale mLocale; 


    /**
     * Creates a new instance of PdfPurchaseOrder
     */
    public Outbound850PDFBuilder() {
        // Reset the normal font to a larger size.
        super.setNormalFontSize(12);
    }

    private int getPoQty(OrderItemData oi) {
        if (oi.getDistItemQuantity() > 0) {
            return oi.getDistItemQuantity();
        } else {
            return oi.getTotalQuantityOrdered();
        }
    }

    private BigDecimal getPoCost(OrderItemData oi) {
        if (oi.getDistUomConvCost() != null && oi.getDistUomConvCost().compareTo(ZERO) > 0) {
            return oi.getDistUomConvCost();
        } else {
            return oi.getDistItemCost();
        }
    }

    //utility function to make an item Element.
    private Table makePoItemElement(OrderItemData pItm, Locale pLocale) throws Exception {

    	Table itmTbl = new PTable(5);
//        itmTbl.setWidthPercentage(100);
        itmTbl.setWidth(100);
        itmTbl.setWidths(itmCoumnWidth);
        itmTbl.getDefaultCell().setBorder(borderType);

        //***********First add the cleanwise sku information and general item information
        itmTbl.addCell(makePhrase(Integer.toString(pItm.getErpPoLineNum()), normal, false));
        String uom;
        if (pItm.getDistItemUom() != null) {
            uom = pItm.getDistItemUom();
        } else {
            uom = pItm.getItemUom();
        }
        String pack;
        if (Utility.isSet(pItm.getDistItemPack())) {
            pack = pItm.getDistItemPack();
        } else if (Utility.isSet(pItm.getItemPack())) {
            pack = pItm.getItemPack();
        } else {
            pack = null;
        }
        //pack = pItm.getItemPack();
        if (pack == null) {
            itmTbl.addCell(makePhrase(Integer.toString(getPoQty(pItm)) + SPACE + uom, normal, false));
        } else {
            itmTbl.addCell(makePhrase(Integer.toString(getPoQty(pItm)) + SPACE + uom + SPACE + pack, normal, false));
        }

        //get and add sku and decription phrase


        Phrase lSku = makePhrase(getMessageLoc("pdf850.text.itemNum") + Integer.toString(pItm.getItemSkuNum()), normal, true);
        lSku.add(makeChunk(pItm.getItemShortDesc(), normal, false));        
        itmTbl.addCell(lSku);

        //get and add itemCost
        String lnCstS = Utility.formatNumber(getPoCost(pItm), pLocale);
        itmTbl.addCell(makePhrase(lnCstS, normal, false));

        //get, add, and calculate line item total
        BigDecimal lnTot = getPoCost(pItm);
        lnTot = lnTot.multiply(new BigDecimal(Integer.toString(getPoQty(pItm))));
        String lnTotS = Utility.formatNumber(lnTot, pLocale);
        itmTbl.addCell(makePhrase(lnTotS, normal, false));

        //***********Now add the distributor information if it exists
        itmTbl.addCell(makePhrase("", normal, true));
        String distText;
        if ((pItm.getDistItemSkuNum() == null || (pItm.getDistItemSkuNum().equals("")))) {
            itmTbl.addCell(makePhrase(SPACE, normal, true));
            distText = SPACE;
        } else {
            itmTbl.addCell(makePhrase(getMessageLoc("pdf850.text.vendorItem"), normal, false));
            distText = pItm.getDistItemSkuNum();
        }
        if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(mStoreType)) {
            if (Utility.isTaxExemptOrderItem(pItm) || Utility.isTaxExemptItemSaleTypeCd(pItm)) {
                distText +=getMessageLoc("pdf850.text.taxExempt");
            }
        }
        Phrase distItmInfo = makePhrase(distText, normal, true);
        itmTbl.addCell(distItmInfo);
        itmTbl.addCell(makePhrase("", normal, true));
        itmTbl.addCell(makePhrase("", normal, true));
        return itmTbl;
    }

    //utility function to make an item Element.
    private Table makePoItemElement(OrderItemData pItm, HashMap pAssetInfo, Locale pLocale) throws DocumentException {

        Table itmTbl = new PTable(7);
//        itmTbl.setWidthPercentage(100);
        itmTbl.setWidth(100);
        itmTbl.setWidths(new int[]{20, 12, 18, 5, 25, 10, 10});
        itmTbl.getDefaultCell().setBorder(borderType);

        String assetName = "UNKNOWN";
        String assetNum = "UNKNOWN";
        String assetSerialNum = "UNKNOWN";

        if (pAssetInfo != null) {
            AssetData assetData = (AssetData) pAssetInfo.get(String.valueOf(pItm.getAssetId()));
            if (assetData != null) {
                assetName = assetData.getShortDesc();
                assetNum = assetData.getAssetNum();
                assetSerialNum = assetData.getSerialNum();
            }
        }

        itmTbl.addCell(makePhrase(assetName, normal, false));
        itmTbl.addCell(makePhrase(assetNum, normal, false));
        itmTbl.addCell(makePhrase(assetSerialNum, normal, false));
        itmTbl.addCell(makePhrase(Integer.toString(pItm.getErpPoLineNum()), normal, false));
        itmTbl.addCell(makePhrase(pItm.getItemShortDesc(), normal, false));

        //get and add itemCost
        String lnCstS = Utility.formatNumber(getPoCost(pItm), pLocale);;
        itmTbl.addCell(makePhrase(lnCstS, normal, false));

        //get, add, and calculate line item total
        BigDecimal lnTot = getPoCost(pItm);
        lnTot = lnTot.multiply(new BigDecimal(Integer.toString(getPoQty(pItm))));
        String lnTotS = Utility.formatNumber(lnTot, pLocale);
        itmTbl.addCell(makePhrase(lnTotS, normal, false));

        //Now add the distributor information if it exists
        itmTbl.addCell(makePhrase("", normal, true));
        String distText;
        if ((pItm.getDistItemSkuNum() == null || (pItm.getDistItemSkuNum().equals("")))) {
            itmTbl.addCell(makePhrase(SPACE, normal, true));
            distText = SPACE;
        } else {
            itmTbl.addCell(makePhrase(getMessageLoc("pdf850.text.vendorItem"), normal, false));
            distText = pItm.getDistItemSkuNum();
        }

        if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(mStoreType)) {
            if (Utility.isTaxExemptOrderItem(pItm)) {
                distText += getMessageLoc("pdf850.text.taxExempt");
            }
        }

        Phrase distItmInfo = makePhrase(distText, normal, true);
        itmTbl.addCell(distItmInfo);
        itmTbl.addCell(makePhrase("", normal, true));
        itmTbl.addCell(makePhrase("", normal, true));

        return itmTbl;
    }

    private void drawPoHeader(Document document,
                              Outbound850PDFBuilder.PdfPoStruct p850,
                              DistributorData pDist, String pErpPoNumber,
                              Date pErpPoDate, Locale pLocale,
                              int pPageNumber,
                              String pSenderName,
                              String pImageName,
                              boolean isStateProvinceRequired) throws DocumentException {

        DateFormat lDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, pLocale);
        SimpleDateFormat usaDateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        //add the logo
        //create the header
        PdfPTable absoluteHeader = new PdfPTable(2);
        absoluteHeader.setWidthPercentage(100);
        absoluteHeader.getDefaultCell().setBorder(borderType);

        if (useImageLogo) {
            absoluteHeader.addCell(makePhrase(SPACE, heading, true));
        } else {
            absoluteHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            absoluteHeader.addCell(makePhrase(p850.getAccountName(), heading, true));
        }
        absoluteHeader.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        absoluteHeader.addCell(makePhrase(getMessageLoc("pdf850.text.PurchaseOrder"), heading, true));
        document.add(absoluteHeader);

        if (useImageLogo) {
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

        //deal with po header info
        //construct the table/cells with all of the header information
        //must be the pdf specific type or the table will not use the full width of the page.
        //unfortuanately this means we are limited to PDF rendering only.
        PdfPTable headerInfo = new PdfPTable(4);
        headerInfo.setWidthPercentage(100);
        int headerWidths[] = {15, 30, 30, 25};
        headerInfo.setWidths(headerWidths);
        headerInfo.getDefaultCell().setBorder(borderType);
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        //add content, reads right to left, not vertically with content
        headerInfo.addCell(new Phrase());
        headerInfo.addCell(new Phrase());
        headerInfo.addCell(makePhrase(getMessageLoc("pdf850.text.page"), smallHeading, true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + Integer.toString(pPageNumber), normal, true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(mStoreType)) {
            headerInfo.addCell(makePhrase(getMessageLoc("pdf850.text.billTo"), smallHeading, true));
        } else {
            headerInfo.addCell(makePhrase(getMessageLoc("pdf850.text.buyerName"), smallHeading, true));
        }
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + pSenderName, normal, true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);


        boolean isCustPoNumberSet = true;
        String cp = p850.getOrderD().getRequestPoNum();

        if (cp != null) {
            cp = cp.trim();
        }

        if (!Utility.isSet(cp) || INVALID_CUST_PO_NUMS.contains(cp)) {
            isCustPoNumberSet = false;
        }

        if (isCustPoNumberSet && RefCodeNames.ERP_SYSTEM_CD.CLW_JCI.equals(p850.getPurchaseOrderD().getErpSystemCd())) {
            headerInfo.addCell(makePhrase(getMessageLoc("pdf850.text.purchaseOrderNumber"), smallHeading, true));
            headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            headerInfo.addCell(makePhrase(SPACE + p850.getPurchaseOrderD().getOutboundPoNum(), normal, true));
            headerInfo.addCell(new Phrase());
            headerInfo.addCell(new Phrase());
            headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            headerInfo.addCell(makePhrase(getMessageLoc("pdf850.text.transRefNumber"), smallHeading, true));
            headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            headerInfo.addCell(makePhrase(SPACE + pErpPoNumber, normal, true));
            headerInfo.addCell(new Phrase());
            headerInfo.addCell(new Phrase());
        } else {
            headerInfo.addCell(makePhrase(getMessageLoc("pdf850.text.purchaseOrderNumber"), smallHeading, true));
            headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
            headerInfo.addCell(makePhrase(SPACE + p850.getPurchaseOrderD().getOutboundPoNum(), normal, true));
            headerInfo.addCell(new Phrase());
            headerInfo.addCell(new Phrase());
        }

        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        headerInfo.addCell(makePhrase(getMessageLoc("pdf850.text.purchaseOrderDate"), smallHeading, true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE + lDateFormatter.format(pErpPoDate), normal, true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        headerInfo.addCell(makePhrase(SPACE, smallHeading, true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        headerInfo.addCell(makePhrase(SPACE, smallHeading, true));
        headerInfo.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

        //add the content
        document.add(headerInfo);

        //draw a line  XXX this is somewhat of a hack, as there seems to be no way to draw a line in the
        //current iText API that is relative to your current position in the document
        document.add(makeLine());

        if (pPageNumber == 1) {
            //deal with the billing and shipping information header
            PdfPTable addrHeader = new PdfPTable(2);
            addrHeader.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            addrHeader.setWidthPercentage(90);
            addrHeader.getDefaultCell().setBorder(borderType);
            addrHeader.addCell(makePhrase(getMessageLoc("pdf850.text.vendorName"), smallHeading, false));
            addrHeader.addCell(makePhrase(getMessageLoc("pdf850.text.shipTo"), smallHeading, false));
            document.add(addrHeader);


            ArrayList shipToAddr = new ArrayList();
            if (mStore.isAccountNameInSiteAddress()) {
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
            addresses.addCell(makePhrase(pDist.getBusEntity().getShortDesc(), normal, false));
            addresses.addCell(makePhrase((String) Utility.safeGetListElement(shipToAddr, 0, ""), normal, false));

            addresses.addCell(makePhrase(pDist.getPrimaryAddress().getAddress1(), normal, false));
            addresses.addCell(makePhrase( (String) Utility.safeGetListElement(shipToAddr, 1, ""), normal, false));

            addresses.addCell(makePhrase(pDist.getPrimaryAddress().getAddress2(), normal, false));
            addresses.addCell(makePhrase((String) Utility.safeGetListElement(shipToAddr, 2, ""), normal, false));

            addresses.addCell(makePhrase(pDist.getPrimaryAddress().getAddress3(), normal, false));
            addresses.addCell(makePhrase((String) Utility.safeGetListElement(shipToAddr, 3, ""), normal, false));

            addresses.addCell(makePhrase(pDist.getPrimaryAddress().getAddress4(), normal, false));
            addresses.addCell(makePhrase((String) Utility.safeGetListElement(shipToAddr, 4, ""), normal, false));

            if (Utility.isSet((String) Utility.safeGetListElement(shipToAddr, 5, ""))) {
                addresses.addCell(SPACE);
                addresses.addCell(makePhrase((String) Utility.safeGetListElement(shipToAddr, 5, ""), normal, false));
            }

            addresses.addCell(makePhrase(pDist.getPrimaryAddress().getCity() + "      " +
                    (isStateProvinceRequired && Utility.isSet(pDist.getPrimaryAddress().getStateProvinceCd()) ? pDist.getPrimaryAddress().getStateProvinceCd() + SPACE : "") +
                    pDist.getPrimaryAddress().getPostalCode(), normal, false));

            //STJ-5872.
            /*addresses.addCell(makePhrase(p850.getShipAddr().getCity() + "      " +
                    (isStateProvinceRequired && Utility.isSet(p850.getShipAddr().getStateProvinceCd()) ? p850.getShipAddr().getStateProvinceCd() + SPACE : "") +
                    p850.getShipAddr().getPostalCode(), normal, false));*/
            String city = p850.getShipAddr().getCity();
            String state = "";
            if(isStateProvinceRequired) {
            	if(Utility.isSet(p850.getShipAddr().getStateProvinceCd())) {
            		state = p850.getShipAddr().getStateProvinceCd();
            	}
            }
            String postalCode = p850.getShipAddr().getPostalCode();
            String addressFormat = Utility.getAddressFormatFor(p850.getShipAddr().getCountryCd()); 
            AddressFormat fmt = new AddressFormat("", "", "", "", "", city, state, 
					postalCode, "", addressFormat);
			String locationAddress = fmt.formatAsString();
            addresses.addCell(makePhrase(locationAddress, normal, false));
            document.add(addresses);

            // If a ship via address has been defined
            // add the information to the PO document.
            boolean addShipVia = false;
            if (p850.getShipVia() != null) {
                addShipVia = true;

            }
            if (addShipVia) {

                PdfPTable shipViaAddrHdr = new PdfPTable(1);

                shipViaAddrHdr.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
                shipViaAddrHdr.setWidthPercentage(90);
                shipViaAddrHdr.getDefaultCell().setBorder(borderType);
                shipViaAddrHdr.addCell(makePhrase(getMessageLoc("pdf850.text.shipVia"), smallHeading, false));
                document.add(shipViaAddrHdr);

                PdfPTable shipViaAddr = new PdfPTable(1);
                shipViaAddr.setWidthPercentage(90);
                shipViaAddr.getDefaultCell().setBorder(borderType);
                shipViaAddr.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

                FreightHandlerView fh = p850.getShipVia();

                shipViaAddr.addCell(makePhrase(fh.getBusEntityData().getShortDesc(), normal, false));
                shipViaAddr.addCell(makePhrase(fh.getPrimaryAddress().getAddress1(), normal, false));

                if (fh.getPrimaryAddress().getAddress2() != null
                        && fh.getPrimaryAddress().getAddress2().length() > 0) {
                    shipViaAddr.addCell(makePhrase(fh.getPrimaryAddress().getAddress2(), normal, false));
                }

                if (fh.getPrimaryAddress().getAddress3() != null
                        && fh.getPrimaryAddress().getAddress3().length() > 0) {
                    shipViaAddr.addCell(makePhrase(fh.getPrimaryAddress().getAddress3(), normal, false));
                }

                if (fh.getPrimaryAddress().getAddress4() != null && fh.getPrimaryAddress().getAddress4().length() > 0) {
                    shipViaAddr.addCell(makePhrase(fh.getPrimaryAddress().getAddress4(), normal, false));
                }
                //STJ-5872.
                city = fh.getPrimaryAddress().getCity();
                state = "";
                if(isStateProvinceRequired) {
                	if(Utility.isSet(fh.getPrimaryAddress().getStateProvinceCd())) {
                		state = fh.getPrimaryAddress().getStateProvinceCd();
                	}
                }
                postalCode = fh.getPrimaryAddress().getPostalCode();
                addressFormat = Utility.getAddressFormatFor(fh.getPrimaryAddress().getCountryCd()); 
                fmt = new AddressFormat("", "", "", "", "", city, state, 
    					postalCode, "", addressFormat);
    			locationAddress = fmt.formatAsString();
                /*shipViaAddr.addCell(makePhrase(fh.getPrimaryAddress().getCity() + "      " +
                        (isStateProvinceRequired && Utility.isSet(fh.getPrimaryAddress().getStateProvinceCd()) ? fh.getPrimaryAddress().getStateProvinceCd() + SPACE : "") +
                        fh.getPrimaryAddress().getPostalCode(), normal, false));*/
                shipViaAddr.addCell(makePhrase(locationAddress, normal, false));

                document.add(shipViaAddr);

            }

            document.add(makeLine());

            PdfPTable misc = new PdfPTable(1);

            misc.setWidthPercentage(90);
            misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_CENTER);
            misc.getDefaultCell().setBorder(borderType);
            //misc.addCell(makePhrase("******************Deliver - " + deliverDateS + "******************",normal,true));
            misc.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);

            String reqshipdate = OrderStatusDescData.getRequestedShipDate(p850.getOrderMetaDV());

            if (Utility.isSet(reqshipdate)) {
				try {
                String s = getMessageLoc("pdf850.text.requestedShipDate") +
					lDateFormatter.format(usaDateFormatter.parse(reqshipdate));
                misc.addCell(makePhrase(s, normal, true));
				} catch(java.text.ParseException exc) {
					throw new DocumentException("Wrong requested ship date format: "+
						reqshipdate+" Source exc mess: "+exc.getMessage());
				}
            }

            if (Utility.isSet(pDist.getPurchaseOrderComments())) {
                misc.addCell(makePhrase(pDist.getPurchaseOrderComments(), normal, true));
            }

            StringBuffer m1 = new StringBuffer(getMessageLoc("pdf850.text.purchaseOrderCurrency")+SPACE);
            m1.append(p850.getOrderD().getCurrencyCd());
            m1.append(", " + getMessageLoc("pdf850.text.conf")+SPACE+getMessageLoc("pdf850.text.numSign"));
            m1.append(p850.getOrderD().getOrderNum());
            if (p850.getOrderD().getSiteId() > 0) {
                m1.append(", " +getMessageLoc("pdf850.text.SID")+SPACE+getMessageLoc("pdf850.text.numSign"));
                m1.append(p850.getOrderD().getSiteId());
            }
            if (Boolean.TRUE.equals(pDist.getPrintCustContactOnPurchaseOrder())) {
                if (Utility.isSet(p850.getOrderD().getOrderContactPhoneNum()) && Utility.isSet(p850.getOrderD().getOrderContactName())) {
                    m1.append(", " + getMessageLoc("pdf850.text.contact")+SPACE).append(p850.getOrderD().getOrderContactName()).append(": ").append(p850.getOrderD().getOrderContactPhoneNum());
                } else if (Utility.isSet(p850.getOrderD().getOrderContactPhoneNum())) {
                    m1.append(", " + getMessageLoc("pdf850.text.contact")+SPACE).append(p850.getOrderD().getOrderContactPhoneNum());
                } else if (Utility.isSet(p850.getOrderD().getOrderContactName())) {
                    m1.append(", " + getMessageLoc("pdf850.text.contact")+SPACE).append(p850.getOrderD().getOrderContactName());
                }
            }

            misc.addCell(makePhrase(m1.toString(), normal, true));

            if (Utility.isSet(p850.getOrderD().getComments())) {
                misc.addCell(makeLine());
                String custComments = getMessageLoc("pdf850.text.customerComments") + p850.getOrderD().getComments();
                misc.addCell(makePhrase(custComments, normal, false));
            }

            if (Utility.isSet(p850.getSiteShipMsg())) {
                misc.addCell(makeLine());
                String cwComments = getMessageLoc("pdf850.text.shippingMessage") + p850.getSiteShipMsg();
                misc.addCell(makePhrase(cwComments, normal, false));
            }

            StringBuffer poline = new StringBuffer();
            boolean printPoLine = false;
            String custPo = p850.getOrderD().getRequestPoNum();
            if (custPo != null) {
                custPo = custPo.trim();
            }

            if (Utility.isSet(custPo) && !INVALID_CUST_PO_NUMS.contains(custPo)) {
                poline.append(p850.getOrderD().getRequestPoNum());
                printPoLine = true;
            }

            if (Utility.isSet(p850.getOrderD().getRefOrderNum())) {
                if (printPoLine) {
                    poline.append("/");
                }
                poline.append(p850.getOrderD().getRefOrderNum());
                printPoLine = true;
            }

            if (printPoLine) {
                misc.addCell(makeLine());
                misc.addCell(makePhrase(getMessageLoc("pdf850.text.lineMustBeIncluded"), normal, false));
                misc.addCell(makePhrase(poline.toString(), normal, false));
            }

            document.add(misc);
        }

        //add the item header line
        PdfPTable itemHeader;
        if (p850.isService()) {
            //add the item header line
            itemHeader = new PdfPTable(7);
            itemHeader.setWidthPercentage(100);
            itemHeader.getDefaultCell().setBorderWidth(2);
            itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
            itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
            itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
            itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
            itemHeader.setWidths(new int[]{20, 12, 18, 5, 25, 10, 10});
            itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.asset"), itemHeading, false));
            itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.number"), itemHeading, false));
            itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.serial"), itemHeading, false));
            itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.line"), itemHeading, false));
            itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.serviceAndDescripton"), itemHeading, false));
        } else {
            //add the item header line
            itemHeader = new PdfPTable(5);
            itemHeader.setWidthPercentage(100);
            itemHeader.getDefaultCell().setBorderWidth(2);
            itemHeader.getDefaultCell().setBackgroundColor(java.awt.Color.black);
            itemHeader.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
            itemHeader.getDefaultCell().setVerticalAlignment(Cell.ALIGN_MIDDLE);
            itemHeader.getDefaultCell().setBorderColor(java.awt.Color.white);
            itemHeader.setWidths(itmCoumnWidth);
            itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.line"), itemHeading, false));
            itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.qtyUomPack"), itemHeading, false));
            itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.itemDescription"), itemHeading, false));
        }
        itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.price"), itemHeading, false));
        itemHeader.addCell(makePhrase(getMessageLoc("pdf850.text.netAmount"), itemHeading, false));
        document.add(itemHeader);

    }

    /**
     * draws the trailer infomration onto the document
     * @param document Document
     * @param pErpPoNumber erp po
     * @param pPageNumber page num
     * @param pLocale locale
     * @param p850 PdfPoStruct
     * @return PdfPTable
     * @throws DocumentException if n errors
     */
    private Table makePoTrailer(Document document,
                                    String pErpPoNumber,
                                    int pPageNumber,
                                    Locale pLocale,
                                    Outbound850PDFBuilder.PdfPoStruct p850,
                                    DistributorData pDist)    throws DocumentException {
        //add trailer information
        document.add(makeBlankLine());
        Table totals = new PTable(3);
        totals.setWidth(100);
//iText 1.2.2        totals.setTotalWidth(100);
        totals.getDefaultCell().setBorder(borderType);

        //add dollar totals
        
        //***Start sub total
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        totals.addCell(makePhrase(SPACE, normal, false));
        if (p850.isService()) {
            totals.addCell(makePhrase(getMessageLoc("pdf850.text.serviceTotal"), normal, false));
        } else {
            totals.addCell(makePhrase(getMessageLoc("pdf850.text.productTotal"), normal, false));
        }
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        String subTot = Utility.formatNumber(p850.getPurchaseOrderD().getLineItemTotal(), pLocale);
        totals.addCell(makePhrase(subTot, normal, false));
        //***End sub total

        //only render tax info for non MLA stores..there is not really a compelling reason to
        //not print it except that it has never been printed and it will never be set so
        //it should not make much difference.
        if (!RefCodeNames.STORE_TYPE_CD.MLA.equals(mStore.getStoreType()) && p850.getPurchaseOrderD().getTaxTotal() != null && mStore.isTaxableIndicator()) {
            //***Start tax total
            totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            totals.addCell(makePhrase(SPACE, normal, false));
            totals.addCell(makePhrase(getMessageLoc("pdf850.text.tax"), normal, false));
            totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
            String tax = Utility.formatNumber(p850.getPurchaseOrderD().getTaxTotal(), pLocale);
            totals.addCell(makePhrase(tax, normal, false));
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
                    String chargeFee = Utility.formatNumber((BigDecimal)charge.getAmount(), pLocale);
                    totals.addCell(makePhrase(chargeFee, normal, false));
                    chargesTotal = chargesTotal.add(charge.getAmount());
        		}
        		
        	}
        	
        }catch (Exception exc){
        	exc.printStackTrace();
        }
        //***Start total
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        totals.addCell(makePhrase(SPACE, normal, false));
        totals.addCell(makePhrase(getMessageLoc("pdf850.text.totalAmount"), normal, false));
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
        BigDecimal tot = p850.getPurchaseOrderD().getPurchaseOrderTotal();

        tot = tot.add(chargesTotal);
  
        String totS = Utility.formatNumber(tot, pLocale);
        totals.addCell(makePhrase(totS, normal, false));
        //***End total

        //now add misc end of po info
        totals.getDefaultCell().setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        totals.addCell(makePhrase(SPACE, normal, false));
        totals.addCell(makePhrase(getMessageLoc("pdf850.text.endOfPO") + pErpPoNumber, normal, false));
        totals.addCell(makePhrase(SPACE, normal, false));
        totals.addCell(makePhrase(SPACE, normal, false));
        totals.addCell(makePhrase(getMessageLoc("pdf850.text.totalNumberOfPages") + pPageNumber, normal, false));
        totals.addCell(makePhrase(SPACE, normal, false));
        return totals;
    }

    /**
     * Takes in an instance of @see OutboundEDIRequestData, @see DistributorData, and @see StoreData
     * and converts them into an outbound purchase order pdf.  This document
     * looks very similar to the output when printing from directly from Lawson.
     *
     * @param pDist      the distributor this fax is going to.
     * @param p850s      the purchase orders to translate.
     * @param pStore     the store this po is comming from.
     * @param pOut       the output stream to write the pdf to.
     * @param pImageName the image to be placed in the upper left corner, if null left off pdf.
     * @throws java.io.IOException if any error occurs.
     */
    public void constructPdfPO(DistributorData pDist,
                               OutboundEDIRequestDataVector p850s,
                               StoreData pStore, OutputStream pOut,
                               String pImageName)
            throws Exception {

        if (p850s.size() == 0) {
            throw new IllegalArgumentException("OutboundEDIRequestDataVector size cannot be emtpy");
        }

        ArrayList lpos = new ArrayList();
        for (int i = 0, len = p850s.size(); i < len; i++) {
            OutboundEDIRequestData ediReq = (OutboundEDIRequestData) p850s.get(i);
            PropertyData ssmProp = null;
            if (ediReq.getSiteProperties() != null) {
                ssmProp = Utility.getProperty(ediReq.getSiteProperties(), RefCodeNames.PROPERTY_TYPE_CD.SITE_SHIP_MSG);
            }
            String siteShipMsg = null;
            if (ssmProp != null) {
                siteShipMsg = ssmProp.getValue();
            }
            String acctName = ediReq.getAccountName();

            if (acctName == null) {
                acctName = "";
            }
            Outbound850PDFBuilder.PdfPoStruct po =
                    new Outbound850PDFBuilder.PdfPoStruct
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
        generatePdf(pDist, lpos, pStore, pOut, pImageName);
    }

    /**
     * Takes in an instance of
     *
     * @throws java.io.IOException if any error occurs.
     * @see com.cleanwise.service.api.value.OrderData @see PurchaseOrderData,@see OrderItemDataVector
     * @see com.cleanwise.service.api.value.OrderAddressData, @see DistributorData, and @see StoreData
     * and converts them into an outbound purchase order pdf.  This document
     * looks very similar to the output when printing from directly from Lawson.
     *
     * @param pDist  distributor
     * @param pOrderData order
     * @param pOrderMetaDataVector order meta
     * @param pPurchaseOrderData  purchase order
     * @param pOrderItemDataVector  items
     * @param pShipAddress  ship address
     * @param pBillAddress  bill address
     * @param pAccountName  account name
     * @param pShipVia      ship via
     * @param pSiteShippingMessage shipping comments
     * @param pStore store
     * @param pOut out stream
     * @param pImageName logo image
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
                               StoreData pStore, OutputStream pOut, String pImageName) throws Exception {

        ArrayList lpo = new ArrayList();
        lpo.add(new Outbound850PDFBuilder.PdfPoStruct(pOrderData,
                pOrderMetaDataVector,
                pPurchaseOrderData,
                pOrderItemDataVector,
                pShipAddress,
                pBillAddress,
                pAccountName,
                pShipVia,
                pSiteShippingMessage,
                null, null)
        );
        generatePdf(pDist, lpo, pStore, pOut, pImageName);
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
                               StoreData pStore, OutputStream pOut,
                               String pImageName,
                               boolean isSimpleService) throws Exception {

        ArrayList lpo = new ArrayList();
        lpo.add(new Outbound850PDFBuilder.PdfPoStruct
                (pOrderData,
                        pOrderMetaDataVector,
                        pPurchaseOrderData,
                        pOrderItemDataVector,
                        pAssetInfo,
                        pShipAddress,
                        pBillAddress,
                        pAccountName,
                        pShipVia,
                        pSiteShippingMessage, isSimpleService)
        );
        generatePdf(pDist, lpo, pStore, pOut, pImageName);
    }

    /**
     * Returns the buyer name.  This is the store name for an MLA store, but for
     * the Distributor and manufacturer store it is the bill to name, which will
     * be the account name for most accounts, but is the name of the ship to when
     * the make ship to bill to flag is checked at the account level.
     * @param  pStore store
     * @param  p850 PdfPoStruct
     * @return java.io.IOException if any error occurs.
     */
    private String getBuyerName(StoreData pStore, Outbound850PDFBuilder.PdfPoStruct p850) {

        String buyerName = null;
        String storeType = pStore.getStoreType().getValue();

        if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType) ) {

            buyerName = p850.getOrderD().getOrderContactName();
            if (Utility.isSet(p850.getOrderD().getOrderContactPhoneNum())) {
                buyerName += "\n" + p850.getOrderD().getOrderContactPhoneNum();
            }
            if (p850.getBillAddr() != null) {
                buyerName = p850.getBillAddr().getShortDesc();

                if (Utility.isSet(p850.getBillAddr().getAddress1())) {
                    buyerName += "\n" + p850.getBillAddr().getAddress1();
                }

                if (Utility.isSet(p850.getBillAddr().getAddress2())) {
                    buyerName += "\n" + p850.getBillAddr().getAddress2();
                }

                if (Utility.isSet(p850.getBillAddr().getAddress3())) {
                    buyerName += "\n" + p850.getBillAddr().getAddress3();
                }

                if (Utility.isSet(p850.getBillAddr().getAddress4())) {
                    buyerName += "\n" + p850.getBillAddr().getAddress4();
                }

                //STJ-5872.
               /* buyerName += "\n" + p850.getBillAddr().getCity();
                if (pStore.isStateProvinceRequired() && Utility.isSet(p850.getBillAddr().getStateProvinceCd())) {
                    buyerName += "      " + p850.getBillAddr().getStateProvinceCd();
                }

                buyerName += SPACE + p850.getBillAddr().getPostalCode();*/
                
                String city = p850.getBillAddr().getCity();
                String state = "";
                if(pStore.isStateProvinceRequired()) {
                	if(Utility.isSet(p850.getBillAddr().getStateProvinceCd())) {
                		state = p850.getBillAddr().getStateProvinceCd();
                	}
                }
                String postalCode = p850.getBillAddr().getPostalCode();
                String addressFormat = Utility.getAddressFormatFor(p850.getBillAddr().getCountryCd()); 
                AddressFormat fmt = new AddressFormat("", "", "", "", "", city, state, 
    					postalCode, "", addressFormat);
    			String locationAddress = fmt.formatAsString();
    			buyerName += "\n" + locationAddress;
            }
        }

        if (!Utility.isSet(buyerName)) {
            buyerName = pStore.getStoreBusinessName().getValue();
        }

        return buyerName;
    }

    //main worker method.  The public methods will call this method
    //to generate the pdf after the object has been converted.
    private void generatePdf(DistributorData pDist,
                             ArrayList p850s,
                             StoreData pStore,
                             OutputStream pOut,
                             String pImageName)  throws Exception {
        mStore = pStore;
        Document document = null;

        if(pStore!=null){
            try {
              initFontsUnicode();

                //we keep track of the page number rather than useing the built in pdf functionality
                //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
                //first page of that po, and on the 9th page of the pdf.
                int pageNumber = 1;

                if(mStore.getStoreType() != null) {
                    mStoreType = mStore.getStoreType().getValue();
                }

                String storeType = mStoreType;
                //int storeId = mStore.getStoreId();

                if (RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)) {
                    useImageLogo = false;
                }
                Phrase footPhrase = makeStoreFooter(pStore);

                HeaderFooter footer = new HeaderFooter(footPhrase, false);
                footer.setAlignment(HeaderFooter.ALIGN_CENTER);

                //setup the borders from the header
                //header.setBorder(borderType);
                footer.setBorder(HeaderFooter.TOP);

                //setup the document
                document = new Document(PageSize.A4, 10, 15, 15, 15);
                //Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, pOut);
                //document.setHeader(header);
                document.setFooter(footer);
                document.open();

                for (int j = 0, len2 = p850s.size(); j < len2; j++) {
                    Outbound850PDFBuilder.PdfPoStruct l850 = (Outbound850PDFBuilder.PdfPoStruct) p850s.get(j);
                    //Verify the integrity of the po data
                    TreeSet t = new TreeSet();
                    for (int i = 0, len = l850.getOrderItemDV().size(); i < len; i++) {
                        OrderItemData itm = (OrderItemData) l850.getOrderItemDV().get(i);
                        if (itm.getErpPoNum() != null) {
                            t.add(itm.getErpPoNum());
                        }
                    }
                    if (t.size() != 1) {
                        throw new IllegalStateException("OutboundEDIRequestData contained more than 1 po.");
                    }

                    Collections.sort(l850.getOrderItemDV(),ORDER_ITEM_ERP_PO_LINE_NUM_COMPARE);

                    String lErpPoNumber = l850.getPurchaseOrderD().getErpPoNum();
                    Date lErpPoDate = ((OrderItemData) l850.getOrderItemDV().get(0)).getErpPoDate();
                    //XXX po locale, or distributor locale?
                    String localeKey = l850.getOrderD().getLocaleCd();
                    Locale lLocale = Utility.parseLocaleCode(localeKey);
                    
                    //STJ-5642
                    String storePrefix = "";
                    if(mStore != null && mStore.getPrefix() != null && Utility.isSet(mStore.getPrefix().getValue())) {
                    	storePrefix = mStore.getPrefix().getValue().replaceAll(Constants.FORWARD_SLASH,StringUtils.EMPTY);
                    }
                    	
                    mLocale = new Locale(lLocale.getLanguage(),lLocale.getCountry(),storePrefix);
                    
                    //  getting Messages by Local and storId:
                    //MessageResourceDataVector mrdv = null;
                    /*try {
                      APIAccess mApiAccess = new APIAccess();
                      PropertyService propEjb = mApiAccess.getPropertyServiceAPI();*/
                      ///** @todo  */ need to have PO sorted by Locale !!!
                      //String localeKey =  lLocale.getLanguage()+"_"+ lLocale.getCountry();

                      /*mResourceMap = propEjb.getMessageResources(storeId,localeKey, MESSAGE_PREFIX);
                    }
                    catch (Exception ex){
                      ex.printStackTrace();
                    }*/

                    drawPoHeader(document, l850, pDist, lErpPoNumber, lErpPoDate, lLocale,
                            pageNumber, getBuyerName(pStore, l850), pImageName, pStore.isStateProvinceRequired());
                    //now add the items
                    BigDecimal poTotal = new BigDecimal(0);
                    for (int i = 0, len = l850.getOrderItemDV().size(); i < len; i++) {
                        document.add(makeBlankLine());
                        OrderItemData itm = (OrderItemData) l850.getOrderItemDV().get(i);
//iText 1.2.2                       PdfPTable itmTable;
                        Table itmTable;
                        if (l850.isService()) {
                            itmTable = makePoItemElement(itm, l850.getAssetInfo(), lLocale);
                        } else {
                            itmTable = makePoItemElement(itm, lLocale);
                        }

                        //if the item data will not fit onto the page, make a new page, and redraw
                        //the header.
                        if (writer.fitsPage(itmTable, document.bottomMargin())) {
                            document.add(itmTable);
                        } else {
                            document.newPage();
                            pageNumber = pageNumber + 1;
                            drawPoHeader(document, l850, pDist, lErpPoNumber, lErpPoDate, lLocale,
                                    pageNumber, getBuyerName(pStore, l850), pImageName, pStore.isStateProvinceRequired());
                            document.add(makeBlankLine());
                            document.add(itmTable);
                        }
                        BigDecimal lnTot = getPoCost(itm);
                        lnTot = lnTot.multiply(new BigDecimal(Integer.toString(getPoQty(itm))));
                        poTotal = poTotal.add(lnTot);
                    }

                    //creates the trailer
                    Table trailer = makePoTrailer(document, lErpPoNumber, pageNumber, lLocale, l850, pDist);
                    //if it fits on the current page add it
                    if (writer.fitsPage(trailer)) {
                        document.add(trailer);
                    } else {
                        //create a brand new page just for the trailer information if it
                        //doesn't fit on the page
                        document.newPage();
                        pageNumber = pageNumber + 1;
                        drawPoHeader(document, l850, pDist, lErpPoNumber, lErpPoDate, lLocale,
                                pageNumber, getBuyerName(pStore, l850), pImageName, pStore.isStateProvinceRequired());
                        //regenerate the trailer to get the correct page number
                        trailer = makePoTrailer(document, lErpPoNumber, pageNumber, lLocale, l850, pDist);
                        document.add(trailer);
                    }

                    if (j < len2) {
                        pageNumber = 1;
                        document.newPage();
                    }
                }                
            } finally {
                if (document != null)
                	//close out the document
                    document.close();
            }
        }
    }

    /**
     * Private inner class to represent the data the po needs to print, and the
     * relationship between the data.  This makes converting between different
     * objects (outboundEdiRequests, and PurchaseOrderDesc/orderItemVectors, etc.)
     * easier
     */
    public class PdfPoStruct {

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
                           OrderPropertyDataVector pOrderProps) {
            mPurchaseOrderD = pPurchaseOrderD;
            mOrderD = pOrderD;
            mOrderMetaDV = pOmdv;
            mOrderItemDV = pOrderItemDV;
            mShipAddr = pShipAddr;
            mBillAddr = pBillAddr;
            mAccountName = pAccountName;
            mShipVia = pShipVia;
            mSiteShipMsg = pSiteShipMsg;
            mService = false;
            mAssetInfo = null;
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
                           String pSiteShipMsg, boolean isService) {
            mPurchaseOrderD = pPurchaseOrderD;
            mOrderD = pOrderD;
            mOrderMetaDV = pOmdv;
            mOrderItemDV = pOrderItemDV;
            mShipAddr = pShipAddr;
            mBillAddr = pBillAddr;
            mAccountName = pAccountName;
            mShipVia = pShipVia;
            mSiteShipMsg = pSiteShipMsg;
            mService = isService;
            mAssetInfo = pAssetInfo;
        }

        private PurchaseOrderData getPurchaseOrderD() {
            return mPurchaseOrderD;
        }

        private OrderData getOrderD() {
            return mOrderD;
        }

        private OrderItemDataVector getOrderItemDV() {
            return mOrderItemDV;
        }

        private OrderMetaDataVector getOrderMetaDV() {
            return mOrderMetaDV;
        }

        private OrderAddressData getShipAddr() {
            return mShipAddr;
        }

        private OrderAddressData getBillAddr() {
            return mBillAddr;
        }

        private String getAccountName() {
            return mAccountName;
        }

        private FreightHandlerView getShipVia() {
            return mShipVia;
        }

        private String getSiteShipMsg() {
            return mSiteShipMsg;
        }

        public boolean isService() {
            return mService;
        }

        public HashMap getAssetInfo() {
            return mAssetInfo;
        }

        public PropertyDataVector getAccountProperties() {
            return mAcctProps;
        }

        public OrderPropertyDataVector getOrderPrFoperties() {
            return mOrderProps;
        }
    }

    private String getMessageLoc( String pKey){
      /*MessageResourceData mrD = (MessageResourceData) mResourceMap.get(pKey);
      String text = (mrD!=null && mrD.getValue()!=null)?
          mrD.getValue():pKey;*/
          
      //STJ-5642
      String text = MessageResource.getMessage(mLocale, pKey);
      return text;

    }
    public void initFontsUnicode() throws Exception {

      //String fontDir = ClwApiCustomizer.getServerDir() + "/xsuite/fonts/";
      // The files are all relative to the JBoss bin directory.
      String fontDir =  "../xsuite/fonts/";
      String fontFileAL = "ARIALUNI.TTF";

      BaseFont alBaseFont = BaseFont.createFont(fontDir + fontFileAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


      normal = new Font(alBaseFont, 12, Font.BOLD);

      smallHeading = new Font(alBaseFont, 9, Font.BOLD);
      itemHeading = new Font(alBaseFont, 8, Font.BOLD);
      itemHeading.setColor(java.awt.Color.white);
      heading = new Font(alBaseFont, 18, Font.BOLD);
   
      //STJ-5690 Purchase Order PDF - Footer does not handle non-ASCII characters
      BaseFont timesBaseFont   = BaseFont.createFont(fontDir + "times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
      BaseFont timesBdBaseFont = BaseFont.createFont(fontDir + "timesbd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
      BaseFont timesItBaseFont = BaseFont.createFont(fontDir + "timesi.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

      small   = new Font(timesBaseFont, 8);
      smallItalic = new Font(timesItBaseFont, 8);
      smallBold   = new Font(timesBdBaseFont, 8);

  }

}
