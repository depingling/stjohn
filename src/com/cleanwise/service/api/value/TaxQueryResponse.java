package com.cleanwise.service.api.value;

import java.math.BigDecimal;

import com.cleanwise.service.api.framework.ValueObject;

public class TaxQueryResponse extends ValueObject{
	BigDecimal taxRate;
	public TaxQueryResponse(BigDecimal pTaxRate){
		taxRate = pTaxRate;
	}
	public BigDecimal calculateTax(BigDecimal pAmount){
		BigDecimal tax = pAmount.multiply(taxRate);
        tax = tax.setScale(2,BigDecimal.ROUND_UP);
        return tax;
	}
}
