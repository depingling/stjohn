/**
 * 
 */
package com.cleanwise.service.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.cleanwise.view.utils.Constants;

/**
 * @author ssharma
 *
 */
public class ReportingDto implements Serializable {
	
	private String _fiscalPeriod;
	private String _fiscalPeriodSelected;
	private String _locations;
	private String _locationSelected;
	private LocationBudgetChartDto _budgetChart;
	private OrdersAtAGlanceDto _ordersReportChart;
	private String _dateRange;
	private String _from;
	private String _to;
	private List<LabelValueBean> _mfgList;
	private String _mfgSelected;
	private List<LabelValueBean> _category1;
	private List<LabelValueBean> _category2;
	private List<LabelValueBean> _category3;
	private List<LabelValueBean> _category4;
	private String _cat1Selected;
	private String _cat2Selected;
	private String _cat3Selected;
	private String _cat4Selected;
	private String _chartCatSelected;
	private Collection _matchedReports;
	private ArrayList<CategoryStructureDto> _categoryTree;
	private String _breadcrumb;
	private int[] _siteId;
	private int[] _accountId;
	
	
	/**
	 * @return the _mfgList
	 */
	public final List<LabelValueBean> getMfgList() {
		return _mfgList;
	}

	/**
	 * @param mfgList the _mfgList to set
	 */
	public final void setMfgList(List<LabelValueBean> mfgList) {
		_mfgList = mfgList;
	}

	/**
	 * @return the breadcrumb
	 */
	public final String getBreadcrumb() {
		return _breadcrumb;
	}

	/**
	 * @param breadcrumb the breadcrumb to set
	 */
	public final void setBreadcrumb(String breadcrumb) {
		this._breadcrumb = breadcrumb;
	}

	/**
	 * @return the _categoryTree
	 */
	public final ArrayList<CategoryStructureDto> getCategoryTree() {
		return _categoryTree;
	}

	/**
	 * @param categoryTree the _categoryTree to set
	 */
	public final void setCategoryTree(ArrayList<CategoryStructureDto> categoryTree) {
		_categoryTree = categoryTree;
	}

	/**
	 * @return the _category1
	 */
	public final List<LabelValueBean> getCategory1() {
		return _category1;
	}

	/**
	 * @param category1 the _category1 to set
	 */
	public final void setCategory1(List<LabelValueBean> category1) {
		_category1 = category1;
	}

	/**
	 * @return the _category2
	 */
	public final List<LabelValueBean> getCategory2() {
		return _category2;
	}

	/**
	 * @param category2 the _category2 to set
	 */
	public final void setCategory2(List<LabelValueBean> category2) {
		_category2 = category2;
	}

	/**
	 * @return the _category3
	 */
	public final List<LabelValueBean> getCategory3() {
		return _category3;
	}

	/**
	 * @param category3 the _category3 to set
	 */
	public final void setCategory3(List<LabelValueBean> category3) {
		_category3 = category3;
	}

	/**
	 * @return the _category4
	 */
	public final List<LabelValueBean> getCategory4() {
		return _category4;
	}

	/**
	 * @param category4 the _category4 to set
	 */
	public final void setCategory4(List<LabelValueBean> category4) {
		_category4 = category4;
	}

	/**
	 * @return the _cat1Selected
	 */
	public final String getCat1Selected() {
		return _cat1Selected;
	}

	/**
	 * @param cat1Selected the _cat1Selected to set
	 */
	public final void setCat1Selected(String cat1Selected) {
		_cat1Selected = cat1Selected;
	}

	/**
	 * @return the _cat2Selected
	 */
	public final String getCat2Selected() {
		return _cat2Selected;
	}

	/**
	 * @param cat2Selected the _cat2Selected to set
	 */
	public final void setCat2Selected(String cat2Selected) {
		_cat2Selected = cat2Selected;
	}

	/**
	 * @return the _cat3Selected
	 */
	public final String getCat3Selected() {
		return _cat3Selected;
	}

	/**
	 * @param cat3Selected the _cat3Selected to set
	 */
	public final void setCat3Selected(String cat3Selected) {
		_cat3Selected = cat3Selected;
	}

	/**
	 * @return the _cat4Selected
	 */
	public final String getCat4Selected() {
		return _cat4Selected;
	}

	/**
	 * @param cat4Selected the _cat4Selected to set
	 */
	public final void setCat4Selected(String cat4Selected) {
		_cat4Selected = cat4Selected;
	}

