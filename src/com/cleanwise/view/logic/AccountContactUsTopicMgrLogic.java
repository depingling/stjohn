package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.forms.AccountContactUsTopicMgrForm;
import com.cleanwise.view.forms.StoreAccountMgrDetailForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class AccountContactUsTopicMgrLogic {

    public static ActionMessages init(HttpServletRequest request,
            AccountContactUsTopicMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        StoreAccountMgrDetailForm accountForm = (StoreAccountMgrDetailForm) session
                .getAttribute("ACCOUNT_DETAIL_FORM");
        String accountIdS = accountForm.getId();
        int accountId = 0;
        try {
            accountId = Integer.parseInt(accountIdS);
        } catch (NumberFormatException exc) {
            ae.add("error", new ActionMessage("error.systemError",
                    "Wrong account number format: " + accountIdS));
            return ae;
        }
        pForm.setAccountId(accountId);
        pForm.setAccountName(accountForm.getName());
        APIAccess factory = new APIAccess();
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        PropertyService ps = factory.getPropertyServiceAPI();
        PropertyDataVector contactUsTopics = ps.getProperties(null,
                new Integer(accountId),
                RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TOPIC);
        Collections.sort(contactUsTopics, new Comparator() {
            public int compare(Object o1, Object o2) {
                PropertyData pd1 = (PropertyData) o1;
                PropertyData pd2 = (PropertyData) o2;
                if (pd1 != null && Utility.isSet(pd1.getValue()) && 
                	pd2 != null && Utility.isSet(pd2.getValue())) {
                    return pd1.getValue().compareToIgnoreCase(pd2.getValue());
                }
                return 0;
            }
        });
        pForm.setTopics(contactUsTopics);
        pForm.setTopicToEdit(null);
        return ae;
    }

    public static ActionMessages addTopic(HttpServletRequest request,
            AccountContactUsTopicMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session
                .getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        PropertyService ps = factory.getPropertyServiceAPI();
        String topicName = pForm.getTopicName();
        if (Utility.isSet(topicName) == false) {
            ae.add("error", new ActionMessage("error.simpleGenericError",
                    "Empty topic name"));
            return ae;
        }
        PropertyDataVector contactUsTopics = ps.getProperties(null,
                new Integer(pForm.getAccountId()),
                RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TOPIC);
        for (Iterator iter = contactUsTopics.iterator(); iter.hasNext();) {
            PropertyData pD = (PropertyData) iter.next();
            String tn = pD.getValue();
            if (topicName.equalsIgnoreCase(tn)) {
                String mess = "Topic already exists: " + tn;
                ae.add("error", new ActionMessage("error.simpleGenericError",
                        mess));
                return ae;
            }
        }
        SessionTool st = new SessionTool(request);
        CleanwiseUser userData = st.getUserData();
        String userName = userData.getUser().getUserName();
        PropertyData propertyData = PropertyData.createValue();
        propertyData.setBusEntityId(pForm.getAccountId());
        propertyData
                .setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
        propertyData
                .setPropertyTypeCd(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TOPIC);
        propertyData
                .setShortDesc(RefCodeNames.PROPERTY_TYPE_CD.CONTACT_US_TOPIC);
        propertyData.setValue(topicName);
        propertyData.setLocaleCd(pForm.getLocaleCd());
        propertyData.setAddBy(userName);
        propertyData.setModBy(userName);
        ps.update(propertyData);
        pForm.setTopicName("");
        pForm.setLocaleCd("");
        return init(request, pForm);
    }

    public static ActionMessages editTopic(HttpServletRequest request,
            AccountContactUsTopicMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        String topicIdS = request.getParameter("topicToEditId");
        int topicId = Integer.parseInt(topicIdS);
        PropertyDataVector topicDV = pForm.getTopics();
        boolean wasFounded = false;
        for (Iterator iter = topicDV.iterator(); iter.hasNext();) {
            PropertyData pD = (PropertyData) iter.next();
            if (pD.getPropertyId() == topicId) {
                wasFounded = true;
                pForm.setTopicToEdit(pD);
                break;
            }
        }
        if (wasFounded == false) {
            String mess = "Topic not found: " + topicIdS;
            throw new Exception(mess);
        }
        return ae;
    }

    public static ActionMessages updateTopic(HttpServletRequest request,
            AccountContactUsTopicMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session
                .getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        PropertyData topicToEdit = pForm.getTopicToEdit();
        int topicId = topicToEdit.getPropertyId();
        String topicName = topicToEdit.getValue();
        if (Utility.isSet(topicName) == false) {
            ae.add("error", new ActionMessage("error.simpleGenericError",
                    "Empty topic name"));
            return ae;
        }
        PropertyDataVector topicDV = pForm.getTopics();
        for (Iterator iter = topicDV.iterator(); iter.hasNext();) {
            PropertyData pD = (PropertyData) iter.next();
            String tn = pD.getValue();
            if (topicName.equalsIgnoreCase(tn) && pD.getPropertyId() != topicId) {
                String mess = "Topic already exists: " + tn;
                ae.add("error", new ActionMessage("error.simpleGenericError",
                        mess));
                return ae;
            }
        }
        SessionTool st = new SessionTool(request);
        CleanwiseUser userData = st.getUserData();
        String userName = userData.getUser().getUserName();
        PropertyService ps = factory.getPropertyServiceAPI();
        topicToEdit.setModBy(userName);
        ps.update(topicToEdit);
        return init(request, pForm);
    }

    public static ActionMessages deleteTopic(HttpServletRequest request,
            AccountContactUsTopicMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        String sTopicId = request.getParameter("topicId");
        int topicId = 0;
        try {
            topicId = Integer.parseInt(sTopicId);
        } catch (NumberFormatException exc) {
            ae.add("error", new ActionMessage("error.systemError",
                    "Wrong account number format: " + sTopicId));
            return ae;
        }
        APIAccess factory = new APIAccess();
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        PropertyService ps = factory.getPropertyServiceAPI();
        ps.delete(topicId);
        return init(request, pForm);
    }
}
