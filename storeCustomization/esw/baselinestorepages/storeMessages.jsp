<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
    String MAX_MESSAGES_DISPLAYED = "12";
    int MESSAGES_PER_SLIDER = 3;
    String formBeanName = request.getParameter(Constants.PARAMETER_FORM_BEAN_NAME);
    String messageURL = request.getParameter(Constants.PARAMETER_MESSAGE_URL);
%>
                    <h3>
                        <app:storeMessage key="message.label.messages" />
                    </h3>
                    <logic:notEmpty name="<%=formBeanName%>" property="messages">
                        <bean:size id="messageCount" name="<%=formBeanName%>" property="messages"/>
                        <%
                            if (messageCount > MESSAGES_PER_SLIDER) {
                        %>
                        <div class="sliderNav-pos">
                        <p class="sliderNav"></p>
                        </div>
                        <%
                            }
                        %>
                        <div class="slider">
                            <div class="slidingWrapper">
                                <div class="slidingItem">
                                <logic:iterate id="message" indexId="messageIndex" length="<%=MAX_MESSAGES_DISPLAYED%>"
                                    name="<%=formBeanName%>" property="messages" 
                                    type="com.cleanwise.service.api.value.StoreMessageView">
                                    <%
                                        if ((messageIndex > 0) && (messageIndex % MESSAGES_PER_SLIDER == 0)) {
                                    %>
                                    </div>
                                    <div class="slidingItem">
                                    <%
                                        }
                                    %>
                                    <%
                                        StringBuilder href = new StringBuilder(messageURL);
                                        href.append(message.getStoreMessageId());
                                        String messageTitle = message.getMessageTitle();
                                        String messageTitle1 = messageTitle.length() > 55 ? messageTitle.substring(0,55)+"..." : messageTitle;
                                    %>
                                    <h4>
                                        <logic:notEmpty name="message" property="messageBody">
                                            <html:link href="<%=href.toString()%>" styleClass="popUpMessage" title="<%=messageTitle%>">
                                                <%=messageTitle1%>
                                            </html:link>
                                        </logic:notEmpty>
                                        <logic:empty name="message" property="messageBody">
                                            <bean:write name="message" property="messageTitle"/>
                                        </logic:empty>
                                    </h4>
                                    <p class="credit">
                                        <app:storeMessage key="message.label.from" />: <bean:write name="message" property="messageAuthor"/>&nbsp;&nbsp;
                                        <span><%=ClwI18nUtil.formatDateInp(request, message.getPostedDate())%></span>
                                    </p>

                                    <hr />

                                </logic:iterate>
                                </div>
                            </div>
                        </div>
                    </logic:notEmpty>
                    <logic:empty name="<%=formBeanName%>" property="messages">
                        <p>
                            <app:storeMessage key="message.message.noMessages" />
                        </p>
                    </logic:empty>
                    