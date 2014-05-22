/*
 * FormTagCompat.java
 *
 * Created on January 17, 2005, 12:44 PM
 */

package com.cleanwise.view.taglibs;

/**
 *
 * @author bstevens
 */
public class FormTagCompat extends org.apache.struts.taglib.html.FormTag{
    /**
     * Holds value of property scope.
     */
    private String scope;

    /**
     * Holds value of property name.
     */
    private String name;

    /**
     * Holds value of property type.
     */
    private String type;

    /**
     * Getter for property scope.
     * @return Value of property scope.
     */
    public String getScope() {

        return this.scope;
    }

    /**
     * Setter for property scope.
     * @param scope New value of property scope.
     */
    public void setScope(String scope) {

        this.scope = scope;
    }

    /**
     * Getter for property name.
     * @return Value of property name.
     */
    public String getName() {

        return this.name;
    }

    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public String getType() {

        return this.type;
    }

    /**
     * Setter for property type.
     * @param type New value of property type.
     */
    public void setType(String type) {

        this.type = type;
    }
    
    
    
}
