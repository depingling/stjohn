<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="theForm" name="STORE_ITEM_LOADER_FORM" type="com.cleanwise.view.forms.StoreItemLoaderMgrForm"/>
<%
  String action=theForm.getAction();
  ActionErrors ae = (ActionErrors) request.getAttribute("org.apache.struts.action.ERROR");
  boolean errorFl = (ae!=null && ae.size()>0)? true:false;
  if(!Utility.isSet(action) || action.equals("init") || action.equals("Search")) {
%>
<jsp:include flush='true' page="storeItemUploadSearch.jsp" />

<%  } else if(action.equals("Create New") ||
               action.equals("Upload") ||
               action.equals("Full Table") ||
               action.equals("Get Subtable") && errorFl ||
               action.equals("edit") && errorFl
) { %>
<jsp:include flush='true' page="storeItemUploadBrowse.jsp" />

<%  } else if(action.equals("Get Subtable") && !errorFl ||
              action.equals("Process") ||
              action.equals("edit") && !errorFl ||
              action.equals("getToUpdate") ||
              action.equals("Select Update") ||
              action.equals("Update Values") ||
              action.equals("Save") ||
              action.equals("Show Matched") ||
              action.equals("Select") ||
              action.equals("Match") ||
              action.equals("Assign Skus") ||
              action.equals("Create Skus") ||
              action.equals("Update Skus") ||
              action.equals("Remove Assignment") ||
              action.equals("editreturn") ||
              action.equals("edit-item") ||
              action.equals("Cancel") ||
              action.equals("Reload")
) { %>
<jsp:include flush='true' page="storeItemUploadTable.jsp" />

<%  } %>
