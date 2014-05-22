<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>


<%--
<table border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="gradient">

                <b class="rtop"><b class="r1"></b><b class="r2"></b><b class="r3"></b><b class="r4"></b></b>
                <table border="0" cellpadding="0" cellspacing="0" width="<%=request.getParameter("width")%>" height="<%=request.getParameter("height")%>">
                    <tr>
                        <td valign="top"><jsp:include page='<%=ClwCustomizer.getStoreFilePath(request, request.getParameter("content"))%>'/>
                        </td>
                    </tr>
                </table>
              <div> <b class="rbottom"><b class="r4"></b><b class="r3"></b><b class="r2"></b><b class="r1"></b></b></div>
            </div>

        </td>
    </tr>
</table>
--%>

<table border="0" cellspacing="0" cellpadding="0">

  <tr  bgcolor="#ECECEC">
  <td><img src='<%=ClwCustomizer.getSIP(request,"topL.png")%>'></td>
  <td style="border:1px solid #B5B5B5;border-bottom:0;border-left:0;border-right:0;">&nbsp;</td>
  <td><img src='<%=ClwCustomizer.getSIP(request,"topR.png")%>'></td>
  </tr>

  <tr  bgcolor="#ECECEC">
  <td style="border:1px solid #B5B5B5;border-bottom:0;border-top:0;border-right:0;">&nbsp;</td>
  <td>

	<table border="0" cellpadding="0" style="width: <%=request.getParameter("width")%>; !" cellspacing="0" width="<%=request.getParameter("width")%>" height="<%=request.getParameter("height")%>">
        <tr>
            <td valign="top"><jsp:include page='<%=ClwCustomizer.getStoreFilePath(request, request.getParameter("content"))%>'/>
            </td>
        </tr>
    </table>

	</td>

	<td style="border:1px solid #B5B5B5;border-bottom:0;border-left:0;border-top:0;">&nbsp;</td>
  </tr>


  <tr  bgcolor="#ECECEC">
  <td><img src='<%=ClwCustomizer.getSIP(request,"botL.png")%>'></td>
  <td style="border:1px solid #B5B5B5;border-top:0;border-left:0;border-right:0;">&nbsp;</td>
  <td><img src='<%=ClwCustomizer.getSIP(request,"botR.png")%>'></td>
  </tr>


</table>




