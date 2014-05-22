package com.cleanwise.service.api.process.operations;

import org.dom4j.Element;

public abstract class OutboundOrderXML {

    private Element createElement(Element parent, String elementName) {
		return createElement(parent, elementName, null, null);
	}

	public Element createElement( Element parent,String elementName,  String elementTextValue) {
		return createElement(parent, elementName, elementTextValue, null);
	}

	private   Element createElement( Element parent,String elementName,  String elementTextValue, String[][] attributes) {
		Element element = parent.addElement(elementName);
        element.setText(elementTextValue != null ? elementTextValue : "");
		addAttributes(element, attributes);
		return element;
	}

	public void createElementIfTextNotEmpty( Element parent,String elementName,  String elementTextValue) {
		if (elementTextValue != null && elementTextValue.trim().length() > 0) {
			createElement(parent, elementName, elementTextValue);
		}
	}

	private   void addAttributes( Element element,String attributes[][]) {

        for (int i = 0; attributes != null && i < attributes.length; i++) {
			if (attributes[i] != null && attributes[i].length == 2
                                      && attributes[i][0] != null
                                      && attributes[i][1] != null)
            {
				element.addAttribute(attributes[i][0], attributes[i][1]);
			}
		}
	}


}
