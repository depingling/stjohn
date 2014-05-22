package com.cleanwise.service.api.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class HotDeployClassLoader extends ClassLoader {

    private static final Log log = LogFactory.getLog(HotDeployClassLoader.class);

    public HotDeployClassLoader(ClassLoader classLoader, String jarFileName) {
        super(classLoader);
		
    }

    public void openJar() {
		return;
		
    }

    public void closeJar() {
		return;
		
    }

    protected Class findClass(String name) throws ClassNotFoundException {

        Class clazz;
		clazz = Class.forName(name);
        log.info("findClass()=>  BEGIN.");
		
        log.info("findClass()=>  clazz: " + clazz);
        log.info("findClass()=>  END.");
        return clazz;
    }

    
    public Class loadClass(String name) throws ClassNotFoundException {
        return findClass(name);
    }

    private class ClassHolder {

        private Class clazz;
        private long timeStamp;

        public ClassHolder(Class clazz, long timeStamp) {
            this.clazz = clazz;
            this.timeStamp = timeStamp;
        }

        public Class getClazz() {
            return clazz;
        }

        public long getTimeStamp() {
            return timeStamp;
        }
    }

    private class RuntimeClassLoader extends ClassLoader {

        public RuntimeClassLoader(ClassLoader classLoader) {
            super(classLoader);
        }

       
        protected Class findClass(String name) throws ClassNotFoundException {
            return loadClass(name);
        }

    }

    public boolean isNeedReload() {
		return false;
    }
}