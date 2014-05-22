package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.WorkOrderContentViewVector;
import com.cleanwise.service.api.value.WorkOrderData;

import java.util.Date;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         16.10.2007
 * Time:         5:54:46
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWorkOrderContentMgrForm extends ActionForm {

    private WorkOrderContentViewVector workOrderContentAllTypes;
    private WorkOrderData workOrder;

    //detail field
    private int workOrderContentId;
    private int workOrderItemId;
    private int workOrderId;
    private String url;
    private int busEntityId;
    private int contentId;
    private int itemId;
    private String contentStatusCd;
    private String contentTypeCd;
    private String contentUsageCd;
    private String effDate;
    private String expDate;
    private String languageCd;
    private String localeCd;
    private String longDesc;
    private String path;
    private String shortDesc;
    private String sourceCd;
    private String version;
    private byte[] data;
    private String contentSize;
    FormFile uploadNewFile;
    private String workOrderAddBy;
    private Date workOrderAddDate;
    private String workOrderModBy;
    private Date workOrderModDate;
    private String contentAddBy;
    private Date contentAddDate;
    private String contentModBy;
    private Date contentModDate;

    public void setWorkOrderContentAllTypes(WorkOrderContentViewVector workOrderContentAllTypes) {
        this.workOrderContentAllTypes = workOrderContentAllTypes;
    }

    public void setWorkOrder(WorkOrderData workOrder) {
        this.workOrder = workOrder;
    }

    public WorkOrderContentViewVector getWorkOrderContentAllTypes() {
        return workOrderContentAllTypes;
    }

    public WorkOrderData getWorkOrder() {
        return workOrder;
    }

    public int getWorkOrderContentId() {
        return workOrderContentId;
    }

    public void setWorkOrderContentId(int workOrderContentId) {
        this.workOrderContentId = workOrderContentId;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBusEntityId() {
        return busEntityId;
    }

    public void setBusEntityId(int busEntityId) {
        this.busEntityId = busEntityId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getContentStatusCd() {
        return contentStatusCd;
    }

    public void setContentStatusCd(String contentStatusCd) {
        this.contentStatusCd = contentStatusCd;
    }

    public String getContentTypeCd() {
        return contentTypeCd;
    }

    public void setContentTypeCd(String contentTypeCd) {
        this.contentTypeCd = contentTypeCd;
    }

    public String getContentUsageCd() {
        return contentUsageCd;
    }

    public void setContentUsageCd(String contentUsageCd) {
        this.contentUsageCd = contentUsageCd;
    }

    public String getEffDate() {
        return effDate;
    }

    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getLanguageCd() {
        return languageCd;
    }

    public void setLanguageCd(String languageCd) {
        this.languageCd = languageCd;
    }

    public String getLocaleCd() {
        return localeCd;
    }

    public void setLocaleCd(String localeCd) {
        this.localeCd = localeCd;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentSize() {
        return contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }

    public FormFile getUploadNewFile() {
        return uploadNewFile;
    }

    public void setUploadNewFile(FormFile uploadNewFile) {
        this.uploadNewFile = uploadNewFile;
    }

    public String getWorkOrderAddBy() {
        return workOrderAddBy;
    }

    public void setWorkOrderAddBy(String workOrderAddBy) {
        this.workOrderAddBy = workOrderAddBy;
    }

    public Date getWorkOrderAddDate() {
        return workOrderAddDate;
    }

    public void setWorkOrderAddDate(Date workOrderAddDate) {
        this.workOrderAddDate = workOrderAddDate;
    }

    public String getWorkOrderModBy() {
        return workOrderModBy;
    }

    public void setWorkOrderModBy(String workOrderModBy) {
        this.workOrderModBy = workOrderModBy;
    }

    public Date getWorkOrderModDate() {
        return workOrderModDate;
    }

    public void setWorkOrderModDate(Date workOrderModDate) {
        this.workOrderModDate = workOrderModDate;
    }

    public String getContentAddBy() {
        return contentAddBy;
    }

    public void setContentAddBy(String contentAddBy) {
        this.contentAddBy = contentAddBy;
    }

    public Date getContentAddDate() {
        return contentAddDate;
    }

    public void setContentAddDate(Date contentAddDate) {
        this.contentAddDate = contentAddDate;
    }

    public String getContentModBy() {
        return contentModBy;
    }

    public void setContentModBy(String contentModBy) {
        this.contentModBy = contentModBy;
    }

    public Date getContentModDate() {
        return contentModDate;
    }

    public void setContentModDate(Date contentModDate) {
        this.contentModDate = contentModDate;
    }


    public int getWorkOrderItemId() {
        return workOrderItemId;
    }

    public void setWorkOrderItemId(int workOrderItemId) {
        this.workOrderItemId = workOrderItemId;
    }
}
