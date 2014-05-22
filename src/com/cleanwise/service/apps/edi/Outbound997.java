package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import java.util.Vector;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.apps.dataexchange.*;

/**
 * Class to build 997 document.
 *
 * @author Deping
 */

public class Outbound997 extends OutboundSuper
{
	protected Logger log = Logger.getLogger(this.getClass());
	Loop ak2Loop;

	/** constructor for class Outbound997
	 */
	public Outbound997()
	{
	}

	/** builds segment AK1 that is part of the Header
	 *<br>Functional Group Response Header used
	 *<br>To start acknowledgment of a functional group
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderAK1(String groupType, int groupCountrolNumber)
	throws OBOEException
	{
		Segment segment = ts.getHeaderTable().createSegment("AK1");
		ts.getHeaderTable().addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 479 Functional Identifier Code
		de.set(groupType);
		de = (DataElement) segment.buildDE(2);  // 28 Group Control Number
		de.set(""+groupCountrolNumber);
		log.info("  buildHeaderAK1, groupType=" + groupType
				+ " groupCountrolNumber=" + groupCountrolNumber
				+ " new segment is=" + segment.get() );
	}

	/** builds segment AK2 that is part of the Header
	 *<br>Transaction Set Response Header used
	 *<br>To start acknowledgment of a single transaction set
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderAK2(String setType, int setControlNumber)
	throws OBOEException
	{
		//YR OBOE ak2Segment = ts.getHeaderTable().createSegment("AK2");
		ak2Loop = ts.getHeaderTable().createLoop("AK2");
		//ts.getHeaderTable().addSegment(ak2Segment);
		ts.getHeaderTable().addLoop(ak2Loop);
		Segment ak2Segment = ak2Loop.createSegment("AK2");
		ak2Loop.addSegment(ak2Segment);
		DataElement de;
		de = (DataElement) ak2Segment.buildDE(1);  // 143 Transaction Set Identifier Code
		de.set(setType);
		de = (DataElement) ak2Segment.buildDE(2);  // 329 Transaction Set Control Number
		de.set(Utility.padLeft("" + setControlNumber, '0', 4));
	}

	/** builds segment AK5 that is part of the HeaderAK2
	 *<br>Transaction Set Response Trailer used
	 *<br>To acknowledge acceptance or rejection and report errors in a transaction set
	 *param inSegment segment containing this subsegment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderAK2AK5(String ackCode, Vector errorCodes)
	{
		Segment segment = ak2Loop.createSegment("AK5");
		ak2Loop.addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 717 Transaction Set Acknowledgment Code
		de.set(ackCode);
		for (int i = 0; i < errorCodes.size() && i < 5; i++)
		{
			de = (DataElement) segment.buildDE(2 + i);  // 718 Transaction Set Syntax Error Code
			de.set((String)errorCodes.get(i));
		}
	}

	/** builds segment AK9 that is part of the Header
	 *<br>Functional Group Response Trailer used
	 *<br>To acknowledge acceptance or rejection of a functional group and report the number of included transaction sets from the original trailer, the accepted sets, and the received sets in this functional group
	 *param inTable table containing this segment
	 *throws OBOEException - most likely segment not found
	 */
	public void buildHeaderAK9(String ackCode, int setIncluded, Vector errorCodes)
	throws OBOEException
	{
		int errorSetCount = 0;
		// YR OBOE  int setReceived = ts.getHeaderTable().getSubsegmentCount("AK2");
		int setReceived = ts.getHeaderTable().getLoopCount("AK2");
		for (int i = 0; i < setReceived; i++)
		{
//			YR OBOE    ak2Segment = ts.getHeaderTable().getSegment("AK2", i);
			ak2Loop = ts.getHeaderTable().getLoop("AK2", i);
//			YR OBOE    Segment ak5Segment = ak2Segment.getSegment("AK5");
			Segment ak5Segment = ak2Loop.getSegment("AK5");
			DataElement de = ak5Segment.getDataElement(1);  // 718 Transaction Set Syntax Error Code
			if (ackCode.equals("R"))
				de.set("R");
			else if (de.get().equals("R"))
				errorSetCount++;
		}
		if (ackCode.equals("R"))
			errorSetCount = setReceived;
		else if (errorSetCount == setReceived)
			ackCode = "R";
		else if (errorCodes.size() == 0 && errorSetCount == 0)
			ackCode = "A";
		else
			ackCode = "E";

		int setAccepted = setReceived - errorSetCount;

		Segment segment = ts.getHeaderTable().createSegment("AK9");
		ts.getHeaderTable().addSegment(segment);
		DataElement de;
		de = (DataElement) segment.buildDE(1);  // 715 Functional Group Acknowledge Code
		de.set(ackCode);
		de = (DataElement) segment.buildDE(2);  // 97 Number of Transaction Sets Included
		de.set(""+setIncluded);
		de = (DataElement) segment.buildDE(3);  // 123 Number of Received Transaction Sets
		de.set(""+setReceived);
		de = (DataElement) segment.buildDE(4);  // 2 Number of Accepted Transaction Sets
		de.set(""+setAccepted);
		for (int i = 0; i < errorCodes.size() && i < 5; i++)
		{
			de = (DataElement) segment.buildDE(5+i);  // 716 Functional Group Syntax Error Code
			de.set((String)errorCodes.get(i));
		}
	}

	public void buildTransactionContent()
	throws Exception
	{
	}
	public void buildTransactionTrailer()
	throws Exception
	{
		buildSummarySE(ts.getHeaderTable());
		fg.addTransactionSet(ts);
		tsv.add(ts);
		log.info("add a ts, size=" + tsv.size() );
		super.buildTransactionTrailer();
	}

	private java.util.ArrayList tsv = new java.util.ArrayList();

	public void buildInterchangeContent()throws Exception{	}

	
}
