/*
 * InboundEdiHandler.java
 *
 * Created on October 22, 2002, 9:33 AM
 */

package com.cleanwise.service.apps.dataexchange;

import java.util.Map;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.JDChinaFileLoaderView;


/**
 *
 * @author  dling
 */
public class InboundJDChinaFileHandler  extends InterchangeInboundSuper implements InboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());

	/**
	 *@param ediData - String edi data parsed
	 */
	public void translate(String ediData) throws Exception
	{
		setFail(false);
		getInterchanges().clear();

		if (ediData == null || ediData.equals(""))
		{
			log.error("No input data");
			setFail(true);
			return;
		}
		
		InboundTranslate translator = (InboundTranslate) this.getTranslator();
		JDChinaFileLoaderView fileLoader = JDChinaFileLoaderView.createValue();
		fileLoader.setFileName(translator.getInputFilename());
		fileLoader.setFileContent(ediData.getBytes());
		Map props = translator.getPropertyMap();
		String url = (String) props.get("dbUrl");
		if (Utility.isSet(url)){
			fileLoader.setDbUrl(url);
			fileLoader.setDbUser((String) props.get("dbUser"));
			fileLoader.setDbPassword((String) props.get("dbPassword"));
		}
		
		this.appendIntegrationRequest(fileLoader);
	}

	@Override
	public void translateInterchangeContent() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void translateInterchangeHeaderByHandler() throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
