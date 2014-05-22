<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script language="JavaScript1.2">
    <!--
    function SetChecked(val) {
       var dml = document.forms['TRADING_PARTNER_FORM'];
	 var ellen = dml.elements.length;
        for (j = 0; j < ellen; j++) {
            if (dml.elements[j].name.match('selectedPropertyMappingIds') && !dml.elements[j].disabled) {
                dml.elements[j].checked = val;
            }
        }
    }
    //-->
</script>

<bean:define id="theForm" name="TRADING_PARTNER_FORM" type="com.cleanwise.view.forms.TradingPartnerMgrForm"/>
<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>System Administrator: Trading Partners</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include flush='true' page="ui/systemToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<div class="text">


<bean:define id="trpid" name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerId" type="java.lang.Integer"/>

  <table width="100%" cellspacing="0" border="0" class="mainbody">
  <html:form name="TRADING_PARTNER_FORM" action="adminportal/tradingprofilemappingmgr.do" type="com.cleanwise.view.forms.TradingPartnerMgrForm">
  <html:hidden name="TRADING_PARTNER_FORM" property="tradingPartner.tradingPartnerId" value="<%=trpid.toString()%>"/>
   <tr><td colspan="4">Define trading profile mappings</td></tr>
   <tr>
        <td><b>Mapping Code</b></td>
        <td><b>Mapping Key</b></td>
        <td><b>Property</b></td>
        <td><b>Property Type/Order Column/Loader Column</b></td>
        <td><b>Qualifier</b></td>
        <td><b>Prefix/Value</b></td>
        <td><b>Mandatory</b></td>
        <td><b>Segment</b></td>
        <td><b>Order By (start at 0)</b></td>
        <td><b>Select</b><br>
        <a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
        <a href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
   </td>
   </tr>
   <logic:iterate id="map" name="TRADING_PARTNER_FORM" property="tradingPropertyMapDataVector" indexId="i">
      <tr>
        <%String prop;%>
        <td>
                <%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.tradingPropertyMapCode";%>
                <html:select name="TRADING_PARTNER_FORM" property="<%=prop%>" onchange="this.form.submit();">
                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                        <html:options  collection="trading.property.map.cd.vector" property="value" />
                </html:select>
        </td>
        <td>
                <%prop = "tradingPropertyMapData["+i+"].tradingConfigId";%>
                <html:select name="TRADING_PARTNER_FORM" property="<%=prop%>" onchange="this.form.submit();">
                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<logic:iterate id="aOpt" name="TRADING_PARTNER_FORM" property="tradingDataExchanges" type="TradingProfileConfigData">
				<html:option value="<%=Integer.toString(aOpt.getTradingProfileConfigId())%>"><bean:write name="aOpt" property="direction"/> <bean:write name="aOpt" property="setType"/> <bean:write name="aOpt" property="pattern"/></html:option>
			</logic:iterate>
                </html:select>
        </td>
        <td>
