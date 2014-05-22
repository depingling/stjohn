package com.cleanwise.service.api.util;

public class ClwApiCustomizer {


    /**
     * Gets the storeDir attribute of the ClwApiCustomizer class
     * @return    The storeDir value
     */
    public static String getStoreDir() {
        String storeDir = System.getProperty("storeDir");
        if (null == storeDir) {
            storeDir = "def";
        }
        return storeDir;
    }


    /**
     *  Gets the serverDir attribute of the ClwApiCustomizer class     *
     *  @return    The serverDir value
     */
    public static String getServerDir() {

        String basename = System.getProperty("tomcat.home");

        String jboss_30_base = System.getProperty("jboss.server.home.dir");

        if (jboss_30_base != null) {
            basename = jboss_30_base;
        } else if (null == basename) {
            basename = System.getProperty("XSUITE_HOME");
            if(basename == null){
                basename = "..";
            }
        } else {
            basename = System.getProperty("tomcat.home") +  "/webapps/defst/externals/";
        }

        return basename;
    }

    /**
     * Get the logo configured for this account or store
     * @return absolute path
     * @param val tag value
     */
    public static String getCustomizeImgElement(String val) {
    	return System.getProperty("webdeploy") + "/en/images/" + val;
    }

    /**
     * Get the images and thumbnails for orger guide products
     * @return absolute path
     * @param val tag value
     */
    public static String getCustomizeElement(String val) {
    	return System.getProperty("webdeploy") + "/" + val;
    }

}
