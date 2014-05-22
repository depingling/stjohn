
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="IMGPath" type="java.lang.String"
  name="pages.store.images"/>

<div class="text">


<table cellSpacing=5 cellPadding=0 align=center border=0>
<tr>
<td>
<logic:present name="Profiling.found.vector">
        <bean:size id="rescount"  name="Profiling.found.vector"/>
        <logic:greaterThan name="rescount" value="0">
        Available Surveys:
          <table cellspacing="0" border="0">
                <logic:iterate id="arrele" name="Profiling.found.vector">
                        <tr>
                                <td>
                                        <logic:equal name="arrele" property="profileTypeCd" value="<%=RefCodeNames.PROFILE_TYPE_CD.SURVEY%>">
                                                <bean:define id="eleid" name="arrele" property="profileId"/>
                                                <a href="customerAccountManagementSurveyDetail.do?action=detail&profileId=<%=eleid%>"><bean:write name="arrele" property="shortDesc"/></a>
                                        </logic:equal>
                                        <%--At least for now do not display this type
                                        <logic:notEqual name="arrele" property="profileTypeCd" value="<%=RefCodeNames.PROFILE_TYPE_CD.SURVEY%>">
                                                <td><bean:write name="arrele" property="shortDesc"/></td>
                                        </logic:notEqual>--%>
                                </td>
                        </tr>
                </logic:iterate>
          </table>
        </logic:greaterThan>
</logic:present>


</td>
</tr>
</table>
