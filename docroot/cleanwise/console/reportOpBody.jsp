<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% String onKeyPress="return submitenter(this,event,'Submit');"; %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="rname" name="REPORT_OP_FORM" property="rname" />
<bean:define id="theForm" name="REPORT_OP_FORM" type="com.cleanwise.view.forms.ReportOpForm"/>



<script language="JavaScript1.2">
<!--

function selectType(pType) {
  document.forms[0].action = "reportOp.do?f_action=select&type=" + pType;
  return document.forms[0].submit();
}




function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

//-->
</script>

<div class="text">

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
 <tbody>
  <tr> <td align="center"><font color=red><html:errors/></font></td></tr>
 </tbody></table>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<html:form name="REPORT_OP_FORM" action="console/reportOp"
    scope="session" type="com.cleanwise.view.forms.ReportOpForm">
 <tbody>
  <tr> <td>&nbsp;</td>
       <td><b>Report Category:<br>
              Report Name:</b></td>
           <td>
             <html:hidden property="f_action" value="" />
             <html:hidden property="change" value="" />
             <html:hidden property="changeType" value="" />
              <html:select name="REPORT_OP_FORM" property="rtype"
               onchange="document.forms[0].changeType.value='rtype';
               return selectType(document.forms[0].elements[1].value);">

               <html:option value="">
                 <app:storeMessage  key="admin.select"/>
               </html:option>
               <html:options collection="Report.type.vector"
                             property="value" labelProperty="value" />
             </html:select>
             <span class="reqind">*</span><br>


             <html:select name="REPORT_OP_FORM" property="rname"
              onchange="document.forms[0].change.value='rname';
                        document.forms[0].f_action.value='report';
                        document.forms[0].submit();">
               <html:option value="">
                 <app:storeMessage  key="admin.select"/>
               </html:option>
               <html:options collection="Report.name.vector"
                             property="value" labelProperty="value" />
             </html:select>
             <span class="reqind">*</span>
           </td>
  </tr>

 <!-- Parameter Control  -->
 <%String controls = ""; %>
 <logic:present name="DATE">
 <% if(controls.length()>0) controls+=","; controls+="DATE"; %>
 <tr> <td>&nbsp</td>
       <td><b>Order Date:</b><br>(mm/dd/yyyy)</td>
           <td colspan="3">
                        Begin Date Range
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        End Date Range<br>
                        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="beginDate" /></span><span class="reqind">*</span>
                        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="endDate" /></span>
           </td>
  </tr>
</logic:present>

 <logic:present name="BEG_DATE">
 <logic:present name="END_DATE">
 <% if(controls.length()>0) controls+=","; controls+="BEG_DATE"; %>
 <% if(controls.length()>0) controls+=","; controls+="END_DATE"; %>
 <tr> <td>&nbsp</td>
       <td><b>Date Interval:</b><br>(mm/dd/yyyy)</td>
           <td colspan="3">
                        Begin Date Range
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        End Date Range<br>
                        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="beginDate" /></span><span class="reqind">*</span>
                        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="endDate" /></span>
           </td>
  </tr>
</logic:present>
</logic:present>


<logic:present name="BEG_DATE">
 <logic:notPresent name="END_DATE">
 <% if(controls.length()>0) controls+=","; controls+="BEG_DATE"; %>
 <tr> <td>&nbsp</td>
       <td><b>Begin Date:</b><br>(mm/dd/yyyy)</td>
           <td colspan="3">
           <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="beginDate" /></span><span class="reqind">*</span>
           </td>
  </tr>
</logic:notPresent>
</logic:present>



<!-- Account -->
<logic:present name="ACCOUNT">
 <% if(controls.length()>0) controls+=","; controls+="ACCOUNT"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Account:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="accountId" />&nbsp;<span class="reqind">*</span>&nbsp;
             <html:button property="locateAccount"
                          onclick="popLocate('../adminportal/accountlocate', 'accountId', '');"
                          value="Locate Account"/>
           </td>
  </tr>
</logic:present>

<!-- Account Optional-->
<logic:present name="ACCOUNT_OPT">
<% if(controls.length()>0) controls+=","; controls+="ACCOUNT_OPT"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Account:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="accountId" />&nbsp;<span class="reqind"></span>&nbsp;
             <html:button property="locateAccount"
                          onclick="popLocate('../adminportal/accountlocate', 'accountId', '');"
                          value="Locate Account"/>
           </td>
  </tr>
