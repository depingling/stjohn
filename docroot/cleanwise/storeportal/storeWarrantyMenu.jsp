<%--

  Title:        storeAssetMenu.jsp
  Description:  menu page
  Purpose:      view of  asset menu page
  Copyright:    Copyright (c) 2006
  Company:      CleanWise, Inc.
  Date:         25.12.2006
  Time:         21:42:21
  author        Alexander Chickin,Evgeny Vlasov, TrinitySoft, Inc.

--%>
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<table ID=1522 width="<%=Constants.TABLEWIDTH%>">
    <tr bgcolor="#000000"><td>
        <logic:present name="STORE_WARRANTY_DETAIL_FORM">
            <bean:define id="theForm" name="STORE_WARRANTY_DETAIL_FORM"  type="com.cleanwise.view.forms.StoreWarrantyDetailForm"/>
            <logic:present name="STORE_WARRANTY_DETAIL_FORM"  property="warrantyData">
                <bean:define id="warrantyId" name="STORE_WARRANTY_DETAIL_FORM" property="warrantyData.warrantyId"/>
                <logic:greaterThan   name="warrantyId" value="0" >


                    <app:renderStatefulButton link="storeWarrantyDetail.do?action=detail"
                                              name="Profile" tabClassOff="tbar" tabClassOn="tbarOn"
                                              linkClassOff="tbar" linkClassOn="tbarOn"
                                              contains="storeWarrantyDetail,storeWarrantyNote,storeWarrantyContent,storeWarrantyConfig,storeAssetWarrantyConfigDetail"/>

                </logic:greaterThan>
            </logic:present>
        </logic:present>
    </td>
    </tr>
</table>
