
package com.cleanwise.view.utils;

import java.io.Serializable;
import java.util.List;


public final class InformationState implements Serializable {

  private String _key;
  private String _name;
  private List   _list;
  private int    _listCount;
  private int    _page;
  private int    _pageSize;


  public InformationState() {
    reset();
  }


  private void reset() {
    _key       = "";
    _name      = "";
    _list      = null;
    _listCount = 0;
    _page      = 1;
    _pageSize  = 10;
  }


  /**
   * Return the key of the active firm.
   */
  public String getKey() {
    return (this._key);
  }


  /**
   * Save the key of the active firm.
   *
   * @param key The key of the active firm
   */
  public void setKey(String key) {
    this._key = key;
  }

  /**
   * Return the key of the active firm.
   */
  public String getName() {
    return (this._name);
  }


  /**
   * Save the key of the active firm.
   *
   * @param name The key of the active firm
   */
  public void setName(String name) {
    this._name = name;
  }


  /**
   * Return all firms associated with this user.  If there
   * are none, a zero-length array is returned.
   */
  public List getList() {
    return (this._list);
  }


  /**
   * Set the firms array.
   *
   * @param objects The array of firms associated with this user
   */
  public void setList(List objects) {
    this._list = objects;
    this._listCount = objects.size();
  }


  /**
   * Return the current firm page.
   */
  public int getListCount() {
    return (this._listCount);
  }


  /**
   * Return the current firm page.
   */
  public int getPage() {
    return (this._page);
  }


  /**
   * Set the current firm page.
   *
   * @param page The firm page currently displayed
   */
  public void setPage(int page) {
    this._page = page;
  }


  /**
   * Return number of firms displayed on a firm page.
   */
  public int getPageSize() {
    return (this._pageSize);
  }


  /**
   * Set the number of firms displayed on a firm page.
   *
   * @param size The number of firms displayed on a firm page
   */
  public void setPageSize(int size) {
    this._pageSize = size;
  }


  /**
   * Return the array index of the first firm to be displayed
   * on the current page.
   */
  public int getOffset() {
    return ((_page - 1) * _pageSize);
  }


}
