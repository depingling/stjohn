/**
 * Title: LocateAction

 
 * Description: This is the Struts Action class handling the ESW locate functionality.
 */

package com.espendwise.view.actions.esw;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.cleanwise.service.api.dto.LocationSearchDto;
import com.cleanwise.service.api.util.SessionDataUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.forms.LocateReportAccountForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.logic.LocateReportAccountLogic;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;
import com.espendwise.view.forms.esw.LocateForm;
import com.espendwise.view.logic.esw.DashboardLogic;
import com.espendwise.view.logic.esw.LocateLogic;

/**
 * Implementation of <code>Action</code> that handles log on functionality.
 */
public final class LocateAction extends EswAction {
    private static final Logger log = Logger.getLogger(LocateAction.class);

    // constants to hold the various action mappings that can be returned by
    // this action class.
    private static final String MAPPING_SPECIFY_LOCATIONS = "locateSpecifyLocations";
    private static final String MAPPING_SPECIFY_ACCOUNTS = "locateSpecifyAccounts";

    /**
     * Process the specified HTTP request, and create the corresponding HTTP
     * response (or forward to another web component that will create it).
     * Return an <code>ActionForward</code> instance describing where and how
     * control should be forwarded, or <code>null</code> if the response has
     * already been completed.
     * 
     * @param mapping
     *            the ActionMapping used to select this instance.
     * @param form
     *            the ActionForm containing the data.
     * @param request
     *            the HTTP request we are processing.
     * @param response
     *            the HTTP response we are creating.
     * @return an ActionForward describing the component that should receive
     *         control.
     */
    public ActionForward performAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        if (!new SessionTool(request).checkSession()) {
            return mapping.findForward(Constants.GLOBAL_FORWARD_ESW_LOGON);
        }
        ActionForward returnValue = null;
        LocateForm theForm = (LocateForm) form;

        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        HttpSession session = request.getSession();
        CleanwiseUser user = (CleanwiseUser) session
                .getAttribute(Constants.APP_USER);
        String operation = theForm.getOperation();
        if (!Utility.isSet(operation)) {
            operation = sessionDataUtil.getPreviousReportingAction();
        }

        if (!Utility.isSet(operation)) {
            operation = Constants.PARAMETER_OPERATION_VALUE_SHOW_BUDGETS_REPORTS;
        }

