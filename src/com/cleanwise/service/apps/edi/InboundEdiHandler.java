/*
 * InboundEdiHandler.java
 *
 * Created on October 22, 2002, 9:33 AM
 */

package com.cleanwise.service.apps.edi;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.log4j.Logger;


import com.americancoders.edi.DocumentErrors;
import com.americancoders.edi.EDIDocumentHandler;
import com.americancoders.edi.Envelope;
import com.americancoders.edi.FunctionalGroup;
import com.americancoders.edi.IContainedObject;
import com.americancoders.edi.OBOEException;
import com.americancoders.edi.Segment;
import com.americancoders.edi.Table;
import com.americancoders.edi.TemplateSegment;
import com.americancoders.edi.TransactionSet;
import com.americancoders.edi.x12.X12Envelope;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ElectronicTransactionData;
import com.cleanwise.service.api.value.IntegrationRequestsVector;
import com.cleanwise.service.api.value.InterchangeRequestData;
import com.cleanwise.service.api.value.TradingPartnerDescView;
import com.cleanwise.service.api.value.TradingProfileConfigData;
import com.cleanwise.service.api.value.TradingProfileData;
import com.cleanwise.service.apps.dataexchange.InboundTransaction;
import com.cleanwise.service.apps.dataexchange.InboundTranslate;
import com.cleanwise.service.apps.dataexchange.InterchangeInboundSuper;
import com.cleanwise.service.apps.dataexchange.OutboundTranslate;
import com.cleanwise.service.apps.dataexchange.Translator;
import com.cleanwise.view.utils.Constants;

/**
 *
 * @author  bstevens
 */
