package com.cleanwise.view.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.GenericReportColumnView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.*;
import com.cleanwise.service.api.value.GenericReportStyleView;
import java.util.HashMap;
import com.cleanwise.service.api.value.GenericReportCellView;

public class PdfReportWritter {
    private final static Font FONT_TABLE_HEADER = new Font(Font.COURIER, 10);

    private final static Font FONT_HEADER = new Font(Font.COURIER, 9);

    private final static Font FONT_CELL = new Font(Font.COURIER, 8);

    static {
        FONT_TABLE_HEADER.setStyle(Font.BOLD);
        FONT_HEADER.setStyle(Font.BOLD);
    }

    public static void writePdfReport(GenericReportResultView repRes,
            String pReportName, Document document, ReportRequest pRepRequest,
            HttpServletRequest request, PdfWriter writer) throws Exception {
        CleanwiseUser appUser = ShopTool.getCurrentUser(request);
        Locale thisUserLocale = appUser.getPrefLocale();
        String thisCurrencyCode = "USD";
        HashMap styleCollection = (HashMap)ReportingUtils.createStyles( repRes.getUserStyle() );
        GenericReportColumnViewVector header = repRes.getHeader();
        int[] colFormat = new int[header.size()];
        int[] colScale = new int[header.size()];
        Object[] total = new Object[header.size()];
        boolean[] totalFl = new boolean[header.size()];
        boolean totalRowFl = false;
        int pageNumber = 1;
        int columnCount = repRes.getColumnCount();
        List table = repRes.getTable();
        // report name
        PdfPTable repNameTable = new PdfPTable(1);
        PdfPCell repNameCell = new PdfPCell(new Phrase(pReportName, FONT_HEADER));
        repNameTable.setWidthPercentage(100);
        repNameCell.setPaddingLeft(20);
        repNameCell.setBorder(0);
        repNameTable.addCell(repNameCell);
        document.add(repNameTable);

        int widths[] = new int[columnCount];
        for (int ii = 0; ii < columnCount; ii++) {
            GenericReportColumnView column = (GenericReportColumnView) header
                    .get(ii);
            String colName = reportColumnName(pRepRequest, column
                    .getColumnName());
            widths[ii] = Utility.parseInt(column.getColumnWidth());
            if (widths[ii] <= 0) {
                throw new Exception("Not set width for column:" + colName);
            }
        }

        // Make title
        PdfPTable pdfTitle = new PdfPTable(columnCount);
        pdfTitle.setWidthPercentage(100);
        pdfTitle.setWidths(widths);

        if (repRes.getTitle() != null)  {
            int columnCountTitle = repRes.getTitle().size();
            for (int ii = 1; ii < columnCountTitle; ii++) {
                    GenericReportColumnView column = (GenericReportColumnView) repRes.getTitle().get(ii);
                    String colName = column.getColumnName();
                    PdfPCell titleCell = new PdfPCell(new Phrase(colName, FONT_HEADER));
                    titleCell.setBorder(0);
                    titleCell.setColspan(columnCount);
                    titleCell.setPaddingLeft(20);
                    pdfTitle.addCell(titleCell);
            }
        }
        document.add(pdfTitle);

        // Make header

        PdfPTable pdfHeader = new PdfPTable(columnCount);
        pdfHeader.setWidthPercentage(100);
        pdfHeader.setWidths(widths);

        String[] colFormatPattern = new String[header.size()];
        for (int ii = 0; ii < columnCount; ii++) {
            GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
            String colName = column.getColumnName();
            String colClass = column.getColumnClass();
            totalFl[ii] = column.getTotalRequestFlag();
            int scale = column.getColumnScale();
            int precision = column.getColumnPrecision();
            colFormatPattern[ii] = Utility.strNN(column.getColumnFormat());
            // Column data style analizing---------
            if (!Utility.isSet(colClass)) {
              GenericReportStyleView styleView = null;
              String colDataStyle = column.getColumnDataStyleName();
              if (Utility.isSet(colDataStyle) && styleCollection.containsKey(colDataStyle)) {
                styleView = (GenericReportStyleView) styleCollection.get(colDataStyle);
              }
              colClass = (styleView != null) ? styleView.getDataClass() :"java.lang.String";
              scale = (styleView != null) ? styleView.getScale() : 0;
              colFormatPattern[ii] =( styleView != null) ? Utility.strNN(styleView.getDataFormat()) : "";
            }
            //--------------------------------------
            colScale[ii] = scale;
            if (colClass.equals("java.math.BigDecimal")) {
                if (totalFl[ii])
                    totalRowFl = true;
                total[ii] = new BigDecimal(0);
                if (ReportingUtils.isColumnForMoney(colName)) {
                    colName = ReportingUtils.extractColumnName(colName);
                }
                if (ReportingUtils.isColumnForMoney(colName) && !Utility.isSet(colFormatPattern[ii])) {
 //                   colName = ReportingUtils.extractColumnName(colName);
                    colFormat[ii] = 1; // accountingFormat;
                } else if (ReportingUtils.isColumnForPercent(colName) ||
                           colFormatPattern[ii].indexOf("%")>=0) {
                    colName = ReportingUtils.extractColumnName(colName);
                    colFormat[ii] = 2; // percentFormat;
                } else if (scale == 0) {
                    colFormat[ii] = 3; // integerFormat;
                } else {
                    colFormat[ii] = 4; // floatFormat;
                }
            } else if (colClass.equals("java.lang.Integer")) {
                if (totalFl[ii])
                    totalRowFl = true;
                total[ii] = new Integer(0);
                colFormat[ii] = 3; // integerFormat;
            } else if (colClass.equals("java.sql.Timestamp")) {
                if (ReportingUtils.isColumnForTime(colName)) {
                    colFormat[ii] = 5; // timeFormat;
                } else {
                    colFormat[ii] = 6; // dateFormat;
                }
            } else if (colClass.equals("java.math.BigDecimal.Separator")) {
                   if ( scale == 0) {
                           colFormat[ii] =3; //7;//thousndsIntegerFormat;
                   } else {
                           colFormat[ii] =4; //8;//thousndsFloatFormat;
                   }

            } else {
                colFormat[ii] = 0; // stringFormat (default);
            }
            String tColKey = "";
    		if (colName.toLowerCase().startsWith("rowinfo_currency")) {
    			tColKey = ReportingUtils.makeColumnKey("Currency");
    		} else {
    			tColKey = ReportingUtils.makeColumnKey(colName);
    		}
            //String tColKey = ReportingUtils.makeColumnKey(colName);
            String finalColName = ClwI18nUtil
                    .getMessageOrNull(request, tColKey);
            if (null == finalColName)
                finalColName = colName;
            PdfPCell cell = new PdfPCell(new Phrase(finalColName, FONT_HEADER));
            pdfHeader.addCell(cell);
        }
        document.add(pdfHeader);

        // Make report
        boolean isNewPage = false;
        for (int ii = 0; ii < table.size(); ii++) {
        	// Add new page for the next report
                //

//itext 1.2.2            PdfPTable tableTable = new PdfPTable(columnCount);
           Table tableTable = new Table(columnCount);
 //itext 1.2.2            tableTable.setWidthPercentage(100);
            tableTable.setWidth(100);
            tableTable.setWidths(widths);
            tableTable.setPadding(1);
            tableTable.setBorderWidth(0.5f);


            List row = (List) table.get(ii);
            // ------------------------------------------------------------------------------
            // See if a currency code is defined.
            for (int i = 0; i < row.size(); i++) {
                Object value = row.get(i);
                if (value instanceof java.lang.String) {
                    String t = (java.lang.String) value;
                    if (t.toLowerCase().startsWith("rowinfo_currencycd=")) {
                        thisCurrencyCode = t.substring(19);
                    }
                }
            }
            Font cellFont = FONT_CELL;
            for (int jj = 0; jj < row.size(); jj++) {
                Object value = row.get(jj);
                String formattedValue = null;
                // if (value == null) {
                // continue;
                // }
                /*if (value instanceof java.lang.String) {
                    String t = (java.lang.String) value;
                    if (t.toLowerCase().startsWith("rowinfo_")) {
                        continue;
                    }
                }*/
              if (value != null && value instanceof HashMap && ((HashMap)value).get("BOLD")!=null){
                value =  ( (HashMap) value).get("BOLD");
                cellFont.setStyle(Font.BOLD);
              }
              if (colFormatPattern[jj] != null &&
                   value instanceof java.math.BigDecimal &&
                   colFormatPattern[jj].indexOf("$")>=0){
                   formattedValue = ClwI18nUtil.formatCurrencyAmount(request, value, thisCurrencyCode);
                   if (colFormatPattern[jj].indexOf("(")>=0 ) {
                     formattedValue = "("+formattedValue+")";
                   }
                } else if (colFormat[jj] == 1
                        && (value instanceof java.math.BigDecimal)) {
                    if (totalFl[jj]) {
                        total[jj] = ((BigDecimal) total[jj])
                                .add((BigDecimal) value);
                    }
                    formattedValue = ClwI18nUtil.formatCurrencyAmount(request,
                            value, thisCurrencyCode);
                } else if (colFormat[jj] == 2
                        && (value instanceof java.math.BigDecimal)) {
                    if (totalFl[jj])
                        total[jj] = ((BigDecimal) total[jj])
                                .add((BigDecimal) value);

                    if (Utility.isSet(colFormatPattern[jj]) && colFormatPattern[jj].indexOf("%")>=0) {
                      String format = colFormatPattern[jj].replace("%","");
                      int scale = format.length() - format.indexOf(".") - 1;
                      value = ( (BigDecimal) value).multiply(new BigDecimal(100.0)).setScale(scale, BigDecimal.ROUND_HALF_UP);
                      formattedValue = formatNumber(value, thisUserLocale)+"%";
                    } else {
                        formattedValue = formatPercent(value, thisUserLocale);  // incorrect percent scale expected
                    }

                } else if (colFormat[jj] == 3
                        && (value instanceof java.math.BigDecimal)) {
                    if (totalFl[jj])
                        total[jj] = ((BigDecimal) total[jj])
                                .add((BigDecimal) value);
                    formattedValue = formatNumber(value, thisUserLocale);
                } else if (colFormat[jj] == 4
                        && (value instanceof java.math.BigDecimal)) {
                    if (totalFl[jj])
                        total[jj] = ((BigDecimal) total[jj]).add((BigDecimal) value);

                    formattedValue = formatNumber(value, thisUserLocale);
                } else if (colFormat[jj] == 3
                        && (value instanceof java.lang.Integer)) {
                    if (totalFl[jj])
                        total[jj] = new Integer(((Integer) total[jj]).intValue()
                                + ((Integer) value).intValue());
                    formattedValue = formatNumber(value, thisUserLocale);
                }else if(value instanceof java.lang.Long){
                	if (totalFl[jj])
                        total[jj] = new Long(((Long) total[jj])
                                .longValue()
                                + ((Long) value).longValue());
                    formattedValue = value.toString();
                }else if(value instanceof java.lang.Integer){
                	if (totalFl[jj])
                        total[jj] = new Integer(((Integer) total[jj])
                                .intValue()
                                + ((Integer) value).intValue());
                    formattedValue = value.toString();
                } else if (colFormat[jj] == 5
                        && (value instanceof java.util.Date)) {
                    formattedValue = formatTime(value, thisUserLocale);
                } else if (colFormat[jj] == 6
                        && (value instanceof java.util.Date)) {
                    formattedValue = formatDate(value, thisUserLocale);

                } else {

			if(value!=null) {
                         if (value instanceof GenericReportCellView) {
                           String styleName = ((GenericReportCellView)value).getStyleName();
                           value = ((GenericReportCellView)value).getDataValue();
                           String format = null;
                           if (Utility.isSet(styleName) && styleCollection.containsKey(styleName)){
                             GenericReportStyleView style = (GenericReportStyleView)styleCollection.get(styleName);
                             format = (style!= null) ? style.getDataFormat() : null;
                           }
                           /** @todo Determine FORMAT of Value more correct from style  !!! */
                           if (format!=null && value instanceof java.math.BigDecimal){
                             if (format.indexOf("$")>=0){
                             //formattedValue = formatNumber(value, thisUserLocale);
                               formattedValue = ClwI18nUtil.formatCurrencyAmount(request, value, thisCurrencyCode);
                               if (format.indexOf("(")>=0 ) {
                                 formattedValue = "("+formattedValue+")";
                               }
                             } else if (format.indexOf("%")>=0) {
                                 format = format.replace("%","");
                                 int scale = format.length() - format.indexOf(".") - 1;
                                 value = ( (BigDecimal) value).multiply(new BigDecimal(100.0)).setScale(scale, BigDecimal.ROUND_HALF_UP);
                                 formattedValue = formatNumber(value, thisUserLocale)+"%";
      //                          formattedValue = formatPercent(value,thisUserLocale);
                             }
                           } else if (value instanceof java.lang.Integer) {
                             if (totalFl[jj]) total[jj] = new Integer(((Integer) total[jj]).intValue()+ ((Integer) value).intValue());
                             formattedValue = formatNumber(value, thisUserLocale);
                           } else if (value instanceof java.util.Date) {
                             formattedValue = formatTime(value, thisUserLocale);
                           } else if (value instanceof java.util.Date) {
                             formattedValue = formatDate(value, thisUserLocale);
                           }  else {
                             formattedValue = value.toString();
                           }

			 } else if (value instanceof java.lang.String){
            			    String t = (java.lang.String) value;
	                        if (t.toLowerCase().startsWith("rowinfo_")) {
	                        	formattedValue = t.substring(19);
	                        }else{
	                        	formattedValue = value.toString();
	                        }
			            } else {
				            formattedValue = "";
		    	        }
			}
                }
                if (formattedValue == null ){
                  formattedValue = "";
                }
//                PdfPCell pageCell = new PdfPCell(new Phrase(formattedValue, FONT_CELL));
//itext 1.2.2                PdfPCell pageCell = new PdfPCell(new Phrase(formattedValue, cellFont));
                Cell pageCell = new Cell(new Phrase(formattedValue, cellFont));

                if (value instanceof java.lang.String) {
                  pageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                } else{
                  pageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                }
                tableTable.addCell(pageCell);
            }
//            if (!writer.fitsPage(tableTable, document.bottomMargin())) {
            if (!writer.fitsPage(tableTable, document.bottomMargin())) {
                PdfPTable pageTable = new PdfPTable(1);
                PdfPCell pageCell = new PdfPCell(new Phrase("Page "+pageNumber, FONT_HEADER));
                pageCell.setBorder(0);
                pageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pageTable.addCell(pageCell);
                document.add(pageTable);

                pageNumber = pageNumber + 1;
                isNewPage = true;

                document.newPage();
                document.add(pdfHeader); // header on the every page
                document.add(tableTable);

            } else {
                document.add(tableTable);
            }

            if (totalRowFl) {
                for (int jj = 0; jj < totalFl.length; jj++) {
                    String formattedValue = null;
                    if (colFormat[jj] == 1
                            && (total[jj] instanceof java.math.BigDecimal)
                            && totalFl[jj]) {
                        formattedValue = ClwI18nUtil.formatCurrencyAmount(
                                request, total[jj], thisCurrencyCode);
                    } else if (colFormat[jj] == 2
                            && (total[jj] instanceof java.math.BigDecimal)
                            && totalFl[jj]) {
                        formattedValue = formatPercent(total[jj],
                                thisUserLocale);
                    } else if (colFormat[jj] == 3
                            && (total[jj] instanceof java.math.BigDecimal)
                            && totalFl[jj]) {
                        formattedValue = formatNumber(total[jj], thisUserLocale);
                    } else if (colFormat[jj] == 4
                            && (total[jj] instanceof java.math.BigDecimal)
                            && totalFl[jj]) {
                        formattedValue = formatNumber(total[jj], thisUserLocale);
                    } else if (colFormat[jj] == 3
                            && (total[jj] instanceof java.lang.Integer)
                            && totalFl[jj]) {
                        formattedValue = formatNumber(total[jj], thisUserLocale);
                    } else {
                        formattedValue = (total[jj] != null) ? total[jj].toString() : "";
                    }
                }
            }
        }
        if (isNewPage) {
            PdfPTable pageTable = new PdfPTable(1);
            PdfPCell pageCell = new PdfPCell(new Phrase("Page "+pageNumber, FONT_HEADER));
            pageCell.setBorder(0);
            pageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pageTable.addCell(pageCell);
            document.add(pageTable);
        }

    }

