package com.cleanwise.service.api.session;

/**
 * Title:        Template
 * Description:  Remote Interface for Template Stateless Session Bean
 * Purpose:      Provides access to the services for managing template information.
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendWise, Inc.
 * @author       John Esielionis, eSpendWise, Inc.
 */

import java.rmi.RemoteException;
import java.util.Map;

import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.TemplateDataExtended;
import com.cleanwise.service.api.value.TemplateDataVector;

/**
 * Remote interface for the <code>Template</code> stateless session bean.
 */
public interface Template extends javax.ejb.EJBObject
{

    /**
     * Method to add a template.
     * @param templateData a <code>TemplateDataExtended</code> value
     * @return a <code>TemplateDataExtended</code> value
     * @exception RemoteException if an error occurs
     */
    public TemplateDataExtended createTemplate(TemplateDataExtended templateData) throws RemoteException;

    /**
     * Method to retrieve all templates that match the given criteria.
     * @param BusEntitySearchCriteria the criteria to use in selecting the templates
     * @return a <code>TemplateDataVector</code> of matching templates
     * @exception RemoteException if an error occurs
     */
    public TemplateDataVector getTemplateByCriteria(EntitySearchCriteria entitySearchCriteria) throws RemoteException;

    /**
     * Method to retrieve all system default templates.
     * @return a <code>TemplateDataVector</code> of system default templates
     * @exception RemoteException if an error occurs
     */
    public TemplateDataVector getSystemDefaultTemplates() throws RemoteException;

    /**
     * Method to update the information for a template.
     * @param templateData a <code>TemplateDataExtended</code> value
     * @return a <code>TemplateDataExtended</code> value
     * @exception RemoteException if an error occurs
     */
    public TemplateDataExtended modifyTemplate(TemplateDataExtended templateData) throws RemoteException;

    /**
     * Method to delete a template.
     * @param templateData a <code>TemplateDataExtended</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public void deleteTemplate(TemplateDataExtended templateData) throws RemoteException;

    /**
     * Method to retrieve the default object map for a template.
     * @param Map a <code>Map</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public Map getDefaultObjectMap(String templateType, Map parameters) throws RemoteException;

}
