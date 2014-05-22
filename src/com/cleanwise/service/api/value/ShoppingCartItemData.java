package com.cleanwise.service.api.value;

import java.math.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.Constants;

/**
 * <code>ShoppingCartItemData</code>
 * Author: Yuriy Kupershmidt
 */
public class ShoppingCartItemData extends ValueObject
{
  //Do not remove or modify next line. It would break object DB saving
  private static final long serialVersionUID = -5887532894184159410L;
  public static int CATALOG = 1;
  public static int ORDER_GUIDE = 3;
  public static int JANITOR_CLOSET =4;
  //------------------------------------------------------------------
  public static int CATALOG_SKU = 1;
  public static int CLW_SKU = 2;
  public static int MANUFACTURER_SKU = 3;
  public static int DISTRIBUTOR_SKU = 4;
  //------------------------------------------------------------------
  private int _orderNumber = 0;
  private ProductData _product = null;
  private CatalogCategoryData _category = null;
  private ItemShoppingHistoryData _shoppingHistory = null;
  private int _templQuantity = 0;
  private int _quantity = 0, _qtyOnHand = 0;
  private String _monthlyOrderQtyString;
  private String _quantityString = "";
  private double _price = 0;
  private int _sourceCd = 0;
  private int _sourceId = 0;
  private boolean _contractFlag = false;
  private boolean _duplicateFlag = false;
  private String _actualSkuNum = "";
  private int _actualSkuType = 0;
  private boolean _reSaleItem = false;
  private int _distInventoryQty = -1; //-1 means not available
  //Jd data begin
  private double _discPrice = 0;
  //Jd date end

  private List _autoDistroShoppingCartHistory = null;

  private String _customCategoryName;
  private int _customSortOrder;

  private SkuValue _skuValue;
  private PriceValue _priceValue;

    //------------------------------------------------------------------
  public ShoppingCartItemData() {
  }

  public void setOrderNumber(int pValue) {_orderNumber = pValue;}
  public int getOrderNumber() {return _orderNumber;}

  public void setContractFlag(boolean pValue){_contractFlag = pValue;}
  public boolean getContractFlag() {return _contractFlag;}

  public void setReSaleItem(boolean pValue){_reSaleItem = pValue;}
  public boolean getReSaleItem() {return _reSaleItem;}

  public void setDistInventoryQty(int pValue){_distInventoryQty = pValue;}
  public int getDistInventoryQty() {return _distInventoryQty;}

  public void setSourceCd(int pValue) {_sourceCd = pValue;}
  public int getSourceCd() {return _sourceCd;}

  public void setSourceId(int pValue) {_sourceId = pValue;}
  public int getSourceId() {return _sourceId;}

  public void setProduct(ProductData pValue) {_product = pValue;}
  public ProductData getProduct() {return _product;}

  public void setQuantity(int pValue) {_quantity = pValue;}
  public int getQuantity() { return _quantity;  }
  public void setQuantityString(String pValue) {
      if(pValue != null){
          pValue = pValue.trim();
      }
    _quantityString = pValue;
  }
  public String getQuantityString() { return _quantityString;  }

  public void setTemplQuantity(int pValue) {_templQuantity = pValue;}
  public int getTemplQuantity() {return _templQuantity;}

  public void setPrice(double pValue) {_price = pValue;}
  public double getPrice() {return _price;}

  public void setCategory(CatalogCategoryData pValue) {_category = pValue;}
  public CatalogCategoryData getCategory() {return _category;}

  public String getCategoryName() {
    if(_category==null) return "";
    return _category.getCatalogCategoryShortDesc();
  }

