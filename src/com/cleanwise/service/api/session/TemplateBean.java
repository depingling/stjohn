package com.cleanwise.service.api.session;

/**
 * Title:        TemplateBean
 * Description:  Bean implementation for Template Stateless Session Bean
 * Purpose:      Provides access to the services for managing template information.
 * Copyright:    Copyright (c) 2010
 * Company:      eSpendWise, Inc.
 * @author       John Esielionis, eSpendWise, Inc.
 */

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.BusEntityDataAccess;
import com.cleanwise.service.api.dao.OrderDAO;
import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.dao.TemplateDataAccess;
import com.cleanwise.service.api.dao.TemplatePropertyDataAccess;
import com.cleanwise.service.api.dto.template.OrderDto;
import com.cleanwise.service.api.dto.template.OrderItemDto;
import com.cleanwise.service.api.framework.BusEntityServicesAPI;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.BusEntityData;
import com.cleanwise.service.api.value.EntitySearchCriteria;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.ItemInfoView;
import com.cleanwise.service.api.value.ItemInfoViewVector;
import com.cleanwise.service.api.value.OrderAddressData;
import com.cleanwise.service.api.value.OrderData;
import com.cleanwise.service.api.value.OrderInfoDataView;
import com.cleanwise.service.api.value.OrderMetaData;
import com.cleanwise.service.api.value.OrderMetaDataVector;
import com.cleanwise.service.api.value.TemplateData;
import com.cleanwise.service.api.value.TemplateDataExtended;
import com.cleanwise.service.api.value.TemplateDataVector;
import com.cleanwise.service.api.value.TemplatePropertyData;
import com.cleanwise.service.api.value.TemplatePropertyDataVector;
import com.cleanwise.view.utils.Constants;

/**
 * <code>Template</code> stateless session bean.
 */
