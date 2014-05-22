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
      	String buildDate = "@build.date@";
		String formattedDate = "";
		if (Utility.isSet(buildDate) && !buildDate.equalsIgnoreCase("@" + "build.date" + "@")) {
			Date date = new SimpleDateFormat("MMMMM dd, yyyy hh:mm:ssa").parse(buildDate);
			DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
			formattedDate = dateFormatter.format(date);
		}
%>
<br>
<b>Application information</b>
<ul>Build Number: @build.number@
<br>Build Date: <%=formattedDate%>
<br>Database URL: @dbUrl@
<br>Database Schema: @dbUser@
</ul>
