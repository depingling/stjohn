<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--%@page import="XML.Xml" %-->
<%@page import="nu.xom.Builder" %>
<%@page import="nu.xom.Document" %>
<%@page import="gnu.crypto.mac.IMac" %>
<%@page import="gnu.crypto.mac.HMacFactory" %>
<%@page import="java.util.HashMap" %>
<%
// we need to start the decoding
// assign the response from the querystring
String PMResponse = request.getParameter("r");
// assign the signature from the querystring
String Signature = request.getParameter("s");
// Convert from modified base64 to normal base 64
PMResponse = PMResponse.replaceAll("-","+");
PMResponse = PMResponse.replaceAll("_","/");
Signature = Signature.replaceAll("-","+");
Signature = Signature.replaceAll("_","/");
// Base 64 decode the response.
sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
PMResponse = new String(decoder.decodeBuffer(PMResponse));

sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
String key = (String) session.getAttribute("paymetrics_key");
byte[] CryptoKey = key.getBytes();
gnu.crypto.mac.IMac HashingAlg = gnu.crypto.mac.HMacFactory.getInstance("HMAC-SHA-256");
HashMap attributes = new HashMap();
attributes.put(IMac.MAC_KEY_MATERIAL, CryptoKey);
//attributes.put(IMac.TRUNCATED_SIZE, new Integer(12));

HashingAlg.init(attributes);

HashingAlg.update(PMResponse.getBytes(),0,PMResponse.length());
String OurSig = encoder.encode(HashingAlg.digest());

// read xml values.
//Xml RR = new Xml (PMResponse,"PaymetricResponse");
Document RR = new Builder().build(PMResponse, "PaymetricResponse");

//session.setAttribute("token", RR.getRootElement().getFirstChildElement("CardInfo").getFirstChildElement("CCToken").getValue());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>JSP Page</title>
  <meta http-equiv="Pragma" content="no-cache">  
  <meta http-equiv="Expires" content="-1">
  <meta http-equiv="Cache-Control" content="no-cache">
  <meta http-equiv="Cache-Control" content="private">
</head>
<body>
<% if (0==Signature.compareTo(OurSig)) { // we have a valid signature %>
  <script type="text/javascript" language="javascript">
    window.parent.document.getElementById('xiPayCCToken').value = '<%=RR.getRootElement().getFirstChildElement("CardInfo").getFirstChildElement("CCToken").getValue()%>';
    window.parent.document.getElementById('ccExpMonth').value = '<%=RR.getRootElement().getFirstChildElement("CardInfo").getFirstChildElement("CCExpirationDate").getFirstChildElement("Month").getValue() %>';
    window.parent.document.getElementById('ccExpYear').value = '<%=RR.getRootElement().getFirstChildElement("CardInfo").getFirstChildElement("CCExpirationDate").getFirstChildElement("Year").getValue() %>';
    window.parent.document.getElementById('xiPayCCType').value = '<%=RR.getRootElement().getFirstChildElement("CardInfo").getFirstChildElement("CCType").getValue() %>';
    window.parent.document.getElementById('xiPayBinRangeType').value = '<%=RR.getRootElement().getFirstChildElement("CardInfo").getFirstChildElement("BinRangeType").getValue() %>';
    window.parent.setAndSubmit('CHECKOUT_FORM','command','placeOrder');
  </script>
<%} else { // invalid signature %>
  <script type="text/javascript" language="javascript">
    window.parent.document.getElementById('xiPayCCToken').value = '';
    window.parent.setAndSubmit('CHECKOUT_FORM','command','placeOrder');
  </script>
<%}%>

    </body>
</html>




