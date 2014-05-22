/*
 * ReportWritter.java
 *
 * Created on March 4, 2003, 11:30 AM
 */

package com.cleanwise.service.api.reporting;

import java.text.SimpleDateFormat;
import java.util.Map;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.Report;
import com.cleanwise.service.api.reporting.ReportingUtils;

//import com.cleanwise.view.i18n.*;
/*
import com.lowagie.text.Chunk;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
*/
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
//POI API------------------------------
//import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.hssf.util.HSSFColor.*;
//import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;
import java.math.BigDecimal;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.MessageResources;
import java.util.HashMap;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.reporting.BaseJDReport;
import com.cleanwise.service.api.value.GenericReportCellView;
import java.util.Iterator;
import com.cleanwise.service.api.framework.ValueObject;
import com.cleanwise.service.api.value.GenericReportStyleView;
//import org.apache.poi.xssf.model.StylesTable;
import java.io.*;

/**
 *
 */
public class ReportWriterUtil {

       public static String TIMES_FONT = "Times New Roman" ;
       public interface DEFAULT_FONT {
         public static final String SIZE = "10",
                                    NAME = TIMES_FONT;
       }
       public static double DEFAULT_WIDTH_FACTOR = 1.5;
       public static Map fontCollection;
       public static Map styleCollection;

	/** Creates a new instance of ReportWritter */
	private ReportWriterUtil() {
	}

	/**
	 *Queries the EJB for the results of the specified report with the specified parameters.
	 *
	 *@param ReportOrder initialized reference to the reportOrder EJB
	 *@param String report category (necessary as reports are unique per category and name)
	 *@param String report name
	 *@param Map with report parameters as the keys to the report param values, may be null
	 *  if the report does not requiere any parameters
	 *@returns GenericReportResultView the result of the report
	 */
	public static GenericReportResultView getReportData(Report reportEjb,
			String rCategory, String rName, Map params) throws Exception {
		GenericReportResultView repRes = reportEjb.processGenericReport(
				rCategory, rName, params);
		return repRes;
	}

	/**
	 *Queries the EJB for the results of the specified report with the specified parameters.
	 *
	 *@param ReportOrder initialized reference to the reportOrder EJB
	 *@param String report category (necessary as reports are unique per category and name)
	 *@param String report name
	 *@param Map with report parameters as the keys to the report param values, may be null
	 *  if the report does not requiere any parameters
	 *@returns vector of GenericReportResultView objects
	 */
	public static GenericReportResultViewVector getReportDataMulti(
			Report reportEjb, String rCategory, String rName, Map params)
			throws Exception {
		GenericReportResultViewVector repRes = reportEjb
				.processGenericReportMulti(rCategory, rName, params);
		return repRes;
	}

	/**
	 *Queries the EJB for the results of the specified report with the specified parameters.
	 *and spools the results to the specified output stream.  The output stream is not flushed or
	 *closed.
	 *
	 *@param ReportOrder initialized reference to the reportOrder EJB
	 *@param String report category (necessary as reports are unique per category and name)
	 *@param String report name
	 *@param Map with report parameters as the keys to the report param values, may be null
	 *  if the report does not requiere any parameters
	 *@param pOut the output stream to write the Excep report to
	 */
	public static void generateExcelReport(Report reportEjb, String rType,
			String rName, Map params, OutputStream pOut) throws Exception {
		writeExcelReportMulti(getReportDataMulti(reportEjb, rType, rName,
				params), pOut);
	}

        public static void writeExcelReport(GenericReportResultView repRes,
                        OutputStream pOut) throws Exception {
          writeExcelReport(repRes, pOut, ".xls");
        }

