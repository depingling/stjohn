package com.cleanwise.service.apps.dataexchange;



import org.apache.log4j.Logger;

import org.apache.poi.hssf.eventmodel.*;

import org.apache.poi.hssf.eventusermodel.*;

import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.hssf.record.*;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;



import java.io.*;

import java.util.ArrayList;

import java.util.List;

import java.util.Iterator;
import java.math.BigDecimal;


/**

 * Deals with reading an excel file.  Classes that wish to read in an entire file should instantiate

 * this class by using the constructor with the StreamedInboundTransaction and InputStream arguments

 * and can thus listen as the the file that is read.  If you wish only a limited subset of cells use

 * empty constructor and the getCell method.

 */

public class ExcelReader implements HSSFListener {
	protected Logger log = Logger.getLogger(this.getClass());
    private SSTRecord sstrec;

    private boolean mDebug = false;

    private ArrayList mTwoDArray = new ArrayList();

    private int currentRowNum = 0;

    private int processedRows = 0;

    private InputStream mExcelStream;

    private boolean fileRead = false;

    private int currRowIdx = -100;

    private List currRow = null;



    /**

     *Reads in a given cell from an Excel stream.

     */

    public Object getCell(int rowIndex,int columnIndex) throws IOException{

        if(fileRead){

            //use read in version

            if(mTwoDArray.size() >= rowIndex){

                List row = (List) mTwoDArray.get(rowIndex);

                if(row.size() >= columnIndex){

                    return row.get(columnIndex);

                }

            }

            return null;

        }



        POIFSFileSystem fs = new POIFSFileSystem(mExcelStream);

        HSSFWorkbook wb = new HSSFWorkbook(fs);

        HSSFSheet sheet = wb.getSheetAt(0);

        HSSFRow row = sheet.getRow(rowIndex);

        HSSFCell cell = row.getCell((short)columnIndex);

        Object value = null;

        switch (cell.getCellType()){

            case HSSFCell.CELL_TYPE_BLANK:

                value = null;

                break;

            case HSSFCell.CELL_TYPE_BOOLEAN:

                value = new Boolean(cell.getBooleanCellValue());

                break;

            case HSSFCell.CELL_TYPE_ERROR:

                value = null;

                break;

            case HSSFCell.CELL_TYPE_FORMULA:

                value = cell.getDateCellValue();

                break;

            case HSSFCell.CELL_TYPE_NUMERIC:

            	if(HSSFDateUtil.isCellDateFormatted(cell)){

            		value = cell.getDateCellValue();

            	}else{

            		value = new Double(cell.getNumericCellValue());

            	}

                break;

            case HSSFCell.CELL_TYPE_STRING:

                value = cell.getStringCellValue();

                break;

            default:

                value = null;

                break;

        }
        return value;

    }







    //safely adds the element to our 2d list at the specified location

    //does some caching of row lookups in the List so as to avoid many get(x) calls

    private void addAt(Object value, int rowIndex, int columnIndex){

        if(currRowIdx != rowIndex){

            //if the current row being requested is the last one requested just use our

            //existing reference to it.

            while (mTwoDArray.size() <= rowIndex){

                mTwoDArray.add(new ArrayList());

            }

            currRow = (ArrayList) mTwoDArray.get(rowIndex);

        }

        if(currRow.size() < columnIndex){

            while (currRow.size() < columnIndex){

                currRow.add(null);

            }

            currRow.add(value);

        }else{

            currRow.add(columnIndex, value);

        }

    }



    /**

     * This method listens for incoming records and handles them as required.

     * @param record    The record that was found while reading.

     */

