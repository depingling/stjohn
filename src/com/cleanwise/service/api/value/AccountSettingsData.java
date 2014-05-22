package com.cleanwise.service.api.value;

import java.util.*;

public class AccountSettingsData {

    public AccountSettingsData( AccountData pAccountData ) {
	mAccountData = pAccountData;
    }

    private AccountData mAccountData;

    public AccountData getAccountData() { return mAccountData; }

    private Hashtable mShoppingControls = new Hashtable(10);

    public void setShoppingControls(ShoppingControlDataVector v) {
	    mAccountData.setShoppingControls(v);

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
	// get the items for this Account that are being
	// controlled.
	ShoppingControlDataVector ctrl =
	    mAccountData.getShoppingControls();

	for ( int j = 0; null != ctrl && j < ctrl.size(); j++) {
	    ShoppingControlData ctrld =
		(ShoppingControlData)ctrl.get(j);

	    ShoppingControlItemView sci
		= ShoppingControlItemView.createValue();
	    sci.setShoppingControlData(ctrld);
	    mShoppingControls.put(new Integer(ctrld.getItemId()),sci);
	}

	for ( int i = 0; null != v && i < v.size(); i++ ) {
	    ShoppingCartItemData sd = (ShoppingCartItemData)v.get(i);
	    ProductData pd = sd.getProduct();

	    Integer pid = new Integer(sd.getItemId());
	    ShoppingControlItemView sci =
		(ShoppingControlItemView)mShoppingControls.get(pid);
	    if ( null == sci){
		sci = ShoppingControlItemView.createValue();
		ShoppingControlData ctrld =
		    ShoppingControlData.createValue();
		ctrld.setItemId(pd.getProductId());
		sci.setShoppingControlData(ctrld);
	    }

	    sci.setSkuNum(String.valueOf(pd.getSkuNum()));
	    sci.setItemId(pd.getItemData().getItemId());
	    sci.setShortDesc(pd.getShortDesc());
	    sci.setUOM(pd.getUom());
	    sci.setPack(pd.getPack());
	    sci.setSize(pd.getSize());
	}

    }
    
    public static ArrayList getShoppingControlsView(ShoppingControlDataVector scDV, ProductDataVector productDV) {
    	ArrayList sciViewDV = new ArrayList();
    	if ( null == productDV ) return sciViewDV; 
    	Map ctrlMap = new HashMap();
    	for (int i = 0; i < scDV.size(); i++){
    		ShoppingControlData scD = (ShoppingControlData) scDV.get(i);
    		ctrlMap.put(scD.getItemId(), scD);
    	}

    	for (int i = 0; i < productDV.size(); i++){
    		ProductData productD = (ProductData) productDV.get(i);
    		ShoppingControlData ctrlD = (ShoppingControlData) ctrlMap.get(productD.getItemData().getItemId());
    		if (ctrlD == null){
    			ctrlD = ShoppingControlData.createValue();
    		}
    		
    		ShoppingControlItemView sciView = ShoppingControlItemView.createValue();    		
    		sciView.setShoppingControlData(ctrlD);
    		sciView.setSkuNum(String.valueOf(productD.getSkuNum()));
    		sciView.setItemId(productD.getItemData().getItemId());
    		sciView.setShortDesc(productD.getShortDesc());
    		sciView.setUOM(productD.getUom());
    		sciView.setPack(productD.getPack());
    		sciView.setSize(productD.getSize());
    		sciView.setDirty(false);
    		sciViewDV.add(sciView);
    	}
    	java.util.Collections.sort(sciViewDV,SKU_COMPARE);	
    	return sciViewDV;
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


