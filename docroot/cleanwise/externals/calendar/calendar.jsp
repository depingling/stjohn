<%@page import="java.util.Locale, com.cleanwise.view.i18n.ClwI18nUtil"%>
<script type="text/javascript" src="../externals/calendar/scw.js"></script>
<%--<script type="text/javascript" src="../externals/calendar/scwLanguages.js"></script>--%>
<%--<script type="text/javascript" src="../externals/calendar/jsCalendar.js"></script>--%>
<%
String calendarDatePattern = ClwI18nUtil.getDatePattern(request);
Locale calendarLocale = ClwI18nUtil.getUserLocale(request);
if (calendarDatePattern == null) {
    calendarDatePattern = "MM/dd/yyyy";
}
if (calendarLocale == null) {
    calendarLocale = Locale.US;
}
%><script>
CalendarMetaInfo.init('<%=calendarLocale.toString()%>', '<%=calendarDatePattern%>');
</script>