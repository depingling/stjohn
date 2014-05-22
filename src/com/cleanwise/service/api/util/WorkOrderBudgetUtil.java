package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.BudgetSpentShortViewVector;
import com.cleanwise.service.api.value.BudgetSpentShortView;

import java.util.*;
import java.math.BigDecimal;

/**
 */
public class WorkOrderBudgetUtil {

    BudgetSpentShortViewVector budgetSpentInfo;
    private static final String DELIM = ":";

    private HashMap container;

    private HashMap yearMap;
    private HashMap periodMap;
    private HashMap ccMap;
    private HashMap beMap;

    private HashSet keys;
    private HashMap yearKeys;
    private HashMap periodKeys;
    private HashMap beKeys;
    private HashMap ccKeys;
    private static String className = "WorkOrderBudgetUtil";

    public WorkOrderBudgetUtil(BudgetSpentShortViewVector budgetSpentInfo) {

        this.container = new HashMap();

        this.ccMap = new HashMap();
        this.periodMap = new HashMap();
        this.yearMap = new HashMap();
        this.beMap = new HashMap();

        this.beKeys = new HashMap();
        this.ccKeys = new HashMap();
        this.yearKeys = new HashMap();
        this.periodKeys = new HashMap();
        this.ccKeys = new HashMap();

        this.keys = new HashSet();

        parse(budgetSpentInfo);

//        log("WorkOrderBudgetUtil ccMap=>"+ccMap);
//        log("WorkOrderBudgetUtil periodMap=>"+periodMap);
//        log("WorkOrderBudgetUtil yearMap=>"+yearMap);
//        log("WorkOrderBudgetUtil beMap=>"+beMap);
//
//        log("WorkOrderBudgetUtil yearKeys=>"+beKeys);
//        log("WorkOrderBudgetUtil yearKeys=>"+yearKeys);
//        log("WorkOrderBudgetUtil periodKeys=>"+periodKeys);
//        log("WorkOrderBudgetUtil ccKeys=>"+ccKeys);
//        log("WorkOrderBudgetUtil keys=>"+keys);
    }

    private void parse(BudgetSpentShortViewVector budgetSpentInfo) {
        Iterator it = budgetSpentInfo.iterator();


        while (it.hasNext()) {
            BudgetSpentShortView infoView = (BudgetSpentShortView) it.next();

            String key = getKey(infoView);

            String beKey = getBeKey(infoView);
            String yearKey = getYearKey(infoView);
            String periodKey = getPeriodKey(infoView);
            String ccKey = getCostCenterKey(infoView);

            addKeyId(beMap, beKey, key);
            addKeyId(yearMap, yearKey, key);
            addKeyId(periodMap, periodKey, key);
            addKeyId(ccMap, ccKey, key);

            container.put(key, infoView);

            addKeyId(beKeys, beKey, yearKey);
            addKeyId(yearKeys, yearKey, periodKey);
            addKeyId(periodKeys, periodKey, ccKey);
            addKeyId(ccKeys, ccKey, ccKey);

            keys.add(key);
        }
    }


    private void addKeyId(HashMap map, String yearKey, String key) {
        List idKeys = (List) map.get(yearKey);
        if (idKeys == null) {
            ArrayList list = new ArrayList();
            list.add(key);
            map.put(yearKey, list);
        } else {
            idKeys.add(key);
        }
    }

    private String getKey(BudgetSpentShortView infoView) {
        return infoView.getBusEntityId()
                + DELIM +
                infoView.getBudgetYear()
                + DELIM +
                infoView.getBudgetPeriod()
                + DELIM +
                infoView.getCostCenterId();
    }
    private String getBeKey(BudgetSpentShortView infoView) {
        return getBeKey(infoView.getBusEntityId());
    }

    private String getBeKey(int busEntityId) {
        return getBeKey(String.valueOf(busEntityId));
    }

    private String getBeKey(String s) {
        return s;
    }

    private String getYearKey(BudgetSpentShortView infoView) {
        return getYearKey(infoView.getBusEntityId(),infoView.getBudgetYear());
    }

    private String getYearKey(int busEntityId,int budgetYear) {
        return getYearKey(String.valueOf(busEntityId),String.valueOf(budgetYear));
    }

    private String getYearKey(String busEntityId, String budgetYear) {
        return getBeKey(busEntityId) + DELIM + budgetYear;
    }

    private String getPeriodKey(BudgetSpentShortView infoView) {
        return getPeriodKey(infoView.getBusEntityId(),infoView.getBudgetYear(), infoView.getBudgetPeriod());
    }