    public int getInventoryOrderQty() {
        int iq = 0;


            if (Utility.isSet(getQuantityString())) {
                iq = getQuantity();

            } else {
            	if (getInventoryQtyIsSet()) {
            		iq = getInventoryParValue() - getInventoryQtyOnHand();
            	}

            }
            /*
           } else if (getIsaInventoryItem() && iq==0 &&
              getAutoOrderEnable() && getAutoOrderFactor()!=null) {
              iq = (int) (getInventoryParValue()*getAutoOrderFactor().doubleValue());
			*/

        if (iq < 0) iq = 0;
        return iq;
    }

    public int getOrderQuantity() {
        int qty = 0;
        if (this._isaInventoryItem) {
            qty = getInventoryOrderQty();
        } else {
            qty = getQuantity();
        }

        return qty;
    }

    public double getAmount() {
        double amount = 0;
        if (this._isaInventoryItem) {
            amount = getInventoryAmount();
        } else {
            amount = getRegularAmount();
        }

        return amount;
    }

    public double getInventoryAmount() {

		BigDecimal priceBD = new BigDecimal(getPrice());
		priceBD = priceBD.multiply(new BigDecimal(getInventoryOrderQty()));
		return priceBD.doubleValue();

    }

    public double getRegularAmount() {

		BigDecimal priceBD = new BigDecimal(getPrice());
		priceBD = priceBD.multiply(new BigDecimal(getQuantity()));
		return priceBD.doubleValue();
		
    }

  public void setDuplicateFlag(boolean pValue) {_duplicateFlag = pValue;}
  public boolean getDuplicateFlag() {return _duplicateFlag;}

  public void setShoppingHistory(ItemShoppingHistoryData pValue){_shoppingHistory = pValue;}
  public ItemShoppingHistoryData getShoppingHistory() {return _shoppingHistory;}

  public void setActualSkuNum(String pValue) {_actualSkuNum = pValue;}
  public String getActualSkuNum() {return _actualSkuNum;}

  public void setActualSkuType(int pValue) {_actualSkuType = pValue;}
  public int getActualSkuType() {return _actualSkuType;}

    public boolean isSameItem( ShoppingCartItemData p) {
        if (p == null ||
            p.getProduct() == null ) {
            return false;
        }
        return isSameItem(p.getProduct().getProductId());
    }

    public boolean isSameItem( int pItemId) {
        if (this._product == null) {
            return false;
        }
        return this._product.getProductId() == pItemId;
    }


  public ShoppingCartItemData copy() {
    ShoppingCartItemData sciD = new ShoppingCartItemData();
    sciD._orderNumber = _orderNumber;
    sciD._product = _product;
    sciD._category = _category;
    sciD._quantity = _quantity;
    sciD._quantityString = _quantityString;
    sciD._templQuantity = _templQuantity;
    sciD._price = _price;
    sciD._sourceCd = _sourceCd;
    sciD._sourceId = _sourceId;
    sciD._contractFlag = _contractFlag;
    sciD._duplicateFlag = _duplicateFlag;
    sciD._shoppingHistory = _shoppingHistory;
    sciD._actualSkuNum = _actualSkuNum;
    sciD._actualSkuType = _actualSkuType;
    sciD._discPrice = _discPrice; //Jd only
    sciD._maxOrderQty = _maxOrderQty;
    sciD._restrictionDays = _restrictionDays;
    sciD._isaInventoryItem = _isaInventoryItem;
    sciD._customCategoryName = _customCategoryName;
    return sciD;
  }
  //Jd begin
  public void setDiscPrice(double pValue) {_discPrice = pValue;}
  public double getDiscPrice() {return _discPrice;}

  //public double getDiscAmount() {
  //   BigDecimal priceBD = new BigDecimal((getDiscPrice()+.005)*100);
  //   long price = priceBD.longValue();
  //   long cost = getQuantity()*price;
  //   double costD = cost;
  //   return costD/100;
  //}
  //Jd end

    private boolean _isaInventoryItem = false;
    public boolean getIsaInventoryItem() {
        return _isaInventoryItem;
    }
    public void setIsaInventoryItem(boolean pVal) {
        _isaInventoryItem = pVal;
    }

