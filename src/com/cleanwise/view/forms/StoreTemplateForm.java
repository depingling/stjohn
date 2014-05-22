package com.cleanwise.view.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cleanwise.service.api.value.TemplateDataExtended;
import com.cleanwise.service.api.value.TemplateDataVector;

public class StoreTemplateForm extends StorePortalBaseForm {

	private static final long serialVersionUID = 6515960312075238428L;
	//data element to hold the information about a single template
	private TemplateDataExtended _templateData;
	//data element to hold the information about a number of templates
	private TemplateDataVector _templateDataVector;
	//data element to hold search criteria
	private String _searchField;
	//data element to hold search type
	private String _searchType;
	//data element to hold sample template content
	private String _sampleContent;
	//data element to hold output for a template.  This is a map because a template may
	//have more than one output (for example, email templates have both a subject and message)
	private Map _templateOutput;
	
	public StoreTemplateForm() {
		super();
	}
	
	public TemplateDataExtended getTemplateData() {
		return _templateData;
	}

	public void setTemplateData(TemplateDataExtended templateData) {
		_templateData = templateData;
	}

	public TemplateDataVector getTemplateDataVector() {
		return _templateDataVector;
	}

	public void setTemplateDataVector(TemplateDataVector templateDataVector) {
		_templateDataVector = templateDataVector;
	}

	public String getSearchField() {
		return _searchField;
	}

	public void setSearchField(String searchField) {
		_searchField = searchField;
	}

	public String getSearchType() {
		return _searchType;
	}

	public void setSearchType(String searchType) {
		_searchType = searchType;
	}

	public String getSampleContent() {
		return _sampleContent;
	}

	public void setSampleContent(String sampleContent) {
		_sampleContent = sampleContent;
	}

	public Map getTemplateOutput() {
		if (_templateOutput == null) {
			setTemplateOutput(new HashMap());
		}
		return _templateOutput;
	}
	
	public String getTemplateOutputStringValue(String key) {
		String returnValue = (String)getTemplateOutput().get(key);
		if (returnValue == null) {
			returnValue = "";
		}
		return returnValue;
	}

	public void setTemplateOutput(Map templateOutput) {
		_templateOutput = templateOutput;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		_templateData = new TemplateDataExtended();
		_templateDataVector = new TemplateDataVector();
		_searchField = "";
		_searchType = "nameBegins";
		_sampleContent = "";
		_templateOutput = null;
		setId(0);
	  }
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
	    return null;
	}

}
