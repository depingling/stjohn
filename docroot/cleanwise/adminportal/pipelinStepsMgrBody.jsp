<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function sumbitSearch() {
  document.forms[0].action1.value='Search'; 
  document.forms[0].submit();
  return false;
}
//-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="PIPELINE_MGR_FORM" type="com.cleanwise.view.forms.PipelineMgrForm"/>

<div class="text">
  <table width="769" cellspacing="0" border="0" class="mainbody">
  <html:form name="PIPELINE_MGR_FORM" action="adminportal/pipelineStepsMgr.do"
    scope="session" type="com.cleanwise.view.forms.PipelineMgrForm">
   <html:hidden property="action1" value="submit"/>
   <tr>
   <td><b>Pipeline Type:</b></td>
   <td colspan='3'>
   <html:select name="PIPELINE_MGR_FORM" property="pipelineTypeFilter" onchange="sumbitSearch();">
   <html:option value=" "></html:option>
   <% 
   RefCdDataVector typeDV = theForm.getPipelineTypes();
   for(Iterator iter=typeDV.iterator(); iter.hasNext(); ) {
     RefCdData rcD = (RefCdData) iter.next();
   %>  
   <html:option  value="<%=rcD.getValue()%>"><%=rcD.getValue()%></html:option>                                        
   <% } %>
   </html:select>   
   </td>
    </tr>
   <tr> <td>&nbsp;</td>
   <td colspan="3"> <html:submit property="action" value="Search"/>
   </td>
  </tr>
   </html:form>
</table>
<% 
 PipelineDataVector pipelineDV = theForm.getPipelineRecords();
 if(pipelineDV!=null) {
%>    
  <table cellspacing="0" border="0" width="769" class="results">
  <html:form name="PIPELINE_MGR_FORM" action="adminportal/pipelineStepsMgr.do"
    scope="session" type="com.cleanwise.view.forms.PipelineMgrForm" >
  <tr>
  <td colspan='7'>Search result count:&nbsp;<%=pipelineDV.size()%></td>
  </tr>
  <tr align=left>
  <td class="subheaders"><b>Type</b></td>
  <td class="subheaders"><b>Id</b></td>
  <td class="subheaders"><b>Order</b></td>
  <td class="subheaders"><b>Class</b></td>
  <td class="subheaders"><b>Account Id</b></td>
  <td class="subheaders"><b>Status</b></td>
  <td class="subheaders"><b>&nbsp;</b></td>
  </tr>
<% 
  String pipelineTypeCd = theForm.getPipelineTypeFilter();
  if(pipelineTypeCd==null || pipelineTypeCd.trim().length()==0) {
%>
  <logic:iterate id="arrele" name="PIPELINE_MGR_FORM" property="pipelineRecords"
   type="com.cleanwise.service.api.value.PipelineData">
  <bean:define id="eleid" name="arrele" property="pipelineId"/>
  <tr>
  <td><bean:write name="arrele" property="pipelineTypeCd"/></td>
  <td><bean:write name="arrele" property="pipelineId"/></td>
  <td><bean:write name="arrele" property="pipelineOrder"/></td>
  <td><bean:write name="arrele" property="classname"/></td>
  <td><bean:write name="arrele" property="busEntityId"/></td>
  <td><bean:write name="arrele" property="pipelineStatusCd"/></td>
  <% if(arrele.getPipelineId()>0) {%>
  <td><html:multibox name="PIPELINE_MGR_FORM" property="selected" value="<%=eleid.toString()%>"/></td>
  <% } else { %>
  <td>&nbsp;</td>
  <% } %>
  
  </tr>
  </logic:iterate>
  <tr>
    <td colspan='7' align='right'>
     <html:submit property="action" value="Copy Selected"/>
    </td>
  </tr>
<% } else { %>
  <logic:iterate id="arrele" indexId="ii" name="PIPELINE_MGR_FORM" property="pipelineRecords"
   type="com.cleanwise.service.api.value.PipelineData">
  <bean:define id="eleid" name="arrele" property="pipelineId"/>
  <tr>
  <td><bean:write name="arrele" property="pipelineTypeCd"/></td>
  <td><bean:write name="arrele" property="pipelineId"/></td>
    <td><html:text name="PIPELINE_MGR_FORM" property='<%="pipelineOrder["+ii+"]"%>' size="4"  styleClass='text' maxlength='4'/></td>
  <td><html:text name="PIPELINE_MGR_FORM" property='<%="classname["+ii+"]"%>' size='70' /></td>
  <td><html:text name="PIPELINE_MGR_FORM" property='<%="busEntityId["+ii+"]"%>' size='8' styleClass='text'/></td>
  <td><html:select name="PIPELINE_MGR_FORM" property='<%="pipelineStatusCd["+ii+"]"%>' styleClass='text'>
     <html:option value='<%=RefCodeNames.PIPELINE_STATUS_CD.INACTIVE%>'>
                         <%=RefCodeNames.PIPELINE_STATUS_CD.INACTIVE%></html:option>
     <html:option value='<%=RefCodeNames.PIPELINE_STATUS_CD.ACTIVE%>'>
                         <%=RefCodeNames.PIPELINE_STATUS_CD.ACTIVE%></html:option>
    </html:select>
  </td>
<% if(arrele.getPipelineId()>0) {%>
  <td><html:multibox name="PIPELINE_MGR_FORM" property="selected" value="<%=eleid.toString()%>"/></td>
  <% } else { %>
  <td>&nbsp;</td>
  <% } %>
  </tr>
  </logic:iterate>
  <tr>
    <td colspan='3'> 
     <html:submit property="action" value="Save"/>
     <html:submit property="action" value="New"/>
     <% if(theForm.getSelectedRecords()!=null && theForm.getSelectedRecords().size()>0) { %>
       <html:submit property="action" value="Paste"/>
     <% } %>
     </td>
    <td colspan='4' align='right'>
     <html:submit property="action" value="Delete Selected"/>
     <html:submit property="action" value="Copy Selected"/>
    </td>
  </tr>
<% } %>  
  </html:form>
 </table>
  <% } %>

<% 
 PipelineDataVector selectedPipelineDV = theForm.getSelectedRecords();
 if(selectedPipelineDV!=null && selectedPipelineDV.size()>0) {
%>    
  <table cellspacing="0" border="0" width="769" class="results">
  <tr>
  <td colspan='7'><b>Copied Records</b></td>
  </tr>
  <tr align=left>
  <td class="subheaders"><b>Order</b></td>
  <td class="subheaders"><b>Class</b></td>
  <td class="subheaders"><b>Account Id</b></td>
  <td class="subheaders"><b>Status</b></td>
  </tr>
  <logic:iterate id="arrele" name="PIPELINE_MGR_FORM" property="selectedRecords"
   type="com.cleanwise.service.api.value.PipelineData">
  <bean:define id="eleid" name="arrele" property="pipelineId"/>
  <tr>
  <td><bean:write name="arrele" property="pipelineOrder"/></td>
  <td><bean:write name="arrele" property="classname"/></td>
  <td><bean:write name="arrele" property="busEntityId"/></td>
  <td><bean:write name="arrele" property="pipelineStatusCd"/></td>
  
  </tr>
  </logic:iterate>
 </table>
 <% } %>
  <!--/html:form-->
  
</div>
  



