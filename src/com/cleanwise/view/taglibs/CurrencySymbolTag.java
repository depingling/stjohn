/*
 * CurrencySymbolTag.java
 *
 */

package com.cleanwise.view.taglibs;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.SessionTool;
import com.cleanwise.view.utils.ClwCustomizer;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.taglib.TagUtils;
import java.util.Locale;

/**
 * get a currency symbol for a given locale and
 * currency string
 * @author dvieira
 */
public class CurrencySymbolTag extends TagSupport {
    
    /**
     * Holds value of property currencyCode.
     */
    private String currencyCode;
    private java.util.Locale mLocale;
    
    /**
     * Process the start tag.
     *
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        
            String sym = ClwCustomizer.getSymbolForCurrency(currencyCode, mLocale);
            TagUtils.getInstance().write(pageContext, sym);
            
            return (SKIP_BODY);
             
        
        
    }
    
    /**
     * Getter for property currencyCode.
     * @return Value of property currencyCode.
     */
    public String getCurrencyCode() {
        
        return this.currencyCode;
    }
    
    /**
     * Setter for property currencyCode.
     * @param currencyCode New value of property currencyCode.
     */
    public void setCurrencyCode(String currencyCode) {
        
        this.currencyCode = currencyCode;
    }
	
	public void setLocale(Locale pLoc ) {
		mLocale = pLoc;
	}
	
	public Locale getLocale() {
		return mLocale;
	}
    
}
