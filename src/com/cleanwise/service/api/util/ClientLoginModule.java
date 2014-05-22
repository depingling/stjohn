package com.cleanwise.service.api.util;

import org.jboss.security.auth.callback.SecurityAssociationCallback;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ClientLoginModule extends org.jboss.security.ClientLoginModule {

    public void initialize(Subject pSubject, CallbackHandler pCallbackHandler, Map<String, ?> pMap, Map<String, ?> pMap1) {

        Callback[] callback = new Callback[]{new SecurityAssociationCallback()};

        try {
            pCallbackHandler.handle(callback);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedCallbackException e) {
            e.printStackTrace();
        }

        Map<String, Object> sharedOpt  = new HashMap<String, Object>();
        sharedOpt.put("javax.security.auth.login.name", ((SecurityAssociationCallback)callback[0]).getPrincipal());
        sharedOpt.put("javax.security.auth.login.password",((SecurityAssociationCallback)callback[0]).getCredential());

        HashMap<String, String> options = new HashMap<String, String>();
        options.put("password-stacking", "true");
        options.put("multi-threaded", "true");

        super.initialize(pSubject, pCallbackHandler, sharedOpt, options);

    }

}
