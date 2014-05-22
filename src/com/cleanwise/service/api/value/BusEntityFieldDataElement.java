/*
 * BusEntityFieldDataElement.java
 *
 * Created on March 1, 2004, 5:03 PM
 */

package com.cleanwise.service.api.value;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.framework.ValueObject;

/**
 *
 * @author  bstevens
 */
public class BusEntityFieldDataElement extends ValueObject{
    /**
     *Converts a list of BusEntityFieldDataElements to a PropertyDataVector.
     */
    public static PropertyDataVector toPropertyDataVector(List pBusEntityFieldDataElementList){
        PropertyDataVector retList = new PropertyDataVector();
        Iterator it = pBusEntityFieldDataElementList.iterator();
        while(it.hasNext()){
            BusEntityFieldDataElement b = (BusEntityFieldDataElement) it.next();
            retList.add(b.getPropertyData());
        }
        return retList;
    }
    
    /**
     *Create a list of BusEntityFieldDataElement objects initialized acording to the passed in
     *propertyDataVector and BusEntityFieldsData.  The propType and busEntityId are used only if
     *a property does not already exist in the list of properties passed in and one needs to be created.
     *They are needed only if on updates the propertData.busEntityId is not set and the 
     *propertData.propertyTypeCd is not set.
     */
    public static List createBusEntityFieldDataElementCol(
        BusEntityFieldsData bfd, PropertyDataVector pv,String propType, int busEntityId){
        ArrayList retList = new ArrayList();
        List bfdl = getBusFieldsDataAsCol(bfd);
        Iterator it = bfdl.iterator();
        while(it.hasNext()){
            BusFieldsDataDef def = (BusFieldsDataDef) it.next();
            String tag = (String) def.tag;
            Iterator it2 = pv.iterator();
            boolean found = false;
            BusEntityFieldDataElement fde = null;
            while(it2.hasNext()){
                PropertyData p = (PropertyData) it2.next();
                if(p.getShortDesc().equals(tag)){
                    found = true;
                    fde = new BusEntityFieldDataElement(p);
                }
            }
            if(!found){
                PropertyData p = PropertyData.createValue();
                p.setShortDesc(tag);
                p.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
                p.setPropertyTypeCd(propType);
                p.setBusEntityId(busEntityId);
                fde = new BusEntityFieldDataElement(p);
            }
            fde.setRequired(def.required);
            fde.setShowAdmin(def.showAdmin);
            fde.setShowRuntime(def.showRuntime);
            retList.add(fde);
        }
        return retList;
    }
    
    /** Holds value of property showAdmin. */
    private boolean showAdmin;
    
    /** Holds value of property showRuntime. */
    private boolean showRuntime;

    /** Holds value of property required. */
    private boolean required;
    
    /** Holds value of property propertyData. */
    private PropertyData propertyData;
    
    /** Creates a new instance of BusEntityFieldDataElement */
    public BusEntityFieldDataElement(PropertyData p) {
        setPropertyData(p);
    }
    
    /** Getter for property showAdmin.
     * @return Value of property showAdmin.
     *
     */
    public boolean isShowAdmin() {
        return this.showAdmin;
    }
    
    /** Setter for property showAdmin.
     * @param showAdmin New value of property showAdmin.
     *
     */
    public void setShowAdmin(boolean showAdmin) {
        this.showAdmin = showAdmin;
    }
    
    /** Getter for property showRuntime.
     * @return Value of property showRuntime.
     *
     */
    public boolean isShowRuntime() {
        return this.showRuntime;
    }
    
    /** Setter for property showRuntime.
     * @param showRuntime New value of property showRuntime.
     *
     */
    public void setShowRuntime(boolean showRuntime) {
        this.showRuntime = showRuntime;
    }
    
    public boolean isRequired() {
		return required;
	}
	
	public void setRequired(boolean required) {
        this.required = required;
    }
    
    /** Getter for property tag.
     * @return Value of property tag.
     *
     */
    public String getTag() {
        return getPropertyData().getShortDesc();
    }
    
    /** Setter for property tag.
     * @param tag New value of property tag.
     *
     */
    public void setTag(String tag) {
        getPropertyData().setShortDesc(tag);
    }
    
    /** Getter for property value.
     * @return Value of property value.
     *
     */
    public String getValue() {
        return getPropertyData().getValue();
    }
    
    /** Setter for property value.
     * @param value New value of property value.
     *
     */
    public void setValue(String value) {
        getPropertyData().setValue(value);
    }
    
    /** Getter for property propertyData.
     * @return Value of property propertyData.
     *
     */
    public PropertyData getPropertyData() {
        return this.propertyData;
    }
    
    /** Setter for property propertyData.
     * @param propertyData New value of property propertyData.
     *
     */
    public void setPropertyData(PropertyData propertyData) {
        this.propertyData = propertyData;
    }
    
    
    /**
     *Returns the tags represented as a list
     */
    private static List getBusFieldsDataAsCol(BusEntityFieldsData bfd){
        List l = new ArrayList();
        l.add(new BusFieldsDataDef(bfd.getF1Tag(), bfd.getF1ShowAdmin(), bfd.getF1ShowRuntime(), bfd.getF1Required()));
        l.add(new BusFieldsDataDef(bfd.getF2Tag(), bfd.getF2ShowAdmin(), bfd.getF2ShowRuntime(), bfd.getF2Required()));
        l.add(new BusFieldsDataDef(bfd.getF3Tag(), bfd.getF3ShowAdmin(), bfd.getF3ShowRuntime(), bfd.getF3Required()));
        l.add(new BusFieldsDataDef(bfd.getF4Tag(), bfd.getF4ShowAdmin(), bfd.getF4ShowRuntime(), bfd.getF4Required()));
        l.add(new BusFieldsDataDef(bfd.getF5Tag(), bfd.getF5ShowAdmin(), bfd.getF5ShowRuntime(), bfd.getF5Required()));
        l.add(new BusFieldsDataDef(bfd.getF6Tag(), bfd.getF6ShowAdmin(), bfd.getF6ShowRuntime(), bfd.getF6Required()));
        l.add(new BusFieldsDataDef(bfd.getF7Tag(), bfd.getF7ShowAdmin(), bfd.getF7ShowRuntime(), bfd.getF7Required()));
        l.add(new BusFieldsDataDef(bfd.getF8Tag(), bfd.getF8ShowAdmin(), bfd.getF8ShowRuntime(), bfd.getF8Required()));
        l.add(new BusFieldsDataDef(bfd.getF9Tag(), bfd.getF9ShowAdmin(), bfd.getF9ShowRuntime(), bfd.getF9Required()));
        l.add(new BusFieldsDataDef(bfd.getF10Tag(), bfd.getF10ShowAdmin(), bfd.getF10ShowRuntime(), bfd.getF10Required()));
        return l;
    }
    
    private static class BusFieldsDataDef{
        String tag;
        boolean showAdmin;
        boolean showRuntime;
        boolean required;
        public BusFieldsDataDef(String pTag, boolean pShowAdmin, boolean pShowRuntime, boolean pRequired){
            showAdmin = pShowAdmin;
            showRuntime = pShowRuntime;
            tag = pTag;
            required = pRequired;
        }
    }
}
