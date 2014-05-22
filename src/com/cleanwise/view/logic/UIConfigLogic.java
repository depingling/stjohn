
package com.cleanwise.view.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.session.Account;
import com.cleanwise.service.api.session.Content;
import com.cleanwise.service.api.session.PropertyService;
import com.cleanwise.service.api.session.Store;
import com.cleanwise.service.api.util.I18nUtil;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.AccountData;
import com.cleanwise.service.api.value.StoreData;
import com.cleanwise.service.api.value.UIConfigData;
import com.cleanwise.view.forms.UIConfigForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SessionTool;

/**
 *  <code>UIConfigLogic</code>
 *
 *@author     durval
 *@created    November 6, 2001
 */
public class UIConfigLogic {
	
	private static Logger log = Logger.getLogger(UIConfigLogic.class);

    /**
     *  Get the ui settings for the business entity being configured.
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void init(HttpServletRequest request,
            ActionForm form)
             throws Exception {
        UIConfigForm sForm = new UIConfigForm();
        fetchUiSettings(request, form);
        return;
    }

    /**
     *  Get the ui settings for the business entity being configured.
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void fetchUiSettings(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        // Reset the form information.
        UIConfigForm sForm = (UIConfigForm) form;
        String localeCd = sForm.getLocaleCd();

        sForm = new UIConfigForm();
        if(Utility.isSet(localeCd)) sForm.setLocaleCd(localeCd);

        int BusEntityId = 0;
        String s = (String)
                request.getSession().getAttribute("BusEntityId");
        if (null != s) {
            BusEntityId = Integer.parseInt(s);
        }
        else {
            System.err.println("No business entity id. ");
            return;
        }

        APIAccess factory = APIAccess.getAPIAccess();
        PropertyService psBean = factory.getPropertyServiceAPI();
        UIConfigData uioptions = psBean.fetchUIConfigData(BusEntityId, localeCd);
  String [] cssfiles = null;
  try {
      cssfiles = listStyleSheets();
  }
  catch (Exception e) {
      System.err.println("css file list error: " + e);
  }

  if ( null == cssfiles ) {
      cssfiles = new String[1];
      cssfiles[0] = "---";
  }

        HttpSession session = request.getSession();
        session.setAttribute
      ("admin.css.filenames", cssfiles);

        sForm.setConfig(uioptions);
        session.setAttribute("UI_CONFIG_FORM", sForm);
        request.setAttribute("UI_CONFIG_FORM", sForm);
        return;
    }


    /**
     *  Description of the Method
     *
     *@param  pBusEntityId  Description of Parameter
     *@param  file          Description of Parameter
     *@return               Description of the Returned Value
     */
    public static String uploadFile(String pStart,
                                    FormFile file,
            HttpServletRequest request ) {

        // Don't know any other way to discern if the file exists
        // or is readable, or some other problem
        if (file.getFileSize() == 0) {
            return "";
        }

        String newfilename = pStart + file.getFileName();
        String fileName = System.getProperty("webdeploy") + "/en/images/" + newfilename;
  String relativeFilename = "./en/images/" + newfilename;

        try {
            //retrieve the file data
            InputStream stream = file.getInputStream();
            OutputStream bos = new FileOutputStream(fileName);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();

            //close the stream
            stream.close();
        }
        catch (IOException ioe) {
            System.err.println("UIConfigLogic.uploadFile: error: " +
                    ioe.getMessage());
            return "";
        }

        // clear the contents???
        file.destroy();

  try {
      // Update the content log.
      SessionTool st = new SessionTool(request);
      String host = st.getInternalHostToken();
      APIAccess factory = (APIAccess)request.getSession().getAttribute(Constants.APIACCESS);
      Content cont = factory.getContentAPI();
      cont.addContent(host, relativeFilename);
  } catch (Exception e) {
      e.printStackTrace();
  }

        return newfilename;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void saveUiSettings(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        saveSettings(request, form);
        fetchUiSettings(request, form);
        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void previewUiSettings(HttpServletRequest request,
            ActionForm form)
             throws Exception {

        UIConfigForm sForm = (UIConfigForm) form;
        request.getSession().setAttribute("ui.preview.form", sForm);

        int busEntityId = 0;
        String s = (String)
                request.getSession().getAttribute("BusEntityId");
        if (null != s) {
            busEntityId = Integer.parseInt(s);
        }
        else {
            System.err.println("No business entity id. ");
            return;
        }

        sForm.setBusEntityId(busEntityId);
        final int userId = 0;//(sForm.getUserInfo() == null) ? 0 : sForm
//				.getUserInfo().getUserId();
        CleanwiseUser appuser = (CleanwiseUser) request.getSession().
                getAttribute(Constants.APP_USER);
        if (null == appuser) {
            log.info
    ("Preview ui config data for, no appuser found.");
            return;
        }
        APIAccess factory = APIAccess.getAPIAccess();

        int storeid = 0;
  String s2 = (String)
      request.getSession().getAttribute("Store.id");
  if (null != s) {
      storeid = Integer.parseInt(s2);
  }

        String t = (String)
                request.getSession().getAttribute("BusEntityType");
        if (null == t) {
            // Assume the we are previewing a store.
            t = "store";
        }
        else if (t.equals("account")) {
            // We are previewing an account, in which case we need to get
            // the store settings first.
            Account aBean = factory.getAccountAPI();
            AccountData ad = aBean.getAccount(busEntityId, 0);
            appuser.setUserAccount(ad);
      LogOnLogic.getUIConfigStore(storeid, appuser, userId, request);
        }

        if (storeid > 0) {
            // Get the store.
            Store sBean = factory.getStoreAPI();
            StoreData sd = sBean.getStore(storeid);
            appuser.setUserStore(sd);
            request.getSession().setAttribute("pages.store.prefix",
                    sd.getPrefix().getValue());
            request.getSession().setAttribute("pages.store.locale",
                    sd.getBusEntity().getLocaleCd());

        }
        if (t.equals("account")) {
      LogOnLogic.getUIConfigAccount(busEntityId, appuser, userId, request);
  } else {
      LogOnLogic.getUIConfigStore(storeid, appuser, userId, request);
  }
        LogOnLogic.fetchCustomProperties(request.getSession(), appuser);

        // Set the properties from the form.
        UIConfigData uioptions = sForm.getConfig();
        /*
        java.io.ByteArrayOutputStream oStream = new java.io.ByteArrayOutputStream();
        java.io.ObjectOutputStream os = new java.io.ObjectOutputStream(oStream);
        os.writeObject(sForm.getConfig());
        os.flush();
        os.close();
        byte[] byteImage = oStream.toByteArray();
        oStream.close();
        java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(byteImage);
        java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
        UIConfigData uioptions = (UIConfigData) is.readObject();
        is.close();
        iStream.close();
        */
        uioptions.setFooterMsg(I18nUtil.getUtf8Str(uioptions.getFooterMsg()));
        uioptions.setHomePageButtonLabel(I18nUtil.getUtf8Str(uioptions.getHomePageButtonLabel()));
        uioptions.setMainMsg(I18nUtil.getUtf8Str(uioptions.getMainMsg()));
        uioptions.setPageTitle(I18nUtil.getUtf8Str(uioptions.getPageTitle()));
        uioptions.setTipsMsg(I18nUtil.getUtf8Str(uioptions.getTipsMsg()));
        FormFile l1File = sForm.getLogo1ImageFile();
        String l1Name;
        if (l1File != null && !l1File.getFileName().equals("")) {
            String fn = "logo1-" + String.valueOf(busEntityId) + "-" +
                String.valueOf(System.currentTimeMillis()) + "-";
            l1Name = uploadFile(fn, l1File, request);
            if (l1Name.length() > 0) {
                uioptions.setLogo1(l1Name);
            }
        }

        FormFile l2File = sForm.getLogo2ImageFile();
        String l2Name;
        if (l2File != null && !l2File.getFileName().equals("")) {
            String fn = "logo2-" + String.valueOf(busEntityId) + "-" +
                String.valueOf(System.currentTimeMillis()) + "-";
            l2Name = uploadFile(fn, l2File, request);
            if (l2Name.length() > 0) {
                uioptions.setLogo2(l2Name);
            }
        }

        LogOnLogic.addUIConfigDataToSession(uioptions,request.getSession());

        return;
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    public static void useStoreUiSettings(HttpServletRequest request,
            ActionForm form)
             throws Exception {
        rmAccountSettings(request, form);
        fetchUiSettings(request, form);
        return;
    }


    /**
     *  Save the ui settings for the business entity being configured.
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void saveSettings(
            HttpServletRequest request,
            ActionForm form)
             throws Exception {
        UIConfigForm sForm =
                (UIConfigForm) request.getSession().getAttribute("ui.preview.form");

        if(sForm==null) {
            return;
        }

        UIConfigData d = sForm.getConfig();

        d.setFooterMsg(I18nUtil.getUtf8Str(d.getFooterMsg()));
        d.setHomePageButtonLabel(I18nUtil.getUtf8Str(d.getHomePageButtonLabel()));
        d.setMainMsg(I18nUtil.getUtf8Str(d.getMainMsg()));
        d.setPageTitle(I18nUtil.getUtf8Str(d.getPageTitle()));
        d.setTipsMsg(I18nUtil.getUtf8Str(d.getTipsMsg()));


        String localeCd = sForm.getLocaleCd();
        if(!Utility.isSet(localeCd)) localeCd = null;
        d.setLocaleCd(localeCd);
        int busEntityId = sForm.getBusEntityId();


        APIAccess factory = APIAccess.getAPIAccess();
        PropertyService psBean = factory.getPropertyServiceAPI();
        psBean.updateUIConfig(busEntityId, d);
    }


    /**
     *  Description of the Method
     *
     *@param  request        Description of Parameter
     *@param  form           Description of Parameter
     *@exception  Exception  Description of Exception
     */
    private static void rmAccountSettings(
            HttpServletRequest request,
            ActionForm form)
             throws Exception {

        UIConfigForm sForm = (UIConfigForm) form;
        UIConfigData d = sForm.getConfig();
        int id = sForm.getBusEntityId();
        if (id == 0) {
            return;
        }

        APIAccess factory = APIAccess.getAPIAccess();
        PropertyService psBean = factory.getPropertyServiceAPI();
        psBean.removeUIConfig(id);
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Returned Value
     */
    private static String[] listStyleSheets() {


        class CSSFileFilter implements FilenameFilter {

            /**
             *  Constructor for the CSSFileFilter object
             */
            public CSSFileFilter() {
                return;
            }


            /**
             *  Description of the Method
             *
             *@param  pDir       Description of Parameter
             *@param  pFilename  Description of Parameter
             *@return            Description of the Returned Value
             */
            public boolean accept(File pDir, String pFilename) {
                if (pFilename.endsWith(".css") || pFilename.endsWith(".CSS")) {
                    return true;
                }
                return false;
            }
        }

        File cssDir = new File(System.getProperty("webdeploy") + "/externals/");
        return cssDir.list(new CSSFileFilter());
    }

}


