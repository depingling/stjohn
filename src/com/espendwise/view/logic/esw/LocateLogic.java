package com.espendwise.view.logic.esw;

import java.util.Collections;
import java.util.Comparator;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountUIView;
import com.cleanwise.service.api.value.AccountUIViewVector;
import com.cleanwise.view.forms.LocateReportAccountForm;
import com.cleanwise.view.utils.Constants;
import com.espendwise.view.forms.esw.LocateForm;

public class LocateLogic {
    private static final Logger log = Logger.getLogger(LocateLogic.class);

    public static int[] getSelectedIds(String idsString, String delimiter) {
        if (Utility.isSet(idsString)) {
            String ids[] = idsString.split(delimiter);
            int result[] = new int[ids.length];
            int i = 0;
            for (String id : ids) {
                result[i++] = Utility.parseInt(id);
            }
            return result;
        }
        return null;
    }

    public static void sortAccounts(final LocateForm form) {
        if (form != null) {
            if (Utility.isSet(form.getSortField()) == false) {
                form.setSortField(Constants.ACCOUNT_SORT_FIELD_NAME);
            }
            if (Utility.isSet(form.getSortOrder()) == false) {
                form.setSortOrder(Constants.ACCOUNT_SORT_ORDER_ASCENDING);
            }
            LocateReportAccountForm searchForm = form
                    .getLocateReportAccountForm();
            if (searchForm != null) {
                AccountUIViewVector accounts = searchForm.getAccounts();
                if (accounts != null && accounts.size() > 1) {
                    Collections.sort(accounts, new Comparator<AccountUIView>() {
                        public int compare(AccountUIView o1, AccountUIView o2) {
                            String s1 = o1.getBusEntity().getShortDesc();
                            String s2 = o2.getBusEntity().getShortDesc();
                            if (Constants.ACCOUNT_SORT_FIELD_CITY.equals(form
                                    .getSortField())) {
                                s1 = o1.getPrimaryAddress().getCity();
                                s2 = o2.getPrimaryAddress().getCity();
                            } else if (Constants.ACCOUNT_SORT_FIELD_STATE
                                    .equals(form.getSortField())) {
                                s1 = o1.getPrimaryAddress()
                                        .getStateProvinceCd();
                                s2 = o2.getPrimaryAddress()
                                        .getStateProvinceCd();
                            } else if (Constants.ACCOUNT_SORT_FIELD_TYPE
                                    .equals(form.getSortField())) {
                                s1 = o1.getAccountType().getValue();
                                s2 = o2.getAccountType().getValue();
                            } else if (Constants.ACCOUNT_SORT_FIELD_STATUS
                                    .equals(form.getSortField())) {
                                s1 = o1.getBusEntity().getBusEntityStatusCd();
                                s2 = o2.getBusEntity().getBusEntityStatusCd();
                            }
                            s1 = Utility.strNN(s1);
                            s2 = Utility.strNN(s2);
                            if (Constants.ACCOUNT_SORT_ORDER_ASCENDING
                                    .equals(form.getSortOrder())) {
                                return s1.compareToIgnoreCase(s2);
                            } else {
                                return s2.compareToIgnoreCase(s1);
                            }
                        };
                    });
                    searchForm.setAccounts(accounts);
                }
            }
        }
    }
}