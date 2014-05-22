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
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

public class DefaultWriter extends Writer {

    static Logger LOGGER = Logger.getLogger("SourceCodeObserver");

    private String _fileName;
    private FileWriter _fileWriter;

    public DefaultWriter(String fileName) {
        _fileWriter = null;
        _fileName = fileName;
        if (_fileName != null) {
            if (_fileName.trim().length() > 0) {
                try {
                    File file = new File(_fileName);
                    _fileWriter = new FileWriter(file);
                    LOGGER.info("File '" + _fileName + "' was opened.");
                }
                catch (Exception ex) {
                    _fileWriter = null;
                    LOGGER.severe("An error occurred at file '" + _fileName + "' opening. " + ex.getMessage());
                }
            }
        }
    }

    public void write(int arg0) throws IOException {
        System.out.write(arg0);
        if (_fileWriter != null) {
            _fileWriter.write(arg0);
        }
    }

    public void close() throws IOException {
        if (_fileWriter != null) {
            try {
                _fileWriter.close();
            }
            catch (Exception ex) {
            }
            LOGGER.info("File '" + _fileName + "' was closed.");
        }
    }

    public void flush() throws IOException {
        if (_fileWriter != null) {
            _fileWriter.flush();
        }
    }

    public void write(char[] cbuf, int off, int len) throws IOException {
        String message = new String(cbuf, off, len);
        System.out.write(message.getBytes());
        if (_fileWriter != null) {
            _fileWriter.write(cbuf, off, len);
        }
    }

    public String getFileName() {
        return _fileName;
    }

}
