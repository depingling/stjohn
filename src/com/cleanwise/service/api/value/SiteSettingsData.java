package com.cleanwise.service.api.value;

import java.util.*;
import com.cleanwise.service.api.util.RefCodeNames;

public class SiteSettingsData {

    public SiteSettingsData( SiteData pSiteData ) {
  mSiteData = pSiteData;
    }

    private SiteData mSiteData;

    public SiteData getSiteData() { return mSiteData; }

    private Hashtable mShoppingControls = new Hashtable(10);

    public void setShoppingControls(ShoppingControlDataVector v) {
      mSiteData.setShoppingControls(v);

      if ( null == mShoppingControls ) { return; }

      Iterator it = v.iterator();
      while (it.hasNext()){
    ShoppingControlData scd =
        (ShoppingControlData)it.next();
    ShoppingControlItemView sci =
        (ShoppingControlItemView)
        mShoppingControls.get(new Integer(scd.getItemId()));
    if ( null != sci) {
        sci.setShoppingControlData(scd);
    }
      }
    }

    public void setShoppingControls(ShoppingCartItemDataVector v) {

  // Map cart items to shopping control view.
  // get the items for this site that are being
  // controlled.
  ShoppingControlDataVector ctrl =
      mSiteData.getShoppingControls();

  for ( int j = 0; null != ctrl && j < ctrl.size(); j++) {
      ShoppingControlData ctrld =
    (ShoppingControlData)ctrl.get(j);
      Integer pid = new Integer(ctrld.getItemId());
      ShoppingControlItemView sci =
    (ShoppingControlItemView)mShoppingControls.get(pid);

      if ( null == sci ) {
    sci = ShoppingControlItemView.createValue();
    sci.setShoppingControlData(ctrld);
    mShoppingControls.put(pid,sci);
      } else {
    if ( RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals
         (ctrld.getControlStatusCd() ) ) {
        // This new control is active, use it.
        sci.setShoppingControlData(ctrld);
        mShoppingControls.put(new Integer(ctrld.getItemId()),sci);
    }
      }
  }

  for ( int i = 0; null != v && i < v.size(); i++ ) {
      ShoppingCartItemData sd = (ShoppingCartItemData)v.get(i);
      ProductData pd = sd.getProduct();

      Integer pid = new Integer(sd.getItemId());
      ShoppingControlItemView sci =
    (ShoppingControlItemView)mShoppingControls.get(pid);
      if ( null != sci){
    //sci.setSkuNum(String.valueOf(pd.getSkuNum()));
    sci.setSkuNum(sd.getActualSkuNum());
    sci.setItemId(pd.getItemData().getItemId());
    sci.setShortDesc(pd.getShortDesc());
    sci.setUOM(pd.getUom());
    sci.setPack(pd.getPack());
    sci.setSize(pd.getSize());
      }
  }

    }

    static final Comparator SKU_COMPARE = new Comparator() {
      public int compare(Object o1, Object o2) {
    String s1 =((ShoppingControlItemView)o1).getSkuNum()
    , s2 = ((ShoppingControlItemView)o2).getSkuNum();
    int i1 = 0, i2 = 0;
    try { i1 = Integer.parseInt(s1); }
    catch (Exception e) {}
    try { i2 = Integer.parseInt(s2); }
    catch (Exception e) {}
    return i1-i2;
      }
  };

    public ArrayList getShoppingControls() {
  // Sort by SKU.
  ArrayList tarr = new ArrayList(mShoppingControls.values());
  java.util.Collections.sort
      (tarr,SKU_COMPARE);
  return tarr;

    }
    public Hashtable getShoppingControlsMap() {
  return mShoppingControls;
    }
}

