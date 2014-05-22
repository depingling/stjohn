package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.TaskView;

/**
 */
public class TaskDescriptor {

    public interface WORK_ORDER_UPDATE {
        public static final TaskView LEDGERUPDATE = new TaskView("ledgerUpdate", "com.cleanwise.service.api.process.operations.MakeLedgerEntry", "ledgerUpdate", new Object[]{"com.cleanwise.service.api.value.WorkOrderDetailView", "com.cleanwise.service.api.value.UserData"}, new Object[]{});
        public static final TaskView LEDGERREMOVE = new TaskView("ledgerRemove", "com.cleanwise.service.api.process.operations.MakeLedgerEntry", "ledgerRemove", new Object[]{"com.cleanwise.service.api.value.WorkOrderDetailView", "com.cleanwise.service.api.value.UserData"}, new Object[]{});
        public static final TaskView BOOLEANANALYZER = new TaskView("booleanAnalyzer", "com.cleanwise.service.api.process.operations.BooleanAnalyzer", "analyze", new Object[]{"java.lang.Boolean"}, new Object[]{});
        public static final TaskView BUDGETWORKFLOW = new TaskView("budgetWorkflow", "com.cleanwise.service.api.process.operations.BudgetWorkflow", "processBudget", new Object[]{"com.cleanwise.service.api.value.WorkOrderDetailView"}, new Object[]{"java.lang.Boolean",});
    }
    
