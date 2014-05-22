<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<% boolean clwSwitch = ClwCustomizer.getClwSwitch(); %>

<%
   String s = (String)request.getRequestURI();
   String hls = null;
   
   String accountId = (String)request.getSession().getAttribute("Account.id");
   if ( null == accountId ) {
     accountId = request.getParameter("searchField");
     request.getSession().setAttribute("Account.id", accountId);
   }
%>


<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr bgcolor="#000000">
<td class="tbartext">
<%
  if(s.indexOf("accountmgrDetail") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>

<a class=<%= hls %> href="accountmgrDetail.do?action=accountdetail&accountId=<%=accountId%>">Detail</a> |

<%if(s.indexOf("relate") > 0 || s.indexOf("accountBillTos") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>
<a class=<%= hls %> href="accountBillTos.do?action=accountBillTos&accountId=<%=accountId%>">BillTos</a> |

<%if(s.indexOf("relate") > 0 || s.indexOf("Relationships") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>
<a class=<%= hls %> href="related.do?action=account">Account Relationships</a> |

<%if(s.indexOf("costcentermgr") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>
<a class=<%= hls %> href="costcentermgr.do?action=viewall">Cost Centers</a> |

<%if(s.indexOf("accountUIConfig") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>
<a class=<%= hls %> href="accountUIConfig.do">UI</a> |


<%if(s.indexOf("accountSiteData") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>
<a class=<%= hls %> href="accountSiteData.do">Site Data</a> |


<%if(s.indexOf("accountWorkflow") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>
<a class=<%= hls %> href="accountWorkflow.do">Workflows</a> |
<%if(clwSwitch) { %>
<%if(s.indexOf("accountItemSubstMgr") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>
<a class=<%= hls %> href="accountitemsubstmgr.do?action=init">Item Substitutions</a>
<% } %>
</td>
</tr>
</table>
<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr bgcolor="#000000">
<td class="tbartext">
<%if(s.indexOf("PriceMgr") > 0){ 
    hls="tbarOn";
  }else{ 
    hls="tbar";
  }
%>
<a class=<%= hls %> href="accountitempricemgr.do?action=init">Item Prices, SKU, Description</a> |

<%if(s.indexOf("pipelinemgr") > 0){ 
    hls="tbarOn"; }else{ hls="tbar"; } %>
<a class=<%= hls %> href="pipelinemgr.do?action=init">Order Processing</a> |
 
<%if(s.indexOf("inventorymgr") > 0){ 
    hls="tbarOn"; }else{ hls="tbar"; } %>
<a class=<%= hls %> href="inventorymgr.do?action=init&accountId=<%=accountId%>">
Inventory Items</a> |
 
<%if(s.indexOf("deliverySchedules") > 0){ 
    hls="tbarOn"; }else{ hls="tbar"; } %>
<a class=<%= hls %> href="deliverySchedules.do?action=deliverySchedInit">Site Delivery Schedules</a>  |

<% 
  if(s.indexOf("accountmgrNote") > 0){ 
    hls="tbarOn"; } else {hls="tbar";  }%>
<a class=<%= hls %> href="accountmgrNote.do">Notes</a> 

</td>
</tr>
</table>
