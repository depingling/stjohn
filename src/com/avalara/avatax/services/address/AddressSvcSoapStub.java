/**
 * AddressSvcSoapStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2 May 03, 2005 (02:20:24 EDT) WSDL2Java emitter.
 */

package com.avalara.avatax.services.address;

import com.avalara.avatax.services.base.Timer;

/**
 * Axis-generated class that implements the proxy interface {@link AddressSvcSoap}
 * for contacting the Avalara Address Web Service. Requires a
 * Web Service Deployment Descriptor (WSDD) configuration file at creation time named
 * (for example the sample file avatax4j.wsdd) in the same directory as the
 * project is running. The values in the file will be loaded as the default
 * configuration information.
 * Note: This class is not meant to be instantiated directly. Instead the preferred usage
 * is to obtain an instance of it (as a {@link AddressSvcSoap} interface via the
 * {@link AddressSvcLocator#getAddressSvcSoap(java.net.URL)} method.
 * <p>
 * <b>Example:</b>
 * <pre>
 * [Java]
 *  EngineConfiguration config = new FileProvider("avatax4j.wsdd");
 *  AddressSvc AddressSvc = new AddressSvcLocator(config);
 *
 *  AddressSvcSoap port = AddressSvc.getAddressSvcSoap(new URL("http://www.avalara.com/services/"));
 *
 *  // Set the profile
 *  Profile profile = new Profile();
 *  profile.setClient("AddressSvcTest,4.0.0.0");
 *  port.setProfile(profile);
 *
 *  // Set security
 *  Security security = new Security();
 *  security.setAccount("account");
 *  security.setLicense("license number");
 *  port.setSecurity(security);
 *
 * </pre>
 * @see org.apache.axis.EngineConfiguration
 * @see org.apache.axis.configuration.FileProvider
 * @author greggr
 * Copyright (c) 2005, Avalara.  All rights reserved.
 */
public class AddressSvcSoapStub extends com.avalara.avatax.services.base.BaseSvcSoapStub implements com.avalara.avatax.services.address.AddressSvcSoap {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Validate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidateRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidateRequest"), com.avalara.avatax.services.address.ValidateRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidateResult"));
        oper.setReturnClass(com.avalara.avatax.services.address.ValidateResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidateResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("Ping");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Message"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PingResult"));
        oper.setReturnClass(com.avalara.avatax.services.address.PingResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PingResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("IsAuthorized");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Operations"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "IsAuthorizedResult"));
        oper.setReturnClass(com.avalara.avatax.services.address.IsAuthorizedResult.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "IsAuthorizedResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    /**
     * Initializes a new instance of the class; preferred usage
     * is to obtain an instance of it (as a {@link AddressSvcSoap} interface via the
     * {@link AddressSvcLocator#getAddressSvcSoap(java.net.URL)} method.
     * @throws org.apache.axis.AxisFault
     */
    public AddressSvcSoapStub() throws org.apache.axis.AxisFault {
        this(null);
    }

    /**
     * Initializes a new instance of the class; preferred usage
     * is to obtain an instance of it (as a {@link AddressSvcSoap} interface via the
     * {@link AddressSvcLocator#getAddressSvcSoap(java.net.URL)} method.
     * @param endpointURL
     * @param service
     * @throws org.apache.axis.AxisFault
     */
    public AddressSvcSoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    /**
     * Initializes a new instance of the class; preferred usage
     * is to obtain an instance of it (as a {@link AddressSvcSoap} interface via the
     * {@link AddressSvcLocator#getAddressSvcSoap(java.net.URL)} method.
     * @param service
     * @throws org.apache.axis.AxisFault
     */
    public AddressSvcSoapStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfMessage");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.ArrayOfMessage.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ArrayOfValidAddress");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.ArrayOfValidAddress.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Address");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.Address.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "BaseResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.BaseResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "IsAuthorizedResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.IsAuthorizedResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Message");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.Message.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "PingResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.PingResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "SeverityLevel");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.SeverityLevel.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "TextCase");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.TextCase.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(enumsf);
        cachedDeserFactories.add(enumdf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidAddress");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.ValidAddress.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidateRequest");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.ValidateRequest.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

        qName = new javax.xml.namespace.QName("http://avatax.avalara.com/services", "ValidateResult");
        cachedSerQNames.add(qName);
        cls = com.avalara.avatax.services.address.ValidateResult.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

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

    public com.avalara.avatax.services.address.ValidateResult validate(com.avalara.avatax.services.address.ValidateRequest validateRequest) throws java.rmi.RemoteException
    {
        if (super.cachedEndpoint == null)
        {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://avatax.avalara.com/services/Validate");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://avatax.avalara.com/services", "Validate"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try
        {
            //Timer to take care of ClientMetrics
            Timer timer = new Timer();
            timer.start();
            java.lang.Object _resp = _call.invoke(new java.lang.Object[] {validateRequest});

            if (_resp instanceof java.rmi.RemoteException)
            {
                throw (java.rmi.RemoteException)_resp;
            }
            else
            {
                extractAttachments(_call);
                try
                {
                    com.avalara.avatax.services.address.ValidateResult result = (com.avalara.avatax.services.address.ValidateResult) _resp;
                    //Will calculate ClientMetrics and will send it back to server
                    timer.stop(this,result);
                    return result;
                }
                catch (java.lang.Exception _exception)
                {
                    return (com.avalara.avatax.services.address.ValidateResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.address.ValidateResult.class);
                }
            }
        }
        catch (org.apache.axis.AxisFault axisFaultException)
        {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.address.PingResult ping(java.lang.String message) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
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
                    return (com.avalara.avatax.services.address.PingResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.address.PingResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.address.PingResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public com.avalara.avatax.services.address.IsAuthorizedResult isAuthorized(java.lang.String operations) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
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
                    return (com.avalara.avatax.services.address.IsAuthorizedResult) _resp;
                } catch (java.lang.Exception _exception) {
                    return (com.avalara.avatax.services.address.IsAuthorizedResult) org.apache.axis.utils.JavaUtils.convert(_resp, com.avalara.avatax.services.address.IsAuthorizedResult.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

}
