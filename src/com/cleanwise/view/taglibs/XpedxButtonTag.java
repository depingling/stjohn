package com.cleanwise.view.taglibs;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.FormTag;
import org.apache.struts.util.RequestUtils;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ProductData;
import com.cleanwise.service.api.value.ShoppingCartItemData;
import com.cleanwise.service.api.value.SiteInventoryInfoView;
import com.cleanwise.view.forms.UserShopForm;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.ShopTool;

public class XpedxButtonTag extends TagSupport {
	private static final Category log = Category.getInstance(DisplayProductAttributesTag.class);
	private String name;
	private int index;
	private Integer iteratorId;

	private String label;
	private String styleClass;
	private String leftImage;
	private String rightImage;
	private String middleImage;
	private String url;
	private String onClick;
        private String target;


	private int getCurrentDisplayIndex(int offset) {
		if(iteratorId == null){
			return offset;
		}
		return iteratorId.intValue() + offset;
	}


	HttpServletRequest request;
	HttpSession session;

	private static String ITERATE_ID = XpedxButtonTag.class.getName()+".ITERATE_ID";

	public int doStartTag() throws JspException {
		request = (HttpServletRequest) pageContext.getRequest();
		session = request.getSession();


		//set the iterator id, used for tab indexing
		iteratorId = (Integer) request.getAttribute(ITERATE_ID);
		if (iteratorId == null) {
			iteratorId = new Integer(0);
		} else {
			iteratorId = new Integer(iteratorId.intValue() + 1);
		}
		request.setAttribute(ITERATE_ID, iteratorId);

		return BodyTagSupport.EVAL_BODY_BUFFERED;
	}

	public int doEndTag() throws JspException {
		Writer out = pageContext.getOut();
		try {
			renderXpedxButton(request, out);
		} catch (IOException e) {
			throw new JspException(e);
		}
		return BodyTagSupport.EVAL_PAGE;
	}



	/**
	 * Does the work of mapping string values to their appropriate values. No
	 * internationalization is done in this method as it is dealing directly
	 * with database values.
	 *
	 * @param out
	 *            Output writter to write to
	 * @param def
	 *            the definition to render
	 * @throws IOException
	 */
	protected void writeElement(HttpServletRequest request, Writer out) throws IOException {
		String buttonText = (label==null)? "??????": ClwI18nUtil.getMessage(request,label,null);
		if(styleClass==null) styleClass = "xpdexGradientButton";
		if(leftImage==null) leftImage = "buttonLeft.png";
		if(rightImage==null) rightImage = "buttonRight.png";
		if(middleImage==null) middleImage = "buttonMiddle.png";               
		String lb = ClwCustomizer.getSIP(request,leftImage);
		String rb = ClwCustomizer.getSIP(request,rightImage);
		String mb = ClwCustomizer.getSIP(request,middleImage);
		String onClickStr = (onClick!=null)? " onClick='"+onClick+"'":"";
                String targetStr = (target == null || target.trim().length()==0)?"":"target='"+target+"'";
                
		//String style = "border-collapse: collapse;padding-top: 5px;padding-bottom: 0px;"
		String useragent = request.getHeader("User-Agent").toLowerCase();
		String styleText = "";
		if(useragent.indexOf("safari") >=0) {
		   styleText = "style = 'border-collapse: collapse;padding-top: 5px; padding-bottom: 0px;'";
		}
			
			
		String buttonStr = 
           "<td align='right' valign='middle'><img src='"+lb+"' border=0'></td>"+
           "<td align='center' valign='middle' nowrap class='"+styleClass+"' >"+
           "<a class='"+styleClass+"' href='"+url+"' "+
		   styleText+" "+
                   targetStr+" "+
		   onClickStr+" >"+
		   buttonText+"</a></td>"+
           "<td align='left' valign='middle'><img src='"+rb+"' border='0'></td>";
		out.write(buttonStr);

	}

	private void renderXpedxButton(HttpServletRequest request, Writer out) throws IOException {
		writeElement(request, out);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}


	public void setLabel(String pLabel) {
		label = pLabel;
	}
	public String getLabel() {
		return label;
	}

	public void setStyleClass(String pStyleClass) {
		styleClass = pStyleClass;
	}
	public String getStyleClass() {
		return styleClass;
	}

	public void setLeftImage(String pLeftImage) {
		leftImage = pLeftImage;
	}

	
	public String getLeftImage() {
		return leftImage;
	}

	public void setRightImage(String pRightImage) {
		rightImage = pRightImage;
	}
	public String getRightImage() {
		return rightImage;
	}

	public void setMiddleImage(String pMiddleImage) {
		middleImage = pMiddleImage;
	}
	public String getMiddleImage() {
		return middleImage;
	}

	public void setUrl(String pUrl) {
		url = pUrl;
	}
	public String getUrl() {
		return url;
	}

	public void setOnClick(String pOnClick) {
		onClick = pOnClick;
	}
	public String getOnClick() {
		return onClick;
	}

	public void setTarget(String pTarget) {
		target = pTarget;
	}
	public String getTarget() {
		return target;
	}
}
