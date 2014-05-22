package com.cleanwise.service.apps.dataexchange;

import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.APIServiceAccessException;

import com.cleanwise.service.apps.loaders.TabFileParser;

import java.rmi.RemoteException;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InboundKranzUserTxt extends InboundUserTxt {
    //private final static String MEMBER_DELIMITER = "*";
    private final String USER_LOADER = "Kranz User Loader";

    protected Logger log = Logger.getLogger(this.getClass());


    /**
   * Called when the object has successfully been parsed
   */
    protected void processParsedObject(Object pParsedObject)
    throws Exception
    {
        if (pParsedObject instanceof NscUsView){
            //Remove nulls and trim
             NscUsView nscUsVw = (NscUsView) pParsedObject;
             String contactName = Utility.strNN(nscUsVw.getContactName()).trim();
             nscUsVw.setContactName(contactName);
//             String catalogName = Utility.strNN(nscUsVw.getCatalogName()).trim();
//             nscUsVw.setCatalogName(catalogName);
             String customerNumber = Utility.strNN(nscUsVw.getCustomerNumber()).trim();
             nscUsVw.setCustomerNumber(customerNumber);
             String emailAddress = Utility.strNN(nscUsVw.getEmailAddress()).trim();
             nscUsVw.setEmailAddress(emailAddress);
             String locationNumber = Utility.strNN(nscUsVw.getLocationNumber()).trim();
             nscUsVw.setLocationNumber(locationNumber);
//             String memberNumber = Utility.strNN(nscUsVw.getMemberNumber()).trim();
//             nscUsVw.setMemberNumber(memberNumber);
             String userName = Utility.strNN(nscUsVw.getUserName()).trim();
             nscUsVw.setUserName(userName);
             String userPasswordCoded = PasswordUtil.getHash(userName, locationNumber);
             nscUsVw.setPassword(userPasswordCoded);

             String firstName;
             String lastName;
             int index;
             if ((index = contactName.lastIndexOf(' ')) > -1) {
                firstName = contactName.substring(0, index).trim();
                lastName = contactName.substring(index + 1).trim();
             } else {
                firstName = contactName;
                lastName = "N/A";
             }
             nscUsVw.setFirstName(firstName);
             nscUsVw.setLastName(lastName);
             nscUsers.add(nscUsVw);

        } else {
            throw new Exception("Found object of wrong type: "+pParsedObject.getClass().getName());
        }
    }

    protected void doPostProcessing()
    throws Exception
    {
        HashMap custMajHM = new HashMap();
        HashMap userNameHM = new HashMap();
        ArrayList errorAL = new ArrayList();
        int lineNum = 0;
        log.info("InboundKranzUserTxt IIIIIIIIIIIIII Start Vaildation Proceess");
        for(Iterator iter=nscUsers.iterator(); iter.hasNext();) {
            NscUsView nscUsVw = (NscUsView) iter.next();
            lineNum++;

            String contactName = Utility.strNN(nscUsVw.getContactName());
            if(contactName.length()==0) {
                String errorMess = "Empty Contact Name. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
//            String catalogName = Utility.strNN(nscUsVw.getCatalogName();
//            if(catalogName.length()==0) {
//                String errorMess = "Empty Catalog Name. Line num: "+lineNum;
//                errorAL.add(errorMess);
//            }
            String customerNumber = Utility.strNN(nscUsVw.getCustomerNumber());
            if(customerNumber.length()==0) {
                String errorMess = "Empty Customer Number. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            String emailAddress = Utility.strNN(nscUsVw.getEmailAddress());
            if(emailAddress.length()==0) {
                String errorMess = "Empty Email Address. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            if(emailAddress.length()>4000) {
                String errorMess = "Email Address is longer than 4000 characters: "+emailAddress.length()+"  Line num: "+lineNum;
                errorAL.add(errorMess);
            }
            /*if (Utility.isSet(emailAddress) && !Utility.isValidEmailAddress(emailAddress)) {
                String errorMess = "Email Address has incorrect format: '"
                        + emailAddress + "'  Line num: " + lineNum;
                errorAL.add(errorMess);
            }*/
            String locationNumber = Utility.strNN(nscUsVw.getLocationNumber());
            if(locationNumber.length()==0) {
                String errorMess = "Empty Location Number. Line num: "+lineNum;
                errorAL.add(errorMess);
            }
//            String status = Utility.strNN(nscUsVw.getUserStatus());
//            if(status.length()==0) {
//                String errorMess = "Empty Active/Inactive status. Line num: "+lineNum;
//                errorAL.add(errorMess);
//            }
            String userName = Utility.strNN(nscUsVw.getUserName());
            if(userName.length()==0) {
                String errorMess = "Empty User Name (id). Line num: "+lineNum;
                errorAL.add(errorMess);
            }

            HashSet siteNumHS = (HashSet) custMajHM.get(customerNumber);
            if(siteNumHS==null) {
                siteNumHS = new HashSet();
                siteNumHS.add(locationNumber);
                custMajHM.put(customerNumber,siteNumHS);
            } else {
                if(!siteNumHS.contains(locationNumber)) {
                    siteNumHS.add(locationNumber);
                }
            }
            if(!userNameHM.containsKey(userName)) {
                userNameHM.put(userName,nscUsVw);
            } else {
                NscUsView nuVw = (NscUsView) userNameHM.get(userName);
                if(!nuVw.getPassword().equals(nscUsVw.getPassword())) {
                    String errorMess = "The same user name used with different passwords. User Name: "+userName;
                    errorAL.add(errorMess);
                }
                if(!nuVw.getContactName().equals(nscUsVw.getContactName())) {
                    String errorMess = "The same user name used with different contact names. User Name: "+userName;
                    errorAL.add(errorMess);
                }
                if(!nuVw.getEmailAddress().equals(nscUsVw.getEmailAddress())) {
                    String errorMess = "The same user name used with different email addresses. User Name: "+userName;
                    errorAL.add(errorMess);
                }
            }
        }

        if(errorAL.size()>0) {
            String errorMess = makeErrorStr(errorAL);
            throw new Exception(errorMess);
        }

        //Save users
        List errors = userEjb.saveNscUsers(nscUsers, storeId, distributorId, false, "Kranz User Loader");
        if(errors.size()>0) {
            String errorMess = makeErrorStr(errors);
            throw new Exception(errorMess);
        }

    }
    protected String getModBy() {
     return USER_LOADER;
    }


}
