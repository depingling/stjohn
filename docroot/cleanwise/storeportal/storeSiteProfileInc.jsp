<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<logic:present name="Profiling.found.vector">
  <bean:size id="rescount"  name="Profiling.found.vector"/>
  Search result count:  <bean:write name="rescount" />
  <logic:greaterThan name="rescount" value="0">
    <table ID=1294 width="769" cellspacing="0" border="0" class="results">
      <tr align=left>
      <td>Id</td>
      <td>Name </td>
      <td>Type</td>
      </tr>

      <logic:iterate id="arrele" name="Profiling.found.vector">
        <tr>
          <td><bean:write name="arrele" property="profileId"/></td>
          <td>
            <logic:equal name="arrele" property="profileTypeCd" value="<%=RefCodeNames.PROFILE_TYPE_CD.SURVEY%>">
              <bean:define id="eleid" name="arrele" property="profileId"/>
              <a ID=1295 href="<%=request.getParameter("detailHref")%>?action=view&profileId=<%=eleid%>"><bean:write name="arrele" property="shortDesc"/></a>
            </logic:equal>
            <logic:notEqual name="arrele" property="profileTypeCd" value="<%=RefCodeNames.PROFILE_TYPE_CD.SURVEY%>">
              <td><bean:write name="arrele" property="shortDesc"/></td>
            </logic:notEqual>
          </td>
          <td><bean:write name="arrele" property="profileTypeCd"/></td>
        </tr>
      </logic:iterate>
    </table>
  </logic:greaterThan>
</logic:present>

