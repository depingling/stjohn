
package com.cleanwise.view.logic;

import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.math.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.OrderOpSearchForm;
import com.cleanwise.view.forms.OrderOpDetailForm;
import com.cleanwise.view.forms.OrderOpItemDetailForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.view.forms.ShareReportForm;
import java.text.SimpleDateFormat;


/**
 * <code>ShareReportLogic</code> implements the logic needed to
 * assign prepared report to users and user groups.
 *
 * @author Yuriy Kupershmidt
 */
public class ShareReportLogic {
    
    /**
     * <code>init</code> method.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param form an <code>ActionForm</code> value
     * @exception Exception if an error occurs
     */
    public static void init(HttpServletRequest request,
    ShareReportForm pForm)
    throws Exception {
      //Generic report categories
      try{
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      User userEjb = factory.getUserAPI();
      Group groupEjb = factory.getGroupAPI();
      String reportResultIdS = request.getParameter("id");
      int reportResultId = 0;
      try {
        reportResultId = Integer.parseInt(reportResultIdS);
      } catch(Exception exc) {}
      if(reportResultId<=0) {
        String errorMess = "Wrong report result id: "+reportResultIdS;
        throw new Exception (errorMess);         
      }
      
      PreparedReportView report = reportEjb.getPreparedReport(reportResultId);
      pForm.setReport(report);
      //Assigned users
      IdVector assocUserIdV = 
        reportEjb.getReportResultAssoc(reportResultId, RefCodeNames.REPORT_RESULT_ASSOC_CD.USER);
      UserDataVector assocUserDV = userEjb.getUsersCollection(assocUserIdV);
      pForm.setUsers(assocUserDV);
      
      //Assigned groups
      IdVector assocGroupIdV = 
        reportEjb.getReportResultAssoc(reportResultId, RefCodeNames.REPORT_RESULT_ASSOC_CD.GROUP);
      GroupDataVector groupDV = new GroupDataVector();
      for(int ii=0; ii<assocGroupIdV.size(); ii++) {
        Integer groupIdI = (Integer) assocGroupIdV.get(ii);
        try {
          GroupData groupD = groupEjb.getGroupDetail(groupIdI.intValue()); 
          groupDV.add(groupD);
        }catch(Exception exc) {
          String mess = "ShareReportLogic. GroupBean.getGroupDetail exception. Group id = "+groupIdI;
          exc.printStackTrace(); //no need to do anything
        }
      }
      pForm.setGroups(groupDV);

      sortUsersByFirstLastName(request,pForm);
      sortGroupsByShortDesc(request,pForm); 
      
    } catch(Exception exc) {
      exc.printStackTrace();
      throw exc;
    }
    }
    
    //
    public static void sortUsersByFirstLastName(HttpServletRequest request,
    ShareReportForm pForm)
    throws Exception {
      UserDataVector uDV = pForm.getUsers();
      if(uDV==null || uDV.size()<=1) return;
      
      Object[] users =  uDV.toArray();
      for(int ii=0; ii<users.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<users.length-ii-1; jj++) {
          UserData uD1 = (UserData) users[jj];
          UserData uD2 = (UserData) users[jj+1];
          String n1 = uD1.getFirstName()+uD1.getLastName();
          String n2 = uD2.getFirstName()+uD2.getLastName();
          if(n1==null) n1 = "";
          if(n2==null) n2 = "";
          int com = n1.compareToIgnoreCase(n2);
          if(com>0) {
             users[jj] = uD2;
             users[jj+1] = uD1;
             exitFl = false;
          }
        }
        if(exitFl) break;
      }
      uDV = new UserDataVector();
      for(int ii=0; ii<users.length; ii++) {
        uDV.add((UserData) users[ii]);
      }
      pForm.setUsers(uDV);
    }

    //
    public static void sortGroupsByShortDesc(HttpServletRequest request,
    ShareReportForm pForm)
    throws Exception {
      GroupDataVector gDV = pForm.getGroups();
      if(gDV==null || gDV.size()<=1) return;
      
      Object[] groups =  gDV.toArray();
      for(int ii=0; ii<groups.length-1; ii++) {
        boolean exitFl = true;
        for(int jj=0; jj<groups.length-ii-1; jj++) {
          GroupData gD1 = (GroupData) groups[jj];
          GroupData gD2 = (GroupData) groups[jj+1];
          String n1 = gD1.getShortDesc();
          String n2 = gD2.getShortDesc();
          if(n1==null) n1 = "";
          if(n2==null) n2 = "";
          int com = n1.compareToIgnoreCase(n2);
          if(com>0) {
             groups[jj] = gD2;
             groups[jj+1] = gD1;
             exitFl = false;
          }
        }
        if(exitFl) break;
      }
      gDV = new GroupDataVector();
      for(int ii=0; ii<groups.length; ii++) {
        gDV.add((GroupData) groups[ii]);
      }
      pForm.setGroups(gDV);
    }
    
    //--------------------------------------------------------------------------
    public static void addGroup(HttpServletRequest request,
    ShareReportForm pForm)
    throws Exception {
      //Generic report categories
      try{
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      String userName = appUser.getUser().getUserName();
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      Group groupEjb = factory.getGroupAPI();
      String groupToAddIdS = pForm.getGroupToAdd();
      pForm.setGroupToAdd("");
      int groupToAddId = 0;
      try {
        groupToAddId = Integer.parseInt(groupToAddIdS);
      } catch(Exception exc) {}
      if(groupToAddId<=0) {
        String errorMess = "Wrong group id: "+groupToAddIdS;
        throw new Exception (errorMess);         
      }
      IdVector groupIdV = new IdVector();
      groupIdV.add(new Integer(groupToAddId));
      int reportResultId = pForm.getReport().getReportResultId();
      reportEjb.updateReportResultAssoc (reportResultId, 
                                       groupIdV, 
                                       null,
                                       RefCodeNames.REPORT_RESULT_ASSOC_CD.GROUP,
                                       userName);
      
       GroupData groupD = groupEjb.getGroupDetail(groupToAddId);
       GroupDataVector groupDV = pForm.getGroups();
       if(groupDV==null) groupDV = new GroupDataVector();
       boolean foundFl = false;
       for(int ii=0; ii<groupDV.size(); ii++) {
         GroupData gD = (GroupData) groupDV.get(ii);
         if(gD.getGroupId()==groupD.getGroupId()) {
           foundFl = true;
           break;
         }
         
       }
       if(!foundFl) {
          groupDV.add(0, groupD);
       }
       
       pForm.setGroups(groupDV);
    } catch(Exception exc) {
      exc.printStackTrace();
      throw exc;
    }
    }

    //--------------------------------------------------------------------------
    public static void delGroup(HttpServletRequest request,
    ShareReportForm pForm)
    throws Exception {
      //Generic report categories
      try{
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      String userName = appUser.getUser().getUserName();
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      Group groupEjb = factory.getGroupAPI();

      String[] groupToDelIdSA = pForm.getGroupSelected();
      int[] groupToDelIdA = new int[groupToDelIdSA.length];
      for(int ii=0; ii<groupToDelIdSA.length; ii++) {
        groupToDelIdA[ii] = Integer.parseInt(groupToDelIdSA[ii]);
      }

      GroupDataVector groupDV = pForm.getGroups();
      IdVector groupToDelIdV = new IdVector();
      for(int ii=0; ii<groupDV.size(); ii++) {
        GroupData gD = (GroupData) groupDV.get(ii);
        int id = gD.getGroupId();
        for(int jj=0; jj<groupToDelIdA.length; jj++) {
          if(groupToDelIdA[jj]==id) {
            groupToDelIdV.add(new Integer(id));
            groupDV.remove(ii);
            break;
          }
        }
      }
      int reportResultId = pForm.getReport().getReportResultId();
      reportEjb.updateReportResultAssoc (reportResultId, 
                                       null, 
                                       groupToDelIdV,
                                       RefCodeNames.REPORT_RESULT_ASSOC_CD.GROUP,
                                       userName);
      
       
       pForm.setGroups(groupDV);
    } catch(Exception exc) {
      exc.printStackTrace();
      throw exc;
    }
    }