    private String getPeriodKey(int busEntityId,int budgetYear, int budgetPeriod) {
        return getPeriodKey(String.valueOf(busEntityId),String.valueOf(budgetYear),String.valueOf(budgetPeriod));
    }

    private String getPeriodKey(String busEntityId ,String budgetYear, String budgetPeriod) {
        return getYearKey(busEntityId,budgetYear) + DELIM + budgetPeriod;
    }

    private String getCostCenterKey(BudgetSpentShortView infoView) {
        return getCostCenterKey(infoView.getBusEntityId(),infoView.getBudgetYear(), infoView.getBudgetPeriod(), infoView.getCostCenterId());
    }

    private String getCostCenterKey(int busEntityId,int budgetYear, int budgetPeriod, int costCenterId) {
        return getCostCenterKey(String.valueOf(busEntityId),String.valueOf(budgetYear),String.valueOf(budgetPeriod),String.valueOf(costCenterId));
    }

    private String getCostCenterKey(String busEntityId,String budgetYear, String budgetPeriod, String costCenterId) {
        return getPeriodKey(busEntityId,budgetYear, budgetPeriod) + DELIM + costCenterId;
    }

    public BudgetSpentShortViewVector getYearInfo(int busEntityId,int year) {
        return get(yearMap, getYearKey(busEntityId,year));
    }

    public BudgetSpentShortViewVector getPeriodInfo(int busEntityId,int year, int period) {
        return get(periodMap, getPeriodKey(busEntityId,year, period));
    }

    public BudgetSpentShortViewVector getCostCenterInfo(int busEntityId,int year, int period, int costCenterId) {
        return get(ccMap, getCostCenterKey(busEntityId,year, period, costCenterId));
    }

    public Collection getData(){
        return  container.values();
    }

    private BudgetSpentShortViewVector get(HashMap map, String mapKey) {
        BudgetSpentShortViewVector result = new BudgetSpentShortViewVector();
        List keyIds = (List) map.get(mapKey);
        if (keyIds != null) {
            Iterator it = keyIds.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                if (Utility.isSet(key)) {
                    Object data = container.get(key);
                    if (data != null) {
                        result.add(data);
                    }
                }
            }
        }
        return result;
    }

    public BigDecimal getAmountSpent(int busEntityId,int fiscalYear,int fiscalPeriod) {
        BudgetSpentShortViewVector budgetSpentCollection = getPeriodInfo(busEntityId,fiscalYear,fiscalPeriod);
        return getAmountSpent(budgetSpentCollection);
    }

    public BigDecimal getAmountAllocated(int busEntityId,int fiscalYear,int fiscalPeriod) {
        BudgetSpentShortViewVector budgetSpentCollection = getPeriodInfo(busEntityId,fiscalYear,fiscalPeriod);
        return getAmountAllocated(budgetSpentCollection);
    }

    private BigDecimal getAmountSpent(BudgetSpentShortViewVector budgetSpentCollection) {
        BigDecimal amount = null;
        Iterator it = budgetSpentCollection.iterator();
        while (it.hasNext()) {
            amount = Utility.addAmt(amount, ((BudgetSpentShortView) it.next()).getAmountSpent());
        }
        return amount;
    }

    private BigDecimal getAmountAllocated(BudgetSpentShortViewVector budgetSpentCollection) {
        BigDecimal amount = null;
        Iterator it = budgetSpentCollection.iterator();
        while (it.hasNext()) {
            amount = Utility.addAmt(amount, ((BudgetSpentShortView) it.next()).getAmountAllocated());
        }
        return amount;
    }

    public BigDecimal getAmountSpent(int busEntityId,int fiscalYear) {
        BudgetSpentShortViewVector budgetSpentCollection = getYearInfo(busEntityId,fiscalYear);
        return getAmountSpent(budgetSpentCollection);
    }

    public BigDecimal getAmountAllocated(int busEntityId,int fiscalYear) {
        BudgetSpentShortViewVector budgetSpentCollection = getYearInfo(busEntityId,fiscalYear);
        return getAmountAllocated(budgetSpentCollection);
    }

    public BudgetSpentShortViewVector getPeriodInfo(int busEntityId,int fiscalYear, int startPeriod, int finishPeriod) {
        BudgetSpentShortViewVector budgetSpentCollection = new BudgetSpentShortViewVector();
        for (int i = startPeriod; i <= finishPeriod; i++) {
            budgetSpentCollection.addAll(getPeriodInfo(busEntityId,fiscalYear, i));
        }
        return budgetSpentCollection;
    }
}
