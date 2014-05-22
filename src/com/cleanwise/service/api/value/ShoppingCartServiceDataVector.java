package com.cleanwise.service.api.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Title:        ShoppingCartServiceDataVector
 * Description:  Container object for ShoppngCartServiceData objects
 * Purpose:      Provides container storage for ShoppingCartServiceData objects.
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         24.01.2007
 * Time:         14:43:19
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class ShoppingCartServiceDataVector extends ShoppingCartItemBaseDataVector
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 7498462355602615609L;

    public boolean addService(ShoppingCartServiceData service) {
        boolean successFl = false;
        boolean existFl = false;
        if (service != null && service.getAssetData() != null && service.getService() != null) {
            Iterator it = this.iterator();
            while (it.hasNext()) {
                ShoppingCartServiceData useServices = (ShoppingCartServiceData) it.next();
                if (useServices.getItemId() == service.getItemId()
                        && useServices.getAssetData().getAssetId() == service.getAssetData().getAssetId()) {
                    existFl = true;
                    break;
                }
            }
            if (!existFl) {
                this.add(service);
                successFl = true;
            }
        }

        return successFl;
    }


}

