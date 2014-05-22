/*
 * HttpUtil.java
 *
 * Created on September 18, 2002, 8:46 PM
 */

package com.cleanwise.service.apps;
import com.meterware.httpunit.*;
import java.net.MalformedURLException;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author  bstevens
 */
public class HttpUtil {
    private static final Logger log = Logger.getLogger(HttpUtil.class);
    public static final int MATCH_CONTAINS = 0;
    public static final int MATCH_EQUALS = 1;
    public static final int MATCH_PAGE_EQUALS = 2;
    
    public static final int ADMIN = 0;
    public static final int CUSTOMER = 1;
    
    public Properties properties;
    
    private String protocol = "http:";
    private String context = "/cleanwise";
    private String logonPage = "/userportal/logon.do";
    
    private String user;
    private String password;
    
    private String host = "localhost";
    private int portInt=0;
    public String getProtocol(){return protocol;}
    public void setPort(int pPort){
        portInt = pPort;
    }
    public void setHost(String pHost){
        host = pHost;
    }
    
    public String getHost(){
        String port;
        if(portInt != 0){
            port = Integer.toString(portInt);
        }else{
            if(properties == null){
                setUpOptions(false,true,true,false);
            }
            port = properties.getProperty("ServletConfidentialPort");
            if(port == null){
                port = properties.getProperty("ServletContainerPort");
            }
        }
        if (port == "80"){
            return host;
        }
        return host+":"+port;
    }
    public String getContext(){return context;}
    public String getLogonPage(){return logonPage;}
    
    public String getUserName(){return user;}
    public String getPassword(){return password;}
    public void setUserName(String pUser){
        user = pUser;
    }
    public void setPassword(String pPassword){
        password = pPassword;
    }
    
    public WebRequest mkUrl(String pLinkUrl) {
        return new GetMethodWebRequest( getProtocol() + "//"
        + getHost() +
        getContext() + pLinkUrl );
    }
    
    public WebConversation login() throws
    MalformedURLException,SAXException,IOException{
        WebConversation wc = new WebConversation();
        WebRequest     req = new GetMethodWebRequest( getProtocol() + "//" + getHost() + getContext() + getLogonPage() );
        WebResponse   resp = wc.getResponse( req );
        WebForm form = resp.getForms()[0];      // select the first form in the page
        form.setParameter("j_username",getUserName());
        form.setParameter("j_password",getPassword());
        
        
        resp = form.submit();
        /*if (resp.getURL().toString().endsWith("selectshippingaddress.do")){
            //select first shipping address
            form = resp.getFormWithName("SelectShippingAddress");
            String shipToAddr = form.getOptionValues("shippingAddressKey")[1];
            form.getOptions("shippingAddressKey")[1]);
            form.setParameter("shippingAddressKey",shipToAddr);
            form.getScriptableObject().setParameterValue("action","select");
            form.getScriptableObject().submit();
            resp = wc.getCurrentPage();
            //now submit the form with the properly selected address
            form = resp.getFormWithName("SelectShippingAddress");
            form.setParameter("action","submit");
            resp = form.submit();
        }*/
        return wc;
    }
    
    /**
     * LogOut.
     */
    public WebResponse logOut(WebConversation wc)
    throws SAXException,IOException{
        WebResponse wr = wc.getCurrentPage();
        List wls = getLinksWithUrl(wr,"logoff.do", HttpUtil.MATCH_PAGE_EQUALS);
        if(wls == null || wls.size() == 0){
            throw new NullPointerException("Could not find logoff link");
        }
        WebLink wl = (WebLink)wls.get(0);
        return wl.click();
    }
    
    
    
