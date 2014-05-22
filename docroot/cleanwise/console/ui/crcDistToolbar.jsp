

<%
   String s = (String)request.getRequestURI();
   String style = null;
%>


<table border="0" cellpadding="0" cellspacing="0" width="769">
  <tr bgcolor="#000000">
    <td class="tbartext">

<% String lDistId = "0";
com.cleanwise.view.forms.DistMgrDetailForm distForm =
  (com.cleanwise.view.forms.DistMgrDetailForm)
  session.getAttribute("DIST_DETAIL_FORM");
if ( null != distForm ) {
  lDistId = distForm.getId();
}

%>

<%
  if(s.indexOf("Detail") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="crcdist.do?action=distdetail&searchType=id&searchField=<%=lDistId%>"> Distributor Detail</a> |

<%
  if(s.indexOf("crcdistItem") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="crcdistItem.do"> Items </a> |
<%
  if(s.indexOf("crcdistTerr") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="crcdistTerr.do"> Territory </a> | 
<%
  if(s.indexOf("deliveryScheduleMgr") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="crcDeliverySchedule.do"> Delivery Schedule </a>  | 
<!--
<%
  if(s.indexOf("distNoteMgr") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="distNoteMgr.do"> Notes </a> 
-->
    </td>
  </tr>
</table>
