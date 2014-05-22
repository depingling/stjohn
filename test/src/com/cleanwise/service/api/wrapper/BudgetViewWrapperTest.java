package com.cleanwise.service.api.wrapper;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.math.BigDecimal;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.BudgetData;
import com.cleanwise.service.api.value.BudgetDetailData;
import com.cleanwise.service.api.value.BudgetDetailDataVector;
import com.cleanwise.service.api.value.BudgetView;


public class BudgetViewWrapperTest {
	BudgetViewWrapper mBudgetViewWrapper;
	BudgetData mBudgetData;
	BudgetView mBudgetView;
	
	@BeforeMethod
	public void setUp() throws Exception {
		//super.setUp();
		//generate some test data.  This would normally come from the db
		mBudgetData = BudgetData.createValue();
		mBudgetData.setBudgetId(1);
		mBudgetData.setBudgetStatusCd(RefCodeNames.BUDGET_STATUS_CD.ACTIVE);
		mBudgetData.setBudgetThreshold(null);
		mBudgetData.setBudgetTypeCd(RefCodeNames.BUDGET_TYPE_CD.SITE_BUDGET);
		mBudgetData.setBudgetYear(2010);
		mBudgetData.setBusEntityId(2);
		mBudgetData.setCostCenterId(3);
		mBudgetData.setShortDesc("Test");
		//test quartly budget
		BudgetDetailData lBudgetDetailData1 = BudgetDetailData.createValue();
		lBudgetDetailData1.setAmount(new BigDecimal("100.00"));
		lBudgetDetailData1.setBudgetDetailId(4);
		lBudgetDetailData1.setPeriod(1);
		BudgetDetailData lBudgetDetailData2 = BudgetDetailData.createValue();
		lBudgetDetailData2.setAmount(new BigDecimal("150.00"));
		lBudgetDetailData2.setBudgetDetailId(3);
		lBudgetDetailData2.setPeriod(2);
		BudgetDetailData lBudgetDetailData3 = BudgetDetailData.createValue();
		lBudgetDetailData3.setAmount(new BigDecimal("80.00"));
		lBudgetDetailData3.setBudgetDetailId(2);
		lBudgetDetailData3.setPeriod(3);
		BudgetDetailData lBudgetDetailData4 = BudgetDetailData.createValue();
		lBudgetDetailData4.setAmount(new BigDecimal("100.00"));
		lBudgetDetailData4.setBudgetDetailId(1);
		lBudgetDetailData4.setPeriod(4);
		
		BudgetDetailDataVector details = new BudgetDetailDataVector();
		details.add(lBudgetDetailData1);
		details.add(lBudgetDetailData2);
		details.add(lBudgetDetailData3);
		details.add(lBudgetDetailData4);
		
		mBudgetView = BudgetView.createValue();
		mBudgetView.setBudgetData(mBudgetData);
		mBudgetView.setDetails(details);
		mBudgetViewWrapper = new  BudgetViewWrapper(mBudgetView);
	}

	@AfterMethod
	public void tearDown() throws Exception {
		//super.tearDown();
	}

	

	@Test
	public void testGetBudgetData() {
		AssertJUnit.assertEquals("Budget view wrapper budgetData object not the same as originally populated", mBudgetViewWrapper.getBudgetData(), mBudgetData);
	}

	@Test
	public void testGetBudget() {
		AssertJUnit.assertEquals("Budget view wrapper budgetView object not the same as originally populated", mBudgetViewWrapper.getBudget(), mBudgetView);
	}

	@Test
	public void testSetAmount() {
		BigDecimal p1 = new BigDecimal("999999999.99");
		BigDecimal p2 = new BigDecimal("55.45");
		
		BigDecimal p365 = new BigDecimal("22.00");
		mBudgetViewWrapper.setAmount(1, p1);
		mBudgetViewWrapper.setAmount(2, p2);
		AssertJUnit.assertEquals("Period 1 not what was set", p1, mBudgetViewWrapper.getAmount(1));
		AssertJUnit.assertEquals("Period 2 not what was set", p2, mBudgetViewWrapper.getAmount(2));
		mBudgetViewWrapper.setAmount(365,p365);
		AssertJUnit.assertEquals("Period 365 not what was set", p365, mBudgetViewWrapper.getAmount(365));
		
		mBudgetViewWrapper.setAmount(10, null);
		AssertJUnit.assertNull("Period set to null not returned as null",mBudgetViewWrapper.getAmount(10));
	}

	@Test
	public void testGetAmount() {
		//most of this is tested in the set amount.  Just test that random
		//value (one that should be empty) is empty and no error is thrown.
		AssertJUnit.assertNull("Unset period was not null", mBudgetViewWrapper.getAmount(99));
	}

}
