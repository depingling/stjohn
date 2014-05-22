<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>


 <script language="JavaScript1.2">
      if(typeof dojo == "undefined"){
          var djConfig = {parseOnLoad: true,isDebug: false, usePlainJson: true}
          document.write("<script type=\"text/javascript\" src=\"<%=request.getContextPath()%>/externals/dojo_1.1.0/dojo/dojo.js\"><"+"/script>")
          document.write('<link rel="stylesheet" type="text/css" href="'+"../externals/dojo_1.1.0/clw/CLW/themes/PopupFacade.css"+'"/>');
      }
  </script>


    <logic:present name="SHOP_FORM" property="catalogMenu">

        <script language="JavaScript" type="text/javascript">
            dojo.require("clw.CLW.PopupFacade");
        </script>
        <app:popupFacade id="popupFacade_1" 
                         width="350px"
                         name="SHOP_FORM"
                         property="catalogMenu"/>

    </logic:present>