	/**
	 *Spools the GenericReportResultView to the specified output stream.
	 *
	 *@param GenericReportResultView
	 *@param OutputStream the output stream to write the Excep report to
	 */
	public static void writeExcelReport(GenericReportResultView repRes,
			OutputStream pOut, String xslType) throws Exception {


                HSSFWorkbook workbook = null;
                if(Utility.isSet(xslType) && xslType.equals(".xlsx")) {
 //                 workbook = new XSSFWorkbook();
                } else {
                  workbook = new HSSFWorkbook();
                }
                if (repRes == null){
                  workbook.write(pOut);
                  return;
                }
                int columnCount = repRes.getColumnCount();
                GenericReportColumnViewVector header = repRes.getHeader();

                fontCollection = createFonts(workbook, repRes.getUserStyle());
                styleCollection = createStyles(workbook, repRes.getUserStyle());
                //Map styles = createStyles(workbook);

		//prepare formats
                HSSFCellStyle[] columnXlsStyle = new HSSFCellStyle[columnCount];
 //               CreationHelper createHelper = workbook.getCreationHelper();
		for (int ii = 0; ii < columnCount; ii++) {
			GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
			String colName = column.getColumnName();
			String colClass = column.getColumnClass();
			int colScale = column.getColumnScale();
                        int colPrecision = column.getColumnPrecision();
                        if (colClass != null ) {
			 if (colClass.equals("java.math.BigDecimal")) {
                                String columnFormat = column.getColumnFormat();
                                if(Utility.isSet(columnFormat)) {
                                      HSSFCellStyle style = workbook.createCellStyle();
                     //               Font cellNormalFont = createCellFont(workbook, 10, TIMES_FONT, Font.BOLDWEIGHT_NORMAL );
                                      style.setFont((HSSFFont)fontCollection.get(ReportingUtils.TABLE_DATA));

//                                      style.setDataFormat(createHelper.createDataFormat().getFormat(columnFormat));
                                      style.setDataFormat(workbook.createDataFormat().getFormat(columnFormat));
                                      columnXlsStyle[ii] = style ;
                                } else {
                                    if (ReportingUtils.isColumnForMoney(colName)) {
                                        colName = ReportingUtils.extractColumnName(colName);
                                        columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.ACCOUNTING_STYLE);
                                    } else if (ReportingUtils.isColumnForPercent(colName)) {
                                            colName = ReportingUtils.extractColumnName(colName);
                                        columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.PERCENT_STYLE);

                                    } else if (colPrecision == 0) {
                                        columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.INTEGER_STYLE);

                                    } else {
                                        columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.FLOAT_STYLE);

                                    }
                                }
			   } else if (colClass.equals("java.sql.Timestamp")) {
				if (ReportingUtils.isColumnForTime(colName)) {
                                        columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.TIME_STYLE);
				} else {
                                        columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.DATE_STYLE);
				}
			   } else if (colClass.equals("java.lang.Integer")) {
                                String columnFormat = column.getColumnFormat();
                                if(Utility.isSet(columnFormat)) {
                                    HSSFCellStyle style = workbook.createCellStyle();
 //                                   Font cellNormalFont = createCellFont(workbook, 10, TIMES_FONT, Font.BOLDWEIGHT_NORMAL );
                                    style.setFont((HSSFFont)fontCollection.get(ReportingUtils.TABLE_DATA));
//                                    style.setDataFormat(createHelper.createDataFormat().getFormat(columnFormat));
                                    style.setDataFormat(workbook.createDataFormat().getFormat(columnFormat));
                                    columnXlsStyle[ii] = style ;
                                } else {
                                    columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.INTEGER_STYLE);
                                }
			   } else {
                                columnXlsStyle[ii] = null;
			}
                     }
                }

		//format

		int pageSize = 65000;
		int shift = 0;
		ArrayList table = repRes.getTable();
		int pageCount = table.size() / pageSize;
		if (table.size() % pageSize > 0)
			pageCount++;
		if (pageCount == 0)
			pageCount++;
		for (int curPage = 0; curPage < pageCount; curPage++) {
			String sheetName = repRes.getName();
			if (sheetName == null || sheetName.trim().length() == 0) {
				sheetName = "Sheet" + curPage;
			}
			if(sheetName.length() > 29){
				sheetName = sheetName.substring(0,29);
			}
                        HSSFSheet sheet = workbook.createSheet(sheetName);
			//Make header
			for (int ii = 0; ii < columnCount; ii++) {
				GenericReportColumnView column = (GenericReportColumnView) header
						.get(ii);
				String colName = column.getColumnName();
				colName = ReportingUtils.extractColumnName(colName);
				String tColKey = "";
				if (colName.toLowerCase().startsWith("rowInfo_currency")) {
					tColKey = ReportingUtils.makeColumnKey("Currency");
				} else {
					tColKey = ReportingUtils.makeColumnKey(colName);
				}
				setHeader(sheet, ii, colName, (HSSFCellStyle)styleCollection.get(ReportingUtils.TABLE_HEADER));

			}

			int startRow = curPage * pageSize;
			int endRowNext = startRow + pageSize;
			if (endRowNext > table.size())
				endRowNext = table.size();
			for (int ii = endRowNext - 1; ii >= startRow; ii--) {
				List repRow = (List) table.get(ii);

                                HSSFRow row = sheet.createRow(startRow + ii + 1);
				for (int jj = 0; jj < repRow.size(); jj++) {
					GenericReportColumnView col = (GenericReportColumnView) header
							.get(jj);
					Object obj = repRow.get(jj);
					String thisCurrencyCode = null;
					if (obj instanceof java.lang.String) {
						String t = (java.lang.String) obj;
						if (t.startsWith("rowInfo_currency")) {
							thisCurrencyCode = t.substring(19);
						}
					}

					if (null != thisCurrencyCode) {
						setCellNoNull(sheet, jj, row, thisCurrencyCode, null);
					} else {
						setCellNoNull(sheet, jj, row, obj, columnXlsStyle[jj]);
					}
				}

			}

		}

		workbook.write(pOut);
	}

	/**
	 *Spools the GenericReportResultView to the specified output stream.
	 *
	 *@param GenericReportResultView
	 *@param OutputStream the output stream to write the Excep report to
	 */
	public static void writeExcelReport(GenericReportResultView repRes,
                        String pSheetName, HSSFWorkbook pWorkbook) throws Exception {
		writeExcelReport(repRes, pSheetName, pWorkbook, null);
	}

	public static void writeExcelReport(GenericReportResultView repRes,
                        String pSheetName, HSSFWorkbook pWorkbook,
			ReportRequestView pRepRequest) throws Exception {
		int columnCount = repRes.getColumnCount();
                GenericReportColumnViewVector header = repRes.getHeader();

                fontCollection = createFonts(pWorkbook, repRes.getUserStyle());
                styleCollection = createStyles(pWorkbook, repRes.getUserStyle() );
		//Determin XLS column type
                HSSFCellStyle[] columnXlsStyle = new HSSFCellStyle[columnCount];
                int tableWidth = 0;
		for (int ii = 0; ii < columnCount; ii++) {
			GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
			String colName = column.getColumnName();
			String colClass = column.getColumnClass();
			int colScale = column.getColumnScale();
                        int colPrecision = column.getColumnPrecision();
                        int colWidth = Utility.parseInt(column.getColumnWidth());

                        String colDataStyle = column.getColumnDataStyleName();
//                        CreationHelper createHelper = pWorkbook.getCreationHelper();
                        if (Utility.isSet(colDataStyle) && styleCollection.containsKey(colDataStyle)){
                          columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(colDataStyle);
                        } else if (colClass != null){
                             if (colClass.equals("java.math.BigDecimal")) {
                                String columnFormat = column.getColumnFormat();
                                if(Utility.isSet(columnFormat)) {
                                      HSSFCellStyle style = pWorkbook.createCellStyle();
//                                      Font cellNormalFont = getCellFont(pWorkbook, 10, TIMES_FONT, Font.BOLDWEIGHT_NORMAL );
//                                      style.setFont(cellNormalFont);
                                      style.setFont((HSSFFont)fontCollection.get(ReportingUtils.TABLE_DATA));
//                                      style.setDataFormat(createHelper.createDataFormat().getFormat(columnFormat));
                                      style.setDataFormat(pWorkbook.createDataFormat().getFormat(columnFormat));
                                      columnXlsStyle[ii] = style ;
                                } else {
                                    if (ReportingUtils.isColumnForMoney(colName)) {
                                            colName = ReportingUtils.extractColumnName(colName);
                                            columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.ACCOUNTING_STYLE) ;
                                    } else if (ReportingUtils.isColumnForPercent(colName)) {
                                            colName = ReportingUtils.extractColumnName(colName);
                                            columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.PERCENT_STYLE) ;
                                    } else if (colScale == 0) {
                                            columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.INTEGER_STYLE) ;
                                    } else {
                                            columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.FLOAT_STYLE) ;

                                    }
                                }
                              } else if (colClass.equals("java.math.BigDecimal.Separator")) {
                                if (colPrecision == 0) {
                                        HSSFCellStyle style = (HSSFCellStyle)styleCollection.get(ReportingUtils.INTEGER_SEPARATOR_STYLE);
                                        columnXlsStyle[ii] = style ;

                                } else {
                                        HSSFCellStyle style = (HSSFCellStyle)styleCollection.get(ReportingUtils.FLOAT_SEPARATOR_STYLE);
                                        columnXlsStyle[ii] = style ;

                                }
                              } else if (colClass.equals("java.sql.Timestamp")) {
				if (ReportingUtils.isColumnForTime(colName)) {
                                  columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.TIME_STYLE) ;
				} else {
                                  columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.DATE_STYLE) ;
				}
                              } else if (colClass.equals("java.lang.Integer")) {
                                String columnFormat = column.getColumnFormat();
                                if(Utility.isSet(columnFormat)) {
                                        HSSFCellStyle style = pWorkbook.createCellStyle();
          //                              Font cellNormalFont = getCellFont(pWorkbook, 10, TIMES_FONT, Font.BOLDWEIGHT_NORMAL );
          //                              style.setFont(cellNormalFont);
                                        style.setFont((HSSFFont)fontCollection.get(ReportingUtils.TABLE_DATA));
//                                        style.setDataFormat(createHelper.createDataFormat().getFormat(columnFormat));
                                        style.setDataFormat(pWorkbook.createDataFormat().getFormat(columnFormat));
                                        columnXlsStyle[ii] = style;
                                } else {
                                      columnXlsStyle[ii] = (HSSFCellStyle)styleCollection.get(ReportingUtils.INTEGER_STYLE) ;
                                }
                              } else {
                                columnXlsStyle[ii] = null;
                              }
                        }
                      tableWidth +=colWidth;

 		}

		//format
		int pageSize =65000;
		int shift = 0;
		ArrayList table = repRes.getTable();
		int pageCount = 0;
		if (table != null) {
			pageCount = table.size() / pageSize;
			if (table.size() % pageSize > 0)
				pageCount++;
		}

		if (pageCount == 0)
			pageCount++;
		for (int curPage = 0; curPage < pageCount; curPage++) {
			String name = pSheetName;
			if (curPage > 0) {
                          name = pSheetName + "." + curPage;
                        }
                        HSSFSheet sheet = pWorkbook.createSheet(name);
                        // Make Title
                        GenericReportColumnViewVector title = repRes.getTitle();

                        int titleRow = 0;
                        if (title != null && curPage == 0) {
                          HSSFCellStyle titleStyle = (HSSFCellStyle)styleCollection.get(ReportingUtils.PAGE_TITLE);
                          int columnCountTitle = title.size();
                          for (int ii = 0; ii < columnCountTitle; ii++) {
                                  GenericReportColumnView column = (GenericReportColumnView) title.get(ii);
                                  String colTitleStyleS = column.getColumnHeaderStyleName();
                                  HSSFCellStyle colTitleStyle = titleStyle;
                                  if (Utility.isSet(colTitleStyleS) && styleCollection.containsKey(colTitleStyleS)){
                                    colTitleStyle = (HSSFCellStyle)styleCollection.get(colTitleStyleS);
                                  }
                                  String colName = reportColumnName(pRepRequest, column.getColumnName());
                                  setTitle(sheet, ii, titleRow, colName, colTitleStyle );

                          }
                          //  GROUP for Title :: todo when it will be requested
                          //   sheet.groupRow(titleRow,columnCountTitle-1);
                          titleRow = columnCountTitle+1;//2;  // title row displacement
                        }
			//Make header
                        HSSFRow rowHeader = sheet.createRow(0 + titleRow);
                        HSSFCellStyle headerStyle = (HSSFCellStyle)styleCollection.get(ReportingUtils.TABLE_HEADER);
                        HashMap userStyles =  repRes.getUserStyle();

			for (int ii = 0; ii < columnCount; ii++) {
				GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
 //                               String colHeaderStyleS = column.getColumnHeaderStyleName();
                                String colHeaderStyleS = column.getColumnHeaderStyleName();
                                //----------------------------------------------------//
                                GenericReportStyleView styleView = null;
                                if (userStyles != null){
                                   styleView = (GenericReportStyleView)userStyles.get(colHeaderStyleS);
                                }
                                String xlsColumnWidth = (styleView != null) ? styleView.getWidth() : "";
                                //---------------------------------------------------//
                                HSSFCellStyle colHeaderStyle = headerStyle;
                                if (Utility.isSet(colHeaderStyleS) && styleCollection.containsKey(colHeaderStyleS)){
                                  colHeaderStyle = (HSSFCellStyle)styleCollection.get(colHeaderStyleS);
                                }
				String colName = reportColumnName(pRepRequest, column.getColumnName());
                                int colWidth = Utility.parseInt(column.getColumnWidth());
                                setColWidth(sheet,ii, colWidth, xlsColumnWidth, repRes.getWidthFactor(), tableWidth, columnCount);
                                setHeader(sheet, ii, titleRow, colName, colHeaderStyle);
			}

                        if (repRes.getFreezePositionColumn()>0 || repRes.getFreezePositionRow() > 0) {
                          sheet.createFreezePane(repRes.getFreezePositionColumn(),repRes.getFreezePositionRow());
                        }
                        //Make table
//			int startRow = curPage * pageSize;
                        int startRow = 0;

                        int endRowNext = startRow + pageSize;
			if (table != null) {
                                if ((curPage + 1) * pageSize - table.size()  < 0){
                                  endRowNext = startRow + pageSize;
                                } else {
                                  endRowNext = table.size()- curPage * pageSize;
                                }

//				if (endRowNext > table.size())
//                                      endRowNext = table.size();
			} else {
                              endRowNext = 0;
			}

			for (int ii = endRowNext - 1; ii >= startRow; ii--) {
//				List repRow = (List) table.get(ii);
                                List repRow = (List) table.get(ii + curPage * pageSize);
                                HSSFRow row = null;
                                if(curPage == 0 ){
                                  row = sheet.createRow(startRow + ii + titleRow + 1);
                                } else {
                                  row = sheet.createRow(startRow + ii );
                                }
                                for (int jj = 0; jj < repRow.size() && jj < columnXlsStyle.length; jj++) {
					GenericReportColumnView col = (GenericReportColumnView) header.get(jj);
					Object obj = repRow.get(jj);
					String thisCurrencyCode = null;
                                        HSSFCellStyle cellStyle = columnXlsStyle[jj];

                                        if (obj instanceof java.lang.String) {
						String t = (java.lang.String) obj;
						if (t.startsWith("rowInfo_currencyCd=")) {
							thisCurrencyCode = t.substring(19);
						}
					}
                                        if (obj instanceof HashMap){
                                          Object cellValue = ((HashMap)obj).get("BOLD") ;
                                          if (cellValue != null){
//                                            CreationHelper createHelper = pWorkbook.getCreationHelper();
                                            //Font cellBoldFont = getCellFont(pWorkbook, 10, TIMES_FONT, Font.BOLDWEIGHT_BOLD );
                                            HSSFCellStyle style = pWorkbook.createCellStyle();
                                            if (columnXlsStyle[jj] != null ) {
                                              String cellFormat = columnXlsStyle[jj].getDataFormatString();
                                              if (Utility.isSet(cellFormat)){
//                                                style.setDataFormat(createHelper.createDataFormat().getFormat(cellFormat));
                                                style.setDataFormat(pWorkbook.createDataFormat().getFormat(cellFormat));
                                              }
                                            }
                              //              style.cloneStyleFrom(columnXlsStyle[jj]);
                                            style.setFont((HSSFFont)fontCollection.get(ReportingUtils.GROUP_TOTAL));
                                            cellStyle = style;
                                            obj = cellValue;
                                          }

                                        }
                                      if (obj instanceof GenericReportCellView){
                                          GenericReportCellView cellView = (GenericReportCellView)obj;
                                          Object cellValue = cellView.getDataValue();

                                          // we need to get style for dataFormat & dataCategory from Column Header
//                                          CreationHelper createHelper = pWorkbook.getCreationHelper();
                                          HSSFCellStyle style = pWorkbook.createCellStyle();
                                          //updating style for row element
                                          HSSFCellStyle newStyle = (HSSFCellStyle)styleCollection.get(cellView.getStyleName());
                                          // cell's data format
                                          String cellFormat = "";
                                          if (newStyle != null && newStyle.getDataFormatString()!=null){
                                            cellFormat = newStyle.getDataFormatString();
                                          } else if (columnXlsStyle[jj] != null) {
                                            cellFormat = columnXlsStyle[jj].getDataFormatString();
                                          }

                                          if (Utility.isSet(cellFormat)){
//                                             style.setDataFormat(createHelper.createDataFormat().getFormat(cellFormat));
                                             style.setDataFormat(pWorkbook.createDataFormat().getFormat(cellFormat));
                                           }
                                          // cell's font
                                          if (cellView.getStyleName() != null && fontCollection.get(cellView.getStyleName()) != null ){
                                            style.setFont( (HSSFFont) fontCollection.get(cellView.getStyleName()));
                                          } else {
                                            style.setFont((HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
                                          }
                                          // cell's color
                                          if (newStyle != null && (newStyle.getFillPattern()!=HSSFCellStyle.NO_FILL)) {
                                            style.setFillForegroundColor(newStyle.getFillForegroundColor());
                                            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                                          }
                                          // cell's alignment
                                          if (newStyle != null && newStyle.getAlignment() >= 0){
                                            style.setAlignment(newStyle.getAlignment());
                                          }
                                          /** @todo change style for MERGE  */
                                          cellStyle = style;
                                          obj = cellValue;

                                        }

					if (null != thisCurrencyCode) {
					   setCellNoNull(sheet, jj, row, thisCurrencyCode, null);
					} else {
                                           setCellNoNull(sheet, jj, row, obj, cellStyle);
					}
				}
			}
		}
	}
        private static int ii = 0;

        private static HSSFFont findCellFont(HSSFWorkbook wb, GenericReportStyleView userFont){
          String fontTypeS = userFont.getFontType();
          if (fontTypeS == null) {
            return (HSSFFont)fontCollection.get(ReportingUtils.TABLE_DATA);
          } else if ( fontCollection.containsKey(fontTypeS) ){
            return (HSSFFont)fontCollection.get(fontTypeS);
          } else {
            return newCellFont(wb, userFont);
          }
        }

        private static HSSFFont newCellFont (HSSFWorkbook wb, GenericReportStyleView userFont){
          String fontTypeS = userFont.getFontType();
          short fontType = (fontTypeS != null && fontTypeS.equals("BOLD")) ? HSSFFont.BOLDWEIGHT_BOLD : HSSFFont.BOLDWEIGHT_NORMAL;
          int fontIndex  = (userFont.getFontSize()!=-1) ? userFont.getFontSize() : Integer.parseInt(DEFAULT_FONT.SIZE);
          String fontName = (userFont.getFontName()!=null) ? userFont.getFontName() : DEFAULT_FONT.NAME;
          IndexedColors color = (userFont.getFontColor()!=null) ? IndexedColors.getColor(userFont.getFontColor()) : null;
          short fontColor = (color != null) ? color.getIndex() : HSSFFont.COLOR_NORMAL;
          return createCellFont( wb,  fontIndex,  fontName,  fontType,  fontColor);
        }

        private static HSSFFont createCellFont (HSSFWorkbook wb, int fontIndex, String fontName, short fontType){
          return createCellFont( wb,  fontIndex,  fontName,  fontType,  HSSFFont.COLOR_NORMAL);
        }

        private static HSSFFont createCellFont (HSSFWorkbook wb, int fontIndex, String fontName, short fontType, short fontColor){
          HSSFFont font = wb.findFont(fontType, fontColor, (short)fontIndex,fontName,false,false,HSSFFont.SS_NONE,HSSFFont.U_NONE  );
          if (font == null ) {
            font = wb.createFont();
            font.setColor(fontColor);
            font.setItalic(false);
            font.setStrikeout(false);
            font.setTypeOffset(HSSFFont.SS_NONE);
            font.setUnderline(HSSFFont.U_NONE);
          }
          font.setFontHeightInPoints((short)fontIndex);
          font.setFontName(fontName);
          font.setBoldweight(fontType);
          return font;
        }


        private static Map createFonts(HSSFWorkbook wb, HashMap userFontDesc){
          Map fonts = new HashMap();
          int defFontSize  = Integer.parseInt(DEFAULT_FONT.SIZE);
          HSSFFont pageTitle = createCellFont(wb, defFontSize,DEFAULT_FONT.NAME, HSSFFont.BOLDWEIGHT_BOLD );
          HSSFFont tableHeader = createCellFont(wb, defFontSize, DEFAULT_FONT.NAME, HSSFFont.BOLDWEIGHT_BOLD );
          HSSFFont tableData = createCellFont(wb, defFontSize, DEFAULT_FONT.NAME, HSSFFont.BOLDWEIGHT_NORMAL );
          HSSFFont groupTotal = createCellFont(wb, defFontSize, DEFAULT_FONT.NAME, HSSFFont.BOLDWEIGHT_BOLD );
          HSSFFont negativeNumber = createCellFont(wb, defFontSize, DEFAULT_FONT.NAME, HSSFFont.BOLDWEIGHT_NORMAL , HSSFFont.COLOR_RED);
          if (userFontDesc != null){
            Map.Entry entry = null;
            Iterator it = userFontDesc.entrySet().iterator();
            while (it.hasNext()) {
              entry = (Map.Entry) it.next();
              fonts.put(entry.getKey(), newCellFont(wb, (GenericReportStyleView)entry.getValue()));
            }
          }
          if (! fonts.containsKey(ReportingUtils.PAGE_TITLE) ){
            fonts.put(ReportingUtils.PAGE_TITLE, pageTitle);
          }
          if (! fonts.containsKey(ReportingUtils.TABLE_HEADER) ){
            fonts.put(ReportingUtils.TABLE_HEADER, tableHeader);
          }
          if (! fonts.containsKey(ReportingUtils.TABLE_DATA) ){
            fonts.put(ReportingUtils.TABLE_DATA, tableData);
          }
          if (! fonts.containsKey(ReportingUtils.GROUP_TOTAL) ){
            fonts.put(ReportingUtils.GROUP_TOTAL, groupTotal);
          }
          if (! fonts.containsKey(ReportingUtils.NEGATIVE_NUMBER) ){
            fonts.put(ReportingUtils.NEGATIVE_NUMBER, negativeNumber);
          }
          return fonts;
        }

        private static Map createStyles(HSSFWorkbook wb, HashMap userStyleDesc){
          Map newStyles = new HashMap();
          HSSFCellStyle style = wb.createCellStyle();
          HSSFDataFormat df = wb.createDataFormat();
          if (userStyleDesc != null) {
            Map.Entry entry = null;
            Iterator it = userStyleDesc.entrySet().iterator();
            while (it.hasNext()) {
              entry = (Map.Entry) it.next();
              GenericReportStyleView styleView = (GenericReportStyleView)entry.getValue();

              if (styleView != null){
                short align = IndexedAligns.valueOf(styleView.getAlignment());
                IndexedColors color = (styleView.getFillColor() != null) ? IndexedColors.getColor(styleView.getFillColor()) : null ;
                String dataCategory = styleView.getDataCategory();
                String dataFormat = styleView.getDataFormat();

                style = wb.createCellStyle();
                if (Utility.isSet(dataCategory)) {
                  if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.DATE)){
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
//                  style.setDataFormat(df.getFormat("MM/dd/yyyy"));
                  }
                  if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.TIME)){
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
                  }
                  if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.INTEGER)){
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                    style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
                  }
                  if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.FLOAT)){
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                    style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
                  }
                  if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.PERCENTAGE)){
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
                    style.setDataFormat(df.getFormat("0.00%"));
                    style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
                  }
                  if (dataCategory.equals(ReportingUtils.DATA_CATEGORY.ACCOUNTING)){
                    style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
//                    char strC = '\u0136';
//                    String strNew = String.valueOf(strC);
//                    String fmt = strNew +"##,##0.00";
                    String fmt = "##,##0.00";
                    style.setDataFormat(df.getFormat(fmt));
                    style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
                  }


                }
                style.setAlignment(align);
                style.setFont( (HSSFFont) fontCollection.get(entry.getKey()));
                if (Utility.isSet(dataFormat)){
                  style.setDataFormat(df.getFormat(dataFormat));
                }
                if (color != null) {
                  style.setFillForegroundColor(color.getIndex());
                  style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                }

                style.setWrapText(styleView.getWrap());

                newStyles.put(entry.getKey(), style);
              }
            }
          }
          return addDefaultStyles( wb, newStyles );
        }

        private static Map addDefaultStyles(HSSFWorkbook wb, Map pStyles){
          HSSFDataFormat df = wb.createDataFormat();
          HSSFCellStyle style = wb.createCellStyle();
          if (pStyles == null) {
            pStyles = new HashMap();
          }
          if (!pStyles.containsKey(ReportingUtils.PAGE_TITLE)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.PAGE_TITLE));
            pStyles.put(ReportingUtils.PAGE_TITLE, style);
          }

          if (!pStyles.containsKey(ReportingUtils.TABLE_HEADER)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_HEADER));
            pStyles.put(ReportingUtils.TABLE_HEADER, style);
          }

          if (!pStyles.containsKey(ReportingUtils.DATE_STYLE)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
            style.setDataFormat(df.getFormat("MM/dd/yyyy"));
            pStyles.put(ReportingUtils.DATE_STYLE, style);
          }

          if (!pStyles.containsKey(ReportingUtils.TIME_STYLE)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
            style.setDataFormat(df.getFormat("H:mm"));
            pStyles.put(ReportingUtils.TIME_STYLE, style);
          }

          if (!pStyles.containsKey(ReportingUtils.INTEGER_STYLE)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
            pStyles.put(ReportingUtils.INTEGER_STYLE, style);
          }

          if (!pStyles.containsKey(ReportingUtils.INTEGER_SEPARATOR_STYLE)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));

            /** @todo  Select currency from DB */

           // char strC = 0x5143;//'¥' ;//'\u0165'; ¥61,00 // '0X5143'
           // String strNew = String.valueOf(strC);
           // String fmt = strNew +"#,##0";
            String fmt = "#,##0";
            style.setDataFormat(df.getFormat(fmt));
            pStyles.put(ReportingUtils.INTEGER_SEPARATOR_STYLE, style);
          }

          if (!pStyles.containsKey(ReportingUtils.PERCENT_STYLE)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
            style.setDataFormat(df.getFormat("0.00%"));
            pStyles.put(ReportingUtils.PERCENT_STYLE, style);
          }
          if (!pStyles.containsKey(ReportingUtils.NEGATIVE_PERCENT_STYLE)){
            style = wb.createCellStyle();
            HSSFCellStyle positiveStyle = (HSSFCellStyle)pStyles.get(ReportingUtils.PERCENT_STYLE);
            style.setAlignment(positiveStyle.getAlignment());
            style.setDataFormat(positiveStyle.getDataFormat());
            style.setFont((HSSFFont) fontCollection.get(ReportingUtils.NEGATIVE_NUMBER));
            pStyles.put(ReportingUtils.NEGATIVE_PERCENT_STYLE, style);
          }

          if (!pStyles.containsKey(ReportingUtils.FLOAT_STYLE)) {
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style.setFont( (HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
            pStyles.put(ReportingUtils.FLOAT_STYLE, style);
          }

          if (!pStyles.containsKey(ReportingUtils.FLOAT_SEPARATOR_STYLE)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style.setFont((HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
            style.setDataFormat(df.getFormat("#,##0.00"));
            pStyles.put(ReportingUtils.FLOAT_SEPARATOR_STYLE, style);
          }

          if (!pStyles.containsKey(ReportingUtils.ACCOUNTING_STYLE)){
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            style.setFont((HSSFFont) fontCollection.get(ReportingUtils.TABLE_DATA));
            pStyles.put(ReportingUtils.ACCOUNTING_STYLE, style);
          }

          return pStyles;
        }

        private static Map createStyles(HSSFWorkbook wb){
          return addDefaultStyles( wb, null);
        }


	private static String reportColumnName(ReportRequestView pRepRequest,
			String pColName) {
		if (null == pColName)
			return "-";
		pColName = ReportingUtils.extractColumnName(pColName);

		if (null == pRepRequest)
		    //|| pRepRequest.mMessageRes == null)
			return pColName;

		String key = "";
		if (pColName.toLowerCase().startsWith("rowinfo_currency")) {
			key = ReportingUtils.makeColumnKey("Currency");
		} else {
			key = ReportingUtils.makeColumnKey(pColName);
		}

		//MessageResources resources = pRepRequest.mMessageRes;
		Locale locale = null;
		Object[] args = null;
		/*
		if (!(resources instanceof ClwMessageResourcesImpl)) {
			String s = resources.getMessage(key, args);
			if (null != s)
				return s;
			return pColName;
		}
		*/

		//int storeId = 0;
		/*
		CleanwiseUser appUser = pRepRequest.mUser;
		if (appUser != null && appUser.getUserStore() != null
				&& appUser.getUserStore().getBusEntity() != null) {
			storeId = appUser.getUserStore().getBusEntity().getBusEntityId();
			// get the locale set up for the user.
			locale = appUser.getPrefLocale();
		}

		ClwMessageResourcesImpl clwResources = (ClwMessageResourcesImpl) resources;
		String s = clwResources.getMessage(storeId, locale, key, args);
		if (null != s)
			return s;
        */
		return pColName;
	}

	/**
	 *Spools the GenericReportResultView to the specified output stream.
	 *
	 *@param GenericReportResultView
	 *@param OutputStream the output stream to write the Excep report to
	 */

	public static void writeReport(GenericReportResultViewVector repResults,
			OutputStream pOut, String pFormat) throws Exception {

		if ( !pFormat.startsWith(".")) pFormat = "." + pFormat;

		GenericReportResultView firstResult = (GenericReportResultView) repResults.get(0);
		if(repResults.size() > 0 && firstResult.getRawOutput() != null && firstResult.getRawOutput().length > 0){
			pOut.write(firstResult.getRawOutput());
			pOut.flush();
		}else if (".xls".equals(pFormat)|| ".xlsx".equals(pFormat)) {
			writeExcelReportMulti(repResults, pOut);
		} else if (".csv".equals(pFormat)) {
			writeCSVReport(repResults, pOut, null);
		} else {
			throw new Exception("Unsupported format (" + pFormat
					+ ") for report");
		}
	}

	public static void writeCSVReport(GenericReportResultViewVector repResv,
			OutputStream pOut, ReportRequestView pRepRequest) throws Exception {

		// Generate a CSV version of the data.
		GenericReportResultView repRes = (GenericReportResultView) repResv.get(0);
		int columnCount = repRes.getColumnCount();
		GenericReportColumnViewVector header = repRes.getHeader();

		//Make header CSV line
		String csvReportHeader = "";
		for (int ii = 0; ii < columnCount; ii++) {
			GenericReportColumnView column = (GenericReportColumnView) header
					.get(ii);
			String colName = reportColumnName(pRepRequest, column
					.getColumnName());
			if (csvReportHeader.length() > 0) csvReportHeader += ",";
			csvReportHeader += colName;
		}

		pOut.write(csvReportHeader.getBytes());
		ArrayList table = repRes.getTable();

		for (int ii = 0; ii < table.size(); ii++) {
			List row = (List) table.get(ii);
			String csvReportRow = "\n";
			for (int jj = 0; jj < row.size(); jj++) {
				Object obj = row.get(jj);
				if (csvReportRow.length() > 1)
					csvReportRow += ",";
				if(obj != null && obj instanceof String){
                    csvReportRow += stripSpecialChars((String) obj);
				}

			}
			pOut.write(csvReportRow.getBytes());
		}
	}

	private static String stripSpecialChars(String pInString) {

		if ( null == pInString ) return pInString;
		String res = pInString;
		res = res.replaceAll(",", ".");
		res = res.replaceAll("\"", " ");
		res = res.replaceAll("\'", " ");
		return res;
	}

	public static void writeExcelReportMulti(
			GenericReportResultViewVector repResults, OutputStream pOut)
			throws Exception {
		writeExcelReportMulti(repResults, pOut, null, ".xls");
	}
        public static void writeExcelReportMulti(
                        GenericReportResultViewVector repResults, OutputStream pOut,
                        ReportRequestView pRepRequest ) throws Exception {
               writeExcelReportMulti(repResults, pOut, pRepRequest, ".xls");
        }
	public static void writeExcelReportMulti(
			GenericReportResultViewVector repResults, OutputStream pOut,
			ReportRequestView pRepRequest, String xlsType ) throws Exception {

               GenericReportResultView repRes = (repResults != null)? (GenericReportResultView) repResults.get(0) : null;
               xlsType = (repRes != null) ? repRes.getReportFormat() : null;

                HSSFWorkbook workbook = null;
                if(Utility.isSet(xlsType) && xlsType.equals(".xlsx")) {
//                  workbook = new XSSFWorkbook();
                } else {
                  workbook = new HSSFWorkbook();
                }

		//format
                if (repResults != null){
                  for (int ii = repResults.size()-1; ii >=0 ; ii--) {
                    repRes =  (GenericReportResultView) repResults.get(ii);
                    String name = repRes.getName();
                    if (name == null || name.trim().length() == 0) {
                      name = "Sheet" + ii + ".";
                    }
                    if(name.length() > 29){
                    	name = name.substring(0,29);
                    }

                    writeExcelReport(repRes, name, workbook, pRepRequest);

                  }
                }

                workbook.write(pOut);
	}
     //------------------------------------------------------------------------
            private static void setTitle(HSSFSheet pSheet, int pCol, int pRow,
                            String pColName, HSSFCellStyle pTitleStyle) throws Exception {
                    if (pCol < 0) {
                      return;
                    }
                    // pivot columns into rows for report title!
                    int iCol = 0;
                    int iRow = pRow + pCol;
                    HSSFRow row = pSheet.createRow(iRow);
                    HSSFCell cell = row.createCell(iCol);
                    cell.setCellValue(pColName);
                    cell.setCellStyle(pTitleStyle);

            }

    //------------------------------------------------------------------------

       private static void setColWidth(HSSFSheet pSheet, int pCol, int pColWidth, String pXlsColWidth, double pWidthFactor, int pAllColumnsWidth, int pColumnCount) {
          int defWidth = pSheet.getDefaultColumnWidth();
          defWidth = (defWidth > 0) ? defWidth : 8;

//         pxWidth = (int) (tableWidthInt*((double)cWidthInt[ii])/(double)allColumnsWidth);
//          int  pxWidth = (int)((defWidth * pColumnCount) * ((double)pColWidth/(double)pAllColumnsWidth));

          double nWidthFactor = DEFAULT_WIDTH_FACTOR;
          if (pWidthFactor > 0 ){
            nWidthFactor = pWidthFactor;
          }

          String width ="";
          if (Utility.isSet(pXlsColWidth)){
            width = pXlsColWidth;
          } else if (pColWidth > 0 && nWidthFactor > 0){
            int  pxWidth = (int)((defWidth * pColumnCount) * ((double)pColWidth/(double)pAllColumnsWidth));
            width = Double.toString(pxWidth * nWidthFactor);
          }

          if (Utility.isSet(width)){
            BigDecimal bd = new BigDecimal(width);
            bd = bd.setScale(0,BigDecimal.ROUND_HALF_UP);
            int w = bd.intValue();
            pSheet.setColumnWidth(pCol, 256 * w);
          }
        }

        private static void setHeader(HSSFSheet pSheet, int pCol,
                    String pColName, HSSFCellStyle pHeaderStyle) throws Exception {
                setHeader( pSheet, pCol, 0, pColName, pHeaderStyle) ;
        }

	private static void setHeader(HSSFSheet pSheet, int pCol, int pRow,
                        String pColName, HSSFCellStyle pHeaderStyle) throws Exception {
		if (pCol < 0) {
		   return;
                }
                HSSFRow row = pSheet.getRow(pRow);
                HSSFCell cell = row.createCell(pCol);
                cell.setCellValue(pColName);
 //               pHeaderStyle.setWrapText(true);
                cell.setCellStyle(pHeaderStyle);

	}

	//------------------------------------------------------------------------
	private static void setCell(HSSFSheet pSheet, int pCol, HSSFRow pRow,
                        Object pValue, HSSFCellStyle pStyle) throws Exception {
		if (pCol < 0) {
                  return;
                }
                HSSFCell cell = pRow.createCell(pCol);
                HSSFCellStyle style = pStyle;
		if (pValue instanceof Date && pValue != null) {
			java.util.Date dateVal = (java.util.Date) pValue;
                        cell.setCellValue(dateVal);
		} else if (pValue instanceof BigDecimal && pValue != null) {
			BigDecimal amt = (BigDecimal) pValue;
                        cell.setCellValue(amt.doubleValue());
                       style = changeStyle(amt.signum(), pStyle); // if NEGATIVE_NUMBER style
		} else if (pValue instanceof Integer && pValue != null) {
			Integer amt = (Integer) pValue;
                        cell.setCellValue(amt.intValue());
                        style = changeStyle(Integer.signum(amt.intValue()), pStyle);  // if NEGATIVE_NUMBER style
		} else {
                        cell.setCellValue(String.valueOf(pValue));
 		}
               if (style !=null) {
                 cell.setCellStyle(style);
               }

	}

        private static HSSFCellStyle changeStyle(int pSignum, HSSFCellStyle pStyle){
          HSSFCellStyle newStyle = pStyle;
          if (pStyle !=null && pStyle.equals((HSSFCellStyle)styleCollection.get(ReportingUtils.NEGATIVE_PERCENT_STYLE))){
            if (pSignum >= 0){
              newStyle = (HSSFCellStyle)styleCollection.get(ReportingUtils.PERCENT_STYLE);
            }
          }
          return newStyle;
        }

        private static void setCellNoNull(HSSFSheet pSheet, int pCol, HSSFRow pRow,
                        Object pValue, HSSFCellStyle pStyle) throws Exception {
                if (pValue == null) {
                  return;
                }
                setCell(pSheet, pCol, pRow, pValue, pStyle);
        }
	/////////////////////////////
	/*
    public static void writePDFReportMulti(
            GenericReportResultViewVector repResults, OutputStream pOut,
            ReportRequest pRepRequest, HttpServletRequest request)
            throws Exception {
        if (repResults == null || repResults.size() == 0) {
            return;
        }
        GenericReportResultView firstReport = (GenericReportResultView) repResults.get(0);
        Rectangle r = PdfReportWritter.getPaperSize(firstReport.getPaperSize(),
                RefCodeNames.REPORT_PAPER_ORIENTATION.LANDSCAPE
                        .equals(firstReport.getPaperOrientation()));
        Document document = new Document(r);
        PdfWriter writer = PdfWriter.getInstance(document, pOut);
        document.open();
 //       for (int ii = 0; ii < repResults.size(); ii++) {
        for (int ii = repResults.size()-1; ii >=0 ; ii--) {
            GenericReportResultView repRes = (GenericReportResultView) repResults
                    .get(ii);
            String name = repRes.getName();
            if (name == null || name.trim().length() == 0) {
                name = "REPORT " + (ii + 1) + ".";
            }
            if (ii < (repResults.size()-1)) {
            	document.newPage();
            }
            PdfReportWritter.writePdfReport(repRes, name, document,
                    pRepRequest, request, writer);
        }
        document.close();
    }
	*/
///////////////////////////////
    private final  static class IndexedAligns  {

      public static short valueOf(String name) {
        if (Utility.isSet(name)){
          if (name.equals(ReportingUtils.ALIGN.CENTER)){
            return HSSFCellStyle.ALIGN_CENTER;
          } else if (name.equals(ReportingUtils.ALIGN.LEFT)){
            return HSSFCellStyle.ALIGN_LEFT;
          } else if (name.equals(ReportingUtils.ALIGN.RIGHT)){
            return HSSFCellStyle.ALIGN_RIGHT;
          } else if (name.equals(ReportingUtils.ALIGN.GENERAL)){
            return HSSFCellStyle.ALIGN_GENERAL;
          } else if (name.equals(ReportingUtils.ALIGN.JUSTIFY)){
            return HSSFCellStyle.ALIGN_JUSTIFY;
          }
        }
        return HSSFCellStyle.ALIGN_RIGHT;
      }
    }
    public enum IndexedColors  {
   BLACK ("BLACK", HSSFColor.BLACK.index),
   WHITE ("WHITE", HSSFColor.WHITE.index),
   RED("RED", HSSFColor.RED.index),
   BRIGHT_GREEN("BRIGHT_GREEN", HSSFColor.BRIGHT_GREEN.index),
   BLUE("BLUE", HSSFColor.BLUE.index),
   YELLOW("YELLOW", HSSFColor.YELLOW.index),
   PINK("PINK", HSSFColor.PINK.index),
   TURQUOISE("TURQUOISE", HSSFColor.TURQUOISE.index),
   DARK_RED("DARK_RED", HSSFColor.DARK_RED.index),
   GREEN("GREEN", HSSFColor.GREEN.index),
   DARK_BLUE("DARK_BLUE", HSSFColor.DARK_BLUE.index),
   DARK_YELLOW("DARK_YELLOW", HSSFColor.DARK_YELLOW.index),
   VIOLET("VIOLET", HSSFColor.VIOLET.index),
   TEAL("TEAL", HSSFColor.TEAL.index),
   GREY_25_PERCENT("GREY_25_PERCENT", HSSFColor.GREY_25_PERCENT.index),
   GREY_50_PERCENT("GREY_50_PERCENT", HSSFColor.GREY_50_PERCENT.index),
    CORNFLOWER_BLUE("CORNFLOWER_BLUE", HSSFColor.CORNFLOWER_BLUE.index),
    MAROON("MAROON", HSSFColor.MAROON.index),
    LEMON_CHIFFON("LEMON_CHIFFON", HSSFColor.LEMON_CHIFFON.index),
    ORCHID("ORCHID", HSSFColor.ORCHID.index),
    CORAL("CORAL", HSSFColor.CORAL.index),
    ROYAL_BLUE("ROYAL_BLUE", HSSFColor.ROYAL_BLUE.index),
    LIGHT_CORNFLOWER_BLUE("LIGHT_CORNFLOWER_BLUE", HSSFColor.LIGHT_CORNFLOWER_BLUE.index),
    SKY_BLUE("SKY_BLUE", HSSFColor.SKY_BLUE.index),
    LIGHT_TURQUOISE("LIGHT_TURQUOISE", HSSFColor.LIGHT_TURQUOISE.index),
    LIGHT_GREEN("LIGHT_GREEN", HSSFColor.LIGHT_GREEN.index),
    LIGHT_YELLOW("LIGHT_YELLOW", HSSFColor.LIGHT_YELLOW.index),
    PALE_BLUE("PALE_BLUE", HSSFColor.PALE_BLUE.index),
    ROSE("ROSE", HSSFColor.ROSE.index),
    LAVENDER("LAVENDER", HSSFColor.LAVENDER.index),
    TAN("TAN", HSSFColor.TAN.index),
    LIGHT_BLUE("LIGHT_BLUE", HSSFColor.LIGHT_BLUE.index),
    AQUA("AQUA", HSSFColor.AQUA.index),
    LIME("LIME", HSSFColor.LIME.index),
    GOLD("GOLD", HSSFColor.GOLD.index),
    LIGHT_ORANGE("LIGHT_ORANGE", HSSFColor.LIGHT_ORANGE.index),
    ORANGE("ORANGE", HSSFColor.ORANGE.index),
    BLUE_GREY("BLUE_GREY", HSSFColor.BLUE_GREY.index),
    GREY_40_PERCENT("GREY_40_PERCENT", HSSFColor.GREY_40_PERCENT.index),
    DARK_TEAL("DARK_TEAL", HSSFColor.DARK_TEAL.index),
    SEA_GREEN("SEA_GREEN", HSSFColor.SEA_GREEN.index),
    DARK_GREEN("DARK_GREEN", HSSFColor.DARK_GREEN.index),
    OLIVE_GREEN("OLIVE_GREEN", HSSFColor.OLIVE_GREEN.index),
    BROWN("BROWN", HSSFColor.BROWN.index),
    PLUM("PLUM", HSSFColor.PLUM.index),
    INDIGO("INDIGO", HSSFColor.INDIGO.index),
    GREY_80_PERCENT("GREY_80_PERCENT", HSSFColor.GREY_80_PERCENT.index),
    AUTOMATIC("AUTOMATIC", HSSFColor.AUTOMATIC.index);

  String value;
  short index;
  // Constructors
  private IndexedColors(String pName, short pIndex) {
    value = pName;
    index = pIndex;
  }
  public String getValue(){
    return value;
  }
  public short getIndex(){
    return index;
  }

  public static IndexedColors getColor(String pName) {
    if (Utility.isSet(pName)){
      for (IndexedColors color : IndexedColors.values()) {
        if (pName.equals(color.getValue())) {
          return color;
          //
        }
      }
    }
    return IndexedColors.AUTOMATIC;
  }
}

}
