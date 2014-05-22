package com.cleanwise.service.apps.dataexchange;

import org.dom4j.Node;
import com.cleanwise.service.apps.dataexchange.InboundSuper;

/** Super class for parsing any transaction set
 *<br>
 *@author Deping
 */
public abstract class InboundXMLSuper extends InboundSuper
{

	protected Node nodeToTranslate;      // current transaction set
	protected InboundXMLHandler xmlHandler = null;
	public String mPassword;
	
	public void setPassword(String pVal) {
		mPassword = pVal;
	}
	  
	public String getPassword() {
		return mPassword;
	}

	public void setParameter(InboundXMLHandler handler, Node node)
	{
		this.mHandler = handler;
		xmlHandler = handler;
		nodeToTranslate = node;
	}
	
	public void extract() {
		try{
			translate(nodeToTranslate);
		}catch(Exception e){
			e.printStackTrace();
			appendErrorMsgs(e.getMessage(), true);
		}
	}

	protected abstract void translate(Node nodeToTranslate) throws Exception;
	
}

