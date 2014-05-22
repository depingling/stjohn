<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.apache.struts.taglib.TagUtils" %>
<%
		Locale locale =  TagUtils.getInstance().getUserLocale(pageContext, null);
		//if the name or format of the build.date property changes in the
		//build.xml file, corresponding changes are likely to be required here.
      	String buildDate = "March 13, 2014 02:25:06PM";
		String formattedDate = "";
		if (Utility.isSet(buildDate) && !buildDate.equalsIgnoreCase("@" + "build.date" + "@")) {
			Date date = new SimpleDateFormat("MMMMM dd, yyyy hh:mm:ssa").parse(buildDate);
			DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
			formattedDate = dateFormatter.format(date);
		}
%>
<br>
<b>Application information</b>
<ul>Build Number: 36.00.267.04
<br>Build Date: <%=formattedDate%>
<br>Database URL: jdbc:oracle:thin:@192.168.100.76:1521:cwdev
<br>Database Schema: qa02
</ul>
