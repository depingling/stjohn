/**
 * 
 */
package com.cleanwise.view.utils.validators;


import javax.servlet.http.HttpServletRequest;

public class IntegerValidator extends Validator {
	private Integer value;
	
	public IntegerValidator(HttpServletRequest request, String valueStr,
			String fieldNameMessageKey, String fieldNameDefaultValue) {
		super(request, valueStr, fieldNameMessageKey, fieldNameDefaultValue);
		parse();
	}

	public boolean isNumberFormatValid() {
		return value != null;
	}

	public boolean isPositiveValue() {
		return value.intValue() >= 0; 
	}

	private void parse() {
		try {
			value = new Integer( Integer.parseInt(valueStr) );
		} catch (NumberFormatException e) {
		}
	}
	
	public int getValue() {
		return this.value.intValue();
	}
}
