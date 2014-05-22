package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.view.utils.SelectableObjects;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;


public class Admin2UserDetailMgrForm extends Admin2PageForm {

    private UserInfoData mDetail;
    private boolean init;

    private Admin2UserRightsForm mBaseUserForm = new Admin2UserRightsForm();

    private String password;
    private String confirmPassword;
    private String effDate;
    private String expDate;
    private String userIDCode;
    private String isCorporateUser;
    private String receiveInvMissingEmail;
    private String totalReadOnly;
    private String manifestLabelHeight;
    private String manifestLabelWidth;
    private String manifestLabelType;
    private String manifestLabelPrintMode;
    private String customerServiceRoleCd;
    private String distributionCenterId;

    private SelectableObjects entities;
    private int id;
  private boolean isEditableForUserFl;

  public UserInfoData getDetail() {
        return mDetail;
    }

    public void setDetail(UserInfoData detail) {
        this.mDetail = detail;
    }

    public boolean isInit() {
        return init;
    }

    public void init() {
        this.init = true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserIDCode() {
        return userIDCode;
    }

    public void setUserIDCode(String userIDCode) {
        this.userIDCode = userIDCode;
    }

    public String getIsCorporateUser() {
        return isCorporateUser;
    }

    public void setIsCorporateUser(String corporateUser) {
        isCorporateUser = corporateUser;
    }

    public String getReceiveInvMissingEmail() {
        return receiveInvMissingEmail;
    }

    public void setReceiveInvMissingEmail(String receiveInvMissingEmail) {
        this.receiveInvMissingEmail = receiveInvMissingEmail;
    }


    public String getManifestLabelHeight() {
        return manifestLabelHeight;
    }

    public void setManifestLabelHeight(String manifestLabelHeight) {
        this.manifestLabelHeight = manifestLabelHeight;
    }

    public String getManifestLabelWidth() {
        return manifestLabelWidth;
    }

    public void setManifestLabelWidth(String manifestLabelWidth) {
        this.manifestLabelWidth = manifestLabelWidth;
    }

    public String getManifestLabelPrintMode() {
        return manifestLabelPrintMode;
    }

    public void setManifestLabelPrintMode(String manifestLabelPrintMode) {
        this.manifestLabelPrintMode = manifestLabelPrintMode;
    }

    public String getManifestLabelType() {
        return manifestLabelType;
    }

    public void setManifestLabelType(String manifestLabelType) {
        this.manifestLabelType = manifestLabelType;
    }


    public void setEntities(SelectableObjects entities) {
        this.entities = entities;
    }

    public SelectableObjects getEntities() {
        return entities;
    }

    public String getCustomerServiceRoleCd() {
        return customerServiceRoleCd;
    }

    public void setCustomerServiceRoleCd(String customerServiceRoleCd) {
        this.customerServiceRoleCd = customerServiceRoleCd;
    }

    public String getTotalReadOnly() {
        return totalReadOnly;
    }

    public void setTotalReadOnly(String totalReadOnly) {
        this.totalReadOnly = totalReadOnly;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        isCorporateUser = String.valueOf(false);
        receiveInvMissingEmail = String.valueOf(false);
        totalReadOnly = String.valueOf(false);
        mBaseUserForm.resetPermissions();
        if (entities != null) {
            entities.handleStutsFormResetRequest();
        }
        super.reset(mapping, request);
    }

    public Admin2UserRightsForm getBaseUserForm() {
        return mBaseUserForm;
    }

    public void setBaseUserForm(Admin2UserRightsForm pBaseUserForm) {
        this.mBaseUserForm = pBaseUserForm;
    }

    public void setDistributionCenterId(String distributionCenterId) {
        this.distributionCenterId = distributionCenterId;
    }


    public String getDistributionCenterId() {
        return distributionCenterId;
    }

    /**
     * Get the effective date field value.
     *
     * @return String representing the date value.
     */
    public String getEffDate() {
        return this.effDate;
    }

    public void setEffDate(String dateString) {
        this.effDate = dateString;
    }


    public String getExpDate() {
        return this.expDate;
    }


    public void setExpDate(String dateString) {
        this.expDate = dateString;
    }

    public int getId() {
        return id;
    }

    public boolean getIsEditableForUserFl() {
      return isEditableForUserFl;
    }

  public boolean isIsEditableForUserFl() {
    return isEditableForUserFl;
  }

  public void setId(int id) {
        this.id = id;
    }

  public void setIsEditableForUserFl(boolean isEditableForUserFl) {
    this.isEditableForUserFl = isEditableForUserFl;
  }
}
