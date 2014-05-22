package com.cleanwise.replicate;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

public class Replicate {
    protected static final Logger log = Logger.getLogger(Replicate.class);

    public static void setProperties() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("app.config"));
            for (Enumeration e = props.propertyNames() ; e.hasMoreElements() ;) {
                String key = (String)e.nextElement();
                System.setProperty(key, props.getProperty(key));
                log.info(key + ": " + props.getProperty(key));
            }
        } catch (IOException ioe) {
            log.error("can not read application configuration file 'app.config'");
            System.exit(-1);
        }
    }

    public static void main(String args[]) throws Exception {
        System.out.println("+------------------------------------------------------+");
        System.out.println("|                   Replicate                          |");
        System.out.println("|                                                      |");
        System.out.println("|     (C) Cleanwise, Inc. All Rights Reserved.         |");
        System.out.println("+------------------------------------------------------+");
        setProperties();
        ReplicateAccess env = new ReplicateAccess();
//        log.info("start: " + new Date(System.currentTimeMillis()));
//        env.copyTable("clw_order_item","rpl_order_item",env.getMasterConnection(), env.getSlaveConnection());
//        log.info("end: " + new Date(System.currentTimeMillis()));
//        env.testTable("clw_order_item",env.getMasterConnection(),env.getSlaveConnection());
//        env.copyTable("clw_inbound",env.getSlaveConnection(),env.getMasterConnection());
//        env.copyTable("clw_inbound", env.getMasterConnection(), env.getSlaveConnection());
        env.replicate("EDB", env.getMasterConnection(),env.getSlaveConnection());
    }

}