    private boolean _inventoryQtyIsSet = false;
    public boolean getInventoryQtyIsSet() {
        return _inventoryQtyIsSet;
    }
    public void setInventoryQtyIsSet(boolean pVal) {
        _inventoryQtyIsSet = pVal;
    }

    private int _inventoryQtyOnHand = 0;
    public int getInventoryQtyOnHand() {
        return _inventoryQtyOnHand;
    }
    public void setInventoryQtyOnHand(int pVal) {
        _inventoryQtyOnHand = pVal;
        _inventoryQtyOnHandString = String.valueOf(getInventoryQtyOnHand());
    }

    private String _inventoryQtyOnHandString = "";
    public String getInventoryQtyOnHandString() {
        if ( null == _inventoryQtyOnHandString ) {
            _inventoryQtyOnHandString = "";
        }
        return _inventoryQtyOnHandString;
    }
    public void setInventoryQtyOnHandString(String pVal) {
        _inventoryQtyOnHandString = pVal;
    }
    private String _qtySourceCd = null;
    public String getQtySourceCd() {
        return _qtySourceCd;
    }
    public void setQtySourceCd(String pVal) {
        _qtySourceCd = pVal;
    }


    private boolean _autoOrderEnable = false;
    public boolean getAutoOrderEnable() {
        return _autoOrderEnable;
    }
    public void setAutoOrderEnable(boolean pVal) {
        _autoOrderEnable = pVal;
    }


    private BigDecimal _autoOrderFactor = null;
    public BigDecimal getAutoOrderFactor() {
        return _autoOrderFactor;
    }
    public void setAutoOrderFactor(BigDecimal pVal) {
        _autoOrderFactor = pVal;
    }

    private int _inventoryParValue = 0;
    public int getInventoryParValue() {
        return _inventoryParValue;
    }

    public void setInventoryParValue(int pVal) {
        _inventoryParValue = pVal;
    }

    private int _maxOrderQty = -1;
    public int getMaxOrderQty() {
        return _maxOrderQty;
    }

    public void setMaxOrderQty(int pVal) {
        _maxOrderQty = pVal;
    }

    private int _restrictionDays = -1;
    public int getRestrictionDays() {
        return _restrictionDays;
    }

    public void setRestrictionDays(int pVal) {
    	_restrictionDays = pVal;
    }

    private int _inventoryParValuesSum = 0;
    public int getInventoryParValuesSum() {
        return _inventoryParValuesSum;
    }
    public void setInventoryParValuesSum(int pVal) {
        _inventoryParValuesSum = pVal;
    }


    public String toString() {
  String os = "ShoppingCartItemData = [ " +
      " OrderNumber = " 		+ _orderNumber +
      " Product = " 		+ _product +
      " Category = " 		+ _category +
      " ShoppingHistory = " 	+ _shoppingHistory +
      " TemplQuantity = " 	+ _templQuantity +
      " Quantity = " 		+ _quantity +
      " QuantityString = " 		+ _quantityString +
      " Price = " 		+ _price +
      " SourceCd = " 		+ _sourceCd +
      " SourceId = " 		+ _sourceId +
      " ContractFlag = " 		+ _contractFlag +
      " DuplicateFlag = " 	+ _duplicateFlag +
      " ActualSkuNum = " 		+ _actualSkuNum  +
      " ActualSkuType = " 	+ _actualSkuType +
      " DiscPrice = " 	+ _discPrice +
      " _isaInventoryItem = " + _isaInventoryItem +
      " _inventoryQtyIsSet = " +_inventoryQtyIsSet +
      " _inventoryQtyOnHand = " + _inventoryQtyOnHand +
      " _inventoryQtyOnHandString= " + _inventoryQtyOnHandString +
      " _inventoryParValue = " + _inventoryParValue +
      " _inventoryParValuesSum = " + _inventoryParValuesSum +
        " _autoOrderFactor = " +_autoOrderFactor +
        " _autoOrderEnable = " +_autoOrderEnable +
        " _qtySourceCd = " +_qtySourceCd +
      " ] ";

  return os;

     }

