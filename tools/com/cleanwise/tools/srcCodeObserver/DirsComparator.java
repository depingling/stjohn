/**
 * Title:        SourceCodeObserver
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.srcCodeObserver;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

public class DirsComparator {

    static Logger LOGGER = Logger.getLogger("SourceCodeObserver");

    static public SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

    private Writer _writer;
    private long _messageCounter;

    public DirsComparator(Writer writer) {
        _writer = writer;
        _messageCounter = 0;
    }

    public boolean compareDirElements(String path1, DirElement elements1, String path2, DirElement elements2) throws Exception {
        _messageCounter = 0;
        return doCompareDirs(path1, elements1, path2, elements2);
    }

    public boolean doCompareFiles(String path1, HashMap<String, FileElement> files1, 
            String path2, HashMap<String, FileElement> files2) throws Exception {
        ///
        boolean isEqual = true;
        StringBuilder buff = new StringBuilder();
        ///
        FileComparator fileComparator = new FileComparator(FileComparator.ComparisonFlags.BY_SIZE | FileComparator.ComparisonFlags.BY_MOD_DATE);
        Iterator<String> it1 = files1.keySet().iterator();
        Iterator<String> it2 = files2.keySet().iterator();
        while (it1.hasNext()) {
            String fileName1 = (String)it1.next();
            if (files2.containsKey(fileName1)) {
                FileElement file1 = (FileElement)files1.get(fileName1);
                FileElement file2 = (FileElement)files2.get(fileName1);
                if (fileComparator.compare(file1, file2) != 0) {
                    isEqual = false;
                    buff.setLength(0);
                    buff.append(path1);
                    buff.append("/");
                    buff.append(file1.getName());
                    buff.append(" File is not identical. ");
                    buff.append("Server 1: ");
                    buff.append("Size='").append(file1.getSize()).append("', ");
                    buff.append("Modification date='").append(DATE_FORMAT.format(file1.getModificationDate())).append("'. ");
                    buff.append("Server 2: ");
                    buff.append("Size='").append(file2.getSize()).append("', ");
                    buff.append("Modification date='").append(DATE_FORMAT.format(file2.getModificationDate())).append("'.\r\n");
                    write(buff.toString());
                }
            } else {
                isEqual = false;
                buff.setLength(0);
                buff.append(path1);
                buff.append("/");
                buff.append(fileName1);
                buff.append(" Is present on server 1 but is not found on server 2.\r\n");
                write(buff.toString());
            }
        }
        while (it2.hasNext()) {
            String fileName2 = (String)it2.next();
            if (!files1.containsKey(fileName2)) {
                isEqual = false;
                buff.setLength(0);
                buff.append(path2);
                buff.append("/");
                buff.append(fileName2);
                buff.append(" Is present on server 2 but is not found on server 1.\r\n");
                write(buff.toString());
            }
        }
        return isEqual;
    }

    public boolean doCompareDirs(String path1, DirElement elements1, 
            String path2, DirElement elements2) throws Exception {
        ///
        boolean isEqual = true;
        StringBuilder buff = new StringBuilder();
        ///
        HashMap<String, FileElement> files1 = elements1.getFiles();
        HashMap<String, FileElement> files2 = elements2.getFiles();
        if (!doCompareFiles(path1, files1, path2, files2)) {
            isEqual = false;
        }
        ///
        HashMap<String, DirElement> dirs1 = elements1.getDirectories();
        HashMap<String, DirElement> dirs2 = elements2.getDirectories();
        Iterator<String> id1 = dirs1.keySet().iterator();
        Iterator<String> id2 = dirs2.keySet().iterator();
        while (id1.hasNext()) {
            String dirName1 = (String)id1.next();
            if (dirs2.containsKey(dirName1)) {
                String subPath1 = path1 + "/" + dirName1;
                String subPath2 = path2 + "/" + dirName1;
                DirElement dir1 = (DirElement)dirs1.get(dirName1);
                DirElement dir2 = (DirElement)dirs2.get(dirName1);
                if (!doCompareDirs(subPath1, dir1, subPath2, dir2)) {
                    isEqual = false;
                }
            } else {
                isEqual = false;
                buff.setLength(0);
                buff.append(path1);
                buff.append("/");
                buff.append(dirName1);
                buff.append(" Is present on server 1 but is not found on server 2.\r\n");
                write(buff.toString());
            }
        }
        while (id2.hasNext()) {
            String dirName2 = (String)id2.next();
            if (!dirs1.containsKey(dirName2)) {
                isEqual = false;
                buff.setLength(0);
                buff.append(path2);
                buff.append("/");
                buff.append(dirName2);
                buff.append(" Is present on server 2 but is not found on server 1.\r\n");
                write(buff.toString());
            }
        }
        return isEqual;
    }

    private void write(String str) {
        if (_writer == null) {
            return;
        }
        if (str == null) {
            return;
        }
        try {
            if (str.trim().length() > 0) {
                _messageCounter += 1;
                _writer.write(String.valueOf(_messageCounter));
                _writer.write(") ");
            }
            _writer.write(str);
        } catch (IOException ex) {
            
        }
    }

}
