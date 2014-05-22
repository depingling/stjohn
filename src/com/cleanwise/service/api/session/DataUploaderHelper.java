package com.cleanwise.service.api.session;

/**
 * Title:        DataUploaderHelper
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendwise, Inc.
 */


import com.cleanwise.service.api.util.TableColumnInfo;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.io.InputStream;
import javax.ejb.EJBObject;

public interface DataUploaderHelper extends EJBObject {

    public static final String DATE_PATTERN  = "DATE_PATTERN";
    public static final String NUMBER_PATTERN  = "NUMBER_PATTERN";

    public boolean isExistsDatabaseTable(String table, String namespace)
        throws RemoteException, SQLException;

    public List<TableColumnInfo> getTableColumnsInfo(String table, String namespace)
        throws RemoteException, SQLException;

    public void createDatabaseTable(String userName, String table, String namespace, 
        List<TableColumnInfo> columnsInfo)
        throws RemoteException, SQLException;

    public void deleteDatabaseTable(String userName, String table, String namespace)
        throws RemoteException, SQLException;

    public void deleteAllRowsFromDatabaseTable(String userName, String table, String namespace)
        throws RemoteException, SQLException;

    public void insertRowIntoDatabaseTable(String userName, String table, String namespace,
        List<TableColumnInfo> columnsInfo, List<String> values, 
        Map<String, String> dataFormats, int rowNum, boolean insertRowNumFl)
        throws RemoteException, SQLException;

    public TreeSet<String> getStringColumnValues(String table, String databaseSchema,
        String columnName) throws RemoteException, SQLException;

    public void updateBlobColumnValues(String table, String databaseSchema,
        String anchorColumnName, String anchorColumnValue, String columnName,
        byte[] bytes) throws RemoteException, SQLException;

}
