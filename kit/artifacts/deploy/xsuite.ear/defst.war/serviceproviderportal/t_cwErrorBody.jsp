<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.*" %>
<%@ page import="com.cleanwise.service.api.session.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);%>


  <table align="center" cellspacing="0"><tr><td  
    style="border-top: solid 1px black; border-right: solid 1px black; border-left: solid 1px black;">
  
  </td></tr>
  <tr><td  
    style="padding: 10px; border: solid 1px black;">
    <br><br><span class="genericerror">
  We are sorry.
  <p>
  Your request could not be handled at this time.<br>
  Please try again or contact Customer Service.
  </p>
      </span>
  </td></tr></table>

  <div style="align: center;">
    <br><br><span class="genericerror">
      <html:errors/></span>
  <div>

