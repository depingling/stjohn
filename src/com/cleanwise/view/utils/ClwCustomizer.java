/**
 *  Title: Customizer Description: This class provides UI objects on session
 *  context basis Purpose: Copyright: Copyright (c) 2001 Company: CleanWise,
 *  Inc.
 *
 *@author     Yuriy Kupershmidt
 */

package com.cleanwise.view.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.ClwApiCustomizer;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.MenuItemView;
import com.cleanwise.service.api.value.PropertyData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.view.i18n.ClwI18nUtil;

/**
 *  Description of the Class
 *
 *@author     dvieira
 *@created    June 6, 2003
 */
public class ClwCustomizer {
	
	public static final String PORTAL_DEFAULT = "store";
	public static final String ATTRIBUTE_FILE_LOCATION_MAP = "store.files";
	private static final Logger log = Logger.getLogger(ClwCustomizer.class);

    /**
     *  Gets the imagePath attribute of the ClwCustomizer class
     *
     *@param  pSession    Description of the Parameter
     *@param  pLogicPath  Description of the Parameter
     *@return             The imagePath value
     */
    public static String getImagePath(HttpSession pSession, String pLogicPath) {
        String storeDir = (String) pSession.getAttribute("store.dir");
        String s = (String) pSession.getAttribute("pages.store.prefix");

        String path = "";
        if (null == s) {
            // This page is not store specific. Get the images from the public area.
            path = "/" + storeDir + "/en/images/";
            return path + pLogicPath;
        }

        // Construct a path to the store specific image repository.
        path = "/" + storeDir + "/store/" + s + "/images/";
        return path + pLogicPath;
    }


    /**
     *  Gets the filePath attribute of the ClwCustomizer class
     *
     *@param  pSession       Description of the Parameter
     *@param  pFileName      Description of the Parameter
     *@return                The filePath value
     *@exception  Exception  Description of the Exception
     */
    public static String getFilePath(HttpSession pSession, String pFileName)
             throws Exception {
        return getFilePath(pSession, null, pFileName);
    }


    /**
     *  Gets the filePath attribute of the ClwCustomizer class
     *
     *@param  pSession      Description of the Parameter
     *@param  pRelativeDir  Description of the Parameter
     *@param  pFileName     Description of the Parameter
     *@return               The filePath value
     */
    public static String getFilePath(HttpSession pSession, String pRelativeDir, 
    		String pFileName) {

        String store = (String) pSession.getAttribute("pages.store.templates");

        // The files are all relative to the JBoss bin directory.
        String absName = System.getProperty("webdeploy") + store + "/" + pFileName;
        File tf = new File(absName);
        String fname = "";
        if (pRelativeDir != null) {
            fname = pRelativeDir;
        }
        // uncomment the log entries to test multi-store pages
        // durval - 7/31/2002
        if (tf.exists()) {
            //log.info("File: " + absName + " found.");
            fname += store + "/" + pFileName;
        } else {
            //log.info("File: " + absName + " not found.");
            if(fname==null || fname.trim().length()==0) fname = "/store/";
            fname +=  pFileName;
        }

        //log.info("Returning:" + fname + ":" );
        return fname;
    }

    public static String getAbsFilePath(HttpServletRequest pRequest, String pFileName) {
    	String returnValue = getAbsFilePath(pRequest,null,pFileName);
    	return returnValue;
    }

    public static String getAbsFilePath(HttpServletRequest pRequest, String pPortal, 
    		String pFileName) {
        HttpSession session = pRequest.getSession();
        String webDir = (String) session.getAttribute("webdeploy");
        String relPath = getStoreFilePath(pRequest,pPortal, pFileName);
        return webDir + relPath;
    }

    public static String getRootFilePath(HttpServletRequest pRequest, String pFileName) {
        return getRootFilePath(pRequest,null,pFileName);
    }

    public static String getRootFilePath(HttpServletRequest pRequest, String pPortal, 
    		String pFileName) {
        HttpSession session = pRequest.getSession();
        String storeDir = (String) session.getAttribute("store.dir");
        String relPath = getStoreFilePath(pRequest,pPortal, pFileName);
        return "/"+storeDir+relPath;
    }