    private static String reportColumnName(ReportRequest pRepRequest,
            String pColName) {
        if (null == pColName)
            return "-";
        pColName = ReportingUtils.extractColumnName(pColName);

        if (null == pRepRequest || pRepRequest.mMessageRes == null) {
            return pColName;
        }
        String key = "";
        if (pColName.toLowerCase().startsWith("rowinfo_currency")) {
            key = ReportingUtils.makeColumnKey("Currency");
        } else {
            key = ReportingUtils.makeColumnKey(pColName);
        }
        MessageResources resources = pRepRequest.mMessageRes;
        Locale locale = null;
        Object[] args = null;
        if (!(resources instanceof ClwMessageResourcesImpl)) {
            String s = resources.getMessage(key, args);
            if (null != s)
                return s;
            return pColName;
        }

        int storeId = 0;
        CleanwiseUser appUser = pRepRequest.mUser;
        if (appUser != null && appUser.getUserStore() != null
                && appUser.getUserStore().getBusEntity() != null) {
            storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
            // get the locale set up for the user.
            locale = appUser.getStorePrefixLocale();
        }
        ClwMessageResourcesImpl clwResources = (ClwMessageResourcesImpl) resources;
        String s = clwResources.getMessage(locale, key, args,new String[0]);
        if (null != s) {
            return s;
        }
        return pColName;
    }

