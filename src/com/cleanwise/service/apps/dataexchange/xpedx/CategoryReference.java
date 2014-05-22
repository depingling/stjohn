package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.util.Utility;

import java.util.List;


public class CategoryReference extends Reference {

    public CategoryReference(String pMasterCustomerName, String pName, String... pParents) {

        String adminName = pMasterCustomerName;
        for (String parent : pParents) {
            adminName += ",";
            adminName += parent;
        }

        create(pName, adminName);
    }

    public CategoryReference(String pShortDesc, String pLongDesc) {
        create(pShortDesc, Utility.strNN(pLongDesc));
    }

    public CategoryReference(String pMasterCustomerName, String pName, List<String> pParents) {

        String adminName = pMasterCustomerName;
        for (String parent : pParents) {
            adminName += ",";
            adminName += parent;
        }

        create(pName, adminName);
    }

    public String getCategoryName() {
        return (String) get(0);
    }

    public String getAdminName() {
        return (String) get(1);
    }

}

