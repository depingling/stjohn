package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.value.*;

/**
 *
 * @author Deping
 */
public abstract class InterchangeInboundSuper extends InterchangeSuper
{
	public void translateInterchange() throws Exception{
		translateInterchangeHeader();
		translateInterchangeContent();
		translateInterchangeTrailer();	
	}
	
	public void translateInterchangeHeader() throws Exception {
		profile = TradingProfileData.createValue();	
		translateInterchangeHeaderByHandler();	
		interchangeD = createInterchangeObject();
	}

	// should be overwrite by inbound handler class
	public abstract void translateInterchangeHeaderByHandler() throws Exception;
	
	public void translateInterchangeTrailer() throws Exception {
		interchangeD.setTradingProfileId(profile.getTradingProfileId());
	}

	public abstract void translateInterchangeContent() throws Exception;
	
	public String getTranslationReport() {
		String str = "";
		if (this.isFail())
		{
			return ("Inbound translation failed");
		}
		else
		{
			str = "Inbound translation is success.\r\n";
			str = "Inbound filename: " + getTranslator().getInputFilename() + "\r\n\r\n";			
		}
		str += "\r\n";
		return str;
	}
}
