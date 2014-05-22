/*
 * InboundFlatFile.java
 *
 * Created on March 7, 2005, 9:38 AM
 */

package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.IntegrationRequestData;
import com.cleanwise.service.api.value.TradingPropertyMapData;
import com.cleanwise.service.api.value.TradingPropertyMapDataVector;
import com.cleanwise.service.apps.loaders.DelimitedParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import org.apache.poi.hssf.record.UnicodeString;

/**
 * Will parse a flat file based off the values defined in the trading partner screen.
 * May be overidden if additional processing is needed, but if it is a simple file to object
 * conversion no addition programming is needed.
 * @author bstevens
 */

public class InboundFlatFile extends InterchangeInboundSuper implements StreamedInboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	/** Holds value of property sepertorChar. */
	private char sepertorChar = ',';

	/** Holds value of property quoteChar. */
	private char quoteChar = '\"';

	protected HashMap columnHeaderMap = new HashMap();
	protected HashMap columnNumberMap = new HashMap();
	protected HashMap staticValuesMap = new HashMap();
	protected int currentLineNumber = 0;
	boolean init = true;
	boolean useHeaderLine = false;
	boolean hasHeaderLine = false;

	protected String javaBeanToPopulate = null;
	String dateFormat = "MM/dd/yyyy";
	private Object reader;

	/**
	 *initiialzes the member variables
	 */
	protected void init(){
		javaBeanToPopulate = getValueObjectClassName();
		log.info("========= Bean to Populate ==============  " + javaBeanToPopulate + "\n");
		if(javaBeanToPopulate == null){
			if(getTranslator().getTradingProfileConfig() != null){
				throw new NullPointerException("Value Object Class Name was null for pattern: "+
						getTranslator().getTradingProfileConfig().getPattern() + " trading config id "+getTranslator().getTradingProfileConfig().getTradingProfileConfigId());
			}else{
				throw new NullPointerException("Value Object Class Name was null");
			}
		}
		TradingPropertyMapDataVector mapping = getTranslator().getTradingPropertyMapDataVector();
		useHeaderLine = Utility.isTrue(getTranslator().getConfigurationProperty(RefCodeNames.ENTITY_PROPERTY_TYPE.FIELD_MAP_USE_HEADERS));
		if(!useHeaderLine){
			hasHeaderLine = Utility.isTrue(getTranslator().getConfigurationProperty(RefCodeNames.ENTITY_PROPERTY_TYPE.FLAT_FILE_CONTAINS_HEADER));
		}else{
			hasHeaderLine = true; //assume it has headers
		}
		Iterator it = mapping.iterator();
		while(it.hasNext()){
			TradingPropertyMapData map = (TradingPropertyMapData) it.next();
			if(RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP.equals(map.getTradingPropertyMapCode())){
				//map any hard values (static text) and any column mappings
				if(RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE.equals(map.getEntityProperty())){
					staticValuesMap.put(map.getPropertyTypeCd(),map.getHardValue());
				}else{
					if(useHeaderLine){
						String key = map.getHardValue();
						columnHeaderMap.put(key, map);
					}else{
						Integer key = new Integer(map.getOrderBy());
						columnNumberMap.put(key, map);
					}
				}
			}
		}
	}

	/**
	 *Returns that class name of the value object we are dynamically populating
	 */
	protected String getValueObjectClassName(){
		//default implementation uses the configuration value
		return getTranslator().getConfigurationProperty(RefCodeNames.ENTITY_PROPERTY_TYPE.VALUE_OBJECT_CLASSNAME);
	}

	/**
	 *Parses the header lines into column number associations for ex:
	 *HEADER_A, HEADER_B
	 *where the definition is:
	 *HEADER_A maps to property headerA
	 *HEADER_B maps to property headerB
	 *becomes the following key pair map:
	 *0, headerA
	 *1, headerB
	 *etc
	 * @throws Exception
	 */
	protected void parseHeaderLine(List pParsedLine) throws Exception{
		log.info("=== InboundFlatFile -> parseHeaderLine ===");
		int ct = 0;
		Iterator it = pParsedLine.iterator();
		while(it.hasNext()){
			String s = (String) it.next();
			if(s != null){
				s = s.trim();
			}
			ct++;
			TradingPropertyMapData map = (TradingPropertyMapData) columnHeaderMap.get(s);
			columnNumberMap.put(new Integer(ct), map);
		}
	}

	protected void parseDetailLine(List pParsedLine) throws Exception{
		Object parsedObj;
		try{
			parsedObj = createObject();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		String errorMessage = null;
		int ct = 0;
		Iterator it = pParsedLine.iterator();
		while(it.hasNext()){
			Object value = it.next();
			TradingPropertyMapData map = (TradingPropertyMapData) columnNumberMap.get(new Integer(ct));
			ct++;
			try{
				if(map != null){
					populateProperty(map, value, parsedObj);
				} else {
				}

			}catch(Exception e){
				errorMessage = "Error parsing column: "+ct+" row: "+this.currentLineNumber+ "("+value+") to bean property: ("+map.getPropertyTypeCd()+")";
				log.error(errorMessage);
				log.error(e.getMessage(),e);
				if (e.getMessage() != null)
					errorMessage += "-Could not populate method: "+map.getPropertyTypeCd() + " Reason:"+e.getMessage();
				else
					errorMessage += "-Could not populate method: "+map.getPropertyTypeCd() + " Reason:"+e;
			}
		}

		//loop through the static value mappings and set any properties defined here.  Note that if the configuration is setup to set
		//a static value and set the same property based off a mapped column the static value wins
		it = staticValuesMap.keySet().iterator();
		while(it.hasNext()){
			String beanProp = (String) it.next();
			log.debug(beanProp+"::"+staticValuesMap.get(beanProp));
			Method meth = Utility.getJavaBeanSetterMethod(parsedObj,beanProp);
			if(meth == null){
				throw new RuntimeException("No setter found for property: "+beanProp);
			}
			try{
				Utility.populateJavaBeanSetterMethod(parsedObj, meth,staticValuesMap.get(beanProp),dateFormat);
			}catch(Exception e){
				e.printStackTrace();
				errorMessage += "-Could not populate method: "+beanProp + " Reason:"+e.getMessage();
			}
		}

		if(errorMessage != null){
			processUnparsableLine(parsedObj,pParsedLine,errorMessage);
		}else{
	         try{
	            processParsedObject(parsedObj);
	         }catch(Exception ex){
	            ex.printStackTrace();
	            throw new RuntimeException(ex.getMessage());
	         }
	     }
	}

	/**
	 *Creates an object that we are trying to populate
	 */
	private Object createObject()
	throws IllegalAccessException, InstantiationException, ClassNotFoundException,InvocationTargetException{
		return Utility.createInstanceFromString(javaBeanToPopulate);
	}

	/**
	 *populates the property of the object based on the passed in column number.  If no mapping exists for this column then
	 *nothing is done.
	 */
	private void populateProperty(TradingPropertyMapData map, Object value, Object obj)
	throws IllegalAccessException, InstantiationException, ClassNotFoundException,InvocationTargetException{
		Method meth = Utility.getJavaBeanSetterMethod(obj,map.getPropertyTypeCd());
		if(meth == null){
			try{
				Map<String, Object> theMap = (Map<String, Object>) Utility.getJavaBeaMap(obj,map.getPropertyTypeCd());
				if(theMap!=null){
					theMap.put(map.getHardValue(),value);
				}else{
					throw new RuntimeException("No setter or map found for property: "+map.getPropertyTypeCd());
				}
			}catch(IllegalAccessException e){
			}catch(InstantiationException e){
			}catch(ClassNotFoundException e){
			}catch(InvocationTargetException e){
			}catch(ClassCastException e){}


		}else if(value != null){
		    Utility.populateJavaBeanSetterMethod(obj, meth,value,dateFormat);
		}
	}


	public boolean isEmpty(List pParsedLine){
		if(pParsedLine == null || pParsedLine.isEmpty()){
			return true;
		}
		Iterator it =pParsedLine.iterator();
		while(it.hasNext()){
			Object aRecord = it.next();
			if(aRecord != null){
				if(aRecord instanceof String){
					if(Utility.isSet((String) aRecord)){
						return false;
					}
				}else if(aRecord instanceof UnicodeString){
					if(Utility.isSet(((UnicodeString) aRecord).getString())){
						return false;
					}
				}else{
					if(Utility.isSet(aRecord.toString())){
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 *passed in the parsed line will preform the necessary logic of populating the object
	 */
	public void parseLine(List pParsedLine) throws Exception{
		if(isEmpty(pParsedLine)){
			log.info("empty line");
			return;
		}
		//log.info("Parsing line");
		if(init){
			init();
			init = false;
		}
		if(hasHeaderLine && (currentLineNumber == 0)){
			if(useHeaderLine){
				parseHeaderLine(pParsedLine);
			}
		}else{
			parseDetailLine(pParsedLine);
		}
		currentLineNumber++;
	}

	/**
	 *Returns the current line number that we are parsing
	 */
	protected int getCurrentLineNumber(){
		return currentLineNumber;
	}

	/**
	 * Called when the object has successfully been parsed
	 */
	protected void processParsedObject(Object pParsedObject) throws Exception{
		addIntegrationRequest(pParsedObject);
	}

	/**
	 * Called when the line could not be parsed
	 */
	protected void processUnparsableLine(Object pPartiallyParsedObject,List pParsedLine, String errorMessage){
		log.info("adding error request");
		addErrorIntegrationRequest(pPartiallyParsedObject,errorMessage);
	}

	protected HashMap getColumMap(){
	      return columnNumberMap;
	    }

	/** Returns a report to be logged.  Should be human readable.
	 *
	 */
	public String getTranslationReport() {
		if(getIntegrationRequests().size() == 0){
			return "no records translated";
		}
		return "Successfully translated "+getIntegrationRequests().size() + " records";
	}

	public void translateInterchangeHeader() throws Exception {
		profile = translator.getProfile();
		translateInterchangeHeaderByHandler();
		interchangeD = createInterchangeObject();
	}

	// nothing to translate
	public void translateInterchangeHeaderByHandler() throws Exception	{}

	public void translateInterchangeContent() throws Exception {
		createTransactionObject();
		if(reader instanceof ExcelReader){
			ExcelReader xlsRdr = (ExcelReader) reader;
			xlsRdr.processFile(this);
		}else{
			translate((Reader)reader);
		}
	}

	public void translateInterchangeTrailer() throws Exception {
		doPostProcessing();
		super.translateInterchangeTrailer();
	}

	/**
	 *Translates a stream of varying types.  This implementation supports xls and flat text.
	 *InputStream the input stream to operate on
	 *String the stream type (such as xls, txt, etc.)
	 *@throws IOException
	 */
	public void translate(InputStream pIn,String pStreamType) throws Exception {
		try{
			if(pStreamType.equalsIgnoreCase("xls")){
				reader = new ExcelReader(pIn);
			}else{
				reader = new InputStreamReader(pIn, "UTF-8");
			}
			translateInterchange();
		}catch(Exception e){
			e.printStackTrace();
			setFail(true);
			throw e;
		}
	}

	/**
	 * Preforms the translator.  It should also determine the TradingPartnerData that is
	 * appropriate if the translator does not make this (as in edi where it is read out of the
	 * file, as opposed to being determined by the file name).
	 */
	public void translate(String s) throws Exception {
		try{
			//convert the String to a buffered reader so we can read it in line by
			//line in a platform independant way.
			reader = new StringReader(s);
			translateInterchange();
		}catch(Exception e){
			e.printStackTrace();
			setFail(true);
			throw e;
		}
	}

	/**
	 *Preforms the translation on the given Reader object.  This is the main processor method.
	 *The other translator methods figure out how to get their input into a steam and then call
	 *call this method to preform the looping logic.
	 */
	protected void translate(Reader pReader) throws Exception{
		BufferedReader r;
		if (pReader instanceof BufferedReader){
			r = (BufferedReader) pReader;
		}else{
			r = new BufferedReader(pReader);
		}
		try{
			String line = null;
			while ((line = r.readLine()) != null) {
				List l = DelimitedParser.parse(line,getSepertorChar(),getQuoteChar(),true);
				parseLine(l);
			}
		}catch(Exception e){
			log.error("Error parsing line: "+(currentLineNumber +1));
			e.printStackTrace();
			setFail(true);
			throw e;
		}
	}

	/**
	 * This method is called when the file is done processing.  Implementing methods should
	 * check the failed flag if post processing should only be done for successfully processed
	 * files.  The default implementation is to do nothing.
	 * @throws Exception
	 */
	protected void doPostProcessing() throws Exception{}


	/** Getter for property sepertorChar (by default this is set to a comma.
	 * @return Value of property sepertorChar.
	 *
	 */
	public char getSepertorChar() {
		return this.sepertorChar;
	}

	/** Setter for property sepertorChar.
	 * @param sepertorChar New value of property sepertorChar.
	 *
	 */
	public void setSepertorChar(char sepertorChar) {
		this.sepertorChar = sepertorChar;
	}

	/** Getter for property quoteChar.
	 * @return Value of property quoteChar.
	 *
	 */
	public char getQuoteChar() {
		return this.quoteChar;
	}

	/** Setter for property quoteChar.
	 * @param quoteChar New value of property quoteChar.
	 *
	 */
	public void setQuoteChar(char quoteChar) {
		this.quoteChar = quoteChar;
	}

	protected void addErrorIntegrationRequest(Object pRequest, String pMessage){
		IntegrationRequestData request = new IntegrationRequestData(pRequest, pMessage, true);
		appendIntegrationRequest(request);
	}

	protected void addIntegrationRequest(Object pRequest){
		IntegrationRequestData request = new IntegrationRequestData(pRequest, null);
		appendIntegrationRequest(request);
	}
        protected String getConfigurationPropertyValue(String propertyName){
          String propertyVal = "";
          boolean hasProperty = Utility.isSet(getTranslator().getConfigurationProperty(propertyName));
          log.info("[InboundFlatFile].getConfigurationPropertyValue() ----> hasProperty =" +  hasProperty);

          if (hasProperty){
            propertyVal = getTranslator().getConfigurationProperty(propertyName);
          }
          return propertyVal;
        }

}
