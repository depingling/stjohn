/*
 * PdfSuper.java
 *
 * Created on May 10, 2002, 5:29 PM
 */

package com.cleanwise.service.apps.dataexchange;
import com.cleanwise.view.utils.ClwCustomizer;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.StoreData;

import java.awt.Color;
import java.util.Properties; //used by the lowagie/iText api, we are not actually using the awt toolkit here
/**
 *Super class to all of the pdf classes.  Contains some of the utility methods, and variables
 *that all of the pdf classes have in common.
 * @author  bstevens
 */
abstract class PdfSuperMyFax1 {
    static final java.math.BigDecimal ZERO = new java.math.BigDecimal(0);
    static final String SPACE = " ";
    static final String lineBreak = "\n";
    protected Font normal = FontFactory.getFont(FontFactory.COURIER,10);
    protected Font normalBold = FontFactory.getFont(FontFactory.COURIER_BOLD,10);
    private Properties miscProperties = new Properties();

    public void setMiscProperties(Properties v) {
        miscProperties = v;
    }

    public Properties getMiscProperties() {
        return miscProperties;
    }

    public void setMiscProperty(String pName, Object pValue) {
        if (miscProperties == null) {
            miscProperties = new Properties();
        }
        miscProperties.put(pName, pValue);
    }

    public Object getMiscProperty(String pName) {
        return getMiscProperty(pName, null);
    }

    public Object getMiscProperty(String pName, String defValue) {
        if (miscProperties == null) {
            return null;
        }
        if (!miscProperties.containsKey(pName)) {
            return null;
        }
        return miscProperties.getProperty(pName, defValue);
    }


    public void setNormalFontSize(int pSize) {
        normal.setSize((float)pSize);
        normalBold.setSize((float)pSize);
    }
    public void setNormalFont(String pFont) {
        normal.setFamily(pFont);
        normalBold.setFamily(pFont);
    }
    public void setNormalFont(Font pFont, Font pBoldFont) {
        normal = pFont;
        normalBold = pBoldFont;
    }

