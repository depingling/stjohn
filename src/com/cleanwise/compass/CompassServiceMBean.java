package com.cleanwise.compass;

import org.jboss.system.*;

public interface CompassServiceMBean extends ServiceMBean {

    void setJndiName(String jndiName) throws Exception;

    String getJndiName();

}
