package com.cleanwise.service.api.value;

import com.cleanwise.service.api.util.Utility;

import java.util.Collections;
import java.util.Comparator;
import java.math.BigDecimal;

/**
 * Title:        ShoppingCartItemDataVector
 * Description:  Container object for ShoppngCartItemData objects
 * Purpose:      Provides container storage for ShoppingCartItemData objects.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 */
public class ShoppingCartItemDataVector extends java.util.LinkedList implements Comparator {
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = -4879801797909043753L;
    public static final int PRODUCT_ID = 0;
    int sortField;

    /**
     * Default constructor
     */
    public ShoppingCartItemDataVector(){}
    
    
    /**
     * Sort
     */
    public void sortByProductId(){
        sortField = PRODUCT_ID;
        Collections.sort(this,this);
    }
    
    
    /**
     * Compares 2 objects
     */
    public int compare(Object o1, Object o2) {
        int retcode = -1;
        ShoppingCartItemData obj1 = (ShoppingCartItemData)o1;
        ShoppingCartItemData obj2 = (ShoppingCartItemData)o2;
        
        switch(sortField){
            case PRODUCT_ID:
                int i1 = obj1.getProduct().getProductId();
                int i2 = obj2.getProduct().getProductId();
                retcode = i1-i2;
                break;
            default:
                throw new RuntimeException("Invalid sort option");
        }
        
        return retcode;
    }
    
    
    
    
    
    /**
     *Returns true if all of the items are resale items
     */
    public boolean isAllResaleItems(){
        for (int ii = 0; ii < this.size(); ii++){
            ShoppingCartItemData sciD = (ShoppingCartItemData)this.get(ii);
            if(!sciD.getReSaleItem()){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Returns the total quantity of items to be ordered
     */
    public int getItemsQuantity() {
        int qty = 0;
        for (int ii = 0; ii < this.size(); ii++) {
            ShoppingCartItemData sciD = (ShoppingCartItemData)this.get(ii);
            qty += sciD.getOrderQuantity();
        }        
        return qty;        
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
    public double getItemsCostNonResale(){
        return getItemsCost(GET_COST_NON_RESALE_ITEMS);
    }
    
    private static final int GET_COST_ALL_ITEMS = 0;
    private static final int GET_COST_RESALE_ITEMS = 1;
    private static final int GET_COST_NON_RESALE_ITEMS = 2;
    
    /**
     *Returns the total cost of the shopping cart items (i.e. subtotal).
     */
    private double getItemsCost(int whatToSum){
        double costD=0;

        for (int ii = 0; ii < this.size(); ii++)
        {
            ShoppingCartItemData sciD = (ShoppingCartItemData)this.get(ii);
            boolean doSum = false;
            if(GET_COST_ALL_ITEMS==whatToSum){
                doSum = true;
            }else if(GET_COST_RESALE_ITEMS==whatToSum && sciD.getReSaleItem()){
                doSum = true;
            }else if(GET_COST_NON_RESALE_ITEMS==whatToSum && !sciD.getReSaleItem()){
                doSum = true;
            }
            if(doSum){
               costD += sciD.getAmount();
            }
        }
        return costD;
   }

    public OrderHandlingItemViewVector getFreightItems() {

        OrderHandlingItemViewVector frItems = new OrderHandlingItemViewVector();

        if (!this.isEmpty()) {

            for (Object oShoppingCartItem : this) {

                ShoppingCartItemData item = (ShoppingCartItemData) oShoppingCartItem;

                OrderHandlingItemView frItem = OrderHandlingItemView.createValue();

                frItem.setItemId(item.getProduct().getProductId());
                BigDecimal priceBD = new BigDecimal(item.getPrice());
                priceBD = priceBD.setScale(2, BigDecimal.ROUND_HALF_UP);
                frItem.setPrice(priceBD);
                frItem.setQty(item.getQuantity());
                BigDecimal weight = Utility.parseBigDecimal(item.getProduct().getShipWeight());
                frItem.setWeight(weight);

                frItems.add(frItem);
            }

        }
        return frItems;
    }

    public ShoppingCartItemDataVector getCostCenterItemsOnly(int pCostCenterId) {
        ShoppingCartItemDataVector costCenterItems = new ShoppingCartItemDataVector();
        for (Object oShoppingCartItem : this) {
            ShoppingCartItemData item = (ShoppingCartItemData) oShoppingCartItem;
            if (item.getProduct().getCostCenterId() == pCostCenterId) {
                costCenterItems.add(item);
            }
        }
        return costCenterItems;
    }

    public ShoppingCartItemDataVector copy() {
        ShoppingCartItemDataVector copy = new ShoppingCartItemDataVector();
        for (Object oItem : this) {
            ShoppingCartItemData item = (ShoppingCartItemData) oItem;
            copy.add(item.copy());
        }
        return copy;
    }

    public ProductDataVector getProducts() {
        ProductDataVector products = new ProductDataVector();
        for (Object oShoppingCartItem : this) {
            products.add(((ShoppingCartItemData) oShoppingCartItem).getProduct());
        }
        return products;
    }
}
