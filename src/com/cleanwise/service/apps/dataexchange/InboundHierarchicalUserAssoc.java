
package com.cleanwise.service.apps.dataexchange;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.Utility;

import com.cleanwise.service.api.value.UserAssocRequestData;
/*
 * Created on Apr 19, 2005
 * Description:  deals with the loading of a hierarchical user file (i.e.
 * employee A has access to site 1, employee B has access to site 2, boss C has
 * access to sites 1 and 2.
 * Copyright:    Copyright (c) 2005
 * Company:      Cleanwise, Inc.
 */
public class InboundHierarchicalUserAssoc extends InboundFlatFile{
	protected Logger log = Logger.getLogger(this.getClass());
    ArrayList toProcess = new ArrayList();
    HashMap flattenedTree = new HashMap();
    
    /**
     * Adds the parsed object to the list of objects that will be delt with on post
     * processing.  We really need to know about all records before we can continue
     * as it is a self referenceing file.
     */
    protected void processParsedObject(Object pParsedObject){
        toProcess.add(pParsedObject);
    }
    
    private void createMapEntry(UserAssocRequestData req){
        String key = req.getCustomerSystemKeyParent();
        ArrayList l = (ArrayList) flattenedTree.get(key);
        if(l == null){
            l = new ArrayList();
            flattenedTree.put(key,l);
        }
        l.add(req);
    }
    
    /**
     * Creates the actual objects for loading.
     */
    protected void doPostProcessing(){
        
        //first loop through the data and create a map
        
        Iterator it = toProcess.iterator();
        if(toProcess.isEmpty()){
            return;
        }
        boolean first = true;
        UserAssocRequestData tipTop = null;
        while(it.hasNext()){
            Object o = it.next();
            if(o instanceof UserAssocRequestData){
                UserAssocRequestData req = (UserAssocRequestData) o;
                //for throws first record we have to create a dummy parent of.  The top people in the organization
                //have a parent node that there is no record for in the file
                if(first){
                    first = false;
                    tipTop = new UserAssocRequestData();
                    tipTop.setCustomerSystemKey(req.getCustomerSystemKeyParent());
                    tipTop.setCustomerSystemKeyParent("0");
                    tipTop.setSiteBudgetReference("0");
                    createMapEntry(tipTop);
                }
                createMapEntry(req);
            }else{
                throw new ClassCastException("InboundHierarchicalUserAssoc only parses UserAssocRequestData data objects");
            }
        }
        if(tipTop != null){
            toProcess.add(tipTop);
        }
        
        //debug the map
        it = flattenedTree.keySet().iterator();
        while(it.hasNext()){
            String key = (String) it.next();
            List l = (List)flattenedTree.get(key);
            Iterator it2 = l.iterator();
            while(it2.hasNext()){
                log.debug("DEBUG--"+key+"::"+((UserAssocRequestData)it2.next()).getCustomerSystemKey());
            }
        }
        
        //loop through again now that we have constructed our map
        it = toProcess.iterator();
        while(it.hasNext()){
            Object o = it.next();
            if(o instanceof UserAssocRequestData){
                UserAssocRequestData req = (UserAssocRequestData) o;
                String theOne = req.getCustomerSystemKey();
                if(theOne == null){
                    continue;
                }
                //Create the association for this particular record
                if(Utility.isSet(req.getSiteBudgetReference())){
                    UserAssocRequestData newReq = new UserAssocRequestData();
                    newReq.setSiteBudgetReference(req.getSiteBudgetReference());
                    newReq.setCustomerSystemKey(req.getCustomerSystemKey());
                    //log.info("Adding request to associate user: "+newReq.getCustomerSystemKey()+ " with site "+newReq.getSiteBudgetReference());
                    addIntegrationRequest(newReq);
                }
                //theOne becomes the key for the user and the decendants is all of the
                //sites that this user has access to, along with some cruft about other
                //users identification
                List decendants = getDescendants(theOne);
                Iterator subIt = decendants.iterator();
                while(subIt.hasNext()){
                    UserAssocRequestData aKid = (UserAssocRequestData) subIt.next();
                    if(Utility.isSet(aKid.getSiteBudgetReference()) && !"0".equals(aKid.getSiteBudgetReference())){
                        UserAssocRequestData newReq = new UserAssocRequestData();
                        newReq.setSiteBudgetReference(aKid.getSiteBudgetReference());
                        newReq.setCustomerSystemKey(theOne);
                        //log.info("Adding request to associate user: "+newReq.getCustomerSystemKey()+ " with site "+newReq.getSiteBudgetReference());
                        addIntegrationRequest(newReq);
                    }
                }
            }
        }
        //mIntegrationRequests.clear();
    }
    
    
    private String debugAList(List l){
        Iterator it = l.iterator();
        while(it.hasNext()){
            Object o = it.next();
            if(o instanceof UserAssocRequestData){
                log.info(((UserAssocRequestData)o).getSiteBudgetReference());
            }else{
                log.info(o);
            }
        }
        return "";
    }
    
    /**
     * returns a list of all the Descendants that this parent has recursivly down the tree
     */
    private List getDescendants(String parent){
    	if(log.isDebugEnabled()){
    		log.debug("Anylizing parent key: "+parent);
        }
        List kids = (List) flattenedTree.get(parent);
        //if we are at the end of the tree then return an empty list
        ArrayList results = new ArrayList();
        if(kids == null || kids.isEmpty()){
            return results;
        }
        if(log.isDebugEnabled()){log.debug("adding kids");}
        results.addAll(kids);
        Iterator it = kids.iterator();
        while(it.hasNext()){
            UserAssocRequestData aKid = (UserAssocRequestData) it.next();
            results.addAll(getDescendants(aKid.getCustomerSystemKey()));
        }
        if(log.isDebugEnabled()){log.debug("returning results: "+debugAList(results));}
        return results;
    }
    
}
