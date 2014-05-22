package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.FiscalCalenderDetailDataVector;
import com.cleanwise.service.api.value.FiscalCalenderDetailData;
import com.cleanwise.service.api.value.FiscalCalenderView;
import com.cleanwise.service.api.value.FiscalCalenderViewVector;


public class FiscalCalendarUtility {

    public static String getMmdd(FiscalCalenderDetailDataVector pFiscalCalenderDetails, int pPeriod) {
        try {
            return getMmdd(pFiscalCalenderDetails, pPeriod, false);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMmdd(FiscalCalenderDetailDataVector pFiscalCalenderDetails, int pPeriod, boolean pThrowExc) throws Exception {

        for (Object oFiscalDetail : pFiscalCalenderDetails) {
            FiscalCalenderDetailData fiscalDetailData = (FiscalCalenderDetailData) oFiscalDetail;
            if (fiscalDetailData.getPeriod() == pPeriod) {
                return fiscalDetailData.getMmdd();
            }
        }

        if (pThrowExc) {
            throw new Exception("Unknown budget period");
        }

        return null;

    }

    public static int getNumberOfBudgetPeriods(FiscalCalenderView pFiscalCalender) {
        if (pFiscalCalender == null) {
            return 0;
        }
        int ct = 0;
        while (true) {
            if (Utility.isSet(getMmdd(pFiscalCalender.getFiscalCalenderDetails(), ct + 1))) {
                ct++;
            } else {
                break;
            }
        }
        return ct;
    }

    public static int getMaxNumberofBudgetPeriods(FiscalCalenderViewVector pFiscalCalenders) {
        int maxNumber = 0;
        if (pFiscalCalenders != null && !pFiscalCalenders.isEmpty()) {
            for (Object oFiscalCalender : pFiscalCalenders) {
                if (oFiscalCalender != null) {
                    FiscalCalenderView fiscalCalender = (FiscalCalenderView) (oFiscalCalender);
                    int numberOfBudgetPeriods = getNumberOfBudgetPeriods(fiscalCalender);
                    if (numberOfBudgetPeriods > maxNumber) {
                        maxNumber = numberOfBudgetPeriods;
                    }
                }
            }
        }
        return maxNumber;
    }

    public static int getMaxSizeofBudgetPeriods(FiscalCalenderViewVector pFiscalCalenders) {
        int maxSize = 0;
        if (pFiscalCalenders != null && !pFiscalCalenders.isEmpty()) {
            for (Object oFiscalCalender : pFiscalCalenders) {
                if (oFiscalCalender != null) {
                    FiscalCalenderView fiscalCalender = (FiscalCalenderView) (oFiscalCalender);
                    if (fiscalCalender.getFiscalCalenderDetails().size() > maxSize) {
                        maxSize = fiscalCalender.getFiscalCalenderDetails().size();
                    }
                }
            }
        }
        return maxSize;
    }

}
