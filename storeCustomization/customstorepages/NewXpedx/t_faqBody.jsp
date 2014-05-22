<%@ page language="java" %>
<%@page import="com.cleanwise.view.utils.*" %>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.service.api.*" %>
<%@page import="com.cleanwise.service.api.session.*" %>
<%@page import="com.cleanwise.service.api.value.*" %>
<%@page import="com.cleanwise.service.api.util.*" %>
<%@page import="com.cleanwise.view.utils.*" %>
<%@ page import="java.util.Iterator" %>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String"  name="pages.store.images"/>

<script language="javascript" type="text/javascript">
function submitForm() {
    var frm = document.forms[0];
    if (frm) {
        frm.submit();
    }
}
function displayTopicDetail(id)
{

    if (eval("document.getElementById('topicDetailTable_'+id)").style.display == 'none') {
        eval("document.getElementById('topicDetailTable_'+id)").style.display = 'block';
        eval("document.getElementById('topicImg'+id)").src = "<%=ClwCustomizer.getSIP(request,"minus.gif")%>";
    }

    else {
        eval("document.getElementById('topicDetailTable_'+id)").style.display = 'none';
        eval("document.getElementById('topicImg'+id)").src = "<%=ClwCustomizer.getSIP(request,"plus.gif")%>";
    }
}

function showInternalMessageBox(){
    eval("document.getElementById('CUSTOMER_ORDER_NOTES_LINK')").style.display = 'none';
    eval("document.getElementById('CUSTOMER_ORDER_NOTES_BOX')").style.display = 'block';
}

var size;
var expand;
function displayAllTopicsDetail(expand,size)
{
	for ( var i = 1; i<=size; i++ )
	{
		if(expand == 'true'){
			eval("document.getElementById('topicDetailTable_'+i)").style.display = 'block';
			eval("document.getElementById('topicImg'+i)").src = "<%=ClwCustomizer.getSIP(request,"minus.gif")%>";
		}else{
			eval("document.getElementById('topicDetailTable_'+i)").style.display = 'none';
			eval("document.getElementById('topicImg'+i)").src = "<%=ClwCustomizer.getSIP(request,"plus.gif")%>";
		}
	}
}
</script>


<bean:define id="theForm" name="FAQ_FORM" type="com.cleanwise.view.forms.FAQForm"/>

<bean:define id="AccountId" type="java.lang.String" name="pages.account.id"/>
<%CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>

<%-----------------bread crumb-----------------------%>
<table class="breadcrumb">
  <tr class="breadcrumb">
    <td class="breadcrumb"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
    <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
    <td class="breadcrumb">
		<%--<a class="breadcrumb" href="faq.do"><app:storeMessage key="shop.menu.main.faq"/></a>--%>
		<div class="breadcrumbCurrent"><app:storeMessage key="shop.menu.main.faq"/></div>
	</td>
  </tr>
  <tr><td>&nbsp;</td></tr>
</table>
<%-------------end of --bread crumb-----------------------%>
  <%
    NoteJoinViewVector faqViewV = theForm.getFaqViewVector();
    int faqActionSeq = 0;
  %>

<table width="780"  cellpadding="0"  cellspacing="2" border="0">

<% if (faqViewV != null && faqViewV.size() > 0) {%>
<tr>
<td colspan="2">
<table cellpadding="0"  cellspacing="2" border="0">
<tr>
<td width="10%"><a style="font-size: 10px; text-decoration: none; color:#000000;" href="#" onclick="displayAllTopicsDetail('true','<%=faqViewV.size()%>');"><app:storeMessage key="faq.admin.heading.expandAll"/>...</a></td>
<td width="1%"><a style="font-size: 10px; text-decoration: none; color:#000000;">/</a></td>
<td align="left"><a style="font-size: 10px; text-decoration: none; color:#000000;" href="#" onclick="displayAllTopicsDetail('false','<%=faqViewV.size()%>');"><app:storeMessage key="faq.admin.heading.collapseAll"/>...</a></td>
</tr>
</table>
</td>
</tr>
<tr><td>&nbsp;</td></tr>
  <% for (Iterator iter = faqViewV.iterator(); iter.hasNext() ; ) {
       NoteJoinView faq = (NoteJoinView) iter.next();
       if (faq != null) {
       faqActionSeq++;
       String faqTitle = faq.getNote().getTitle();
       NoteTextDataVector faqTextDV = faq.getNoteText(); %>
       <%-----------------Topic  ------------------------%>
       <tr>
         <% if (faqTextDV.size()>0){ %>
         <td width="9" style="padding-right:7px">
           <img id="topicImg<%=faqActionSeq%>" style="cursor:hand"
             src="<%=ClwCustomizer.getSIP(request,"plus.gif")%>" border="0"
             onClick="displayTopicDetail('<%=faqActionSeq%>');"/>
         </td>
         <% } %>
         <td colspan="2" align="left" >
           <b><a class ="notes" href="#" onclick="displayTopicDetail('<%=faqActionSeq%>');"><%=faq.getNote().getTitle()%></a></b>
         </td>
       </tr>
       <%-----------------FAQ Message  Detail------------------------%>
       <tr>

         <td width="9" style="padding-right:7px"></td>
         <td colspan="2">
           <table id="topicDetailTable_<%=faqActionSeq%>"
           width="750"
           style="display:none" border="0"
           cellspacing="0" cellpadding="0">

           <%
           Iterator faqTextDVIt = faqTextDV.iterator();
           String faqDetail = "";
           while (faqTextDVIt.hasNext()) {
             NoteTextData faqTextData = (NoteTextData)faqTextDVIt.next();
             faqDetail = faqTextData.getNoteText();
           %>

             <tr>
               <td >
                 <%=faqDetail%>
               </td>
             </tr>
           </table>
         </td>
       </tr>
       <% } %>

       <% } %>
  <% } // end of loop%>
<% } // end of if %>
</table>