</logic:present>

<!-- Distributor -->
<logic:present name="DISTRIBUTOR">
  <% if(controls.length()>0) controls+=","; controls+="DISTRIBUTOR"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Distributor:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="vendorId" />&nbsp;<span class="reqind">*</span>&nbsp;
             <html:button property="locateDistributor"
                          onclick="popLocate('../adminportal/distlocate', 'vendorId', '');"
                          value="Locate Distributor"/>
           </td>
  </tr>
</logic:present>
<!-- Distributor option -->
<logic:present name="DISTRIBUTOR_OPT">
  <% if(controls.length()>0) controls+=","; controls+="DISTRIBUTOR_OPT"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Distributor:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="vendorId" />&nbsp;<span class="reqind"></span>&nbsp;
             <html:button property="locateDistributor"
                          onclick="popLocate('../adminportal/distlocate', 'vendorId', '');"
                          value="Locate Distributor"/>
           </td>
  </tr>
</logic:present>

<!-- Manufacturer -->
<logic:present name="MANUFACTURER">
  <% if(controls.length()>0) controls+=","; controls+="MANUFACTURER"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Manufacturer:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="manufacturerId" />&nbsp;<span class="reqind">*</span>&nbsp;
             <html:button property="locateManufacturer"
                          onclick="popLocate('../adminportal/manuflocate', 'manufacturerId', '');"
                          value="Locate Manufacturer"/>
           </td>
  </tr>
</logic:present>

<!-- Item -->
<logic:present name="ITEM">
  <% if(controls.length()>0) controls+=","; controls+="ITEM"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Item:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="itemId" styleId="item"/>&nbsp;<span class="reqind">*</span>&nbsp;
             <html:button property="locateItem"
                          onclick="popLocate('../adminportal/itemlocate', 'itemId', '');"
                          value="Locate Item"/><br>
             <html:checkbox name="REPORT_OP_FORM"  property="isBadDistSku"/>&nbsp;Filter for bad distributor SKUs
           </td>
  </tr>
</logic:present>

<!-- contract -->
<logic:present name="CONTRACT">
  <% if(controls.length()>0) controls+=","; controls+="CONTRACT"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Contract:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="contractId" styleId="item"/>&nbsp;<span class="reqind">*</span>&nbsp;
             <html:button property="locateContract"
                          onclick="popLocate('../adminportal/contractlocate', 'contractId', '');"
                          value="Locate Contract"/><br>

           </td>
  </tr>
</logic:present>

<!-- catalog -->
<logic:present name="CATALOG">
  <% if(controls.length()>0) controls+=","; controls+="CATALOG"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Catalog:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="catalogId" styleId="item"/>&nbsp;<span class="reqind">*</span>&nbsp;
             <html:button property="locateCatalog"
                          onclick="popLocate('../adminportal/cataloglocate', 'catalogId', '');"
                          value="Locate Catalog"/><br>

           </td>
  </tr>
</logic:present>

<!-- Site List -->
<logic:present name="SITE_LIST">
  <% if(controls.length()>0) controls+=","; controls+="SITE_LIST"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Site List:</b></td>
           <td>
             <html:textarea onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="siteList" />&nbsp;<span class="reqind">*</span>&nbsp;
             <html:button property="locateSite"
                          onclick="popLocate('../adminportal/sitelocate', 'siteList', '');"
                          value="Locate Sites"/>
           </td>
  </tr>
</logic:present>

<!-- Site -->
<logic:present name="SITE">
  <% if(controls.length()>0) controls+=","; controls+="SITE"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Interval:</b></td>
       <td colspan="4">
          <html:radio name="REPORT_OP_FORM" property="interval" value="DAILY"/>&nbsp;Daily&nbsp;&nbsp;&nbsp;
          <html:radio name="REPORT_OP_FORM" property="interval" value="WEEKLY"/>&nbsp;Weekly&nbsp;&nbsp;&nbsp;
          <html:radio name="REPORT_OP_FORM" property="interval" value="MONTHLY"/>&nbsp;Monthly&nbsp;&nbsp;&nbsp;
          <html:radio name="REPORT_OP_FORM" property="interval" value="QUARTERLY"/>&nbsp;Quarterly&nbsp<span class="reqind">*</span>
          <html:radio name="REPORT_OP_FORM" property="interval" value="YEARLY"/>&nbsp;Yearly&nbsp<span class="reqind">*</span>
       </td>
  </tr>