    public interface WORK_ORDER_PROCESS {
        public static final TaskView SENDPDFTOPROVIDER = new TaskView("sendPdfToProvider", "com.cleanwise.service.api.process.operations.WOOperations", "sendPdfToProvider", new Object[]{"com.cleanwise.service.api.value.WorkOrderDetailView", "java.util.Locale", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView PROCESSWOSITEWORKFLOW = new TaskView("ProcessWOSiteWorkflow", "com.cleanwise.service.api.pipeline.ProcessWOSiteWorkflow", "process", new Object[]{"com.cleanwise.service.api.value.WorkOrderDetailView"}, new Object[]{"com.cleanwise.service.api.process.workflow.WOProcessingData",});
        public static final TaskView COMMITER = new TaskView("commiter", "com.cleanwise.service.api.process.operations.WorkOrderSendToProviderCommiter", "process", new Object[]{"com.cleanwise.service.api.value.WorkOrderDetailView"}, new Object[]{});
        public static final TaskView LEDGERUPDATE = new TaskView("ledgerUpdate", "com.cleanwise.service.api.process.operations.MakeLedgerEntry", "ledgerUpdate", new Object[]{"com.cleanwise.service.api.value.WorkOrderDetailView", "com.cleanwise.service.api.value.UserData"}, new Object[]{});
        public static final TaskView LEDGERREMOVE = new TaskView("ledgerRemove", "com.cleanwise.service.api.process.operations.MakeLedgerEntry", "ledgerRemove", new Object[]{"com.cleanwise.service.api.value.WorkOrderDetailView", "com.cleanwise.service.api.value.UserData"}, new Object[]{});
    }

    public interface JAN_PACK_SITE_LOADER {
        public static final TaskView PREPARE = new TaskView("prepare", "com.cleanwise.service.api.process.operations.JanPakSiteLoaderOperations", "prepare", new Object[]{"java.lang.String", "java.lang.String", "java.lang.Object", "java.lang.Boolean"}, new Object[]{});
        public static final TaskView MATCH = new TaskView("match", "com.cleanwise.service.api.process.operations.JanPakSiteLoaderOperations", "match", new Object[]{"java.lang.Integer", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView UPDATE = new TaskView("update", "com.cleanwise.service.api.process.operations.JanPakSiteLoaderOperations", "update", new Object[]{"java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView INSERT = new TaskView("insert", "com.cleanwise.service.api.process.operations.JanPakSiteLoaderOperations", "insert", new Object[]{"java.lang.Integer", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView DELETE = new TaskView("delete", "com.cleanwise.service.api.process.operations.JanPakSiteLoaderOperations", "delete", new Object[]{"java.lang.Integer", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView REPORT = new TaskView("report", "com.cleanwise.service.api.process.operations.JanPakSiteLoaderOperations", "report", new Object[]{"java.lang.String"}, new Object[]{});
    }

    public interface JAN_PACK_ITEM_LOADER {
        public static final TaskView GETDISTRIBUTORID = new TaskView("getDistributorId", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "getDistributorId", new Object[]{"java.lang.Integer"}, new Object[]{"java.lang.Integer",});
        public static final TaskView GETSTORECATALOGID = new TaskView("getStoreCatalogId", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "getStoreCatalogId", new Object[]{"java.lang.Integer"}, new Object[]{"java.lang.Integer",});
        public static final TaskView PREPARE = new TaskView("prepare", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "prepare", new Object[]{"java.lang.String", "java.lang.String", "java.lang.Object", "java.lang.Boolean", "java.lang.String"}, new Object[]{});
        public static final TaskView MATCH = new TaskView("match", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "match", new Object[]{"java.lang.Integer", "java.lang.Integer", "java.lang.Integer", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView UPDATE = new TaskView("update", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "update", new Object[]{"java.lang.Integer", "java.lang.Integer", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView INSERT = new TaskView("insert", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "insert", new Object[]{"java.lang.Integer", "java.lang.Integer", "java.lang.Integer", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView DELETE = new TaskView("delete", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "delete", new Object[]{"java.lang.Integer", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView REPORT = new TaskView("report", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "report", new Object[]{"java.lang.String"}, new Object[]{});
        public static final TaskView DROPWORKTABLES = new TaskView("dropWorkTables", "com.cleanwise.service.api.process.operations.JanPakItemLoaderOperations", "dropWorkTables", new Object[]{"java.lang.String"}, new Object[]{});
        public static final TaskView SENDEVENT = new TaskView("sendEvent", "com.cleanwise.service.api.process.operations.EventJobOperations", "sendEvent", new Object[]{"com.cleanwise.service.api.value.EventProcessView", "java.lang.String"}, new Object[]{});
    }

    public interface PROCESS_OUTBOUND_TRANSACTION {
        public static final TaskView PROCESSOUTBOUNDREQUEST = new TaskView("processOutboundRequest", "com.cleanwise.service.apps.dataexchange.SendOutboundFile", "processOutboundRequest", new Object[]{"java.lang.String", "java.lang.String", "java.lang.String", "java.lang.Object", "java.util.HashMap"}, new Object[]{});
    }

    public interface EVENT_SYS_JOB {
        public static final TaskView JOBSTART = new TaskView("jobStart", "com.cleanwise.service.api.process.operations.EventJobOperations", "jobStart", new Object[]{"java.lang.String", "org.quartz.JobDetail"}, new Object[]{});
    }

    public interface ORDER_NOTIFICATION {
        public static final TaskView GENXML = new TaskView("genXml", "com.cleanwise.service.api.process.operations.GeneratorUtility", "runXmlFileGenerator", new Object[]{"java.lang.Integer", "java.lang.String", "java.lang.String", "java.lang.String"}, new Object[]{"java.lang.String",});
        public static final TaskView PREPAREEVENT = new TaskView("prepareEvent", "com.cleanwise.service.api.process.operations.EmailOperation", "prepareEventEmailData", new Object[]{"java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String"}, new Object[]{"com.cleanwise.service.api.value.EventEmailData",});
        public static final TaskView CREATE = new TaskView("create", "com.cleanwise.service.api.process.operations.EmailOperation", "createEvent", new Object[]{"com.cleanwise.service.api.value.EventEmailData", "java.lang.Integer", "java.lang.String"}, new Object[]{});
    }

    public interface OUTBOUND_SERVICE {
        public static final TaskView BUILDOUTBOUNDREQUEST = new TaskView("buildOutboundRequest", "com.cleanwise.service.api.process.operations.OutboundOperations", "buildOutboundRequest", new Object[]{"com.cleanwise.service.apps.dataexchange.OutboundProvider", "com.cleanwise.service.api.value.OutboundEDIRequestDataVector", "java.lang.String", "java.lang.String"}, new Object[]{"java.util.HashMap",});
        public static final TaskView OUTBOUNDREQUESTPROCESS = new TaskView("outboundRequestProcess", "com.cleanwise.service.api.process.operations.OutboundOperations", "outboundRequestProcess", new Object[]{"com.cleanwise.service.apps.dataexchange.OutboundProvider", "java.util.HashMap"}, new Object[]{"com.cleanwise.service.api.value.ProcessOutboundResultViewVector",});
        public static final TaskView PROCESSRESULTS = new TaskView("processResults", "com.cleanwise.service.api.process.operations.OutboundOperations", "processResults", new Object[]{"com.cleanwise.service.apps.dataexchange.OutboundProvider", "com.cleanwise.service.api.value.ProcessOutboundResultViewVector", "Integer"}, new Object[]{});
    }

    public interface PROCESS_INBOUND_TRANSACTION {
        public static final TaskView PROCESSINBOUNDREQUEST = new TaskView("processInboundRequest", "com.cleanwise.service.apps.dataexchange.InboundTranslate", "processInboundRequest", new Object[]{"java.lang.String", "java.lang.String", "java.lang.Object", "java.net.URI", "java.util.Map"}, new Object[]{});
    }

    public interface PROCESS_ORDER_850 {
        public static final TaskView PROCESSORDERREQUEST = new TaskView("processOrderRequest", "com.cleanwise.service.api.process.operations.OrderRequest850Task", "processOrderRequest", new Object[]{"com.cleanwise.service.api.value.OrderRequestData"}, new Object[]{});
    }

    public interface JAN_PACK_INVOICE_LOADER {
        public static final TaskView GETDISTRIBUTORID = new TaskView("getDistributorId", "com.cleanwise.service.api.process.operations.JanPakInvoiceLoaderOperations", "getDistributorId", new Object[]{"java.lang.Integer"}, new Object[]{"java.lang.Integer",});
        public static final TaskView PREPARE = new TaskView("prepare", "com.cleanwise.service.api.process.operations.JanPakInvoiceLoaderOperations", "prepare", new Object[]{"java.lang.Integer", "java.lang.Integer", "java.lang.String", "java.lang.Object", "java.lang.Boolean"}, new Object[]{});
        public static final TaskView MATCH = new TaskView("match", "com.cleanwise.service.api.process.operations.JanPakInvoiceLoaderOperations", "match", new Object[]{"java.lang.Integer", "java.lang.Integer", "java.lang.String"}, new Object[]{});
        public static final TaskView INSERT = new TaskView("insert", "com.cleanwise.service.api.process.operations.JanPakInvoiceLoaderOperations", "insert", new Object[]{"java.lang.Integer", "java.lang.Integer", "java.lang.String", "java.lang.String"}, new Object[]{});
        public static final TaskView REPORT = new TaskView("report", "com.cleanwise.service.api.process.operations.JanPakInvoiceLoaderOperations", "report", new Object[]{"java.lang.String"}, new Object[]{});
        public static final TaskView DROPWORKTABLES = new TaskView("dropWorkTables", "com.cleanwise.service.api.process.operations.JanPakInvoiceLoaderOperations", "dropWorkTables", new Object[]{"java.lang.String"}, new Object[]{});
    }
}
