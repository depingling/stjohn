/*
 * ReportWritter.java
 *
 * Created on March 4, 2003, 11:30 AM
 */

package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.value.GenericReportColumnView;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.value.GenericReportResultViewVector;
import jxl.*;
import jxl.write.*;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Title:        XlsBuilder
 * Description:  Super class to all of the xls classes.Contains some of the utility methods, and variables
 *               that all of the xls classes have in common.
 * Purpose:      Super class to all of the xls classes.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         24.08.2007
 * Time:         17:14:33
 *
 * @author       Alexander Chickin, TrinitySoft, Inc.
 *
 * Note: Main part it the copy from com.cleanwise.view.utils.pdf.ReportWritter
 * Difference between achievement of classes it the scope level into the system packages.
 */
public class XlsBuilder {


    public  void writeExcelReport(GenericReportResultView repRes,
                                  OutputStream pOut) throws Exception {

        int columnCount = repRes.getColumnCount();
        GenericReportColumnViewVector header = repRes.getHeader();
        //prepare formats
        DateFormat customDateFormat = new DateFormat("MM/dd/yyyy");
        WritableCellFormat dateFormat = new WritableCellFormat(customDateFormat);
        DateFormat customTimeFormat = new DateFormat("H:mm");
        WritableCellFormat timeFormat = new WritableCellFormat(customTimeFormat);
        WritableCellFormat accountingFormat = new WritableCellFormat(NumberFormats.FLOAT);
        WritableCellFormat percentFormat = new WritableCellFormat(NumberFormats.PERCENT_FLOAT);
        WritableCellFormat floatFormat = new WritableCellFormat(NumberFormats.FLOAT);
        WritableCellFormat integerFormat = new WritableCellFormat(NumberFormats.INTEGER);

        //Determine XLS column type
        WritableCellFormat[] columnXlsFormat = new WritableCellFormat[columnCount];
        for (int ii = 0; ii < columnCount; ii++) {
            GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
            String colName = column.getColumnName();
            String colClass = column.getColumnClass();
            int colScale = column.getColumnScale();
            if (colClass.equals("java.math.BigDecimal")) {
                if (ReportingUtils.isColumnForMoney(colName)) {
                    colName = ReportingUtils.extractColumnName(colName);
                    columnXlsFormat[ii] = accountingFormat;
                } else if (ReportingUtils.isColumnForPercent(colName)) {
                    colName = ReportingUtils.extractColumnName(colName);
                    columnXlsFormat[ii] = percentFormat;
                } else if (colScale == 0) {
                    columnXlsFormat[ii] = integerFormat;
                } else {
                    columnXlsFormat[ii] = floatFormat;
                }
            } else if (colClass.equals("java.sql.Timestamp")) {
                if (ReportingUtils.isColumnForTime(colName)) {
                    columnXlsFormat[ii] = timeFormat;
                } else {
                    columnXlsFormat[ii] = dateFormat;
                }
            } else {
                columnXlsFormat[ii] = null;
            }
        }

        WritableWorkbook workbook = Workbook.createWorkbook(pOut);
        //format
        WritableFont times10font = new WritableFont(WritableFont.TIMES, 10,WritableFont.BOLD, false);
        WritableCellFormat headerFormat = new WritableCellFormat(times10font);
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
            WritableSheet sheet = workbook.createSheet(sheetName, curPage);
            //Make header
            for (int ii = 0; ii < columnCount; ii++) {
                GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
                String colName = column.getColumnName();
                colName = ReportingUtils.extractColumnName(colName);
                String tColKey = "";
                if (colName.toLowerCase().startsWith("rowInfo_currency")) {
                    tColKey = ReportingUtils.makeColumnKey("Currency");
                } else {
                    tColKey = ReportingUtils.makeColumnKey(colName);
                }
                setHeader(sheet, ii, colName, headerFormat);

            }

            int startRow = curPage * pageSize;
            int endRowNext = startRow + pageSize;
            if (endRowNext > table.size())
                endRowNext = table.size();
            for (int ii = endRowNext - 1; ii >= startRow; ii--) {
                List row = (List) table.get(ii);
                for (int jj = 0; jj < row.size(); jj++) {
                    GenericReportColumnView col = (GenericReportColumnView) header.get(jj);
                    Object obj = row.get(jj);
                    String thisCurrencyCode = null;
                    if (obj instanceof java.lang.String) {
                        String t = (java.lang.String) obj;
                        if (t.startsWith("rowInfo_currency")) {
                            thisCurrencyCode = t.substring(19);
                        }
                    }

                    if (null != thisCurrencyCode) {
                        setCellNoNull(sheet, jj, startRow + ii + 1,thisCurrencyCode, null);
                    } else {
                        setCellNoNull(sheet, jj, startRow + ii + 1, obj,columnXlsFormat[jj]);
                    }
                }

            }

        }

