package com.cleanwise.service.api.process.operations;

/**
 * Title:        TableColumnInfo
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendwise, Inc.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.rmi.RemoteException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.AlphaComposite;
import javax.naming.NamingException;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIServiceAccessException;
import com.cleanwise.service.apps.GetFile;
import com.cleanwise.service.api.util.TableColumnInfo;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.DataUploaderHelper;
import com.cleanwise.service.api.session.Process;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.EventProcessView;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.value.EventPropertyData;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventPropertyDataVector;


public class FtpFileToDbTableLoader {

    public static final String USER_NAME              = "FtpFileToDbTableLoader";

    public static final int    DEFAULT_FTP_PORT       = 21;

    public static final String PARAM_FROM_HOST        = "fromhost";
    public static final String PARAM_FILE_NAME        = "filename";
    public static final String PARAM_PORT             = "port";
    public static final String PARAM_USER             = "username";
    public static final String PARAM_PASSWORD         = "password";
    public static final String PARAM_FROM_DIR         = "fromdir";
    public static final String PARAM_TRANSFER_TYPE    = "transfer_type";
    public static final String PARAM_REMOVE_FILE      = "removefile";
    public static final String PARAM_PATTERN          = "pattern";
    public static final String PARAM_TABLE            = "table";

    private static final Logger log = Logger.getLogger(FtpFileToDbTableLoader.class);

    private String _fromhost;
    private String _port;
    private String _user;
    private String _password;
    private String _fromdir;
    private String _transfer_type;
    private String _removefile;
    private String _pattern;
    private String _table;
    private String _database_schema;
    private String _field_qty;
    private String _field_separator;
    private Integer _pProcessActiveId;
    private String _date_pattern;
    private String _storeId;
    private int _parentEventId = 0;
    private SimpleDateFormat _dateFormat;
    private ByteArrayInputStream _loadedDataStream;

    private Integer eventId;

    class FileFormatError {
        private int _lineNumber;
        private String _message;
        public FileFormatError(int lineNumber, String message) {
            _lineNumber = lineNumber;
            _message = message;
        }
        public FileFormatError() {
            this(0, "");
        }
        public int getLineNumber() {
            return _lineNumber;
        }
        public void setLineNumber(int lineNumber) {
            _lineNumber = lineNumber;
        }
        public String getMessage() {
            return _message;
        }
        public void setMessage(String message) {
            _message = message;
        }
    }

    class FileData {
        private String _fileName;
        private ByteArrayInputStream _dataStream = null;
        public FileData(String fileName, ByteArrayInputStream dataStream) {
            _fileName = fileName;
            _dataStream = dataStream;
        }
        public FileData() {
            this("", null);
        }
        public ByteArrayInputStream getDataStream() {
            return _dataStream;
        }
        public void setDataStream(ByteArrayInputStream dataStream) {
            _dataStream = dataStream;
        }
        public String getFileName() {
            return _fileName;
        }
        public void setFileName(String fileName) {
            _fileName = fileName;
        }
    }

    public FtpFileToDbTableLoader() {
        _fromhost = "";
        _port = "";
        _user = "";
        _password = "";
        _fromdir = "";
        _transfer_type = "";
        _removefile = "";
        _pattern = "";
        _table = "";
        _database_schema = "";
        _field_qty = "";
        _field_separator = "";
        _pProcessActiveId = null;
        _date_pattern = "";
        _storeId = "";
        _dateFormat = null;
    }

    public String loadFile (
              String fromhost
            , String port
            , String user
            , String password
            , String fromdir
            , String transfer_type
            , String removefile
            , String pattern
            , String table
            , String database_schema
            , String field_qty
            , String field_separator
            , Integer pProcessActiveId
            , String date_pattern
            , String storeId
            , Integer parentEventId) throws APIServiceAccessException, NamingException, RemoteException  {

        StringBuilder buff = new StringBuilder();

        buff.append("Parameters: \r\n");
        buff.append("           fromhost = ").append(fromhost).append("\r\n");
        buff.append("               port = ").append(port).append("\r\n");
        buff.append("               user = ").append(user).append("\r\n");
        buff.append("           password = ").append(password).append("\r\n");
        buff.append("            fromdir = ").append(fromdir).append("\r\n");
        buff.append("      transfer_type = ").append(transfer_type).append("\r\n");
        buff.append("         removefile = ").append(removefile).append("\r\n");
        buff.append("            pattern = ").append(pattern).append("\r\n");
        buff.append("              table = ").append(table).append("\r\n");
        buff.append("    database_schema = ").append(database_schema).append("\r\n");
        buff.append("          field_qty = ").append(field_qty).append("\r\n");
        buff.append("    field_separator = ").append(field_separator).append("\r\n");
        buff.append("   pProcessActiveId = ").append(pProcessActiveId).append("\r\n");
        buff.append("       date_pattern = ").append(date_pattern).append("\r\n");
        buff.append("            storeId = ").append(storeId).append("\r\n");
        buff.append("      parentEventId = ").append(parentEventId);
        log.info("[loadFile] Start of loading file into database table. \r\n" + buff.toString());

        _fromhost = fromhost;
        _port = port;
        _user = user;
        _password = password;
        _fromdir = fromdir;
        _transfer_type = transfer_type;
        _removefile = removefile;
        _pattern = pattern;
        _table = table;
        _database_schema = database_schema;
        _field_qty = field_qty;
        _field_separator = field_separator;
        _pProcessActiveId = pProcessActiveId;
        _date_pattern = date_pattern;
        _storeId = storeId;
        _parentEventId = parentEventId == null ? 0 : parentEventId.intValue();

        String userName = USER_NAME;
        StringBuilder messages = new StringBuilder();
        Map<String, String> filePropertyMap = new HashMap<String, String>();
        List<TableColumnInfo> columns = new ArrayList<TableColumnInfo>();
		boolean insertLineNumberFl = false;
        FileData fileData = new FileData();
        boolean isOk = true;
        boolean isLoadedFile = false;
		String fileName = null;

        try
        {
            do {
                ///
                log.info("[loadFile] Validation of parameters");
                if (!validateParameters(messages)) {
                    isOk = false;
                    log.error("[loadFile] Invalid parameters. Process will be terminated.");
                    break;
                }

                /// To get interfaces to work with database
                log.info("[loadFile] Try to obtain the interfaces to work with database");
                APIAccess factory = new APIAccess();
                DataUploaderHelper dataUploaderHelper = factory.getDataUploaderHelperAPI();

                /// Validation of existence of table in database
                log.info("[loadFile] Check the existence of table");
                if (!dataUploaderHelper.isExistsDatabaseTable(_table, _database_schema)) {
                    log.error("[loadFile] Table '" + _table + "' is not found. Processing will be terminated.");
                    isOk = false;
                    messages.append("Table '");
                    messages.append(_table);
                    messages.append("' is not found. Schema: '");
                    messages.append(_database_schema);
                    messages.append("'.");
                    break;
                }

                ///
                _fromhost = _fromhost.trim();
                if (IsEmptyString(_port)) {
                    _port = String.valueOf(DEFAULT_FTP_PORT);
                    log.info("[loadFile] Value of ftp port is not defined. Default value will be used: '" + _port + "'.");
                } else {
                    _port = _port.trim();
                }
                if (IsEmptyString(_fromdir)) {
                    _fromdir = "/";
                    log.info("[loadFile] Value of 'fromdir' is not defined. Default value will be used: '" + _fromdir + "'.");
                } else {
                    _fromdir = _fromdir.trim();
                }
                _transfer_type = (_transfer_type == null ? "" : _transfer_type.trim());
                _removefile = (_removefile == null ? "" : _removefile.trim());
                _pattern = _pattern.trim();
                _table = _table.trim();
                _database_schema = (_database_schema == null ? "" : _database_schema.trim());
                _pProcessActiveId = _pProcessActiveId;
                _date_pattern = _date_pattern.trim();
                _storeId = (_storeId == null ? "" : _storeId.trim());

                ///
                filePropertyMap.put(PARAM_FROM_HOST, _fromhost);
                filePropertyMap.put(PARAM_FROM_DIR, _fromdir);
                filePropertyMap.put(PARAM_PORT, _port);
                filePropertyMap.put(PARAM_USER, _user);
                filePropertyMap.put(PARAM_PASSWORD, _password);
                filePropertyMap.put(PARAM_TRANSFER_TYPE, _transfer_type);
                filePropertyMap.put(PARAM_REMOVE_FILE, _removefile);

                ///
                log.info("[loadFile] Try to open file via ftp");
                try {
                    fileData = openFtpFile(filePropertyMap, _pattern.toUpperCase());
                    if(fileData==null) {
                        return ""; //No file found. Empty run.
                    }
                } catch (Exception ex) {
                    isOk = false;
                    messages.append(ex.getMessage());
                    messages.append(" ");
                    break;
                }
                fileName = fileData.getFileName();
                ///
                isLoadedFile = true;

                ///
                log.info("[loadFile] Try to load the database table information");
                int fieldQty = 0;
                if (!IsEmptyString(_field_qty)) {
                    fieldQty = string2Int(_field_qty, 0);
                }
                List<TableColumnInfo> allColumns = dataUploaderHelper.getTableColumnsInfo(
                    _table, _database_schema);
                if (allColumns == null || allColumns.size() == 0) {
                    break;
                }
                if (fieldQty > 0) {
                    for (int i = 0; i < allColumns.size(); ++i) {
					    TableColumnInfo colInf = allColumns.get(i);
						if( i < fieldQty ) {
							columns.add(colInf);
						} else {
							String colName = colInf.getName();
							if(colName.equalsIgnoreCase("LINE_NUMBER")) {
								insertLineNumberFl = true;
							}
						}
                    }
                } else {
                    columns = allColumns;
                }
                for (int i = 0; i < columns.size(); ++i) {
                    log.info("[loadFile] Column[" + (i + 1) + "]: " + columns.get(i).toString());
                }

                ///
                Map<String, String> dataFormats = new HashMap<String, String>();
                dataFormats.put(DataUploaderHelper.DATE_PATTERN, _date_pattern);

                ///
                log.info("[loadFile] Parsing rows from file");
                List<List<String>> dataFromFile = new ArrayList<List<String>>();
                List<FileFormatError> parsingErrors = null;
                if(fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().indexOf(".xls.")>=0) {
                    parsingErrors = parseXlsFile(fileData.getDataStream(),columns, dataFormats, dataFromFile);
                } else {
                    parsingErrors = parseRows(fileData.getDataStream(),columns, _field_separator,
                                                                     dataFormats, dataFromFile);
                }

                ///
                if (parsingErrors != null && parsingErrors.size() > 0) {
                    messages.append("An errors has been found in the file. \r\n");
                    for (int i = 0; i < parsingErrors.size(); ++i) {
                        FileFormatError error = (FileFormatError)parsingErrors.get(i);
                        messages.append("Error on line '");
                        messages.append(error.getLineNumber());
                        messages.append("': ");
                        messages.append(error.getMessage());
                        messages.append("\r\n");
                    }
                    isOk = false;
                    break;
                }

                ///
                String tableName = getTableName(_table, String.valueOf(_pProcessActiveId));

                ///
                log.info("[loadFile] Check the existence of table: " + tableName);
                boolean isExistsTable = dataUploaderHelper.isExistsDatabaseTable(tableName,
                    _database_schema);
                if (isExistsTable) {
                    log.info("[loadFile] Try to delete all rows from the table: " + tableName);
                    dataUploaderHelper.deleteDatabaseTable(userName, tableName, _database_schema);
                    dataUploaderHelper.createDatabaseTable(userName, tableName, _database_schema, allColumns);
                    //dataUploaderHelper.deleteAllRowsFromDatabaseTable(userName, tableName, _database_schema);
                } else {
                    log.info("[loadFile] Try to create the table: " + tableName);
                    dataUploaderHelper.createDatabaseTable(userName, tableName,
                        _database_schema, allColumns);
                }

                ///
                log.info("[loadFile] Try to insert new rows into table: " + tableName);
                for (int i = 0; i < dataFromFile.size(); ++i) {
                    List<String> row = (List<String>)dataFromFile.get(i);					
                    dataUploaderHelper.insertRowIntoDatabaseTable(userName,
                        tableName, _database_schema, columns, row, dataFormats,i+1, insertLineNumberFl);
                }

            } while (false);
        } catch (Exception ex) {
            isOk = false;
			ex.printStackTrace();
            messages.append(ex.getMessage());
        }

        ///
        if (fileData.getDataStream() != null) {
            try {
                fileData.getDataStream().close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        /// Ftp file removing
        /*
        if (isLoadedFile && isOk) {
            if (string2Boolean(_removefile)) {
                log.info("Try to remove the ftp file");
                removeFtpFile(filePropertyMap, fileData.getFileName());
            }
        }
        //*/

        if (!isOk) {
            log.error("[loadFile] An error occurred");
            throw new RemoteException(messages.toString());
        }

        log.info("[loadFile] Finish of loading file into database table");
		return fileName;
    }

    public void processLinksFromTable (
              String table
            , Integer pProcessActiveId
            , String databaseSchema
			, String ftpFileName) throws APIServiceAccessException, NamingException, RemoteException  {

        final String BINARY_COLUMN_SUFFIX = "_BLOB";
        final String IMAGE_COLUMN_SUFFIX  = "_BLOB";

        StringBuilder buff = new StringBuilder();

        String tableNameToProcess = getTableName(table, String.valueOf(pProcessActiveId));

        buff.append("Parameters: \r\n");
        buff.append("              table = ").append(table).append("\r\n");
        buff.append("   pProcessActiveId = ").append(pProcessActiveId).append("\r\n");
        buff.append("    database_schema = ").append(databaseSchema).append("\r\n");
        buff.append(" tableNameToProcess = ").append(tableNameToProcess).append("\r\n");
        buff.append(" ftpFileName = ").append(ftpFileName);

        log.info("[processLinksFromTable] Start of loading file into database table. \r\n" + buff.toString());
        if(ftpFileName ==null || ftpFileName.trim().length()==0) {
			return; //No file found. Empty run.
		}

        boolean isOk = true;
        StringBuilder messages = new StringBuilder();
        TreeSet<String> binaryColumnNames = new TreeSet<String>();
        TreeSet<String> imageColumnNames = new TreeSet<String>();

        binaryColumnNames.add("MSDS");
        binaryColumnNames.add("SPECIFICATION");
        binaryColumnNames.add("ASSOC_DOC_1");
        binaryColumnNames.add("ASSOC_DOC_2");
        binaryColumnNames.add("ASSOC_DOC_3");
        imageColumnNames.add("IMAGE");

        try {
            do {
                ///
                if (binaryColumnNames.size() == 0 && imageColumnNames.size() == 0) {
                    log.info("[processLinksFromTable] There are no data for processing");
                    break;
                }

                ///
                log.info("[processLinksFromTable] Validation of parameters");
                if (IsEmptyString(table)) {
                    isOk = false;
                    messages.append("Value of 'table' parameter is empty.\r\n");
                }
                if (!isOk) {
                    break;
                }

                ///
                log.info("[processLinksFromTable] Try to obtain the interfaces to work with database");
                APIAccess factory = new APIAccess();
                DataUploaderHelper dataUploaderHelper = factory.getDataUploaderHelperAPI();

                ///
                log.info("[processLinksFromTable] Check the existence of table: " + tableNameToProcess);
                boolean isExistsTable = dataUploaderHelper.isExistsDatabaseTable(tableNameToProcess, databaseSchema);
                if (!isExistsTable) {
                    log.info("[processLinksFromTable] Table " + tableNameToProcess + " is not found");
                    break;
                }

                ///
                log.info("[processLinksFromTable] Try to load the database table information");
                List<TableColumnInfo> columns = dataUploaderHelper.getTableColumnsInfo(tableNameToProcess, databaseSchema);
                if (columns == null || columns.size() == 0) {
                    log.info("[processLinksFromTable] List of columns for table " + tableNameToProcess + " is empty.");
                    break;
                }
                for (int i = 0; i < columns.size(); ++i) {
                    log.info("[processLinksFromTable] Column[" + (i + 1) + "]: " + columns.get(i).toString());
                }

                ///
                TreeSet<String> binaryColumnNamesToProcess = new TreeSet<String>();
                TreeSet<String> imageColumnNamesToProcess = new TreeSet<String>();
                {
                    Iterator<String> it = binaryColumnNames.iterator();
                    while (it.hasNext()) {
                        String columnName = it.next();
                        if (searchColumnByName(columns, columnName) &&
                            searchColumnByNameAndType(columns, columnName + BINARY_COLUMN_SUFFIX, "BLOB")) {
                            binaryColumnNamesToProcess.add(columnName);
                        }
                    }
                }
                {
                    Iterator<String> it = imageColumnNames.iterator();
                    while (it.hasNext()) {
                        String columnName = it.next();
                        if (searchColumnByName(columns, columnName) &&
                            searchColumnByNameAndType(columns, columnName + IMAGE_COLUMN_SUFFIX, "BLOB")) {
                            imageColumnNamesToProcess.add(columnName);
                        }
                    }
                }

                ///
                {
                    Iterator<String> it = binaryColumnNamesToProcess.iterator();
                    while (it.hasNext()) {
                        String columnName = it.next();
                        TreeSet<String> valuesList = dataUploaderHelper.getStringColumnValues(tableNameToProcess,
                            databaseSchema, columnName);
                        if (valuesList != null) {
                            Iterator<String> itVal = valuesList.iterator();
                            while (itVal.hasNext()) {
                                String columnValue = itVal.next();
                                byte[] bytes = loadBinaryDataByUrl(columnValue);
                                dataUploaderHelper.updateBlobColumnValues(tableNameToProcess,
                                    databaseSchema, columnName, columnValue,
                                    columnName + BINARY_COLUMN_SUFFIX, bytes);
                            }
                        }
                    }
                }
                {
                    Iterator<String> it = imageColumnNamesToProcess.iterator();
                    while (it.hasNext()) {
                        String columnName = it.next();
                        TreeSet<String> valuesList = dataUploaderHelper.getStringColumnValues(tableNameToProcess,
                            databaseSchema, columnName);
                        if (valuesList != null) {
                            Iterator<String> itVal = valuesList.iterator();
                            while (itVal.hasNext()) {
                                String columnValue = itVal.next();
                                byte[] bytes = loadImageDataByUrl(columnValue, 200, 200);
                                dataUploaderHelper.updateBlobColumnValues(tableNameToProcess,
                                    databaseSchema, columnName, columnValue,
                                    columnName + IMAGE_COLUMN_SUFFIX, bytes);
                            }
                        }
                    }
                }
            } while (false);
        } catch (Exception ex) {
            isOk = false;
            messages.append(ex.getMessage());
        }

        if (!isOk) {
            log.error("[processLinksFromTable] An error occurred");
            throw new RemoteException(messages.toString());
        }

        log.info("[processLinksFromTable] Finish");
    }

    public void removeFtpFile (
              String fromhost
            , String port
            , String user
            , String password
            , String fromdir
            , String removefile
            , String ftpFileName
              )
            throws RemoteException  {

        StringBuilder buff = new StringBuilder();

        buff.append("Parameters: \r\n");
        buff.append("           fromhost = ").append(fromhost).append("\r\n");
        buff.append("               port = ").append(port).append("\r\n");
        buff.append("               user = ").append(user).append("\r\n");
        buff.append("           password = ").append(password).append("\r\n");
        buff.append("            fromdir = ").append(fromdir).append("\r\n");
        buff.append("           removefile = ").append(removefile).append("\r\n");
        buff.append("           ftpFileName = ").append(ftpFileName).append("\r\n");
        log.info("[removeFtpFile] Start of removing ftp file. \r\n" + buff.toString());

        StringBuilder messages = new StringBuilder();
        boolean isOk = true;

        try {
            do {
                ///
                if (IsEmptyString(fromhost)) {
                    isOk = false;
                    messages.append("Value of 'fromhost' parameter is empty.\r\n");
                }
                if (!IsEmptyString(port)) {
                    if (!isIntValue(port)) {
                        isOk = false;
                        messages.append("Invalid numerical value for 'port' parameter.\r\n");
                    }
                }
                if (IsEmptyString(user)) {
                    isOk = false;
                    messages.append("Value of 'user' parameter is empty.\r\n");
                }
//                if (IsEmptyString(ftpFileName)) {
//                    isOk = false;
//                    messages.append("Value of 'ftpFileName' parameter is empty.");
//                }
                if (!isOk) {
                    break;
                }

                ///
                if (string2Boolean(removefile) && !IsEmptyString(ftpFileName)){
                  if (IsEmptyString(port)) {
                    log.warn("[removeFtpFile] Value of ftp port is not defined. Default value will be used: '" + DEFAULT_FTP_PORT + "'.");
                  }
                  if (IsEmptyString(fromdir)) {
                    log.warn("[removeFtpFile] Value of 'fromdir' is not defined. Default value will be used: '/'.");
                  }
                  String actualPort = (IsEmptyString(port) ? String.valueOf(DEFAULT_FTP_PORT) : port.trim());
                  String actualFromdir = (IsEmptyString(fromdir) ? "/" : fromdir.trim());

                  ///
                  Map<String, String> filePropertyMap = new HashMap<String, String>();
                  filePropertyMap.put(PARAM_FROM_HOST, fromhost.trim());
                  filePropertyMap.put(PARAM_FROM_DIR, actualFromdir);
                  filePropertyMap.put(PARAM_FILE_NAME, ftpFileName.trim());
                  filePropertyMap.put(PARAM_PORT, actualPort);
                  filePropertyMap.put(PARAM_USER, user);
                  filePropertyMap.put(PARAM_PASSWORD, password);

                  ///
                  log.info("[removeFtpFile] Try to remove the ftp file");
                  removeFtpFile(filePropertyMap, ftpFileName.trim());
                }
            } while (false);
        } catch (Exception ex) {
            isOk = false;
            messages.append(ex.getMessage());
        }

        if (!isOk) {
            log.error("[removeFtpFile] An error occurred");
            throw new RemoteException(messages.toString());
        }

        log.info("[removeFtpFile] Finish of removing ftp file");
    }
    
    private byte[] loadBinaryDataByUrl(String url) throws Exception {
        byte[] bytes = null;
        log.info("[loadBinaryDataByUrl] Try to load binary data by url: '" + url + "'");
        if (IsEmptyString(url)) {
            log.error("[loadBinaryDataByUrl] Url is not defined!");
            return null;
        }
        HttpURLConnection httpConnection = null;
        URL urlConnection = null;
        try {
            urlConnection = new URL(url);
            httpConnection = (HttpURLConnection)urlConnection.openConnection();
            InputStream inputStream = new BufferedInputStream(httpConnection.getInputStream());
            if (inputStream == null) {
                log.info("[loadBinaryDataByUrl] Empty data");
            } else {
            	ByteArrayOutputStream bout = new ByteArrayOutputStream(10000);
                int b;
                while ((b = inputStream.read()) != -1) {
                  bout.write(b);
                }
                bytes = bout.toByteArray();
                log.info("[loadBinaryDataByUrl] Data size: " + bytes.length);
            }
        } catch (Exception ex) {
            log.error("[loadBinaryDataByUrl] Error occurred. " + ex.getMessage());
            throw new Exception(ex.getMessage());
        } finally {
            try {
                if (httpConnection != null) {
                    httpConnection.disconnect();
                }
            } catch (Exception ex) {
                log.error("[loadBinaryDataByUrl] Closure of HTTP connection failed");
            }
        }
        return bytes;
    }

    private byte[] loadImageDataByUrl(String url, int width, int height)
        throws Exception {
        byte[] bytes = null;
        log.info("[loadImageDataByUrl] Try to load binary data by url: '" + url + "'");
        if (IsEmptyString(url)) {
            log.error("[loadImageDataByUrl] Url is not defined!");
            return null;
        }
        URL urlConnection = null;
        try {
            urlConnection = new URL(url);
            BufferedImage image = ImageIO.read(urlConnection);
            int type = image.getType();
            BufferedImage resizedImage = new BufferedImage(width, height, type);
            Graphics2D g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(image, 0, 0, width, height, null);
            g.dispose();
            bytes = bufferedImage2ByteArray(resizedImage, "jpg");
        } catch (Exception ex) {
            log.error("[loadImageDataByUrl] Error occurred. " + ex.getMessage());
            throw new Exception(ex.getMessage());
        }
        return bytes;
    }

    private byte[] bufferedImage2ByteArray(BufferedImage imageObj, String imageType) {
        if (imageObj != null) {
            BufferedImage image = (BufferedImage)imageObj;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, imageType, baos);
            } catch (IOException ex) {
                throw new IllegalStateException(ex.toString());
            }
            byte[] b = baos.toByteArray();
            return b;
        }
        return new byte[0];
    }

    private BufferedImage bufferedImageFromByteArray(byte[] imageBytes) {
        try {
            if (imageBytes != null && (imageBytes.length > 0)) {
                BufferedImage im = ImageIO.read(new ByteArrayInputStream(imageBytes));
                return im;
            }
            return null;
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.toString());
        }
    }

    private boolean validateParameters(StringBuilder messages) {
        boolean isOk = true;
        if (IsEmptyString(_fromhost)) {
            isOk = false;
            messages.append("Value of 'fromhost' parameter is empty.\r\n");
        }
        if (!IsEmptyString(_port)) {
            if (!isIntValue(_port)) {
                isOk = false;
                messages.append("Invalid numerical value for 'port' parameter.\r\n");
            }
        }
        if (IsEmptyString(_user)) {
            isOk = false;
            messages.append("Value of 'user' parameter is empty.\r\n");
        }
        if (IsEmptyString(_fromdir)) {
            log.warn("[validateParameters] Value of 'fromdir' parameter is empty.");
        }
        if (IsEmptyString(_transfer_type)) {
            isOk = false;
            messages.append("Value of 'transfer_type' parameter is empty.\r\n");
        }
        if (IsEmptyString(_removefile)) {
            isOk = false;
            messages.append("Value of 'removefile' parameter is empty.\r\n");
        }
        if (IsEmptyString(_table)) {
            isOk = false;
            messages.append("Value of 'table' parameter is empty.\r\n");
        }
        if (IsEmptyString(_database_schema)) {
            log.warn("[validateParameters] Value of 'database_schema' parameter is empty.");
        }
        if (!IsEmptyString(_field_qty)) {
            if (!isIntValue(_field_qty)) {
                isOk = false;
                messages.append("Invalid numerical value for 'field_qty' parameter.\r\n");
            }
        }
        if (IsEmptyString(_field_separator)) {
            isOk = false;
            messages.append("Value of 'field_separator' parameter is empty.\r\n");
        }
        if (_pProcessActiveId==null) {
            isOk = false;
            messages.append("Value of 'p' parameter is empty.\r\n");
        }
        if (IsEmptyString(_date_pattern)) {
            isOk = false;
            messages.append("Value of 'date_pattern' parameter is empty.\r\n");
        } else {
            try {
                _dateFormat = new SimpleDateFormat(_date_pattern);
                _dateFormat.setLenient(false);
            } catch (Exception ex) {
                isOk = false;
                messages.append("Invalid pattern for data format: '");
                messages.append(_date_pattern);
                messages.append("'. ");
                messages.append(ex.getMessage());
                messages.append("\r\n");
            }
        }
        if (!IsEmptyString(_storeId)) {
            if (!isIntValue(_storeId)) {
                isOk = false;
                messages.append("Invalid numerical value for 'storeId' parameter.\r\n");
            }
        }
        return isOk;
    }

    private FileData openFtpFile(Map<String, String> propertyMap,
        String pattren) throws Exception {
        FileData resData = new FileData();
        StringBuilder messages = new StringBuilder();
        boolean isOk = true;
        GetFile getClient = null;

        try {
            do {
                ///
                getClient = new GetFile();
                getClient.setProperties(propertyMap);
                ///
                log.info("[openFtpFile] Try to connect to the host via ftp.");
                getClient.connect();
                log.info("[openFtpFile] Connection to the host via ftp was established.");
                ///

                String fs[] = getClient.getFileNames();
                ArrayList filesToLoad = new ArrayList();
                for (String fileName : fs) {
                  if (fileName.toUpperCase().startsWith(pattren)) {
                       filesToLoad.add(fileName);
                  }
                }
                ///
                if (filesToLoad == null || filesToLoad.size() ==0 ) {
                    log.info("[openFtpFile] File by pattren '" + pattren + "' was not found.");
                    return null;
                }
                // Renaming ftp file and updating pattern
                String fileNameToRename = (String)filesToLoad.get(0);
                String fileNameToLoad = fileNameToRename;
                if (!fileNameToRename.equalsIgnoreCase(pattren)) {
                  fileNameToLoad = renameFtpFile(getClient, fileNameToRename);
                  // generating event to load next file from ftp if multiple files
                  // found by pattern
                  if (filesToLoad.size() > 1) {
                     generateNewEvent();
                  }
                }
                ///
                resData.setFileName(fileNameToLoad);
                ///
                log.info("[openFtpFile] Try to read data from file: " + fileNameToLoad + "'");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (getClient.readIFile(fileNameToLoad, stream)) {
                    resData.setDataStream(new ByteArrayInputStream(stream.toByteArray()));
                    log.info("Data from file was read.");
                } else {
                    isOk = false;
                    log.error("[openFtpFile] Data has not been read!");
                    messages.append("Data form file '" + fileNameToLoad + "' has not been read!");
                }

            } while (false);

        } catch (Exception ex) {
            isOk = false;
            log.error("[openFtpFile] An error occurred at reading data from fila via ftp. " + ex.getMessage());
            messages.append("An error occurred at reading data from fila via ftp. " + ex.getMessage());
        } finally {
            if (getClient != null) {
                try {
                    getClient.closeSession();
                } catch (Exception ex) {
                    log.error("Session closing failed. " + ex.getMessage());
                }
            }
        }
        if (!isOk) {
            throw new Exception(messages.toString());
        }
        return resData;
    }

    private List<FileFormatError> parseRows(ByteArrayInputStream dataStream,
        List<TableColumnInfo> columns, String separator, Map<String, String> dataFormats,
        List<List<String>> parsedData) throws Exception {

        List<FileFormatError> errors = new ArrayList<FileFormatError>();
        boolean existData = true;
        int rowNumber = 0;
        SimpleDateFormat dateFormat = null;
        StringBuilder lineBuff = new StringBuilder();

        if (dataFormats != null) {
            String datePattern = dataFormats.get(DataUploaderHelper.DATE_PATTERN);
            if (!IsEmptyString(datePattern)) {
                 try {
                    dateFormat = new SimpleDateFormat(datePattern);
                    dateFormat.setLenient(false);
                } catch (Exception ex) {
                    log.error("[parseRows] Invalid pattern for data format: " + datePattern);
                    dateFormat = null;
                }
            }
        }

        while (existData) {
            if (!getLineFromStream(dataStream, lineBuff)) {
                existData = false;
            } else {
                List<String> values = getValuesFromLine(lineBuff.toString(), separator);
                List<FileFormatError> lineErrors = checkValuesForRow(rowNumber,
                    columns, values, dateFormat);
                if (lineErrors == null || lineErrors.size() == 0) {
                    parsedData.add(values);
                } else {
                    errors.addAll(lineErrors);
                    if (errors.size() > 100) {
                        break;
                    }
                }
                rowNumber += 1;
            }
        }
        return errors;
    }

    private boolean getLineFromStream(ByteArrayInputStream dataStream, StringBuilder lineBuff) {
        if (dataStream == null) {
            return false;
        }
        lineBuff.setLength(0);
        boolean isNotEnd = true;
        while (true) {
            int chr = dataStream.read();
            if (chr == -1) {
                isNotEnd = false;
                break;
            }
            if (chr == '\r') {
                continue;
            }
            if (chr == '\n') {
                break;
            } else {
                lineBuff.append((char)chr);
            }
        }
        return isNotEnd;
    }

    private static List<String> getValuesFromLine(String line, String separator) {
        if (IsEmptyString(line) || IsEmptyString(separator)) {
            return null;
        }
        List<String> values = new ArrayList<String>();
        int pos1 = 0;
        int pos2 = 0;
        while (true) {
            pos2 = line.indexOf(separator, pos1);
            if (pos2 >= 0) {
                values.add(line.substring(pos1, pos2));
                pos1 = pos2 + separator.length();
            } else {
                values.add(line.substring(pos1, line.length()));
                break;
            }
        }
        return values;
    }

