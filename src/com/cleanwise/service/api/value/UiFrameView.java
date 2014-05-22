
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        UiFrameView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.util.RefCodeNames;

import java.lang.*;
import java.util.*;
import java.io.*;

import org.w3c.dom.*;

/**
 * <code>UiFrameView</code> is a ViewObject class for UI.
 */
public class UiFrameView
extends ValueObject
{
   
    private static final long serialVersionUID = 
            -3L
        ;
    private UiFrameData mFrameData;
    private UiFrameSlotViewVector mFrameSlotViewVector;

    /**
     * Constructor.
     */
    public UiFrameView ()
    {
    }

    /**
     * Constructor. 
     */
    public UiFrameView(UiFrameData parm1, UiFrameSlotViewVector parm2)
    {
        mFrameData = parm1;
        mFrameSlotViewVector = parm2;
        
    }

    /**
     * Creates a new UiFrameView
     *
     * @return
     *  Newly initialized UiFrameView object.
     */
    public static UiFrameView createValue () 
    {
        UiFrameView valueView = new UiFrameView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this UiFrameView object
     */
    public String toString()
    {
        return "[" + "FrameData=" + mFrameData + ", FrameSlotViewVector=" + mFrameSlotViewVector + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return ElementNode.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("UiFrame");
	root.setAttribute("Id", String.valueOf(mFrameData));

	Element node;

        node = doc.createElement("FrameSlotViewVector");
        node.appendChild(doc.createTextNode(String.valueOf(mFrameSlotViewVector)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public UiFrameView copy()  {
      UiFrameView obj = new UiFrameView();
      obj.setFrameData(mFrameData);
      obj.setFrameSlotViewVector(mFrameSlotViewVector);
      
      return obj;
    }

    
    /**
     * Sets the FrameData property.
     *
     * @param pFrameData
     *  UiFrameData to use to update the property.
     */
    public void setFrameData(UiFrameData pFrameData){
        this.mFrameData = pFrameData;
    }
    /**
     * Retrieves the FrameData property.
     *
     * @return
     *  UiFrameData containing the FrameData property.
     */
    public UiFrameData getFrameData(){
        return mFrameData;
    }


    /**
     * Sets the FrameSlotViewVector property.
     *
     * @param pFrameSlotViewVector
     *  UiFrameSlotViewVector to use to update the property.
     */
    public void setFrameSlotViewVector(UiFrameSlotViewVector pFrameSlotViewVector){
        this.mFrameSlotViewVector = pFrameSlotViewVector;
    }
    /**
     * Retrieves the FrameSlotViewVector property.
     *
     * @return
     *  UiFrameSlotViewVector containing the FrameSlotViewVector property.
     */
    public UiFrameSlotViewVector getFrameSlotViewVector(){
        return mFrameSlotViewVector;
    }


    public UiFrameSlotViewVector getSlotsByType(String pSlotType){
        UiFrameSlotViewVector result = new UiFrameSlotViewVector();
        if (mFrameSlotViewVector == null || mFrameSlotViewVector.size() == 0) {
            return result;
        }
        Iterator i = mFrameSlotViewVector.iterator();
        while (i.hasNext()) {
            UiFrameSlotView slot = (UiFrameSlotView)i.next();
            if (slot.getSlotTypeCd().equals(pSlotType)) {
                result.add(slot);
            }
        }
        return result;
    }


    public UiFrameSlotView getSlotByType(String pSlotType, int i) {
        UiFrameSlotViewVector slots = getSlotsByType(pSlotType);
        if (slots == null || slots.size() < i ) {
            return null;
        }
        return (UiFrameSlotView)slots.get(i);
    }

    public UiFrameSlotViewVector getImageSlots() {
        return getSlotsByType(RefCodeNames.SLOT_TYPE_CD.IMAGE);
    }

    public UiFrameSlotViewVector getTextSlots() {
        return getSlotsByType(RefCodeNames.SLOT_TYPE_CD.HTML_TEXT);
    }

    public UiFrameSlotView getImageSlot(int i) {
        UiFrameSlotViewVector slots = getSlotsByType(RefCodeNames.SLOT_TYPE_CD.IMAGE);
        if (slots == null || slots.size() < i ) {
            return UiFrameSlotView.createValue();
        }
        return (UiFrameSlotView)slots.get(i);
    }

    public UiFrameSlotView getTextSlot(int i) {
        UiFrameSlotViewVector slots = getSlotsByType(RefCodeNames.SLOT_TYPE_CD.HTML_TEXT);
        if (slots == null || slots.size() < i ) {
            return UiFrameSlotView.createValue();
        }
        return (UiFrameSlotView)slots.get(i);
    }

}
