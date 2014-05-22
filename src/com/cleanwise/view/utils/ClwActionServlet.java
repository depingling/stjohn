package com.cleanwise.view.utils;
import org.apache.struts.action.ActionServlet;

import com.cleanwise.view.logic.LogOnLogic;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;
import java.util.Date;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;


public class ClwActionServlet extends ActionServlet {

    private static final Logger log = Logger.getLogger(ClwActionServlet.class);

    public void service(ServletRequest req, ServletResponse res)
    throws ServletException, IOException {
        String requestId = "" + System.currentTimeMillis();
        req.setAttribute("requestId", requestId);
        Date ddd = new Date();
        javax.servlet.http.HttpServletRequest request;
        HttpServletResponse response;        
        try {
            request = (HttpServletRequest)req;
            response = (HttpServletResponse)res;
        } catch(ClassCastException classcastexception) {
            throw new ServletException("non-HTTP request or response");
        }
        String jbossVersion = System.getProperty("jbossVersion","");
        //request.setCharacterEncoding("UTF-8"); 
        if(!jbossVersion.startsWith("2.")) {
            try {
                Class reqClass = request.getClass();
                Class[] params = new Class[] {String.class};
                java.lang.reflect.Method setCharacterEncodingMethod = 
                          reqClass.getMethod("setCharacterEncoding",params);
                setCharacterEncodingMethod.invoke(request,(new Object[]{"UTF-8"}));
                log.info("ClwActionServlet WWWWWWWWW UTF-8 encoding IS set");
            } catch (Exception exc){
                log.info("ClwActionServlet WWWWWWWWW UTF-8 encoding IS NOT set");
            }
        }
        //log.info("ClwActionServlet WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW charSet: "+charSet);
        SessionTool st = new SessionTool(request);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss:ms");
        Date sTime = new Date();
        String sTimeS = sdf.format(sTime);
        String referer = request.getHeader("Referer");
        HttpSession session = request.getSession();
        String sessionId=session.getId();
        String cuser = (String) session.getAttribute(Constants.USER_NAME);
        String mess = "@@##@@S  " + "Referer: <"+referer+">";
        mess += " RequestId: <" + requestId +">";
        mess += " Started at: <"+sTimeS+">";
        mess += " Session id : <"+sessionId+">";
        mess += " User: <"+cuser+">";
        mess += " Request URI: <" + request.getRequestURI() + ">";
        mess += " Request Protocol: <" + request.getProtocol() + ">";
        mess += " Servlet path: <" + request.getServletPath() + ">";
        mess += " Path info: <" + request.getPathInfo() + ">";
        mess += getRequestCookiesInfo(request);
        mess += "@@##@@";
        log.info(mess+" 1 Request information: "+st.mkReqInfoString(1000));

        LoginContext loginContext = (LoginContext) session.getAttribute(Constants.CALLER_PARAMETERS_CONTEXT);
        if (loginContext == null) {
        	log.info("calling CallerParametersStJohn.initDefaultMultiDbAccess(request)");
            CallerParametersStJohn.initDefaultMultiDbAccess(request);
        } else {
            try {
            	log.info("loginContext != null");
                loginContext.login();
            } catch (LoginException exc) {
                exc.printStackTrace();
            }
        }

        if ( st.checkSession() == false ) {
            LogOnLogic.initAnonymousUser(request,response);
            //String cookiePath = (String) session.getAttribute("pages.root");
            String cookiePath = "/"; //ignore context
            Object value = request.getHeader("X-Forwarded-For");
            if(value == null){
                value = request.getRemoteAddr();
                if(value == null){
                    value = "unknown";
                }
            }
            SessionTool.setCookie(response,request,SessionTool.COOKIE_ORIG_IP, value.toString(),cookiePath);
            SessionTool.setCookie(response,request,SessionTool.COOKIE_SERVER_KEY, session.getId(),cookiePath);
            
        }

        try {
            super.service(req,res);
        } catch(IOException exc) {
            Date eTime = new Date();
            String eTimeS = sdf.format(eTime);
            double dur = (double) eTime.getTime()-sTime.getTime();
            dur /= 1000;
            mess = "@@##@@E  " + "Referer: <"+referer+">";
            mess += " RequestId: <" + requestId +">"; 
            mess += " Started at: <"+sTimeS+">";
            mess += " Ended at: <"+eTimeS+">";
            mess += " Result: <Errror>";
            mess += " Duration: <"+dur+">";
            mess += " Session id : <"+sessionId+">";
            mess += " User: <"+cuser+">";
            mess += " Request URI: <" + request.getRequestURI() + ">";
            mess += " Request Protocol: <" + request.getProtocol() + ">";
            mess += " Servlet path: <" + request.getServletPath() + ">";
            mess += " Path info: <" + request.getPathInfo() + ">";
            mess += getRequestCookiesInfo(request);
            mess += "@@##@@";
	        log.info(mess+" 2 Request information: "+st.mkReqInfoString(-1));
            exc.printStackTrace();
            throw exc;
        } catch(ServletException exc) {
            Date eTime = new Date();
            String eTimeS = sdf.format(eTime);
            double dur = (double) eTime.getTime()-sTime.getTime();
            dur /= 1000;
            mess = "@@##@@E  " + "Referer: <"+referer+">";
            mess += " RequestId: <" + requestId +">";
            mess += " Started at: <"+sTimeS+">";
            mess += " Ended at: <"+eTimeS+">";
            mess += " Result: <Errror>";
            mess += " Duration: <"+dur+">";
            mess += " Session id : <"+sessionId+">";
            mess += " User: <"+cuser+">";
            mess += " Request URI: <" + request.getRequestURI() + ">";
            mess += " Request Protocol: <" + request.getProtocol() + ">";
            mess += " Servlet path: <" + request.getServletPath() + ">";
            mess += " Path info: <" + request.getPathInfo() + ">";
            mess += getRequestCookiesInfo(request);
            mess += "@@@@";
	        log.info(mess+" 3 Request information: "+st.mkReqInfoString(-1));
            exc.printStackTrace();
            throw exc;
        }
        /////////////////////
        Date eTime = new Date();
        String eTimeS = sdf.format(eTime);
        double dur = (double) eTime.getTime()-sTime.getTime();
        dur /= 1000;
        mess = "@@##@@E  " + "Referer: <"+referer+">";
        mess += " RequestId: <" + requestId +">";
        mess += " Started at: <"+sTimeS+">";
        mess += " Ended at: <"+eTimeS+">";
        mess += " Result: <OK>";
        mess += " Duration: <"+dur+">";
        mess += " Session id : <"+sessionId+">";
        mess += " User: <"+cuser+">";
        mess += " Request URI: <" + request.getRequestURI() + ">";
        mess += " Request Protocol: <" + request.getProtocol() + ">";
        mess += " Servlet path: <" + request.getServletPath() + ">";
        mess += " Path info: <" + request.getPathInfo() + ">";
        mess += getRequestCookiesInfo(request);
        mess += "@@##@@";
	    log.info(mess+"\n"+st.mkReqInfoString(1000));
    }
    
    private String getRequestCookiesInfo(HttpServletRequest request) {
        com.cleanwise.view.utils.SessionTool st =
                new com.cleanwise.view.utils.SessionTool(request);
        return st.mkCookieInfoString();
    }
}