    public void processRecord(Record record) {

        switch (record.getSid()) {



            case NumberRecord.sid:

                NumberRecord numrec = (NumberRecord) record;


                if(HSSFDateUtil.isValidExcelDate(numrec.getValue())){

                	if(mDebug){

                		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");

                        log.debug("Date Cell found with value " + numrec.getValue()

                        + "("+sdf.format(HSSFDateUtil.getJavaDate(numrec.getValue()))+")"

                        + " at row " + numrec.getRow() + " and column " + numrec.getColumn());

                    }

            		//addAt(HSSFDateUtil.getJavaDate(numrec.getValue()),numrec.getRow(), numrec.getColumn());

                    // try to check if integer
                    BigDecimal b = new BigDecimal(numrec.getValue());
                    int ib = b.intValue();
                    if ((b.multiply(new BigDecimal(100)).compareTo(new BigDecimal(ib * 100)) == 0)) {
                        addAt(new Integer(ib),numrec.getRow(), numrec.getColumn());
                    } else {
                        addAt(b,numrec.getRow(), numrec.getColumn());
                    }                           

                }else{

                	if(mDebug){

                		log.debug("Number Cell found with value " + numrec.getValue()

                        + " at row " + numrec.getRow() + " and column " + numrec.getColumn());

                    }

                    // try to check if integer
                    BigDecimal b = new BigDecimal(numrec.getValue());
                    int ib = b.intValue();
                    if ((b.multiply(new BigDecimal(100)).compareTo(new BigDecimal(ib * 100)) == 0)) {
                        addAt(new Integer(ib),numrec.getRow(), numrec.getColumn());
                    } else {
                        addAt(new Double(numrec.getValue()),numrec.getRow(), numrec.getColumn());
                    }

                }

                break;

                // SSTRecords store a array of unique strings used in Excel.

            case SSTRecord.sid:

                sstrec = (SSTRecord) record;

                break;

            case LabelSSTRecord.sid:

                LabelSSTRecord lrec = (LabelSSTRecord) record;

                if(mDebug){

                	log.debug("String cell found with value "

                    + sstrec.getString(lrec.getSSTIndex())

                    + " at row " + lrec.getRow() + " and column " + lrec.getColumn());

                }

                addAt(sstrec.getString(lrec.getSSTIndex()),lrec.getRow(), lrec.getColumn());

                break;

            case BoolErrRecord.sid:

                BoolErrRecord brec = (BoolErrRecord) record;

                if(mDebug){

                    log.debug("Boolean cell found with value "

                    + brec.getBooleanValue()

                    + " at row " + brec.getRow() + " and column " + brec.getColumn());

                }

                addAt(new Boolean(brec.getBooleanValue()),brec.getRow(), brec.getColumn());

                break;


        }

    }



    /**

     *Reads in the excel file and calls the listners parseLine(List) method for each row.  It needs

     *to actually read in the entire file as there is no gaurentee as to the order of an Excel file,

     *i.e. a row may be physically at the top of the file, but is actually in the middle when the file

     *is displayed on screen.  The listner is gurenteed to have it's parseline method called for rows

     *in order (1,2,3,4 etc).  Subsequent calls to this method will not reflect changes in the underlying

     *stream.  In fact after processing this the stream is closed.

     *

     *@param StreamedInboundTransaction
     * @throws Exception 
     */

    public void processFile(StreamedInboundTransaction pListener) throws Exception{

        if(!fileRead){

            // create a new org.apache.poi.poifs.filesystem.Filesystem

            POIFSFileSystem poifs = new POIFSFileSystem(mExcelStream);

            // get the Workbook (excel part) stream in a InputStream

            InputStream din = poifs.createDocumentInputStream("Workbook");

            // construct out HSSFRequest object

            HSSFRequest req = new HSSFRequest();

            // lazy listen for ALL records with the listener shown above

            req.addListenerForAllRecords(this);

            // create our event factory

            HSSFEventFactory factory = new HSSFEventFactory();

            // process our events based on the document input stream

            factory.processEvents(req, din);

            // once all the events are processed close our file input stream

            mExcelStream.close();

            // and our document input stream (don't want to leak these!)

            din.close();



            //log.info("done reading file.  Processed " + processedRows + " Rows");
            log.info("done reading file.  Processed " + mTwoDArray.size() + " Rows");

            fileRead = true;

        }

        if(pListener != null){

            Iterator it = mTwoDArray.iterator();

            while(it.hasNext()){

                pListener.parseLine((List) it.next());

            }

            log.info("done calling listner");

        }



    }





    /**

     *Using this contructor will cause the parseLine method to be called once for each record in the

     *excel document.

     */

    public ExcelReader(InputStream pInput) throws IOException {

        //set up the stream

        //  mExcelStream = pInput;
        mExcelStream = POIFSFileSystem.createNonClosingInputStream(pInput) ;
    }

}