</logic:present>

<!-- Date Type and Grouping -->
<logic:present name="DATE_TYPE_GROUPING">
  <% if(controls.length()>0) controls+=","; controls+="DATE_TYPE_GROUPING"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Date Type and Grouping:</b></td>
       <td colspan="4">
          <html:radio name="REPORT_OP_FORM" property="dateType" value="ORDER_DATE"/>&nbsp;Date Ordered&nbsp;&nbsp;&nbsp;
          <html:radio name="REPORT_OP_FORM" property="dateType" value="INVOICE_DATE"/>&nbsp;Date Invoiced&nbsp;&nbsp;&nbsp;<br>
          <html:checkbox name="REPORT_OP_FORM" property="groupOnOrder"/>&nbsp;Group by order
       </td>
  </tr>
</logic:present>

<!-- Shipment -->
<logic:present name="SHIPMENT">
  <% if(controls.length()>0) controls+=","; controls+="SHIPMENT"; %>
  <tr> <td>&nbsp;</td>
       <td><b>First/Last Shipment:</b></td>
       <td colspan="4">
          <html:radio name="REPORT_OP_FORM" property="shipment" value="FIRST"/>&nbsp;First Order Shipment&nbsp;&nbsp;&nbsp;
          <html:radio name="REPORT_OP_FORM" property="shipment" value="LAST"/>&nbsp;Last Order Shipment&nbsp;&nbsp;&nbsp;<br>
       </td>
  </tr>
</logic:present>


<!-- Order Amount -->
<logic:present name="AMOUNT">
  <% if(controls.length()>0) controls+=","; controls+="AMOUNT"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Order Total:</b></td>
           <td colspan="3">
                        Minimum Amount:
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        Maximum Amount:<br>
                        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="minAmt" /></span>
                        <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="maxAmt" /></span>
           </td>
  </tr>
</logic:present>

<!-- inclusive -->
<logic:present name="INCLUSIVE">
  <% if(controls.length()>0) controls+=","; controls+="INCLUSIVE"; %>
  <tr> <td>&nbsp;</td>
       <td><b>Range:</b></td>
       <td colspan="4">
          <html:radio name="REPORT_OP_FORM" property="inclusive" value="true"/>&nbsp;Inclusive&nbsp;&nbsp;&nbsp;
          <html:radio name="REPORT_OP_FORM" property="inclusive" value="false"/>&nbsp;Exclusive&nbsp;&nbsp;&nbsp;<span class="reqind">*</span>
       </td>
  </tr>
</logic:present>

<!-- State Optional-->
<logic:present name="STATE_OPT">
  <% if(controls.length()>0) controls+=","; controls+="STATE_OPT"; %>
  <tr> <td>&nbsp;</td>
       <td><b>State:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="stateCd" />&nbsp;&nbsp;
       </td>
  </tr>
</logic:present>
<!-- County Optional-->
<logic:present name="COUNTY_OPT">
  <% if(controls.length()>0) controls+=","; controls+="STATE_OPT"; %>
  <tr> <td>&nbsp;</td>
       <td><b>County:</b></td>
           <td>
             <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="countyCd" />&nbsp;&nbsp;
       </td>
  </tr>