        workbook.write();
        workbook.close();
    }

    public  void writeExcelReport(GenericReportResultView repRes,
                                  String pSheetName,
                                  WritableWorkbook pWorkbook) throws Exception {
        int columnCount = repRes.getColumnCount();
        GenericReportColumnViewVector header = repRes.getHeader();
        //prepare formats
        String tDateFormat = "MM/dd/yyyy";

        DateFormat customDateFormat         = new DateFormat(tDateFormat);
        WritableCellFormat dateFormat       = new WritableCellFormat(customDateFormat);
        DateFormat customTimeFormat         = new DateFormat("H:mm");
        WritableCellFormat timeFormat       = new WritableCellFormat(customTimeFormat);
        WritableCellFormat accountingFormat = new WritableCellFormat(NumberFormats.FLOAT);
        WritableCellFormat percentFormat    = new WritableCellFormat(NumberFormats.PERCENT_FLOAT);
        WritableCellFormat floatFormat      = new WritableCellFormat(NumberFormats.FLOAT);
        WritableCellFormat integerFormat    = new WritableCellFormat(NumberFormats.INTEGER);
        WritableFont times10font            = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
        WritableCellFormat headerFormat     = new WritableCellFormat(times10font);
        //Determin XLS column type
        WritableCellFormat[] columnXlsFormat = new WritableCellFormat[columnCount];
        for (int ii = 0; ii < columnCount; ii++) {
            GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
            String colName = column.getColumnName();
            String colClass = column.getColumnClass();
            int colScale = column.getColumnScale();
            if (colClass.equals("java.math.BigDecimal")) {
                if (ReportingUtils.isColumnForMoney(colName)) {
                    colName = ReportingUtils.extractColumnName(colName);
                    columnXlsFormat[ii] = accountingFormat;
                } else if (ReportingUtils.isColumnForPercent(colName)) {
                    colName = ReportingUtils.extractColumnName(colName);
                    columnXlsFormat[ii] = percentFormat;
                } else if (colScale == 0) {
                    columnXlsFormat[ii] = integerFormat;
                } else {
                    columnXlsFormat[ii] = floatFormat;
                }
            } else if (colClass.equals("java.sql.Timestamp")) {
                if (ReportingUtils.isColumnForTime(colName)) {
                    columnXlsFormat[ii] = timeFormat;
                } else {
                    columnXlsFormat[ii] = dateFormat;
                }
            } else {
                columnXlsFormat[ii] = null;
            }
        }

        //format
        int pageSize = 65000;
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
            if (curPage > 0)
                name = pSheetName + "." + curPage;
            WritableSheet sheet = pWorkbook.createSheet(name, curPage);
            //Make header
            for (int ii = 0; ii < columnCount; ii++) {
                GenericReportColumnView column = (GenericReportColumnView) header
                        .get(ii);
                String colName = reportColumnName(column.getColumnName());
                setHeader(sheet, ii, colName, headerFormat);
            }

            int startRow = curPage * pageSize;
            int endRowNext = startRow + pageSize;
            if (table != null) {
                if (endRowNext > table.size())
                    endRowNext = table.size();
            } else {
                endRowNext = 0;
            }
            for (int ii = endRowNext - 1; ii >= startRow; ii--) {
                List row = (List) table.get(ii);
                for (int jj = 0; jj < row.size() && jj < columnXlsFormat.length; jj++) {
                    GenericReportColumnView col = (GenericReportColumnView) header
                            .get(jj);
                    Object obj = row.get(jj);
                    String thisCurrencyCode = null;
                    if (obj instanceof java.lang.String) {
                        String t = (java.lang.String) obj;
                        if (t.startsWith("rowInfo_currencyCd=")) {
                            thisCurrencyCode = t.substring(19);
                        }
                    }

                    if (null != thisCurrencyCode) {
                        setCellNoNull(sheet, jj, startRow + ii + 1,
                                thisCurrencyCode, null);
                    } else {
                        setCellNoNull(sheet, jj, startRow + ii + 1, obj,
                                columnXlsFormat[jj]);
                    }
                }
            }
        }
    }

    private  String reportColumnName(String pColName) {
        if (null == pColName)    {
            return "-";
        }
        return ReportingUtils.extractColumnName(pColName);
    }

    public  void writeReport(GenericReportResultViewVector repResults,
                             OutputStream pOut,
                             String pFormat) throws Exception {

        if (!pFormat.startsWith(".")) pFormat = "." + pFormat;

        if (".xls".equals(pFormat)) {
            writeExcelReportMulti(repResults, pOut);
        } else if (".csv".equals(pFormat)) {
            writeCSVReport(repResults, pOut);
        } else {
            throw new Exception("Unsupported format (" + pFormat
                    + ") for report");
        }
    }

    public void writeCSVReport(GenericReportResultViewVector repResv,
                               OutputStream pOut) throws Exception {

        // Generate a CSV version of the data.
        GenericReportResultView repRes = (GenericReportResultView) repResv.get(0);
        int columnCount = repRes.getColumnCount();
        GenericReportColumnViewVector header = repRes.getHeader();

        //Make header CSV line
        String csvReportHeader = "";
        for (int ii = 0; ii < columnCount; ii++) {
            GenericReportColumnView column = (GenericReportColumnView) header.get(ii);
            String colName = reportColumnName(column.getColumnName());
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
                csvReportRow += stripSpecialChars((String) obj);

            }
            pOut.write(csvReportRow.getBytes());
        }
    }

    private String stripSpecialChars(String pInString) {

        if (null == pInString) return pInString;
        String res = pInString;
        res = res.replaceAll(",", ".");
        res = res.replaceAll("\"", " ");
        res = res.replaceAll("\'", " ");
        return res;
    }

    public  void writeExcelReportMulti(GenericReportResultViewVector repResults,
                                       OutputStream pOut) throws Exception {
        WritableWorkbook workbook = Workbook.createWorkbook(pOut);
        //format
        for (int ii = 0; ii < repResults.size(); ii++) {
            GenericReportResultView repRes = (GenericReportResultView) repResults .get(ii);
            String name = repRes.getName();
            if (name == null || name.trim().length() == 0) {
                name = "Sheet" + ii + ".";
            }
            writeExcelReport(repRes, name, workbook);
        }
        workbook.write();
        workbook.close();
    }

    private void setHeader(WritableSheet pSheet,
                           int pCol,
                           String pColName,
                           WritableCellFormat pHeaderFormat) throws Exception {
        if (pCol < 0)
            return;
        Label label;
        label = new Label(pCol, 0, pColName, pHeaderFormat);
        pSheet.addCell(label);
    }

    private  void setCell(WritableSheet pSheet,
                          int pCol,
                          int pRow,
                          Object pValue,
                          WritableCellFormat pFormat) throws Exception {

        if (pCol < 0)  return;

        jxl.write.Label label;
        jxl.write.Number number;
        jxl.write.DateTime date;

        if (pValue instanceof Date) {
            java.util.Date dateVal = (java.util.Date) pValue;
            if (pFormat != null) {
                date = new DateTime(pCol, pRow, dateVal, pFormat);
            } else {
                date = new DateTime(pCol, pRow, dateVal);
            }
            pSheet.addCell(date);
        }else if (pValue instanceof BigDecimal) {
            BigDecimal amt = (BigDecimal) pValue;
            if (pFormat != null) {
                number = new jxl.write.Number(pCol, pRow, amt.doubleValue(),
                        pFormat);
            } else {
                number = new jxl.write.Number(pCol, pRow, amt.doubleValue());
            }
            pSheet.addCell(number);
        } else if (pValue instanceof Integer) {
            Integer amt = (Integer) pValue;
            if (pFormat != null) {
                number = new jxl.write.Number(pCol, pRow, amt.doubleValue(),pFormat);
            } else {
                number = new jxl.write.Number(pCol, pRow, amt.doubleValue());
            }
            pSheet.addCell(number);
        } else {
            label = new Label(pCol, pRow, String.valueOf(pValue));
            pSheet.addCell(label);
        }

    }

    private void setCellNoNull(WritableSheet pSheet,
                               int pCol,
                               int pRow,
                               Object pValue,
                               WritableCellFormat pFormat) throws Exception {
        if (pValue == null){
            return;
        }
        setCell(pSheet, pCol, pRow, pValue, pFormat);
    }

}
