package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.AssetData;

import java.util.Date;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         29.11.2007
 * Time:         8:54:00
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserAssetContentMgrForm extends ActionForm {

    AssetData asset;

    private int assetContentId;
    private int assetId;
    private String url;
    private int busEntityId;
    private int contentId;
    private int itemId;
    private String contentStatusCd;
    private String contentTypeCd;
    private String typeCd;
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
    private String assetAddBy;
    private Date assetAddDate;
    private String assetModBy;
    private Date assetModDate;
    private String contentAddBy;
    private Date contentAddDate;
    private String contentModBy;
    private Date contentModDate;

    public AssetData getAsset() {
        return asset;
    }

    public void setAsset(AssetData asset) {
        this.asset = asset;
    }

    public int getAssetContentId() {
        return assetContentId;
    }

    public void setAssetContentId(int assetContentId) {
        this.assetContentId = assetContentId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSourceCd() {
        return sourceCd;
    }

    public void setSourceCd(String sourceCd) {
        this.sourceCd = sourceCd;
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

    public String getAssetAddBy() {
        return assetAddBy;
    }

    public void setAssetAddBy(String assetAddBy) {
        this.assetAddBy = assetAddBy;
    }

    public Date getAssetAddDate() {
        return assetAddDate;
    }

    public void setAssetAddDate(Date assetAddDate) {
        this.assetAddDate = assetAddDate;
    }

    public String getAssetModBy() {
        return assetModBy;
    }

    public void setAssetModBy(String assetModBy) {
        this.assetModBy = assetModBy;
    }

    public Date getAssetModDate() {
        return assetModDate;
    }

    public void setAssetModDate(Date assetModDate) {
        this.assetModDate = assetModDate;
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

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }
}
