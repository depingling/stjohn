package com.cleanwise.service.apps.edi;

/**
 *OBOE - Open Business Objects for EDI
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

import com.americancoders.edi.DataElement;
import com.americancoders.edi.DocumentErrors;
import com.americancoders.edi.IContainedObject;
import com.americancoders.edi.Segment;
import com.americancoders.edi.TemplateFunctionalGroup;
import com.americancoders.edi.TransactionSet;
import com.americancoders.edi.x12.*;

/**
 * class for container Functional_Group
 *
 */
public class CwX12FunctionalGroup
	extends X12FunctionalGroup {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** static segment ids */

	public static final String idHeader = "GS";
	public static final String idTrailer = "GE";
	/** instantiates a functional group
	 */

	public CwX12FunctionalGroup() {
		super();
	}

	/** instantiates a functional group from the definition in an
	 * envelope xml rules file.
	 */

	public CwX12FunctionalGroup(TemplateFunctionalGroup inTFG, IContainedObject inParent) {
		super(inTFG, inParent);
	}

	/**
	* validates
	* <br> doesn't throw exception but placess error message in DocumentErrors object
	*/

	public void validate(DocumentErrors inDErr) {
        boolean hNoErr=false, tNoErr=false;
        Segment header = getHeader();
        Segment trailer = getTrailer();
		if (header != null) {
			hNoErr = header.validate(inDErr);
		} else
			inDErr.addError(0,
				"Envelope",	"Missing FunctionalGroup Header",
				this,	"3",
				this, 1);

		int i;
		TransactionSet ts;
		if (getTransactionSetCount() == 0)
			inDErr.addError(0, "FG", "No transaction sets", this, "4", this, 1);
		else
			for (i = 0; i < getTransactionSetCount(); i++) {
				ts = (TransactionSet) getTransactionSet(i);
				// not validate function group
				/*if (fggrp.compareTo(ts.getFunctionalGroup()) != 0)
					inDErr.addError(0,	"FG",
						"Functional Identifier Code (GS01-479) value is "
							+ fggrp
							+ " does not match for Transaction Set ID "
							+ ts.getID()
							+ "-"
							+ ts.getFunctionalGroup(),
						this,"6",this, 2);*/
				ts.validate(inDErr);
			}

		if (getTransactionSetCount() < 1)
			inDErr.addError(0,"FG",
				"No Transaction Sets Defined",
				this,"4",
				this, 1);

		if (trailer != null)
			tNoErr = trailer.validate(inDErr);
		else
			inDErr.addError(0,"FG",
				"Missing FunctionalGroup Trailer",
				this,"4",
				this, 1);

		if (header != null && trailer != null && hNoErr == true && tNoErr == true) {
			DataElement de1 = header.getDataElement("28");
			DataElement de2 = trailer.getDataElement("28");
			if (de1 != null && de2 != null)
				if (de1.get() != null && de2.get() != null)
					if (de1.get().compareTo(de2.get()) != 0)
						inDErr.addError(0,"FG",
							"Control number mismatch (28)",
							this,	"4",
							header, 3);
			de1 = trailer.getDataElement("97");
			int saidCount = Integer.parseInt(de1.get());
			int readCount = this.getTransactionSetCount();
			if (saidCount != readCount)
				inDErr.addError(0, "97", "Transaction Set Count Mismatch.  Should Be "+readCount, this, "7", trailer.getDataElement("97"), 3);

		}

	}

}
