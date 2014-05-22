<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.cleanwise.view.forms.LocateStoreSiteForm" %>
<%@ page import="com.cleanwise.view.forms.StorePortalForm" %>
<%@ page import="com.cleanwise.view.utils.SessionAttributes" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%

  String jspFormName = request.getParameter("jspFormName");
  if(jspFormName == null) {
    throw new RuntimeException("jspFormName cannot be null");
  }
  
  //LocateStoreSiteForm theForm = null;
  String jspFormNestProperty = request.getParameter("jspFormNestProperty");
  if(jspFormNestProperty != null) {
    jspFormNestProperty = jspFormNestProperty + ".locateStoreSiteForm";
  } else {
    jspFormNestProperty = "locateStoreSiteForm";
  }  
  %>
  <logic:present name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" >
  <bean:define id="theForm" name="<%=jspFormName%>" property="<%=jspFormNestProperty%>" type="LocateStoreSiteForm"/>
  
  <%
  if(theForm != null && theForm.getLocateSiteFl()) {
    String jspFormAction = request.getParameter("jspFormAction");  
  if(jspFormAction == null) {
    throw new RuntimeException("jspFormAction cannot be null");
  }
  String jspSubmitIdent = request.getParameter("jspSubmitIdent");
  if(jspSubmitIdent == null) {
    throw new RuntimeException("jspSubmitIdent cannot be null");
  }
  String jspReturnFilterProperty = request.getParameter("jspReturnFilterProperty");
  if(jspReturnFilterProperty == null) {
    throw new RuntimeException("jspReturnFilterProperty cannot be null");
  }
  jspSubmitIdent += "#" + SessionAttributes.SEARCH_FORM.LOCATE_STORE_SITE_FORM;

%>



<script src="../externals/lib.js" language="javascript"></script>
<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.forms; 
 for(i=0; i<dml.length; i++) {
  found = false;
  ellen = dml[i].elements.length;
  //alert('next_form='+ellen);
  for(j=0; j<ellen; j++) {
    if (dml[i].elements[j].name=='<%=jspFormNestProperty%>.selected') {
      dml[i].elements[j].checked=val;
      found = true;
    }
  }
  if(found) break;
 }
}

function SetAndSubmit (form_id, val) {
  var dml = document.getElementById(form_id);
  var name = '<%=jspFormNestProperty%>.selected';
  var ellen = dml[name].length;
  if(ellen>0) {
  for(j=0; j<ellen; j++) {
    if(dml[name][j].value==val) {
      found = true;      
      dml[name][j].checked=1;
    } else {
      dml[name][j].checked=0;
    }
  }
  } else {
    dml[name].checked=1;
  }
  actionSubmit(form_id,'Return Selected');
}

function submitenter(myfield,e)
{
    var keycode;
    if (window.event) { 
        keycode = window.event.keyCode;
    } else if (e) {
        keycode = e.which;
    } else return true;

    if (keycode == 13) {
        myfield.form.submit();
    return false;
    } else return true;
}

//-->
</script>



<html:form styleId="22222" action="<%=jspFormAction%>"  scope="session">
    <html:hidden property="jspSubmitIdent" value="<%=jspSubmitIdent%>"/>
        <%String prop=jspFormNestProperty+".property";%>
    <html:hidden property="<%=prop%>" value="<%=jspReturnFilterProperty%>"/>
        <%prop=jspFormNestProperty+".name";%>
    <html:hidden property="<%=prop%>" value="<%=jspFormName%>"/>

<div style="border:#D3D3D3 1px solid;
            text-align: center;
            padding: 0px 8px 10px 8px;">
    Find Sites