/////////////////////////////////////////////////////////
    private List<FileFormatError> parseXlsFile(ByteArrayInputStream dataStream,
        List<TableColumnInfo> columns, Map<String, String> dataFormats,
        List<List<String>> parsedData)
	throws Exception {

        List<FileFormatError> errors = new ArrayList<FileFormatError>();
        boolean existData = true;
        int rowNumber = 0;
        SimpleDateFormat dateFormat = null;
        StringBuilder lineBuff = new StringBuilder();

        if (dataFormats != null) {
            String datePattern = dataFormats.get(DataUploaderHelper.DATE_PATTERN);
            log.info("FtpFileToDbTableLoader FFTDFFTDFFTDFFTD date pattern: "+datePattern);
            if (!IsEmptyString(datePattern)) {
                 try {
                    dateFormat = new SimpleDateFormat(datePattern);
                    dateFormat.setLenient(false);
                } catch (Exception ex) {
                    log.error("[parseRows] Invalid pattern for data format: " + datePattern);
                    dateFormat = null;
                }
            }
        }

        HSSFWorkbook workBook = new HSSFWorkbook(dataStream);
		HSSFSheet sheet = workBook.getSheetAt(0);
		int rows = sheet.getPhysicalNumberOfRows();

		for (int rr = 1; rr < rows; rr++) {
			HSSFRow row = sheet.getRow(rr);
			if (row == null) {
				continue;
			}

			int cells = columns.size(); //row.getLastCellNum(); //row.getPhysicalNumberOfCells();
			List<String> values = new ArrayList<String>();
			boolean emptyRowFl = true;
			for (int c = 0; c < cells; c++) {
				TableColumnInfo column = (TableColumnInfo) columns.get(c);
				HSSFCell cell = row.getCell(c);
				String value = null;
if(cell==null) {				
String nullStr = null;
values.add(nullStr);
continue;
}
				//if(DateUtil.isCellDateFormatted((Cell)cell)) {
				//	Date dd = cell.getDateCellValue();
				//	value = dateFormat.format(dd);
				//} else {
					switch (cell.getCellType()) {

					case HSSFCell.CELL_TYPE_NUMERIC:
						if("DATE".equalsIgnoreCase(column.getType())) {
							Date dt = cell.getDateCellValue();
							value = dateFormat.format(dt);
							log.info("FtpFileToDbTableLoader FFTDFFTDFFTDFFTD cell ["+c+"] = "+ cell.toString()+" <"+dt+"> = <"+value+">");
						} else {						
							double dd = cell.getNumericCellValue();
							long ll = Math.round(dd);
							if(Math.abs(ll-dd)<0.00000001) {
								value = String.valueOf(ll);
							} else {
								value = String.valueOf(dd);
							}
						}
						break;

					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;


					default:
						value = cell.toString();
if("DATE".equalsIgnoreCase(column.getType())) {
	log.info("FtpFileToDbTableLoader FFTDFFTDFFTDFFTD def cell ["+c+"] = "+ cell.toString()+" = <"+value+">");
}
					}
				//}
				if(emptyRowFl && Utility.isSet(value)) {emptyRowFl = false;}
				values.add(value);
			}
			if(emptyRowFl) continue;
            List<FileFormatError> lineErrors =
				checkValuesForRow(rr, columns, values, dateFormat);
            if (lineErrors == null || lineErrors.size() == 0) {
                parsedData.add(values);
            } else {
                errors.addAll(lineErrors);
                if (errors.size() > 100) {
                    break;
                }
            }
		}
		return errors;
    }

