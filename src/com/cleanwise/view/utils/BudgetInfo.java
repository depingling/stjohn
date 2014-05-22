package com.cleanwise.view.utils;

import java.lang.StringBuilder;
import java.math.BigDecimal;


public class BudgetInfo {
    String     _categoryName;
    BigDecimal _allocated;
    BigDecimal _spent;
    BigDecimal _cartTotal;
    BigDecimal _threshold;

    public BudgetInfo(String categoryName, BigDecimal allocated, BigDecimal spent, BigDecimal cartTotal, BigDecimal threshold) {
        _categoryName = categoryName;
        _allocated = allocated;
        _spent = spent;
        _cartTotal = cartTotal;
        _threshold = threshold;
    }
    public BudgetInfo() {
        this("", new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0));
    }
    public String getCategoryName() {
        return _categoryName;
    }
    public BigDecimal getAllocated() {
        return _allocated;
    }
    public BigDecimal getSpent() {
        return _spent;
    }
    public BigDecimal getCartTotal() {
        return _cartTotal;
    }
    public BigDecimal getThreshold() {
        return _threshold;
    }
    public void setCategoryName(String categoryName) {
        if (categoryName == null) {
            _categoryName = "";
            return;
        }
        _categoryName = categoryName;
    }
    public void setAllocated(BigDecimal allocated) {
        if (allocated == null) {
            _allocated = new BigDecimal(0);
            return;
        }
        _allocated = allocated;
    }
    public void setSpent(BigDecimal spent) {
        if (spent == null) {
            _spent = new BigDecimal(0);
            return;
        }
        _spent = spent;
    }
    public void setCartTotal(BigDecimal cartTotal) {
        if (cartTotal == null) {
            _cartTotal = new BigDecimal(0);
        }
        _cartTotal = cartTotal;
    }
    public void setThreshold(BigDecimal threshold) {
        if (threshold == null) {
            _threshold = new BigDecimal(0);
            return;
        }
        _threshold = threshold;
    }
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("CategoryName: ").append(_categoryName).append(", ");
        buff.append("Allocated: ").append(_allocated).append(", ");
        buff.append("Spent: ").append(_spent).append(", ");
        buff.append("CartTotal: ").append(_cartTotal).append(", ");
        buff.append("Threshold: ").append(_threshold);
        return buff.toString();
    }
}