    /**
     *Sets up any http unit options we need.
     */
    public void setUpOptions(boolean pExceptionOnError,boolean pPrintHTMLErrors,boolean pExceptionOnWarning,boolean pPrintHTMLWarnings){
        try{
            //first remove the default listners and add ours
            /*java.util.List l = HttpUnitOptions.getHtmlErrorListeners();
           * for(int i=0;i<l.size();i++){
            *    HTMLParserFactory.removeHtmlParserListener((HtmlErrorListener) l.get(i));
           * }
           * */
            //HTMLParserfactory.addHtmlParserListener(new HtmlErrorListenerImpl(pExceptionOnError,pPrintHTMLErrors,pExceptionOnWarning,pPrintHTMLWarnings));
            HttpUnitOptions.setImagesTreatedAsAltText(true);
            properties = new Properties();
            try{
                properties.load(new FileInputStream("installation.properties"));
            }catch(java.io.FileNotFoundException e){}
        }catch (IOException e){
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
    
    public List getLinksWithUrl(WebResponse pWr,String pText, int match)
    throws SAXException{
        WebLink[] links = pWr.getLinks();
        ArrayList matchedLinks = new ArrayList();
        switch (match){
            case MATCH_CONTAINS:
                for(int i=0;i<links.length;i++){
                    if (links[i].getURLString().indexOf(pText) > 0){
                        matchedLinks.add(links[i]);
                    }
                }
                break;
            case MATCH_EQUALS:
                for(int i=0;i<links.length;i++){
                    if (links[i].getURLString().equals(pText)){
                        matchedLinks.add(links[i]);
                    }
                }
                break;
            case MATCH_PAGE_EQUALS:
                for(int i=0;i<links.length;i++){
                    String baseUrl = stripParams(links[i].getURLString());
                    if (baseUrl.endsWith(pText)){
                        matchedLinks.add(links[i]);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("invalid match type: " + match);
        }
        return matchedLinks;
    }
    
    /**
     *Strips out any of the extranious paramaters from the url:
     *ex:
     *in --> http://www.cleanwise.com/index.jsp?param1=abcd
     *out --> http://www.cleanwise.com/index.jsp
     */
    public String stripParams(String pUrl){
        int firstIndx = pUrl.indexOf('?');
        if(firstIndx > 0){
            return pUrl.substring(0,firstIndx);
        }
        return pUrl;
    }
    
    public boolean areTheSamePage(String pUrl1,String pUrl2){
        return stripParams(pUrl1).equals(stripParams(pUrl2));
    }
    
    /**
     *simple HtmlErrorListenerImpl that allows us to suppress HTML errors.
     */
    public class HtmlErrorListenerImpl implements HtmlErrorListener {
        boolean exceptionOnWarning;
        boolean printHTMLWarnings;
        boolean exceptionOnError;
        boolean printHTMLErrors;
        /** Creates a new instance of HtmlErrorListenerImpl */
        public HtmlErrorListenerImpl(boolean pExceptionOnError,boolean pPrintHTMLErrors,boolean pExceptionOnWarning,boolean pPrintHTMLWarnings) {
            exceptionOnWarning = pExceptionOnWarning;
            printHTMLWarnings = pPrintHTMLWarnings;
            exceptionOnError = pExceptionOnError;
            printHTMLErrors = pPrintHTMLErrors;
        }
        
        /** callback for HTML errors.
         *
         */
        public void error(URL url, String msg, int line, int column) {
            if(printHTMLErrors){
                log.info("Error: "+url+"::"+msg+" at line: "+line+" column: "+column);
            }
            if(exceptionOnError){
                throw new RuntimeException("Error: "+url+"::"+msg+" at line: "+line+" column: "+column);
            }
        }
        
        /** callback for HTML warning.
         *
         */
        public void warning(URL url, String msg, int line, int column) {
            if(printHTMLWarnings){
                log.info("Warn: "+url+"::"+msg+" at line: "+line+" column: "+column);
            }
            if(exceptionOnWarning){
                throw new RuntimeException("Warn: "+url+"::"+msg+" at line: "+line+" column: "+column);
            }
        }
        
    }
}
