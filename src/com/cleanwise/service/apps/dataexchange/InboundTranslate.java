package com.cleanwise.service.apps.dataexchange;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.EventPropertyDataVector;
import com.cleanwise.service.api.value.InboundEventData;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.api.value.TradingPartnerDescView;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.view.utils.Constants;

/**
 * Class to make anisochronous request order.
 *
 */
public class InboundTranslate extends Translator {
	protected Logger log = Logger.getLogger(this.getClass());
	String className = "InboundTranslate";
	private Integer eventId = 0;
	
	
	/**
	 * @return the eventId
	 */
	public Integer getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public InboundTranslate() throws Exception {
		super();
	}

	public InboundTransaction processInboundRequest(String fileName, String partnerKey, Object dataContents,
    		URI sourceURI, Map propertyMap)
            throws Exception {

		return processInboundRequest(fileName,partnerKey,dataContents,sourceURI,propertyMap,0,0);
	}
    /**
     * Invoke to make inbound translation request.
     *
     * @param dataContents - inbound data that stored in byte array.
     * @param fileName - inbound file name.
     * @param partnerKey - trading partner key.
     * @throws Exception - Throws if was any problems.
     */
    public InboundTransaction processInboundRequest(String fileName, String partnerKey, Object dataContents,
    		URI sourceURI, Map propertyMap, Integer currentEventId, Integer parentEventId)
            throws Exception {

    	if (propertyMap != null){

    		Iterator iter = propertyMap.keySet().iterator();
    		while (iter.hasNext()) {
				String name = (String) iter.next();
				Object value = propertyMap.get(name);
				log.info("XXX " + name + " = " + value);
			}

    		if(parentEventId>0){
    			propertyMap.put("pParentEventId", parentEventId);
    		}
    		log.info("XXX pParentEventId" + " = " + parentEventId);
    	}

    	setEventId(currentEventId);
        setInputFilename(fileName);
        setPartnerKey(partnerKey);
        setSourceURI(sourceURI);
        setPropertyMap(propertyMap);
        setDataContents((byte[])dataContents);

        InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream((byte[])dataContents));
        if (inputStream.available() == 0){
        	String ignoreEmptyFileStr = (String) propertyMap.get("ignoreEmptyFile");

        	if (ignoreEmptyFileStr != null && ignoreEmptyFileStr.equalsIgnoreCase("true")){
        		return null;
        	}else{
        		throw new Exception("Stream size is 0 - " + fileName);
        	}
        }

