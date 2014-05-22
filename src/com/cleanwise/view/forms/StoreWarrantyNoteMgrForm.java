package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.WarrantyData;
import com.cleanwise.service.api.value.WarrantyNoteDataVector;
import com.cleanwise.service.api.value.WarrantyNoteData;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         02.10.2007
 * Time:         13:22:03
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */

public class StoreWarrantyNoteMgrForm  extends StorePortalBaseForm {
    private WarrantyData warrantyData;
    private String warrantyNoteTypeCd="";
    private String searchField="";
    private WarrantyNoteDataVector searchResult;
    private WarrantyNoteData noteDetail;

    public void setWarrantyData(WarrantyData warrantyData) {
        this.warrantyData = warrantyData;
    }

    public void setWarrantyNoteTypeCd(String warrantyNoteTypeCd) {
        this.warrantyNoteTypeCd = warrantyNoteTypeCd;
    }

    public WarrantyData getWarrantyData() {
        return warrantyData;
    }

    public String getWarrantyNoteTypeCd() {
        return warrantyNoteTypeCd;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public WarrantyNoteDataVector getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(WarrantyNoteDataVector searchResult) {
        this.searchResult = searchResult;
    }

    public void setNoteDetail(WarrantyNoteData noteDetail) {
        this.noteDetail = noteDetail;
    }


    public WarrantyNoteData getNoteDetail() {
        return noteDetail;
    }
}
