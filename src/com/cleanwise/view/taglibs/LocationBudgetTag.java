package com.cleanwise.view.taglibs;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.dto.LocationBudgetChartDto;
import com.cleanwise.service.api.value.SiteData;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.i18n.ClwMessageResourcesImpl;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;

public class LocationBudgetTag extends TagSupport{
	private static final Logger log = Logger.getLogger(LocationBudgetTag.class);
	
	private LocationBudgetChartDto _chartDto; // Holds complete information for rendering the chart
	private String _totalBgtContainer; // name of the div tag in which total budget bar is rendered
	private String _costCenterContainer; // name of the div tag in which cost center bars are rendered
	private String _theme; // Holds name of the js file for applying styles 
	//private boolean _totalBudgetBar; // based on the value of this field either total budget bar or cost center bar is rendered
	private Map _styles; // holds colors and font-family for bar segments
	private String _budgetColor = "#7BAE2B"; // Green 
	private String _overColor = "#C84B4B"; // Red 
	private String _spentColor = "#285B8C"; // Blue 
	private String _cartColor = "#B6B6B6"; // Dark Grey 
	private String _pendingApprColor = "#6C6C6C"; // Med Grey 
	private String _unusedColor = "#EBEBEB"; // Light Grey 
	private String _fontFamily = "serif";
	private boolean _isReportingChart = false;
	private String _graph;
	private String _totalUnBgtContainer; // name of the div tag in which unbudgeted cost center bars are rendered
	private String _unbudgetedCostCenterContainer; // name of the div tag in which unbudgeted cost center bars are rendered
	private String _totalCombinedContainer; // name of the div tag in which combined cost center bars are rendered
	private String _combinedCostCenterContainer; // name of the div tag in which combined cost center bars are rendered
	
	/**	
	 * @return the budgetColor
	 */
	public String getBudgetColor() {
		return _budgetColor;
	}

	/**
	 * @param budgetColor the budgetColor to set
	 */
	public void setBudgetColor(String budgetColor) {
		_budgetColor = budgetColor;
	}

	/**
	 * @return the overColor
	 */
	public String getOverColor() {
		return _overColor;
	}

	/**
	 * @param overColor the overColor to set
	 */
	public void setOverColor(String overColor) {
		_overColor = overColor;
	}

	/**
	 * @return the spentColor
	 */
	public String getSpentColor() {
		return _spentColor;
	}

	/**
	 * @param spentColor the spentColor to set
	 */
	public void setSpentColor(String spentColor) {
		_spentColor = spentColor;
	}

	/**
	 * @return the cartColor
	 */
	public String getCartColor() {
		return _cartColor;
	}

	/**
	 * @param cartColor the cartColor to set
	 */
	public void setCartColor(String cartColor) {
		_cartColor = cartColor;
	}

	/**
	 * @return the pendingApprColor
	 */
	public String getPendingApprColor() {
		return _pendingApprColor;
	}

	/**
	 * @param pendingApprColor the pendingApprColor to set
	 */
	public void setPendingApprColor(String pendingApprColor) {
		_pendingApprColor = pendingApprColor;
	}

	/**
	 * @return the unusedColor
	 */
	public String getUnusedColor() {
		return _unusedColor;
	}

	/**
	 * @param unusedColor the unusedColor to set
	 */
	public void setUnusedColor(String unusedColor) {
		_unusedColor = unusedColor;
	}

	/**
	 * @return the fontFamily
	 */
	public String getFontFamily() {
		return _fontFamily;
	}

	/**
	 * @param fontFamily the fontFamily to set
	 */
	public void setFontFamily(String fontFamily) {
		_fontFamily = fontFamily;
	}
	
	/**
	 * @return the styles
	 */
	public Map getStyles() {
		return _styles;
	}

	/**
	 * @param styles the styles to set
	 */
	public void setStyles(Map styles) {
		_styles = styles;
	}

	/**
	 * 
	 * @return the totalBgtContainer
	 */
	public String getTotalBgtContainer() {
		return _totalBgtContainer;
	}
	
	/**
	 * @param totalBgtContainer the totalBgtContainer to set
	 */
	public void setTotalBgtContainer(String totalBgtContainer) {
		_totalBgtContainer = totalBgtContainer;
	}