//--------------------------------------------------------------------------
    public static void addUser(HttpServletRequest request,
    ShareReportForm pForm)
    throws Exception {
      //Generic report categories
      try{
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      String userName = appUser.getUser().getUserName();
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      User userEjb = factory.getUserAPI();
      String userToAddIdS = pForm.getUserToAdd();
      pForm.setUserToAdd("");
      int userToAddId = 0;
      try {
        userToAddId = Integer.parseInt(userToAddIdS);
      } catch(Exception exc) {}
      if(userToAddId<=0) {
        String errorMess = "Wrong user id: "+userToAddIdS;
        throw new Exception (errorMess);         
      }
      IdVector userIdV = new IdVector();
      userIdV.add(new Integer(userToAddId));
      int reportResultId = pForm.getReport().getReportResultId();
      reportEjb.updateReportResultAssoc (reportResultId, 
                                       userIdV, 
                                       null,
                                       RefCodeNames.REPORT_RESULT_ASSOC_CD.USER,
                                       userName);
      
       UserData userD = userEjb.getUser(userToAddId);
       UserDataVector userDV = pForm.getUsers();
       if(userDV==null) userDV = new UserDataVector();
       boolean foundFl = false;
       for(int ii=0; ii<userDV.size(); ii++) {
         UserData uD = (UserData) userDV.get(ii);
         if(uD.getUserId()==userD.getUserId()) {
           foundFl = true;
           break;
         }
         
       }
       if(!foundFl) {
          userDV.add(0, userD);
       }
       
       pForm.setUsers(userDV);
    } catch(Exception exc) {
      exc.printStackTrace();
      throw exc;
    }
    }

    //--------------------------------------------------------------------------
    public static void delUser(HttpServletRequest request,
    ShareReportForm pForm)
    throws Exception {
      //Generic report categories
      try{
      HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      String userName = appUser.getUser().getUserName();
      APIAccess factory = new APIAccess();
      Report reportEjb = factory.getReportAPI();
      User userEjb = factory.getUserAPI();

      String[] userToDelIdSA = pForm.getUserSelected();
      int[] userToDelIdA = new int[userToDelIdSA.length];
      for(int ii=0; ii<userToDelIdSA.length; ii++) {
        userToDelIdA[ii] = Integer.parseInt(userToDelIdSA[ii]);
      }

      UserDataVector userDV = pForm.getUsers();
      IdVector userToDelIdV = new IdVector();
      for(int ii=0; ii<userDV.size(); ii++) {
        UserData uD = (UserData) userDV.get(ii);
        int id = uD.getUserId();
        for(int jj=0; jj<userToDelIdA.length; jj++) {
          if(userToDelIdA[jj]==id) {
            userToDelIdV.add(new Integer(id));
            userDV.remove(ii);
            break;
          }
        }
      }
      int reportResultId = pForm.getReport().getReportResultId();
      reportEjb.updateReportResultAssoc (reportResultId, 
                                       null, 
                                       userToDelIdV,
                                       RefCodeNames.REPORT_RESULT_ASSOC_CD.USER,
                                       userName);
      
       
       pForm.setUsers(userDV);
    } catch(Exception exc) {
      exc.printStackTrace();
      throw exc;
    }
    }
}
    
    
    
    