	/**
	 * @return the _chartCatSelected
	 */
	public final String getChartCatSelected() {
		return _chartCatSelected;
	}

	/**
	 * @param chartCatSelected the _chartCatSelected to set
	 */
	public final void setChartCatSelected(String chartCatSelected) {
		_chartCatSelected = chartCatSelected;
	}

	public ReportingDto(){
		setFiscalPeriod(StringUtils.EMPTY);
		setLocations(StringUtils.EMPTY);
		setLocationSelected(StringUtils.EMPTY);
		setChartCatSelected(StringUtils.EMPTY);
		setCat1Selected(StringUtils.EMPTY);
		setCat2Selected(StringUtils.EMPTY);
		setCat3Selected(StringUtils.EMPTY);
		setCat4Selected(StringUtils.EMPTY);
		setDateRange(StringUtils.EMPTY);
    	setFrom(Constants.ORDERS_DATE_RANGE_FORMAT);
    	setTo(Constants.ORDERS_DATE_RANGE_FORMAT);
	}

	/**
	 * @return the _fiscalPeriod
	 */
	public final String getFiscalPeriod() {
		return _fiscalPeriod;
	}

	/**
	 * @param fiscalPeriod the _fiscalPeriod to set
	 */
	public final void setFiscalPeriod(String fiscalPeriod) {
		_fiscalPeriod = fiscalPeriod;
	}

	/**
	 * @return the _fiscalPeriodSelected
	 */
	public final String getFiscalPeriodSelected() {
		return _fiscalPeriodSelected;
	}

	/**
	 * @param fiscalPeriodSelected the _fiscalPeriodSelected to set
	 */
	public final void setFiscalPeriodSelected(String fiscalPeriodSelected) {
		_fiscalPeriodSelected = fiscalPeriodSelected;
	}

	/**
	 * @return the _location
	 */
	public final String getLocations() {
		return _locations;
	}

	/**
	 * @param location the _location to set
	 */
	public final void setLocations(String locations) {
		_locations = locations;
	}

	/**
	 * @return the _locationSelected
	 */
	public final String getLocationSelected() {
		return _locationSelected;
	}

	/**
	 * @param locationSelected the _locationSelected to set
	 */
	public final void setLocationSelected(String locationSelected) {
		_locationSelected = locationSelected;
	}

	/**
	 * @return the budgetChart
	 */
	public final LocationBudgetChartDto getBudgetChart() {
		return _budgetChart;
	}

	/**
	 * @param budgetChart the budgetChart to set
	 */
	public final void setBudgetChart(LocationBudgetChartDto budgetChart) {
		this._budgetChart = budgetChart;
	}

	
	/**
	 * @return the _ordersReportChart
	 */
	public final OrdersAtAGlanceDto getOrdersReportChart() {
		return _ordersReportChart;
	}

	/**
	 * @param ordersReportChart the _ordersReportChart to set
	 */
	public final void setOrdersReportChart(OrdersAtAGlanceDto ordersReportChart) {
		_ordersReportChart = ordersReportChart;
	}

	/**
	 * @return the dateRange
	 */
	public final String getDateRange() {
		return _dateRange;
	}

	/**
	 * @param dateRange the dateRange to set
	 */
	public final void setDateRange(String dateRange) {
		this._dateRange = dateRange;
	}

	/**
	 * @return the from
	 */
	public final String getFrom() {
		return _from;
	}

	/**
	 * @param from the from to set
	 */
	public final void setFrom(String from) {
		this._from = from;
	}

	/**
	 * @return the to
	 */
	public final String getTo() {
		return _to;
	}

	/**
	 * @param to the to to set
	 */
	public final void setTo(String to) {
		this._to = to;
	}

	/**
	 * @return the _mfgSelected
	 */
	public final String getMfgSelected() {
		return _mfgSelected;
	}

	/**
	 * @param mfgSelected the _mfgSelected to set
	 */
	public final void setMfgSelected(String mfgSelected) {
		_mfgSelected = mfgSelected;
	}

    public Collection getMatchedReports() {
        return _matchedReports;
    }
	
    public void setMatchedReports(Collection matchedReports) {
        _matchedReports = matchedReports;
    }

    public int[] getSiteId() {
        return _siteId;
    }

    public void setSiteId(int[] siteId) {
        this._siteId = siteId;
    }

    public int[] getAccountId() {
        return _accountId;
    }

    public void setAccountId(int[] accountId) {
        this._accountId = accountId;
    }
}
