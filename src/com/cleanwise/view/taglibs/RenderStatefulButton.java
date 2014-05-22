/*
 * RenderStatefulButton.java
 *
 * Created on February 21, 2005, 2:23 PM
 */

package com.cleanwise.view.taglibs;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.utils.SessionTool;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
/**
 *
 * @author bstevens
 */
public class RenderStatefulButton extends TagSupport{
    /**
     * Holds value of property tabClassOn.
     */
    private String tabClassOn;
    
    /**
     * Holds value of property tabClassOff.
     */
    private String tabClassOff;
    
    /**
     * Holds value of property linkClassOff.
     */
    private String linkClassOff;
    
    /**
     * Holds value of property linkClassOn.
     */
    private String linkClassOn;
    
    /**
     * Holds value of property link.
     */
    private String link;
    
    /**
     * Holds value of property name.
     */
    private String name;
    
    /**
     * Holds value of property contains.
     */
    private String contains;
    
    /**
     * Getter for property tabClassOn.
     * @return Value of property tabClassOn.
     */
    public String getTabClassOn() {
        
        return this.tabClassOn;
    }
    
    /**
     * Setter for property tabClassOn.
     * @param tabClassOn New value of property tabClassOn.
     */
    public void setTabClassOn(String tabClassOn) {
        
        this.tabClassOn = tabClassOn;
    }
    
    /**
     * Getter for property tabClassOff.
     * @return Value of property tabClassOff.
     */
    public String getTabClassOff() {
        
        return this.tabClassOff;
    }
    
    /**
     * Setter for property tabClassOff.
     * @param tabClassOff New value of property tabClassOff.
     */
    public void setTabClassOff(String tabClassOff) {
        
        this.tabClassOff = tabClassOff;
    }
    
    /**
     * Getter for property linkClassOff.
     * @return Value of property linkClassOff.
     */
    public String getLinkClassOff() {
        
        return this.linkClassOff;
    }
    
    /**
     * Setter for property linkClassOff.
     * @param linkClassOff New value of property linkClassOff.
     */
    public void setLinkClassOff(String linkClassOff) {
        
        this.linkClassOff = linkClassOff;
    }
    
    /**
     * Getter for property linkClassOn.
     * @return Value of property linkClassOn.
     */
    public String getLinkClassOn() {
        
        return this.linkClassOn;
    }
    
    /**
     * Setter for property linkClassOn.
     * @param linkClassOn New value of property linkClassOn.
     */
    public void setLinkClassOn(String linkClassOn) {
        
        this.linkClassOn = linkClassOn;
    }
    
    /**
     * Getter for property link.
     * @return Value of property link.
     */
    public String getLink() {
        
        return this.link;
    }
    
    /**
     * Setter for property link.
     * @param link New value of property link.
     */
    public void setLink(String link) {
        
        this.link = link;
    }
    
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        
        return this.name;
    }
    
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        
        this.name = name;
    }
    
    /**
     *Render the link and the table cell.  Rendered HTML will look like:
     *<pre>
     *<td class="aton">
     *   <a class="tbar" href="storemgr.do">Store</a>
     *</td>
     *</pre>
     */
    public int doStartTag() throws JspException {
        if(!(pageContext.getRequest() instanceof HttpServletRequest)){
            //return (SKIP_BODY);
        }
        try{
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();     
            /* Get the page being accessed. */
            String pg = SessionTool.getActualRequestedURI(request);
            
            
            if(pg == null){
                //should not happen, but if all else fails render the button
                //incorrectly rather than not at all.
                pg = "";
            }
            String lTabClass;
            String lLinkClass;
            if(pg.indexOf("related") > 0) {
                pg = " " + request.getParameter("action");
            }
            String[] templA = new String[1];
            if(!Utility.isSet(contains)){
                String checkCondition = link;
                int end = checkCondition.indexOf('.');
                if(end > 0){
                    checkCondition = checkCondition.substring(0,end);
                }
                templA[0] = checkCondition.toLowerCase();
            } else {
              templA = Utility.parseStringToArray(contains.toLowerCase(),",");
            }
            
            boolean foundFl = false;
            String pglc = pg.toLowerCase();
            for(int ii=0; ii<templA.length; ii++) {
              String checkCondition = templA[ii];
              if(!Utility.isSet(checkCondition)) {
                continue;
              }
              if(pglc.indexOf(checkCondition)>=0) {
                foundFl = true;
                break;
              }
            }

            if ( foundFl ) {
                lTabClass = tabClassOn;
                lLinkClass = linkClassOn;
            } else {
                lTabClass = tabClassOff;
                lLinkClass = linkClassOff;
            }
            
            JspWriter out = pageContext.getOut();
            out.write(PART_A);
            out.write(lTabClass);
            out.write(PART_B);
            out.write(lLinkClass);
            out.write(PART_C);
            out.write(link);
            out.write(PART_D);
            out.write(name);
            out.write(PART_E);
            
        }catch(IOException e){
            e.printStackTrace();
            throw new JspException("Caught IO Exception while rendering tag: "+e.getMessage());
        }
        return (SKIP_BODY);
    }
    
    private static final String PART_A="<td class=\"";
    private static final String PART_B="\"><a class=\"";
    private static final String PART_C="\" href=\"";
    private static final String PART_D="\">";
    private static final String PART_E="</a></td>";
    
    /**
     * Release all allocated resources.
     */
    public void release() {
        
        super.release();
        link = null;
        linkClassOff = null;
        linkClassOn = null;
        name = null;
        tabClassOff = null;
        tabClassOn = null;
        contains = null;
        
    }
    
    /**
     * Getter for property contains.
     * @return Value of property contains.
     */
    public String getContains() {
        
        return this.contains;
    }
    
    /**
     * Setter for property contains.
     * @param contains New value of property contains.
     */
    public void setContains(String contains) {
        
        this.contains = contains;
    }
}
