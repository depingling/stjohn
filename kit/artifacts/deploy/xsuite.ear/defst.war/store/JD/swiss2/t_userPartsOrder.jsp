<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.text.DateFormat" %>

<%--
  Date: 05.05.2008
  Time: 13:55:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>




<bean:define id="theOrder" type="com.cleanwise.service.api.value.OrderJoinData" name="order"/>
<bean:define id="appUser" type="com.cleanwise.view.utils.CleanwiseUser" name="<%=Constants.APP_USER%>"/>

<div class="text"><font color="red"><html:errors/></font></div>

<table width="100%" cellpadding="0" cellspacing="0">
<tr>
    <td>
        <br>
        <table id="tMain" width="90%" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="2">
                <table id="t1" width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="15%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.orderStatus"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <logic:present name="theOrder" property="order.orderStatusCd">
                                <bean:write name="theOrder" property="order.orderStatusCd"/>
                            </logic:present>
                        </td>
                    </tr>
                </table>
                <br>
                </td>
            </tr>
            <tr>
                <td>
                <table id="t2" width="100%" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="40%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWorkOrder.text.partsOrder.orderNum"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <logic:present name="theOrder" property="order.orderNum">
                                <bean:write name="theOrder" property="order.orderNum"/>
                            </logic:present>
                        </td>
                    </tr>
                    <tr>
                        <td width="40%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="userWorkOrder.text.partsOrder.orderDate"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <%=ClwI18nUtil.formatDate(request, theOrder.getOrder().getAddDate(), DateFormat.DEFAULT)%>
                        </td>
                    </tr>
                    <tr>
                        <td width="40%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.poNumber"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <logic:present name="theOrder" property="order.requestPoNum">
                                <bean:write name="theOrder" property="order.requestPoNum"/>
                            </logic:present>
                        </td>
                    </tr>
                    <tr>
                        <td width="40%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.source"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <logic:present name="theOrder" property="order.orderSourceCd">
                                <bean:write name="theOrder" property="order.orderSourceCd"/>
                            </logic:present>
                        </td>
                    </tr>
                </table>
                </td>
                <td>
                <table id="t3" width="100%" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="40%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.contactName"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <logic:present name="theOrder" property="placedByFirstName">
                                <bean:write name="theOrder" property="placedByFirstName"/>&nbsp;
                            </logic:present>
                            <logic:present name="theOrder" property="placedByLastName">
                                <bean:write name="theOrder" property="placedByLastName"/>
                            </logic:present>
                        </td>
                    </tr>
                    <tr>
                        <td width="40%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.contactPhone"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <logic:present name="theOrder" property="order.orderContactPhoneNum">
                                <bean:write name="theOrder" property="order.orderContactPhoneNum"/>
                            </logic:present>
                        </td>
                    </tr>
                    <tr>
                        <td width="40%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.contactEmail"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <logic:present name="theOrder" property="order.orderContactEmail">
                                <bean:write name="theOrder" property="order.orderContactEmail"/>
                            </logic:present>
                        </td>
                    </tr>
                    <tr>
                        <td width="40%" align="left">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.placedBy"/>:&nbsp;</b>
                            </span>
                        </td>
                        <td align="left">
                            <logic:present name="theOrder" property="placedByFirstName">
                                <bean:write name="theOrder" property="placedByFirstName"/>&nbsp;
                            </logic:present>
                            <logic:present name="theOrder" property="placedByLastName">
                                <bean:write name="theOrder" property="placedByLastName"/>&nbsp;
                            </logic:present>
                            <logic:present name="theOrder" property="order.addBy">
                                (<bean:write name="theOrder" property="order.addBy"/>)
                            </logic:present>
                        </td>
                    </tr>
                </table>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                <table id="t4" width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td>
                            <br>
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.comments"/>:&nbsp;</b>
                                <logic:present name="theOrder" property="order.comments">
                                    <bean:write name="theOrder" property="order.comments"/>
                                </logic:present>
                            </span>
                            <br>
                        </td>
                    </tr>
                </table>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <br>
                </td>
            </tr>
        </table>    
    </td>
