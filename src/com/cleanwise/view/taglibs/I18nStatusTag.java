/**
 * 
 */
package com.cleanwise.view.taglibs;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;
import org.apache.struts.Globals;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import org.apache.struts.taglib.TagUtils;

/**
 * @author ssharma
 *
 * Translate messages per user's locale. 
 * The key will be constructed from input parameters -refCodeGroup and refCodeName. 
 */
public class I18nStatusTag extends TagSupport{
	private String refCodeName;
	private String refCodeGroup;
	/**
	 * @return the refCodeName
	 */
	public String getRefCodeName() {
		return refCodeName;
	}
	/**
	 * @param refCodeName the refCodeName to set
	 */
	public void setRefCodeName(String refCodeName) {
		this.refCodeName = refCodeName;
	}
	/**
	 * @return the refCodeGroup
	 */
	public String getRefCodeGroup() {
		return refCodeGroup;
	}
	/**
	 * @param refCodeGroup the refCodeGroup to set
	 */
	public void setRefCodeGroup(String refCodeGroup) {
		this.refCodeGroup = refCodeGroup;
	}
	
	public int doStartTag() throws JspException {
		if(getRefCodeName() == null || getRefCodeGroup() == null
				|| pageContext == null || pageContext.getSession() == null){
            return SKIP_BODY;
        } 
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		String key = getRefCodeGroup()+"."+xlateRefCodeName();
        
        String status = ClwI18nUtil.getMessage(request, key, null);
        
        TagUtils.getInstance().write(pageContext, status);
        
        return SKIP_BODY;
	}   
    
	private String xlateRefCodeName(){
		refCodeName = refCodeName.replaceAll(" ", "_");
		return refCodeName.toUpperCase();
	}
}

