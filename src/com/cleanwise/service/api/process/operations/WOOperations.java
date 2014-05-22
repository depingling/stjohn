package com.cleanwise.service.api.process.operations;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.session.WorkOrder;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.WorkOrderUtil;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.ContentDetailView;
import com.cleanwise.service.api.value.ContentDetailViewVector;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.PropertyDataVector;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.WorkOrderContentViewVector;
import com.cleanwise.service.api.value.WorkOrderData;
import com.cleanwise.service.api.value.WorkOrderDetailView;
import com.cleanwise.service.apps.ApplicationsEmailTool;
import com.cleanwise.view.forms.UserWorkOrderDetailMgrForm;
import org.apache.log4j.Logger;

public class WOOperations {
    private static final Logger log = Logger.getLogger(WOOperations.class);

    public static void sendPdfToProvider(WorkOrderDetailView workOrderDetailView, Locale localeCd, String providerTradingType, String emailAddress) throws Exception {

        String lineSeparator = System.getProperty("line.separator");
        APIAccess factory  = new APIAccess();
        Content contentEjb = factory.getContentAPI();
        Account accountEjb = factory.getAccountAPI();
        Store storeEjb     = factory.getStoreAPI();
        WorkOrder workOrderEjb = factory.getWorkOrderAPI();

        ByteArrayOutputStream pdfout = new ByteArrayOutputStream();
        WorkOrderUtil woUtil = new WorkOrderUtil();
        
        workOrderDetailView = workOrderEjb.getWorkOrderDetailView(workOrderDetailView.getWorkOrder().getWorkOrderId());
        if (workOrderDetailView.getWorkOrder() != null &&
            RefCodeNames.WORK_ORDER_STATUS_CD.SENT_TO_PROVIDER.equals(workOrderDetailView.getWorkOrder().getStatusCd())) {
        
            if (woUtil.writeWoPdfToStream(workOrderDetailView, pdfout, localeCd)) {

                if (UserWorkOrderDetailMgrForm.EMAIL.equals(providerTradingType)) {

                    WorkOrderData woD = workOrderDetailView.getWorkOrder();
                    String name = "Work Order";
                    if (woD != null) {
                        name += " " + workOrderDetailView.getWorkOrder().getWorkOrderNum();
                    }
                    String fileName = name + ".pdf";

                    int siteId = workOrderDetailView.getWorkOrder().getBusEntityId();
                    int accountId = accountEjb.getAccountIdForSite(siteId);
                    int storeId = storeEjb.getStoreIdByAccount(accountId);
                    AccountData accountD = accountEjb.getAccount(accountId, storeId);
                    StoreData storeD = storeEjb.getStore(storeId);

                    StringBuffer automatedMessage = new StringBuffer();
                    automatedMessage.append("************************************************************************************************")
                                    .append(lineSeparator)
                                    .append("This is an automated email.  Do not reply to this email.")
                                    .append(lineSeparator)
                                    .append(lineSeparator)
                                    .append("Dear Provider - ")
                                    .append(lineSeparator);
                    if (woD != null && accountD != null && storeD != null) {
                        automatedMessage.append("Attached please find, Work order: ")
                                        .append(workOrderDetailView.getWorkOrder().getWorkOrderNum())
                                        .append(" (from store: ")
                                        .append(storeD.getBusEntity().getShortDesc())
                                        .append("/account: ")
                                        .append(accountD.getBusEntity().getShortDesc())
                                        .append(")");
                    } else {
                        automatedMessage.append("Attached please find, Work order");
                    }
                        automatedMessage.append(" for your review.")
                                        .append(lineSeparator)
                                        .append("You will need Adobe Acrobat Reader to open the attached file.")
                                        .append(lineSeparator)
                                        .append("Please respond as appropriate.")
                                        .append(lineSeparator)
                                        .append(lineSeparator)
                                        .append("Thank you.")
                                        .append(lineSeparator)
                                        .append("************************************************************************************************")
                                        .append(lineSeparator);

                    String emailFromAddress = null;
                    if (storeD != null) {
                        PropertyDataVector miscPropertyD = storeD.getMiscProperties();
                        if (miscPropertyD != null) {
                            Iterator miscProperyIt = miscPropertyD.iterator();
                            PropertyData pD;
                            while(miscProperyIt.hasNext()) {
                                pD = (PropertyData) miscProperyIt.next();
                                if (RefCodeNames.PROPERTY_TYPE_CD.WORK_ORDER_EMAIL_ADDRESS.equals(pD.getPropertyTypeCd())) {
                                    emailFromAddress = pD.getValue();
                                }
                            }
                        }
                    }

                    EventEmailDataView eventEmail = new EventEmailDataView();

                    if (Utility.isSet(emailFromAddress)) {
                        eventEmail.setFromAddress(emailFromAddress);
                    }
                    eventEmail.setToAddress(emailAddress);
                    eventEmail.setSubject(name);
                    eventEmail.setText(automatedMessage.toString());
                    eventEmail.setEmailStatusCd(Event.STATUS_READY);

                    ArrayList attachArray = new ArrayList();

                    byte[] outbytes = pdfout.toByteArray();
                    FileAttach pdf = new FileAttach(fileName, outbytes, "application/pdf", outbytes.length);
                    attachArray.add(pdf);

                    WorkOrderContentViewVector contents = new WorkOrderContentViewVector();
                    contents.addAll(workOrderDetailView.getContents());
                    Collection woiContent = WorkOrderUtil.getContentOnly(workOrderDetailView.getWorkOrderItems());
                    contents.addAll(woiContent);
                    IdVector contentIds = WorkOrderUtil.getContentIds(contents);

                    ContentDetailViewVector contentDetailVV = contentEjb.getContentDetailViewVector(contentIds);

                    if (!contentDetailVV.isEmpty()) {
                        Iterator it = contentDetailVV.iterator();
                        while (it.hasNext()) {
                            ContentDetailView contentDetail = (ContentDetailView) it.next();
                            byte[] contentData = contentDetail.getData();
                            if (contentData.length > 0) {
                                FileAttach contentAtt = new FileAttach(contentDetail.getPath(),
                                        contentData,
                                        contentDetail.getContentTypeCd(),
                                        contentData.length);
                                attachArray.add(contentAtt);
                            }
                        }
                    }

                    eventEmail.setAttachments((FileAttach[]) attachArray.toArray(new FileAttach[attachArray.size()]));

                    new ApplicationsEmailTool().createEvent(eventEmail);


                }
            }
        }
    }
}
