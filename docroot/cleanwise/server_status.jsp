<%@ page language="java" %><%@ page import="com.cleanwise.service.api.*" %><%@ page import="com.cleanwise.service.api.session.*" %><%@ page import="java.rmi.RemoteException" %><%@ page import="java.lang.Runtime" %><%
boolean dbIsGood = false;
boolean memoryIsGood;
long current = System.currentTimeMillis();
long last = 0;
boolean now;
String renderType = "html";
if(request.getParameter("fmt")!=null){
    renderType=request.getParameter("fmt");
}
if (null != System.getProperty("server.status.db.last.request.time")) {
  try {
    last = Long.parseLong(System.getProperty("server.status.db.last.request.time"));
  } catch (Exception ex) {
    ex.printStackTrace();
  }
}
if (last == 0 || (current - last)/1000 > @server.status.db.interval@) {
  System.setProperty("server.status.db.last.request.time", "" + current);
  now = true;
  try {
    APIAccess factory = new APIAccess();
    InboundFiles ifEjb = factory.getInboundFilesAPI();
    ifEjb.getInboundFileCount();
    dbIsGood = true;
  } catch (RemoteException ex) {
    dbIsGood = false;
  } finally {
    System.setProperty("server.status.db.last.request.duration", "" + (System.currentTimeMillis() - current));
    System.setProperty("server.status.db.last.request.result", dbIsGood?"DB_GOOD":"DB_BAD");
  }
} else {
  now = false;
  dbIsGood = "DB_GOOD".equals(System.getProperty("server.status.db.last.request.result"));
}

long maxMemory = Runtime.getRuntime().maxMemory();  
long allocatedMemory = Runtime.getRuntime().totalMemory();  
long freeMemory = Runtime.getRuntime().freeMemory();
freeMemory = (freeMemory + (maxMemory - allocatedMemory));

long threshold = @server.status.memory.threshold@;
if ((freeMemory/(1024*1024) - threshold) > 0) {
  memoryIsGood = true;
} else {
  memoryIsGood = false;
}

renderType = renderType.toLowerCase();
if("plain".equals(renderType)){
  if (dbIsGood && memoryIsGood) {
    out.write("GOOD");
  } else {
    out.write("BAD");
  }
}
else {%><html>
<body>
<!--START STATUS--><%
  if (dbIsGood && memoryIsGood) {
    out.write("GOOD");
  } else {
    out.write("BAD");
  }
%><!--END STATUS-->
<br><br>
<%if (dbIsGood) {%>
<i><b>Database is GOOD</b></i>
<%} else {%>
<i><b>Database is BAD</b></i>
<%}%>
<br>
<i>Database request duration:&nbsp;</i><%=System.getProperty("server.status.db.last.request.duration")%>&nbsp;milliseconds
<%if (!dbIsGood) {%>
&nbsp;(timeout)
<%}%>
<br>
<%if (!now) {%>
<%=(current - last)/1000%>&nbsp;seconds ago
<%}%>

<br><br>
<%if (memoryIsGood) {%>
<i><b>Free memory is GOOD</b></i>
<%} else {%>
<i><b>Free memory is BAD</b></i>
<%}%>
<br>
<i>Maximum memory amount:&nbsp;</i><%=Runtime.getRuntime().maxMemory()/(1024*1024)%>&nbsp;megabytes
<br>
<i>Total memory amount:&nbsp;</i><%=Runtime.getRuntime().totalMemory()/(1024*1024)%>&nbsp;megabytes
<br>
<i>Free memory amount:&nbsp;</i><%=freeMemory/(1024*1024)%>&nbsp;megabytes
<br>
<i>Free memory threshold:&nbsp;</i><%=threshold%>&nbsp;megabytes



<br><br>
        <i><b>Rendering</b></i> If you do not want HTML returned (this page) please specify a &quot;fmt&quot; option.  Valid options are HTML and Plain (case is not important).
        <br>
        <i>example:</i> fmt=plain
<br>
</body>
</html>
<%}%>
