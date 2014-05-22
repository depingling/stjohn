/*
 * SelectableObjectList.java
 *
 * Created on July 17, 2003, 3:45 PM
 */

package com.cleanwise.view.utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * Class that may be used to facilitate the task of creating jsp pages that need to have selectable
 * elements to them.  This class will take care of the matching and state as far as what was originally
 * set and what is set now.
 * @author  bstevens
 * 12/9/2008: Sergei Cher added: SelectableObjects(List, List, Comparator, int) constructor
 *                               selectableAndConfObjects(List, List, Comparator) method
 */
public class SelectableObjects{
	
	public static Logger log = Logger.getLogger(SelectableObjects.class);
	
    private List mValues;
    private List mCachedValueList; //caches a list of the values of the SelectableObjects
    
    /** Creates a new instance of SelectableObjectList.  If the optional comparator is supplied
     * it will be used to determine equality, otherwise the equals method of the objects is used.*/
    public SelectableObjects(List selected, List allOptions, Comparator pOptionalComp) {
    	
    	log.info("**********SVC SelectableObjects class: " + "selected = " + selected);//SVC 
    	log.info("**********SVC SelectableObjects class: " + "allOptions = " + allOptions);//SVC 
        
    	selectableObjects(selected,allOptions,pOptionalComp);
    }

    /** Creates a new instance of SelectableObjectList.  If the optional comparator is supplied
     * it will be used to determine equality, otherwise the equals method of the objects is used.*/
    public SelectableObjects(List selected, List allOptions, Comparator pOptionalComp, int showConfOnlyFl) {
    	
    	//log.info("**********SVC SelectableObjects class: " + "selected = " + selected);//SVC 
    	//log.info("**********SVC SelectableObjects class: " + "allOptions = " + allOptions);//SVC 
        
    	if (showConfOnlyFl == 0){ //configured and non-configured List of elements
    	    selectableObjects(selected,allOptions,pOptionalComp);
    	} else { //configured only List of elements configured and non-configured List of elements
    		selectableAndConfObjects(selected,allOptions,pOptionalComp);
    	}
    }
    
    /** Creates a new instance of SelectableObjectList.  If the optional comparator is supplied
     * it will be used to determine equality, otherwise the equals method of the objects is used.*/
    public SelectableObjects(List selected, List allOptions, Comparator pOptionalComp,boolean selectOnlyTrue) {
        selectableObjects(selected,allOptions,pOptionalComp, selectOnlyTrue);
    }


