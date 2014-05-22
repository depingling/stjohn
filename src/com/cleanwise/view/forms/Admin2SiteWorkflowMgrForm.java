package com.cleanwise.view.forms;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import com.cleanwise.service.api.value.*;
import java.util.Hashtable;

public class Admin2SiteWorkflowMgrForm extends StorePortalBaseForm {

    public static final String CLONE_WITHOUT_RELATIONSHIPS = "CLONE_WITHOUT_RELATIONSHIPS";
    public static final String CLONE_WITH_RELATIONSHIPS = "CLONE_WITH_RELATIONSHIPS";

  private SiteData siteData;
  private String accountId;
  private String accountName;
  private BusEntityFieldsData siteFields;
  private int storeId;
  private String storeName;
  private AccountDataVector mAccountFilter;
  private String workflowTypeCd;
  private String selectedWorkflowId="";
  private String assignedWorkflowId="";
  private RefCdDataVector workflowStatusVector;
  private WorkflowDataVector workflowFound;
  private Hashtable workflowRulesTab;
  private WorkflowData siteWorkflow;
  private WorkflowRuleDataVector workflowRules;
  private boolean init;
  public void setSiteData(SiteData siteData) {
        this.siteData = siteData;
  }

  public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setSiteFields(BusEntityFieldsData siteFields) {
        this.siteFields = siteFields;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

  public SiteData getSiteData() {
        return siteData;
    }


  public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public BusEntityFieldsData getSiteFields() {
        return siteFields;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

  public AccountDataVector getAccountFilter() {
        return mAccountFilter;
    }

    public void setAccountFilter(AccountDataVector mAccountFilter) {
        this.mAccountFilter = mAccountFilter;
    }

    /**
     *  <code>reset</code> method, set the search fiels to null.
     *
     *@param  mapping  an <code>ActionMapping</code> value
     *@param  request  a <code>HttpServletRequest</code> value
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
      selectedWorkflowId="";
    }

  public void setWorkflowTypeCd(String workflowTypeCd) {
    this.workflowTypeCd = workflowTypeCd;
  }

  public void setSelectedWorkflowId(String selectedWorkflowId) {
    this.selectedWorkflowId = selectedWorkflowId;
  }

  public void setAssignedWorkflowId(String assignedWorkflowId) {
    this.assignedWorkflowId = assignedWorkflowId;
  }

  public void setWorkflowStatusVector(RefCdDataVector workflowStatusVector) {
    this.workflowStatusVector = workflowStatusVector;
  }

  public void setWorkflowFound(WorkflowDataVector workflowFound) {
    this.workflowFound = workflowFound;
  }

  public void setWorkflowRulesTab(Hashtable workflowRulesTab) {
    this.workflowRulesTab = workflowRulesTab;
  }

  public void setSiteWorkflow(WorkflowData siteWorkflow) {
    this.siteWorkflow = siteWorkflow;
  }

  public void setWorkflowRules(WorkflowRuleDataVector workflowRules) {
    this.workflowRules = workflowRules;
  }

  public String getWorkflowTypeCd() {
    return workflowTypeCd;
  }

  public String getSelectedWorkflowId() {
    return selectedWorkflowId;
  }

  public String getAssignedWorkflowId() {
    return assignedWorkflowId;
  }

  public RefCdDataVector getWorkflowStatusVector() {
    return workflowStatusVector;
  }

  public WorkflowDataVector getWorkflowFound() {
    return workflowFound;
  }

  public Hashtable getWorkflowRulesTab() {
    return workflowRulesTab;
  }

  public WorkflowData getSiteWorkflow() {
    return siteWorkflow;
  }

  public WorkflowRuleDataVector getWorkflowRules() {
    return workflowRules;
  }
  public void init() {
      this.init= true;
  }
  public boolean isInit() {
      return init;
  }

}
