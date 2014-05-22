<%@page language="java" %>

<script language="javascript">

var xmlHttp

function showStatus(str)
{
  if (str.length<3)
  { 
    document.getElementById("txtHint").innerHTML=""
    return
  }
  xmlHttp=GetXmlHttpObject()
  if (xmlHttp==null)
  {
    alert ("Browser does not support HTTP Request")
    return
  } 
  var url="/cleanwise/devutil/xri.jsp"
  url=url+"?action=xmlreq&str="+str
  url=url+"&sid="+Math.random()
  xmlHttp.onreadystatechange=stateChanged 
  xmlHttp.open("GET",url,true)
  xmlHttp.send(null)
} 

function stateChanged() 
{ 
  if (xmlHttp.readyState==4 || xmlHttp.readyState=="complete")
  { 
    display = '<table>';
    myNodes = xmlHttp.responseXML.getElementsByTagName('server');
    for(var counter=0;counter<myNodes.length;counter++) {
      display += "<tr><td>Server</td><td> " + myNodes.item(counter).firstChild.nodeValue +"</td></tr>";
    }
    myNodes = xmlHttp.responseXML.getElementsByTagName('msg');
    for(var counter=0;counter<myNodes.length;counter++) {
      display += "<tr><td>Message</td><td> " + myNodes.item(counter).firstChild.nodeValue +"</td></tr>";
    }
    myNodes = xmlHttp.responseXML.getElementsByTagName('freemem');
    for(var counter=0;counter<myNodes.length;counter++) {
      display += "<tr><td>Free memory</td><td> " + myNodes.item(counter).firstChild.nodeValue +"</td></tr>";
    }
    myNodes = xmlHttp.responseXML.getElementsByTagName('totalmem');
    for(var counter=0;counter<myNodes.length;counter++) {
      display += "<tr><td>Total memory</td><td> " + myNodes.item(counter).firstChild.nodeValue +"</td></tr>";
    }
    myNodes = xmlHttp.responseXML.getElementsByTagName('rfreemem');
    for(var counter=0;counter<myNodes.length;counter++) {
      display += "<tr><td>Ratio of Free memory</td><td> " + myNodes.item(counter).firstChild.nodeValue +"</td></tr>";
    }
    myNodes = xmlHttp.responseXML.getElementsByTagName('threads');
    for(var counter=0;counter<myNodes.length;counter++) {
      display += "<tr><td>Number of threads</td><td> " + myNodes.item(counter).firstChild.nodeValue +"</td></tr>";
    }
    myNodes = xmlHttp.responseXML.getElementsByTagName('sessioncount');
    for(var counter=0;counter<myNodes.length;counter++) {
      display += "<tr><td>Number of sessions</td><td> " + myNodes.item(counter).firstChild.nodeValue +"</td></tr>";
    }
    //alert('display='+display);
    document.getElementById("txtStatus").innerHTML=display;
  } 
} 

function GetXmlHttpObject()
{ 
  var objXMLHttp=null
  if (window.XMLHttpRequest)
  {
    objXMLHttp=new XMLHttpRequest()
  }
  else if (window.ActiveXObject)
  {
    objXMLHttp=new ActiveXObject("Microsoft.XMLHTTP")
  }
  return objXMLHttp
} 

</script>

<html>
<head>
<title>Server Status Information</title>
<META HTTP-EQUIV="Refresh" CONTENT="30">
</head>
<body onload='showStatus("-- status")'>

<p>Server status (reloads in 30 seconds)<br> 
<pre>
<span id="txtStatus"></span>
</pre> 
</p> 


</body>
</html>


<%
// invalidate any sessions no longer in use.

java.util.Hashtable loginSessions =
  (java.util.Hashtable) pageContext.getAttribute(
    "login.session.vector", PageContext.APPLICATION_SCOPE );

if ( null != loginSessions ) {

  java.util.Enumeration sesKeys = loginSessions.keys();

  while (sesKeys.hasMoreElements()) {

  javax.servlet.http.HttpSession thisSes =
    (javax.servlet.http.HttpSession)sesKeys.nextElement();
  String userName = null;

  try { userName = (String)thisSes.getAttribute("LoginUserName"); }
  catch (Exception e) {}
  if ( null == userName ) {
    loginSessions.remove(thisSes);
  }
  }
  }
%>
