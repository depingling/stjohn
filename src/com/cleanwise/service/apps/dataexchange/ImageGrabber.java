package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.URLResponseDataView;
import org.apache.log4j.Logger;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class ImageGrabber implements Runnable {

    protected static Logger log = Logger.getLogger(ImageGrabber.class);

    private static final String EMPTY = "";
    private static final String HTTP_HF_LOCATION = "Location";

    private static final int DEFAULT_CONNECT_TIMEOUT = 30000; //milliseconds;
    private static final int DEFAULT_READ_TIMEOUT = 30000; //milliseconds;
    private static final int READ_BUFFER = 8 * 1024;

    private Set<String> mRequestedUrls;
    private List<URLResponseDataView> mDataList;
    private Integer mConnectTimeOut;
    private Integer mReadTimeOut;

    public ImageGrabber(Set<String> pRequestedUrls) {
        this.mRequestedUrls = pRequestedUrls;
        this.mConnectTimeOut = DEFAULT_CONNECT_TIMEOUT;
        this.mReadTimeOut = DEFAULT_READ_TIMEOUT;
    }

    public ImageGrabber(Set<String> pRequestedUrls, Integer pConnectTimeOut, Integer pReadTimeOut) {
        this.mRequestedUrls = pRequestedUrls;
        this.mConnectTimeOut = pConnectTimeOut == null ? DEFAULT_CONNECT_TIMEOUT : pConnectTimeOut;
        this.mReadTimeOut = pReadTimeOut == null ? DEFAULT_READ_TIMEOUT : pReadTimeOut;
    }

    public void run() {

        log.info("run()=> BEGIN");

        mDataList = read(getRequestedUrls());

        log.info("run()=> END.");
    }

    public List<URLResponseDataView> read(Set<String> pUrls) {

        log.info("read()=> BEGIN."
                + " ConnectTimeOut= " + getConnectTimeOut() + " ms"
                + ", ReadTimeOut= " + getReadTimeOut() + " ms");

        List<URLResponseDataView> dataList = new ArrayList<URLResponseDataView>();

        Map<String, Set<URLRequest>> hostImgUrls = getHostImgUrls();

        for (Map.Entry<String, Set<URLRequest>> imgUrlEntry : hostImgUrls.entrySet()) {

            log.info("read()=> Reading image(s) from '" + imgUrlEntry.getKey() + "' , Img.Count: " + imgUrlEntry.getValue().size());

            for (URLRequest urlReq : imgUrlEntry.getValue()) {

                InputStream is;
                byte[] data;
                try {

                    URL url = urlReq.getUrl();

                    log.debug("read()=> Reading image " + url.toExternalForm() + "(" + urlReq.getRequest() + ")");

                    try {
                        is = getInputStream(url);
                        data = read(is);
                        close(is);
                    } catch (ConnectException e) {
                        log.info("read()=> ERROR: " + e.getMessage());
                        log.warn("read()=> Reading image(s) from '" + url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : EMPTY) + "' has been stopped");
                        break;
                    }

                    log.debug("read()=> Data " + (data != null ? "Length: " + data.length : "is null"));

                    dataList.add(new URLResponseDataView(urlReq.getRequest(), data));

                } catch (CommunicateException e) {
                    log.info("read()=> ERROR: " + e.getMessage());
                } catch (Exception e) {
                    log.info("read()=> ERROR: " + e.getMessage());
                }
            }
        }

        log.info("read()=> END.");

        return dataList;
    }

    private InputStream getInputStream(URL url) throws Exception {
        URLConnection conn = connect(url);
        if (conn.getContentType().startsWith("image/"))
        	return getInputStream(conn);
        else{
        	if (conn instanceof HttpURLConnection && 
        			((HttpURLConnection)conn).getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND){
        			throw new Exception("The URL (" + url + ") does not exist on the server");
        	}else
        		throw new Exception("The URL (" + url + ") does not exist on the server OR the URL exists on the server, but does not contain a valid image");
        }        	
    }

    private InputStream getInputStream(URLConnection pCon) throws Exception {
        if (pCon instanceof HttpURLConnection) {
            return doResponse((HttpURLConnection) pCon);
        } else {
            return pCon.getInputStream();
        }
    }


    private byte[] read(InputStream pInputStream) {

        ByteArrayOutputStream out = null;
        try {

            out = new ByteArrayOutputStream();
            byte[] buffer = new byte[READ_BUFFER];

            int count = 0;
            do {
                out.write(buffer, 0, count);
                count = pInputStream.read(buffer, 0, buffer.length);
            } while (count != -1);

            out.flush();

            return out.toByteArray();

        } catch (Exception e) {
            log.error("read()=> " + e.getMessage());
        } finally {
            close(out);
        }

        return null;
    }

    private void close(InputStream pStream) {
        if (pStream != null) {
            try {
                pStream.close();
            } catch (IOException e) {
                log.error("close()=> " + e.getMessage());
            }
        }
    }

    private static void close(OutputStream pStream) {
        if (pStream != null) {
            try {
                pStream.close();
            } catch (IOException e) {
                log.error("close()=> " + e.getMessage());
            }
        }
    }

    private URLConnection connect(URL pUrl) throws ImageGrabber.ConnectException {

        URLConnection conn;
        try {

            log.debug("connect()=> connect to " + pUrl.getHost() + (pUrl.getPort() > -1 ? ":" + pUrl.getPort() : EMPTY));
            conn = pUrl.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
            conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
            conn.connect();

        } catch (Exception e) {
            throw new ConnectException("Can't connect to " + pUrl.getHost() + (pUrl.getPort() > -1 ? ":" + pUrl.getPort() : EMPTY));
        }

        return conn;

    }

    private InputStream doResponse(HttpURLConnection pCon) throws Exception {

        int responceCode = pCon.getResponseCode();

        log.debug("doResponse()=> responceCode: " + responceCode);

        switch (responceCode) {
            case HttpURLConnection.HTTP_OK         : return do200(pCon);
            case HttpURLConnection.HTTP_MOVED_TEMP : return do302(pCon);
            default: throw new CommunicateException("Bad response status-code: " + responceCode + " for " + pCon.getURL().toExternalForm());
        }

    }

    private InputStream do200(HttpURLConnection pCon) throws Exception {
        log.debug("do200()=> BEGIN. HTTP Status-Code 200: OK.");
        InputStream is = pCon.getInputStream();
        log.debug("do200()=> END.");
        return is;
    }

    private InputStream do302(HttpURLConnection pCon) throws Exception {
        log.debug("do302()=> BEGIN. HTTP Status-Code 302: Temporary Redirect.");
        String redirectUrl = pCon.getHeaderField(HTTP_HF_LOCATION);
        if (!Utility.isSet(redirectUrl)) {
            throw new Exception("Bad response header fields, HTTP Status-Code 302: Temporary Redirect.");
        }
        log.debug("do302()=> redirecting to  " + redirectUrl);
        InputStream is = getInputStream(new URL(redirectUrl));
        log.debug("do302()=> END.");
        return is;
    }

    private Map<String, Set<URLRequest>> getHostImgUrls() {
        Map<String, Set<URLRequest>> hostImgUrls = new HashMap<String, Set<URLRequest>>();
        for (String urlStr : getRequestedUrls()) {
            try {
            	URL url = new URL(urlStr.trim());
                String key = url.getHost() + (url.getPort() > -1 ? ":" + url.getPort() : EMPTY);
                Set<URLRequest> urls = hostImgUrls.get(key);
                if (urls == null) {
                    urls = new HashSet<URLRequest>();
                    hostImgUrls.put(key, urls);
                }
                urls.add(new URLRequest(urlStr, url));
            } catch (MalformedURLException e) {
                log.error("getHostImgUrls()=> " + e.getMessage());
            }
        }
        return hostImgUrls;
    }


    public Set<String> getRequestedUrls() {
        return mRequestedUrls;
    }

    public List<URLResponseDataView> getDataList() {
        return mDataList;
    }

    public Integer getConnectTimeOut() {
        return mConnectTimeOut == null ? DEFAULT_CONNECT_TIMEOUT : mConnectTimeOut;
    }

    public Integer getReadTimeOut() {
        return mReadTimeOut == null ? DEFAULT_READ_TIMEOUT : mReadTimeOut;
    }

    public class ConnectException extends java.net.ConnectException {
        public ConnectException(String s) {
            super(s);
        }
    }

    public class CommunicateException extends IOException {
        public CommunicateException(String s) {
            super(s);
        }
    }

    public class URLRequest implements Serializable {

        private URL mUrl;
        private String mRequest;

        public URLRequest(String pRequest, URL mUrl) {
            this.mRequest = pRequest;
            this.mUrl = mUrl;
        }

        public URL getUrl() {
            return mUrl;
        }

        public void setUrl(URL mUrl) {
            this.mUrl = mUrl;
        }

        public String getRequest() {
            return mRequest;
        }

        public void setRequest(String mRequest) {
            this.mRequest = mRequest;
        }
    }

}