        if (Utility.isSet(operation)) {
            operation = operation.trim();
        }
        int[] selected = LocateLogic.getSelectedIds(
                request.getParameter("ids"), ",");
        if (selected != null && selected.length > 0) {
            theForm.setSelected(selected);
        }
        if (Constants.PARAMETER_OPERATION_SPECIFY_LOCATIONS
                .equalsIgnoreCase(operation)) {
            returnValue = handleSpecifyLocationsRequest(user, request,
                    response, theForm, mapping, false);
        } else if (Constants.PARAMETER_OPERATION_SPECIFY_LOCATIONS_SEARCH
                .equalsIgnoreCase(operation)) {
            returnValue = handleSpecifyLocationsRequest(user, request,
                    response, theForm, mapping, true);
        } else if (Constants.PARAMETER_OPERATION_SPECIFY_ACCOUNTS
                .equalsIgnoreCase(operation)) {
            returnValue = handleSpecifyAccountsRequest(request, response,
                    theForm, mapping, true);
        } else if (Constants.PARAMETER_OPERATION_SPECIFY_ACCOUNTS_SEARCH
                .equalsIgnoreCase(operation)) {
            returnValue = handleSpecifyAccountsRequest(request, response,
                    theForm, mapping, true);
        } else if (Constants.PARAMETER_OPERATION_VALUE_SORT_LOCATIONS
                .equalsIgnoreCase(operation)) {
            returnValue = handleSortLocationsRequest(request, response,
                    theForm, mapping);
        } else {
            returnValue = handleUnknownOperation(request, response, theForm,
                    mapping);
        }
        return returnValue;
    }

    private ActionForward handleSpecifyLocationsRequest(CleanwiseUser user,
            HttpServletRequest request, HttpServletResponse response,
            LocateForm form, ActionMapping mapping, boolean runSearch) {
        ActionErrors errors = new ActionErrors();
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        //STJ-5677: Need to preserve each filter criteria for every instance of 'Specify Location'
        String pageForSpecifyLocation = form.getPageForSpecifyLocation();
        if(!Utility.isSet(pageForSpecifyLocation)) {
        	pageForSpecifyLocation = sessionDataUtil.getPageForSpecifyLocation();
        } else {
        	sessionDataUtil.setPageForSpecifyLocation(pageForSpecifyLocation);
        }
        
        Map<String,LocationSearchDto> locationSearchDtoMap = sessionDataUtil.getLocationSearchDtoMap();
        if (runSearch) {
            LocationSearchDto locationSearchInfo = form.getLocationSearchInfo();
            if (Utility.isSet(locationSearchInfo.getSortField()) == false) {
                locationSearchInfo
                        .setSortField(Constants.LOCATION_SORT_FIELD_LAST_VISIT);
            }
            if (Utility.isSet(locationSearchInfo.getSortOrder()) == false) {
                locationSearchInfo
                        .setSortOrder(Constants.LOCATION_SORT_ORDER_DESCENDING);
            }
            locationSearchInfo.setSearchInactive(form.isShowInactiveFl());
            errors = DashboardLogic.performLocationSearch(request,
                    locationSearchInfo);
            if (errors != null && !errors.isEmpty()) {
                saveErrors(request, errors);
            } else {
            	locationSearchDtoMap.put(pageForSpecifyLocation, locationSearchInfo);
                generateLocationCountMessage(request, locationSearchInfo);
            }
        } else {
        	
            LocationSearchDto previousSearch = locationSearchDtoMap.get(pageForSpecifyLocation);
            generateLocationCountMessage(request, previousSearch);
            form.setLocationSearchInfo(previousSearch);
        }
        return mapping.findForward(MAPPING_SPECIFY_LOCATIONS);
    }

    private ActionForward handleSortLocationsRequest(
            HttpServletRequest request, HttpServletResponse response,
            LocateForm form, ActionMapping mapping) {
        LocationSearchDto locationSortInfo = form.getLocationSearchInfo();
        ActionErrors errors = DashboardLogic.performLocationSearch(request,
                locationSortInfo);
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
        } else {
            SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
          //STJ-5677: Need to preserve each filter criteria for every instance of 'Specify Location'
            String pageForSpecifyLocation = form.getPageForSpecifyLocation();
            if(!Utility.isSet(pageForSpecifyLocation)) {
            	pageForSpecifyLocation = sessionDataUtil.getPageForSpecifyLocation();
            } else {
            	sessionDataUtil.setPageForSpecifyLocation(pageForSpecifyLocation);
            }
            Map<String,LocationSearchDto> locationSearchDtoMap = sessionDataUtil.getLocationSearchDtoMap();
            locationSearchDtoMap.put(pageForSpecifyLocation,locationSortInfo);
            
            generateLocationCountMessage(request, locationSortInfo);
        }
        return mapping.findForward(MAPPING_SPECIFY_LOCATIONS);
    }

    private void generateLocationCountMessage(HttpServletRequest request,
            LocationSearchDto dto) {
        int locationCount = dto.getMatchingLocations().size();
        String message = null;
        if (locationCount == 0) {
            message = ClwI18nUtil.getMessage(request,
                    "location.search.noResults", null);
        } else if (locationCount >= Constants.LOCATION_SEARCH_RESULTS_MAX_DISPLAY) {
            Object[] insertionStrings = new Object[1];
            insertionStrings[0] = Integer
                    .toString(Constants.LOCATION_SEARCH_RESULTS_MAX_DISPLAY);
            message = ClwI18nUtil.getMessage(request,
                    "location.search.maximumResultsMet", insertionStrings);
        }
        if (Utility.isSet(message)) {
            ActionMessages messages = new ActionMessages();
            messages.add("message", new ActionMessage("message.simpleMessage",
                    message));
            saveMessages(request, messages);
        }
    }

    private ActionForward handleSpecifyAccountsRequest(
            HttpServletRequest request, HttpServletResponse response,
            LocateForm theForm, ActionMapping mapping, boolean filter) {
        ActionErrors errors = new ActionErrors();
        SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
        LocateReportAccountForm form = theForm.getLocateReportAccountForm();
        if (filter) {
            try {
                errors = LocateReportAccountLogic.search(request, form);
                LocateLogic.sortAccounts(theForm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (errors != null && !errors.isEmpty()) {
            saveErrors(request, errors);
        }
        return mapping.findForward(MAPPING_SPECIFY_ACCOUNTS);
    }
}