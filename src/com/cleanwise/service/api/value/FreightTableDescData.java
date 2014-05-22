package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.ValueObject;

public class FreightTableDescData extends ValueObject {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 3438985336662772229L;
	private FreightTableData mFreightTable;
	private FreightTableCriteriaDataVector mFreightTableCriteria;
	public FreightTableData getFreightTable() {
		return mFreightTable;
	}
	public void setFreightTable(FreightTableData freightTable) {
		mFreightTable = freightTable;
	}
	public FreightTableCriteriaDataVector getFreightTableCriteria() {
		return mFreightTableCriteria;
	}
	public void setFreightTableCriteria(
			FreightTableCriteriaDataVector freightTableCriteria) {
		mFreightTableCriteria = freightTableCriteria;
	}
	
	public boolean hasValidFreightCriteria(){
		if(mFreightTableCriteria != null && !mFreightTableCriteria.isEmpty()){
			return true;
		}
		return false;
	}
}