    public String description() {
  String os =
      " [ Sku = " 		+ _actualSkuNum
      + " Description = " + _product.getItemData().getShortDesc()
      + " ] ";

  return os;

     }

    private List _shoppingCartHistory = null;
  private String categoryPath;
  private String categoryPathForNewUI;
  public List getItemShoppingCartHistory()
    {
        return _shoppingCartHistory;
    }

    public void setShoppingCartHistory(List pValue)
    {
        _shoppingCartHistory =new ArrayList();
        if (pValue == null || pValue.size() <= 0) return;

        for ( int idx = 0; idx <  pValue.size(); idx++ ) {
            ShoppingInfoData sid =
                (ShoppingInfoData)pValue.get(idx);
            if ( this.isSameItem(sid.getItemId()) )
                {
                    _shoppingCartHistory.add(sid);
                }
        }
    }

    public int getItemId() {
  if( this.getProduct() == null ||
      this.getProduct().getItemData() == null ) {
      return 0;
  }
  return this.getProduct().getItemData().getItemId();
    }

    public String getItemDesc() {
  if ( null == _product ||
       null == _product.getItemData() ||
       null == _product.getItemData().getShortDesc() ){
      return "";
  }
  return _product.getItemData().getShortDesc();
     }

    /**
     *Returns true of this item is taxable
     */
    public boolean isTaxable(){
        boolean retValue = true;
        if(getProduct() == null ||  getProduct().getCatalogStructure() == null){
            retValue = true;
        }else if(Utility.isTrue(getProduct().getCatalogStructure().getTaxExempt())){
            retValue = false;
        }else{
            retValue = !getReSaleItem();
        }
        return retValue;
    }


    public void setMonthlyOrderQtyString(String pVal) {
        this._monthlyOrderQtyString = pVal;
    }

    public String getMonthlyOrderQtyString() {
       return _monthlyOrderQtyString;
    }