</logic:present>
<% GenericReportControlViewVector grcVV = theForm.getGenericControls();
   if(grcVV!=null && grcVV.size()>0) {
   for(int ii=0; ii<grcVV.size(); ii++) {
   GenericReportControlView grc = (GenericReportControlView) grcVV.get(ii);
   String name = grc.getName();
   String label = grc.getLabel();
   if(label!=null && label.length()==0) label=null;
   boolean mandatoryFl=true;
   String mf = grc.getMandatoryFl();
   if(mf!=null) mf = mf.trim().toUpperCase();
   if("N".equals(mf) || "NO".equals(mf) ||"0".equals(mf) ||"F".equals(mf) ||"FALSE".equals(mf)) mandatoryFl = false;
   if(name.endsWith("_OPT")){
        mandatoryFl = false;
   }
   String controlEl = "genericControlValue["+ii+"]";
%>

<% if("BEG_DATE".equalsIgnoreCase(name)||"BEG_DATE_OPT".equalsIgnoreCase(name)) { %>
 <tr> <td>&nbsp</td>
      <%mandatoryFl=true;
      if(name.endsWith("_OPT")){
        mandatoryFl=false;
      }%>
       <td><b><%=(label==null)?"Begin Date:":label%></b></td>
       <td colspan="3">
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
    <% for(int jj=0; jj<grcVV.size(); jj++) {
    GenericReportControlView grc1 = (GenericReportControlView) grcVV.get(jj);
    String name1 = grc1.getName();
    if("END_DATE".equalsIgnoreCase(name1) || "END_DATE_OPT".equalsIgnoreCase(name1)) {
      String label1 = grc.getLabel();
      if(label1!=null && label1.length()==0) label1=null;
      boolean mandatoryFl1=true;
      if(name1.endsWith("OPT")){
        mandatoryFl1=false;
      }
      String mf1 = grc1.getMandatoryFl();
      if(mf!=null) mf1 = mf1.trim().toUpperCase();
      if("N".equals(mf1) || "NO".equals(mf1) ||"0".equals(mf1) ||"F".equals(mf1) ||"FALSE".equals(mf1)) mandatoryFl1 = false;
      String controlEl1 = "genericControlValue["+jj+"]"; %>
       <b><%=(label==null)?"End Date:":label1%></b>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl1%>" /><%if(mandatoryFl1) { %><span class="reqind">*</span> <%}%>
     <% break; }}%>
       </td>
  </tr>
<% } else if("END_DATE".equalsIgnoreCase(name)) { %>
<% } else if("STORE".equalsIgnoreCase(name)) { %>
<!-- Store -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Store:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/storelocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
       <html:button property="locateStore"
                    onclick="<%=onClick%>"
                    value="Locate Store"/>
        </td>
  </tr>
<% } else if("STORE_OPT".equalsIgnoreCase(name)) { %>
<!-- Store Optional-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Store:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/storelocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
       <html:button property="locateStore"
                    onclick="<%=onClick%>"
                    value="Locate Store"/>
        </td>
  </tr>
<% } else if("ALLOW_UPDATES".equalsIgnoreCase(name)) { %>
  <html:hidden name="REPORT_OP_FORM" property="<%=controlEl%>" value="false" />
<% } else if("GROUP_OPT".equalsIgnoreCase(name) || "GROUP".equalsIgnoreCase(name)) { %>
<!-- Group -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Group:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/grouplocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
       <html:button property="locateGroup"
                    onclick="<%=onClick%>"
                    value="Locate Group"/>
        </td>
  </tr>
<% } else if("ACCOUNT".equalsIgnoreCase(name)) { %>
<!-- Account -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Account:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/accountlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="Locate Account"/>
        </td>
  </tr>
<% } else if("ACCOUNT_OPT".equalsIgnoreCase(name)) { %>
<!-- Account Optional-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Account:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/accountlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="Locate Account"/>
        </td>
  </tr>
<% } else if("ACCOUNT_MULTI_OPT".equalsIgnoreCase(name)) { %>
<!-- Account Optional-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Account(s):":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/accountLocateMulti', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="Locate Account(s)"/>
        </td>
  </tr>
<% } else if("DISTRIBUTOR".equalsIgnoreCase(name)) { %>
<!-- Distributor -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Distributor:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/distlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateDistributor"
                          onclick="<%=onClick%>"
                          value="Locate Distributor"/>
        </td>
  </tr>

<% } else if("DISTRIBUTOR_OPT".equalsIgnoreCase(name)) { %>
<!-- Distributor Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Distributor:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/distlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
             <html:button property="locateDistributor"
                          onclick="<%=onClick%>"
                          value="Locate Distributor"/>
        </td>
  </tr>

<% } else if("DISTRIBUTOR_MULTI_OPT".equalsIgnoreCase(name)) { %>
<!-- Distributor Multi Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Distributor(s):":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/distLocateMulti', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
             <html:button property="locateDistributor(s)"
                          onclick="<%=onClick%>"
                          value="Locate Distributor(s)"/>
        </td>
  </tr>
<% } else if("MANUFACTURER".equalsIgnoreCase(name)) { %>
<!-- Manufacturer -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Manufacturer:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/manuflocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateManufacturer"
                          onclick="<%=onClick%>"
                          value="Locate Manufacturer"/>
        </td>
  </tr>
<% } else if("MANUF_MULTI_OPT".equalsIgnoreCase(name)) { %>
<!-- Manufacturer Multi Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Manufacturer(s):":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/manufLocateMulti', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
             <html:button property="locateManufacturer(s)"
                          onclick="<%=onClick%>"
                          value="Locate Manufacturer(s)"/>
        </td>
  </tr>
<% } else if("ITEM".equalsIgnoreCase(name)) { %>
<!-- Item -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Item:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/itemlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateItem"
                          onclick="<%=onClick%>"
                          value="Locate Item"/><br>
        </td>
  </tr>
<% } else if("ITEM_OPT".equalsIgnoreCase(name)) { %>
<!-- Item Opt-->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Item:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/itemlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
             <html:button property="locateItem"
                          onclick="<%=onClick%>"
                          value="Locate Item"/><br>
        </td>
  </tr>
<% } else if("CONTRACT".equalsIgnoreCase(name)||"CONTRACT_OPT".equalsIgnoreCase(name)) { %>
<!-- Contract -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Contract:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/contractlocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
         <html:button property="locateContract"
                          onclick="<%=onClick%>"
                          value="Locate Contract"/><br>
 </td>
  </tr>
<% } else if("CATALOG".equalsIgnoreCase(name)) { %>
<!-- Catalog -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Catalog:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/cataloglocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateCatalog"
                          onclick="<%=onClick%>"
                          value="Locate Catalog"/><br>
        </td>
  </tr>
<% } else if("SITE".equalsIgnoreCase(name)) { %>
<!-- Site -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Site:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/sitelocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
             <html:button property="locateSite"
                          onclick="<%=onClick%>"
                          value="Locate Site"/><br>
        </td>
  </tr>
<% } else if("COUNTY_OPT".equalsIgnoreCase(name)) { %>
<!-- County Opt -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"County:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } else if("STATE_OPT".equalsIgnoreCase(name)) { %>
<!-- State Opt -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"State:":label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
        </td>
  </tr>
<% } else if("CUSTOMER".equalsIgnoreCase(name)) { %>
<!-- Customer -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?"Customer:":label%></b></td>
       <td>
       <% String onClick = "popLocate('../adminportal/usermgrLocate', '"+controlEl+"', '');";%>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" />
       <html:button property="locateUser"
                    onclick="<%=onClick%>"
                    value="Locate Customer"/>
        </td>
  </tr>
<% } else  { %>
<!-- Generic Opt -->
  <tr> <td>&nbsp;</td>
       <td><b><%=(label==null)?name:label%></b></td>
       <td>
       <html:text onkeypress="<%=onKeyPress%>" name="REPORT_OP_FORM" property="<%=controlEl%>" /><%if(mandatoryFl) { %><span class="reqind">*</span> <%}%>
        </td>
  </tr>
<% } %>
<% }} //End of generic report controls %>
<html:hidden name="REPORT_OP_FORM" property="requestedControls" value="<%=controls%>" />
  <tr>
       <td colspan="3" align="center">
<% String name = (String)request.getParameter("rname");
   String Change = (String)request.getParameter("change");
   String type = (String)request.getParameter("rtype");
   String action = (String)request.getParameter("f_action");
%>
        <html:button property="foo" onclick="document.forms[0].f_action.value='Submit';
                                             return document.forms[0].submit();">
                <app:storeMessage  key="global.action.label.submit"/>
        </html:button>

       </td>
  </tr>

<%
   ActionErrors ae  = (ActionErrors)request.getAttribute("org.apache.struts.action.ERROR");

   if (Change == null) { Change ="";}
   if (name == null) { name ="";}
   if (type == null) { type ="";}
   if (((name != "") && (Change.equals("rname") == false) && (type != "")) && (ae == null || ae.size()==0)) { %>

<%   } %>

</tbody></html:form>
</table>



</div>




