package com.cleanwise.service.apps.dataexchange.xpedx;

public class AccountReference extends Reference {

    public AccountReference(String pAccountNamber) {
        create(pAccountNamber);
    }

    public String getAccountNamber() {
        return (String) get(0);
    }

}