public class TemplateBean extends BusEntityServicesAPI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8199126036817671597L;

	/**
     * Creates a new <code>TemplateBean</code> instance.
     *
     */
    public TemplateBean() {}

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @exception CreateException if an error occurs
     * @exception RemoteException if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {}
    
    /**
     * Method to add a template.
     *
     * @param templateData a <code>TemplateDataExtended</code> value
     * @return a <code>TemplateDataExtended</code> value
     * @exception RemoteException if an error occurs
     */
    public TemplateDataExtended createTemplate(TemplateDataExtended templateData) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            //create the template
            templateData = (TemplateDataExtended) TemplateDataAccess.insert(conn, templateData);
        	//create any properties
            TemplatePropertyDataVector propertyVector = templateData.getProperties();
            TemplatePropertyDataVector updatedProperties = new TemplatePropertyDataVector();
            Iterator<TemplatePropertyData> propertyIterator = propertyVector.iterator();
            while (propertyIterator.hasNext()) {
            	TemplatePropertyData property = propertyIterator.next();
            	//update the property with the id of the template as well as the create by and modified
            	//by values.  Note that the created by date, modified by date, and template property id 
            	//values are all intentionally left blank, as they are automatically populated by the code 
            	//that performs the database work (TemplatePropertyDataAccess.insert())
            	property.setTemplateId(templateData.getTemplateId());
            	property.setAddBy(templateData.getAddBy());
            	property.setModBy(templateData.getModBy());
            	property = TemplatePropertyDataAccess.insert(conn, property);
            	updatedProperties.add(property);
            }
            templateData.setProperties(updatedProperties);
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    	return templateData;
    }
    
    /**
     * Method to modify a template.
     *
     * @param templateData a <code>TemplateDataExtended</code> value
     * @return a <code>TemplateDataExtended</code> value
     * @exception RemoteException if an error occurs
     */
    public TemplateDataExtended modifyTemplate(TemplateDataExtended templateData) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            //update the template
            if (TemplateDataAccess.update(conn, templateData) > 0) {
            	//if the update succeeded, update the properties.  To do this, we need to get the
            	//existing properties and determine which properties need to be added, modified, and
            	//deleted.  NOTE - this code assumes each single valued properties, and will not handle 
            	//multi-valued properties (i.e. two properties with the same code but different values).
                EntitySearchCriteria searchCriteria = new EntitySearchCriteria();
                searchCriteria.setSearchId(templateData.getTemplateId());
                TemplateDataExtended existingTemplate = (TemplateDataExtended)getTemplateByCriteria(searchCriteria).get(0);
            	Map<String, TemplatePropertyData> propertiesToDelete = new HashMap();
            	Map<String, TemplatePropertyData> propertiesToModify = new HashMap();
            	Map<String, TemplatePropertyData> propertiesToAdd = new HashMap();
                TemplatePropertyDataVector propertyVector = templateData.getProperties();
                Iterator<TemplatePropertyData> propertyIterator = propertyVector.iterator();
                while (propertyIterator.hasNext()) {
                	TemplatePropertyData property = propertyIterator.next();
                	TemplatePropertyData existingProperty = existingTemplate.getProperty(property.getTemplatePropertyCd());
                	if (existingProperty.getTemplatePropertyId() > 0) {
                		//update the existing property with the new value and modified by value and put 
                		//it into the map, so we can just reuse all the values for the fields that will
                		//not be changing (id, add by, add date, etc).
                		existingProperty.setValue(property.getValue());
                		existingProperty.setModBy(templateData.getModBy());
                		propertiesToModify.put(existingProperty.getTemplatePropertyCd(), existingProperty);
                	}
                	else {
                		propertiesToAdd.put(property.getTemplatePropertyCd(), property);
                	}
                }
                //any existing properties that are not in the modify map are to be deleted.  Note that
                //we need to check that the id is greater than 0, due to the calls above to getProperty
                //(which will add a property if it doesn't already exist)
                propertyIterator = existingTemplate.getProperties().iterator();
                while (propertyIterator.hasNext()) {
                	TemplatePropertyData property = propertyIterator.next();
                	String propertyCode = property.getTemplatePropertyCd();
                	if (property.getTemplatePropertyId() > 0 && 
                			!propertiesToModify.containsKey(propertyCode)) {
                		propertiesToDelete.put(property.getTemplatePropertyCd(), property);
                	}
                }
                //add any new properties
                Iterator<String> addPropertyIterator = propertiesToAdd.keySet().iterator();
                while (addPropertyIterator.hasNext()) {
                	String propertyCode = addPropertyIterator.next();
                	TemplatePropertyData property = propertiesToAdd.get(propertyCode);
                	//update the property with the id of the template as well as the create by and modified
                	//by values.  Note that the created by date, modified by date, and template property id 
                	//values are all intentionally left blank, as they are automatically populated by the code 
                	//that performs the database work (TemplatePropertyDataAccess.insert())
                	property.setTemplateId(templateData.getTemplateId());
                	property.setAddBy(templateData.getAddBy());
                	property.setModBy(templateData.getModBy());
                	property = TemplatePropertyDataAccess.insert(conn, property);
                }
                //update any modified properties
                Iterator<String> modifyPropertyIterator = propertiesToModify.keySet().iterator();
                while (modifyPropertyIterator.hasNext()) {
                	String propertyCode = modifyPropertyIterator.next();
                	TemplatePropertyData property = propertiesToModify.get(propertyCode);
                	//because we reused the existing properties above there is nothing that needs to be
                	//set here, and we can just call update
                	if (TemplatePropertyDataAccess.update(conn, property) <= 0) {
                    	throw new RemoteException("Modification of property with id (" + property.getTemplatePropertyId() + 
                    			") for template with id (" + templateData.getTemplateId() + ") failed.");
                	}
                }
                //delete any removed properties
                Iterator<String> deletePropertyIterator = propertiesToDelete.keySet().iterator();
                while (deletePropertyIterator.hasNext()) {
                	String propertyCode = deletePropertyIterator.next();
                	TemplatePropertyData property = propertiesToDelete.get(propertyCode);
                	//because we reused the existing properties above we have its id so use it
                	if (TemplatePropertyDataAccess.remove(conn, property.getTemplatePropertyId()) <= 0) {
                    	throw new RemoteException("Deletion of property with id (" + property.getTemplatePropertyId() + 
                    			") for template with id (" + templateData.getTemplateId() + ") failed.");
                	}
                }
                //retrieve the template, so that all changes are reflected
                templateData = (TemplateDataExtended)getTemplateByCriteria(searchCriteria).get(0);
            }
            else {
            	throw new RemoteException("Modification of template with id (" + templateData.getTemplateId() + ") failed.");
            }
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    	return templateData;
    }

    /**
     * Get all templates that match the given criteria.
     * @param TemplateSearchCriteria the criteria to use in selecting the templates
     * @return a <code>TemplateDataVector</code> of matching templates
     * @exception RemoteException if an error occurs
     */
    public TemplateDataVector getTemplateByCriteria(EntitySearchCriteria entitySearchCriteria) throws RemoteException {
    	TemplateDataVector templateVector = null;
    	Connection conn = null;
    	try {
    		conn = getConnection();
    	    DBCriteria dbc = new DBCriteria();
    	    
    	    //handle implicit search criteria (store id)
    	    IdVector storeIds = entitySearchCriteria.getStoreBusEntityIds();
    	    if (storeIds != null && storeIds.size() > 0) {
                dbc.addOneOf(TemplateDataAccess.BUS_ENTITY_ID, storeIds);
    	    }

    	    //handle explicit (user-specified) search criteria, if any 
    	    if (Utility.isSet(entitySearchCriteria.getSearchName())) {
    	      switch (entitySearchCriteria.getSearchNameType()) {
    	      case EntitySearchCriteria.NAME_CONTAINS:
    	        dbc.addLikeIgnoreCase(TemplateDataAccess.NAME,
    	                              "%" + entitySearchCriteria.getSearchName() +
    	                              "%");
    	        break;
    	      case EntitySearchCriteria.NAME_STARTS_WITH:
    	        dbc.addLikeIgnoreCase(TemplateDataAccess.NAME,
    	                              entitySearchCriteria.getSearchName() + "%");
    	        break;
    	      default:
    	        throw new RemoteException(
    	          "Error. TemplateBean.getTemplateByCriteria(). Unknown match type: " +
    	          entitySearchCriteria.getSearchNameType());
    	      }
    	    }
    	    if (0 != entitySearchCriteria.getSearchIdAsInt()) {
    	      dbc.addEqualTo(TemplateDataAccess.TEMPLATE_ID,
    	                     entitySearchCriteria.getSearchIdAsInt());
    	    }
    	    
    	    //specify the result order
    	    if (EntitySearchCriteria.ORDER_BY_ID == entitySearchCriteria.getOrder()) {
    	    	dbc.addOrderBy(TemplateDataAccess.TEMPLATE_ID);
    	    } else {
    	    	dbc.addOrderBy(TemplateDataAccess.NAME);
    	    }
    	    
    	    //find the templates matching the criteria
    		templateVector = TemplateDataAccess.select(conn, dbc);
    		
    		//retrieve the properties belonging to the retrieved templates and populate each template 
    		//with its properties 
    		if (templateVector.size() > 0) {
    			List templateIds = new ArrayList();
    			Map<String,TemplateDataExtended> templateMap = new HashMap();
    			Iterator<TemplateData> templateIterator = templateVector.iterator();
    			while (templateIterator.hasNext()) {
    				TemplateData template = templateIterator.next();
    				String templateId = template.getTemplateId() + "";
    				templateIds.add(templateId);
    				templateMap.put(templateId, new TemplateDataExtended(template));
    			}
        	    dbc = new DBCriteria();
        	    dbc.addOneOf(TemplatePropertyDataAccess.TEMPLATE_ID, templateIds);
        	    TemplatePropertyDataVector propertyVector = TemplatePropertyDataAccess.select(conn, dbc);
        	    Iterator<TemplatePropertyData> propertyIterator = propertyVector.iterator();
    			while (propertyIterator.hasNext()) {
    				TemplatePropertyData property = propertyIterator.next();
    				TemplateDataExtended template = templateMap.get(property.getTemplateId() + "");
    				template.getProperties().add(property);
    			}
    			//clear the template data vector (which holds the templates without their properties), and
    			//repopulate it with the templates in the map (which have their properties).  Maintain the
    			//the original order of the templates.
    			templateVector.clear();
    			Iterator idIterator = templateIds.iterator();
    			while (idIterator.hasNext()) {
    				String templateId = (String)idIterator.next();
    				templateVector.add(templateMap.get(templateId));
    			}
    		}
		} catch (Exception e) {
		    throw processException(e);
		} finally {
		    closeConnection(conn);
		}
	return templateVector;
    }

    /**
     * Get all system default templates.
     * @return a <code>TemplateDataVector</code> of system default templates
     * @exception RemoteException if an error occurs
     */
    public TemplateDataVector getSystemDefaultTemplates() throws RemoteException {
    	TemplateDataVector templateVector = null;
    	Connection conn = null;
    	try {
    		conn = getConnection();
    	    DBCriteria dbc = new DBCriteria();
    	    dbc.addIsNull(TemplateDataAccess.BUS_ENTITY_ID);
    	    
    	    //find the templates matching the criteria
    		templateVector = TemplateDataAccess.select(conn, dbc);
    		
    		//retrieve the properties belonging to the retrieved templates and populate each template 
    		//with its properties 
    		if (templateVector.size() > 0) {
    			List templateIds = new ArrayList();
    			Map<String,TemplateDataExtended> templateMap = new HashMap();
    			Iterator<TemplateData> templateIterator = templateVector.iterator();
    			while (templateIterator.hasNext()) {
    				TemplateData template = templateIterator.next();
    				String templateId = template.getTemplateId() + "";
    				templateIds.add(templateId);
    				templateMap.put(templateId, new TemplateDataExtended(template));
    			}
        	    dbc = new DBCriteria();
        	    dbc.addOneOf(TemplatePropertyDataAccess.TEMPLATE_ID, templateIds);
        	    TemplatePropertyDataVector propertyVector = TemplatePropertyDataAccess.select(conn, dbc);
        	    Iterator<TemplatePropertyData> propertyIterator = propertyVector.iterator();
    			while (propertyIterator.hasNext()) {
    				TemplatePropertyData property = propertyIterator.next();
    				TemplateDataExtended template = templateMap.get(property.getTemplateId() + "");
    				template.getProperties().add(property);
    			}
    			//clear the template data vector (which holds the templates without their properties), and
    			//repopulate it with the templates in the map (which have their properties).  Maintain the
    			//the original order of the templates.
    			templateVector.clear();
    			Iterator idIterator = templateIds.iterator();
    			while (idIterator.hasNext()) {
    				String templateId = (String)idIterator.next();
    				templateVector.add(templateMap.get(templateId));
    			}
    		}
		} catch (Exception e) {
		    throw processException(e);
		} finally {
		    closeConnection(conn);
		}
	return templateVector;
    }
    
    /**
     * Method to delete a template.
     *
     * @param templateData a <code>TemplateDataExtended</code> value
     * @exception RemoteException if an error occurs
     */
    public void deleteTemplate(TemplateDataExtended templateData) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            //retrieve the template properties
            EntitySearchCriteria searchCriteria = new EntitySearchCriteria();
            searchCriteria.setSearchId(templateData.getTemplateId());
            TemplateDataExtended existingTemplate = (TemplateDataExtended)getTemplateByCriteria(searchCriteria).get(0);
            TemplatePropertyDataVector propertyVector = existingTemplate.getProperties();
            //delete the template properties (this must be done first to prevent referential integrity 
            //constraint violations)
            Iterator<TemplatePropertyData> propertyIterator = propertyVector.iterator();
            while (propertyIterator.hasNext()) {
            	TemplatePropertyData property = propertyIterator.next();
            	if (TemplatePropertyDataAccess.remove(conn, property.getTemplatePropertyId()) <= 0) {
                	throw new RemoteException("Deletion of property with id (" + property.getTemplatePropertyId() + 
                			") for template with id (" + templateData.getTemplateId() + ") failed.");
            	}
            }
            //delete the template
            if (TemplateDataAccess.remove(conn, templateData.getTemplateId()) <= 0) {
            	throw new RemoteException("Deletion of template with id (" + templateData.getTemplateId() + ") failed.");
            }
        }
        catch (Exception e) {
            throw new RemoteException(e.getMessage(), e);
        }
        finally {
            closeConnection(conn);
        }
    }
    
    /**
     * Method to retrieve the default object map for a template.
     * @param Map a <code>Map</code> value
     * @return none
     * @exception RemoteException if an error occurs
     */
    public Map getDefaultObjectMap(String templateType, Map parameters) throws RemoteException {
    	Map returnValue = new HashMap();
    	if (Constants.TEMPLATE_TYPE_EMAIL.equalsIgnoreCase(templateType)) {
    		int orderId = ((BigDecimal)parameters.get(Constants.ORDER_ID)).intValue();
            Connection conn = null;
            try {
                conn = getConnection();
	        	//create an order dto to be placed into the map
	            OrderData orderData = OrderDataAccess.select(conn, orderId);
	            OrderDto orderDto = new OrderDto(orderData);
	        	//populate the order with shipping information
	            OrderAddressData shippingAddress = OrderDAO.getShippingAddress(conn, orderId);
	            orderDto.populateShippingAddress(shippingAddress);
	            //populate the order total cost
	            orderDto.setTotalCost(calculateOrderTotal(conn,orderData));
	            //populate the order with account information
	        	BusEntityData accountData = BusEntityDataAccess.select(conn,orderData.getAccountId());
	            orderDto.populateAccount(accountData);
	            //get some additional information
	            OrderInfoDataView orderInfoData = APIAccess.getAPIAccess().getOrderAPI().getOrderInfoData(orderData.getOrderId());
	            if (orderInfoData != null) {
	            	//set the placed by value
	            	orderDto.setPlacedBy(orderInfoData.getOrderInfo().getPlacedBy());
	            	//populate the order item information
	                ItemInfoViewVector items = orderInfoData.getItems();
	                if (items != null) {
	                	Iterator itemIterator = items.iterator();
	                	while (itemIterator.hasNext()) {
	                		ItemInfoView item = (ItemInfoView) itemIterator.next();
	                    	OrderItemDto orderItemDto = new OrderItemDto();
	                    	orderItemDto.setSku(item.getSkuNum());
	                    	orderItemDto.setName(item.getItemName());
	                    	orderItemDto.setPack(item.getPack());
	                    	orderItemDto.setUnitOfMeasure(item.getUom());
	                    	orderItemDto.setPrice(item.getCustCost());
	                    	orderItemDto.setQuantity(item.getQty());
	                    	orderItemDto.setOrderLocale(orderDto.getOrderLocale());
	                    	try {
	                    		orderItemDto.setTotal(item.getQty().multiply(item.getCustCost()));
	                    	}
	                    	catch (Exception e) {
	                    		orderItemDto.setTotal(null);
	                    	}
	                    	orderDto.getItems().add(orderItemDto);
	                	}
	                }
	            }
	        	//put the order information into the map
	        	returnValue.put(Constants.TEMPLATE_EMAIL_MAP_KEY_ORDER, orderDto);
            }
            catch (Exception e) {
                throw new RemoteException(e.getMessage(), e);
            }
            finally {
                closeConnection(conn);
            }
	    		
    	}
    	return returnValue;
    }
    
    /*
     * Method to determine the total value of an order
     * @param conn - a <code>Connection</code> object.
     * @param orderData - a <code>OrderData</code> object containing information about an order
     * @return - a <code>BigDecimal</code> object containing the total value of the order.
     */
    private BigDecimal calculateOrderTotal(Connection conn, OrderData orderData){
    	OrderMetaDataVector orderMeta = OrderDAO.getOrderMetaDV(conn, orderData.getOrderId());
    	BigDecimal orderDiscount = null;
    	BigDecimal orderSmallOrderFee = null;
    	BigDecimal orderFuelsurcharge = null;
    	BigDecimal freightAmt = orderData.getTotalFreightCost();
    	BigDecimal handlingAmt = orderData.getTotalMiscCost();
    	BigDecimal rushOrderCharge = orderData.getTotalRushCharge();
    	BigDecimal salesTax = orderData.getTotalTaxCost();
    	BigDecimal subTotal = orderData.getTotalPrice();

    	OrderMetaData mDiscount = getMetaObject(orderData, orderMeta, RefCodeNames.CHARGE_CD.DISCOUNT);
    	if (mDiscount != null && Utility.isSet(mDiscount.getValue())) {
    		orderDiscount = new BigDecimal(mDiscount.getValue());
    	}
    	OrderMetaData mSmallOrderFee = getMetaObject(orderData, orderMeta, RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
    	if (mSmallOrderFee != null && Utility.isSet(mSmallOrderFee.getValue())) {
    		orderSmallOrderFee = new BigDecimal(mSmallOrderFee.getValue());
    	}
    	OrderMetaData mFuelsurcharge = getMetaObject(orderData, orderMeta, RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
    	if (mFuelsurcharge != null && Utility.isSet(mFuelsurcharge.getValue())) {
    		orderFuelsurcharge = new BigDecimal(mFuelsurcharge.getValue());
    	}

    	BigDecimal orderTotal = (subTotal != null ) ? subTotal : new BigDecimal(0);
    	orderTotal = orderTotal.add(orderDiscount!= null ? orderDiscount : new BigDecimal(0));
    	orderTotal = orderTotal.add(orderFuelsurcharge!=null ? orderFuelsurcharge : new BigDecimal(0));
    	orderTotal = orderTotal.add(orderSmallOrderFee!=null ? orderSmallOrderFee : new BigDecimal(0));
    	orderTotal = orderTotal.add(freightAmt != null ? freightAmt : new BigDecimal(0));
    	orderTotal = orderTotal.add(handlingAmt!= null ? handlingAmt : new BigDecimal(0));
    	orderTotal = orderTotal.add(rushOrderCharge!= null ? rushOrderCharge : new BigDecimal(0));
    	orderTotal = orderTotal.add(salesTax!= null ? salesTax : new BigDecimal(0));
    	return orderTotal;
    }
    
    /*
     * Method to retrieve meta data about an order
     * @param orderData - a <code>OrderData</code> object containing information about an order
     * @param orderMeta - a <code>OrderMetaDataVector</code> object containing meta data about an
     * 		order.
     * @param pName - a <code>String</code> containing the name of the meta data attribute to be
     * 		returned.
     * @return
     */
    private OrderMetaData getMetaObject(OrderData orderData, OrderMetaDataVector orderMeta, String pName) {
        if (orderMeta == null || pName == null) {
        	return null;
        }
        Date modDate = null;
        OrderMetaData retObject = null;
        for (int ii = 0; ii < orderMeta.size(); ii++) {
            OrderMetaData omD = (OrderMetaData) orderMeta.get(ii);
            String name = omD.getName();
            if (pName.equals(name)) {
                if (modDate == null) {
                    modDate = orderData.getModDate();
                    retObject = omD;
                } else {
                    Date mD = orderData.getModDate();
                    if (mD == null) continue;
                    if (modDate.before(mD)) {
                        modDate = mD;
                        retObject = omD;
                    }
                }
            }
        }
        return retObject;
    }
}

