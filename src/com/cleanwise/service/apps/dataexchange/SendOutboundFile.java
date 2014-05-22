package com.cleanwise.service.apps.dataexchange;

import java.util.HashMap;
import java.util.Iterator;

import com.cleanwise.service.apps.FileTransferClient;
import com.cleanwise.service.apps.SendFile;

import org.apache.log4j.Logger;


public class SendOutboundFile {
        protected Logger log = Logger.getLogger(this.getClass());
	FileTransferClient ftpc;

	public static String P_USER = "username";
	public static String P_PASSWORD = "password";
	public static String P_FROM_FILENAME = "filename";
	public static String P_TO_FILENAME = "tofilename";
	public static String P_FTPMODE = "ftpmode";
	public static String P_TRANSFER_TYPE = "transfer_type";
	public static String P_PARTNER_KEY = "partnerkey";
	public static String P_PGP_CREDENTIAL = "pgpcredential";
	public static String P_TO_DIR = "todir";
	public static String P_HOSTNAME = "tohost";
	public static String P_PORT = "port";
	public static String P_RESPONSE_CHECK = "responseCheck";

	public void processOutboundRequest(String fileName, String partnerKey, String setType, Object dataContents, HashMap sendParameterMap) throws Exception {

		String show = "";
		String key;

		if (sendParameterMap != null && sendParameterMap.size() != 0) {
			log.info("parameters:");
			Iterator keys = sendParameterMap.keySet().iterator();

			while(keys.hasNext()){
				key=(String)keys.next();
				log.info(key + " is " + sendParameterMap.get(key) + "\n");
			}

			sendParameterMap.put("filename", fileName);

			SendFile sender = new SendFile();
			sender.setProperties(sendParameterMap);
			sender.send((byte[])dataContents);

		}
		else{
			throw new Exception("Outbound send property is not set.");
		}


	}

}
