package com.cleanwise.service.api.value;

import java.util.Iterator;

import com.cleanwise.service.api.util.Utility;

public class TemplateDataExtended extends TemplateData {

	private static final long serialVersionUID = 2699539385758897895L;

    private TemplatePropertyDataVector _properties;
    private String _busEntityName;
    
    public String getBusEntityName() {
		return _busEntityName;
	}

	public void setBusEntityName(String busEntityName) {
		_busEntityName = busEntityName;
	}

	public TemplateDataExtended() {
    	super();
    }
    
    public TemplateDataExtended(TemplateData templateData) {
    	this.setAddBy(templateData.getAddBy());
    	this.setAddDate(templateData.getAddDate());
    	this.setBusEntityId(templateData.getBusEntityId());
    	this.setContent(templateData.getContent());
    	this.setModBy(templateData.getModBy());
    	this.setModDate(templateData.getModDate());
    	this.setName(templateData.getName());
    	this.setTemplateId(templateData.getTemplateId());
    	this.setType(templateData.getType());
    }

	public TemplatePropertyDataVector getProperties() {
		if (_properties == null) {
			_properties = new TemplatePropertyDataVector();
		}
		return _properties;
	}

	public void setProperties(TemplatePropertyDataVector properties) {
		_properties = properties;
	}
    
	//TODO - implement some type of check to make sure the specified propertyCode is
	//applicable to this template data.
	public TemplatePropertyData getProperty(String propertyCode) {
		TemplatePropertyData returnValue = null;
		//if no property code was specified, return null
		if (!Utility.isSet(propertyCode)) {
			return returnValue;
		}
		//otherwise iterate over the properties, stopping once we find the property
		//with the specified code
		Iterator iterator = getProperties().iterator();
		while (iterator.hasNext() && returnValue == null) {
			TemplatePropertyData property = (TemplatePropertyData)iterator.next();
			if (property.getTemplatePropertyCd().equalsIgnoreCase(propertyCode)) {
				returnValue = property;
			}
		}
		//if we didn't find a property with the specified code, create a new property
		//and add it to the list of properties.
		if (returnValue == null) {
			returnValue = new TemplatePropertyData();
			returnValue.setTemplateId(this.getTemplateId());
			returnValue.setTemplatePropertyCd(propertyCode);
			getProperties().add(returnValue);
		}
		return returnValue;
	}
    
}
