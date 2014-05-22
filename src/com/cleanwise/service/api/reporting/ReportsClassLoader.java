package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.util.HotDeployClassLoader;
import com.cleanwise.service.api.session.ReportBean;

public class ReportsClassLoader extends HotDeployClassLoader {

    public static ReportsClassLoader INSTANCE = new ReportsClassLoader(
            ReportBean.class.getClassLoader(),
            System.getProperty("jboss.server.home.dir") +
                    System.getProperty("file.separator") +
                    "deploy" + System.getProperty("file.separator") +
                    System.getProperty("reporting.jar")
    );

    public ReportsClassLoader(ClassLoader classLoader, String jarFileName) {
        super(classLoader, jarFileName);
        openJar();
    }

    public static ReportsClassLoader getInstance(){
        return INSTANCE;
    }


    protected void finalize() throws Throwable {
        closeJar();
        super.finalize();
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        if(isNeedReload()) {
            reload();
        }
        return super.loadClass(name);
    }

    protected Class findClass(String name) throws ClassNotFoundException {
        if(isNeedReload()) {
            reload();
        }
        return super.findClass(name);
    }

    private void reload() {
        closeJar();  
        openJar();
    }
}
