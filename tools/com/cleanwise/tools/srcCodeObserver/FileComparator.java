/**
 * Title:        SourceCodeObserver
 * Description:  
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @version      1.0
 */

package com.cleanwise.tools.srcCodeObserver;

import java.util.Comparator;

public class FileComparator implements Comparator<FileElement> {

    public interface ComparisonFlags {
        static public int BY_NAME     = 1 << 0;
        static public int BY_MOD_DATE = 1 << 1;
        static public int BY_SIZE     = 1 << 2;
    }

    private int _flags;

    public FileComparator(int flags) {
        _flags = flags;
    }

    public int compare(FileElement file1, FileElement file2) {
        if (file1 == null && file2 == null) {
            return 0;
        }
        if (file1 != null && file2 == null) {
            return 1;
        }
        if (file1 == null && file2 != null) {
            return -1;
        }
        ///
        if ((_flags & ComparisonFlags.BY_NAME) != 0) {
            if (file1.getName() != null && file2.getName() == null) {
                return 1;
            } else if (file1.getName() == null && file2.getName() != null) {
                return -1;
            }
            if (file1.getName() != null && file2.getName() != null) {
                int res = file1.getName().compareTo(file2.getName());
                if (res > 0) {
                    return 1;
                } else if (res < 0) {
                    return -1;
                }
            } 
        }
        ///
        if ((_flags & ComparisonFlags.BY_SIZE) != 0) {
            if (file1.getSize() > file2.getSize()) {
                return 1;
            } else if (file1.getSize() < file2.getSize()) {
                return -1;
            }
        }
        ///
        if ((_flags & ComparisonFlags.BY_MOD_DATE) != 0) {
            if (file1.getModificationDate() != null && file2.getModificationDate() == null) {
                return 1;
            } else if (file2.getModificationDate() == null && file2.getModificationDate() != null) {
                return -1;
            } else if (file2.getModificationDate() != null && file2.getModificationDate() != null) {
                int res = file2.getModificationDate().compareTo(file2.getModificationDate());
                if (res > 0) {
                    return 1;
                } else if (res < 0) {
                    return -1;
                }
            }
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof FileComparator) {
            return true;
        }
        return false;
    }

}