    public String getMonthlyOrderQty() {

        if (Utility.isSet(_monthlyOrderQtyString)) {
            try {
                return String.valueOf(Integer.parseInt(_monthlyOrderQtyString));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (_inventoryQtyIsSet) {
            return String.valueOf(_inventoryParValue - _inventoryQtyOnHand);
        } else {
            return "";
        }

    }

    public String getCategoryPath() {
        if (_product.getCatalogCategories() != null) {
            categoryPath = "";
            for (int cci = _product.getCatalogCategories().size() - 1; cci >= 0; cci--) {
                categoryPath += ((categoryPath.equals(""))? " " : ">") +
                    ( (CatalogCategoryData) _product.getCatalogCategories().get(cci)).getCatalogCategoryShortDesc();
            }
            return categoryPath;
        } else {
            return getCategoryName();
        }
    }
    
    public String getCategoryPathForNewUI() {
    	if(Utility.isSet(getCustomCategoryName())){
    		return getCustomCategoryName();
    	}else{
	        if (_product.getCatalogCategories() != null) {
	        	categoryPathForNewUI = "";
	            for (int cci = _product.getCatalogCategories().size() - 1; cci >= 0; cci--) {
	            	categoryPathForNewUI += ((categoryPathForNewUI.equals(""))? " " : " > ") +
	                    ( (CatalogCategoryData) _product.getCatalogCategories().get(cci)).getCatalogCategoryShortDesc();
	            }
	            return categoryPathForNewUI;
	        } else {
	            return getCategoryName();
	        }
    	}
    }
    
    public List getAutoDistroShoppingCartHistory() {
        return _autoDistroShoppingCartHistory;
    }

    public void setAutoDistroShoppingCartHistory(List history) {
        _autoDistroShoppingCartHistory = new ArrayList();
        if (history == null || history.size() <= 0) 
            return;
        for (int i = 0; i < history.size(); i++) {
            ShoppingInfoData shopInfoData = (ShoppingInfoData)history.get(i);
            if (this.isSameItem(shopInfoData.getItemId())) {
                _autoDistroShoppingCartHistory.add(shopInfoData);
            }
        }
    }

    public void setCustomCategoryName(String pCustomCategoryName) {
        this._customCategoryName = pCustomCategoryName;
    }

    public String getCustomCategoryName() {
        return _customCategoryName;
    }

    public void setCustomSortOrder(int pCustomSortOrder) {
        this._customSortOrder = pCustomSortOrder;
    }

    public int getCustomSortOrder() {
        return _customSortOrder;
    }

    public boolean isSkuValueMissing() {
        return _skuValue == null;
    }

    public void setSkuValue(SkuValue skuValue) {
        this._skuValue = skuValue;
    }

     public SkuValue getSkuValue() {
       return  this._skuValue;
    }

    public void setPriceValue(PriceValue priceValue) {
        this._priceValue = priceValue;
    }

    public PriceValue getPriceValue() {
        return _priceValue;
    }
    public String getCategoryPathWithLinksForNewUI(HttpServletRequest request) {
    	String link ="";
    	StringBuilder shoppingCatalogLink = new StringBuilder(50);
    	shoppingCatalogLink.append(request.getSession().getAttribute("pages.root"));
    	shoppingCatalogLink.append("/userportal/esw/shopping.do?");
    	shoppingCatalogLink.append(Constants.PARAMETER_OPERATION);
    	shoppingCatalogLink.append("=");
    	shoppingCatalogLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG);
    	shoppingCatalogLink.append("&");
    	shoppingCatalogLink.append(Constants.PARAMETER_CATALOG_ITEM_KEY);
    	shoppingCatalogLink.append("=");
    	String selectedCatalogKeyS = request.getParameter("catalogItemKey");
    	boolean found = false;
    	int selectedCatalogKey = 0;
    	if(selectedCatalogKeyS!=null && !selectedCatalogKeyS.trim().equals(""))
    		selectedCatalogKey = Integer.parseInt(selectedCatalogKeyS);
    	int i = 0;
    	for (;i <_product.getCatalogCategories().size();  i++) 
    	{
    		CatalogCategoryData ccd = (CatalogCategoryData) _product.getCatalogCategories().get(i);
    		if(ccd.getItemData().getItemId() == selectedCatalogKey){
    			found = true;
    			break;
    		}
    	}
    	if(found==false){
    		i = _product.getCatalogCategories().size()-1;
    	}
    	Map<String,Integer> categoryItemsCountMap = (HashMap<String,Integer>)request.getAttribute("categoryItemsCountMap");
        if (_product.getCatalogCategories() != null) {
        	categoryPathForNewUI = "";
        	
            for (int j = i; j >= 0; j--) {
            	int itemId = ( (CatalogCategoryData) _product.getCatalogCategories().get(j)).getItemData().getItemId();
            	int itemsCount = categoryItemsCountMap.get(String.valueOf(itemId));
            	if(itemsCount < 500)
            	{
            		link ="<a href=\""+ shoppingCatalogLink.toString()+ itemId+"\">";
            		categoryPathForNewUI += ((categoryPathForNewUI.equals(""))? " " : " > ") + link +
                    ( (CatalogCategoryData) _product.getCatalogCategories().get(j)).getCatalogCategoryShortDesc() +"</a>";
            	}
            	else
            	{
            		categoryPathForNewUI += ((categoryPathForNewUI.equals(""))? " " : " > ") +
                    	( (CatalogCategoryData) _product.getCatalogCategories().get(j)).getCatalogCategoryShortDesc();
            	}
            }
            return categoryPathForNewUI;
        } else {
            return getCategoryName();
        }
    }
}
