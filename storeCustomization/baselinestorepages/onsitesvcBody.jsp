
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="java.util.Date" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>




<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<table align="center" border="0" 
  cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  
<%
// Result message of the onsite services request.
%>
<bean:define id="resmsg" type="java.lang.String"
  name="ONSITE_SVC_FORM" property="resultMsg" 
  scope="session" toScope="session"/>

<% if ( resmsg.length() > 0 ) { %> 
<tr>
<td class="tableoutline" width="1" bgcolor="black">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
<td>&nbsp;&nbsp;&nbsp;&nbsp;     <table><tr><td>
<% if (resmsg.equals("OK") ) { %>
<span class=greenformtext>
Your request has been processed.
</span>
<% } else { %>
<span class=errormsg>
Your request could not be processed.  Please contact a 
customer service representative.
</span>
<% } %>

</td></tr></table>  
</td>
<td class="tableoutline" width="1" bgcolor="black">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
</tr>
<% } // End of resmsg.length check %>

<tr>
<td class="tableoutline" width="1" bgcolor="black">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
<td>
&nbsp;

<!-- start of new stuff -->

<TABLE cellSpacing=0 cellPadding=0 align=center border=0>
<TBODY>

<html:form action="/store/onsitesvc.do"   
  name="ONSITE_SVC_FORM" 
  type="com.cleanwise.view.forms.OnsiteServicesForm"
  method="POST">
<html:hidden property="action" value="send_onsite_service_request"/>

<TR>
          <TD><IMG height=21 src="<%=IMGPath%>/cw_onsiteservices1.gif" width=217></TD>
          <TD><IMG height=21 src="<%=IMGPath%>/cw_onsiteservicesgreenvert.gif" width=3></TD>
          <TD align=left><IMG height=21 src="<%=IMGPath%>/cw_onsiteservices2.gif" width=227></TD>
          <TD><IMG height=21 src="<%=IMGPath%>/cw_onsiteservicesgreenvert.gif"   width=3></TD>
          <TD><IMG height=21 src="<%=IMGPath%>/cw_onsiteservices3.gif"  width=280></TD>
</tr>

<tr>
<td valign=top>

  <TABLE cellSpacing=0 cellPadding=0 width="90%" align="left" border="0">
  
              <TBODY>
              <TR>
                <TD colSpan=2><IMG height=7  src="<%=IMGPath%>/cw_spacer.gif" width=5></TD></TR>
<% if ( resmsg.length() > 0 ) { // If check 1 %>                 
<tr><td class=greenformtext align=left>
<bean:write name="ONSITE_SVC_FORM" property="onSiteServiceType"/>
</td></tr>
<% } else { %>
              <TR>
                <TD>
                  <html:radio name="ONSITE_SVC_FORM"
                  property="onSiteServiceType" 
                  value="Onsite Trouble Shooting"/>
                </TD>
                <TD class=greenformtext>Onsite Troubleshooting</TD></TR>
              <TR>
                <TD>
                  <html:radio name="ONSITE_SVC_FORM"
                  property="onSiteServiceType" 
                  value="Program Development"/>
				</TD>
                <TD class=greenformtext>Program Development</TD></TR>
              <TR>
                <TD>
		            <html:radio name="ONSITE_SVC_FORM"
        	          property="onSiteServiceType" 
            	      value="Onsite Training"/>
				</TD>
                <TD class=greenformtext>Onsite Training</TD></TR>
              <TR>
                <TD>
	            <html:radio name="ONSITE_SVC_FORM"
        	          property="onSiteServiceType" 
            	      value="Equipment Repair"/>
				</TD>
                <TD class=greenformtext>Equipment Repair</TD>
                </TR>
<% } // End of length if check 1 %>                
                </TBODY>
  </TABLE>


</td>

<TD background="<%=IMGPath%>/cw_onsiteserviceswhitevert.gif">
<IMG height=1 src="<%=IMGPath%>/cw_spacer.gif" width=3>
</TD>

         <td valign=top>
            <table cellspacing=0 cellpadding=0 border="0">
              <TBODY>
              <TR>                
                <TD colSpan=2><IMG height=7 
                  src="<%=IMGPath%>/cw_spacer.gif" width=5></TD></TR>
<% if ( resmsg.length() > 0 ) { // If check 2 %>                 
<tr><td class=greenformtext align=left>
<bean:write name="ONSITE_SVC_FORM" property="locationSameAsShipping"/>
</td></tr>
<% } else { %>
                  
              <TR>
                <TD>
                <html:radio name="ONSITE_SVC_FORM"
        	          property="locationSameAsShipping" 
            	      value="Yes"/>
				</TD>
                <TD class="greenformtext">&nbsp;&nbsp;Yes</TD>
                </TR>
              <TR>                
                <TD>
                 <html:radio name="ONSITE_SVC_FORM"
        	          property="locationSameAsShipping" 
            	      value="No"/>
				</TD>
                <TD class="greenformtext">&nbsp;&nbsp;No</TD>
                </TR>
<% } // End of if check 2 %>
                
                </TBODY>
                </TABLE>
                </TD>
                
          <IMG height=1 src="<%=IMGPath%>/cw_spacer.gif" width=3>
</TD>

          <TD colspan=2 valign=top>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" align=center border=0>
              <TBODY>
              <TR>
                <TD><IMG height=7 
                  src="<%=IMGPath%>/cw_spacer.gif" width=5></TD></TR>
<% if ( resmsg.length() > 0 ) { // If check 3 %>                 
<tr><td class=greenformtext align=left>
<pre>
<bean:write name="ONSITE_SVC_FORM" property="comments"/>
</pre>
</td></tr>
<% } else { %>
                  
              <TR>
                <TD>
                  <html:textarea name="ONSITE_SVC_FORM"
        	          property="comments" rows="6" cols="25"/>
        	    </TD>
        	  </TR>
              <TR>
                <TD><IMG height=4 
                  src="<%=IMGPath%>/cw_spacer.gif" width=5></TD></TR>
              <TR>
                <TD>
<html:link href="javascript:{document.ONSITE_SVC_FORM.submit();}">
<input type="image"  src="<%=IMGPath%>/cw_submit_teal.gif" 
 border="0" name="Send">
</html:link> 
				</TD>
				</TR>
<% } // End if check 3 %>				
              <TR>
                <TD><IMG height=4 
                  src="<%=IMGPath%>/cw_spacer.gif" 
              width=5></TD></TR>
              </TBODY>
              </TABLE>
           </TD>
</tr>

          <TR>
          <TD class=shopdk colSpan=5><IMG height=2 
           src="<%=IMGPath%>/cw_spacer.gif" width=1>
           </TD>
           </TR>

</tbody>
</table>

</html:form>

<!-- end of new stuff -->

</td>

<td class="tableoutline" width="1" bgcolor="black">
  <img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1">
</td>
</tr>

</table>

<table align="center" border="0" cellpadding="0" cellspacing="0">
   <TR>
    <TD>
    <IMG  width="<%=Constants.TABLEWIDTH%>" height=23 src="<%=IMGPath%>/cw_shopdarkfooter.gif">
  </TD>
  </TR>
</table>






