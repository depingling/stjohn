package com.cleanwise.view.taglibs;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ProductViewDefData;
import com.cleanwise.service.api.value.ProductViewDefDataVector;

public class DefineProductAttributesDetail extends DisplayProductHeader{

	/**
	 * Retrieves the actual definitions that we need to render. These are pulled
	 * from the account data object for the account the usre is currently
	 * shopping for.
	 *
	 * @return a list of ProductViewDefinition defining what item attributes
	 *         that we are going to render.
	 */
	protected ProductViewDefDataVector getProductDefinitions() {
		ProductViewDefDataVector defs = appUser.getUserAccount().getProductViewDefinitions(RefCodeNames.SHOP_UI_PRODUCT_VIEW_CD.SHOP_UI_DETAIL);
		if(defs==null){
			defs = getDefaultViewDefinitions();
		}
		Collections.sort(defs,PRODUCT_DEF_SORT);
		return defs;
	}

	/**
	 * Called on end of the tag Renders the content
	 */
	public int doEndTag() throws JspException {
                noLinks = true;

		Writer out = pageContext.getOut();
		try {
			renderXpedxItemView(out);
		} catch (IOException e) {
			throw new JspException(e);
		}

		return BodyTagSupport.EVAL_PAGE;

	}

	public String getDefaultStyle(){
		return "itemDetail";
	}

	/**
	 * Renders the xpedx view.
	 *
	 * @param request
	 * @param out
	 * @throws IOException
	 */
	private void renderXpedxItemView(Writer out) throws IOException {

		Iterator it = getProductDefinitions().iterator();
		while (it.hasNext()) {
			ProductViewDefData def = (ProductViewDefData) it.next();
			if (!shouldAttributeBeRendered(def.getAttributename())) {
				return;
			}

			String style = def.getStyleClass();
			if (!Utility.isSet(def.getStyleClass())) {
				style = getDefaultStyle();
			} else {
				style = style + "  "+getDefaultStyle();
			}

			out.write("<tr><td class=\"" + style + "\">");
			//out.write("<div class=\"" + style + "\">");
                        String header = xlateHeaderName(def.getAttributename());
			if (Utility.isSet(header) && !"&nbsp;".equals(header)) {
				out.write(header); // header
				out.write(":&nbsp;");
			}
			writeElement(out, def);
			//out.write("</div>");
			//out.write("<BR>");
			out.write("</td></tr>");
		}
	}
}
