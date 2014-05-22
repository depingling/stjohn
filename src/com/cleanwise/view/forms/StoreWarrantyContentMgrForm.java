package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;

import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         04.10.2007
 * Time:         12:17:36
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class StoreWarrantyContentMgrForm extends StorePortalBaseForm {

    private WarrantyData warrantyData;

    private WarrantyContentViewVector findResult;

    private int warrantyContentId;
    private int warrantyId;
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
    private String warrantyAddBy;
    private Date warrantyAddDate;
    private String warrantyModBy;
    private Date warrantyModDate;
    private String contentAddBy;
    private Date contentAddDate;
    private String contentModBy;
    private Date contentModDate;

    public int getWarrantyContentId() {
        return warrantyContentId;
    }

    public void setWarrantyContentId(int warrantyContentId) {
        this.warrantyContentId = warrantyContentId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getLocaleCd() {
        return localeCd;
    }

    public void setLocaleCd(String localeCd) {
        this.localeCd = localeCd;
    }

    public String getLanguageCd() {
        return languageCd;
    }

    public void setLanguageCd(String languageCd) {
        this.languageCd = languageCd;
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

    public String getContentStatusCd() {
        return contentStatusCd;
    }

    public void setContentStatusCd(String contentStatusCd) {
        this.contentStatusCd = contentStatusCd;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getBusEntityId() {
        return busEntityId;
    }

    public void setBusEntityId(int busEntityId) {
        this.busEntityId = busEntityId;
    }

    public int getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(int warrantyId) {
        this.warrantyId = warrantyId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWarrantyData(WarrantyData warrantyData) {
        this.warrantyData = warrantyData;
    }

    public WarrantyData getWarrantyData() {
        return warrantyData;
    }

    public void setFindResult(WarrantyContentViewVector findResult) {
        this.findResult = findResult;
    }

    public WarrantyContentViewVector getFindResult() {
        return findResult;
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


    public String getWarrantyAddBy() {
        return warrantyAddBy;
    }

    public void setWarrantyAddBy(String warrantyAddBy) {
        this.warrantyAddBy = warrantyAddBy;
    }

    public Date getWarrantyAddDate() {
        return warrantyAddDate;
    }

    public void setWarrantyAddDate(Date warrantyAddDate) {
        this.warrantyAddDate = warrantyAddDate;
    }

    public String getWarrantyModBy() {
        return warrantyModBy;
    }

    public void setWarrantyModBy(String warrantyModBy) {
        this.warrantyModBy = warrantyModBy;
    }

    public Date getWarrantyModDate() {
        return warrantyModDate;
    }

    public void setWarrantyModDate(Date warrantyModDate) {
        this.warrantyModDate = warrantyModDate;
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
}
