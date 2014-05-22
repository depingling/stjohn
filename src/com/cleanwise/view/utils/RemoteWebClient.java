package com.cleanwise.view.utils;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.IdVector;

import javax.jms.Session;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RemoteWebClient {

    private Integer mPort;
    private String mServer;
    private String mAccessToken;

    public RemoteWebClient(String pServer, Integer pPort, String pAccessToken) {
        mServer = pServer;
        mPort = pPort;
        mAccessToken = pAccessToken;
    }

    public IdVector determineUnAvailableStNumbers(String pSessionId, CleanwiseUser pUser, List<Integer> pValidNumbers, Date pDate, boolean pRestrictByPeriod) throws Exception {


        try {

            String server = getServer();
            Integer  port = getPort();
            String  token = getAccessToken();

            RemoteWebRequest webRequest = new RemoteWebRequest(pSessionId, server, port, token);

            webRequest.setQueryParameter("serviceTicketNumbers", Utility.toCommaSting(pValidNumbers));
            webRequest.setQueryParameter("verifyForDate", new SimpleDateFormat(Constants.SIMPLE_DATE_PATTERN).format(pDate));
            webRequest.setQueryParameter("storeId", String.valueOf(pUser.getUserStore().getStoreId()));
            webRequest.setQueryParameter("accountId", String.valueOf(pUser.getUserAccount().getAccountId()));
            webRequest.setQueryParameter("siteId", String.valueOf(pUser.getSite().getSiteId()));
            webRequest.setQueryParameter("userId", String.valueOf(pUser.getUserId()));
            webRequest.setQueryParameter("restrictByPeriod", String.valueOf(pRestrictByPeriod));

            String response = webRequest.execute("serviceticket-site/verifynumbers");

            if (response == null) {
                throw new Exception("server response is null");
            }

            return Utility.parseIdStringToVector(response, ",", true);

        } catch (Exception e) {
            throw new Exception("Unable to determine the available" +
                    " service tickets for site," +
                    " SiteID: " + pUser.getSite().getSiteId() + ". Error: " + e.getMessage());

        }


    }


    public Integer getPort() {
        return mPort;
    }

    public String getServer() {
        return mServer;
    }

    public String getAccessToken() {
        return mAccessToken;
    }
}