public class InboundEdiHandler  extends InterchangeInboundSuper implements EDIDocumentHandler, InboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private CwX12Envelope env = null;
	private FunctionalGroup fg = null;
	private Vector groupErrorCodes = new Vector();
	private Vector setErrorCodes = new Vector();
	private InboundEdiSuper parser = null;// point to current inbound parser
	private int setIncluded;
	//private String icDate, icTime, gsDate, gsTime;
	private Outbound997 ob997 = null;
	private OutboundTranslate obTranslator;
	private HashMap transErrors = new HashMap();
	private boolean suspend997Fl = true;
	private DocumentErrors errors = null;
	
	public void setTransErrors(HashMap pVal){
		transErrors = pVal;
	}

	public HashMap getTransErrors(){
		return transErrors;
	}

	OutboundTranslate getOutTranslator(){
		return obTranslator;
	}

	/** do nothing when an envelope is ended
	 */
	public void endEnvelope(Envelope inEnv){
		//translateInterchange();
	}
	/** do nothing when an fg is ended
	 */
	public void endFunctionalGroup(FunctionalGroup inFG){
	}

	/** create a Valid XML document for each ts found
	 */
	public void endTransactionSet(TransactionSet inTS){;}

	/** do nothing when an seg is ended
	 * <br>Note that these segments are only envelope and fg segments NOT SEGMENTS inside of Transaction Sets
	 */
	public void endSegment(Segment inSeg){
	}

	/** do nothing when an envelope is started */
	public void startEnvelope(Envelope inEnv) {
		env = (CwX12Envelope) inEnv;
	}



	/** called when an FunctionalGroup object is created
	 * @param inFG FunctionalGroup found
	 */

	public void startFunctionalGroup(FunctionalGroup inFG) {
		fg = inFG;
		env.addFunctionalGroup(inFG);
	}

	public void startParsing(java.io.Reader inReader)
	throws OBOEException {
		;
	}

	/** called when an Segment object is created
	 * <br>only called for segments at the Envelope and functionalGroup level
	 * does not get called for segments within TransactionSet
	 * @param inSeg  Segment found
	 */
	public void startSegment(Segment inSeg) {

		if (inSeg.getID().compareTo(CwX12Envelope.idInterchangeHeader) == 0)
			env.addSegment(inSeg);
		else
			if (inSeg.getID().compareTo(CwX12Envelope.idInterchangeTrailer) == 0)
				env.addSegment(inSeg);
			else
				if (inSeg.getID().compareTo(CwX12Envelope.idGradeofServiceRequest) == 0)
					env.addSegment(inSeg);
				else
					if (inSeg.getID().compareTo(CwX12Envelope.idDeferredDeliveryRequest) == 0)
						env.addSegment(inSeg);
					else
						if (inSeg.getID().compareTo(CwX12Envelope.idInterchangeAcknowledgment) == 0)
							env.addSegment(inSeg);
						else
							if (inSeg.getID().compareTo(CwX12FunctionalGroup.idHeader) == 0)
								fg.addSegment(inSeg);
							else
								if (inSeg.getID().compareTo(CwX12FunctionalGroup.idTrailer) == 0)
									fg.addSegment(inSeg);

	}



	/** called when an TransactionSet object is created
	 * @param inTS TransactionSet found
	 */
	public void startTransactionSet(TransactionSet inTS) {
		fg.addTransactionSet(inTS);

	}

	java.util.Hashtable incomingDataParserClasses = new
	java.util.Hashtable();

	private TradingPartnerDescView getTradingPartnerDescView
	(int pProfileId, String pTsType ) 
	throws RemoteException {
		String key = pProfileId+":"+pTsType;
		if ( null == incomingDataParserClasses.get(key) ) {
			log.info(" class not cached, lookup for pTsType=" + key);
			Object obj = Translator.getTradingPartnerDescView(pProfileId,pTsType, "IN");
			if(obj == null){
				throw new RuntimeException("Trading Profile ("+pProfileId+") not configured for: "+pTsType+" with direction: IN");
			}
			incomingDataParserClasses.put(key, obj);
		}
		return (TradingPartnerDescView)incomingDataParserClasses.get(key);
	}

	public void extractHeaderISA(Segment isa)
	{
		profile.setInterchangeSender(isa.getDataElement(6).get());//int sender info 15-15
		profile.setInterchangeReceiver(isa.getDataElement(8).get());//int id 15-15
		//icDate = isa.getDataElement(9).get();// int dt 6-6
		//icTime = isa.getDataElement(10).get();//int tm 4-4
		profile.setInterchangeControlNum(Integer.parseInt(isa.getDataElement(13).get()));// int cntrl number 9-9
		profile.setAcknowledgmentRequested(isa.getDataElement(14).get());//ack req 1-1
		profile.setTestIndicator(isa.getDataElement(15).get());//test ind 1-1
	}
	public void extractHeaderGS(Segment gs)
	{
		getTranslator().setGroupType(gs.getDataElement(1).get());
		profile.setGroupSender(gs.getDataElement(2).get());
		profile.setGroupReceiver(gs.getDataElement(3).get());
		//gsDate = gs.getDataElement(4).get();
		//gsTime = gs.getDataElement(5).get();
		profile.setGroupControlNum(Integer.parseInt(gs.getDataElement(6).get()));
	}
	public int extractHeaderST(Table table)
	{
		int errorCount = 0;
		Segment st = table.getSegment("ST");
		try{
			transactionD.setSetControlNumber(Integer.parseInt(st.getDataElement(2).get()));
		}catch(Exception e1){
			parser.getErrorMsgs().add("Error - Missing or Invalid Transaction Set Control Number");
			setErrorCodes.add("7");
			errorCount++;
		}
		return errorCount;
	}

	public void extractHeaderIEA(Segment iea)
	{
		//int number of function group included in current interchange
		int count = Integer.parseInt(iea.getDataElement(1).get());
		if (count != env.getFunctionalGroupCount())
			throw new RuntimeException("Error - Envelope count = " + count + ", actual count = " + env.getFunctionalGroupCount() + " (Envelope # " + profile.getInterchangeControlNum() + ")");

		//int interchange control number
		int num = Integer.parseInt(iea.getDataElement(2).get());
		if (num != profile.getInterchangeControlNum())
			throw new RuntimeException("Error - ISA Control number = " + profile.getInterchangeControlNum() + "   IEA Control number = " + num);
	}

	public void extractHeaderGE(Segment ge)
	{
		//int number of transaction set included in current function group
		setIncluded = Integer.parseInt(ge.getDataElement(1).get());
		if (setIncluded != fg.getTransactionSetCount())
		{
			groupErrorCodes.add("5");
			throw new RuntimeException("Error - GE count = " + setIncluded + ", actual count = " + fg.getTransactionSetCount() + " (GS # " + profile.getGroupControlNum() + ")");
		}

		//int functional group control number
		int num = Integer.parseInt(ge.getDataElement(2).get());
		if (num != profile.getGroupControlNum())
		{
			groupErrorCodes.add("4");
			throw new RuntimeException("Error - GE Control number = " + num + "   GS Control number = " + profile.getGroupControlNum());
		}
	}

	public int extractHeaderSE(TransactionSet ts)
	{
		int errorCount = 0;
		Segment se = null;
		Table table;

		try{
			table = ts.getSummaryTable();
			if (table != null){
				se = table.getSegment("SE");
			}
			else {
				try{
					table = ts.getHeaderTable();
					se = table.getSegment("SE");
				}catch(OBOEException e1) {
					try{
						table = ts.getDetailTable();
						se = table.getSegment("SE");
					}catch(OBOEException e2) {
						se = null;
					}
				}
			}
		}catch(OBOEException e) {
			se = null;
		}

		if (se == null)
		{
			parser.getErrorMsgs().add("Error - Transaction Set Trailer Missing (ST # " + transactionD.getSetControlNumber() + ")");
			setErrorCodes.add("2");
			errorCount++;
			return errorCount;
		}
		//int number of segment included in current transaction set
		int count = Integer.parseInt(se.getDataElement(1).get());
		if (count != ts.getSegmentCount())
		{
			//errorCount++;
			parser.getErrorMsgs().add("Error - SE count = " + count + ", actual count = " + ts.getSegmentCount() + " (ST # " + transactionD.getSetControlNumber() + ")");
			setErrorCodes.add("4");
		}

		//int transaction set control number
		try{
			int num = Integer.parseInt(se.getDataElement(2).get());
			if (num != transactionD.getSetControlNumber())
			{
				parser.getErrorMsgs().add("Error - SE Control number = " + num + "   ST Control number = " + transactionD.getSetControlNumber());
				setErrorCodes.add("3");
				errorCount++;
			}
		}catch(Exception e1){
			parser.getErrorMsgs().add("Error - Missing or Invalid Transaction Set Control Number (7)");
			setErrorCodes.add("7");
			errorCount++;
		}
		return errorCount;
	}



	/**
	 * Input ediData contain multiple envelopes, but X12DocumentParser only take
	 * one envelope data. This function will seperate all the envelope and store
	 * them in the vector that returned
	 */
	private Vector unbundleInterchanges(String ediData)
	{
		Vector ICDatas = new Vector(); // a list of seperated envelope data

		String currIC;  // current envelope data
		int fromIndex = 0, nextIndex = 0; // beginning index of current and next envelope data

		fromIndex = getNextIndexForISA(ediData, 0);
		while (fromIndex >= 0)
		{
			nextIndex = getNextIndexForISA(ediData, fromIndex + 3);
			if (nextIndex == -1)
				currIC = ediData.substring(fromIndex);
			else
				currIC = ediData.substring(fromIndex, nextIndex);
			fromIndex = nextIndex;
			ICDatas.add(currIC);
		}

		return ICDatas;
	}
	// get index position on ediDate of next ISA segment
	private int getNextIndexForISA(String ediData, int fromIndex)
	{
		int nextIndex = ediData.indexOf("ISA", fromIndex);
		// ISA segment contain 106 characters
		if (nextIndex >= 0 && ediData.length() > nextIndex + 106)
		{
			// match element seperator
			if (ediData.charAt(nextIndex + 3) != ediData.charAt(nextIndex + 103))
				return (getNextIndexForISA(ediData, nextIndex + 3));
			// check to see if it is test or production flag
			if (ediData.charAt(nextIndex + 102) != 'P' && ediData.charAt(nextIndex + 102) != 'T')
				return (getNextIndexForISA(ediData, nextIndex + 3));
			return nextIndex;
		}
		return -1;
	}


	/**
	 *@param ediData - String edi data parsed
	 */
	public void translate(String ediData) throws Exception
	{
		setFail(false);
		getInterchanges().clear();

		if (ediData == null || ediData.equals(""))
		{
			throw new Exception("No input data");
		}

		CwX12DocumentParser parser = new CwX12DocumentParser(); // create a parser object
		parser.registerHandler(this); // register with the parser
		Vector ICDatas = unbundleInterchanges(ediData);

		//retrieve the existing documentFactory and saxParser property values
		String documentBuilderFactory = System.getProperty(Constants.XML_DOCUMENT_BUILDER_FACTORY);
		String saxParserFactory = System.getProperty(Constants.XML_SAX_PARSER_FACTORY);
		
		try{
			if(ICDatas==null || ICDatas.size() == 0){
				throw new IllegalStateException("Document has data, but there are interchanges (malformed EDI)!");
			}
			
			//specify the usage of the xerces documentFactory and saxParser property values
			System.setProperty(Constants.XML_DOCUMENT_BUILDER_FACTORY, Constants.XML_DOCUMENT_BUILDER_FACTORY_VALUE_XERCES);
			System.setProperty(Constants.XML_SAX_PARSER_FACTORY, Constants.XML_SAX_PARSER_FACTORY_VALUE_XERCES);
			
			for (int i = 0; i < ICDatas.size() && isFail() == false; i++)
			{
				log.info( "ICDatas, i=" + i + " \n" + (String)ICDatas.get(i) );
				parser.parseDocument((String)ICDatas.get(i));
				translateInterchange();
			}
			
			if (ob997 != null && !isFail()) {
				ob997.buildInterchangeTrailer();
				ob997.getTransactionObject().setKeyString("Acknowledged EDI file: " + interchangeD.getEdiFileName());
			}
			if (isFail()){
				throw new Exception(this.parser.getFormatedErrorMsgs());
			}
		}
		catch (OBOEException e1)
		{
			errors = parser.getDocumentErrors();
			boolean fatalErrorFl = false;

			for (int i = 0; i < errors.getErrorCount(); i++)
			{
				boolean fl = true;
				IContainedObject isc = errors.getContainer(i);
				if(isc instanceof Segment) {
					Segment seg = (Segment) isc;
					String segmentCode = seg.getID();
					HashMap knownErrorsHM = getTransErrors();
					Collection col = knownErrorsHM.values();
					MMM: for(Iterator iter = col.iterator(); iter.hasNext();) {
						LinkedList errSegCodeLL =  (LinkedList) iter.next();
						for(Iterator iter1 = errSegCodeLL.iterator(); iter1.hasNext();) {
							String errCode = (String) iter1.next();
							if(errCode.equals(segmentCode)) {
								fl = false;
								break MMM;
							}
						}
					}
				}
				if(fl) fatalErrorFl = fl;
			}
			String notes = getDocumentErrorMessage();
			log.error(notes, e1);
			if (Utility.isSet(notes))
				throw new Exception (notes, e1);
			else
				throw(e1);			
		}
		catch (Exception e2)
		{
			log.error(e2);
			setFail(true);
			throw(e2);

		}
		finally{
			//restore the original existing documentFactory and saxParser property values
			if (Utility.isSet(documentBuilderFactory)) {
				System.setProperty(Constants.XML_DOCUMENT_BUILDER_FACTORY, documentBuilderFactory);
			}
			else {
				System.clearProperty(Constants.XML_DOCUMENT_BUILDER_FACTORY);
			}
			if (Utility.isSet(saxParserFactory)) {
				System.setProperty(Constants.XML_SAX_PARSER_FACTORY, saxParserFactory);
			}
			else {
				System.clearProperty(Constants.XML_SAX_PARSER_FACTORY);
			}
			
			if (getOutTranslator() != null) {
				getOutTranslator().closeOutputStream();
			}
			if ((isFail() || suspend997Fl)
					&& getOutTranslator()!=null
					&& Utility.isSet(getOutTranslator().getOutputFileName()) ) {
				File ackFile = new File(getOutTranslator().getOutputFileName());
				if(ob997!=null && !ob997.isFail()) {
					if(ackFile.exists() && ackFile.isFile()) {
						//IOUtilities.moveFile(ackFile, new File(logDir), null);
					}
				} else {
					if(ackFile.exists() && ackFile.isFile()) {
						ackFile.delete();
					}
				}
			} 
		}

	}

	public String toString(){
		if(parser != null){
			return this.getClass().getName() + ":parser:" + parser.getClass().getName();
		}else{
			return this.getClass().getName();
		}
	}	
	
	public void translateInterchangeHeaderByHandler() throws Exception {
		extractHeaderISA(env.getInterchange_Header());
	}

	public void translateInterchangeTrailer() throws Exception {
		extractHeaderIEA(env.getInterchange_Trailer());
		super.translateInterchangeTrailer();
	}

	public IntegrationRequestsVector getRequestsToProcess(){
		IntegrationRequestsVector returnReqs = super.getRequestsToProcess();
		if (ob997 != null){
			returnReqs.addAll(ob997.getRequestsToProcess());
		}
		return returnReqs;
	}
	public void translateInterchangeContent() throws Exception {
		TransactionSet ts = null;
		TradingProfileData ackProfile = null; // profile for writing 997 acknowledge
		
		
		suspend997Fl = false;
		for (int fgPosition = 0; fgPosition < env.getFunctionalGroupCount(); fgPosition++) {
			TradingProfileData matchProfile = null;
			groupErrorCodes.clear();
			fg = env.getFunctionalGroup(fgPosition);
			setGroupType(fg.getHeader().getDataElement(1).get());

			extractHeaderGS(fg.getHeader());
			extractHeaderGE(fg.getTrailer());
			boolean send997Flag = (!getGroupType().equals("FA"));
			
			int tsCount = fg.getTransactionSetCount();
			for (int tsPosition = 0; tsPosition < tsCount; tsPosition++) {
				setErrorCodes.clear();
				ts = fg.getTransactionSet(tsPosition);
				setSetType(ts.getID());
				if (matchProfile == null){					
					matchProfile = getTradingProfile(profile.getGroupReceiver(), profile.getGroupSender(), "IN", ts.getID());
					if (matchProfile == null) {
						throw new Exception("Trading Partner profile is not set up for Group Sender Id = "
								+ profile.getGroupReceiver() + " and Group Receiver Id = "
								+ profile.getGroupSender() + ", direction = 'IN', setType = " + ts.getID());
					}

					if (send997Flag) {
						ackProfile = getTradingProfile(profile.getGroupReceiver(), profile.getGroupSender(), "OUT", RefCodeNames.EDI_TYPE_CD.T997);
						if (ackProfile == null) {
							throw new Exception("Trading Partner profile is not set up for Group Sender Id = "
									+ profile.getGroupSender() + " and Group Receiver Id = "
									+ profile.getGroupReceiver() + ", direction = 'OUT', setType = " + RefCodeNames.EDI_TYPE_CD.T997);
						}
						
						if (getOutTranslator() == null) {
							obTranslator = new OutboundTranslate();
							obTranslator.setSendParameterMap(((InboundTranslate)translator).getPropertyMap());
							ob997 = new Outbound997();
							ob997.setTranslator(obTranslator);
							obTranslator.setUp1(getErpNum(),0, "997", (InboundTranslate)getTranslator());
							obTranslator.setProfile(ackProfile);  //added for new abstraction
							ob997.setProfile(ackProfile);
							obTranslator.initializeOutputStream(ob997);
							translator.setOutputFileName(getOutTranslator().getOutputFileName());
							ob997.buildInterchangeHeader();	
						} else {
							String ackGroupSender = ackProfile.getGroupSender();
							String ackGroupReceiver = ackProfile.getGroupReceiver();
							if(!ackGroupReceiver.equals(getOutTranslator().getProfile().getGroupReceiver())|| !ackGroupSender.equals(getOutTranslator().getProfile().getGroupSender())) {
								ob997.getTranslator().setProfile(ackProfile);
								FunctionalGroup fg997 = ob997.env.createFunctionalGroup();
								Segment fgHeader = fg997.getHeader();
								ob997.buildHeaderGS(fgHeader);
								ob997.fg = fg997;
							}
						}

						//moved out
						ob997.buildTransactionHeader();
						ob997.buildHeaderAK1(getGroupType(), profile.getGroupControlNum());
					}
				}
				transactionD = createTransactionObject();
				transactionD.setSetStatus(RECA_MSG);// initial to receive ok
				
				// Get the parser for the current transaction.
				String currTsType =  ts.getID();
				TradingPartnerDescView tpDescViewD = getTradingPartnerDescView(matchProfile.getTradingProfileId(), currTsType);
				TradingProfileConfigData profileConfigD = tpDescViewD.getTradingProfileConfigData();
				getTranslator().setTradingPartnerDescView(tpDescViewD);

				if (profileConfigD == null){
					throw new Exception("Error - Transaction Set " + ts.getID() + " Not Supported");
				}
				else{
                                        log.info("Using edi parsing class: "+profileConfigD.getClassname());
					Class c = Class.forName(profileConfigD.getClassname());
					parser = (InboundEdiSuper) c.newInstance();
					parser.setParameter(this, ts);
				}
				
				parser.setValid(true);

				int errorCount = 0;
				errorCount = extractHeaderST(ts.getHeaderTable());
				errorCount += extractHeaderSE(ts);
				if (errorCount > 0)  // error in ST or SE segment
				{
					throw new Exception(parser.getFormatedErrorMsgs());
				}

				if (transactionD.getSetStatus() == RECA_MSG) {
					log.info("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH =============================");
					log.info("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH processing ts #: "+tsPosition);
					log.info("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH transaction type: "+ts.getID() + " Set Control#=" + transactionD.getSetControlNumber());
					DocumentErrors errs = new DocumentErrors();
					ts.validate(errs);
					if (errs.getErrorCount() > 0) {
						parser.setValid(false);
						suspend997Fl = true;
						String notes = "";
						for (int i = 0; i < errs.getErrorCount(); i++) {
							IContainedObject isc = errs.getContainer(i);
							if(isc instanceof Segment) {
								Segment seg = (Segment) isc;
								TemplateSegment template = seg.getTemplate();
								if(template!=null) {
									String segmentCode = template.getID();
									HashMap tErrHM = getTransErrors();
									Integer tsPositionI = new Integer(tsPosition);
									LinkedList errL = (LinkedList) tErrHM.get(tsPositionI);
									if(errL == null) {
										errL = new LinkedList();
										tErrHM.put(tsPositionI,errL);
									}
									errL.add(segmentCode);
									log.info("InboundEdiHandler " +
											"HHHHHHHHHHHHHHHHHHHHHHHHHHHHH error segment: "+
											tsPositionI +" -> " + segmentCode);

								}
							}
							notes += errs.getError()[i] + "\r\n";
						}
						transactionD.setSetStatus(RECR_MSG);
						parser.getErrorMsgs().add(notes);
						setErrorCodes.add("5");
						errorCount++;
						throw new Exception(parser.getFormatedErrorMsgs());
					} else {
						parser.extract();

						if (!parser.getValid()) {
							suspend997Fl = true;
							transactionD.setSetStatus(RECR_MSG);
							setErrorCodes.add("5");
							Vector errV = parser.getErrorMsgs();
							if(errV!=null) {
								log.info(parser.getFormatedErrorMsgs());
							}
							errorCount++;
							throw new Exception(parser.getFormatedErrorMsgs());
						} else {
							parser.processTransaction();
							if (!parser.getValid()) {
								suspend997Fl = true;
								transactionD.setSetStatus(RECR_MSG);
								setErrorCodes.add("5");
								Vector errV = parser.getErrorMsgs();
								if(errV!=null) {
									log.info(parser.getFormatedErrorMsgs());
								}
								errorCount++;
								throw new Exception(parser.getFormatedErrorMsgs());
							}
						}
					}
					//=====================
					log.info("InboundEdiHandler HHHHHHHHHHHHHHHHHHHHHHHHHHHHH trasaction processed");
				}
				transactionD.setException(parser.getFormatedErrorMsgs());
				if (transactionD.getException() != null && !transactionD.getException().equals("")){
					log.info(transactionD.getException());
					transactionD.setSetData(ts.getFormattedText(X12Envelope.X12_FORMAT));
				}

				if (send997Flag) {
					ob997.buildHeaderAK2(transactionD.getSetType(), transactionD.getSetControlNumber());
					String errorCode;
					if (transactionD.getSetStatus() == RECA_MSG)
						errorCode = "A";
					else if (transactionD.getSetStatus() == RECE_MSG)
						errorCode = "E";
					else
						errorCode = "R";

					ob997.buildHeaderAK2AK5(errorCode, setErrorCodes);
				}
			}
			if (send997Flag) {
				ob997.buildHeaderAK9("", setIncluded, groupErrorCodes);
				ob997.buildTransactionTrailer();
			}
		}		
	}

	public String getTranslationReport() {
		String str = "";
		
		if (this.isFail())
		{
			str += "Inbound EDI translation failed. ";
			if (getDocumentErrors() != null)
				str += getDocumentErrorMessage();
			return str;
		}
		else
		{
			if (getTranslator().getOutputFileName() != null){
				str = "A 997 document that acknowledges inbound transactions in file: \r\n" +
				getTranslator().getInputFilename() +
				"\r\n has been generated and saved to file: \r\n" +
				getTranslator().getOutputFileName() + ".\r\n\r\n";
			}
			str += "Inbound EDI translation is success.\r\n";
			str += "Inbound edi filename: " + getTranslator().getInputFilename() + "\r\n\r\n";
			
			str += "Sender          Receiver        Set_Type  Group_Control_Num  Set_Control_Num  Transaction_Status\r\n";
			for (int i = 0; i < getInterchanges().size(); i++) {
				InterchangeRequestData interchangeReqD = (InterchangeRequestData)getInterchanges().get(i);
				for (int j = 0; j < interchangeReqD.getTransactionDataVector().size(); j++) {
					ElectronicTransactionData transactionD = (ElectronicTransactionData)interchangeReqD.getTransactionDataVector().get(j);
					str += Utility.padRight(transactionD.getGroupSender(), ' ', 16)
					+ Utility.padRight(transactionD.getGroupReceiver(), ' ', 16)
					+ Utility.padRight(transactionD.getSetType(), ' ', 10)
					+ Utility.padRight(""+transactionD.getGroupControlNumber(), ' ', 19)
					+ Utility.padRight(""+transactionD.getSetControlNumber(), ' ', 17)
					+ status[transactionD.getSetStatus()] + "\r\n";
				}
			}
		}
		return str;
	}
	
	public DocumentErrors getDocumentErrors() {
		return errors;
	}
	
	public String getDocumentErrorMessage(){		
		String notes = "";
		if (errors != null){
			for (int i = 0; i < errors.getErrorCount(); i++)
			{
				boolean fl = true;
				IContainedObject isc = errors.getContainer(i);
				if(isc instanceof Segment) {
					Segment seg = (Segment) isc;
					String segmentCode = seg.getID();
					HashMap knownErrorsHM = getTransErrors();
					Collection col = knownErrorsHM.values();
					MMM: for(Iterator iter = col.iterator(); iter.hasNext();) {
						LinkedList errSegCodeLL =  (LinkedList) iter.next();
						for(Iterator iter1 = errSegCodeLL.iterator(); iter1.hasNext();) {
							String errCode = (String) iter1.next();
							if(errCode.equals(segmentCode)) {
								break MMM;
							}
						}
					}
				}
				notes = notes + errors.getError()[i] + "\r\n";
			}
		}
		return notes;
	}

	
}
