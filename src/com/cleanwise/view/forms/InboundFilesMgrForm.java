package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.InboundDataVector;

public class InboundFilesMgrForm extends ActionForm {

    private int _inboundFilesCount;
    private int _searchInboundFilesCount;
    private String _searchInboundId;
    private String _searchDateFrom;
    private String _searchDateTo;
    private String _searchFileName;
    private String _searchPartnerKey;
    private String _searchUrl;
    private String _searchTextInFile;
    private InboundDataVector _inboundFiles;

    public InboundFilesMgrForm() {
        _inboundFilesCount = 0;
        _searchInboundFilesCount = 0;
        _searchInboundId = "";
        _searchDateFrom = "";
        _searchDateTo = "";
        _searchFileName = "";
        _searchPartnerKey = "";
        _searchUrl = "";
        _searchTextInFile = "";
        _inboundFiles = null;
    }

    public InboundDataVector getInboundFiles() {
        return _inboundFiles;
    }

    public void setInboundFiles(InboundDataVector inboundFiles) {
        _inboundFiles = inboundFiles;
        if (_inboundFiles == null) {
            _searchInboundFilesCount = 0;
        } else {
            _searchInboundFilesCount = _inboundFiles.size();
        }
    }

    public int getInboundFilesCount() {
        return _inboundFilesCount;
    }

    public void setInboundFilesCount(int inboundFilesCount) {
        _inboundFilesCount = inboundFilesCount;
    }

    public int getSearchInboundFilesCount() {
        return _searchInboundFilesCount;
    }

    public String getSearchDateFrom() {
        return _searchDateFrom;
    }

    public void setSearchDateFrom(String searchDateFrom) {
        _searchDateFrom = searchDateFrom;
    }

    public String getSearchDateTo() {
        return _searchDateTo;
    }

    public void setSearchDateTo(String searchDateTo) {
        _searchDateTo = searchDateTo;
    }

    public String getSearchFileName() {
        return _searchFileName;
    }

    public void setSearchFileName(String searchFileName) {
        _searchFileName = searchFileName;
    }

    public String getSearchInboundId() {
        return _searchInboundId;
    }

    public void setSearchInboundId(String searchInboundId) {
        _searchInboundId = searchInboundId;
    }

    public String getSearchPartnerKey() {
        return _searchPartnerKey;
    }

    public void setSearchPartnerKey(String searchPartnerKey) {
        _searchPartnerKey = searchPartnerKey;
    }

    public String getSearchTextInFile() {
        return _searchTextInFile;
    }

    public void setSearchTextInFile(String searchTextInFile) {
        _searchTextInFile = searchTextInFile;
    }

    public String getSearchUrl() {
        return _searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        _searchUrl = searchUrl;
    }

}
