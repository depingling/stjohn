<%@ page import="com.cleanwise.view.i18n.*" %>
<style>
table.scw  {background-color:  #4F4F4F;}
td.scwFoot {background-color:  #4F4F4F;}
td.scwInputDate   {background-color:  #FFFFFF;
                   color:             #0000FF;}


</style>
<% Locale userLocale = ClwI18nUtil.getUserLocale(request); %>
<%
    onKeyPress="return submitenter(this,event,'Submit');";
    String controlBeginDate = "DW_BEGIN_DATE";
    String controlEndDate = "DW_END_DATE";
    String controlEndDateOPT = "DW_END_DATE_OPT";
    GenericReportControlView dateControl = (GenericReportControlView) grcVV.get(2);
    String dateName = null;
    if (dateControl != null){
      dateName = dateControl.getName();
    }
%>

<%//if("BEGIN_DATE".equalsIgnoreCase(name) || "BEGIN_DATE_OPT".equalsIgnoreCase(name)) { %>
 <td>&nbsp;</td>
       <td><b><%=(label==null)?"Begin Date ":label%>
       (
       <%=ClwI18nUtil.getUIDateFormat(request)%>
       ):
       </b></td> 
       <td >
          <DIV "subheadergeneric">
                <html:text name="ANALYTIC_REPORT_FORM" styleId="searchDateFrom" property="<%=controlEl%>" maxlength="30"/>
                <% if (userLocale.toString().trim().equals("en_US")) { %>
                <a href="#"
                   onClick="showCalendar('searchDateFrom', event);return false;"
                   title="Choose Date"><img name="DATEFROM" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                              align="absmiddle" style="position:relative"
                              onmouseover="window.status='Choose Date';return true"
                              onmouseout="window.status='';return true"></a>
                <% } %>
                  <% if(mandatoryFl) { %>
                    <span class="reqind">*</span>

                  <% } %>
           </DIV>
        </td>
  <%
    //  GenericReportControlViewVector grcVV = theForm.getGenericControls();
      for(int jj=0; jj<grcVV.size(); jj++) {
          GenericReportControlView grc1 = (GenericReportControlView) grcVV.get(jj);
          String name1 = grc1.getName();
          if(controlEndDate.equalsIgnoreCase(name1) || (controlEndDateOPT).equalsIgnoreCase(name1)) {
              String label1 = grc1.getLabel();
              if(label1!=null && label1.length()==0){
                  label1=null;
              }
              boolean mandatoryFl1=true;
              if(name1.endsWith("OPT")){
                  mandatoryFl1=false;
              }
              String mf1 = grc1.getMandatoryFl();
              if(mf!=null){
                  mf1 = mf1.trim().toUpperCase();
              }
              String controlEl1 = "genericControlValue["+jj+"]";
              %>
              <tr> <td>&nbsp;</td>
                   <td><b><%=(label==null)?"End Date ":label1%>
                   (
                   <%=ClwI18nUtil.getUIDateFormat(request)%>
                   ):
                   </b></td>
                   <td >
                     <DIV "subheadergeneric">
                       <html:text name="ANALYTIC_REPORT_FORM" styleId="searchDateTo" property="<%=controlEl1%>" maxlength="30"/>
                       <% if (userLocale.toString().trim().equals("en_US")) { %>
                       <a href="#"
                       onClick="showCalendar('searchDateTo', event);return false;"
                       title="Choose Date"><img name="DATETO" src="../externals/images/showCalendar.gif" width=19 height=19 border=0
                       align="absmiddle" style="position:relative"
                       onmouseover="window.status='Choose Date';return true"
                       onmouseout="window.status='';return true"></a>
                       <% } %>
                  <% if(mandatoryFl) { %>
                    <span class="reqind">*</span>
                  <% } %>
                  </DIV>
                 </td>
               </tr>
              <% break; 
            }
         }
     } else if("DW_END_DATE".equalsIgnoreCase(name)) {
     } else if("DW_END_DATE_OPT".equalsIgnoreCase(name)) {
%>

