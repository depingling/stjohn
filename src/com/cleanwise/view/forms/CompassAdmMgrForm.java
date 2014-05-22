package com.cleanwise.view.forms;

import javax.servlet.http.*;
import java.util.*;
import org.apache.struts.action.*;
import com.cleanwise.service.api.value.IdVector;

public class CompassAdmMgrForm extends ActionForm {

    private String status;
    private ArrayList threads  = new ArrayList();
    private boolean manualIndexing = false;
    private boolean mirroring = false;
    private boolean presentGps = false;
    private boolean runningGps = false;
    private String percentage = "";

    private String queryStr;
    private String queryAliase;
    private String queryId;
    private IdVector resultIds = new IdVector();

    //------------------------------------------------------------------------

    public IdVector getResultIds() {
        return resultIds;
    }

    public void setResultIds(IdVector resultIds) {
        this.resultIds = resultIds;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public void setQueryStr(String queryStr) {
        this.queryStr = queryStr;
    }
    public String getQueryAliase() {
        return queryAliase;
    }

    public void setQueryAliase(String queryAliase) {
        this.queryAliase = queryAliase;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList getThreads() {
        return threads;
    }

    public void setThreads(ArrayList threads) {
        this.threads = threads;
    }

    public boolean getManualIndexing() {
        return manualIndexing;
    }

    public void setManualIndexing(boolean manualIndexing) {
        this.manualIndexing = manualIndexing;
    }

    public boolean getRunningGps() {
        return runningGps;
    }

    public void setRunningGps(boolean runningGps) {
        this.runningGps = runningGps;
    }

    public boolean getPresentGps() {
        return presentGps;
    }

    public void setPresentGps(boolean presentGps) {
        this.presentGps = presentGps;
    }

    public boolean getMirroring() {
        return mirroring;
    }

    public void setMirroring(boolean mirroring) {
        this.mirroring = mirroring;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
    }
    public ActionErrors validate(ActionMapping mapping,
                                 HttpServletRequest request) {
        return null;
    }


}
