package com.cleanwise.service.apps.edi;

/**
 *OBOE - Open Business Objects for EDI
 *<P>java access to EDI
 *<p>Copyright 1998-2007 - American Coders, LTD  - Raleigh NC USA
 *<p>All rights reserved
 *<p>American Coders, Ltd
 *<br>P. O. Box 97462
 *<br>Raleigh, NC  27624  USA
 *<br>1-919-846-2014
 *<br>http://www.americancoders.com
@author Joe McVerry - American Coders, Ltd.
  @version   3.5.3
 */


import java.io.Reader;
import java.io.StringReader;


import org.apache.log4j.Logger;

import com.americancoders.edi.EDIDocumentParser;
import com.americancoders.edi.Envelope;
import com.americancoders.edi.EnvelopeFactory;
import com.americancoders.edi.FunctionalGroup;
import com.americancoders.edi.OBOEException;
import com.americancoders.edi.Segment;
import com.americancoders.edi.TemplateEnvelope;
import com.americancoders.edi.Tokenizer;
import com.americancoders.edi.TransactionSet;
import com.americancoders.edi.TransactionSetFactory;
import com.americancoders.edi.x12.X12Envelope;
import com.americancoders.edi.x12.X12DocumentParser;
import com.americancoders.edi.x12.X12FunctionalGroup;
import com.americancoders.edi.x12.X12Tokenizer;
import com.americancoders.util.Util;

/**
 * class to parse input string for all defined OBOE Transaction Sets
 * <br> x12 dependent
 *
 */

public class CwX12DocumentParser extends X12DocumentParser {
    
	static Logger logr = Logger.getLogger(CwX12DocumentParser.class);
	static 	{		Util.isLog4JNotConfigured();	}
	
