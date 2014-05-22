package com.cleanwise.service.api.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.cleanwise.service.api.process.WorkOrderProcessManager.OP_CODE;
import com.cleanwise.service.api.session.Event;

public class WorkOrderLifeCycle {

	String statusCd;
	ICleanwiseUser user;

	public WorkOrderLifeCycle(String statusCd, ICleanwiseUser user) {
		super();
		this.statusCd = statusCd;
		this.user = user;
	}

	public boolean AllowableTransition(String ToStatusCd) {
		return true;
	}

	public String getWorkOrderStatus() {
		return this.statusCd;
	}

	static HashMap processTasks = new HashMap();
	static HashMap processStatus = new HashMap();
	static {
		
		final int[] sendToProviderTasks = new int[] { OP_CODE.WORKFLOW_PROCESS, OP_CODE.UPDATE_LEDGER,
				OP_CODE.COMMIT, OP_CODE.SEND_PDF_TO_PROVIDER };
		final int[] ledgerUpdateTasks = new int[] { OP_CODE.UPDATE_LEDGER };

		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST,	null);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED,	null);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER,	sendToProviderTasks);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL, null);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.APPROVED, ledgerUpdateTasks);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER, ledgerUpdateTasks);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER, ledgerUpdateTasks);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER, ledgerUpdateTasks);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED, ledgerUpdateTasks);
		processTasks.put(RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED, ledgerUpdateTasks);
		
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.NEW_REQUEST, null);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.CANCELLED, null);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.SENDING_TO_PROVIDER, Event.STATUS_READY);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.PENDING_APPROVAL, null);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.APPROVED, Event.STATUS_SYNC_CALL);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER, Event.STATUS_SYNC_CALL);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.ACCEPTED_BY_PROVIDER, Event.STATUS_SYNC_CALL);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.REJECTED_BY_PROVIDER, Event.STATUS_SYNC_CALL);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.COMPLETED, Event.STATUS_SYNC_CALL);
		processStatus.put(RefCodeNames.WORK_ORDER_STATUS_CD.CLOSED,	Event.STATUS_SYNC_CALL);

	}

	public boolean isNeedToSendEvent() {
		return processTasks.get(this.statusCd) != null;
	}

	public int[] getProcessTaskOpCodes() {
		int[] opCodes = new int[] {};
		if (isNeedToSendEvent()) {
			opCodes = (int[]) processTasks.get(this.statusCd);
		}
		return opCodes;
	}

	public String getEventProcessStatus() {
		return processStatus.get(this.statusCd) == null ? null : (String) processStatus.get(this.statusCd);
	}

}
