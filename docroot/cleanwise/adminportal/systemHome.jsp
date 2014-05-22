<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="java.util.*" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<style>
pre { font-size: medium; }

.bb  { font-family: Tahoma, Verdana, sans-serif;
        color: #e76931 ;
	font-weight:heavy;
	font-size: 12px;
border: solid 1px black; width: 250;
}

</style>

<app:setLocaleAndImages/>
<app:checkLogon/>
<bean:define id="Location" value="none" type="java.lang.String" toScope="session"/>
<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">
<%
        String action = request.getParameter("action");
        if ( action == null ) action = "init";
%>
<table border=0 width="769" cellpadding="0" cellspacing="0">
<tr>
  <td>
    <jsp:include flush='true' page="ui/systemToolbar.jsp"/>
  </td>
</tr>
<tr>
  <td>
    <jsp:include flush='true' page="ui/loginInfo.jsp"/>
</td>
</tr>
</table>


<table>
<tr>
  <td align=left valign=top width=200>
  
<%
    long heapSize = Runtime.getRuntime().totalMemory();
    
    // Get maximum size of heap in bytes. The heap cannot grow beyond this size.
    // Any attempt will result in an OutOfMemoryException.
    long heapMaxSize = Runtime.getRuntime().maxMemory();
    
    // Get amount of free memory within the heap in bytes. This size will increase
    // after garbage collection and decrease as new objects are created.
    long heapFreeSize = Runtime.getRuntime().freeMemory();
%>  
Max Memory: <i18n:formatNumber value="<%=new Long(heapMaxSize)%>" locale="<%=Locale.US%>"/><br>
Total Memory: <i18n:formatNumber value="<%=new Long(heapSize)%>" locale="<%=Locale.US%>"/><br>
Free Memory: <i18n:formatNumber value="<%=new Long(heapFreeSize)%>" locale="<%=Locale.US%>"/><br>
<br>



<br>
<div class="bb"> 
    <a href="systemhome.do?action=flushViewCache">Flush View Cache</a> (Message Resources, RefCodes, etc)
    <% if(action.equals("flushViewCache")){
	    I18nUtil.flushCache();
        CachedViewDataManager.flushCache();
		ClwMessageResourcesImpl.requestReload(request);
        %><BR>Reload Currency and Locales-Done
		<BR>Flushing Cache-Done
		<BR>Flushing Message Resources (translations)-Done
		<%
    }%>  
</div>
<br>


<br>
<div class="bb"> 
    <a href=>Set System Property</a>
	<form method="post" action="systemhome.do?action=setSystemProperty">
		<input type="text" name="systempropkey" size="15" value="">
		<input type="text" name="systempropvalue" size="15" value="">
		<input type='submit'/> 
	</form>
    <% if(action.equals("setSystemProperty")){
		String key = request.getParameter("systempropkey");
		String value = request.getParameter("systempropvalue");
        System.setProperty(key, value);
        %>
		Set [<%=key%>] to [<%=value%>]
		<%
    }%>  
</div>
<br>


<div class="bb"> 
<a href="systemhome.do?action=freeMemory2">Free Memory</a>
<%        if(action.equals("freeMemory2")){        
Runtime.getRuntime().gc();
Runtime.getRuntime().runFinalization();%>
Free Memory: <%=new Long(Runtime.getRuntime().freeMemory())%><br>
Total Memory: <%=new Long(Runtime.getRuntime().totalMemory())%><br>
Garbage Collection and Finalizations-Done
<%}%>
</div>


<br>
<br><div class="bb"> 
<a href="systemhome.do?action=refresh_content">Refresh content</a>
</div>


<br>
<div class="bb"> 
<a href="systemhome.do?action=verifyDao">Verify DAO layer</a>
<%      List errs = (List) request.getAttribute("daoErrors");  
        if(errs != null){ 
			  Iterator it = errs.iterator();
			  while(it.hasNext()){
			      String anErr = (String) it.next();
				  %>
				  Error: <%=anErr%><br>
				  <%
			  }
	    }
%>
</div>
<br>
<div class="bb"> 
<a href="systemhome.do?action=browseDatabase">Browse the database</a>
</div>
<br>
<div class="bb"> 
<a href="systemhome.do?action=show_debug">Show debug info</a>
</div>
<br><div class="bb"> 
<a href="systemhome.do?action=show_cache_info">Show cached info</a>
</div>

  </td>

<td align=left>



<%        if(action.equals("show_debug")){        %>
<app:debugInfo/> 
<%}%>


<%        if(action.equals("view")){        %>
                <jsp:include flush='true' page="systemMonitor.jsp"/>
<%}%>

<%        if(action.equals("show_cache_info")){        %>
<pre>
<%= com.cleanwise.service.api.util.CacheManager.getCacheInfo() %>
</pre>

<div class="bb"> 
<a href="systemhome.do?action=reset_cache_info">Reset cache</a>
</div>
<%}%>

<%        if(action.equals("reset_cache_info")){        %>
<pre>
<%= com.cleanwise.service.api.util.CacheManager.resetCache() %>
</pre>

<%}%>

</td>
</tr>

</table>


<jsp:include flush='true' page="ui/admFooter.jsp"/> </td> </tr>


</body>

</html:html>
