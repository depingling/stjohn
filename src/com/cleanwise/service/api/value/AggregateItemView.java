
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        AggregateItemView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;

import java.math.BigDecimal;


/**
 * <code>AggregateItemView</code> is a ViewObject class for UI.
 */
public class AggregateItemView
extends ValueObject
{
   
    private static final long serialVersionUID = 925680371540086106L;
    private int mContractId;
    private int mCatalogId;
    private int mCatalogStructureId;
    private int mContractItemId;
    private int mOrderGuideId;
    private int mOrderGuideStructureId;
    private int mItemId;
    private String mOrderGuideDesc;
    private String mDistDesc;
    private String mContractDesc;
    private String mCatalogDesc;
    private String mCatalogStatus;
    private String mCatalogType;
    private String mContractStatus;
    private String mAccountDesc;
    private List mCategories;
    private String mCustSku;
    private int mDistId;
    private BigDecimal mItemCost;
    private BigDecimal mItemPrice;
    private BigDecimal mDistBaseCost;
    private String mDistIdStr;
    private String mItemCostStr;
    private String mItemPriceStr;
    private String mDistBaseCostStr;

    /**
     * Constructor.
     */
    public AggregateItemView ()
    {
        mOrderGuideDesc = "";
        mDistDesc = "";
        mContractDesc = "";
        mCatalogDesc = "";
        mCatalogStatus = "";
        mCatalogType = "";
        mContractStatus = "";
        mAccountDesc = "";
        mCustSku = "";
        mDistIdStr = "";
        mItemCostStr = "";
        mItemPriceStr = "";
        mDistBaseCostStr = "";
    }

    /**
     * Constructor. 
     */
    public AggregateItemView(int parm1, int parm2, int parm3, int parm4, int parm5, int parm6, int parm7, String parm8, String parm9, String parm10, String parm11, String parm12, String parm13, String parm14, String parm15, List parm16, String parm17, int parm18, BigDecimal parm19, BigDecimal parm20, BigDecimal parm21, String parm22, String parm23, String parm24, String parm25)
    {
        mContractId = parm1;
        mCatalogId = parm2;
        mCatalogStructureId = parm3;
        mContractItemId = parm4;
        mOrderGuideId = parm5;
        mOrderGuideStructureId = parm6;
        mItemId = parm7;
        mOrderGuideDesc = parm8;
        mDistDesc = parm9;
        mContractDesc = parm10;
        mCatalogDesc = parm11;
        mCatalogStatus = parm12;
        mCatalogType = parm13;
        mContractStatus = parm14;
        mAccountDesc = parm15;
        mCategories = parm16;
        mCustSku = parm17;
        mDistId = parm18;
        mItemCost = parm19;
        mItemPrice = parm20;
        mDistBaseCost = parm21;
        mDistIdStr = parm22;
        mItemCostStr = parm23;
        mItemPriceStr = parm24;
        mDistBaseCostStr = parm25;
        
    }

    /**
     * Creates a new AggregateItemView
     *
     * @return
     *  Newly initialized AggregateItemView object.
     */
    public static AggregateItemView createValue () 
    {
        AggregateItemView valueView = new AggregateItemView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this AggregateItemView object
     */
    public String toString()
    {
        return "[" + "ContractId=" + mContractId + ", CatalogId=" + mCatalogId + ", CatalogStructureId=" + mCatalogStructureId + ", ContractItemId=" + mContractItemId + ", OrderGuideId=" + mOrderGuideId + ", OrderGuideStructureId=" + mOrderGuideStructureId + ", ItemId=" + mItemId + ", OrderGuideDesc=" + mOrderGuideDesc + ", DistDesc=" + mDistDesc + ", ContractDesc=" + mContractDesc + ", CatalogDesc=" + mCatalogDesc + ", CatalogStatus=" + mCatalogStatus + ", CatalogType=" + mCatalogType + ", ContractStatus=" + mContractStatus + ", AccountDesc=" + mAccountDesc + ", Categories=" + mCategories + ", CustSku=" + mCustSku + ", DistId=" + mDistId + ", ItemCost=" + mItemCost + ", ItemPrice=" + mItemPrice + ", DistBaseCost=" + mDistBaseCost + ", DistIdStr=" + mDistIdStr + ", ItemCostStr=" + mItemCostStr + ", ItemPriceStr=" + mItemPriceStr + ", DistBaseCostStr=" + mDistBaseCostStr + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("AggregateItem");
	root.setAttribute("Id", String.valueOf(mContractId));

	Element node;

        node = doc.createElement("CatalogId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogId)));
        root.appendChild(node);

        node = doc.createElement("CatalogStructureId");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogStructureId)));
        root.appendChild(node);

        node = doc.createElement("ContractItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mContractItemId)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideId)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideStructureId");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideStructureId)));
        root.appendChild(node);

        node = doc.createElement("ItemId");
        node.appendChild(doc.createTextNode(String.valueOf(mItemId)));
        root.appendChild(node);

        node = doc.createElement("OrderGuideDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mOrderGuideDesc)));
        root.appendChild(node);

        node = doc.createElement("DistDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mDistDesc)));
        root.appendChild(node);

        node = doc.createElement("ContractDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mContractDesc)));
        root.appendChild(node);

        node = doc.createElement("CatalogDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogDesc)));
        root.appendChild(node);

        node = doc.createElement("CatalogStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogStatus)));
        root.appendChild(node);

        node = doc.createElement("CatalogType");
        node.appendChild(doc.createTextNode(String.valueOf(mCatalogType)));
        root.appendChild(node);

        node = doc.createElement("ContractStatus");
        node.appendChild(doc.createTextNode(String.valueOf(mContractStatus)));
        root.appendChild(node);

        node = doc.createElement("AccountDesc");
        node.appendChild(doc.createTextNode(String.valueOf(mAccountDesc)));
        root.appendChild(node);

        node = doc.createElement("Categories");
        node.appendChild(doc.createTextNode(String.valueOf(mCategories)));
        root.appendChild(node);

        node = doc.createElement("CustSku");
        node.appendChild(doc.createTextNode(String.valueOf(mCustSku)));
        root.appendChild(node);

        node = doc.createElement("DistId");
        node.appendChild(doc.createTextNode(String.valueOf(mDistId)));
        root.appendChild(node);

        node = doc.createElement("ItemCost");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCost)));
        root.appendChild(node);

        node = doc.createElement("ItemPrice");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPrice)));
        root.appendChild(node);

        node = doc.createElement("DistBaseCost");
        node.appendChild(doc.createTextNode(String.valueOf(mDistBaseCost)));
        root.appendChild(node);

        node = doc.createElement("DistIdStr");
        node.appendChild(doc.createTextNode(String.valueOf(mDistIdStr)));
        root.appendChild(node);

        node = doc.createElement("ItemCostStr");
        node.appendChild(doc.createTextNode(String.valueOf(mItemCostStr)));
        root.appendChild(node);

        node = doc.createElement("ItemPriceStr");
        node.appendChild(doc.createTextNode(String.valueOf(mItemPriceStr)));
        root.appendChild(node);

        node = doc.createElement("DistBaseCostStr");
        node.appendChild(doc.createTextNode(String.valueOf(mDistBaseCostStr)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public AggregateItemView copy()  {
      AggregateItemView obj = new AggregateItemView();
      obj.setContractId(mContractId);
      obj.setCatalogId(mCatalogId);
      obj.setCatalogStructureId(mCatalogStructureId);
      obj.setContractItemId(mContractItemId);
      obj.setOrderGuideId(mOrderGuideId);
      obj.setOrderGuideStructureId(mOrderGuideStructureId);
      obj.setItemId(mItemId);
      obj.setOrderGuideDesc(mOrderGuideDesc);
      obj.setDistDesc(mDistDesc);
      obj.setContractDesc(mContractDesc);
      obj.setCatalogDesc(mCatalogDesc);
      obj.setCatalogStatus(mCatalogStatus);
      obj.setCatalogType(mCatalogType);
      obj.setContractStatus(mContractStatus);
      obj.setAccountDesc(mAccountDesc);
      obj.setCategories(mCategories);
      obj.setCustSku(mCustSku);
      obj.setDistId(mDistId);
      obj.setItemCost(mItemCost);
      obj.setItemPrice(mItemPrice);
      obj.setDistBaseCost(mDistBaseCost);
      obj.setDistIdStr(mDistIdStr);
      obj.setItemCostStr(mItemCostStr);
      obj.setItemPriceStr(mItemPriceStr);
      obj.setDistBaseCostStr(mDistBaseCostStr);
      
      return obj;
    }

    
    /**
     * Sets the ContractId property.
     *
     * @param pContractId
     *  int to use to update the property.
     */
    public void setContractId(int pContractId){
        this.mContractId = pContractId;
    }
    /**
     * Retrieves the ContractId property.
     *
     * @return
     *  int containing the ContractId property.
     */
    public int getContractId(){
        return mContractId;
    }


    /**
     * Sets the CatalogId property.
     *
     * @param pCatalogId
     *  int to use to update the property.
     */
    public void setCatalogId(int pCatalogId){
        this.mCatalogId = pCatalogId;
    }
    /**
     * Retrieves the CatalogId property.
     *
     * @return
     *  int containing the CatalogId property.
     */
    public int getCatalogId(){
        return mCatalogId;
    }


    /**
     * Sets the CatalogStructureId property.
     *
     * @param pCatalogStructureId
     *  int to use to update the property.
     */
    public void setCatalogStructureId(int pCatalogStructureId){
        this.mCatalogStructureId = pCatalogStructureId;
    }
    /**
     * Retrieves the CatalogStructureId property.
     *
     * @return
     *  int containing the CatalogStructureId property.
     */
    public int getCatalogStructureId(){
        return mCatalogStructureId;
    }


    /**
     * Sets the ContractItemId property.
     *
     * @param pContractItemId
     *  int to use to update the property.
     */
    public void setContractItemId(int pContractItemId){
        this.mContractItemId = pContractItemId;
    }
    /**
     * Retrieves the ContractItemId property.
     *
     * @return
     *  int containing the ContractItemId property.
     */
    public int getContractItemId(){
        return mContractItemId;
    }


    /**
     * Sets the OrderGuideId property.
     *
     * @param pOrderGuideId
     *  int to use to update the property.
     */
    public void setOrderGuideId(int pOrderGuideId){
        this.mOrderGuideId = pOrderGuideId;
    }
    /**
     * Retrieves the OrderGuideId property.
     *
     * @return
     *  int containing the OrderGuideId property.
     */
    public int getOrderGuideId(){
        return mOrderGuideId;
    }


    /**
     * Sets the OrderGuideStructureId property.
     *
     * @param pOrderGuideStructureId
     *  int to use to update the property.
     */
    public void setOrderGuideStructureId(int pOrderGuideStructureId){
        this.mOrderGuideStructureId = pOrderGuideStructureId;
    }
    /**
     * Retrieves the OrderGuideStructureId property.
     *
     * @return
     *  int containing the OrderGuideStructureId property.
     */
    public int getOrderGuideStructureId(){
        return mOrderGuideStructureId;
    }


    /**
     * Sets the ItemId property.
     *
     * @param pItemId
     *  int to use to update the property.
     */
    public void setItemId(int pItemId){
        this.mItemId = pItemId;
    }
    /**
     * Retrieves the ItemId property.
     *
     * @return
     *  int containing the ItemId property.
     */
    public int getItemId(){
        return mItemId;
    }


    /**
     * Sets the OrderGuideDesc property.
     *
     * @param pOrderGuideDesc
     *  String to use to update the property.
     */
    public void setOrderGuideDesc(String pOrderGuideDesc){
        this.mOrderGuideDesc = pOrderGuideDesc;
    }
    /**
     * Retrieves the OrderGuideDesc property.
     *
     * @return
     *  String containing the OrderGuideDesc property.
     */
    public String getOrderGuideDesc(){
        return mOrderGuideDesc;
    }


    /**
     * Sets the DistDesc property.
     *
     * @param pDistDesc
     *  String to use to update the property.
     */
    public void setDistDesc(String pDistDesc){
        this.mDistDesc = pDistDesc;
    }
    /**
     * Retrieves the DistDesc property.
     *
     * @return
     *  String containing the DistDesc property.
     */
    public String getDistDesc(){
        return mDistDesc;
    }


    /**
     * Sets the ContractDesc property.
     *
     * @param pContractDesc
     *  String to use to update the property.
     */
    public void setContractDesc(String pContractDesc){
        this.mContractDesc = pContractDesc;
    }
    /**
     * Retrieves the ContractDesc property.
     *
     * @return
     *  String containing the ContractDesc property.
     */
    public String getContractDesc(){
        return mContractDesc;
    }


    /**
     * Sets the CatalogDesc property.
     *
     * @param pCatalogDesc
     *  String to use to update the property.
     */
    public void setCatalogDesc(String pCatalogDesc){
        this.mCatalogDesc = pCatalogDesc;
    }
    /**
     * Retrieves the CatalogDesc property.
     *
     * @return
     *  String containing the CatalogDesc property.
     */
    public String getCatalogDesc(){
        return mCatalogDesc;
    }


    /**
     * Sets the CatalogStatus property.
     *
     * @param pCatalogStatus
     *  String to use to update the property.
     */
    public void setCatalogStatus(String pCatalogStatus){
        this.mCatalogStatus = pCatalogStatus;
    }
    /**
     * Retrieves the CatalogStatus property.
     *
     * @return
     *  String containing the CatalogStatus property.
     */
    public String getCatalogStatus(){
        return mCatalogStatus;
    }


    /**
     * Sets the CatalogType property.
     *
     * @param pCatalogType
     *  String to use to update the property.
     */
    public void setCatalogType(String pCatalogType){
        this.mCatalogType = pCatalogType;
    }
    /**
     * Retrieves the CatalogType property.
     *
     * @return
     *  String containing the CatalogType property.
     */
    public String getCatalogType(){
        return mCatalogType;
    }


    /**
     * Sets the ContractStatus property.
     *
     * @param pContractStatus
     *  String to use to update the property.
     */
    public void setContractStatus(String pContractStatus){
        this.mContractStatus = pContractStatus;
    }
    /**
     * Retrieves the ContractStatus property.
     *
     * @return
     *  String containing the ContractStatus property.
     */
    public String getContractStatus(){
        return mContractStatus;
    }


    /**
     * Sets the AccountDesc property.
     *
     * @param pAccountDesc
     *  String to use to update the property.
     */
    public void setAccountDesc(String pAccountDesc){
        this.mAccountDesc = pAccountDesc;
    }
    /**
     * Retrieves the AccountDesc property.
     *
     * @return
     *  String containing the AccountDesc property.
     */
    public String getAccountDesc(){
        return mAccountDesc;
    }


    /**
     * Sets the Categories property.
     *
     * @param pCategories
     *  List to use to update the property.
     */
    public void setCategories(List pCategories){
        this.mCategories = pCategories;
    }
    /**
     * Retrieves the Categories property.
     *
     * @return
     *  List containing the Categories property.
     */
    public List getCategories(){
        return mCategories;
    }


    /**
    * Keeps track if the CustSku property has been changed.  This is a best guess
    * as if the property was changed and then changed back to the original value it would not be reflected here.
    */
    private boolean mCustSkuChanged = false;
    public boolean isCustSkuChanged(){
        return mCustSkuChanged;
    }
    
    /**
     * Sets the CustSku property.
     *
     * @param pCustSku
     *  String to use to update the property.
     */
    public void setCustSku(String pCustSku){
        
        if(this.mCustSku == null && pCustSku!= null){
                this.mCustSkuChanged = true;
                this.mCustSku = pCustSku;
        }else if(this.mCustSku == null && pCustSku == null){
        }else if(!this.mCustSku.equals(pCustSku)){
                this.mCustSkuChanged = true;
                this.mCustSku = pCustSku;
        }
          
    }
    /**
     * Retrieves the CustSku property.
     *
     * @return
     *  String containing the CustSku property.
     */
    public String getCustSku(){
        return mCustSku;
    }


    /**
    * Keeps track if the DistId property has been changed.  This is a best guess
    * as if the property was changed and then changed back to the original value it would not be reflected here.
    */
    private boolean mDistIdChanged = false;
    public boolean isDistIdChanged(){
        return mDistIdChanged;
    }
    
    /**
     * Sets the DistId property.
     *
     * @param pDistId
     *  int to use to update the property.
     */
    public void setDistId(int pDistId){
        
        if(this.mDistId!=(pDistId)){
                mDistIdChanged = true;
                this.mDistId = pDistId;
        }
          
    }
    /**
     * Retrieves the DistId property.
     *
     * @return
     *  int containing the DistId property.
     */
    public int getDistId(){
        return mDistId;
    }


    /**
    * Keeps track if the ItemCost property has been changed.  This is a best guess
    * as if the property was changed and then changed back to the original value it would not be reflected here.
    */
    private boolean mItemCostChanged = false;
    public boolean isItemCostChanged(){
        return mItemCostChanged;
    }
    
    /**
     * Sets the ItemCost property.
     *
     * @param pItemCost
     *  BigDecimal to use to update the property.
     */
    public void setItemCost(BigDecimal pItemCost){
        
        if(this.mItemCost == null && pItemCost!= null){
                this.mItemCostChanged = true;
                this.mItemCost = pItemCost;
        }else if(this.mItemCost == null && pItemCost == null){
        }else if(!this.mItemCost.equals(pItemCost)){
                this.mItemCostChanged = true;
                this.mItemCost = pItemCost;
        }
          
    }
    /**
     * Retrieves the ItemCost property.
     *
     * @return
     *  BigDecimal containing the ItemCost property.
     */
    public BigDecimal getItemCost(){
        return mItemCost;
    }


    /**
    * Keeps track if the ItemPrice property has been changed.  This is a best guess
    * as if the property was changed and then changed back to the original value it would not be reflected here.
    */
    private boolean mItemPriceChanged = false;
    public boolean isItemPriceChanged(){
        return mItemPriceChanged;
    }
    
    /**
     * Sets the ItemPrice property.
     *
     * @param pItemPrice
     *  BigDecimal to use to update the property.
     */
    public void setItemPrice(BigDecimal pItemPrice){
        
        if(this.mItemPrice == null && pItemPrice!= null){
                this.mItemPriceChanged = true;
                this.mItemPrice = pItemPrice;
        }else if(this.mItemPrice == null && pItemPrice == null){
        }else if(!this.mItemPrice.equals(pItemPrice)){
                this.mItemPriceChanged = true;
                this.mItemPrice = pItemPrice;
        }
          
    }
    /**
     * Retrieves the ItemPrice property.
     *
     * @return
     *  BigDecimal containing the ItemPrice property.
     */
    public BigDecimal getItemPrice(){
        return mItemPrice;
    }


    /**
    * Keeps track if the DistBaseCost property has been changed.  This is a best guess
    * as if the property was changed and then changed back to the original value it would not be reflected here.
    */
    private boolean mDistBaseCostChanged = false;
    public boolean isDistBaseCostChanged(){
        return mDistBaseCostChanged;
    }
    
    /**
     * Sets the DistBaseCost property.
     *
     * @param pDistBaseCost
     *  BigDecimal to use to update the property.
     */
    public void setDistBaseCost(BigDecimal pDistBaseCost){
        
        if(this.mDistBaseCost == null && pDistBaseCost!= null){
                this.mDistBaseCostChanged = true;
                this.mDistBaseCost = pDistBaseCost;
        }else if(this.mDistBaseCost == null && pDistBaseCost == null){
        }else if(!this.mDistBaseCost.equals(pDistBaseCost)){
                this.mDistBaseCostChanged = true;
                this.mDistBaseCost = pDistBaseCost;
        }
          
    }
    /**
     * Retrieves the DistBaseCost property.
     *
     * @return
     *  BigDecimal containing the DistBaseCost property.
     */
    public BigDecimal getDistBaseCost(){
        return mDistBaseCost;
    }


    /**
     * Sets the DistIdStr property.
     *
     * @param pDistIdStr
     *  String to use to update the property.
     */
    public void setDistIdStr(String pDistIdStr){
        this.mDistIdStr = pDistIdStr;
    }
    /**
     * Retrieves the DistIdStr property.
     *
     * @return
     *  String containing the DistIdStr property.
     */
    public String getDistIdStr(){
        return mDistIdStr;
    }


    /**
     * Sets the ItemCostStr property.
     *
     * @param pItemCostStr
     *  String to use to update the property.
     */
    public void setItemCostStr(String pItemCostStr){
        this.mItemCostStr = pItemCostStr;
    }
    /**
     * Retrieves the ItemCostStr property.
     *
     * @return
     *  String containing the ItemCostStr property.
     */
    public String getItemCostStr(){
        return mItemCostStr;
    }


    /**
     * Sets the ItemPriceStr property.
     *
     * @param pItemPriceStr
     *  String to use to update the property.
     */
    public void setItemPriceStr(String pItemPriceStr){
        this.mItemPriceStr = pItemPriceStr;
    }
    /**
     * Retrieves the ItemPriceStr property.
     *
     * @return
     *  String containing the ItemPriceStr property.
     */
    public String getItemPriceStr(){
        return mItemPriceStr;
    }


    /**
     * Sets the DistBaseCostStr property.
     *
     * @param pDistBaseCostStr
     *  String to use to update the property.
     */
    public void setDistBaseCostStr(String pDistBaseCostStr){
        this.mDistBaseCostStr = pDistBaseCostStr;
    }
    /**
     * Retrieves the DistBaseCostStr property.
     *
     * @return
     *  String containing the DistBaseCostStr property.
     */
    public String getDistBaseCostStr(){
        return mDistBaseCostStr;
    }


    /**
    *Resets any state tracking this pbject may contain;
    */
    public void reset(){
        
        this.mCustSkuChanged = false;
        this.mDistIdChanged = false;
        this.mItemCostChanged = false;
        this.mItemPriceChanged = false;
        this.mDistBaseCostChanged = false;
    }
    
    
}
