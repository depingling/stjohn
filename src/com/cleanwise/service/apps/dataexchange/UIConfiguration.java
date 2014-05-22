/*
 * UIConfiguration.java
 *
 * Created on March 4, 2005, 4:18 PM
 */

package com.cleanwise.service.apps.dataexchange;

/**
 * Classes implementing this interface are assumed to be configurable to the 
 *trading partner managment.  The degree of configurability versus custom code
 *is not  implied by implementing this class.
 * @author bstevens
 */
public interface UIConfiguration {
    /**
     *Returns the class that should be configured by the UI.  This class should
     *follow the javaBean fraemwork of properties withe getters and setters.
     */
    public Class getConfigurableBean();
}
