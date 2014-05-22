/**
 *  Title: StoreSiteOrderGuideNewForm Description: This is the Struts ActionForm
 *  class for user management page. Purpose: Strut support to search for
 *  distributors. Copyright: Copyright (c) 2001 Company: CleanWise, Inc.
 *
 *@author     veronika
 */

package com.cleanwise.view.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.*;

/**
 *  Form bean used to create new order guides.
 *
 *@author     veronika
 *@created    October 17, 2006
 */
public final class Admin2SiteOrderGuideNewForm extends StorePortalBaseForm {

  private String mParentCatalogIdStr;
  private String mParentCatalogName;
  private String mParentContractIdStr;
  private String mParentContractName;
  private String mParentOrderGuideIdStr;
  private String mParentOrderGuideName;
  private String mCreateFrom;
  private int mSiteId;
  private CatalogDataVector mCatalogFilter;



  /**
   *  Sets the ParentOrderGuideName attribute of the OrderGuidesMgrNewForm
   *  object
   *
   *@param  v  The new ParentOrderGuideName value
   */
  public void setParentOrderGuideName(String v) {
    this.mParentOrderGuideName = v;
  }


  /**
   *  Sets the ParentContractName attribute of the OrderGuidesMgrNewForm
   *  object
   *
   *@param  v  The new ParentContractName value
   */
  public void setParentContractName(String v) {
    this.mParentContractName = v;
  }


  /**
   *  Sets the ParentCatalogName attribute of the OrderGuidesMgrNewForm object
   *
   *@param  v  The new ParentCatalogName value
   */
  public void setParentCatalogName(String v) {
    this.mParentCatalogName = v;
  }


  /**
   *  Sets the ParentContractIdStr attribute of the OrderGuidesMgrNewForm
   *  object
   *
   *@param  pParentContractIdStr  The new ParentContractIdStr value
   */
  public void setParentContractId(String pParentContractIdStr) {
    this.mParentContractIdStr = pParentContractIdStr;
  }


  /**
   *  Sets the OrderGuideIdStr attribute of the OrderGuidesMgrNewForm object
   *
   *@param  pOrderGuideIdStr  The new OrderGuideIdStr value
   */
  public void setParentOrderGuideId(String pOrderGuideIdStr) {
    this.mParentOrderGuideIdStr = pOrderGuideIdStr;
  }


  /**
   *  Sets the CreateFrom attribute of the OrderGuidesMgrNewForm object
   *
   *@param  pCreateFrom  The new CreateFrom value
   */
  public void setCreateFrom(String pCreateFrom) {
    this.mCreateFrom = pCreateFrom;
  }


  /**
   *  Sets the CatalogId attribute of the OrderGuidesMgrNewForm object
   *
   *@param  pParentCatalogIdStr  The new CatalogId value
   */
  public void setParentCatalogId(String pParentCatalogIdStr) {
    this.mParentCatalogIdStr = pParentCatalogIdStr;
  }


  /**
   *  Gets the ParentOrderGuideName attribute of the OrderGuidesMgrNewForm
   *  object
   *
   *@return    The ParentOrderGuideName value
   */
  public String getParentOrderGuideName() {
    return mParentOrderGuideName;
  }


  /**
   *  Gets the ParentCatalogName attribute of the OrderGuidesMgrNewForm object
   *
   *@return    The ParentCatalogName value
   */
  public String getParentCatalogName() {
    return mParentCatalogName;
  }


  /**
   *  Gets the ParentContractName attribute of the OrderGuidesMgrNewForm
   *  object
   *
   *@return    The ParentContractName value
   */
  public String getParentContractName() {
    return mParentContractName;
  }


  /**
   *  Gets the ParentContractId attribute of the OrderGuidesMgrNewForm object
   *
   *@return    The ParentContractId value
   */
  public String getParentContractId() {
    return mParentContractIdStr;
  }


  /**
   *  Gets the OrderGuideId attribute of the OrderGuidesMgrNewForm object
   *
   *@return    The OrderGuideId value
   */
  public String getParentOrderGuideId() {
    return mParentOrderGuideIdStr;
  }


  /**
   *  Gets the CreateFrom attribute of the OrderGuidesMgrNewForm object
   *
   *@return    The CreateFrom value
   */
  public String getCreateFrom() {
    return mCreateFrom;
  }


  /**
   *  Gets the CatalogId attribute of the OrderGuidesMgrNewForm object
   *
   *@return    The CatalogId value
   */
  public String getParentCatalogId() {
    return mParentCatalogIdStr;
  }


  /**
   *  <code>reset</code> method, set the search fiels to null.
   *
   *@param  mapping  an <code>ActionMapping</code> value
   *@param  request  a <code>HttpServletRequest</code> value
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    return;
  }


  /**
   *  <code>validate</code> method is a stub.
   *
   *@param  mapping  an <code>ActionMapping</code> value
   *@param  request  a <code>HttpServletRequest</code> value
   *@return          an <code>ActionErrors</code> value
   */
  public ActionErrors validate(ActionMapping mapping,
                               HttpServletRequest request) {
    // Validation happens in the logic bean.
    return null;
  }

  private String NewOrderGuideType;

  /**
   * Get the value of NewOrderGuideType.
   * @return value of NewOrderGuideType.
   */
  public String getNewOrderGuideType() {
    return NewOrderGuideType;
  }

  /**
   * Set the value of NewOrderGuideType.
   * @param v  Value to assign to NewOrderGuideType.
   */
  public void setNewOrderGuideType(String v) {
    this.NewOrderGuideType = v;
  }

  public int getSiteId() {
    return this.mSiteId;
  }

  public void setSiteId(int v) {
    this.mSiteId = v;
  }

  public CatalogDataVector getCatalogFilter() {return mCatalogFilter;}
  public void setCatalogFilter(CatalogDataVector pVal) {mCatalogFilter = pVal;}

}

