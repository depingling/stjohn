package com.espendwise.view.taglibs.esw;

import java.io.IOException;
import java.io.Writer;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

public class SpecifyLocationsTag extends TagSupport {
    private final static String LAYOUT_H = "H";
    private static long randIdCounter;

    private String layout;
    private boolean useSelect;
    private String selectName;
    private String locationSelected;
    private String locationIds;
    private String hiddenName;
    //STJ-5677: Preserve location search criteria and list of locations per each functionality.
    //Variable to hold the parent page location of 'Specify Location' option.
    private String pageForSpecifyLocation;

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public boolean isUseSelect() {
        return useSelect;
    }

    public void setUseSelect(boolean useSelect) {
        this.useSelect = useSelect;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    /**
	 * @return the locationSelected
	 */
	public final String getLocationSelected() {
		return locationSelected;
	}

	/**
	 * @param locationSelected the locationSelected to set
	 */
	public final void setLocationSelected(String locationSelected) {
		this.locationSelected = locationSelected;
	}

	/**
	 * @return the locationIds
	 */
	public final String getLocationIds() {
		return locationIds;
	}

	/**
	 * @param locationIds the locationIds to set
	 */
	public final void setLocationIds(String locationIds) {
		this.locationIds = locationIds;
	}

	public String getHiddenName() {
        return hiddenName;
    }

    public void setHiddenName(String hiddenName) {
        this.hiddenName = hiddenName;
    }
    
	/**
	 * @return the pageForSpecifyLocation
	 */
	public String getPageForSpecifyLocation() {
		return pageForSpecifyLocation;
	}

	/**
	 * @param pPageForSpecifyLocation the pageForSpecifyLocation to set
	 */
	public void setPageForSpecifyLocation(String pPageForSpecifyLocation) {
		pageForSpecifyLocation = pPageForSpecifyLocation;
	}

	public int doStartTag() throws JspException {
        return BodyTagSupport.EVAL_BODY_BUFFERED;
    }

    public int doEndTag() throws JspException {
        try {
            Writer out = pageContext.getOut();
            HttpServletRequest request = (HttpServletRequest) pageContext
                    .getRequest();
            HttpSession session = request.getSession(true);
            CleanwiseUser user = (CleanwiseUser) session
                    .getAttribute(Constants.APP_USER);
            SiteData currentLocation = user.getSite();
            if (Utility.isSet(layout) == false) {
                layout = LAYOUT_H;
            }
            String label = ClwMessageResourcesImpl.getMessage(request,
                    "global.label.location");
            final long randId = ++randIdCounter;
            final String specifyId = Constants.PARAMETER_OPERATION_SPECIFY_LOCATIONS
                    + randIdCounter;
            String linkLabel = ClwMessageResourcesImpl.getMessage(request,
                    "locate." + Constants.PARAMETER_OPERATION_SPECIFY_LOCATIONS
                            + ".link.label");
            String linkTitle = ClwMessageResourcesImpl.getMessage(request,
                    "locate." + Constants.PARAMETER_OPERATION_SPECIFY_LOCATIONS
                            + ".link.title");
            String linkHref = request.getContextPath()
                    + "/userportal/esw/locate.do?operation="
                    + Constants.PARAMETER_OPERATION_SPECIFY_LOCATIONS
                    + "&randId=" + randId + "&locationSelected=" + locationSelected +
                    "&locationIds=" + locationIds + "&hiddenName=" + hiddenName +
                    "&pageForSpecifyLocation=" + pageForSpecifyLocation;

            int siteId[] = getSiteId(request, locationIds);
            StringBuilder idString = new StringBuilder();
            String specifiedText = ClwI18nUtil.getMessage(request,
                    "locate.specifyLocations.label.specifiedLocations",
                    new Object[] { siteId == null ? 0 : siteId.length });
            StringBuilder hiddenText = new StringBuilder();
            for (int i = 0; siteId != null && i < siteId.length; i++) {
                hiddenText.append("<input type='hidden' name='" + hiddenName
                        + "' value='" + siteId[i] + "'/>");
                if (idString.length() > 0) {
                    idString.append(',');
                }
                idString.append(siteId[i]);
            }
            if (idString.length() > 0) {
                linkHref += "&ids=" + idString;
            }
            StringBuilder html = new StringBuilder();
            //STJ-5238
            int configuredSites = user.getConfiguredLocationCount();
            if( configuredSites == 1 ) {
            	String currentLocationLabel = ClwMessageResourcesImpl.getMessage(request,
            			"locate.specifyLocations.label.currentLocation");
            	if (layout.toUpperCase().startsWith(LAYOUT_H)) {
	                html.append("<tr><td>");
	                html.append(label);
	                html.append("</td><td>");
	                html.append("<input type=\"text\" class=\"fade\" readonly=\"readonly\" value=\""+currentLocationLabel+"\"/>");
	                html.append("</td><td>");
	                html.append("<html:hidden property=\""+selectName+ "\" value=\""+locationSelected+"\" />");
	                html.append("</td></tr>");
	            } else {
	                html.append("<p>");
	                html.append(label);
	                html.append("<br />");
	                html.append("<input type=\"text\" class=\"fade\" readonly=\"readonly\" value=\""+currentLocationLabel+"\"/>");
	                html.append("<br />");
	                html.append("<html:hidden property=\""+selectName+ "\" value=\""+locationSelected+"\" />");
	                html.append("</p>");
	            }
            }
            else{
	            StringBuilder htmlButton = new StringBuilder();
	            htmlButton.append("<p id='");
	            htmlButton.append(specifyId);
	            htmlButton.append("Info'>");
	            if (siteId != null && siteId.length > 0 && useSelect == false) {
	                htmlButton.append(specifiedText);
	            }
	            htmlButton.append(hiddenText);
	            htmlButton.append("</p>");
	            htmlButton.append("<p class='buttonRow clearfix'><a id='");
	            htmlButton.append(specifyId);
	            htmlButton.append("Link' class='blueBtnMed popUpWide' title='");
	            htmlButton.append(linkTitle + "'");
	            htmlButton.append(" href='" + linkHref + "'");
	            htmlButton.append("><span>" + linkLabel + "</span></a></p>");
	
	            String htmlSelect = "";
	            if (useSelect) {
	                htmlSelect = createSelect(request, specifyId, currentLocation,
	                        user, specifiedText);
	            }
	
	            if (layout.toUpperCase().startsWith(LAYOUT_H)) {
	                html.append("<tr><td>");
	                html.append(label);
	                html.append("</td><td>");
	                html.append(htmlSelect);
	                html.append(htmlButton.toString());
	                html.append("</td></tr>");
	            } else {
	                html.append("<p>");
	                html.append(label);
	                html.append("<br />");
	                html.append(htmlSelect);
	                html.append(htmlButton.toString());
	                html.append("</p>");
	            }
            }
            out.write(html.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return BodyTagSupport.EVAL_PAGE;
    }

    private String createSelect(HttpServletRequest request, String specifyId,
            SiteData currentLocation, CleanwiseUser user, String specifiedText) {
        StringBuilder htmlSelect = new StringBuilder();
        htmlSelect.append("<select name='" + selectName + "'>");
       
        String locationSelected = getLocationSelected();
        Object[] params = new Object[] { user.getConfiguredLocationCount() };
        htmlSelect.append("<option value='");
        htmlSelect.append(Constants.ORDERS_ALL_LOCATIONS);
        htmlSelect.append("'");
        htmlSelect.append(">");
        htmlSelect.append(ClwI18nUtil.getMessage(request,
                "locate.specifyLocations.label.allLocations", params));
        htmlSelect.append("</option>");
        htmlSelect.append("<option value='");
        htmlSelect.append(Constants.ORDERS_SPECIFIED_LOCATIONS);
        htmlSelect.append("' id='" + specifyId + "Option");
        htmlSelect.append("'");
        if (Constants.ORDERS_SPECIFIED_LOCATIONS.equals(locationSelected)) {
            htmlSelect.append(" selected");
        }
        htmlSelect.append(">");
        htmlSelect.append(specifiedText);
        htmlSelect.append("</option>");

        if (currentLocation != null) {
            htmlSelect.append("<option value='");
            htmlSelect.append(Constants.ORDERS_CURRENT_LOCATION);
            htmlSelect.append("'");
            if (Constants.ORDERS_CURRENT_LOCATION.equals(locationSelected)) {
                htmlSelect.append(" selected");
            }
            htmlSelect.append(">");
            htmlSelect.append(ClwMessageResourcesImpl.getMessage(request,
                    "locate.specifyLocations.label.currentLocation"));
            htmlSelect.append("</option>");
        }
        htmlSelect.append("</select><br />");
        return htmlSelect.toString();
    }

    private static int[] getSiteId(HttpServletRequest request, String locationIds) {
        
    	int ii=0;
    	StringTokenizer st = new StringTokenizer(locationIds, ",");
    	int siteId[] = new int[st.countTokens()];
    	
    	while(st.hasMoreTokens()){
    		String val = st.nextToken();
    		siteId[ii] = Integer.parseInt(val);
    		ii++;
    	}
    	
    	return siteId;
    }
}