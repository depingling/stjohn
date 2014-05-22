package com.cleanwise.service.api.value;

/**
 * Title:        CatalogCategoryData
 * Description:  Value object extension for marshalling category attribute value pairs.
 * Purpose:      Obtains and marshals category information among session components.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Liang Li, CleanWise, Inc.
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;

import com.cleanwise.service.api.util.RefCodeNames;

/**
 * <code>CatalogCategoryData</code>
 */
public class CatalogCategoryData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving 
  private static final long serialVersionUID = 4783859016869086052L;
    int TreeLevel;
    ItemDataVector ChildCategories;
    int mSortOrder;
    boolean lowestLevel = false;
//    ArrayList ChildCategories;
    
    /**
     * Get the value of TreeLevel.
     * @return value of TreeLevel.
     */
    public int getTreeLevel() {
	return TreeLevel;
    }
    
    /**
     * Set the value of TreeLevel.
     * @param v  Value to assign to TreeLevel.
     */
    public void setTreeLevel(int  v) {
	this.TreeLevel = v;
    }
    
    ItemData ParentCategory;
    
    /**
     * Get the value of ParentCategory.
     * @return value of ParentCategory.
     */
    public ItemData getParentCategory() {
	return ParentCategory;
    }
    
    /**
     * Set the value of ParentCategory.
     * @param v  Value to assign to ParentCategory.
     */
    public void setParentCategory(ItemData  v) {
	this.ParentCategory = v;
    }
    
    ItemData MajorCategory;
    
    /**
     * Get the value of MajorCategory.
     * @return value of MajorCategory.
     */
    public ItemData getMajorCategory() {
	return MajorCategory;
    }
    
    /**
     * Set the value of MajorCategory.
     * @param v  Value to assign to MajorCategory.
     */
    public void setMajorCategory(ItemData  v) {
	this.MajorCategory = v;
    }
  // -------------------------------------------------------- Instance Variables
  public ItemData _itemD;
    
  public CatalogCategoryData() {

  }

  /**
   * Gets the ItemData object
   * return ItemData
   */
   public ItemData getItemData() {
     if(_itemD==null) setEmptyItem();
     return _itemD;
   }

  /**
   * Sets ItemData object related to this category
   * @param pItemData ItemData object
   * return none
   */
  public void setItemData(ItemData pItemData) {
    _itemD=pItemData;
  }

  /**
   * Gets the categoryId (category identifier) property
   * return int
   */
  public int getCatalogCategoryId() {
    if(_itemD==null) setEmptyItem();
    return _itemD.getItemId();
  }

  /**
   * Sets the categoryId (category identifier) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogCategoryId(int pValue) {
    if(_itemD==null) setEmptyItem();
    _itemD.setItemId(pValue);
  }

  /**
   * Gets the categoryShortDesc (category short description) property
   * return String
   */
  public String getCatalogCategoryShortDesc() {
    if(_itemD==null) setEmptyItem();
    return _itemD.getShortDesc();
  }

  /**
   * Sets the categoryShortDesc (category short description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogCategoryShortDesc(String pValue) {
    if(_itemD==null) setEmptyItem();
    _itemD.setShortDesc(pValue);
  }

  /**
   * Gets the categoryLongDesc (category Long description) property
   * return String
   */
  public String getCatalogCategoryLongDesc() {
    if(_itemD==null) setEmptyItem();
    return _itemD.getLongDesc();
  }

  /**
   * Sets the categoryLongDesc (category Long description) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogCategoryLongDesc(String pValue) {
    if(_itemD==null) setEmptyItem();
    _itemD.setLongDesc(pValue);
  }

  /**
   * Gets the categoryTypeCd (category type code) property
   * return String
   */
  public String getCatalogCategoryTypeCd() {
    if(_itemD==null) setEmptyItem();
    return _itemD.getItemTypeCd();
  }

  /**
   * Sets the categoryTypeCd (category type code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogCategoryTypeCd(String pValue) {
    if(_itemD==null) setEmptyItem();
    _itemD.setItemTypeCd(pValue);
  }

  /**
   * Gets the categoryStatusCd (category Status code) property
   * return String
   */
  public String getCatalogCategoryStatusCd() {
    if(_itemD==null) setEmptyItem();
    return _itemD.getItemStatusCd();
  }

  /**
   * Sets the categoryStatusCd (category Status code) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setCatalogCategoryStatusCd(String pValue) {
    if(_itemD==null) setEmptyItem();
    _itemD.setItemStatusCd(pValue);
  }

  /**
   * Gets the EffDate (effective date) property
   * return Date
   */
  public Date getEffDate() {
    if(_itemD==null) setEmptyItem();
    return _itemD.getEffDate();
  }

  /**
   * Sets the EffDate (effective date) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setEffDate(Date pValue) {
    if(_itemD==null) setEmptyItem();
    _itemD.setEffDate(pValue);
  }

  /**
   * Gets the ExpDate (expiration date) property
   * return Date
   */
  public Date getExpDate() {
    if(_itemD==null) setEmptyItem();
    return _itemD.getExpDate();
  }

  /**
   * Sets the ExpDate (expiration date) property
   * @param pValue  the value we want to set
   * return none
   */
  public void setExpDate(Date pValue) {
    if(_itemD==null) setEmptyItem();
    _itemD.setExpDate(pValue);
  }

  /*
  */
  private void setEmptyItem() {
      _itemD = ItemData.createValue();
      _itemD.setItemTypeCd( RefCodeNames.ITEM_TYPE_CD.CATEGORY);
      _itemD.setItemStatusCd( RefCodeNames.ITEM_STATUS_CD.ACTIVE);
  }
    public String toString() {
	StringBuffer desc = new StringBuffer();
	for ( int i = 0; i < TreeLevel; i++ ) {
	    desc.append(" ");
	}
	desc.append(_itemD.getShortDesc());
	if ( TreeLevel > 0 ) {
	    desc.append(" (");
	    desc.append(TreeLevel);
	    desc.append(")");
	}

	return desc.toString();
    }

    public String getLabelDesc() {
	return toString();
    }

	/**
	 * @return Returns the childCategories.
	 */
	public ItemDataVector getChildCategories() {
		return ChildCategories;
	}

	/**
	 * @param childCategories The childCategories to set.
	 */
	public void setChildCategories(ItemDataVector childCategories) {
		ChildCategories = childCategories;
	}

    public void setSortOrder(int pSort) {
        this.mSortOrder = pSort;
    }                           

    public int getSortOrder() {
        return mSortOrder;
    }

	public void setLowestLevel(boolean lowestLevel) {
		this.lowestLevel = lowestLevel;
	}

	public boolean isLowestLevel() {
		return lowestLevel;
	}

	public CatalogCategoryData clone() {
		CatalogCategoryData ccD = new CatalogCategoryData();
        ccD.TreeLevel = this.TreeLevel;
        ccD.ChildCategories = null;
		if(this.ChildCategories!=null) {
		    ccD.ChildCategories = new ItemDataVector();
			for(Iterator iter=this.ChildCategories.iterator(); iter.hasNext();) {
				ItemData itD = (ItemData) iter.next();
				ccD.ChildCategories.add(itD);
			}
		}
        ccD.mSortOrder = this.mSortOrder;
        ccD.lowestLevel = this.lowestLevel;    
        ccD.ParentCategory = (this.ParentCategory==null)?null:(ItemData) this.ParentCategory.clone();
        ccD.MajorCategory = (this.MajorCategory==null)?null:(ItemData) this.MajorCategory.clone();
        ccD._itemD = (this._itemD==null)?null:(ItemData) this._itemD.clone();
		return ccD;
	}
	
}
