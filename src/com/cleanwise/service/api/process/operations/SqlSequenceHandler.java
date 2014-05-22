package com.cleanwise.service.api.process.operations;

import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.log4j.Logger;


import com.cleanwise.service.apps.ClientServicesAPI;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Content;
//import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import com.cleanwise.service.api.APIServiceAccessException;

public class SqlSequenceHandler extends ClientServicesAPI /*ApplicationServicesAPI*/ {

    public static final String USER_NAME = "SqlSequenceHandler";

    private static final Logger log = Logger.getLogger(SqlSequenceHandler.class);

    public void execSequenceOfSql(ArrayList sqlList) 
	throws APIServiceAccessException,NamingException,RemoteException {
        log.info("Start of performing the sequence of sql.");

        if (sqlList == null) {
            log.info("The sequence of sql is not defined.");
            return;
        }
        if (sqlList.size() == 0) {
            log.info("The sequence of sql is empty.");
            return;
        }

        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        PrintStream infoPrint = new PrintStream(infoStream);

        APIAccess factory = new APIAccess();
        Content contentEjb = factory.getContentAPI();

        try {
			contentEjb.execSequenceOfSql(sqlList);
        } catch (Exception ex) {
            log.error("An errors occurred. Interrupting of performing the sequence of sql.");
            log.error(ex.getMessage());
            infoPrint.println(ex.getMessage());
            ex.printStackTrace(infoPrint);
            throw new RemoteException(infoStream.toString());
        }

        log.info("Finish of performing the sequence of sql.");
    }
}
