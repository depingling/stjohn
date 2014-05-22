
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
   String s = (String)request.getRequestURI();
   s += " " + request.getParameter("action");
   String style = null;
   
%>


<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr>
  <tr bgcolor="#000000"> 
<td class="tbartext">

<%
  if(s.indexOf("detail") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="sitemgrDetail.do?action=sitedetail">Site Detail</a> |

<%
  if(s.indexOf("relate") > 0 || s.indexOf("Relationships") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="related.do?action=site">Site Relationships</a> |

<%
  if(s.indexOf("sitemgrBudget") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="sitemgrBudgets.do?action=sitebudgets">Site Budgets</a> |

<%
  if(s.indexOf("workflow") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="sitemgrWorkflow.do?action=site_workflow">Workflow</a> |

<%
  if(s.indexOf("siteConfig") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="siteConfig.do?action=init&siteconfig=true">Site Configuration</a> |

<%
  if(s.indexOf("sitemgrProfiling") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="sitemgrProfiling.do">Site Profiling</a> |

<%
  if(s.indexOf("sitemgrNotes") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="sitemgrNote.do">Notes</a> 

</td>
<bean:define id="thisSiteId"
  name="SITE_DETAIL_FORM" property="id" type="java.lang.String"/>
<td>
<a style="tbar" href="../userportal/msbsites.do?action=shop_site&start_point=shop_quick_order&siteId=<%=thisSiteId%>">Runtime</a>
</td>
</tr>
</table>