    public static String getStoreFilePath
            (HttpServletRequest pRequest, String pFileName) {
        return getStoreFilePath(pRequest,null, pFileName);
    }
    
    /**
     * Flush the file map cache for the session.  This is necessary if you want to 
     * change any of the underlying path infromation for the store or account.
     * @param session the session to clear the information from
     */
    public static void flushFileMapCache(HttpSession session) {
    	HashMap<String, String> filePathHM = (HashMap<String,String>) session.getAttribute(ClwCustomizer.ATTRIBUTE_FILE_LOCATION_MAP);
        if(filePathHM!=null) {
        	filePathHM.clear();
       }
    }

    public static String getStoreFilePath(HttpServletRequest pRequest, String pPortal, 
    		String pFileName) {
    	String returnValue = null;
    	
    	//if no filename was specified, return
        if(pFileName==null) {
            return "";
        }
        
        pFileName = pFileName.trim();
        HttpSession session = pRequest.getSession();
        HashMap<String, String> filePathHM = (HashMap<String, String>) session.getAttribute(ClwCustomizer.ATTRIBUTE_FILE_LOCATION_MAP);
        //TODO - add a map holding store to new UI access to improve performance if 
        //necessary.  Currently this is determined every time this method is run.
        if(filePathHM==null) {
            filePathHM = new HashMap<String, String>();
            session.setAttribute(ClwCustomizer.ATTRIBUTE_FILE_LOCATION_MAP,filePathHM);
        }
        //create a list of portals in which we will look for the specified file.
        List<String> portalList = new ArrayList<String>();
        //if a portal was specified as a parameter, add it to the list.  If not, add the
        //default portal
        if (Utility.isSet(pPortal)) {
        	portalList.add(pPortal);
        }
        else {
            portalList.add(ClwCustomizer.PORTAL_DEFAULT);        	
        }
        //Make key
        int storeId = 0;
        int accountId = 0;
        CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        String accountDir = "";
        if(user!=null) {
            StoreData storeD = user.getUserStore();
            if(storeD!=null) {
                storeId = storeD.getBusEntity().getBusEntityId();
                //if a portal wasn't specified as a parameter, and an alternate portal has been
                //specified for the store, add that portal to the beginning of the list of portals 
                //to check.
                if (!Utility.isSet(pPortal)) {
                	String alternatePortal = Utility.getAlternatePortal(storeD);
                	if (Utility.isSet(alternatePortal)) {
                		portalList.add(alternatePortal);
                	}
                }
            }
            AccountData accountD = user.getUserAccount();
            if(accountD!=null) {
                accountId = accountD.getBusEntity().getBusEntityId();
                accountDir = accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER);
                String alternatePortal = Utility.getAlternatePortal(accountD);
            	if (Utility.isSet(alternatePortal) && !portalList.contains(alternatePortal)) {
            		portalList.add(alternatePortal);
            	}
            }
        }
        Locale locale = ClwI18nUtil.getUserLocale(pRequest);

