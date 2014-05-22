/*
 * Inbound856.java
 *
 * Created on August 10, 2005, 4:44 PM
 */

package com.cleanwise.service.apps.edi;

import com.americancoders.edi.*;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.cleanwise.service.apps.dataexchange.InboundTranslate;

import java.util.*;

import org.apache.log4j.Logger;

/**
 *
 * @author Deping
 */
public class Inbound856_Orderline  extends Inbound856
{
	private static final Logger log = Logger.getLogger(Inbound856_Orderline.class);

	public void extractItemIdentificationREF(Loop inLoop)
	throws OBOEException
	{
		try{
			int numberOfSegmentsInVector = inLoop.getCount("REF");

			Segment segment = null;

			ArrayList trackingNums = new ArrayList();
			for (int i = 0; i <  numberOfSegmentsInVector; i++)
			{
				segment = inLoop.getSegment("REF", i);
				if (segment == null)
					return;
				String identQualifier =  getField(segment,1,false,null);
				String identification =  getField(segment,2,false,null);
				if("2I".equals(identQualifier)) {
					trackingNums.add(identification);
				}else if("CN".equals(identQualifier)) {
					trackingNums.add(identification);
					ediInp856Vw.setCarrierName("LTL");
				}
			}
			ediInp856ItemVw.setTrackingNumList(trackingNums);
			
			if(ediInp856Vw!=null){
				ediInp856Vw.setUpdateOrderItemActions("false");
			}
		}catch(Exception e){
			return;
		}

		return;
	}

}