<%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.entityProperty";%>
<logic:equal name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP%>">
<html:select name="TRADING_PARTNER_FORM" property="<%=prop%>" onchange="this.form.submit();">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.COLUMN_HEADER%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.COLUMN_HEADER%></html:option>
<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%></html:option>
</html:select>
</logic:equal>

            <logic:equal name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY%>">
                <html:select name="TRADING_PARTNER_FORM" property="<%=prop%>" onchange="this.form.submit();">
                    <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                    <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.FLAT_FILE_CONTAINS_HEADER%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.FLAT_FILE_CONTAINS_HEADER%></html:option>
                    <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.FIELD_MAP_USE_HEADERS%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.FIELD_MAP_USE_HEADERS%></html:option>
                    <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.VALUE_OBJECT_CLASSNAME%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.VALUE_OBJECT_CLASSNAME%></html:option>
                    <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.CLW_TRADING_PARTNER_KEY%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.CLW_TRADING_PARTNER_KEY%></html:option>
                    <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.SET_SHIP_TO_FH_ADDRESS%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.SET_SHIP_TO_FH_ADDRESS%></html:option>
                    <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.EMAIL_ACKNOWLEDGE%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.EMAIL_ACKNOWLEDGE%></html:option>
                    <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%></html:option>
                    <logic:equal name="map" property="tradingPropertyMapData.setType" value="<%=RefCodeNames.EDI_TYPE_CD.T850%>">
                        <logic:equal name="map" property="tradingPropertyMapData.direction" value="OUT">
                            <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.PADDING_CHAR%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.PADDING_CHAR%></html:option>
                            <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.PADDING_TOTAL_LENGTH%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.PADDING_TOTAL_LENGTH%></html:option>
                        </logic:equal>
                    </logic:equal>
                    <logic:equal name="map" property="tradingPropertyMapData.direction" value="IN">
                    	<logic:equal name="map" property="tradingPropertyMapData.setType" value="<%=RefCodeNames.EDI_TYPE_CD.T855%>">
                            <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.IGNORE_MISSING_LINE_INFO%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.IGNORE_MISSING_LINE_INFO%></html:option>
                            <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.MATCH_ORDER_BY_DIST_ORDER_NUM%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.MATCH_ORDER_BY_DIST_ORDER_NUM%></html:option>
                        </logic:equal>
                        <logic:equal name="map" property="tradingPropertyMapData.setType" value="<%=RefCodeNames.EDI_TYPE_CD.T856%>">
                            <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.IGNORE_MISSING_LINE_INFO%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.IGNORE_MISSING_LINE_INFO%></html:option>
                        </logic:equal>
                        <logic:equal name="map" property="tradingPropertyMapData.setType" value="<%=RefCodeNames.EDI_TYPE_CD.TFLAT_FILE_IN%>">
                            <html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.PROCESS_NEW_RECORD_ONLY%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.PROCESS_NEW_RECORD_ONLY%></html:option>
                        </logic:equal>
                    </logic:equal>
                </html:select>
            </logic:equal>

		<logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP%>">
			<logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY%>">
				<html:select name="TRADING_PARTNER_FORM" property="<%=prop%>" onchange="this.form.submit();">
					<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
					<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_COLUMN%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_COLUMN%></html:option>
					<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%></html:option>
					<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_PROPERTY%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_PROPERTY%></html:option>
					<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_PROPERTY%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_PROPERTY%></html:option>
					<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_FIELD_PROPERTY%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_FIELD_PROPERTY%></html:option>
					<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_PROPERTY%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_PROPERTY%></html:option>
					<html:option value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_FIELD_PROPERTY%>"><%=RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_FIELD_PROPERTY%></html:option>
				</html:select>
			</logic:notEqual>
		</logic:notEqual> <%--CONFIGURATION_PROPERTY--%>
        </td>
        <td>
	    <logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY%>">
                <%boolean propertyTypeCdEnabled = true;%>
                <logic:equal name="map" property="tradingPropertyMapData.entityProperty" value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%>">
                        <%propertyTypeCdEnabled = false;%>
                </logic:equal>
                <logic:equal name="map" property="tradingPropertyMapData.entityProperty" value="">
                        <%propertyTypeCdEnabled = false;%>
                </logic:equal>
		<%String selectOptions = "property.type.cd.vector";%>
		<logic:equal name="map" property="tradingPropertyMapData.entityProperty" value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.SITE_FIELD_PROPERTY%>">
			<%selectOptions = null;%>
		</logic:equal>
		<logic:equal name="map" property="tradingPropertyMapData.entityProperty" value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.ACCOUNT_FIELD_PROPERTY%>">
			<%selectOptions = null;%>
		</logic:equal>
		<logic:equal name="map" property="tradingPropertyMapData.entityProperty" value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.ORDER_COLUMN%>">
			<%selectOptions = "order.data.properties";%>
		</logic:equal>
		<logic:equal name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP%>">
			<%selectOptions=null;%>
			<logic:present name="map" property="tradingProfileConfig">
				<logic:present name="map" property="tradingProfileConfig.classname">
					<bean:define id="currConfig" name="map" property="tradingProfileConfig" type="com.cleanwise.service.api.value.TradingProfileConfigData"/>
					<%selectOptions = currConfig.getTradingProfileConfigId()+".properties."+currConfig.getClassname();%>
					<logic:notPresent name="<%=selectOptions%>">
						<%selectOptions = null;%>
					</logic:notPresent>
				</logic:present>
			</logic:present>
		</logic:equal>
		<%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.propertyTypeCd";%>
		<%if(selectOptions == null){%>
			<html:text name="TRADING_PARTNER_FORM" property="<%=prop%>" size="10"/>
		<%}else{%>
			<html:select name="TRADING_PARTNER_FORM" property="<%=prop%>">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="<%=selectOptions%>" property="value"/>
			</html:select>
		<%}%>
	   </logic:notEqual> <%--CONFIGURATION_PROPERTY--%>
	   <logic:equal name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY%>">
            <%boolean propertyTypeCdEnabled = false;%>
            <logic:equal name="map" property="tradingPropertyMapData.entityProperty" value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%>">
                <%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.propertyTypeCd";%>		
				<html:text name="TRADING_PARTNER_FORM" property="<%=prop%>" size="20"/>
            </logic:equal>
        </logic:equal> <%--CONFIGURATION_PROPERTY--%>
        </td>
        <td>
	   <logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY%>">
		<logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP%>">
			<%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.qualifierCode";%>
			<html:text name="TRADING_PARTNER_FORM" property="<%=prop%>" size="2" maxlength="2"/>
		</logic:notEqual>
	   </logic:notEqual> <%--CONFIGURATION_PROPERTY--%>
        </td>
        <td>
		<%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.hardValue";%>
		<html:text name="TRADING_PARTNER_FORM" property="<%=prop%>" size="10"/>
        </td>
        <td>
	    <logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY%>">
		<logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP%>">
			<%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.mandatory";%>
			<html:select name="TRADING_PARTNER_FORM" property="<%=prop%>">
				<html:option value="true">True</html:option>
				<html:option value="false">False</html:option>
			</html:select>
		</logic:notEqual>
	    </logic:notEqual> <%--CONFIGURATION_PROPERTY--%>
        </td>
        <td>
	    <logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY%>">
		<logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.FIELD_MAP%>">
			<%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.useCode";%>
			<html:text name="TRADING_PARTNER_FORM" property="<%=prop%>" size="2"/>
		</logic:notEqual>
	    </logic:notEqual> <%--CONFIGURATION_PROPERTY--%>
        </td>
        <td>
	   <logic:notEqual name="map" property="tradingPropertyMapData.tradingPropertyMapCode" value="<%=RefCodeNames.TRADING_PROPERTY_MAP_CD.CONFIGURATION_PROPERTY%>">
	    <logic:notEqual name="map" property="tradingPropertyMapData.entityProperty" value="<%=RefCodeNames.ENTITY_PROPERTY_TYPE.HARD_VALUE%>">
			<%prop = "tradingPropertyMapData["+i+"].tradingPropertyMapData.orderBy";%>
			<html:text name="TRADING_PARTNER_FORM" property="<%=prop%>" size="2"/>find me
		</logic:notEqual>
	   </logic:notEqual> <%--CONFIGURATION_PROPERTY--%>
        </td>
     <td>
     <%prop="tradingPropertyMapData.tradingPropertyMapId";%>
     <bean:define id ="tradingPropMapId" name="map" property="<%=prop%>"/>
     <html:multibox name ="TRADING_PARTNER_FORM" property="selectedPropertyMappingIds" value="<%=tradingPropMapId.toString()%>"/></td>
     </tr>
   </logic:iterate>
   <tr>
   <td colspan = "4">
        <html:submit property="action" value="Add Mapping"/>
        <html:submit property="action" value="Update Mappings"/>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.back"/>
        </html:submit>
        <html:submit property="action" value="Delete Mapping"/>
   </td>
   </tr>
  </html:form>
  </table>


<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>

