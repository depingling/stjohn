package com.cleanwise.view.forms;

import org.apache.struts.action.ActionForm;
import com.cleanwise.service.api.value.WorkOrderNoteDataVector;
import com.cleanwise.service.api.value.WorkOrderData;

import java.util.Date;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         16.10.2007
 * Time:         3:46:42
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserWorkOrderNoteMgrForm extends ActionForm {

    WorkOrderNoteDataVector workOrderNotes;
    WorkOrderData  workOrder;

    //detail field
    private int workOrderId;
    private int workOrderNoteId;
    private String addBy;
    private Date addDate;
    private String modBy;
    private Date modDate;
    private String note;
    private String shortDesc;
    private String typeCd;

    public WorkOrderNoteDataVector getWorkOrderNotes() {
        return workOrderNotes;
    }

    public void setWorkOrderNotes(WorkOrderNoteDataVector workOrderNotes) {
        this.workOrderNotes = workOrderNotes;
    }

    public WorkOrderData getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrderData workOrder) {
        this.workOrder = workOrder;
    }


    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getAddBy() {
        return addBy;
    }

    public void setAddBy(String addBy) {
        this.addBy = addBy;
    }

    public int getWorkOrderNoteId() {
        return workOrderNoteId;
    }

    public void setWorkOrderNoteId(int workOrderNoteId) {
        this.workOrderNoteId = workOrderNoteId;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getModBy() {
        return modBy;
    }

    public void setModBy(String modBy) {
        this.modBy = modBy;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }
}