		/**
		 * parses an X12 Document and passes results to EDIDocumentHandlers
		 * @param inReader the edi document passed through by a reader object
		 * @param inValidate - if true call validation method of the envelope after parsing
		 * <br>if you pass false don't forget to call the validation method on the envelope object
		 * <br>pass to the validation method the documenterrors object created here, use
		 * the getDocumentErrors method of the parser object.
		 * @exception OBOEException
		 *                      - unknown transaction set, this transaction set is undefined to OBOE
		 */
		public void parseDocument(Reader inReader, boolean inValidate) throws OBOEException {

		CwX12Envelope envelope;
		Tokenizer et = new X12Tokenizer(inReader, dErr); // , posStop-1));
		logr.debug("env ver = "+        et.getVersion());
		try {
			TemplateEnvelope te = EnvelopeFactory.buildEnvelope("x12.envelope",et.getVersion() );
			envelope = new CwX12Envelope(te);
		} catch (OBOEException oe) {
			//if (oe.getMessage().startsWith("File not found"))
			//    envelope = new X12Envelope();
			//else
			throw oe;
		}
	    
		notifyStartEnvelope((Envelope) envelope);
	    
		envelope.setDelimiters(et.getSeparators());
	    
		et.getNextSegment(null);
		String findID = et.getNextDataElement();
	    
		if (findID.compareTo(X12Envelope.idInterchangeHeader) != 0)
			throw new OBOEException(
				"Segment ID \""
					+ findID
					+ "\" found, was expecting "
					+ X12Envelope.idInterchangeHeader
					+ "\"");
		
		Segment ISA_Interchange_Control_Header = envelope.createInterchange_Header();
		ISA_Interchange_Control_Header.setByteOffsetPositionInIncomingDocument(et.getInputByteCount());  // should be 0
		notifyStartSegment(ISA_Interchange_Control_Header);

		ISA_Interchange_Control_Header.parse(et);
		
		
		notifyEndSegment(ISA_Interchange_Control_Header);

		findID = et.getCurrentDataElement();
		if (findID.compareTo(X12Envelope.idGradeofServiceRequest) == 0) {
			Segment ISB_Grade_of_Service_Request = envelope.createGrade_of_Service_Request();
			ISB_Grade_of_Service_Request.setByteOffsetPositionInIncomingDocument(et.getInputByteCount());
			notifyStartSegment(ISB_Grade_of_Service_Request);
			ISB_Grade_of_Service_Request.parse(et);
			notifyEndSegment(ISB_Grade_of_Service_Request);
			findID = et.getCurrentDataElement();
		}
		if (findID.compareTo(X12Envelope.idDeferredDeliveryRequest) == 0) {
			Segment ISE_Deferred_Delivery_Request = envelope.createDeferred_Delivery_Request();
			ISE_Deferred_Delivery_Request.setByteOffsetPositionInIncomingDocument(et.getInputByteCount());
			notifyStartSegment(ISE_Deferred_Delivery_Request);
			ISE_Deferred_Delivery_Request.parse(et);
			notifyStartSegment(ISE_Deferred_Delivery_Request);
			findID = et.getCurrentDataElement();
		}
		boolean ta1Found = false;
		if (findID.compareTo(X12Envelope.idInterchangeAcknowledgment) == 0) {
//			/Vector v_Interchange_Acknowledgment = new Vector();
			do {
				Segment Temp_Interchange_Acknowledgment =
					envelope.createInterchange_Acknowledgment();
				Temp_Interchange_Acknowledgment.setByteOffsetPositionInIncomingDocument(et.getInputByteCount());
				notifyStartSegment(Temp_Interchange_Acknowledgment);
				Temp_Interchange_Acknowledgment.parse(et);
				notifyEndSegment(Temp_Interchange_Acknowledgment);
				findID = et.getCurrentDataElement();
				ta1Found = true;
			} while (findID.compareTo("TA1") == 0);
		}
     
		findID = et.getCurrentDataElement();
		if (findID.compareTo(X12FunctionalGroup.idHeader) != 0)
		   if (!ta1Found)
			throw new OBOEException(
				"Segment ID \""
					+ findID
					+ "\" found, was expecting \""
					+ X12FunctionalGroup.idHeader
					+ "\"");
		
		while (findID != null && findID.compareTo(X12FunctionalGroup.idHeader) == 0) {

			FunctionalGroup functionalGroup = envelope.createFunctionalGroup();
			notifyStartFunctionalGroup(functionalGroup);
			Segment GS_Functional_Group_Header =
				functionalGroup.createSegment(X12FunctionalGroup.idHeader);
			GS_Functional_Group_Header.setByteOffsetPositionInIncomingDocument(et.getInputByteCount());
			notifyStartSegment(GS_Functional_Group_Header);
			GS_Functional_Group_Header.parse(et);
			notifyEndSegment(GS_Functional_Group_Header);
			findID = et.getCurrentDataElement();
			TransactionSet parsedTransactionSet = null;
			
			while (findID != null && findID.compareTo("ST") == 0) {
				findID = et.getNextDataElement();
				et.resetSegment();
				
				logr.debug("p");

				parsedTransactionSet = TransactionSetFactory.buildTransactionSet(findID, null,
					// use oboe.properties searchDirective
		GS_Functional_Group_Header.getDataElement("480").get(), // version
		ISA_Interchange_Control_Header.getDataElement("I07").get(), // receiver
		ISA_Interchange_Control_Header.getDataElement("I06").get(), // sender
	    ISA_Interchange_Control_Header.getDataElement("I14").get()); // test or production
		       

				parsedTransactionSet.setParent(functionalGroup);
				parsedTransactionSet.setFormat(Envelope.X12_FORMAT);
				notifyStartTransactionSet(parsedTransactionSet);
				parsedTransactionSet.parse(et);
				while (true) {
					findID = et.getCurrentDataElement();
					if (findID == null)
					  return;
					if (findID.compareTo("ST") == 0) {
						notifyEndTransactionSet(parsedTransactionSet);
						break;
					} else if (findID.compareTo(X12FunctionalGroup.idTrailer) == 0) {
						notifyEndTransactionSet(parsedTransactionSet);
						break;
					} else if (findID.compareTo(X12Envelope.idInterchangeTrailer) == 0) {
						et.reportError(
							"Should not appears before end of functional group",
							"2");
						notifyEndTransactionSet(parsedTransactionSet);
						break;
					} else if (findID.length() > 0) {

						//et.reportError("Unknown or out of place segment", "2");

						et.getLastSegmentContainer().whyNotUsed(et);
						// here is where we put logic to try and get back into sync.
						et.getNextSegment(et.getLastSegmentContainer());
						et.getNextDataElement();
						if (parsedTransactionSet
							.continueParse(et.getLastSegmentContainer(), et)
							== false) {
							et.reportError("May not be able to restart parser", "?");
							break;
						}
					} else {
						et.reportError("Empty Data Line Error", "?");
						// here is where we put logic to try and get back into sync.
						et.getNextSegment(et.getLastSegmentContainer());
						et.getNextDataElement();
						if (parsedTransactionSet
							.continueParse(et.getLastSegmentContainer(), et)
							== false) {
							et.reportError("Can not restart parser", "?");
							break;
						}
					}
				}
			}
			if (findID == null) {
				et.reportError("Envelope ended too soon", "2");
			} else if (findID.compareTo(X12FunctionalGroup.idTrailer) != 0) {
				et.reportError("Unknown or out of place segment ("+findID+")", "2");

			} else {
				Segment GE_Functional_Group_Trailer =
					functionalGroup.createSegment(X12FunctionalGroup.idTrailer);
				GE_Functional_Group_Trailer.setByteOffsetPositionInIncomingDocument(et.getInputByteCount());
				notifyStartSegment(GE_Functional_Group_Trailer);
				GE_Functional_Group_Trailer.parse(et);
				notifyEndSegment(GE_Functional_Group_Trailer);
				notifyEndFunctionalGroup(functionalGroup);
				findID = et.getCurrentDataElement();
			}

		}
		 
		if (findID == null) {
			et.reportError("Envelope ended too soon", "2");
		} else if (findID.compareTo(X12Envelope.idInterchangeTrailer) != 0) {
			et.reportError("Unknown or out of place segment ("+findID+")", "2");
		} else {
			Segment IEA_Interchange_Control_Trailer = envelope.createInterchange_Trailer();
			IEA_Interchange_Control_Trailer.setByteOffsetPositionInIncomingDocument(et.getInputByteCount());
			notifyStartSegment(IEA_Interchange_Control_Trailer);
			IEA_Interchange_Control_Trailer.parse(et);
			notifyEndSegment(IEA_Interchange_Control_Trailer);
		}
		 
		notifyEndEnvelope(envelope);
		 
		if (inValidate)
			envelope.validate(dErr);

		if (com.americancoders.util.Util.propertyFileIndicatesThrowParsingException()
			&& dErr.getErrorCount() > 0) {
			
			dErr.logErrors();
			
			throw new OBOEException(dErr);

		}
	}

}