</tr>
<logic:notEqual name="theOrder" property="order.orderStatusCd" value="<%=RefCodeNames.ORDER_STATUS_CD.RECEIVED%>">
<tr>
    <td>        
        <table id="tOrderItems" width="100%" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan="10" class="tableoutline" ><img src="/images/cw_spacer.gif" height="1"></td>
            </tr>
            <tr class="shopcharthead">
                <td width="6%" align="center"><div>Our&nbsp;Sku&nbsp;#</td>
                <td width="30%" align="center"><div>Product&nbsp;Name</div></td>
                <td width="12%" align="center"><div>Size</div></td>
                <td width="3%" align="center"><div>Pack</div></td>
                <td width="3%" align="center"><div>UOM</div></td>
                <td width="13%" align="center"><div>Manufacturer</div></td>
                <td width="13%" align="center"><div>Mfg.Sku&nbsp;#</div></td>
                <td width="8%" align="center"><div>Price</div></td>
                <td width="4%" align="center"><div>Qty</td>
                <td width="8%" align="center"><div>Extended&nbsp;Price</td>
            </tr>
            <tr>
                <td colspan="10" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
            </tr>
            <tr>
                <td colspan="10">
                    <br>
                </td>
            </tr>
            <logic:iterate  id="orderItems"
                            name="theOrder"
                            property="orderJoinItems"
                            type="com.cleanwise.service.api.value.OrderItemJoinData"
                            indexId="j">
                <bean:define id="itemData" name="orderItems" property="orderItem" type="com.cleanwise.service.api.value.OrderItemData"/>
                
                <logic:present name="itemData" property="totalQuantityOrdered">
                <logic:greaterThan name="itemData" property="totalQuantityOrdered" value="0">
                
                    <bean:define id="qty" name="itemData" property="totalQuantityOrdered" type="Integer"/>
                    <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)j)%>">
                        <td align="center">
                            <logic:present name="itemData" property="itemSkuNum">
                                <logic:greaterThan name="itemData" property="itemSkuNum" value="0">
                                    <bean:write name="itemData" property="itemSkuNum"/>
                                </logic:greaterThan>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="itemData" property="itemShortDesc">
                                <bean:write name="itemData" property="itemShortDesc"/>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="itemData" property="itemSize">
                                <bean:write name="itemData" property="itemSize"/>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="itemData" property="itemPack">
                                <bean:write name="itemData" property="itemPack"/>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="itemData" property="itemUom">
                                <bean:write name="itemData" property="itemUom"/>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="itemData" property="manuItemShortDesc">
                                <bean:write name="itemData" property="manuItemShortDesc"/>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="itemData" property="manuItemSkuNum">
                                <bean:write name="itemData" property="manuItemSkuNum"/>
                            </logic:present>
                        </td>
                        <td align="center">
                            <logic:present name="itemData" property="custContractPrice">
                                <%=ClwI18nUtil.priceFormat(request, itemData.getCustContractPrice(), theOrder.getLocaleCd(), "")%>
                            </logic:present>
                        </td>
                        <td align="center">
                            <bean:write name="itemData" property="totalQuantityOrdered"/>
                        </td>
                        <td align="center">
                            <logic:present name="itemData" property="custContractPrice"> 
                            <bean:define id="price" name="itemData" property="custContractPrice" type="java.math.BigDecimal"/>
                                <%=ClwI18nUtil.priceFormat(request, price.multiply(new java.math.BigDecimal(qty.doubleValue())), theOrder.getLocaleCd(), "&nbsp;")%>
                            </logic:present>
                        </td>
                    </tr>
                </logic:greaterThan>
                </logic:present>
                
            </logic:iterate>
            <tr>
                <td colspan="10">
                    <br>
                </td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <logic:present name="theOrder" property="order">
        <bean:define id="order" name="theOrder" property="order" type="com.cleanwise.service.api.value.OrderData"/>
        
        <logic:present name="order" property="totalPrice">
        <bean:define id="totalPrice" name="order" property="totalPrice" type="java.math.BigDecimal"/>
        <table id="tAddressInfo" width="100%" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td colspan=3 class="tableoutline" ><img src="/images/cw_spacer.gif" height="1"></td>
            </tr>
            <tr class="shopcharthead">
                <td width="6%" align="center"> <b>&raquo;&nbsp;<app:storeMessage key="shop.orderStatus.text.orderSummary"/></b></td>
                <td width="6%" align="center"> <b>&raquo;&nbsp;<app:storeMessage key="shop.orderStatus.text.shipTo"/></b></td>
                <td width="6%" align="center"> <b>&raquo;&nbsp;<app:storeMessage key="shop.orderStatus.text.billTo"/></b></td>
            </tr>
            <tr>
                <td colspan=3 class="tableoutline" ><img src="/images/cw_spacer.gif" height="1"></td>
            </tr>
            <tr>
                <td colspan="3">
                    <br>
                </td>
            </tr>
            <tr>
                <td valign="top">
                <table id="t1" width="100%" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="50%" align="right">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.subtotal:"/>&nbsp;</b>
                            </span>
                        </td>
                        <td width="50%" align="left">
                            <%=ClwI18nUtil.priceFormat(request, totalPrice, theOrder.getLocaleCd(), "")%>
                        </td>
                    </tr>
                    <logic:present name="order" property="totalMiscCost">
                    <bean:define id="miscCost" name="order" property="totalMiscCost" type="java.math.BigDecimal"/>  
                    <tr>
                        <td width="50%" align="right">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.handling:"/>&nbsp;</b>
                            </span>
                        </td>
                        <td width="50%" align="left">
                            <%=ClwI18nUtil.priceFormat(request, miscCost, theOrder.getLocaleCd(), "")%>
                        </td>
                    </tr>
                    <tr>
                        <td width="50%" align="right">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.totalExcludingVOC:"/>&nbsp;</b>
                            </span>
                        </td>
                        <td width="50%" align="left">
                            <%=ClwI18nUtil.priceFormat(request, totalPrice.add(miscCost), theOrder.getLocaleCd(), "")%>
                        </td>
                    </tr>
                    </logic:present>
                </table>
                </td>
                <td valign="top">
                <table id="t2" width="100%" align="center" cellpadding="0" cellspacing="0">
                    <logic:notEqual name="theOrder" property="shipAddress1" value="">
                    <tr>
                        <td width="50%" align="right" valign="top">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.address:"/>&nbsp;</b>
                            </span>
                        </td>
                        <td width="50%" align="left">
                            <bean:write name="theOrder" property="shipShortDesc"/>
                            <br>
                            <bean:write name="theOrder" property="shipAddress1"/>
                            <br>
                            <bean:write name="theOrder" property="shipAddress2"/>
                            <br>
                            <bean:write name="theOrder" property="shipAddress3"/>
                            <br>
                            <bean:write name="theOrder" property="shipAddress4"/>
                            <br>
                            <bean:write name="theOrder" property="shipCity"/>
                            <br>
                            <bean:write name="theOrder" property="shipPostalCode"/>
                        </td>
                    </tr>
                    </logic:notEqual>
                </table>
                </td>
                <td valign="top">
                <table id="t3" width="100%" align="center" cellpadding="0" cellspacing="0">
                    <logic:notEqual name="theOrder" property="billAddress1" value="">
                    <tr>
                        <td width="50%" align="right" valign="top">
                            <span class="shopassetdetailtxt">
                                <b><app:storeMessage key="shop.orderStatus.text.address:"/>&nbsp;</b>
                            </span>
                        </td>
                        <td width="50%" align="left">
                            <bean:write name="theOrder" property="billShortDesc"/>
                            <br>
                            <bean:write name="theOrder" property="billAddress1"/>
                            <br>
                            <bean:write name="theOrder" property="billAddress2"/>
                            <br>
                            <bean:write name="theOrder" property="billAddress3"/>
                            <br>
                            <bean:write name="theOrder" property="billAddress4"/>
                            <br>
                            <bean:write name="theOrder" property="billCity"/>
                            <br>
                            <bean:write name="theOrder" property="billPostalCode"/>
                        </td>
                    </tr>
                    </logic:notEqual>
                </table>
                </td>
            </tr>
        </table>
        </logic:present>
        </logic:present>
    </td>
</tr>
</logic:notEqual>
</table>
