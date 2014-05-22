<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ page import="com.cleanwise.view.logic.AnalyticReportLogic" %>

<%@ page import="java.util.HashMap" %>
<%
    String onChangeStore = "setAndSubmit('ANALYTIC_REPORT_FORM_ID','command','saveSelectedStoreDW');";
    String onChangeUser = "setAndSubmit('ANALYTIC_REPORT_FORM_ID','command','saveSelectedUserDW');";
    String submitAction = "action1";
    boolean isReportingManager = false;
    if (user.getUser().getUserRoleCd().contains("RepM^")){
      isReportingManager = true;
    }
%>

            <td>&nbsp;</td>
            <td>
                    <b><%=(label==null)?"Select Store:":label%></b>
            </td>
            <td>
                    <select name="<%=controlEl%>" onchange="<%=onChangeStore%>">
                    <%  HashMap controlFiltersMap = (HashMap)request.getSession().getAttribute("REPORT_CONTROL_FILTER_MAP");
                        Integer selectedStoreIdInt = null;
                        if (controlFiltersMap != null) {
                          Object elValue = controlFiltersMap.get(Constants.DW_STORE_FILTER_NAME);
                          if (elValue != null){
                            if ( elValue instanceof Integer ){
                              selectedStoreIdInt = (Integer)elValue;
                            }else{
                              selectedStoreIdInt= Integer.valueOf(elValue.toString());
                            }
                          }
                        }
                        String selectedStoreId = (selectedStoreIdInt != null) ? selectedStoreIdInt.toString() : "0";

                        BusEntityDataVector userStoresDV = user.getStores();

                        for(int j = 0; j < userStoresDV.size(); j++) {
                                BusEntityData beD = (BusEntityData) userStoresDV.get(j);
                                String shortDesc = beD.getShortDesc();
                                String storeIdStr = "" + beD.getBusEntityId();
	if(storeIdStr.equals("128584")) storeIdStr = "196842";
	if(storeIdStr.equals("168910")) storeIdStr = "196842";
                                if(shortDesc.equalsIgnoreCase("-Cleanwise.com")) {// JanPak Integration
                                    storeIdStr = ""+ 169080;  // Only for testing !  196842
                                }

                                if (selectedStoreId.equals(storeIdStr)) { %>
                                        <option value="<%=storeIdStr%>" selected="true"><%=shortDesc%></option>
                                <% } else {%>
                                        <option value="<%=storeIdStr%>"><%=shortDesc%></option>
                                <% }
                        }
                        String valS = request.getParameter(controlEl);
                        if (Utility.isSet(valS)) {
                          Integer filterVal =Integer.valueOf(valS);
			  AnalyticReportLogic.setControlFilter(request,  theForm,  Constants.DW_STORE_FILTER_NAME, filterVal);
                        } else{
                          AnalyticReportLogic.changeDwStoreDimId(request,  theForm);
                        }
                     %>
                  </select>
            <html:hidden name="ANALYTIC_REPORT_FORM" property="action1" value="0"/>
            <%if(mandatoryFl) { %> &nbsp;<span class="reqind">*</span> <% } %>

       <!-- User dropdown list-->
    <% if (isReportingManager && !scheduleFl) {%>
      <%for(int jj=0; jj < grcVV.size(); jj++) {
          GenericReportControlView grc1 = (GenericReportControlView) grcVV.get(jj);
          String name1 = grc1.getName();
          if("DW_USER_SELECT".equalsIgnoreCase(name1) || "DW_USER_SELECT_OPT".equalsIgnoreCase(name1)) {
              String label1 = grc1.getLabel();
              if(label1!=null && label1.length()==0){
                  label1=null;
              }
              String controlEl1 = "genericControlValue["+jj+"]";
       %>
              <b><%=(label==null)?"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select User:&nbsp;":label1%></b>
              <select name="<%=controlEl1%>" onchange="<%=onChangeUser%>">

              <%  Integer selectedUserIdInt = null;
                  if (controlFiltersMap != null) {
                    Object elValue= controlFiltersMap.get("DW_USER_SELECT");
                    if (elValue != null){
                      if (elValue instanceof UserData ){
                        selectedUserIdInt = new Integer(((UserData)elValue).getUserId());
                      }else{
                        selectedUserIdInt = Integer.valueOf(elValue.toString());
                      }
                    }
                  }
                  String selectedUserId = (selectedUserIdInt != null) ? selectedUserIdInt.toString() : String.valueOf(user.getUserId());

                  UserDataVector usersDV = theForm.getStoreUsers();
                  if (usersDV == null) {
                    usersDV = new UserDataVector();
                  }
                  for(int j = 0; j < usersDV.size(); j++) {
                          UserData userD = (UserData) usersDV.get(j);
                          String userName = userD.getUserName();
                          String userIdStr = "" + userD.getUserId();

                          if (selectedUserId.equals(userIdStr)) { %>
                                  <option value="<%=userIdStr%>" selected="true"><%=userName%></option>
                          <% } else {%>
                                  <option value="<%=userIdStr%>"><%=userName%></option>
                          <% }
                  } %>
              </select>
      <%   // break; %>
      <%   } %>
      <% }%>

     <% } %>


            </td>

