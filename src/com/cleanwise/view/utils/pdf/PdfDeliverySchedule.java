/*
 * PdfDeliverySchedule.java
 *
 * Created on July 15, 2002, 5:36 PM
 */

package com.cleanwise.view.utils.pdf;

import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.DisplayListSort;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;


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
import java.util.GregorianCalendar;
import java.io.OutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *Constructs a pdf document in the form of a pdf document.  It understands the various
 *propriatary objects, and uses them to construct a purchase order document.
 * @author  Yuriy Kupershmidt
 */
public class PdfDeliverySchedule extends PdfSuper {
    //static configuration variables
    //configures the percentage widths of item columns in the document
    private static int itmColumnWidth[] = {4,8,32,12,6,4,14,12,8};
    private static int halves[] = {62,38};
    private static int sizes[] = {30,70};

    /** Creates a new instance of PdfDeliverySchedule */
    public PdfDeliverySchedule() {
    }
    //utility function to make an item Element.
/*
    private PdfPTable makeItemElement(ShoppingCartItemData pItm,Locale pLocale) throws DocumentException{
        NumberFormat nf = NumberFormat.getCurrencyInstance(pLocale);
        PdfPTable itmTbl = new PdfPTable(9);

        itmTbl.setWidthPercentage(100);
        itmTbl.setWidths(itmColumnWidth);
        itmTbl.getDefaultCell().setBorderColor(java.awt.Color.black);
        itmTbl.getDefaultCell().setVerticalAlignment(Cell.ALIGN_TOP);


        itmTbl.addCell(makePhrase("",normal,true));
        if(pItm.getProduct().isPackProblemSku()){
            itmTbl.addCell(makePhrase("*"+pItm.getActualSkuNum(),normal,true));
        }else{
            itmTbl.addCell(makePhrase(pItm.getActualSkuNum(),normal,true));
        }
        itmTbl.addCell(makePhrase(pItm.getProduct().getCatalogProductShortDesc(),normal,true));
        itmTbl.addCell(makePhrase(pItm.getProduct().getSize(),normal,true));
        itmTbl.addCell(makePhrase(pItm.getProduct().getPack(),normal,true));
        itmTbl.addCell(makePhrase(pItm.getProduct().getUom(),normal,true));
        itmTbl.addCell(makePhrase(pItm.getProduct().getManufacturerName(),normal,true));

        BigDecimal price = new BigDecimal(pItm.getPrice());
        PdfPCell pcell = new PdfPCell(makePhrase(nf.format(price),normal,true));
        pcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        itmTbl.addCell(pcell);

        //BigDecimal amount = new BigDecimal(pItm.getAmount());
        itmTbl.addCell(makePhrase("",normal,true));

        return itmTbl;
    }
*/

    private void drawHeader(Document document,String pSiteName, Locale pLocale,
                              Date pStartDate, Date pEndDate,
                              String pImageName)
    throws DocumentException {
        DateFormat lDateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT,pLocale);
        //add the logo
        if(pImageName!=null) {
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
            e.printStackTrace();
        }
        }



        //make the two tables into one
        PdfPTable wholeTable = new PdfPTable(2);
        wholeTable.setWidthPercentage(100);

        //wholeTable.setWidths(halves);
        wholeTable.getDefaultCell().setBorder(borderType);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMMMMMMMMM, yyyy");
        String dateInterval = sdf.format(pStartDate)+" - "+ sdf.format(pEndDate);
        wholeTable.addCell(pSiteName);
        wholeTable.addCell(dateInterval);
        document.add(wholeTable);


    }

    public void generatePdf(CleanwiseUser pAppUser, DistributorData pDist,
               GregorianCalendar pStartDate, GregorianCalendar pEndDate,
               ArrayList pOrderDatesVector, String pImage, OutputStream pOut)
    throws IOException, Exception{
                boolean showDeliveryFl = true;
                AccountData acctD = pAppUser.getUserAccount();
                boolean clwFooterFl = true;
                if(acctD != null ) {
                    String acctName = acctD.getBusEntity().getShortDesc();
                    acctName = acctName.toLowerCase();
                    if(acctName.indexOf("kohl")>=0) {
                        showDeliveryFl = false;
                        clwFooterFl = false;
                    }
                    if(acctName.indexOf("apple")>=0) {
                        clwFooterFl = false;
                    }
                }
		generatePdf(pAppUser, pDist, pStartDate, pEndDate,
               pOrderDatesVector, pImage, !showDeliveryFl, clwFooterFl, pOut);

    }			   //main worker method.  The public methods will call this method
    //to generate the pdf after the object has been converted.
    public void generatePdf(CleanwiseUser pAppUser, DistributorData pDist,
               GregorianCalendar pStartDate, GregorianCalendar pEndDate,
               ArrayList pOrderDatesVector, String pImage, boolean pCutoffOnlyFl, boolean pClwFooterFl, OutputStream pOut)
