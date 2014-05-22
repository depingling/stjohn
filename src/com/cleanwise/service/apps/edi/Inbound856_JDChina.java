package com.cleanwise.service.apps.edi;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import com.americancoders.edi.*;

import java.rmi.RemoteException;
import java.util.*;
import java.math.BigDecimal;

import org.apache.log4j.Logger;



/**
 * @author deping
 */
public class Inbound856_JDChina extends Inbound856
{
	protected Logger log = Logger.getLogger(this.getClass());

	/** extract data from segment REF that is part of the Header
	 *<br>Reference Identification used
	 *<br>To specify identifying information
	 *param inTable table containing this segment
	 *@throws OBOEException - most likely segment not found
	 */
	public void extractReferenceIdentificationREF(Loop inLoop)
	throws OBOEException {
		super.extractReferenceIdentificationREF(inLoop);
		if (isQualiferExistsInRefSegment("ZZ", inLoop))
			ediInp856Vw.setMatchPoNumType(RefCodeNames.MATCH_PO_NUM_TYPE_CD.STORE_ERP_PO_NUM);
		else
			ediInp856Vw.setMatchPoNumType(RefCodeNames.MATCH_PO_NUM_TYPE_CD.VENDOR_ORDER_NUM);
	}
}

