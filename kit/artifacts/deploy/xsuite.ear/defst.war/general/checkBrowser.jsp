<%
  
  String flag = (String)session.getAttribute("IsMSIE");
  if (flag == null) {
    //String userAgent = request.getHeader("USER_AGENT");
    // JSP 1.1 uses User-Agent instead of USER_AGENT
    String userAgent = request.getHeader("User-Agent");
    userAgent = (userAgent == null) ? "" : userAgent;
    boolean isMSIE = (userAgent.indexOf("MSIE") >= 0);
    boolean isMSIE4 = (userAgent.indexOf("MSIE 4") >= 0);
    boolean isNS408 = false;
    if (!isMSIE) {
      isNS408 = true;
      if (userAgent.indexOf("4.0") >= 0) {
        isNS408 = (!((userAgent.indexOf("4.08") < 0) && (userAgent.indexOf("4.09") < 0)));
      }
    }
    String value = (isMSIE) ? "Y" : "N";
    session.setAttribute("IsMSIE", value);
    value = (isMSIE4) ? "Y" : "N";
    session.setAttribute("IsMSIE4", value);
    value = (isNS408) ? "Y" : "N";
    session.setAttribute("IsNS408", value);
  }

%>

