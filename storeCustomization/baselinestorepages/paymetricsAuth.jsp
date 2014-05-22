<%@page import="gnu.crypto.mac.IMac" %>
<%@page import="gnu.crypto.mac.HMacFactory" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Properties" %>
<%@page import="java.io.*" %>
<%@page import="com.cleanwise.view.utils.*" %>
<%@page import="com.cleanwise.service.api.util.Utility" %>

<%
String root = (String) session.getAttribute("pages.root"); %>
<% /*** new code, which is looking for the HTTP type: Begin ***/ %>
<% 
   //String jbossHomeDirectory = Utility.loadProperties("defst.default.properties").getProperty("jbossHome"); YKR25
   String jbossHomeDirectory = System.getProperty("jbossHome"); 
%>
<% String propFileName = jbossHomeDirectory + "/xsuite/app.properties"; // full path to the properties file in production - uncomment after testing %>
<% Properties props = new Properties(); %>
<% java.io.File f = new java.io.File(propFileName);%>
<% props.load(new FileInputStream(propFileName) ); %>
<% //getting the value of a httpType property %>
<% String strHttpType = props.getProperty("httpType"); %>
<% 

/*** new code, which is looking for the HTTP type: End ***/


//String redirectUrl =  "https://" +
//                     request.getServerName() + 
//                     root + ClwCustomizer.getStoreFilePath(request,"paymetricsResponse.jsp");
%>
<% 
/*** new code, which is looking for the HTTP type: Begin ***/
String redirectUrl = strHttpType + "://" +
                     request.getServerName() + 
                     root + ClwCustomizer.getStoreFilePath(request,"paymetricsResponse.jsp");
/*** new code, which is looking for the HTTP type: End ***/
// Specify the GUID here.
String MyGUID = (String) session.getAttribute("paymetrics_guid");
String amt = (String) session.getAttribute("paymetrics_amount");
amt = amt == null?"0":amt;
String cur = (String) session.getAttribute("paymetrics_cyrrency");
cur = cur == null?"USD":cur;
// specify the XML payload here
String XMLPayload = "<Request><MerchantReference>WEBTEST</MerchantReference><TotalAmount>" +
                                     "<Tax></Tax><GrandTotal>" + amt +
                                     "</GrandTotal><CurrencyCode>" + cur +
                                     "</CurrencyCode>" +
                                     "</TotalAmount><BillToInfo><ID></ID><Name><FirstName></FirstName>" +
                                     "<LastName></LastName><MiddleName></MiddleName></Name>" +
                                     "<Address><Address1></Address1><Address2></Address2>" +
                                     "<City></City><CountryCode></CountryCode><PostalCode></PostalCode>" +
                                     "<State></State></Address><PhoneNumber></PhoneNumber>" +
                                     "<Email></Email><Reference></Reference><Description></Description>" +
                                     "</BillToInfo><ShipToInfo><ID></ID><Name><FirstName></FirstName><LastName></LastName>" +
                                     "<MiddleName></MiddleName></Name><Address><Address1></Address1>" +
                                     "<Address2></Address2><City></City><CountryCode></CountryCode>" +
                                     "<PostalCode></PostalCode><State></State></Address><PhoneNumber></PhoneNumber>" +
                                     "<Email></Email><Reference></Reference><Description></Description></ShipToInfo>" +
                                     "<TokenizedCard><CCToken></CCToken><CCExpirationDate><Month></Month><Year></Year></CCExpirationDate>" +
                                     "<CCType></CCType></TokenizedCard><RedirectURL>" + redirectUrl + "</RedirectURL></Request>";
// setup the base64 encoding object.
sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
String key = (String) session.getAttribute("paymetrics_key");
key = key == null?"":key;
byte[] CryptoKey = key.getBytes();
gnu.crypto.mac.IMac HashingAlg = gnu.crypto.mac.HMacFactory.getInstance("HMAC-SHA-256");
HashMap attributes = new HashMap();
attributes.put(IMac.MAC_KEY_MATERIAL, CryptoKey);


HashingAlg.init(attributes);

HashingAlg.update(XMLPayload.getBytes(),0,XMLPayload.length());
String GnuSignature = encoder.encode(HashingAlg.digest());

// generate the payload that include the XML and the signature. This will be written to the html later.
String SignedPayload = "<MerchantRequest><MerchantGUID>" + MyGUID + "</MerchantGUID><Signature>" + GnuSignature + "</Signature>" + XMLPayload + "</MerchantRequest>";
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DI EComm Request</title>
		  <meta http-equiv="Pragma" content="no-cache">  
		  <meta http-equiv="Expires" content="-1">
		  <meta http-equiv="Cache-Control" content="no-cache">
		  <meta http-equiv="Cache-Control" content="private">
        <script language="javascript" type="text/javascript">
            var Language = {"CCType":"My Credit Card Type", "CCNumber":"Credit 1 Card 2 Number", "ExpDate":"Expiration Date", "StartDate":"Start Date", "IssuerNumber":"Issuer Number", "PayNow":"Continue", "CVV":"My Custom CVV", "":""}
            function MerchantValidationCheck(BinRange, ExpirationMonth, ExpirationYear, CVV, StartMonth, StartYear, Issuernumber) {
//                alert("BinRange: " + BinRange);
                return true;
            }
        </script>
        <style type="text/css">
            .DataInterceptCreditCardType {
               font-size: 10px;
               font-weight: bold;
               vertical-align: middle;
            }

            .DataInterceptCreditCardNumber {
               font-size: 10px;
               font-weight: bold;
               vertical-align: middle;
            }

            .DataInterceptStartDate {
               font-size: 10px;
               font-weight: bold;
               vertical-align: middle;
            }

            .DataInterceptExpirationDate {
               font-size: 10px;
               font-weight: bold;
               vertical-align: middle;
            }

            .DataInterceptIssuerNumber {
               font-size: 10px;
               font-weight: bold;
               vertical-align: middle;
            }

            .DataInterceptCVV {
               font-size: 10px;
               font-weight: bold;
               vertical-align: middle;
            }

            .DataIntercept input{
               text-align: center;
               font-size: 12px;
               margin-top: 2px;
               margin-bottom: 2px;
            }

        </style>
    </head>
    <body>
        <form action="" method="post">
	<%if (1>0){%>
            <!--table border="1">
              <tr>
                <td-->
                  <div style="width: 300px;">
                    <%--script src="https://devapp02.xisecurenet.com/diecomm/Preloader/EN.ashx?GUID=<%= MyGUID%>" type="text/javascript" language="javascript"></script--%>
                    <script src="<%=System.getProperty("DataIntercept.URL")%>?GUID=<%= MyGUID%>" type="text/javascript" language="javascript"></script>
                  </div>
                  <script type="text/javascript" language="javascript">

                ////////////////////////////////////////////////////////////////////////
                //
                // The call below is required to populate the form that will be posted
                // back to the Paymetric Webservers. If you do not make this call the
                // form will not populate and an error will be generated when the client
                // tries to hit the Pay Now button
                //
                ////////////////////////////////////////////////////////////////////////
                UpdatePaymentPageContent('<%= SignedPayload%>');
                </script>
                <!--/td>
                <td>
                  <input type="text" id="ownnerName">
                </td>
              </tr>
            </table-->
	<%}else	{%>

	<%}%>
        </form>
    </body>
</html>
