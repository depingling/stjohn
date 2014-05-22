/**
 * 
 */
package com.cleanwise.view.utils.validators;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

public class BigDecimalValidator extends Validator {
	private BigDecimal value;

	public BigDecimalValidator(HttpServletRequest request, String valueStr,
			String fieldNameMessageKey, String fieldNameDefaultValue) {
		super(request, valueStr, fieldNameMessageKey, fieldNameDefaultValue);
		parse();
	}

	public boolean isNumberFormatValid() {
		return value != null;
	}

	public boolean isPositiveValue() {
		return value.compareTo(new BigDecimal(0)) > -1;
	}

	private void parse() {
		try {
			value = new BigDecimal(valueStr);
		} catch (NumberFormatException e) {
		}
	}

	public BigDecimal getValue() {
		return this.value;
	}
}
