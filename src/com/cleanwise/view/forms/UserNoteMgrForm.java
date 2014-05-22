package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import com.cleanwise.service.api.value.NoteJoinViewVector;
import com.cleanwise.service.api.value.NoteJoinView;
import com.cleanwise.view.utils.SelectableObjects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         04.12.2007
 * Time:         11:13:41
 *
 * @author Evgeny Vlasov, TrinitySoft, Inc.
 */

public class UserNoteMgrForm extends ActionForm {

    //fields for search
    private String effDate;
    private String expDate;
    private String searchType;
    private String searchField;

    //search result
    private SelectableObjects searchResult;

    //data for detail view
    private NoteJoinView noteForRead;
    private NoteJoinViewVector allNoteForRead;
    private NoteJoinViewVector readedNote;


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

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public SelectableObjects getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(SelectableObjects searchResult) {
        this.searchResult = searchResult;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        if (getSearchResult() != null) {
            getSearchResult().handleStutsFormResetRequest();
        }

    }

    public void reset(ActionMapping mapping, ServletRequest request) {
        if (getSearchResult() != null) {
            getSearchResult().handleStutsFormResetRequest();
        }
    }

    public void setNoteForRead(NoteJoinView noteForRead) {
        this.noteForRead = noteForRead;
    }


    public NoteJoinView getNoteForRead() {
        return noteForRead;
    }

    public void setAllNoteForRead(NoteJoinViewVector allNoteForRead) {
        this.allNoteForRead = allNoteForRead;
    }

    public NoteJoinViewVector getAllNoteForRead() {
        return allNoteForRead;
    }

    public NoteJoinViewVector getReadedNote() {
        return readedNote;
    }

    public void setReadedNote(NoteJoinViewVector readedNote) {
        this.readedNote = readedNote;
    }
}