<table ID="324" align="center" class="mainbody" cellspacing="0" cellpadding="2" width="97%">
    <tr>
        <td width="15%" align="right">
            <b><app:storeMessage key="userlocate.site.text.siteName"/>:</b>
        </td>
        <td width="30%">
            <%prop=jspFormNestProperty+".searchField";%>
            <html:text property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
        </td>
        <td align="left">
        <%prop=jspFormNestProperty+".searchType";%>
        <html:radio property="<%=prop%>" value="id" />
            ID&nbsp;
        <html:radio property="<%=prop%>" value="nameBegins" />
            Name(starts with)&nbsp;
        <html:radio property="<%=prop%>" value="nameContains" />
            Name(contains)
        </td>
    </tr>
    
    <tr>
        <td width="15%" align="right">
            <b><app:storeMessage key="userlocate.account.text.referenceNumber"/>:</b>
        </td>
        <td width="30%">
            <%prop=jspFormNestProperty+".searchRefNum";%>
            <html:text property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
        </td>
        <td align="left">
        <%prop=jspFormNestProperty+".searchRefNumType";%>
        <html:radio property="<%=prop%>" value="nameBegins" />
            (starts with)&nbsp;
        <html:radio property="<%=prop%>" value="nameContains" />
            (contains)&nbsp;
        </td>
    </tr>
    
    <tr>
        <td width="15%" align="right">
            <b><app:storeMessage key="userlocate.site.text.city"/>:</b>
        </td>
        <td width="30%">
            <%prop=jspFormNestProperty+".city";%>
            <html:text property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
        </td>
        <td></td>
    </tr>
    
    <tr>
        <td width="15%" align="right">
            <b><app:storeMessage key="userlocate.site.text.state"/>:</b>
        </td>
        <td width="30%">
            <%prop=jspFormNestProperty+".state";%>
            <html:text property="<%=prop%>" onkeypress="return submitenter(this,event)"/>
        </td>
        <td></td>
    </tr>
    
    <tr>
        <td width="15%">&nbsp;</td>
        <td width="30%" align="left">
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit('22222','ServiceProviderSiteLocate');">
                <app:storeMessage key="global.action.label.search"/>
            </html:button>
            <%prop=jspFormNestProperty+".showInactiveFl";%>
            &nbsp;
            <html:checkbox property="<%=prop%>"/>
            Show Inactive
        </td>
        <td align="left">
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit('22222','Cancel');">
                <app:storeMessage key="global.action.label.cancel"/>
            </html:button>
            &nbsp;
            <html:button property="action" styleClass="store_fb" onclick="actionSubmit('22222','Return Selected');">
                <app:storeMessage key="userlocate.account.text.returnSelected"/>
            </html:button>
        </td>
    </tr>
</table>

<!-- logic:present name="locateStoreSiteForm.sites" -->
    <% if(theForm.getSites() != null) {     
        int rescount = theForm.getSites().size();
        
        if(rescount > 0) { %>
            Search result count: <%=rescount%> 
            <% if ( rescount >= Constants.MAX_SITES_TO_RETURN ){ %>
                (Request Maximum) <%=Constants.MAX_SITES_TO_RETURN%>
            <%}%>
            
            <table ID="393" align="center" border="0" width="97%" style="background-color: #E4F3CF">
                <tr align="left">
                    <td><a ID="394" class="tableheader">Site Id</a></td>
                    <td><a ID="395" class="tableheader">Site Name</a></td>
                    <td>
                        <a ID="396" href="javascript:SetChecked(1)">[Check&nbsp;All]</a><br>
                        <a ID="397" href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
                    </td>
                    <td><a ID="398" class="tableheader">Rank</a></td>
                    <td><a ID="399" class="tableheader">Account Name</a></td>
                    <td><a ID="400" class="tableheader">Street Address</a></td>
                    <td><a ID="401" class="tableheader">City</a></td>
                    <td><a ID="402" class="tableheader">State</a></td>
                    <td><a ID="403" class="tableheader">Status</a></td>
                </tr>
                
                <%
                  String propName = jspFormNestProperty + ".sites";
                  prop = jspFormNestProperty+".sites";
                  String selectBoxProp = jspFormNestProperty+".selected";
                %>
                
                <logic:iterate  id="arrele"
                                name="<%=jspFormName%>"
                                property="<%=prop%>" 
                                type="com.cleanwise.service.api.value.SiteView">
                <bean:define id="key" name="arrele" property="id"/>
                <% String linkHref = "javascript: SetAndSubmit ('22222', "+key+");";%>
                
                    <tr>
                        <td><bean:write name="arrele" property="id"/></td>
                        <td>
                            <a ID="404" href="<%=linkHref%>"><bean:write name="arrele" property="name"/></a>
                        </td>
                        <td><html:multibox property="<%=selectBoxProp%>" value="<%=key.toString()%>"/></td>
                        <td><bean:write name="arrele" property="targetFacilityRank"/></td>
                        <td><bean:write name="arrele" property="accountName"/></td>
                        <td><bean:write name="arrele" property="address"/></td>
                        <td><bean:write name="arrele" property="city"/></td>
                        <td><bean:write name="arrele" property="state"/></td>
                        <td><bean:write name="arrele" property="status"/></td>
                    </tr>
                </logic:iterate>
            </table>
        <% } %>
    <% } %>
    
<!-- /logic:present -->

</div>

    <html:hidden property="action" value="hiddenAction"/>
    <html:hidden property="action" value="Search"/>
    <html:hidden property="tabs" value="f_serviceProviderWorkOrderToolbar"/>
    <html:hidden property="display" value="t_serviceProviderWorkOrder"/>
</html:form>


<% } %>
</logic:present>

<%--main form--%>