	/**
	 * 
	 * @return the costCenterContainer
	 */
	public String getCostCenterContainer() {
		return _costCenterContainer;
	}
	
	/**
	 * @param costCenterContainer the costCenterContainer to set
	 */
	public void setCostCenterContainer(String costCenterContainer) {
		_costCenterContainer = costCenterContainer;
	}

	/**
	 * 
	 * @return the totalBudgetBar
	 *//*
	public boolean isTotalBudgetBar() {
		return _totalBudgetBar;
	}


	*//**
	 * @param totalBudgetBar the totalBudgetBar to set
	 *//*
	public void setTotalBudgetBar(boolean totalBudgetBar) {
		_totalBudgetBar = totalBudgetBar;
	}*/
	
	/**
	 * 
	 * @return the isReportingChart
	 */
	public boolean isReportingChart() {
		return _isReportingChart;
	}


	/**
	 * @param isReportingChart to set
	 */
	public void setIsReportingChart(boolean isReportingChart) {
		_isReportingChart = isReportingChart;
	}
	
	/**
	 * 
	 * @return the theme
	 */
	public String getTheme() {
		return _theme;
	}

	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		_theme = theme;
	}

	
	/**
	 * 
	 * @return the chartDto
	 */
	public LocationBudgetChartDto getChartDto() {
		return _chartDto;
	}

	/**
	 * @param chartDto the chartDto to set
	 */
	public void setChartDto(LocationBudgetChartDto chartDto) {
		_chartDto = chartDto;
	}
	
	/**
	 * 
	 * @return the graph
	 */
	public String getGraph() {
		return _graph;
	}

	/**
	 * @param graph the graph to set
	 */
	public void setGraph(String graph) {
		_graph = graph;
	}

	public String getTotalUnBgtContainer() {
		return _totalUnBgtContainer;
	}

	public void setTotalUnBgtContainer(String totalUnBgtContainer) {
		_totalUnBgtContainer = totalUnBgtContainer;
	}

	public String getUnbudgetedCostCenterContainer() {
		return _unbudgetedCostCenterContainer;
	}

	public void setUnbudgetedCostCenterContainer(String unbudgetedCostCenterContainer) {
		_unbudgetedCostCenterContainer = unbudgetedCostCenterContainer;
	}
	
	public String getTotalCombinedContainer() {
		return _totalCombinedContainer;
	}

	public void setTotalCombinedContainer(String totalCombinedContainer) {
		_totalCombinedContainer = totalCombinedContainer;
	}

	public String getCombinedCostCenterContainer() {
		return _combinedCostCenterContainer;
	}

	public void setCombinedCostCenterContainer(String combinedCostCenterContainer) {
		_combinedCostCenterContainer = combinedCostCenterContainer;
	}

	
	public LocationBudgetTag(){
		
	}
	
	public int doStartTag() throws JspException{
		try
		{
			JspWriter out = pageContext.getOut();
			setStyles();
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

			//Here we retrieve i18n messages.
			String unused = ClwI18nUtil.getMessage(request, "location.budget.label.unused", null);
			String cart = ClwI18nUtil.getMessage(request, "location.budget.label.cart", null);
			String pending = ClwI18nUtil.getMessage(request, "location.budget.label.pending", null);
			String spent = ClwI18nUtil.getMessage(request, "location.budget.label.spent", null);
			String over = ClwI18nUtil.getMessage(request, "location.budget.label.over", null);
			String budget = ClwI18nUtil.getMessage(request, "location.budget.label.budget", null);

			//Here we retrieve data for total budgeted graph.
			//Here we replace "0" with null to remove unnecessary chunk in IE.
			boolean existsBudgetedAmounts = _chartDto.isExistsBudgetedAmounts();
			boolean existsNonBudgetedAmounts = _chartDto.isExistsNonBudgetedAmounts();
			boolean existsCombinedAmounts = _chartDto.isExistsCombinedAmounts();
			
			String totalSpentAmt = ("0".equals(_chartDto.getTotalSpent())) ? null : _chartDto.getTotalSpent();
			String totalBudgetAmt = ("0".equals(_chartDto.getTotalBD())) ? null : _chartDto.getTotalBD();
			String totalCartAmt =  ("0".equals(_chartDto.getTotalCart())) ? null : _chartDto.getTotalCart();
			String totalPendingAmt =  ("0".equals(_chartDto.getTotalPending())) ? null : _chartDto.getTotalPending();
			String totalUnused =  ("0".equals(_chartDto.getTotalUnused()))? null : _chartDto.getTotalUnused();
			String totalOverAmt =  ("0".equals(_chartDto.getTotalOver())) ? null : _chartDto.getTotalOver();

			//Here we retrieve data for total non-budgeted graph.
			//Here we replace "0" with null to remove unnecessary chunk in IE.
			String totalUnbudgetedSpentAmt =  ("0".equals(_chartDto.getUnbudgetedTotalSpent())) ? null : _chartDto.getUnbudgetedTotalSpent();
			String totalUnbudgetdCartAmt =  ("0".equals(_chartDto.getUnbudgetedTotalCart())) ? null : _chartDto.getUnbudgetedTotalCart();
			String totalUnbudgetedPendingAmt =  ("0".equals(_chartDto.getUnbudgetedTotalPending())) ? null : _chartDto.getUnbudgetedTotalPending();
			
			//Here we retrieve data for cost center specific budgeted graph,
			//and replace Zero's with null to remove unnecessary chunks in IE.
			String allocatedAmt = replaceZeroWithNull(_chartDto.getAllocatedBudget()).toString();
			String spentAmt = replaceZeroWithNull(_chartDto.getSpentAmount()).toString();
			String pendingAmt = replaceZeroWithNull(_chartDto.getPendingAppr()).toString();
			String cartAmt = replaceZeroWithNull(_chartDto.getShoppingCart()).toString();
			String overBudgetAmt = replaceZeroWithNull(_chartDto.getOverBudget()).toString();
			String unusedBudgetAmt = replaceZeroWithNull(_chartDto.getUnusedBudget()).toString();
			ArrayList costCenterNamesList = _chartDto.getCostCenterNames();
			
			//Here we retrieve data for cost center specific non-budgeted graph,
			//and replace Zero's with null to remove unnecessary chunks in IE.
			String unbudgetedSpentAmt = replaceZeroWithNull(_chartDto.getUnbudgetedSpentList()).toString();
			String unbudgetedPendingAmt = replaceZeroWithNull(_chartDto.getUnbudgetedPendingList()).toString();
			String unbudgetedCartAmt = replaceZeroWithNull(_chartDto.getUnbudgetedCartList()).toString();
			ArrayList unbudgetedCostCenterNamesList = _chartDto.getUnbudgetedCostCenterNamesList();
			
			//Here we retrieve data for total amounts for combined budgets.
			//and replace Zero's with null to remove unnecessary chunks in IE.
			String combinedTotalSpentAmt =  ("0".equals(_chartDto.getCombinedTotalSpent())) ? null : _chartDto.getCombinedTotalSpent();
			String combinedTotalPendingAmt =  ("0".equals(_chartDto.getCombinedTotalPending())) ? null : _chartDto.getCombinedTotalPending();
			String combinedTotalCartAmt =  ("0".equals(_chartDto.getCombinedTotalCart())) ? null : _chartDto.getCombinedTotalCart();
			
			//Here we retrieve amounts for combined budgets.
			//and replace Zero's with null to remove unnecessary chunks in IE.
			String combinedSpentAmt = replaceZeroWithNull(_chartDto.getCombinedSpentList()).toString();
			String combinedPendingAmt = replaceZeroWithNull(_chartDto.getCombinedPendingList()).toString();
			String combinedCartAmt = replaceZeroWithNull(_chartDto.getCombinedCartList()).toString();
			ArrayList combinedCostCenterNamesList = _chartDto.getCombinedCostCenterNamesList();
			
			
			//These calculations are for the budgeted cost centers.
			String costCenterNames = "";
			int numCostCenters = costCenterNamesList.size();
			int marginTop = 37;
			int marginBottom = 80;
			int ccContainerHeight = 60 * numCostCenters + marginTop + marginBottom; 
			int totalBgtcontainerHeight = 60 * 1 + marginTop + marginBottom;
			for(int i = 0;costCenterNamesList != null && i<costCenterNamesList.size();i++)
			{
				costCenterNames+="'"+costCenterNamesList.get(i)+"',";
			}
			if(costCenterNamesList != null && costCenterNamesList.size()>0)
			{
				costCenterNames = costCenterNames.substring(0, costCenterNames.length()-1);
			}
			
			//These calculations are for the non-budgeted cost centers.
			String unbudgetedCostCenterNames = "";
			numCostCenters = unbudgetedCostCenterNamesList.size();
			int unbudgetedCcContainerHeight = 60 * numCostCenters + marginTop + marginBottom; 
			for(int i = 0; unbudgetedCostCenterNamesList != null && i < unbudgetedCostCenterNamesList.size();i++)
			{
				unbudgetedCostCenterNames += "'" + unbudgetedCostCenterNamesList.get(i)+"',";
			}
			if(unbudgetedCostCenterNamesList != null && unbudgetedCostCenterNamesList.size()>0)
			{
				unbudgetedCostCenterNames = unbudgetedCostCenterNames.substring(0, unbudgetedCostCenterNames.length()-1);
			}
			
			//These calculations are for the combined budgeted cost centers.
			String combinedCostCenterNames = "";
			numCostCenters = combinedCostCenterNamesList.size();
			int combinedCcContainerHeight = 30 * numCostCenters + marginTop + marginBottom; 
			for(int i = 0; combinedCostCenterNamesList != null && i < combinedCostCenterNamesList.size();i++)
			{
				combinedCostCenterNames += "'" + combinedCostCenterNamesList.get(i)+"',";
			}
			if(combinedCostCenterNamesList != null && combinedCostCenterNamesList.size()>0)
			{
				combinedCostCenterNames = combinedCostCenterNames.substring(0, combinedCostCenterNames.length()-1);
			}
			
			int totalNonBudgetedContainerHeight = 30 + marginTop + marginBottom;
			//ClwI18nUtil.getCurrencyAlignment is used to format currency amount based on user locale
			String [] currencyFormatter = ClwI18nUtil.getCurrencyAlignment(request,null,null," ");
			ArrayList<Character> numberSeparators = ClwI18nUtil.getDecimalAndGroupingSeparator(request,null);
			Character decimalSeparator = numberSeparators.get(0);
			Character groupingSeparator = numberSeparators.get(1);
			
			if(getGraph().equals(Constants.TOTAL_BUDGETED)) {  
				if(!existsBudgetedAmounts) {
					return SKIP_BODY;
				}
			} else if(getGraph().equals(Constants.TOTAL_UNBUDGETED)) {
				if(!existsNonBudgetedAmounts) {
					return SKIP_BODY;
				}
				
			} else if(getGraph().equals(Constants.TOTAL_COMBINED)) {
				 if(!existsCombinedAmounts) {
					 return SKIP_BODY;
				 }
				
			} else if(getGraph().equals(Constants.BUDGETED_COST_CENTER)) { 
				if(!existsBudgetedAmounts) {
					 return SKIP_BODY;
				}
			} else if(getGraph().equals(Constants.UNBUDGETED_COST_CENTER)) {
				if(!existsNonBudgetedAmounts) {
					 return SKIP_BODY;
				}
				
			} else if(getGraph().equals(Constants.COMBINED_COST_CENTER)) {
				if(!existsCombinedAmounts) {
					 return SKIP_BODY;
				}
			}
			
			StringBuffer htmlBuffer  = new StringBuffer();
			htmlBuffer.append("<script type=\"text/javascript\">");
			htmlBuffer.append("var browserName=navigator.appName;"); 
			htmlBuffer.append("var yVal = 5;");
			htmlBuffer.append("if (browserName==\"Microsoft Internet Explorer\")");
			htmlBuffer.append("{");
			htmlBuffer.append("yVal = 8;");
			htmlBuffer.append("}");
			if(currencyFormatter[2].equals(Constants.POLLOCK_STORE_GLOBAL_CODE)){// For pollock store
			if(currencyFormatter[0].equals("localeCodeOrPriceCurrency")){
				htmlBuffer.append(" function formatChartDataLabel(pct, value) {if (value == 0 || value == null || pct < 1 ) {return \"\";} else if (pct < 22) {return \"*\";} else {return  Highcharts.numberFormat(value, 4)+'"+currencyFormatter[1]+"' ;}}");
				htmlBuffer.append(" function formatChartTooltip(series, center, value) { if(center == '') return series +':'+ Highcharts.numberFormat(value, 4)+'"+currencyFormatter[1]+"'; else return '<strong>' + center + '</strong><br>' +series +':'+ Highcharts.numberFormat(value, 4)+'"+currencyFormatter[1]+"';}");
			}
			else
				if(currencyFormatter[0].equals(Constants.DISPLAY_CURRENCY_LEFT)){
				htmlBuffer.append(" function formatChartDataLabel(pct, value) {if (value == 0 || value == null || pct < 1) {return \"\";} else if (pct < 22) {return \"*\";} else {return '"+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 4);}}");
				htmlBuffer.append(" function formatChartTooltip(series, center, value) {if(center == '') return series + ': "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 4); else return '<strong>' + center + '</strong><br>' +series + ': "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 4);}");
				}
				else{
					htmlBuffer.append(" function formatChartDataLabel(pct, value) {if (value == 0 || value == null || pct < 1) {return \"\";} else if (pct < 22) {return \"*\";} else {return  Highcharts.numberFormat(value, 4)+'"+currencyFormatter[1]+"' ;}}");
					htmlBuffer.append(" function formatChartTooltip(series, center, value) {if(center == '') return series  Highcharts.numberFormat(value, 4)+': "+currencyFormatter[1]+"'; else return '<strong>' + center + '</strong><br>' +series   Highcharts.numberFormat(value, 4)+': "+currencyFormatter[1]+"';}");					
				}
			}
			else{
				if(currencyFormatter[0].equals("localeCodeOrPriceCurrency")){
					htmlBuffer.append(" function formatChartDataLabel(pct, value) {if (value == 0 || value == null || pct < 1) {return \"\";} else if (pct < 12) {return \"*\";} else {return  Highcharts.numberFormat(value, 2)+'"+currencyFormatter[1]+"' ;}}");
					htmlBuffer.append(" function formatChartTooltip(series, center, value) {if(center == '') return series  +':'+ Highcharts.numberFormat(value, 2)+'"+currencyFormatter[1]+"'; else return '<strong>' + center + '</strong><br>' +series  +':'+ Highcharts.numberFormat(value, 2)+'"+currencyFormatter[1]+"';}");
				}
				else
					if(currencyFormatter[0].equals(Constants.DISPLAY_CURRENCY_LEFT)){
					htmlBuffer.append(" function formatChartDataLabel(pct, value) {if (value == 0 || value == null || pct < 1) {return \"\";} else if (pct < 12) {return \"*\";} else {return '"+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 2);}}");
					htmlBuffer.append(" function formatChartTooltip(series, center, value) {if(center == '') return series + ': "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 2); else return '<strong>' + center + '</strong><br>' +series + ': "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 2);}");
					}
					else{
						htmlBuffer.append(" function formatChartDataLabel(pct, value) {if (value == 0 || value == null || pct < 1) {return \"\";} else if (pct < 12) {return \"*\";} else {return  Highcharts.numberFormat(value, 2)+'"+currencyFormatter[1]+"' ;}}");
						htmlBuffer.append(" function formatChartTooltip(series, center, value) {if(center == '') return series + ': "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 2); else return '<strong>' + center + '</strong><br>' +series + ': "+currencyFormatter[1]+"' + Highcharts.numberFormat(value, 2);}");					
					}
			}
			htmlBuffer.append("$(document).ready(function() {	"+"new Highcharts.Chart(" +"{"+"chart: {");
			htmlBuffer.append("style: {fontFamily: '"+getFontFamily()+"'},");

			if(getGraph().equals(Constants.TOTAL_BUDGETED)) { // for rendering the total budget bar, if totalBudget attribute is set to true in jsp 
				htmlBuffer.append("renderTo: '"+_totalBgtContainer+"',");
				htmlBuffer.append("height: "+totalBgtcontainerHeight+",");
			} else if(getGraph().equals(Constants.TOTAL_UNBUDGETED)) { 
				htmlBuffer.append("renderTo: '"+_totalUnBgtContainer+"',");
				htmlBuffer.append("height: "+totalNonBudgetedContainerHeight+",");
			} else if(getGraph().equals(Constants.TOTAL_COMBINED)) { 
				htmlBuffer.append("renderTo: '"+_totalCombinedContainer+"',");
				htmlBuffer.append("height: "+totalNonBudgetedContainerHeight+",");
			} else if(getGraph().equals(Constants.BUDGETED_COST_CENTER)) {  //else ,for rendering cost center bars
				htmlBuffer.append("renderTo: '"+_costCenterContainer+"',");
				htmlBuffer.append("height: "+ccContainerHeight+",");
			} else if(getGraph().equals(Constants.UNBUDGETED_COST_CENTER)) {
				htmlBuffer.append("renderTo: '"+_unbudgetedCostCenterContainer+"',");
				htmlBuffer.append("height: "+unbudgetedCcContainerHeight+",");
			} else if(getGraph().equals(Constants.COMBINED_COST_CENTER)) {
				htmlBuffer.append("renderTo: '"+_combinedCostCenterContainer+"',");
				htmlBuffer.append("height: "+combinedCcContainerHeight+",");
			}
			htmlBuffer.append("marginTop: "+marginTop+",");
			htmlBuffer.append("marginBottom: "+marginBottom+",");
			htmlBuffer.append("defaultSeriesType: 'bar'},");
			htmlBuffer.append(" legend: {align: 'center',verticalAlign: 'bottom', backgroundColor: '#fef9de', reversed: true},title: {text: '',align:'left'},");// needed to get legend in right order
			
			htmlBuffer.append("xAxis: {");
			if((getGraph().equals(Constants.TOTAL_BUDGETED) || getGraph().equals(Constants.TOTAL_UNBUDGETED) 
					|| getGraph().equals(Constants.TOTAL_COMBINED))) {  // cost center names are not required for total budget bar 
				htmlBuffer.append("categories: ['']"+"},");
			} else if(getGraph().equals(Constants.BUDGETED_COST_CENTER)) {
				htmlBuffer.append("categories: ["+costCenterNames+"]"+"},");
			} else if(getGraph().equals(Constants.UNBUDGETED_COST_CENTER)) {
				htmlBuffer.append("categories: ["+unbudgetedCostCenterNames+"]"+"},");
			} else if(getGraph().equals(Constants.COMBINED_COST_CENTER)) {
				htmlBuffer.append("categories: ["+combinedCostCenterNames+"]"+"},");
			}
			htmlBuffer.append("tooltip: {formatter: function() {if(this.series.name=='padding') return false; else return formatChartTooltip(this.series.name, this.x, this.y);}},");
			htmlBuffer.append("yAxis: {showFirstLabel: true,showLastLabel: ");
			if(getGraph().equals(Constants.BUDGETED_COST_CENTER) || getGraph().equals(Constants.UNBUDGETED_COST_CENTER)
					|| getGraph().equals(Constants.COMBINED_COST_CENTER)) {
				htmlBuffer.append(" true,tickInterval: 20");
			} else {
				htmlBuffer.append(" false, maxPadding: 0.1 ");
			}
			
			htmlBuffer.append("},");
			htmlBuffer.append(" plotOptions: {"+"series: {"+"dataLabels: {"+"enabled: true,"+"formatter: function() {"+"return formatChartDataLabel(this.percentage,this.y);},");
			htmlBuffer.append("align: 'right',");
			htmlBuffer.append("color: '#FFFFFF',");
			htmlBuffer.append("style: {fontSize: '11px' },");
			htmlBuffer.append("x: -3,"+"y: yVal"+"},");
			
			htmlBuffer.append("borderRadius: 3,");
			htmlBuffer.append("pointWidth: 19,");
			htmlBuffer.append( "groupPadding: 0.15,");
			if((getGraph().equals(Constants.TOTAL_BUDGETED) || getGraph().equals(Constants.TOTAL_UNBUDGETED) 
					|| getGraph().equals(Constants.TOTAL_COMBINED))) {
				htmlBuffer.append( "stacking: 'normal'}");// stacking is normal for total budget bar
			}
			else
			{
				htmlBuffer.append( "stacking: 'percent'}"); // stacking is percent to show cost center bars of equal length
			}
			htmlBuffer.append("},");
			htmlBuffer.append(  "series: [{");
			if(getGraph().equals(Constants.TOTAL_BUDGETED)) {
				htmlBuffer.append("data: ["+totalUnused +"],"+"name: '"+unused+"',stack: 'spent',color:'"+getUnusedColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				if(!isReportingChart()){// for budgets-at-glance, cart segment is not required.
					htmlBuffer.append("data: ["+totalCartAmt +"],"+ "name: '"+cart+"',stack: 'spent',color:'"+getCartColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				}
				htmlBuffer.append("data: ["+totalPendingAmt +"],"+"name: '"+pending+"',stack: 'spent',color:'"+getPendingApprColor()+"'}, {");
				htmlBuffer.append( "data: ["+totalSpentAmt+"],"+"name: '"+spent+"',stack: 'spent',color:'"+getSpentColor()+"'}, {");
				
				htmlBuffer.append( "data: ["+totalOverAmt +"],"+  "name: '"+over+"',stack: 'budget',color:'"+getOverColor()+"'}, {");
				htmlBuffer.append("data: ["+totalBudgetAmt +"],"+"name: '"+budget+"',stack: 'budget',color:'"+getBudgetColor()+"'}]});}); ");
			} else if(getGraph().equals(Constants.TOTAL_UNBUDGETED)) {
				//htmlBuffer.append("data: ["+totalUnbudgetdUnusedAmt +"],"+"name: '"+unused+"',stack: 'spent',color:'"+getUnusedColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				if(!isReportingChart()){// for budgets-at-glance, cart segment is not required.
					htmlBuffer.append("data: ["+totalUnbudgetdCartAmt +"],"+ "name: '"+cart+"',stack: 'spent',color:'"+getCartColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				}
				htmlBuffer.append("data: ["+totalUnbudgetedPendingAmt +"],"+"name: '"+pending+"',stack: 'spent',color:'"+getPendingApprColor()+"'}, {");
				htmlBuffer.append( "data: ["+totalUnbudgetedSpentAmt+"],"+"name: '"+spent+"',stack: 'spent',color:'"+getSpentColor()+"'}]});}); ");
				
				//htmlBuffer.append( "data: ["+totalUnbudgetedOverAmt +"],"+  "name: '"+over+"',stack: 'budget',color:'"+getOverColor()+"'}, {");
				//htmlBuffer.append("data: ["+totalUnbudgetedAmt +"],"+"name: '"+budget+"',stack: 'budget',color:'"+getBudgetColor()+"'}]});}); ");
			} else if(getGraph().equals(Constants.TOTAL_COMBINED)) {
				if(!isReportingChart()){// for budgets-at-glance, cart segment is not required.
					htmlBuffer.append("data: ["+combinedTotalCartAmt +"],"+ "name: '"+cart+"',stack: 'spent',color:'"+getCartColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				}
				htmlBuffer.append("data: ["+combinedTotalPendingAmt +"],"+"name: '"+pending+"',stack: 'spent',color:'"+getPendingApprColor()+"'}, {");
				htmlBuffer.append( "data: ["+combinedTotalSpentAmt+"],"+"name: '"+spent+"',stack: 'spent',color:'"+getSpentColor()+"'}]});}); ");
//				htmlBuffer.append("data: "+allocatedAmt +","+"name: '"+budget+"',stack: 'budget',color:'"+getBudgetColor()+"'}]});}); ");
			} else if(getGraph().equals(Constants.BUDGETED_COST_CENTER)) {
				htmlBuffer.append("data: "+unusedBudgetAmt +","+"name: '"+unused+"',stack: 'spent',color:'"+getUnusedColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				if(!isReportingChart()){
					htmlBuffer.append("data: "+cartAmt +","+ "name: '"+cart+"',stack: 'spent',color:'"+getCartColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				}
				htmlBuffer.append("data: "+pendingAmt +","+"name: '"+pending+"',stack: 'spent',color:'"+getPendingApprColor()+"'}, {");
				htmlBuffer.append( "data: "+spentAmt+","+"name: '"+spent+"',stack: 'spent',color:'"+getSpentColor()+"'}, {");
				htmlBuffer.append( "data: "+overBudgetAmt +","+  "name: '"+over+"',stack: 'budget',color:'"+getOverColor()+"'}, {");
				htmlBuffer.append("data: "+allocatedAmt +","+"name: '"+budget+"',stack: 'budget',color:'"+getBudgetColor()+"'}]});}); ");
			} else if(getGraph().equals(Constants.UNBUDGETED_COST_CENTER)) {
				//htmlBuffer.append("data: "+unbudgetedUnusedAmt +","+"name: '"+unused+"',stack: 'spent',color:'"+getUnusedColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				if(!isReportingChart()){
					htmlBuffer.append("data: "+unbudgetedCartAmt +","+ "name: '"+cart+"',stack: 'spent',color:'"+getCartColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				}
				htmlBuffer.append("data: "+unbudgetedPendingAmt +","+"name: '"+pending+"',stack: 'spent',color:'"+getPendingApprColor()+"'}, {");
				htmlBuffer.append( "data: "+unbudgetedSpentAmt +","+"name: '"+spent+"',stack: 'spent',color:'"+getSpentColor()+"'}]});}); ");
				//htmlBuffer.append( "data: "+unbudgetedOverAmt +","+  "name: '"+over+"',stack: 'budget',color:'"+getOverColor()+"'}, {");
				//htmlBuffer.append("data: "+unbudgetedAllocatedAmt +","+"name: '"+budget+"',stack: 'budget',color:'"+getBudgetColor()+"'}]});}); ");
			} else if(getGraph().equals(Constants.COMBINED_COST_CENTER)) {
				if(!isReportingChart()){
					htmlBuffer.append("data: "+combinedCartAmt +","+ "name: '"+cart+"',stack: 'spent',color:'"+getCartColor()+"', dataLabels: {color: '#0F0F0F'}}, {");
				}
				htmlBuffer.append("data: "+combinedPendingAmt +","+"name: '"+pending+"',stack: 'spent',color:'"+getPendingApprColor()+"'}, {");
				htmlBuffer.append( "data: "+combinedSpentAmt +","+"name: '"+spent+"',stack: 'spent',color:'"+getSpentColor()+"'}]});}); ");
			} 
			if(_theme!=null){
				htmlBuffer.append(" Highcharts.setOptions(Highcharts."+_theme+"); ");
			}
			htmlBuffer.append("Highcharts.setOptions({lang: {decimalPoint: '"+decimalSeparator+"',thousandsSep: '"+groupingSeparator+"'}});");
			htmlBuffer.append(" </script> ");
			out.print(htmlBuffer.toString());
			}
		catch(IOException ioe)
		{
			log.error("Unexpected exception in LocationBudgetTag: ", ioe);
		}
		return SKIP_BODY;
	}
	
	/*Private method to set colors and font for bar segments */
	private void setStyles()
	{
		Map styles = getStyles();
		if(styles!=null){
			if(styles.get(Constants.BUDGET_COLOR)!=null){// if true set specified color,else use default color
				setBudgetColor((String)styles.get(Constants.BUDGET_COLOR));
			}
			if(styles.get(Constants.OVER_COLOR)!=null){// if true set specified color,else use default color
				setOverColor((String)styles.get(Constants.OVER_COLOR));
			}
			if(styles.get(Constants.SPENT_COLOR)!=null){// if true set specified color,else use default color
				setSpentColor((String)styles.get(Constants.SPENT_COLOR));
			}
			if(styles.get(Constants.CART_COLOR)!=null){// if true set specified color,else use default color
				setCartColor((String)styles.get(Constants.CART_COLOR));
			}
			if(styles.get(Constants.PENDING_COLOR)!=null){// if true set specified color,else use default color
				setPendingApprColor((String)styles.get(Constants.PENDING_COLOR));
			}
			if(styles.get(Constants.UNUSED_COLOR)!=null){// if true set specified color,else use default color
				setUnusedColor((String)styles.get(Constants.UNUSED_COLOR));
			}
			if(styles.get(Constants.FONT_FAMILY)!=null){// if true set specified font,else use default font
				setFontFamily((String)styles.get(Constants.FONT_FAMILY));
			}
		}
	}
	// Fix for small chunks issue in IE browser.
    public static ArrayList replaceZeroWithNull(ArrayList<BigDecimal> chartValues){
    	for(int index = 0 ;index < chartValues.size();index++){
    		if(chartValues.get(index) != null && chartValues.get(index).compareTo(BigDecimal.ZERO) == 0){
    			chartValues.set(index, null);
    		}
    	}
    	return chartValues;
    }
}





 


