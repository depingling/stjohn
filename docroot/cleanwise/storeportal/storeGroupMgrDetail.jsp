<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<html:html>

    <div class="text">
        
        <table ID=825 border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
            <html:form styleId="826" name="STORE_GROUP_FORM" action="/storeportal/storeGroupMgrDetail.do"
                       type="com.cleanwise.view.forms.StoreGroupForm">
                <tr>
                    <td><b>Group Id:</b></td>
                    <td>
                        <bean:write name="STORE_GROUP_FORM" property="groupData.groupId"/>
                    </td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td><b>Group Type:</b></td>
                    <td>
						<logic:equal name="STORE_GROUP_FORM" property="showGropTypeFl" value="true">
							<html:select name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
										 onchange="document.forms[0].submit();">
								<html:option value="">
									<app:storeMessage  key="admin.select"/>
								</html:option>
								<html:options collection="group.type.vector" property="value"/>
							</html:select>
							<span class="reqind">*</span>
						</logic:equal>
						<logic:equal name="STORE_GROUP_FORM" property="showGropTypeFl" value="false">
							<bean:write name="STORE_GROUP_FORM" property="groupData.groupTypeCd" />
						</logic:equal>
                    </td>
                    <td><b>Group Status:</b></td>
                    <td>
                        <html:select name="STORE_GROUP_FORM" property="groupData.groupStatusCd">
                            <html:option value="">
                                <app:storeMessage  key="admin.select"/>
                            </html:option>
                            <html:options collection="group.status.vector" property="value"/>
                        </html:select>
                        <span class="reqind">*</span>
                    </td>
                </tr>
                <tr>
                    <td><b>Group Name:</b></td>
                    <td colspan="3">
                        <html:text name="STORE_GROUP_FORM" property="groupData.shortDesc" size="30"/>
                        <logic:equal name="STORE_GROUP_FORM" property="groupData.groupTypeCd"
                                     value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
                            Or select name from the list:
                            <html:select name="STORE_GROUP_FORM" property="groupNameSelect">
                                <html:option value="">
                                    <app:storeMessage  key="admin.select"/>
                                </html:option>
                                <html:options collection="group.intrinsic.name.vector" property="value"/>
                            </html:select>
                        </logic:equal>
                        <span class="reqind">*</span>
                    </td>
                </tr>

                <tr>
                    <td>
                        <html:submit property="action">
                            <app:storeMessage  key="admin.button.submitUpdates"/>
                        </html:submit>
                    </td>
                    <logic:equal name="<%=Constants.APP_USER%>" property="user.userTypeCd"
                                 value="<%=RefCodeNames.USER_TYPE_CD.SYSTEM_ADMINISTRATOR%>">
                        <td colspan="3">
                            <html:checkbox name="STORE_GROUP_FORM" property="assignGroupStoreAssocFl"/>
                            Bind to store
                        </td>
                    </logic:equal>
                </tr>

            </html:form>
            
        </table>

    </div>

</html:html>
