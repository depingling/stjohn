package com.cleanwise.service.api.process.variables;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Interchange;


public class InboundContent implements ExtProcessVariable {

    private int inboundId;

    public InboundContent(int pValue) {
        this.inboundId = pValue;
    }

    public byte[] getDecContent() throws Exception {
        Interchange interchangeEjb;
        interchangeEjb = APIAccess.getAPIAccess().getInterchangeAPI();
        return interchangeEjb.getInboundDecContent(this.inboundId);
    }

    public void updateDecContent(byte[] data, String user) throws Exception {
        Interchange interchangeEjb;
        interchangeEjb = APIAccess.getAPIAccess().getInterchangeAPI();
        interchangeEjb.updateInboundDecContent(this.inboundId, data, user);
    }

    public Object getValue() throws Exception {
        return getDecContent();
    }

    public void updateValue(Object pValue, String pUser) throws Exception {
        updateDecContent((byte[]) pValue, pUser);
    }
}