    public static boolean isCorrectReport(GenericReportResultView report) {
        if (report != null) {
            String cols[] = checkColumnWidth(report);
            if (cols == null || cols.length == 0) {
                try {
                    if (getPaperSize(report.getPaperSize(),
                            RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE
                                    .equals(report.getPaperOrientation())) != null) {
                        return true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    public static String[] checkColumnWidth(GenericReportResultView repRes) {
        int columnCount = repRes.getColumnCount();
        GenericReportColumnViewVector header = repRes.getHeader();
        List cols = new ArrayList();
        for (int ii = 0; ii < columnCount; ii++) {
            GenericReportColumnView column = (GenericReportColumnView) header
                    .get(ii);
            int width = Utility.parseInt(column.getColumnWidth());
            if (width <= 0) {
                cols.add(column.getColumnName());
            }
        }
        return (String[]) cols.toArray(new String[cols.size()]);
    }

    public static String formatPercent(Object value, Locale locale) {
        String text = "";
        if (value != null) {
            Format formatter = NumberFormat.getPercentInstance(locale);
            text = formatter.format(value);
        }
        return text;
    }

    public static String formatNumber(Object value, Locale locale) {
        String text = "";
        if (value != null) {
            Format formatter = NumberFormat.getInstance(locale);
            text = formatter.format(value);
        }
        return text;
    }

    public static String formatTime(Object value, Locale locale) {
        String text = "";
        if (value != null) {
            Format formatter = DateFormat.getTimeInstance(3, locale);
            text = formatter.format(value);
        }
        return text;
    }

    public static String formatDate(Object value, Locale locale) {
        String text = "";
        if (value != null) {
            Format formatter = DateFormat.getDateInstance(3, locale);
            text = formatter.format(value);
        }
        return text;
    }

    public static Rectangle getPaperSize(String paperSize, boolean landscape) {
        Rectangle rectangle = getPaperSize(paperSize);
        if (landscape == true) {
            rectangle = rectangle.rotate();
        }
        return rectangle;
    }

    public static Rectangle getPaperSize(String paperSize) {
        if ("A0".equals(paperSize)) {
            return PageSize.A0;
        } else if ("A1".equals(paperSize)) {
            return PageSize.A1;
        } else if ("A10".equals(paperSize)) {
            return PageSize.A10;
        } else if ("A2".equals(paperSize)) {
            return PageSize.A2;
        } else if ("A3".equals(paperSize)) {
            return PageSize.A3;
        } else if ("A4".equals(paperSize)) {
            return PageSize.A4;
        } else if ("A5".equals(paperSize)) {
            return PageSize.A5;
        } else if ("A6".equals(paperSize)) {
            return PageSize.A6;
        } else if ("A7".equals(paperSize)) {
            return PageSize.A7;
        } else if ("A8".equals(paperSize)) {
            return PageSize.A8;
        } else if ("A9".equals(paperSize)) {
            return PageSize.A9;
        } else if ("ARCH_A".equals(paperSize)) {
            return PageSize.ARCH_A;
        } else if ("ARCH_B".equals(paperSize)) {
            return PageSize.ARCH_B;
        } else if ("ARCH_C".equals(paperSize)) {
            return PageSize.ARCH_C;
        } else if ("ARCH_D".equals(paperSize)) {
            return PageSize.ARCH_D;
        } else if ("ARCH_E".equals(paperSize)) {
            return PageSize.ARCH_E;
        } else if ("B0".equals(paperSize)) {
            return PageSize.B0;
        } else if ("B1".equals(paperSize)) {
            return PageSize.B1;
        } else if ("B2".equals(paperSize)) {
            return PageSize.B2;
        } else if ("B3".equals(paperSize)) {
            return PageSize.B3;
        } else if ("B4".equals(paperSize)) {
            return PageSize.B4;
        } else if ("B5".equals(paperSize)) {
            return PageSize.B5;
        } else if ("FLSA".equals(paperSize)) {
            return PageSize.FLSA;
        } else if ("FLSE".equals(paperSize)) {
            return PageSize.FLSE;
        } else if ("HALFLETTER".equals(paperSize)) {
            return PageSize.HALFLETTER;
        } else if ("LEDGER".equals(paperSize)) {
            return PageSize.LEDGER;
        } else if ("LEGAL".equals(paperSize)) {
            return PageSize.LEGAL;
        } else if ("LETTER".equals(paperSize)) {
            return PageSize.LETTER;
        } else if ("NOTE".equals(paperSize)) {
            return PageSize.NOTE;
        } else if ("_11X17".equals(paperSize)) {
            return PageSize._11X17;
        } else {
            throw new RuntimeException("Not exist paper size:" + paperSize);
        }
    }
}
