<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.view.utils.SelectableObjects"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.util.DBCriteria"%>
<%@ page import="com.cleanwise.service.api.dao.NoteDataAccess "%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="com.cleanwise.view.utils.DisplayListSort"%>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%
   String action = (String) request.getParameter("action");
   String actionMore = "more";
   NoteViewVector result= null;
   CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
   SiteData thisSite = appUser.getSite();

   APIAccess factory = APIAccess.getAPIAccess();
   Note noteBean = factory.getNoteAPI();

   //Create criteria for searching
   DBCriteria dbCrit = new DBCriteria();
   dbCrit.addEqualTo(NoteDataAccess.BUS_ENTITY_ID, thisSite.getAccountId());
//   dbCrit.addOrderBy(NoteDataAccess.NOTE_ID, false);

   int stopNoteId = 0;
   int startNoteId = 0;
   int rowCount = 0;
   int maxNotesToShow = 5;
   if(actionMore.equals(action)){
      maxNotesToShow = 99;
   }
   int rowNum = Constants.MAX_NOTES_TO_RETURN;

   if (action != null && action.equals(actionMore)) {
     rowNum = 0;
     startNoteId = Utility.parseInt(request.getParameter("lastNoteId"));
   }

    try {
      result = noteBean.getNoteTitlesForUser(appUser.getUser().getUserId(), "INTERSTITIAL_MESSAGE",dbCrit, rowNum );
      DisplayListSort.sort(result, Constants.NOTES_SORT_EFF_DATE, null);
    } catch (Exception exc) {
        exc.printStackTrace();
    }
  %>

<table width="100%"  cellspacing="0" cellpadding="0"  >
<tr><td align="left"  class="xpdexMenuHeader"><app:storeMessage key="shop.menu.main.news"/></td></tr>
<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="4"></td></tr>
<% if (result != null && result.size() > 0) {
	//remove expired articles
    Date curD = new Date();
	Iterator it = result.iterator();
	while(it.hasNext()){
		NoteView n = (NoteView)it.next();
		Date expD =(noteBean.getNote(n.getNoteId())).getNote().getExpDate();
		if(curD.after(expD)){
			it.remove();
		}
		
		Date effD = n.getModDate();
		if(curD.before(effD)){
			it.remove();
		}
	}
%>
  <% for (Iterator iter = result.iterator(); iter.hasNext() && rowCount < maxNotesToShow; ) {
       NoteView note = (NoteView) iter.next();
       %>
       <% if (note != null) {
            if (!actionMore.equals(action)  || (action != null && action.equals(actionMore) && note.getNoteId() < startNoteId) ) {
              rowCount++;
              stopNoteId = note.getNoteId();
              String actionVal = ClwI18nUtil.getMessageOrNull(request,"msbNotes.text.read");
              if(actionVal==null){
                actionVal="Read";
              } %>
              <tr>
                <td >
                  <%--<b><a class ="categorymenulevel_2" href="msbsites.do?action=read_note2&userNoteId=<%=note.getNoteId()%>"><%=note.getTitle()%></a></b>--%>
                  <b><a class ="categorymenulevel_1" style="width:170" href="#" onclick="appleNewsAjax.initialize(false, <%=note.getNoteId()%>);return false;"><%=note.getTitle()%></a></b>
			  </td></tr>
				<tr><td>
                  <%=ClwI18nUtil.formatDate(request, note.getModDate(), DateFormat.DEFAULT)%>
                </td>

              </tr>
			  <%}else if(actionMore.equals(action)){
				String actionVal = ClwI18nUtil.getMessageOrNull(request,"msbNotes.text.read");
              if(actionVal==null){
                actionVal="Read";
              } %>
              <tr>
                <td >
                  <%--<b><a class ="categorymenulevel_2" href="msbsites.do?action=read_note2&userNoteId=<%=note.getNoteId()%>"><%=note.getTitle()%></a></b>--%>
                  <b><a class ="categorymenulevel_1" style="width:170" href="#" onclick="appleNewsAjax.initialize(false, <%=note.getNoteId()%>);return false;"><%=note.getTitle()%></a></b>
			  </td></tr>
				<tr><td>
                  <%=ClwI18nUtil.formatDate(request, note.getModDate(), DateFormat.DEFAULT)%>
                </td>

              </tr>

			  <%}%>
           <% } %>
		  <tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="8"></td></tr>
  <% } // end of loop%>
<% } // end of if %>
<% if (rowCount >= maxNotesToShow && !actionMore.equals(action))  { %>
  <tr>
    <td align ="right"  valign = "bottom">
      <a  style="font-size: 9px; text-decoration: none; color:#000000;"  href="msbsites.do?action=more&lastNoteId=<%=stopNoteId%>"><app:storeMessage key="msbNotes.text.more"/></a>
    </td>
  </tr>
<% } %>
</table>
<%@include file="appleNewsAjax.jsp"%>