    static Font smallHeading = FontFactory.getFont(FontFactory.HELVETICA_BOLD,"utf-8",true,9);
    static Font itemHeading = FontFactory.getFont(FontFactory.HELVETICA_BOLD,"utf-8",true,8);
    static{itemHeading.setColor(java.awt.Color.white);}
    static Font heading = FontFactory.getFont(FontFactory.HELVETICA_BOLD,"utf-8",true,18);
    static Font small = FontFactory.getFont(FontFactory.HELVETICA,"utf-8",true,8);
    static Font smallItalic = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE,"utf-8",true,8);
    static Font smallBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD,"utf-8",true,8);

    int borderType;

    public void initFontsUnicode ()
    throws Exception {
//       String fontDir = ClwCustomizer.getServerDir() + "/xsuite/fonts/";
       String fontDir = ClwCustomizer.getServerDir() + "/resources/fonts/";

       BaseFont courBaseFont =
               BaseFont.createFont(fontDir+"cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
       BaseFont courBdBaseFont =
               BaseFont.createFont(fontDir+"courbd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
       BaseFont timesBaseFont =
               BaseFont.createFont(fontDir+"times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
       BaseFont timesBdBaseFont =
               BaseFont.createFont(fontDir+"timesbd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
       BaseFont timesItBaseFont =
               BaseFont.createFont(fontDir+"timesi.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

       normal = new Font(courBaseFont,10);
       normalBold = new Font(courBdBaseFont,10);
       smallHeading = new Font(timesBdBaseFont,9);
       itemHeading = new Font(timesBdBaseFont,8);
       itemHeading.setColor(java.awt.Color.white);
       heading = new Font(timesBdBaseFont,18);
       small = new Font(timesBaseFont,8);
       smallItalic = new Font(timesItBaseFont,8);
       smallBold = new Font(timesBdBaseFont,8);

    }

    /**utility method to constuct a <i>line</i>.  This is more of a hack than anything else
     * as it makes a table and gives it a bottom border.  There apears to be no way of makeing a
     * line object that is relative as far as the page position goes.*/
    PdfPTable makeLine(int widthPercentage){
        return makeLine(100);
    }

    PdfPTable makeLine(){
        return makeLineWithWidth(1);
    }

    PdfPTable makeLineWithWidth(float width){
        PdfPTable line = new PdfPTable(1);
        line.setWidthPercentage(100);
        line.getDefaultCell().setBorder(Rectangle.BOTTOM);
        line.getDefaultCell().setBorderWidth(width);
        line.addCell("");
        return line;
    }

    Element makeBlankLine(){
        return makeBlankLine(100);
    }

    Element makeBlankLine(int widthPercentage){
        PdfPTable line = new PdfPTable(1);
        line.setWidthPercentage(widthPercentage);
        line.getDefaultCell().setBorder(borderType);
        line.addCell(" ");
        return line;
    }

    //utility function to create a phrase with a single chunk using makeChunk
    Phrase makePhrase(String s,Font f,boolean newline){
        if(s == null){
            return new Phrase(makeChunk("",f,newline));
        }
        return new Phrase(makeChunk(s,f,newline));
    }

    //utility function to check if string is null, and if so return a blank chunk
    Chunk makeChunk(String s,Font f,boolean newline){
        Chunk c;
        if(s==null){
            //add the font just in case we alter the contents later on
            c = new Chunk("",f);
        } else{
            c = new Chunk(s,f);
        }
        if (newline){
            c.append(lineBreak);
        }
        return c;
    }

    Phrase makeStoreFooter(StoreData pStore){
        return makeStoreFooter(pStore, null, null);
    }

    Phrase makeStoreFooter(StoreData pStore,Chunk pOptionalBegChunk,
                      Chunk pOptionalEndChunk) {
        String addr = pStore.getPrimaryAddress().getAddress1() + SPACE + pStore.getPrimaryAddress().getAddress2();
        if(pStore.getPrimaryAddress().getAddress1() == null && pStore.getPrimaryAddress().getAddress2() == null){
            addr="";
        }else if(pStore.getPrimaryAddress().getAddress1() != null && pStore.getPrimaryAddress().getAddress2() != null){
            addr=pStore.getPrimaryAddress().getAddress1() + SPACE + pStore.getPrimaryAddress().getAddress2();
        }else if(pStore.getPrimaryAddress().getAddress1() != null){
            addr=pStore.getPrimaryAddress().getAddress1();
        }else{
            addr=pStore.getPrimaryAddress().getAddress2();
        }
        String stateProvince = null;
        if (pStore.isStateProvinceRequired()) {
          stateProvince = pStore.getPrimaryAddress().getStateProvinceCd();
        }
        return makeFooter
    (pStore.getStoreBusinessName().getValue(),
     addr,
     pStore.getPrimaryAddress().getCity(),
     stateProvince,
     pStore.getPrimaryAddress().getPostalCode(),
     pStore.getPrimaryPhone().getPhoneNum(),
     pStore.getPrimaryFax().getPhoneNum(),
     pStore.getStorePrimaryWebAddress().getValue(),pOptionalBegChunk,pOptionalEndChunk);
    }

    Phrase makeFooter(String pStoreName,
          String pStreetAddr,
          String pCity,
          String pStateCd,
          String pPostalCode,
          String pPhoneNum,
          String pFaxNum,
          String pWebUrl){
        return makeFooter(pStoreName, pStreetAddr, pCity, pStateCd, pPostalCode, pPhoneNum, pFaxNum, pWebUrl, null, null);
    }

    Phrase makeFooter(String pStoreName,
          String pStreetAddr,
          String pCity,
          String pStateCd,
          String pPostalCode,
          String pPhoneNum,
          String pFaxNum,
          String pWebUrl,
                      Chunk pOptionalBegChunk,
                      Chunk pOptionalEndChunk) {

  Phrase footPhrase=new Phrase();
        if(pOptionalBegChunk != null){
            footPhrase.add(pOptionalBegChunk);
        }

  if(Utility.isSetForDisplay(pStoreName)){
    footPhrase.add(makeChunk(pStoreName,smallBold,false));
    }
  if(Utility.isSetForDisplay(pStreetAddr)){
    footPhrase.add(makeChunk(" * "+pStreetAddr,small,false));
    }
  if(Utility.isSetForDisplay(pCity)){
    footPhrase.add(makeChunk(" * "+pCity,small,false));
    }
  if(Utility.isSetForDisplay(pStateCd)){
    footPhrase.add(makeChunk(", " + pStateCd,small,false));
    }
  if(Utility.isSetForDisplay(pPostalCode)){
    footPhrase.add(makeChunk(" * "+pPostalCode,small,false));
  }
  if(Utility.isSetForDisplay(pPhoneNum)){
    footPhrase.add(makeChunk(" * Phone: " + pPhoneNum,small,false));
  }
  if(Utility.isSetForDisplay(pFaxNum)){
    footPhrase.add(makeChunk(" * Fax: " + pFaxNum,small,false));
  }
  if(Utility.isSetForDisplay(pWebUrl)){
    footPhrase.add(makeChunk(" * "+pWebUrl,smallItalic,false));
  }
        if(pOptionalEndChunk != null){
            footPhrase.add(pOptionalEndChunk);
        }

  return footPhrase;
    }
    /** Class PTable extends com.lowagie.text.Table class and makes some settings
     * similar to PdfPTable. Actual to replace iText.jar version from 1.2.2 to 2.1.0
     * @param numColumns the number of columns
     */
     class PTable extends Table{

        /** Constructs a <CODE>PTable</CODE> with <CODE>numColumns</CODE> columns.
        * @param numColumns the number of columns
        */
          public PTable(int numColumns) throws DocumentException {
            super(numColumns);
            super.setPadding(1);
            super.setBorderWidth(0.5f);
          }
    }
}
