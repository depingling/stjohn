/**
 * Title:        SourceCodeObserver
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.srcCodeObserver;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LoggerHandler extends Handler {

    static public SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

    private String _fileName;
    private FileWriter _fileWriter;

    public LoggerHandler(String fileName) {
        _fileWriter = null;
        _fileName = fileName;
        if (_fileName != null) {
            if (_fileName.trim().length() > 0) {
                try {
                    File file = new File(_fileName);
                    _fileWriter = new FileWriter(file);
                }
                catch (Exception ex) {
                    _fileWriter = null;
                    System.out.println("An error occurred at file '" + _fileName + "' opening. " + ex.getMessage());
                }
            }
        }
    }

    public void close() throws SecurityException {
        if (_fileWriter != null) {
            try {
                _fileWriter.close();
            }
            catch (Exception ex) {
            }
        }
    }

    public void flush() {
        if (_fileWriter != null) {
            try {
                _fileWriter.flush();
            } catch (Exception ex) {
            }
        }
    }

    public void publish(LogRecord logRecord) {
        StringBuilder buff = new StringBuilder();
        buff.append("[");
        buff.append(DATE_FORMAT.format(new Date(logRecord.getMillis())));
        buff.append("] [");
        buff.append(logRecord.getLoggerName());
        buff.append("] [");
        buff.append(logRecord.getLevel().toString());
        buff.append("] ");
        buff.append(logRecord.getMessage());
        System.out.println(buff.toString());
        if (_fileWriter != null) {
            try {
                _fileWriter.write(buff.toString());
                _fileWriter.write("\r\n");
            } catch (Exception ex) {
            }
        }
    }

    public String getFileName() {
        return _fileName;
    }

    public void setFileName(String fileName) {
        _fileName = fileName;
    }

}
