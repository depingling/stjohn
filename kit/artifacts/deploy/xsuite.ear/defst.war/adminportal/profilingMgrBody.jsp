<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<div class="text">
<div class="text">
<font color=red>
<html:errors/>
</font>

<table width="769" cellspacing="0" border="0"  class="mainbody">
  <html:form action="adminportal/profilingMgr.do"
    type="com.cleanwise.view.forms.ProfilingMgrSearchForm">
  <tr> <td><b>Find Profile:</b></td>
       <td colspan="3">
                        <html:text property="searchField"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio property="searchType" value="id" />
         ID
         <html:radio property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio property="searchType" value="nameContains" />
         Name(contains)
       </td>
  </tr>
  <tr><td>&nbsp;</td>
      <td colspan="3">
      <html:select property="searchPofileTypeCd">
        <html:option value="<%=RefCodeNames.PROFILE_TYPE_CD.SURVEY%>"><%=RefCodeNames.PROFILE_TYPE_CD.SURVEY%></html:option>
      </html:select>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.create"/>
        </html:submit>
     </html:form>
    </td>
  </tr>
</table>

<jsp:include flush='true' page="profilingFoundInc.jsp">
        <jsp:param name="detailHref" value="profilingSurveyMgr.do" />
</jsp:include>

</div>