        //iterate over the list of portals, looking for the specified file
        Iterator<String> portalIterator = portalList.iterator();
        while (portalIterator.hasNext() && !Utility.isSet(returnValue)) {
        	String portal = portalIterator.next();
            String key = portal+"@"+locale+"@"+pFileName+"@"+storeId+"@"+accountId;
            String resultFilePath = (String) filePathHM.get(key);
            if(resultFilePath!=null) {
              return resultFilePath;
            }

            String storePrefix = (String) session.getAttribute("pages.store.prefix");
            if(storePrefix==null) storePrefix = "";

            //pre-pend mobile to the store dir if this is a mobile setup
            if(Utility.isTrue((String)session.getAttribute(Constants.MOBILE_CLIENT))){
            	storePrefix = "mobile" + "/" + storePrefix;
            }

            String webDir = (String) session.getAttribute("webdeploy");

            if(webDir==null) webDir="";
            int ind = webDir.indexOf("deploy");
            if(ind<0) {
              (new Exception("Invalid current directory: "+webDir)).printStackTrace();
              return "";
            }
            //trying to find file
            String storeAcctPrefix = (Utility.isSet(accountDir))?
                                          storePrefix+"/"+accountDir:storePrefix;
            StringTokenizer tok = new StringTokenizer(storeAcctPrefix,"/");
            String[] storeAcctPrefixA = new String[tok.countTokens()+1];
            storeAcctPrefixA[0] = "";
            ind = 1;
            while(tok.hasMoreTokens()){
                String nextToken = tok.nextToken();
                if(ind==1) {
                    storeAcctPrefixA[ind] = nextToken;
                } else {
                    storeAcctPrefixA[ind] = storeAcctPrefixA[ind-1]+"/"+nextToken;
                }
                ind++;
            }
            String absPath = "";
            String filePath = "";
            boolean foundFl = false;
            for(int ii=storeAcctPrefixA.length-1; ii>=0; ii--) {
                filePath =
                   "/" + portal + "/" + storeAcctPrefixA[ii] + "/" + locale + "/" + pFileName;

                absPath =  webDir + filePath;
                File tf = new File(absPath);
                if(tf.exists()) {
                    foundFl = true;
                    break;
                }

                if(Utility.isSet(locale.getLanguage())) {
                    filePath = "/" + portal + "/" + storeAcctPrefixA[ii] + "/" + locale.getLanguage() + "/" + pFileName;
                    absPath =  webDir + filePath;
                    tf = new File(absPath);
                    if(tf.exists()) {
                        foundFl = true;
                        break;
                    }
                }

                filePath = "/" + portal + "/" + storeAcctPrefixA[ii] + "/" + pFileName;
                absPath =  webDir + filePath;
                tf = new File(absPath);
                if(tf.exists()) {
                    foundFl = true;
                    break;
                }
            }

            log.info("ClwCustomizer.getStoreFilePath: absPath="+absPath+" ("+foundFl+")");
            if(foundFl) {
                filePathHM.put(key,filePath);
                returnValue = filePath;
            }
        }
        return returnValue;
    }

    public static String getSIP(HttpServletRequest pRequest, String pPortal, 
    		String pFileName) {
    	return getSIP(pRequest, pPortal, pFileName, false);
    }

    /**
     * Returns the store specific/account specific path for the image.  @see storeFileImagePath
     */
    public static String getSIP(HttpServletRequest pRequest, String pPortal, 
    		String pFileName,boolean absolutePath) {
    	if(pFileName==null) {
            return "";
        }
    	HttpSession session = pRequest.getSession();
    	HashMap<String, String> filePathHM = (HashMap<String, String>) session.getAttribute(ClwCustomizer.ATTRIBUTE_FILE_LOCATION_MAP);
        if(filePathHM==null) {
            filePathHM = new HashMap<String, String>();
            session.setAttribute(ClwCustomizer.ATTRIBUTE_FILE_LOCATION_MAP,filePathHM);
        }

        CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD =null;
        AccountData accountD =null;
        if(user!=null) {
        	storeD = user.getUserStore();
        	accountD = user.getUserAccount();
        }

        String rootDir = (String) session.getAttribute("store.dir");
        Locale locale = ClwI18nUtil.getUserLocale(pRequest);
        String storePrefix = (String) session.getAttribute("pages.store.prefix");
        String webDir = (String) session.getAttribute("webdeploy");
        return getSIP(pPortal, pFileName, storePrefix, locale, filePathHM, storeD, accountD, webDir, rootDir,absolutePath);
    }

    /**
     * Returns the store specific/account specific path for the image.  @see storeFileImagePath
     */
    public static String getSIP(HttpServletRequest pRequest, String pFileName) {
    	return getSIP(pRequest,null,pFileName);
	}

    /**
     * Returns the store specific/account specific path for the image.  @see storeFileImagePath
     */
    private static String getSIP(String pFileName,StoreData storeD,AccountData accountD,boolean absolutePath) {
    	return getSIP(null,pFileName,null,null,null,storeD,accountD,null, null,absolutePath);
	}

    /**
     * Returns the store specific/account specific path for the image.  @see storeFileImagePath and @see getSIP
     */
	private static String getSIP(String pPortal, String pFileName,String storePrefix, Locale locale,
			HashMap<String, String> filePathHM,StoreData storeD,AccountData accountD, String webDir, String rootDir,boolean absolutePath){
		
		String returnValue = null;
		
		//if no filename was specified just return
        if(pFileName==null) {
            return "";
        }

        pFileName = pFileName.trim();

        //guess at some values if not provided
        if(locale==null){
	    	if(accountD != null && Utility.isSet(accountD.getBusEntity().getLocaleCd())){
	    		locale = Utility.parseLocaleCode(accountD.getBusEntity().getLocaleCd());
	    	}
	    	if(storeD != null && Utility.isSet(storeD.getBusEntity().getLocaleCd())){
	    		locale = Utility.parseLocaleCode(storeD.getBusEntity().getLocaleCd());
	    	}
        }
        if(filePathHM == null){
        	filePathHM = new HashMap<String, String>();
        }
        if(rootDir == null){
        	rootDir = ClwCustomizer.getStoreDir();
        }
        if(!Utility.isSet(storePrefix)){
        	storePrefix = storeD.getPrefix().getValue();
        }
        if(!Utility.isSet(webDir)){
        	log.info(">>>>>>>>>>>>>>>>>>AHHHHHHH::"+System.getProperty("webdeploy")+"<<<<<<<<<<<<<<<<<<");
        	webDir = System.getProperty("webdeploy");
        }

        //create a list of portals in which we will look for the specified file.
        List<String> portalList = new ArrayList<String>();
        //if a portal was specified as a parameter, add it to the list.  If not, add the
        //default portal
        if (Utility.isSet(pPortal)) {
        	portalList.add(pPortal);
        }
        else {
            portalList.add(ClwCustomizer.PORTAL_DEFAULT);        	
        }
        
        //Make key
        int storeId = 0;
        int accountId = 0;
        String accountDir = "";

        if(storeD!=null) {
            storeId = storeD.getBusEntity().getBusEntityId();
            //if a portal wasn't specified as a parameter, and an alternate portal has been
            //specified for the store, add that portal to the beginning of the list of portals 
            //to check.
            if (!Utility.isSet(pPortal)) {
            	String alternatePortal = Utility.getAlternatePortal(storeD);
            	if (Utility.isSet(alternatePortal)) {
            		portalList.add(alternatePortal);
            	}
            }
        }

        if(accountD!=null) {
            accountId = accountD.getBusEntity().getBusEntityId();
            accountDir = accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER);
            String alternatePortal = Utility.getAlternatePortal(accountD);
         	if (Utility.isSet(alternatePortal) && !portalList.contains(alternatePortal)) {
         		portalList.add(alternatePortal);
         	}
        }

        //iterate over the list of portals, looking for the specified file
        Iterator<String> portalIterator = portalList.iterator();
        while (portalIterator.hasNext() && !Utility.isSet(returnValue)) {
        	String portal = portalIterator.next();
            String key = portal+"@"+locale+"@"+pFileName+"@"+storeId+"@"+accountId+"@"+absolutePath;
            String resultFilePath = (String) filePathHM.get(key);
            if(resultFilePath!=null) {
              return resultFilePath;
            }

            if(storePrefix==null) storePrefix = "";

            if(webDir==null) webDir="";
            int ind = webDir.indexOf("deploy");
            if(ind<0) {
              (new Exception("Invalid current directory: "+webDir)).printStackTrace();
              return "";
            }
            //trying to find file
            String storeAcctPrefix = (Utility.isSet(accountDir))?
                                          storePrefix+"/"+accountDir:storePrefix;
            StringTokenizer tok = new StringTokenizer(storeAcctPrefix,"/");
            String[] storeAcctPrefixA = new String[tok.countTokens()+1];
            storeAcctPrefixA[0] = "";
            ind = 1;
            while(tok.hasMoreTokens()){
                String nextToken = tok.nextToken();
                if(ind==1) {
                    storeAcctPrefixA[ind] = nextToken;
                } else {
                    storeAcctPrefixA[ind] = storeAcctPrefixA[ind-1]+"/"+nextToken;
                }
                ind++;
            }
            String absPath = "";
            String filePath = "";
            boolean foundFl = false;
            for(int ii=storeAcctPrefixA.length-1; ii>=0; ii--) {
                filePath = "/" + portal + "/" + storeAcctPrefixA[ii] +
                           "/" + locale + "/" + "images/"+ pFileName;
                absPath =  webDir + filePath;
                File tf = new File(absPath);
                if(tf.exists()) {
                    foundFl = true;
                    break;
                }

                filePath = "/" + portal + "/" + storeAcctPrefixA[ii] +
                           "/" + "images/"+ pFileName;
                absPath =  webDir + filePath;
                tf = new File(absPath);
                if(tf.exists()) {
                    foundFl = true;
                    break;
                }

                filePath = "/" + portal + "/" + storeAcctPrefixA[ii] +
                           "/" + "images/"+ pFileName;
                absPath =  webDir + filePath;
                tf = new File(absPath);
                if(tf.exists()) {
                    foundFl = true;
                    break;
                }
            }

            log.info("ClwCustomizer ZZZZZZZZZZZZZZZZZZZZZZZZZZZ absPath: "+absPath+" ("+foundFl+")");
            if(absolutePath){
            	filePath = absPath;
            }else{
            	filePath = "/" + rootDir + filePath;
            }
            if(foundFl) {
                filePathHM.put(key,filePath);
            }
            returnValue = filePath;
        }
        return returnValue;
    }

    /**
     *  Gets the evenRowColor attribute of the ClwCustomizer class
     *
     *@param  pSession  Description of the Parameter
     *@return           The evenRowColor value
     */
    public static String getEvenRowColor(HttpSession pSession) {
        if(pSession != null){
            String color = (String) pSession.getAttribute(Constants.EVEN_ROW_COLOR);
            if(color != null){
                return color;
            }
        }
        return "#FFFFFF";
    }


    /**
     *  Gets the oddRowColor attribute of the ClwCustomizer class
     *
     *@param  pSession  Description of the Parameter
     *@return           The oddRowColor value
     */
    public static String getOddRowColor(HttpSession pSession) {
        if(pSession != null){
            String color = (String) pSession.getAttribute(Constants.ODD_ROW_COLOR);
            if(color != null){
                return color;
            }
        }
        return "B4E0B4";
    }


    /**
     *  Gets the evenRowColor attribute of the ClwCustomizer class
     *
     *@param  pSession    Description of the Parameter
     *@param  pRowNumber  Description of the Parameter
     *@return             The evenRowColor value
     */
    public static String getEvenRowColor(HttpSession pSession, Integer pRowNumber) {
        return getEvenRowColor(pSession, pRowNumber.intValue());
    }

    public static String getEvenRowColor(HttpSession pSession, int pRowNum ) {
        if ((pRowNum % 2) == 0) {
            return getEvenRowColor(pSession);
        }
        return getOddRowColor(pSession);
    }

    /**
     *  Gets the storeDir attribute of the ClwCustomizer class
     *
     *@return    The storeDir value
     */
    public static String getStoreDir() {
       return ClwApiCustomizer.getStoreDir();
    }

    /**
     *  Gets the clwSwitch attribute of the ClwCustomizer class
     *
     *@return                The clwSwitch value
     *@exception  Exception  Description of the Exception
     */
    public static boolean getClwSwitch() {
       return true;
    }


    /**
     *  Gets the serverDir attribute of the ClwCustomizer class
     *
     *@return    The serverDir value
     */
    public static String getServerDir() {
      return ClwApiCustomizer.getServerDir();
    }

    static public String getLogoPathForPrinterDisplay(StoreData pStore){
    	String path = getSIP("logo.png", pStore, null,true);
    	log.info("Returning="+path);
    	return path;
    }

	// get the currency symbol for a currency viewed through a particular locale.
	// The symbol for USD us $ when viewed from the en_US locale.
	// The symbol for USD us US$ when viewed from the en_IE locale.
	public static String getSymbolForCurrency(String pCurrencyCode, Locale pLoc) {
		Currency ocurrCd = Currency.getInstance(pCurrencyCode);
		return ocurrCd.getSymbol(pLoc);
	}

    /**
     * Get the logo configured for this account or store
     *
     * @param pRequest    request
     * @param pImgElement example : pImgElement = "pages.logo1.image" )
     * @return absolute path
     */
    public static String getCustomizeImgElement(HttpServletRequest pRequest, String pImgElement) {
        HttpSession session = pRequest.getSession();
        String val = (String) session.getAttribute(pImgElement);
        return ClwApiCustomizer.getCustomizeImgElement(val);
    }

    public static Properties getUiProperties(HttpServletRequest pRequest)
    throws FileNotFoundException, IOException
    {
        Properties configProps = new Properties();
        String fileName = ClwCustomizer.getAbsFilePath(pRequest,"config.txt");
        if(Utility.isSet(fileName)) {
            FileInputStream configIS = new FileInputStream(fileName);
            configProps.load(configIS);
        }
        return configProps;
    }

    public static Object getJavaObject(HttpServletRequest pRequest, String pFullClassName)
    throws Exception
    {
        HttpSession session = pRequest.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        StoreData storeD = appUser.getUserStore();
        AccountData accountD = appUser.getUserAccount();
        int storeId = 0;
        int accountId = 0;
        if(storeD!=null) storeId = storeD.getBusEntity().getBusEntityId();
        if(accountD!=null) accountId = accountD.getBusEntity().getBusEntityId();
        String key = storeId+"@"+accountId+"@"+pFullClassName;
        Object javaObj =  session.getAttribute(key);
        if(javaObj==null) {
            String path = "";
            PropertyData storePrefixPD = storeD.getPrefix();
            if(storePrefixPD!=null) {
                path = Utility.strNN(storePrefixPD.getValue());
            }
            String accountDir = accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_FOLDER);
            if(Utility.isSet(accountDir)) {
                path += "/"+accountDir;
            }
            path = path.replace('\\','/').toLowerCase();
            String[] pathDirA = Utility.parseStringToArray(path, "/");
            String pathInc = null;
            for(int ii=0; ii<pathDirA.length; ii++) {
                String ss = pathDirA[ii];
                if(Utility.isSet(ss)) {
                    if(pathInc==null) {
                        pathInc = "."+ss;
                    } else {
                        pathInc += "."+ss;
                    }
                    pathDirA[ii] = pathInc;
                } else {
                    pathDirA[ii] = null;
                }
            }

            int ll = pFullClassName.lastIndexOf(".");
            String defaultPath = pFullClassName.substring(0,ll);
            String className = pFullClassName.substring(ll);
            Class clazz = null;
            for(int ii=pathDirA.length-1; ii>=0; ii--) {
                if(pathDirA[ii]!=null) {
                    String fullClassNameTest = defaultPath + pathDirA[ii] + className;
                    log.debug("ClwCustomizer: attempting to find class " + fullClassNameTest);
                    try {
                        clazz = Class.forName(fullClassNameTest);
                        if(clazz!=null) {
                            break;
                        }
                    } catch(Exception exc) {
                    	log.debug("ClwCustomizer: failed to find class " + fullClassNameTest);
                    }
                }
            }
            if(clazz==null) {
            	log.debug("ClwCustomizer: attempting to find class : " + pFullClassName);
                clazz = Class.forName(pFullClassName);
            }
            if(clazz!=null) {
                javaObj = clazz.newInstance();
                 session.setAttribute(key, javaObj);
            }
        }
        return javaObj;
    }

    public static String getTemplateImgRelativePath() {
        return getImgRelativePath() + "/tmp/";
    }

    public static String getImgRelativePath() {
        String pStoreDir = getStoreDir();
        if (pStoreDir.equals("def")) {
            pStoreDir = "defst";
        }
        return "/" + pStoreDir + "/en/images/";
    }

    public static void buildMenuStr(MenuItemView item, StringBuffer menuContent,String uid,int level) {
        if (item.getSubItems() != null && !item.getSubItems().isEmpty()) {
            Iterator it = item.getSubItems().iterator();
            level++;
            String displayStatus = item.getDisplayStatus();
            String ulClassName=null;
           if (MenuItemView.DISPLAY_STATUS.OPEN.equals(displayStatus) || Constants.ROOT.equals(item.getKey())) {
                ulClassName = "openelemBlocked";
            } else if (MenuItemView.DISPLAY_STATUS.CLOSE.equals(displayStatus)) {
                ulClassName = "closeelem";
            }

            menuContent.append("<");
            menuContent.append("table");
            menuContent.append(Utility.isSet(item.getKey()) ? " id="+Utility.strNN(uid) + item.getKey() : "");
            menuContent.append(Utility.isSet(ulClassName) ? " class=" + ulClassName : "");
            menuContent.append(">");

            while (it.hasNext()) {

                MenuItemView menuItem = (MenuItemView) it.next();

                String displayStatusChild = menuItem.getDisplayStatus();
                String liClassName = null;
                if (MenuItemView.DISPLAY_STATUS.OPEN.equals(displayStatusChild)) {
                    liClassName = "openliBlocked";
                } else if (MenuItemView.DISPLAY_STATUS.CLOSE.equals(displayStatus)) {
                    liClassName = "closeli";
                }

                menuContent.append("<"); //open 'li'
                menuContent.append("tr");
                menuContent.append(Utility.isSet(menuItem.getKey())?" id=child"+Utility.strNN(uid)+menuItem.getKey():"");
                menuContent.append(Utility.isSet(liClassName) ? " class=" + liClassName : "");
                menuContent.append(">");//close 'li'
                menuContent.append("<td class=categName>");
                menuContent.append("<");//open 'a'
                menuContent.append("a class=\"categorymenulevel_");
                menuContent.append(level>3?3:level);
                menuContent.append(MenuItemView.DISPLAY_STATUS.OPEN.equals(displayStatusChild)?"_block":"");
                menuContent.append("\"");
                if(menuItem.getSubItems()!=null && !menuItem.getSubItems().isEmpty()) {
                    menuContent.append(Utility.isSet(menuItem.getKey()) ? " href=\""+(Utility.isSet(menuItem.getLink())?menuItem.getLink():"#")+"\" onMouseOver=\"javascript:openTreeLevel("+"child"+Utility.strNN(uid)+menuItem.getKey()+"," +Utility.strNN(uid)+ menuItem.getKey() + ","+Utility.strNN(uid)+item.getKey()+")\""+" onMouseOut=\"javascript:closeTreeLevel("+"child"+Utility.strNN(uid)+menuItem.getKey()+","+Utility.strNN(uid) + menuItem.getKey() + ","+Utility.strNN(uid)+item.getKey()+")\"" : "");
                } else{
                    menuContent.append(Utility.isSet(menuItem.getKey()) ? " href=\""+(Utility.isSet(menuItem.getLink())?menuItem.getLink():"#")+"\" onMouseOver=\"javascript:openItem("+"child"+Utility.strNN(uid)+menuItem.getKey()+","+Utility.strNN(uid)+item.getKey()+")\""+" onMouseOut=\"javascript:closeItem("+"child"+Utility.strNN(uid)+menuItem.getKey()+","+Utility.strNN(uid)+item.getKey()+")\"" : "");
                }
                menuContent.append(">"); //close 'a'

                menuContent.append(menuItem.getName());

                menuContent.append("</a>");

                buildMenuStr(menuItem, menuContent,uid,level);

                menuContent.append("</td>");
                menuContent.append("</tr>");
            }
            menuContent.append("</table>");
        }
    }

    public static void buildShopCatalogMenuStr(MenuItemView item, StringBuffer menuContent, int level) {
        if (item.getSubItems() != null && !item.getSubItems().isEmpty()) {
            Iterator it = item.getSubItems().iterator();
            while (it.hasNext()) {

                MenuItemView menuItem = (MenuItemView) it.next();
                menuContent.append("&nbsp;&nbsp;");
                for (int i=0;i<level;i++) {
                    menuContent.append("&nbsp;&nbsp;");
                }

                if(menuItem.getSubItems()==null || menuItem.getSubItems().isEmpty()) {
                    menuContent.append("<a ");//open 'a'
                    menuContent.append("href=\"javascript:  document.forms[0].elements['navigateCategoryId'].value='");
                    menuContent.append(menuItem.getKey());
                    menuContent.append("'; document.forms[0].submit();\" >");
                }
                menuContent.append(menuItem.getName());
                if(menuItem.getSubItems()==null || menuItem.getSubItems().isEmpty()) {
                    menuContent.append("</a>");
                }
                menuContent.append("<br/>");

                buildShopCatalogMenuStr(menuItem, menuContent,(level+1));
            }
        }
    }    

}
