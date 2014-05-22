/*
 * PdfManifestLabel.java
 *
 * Created on June 20, 2003, 11:14 AM
 */

package com.cleanwise.view.utils.pdf;
import com.lowagie.text.pdf.*;
import com.lowagie.text.*;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.cleanwise.service.api.value.*;
import java.awt.Color; //used by the lowagie/iText api, we are not actually using the awt toolkit here
import java.util.Iterator;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.RefCodeNames;

/**
 *
 * @author  bstevens
 */
public class PdfManifestLabel extends PdfSuper {

    private int barcodeCellPadding;
    private static final int SEPERATOR_LINE_THICKNESS = 7;

    //private int printMode = MULTIPLE_PRINT_MODE;

    //member variables that may be changed through the api later.  Set up the default values
    private String mPrintMode = RefCodeNames.MANIFEST_LABEL_MODE_CD.CONTINUOUS_PRINT_MODE;
    private int mPageWidth  = 234;
    private int mPageHeight = 432;
    private String mLabelType = RefCodeNames.MANIFEST_LABEL_TYPE_CD.PLAIN;


    /**
     *Sets the print mode.  @see MULTIPLE_PRINT_MODE and @see CONTINUOUS_PRINT_MODE.
     *@param the new print mode to use.
     */
    public void setPrintMode(String pPrintMode){
        if(pPrintMode != null){
            mPrintMode = pPrintMode;
        }
    }
    /**
     *The print mode.  @see MULTIPLE_PRINT_MODE and @see CONTINUOUS_PRINT_MODE.
     *@param the new print mode to use.
     */
    public String getPrintMode(){
        return mPrintMode;
    }

    /**
     *Page Width to use when in print mode = CONTINUOUS_PRINT_MODE
     */
    public void setPageWidth(int pPageWidth){
        if(pPageWidth > 0){
            mPageWidth = pPageWidth;
        }
    }

    /**
     *Page Width to use when in print mode = CONTINUOUS_PRINT_MODE
     */
    public int getPageWidth(){
        return mPageWidth;
    }

    /**
     *Page Height to use when in print mode = CONTINUOUS_PRINT_MODE
     */
    public void setPageHeight(int pPageHeight){
        if(pPageHeight > 0){
            mPageHeight = pPageHeight;
        }
    }

    /**
     *Page Height to use when in print mode = CONTINUOUS_PRINT_MODE
     */
    public int getPageHeight(){
        return mPageHeight;
    }

    /**
     *Type of lable, USPS delivey confirm etc
     */
    public void setLabelType(String pLabelType){
        if(pLabelType != null){
            mLabelType = pLabelType;
        }
    }
    /**
     *Type of lable, USPS delivey confirm etc
     */
    public String getLabelType(){
        return mLabelType;
    }

    /** Creates a new instance of PdfManifestLabel */
    public PdfManifestLabel() {
    }