    protected void selectableObjects(List selected, List allOptions, Comparator pOptionalComp,boolean selectOnlyTrue) {
        if(selectOnlyTrue) {
            mValues = new ArrayList();
            Iterator it = allOptions.iterator();
            while(it.hasNext()){
                Object o = it.next();
                SelectableObject newEntry = new SelectableObject();
                if(selected != null){
                    Iterator selIt = selected.iterator();
                    while(selIt.hasNext()){
                        Object selectedObj = selIt.next();
                        if(pOptionalComp == null){
                            if(o.equals(selectedObj)){
                                newEntry.setValue(o);
                                newEntry.setSelected(true);
                                newEntry.setOriginallySelected(true);
                                mValues.add(newEntry);

                            }
                        }else{
                            if(pOptionalComp.compare(o, selectedObj)==0){
                                newEntry.setValue(o);
                                newEntry.setSelected(true);
                                newEntry.setOriginallySelected(true);
                                mValues.add(newEntry);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            selectableObjects(selected,allOptions,pOptionalComp);
        }

    }

    protected void selectableObjects(List selected, List allOptions, Comparator pOptionalComp)
    {
    	log.info("selectableObjects().Begin");
        mValues = new ArrayList();
        Iterator it = allOptions.iterator();
        while(it.hasNext()){
            Object o = it.next();
            SelectableObject newEntry = new SelectableObject();
            newEntry.setValue(o);
            if(selected != null){
                Iterator selIt = selected.iterator();
                while(selIt.hasNext()){
                    Object selectedObj = selIt.next();
                    if(pOptionalComp == null){
                        if(o.equals(selectedObj)){
                            newEntry.setSelected(true);
                            newEntry.setOriginallySelected(true);
                        }
                    }else{
                        if(pOptionalComp.compare(o, selectedObj)==0){
                            newEntry.setSelected(true);
                            newEntry.setOriginallySelected(true);
                        }
                    }
                }
            }
            mValues.add(newEntry);
        }
        
        log.info("selectableObjects(): " + "mValues size = " + mValues.size());//SVC 
        log.info("selectableObjects(): " + "mValues = " + mValues);//SVC 
        log.info("selectableObjects().End");
    }
 
    protected void selectableAndConfObjects(List selected, List allOptions, Comparator pOptionalComp)
    {
    	int fl = 0; //flag
        mValues = new ArrayList();
        Iterator it = allOptions.iterator();
        while(it.hasNext()){
            Object o = it.next();
            SelectableObject newEntry = new SelectableObject();
            newEntry.setValue(o);
            if(selected != null){
            	fl = 0;
                Iterator selIt = selected.iterator();
                while(selIt.hasNext()){
                    Object selectedObj = selIt.next();
                    if(pOptionalComp == null){
                        if(o.equals(selectedObj)){
                            newEntry.setSelected(true);
                            newEntry.setOriginallySelected(true);
                            fl = 1;
                        }
                    }else{
                        if(pOptionalComp.compare(o, selectedObj)==0){
                            newEntry.setSelected(true);
                            newEntry.setOriginallySelected(true);
                            fl = 1;
                        }
                    }
                }                
            }
            if (fl == 1) {
            	mValues.add(newEntry);
            }
            //mValues.add(newEntry);
        }
        
    	log.info("selectableAndConfObjects(): selectableObjects() " + "mValues size = " + mValues.size());//SVC 
    }
    /**
     *JSP indexed property accessor
     */
    public Object getValue(int idx){
        if(idx>mValues.size()){
            return null;
        }
        return ((SelectableObject)mValues.get(idx)).getValue();
    }
    
    /**
     *Jsp indexed property accessor
     */
    public void setValue(int idx, Object pValue){
        int len = mValues.size();
        SelectableObject sel;
        if(len<=idx){
            while(len<idx){
                sel = new SelectableObject();
                mValues.add(sel);
            }
            sel = new SelectableObject();
            mValues.add(sel);
        }else{
            sel = (SelectableObject) mValues.get(idx);
        }
        sel.setValue(pValue);
    }
    
    /**
     *Jsp indexed property accessor
     */
    public boolean getSelected(int idx){
        if(idx>mValues.size()){
            return false;
        }
        return ((SelectableObject)mValues.get(idx)).isSelected();
    }
    
    /**
     *Jsp indexed property accessor
     */
    public void setSelected(int idx, boolean pValue){
        int len = mValues.size();
        SelectableObject sel;
        if(len<=idx){
            while(len<idx){
                sel = new SelectableObject();
                mValues.add(sel);
            }
            sel = new SelectableObject();
            mValues.add(sel);
        }else{
            sel = (SelectableObject) mValues.get(idx);
        }
        sel.setSelected(pValue);
    }
    
    /**
     *Returns a list of all the objects that were originally not selected but are now selected.
     */
    public List getNewlySelected(){
        ArrayList returnList = new ArrayList();
        Iterator it = mValues.iterator();
        while(it.hasNext()){
            SelectableObject sel = (SelectableObject) it.next();
            if(sel.isSelected() && !sel.isOriginallySelected()){
                if(sel.getValue() != null){
                    returnList.add(sel.getValue());
                }
            }
        }
        return returnList;
    }
    
    /**
     *Returns a list of all the objects that are selected, whether they are newly
     *selected or were originally selected.  You should use this method for validation
     *primarily, the @see getNewlySelected method will tell you what has been changed
     *to selected and @see getDeselected method for what has been unchecked.
     */
    public List getCurrentlySelected(){
        ArrayList returnList = new ArrayList();
        Iterator it = mValues.iterator();
        while(it.hasNext()){
            SelectableObject sel = (SelectableObject) it.next();
            if(sel.isSelected()){
                if(sel.getValue() != null){
                    returnList.add(sel.getValue());
                }
            }
        }
        return returnList;
    }
    
    /**
     *Returns a list of all the objects that were originally selected but are now not selected.
     */
    public List getDeselected(){
        ArrayList returnList = new ArrayList();
        Iterator it = mValues.iterator();
        while(it.hasNext()){
            SelectableObject sel = (SelectableObject) it.next();
            if(!sel.isSelected() && sel.isOriginallySelected()){
                if(sel.getValue() != null){
                    returnList.add(sel.getValue());
                }
            }
        }
        log.info("getDeselected(): returnList = " + returnList);
        return returnList;
    }
    
    /**
     *Returns a List of all the values represented by this class.
     */
    public List getValues(){
        if(mCachedValueList == null){
            mCachedValueList = new ArrayList();
            Iterator it = mValues.iterator();
            while(it.hasNext()){
                SelectableObject sel = (SelectableObject) it.next();
                mCachedValueList.add(sel.getValue());
            }
        }
        return mCachedValueList;
    }
    
    /**
     *Resets the internal state as to what has and has not been checked to the current state of
     *what is currently selected.  Thus after calling this method calls to getNewlySelected and
     *getDeselected will return empty lists.  This method should be called if this object has been
     *queried and needs to be used again, thus the series should be:
     *List deSelected = selectableObject.getDeselected();
     *List newlySelected = selectableObject.getNewlySelected();
     *selectableObject.resetState();
     */
    public void resetState(){
        Iterator it = mValues.iterator();
        while(it.hasNext()){
            SelectableObject sel = (SelectableObject) it.next();
            sel.setOriginallySelected(sel.isSelected());
        }
    }
    
    /**
     *Handles the reset call from the struts Action Form reset method.  This is necessary for the
     *checkboses to correctly register the fact that they have been unchecked.  This method should
     *always and only be called from the struts
     *ActionForm.reset(ActionMapping mapping, HttpServletRequest request)
     *method.
     */
    public void handleStutsFormResetRequest(){
        Iterator it = mValues.iterator();
        while(it.hasNext()){
            SelectableObject sel = (SelectableObject) it.next();
            sel.setSelected(false);
        }
    }
    
    /**
     *Private inner class used to represent the state of the selected object
     */
    private class SelectableObject {
        private boolean mOriginallySelected;
        private boolean mSelected;
        private Object mValue;
        private boolean isSelected(){
            return mSelected;
        }
        private void setSelected(boolean pSelected){
            mSelected = pSelected;
        }
        private boolean isOriginallySelected(){
            return mOriginallySelected;
        }
        private void setOriginallySelected(boolean pOriginallySelected){
            mOriginallySelected = pOriginallySelected;
        }
        private Object getValue(){
            return mValue;
        }
        private void setValue(Object pValue){
            mValue = pValue;
        }
    }
}
