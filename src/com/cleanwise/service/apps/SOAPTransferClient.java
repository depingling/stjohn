package com.cleanwise.service.apps;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.auth.*;

public class SOAPTransferClient {

	public static String invoke(String endpoint, String namespaceURI,
			String operation, String user, String userPass) throws IOException {
		return invoke(endpoint, namespaceURI, operation, user, userPass, null);
	}

	public static String invoke(String endpoint, String namespaceURI,
			String operation, String user, String userPass, Map params)
			throws IOException {
		try {
			if(endpoint==null){
				endpoint = "";
			}

			if(operation != null && !endpoint.endsWith(operation)){
				if(!endpoint.endsWith("/")){
					endpoint = endpoint+"/";
				}

				endpoint = endpoint+operation;
			}

			HttpClient client = new HttpClient();
			if (user!=null && user.trim().length()>0) {
				URL endpointURL = new URL(endpoint);// used to parse out the
													// endpoint
				client.getParams().setAuthenticationPreemptive(true);
				Credentials defaultcreds = new UsernamePasswordCredentials(
						user, userPass);
				client.getState().setCredentials(
						new AuthScope(endpointURL.getHost(), endpointURL
								.getPort(), AuthScope.ANY_REALM), defaultcreds);
			}


			HttpMethod method;
			if (params == null) {
				method = new GetMethod(endpoint);
			} else {
				method = new PostMethod(endpoint);
				Iterator it = params.keySet().iterator();
				NameValuePair[] args = new NameValuePair[params.size()];
				int ct = 0;
				while (it.hasNext()) {
					String key = (String) it.next();
					args[ct] = new NameValuePair(key, (String) params.get(key));
					ct++;
				}
				((PostMethod) method).setRequestBody(args);
			}
			// method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
			// new DefaultHttpMethodRetryHandler(3, false));

			// execute method and handle any error responses.
			client.executeMethod(method);

			String retXml = method.getResponseBodyAsString();
			retXml = retXml.replaceAll("&lt;","<");
            retXml = retXml.replaceAll("&gt;",">");
			try{method.releaseConnection();}catch(Exception e){}
            return retXml;


			/*
			 * // namespaceURI = http://www.interdynaka.com/ // operation =
			 * getOrderStatus // endpoint =
			 * "http://webservicesstg.xpedx.com/gp/eConnectWS.asmx";
			 *
			 * URL url = new URL(endpoint);
			 *
			 * Service service = new Service(); Call call = (Call)
			 * service.createCall();
			 *
			 * call.setTargetEndpointAddress(url); call.setOperationName(new
			 * QName(namespaceURI, operation));
			 *
			 * if (Utility.isSet(user)) {
			 * call.getMessageContext().setUsername(user);
			 * call.getMessageContext().setPassword(userPass); }
			 *
			 * String soapactionURI = namespaceURI + "/" + operation; //
			 * soapactionURI = "http://webservicesstg.xpedx.com/getOrderStatus"
			 * call.setProperty(Call.CONNECTION_TIMEOUT_PROPERTY , new
			 * Integer(7000)); call.setProperty(Call.SOAPACTION_USE_PROPERTY,
			 * new Boolean(true));
			 * call.setProperty(Call.SOAPACTION_URI_PROPERTY, soapactionURI);
			 * call.setReturnType(Constants.XSD_STRING); String ret;
			 *
			 * if(params == null){ ret = (String) call.invoke(new Object[] {
			 * operation }); }else{ Iterator it = params.keySet().iterator();
			 * Object[] args = new Object[params.size()]; int ct = 0;
			 * while(it.hasNext()){ String key = (String) it.next();
			 * call.addParameter(key, XMLType.XSD_INT, ParameterMode.IN);
			 * args[ct] = params.get(key); ct++; } ret = (String)
			 * call.invoke(args); }
			 *
			 *  } catch (ServiceException e) { e.printStackTrace(); throw new
			 * IOException(e.getMessage()); } catch (SOAPException e) {
			 * e.printStackTrace(); throw new IOException(e.getMessage()); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}
}
