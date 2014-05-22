package com.cleanwise.service.api.value;

import java.util.Collections;
import java.util.Comparator;
import java.math.BigDecimal;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * Date:         24.01.2007
 * Time:         14:51:58
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */

public class ShoppingCartItemBaseDataVector extends java.util.LinkedList implements Comparator {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -2534079908959862974L;

    public static final int ITEM_ID = 0;
    private static final int GET_COST_ALL_ITEMS = 0;
    int sortField;

       /**
        * Default constructor
        */
       public ShoppingCartItemBaseDataVector(){}


       /**
        * Sort
        */
       public void sortByItemId(){
           sortField = ITEM_ID;
           Collections.sort(this,this);
       }


       /**
        * Compares 2 objects
        */
       public int compare(Object o1, Object o2) {
           int retcode = -1;
           ShoppingCartItemBase obj1 = (ShoppingCartItemBase )o1;
           ShoppingCartItemBase obj2 = (ShoppingCartItemBase)o2;

           switch(sortField){
               case ITEM_ID:
                   int i1 = obj1.getItemData().getItemId();
                   int i2 = obj2.getItemData().getItemId();
                   retcode = i1-i2;
                   break;
               default:
                   throw new RuntimeException("Invalid sort option");
           }

           return retcode;
       }


       /**
        *Returns the total cost of the shopping cart items (i.e. subtotal).
        */
       public double getItemsCost(){
           return getItemsCost(GET_COST_ALL_ITEMS);
       }







       /**
        *Returns the total cost of the shopping cart items (i.e. subtotal).
        */
       private double getItemsCost(int whatToSum){
           BigDecimal cost = new BigDecimal(0);

           for (int ii = 0; ii < this.size(); ii++)
           {
               ShoppingCartItemBase sciD = (ShoppingCartItemBase)this.get(ii);
               boolean doSum = false;
               if(GET_COST_ALL_ITEMS==whatToSum){
                   doSum = true;
               }
               if(doSum){
                   BigDecimal priceBD = new BigDecimal(sciD.getPrice());
				   priceBD = priceBD.multiply(new BigDecimal(sciD.getQuantity()));
                   cost = cost.add(priceBD);
               }
           }

           double costD = cost.doubleValue();

           return costD ;
       }

}
