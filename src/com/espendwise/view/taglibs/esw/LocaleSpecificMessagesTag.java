package com.espendwise.view.taglibs.esw;

import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.taglib.html.MessagesTag;
import org.apache.struts.taglib.TagUtils;

import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.view.taglibs.DisplayProductAttributesTag;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ShopTool;

/**
 * Displays the messages for the keys passed to ActionError constructor from the
 * user's locale specific message resource files.
 * 
 * @author vkatanguri
 */
public class LocaleSpecificMessagesTag extends MessagesTag {

	/**
	 * Called on start of tag, it retrieves user's locale from CleanwiseUser
	 * object, and retrieves keys and values added to ActionError constructor,
	 * and retrieves messages based on the locale, key and value.
	 */
	public int doStartTag() throws JspException {
		this.processed = false;

		ActionMessages messages = null;

		String name = this.name;

		if ((this.message != null) && ("true".equalsIgnoreCase(this.message))) {
			name = "org.apache.struts.action.ACTION_MESSAGE";
		}
		try {
			messages = TagUtils.getInstance().getActionMessages(
					this.pageContext, name);
		} catch (JspException e) {
			TagUtils.getInstance().saveException(this.pageContext, e);
			throw e;
		}

		this.iterator = (this.property == null ? messages.get() : messages
				.get(this.property));

		if (!this.iterator.hasNext()) {
			return 0;
		}

		ActionMessage actionMessage = (ActionMessage) this.iterator.next();
		// String msg = TagUtils.getInstance().message(this.pageContext,
		// this.bundle, this.locale, report.getKey(), report.getValues());
		CleanwiseUser user = ShopTool.getCurrentUser(this.pageContext
				.getSession());
		String msg = I18nUtil.getMessage(user.getStorePrefixLocale(),
				actionMessage.getKey(), actionMessage.getValues());

		if (msg == null)
			this.pageContext.removeAttribute(this.id);
		else {
			this.pageContext.setAttribute(this.id, msg);
		}

		if ((this.header != null) && (this.header.length() > 0)) {
			// String headerMessage =
			// TagUtils.getInstance().message(this.pageContext, this.bundle,
			// this.locale, this.header);
			String headerMessage = I18nUtil.getMessage(
					user.getStorePrefixLocale(), this.header);

			if (headerMessage != null) {
				TagUtils.getInstance().write(this.pageContext, headerMessage);
			}

		}

		this.processed = true;

		return 2;
	}

	public int doAfterBody() throws JspException {

		if (this.bodyContent != null) {
			TagUtils.getInstance().writePrevious(this.pageContext,
					this.bodyContent.getString());
			this.bodyContent.clearBody();
		}

		if (this.iterator.hasNext()) {
			ActionMessage actionMessage = (ActionMessage) this.iterator.next();
			CleanwiseUser user = ShopTool.getCurrentUser(this.pageContext
					.getSession());
			String msg = I18nUtil.getMessage(user.getStorePrefixLocale(),
					actionMessage.getKey(), actionMessage.getValues());
			// String msg = TagUtils.getInstance().message(this.pageContext,
			// this.bundle, this.locale, report.getKey(), report.getValues());

			if (msg == null)
				this.pageContext.removeAttribute(this.id);
			else {
				this.pageContext.setAttribute(this.id, msg);
			}

			return 2;
		}
		return 0;
	}

	/**
	 * Called on end of the tag Renders the content
	 */
	public int doEndTag() throws JspException {
		if ((this.processed) && (this.footer != null)
				&& (this.footer.length() > 0)) {
			CleanwiseUser user = ShopTool.getCurrentUser(this.pageContext
					.getSession());
			String footerMessage = I18nUtil.getMessage(
					user.getStorePrefixLocale(), this.footer);
			// String footerMessage =
			// TagUtils.getInstance().message(this.pageContext, this.bundle,
			// this.locale, this.footer);

			if (footerMessage != null) {
				TagUtils.getInstance().write(this.pageContext, footerMessage);
			}
		}

		return 6;
	}

}