    public void constructPdfManifestLabel(OutputStream pOut,
            PurchaseOrderStatusDescDataViewVector pPurchaseOrdersToManifest)
            throws IOException{
        try {
            generatePdf(pOut,pPurchaseOrdersToManifest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    //creates a barcode with all our defaults set up.
    private Phrase createBarcode(PdfWriter pWriter,String pText,int pType){
        PdfContentByte cb = pWriter.getDirectContent();
        Barcode128 code = new Barcode128();
        code.setCodeType(pType);
        code.setGenerateChecksum(true);
        code.setBarHeight(60);  //minimum height for USPS is .75 inches
        code.setX(1.152f); //distance between bars (basically controls the width)
        //set font to null as we have special non standard requierments for the label,
        //so we will just print ours out by hand.
        code.setFont(null);

        code.setCode(pText);
        Image image = code.createImageWithBarcode(cb, null, null);
        return new Phrase(new Chunk(image, 0, 0));
    }


    private Phrase createCwBarcode(PdfWriter pWriter,String pText){
        PdfContentByte cb = pWriter.getDirectContent();
        Barcode128 code = new Barcode128();
        code.setCodeType(Barcode128.CODE128_UCC);
        code.setGenerateChecksum(true);
        code.setBarHeight(mBarCodeHeight);
        code.setX(mBarCodeSpacing);
        code.setFont(null);

        code.setCode(pText);
        Image image = code.createImageWithBarcode(cb, null, null);
        return new Phrase(new Chunk(image, 0, 0));
    }



    PdfPTable masterTable;

    private static int mBarCodeHeight = 120;
    private static float mBarCodeSpacing = 1.5f;

    private void fmtCwAddressLabel
            (PdfWriter writer,
            PurchaseOrderStatusDescDataViewVector pPurchaseOrdersToManifest)
            throws Exception{

        Font  normalcw = FontFactory.getFont(FontFactory.HELVETICA,9);
        Font  bigcw = FontFactory.getFont(FontFactory.HELVETICA,12);

        masterTable.getDefaultCell().setBorder(0);
        masterTable.getDefaultCell().setPadding(0);
        barcodeCellPadding = 12;
        Iterator poIt = pPurchaseOrdersToManifest.iterator();
        int counter = 0;
        while(poIt.hasNext()){

            PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) poIt.next();
            Iterator manIt = po.getManifestItems().iterator();
            String msg = "PO counter=" + counter;
            while(manIt.hasNext()){
                ManifestItemView manifest = (ManifestItemView) manIt.next();

                PdfPTable subTable = new PdfPTable(1);
                subTable.getDefaultCell().setBorder(0);
                subTable.getDefaultCell().setPadding(0);



                // start ship from and ship to info
                float[] widths1 = {0.4f, 0.6f};
                PdfPTable section1 = new PdfPTable(widths1);
                //section1.getDefaultCell().setBorder(borderType);
                section1.getDefaultCell().setPadding(1);
                String vendorInfo = "FROM\n";
                if ( null != po.getDistributorBusEntityData() &&
                        null != po.getDistributorBusEntityData().getShortDesc() ) {
                    vendorInfo += po.getDistributorBusEntityData().getShortDesc() + "\n";
                }
                vendorInfo += mkAddressString(manifest.getReturnAddress());
                section1.addCell(makePhrase(vendorInfo,normalcw,false));

                String shiptoInfo = "TO\n";
                shiptoInfo += mkAddressString(po.getShipToAddress());
                section1.addCell(makePhrase(shiptoInfo,bigcw,false));
                subTable.addCell(section1);
                // end ship from and ship to info

                // start of zip and po info
                float[] widths2 = {0.75f, 0.25f};
                PdfPTable section2 = new PdfPTable(widths2);
                section2.getDefaultCell().setBorder(0);
                section2.getDefaultCell().setPadding(0);
                section2.getDefaultCell().setPaddingTop(1);
                section2.getDefaultCell().setPaddingBottom(1);
                String tz =  po.getShipToAddress().getPostalCode();
                if (  null == tz ) tz = "";
                // Only 5 digits of zip code info will fit on the
                // label.
                if ( tz.length() > 5 ) tz = tz.substring(0,5);
                String z = "(420)"+ tz;
                String shiptoZipInfo = "SHIP TO POSTAL CODE    "+z;

                PdfPTable zipTable = new PdfPTable(1);
                zipTable.getDefaultCell().setBorder(0);
                zipTable.getDefaultCell().setPadding(0);
                PdfPCell cz1 =	new PdfPCell(makePhrase(shiptoZipInfo,normalcw,false));
                cz1.setBorder(0);
                cz1.setPaddingLeft(2);
                zipTable.addCell(cz1);

                mBarCodeHeight = 42; // about half an inch.
                PdfPCell cz =	new PdfPCell
                        (this.createCwBarcode(writer,"420" + tz));

                cz.setBorder(0);
                cz.setPadding(0);
                cz.setPaddingLeft(barcodeCellPadding);
                cz.setHorizontalAlignment(Element.ALIGN_LEFT);

                zipTable.addCell(cz);
                section2.addCell(zipTable);

                // PO info
                String tpo = po.getOrderData().getRequestPoNum();
                if ( tpo.startsWith("EPRO") ) {
                    tpo = tpo.substring(4);
                }

                int tidx = tpo.indexOf('/');
                if ( tidx != -1 ) {
                    tpo = tpo.substring(0,tidx);
                }
                String poInfo = "PO " + tpo
                        + "\nSUPP# 018283\nNFR";

                PdfPCell cTok =	new PdfPCell(makePhrase(poInfo,normalcw,false));
                cTok.setHorizontalAlignment(Element.ALIGN_LEFT);
                cTok.setVerticalAlignment(Element.ALIGN_CENTER);
                section2.addCell(cTok);
                subTable.addCell(section2);

                // end of zip and po info


                mBarCodeHeight = 105; // remaining bar codes need to be 1.25+ inches


                String siteToken = po.getShipToAddress().getShortDesc();
                int idx = siteToken.indexOf(' ');
                if ( idx != -1 ) { siteToken = siteToken.substring(0,idx);}
                String origsiteToken = siteToken;
                if ( null != siteToken && siteToken.length() <= 5) {
                    siteToken = "0" + siteToken;
                }

                // site token info
                PdfPTable section3 = new PdfPTable(widths2);
                section3.getDefaultCell().setBorder(0);
                section3.getDefaultCell().setPaddingBottom(0);
                String forInfo = "FOR   (91)" + siteToken;
                PdfPTable forTable = new PdfPTable(1);
                forTable.getDefaultCell().setBorder(0);

                PdfPCell cg211 = new PdfPCell
                        (makePhrase(forInfo,normalcw,false));
                cg211.setBorder(0);
                cg211.setHorizontalAlignment(Element.ALIGN_LEFT);
                forTable.addCell(cg211);


                PdfPCell cg212 = new PdfPCell
        (this.createCwBarcode(writer, "91" + siteToken));

                cg212.setBorder(0);
                cg212.setPaddingLeft(barcodeCellPadding);
                cg212.setHorizontalAlignment(Element.ALIGN_LEFT);
                forTable.addCell(cg212);

                PdfPCell s3c = new PdfPCell(forTable);
                s3c.setBorder(Rectangle.BOTTOM);
                s3c.setBorder(Rectangle.TOP);
                section3.addCell(s3c);

                String tInfo = "\n\n" + origsiteToken;
                PdfPCell cg22 = new PdfPCell
                        (makePhrase(tInfo,normalBold,false));
                cg22.setHorizontalAlignment(Element.ALIGN_CENTER);
                cg22.setVerticalAlignment(Element.ALIGN_CENTER);
                section3.addCell(cg22);
                subTable.addCell(section3);


                // SSC id section
                String pid =
        Utility.makeSSCC("0018283", String.valueOf
          (manifest.getManifestItem().getPackageId())
          );

                String sscInfo = "    SSCC-18    (00) " + pid;
                PdfPCell c1 = new PdfPCell(makePhrase(sscInfo,normalcw,false));
                c1.setBorder(0);
                c1.setBorder(Rectangle.TOP);
                c1.setPaddingLeft(barcodeCellPadding);
                c1.setPaddingRight(barcodeCellPadding);
                subTable.addCell(c1);

                PdfPCell c2 =	new PdfPCell
                        (this.createCwBarcode(writer, "00" + pid));

                c2.setBorder(borderType);
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                subTable.addCell(c2);

                masterTable.addCell(subTable);
                document.newPage();
            }

            counter ++;
        }

        return ;
    }


    private String mkAddressString(OrderAddressData addr) {

        StringBuffer text = new StringBuffer("");

        if(Utility.isSet(addr.getShortDesc())){
            text.append(addr.getShortDesc()+"\n");
        }
        if(Utility.isSet(addr.getAddress1())){
            text.append(addr.getAddress1()+"\n");
        }
        if(Utility.isSet(addr.getAddress2())){
            text.append(addr.getAddress2()+"\n");
        }
        if(Utility.isSet(addr.getAddress3())){
            text.append(addr.getAddress3()+"\n");
        }
        if(Utility.isSet(addr.getAddress4())){
            text.append(addr.getAddress4()+"\n");
        }
        text.append(addr.getCity());
        text.append(" ");
        if (Utility.isSet(addr.getStateProvinceCd())) {
          text.append(addr.getStateProvinceCd());
          text.append(" ");
        }
        text.append(addr.getPostalCode());

        return text.toString();
    }


    private void fmtAddressLabel(PdfWriter writer,PurchaseOrderStatusDescDataViewVector pPurchaseOrdersToManifest)
    throws Exception{

        masterTable.getDefaultCell().setBorder(borderType);

        Iterator poIt = pPurchaseOrdersToManifest.iterator();
        int counter = 0;
        while(poIt.hasNext()){
            PurchaseOrderStatusDescDataView po = (PurchaseOrderStatusDescDataView) poIt.next();
            Iterator manIt = po.getManifestItems().iterator();
            while(manIt.hasNext()){
                PdfPTable subTable = new PdfPTable(1);
                subTable.getDefaultCell().setBorder(borderType);
                subTable.setWidthPercentage(100);
                ManifestItemView manifest = (ManifestItemView) manIt.next();

                PdfPTable myTable = new PdfPTable(2);
                myTable.setWidthPercentage(100);
                myTable.setWidths(new int[]{65,35});
                myTable.getDefaultCell().setBorder(borderType);
                myTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);

                //Return Address
                StringBuffer text = new StringBuffer();
                //text.append(myTable.addCell(makePhrase("Return Address:\n",normal,false));
                int returnLines = 2; //name and city state zip
                if(po.getDistributorBusEntityData() != null){
                    //AddressData lDistAddr = (AddressData) pDistributorMap.get(new Integer(po.getDistributorBusEntityData().getBusEntityId()));
                    OrderAddressData lDistAddr = manifest.getReturnAddress();
                    text.append(po.getDistributorBusEntityData().getShortDesc());
                    text.append("\n");
                    if(lDistAddr != null){
                        if(Utility.isSet(lDistAddr.getAddress1())){
                            returnLines ++;
                            text.append(lDistAddr.getAddress1()+"\n");
                        }
                        if(Utility.isSet(lDistAddr.getAddress2())){
                            returnLines ++;
                            text.append(lDistAddr.getAddress2()+"\n");
                        }
                        if(Utility.isSet(lDistAddr.getAddress3())){
                            returnLines ++;
                            text.append(lDistAddr.getAddress3()+"\n");
                        }
                        if(Utility.isSet(lDistAddr.getAddress4())){
                            returnLines ++;
                            text.append(lDistAddr.getAddress4()+"\n");
                        }
                        text.append(lDistAddr.getCity());
                        text.append(" ");
                        if (Utility.isSet(lDistAddr.getStateProvinceCd())) {
                          text.append(lDistAddr.getStateProvinceCd());
                          text.append(" ");
                        }
                        text.append(lDistAddr.getPostalCode());
                    }
                }

                myTable.addCell(makePhrase(text.toString(),normal,false));
                subTable.addCell(myTable);

                //pp dropship (upper left hand corner)
                myTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                myTable.getDefaultCell().setVerticalAlignment(PdfPCell.ALIGN_TOP);

                PdfPTable innerTable = new PdfPTable(1);
                innerTable.setWidthPercentage(100);
                innerTable.getDefaultCell().setBorder(PdfPCell.BOX);
                innerTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);


                text = new StringBuffer("Parcel Select\n");
                text.append("U.S. Postage Paid\n");
                text.append(shipper);
                innerTable.addCell(makePhrase(text.toString(),indiciaTextFont,false));
                if(returnLines > 3){
                    innerTable.getDefaultCell().setBorder(this.borderType);
                    innerTable.addCell(makePhrase("",normal,false));
                }

                myTable.addCell(innerTable);

                subTable.addCell(myTable);

                //line
                subTable.addCell(makeLine());

                //shipper line
                myTable = new PdfPTable(1);
                myTable.setWidthPercentage(100);
                myTable.getDefaultCell().setBorder(borderType);
                myTable.getDefaultCell().setPadding(0);
                myTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                myTable.addCell(makePhrase("Shipper: "+shipper,normalBold,false));
                subTable.addCell(myTable);


                //line
                subTable.addCell(makeLine());

                //Ship To:
                myTable = new PdfPTable(1);
                myTable.setWidthPercentage(100);
                myTable.getDefaultCell().setBorder(borderType);
                myTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                String spacer = "           ";
                OrderAddressData addr = po.getShipToAddress();
                text = new StringBuffer("Ship To:\n");
                text.append(spacer);
                text.append(po.getOrderData().getRequestPoNum()+"\n");
                if(Utility.isSet(addr.getAddress1())){
                    text.append(spacer);
                    text.append(addr.getAddress1()+"\n");
                }
                if(Utility.isSet(addr.getAddress2())){
                    text.append(spacer);
                    text.append(addr.getAddress2()+"\n");
                }
                if(Utility.isSet(addr.getAddress3())){
                    text.append(spacer);
                    text.append(addr.getAddress3()+"\n");
                }
                if(Utility.isSet(addr.getAddress4())){
                    text.append(spacer);
                    text.append(addr.getAddress4()+"\n");
                }
                text.append(spacer);
                text.append(addr.getCity());
                text.append(" ");
                if (Utility.isSet(addr.getStateProvinceCd())) {
                  text.append(addr.getStateProvinceCd());
                  text.append(" ");
                }
                text.append(addr.getPostalCode());
                myTable.addCell(makePhrase(text.toString(),shipToFont,false));
                subTable.addCell(myTable);

                //line
                subTable.addCell(makeLineWithWidth(SEPERATOR_LINE_THICKNESS));

                //USPS Deliver Confirmation text and barcode
                myTable = new PdfPTable(1);
                myTable.setWidthPercentage(100);
                myTable.getDefaultCell().setBorder(borderType);
                myTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                myTable.getDefaultCell().setPadding(barcodeCellPadding);
                //make the barcode text (add 91 to it, the AI for service requested to USPS)

                String barcodeText;
                int codeType;
                if(RefCodeNames.MANIFEST_LABEL_TYPE_CD.USPS_DELIVERY_CONFIRM.equals(getLabelType())){
                    myTable.addCell(makePhrase("USPS DELIVERY CONFIRM",barcodeHeader,false));
                    barcodeText = RefCodeNames.APPLICATION_IDENTIFIER.SERVICE_TYPE_CD + manifest.getManifestItem().getPackageConfirmId();
                    codeType = Barcode128.CODE128_UCC;
                }else if(RefCodeNames.MANIFEST_LABEL_TYPE_CD.PLAIN.equals(getLabelType())){
                    myTable.addCell(makePhrase("",barcodeHeader,false));
                    barcodeText = manifest.getManifestItem().getPackageId();
                    codeType = Barcode128.CODE128;
                }else{
                    throw new IllegalArgumentException(getLabelType() + " is not a valid delivery type code");
                }

                myTable.addCell(this.createBarcode(writer,barcodeText,codeType));
                //split out the barcode into groups of 4 digits seperated by spaces:
                //123456789101112131415161...
                //1234 5678 9101 1121 3141 5161 ...
                StringBuffer humanBarcodeText = new StringBuffer();
                for(int i=0,len=barcodeText.length();i<len;i++){
                    humanBarcodeText.append(barcodeText.charAt(i));
                    if((i+1) % 4 == 0){
                        humanBarcodeText.append(' ');
                    }
                }
                myTable.addCell(makePhrase(humanBarcodeText.toString(),barcodeTextF,false));//print out text underneath barcode
                //we don't want to let the default text print out as we
                //have specific requierments for USPS
                subTable.addCell(myTable);



                //line
                subTable.addCell(makeLineWithWidth(SEPERATOR_LINE_THICKNESS));
                subTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                //Zip text and Barcode
                myTable = new PdfPTable(3);
                myTable.setWidths(new int[] {25,50,25});
                myTable.getDefaultCell().setBorder(borderType);
                myTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                myTable.getDefaultCell().setPadding(0);


                String zip = addr.getPostalCode();
                zip = zip.substring(0,5);
                barcodeText = "420" + zip;
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(createBarcode(writer,barcodeText,Barcode128.CODE128_UCC));
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(makePhrase(" ",normal,false));
                myTable.addCell(makePhrase("ZIP    " + zip,barcodeTextF,false));  //print out text underneath barcode, do not print AI
                myTable.addCell(makePhrase(" ",normal,false));
                //we don't want to let the default text print out as we
                //have specific requierments for USPS

                subTable.addCell(myTable);
                masterTable.addCell(subTable);
                counter ++;
            }
        }

        return ;
    }


