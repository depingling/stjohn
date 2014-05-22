package com.cleanwise.view.utils.validators;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import com.cleanwise.view.i18n.ClwI18nUtil;

public abstract class Validator {

	public static BigDecimalValidator getBigDecimalInstance(HttpServletRequest request,
			String valueStr, String fieldNameMessageKey,
			String fieldNameDefaultValue) {
		return new BigDecimalValidator(request, valueStr, fieldNameMessageKey,
				fieldNameDefaultValue);
	}

	public static IntegerValidator getIntegerInstance(HttpServletRequest request,
			String valueStr, String fieldNameMessageKey,
			String fieldNameDefaultValue) {
		return new IntegerValidator(request, valueStr, fieldNameMessageKey,
				fieldNameDefaultValue);
	}

	HttpServletRequest request;
	String valueStr;
	String fieldNameMessageKey;
	String fieldNameDefaultValue;

	String propertyName;

	protected Validator(HttpServletRequest request, String valueStr,
			String fieldNameMessageKey, String fieldNameDefaultValue) {
		this.request = request;
		this.valueStr = valueStr;
		this.fieldNameMessageKey = fieldNameMessageKey;
		this.fieldNameDefaultValue = fieldNameDefaultValue;
		this.propertyName = fieldNameDefaultValue.replace(" ", "");
		this.propertyName = this.propertyName.substring(0, 1).toLowerCase() + this.propertyName.substring(1); 
	}

	public abstract boolean isNumberFormatValid();
	public abstract boolean isPositiveValue();
	

	private String getNumberFormatErrorMessage(String errorMessageKey, int lineNumber) {
		String property = ClwI18nUtil.getMessageOrNull(this.request,
				this.fieldNameMessageKey);
		if (property == null) {
			property = this.fieldNameDefaultValue;
		}
		final Object[] args = new Object[] {
				property + (lineNumber == -1 ? "" : " (Line " + String.valueOf(lineNumber) + ")"),
				valueStr };
		return ClwI18nUtil.getMessage(this.request, errorMessageKey,
				args, true);
	}
	public void addNumberFormatErrorMessageToActionErrors(ActionErrors actionErrors) {
		this.addNumberFormatErrMsg(actionErrors, -1);
	}

	public void addNumberFormatErrMsg(ActionErrors actionErrors, final int lineNum) {
		actionErrors.add(this.propertyName, new ActionError("error.simpleError",
				this.getNumberFormatErrorMessage("error.wrongNumberFormat", lineNum)));
	}
	
	public void addNegativeErrorMessageToActionErrors(ActionErrors actionErrors) {
		this.addNegativeErrMsg(actionErrors, -1);
	}

	public void addNegativeErrMsg(ActionErrors actionErrors, final int lineNum) {
		actionErrors.add(this.propertyName, new ActionError("error.simpleError",
				this.getNumberFormatErrorMessage("error.wrongNegativeValue", lineNum)));
	}

	public void addNumberFormatErrorMessageToActionErrors(ActionErrors actionErrors,
			String errorMessageKey, Object[] parameters) {
        String mess = ClwI18nUtil.getMessage(request, "userWorkOrder.errors.wrongNteNumberFormat", parameters, true);                
		actionErrors.add(this.propertyName, new ActionError("error.simpleError", mess));
	}
}
