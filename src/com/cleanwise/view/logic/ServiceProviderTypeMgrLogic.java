package com.cleanwise.view.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.ServiceProviderType;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.BusEntityDataVector;

import com.cleanwise.view.forms.ServiceProviderTypeMgrForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ServiceProviderTypeMgrLogic {

    public static ActionMessages init(HttpServletRequest request,
            ServiceProviderTypeMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();

        APIAccess factory = new APIAccess();
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int storeId = appUser.getUserStore().getStoreId();
        pForm.setStoreId(storeId);
        
        ServiceProviderType serviceProviderTypeEjb = factory.getServiceProviderTypeAPI();
        BusEntityDataVector serviceProviderTypes = serviceProviderTypeEjb.getServiceProviderTypesForStore(storeId, false);
        
        Collections.sort(serviceProviderTypes, new Comparator() {
            public int compare(Object o1, Object o2) {
                BusEntityData be1 = (BusEntityData) o1;
                BusEntityData be2 = (BusEntityData) o2;
                if (be1 != null && be2 != null) {
                    return be1.getShortDesc().compareToIgnoreCase(be2.getShortDesc());
                }
                return 0;
            }
        });
        pForm.setServiceProviderTypes(serviceProviderTypes);
        pForm.setServiceProviderTypeToEdit(null);
        return ae;
    }

    public static ActionMessages addServiceProviderType(HttpServletRequest request,
            ServiceProviderTypeMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session
                .getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        ServiceProviderType serviceProviderTypeEjb = factory.getServiceProviderTypeAPI();
        String serviceProviderTypeName = pForm.getServiceProviderTypeName();
        int storeId = pForm.getStoreId();
        
        if (Utility.isSet(serviceProviderTypeName) == false) {
            ae.add("error", new ActionMessage("error.simpleGenericError",
                    "Empty Service Provider Type name"));
            return ae;
        }

        BusEntityDataVector serviceProviderTypes = serviceProviderTypeEjb.getServiceProviderTypesForStore(pForm.getStoreId(), false);
        
        for (Iterator iter = serviceProviderTypes.iterator(); iter.hasNext();) {
            BusEntityData beD = (BusEntityData) iter.next();
            String shortDesc = beD.getShortDesc();
            if (serviceProviderTypeName.equals(shortDesc)) {
                String mess = "Service Provider Type already exists: " + shortDesc;
                ae.add("error", new ActionMessage("error.simpleGenericError",
                        mess));
                return ae;
            }
        }
        SessionTool st = new SessionTool(request);
        CleanwiseUser userData = st.getUserData();
        String userName = userData.getUser().getUserName();
        String userLocale = userData.getUser().getPrefLocaleCd();
        
        BusEntityData sptData = BusEntityData.createValue();
        String serviceProviderName = pForm.getServiceProviderTypeName();
        sptData.setShortDesc(serviceProviderName);
        sptData.setLongDesc(serviceProviderName);
        sptData.setBusEntityStatusCd(RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
        sptData.setBusEntityTypeCd(RefCodeNames.BUS_ENTITY_TYPE_CD.SERVICE_PROVIDER_TYPE);
        sptData.setWorkflowRoleCd(RefCodeNames.WORKFLOW_ROLE_CD.UNKNOWN);
        sptData.setLocaleCd(userLocale);
        sptData.setAddBy(userName);
        sptData.setModBy(userName);
        serviceProviderTypeEjb.addServiceProviderType(sptData, storeId);
        
        pForm.setServiceProviderTypeName("");
        return init(request, pForm);
    }

    public static ActionMessages editServiceProviderType(HttpServletRequest request,
            ServiceProviderTypeMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        String serviceProviderTypeId = request.getParameter("serviceProviderTypeToEditId");
        int sptId = Integer.parseInt(serviceProviderTypeId);
        BusEntityDataVector serviceProviderTypeDV = pForm.getServiceProviderTypes();
        
        boolean wasFounded = false;
        for (Iterator iter = serviceProviderTypeDV.iterator(); iter.hasNext();) {
            BusEntityData beD = (BusEntityData) iter.next();
            if (beD.getBusEntityId() == sptId) {
                pForm.setServiceProviderTypeToEdit(beD);
                wasFounded = true;
                break;
            }
        }
        if (wasFounded == false) {
            String mess = "Service Provider Type not found: " + serviceProviderTypeId;
            throw new Exception(mess);
        }
        return ae;
    }

    public static ActionMessages updateServiceProviderType(HttpServletRequest request,
            ServiceProviderTypeMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
        
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        BusEntityData serviceProviderTypeToEdit = pForm.getServiceProviderTypeToEdit();
        int serviceProviderTypeId = serviceProviderTypeToEdit.getBusEntityId();
        String serviceProviderTypeName = serviceProviderTypeToEdit.getShortDesc();
        if (Utility.isSet(serviceProviderTypeName) == false) {
            ae.add("error", new ActionMessage("error.simpleGenericError",
                    "Empty Service Provider Type name"));
            return ae;
        }
        BusEntityDataVector serviceProviderTypeDV = pForm.getServiceProviderTypes();
        for (Iterator iter = serviceProviderTypeDV.iterator(); iter.hasNext();) {
            BusEntityData beD = (BusEntityData) iter.next();
            String sptName = beD.getShortDesc(); 
            if (serviceProviderTypeName.equals(sptName) && serviceProviderTypeId != beD.getBusEntityId()) {
                String mess = "Service Provider Type already exists: " + sptName;
                ae.add("error", new ActionMessage("error.simpleGenericError",
                        mess));
                return ae;
            }
        }
        SessionTool st = new SessionTool(request);
        CleanwiseUser userData = st.getUserData();
        String userName = userData.getUser().getUserName();
        ServiceProviderType serviceProviderTypeEjb = factory.getServiceProviderTypeAPI();
        serviceProviderTypeToEdit.setModBy(userName);
        serviceProviderTypeEjb.updateServiceProviderType(serviceProviderTypeToEdit, pForm.getStoreId());
        
        return init(request, pForm);
    }

    public static ActionMessages deleteServiceProviderType(HttpServletRequest request,
            ServiceProviderTypeMgrForm pForm) throws Exception {
        ActionMessages ae = new ActionMessages();
        String strServiceProviderTypeId = request.getParameter("serviceProviderTypeId");
        int serviceProviderTypeId = 0;
        try {
            serviceProviderTypeId = Integer.parseInt(strServiceProviderTypeId);
        } catch (NumberFormatException exc) {
            ae.add("error", new ActionMessage("error.systemError",
                    "Wrong Service Provider Type number format: " + serviceProviderTypeId));
            return ae;
        }
        APIAccess factory = new APIAccess();
        if (null == factory) {
            throw new Exception("No APIAccess.");
        }
        ServiceProviderType serviceProviderTypeEjb = factory.getServiceProviderTypeAPI();
        serviceProviderTypeEjb.removeServiceProviderType(serviceProviderTypeId);
        return init(request, pForm);
    }
}
