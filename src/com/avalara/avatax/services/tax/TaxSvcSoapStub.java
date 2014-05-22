/**
 * TaxSvcSoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.tax;

import com.avalara.avatax.services.base.Timer;

/**
 * Axis-generated class that implements the proxy interface {@link TaxSvcSoap}
 * for contacting the Avalara Tax Web Service. Requires a
 * Web Service Deployment Descriptor (WSDD) configuration file at creation time named
 * (for example the sample file avatax4j.wsdd) in the same directory as the
 * project is running. The values in the file will be loaded as the default
 * configuration information.
 * Note: This class is not meant to be instantiated directly. Instead the preferred usage
 * is to obtain an instance of it (as a {@link TaxSvcSoap} interface via the
 * {@link TaxSvcLocator#getTaxSvcSoap(java.net.URL)} method.
 * <p>
 * <pre>
 * <b>Example:</b>
 * [Java]
 * EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 * TaxSvcLocator taxSvcLoc = new TaxSvcLocator(config);
 *
 * TaxSvcSoap svc = taxSvcLoc.getTaxSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 * // Set the profile
 * Profile profile = new Profile();
 * profile.setClient("TaxSvcTest,4.0.0.0");
 * svc.setProfile(profile);
 *
 * // Set security
 * Security security = new Security();
 * security.setAccount("account");
 * security.setLicense("license number");
 * svc.setSecurity(security);
 * </pre>
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class TaxSvcSoapStub extends com.avalara.avatax.services.base.BaseSvcSoapStub implements com.avalara.avatax.services.tax.TaxSvcSoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[10];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetTax");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxRequest"), com.avalara.avatax.services.tax.GetTaxRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.GetTaxResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetTaxHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistoryRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistoryRequest"), com.avalara.avatax.services.tax.GetTaxHistoryRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistoryResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.GetTaxHistoryResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistoryResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("PostTax");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostTaxRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostTaxRequest"), com.avalara.avatax.services.tax.PostTaxRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostTaxResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.PostTaxResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostTaxResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CommitTax");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CommitTaxRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CommitTaxRequest"), com.avalara.avatax.services.tax.CommitTaxRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CommitTaxResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.CommitTaxResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CommitTaxResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CancelTax");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelTaxRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelTaxRequest"), com.avalara.avatax.services.tax.CancelTaxRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelTaxResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.CancelTaxResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelTaxResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ReconcileTaxHistory");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReconcileTaxHistoryRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReconcileTaxHistoryRequest"), com.avalara.avatax.services.tax.ReconcileTaxHistoryRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReconcileTaxHistoryResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.ReconcileTaxHistoryResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReconcileTaxHistoryResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Ping");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Message"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PingResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.PingResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PingResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("IsAuthorized");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Operations"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "IsAuthorizedResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.IsAuthorizedResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "IsAuthorizedResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("AdjustTax");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Operations"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AdjustTaxResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.AdjustTaxResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AdjustTaxResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ApplyPayment");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ApplyPaymentRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ApplyPaymentRequest"), com.avalara.avatax.services.tax.ApplyPaymentRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ApplyPaymentResult"));
        oper.setReturnClass(com.avalara.avatax.services.tax.ApplyPaymentResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ApplyPaymentResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[9] = oper;

    }

    /**
     * Initializes a new instance of the class; preferred usage
     * is to obtain an instance of it (as a {@link TaxSvcSoap} interface via the
     * {@link TaxSvcLocator#getTaxSvcSoap(java.net.URL)} method.
     *
     * @throws org.apache.axis.AxisFault
     */
    public TaxSvcSoapStub() throws org.apache.axis.AxisFault {
        this(null);
    }

    /**
     * Initializes a new instance of the class; preferred usage
     * is to obtain an instance of it (as a {@link TaxSvcSoap} interface via the
     * {@link TaxSvcLocator#getTaxSvcSoap(java.net.URL)} method.
     *
     * @param endpointURL
     * @param service
     * @throws org.apache.axis.AxisFault
     */
    public TaxSvcSoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    /**
     * Initializes a new instance of the class; preferred usage
     * is to obtain an instance of it (as a {@link TaxSvcSoap} interface via the
     * {@link TaxSvcLocator#getTaxSvcSoap(java.net.URL)} method.
     *
     * @param service
     * @throws org.apache.axis.AxisFault
     */
    public TaxSvcSoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
        java.lang.Class cls;
        javax.xml.namespace.QName qName;
        javax.xml.namespace.QName qName2;
        java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
        java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
        java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
        java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
        java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
        java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
        java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
        java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
        java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
        java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfBaseAddress");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ArrayOfBaseAddress.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfGetTaxResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ArrayOfGetTaxResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfLine");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ArrayOfLine.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfMessage");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ArrayOfMessage.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxAddress");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ArrayOfTaxAddress.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxDetail");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ArrayOfTaxDetail.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfTaxLine");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ArrayOfTaxLine.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BaseAddress");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.BaseAddress.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BaseResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.BaseResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BoundaryLevel");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.BoundaryLevel.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelCode");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.CancelCode.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelTaxRequest");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.CancelTaxRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelTaxResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.CancelTaxResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CommitTaxRequest");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.CommitTaxRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CommitTaxResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.CommitTaxResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DetailLevel");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.DetailLevel.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocStatus");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.DocStatus.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "DocumentType");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.DocumentType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistoryRequest");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.GetTaxHistoryRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistoryResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.GetTaxHistoryResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxRequest");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.GetTaxRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.GetTaxResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "IsAuthorizedResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.IsAuthorizedResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "JurisdictionType");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.JurisdictionType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Line");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.Line.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Message");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.Message.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PingResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.PingResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostTaxRequest");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.PostTaxRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostTaxResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.PostTaxResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReconcileTaxHistoryRequest");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ReconcileTaxHistoryRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReconcileTaxHistoryResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.ReconcileTaxHistoryResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "SearchTaxHistoryResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.SearchTaxHistoryResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "SeverityLevel");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.SeverityLevel.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxAddress");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.TaxAddress.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxDetail");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.TaxDetail.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxLine");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.TaxLine.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TaxType");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.tax.TaxType.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                    cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                    cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.avalara.avatax.services.tax.GetTaxResult getTax(com.avalara.avatax.services.tax.GetTaxRequest getTaxRequest) throws java.rmi.RemoteException
    {
        if (super.cachedEndpoint == null)
        {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/GetTax");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTax"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try
        {
            //Timer to take care of ClientMetrics
            Timer timer = new Timer();
            timer.start();
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] {getTaxRequest});

            if (_resp instanceof java.rmi.RemoteException)
            {
                throw (java.rmi.RemoteException)_resp;
            }
            else
            {
                extractAttachments(_call);
                try
                {
                    GetTaxResult result = (com.avalara.avatax.services.tax.GetTaxResult) _resp;
                    //Will calculate ClientMetrics and will send it back to server
                    timer.stop(this,result);
                    return result;
                }
                catch (java.lang.Exception _exception)
                {
                    return (com.avalara.avatax.services.tax.GetTaxResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.GetTaxResult.class);
                }
            }
        }
        catch (org.apache.axis.AxisFault axisFaultException)
        {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.tax.GetTaxHistoryResult getTaxHistory(com.avalara.avatax.services.tax.GetTaxHistoryRequest getTaxHistoryRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/GetTaxHistory");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "GetTaxHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {getTaxHistoryRequest});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (com.avalara.avatax.services.tax.GetTaxHistoryResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.tax.GetTaxHistoryResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.GetTaxHistoryResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.tax.PostTaxResult postTax(com.avalara.avatax.services.tax.PostTaxRequest postTaxRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/PostTax");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PostTax"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {postTaxRequest});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (com.avalara.avatax.services.tax.PostTaxResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.tax.PostTaxResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.PostTaxResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.tax.CommitTaxResult commitTax(com.avalara.avatax.services.tax.CommitTaxRequest commitTaxRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/CommitTax");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CommitTax"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {commitTaxRequest});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (com.avalara.avatax.services.tax.CommitTaxResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.tax.CommitTaxResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.CommitTaxResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.tax.CancelTaxResult cancelTax(com.avalara.avatax.services.tax.CancelTaxRequest cancelTaxRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/CancelTax");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "CancelTax"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {cancelTaxRequest});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (com.avalara.avatax.services.tax.CancelTaxResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.tax.CancelTaxResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.CancelTaxResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.tax.ReconcileTaxHistoryResult reconcileTaxHistory(com.avalara.avatax.services.tax.ReconcileTaxHistoryRequest reconcileTaxHistoryRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/ReconcileTaxHistory");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ReconcileTaxHistory"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {reconcileTaxHistoryRequest});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (com.avalara.avatax.services.tax.ReconcileTaxHistoryResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.tax.ReconcileTaxHistoryResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.ReconcileTaxHistoryResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.tax.PingResult ping(java.lang.String message) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/Ping");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Ping"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {message});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (com.avalara.avatax.services.tax.PingResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.tax.PingResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.PingResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.tax.IsAuthorizedResult isAuthorized(java.lang.String operations) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/IsAuthorized");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "IsAuthorized"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {operations});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (com.avalara.avatax.services.tax.IsAuthorizedResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.tax.IsAuthorizedResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.IsAuthorizedResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.tax.AdjustTaxResult adjustTax(com.avalara.avatax.services.tax.AdjustTaxRequest adjustTaxRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/AdjustTax");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "AdjustTax"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {adjustTaxRequest});

            if (_resp instanceof java.rmi.RemoteException) {
                throw (java.rmi.RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (com.avalara.avatax.services.tax.AdjustTaxResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.tax.AdjustTaxResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.AdjustTaxResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }


    }

    public com.avalara.avatax.services.tax.ApplyPaymentResult applyPayment(com.avalara.avatax.services.tax.ApplyPaymentRequest applyPaymentRequest) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/ApplyPayment");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ApplyPayment"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {applyPaymentRequest});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.avalara.avatax.services.tax.ApplyPaymentResult) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.avalara.avatax.services.tax.ApplyPaymentResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.tax.ApplyPaymentResult.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }
}