    //(XXX:Hardcoded for now, may want to go back to the freight
    String shipper = "Parcel Direct";

    /**
     *Does the work of generating the manifest label
     */
    Font indiciaTextFont = FontFactory.getFont(FontFactory.HELVETICA,8);
    Font shipToFont =  FontFactory.getFont(FontFactory.HELVETICA_BOLD,10);
    Font barcodeTextF =  FontFactory.getFont(FontFactory.HELVETICA_BOLD,10);
    Font barcodeHeader =  FontFactory.getFont(FontFactory.HELVETICA_BOLD,12);

    Document document;

    private void generatePdf
            (OutputStream pOut,
            PurchaseOrderStatusDescDataViewVector pPurchaseOrdersToManifest)
            throws Exception{

        normal = FontFactory.getFont(FontFactory.HELVETICA,10);
        normalBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD,14);

        //handler bus entity to determine the real shipper)
        try{

            //setup the document
            if(mPrintMode.equals(RefCodeNames.MANIFEST_LABEL_MODE_CD.CONTINUOUS_PRINT_MODE)){
                //document = new Document(new Rectangle(225,432), 10, 15, 30, 15);
                if ( RefCodeNames.MANIFEST_LABEL_TYPE_CD.CW_SHIP_LABEL.equals(getLabelType() ) ) {
                    mPageWidth = 288;
                }
                document = new Document(new Rectangle(mPageWidth,mPageHeight), 10, 15, 30, 15);
                //document = new Document(new Rectangle(288,432), 10, 15, 30, 15);
                //document.setMargins(2, 2, 2, 2);
                document.setMargins(5, 5, 5, 5);
                barcodeCellPadding = 5;
            }else if(mPrintMode.equals(RefCodeNames.MANIFEST_LABEL_MODE_CD.MULTIPLE_PRINT_MODE)){
                //sets up the doc to be in "landscape mode" for an A4 (normal) style document
                float w = PageSize.A4.getWidth();
                float h = PageSize.A4.getHeight();
                document = new Document(new Rectangle(h,w), 10, 15, 30, 15);
                document.setMargins(5, 5, 5, 5);
                barcodeCellPadding = 7;
            }else{
                throw new IllegalArgumentException(mPrintMode + " Not a valid print mode");
            }

            PdfWriter writer = PdfWriter.getInstance(document, pOut);
            document.open();

            if(mPrintMode.equals(RefCodeNames.MANIFEST_LABEL_MODE_CD.CONTINUOUS_PRINT_MODE)){
                masterTable = new PdfPTable(1);
                masterTable.setWidthPercentage(100);
            }else if(mPrintMode.equals(RefCodeNames.MANIFEST_LABEL_MODE_CD.MULTIPLE_PRINT_MODE)){
                masterTable = new PdfPTable(2);
                masterTable.setWidthPercentage(100);
                masterTable.setWidths(new int[]{50,50});
                masterTable.getDefaultCell().setPadding(30);
            }else{
                throw new IllegalArgumentException(mPrintMode + " Not a valid print mode");
            }

            if(RefCodeNames.MANIFEST_LABEL_TYPE_CD.CW_SHIP_LABEL.equals(getLabelType())){
                fmtCwAddressLabel( writer, pPurchaseOrdersToManifest);
            } else {
                fmtAddressLabel( writer, pPurchaseOrdersToManifest);
            }

            if(mPrintMode.equals(RefCodeNames.MANIFEST_LABEL_MODE_CD.MULTIPLE_PRINT_MODE)){
                masterTable.addCell("");
            }
            document.add(masterTable);
            document.close();

        }catch (DocumentException e){
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }


