package com.cleanwise.useractivity;

import java.math.BigDecimal;
import java.util.Date;

public class LogItem {
    private String _requestId;
    private String _tokenType;
    private String _sessionId;
    private String _userName;
    private String _className;
    private String _endFile;
    private String _action;
    private String _httpStartedAtS;
    private Date   _httpStartedAt;
    private String _actionStartedAtS;
    private Date   _actionStartedAt;
    private String _actionEndedAtS;
    private Date   _actionEndedAt;
    private String _httpEndedAtS;
    private Date   _httpEndedAt;
    private String _actionResult;
    private String _httpResult;
    private BigDecimal _actionDuration;
    private BigDecimal _httpDuration;
    private String _referer;
    private String _params;

    public String getAction() {
        return _action;
    }

    public void setAction(String action) {
        this._action = action;
    }

    public BigDecimal getActionDuration() {
        return _actionDuration;
    }

    public void setActionDuration(BigDecimal actionDuration) {
        this._actionDuration = actionDuration;
    }

    public Date getActionEndedAt() {
        return _actionEndedAt;
    }

    public void setActionEndedAt(Date actionEndedAt) {
        this._actionEndedAt = actionEndedAt;
    }

    public String getActionEndedAtS() {
        return _actionEndedAtS;
    }

    public void setActionEndedAtS(String actionEndedAtS) {
        this._actionEndedAtS = actionEndedAtS;
    }

    public String getActionResult() {
        return _actionResult;
    }

    public void setActionResult(String actionResult) {
        this._actionResult = actionResult;
    }

    public Date getActionStartedAt() {
        return _actionStartedAt;
    }

    public void setActionStartedAt(Date actionStartedAt) {
        this._actionStartedAt = actionStartedAt;
    }

    public String getActionStartedAtS() {
        return _actionStartedAtS;
    }

    public void setActionStartedAtS(String actionStartedAtS) {
        this._actionStartedAtS = actionStartedAtS;
    }

    public String getClassName() {
        return _className;
    }

    public void setClassName(String className) {
        this._className = className;
    }

    public String getEndFile() {
        return _endFile;
    }

    public void setEndFile(String endFile) {
        this._endFile = endFile;
    }

    public BigDecimal getHttpDuration() {
        return _httpDuration;
    }

    public void setHttpDuration(BigDecimal httpDuration) {
        this._httpDuration = httpDuration;
    }

    public Date getHttpEndedAt() {
        return _httpEndedAt;
    }

    public void setHttpEndedAt(Date httpEndedAt) {
        this._httpEndedAt = httpEndedAt;
    }

    public String getHttpEndedAtS() {
        return _httpEndedAtS;
    }

    public void setHttpEndedAtS(String httpEndedAtS) {
        this._httpEndedAtS = httpEndedAtS;
    }

    public String getHttpResult() {
        return _httpResult;
    }

    public void setHttpResult(String httpResult) {
        this._httpResult = httpResult;
    }

    public Date getHttpStartedAt() {
        return _httpStartedAt;
    }

    public void setHttpStartedAt(Date httpStartedAt) {
        this._httpStartedAt = httpStartedAt;
    }

    public String getHttpStartedAtS() {
        return _httpStartedAtS;
    }

    public void setHttpStartedAtS(String httpStartedAtS) {
        this._httpStartedAtS = httpStartedAtS;
    }

    public String getParams() {
        return _params;
    }

    public void setParams(String params) {
        this._params = params;
    }

    public String getReferer() {
        return _referer;
    }

    public void setReferer(String referer) {
        this._referer = referer;
    }

    public String getRequestId() {
        return _requestId;
    }

    public void setRequestId(String requestId) {
        this._requestId = requestId;
    }

    public String getSessionId() {
        return _sessionId;
    }

    public void setSessionId(String sessionId) {
        this._sessionId = sessionId;
    }

    public String getUserName() {
        return _userName;
    }

    public void setUserName(String userName) {
        this._userName = userName;
    }

    public String getTokenType() {
        return _tokenType;
    }

    public void setTokenType(String tokenType) {
        this._tokenType = tokenType;
    }
}