        try{
        	int readAhead = 2 * 1000000;//max size of read ahead, 2 megs
							            //if we go beyond this (hudge file to translate)
							            //use the file we are writting out
        	inputStream.mark(readAhead);
        	InboundTransaction handler = constructHandler(inputStream, fileName, partnerKey);

        	inputStream.reset();
        	inputStream.mark(readAhead);
            translate(inputStream,handler);
            return handler;
        }finally{
            if(inputStream!=null){
            	inputStream.close();
            }
        }
    }
    /**
     *creates the Inbound transaction class based off the file name, and the file contents, etc.
     */
    private InboundTransaction constructHandler(InputStream inputStrem, String pStreamName, String partnerKey)
            throws Exception{

        InboundTransaction handler = null;

        //try to determine type by the first couple chars in the file
        String idInFile=null;
        String suffix = null;

        if(pStreamName != null){
            suffix = getFileExtension(pStreamName);
        }

        if("xls".equalsIgnoreCase(suffix)){
            /*
			Don't do anything, this causes all kinds of problems and is not ever used.
			Also now there is a bug with new POI that closes the stream and causes failures
                        further up the line.
            ExcelReader er = new ExcelReader(inputStrem);
            //always read in the second line in case they send over a header
            Object xlsCell = er.getCell(1,0);
            //if there was nothing in second line look at the first
            if(xlsCell == null){
                xlsCell = er.getCell(0,0);
            }

            idInFile = xlsCell.toString();*/

        }
        else if("xml".equalsIgnoreCase(suffix)){
        	idInFile = "xml";
        	/** Comment out following code since it failed on parser class that determined by contents.
        	 * For example cXML type document
        	TradingPartnerDescView tpdv = getTradingPartnerDescViewFromName(pStreamName);
        	Class c = Class.forName(tpdv.getTradingProfileConfigData().getClassname());
        	Object o = c.newInstance();
        	if (o instanceof InboundXMLSuper){
	            logInfo("constructHandler()=> Run XMLInboundTranslator()");
	            handler = new InboundXMLHandler(pStreamName);
        	}*/
        }
        else{
            idInFile = getIdFromFile(inputStrem);
            logInfo("constructHandler()=> idInFile=" + idInFile);
        }

        if(Utility.isSet(idInFile) && handler == null){
            if(idInFile.startsWith("ISA")){
            	logInfo("constructHandler()=> Run InboundEdiHandler");
                //this is an EDI file
                handler = (InboundTransaction) Class.forName("com.cleanwise.service.apps.edi.InboundEdiHandler").newInstance();
            }
            else if(idInFile.equalsIgnoreCase("xml")){
                handler = new InboundXMLHandler(pStreamName);
            }
        }

        //figure out the type by the file name
        if(handler == null){
            try{
                handler = getHandlerFromName(partnerKey, pStreamName);
            }catch(Exception e){
                logInfo("constructHandler()=> could not determine class by parent " + partnerKey + " and file name: " + pStreamName + "::" + e.getMessage());
            }
        }

        //if all else fails maybe it is an EDI file and we just missed it.
        if(handler == null){
            logInfo("constructHandler()=> Exception using default handler");
            handler = (InboundTransaction) Class.forName("com.cleanwise.service.apps.edi.InboundEdiHandler").newInstance();
        }

        return handler;
    }

    public ByteArrayOutputStream getOutputExitingOutputStream(){
        return getOutputStream();
    }

    private String getIdFromFile(InputStream tempFis) throws IOException {

        boolean foundStart = false;
        StringBuffer idInFileBuf = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(tempFis));
        
        int ct = 0;

        while (true){
        	ct ++;
        	char ch = (char)reader.read();
        	if (ch >= 0)
            if(Character.isLetter(ch) || Character.isDigit(ch)){
                foundStart = true;
                idInFileBuf.append(ch);
            }else{
                //if we find a non blank and we have already found start of first identifier
                //break out of loop.  
                if(foundStart){
                    break;
                }
            }
        	// include infinite loop check of 100 characters
        	if(idInFileBuf.length() > 100){
                break;
            }
            
        }

        return idInFileBuf.toString();
    }

    /**
     *Once the handler is determined the translation is done by this method.
     *@returns true if there was something to translate, false otherwise, handler.isFail()
     *should be checked for success or failure.
     */
    private void translate(InputStream pIn,InboundTransaction handler)
    throws Exception{
        logInfo("Using parsing class: " + handler.getClass().getName());

        handler.setTranslator(this);
        if(handler instanceof StreamedInboundTransaction){
        	try{
	        	StreamedInboundTransaction sihandler = (StreamedInboundTransaction) handler;
	            sihandler.translate(pIn,getFileExtension(getInputFilename()));
        	}catch(Exception e) {
        		String errorMsg = e.getMessage() != null ? e.getMessage() : Constants.FILE_PARSING_FAILED;
        		processEmailAcknowladgement( handler,errorMsg, true);
        		throw new Exception(errorMsg+" - " + getInputFilename());
        	}
        }else{
            /****Start Reading in entire File ****/
        	BufferedReader br = new BufferedReader(new InputStreamReader(pIn));
            StringBuffer sb = new StringBuffer();
            String s;
            try {
                while(true) {
                    s = br.readLine();
                    if (s == null)
                        break;
                    sb.append(s+"\n");
                }
            }catch (EOFException e) {;}
            pIn.close();
            s = new String(sb);
            if (s.length() == 0){
            	String errorMsg = Constants.NO_INPUT_DATA_IN_FILE;
            	processEmailAcknowladgement( handler,errorMsg, true);
            	throw new Exception(errorMsg+" - " + getInputFilename());
            }
            /****End Reading in entire File ****/
            try{
            	handler.translate(s);
            }catch(Exception e){
            	String errorMsg = e.getMessage() != null ? e.getMessage() : Constants.FILE_PARSING_FAILED;
            	processEmailAcknowladgement( handler,errorMsg, true);
            	throw new Exception(e.getMessage(),e);
            }
        }

        processEmailAcknowladgement( handler, "", false);

        logInfo("handler.isFail():"+handler.isFail());
        if (!handler.isFail()){
        	/*if (!processIntegrationRequests(handler.getRequestsToProcess()))
        		throw new Exception("Failed to process integration requests.");*/
        	if (!processIntegrationRequestsChunks(handler))
        		throw new Exception("Failed to process integration requests.");
        }

        logInfo(handler.getTranslationReport());
        if (handler.isFail()){
        	throw new Exception(handler.getTranslationReport());
        }
    }

    //STJ-3712
    private void processEmailAcknowladgement(InboundTransaction handler,String errorMessage, boolean failed) throws Exception {
    	log.info("IN processEmailAcknowladgement");
		try{
			String emailAddress = getConfigurationProperty(RefCodeNames.ENTITY_PROPERTY_TYPE.EMAIL_ACKNOWLEDGE);
			//Check for event id being greater than 0.  This is so that we validate that we are able to check for duplicates.  This
			//does mean we are unable to send notifications on streaming transactions which is considered ok for now, if this check is
			//removed a way of not sending the same email every time a file is reprocessed is needed.
			if (Utility.isSet(emailAddress) && getEventId() > 0)  {

				java.util.Date date = new java.util.Date();
				java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("EEEE, MMMM d, yyyy hh:mm a");

				String message = Constants.FILE_NAME +getInputFilename() + "\n" +
					Constants.PROCESS_DATE + fmt.format(date) + "\n"+//Friday, February 26, 2010 4:51 PM;
					"\n"+Constants.FILE_STATUS;
				String status = "";
				if (handler.isFail() || failed) {
					status = Constants.PROCESSING_FAILED;
					message +=status+"\n"+Constants.ERROR+errorMessage;
				}else {
					status = Constants.PROCESSED_SUCCESSFULLY;
					message +=status;
				}
				log.info("[InboundTranslate].processEmailAcknowladgement() ---> message =" + message);
				//subject is a user friendly status, the actual result, and the event id.
				String defaultSubject = Constants.FILE_LOADER_ACKNOWLEDGEMENT+status+" eid="+getEventId();
				//String subject = ((InboundFlatFile)handler).getConfigurationPropertyValue(RefCodeNames.ENTITY_PROPERTY_TYPE.EMAIL_ACKNOWLEDGE_SUBJ);

				
				if(!APIAccess.getAPIAccess().getEmailClientAPI().wasThisEmailSentNoTx(defaultSubject, emailAddress)){
					log.info("Sending Acknowladgement email");
					EventEmailDataView eventEmailData = new EventEmailDataView();
					eventEmailData.setEventEmailId(0);
					eventEmailData.setFromAddress(Constants.EMAIL_ADDR_NO_REPLY);
					eventEmailData.setToAddress(emailAddress);
					eventEmailData.setSubject(defaultSubject);
					eventEmailData.setEmailStatusCd(Event.STATUS_READY);
					eventEmailData.setText(message);
					EventData eventData = Utility.createEventDataForEmail();
					EventEmailView eev = new EventEmailView(eventData, eventEmailData);
					Event eventEjb = APIAccess.getAPIAccess().getEventAPI();
					eventEjb.addEventEmailNoTx(eev, "InboundTranslate");
				}else{
					log.info("Not sending Acknowladgement email as email was already sent");
				}
			}else if(Utility.isSet(emailAddress) && getEventId() == 0){
				log.info("Not sending Acknowladgement email as event id was 0");
			}
		}catch(Exception e){
			//don't do anything else, we don't want to fail a transaction as we couldn't send the email.
			log.fatal("COULD NOT SEND ERROR ACKNOWLADGMENT EMAIL!!",e);
		}
	}
    
    /**
     *when a new handler is found through the trading partner setup this will handle
     *the initialization of the various other bits of data that comes with find that
     *configuration (Trading Partner Mappings, Config Data, Etc) and making it avaliable
     *to the builders.
     */
    private InboundTransaction processFoundHandlerAction(TradingPartnerDescView desc)
    throws ClassNotFoundException, InstantiationException,IllegalAccessException{
    	setTradingPartnerDescView(desc);
        try{
        	InboundTransaction handler = (InboundTransaction) Class.forName(desc.getTradingProfileConfigData().getClassname().trim()).newInstance();
        	return handler;
        }catch(ClassNotFoundException e){
        	throw new ClassNotFoundException("Class: ["+desc.getTradingProfileConfigData().getClassname().trim()+"] was not found on the classpath");
        }
    }

    /**
     * Initializes a transaction based off the stream name (generally the file name, or some name in a url etc)
     *
     */
    public InboundTransaction getHandlerFromName(String partnerKey, String pStreamName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
            TradingPartnerDescView tpdv = null;
            try{
                tpdv = partnerSvc.getTradingPartnerInfoByPattern(partnerKey,pStreamName);
            }catch(Exception e){
                logInfo("Could not determin trading profile by pattern: streamName="+pStreamName + ", partnerKey="+partnerKey);
                e.printStackTrace();
            }

            if (tpdv != null){
                logInfo("determined handler by file name");
                return processFoundHandlerAction(tpdv);
            }
            return null;
    }

    /**
     * Initializes a transaction based off the stream name (generally the file name, or some name in a url etc)
     *
     */
    public TradingPartnerDescView getTradingPartnerDescViewFromName(String pStreamName) throws Exception {
            TradingPartnerDescView tpdv = null;
            try{
                tpdv = partnerSvc.getTradingPartnerInfoByPattern(getPartnerKey(),pStreamName);
            }catch(Exception e){
                throw new Exception("ERROR Could not find trading profile by pattern for partner key: '"+getPartnerKey() + "' and pattern '" + pStreamName+"'");
            }

            return tpdv;
    }

    public boolean processIntegrationRequestsChunks(InboundTransaction handler)
    throws Exception {

    	IntegrationRequestsVector reqs = handler.getRequestsToProcess();

    	String erpNum = this.getErpNum();
    	int tpId = this.getPartner().getTradingPartnerId();

    	String fName = this.getInputFilename();
    	String partnerKey = this.getPartnerKey();
    	Integer pEventId = 0;
    	if(this.propertyMap!=null && this.propertyMap.get("pParentEventId")!=null){
    		pEventId = (Integer)this.propertyMap.get("pParentEventId");
    	}
    	if(reqs.size() > 0){
            logInfo("InboundTranslate processing "+reqs.size()+" Integration Requests");
            String chunkStr=null;
            if(this.propertyMap!=null && this.propertyMap.get("ChunkSize")!=null){
            	chunkStr = (String)this.propertyMap.get("ChunkSize");
            }

            int chunkSize = 0;
            if(chunkStr != null){
            	try{chunkSize=Integer.parseInt(chunkStr);}catch(Exception e){
            		logInfo("Could not parse ChunkSize continuing");
            	}
            }

            Event eventEjb = APIAccess.getAPIAccess().getEventAPI();

            log.info("Chunk size: "+chunkSize);
        	log.info("Total request size: "+reqs.size());
            if(chunkSize>0 && reqs.size()>chunkSize){

            	Iterator it = reqs.iterator();

            	IntegrationRequestsVector tmpIntegrationRequests = new IntegrationRequestsVector();

            	int ct = 0;
            	int chunkCt = 0;

            	while(it.hasNext()){
            		ct ++;
            		tmpIntegrationRequests.add(it.next());
            		if(ct >= chunkSize || !it.hasNext()){
            			ct = 0;
            			chunkCt++;
            			logInfo("Sending chunk "+chunkCt);

            			EventData eventData = Utility.createEventDataForProcess();
            			ProcessData process = APIAccess.getAPIAccess().getProcessAPI()
            			.getProcessByName(RefCodeNames.PROCESS_NAMES.PROCESS_INBOUND_CHUNKS);

            			EventProcessView epv = new EventProcessView(eventData, new EventPropertyDataVector(), getEventId());

            			epv.getProperties().add(Utility.createEventPropertyData("process_id",
           		             Event.PROPERTY_PROCESS_TEMPLATE_ID,
           		             new Integer(process.getProcessId()),
           		             1));

            			epv.getProperties().add(Utility.createEventPropertyData("pReqs",
            					Event.PROCESS_VARIABLE,
            					tmpIntegrationRequests,
            					1));


            			epv.getProperties().add(Utility.createEventPropertyData("pTradingPartnerId",
            					Event.PROCESS_VARIABLE,
            					new Integer(tpId),
            					2));

            			epv.getProperties().add(Utility.createEventPropertyData("pPartnerKey",
            					Event.PROCESS_VARIABLE,
            					partnerKey,
            					3));

            			epv.getProperties().add(Utility.createEventPropertyData("pParentFileName",
            					Event.PROCESS_VARIABLE,
            					fName,
            					4));

            			epv.getProperties().add(Utility.createEventPropertyData("pChunkSize",
            					Event.PROCESS_VARIABLE,
            					chunkStr,
            					5));

            			if(pEventId>0){
            				epv.getProperties().add(Utility.createEventPropertyData("pParentEventId",
            						Event.PROCESS_VARIABLE,
            						pEventId,
            						6));
            			}
            			EventProcessView newEvent = eventEjb.addEventProcess(epv, "InboundTranslate");
            			if(newEvent == null || newEvent.getEventData() == null){
            				log.fatal("InboundTranslate:Event not added ");
            			}else{
            				log.info("Added event id "+newEvent.getEventData().getEventId());
            			}

            			tmpIntegrationRequests.clear();
            		}
            	}

            }else{
            	//old method
            	processIntegrationRequests(reqs);
            }
    	}
    	return true;
    }

    public boolean processIntegrationRequests(IntegrationRequestsVector reqs)
    throws Exception {
        if(reqs.size() > 0){
            logInfo("InboundTranslate processing "+reqs.size()+" Integration Requests");
            String chunkStr = System.getProperty("ChunkSize");
            int chunkSize = 0;
            if(chunkStr != null){
            	try{chunkSize=Integer.parseInt(chunkStr);}catch(Exception e){
            		logInfo("Could not parse ChunkSize continuing");
            	}
            }
            if(chunkSize>0){
            	log.info("Using chunk size of "+chunkSize);
            	log.info("Total request size: "+reqs.size());
            	Iterator it = reqs.iterator();
            	IntegrationRequestsVector tmpIntegrationRequests = new IntegrationRequestsVector();
            	int ct = 0;
            	int chunkCt = 0;
            	while(it.hasNext()){
            		ct ++;
            		tmpIntegrationRequests.add(it.next());
            		if(ct >= chunkSize){
            			ct = 0;
            			chunkCt++;
            			logInfo("Sending chunk "+chunkCt);
            			if (!processIntegrationRequests(tmpIntegrationRequests, getErpNum()))
            				return false;
            			tmpIntegrationRequests.clear();
            		}
            	}
            	if(!tmpIntegrationRequests.isEmpty()){
            		logInfo("Sending last chunk");
            		if (!processIntegrationRequests(tmpIntegrationRequests, getErpNum()))
        				return false;
            	}

            }else{
            	if (!processIntegrationRequests(reqs, getErpNum()))
            		return false;
            }
        }
        return true;
    }

    /**
     *Returns the extension of a file:
     *foo.bar returns bar
     *foobar returns foobar
     */
    private String getFileExtension(String pFileName){
        java.util.StringTokenizer tok = new java.util.StringTokenizer(pFileName,".");
        String suffix = null;
        while(tok.hasMoreTokens()){
            suffix = tok.nextToken();
        }
        return suffix;
    }

    URI mSourceURI;
    public URI getSourceURI(){
        return mSourceURI;
    }
    public void setSourceURI(URI pSourceURI){
        mSourceURI = pSourceURI;
    }
    public void setSourceURI(File pSourceFile){
        mSourceURI = pSourceFile.toURI();
    }

    private Map propertyMap;
    public void setPropertyMap(Map propertyMap) {
		this.propertyMap = propertyMap;
	}
	public Map getPropertyMap() {
		return propertyMap;
	}
	
	private byte[] dataContents;
	public void setDataContents(byte[] dataContents) {
		this.dataContents = dataContents;
	}

	public byte[] getDataContents() {
		return dataContents;
	}

    /**
     *Tranlates an input stream.  If the input stream is really large then we will
     *use the logged version which we write out to file.  Otherwise it will buffer it in
     *memory
     *@param pStreamSource this may in fact be null if this stream does not have an identifiable stream source
     */
    public InboundTransaction translateByInputStream(InputStream pIn, String optionalStreamType, URI pStreamSource)
    throws Exception{
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len;
		while ((len = pIn.read(buf)) > 0) {
			bos.write(buf, 0, len);
		}
		byte[] byteArray = bos.toByteArray();

        String partnerKey = "XX"; //TODO: not sure
        InboundTransaction handler;
        try{
        	handler = processInboundRequest(optionalStreamType, partnerKey, byteArray, pStreamSource, null,0,0);
        }catch(Exception e){
        	e.printStackTrace();
        	throw new Exception(e.getMessage());
        }
    	if (!handler.isFail()){
    		InboundEventData eventData = InboundEventData.generateInboundEvent(optionalStreamType, byteArray, partnerKey, pStreamSource);
            eventData.setStatus(Event.STATUS_PROCESSED);
            IntegrationRequestsVector reqs = new IntegrationRequestsVector();
            reqs.add(eventData);
            if (!processIntegrationRequests(reqs))
        		throw new Exception("Failed to process inbound event requests.");
        }
        return handler;
    }

	public InboundTransaction translateFileByHandlerName(String filePath, String handlerName) throws Exception{
		InboundTransaction handler = (InboundTransaction) Class.forName(handlerName).newInstance();
		File file = new File(filePath);
		setInputFilename(file.getName());
		InputStream inputStrem = new BufferedInputStream(new FileInputStream(file));
		translate(inputStrem, handler);
		return handler;
	}

	public String getValueFromMappingByPropertyTypeCd(String pPropertyTypeCd){

		if ( null == pPropertyTypeCd || pPropertyTypeCd.length() == 0 ) {
			return null;
		}

		TradingPropertyMapDataVector mapping = getTradingPropertyMapDataVector();
		Iterator it = mapping.iterator();
		while(it.hasNext()){
			TradingPropertyMapData map = (TradingPropertyMapData) it.next();
			if ( pPropertyTypeCd.equals(map.getPropertyTypeCd())
					&& map.getHardValue() != null
			) {
				return map.getHardValue();
			}
		}

		return null;
	}
	
}