    public static void main(String args[]){
        PdfManifestLabel me = new PdfManifestLabel();
        try{
            PurchaseOrderStatusDescDataView po =  PurchaseOrderStatusDescDataView.createValue();
            OrderAddressData oad = OrderAddressData.createValue();
            //oad.setShortDesc("00059 - J C Penney Type 1");
            oad.setShortDesc("06635 - J C Penney Type 1");
            oad.setAddress1("McCain Shopping Center");
            oad.setAddress2("3929 McCain Blvd & Hwy 67");
            oad.setAddress3("ATTN: Retail Technician");
            oad.setCity("Jacksonville");
            oad.setPostalCode("72116");
            oad.setStateProvinceCd("FL");
            po.setShipToAddress(oad);
            OrderData od = OrderData.createValue();
            String t = "EPRO54779242";
            od.setRequestPoNum(t.substring(4));
            po.setOrderData(od);

            ManifestItemViewVector mivv = new ManifestItemViewVector();
            ManifestItemView  miv =  ManifestItemView.createValue();
            miv.setManifestItem(ManifestItemData.createValue());
            miv.getManifestItem().setPackageId("22203401");
            String confirm = "02157793733"+miv.getManifestItem().getPackageId();
            int digit = Utility.calculateUSPSDeliveryServiceCheckDigitFromPackageId(confirm);
            confirm = confirm + digit;
            miv.getManifestItem().setPackageConfirmId(confirm);
            mivv.add(miv);
            // mivv.add(miv);
            // mivv.add(miv);
            po.setManifestItems(mivv);

            BusEntityData bd = BusEntityData.createValue();
            bd.setShortDesc("Cleanwise Inc");
            bd.setBusEntityId(1);
            po.setDistributorBusEntityData(bd);

            PurchaseOrderStatusDescDataViewVector pov = new PurchaseOrderStatusDescDataViewVector();
            pov.add(po);

            java.io.FileOutputStream out = new java.io.FileOutputStream("c:/myPdf.pdf");

            OrderAddressData retAddr = OrderAddressData.createValue();
            retAddr.setAddress1("33 Boston Post Road West");
            retAddr.setAddress2("Suite 400");
            retAddr.setAddress3("");
            retAddr.setAddress4("");
            retAddr.setCity("Marlboro");
            retAddr.setStateProvinceCd("MA");
            retAddr.setPostalCode("01752");
            miv.setReturnAddress(retAddr);

            //Map distMap = new java.util.HashMap();
            //distMap.put(new Integer(bd.getBusEntityId()), retAddr);

            me.setLabelType(RefCodeNames.MANIFEST_LABEL_TYPE_CD.CW_SHIP_LABEL);
            me.generatePdf(out,pov);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
