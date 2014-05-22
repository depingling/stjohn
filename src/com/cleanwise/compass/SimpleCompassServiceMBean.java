package com.cleanwise.compass;

import org.jboss.system.*;

public interface SimpleCompassServiceMBean extends ServiceMBean {

    void setJndiName(String jndiName) throws Exception;

    String getJndiName();

}
