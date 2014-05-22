
<%
   String s = (String)request.getRequestURI();
   String style = null;

%>

<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr bgcolor="#000000">
<td class="tbartext">
<%
  if(s.indexOf("storemgrDetail") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="storemgrDetail.do?action=storedetail">Store Detail</a> |

<%
  if(s.indexOf("relate") > 0 || s.indexOf("Relationships") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="related.do?action=store">Store Relationships</a> |

<%
  if(s.indexOf("storeUIConfig") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="storeUIConfig.do">UI</a> |

<%if(s.indexOf("storeWorkflow") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="storeWorkflow.do">Workflows</a> |

<%if(s.indexOf("storeAccountData") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="storeAccountData.do">Account Data</a> |

<%if(s.indexOf("storeResources") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="storeResources.do">Resource Strings</a>

</td>
</tr>
</table>