/*    (UserShopForm sForm,List pItems, StoreData pStore,OutputStream pOut,String pImageName, boolean personal)*/
    throws IOException, Exception{
    try
    {

       //we keep track of the page number rather than useing the built in pdf functionality
       //as we have to maintain pages between pos, so we may be on the 3rd po, but on the
       //first page of that po, and on the 9th page of the pdf.
            StoreData storeD = pAppUser.getUserStore();
            Locale lLocale = Utility.parseLocaleCode(storeD.getBusEntity().getLocaleCd());
            //create the header and footer

            Phrase headPhrase = new Phrase(makeChunk("", heading,true));
            headPhrase.add(makeChunk("Delivery Order Planner",heading,true));
            headPhrase.add(makeChunk(" ",heading,true));
            SiteData siteD = pAppUser.getSite();
            if(siteD==null) {
              String mess = "No site data";
              throw new Exception(mess);
            }

            HeaderFooter header = new HeaderFooter(headPhrase,true);
            header.setAlignment(HeaderFooter.ALIGN_RIGHT);

            //setup the document
            Document document = new Document(PageSize.A4, 10, 15, 30, 15);

            PdfWriter writer = PdfWriter.getInstance(document, pOut);

            Chunk disclaimer = null;
          Phrase footPhrase=new Phrase();
        //footPhrase.add(makeChunk(siteD.getBusEntity().getShortDesc() +  " * ",smallBold,false));
			if(pClwFooterFl) {
        footPhrase.add(makeChunk("Cleanwise™ – The Industry Leading Jan-San Procurement Management Company",small,false));
            }
            HeaderFooter footer = new HeaderFooter(footPhrase,false);
            footer.setAlignment(HeaderFooter.ALIGN_CENTER);

            //setup the borders from the header
            header.setBorder(borderType);
            footer.setBorder(HeaderFooter.TOP);

            document.setHeader(header);
            document.setFooter(footer);
            document.open();
            drawHeader(document,siteD.getBusEntity().getShortDesc(), lLocale,
               pStartDate.getTime(), pEndDate.getTime(), pImage);
            //Draw calendar
            int startMonth = pStartDate.get(Calendar.MONTH);
            int startYear = pStartDate.get(Calendar.YEAR);
            int endMonth = pEndDate.get(Calendar.MONTH);
            int endYear = pEndDate.get(Calendar.YEAR);
            int nbMonths = (endYear - startYear)*12 + (endMonth - startMonth)+1;
            if(nbMonths<=0) {
               String errorMess = "Error: Start Date can not be after End Date";
               addError(document,errorMess);
               document.close();
               return;
            }
             //Site Address
             AddressData siteAddress = siteD.getSiteAddress();
             PdfPTable addrTable = new PdfPTable(1);
             addrTable.setWidthPercentage(100);
             addrTable.getDefaultCell().setBorder(borderType);
             String addr = siteAddress.getAddress1();
             if(siteAddress.getAddress2() != null) {
                addr = addr + " " + siteAddress.getAddress2();
             }
             addrTable.addCell(addr);
             String cityAddr = siteAddress.getCity()+ ", ";
             if (pAppUser.getUserStore().isStateProvinceRequired() && Utility.isSet(siteAddress.getStateProvinceCd())) {
               cityAddr += siteAddress.getStateProvinceCd()+" ";
             }
             cityAddr += siteAddress.getPostalCode();
             addrTable.addCell(cityAddr);
             document.add(addrTable);
/*
            if(pDist!=null) {
              String distStr = "Distributor: "+pDist.getBusEntity().getShortDesc();
              PdfPTable distTable = new PdfPTable(1);
              distTable.setWidthPercentage(100);
              distTable.getDefaultCell().setBorder(borderType);
              distTable.addCell(distStr);
              document.add(distTable);
            }
 */
            if(pOrderDatesVector==null) {
               String errorMess = "Error: No delivery calendar found";
               addError(document,errorMess);
               document.close();
               return;
            }
            //postion delivery and cutoff dates
            int cutoffIndex=0;
            int deliveryIndex=0;
            Date start = pStartDate.getTime();
            for(;cutoffIndex<pOrderDatesVector.size();cutoffIndex++){
              ScheduleOrderDates sod = (ScheduleOrderDates) pOrderDatesVector.get(cutoffIndex);
              Date dd = sod.getNextOrderCutoffDate();
              if(!dd.before(start)) break;
            }
            for(;deliveryIndex<pOrderDatesVector.size();deliveryIndex++){
              ScheduleOrderDates sod = (ScheduleOrderDates) pOrderDatesVector.get(deliveryIndex);
              Date dd = sod.getNextOrderDeliveryDate();
              if(!dd.before(start)) break;
            }
            PdfPTable calTable = new PdfPTable(3);
            calTable.setWidthPercentage(100);
            calTable.getDefaultCell().setBorder(borderType);
            GregorianCalendar wrkDate = (GregorianCalendar) pStartDate.clone();
            SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMMMMMMMMMMM");
            int loopQty = (nbMonths-1)/3+1;
            loopQty *=3;
            for(int ii=0; ii<loopQty; ii++) {
              if(ii>=nbMonths) {
                calTable.addCell("");
                continue;
              }
              PdfPTable monthTable = new PdfPTable(1);
              monthTable.getDefaultCell().setBorder(borderType);
              String monthS = sdfMonth.format(wrkDate.getTime());
              monthTable.addCell(monthS);
              PdfPTable weekTable = new PdfPTable(7);
              weekTable.getDefaultCell().setBorder(borderType);
              weekTable.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
              weekTable.addCell(makePhrase("Sun",normal,false));
              weekTable.addCell(makePhrase("Mon",normal,false));
              weekTable.addCell(makePhrase("Tue",normal,false));
              weekTable.addCell(makePhrase("Wed",normal,false));
              weekTable.addCell(makePhrase("Thu",normal,false));
              weekTable.addCell(makePhrase("Fri",normal,false));
              weekTable.addCell(makePhrase("Sat",normal,false));
              GregorianCalendar wrkDate1 = (GregorianCalendar) wrkDate.clone();
              int dayShift = wrkDate1.get(Calendar.DAY_OF_WEEK)-1;
              wrkDate1.add(Calendar.MONTH,1);
              wrkDate1.add(Calendar.DATE,-1);
              int lastDay = wrkDate1.get(Calendar.DAY_OF_MONTH);
              int curMonth = wrkDate1.get(Calendar.MONTH);
              GregorianCalendar wrkDateCurr = (GregorianCalendar) wrkDate.clone();
              for(int jj=1; jj<=42; jj++) {
                int dayOfMonth = jj-dayShift;
                String dayOfMonthS = "";
                Date curDate = null;
                if(dayOfMonth>0 && dayOfMonth<=lastDay){
                  dayOfMonthS = "" + dayOfMonth;
                  wrkDateCurr.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                  curDate = wrkDateCurr.getTime();
                }
                PdfPCell dateCell = null;
                boolean foundFl = false;
                if(cutoffIndex<pOrderDatesVector.size()){
                  ScheduleOrderDates sod = (ScheduleOrderDates) pOrderDatesVector.get(cutoffIndex);
                  Date dd = sod.getNextOrderCutoffDate();
                  if(curDate!=null && curDate.compareTo(dd)==0) {
                    dayOfMonthS += "C";
                    foundFl=true;
                    cutoffIndex++;
                  }
                }
				if(!pCutoffOnlyFl) {
					if(deliveryIndex<pOrderDatesVector.size()){
						ScheduleOrderDates sod =
							(ScheduleOrderDates) pOrderDatesVector.get(deliveryIndex);
						Date dd = sod.getNextOrderDeliveryDate();
						if(curDate!=null && curDate.compareTo(dd)==0){
							dayOfMonthS += "D";
							foundFl=true;
							deliveryIndex++;
						}
					}
				}
                if(foundFl) {
                    dateCell = new PdfPCell(makePhrase(dayOfMonthS,normalBold,false));
                }  else{
                    dayOfMonthS += " ";
                    dateCell = new PdfPCell(makePhrase(dayOfMonthS,normal,false));
                }
                dateCell.setBorder(borderType);
                dateCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                weekTable.addCell(dateCell);
              }
              monthTable.addCell(weekTable);
              calTable.addCell(monthTable);
              wrkDate.add(Calendar.MONTH,1);
            }
            document.add(calTable);
            //Legend
            PdfPTable legendTable = new PdfPTable(1);
             legendTable.setWidthPercentage(100);
             legendTable.getDefaultCell().setBorder(borderType);
             legendTable.addCell("C = Order Cutoff");
			 if(!pCutoffOnlyFl) {
				legendTable.addCell("D = Delivery Date");
                         }
                         if(pClwFooterFl) {
				legendTable.addCell("Questions? Contact the Cleanwise Customer Resource Center");
				legendTable.addCell("at (800) 236-7097, 8:30 AM – 5:00 PM EST, M-F");
			 }
             document.add(legendTable);

            //close out the document
            document.close();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }
    private void addError(Document document, String pErrorMess)
    throws Exception
    {
         PdfPTable errorTable = new PdfPTable(1);
         errorTable.setWidthPercentage(100);
         errorTable.getDefaultCell().setBorder(borderType);
         errorTable.addCell(pErrorMess);
         document.add(errorTable);
         return;
    }
}
