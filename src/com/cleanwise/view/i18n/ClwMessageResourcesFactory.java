/*
 * ClwMessageResourcesFactory.java
 *
 * Created on January 18, 2005, 2:19 PM
 */

package com.cleanwise.view.i18n;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.MessageResources;
import com.cleanwise.service.api.APIAccess;
/**
 *
 * @author bstevens
 */
public class ClwMessageResourcesFactory  extends MessageResourcesFactory {
    
    /**
     * Create and return a newly instansiated <code>MessageResources</code>.
     * This method must be implemented by concrete subclasses.
     *
     * @param config Configuration parameter(s) for the requested bundle
     */
	 public MessageResources createResources(String config) {
         return new ClwMessageResourcesImpl(this, config, this.returnNull);
 }
    
}
