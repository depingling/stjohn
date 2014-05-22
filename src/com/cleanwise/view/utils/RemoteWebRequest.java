package com.cleanwise.view.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class RemoteWebRequest {

    private static final Logger log = Logger.getLogger(RemoteWebRequest.class);

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String SESSION_ID = "sessionId";

    private String mServer;
    private List<NameValuePair> mParams;
    private Integer mPort;
    private String mAccessToken;
    private String mSessionId;

    public RemoteWebRequest(String pSessionId, String pServer, Integer pPort, String pAccessToken) {
        mSessionId   = pSessionId;
        mServer      = pServer;
        mPort        = pPort;
        mAccessToken = pAccessToken;
        mParams      = new ArrayList<NameValuePair>();
    }

    public String execute(String pMethod) {

        log.info("execute()=> BEGIN, pMethod: " + pMethod);

        String response = null;

        try {

            String              server = getServer();
            Integer               port = getPort();
            String         accessToken = getAccessToken();
            String           sessionId = getSessionId();
            List<NameValuePair> params = getParams();


            String url = "http://" + server + (port != null ?(":"+ port) : "") + "/restws/orca-ext-session-access/" + pMethod;

            log.info("execute()=> URL " + url);

            HttpClient client = new HttpClient();
            GetMethod method = new GetMethod((url));

            setQueryParameter(ACCESS_TOKEN , accessToken);
            setQueryParameter(SESSION_ID   , sessionId);

            method.setQueryString(params.toArray(new NameValuePair[]{}));

            log.info("execute()=> connecting to " + server  + ", port: " + port);

            int status = client.executeMethod(method);

            log.info("execute()=> response status " + status);

            if (status == HttpServletResponse.SC_OK) {
                response = method.getResponseBodyAsString();
            }

            method.releaseConnection();

        } catch (Exception e) { //ignore
        }

        log.info("execute()=> END, response: " + response);

        return response;

    }

    public void setQueryParameter(String pName, String pValue) {
        mParams.add(new NameValuePair(pName, pValue));
    }


    public String getServer() {
        return mServer;
    }

    public List<NameValuePair> getParams() {
        return mParams;
    }

    public Integer getPort() {
        return mPort;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getSessionId() {
        return mSessionId;
    }
}
