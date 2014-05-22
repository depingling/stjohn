<%@ page language="java" %><%@ page import="java.util.Calendar" %><%@ page import="java.util.TimeZone" %><%@ page import="java.text.SimpleDateFormat" %><%
String format = "yyyy.MM.dd 'at' HH:mm:ss";
if(request.getParameter("dateFmt")!=null){
    format=request.getParameter("dateFmt");
}
Calendar cal = Calendar.getInstance();
long offset = cal.getTimeZone().getOffset(cal.getTimeInMillis());
cal.setTimeInMillis(cal.getTimeInMillis() - offset);
String renderType = "html";
if(request.getParameter("fmt")!=null){
    renderType=request.getParameter("fmt");
}
String formattedDate;
try{
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    formattedDate = formatter.format(cal.getTime());
}catch(Exception e){
    formattedDate="dateFmt ("+format+") was not valid";
    renderType="html";//on error default to html.
}
renderType = renderType.toLowerCase();
if("plain".equals(renderType)){
    %><%=formattedDate%><%
}
else if("html".equals(renderType)){%>
<html>
<body>
<!--START DATE-->
<%=formattedDate%>
<!--END DATE-->
<br><br>
<i><b>Note</b></i> All teims are in GMT<br>
<br>
<b>Formatting Options:</b>
<ul>
    <li>
        <i><b>Date Formatting</b></i> A different date formate may be supplied as a request param of the name &quot;dateFmt&quot;.
        <br>
        <i>example:</i> dateFmt=MM/dd/yyyy
        <br>Please note MM is month, mm is minutes.  See http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html for documentation on formatting options.
    </li>
    <li>
        <i><b>Rendering</b></i> If you do not want HTML returned (this page) please specify a &quot;fmt&quot; option.  Valid options are HTML and Plain (case is not important).
        <br>
        <i>example:</i> fmt=plain
    </li>
</ul>
<br>
<i>examples:</i><br>
server_time.jsp?fmt=plain&dateFmt=MMddyyyy<br>
server_time.jsp?dateFmt=MMddyyyy%20HH:mm:ss&fmt=pLaIn
</body>
</html>
<%}else{ //End HTML rendering%>
<%=renderType%> is an INVALID fmt OPTION
<%} //End unknown rendering%>