////////////////////
    private List<FileFormatError> checkValuesForRow(int rowNumber,
        List<TableColumnInfo> columns, List<String> values, SimpleDateFormat dateFormat) {
        List<FileFormatError> errors = new ArrayList<FileFormatError>();
        if (columns == null || columns.size() == 0) {
            errors.add(new FileFormatError(rowNumber, "Columns are not defined!"));
            return errors;
        }
        if (values == null) {
            errors.add(new FileFormatError(rowNumber, "Values loaded from file are not defined!"));
            return errors;
        }
        if (columns.size() > values.size()) {
            errors.add(new FileFormatError(rowNumber, "Some values are missed!"));
            return errors;
        }
        for (int i = 0; i < columns.size(); ++i) {
            TableColumnInfo column = (TableColumnInfo)columns.get(i);
            String value = (String)values.get(i);
            FileFormatError error = checkValueForColumn(rowNumber, column, value, dateFormat);
            if (error != null) {
                errors.add(error);
            }
        }
        return errors;
    }

    private FileFormatError checkValueForColumn(int rowNumber,
        TableColumnInfo column, String value, SimpleDateFormat dateFormat) {
        if (column == null) {
            return new FileFormatError(rowNumber, "Column is not defined!");
        }

        FileFormatError error = null;

        if (value == null || value.length() == 0) {
			if (!column.getIsNullable()) {
				return new FileFormatError(rowNumber, "Value of '" +
                    column.getName() + "' column must be not empty.");
            } else {
				return error;
			}
        }

        if ("NUMBER".equalsIgnoreCase(column.getType())) {
            if (column.getScale() <= 0) {
                if (!isIntValue(value)) {
                    return new FileFormatError(rowNumber, "Value of '" +
                        column.getName() + "' have invalid numeric format: '" +
                        value + "'.");
                }
            } else {
                if (!isDoubleValue(value)) {
                    return new FileFormatError(rowNumber, "Value of '" +
                        column.getName() + "' have invalid numeric format: '" +
                        value + "'.");
                }
            }
        } else if (
            "VARCHAR2".equalsIgnoreCase(column.getType()) ||
            "NVARCHAR2".equalsIgnoreCase(column.getType()) ||
            "CHAR".equalsIgnoreCase(column.getType()) ||
            "NCHAR".equalsIgnoreCase(column.getType())) {
            if (column.getCharLength() > 0 && column.getCharLength() < value.length()) {
                return new FileFormatError(rowNumber, "Length of string for column '" +
                    column.getName() + "' is very big: '" +
                        value + "'.");
            }
        } else if ("FLOAT".equalsIgnoreCase(column.getType())) {
            if (!isDoubleValue(value)) {
                return new FileFormatError(rowNumber, "Value of '" +
                    column.getName() + "' have invalid numeric format: '" +
                        value + "'.");
            }
        } else if ("LONG".equalsIgnoreCase(column.getType())) {
            if (!isLongValue(value)) {
                return new FileFormatError(rowNumber, "Value of '" +
                    column.getName() + "' have invalid numeric format: '" +
                        value + "'.");
            }
        } else if ("DATE".equalsIgnoreCase(column.getType())) {
            if (!isDateValue(value, dateFormat)) {
                return new FileFormatError(rowNumber, "Value of '" +
                    column.getName() + "' have invalid date format: '" +
                        value + "'.");
            }
        } else if (column.getType().toUpperCase().startsWith("TIMESTAMP")) {
            if (!isDateValue(value, dateFormat)) {
                return new FileFormatError(rowNumber, "Value of '" +
                    column.getName() + "' have invalid date format: '" +
                        value + "'.");
            }
        } else if ("RAW".equalsIgnoreCase(column.getType())) {
            if (column.getSize() > 0 && column.getSize() < value.length()) {
                return new FileFormatError(rowNumber, "Length of string for column '" +
                    column.getName() + "' is very big: '" +
                        value + "'.");
            }
        }
        return error;
    }

    private void removeFtpFile(Map<String, String> propertyMap, String fileName) {
        GetFile getClient = null;

        try {
            log.info("[removeFtpFile] Try to remove ftp file: " + fileName);
            getClient = new GetFile();
            getClient.setProperties(propertyMap);
            getClient.connect();
            getClient.remove(fileName);
        } catch (Exception ex) {
            log.error("Removing of file failed. " + ex.getMessage());
        } finally {
            if (getClient != null) {
                try {
                    getClient.closeSession();
                } catch (Exception ex) {
                    log.error("Session closing failed. " + ex.getMessage());
                }
            }
        }
    }

    private boolean searchColumnByName(List<TableColumnInfo> columns, String columnName) {
        if (columns == null || columnName == null) {
            return false;
        }
        boolean found = false;
        for (int i = 0; i < columns.size(); ++i) {
            TableColumnInfo column = (TableColumnInfo)columns.get(i);
            if (column == null || column.getName() == null) {
                continue;
            }
            if (column.getName().equalsIgnoreCase(columnName)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean searchColumnByNameAndType(List<TableColumnInfo> columns, String columnName, String columnType) {
        if (columns == null || columnName == null || columnType == null) {
            return false;
        }
        boolean found = false;
        for (int i = 0; i < columns.size(); ++i) {
            TableColumnInfo column = (TableColumnInfo)columns.get(i);
            if (column == null || column.getName() == null || column.getType() == null) {
                continue;
            }
            if (column.getName().equalsIgnoreCase(columnName) &&
                column.getType().equalsIgnoreCase(columnType)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private String getTableName(String table, String suffix)  {
        if (IsEmptyString(table)) {
            return table;
        }
        String fullTableName = table.trim().toUpperCase();
        if (!IsEmptyString(suffix)) {
            fullTableName = fullTableName + suffix.trim().toUpperCase();
        }
        return fullTableName;
    }

    private static boolean string2Boolean(String src) {
        if (src == null) {
            return false;
        }
        if ("true".equalsIgnoreCase(src.trim())) {
            return true;
        }
        if ("ok".equalsIgnoreCase(src.trim())) {
            return true;
        }
        if ("yes".equalsIgnoreCase(src.trim())) {
            return true;
        }
        return false;
    }

    private static int string2Int(String src, int defaultValue) {
        if (src == null) {
            return defaultValue;
        }
        int value = defaultValue;
        try {
            Integer tempValue = Integer.parseInt(src.trim());
            value = tempValue.intValue();
        } catch (Exception ex) {
            value = defaultValue;
        }
        return value;
    }

    private static boolean isDoubleValue(String src) {
        if (src == null) {
            return false;
        }
        boolean isOk = true;
        try {
             double dValue = Double.parseDouble(src.trim());
        } catch (Exception ex) {
            isOk = false;
        }
        return isOk;
    }

    private static boolean isIntValue(String src) {
        if (src == null) {
            return false;
        }
        boolean isOk = true;
        try {
            Integer intValue = Integer.parseInt(src.trim());
        } catch (Exception ex) {
            isOk = false;
        }
        return isOk;
    }

    private static boolean isLongValue(String src) {
        if (src == null) {
            return false;
        }
        boolean isOk = true;
        try {
            long longValue = Long.parseLong(src.trim());
        } catch (Exception ex) {
            isOk = false;
        }
        return isOk;
    }

    private static boolean isDateValue(String src, SimpleDateFormat dateFormat) {
        if (src == null || dateFormat == null) {
            return false;
        }
        boolean isOk = true;
        try {
            Date date = dateFormat.parse(src.trim());
        } catch (Exception ex) {		
            isOk = false;
        }
        return isOk;
    }

    static private boolean IsEmptyString(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    private String renameFtpFile ( GetFile pGetClient, String pFileNameToRename)  throws Exception {
      String fileNameToLoad = genNewFileName(pFileNameToRename);
      log.info("[renameFtpFile] Renaming Ftp file: from " + pFileNameToRename +" to " + fileNameToLoad);
      pGetClient.rename(pFileNameToRename, fileNameToLoad);
      Event eventBean = APIAccess.getAPIAccess().getEventAPI();
      eventBean.updateEventProperty(eventId, "pattern", fileNameToLoad, "FtpFileToDbTableLoader");
      return fileNameToLoad;
    }

    private String genNewFileName(String pFileNameToRename)  throws Exception {
      Process processBean = APIAccess.getAPIAccess().getProcessAPI();
      IdVector processIdV = new IdVector();
      processIdV.add(new Integer(_pProcessActiveId));
      IdVector eventIdV = processBean.getEventsByProcessIds(processIdV);
      eventId = new Integer(0);
      if (eventIdV != null && eventIdV.size() > 0){
        eventId = (Integer)eventIdV.get(0);
      }
      String newName = pFileNameToRename.substring(0,1)+ eventId.toString() +
                       pFileNameToRename.substring(1);
      return  newName;
    }

    private void generateNewEvent() throws Exception{
      log.debug("[generateNewEvent]---> Begin.");
      Event eventBean = APIAccess.getAPIAccess().getEventAPI();
      EventPropertyDataVector eventPropDV = eventBean.getEventPropertiesAsVector(eventId);

      EventData eventData = Utility.createEventDataForProcess();
      EventProcessView epv = new EventProcessView(eventData, new EventPropertyDataVector(), _parentEventId);

      for(Iterator iter=eventPropDV.iterator(); iter.hasNext();) {
          EventPropertyData epD = (EventPropertyData) iter.next();
          if (Event.PROPERTY_PROCESS_TEMPLATE_ID.equals(epD.getType())){
             epv.getProperties().add(Utility.createEventPropertyData("process_id", Event.PROPERTY_PROCESS_TEMPLATE_ID, epD.getNumberVal(), 1));
             log.debug("[generateNewEvent]---> Added PROPERTY_PROCESS_TEMPLATE_ID = " +epD.getNumberVal());
         }
          if (Event.PROCESS_VARIABLE.equals(epD.getType())){
             int num  = epD.getNum();
             String varName = epD.getShortDesc();
             String varType = epD.getVarClass();
             if("java.lang.Integer".equals(varType)) {
                epv.getProperties().add(Utility.createEventPropertyData(varName, Event.PROCESS_VARIABLE, epD.getNumberVal(), num));
                log.debug("[generateNewEvent]---> Added PROCESS_VARIABLE = " +epD.getNumberVal());
             } else if("java.lang.String".equals(varType)) {

                String varValue = (epD.getStringVal()!= null) ?
                                  (("pattern".equals(varName))? _pattern : epD.getStringVal()): "" ;
                epv.getProperties().add(Utility.createEventPropertyData(varName, Event.PROCESS_VARIABLE, varValue, num));
                log.debug("[generateNewEvent]---> Added PROCESS_VARIABLE = " + varValue);
             }
          }
      }

      EventProcessView newEvent = eventBean.addEventProcess(epv, "FtpFileToDbTableLoader");
      log.debug("[generateNewEvent]---> End.");
      if (newEvent != null && newEvent.getEventData() != null){
        log.info("[generateNewEvent]---> New Event has bean generated. Event Id = " + newEvent.getEventData().getEventId());
      }
    }
}
