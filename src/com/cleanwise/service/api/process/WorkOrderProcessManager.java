package com.cleanwise.service.api.process;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.process.operations.MakeLedgerEntry;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.EventBean;
import com.cleanwise.service.api.session.ProcessBean;
import com.cleanwise.service.api.util.ICleanwiseUser;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.TaskDescriptor;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.WorkOrderLifeCycle;
import com.cleanwise.service.api.util.WorkOrderUtil;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventProcessView;
import com.cleanwise.service.api.value.EventPropertyDataVector;
import com.cleanwise.service.api.value.ProcessData;
import com.cleanwise.service.api.value.TaskView;
import com.cleanwise.service.api.value.WorkOrderDetailView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Category;

/**
 */
public class WorkOrderProcessManager {

	public static interface OP_CODE {
		int WORKFLOW_PROCESS = 1;
		int SEND_PDF_TO_PROVIDER = 2;
		int COMMIT = 3;
		int UPDATE_LEDGER = 4;
		int REMOVE_LEDGER = 5;
	}

	private static String className = "WorkOrderProcessManager";

	WorkOrderDetailView workOrderDetailView;
	ICleanwiseUser user;
	boolean workflowProcessing;

	String mLogCategory = this.getClass().getName();
	Category category = Category.getInstance(mLogCategory);

	public WorkOrderProcessManager(WorkOrderDetailView workOrderDetailView,
			ICleanwiseUser user, boolean workflowProcessing) {
		super();
		this.workOrderDetailView = workOrderDetailView;
		this.user = user;
		this.workflowProcessing = workflowProcessing;
	}

	public ArrayList getOperationCodes(WorkOrderLifeCycle lifeCycle) {

		ArrayList opCodes = new ArrayList();
		boolean approver = user
				.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.WORK_ORDER_APPROVER);

		for (int i = 0; i < lifeCycle.getProcessTaskOpCodes().length; i++) {
			int code = lifeCycle.getProcessTaskOpCodes()[i];
			if (!(code == OP_CODE.WORKFLOW_PROCESS && approver && !this.workflowProcessing)) {
				opCodes.add(new Integer(code));
			}
		}
		return opCodes;
	}

	public static ProcessSchema getProcessSchema(List opCodes) {
		ProcessSchema schema = new ProcessSchema();
		Iterator it = opCodes.iterator();
		while (it.hasNext()) {
			int opCd = ((Integer) it.next()).intValue();
			switch (opCd) {
			case OP_CODE.WORKFLOW_PROCESS:
				schema
						.addStep(TaskDescriptor.WORK_ORDER_PROCESS.PROCESSWOSITEWORKFLOW);
				break;
			case OP_CODE.SEND_PDF_TO_PROVIDER:
				schema
						.addStep(TaskDescriptor.WORK_ORDER_PROCESS.SENDPDFTOPROVIDER);
				break;
			case OP_CODE.COMMIT:
				schema.addStep(TaskDescriptor.WORK_ORDER_PROCESS.COMMITER);
				break;
			case OP_CODE.UPDATE_LEDGER:
				schema.addStep(TaskDescriptor.WORK_ORDER_PROCESS.LEDGERUPDATE);
				break;
			case OP_CODE.REMOVE_LEDGER:
				schema.addStep(TaskDescriptor.WORK_ORDER_PROCESS.LEDGERREMOVE);
				break;
			}
		}
		return schema;
	}

	public void sendEvent(Connection connection, WorkOrderLifeCycle lifeCycle)
			throws Exception {
		Event eventEjb = APIAccess.getAPIAccess().getEventAPI();

		ProcessData process = new ProcessBean().getProcessByName(connection,
				RefCodeNames.PROCESS_NAMES.WORK_ORDER_PROCESS);

		EventData eventData = Utility.createEventDataForProcess();
		EventProcessView eventProcessView = new EventProcessView(eventData,
				new EventPropertyDataVector(), 0);
		eventProcessView.getProperties().add(
				Utility.createEventPropertyData("process_id",
						Event.PROPERTY_PROCESS_TEMPLATE_ID, new Integer(process
								.getProcessId()), 1));
		eventProcessView.getProperties().add(
				Utility.createEventPropertyData("workOrderDetail",
						Event.PROCESS_VARIABLE, this.workOrderDetailView, 2));
		eventProcessView.getProperties().add(
				Utility.createEventPropertyData("localeCd",
						Event.PROCESS_VARIABLE, this.user.getPrefLocale(), 3));
		eventProcessView.getProperties().add(
				Utility.createEventPropertyData("providerTradingType",
						Event.PROCESS_VARIABLE, WorkOrderUtil.EMAIL, 4));

		final String serviceProviderEmailAddress = this.workOrderDetailView
				.getServiceProvider() == null ? "" : this.workOrderDetailView
				.getServiceProvider().getPrimaryEmail().getEmailAddress();
		eventProcessView.getProperties()
				.add(
						Utility.createEventPropertyData("emailAddress",
								Event.PROCESS_VARIABLE,
								serviceProviderEmailAddress, 5));
		
		eventProcessView.getProperties().add(
				Utility.createEventPropertyData("user", Event.PROCESS_VARIABLE,
						user.getUser(), 6));

		final ProcessSchema processSchema = WorkOrderProcessManager
				.getProcessSchema(getOperationCodes(lifeCycle));

		for (int i = 0; i < processSchema.getSchema().length; i++) {
			category.info(String.format("processSchema[%1d]=%2s", i,
					((TaskView) ((Object[]) processSchema.getSchema()[i])[0])
							.getName()));
		}

		eventProcessView.getProperties().add(
				Utility.createEventPropertyData("processSchema",
						Event.PROCESS_SCHEMA, processSchema, 7));

		final String eventProcessStatus = lifeCycle.getEventProcessStatus();

		category.info(String.format(
				"found event status==%1s, for work order status=%2s",
				eventProcessStatus, lifeCycle.getWorkOrderStatus()));

		if(eventProcessStatus == null) 
		{
		} 
		else if (eventProcessStatus.equals(Event.STATUS_READY)) 
		{
			eventProcessView.getEventData().setStatus(eventProcessStatus);

			eventProcessView = new EventBean().addEventProcess(connection,
				eventProcessView, user.getUser().getUserName());

			category.info(String.format("event added, id=%1s, status=%2s",
				eventProcessView.getEventData().getEventId(), eventProcessView
						.getEventData().getStatus()));
		} 
		else if (eventProcessStatus.equals(Event.STATUS_SYNC_CALL)) 
		{

//			new EventBean().processEvent(eventProcessView.getEventData()
//					.getEventId());
//			category.info(String.format("event processed, id=%1s",
//					eventProcessView.getEventData().getEventId()));
			new MakeLedgerEntry().ledgerUpdate(this.workOrderDetailView, this.user.getUser());
			category.info(String.format("ledger updated, wotkOrderId=%1s, user=%2s",
					this.workOrderDetailView.getWorkOrder().getWorkOrderId(), this.user.getUser().getUserName()));
		} 
		else 
		{
			throw new Exception("Unexpected status:" + eventProcessStatus);
		}

	}

}
