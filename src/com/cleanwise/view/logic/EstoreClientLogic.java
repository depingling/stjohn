/*
 * EstoreClientLogic.java
 *
 * Created on October 11, 2002, 4:52 PM
 */

package com.cleanwise.view.logic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.MessageResources;
import org.apache.struts.upload.FormFile;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.util.*;
import com.cleanwise.view.utils.*;

import com.cleanwise.service.api.session.IntegrationServices;
import com.cleanwise.service.api.session.TradingPartner;
import com.cleanwise.service.api.session.PropertyService;

import com.cleanwise.service.api.util.IOUtilities;

import com.cleanwise.service.api.value.TradingPartnerData;
import com.cleanwise.service.api.value.OrderDataVector;
import com.cleanwise.service.api.value.OutboundEDIRequestDataVector;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.IdVector;

import java.io.*;
import java.util.jar.*;
import java.util.Properties;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.TimeZone;

import com.cleanwise.service.crypto.*;
import com.cleanwise.service.apps.dataexchange.*;

import com.cleanwise.view.forms.EstoreClientCommunicationForm;

import org.apache.log4j.*;

/**
 *
 * @author  bstevens
 */
public class EstoreClientLogic {
    private static final Logger log = Logger.getLogger(EstoreClientLogic.class);
    private static final int LOAD_ENCRYPTED_FILE = 0;
    private static final int LOAD_UNENCRYPTED_FILE = 1;
    private static final int RUN_LOADER_ONLY = 2;
    
    private static String kInterfaceVersion = "2.0";
    
    //utility method to set a property from the 
    private static void setProperty(PropertyService propServ, String prop,Properties encrptionProps)
    throws Exception{
        try{
            encrptionProps.setProperty(prop,propServ.getProperty(prop));
        }catch (Exception e){
            log.info("[WARNING] Property: "+prop+" not found in database");
        }
    }
    
    //retrieves the encryption properties from the database, this could just
    //as easily be from a file
    private static void populateEncryptionProperties(APIAccess factory,Properties encrptionProps)throws Exception{
        PropertyService propServ = factory.getPropertyServiceAPI();
        setProperty(propServ,CryptoPropNames.kAsymmetricAlgPropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kBlockModePropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kKeyStorefilePropName, encrptionProps);       
        setProperty(propServ,CryptoPropNames.kMessageDigestAlgPropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kPaddingPropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kAssociateAliasPropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kHostAliasPropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kSigningAlgPropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kSymmetricAlgPropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kSymmetricKeyLenPropName, encrptionProps);
        setProperty(propServ,CryptoPropNames.kProvider, encrptionProps);
        setProperty(propServ,CryptoPropNames.kProviderName, encrptionProps);
    }
    
    /**
     *Writes the properties to the given output stream.  This is the method that will need
     *changing if a file based system is desired as opposed to a dynamic generation
     *system.
     *If encryption is enabled the inputStream is assumed to be an EncryptionInputStream
     */
    private static void writePropertiesToStream
    (HttpServletRequest request,OutputStream pOut,InputStream pIn,boolean encrypt,
    PropertyService propServEjb,Properties pOrderFileProperties)
    throws Exception{
        if(encrypt){
            String keystorepass = propServEjb.getProperty("crypto.keystorepass");
            String keypass = propServEjb.getProperty("crypto.keypass");
            CryptoPackager cryptor = new CryptoPackager();
            cryptor.writeEncryptProps(pOut, (EncryptionInputStream)pIn, keystorepass, keypass,pOrderFileProperties);
        }else{
            pOrderFileProperties.store(pOut,"No Encryption");
        }
    }
    
    /**
     *Writes the orders to the given output stream.  This is the method that will need
     *changing if a file based system is desired as opposed to a dynamic generation
     *system.
     *returns true if anything is written to the output stream
     */
    private static InputStream writeOrdersToStream(HttpServletRequest request,
        OutputStream pOut,boolean encrypt,Properties pOrderFileProperties,OutboundEDIRequestDataVector orders)
    throws Exception{
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new IllegalStateException("Without APIAccess.");
        }
        TradingPartner tradingEjb = factory.getTradingPartnerAPI();
        IntegrationServices integrationServEjb = factory.getIntegrationServicesAPI();
        
        //create a writer to write the outbound "file" to
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        
        if(orders.size() > 0){
            //set up the translator
            OutboundTranslate trans = new OutboundTranslate(null,0,RefCodeNames.EDI_TYPE_CD.TORDER_PROCESSING_OUT,integrationServEjb,tradingEjb,writer);
            //translate the orders
            trans.translate(orders,null, 0,RefCodeNames.EDI_TYPE_CD.TORDER_PROCESSING_OUT);
            //convert the writer to a input stream 
            ByteArrayInputStream in = new ByteArrayInputStream(writer.toByteArray());
            
            //the following deals with the Encryption stuff
            if(encrypt){
                populateEncryptionProperties(factory,pOrderFileProperties);
                SecurityProvider.loadProvider(pOrderFileProperties.getProperty(CryptoPropNames.kProviderName),pOrderFileProperties.getProperty(CryptoPropNames.kProvider));
                CryptoPackager cryptor = new CryptoPackager();
                EncryptionInputStream eIn = cryptor.encryptToOutputStream (pOut,  in, pOrderFileProperties);
                return eIn;
            }else{
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                out.write(writer.toByteArray().toString().getBytes());
                out.writeTo(pOut);
                return in;
            }
        }else{
            return null;
        }
    }
    
    /**
     *Acknowledges the transaction that took place sometime earlier in the session
     */
    public static ActionErrors acknowledgeTransaction(HttpServletResponse response,HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            IntegrationServices integrationServicesEjb = factory.getIntegrationServicesAPI();
            OutboundEDIRequestDataVector orders = (OutboundEDIRequestDataVector) session.getAttribute("estore.communications.ordersBeingProcessed");
            if(orders == null || orders.size() == 0){
                errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError","No orders to acknowledge"));
                return errors;
            }
            IdVector orderIds = new IdVector();
            for(int i=0,len=orders.size();i<len;i++){
                OutboundEDIRequestData order = (OutboundEDIRequestData) orders.get(i);
                orderIds.add(new Integer(order.getOrderD().getOrderId()));
            }
            integrationServicesEjb.acknowledgeOrderTransaction(orderIds);
        }catch (Exception e){
            e.printStackTrace();
            errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Takes the request and streams out a jar file of orders.  Returns a populated
     *ActionErrors list if there were any errors.  The controlling Action Class should
     *check to see if the response has been commited already, or alternativly check
     *the action errors before redirecting.
     *<code>
     *  ActionErrors err = downloadOrders(response,request,form);
     *  if (err.size > 0){
     *      ...process errors
     *      ...preform redirect
     *  }else{
     *      return null;
     *  }
     *</code>
     */
    public static ActionErrors downloadOrders(HttpServletResponse response,HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (null == factory) {
                throw new Exception("Without APIAccess.");
            }
            IntegrationServices integrationServEjb = factory.getIntegrationServicesAPI();
            
            //if there have been any errors return them before streaming the response
            if(errors.size() > 0){
                return errors;
            }
            CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
            Date startTime = new Date();
            
            //sets various header values
            response.setContentType("application/jar");
            response.setHeader("Cleanwise-Interface-Version", kInterfaceVersion);
            response.setHeader("content-disposition","inline; filename="+constructOrderDownloadJarFileName(startTime.getTime()));
            response.setHeader("Cleanwise-File-Name",constructOrderDownloadJarFileName(startTime.getTime()));
            //create temporary output stream so this works in IE.
            //(IE, among others, requieres the size of the response to be sent
            //with the file, given that this is a dynamic file we do not know it
            //and although we could stream the file directly to the requestor
            //the workaround is to create the file in memory and then stream that out
            //to the client.
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //get the orders
            OutboundEDIRequestDataVector orders = integrationServEjb.getEDIOrdersByErpNumAndSetType(null,RefCodeNames.EDI_TYPE_CD.TORDER_PROCESSING_OUT,0);
            session.setAttribute("estore.communications.ordersBeingProcessed", orders);
            response.setHeader("Cleanwise-Order-Count",Integer.toString(orders.size()));
            if(orders.size()==0 || !createJarFile(out,request,startTime,orders)){
                return errors;
            }
            response.setHeader("Cleanwise-No-Orders","false");
            response.setContentLength(out.size());
            out.writeTo(response.getOutputStream());
            response.flushBuffer();
            response.getOutputStream().close();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Creates the jar file to stream to the client.  Returns true if a file was created
     *and false if not.  A file that is not created does not indicate an error, but
     *that there was nothing to put into the file.  If an error occurs the exception
     *is thrown.
     */
    private static boolean createJarFile(OutputStream out,HttpServletRequest request,Date startTime,OutboundEDIRequestDataVector orders)
    throws Exception
    {
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            throw new IllegalStateException("Without APIAccess.");
        }
        PropertyService propServEjb = factory.getPropertyServiceAPI();
        boolean encrypt = true;
        try{
            String val = propServEjb.getProperty("crypto.encryptionEnabled");
            if ("false".equalsIgnoreCase(val)){
                encrypt = false;
            }
        }catch (Exception e){
            log.info("[WARNING] encryptionEnabled property could not be found, defaulting "+
                "to encryption enabled");
        }
        JarOutputStream jos = new JarOutputStream(out);
        Manifest man = new Manifest();
        man.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        man.getMainAttributes().put(Attributes.Name.SPECIFICATION_VENDOR, "Cleanwise");
        DateFormat asGMT = DateFormat.getDateTimeInstance();
        asGMT.setTimeZone(TimeZone.getTimeZone("GMT"));

        Attributes attr = new Attributes();
        startTime.getTime();
        attr.put(new Attributes.Name("Date"), asGMT.format(startTime));
        man.getEntries().put(constructOrderDownloadFileName(startTime.getTime()), attr);
        man.getEntries().put(constructOrderDownloadFileName(startTime.getTime())+".properties", attr);

        //add the entry
        //holds any properties that need to be written to the jar
        Properties orderFileOptions = new Properties();
        JarEntry jent = new JarEntry(constructOrderDownloadFileName(startTime.getTime()));
        jos.putNextEntry(jent);
        InputStream in = writeOrdersToStream(request,jos,encrypt,orderFileOptions,orders);
        jos.flush();

        if (in == null){
            return false;
        }
        //add the properties file
        jent = new JarEntry(constructOrderDownloadFileName(startTime.getTime())+".properties");
        jos.putNextEntry(jent);
        writePropertiesToStream(request,jos,in,encrypt,propServEjb,orderFileOptions);

        jos.flush();
        jos.close();
        return true;
    }
    
    /**
     *Returns the name of the file that the client should save this download as.
     *This is passed over in a header that the client understands.
     */
    private static String constructOrderDownloadJarFileName(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyyHHmmss");
        String lDateTimeString = formatter.format(new Date( time ));
        return "orderpackage_" + lDateTimeString + ".jar";
    }
    
     /**
     *Returns the name of the file that the client should save this download as.
     *This is passed over in a header that the client understands.
     */
    private static String constructOrderDownloadFileName(long time){
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyyHHmmss");
        String lDateTimeString = formatter.format(new Date( time ));
        return "order_" + lDateTimeString + ".txt";
    }
    
    
    /**
     *Handles uploading the file and returns a handle to the uploaded file.
     *@param archEncDir the web form
     */
    private static File handleUpload(FormFile pFile,File archEncDir)
    throws Exception{
        File encryptedFile = IOUtilities.getAvailiableFileHandle(pFile.getFileName(), archEncDir, null);
        FileOutputStream fous = new FileOutputStream(encryptedFile);
        IOUtilities.loadWebFileToOutputStream(pFile,fous);
        return encryptedFile;
    }
    
    /**
     *Handles decrypting and unpackaging the jar file.  Returns an array of the unpackaged
     *files.
     *@param jarFile the encrypted jar file
     *@param encryptionProps the encryption properties as used by the cleanwise crypto libraries
     *@param toProcessDir the directory to decypt the files to
     *@param manEncDir the directory to put the jarFile in if there were problems
     *@param keystorepass keypass to use in decryption
     *@param keypass keystorepass to use in decryption
     */
    private static void handleDecryptingAndUnpackaging(File jarFile,Properties encryptionProps,
        File toProcessDir, File manEncDir, String keystorepass, String keypass)
    throws Exception{
        try{
            SecurityProvider.loadProvider(encryptionProps.getProperty(CryptoPropNames.kProviderName),encryptionProps.getProperty(CryptoPropNames.kProvider));
            CryptoPackager crypto = new CryptoPackager();
            if(!crypto.decryptAndUnpackage(keystorepass, keypass,encryptionProps,jarFile,toProcessDir)){
                throw new IOException("Something went wrong while decrypting files.  Check log.");
            }
        }catch(Exception e){
            //move the file to the manual intervention directory
            IOUtilities.moveFile(jarFile, manEncDir, null);
            throw e;
        }
    }
    
    /**
     *Handles loading any of the files in the supplied directory
     */
    private static void handleLoading(File toProcessDir,File archToProcessDir,File manDecryptDir,IntegrationServices integrationServEjb,TradingPartner tradingEjb)
    throws Exception{
        //InboundTranslate trans = new InboundTranslate(integrationServEjb,tradingEjb);
        // TODO: need to revisit the code
        //trans.translateDirectory(toProcessDir, integrationServEjb);
    }
    
    /**
     *Handles an encrypted file being uploaded by the client.
     */
    public static ActionErrors processEncryptedUpload(HttpServletResponse response,HttpServletRequest request, ActionForm pForm)
    {
        return processClientFile(response,request,pForm,LOAD_ENCRYPTED_FILE);
    }
        
     /**
     *Handles an encrypted file being uploaded by the client.
     */
    public static ActionErrors processUnencryptedUpload(HttpServletResponse response,HttpServletRequest request, ActionForm pForm)
    {
        return processClientFile(response,request,pForm,LOAD_UNENCRYPTED_FILE);
    }
    
    /**
     *Handles a request to run the loader.
     */
    public static ActionErrors processRunLoaderRequest(HttpServletResponse response,HttpServletRequest request, ActionForm pForm)
    {
        return processClientFile(response,request,pForm,RUN_LOADER_ONLY);
    }
    
    private static ActionErrors processClientFile(HttpServletResponse response,
        HttpServletRequest request, ActionForm pForm,
        int mode)
    {
        ActionErrors errors = new ActionErrors();
        EstoreClientCommunicationForm eForm = null;
        
        File archEncDir = null;
        File manEncDir = null;
        File manDecryptDir = null;
        File toProcessDir = null;
        File archToProcessDir = null;
        
        Properties encrptionProps = new Properties();
        
        String keystorepass = null;
        String keypass = null;
        
        TradingPartner tradingEjb = null;
        IntegrationServices integrationEjb = null;
        try{
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (factory == null) {
                throw new Exception("Without APIAccess.");
            }
            PropertyService propServ = factory.getPropertyServiceAPI();
            tradingEjb = factory.getTradingPartnerAPI();
            integrationEjb = factory.getIntegrationServicesAPI();
            eForm = (EstoreClientCommunicationForm) pForm;
            
            //load some of properties that the various utility methods need
            archEncDir = new File(propServ.getProperty("loader.archiveDirectoryEncrypted"));
            toProcessDir = new File(propServ.getProperty("loader.toProcessDirectoryDecrypted"));
            archToProcessDir = new File(propServ.getProperty("loader.archiveToProcessDirectoryDecrypted"));
            manEncDir = new File(propServ.getProperty("loader.manualInterventionDirectoryEncrypted"));
            manDecryptDir = new File(propServ.getProperty("loader.manualInterventionDirectoryDecrypted"));
            
            keystorepass = propServ.getProperty("crypto.keystorepass");
            keypass = propServ.getProperty("crypto.keypass");
            
            //make any of the directories that do not exsist
            createDirs(errors, archEncDir);
            createDirs(errors, manEncDir);
            createDirs(errors, manDecryptDir);
            createDirs(errors, toProcessDir);
            createDirs(errors, archToProcessDir);
            
            populateEncryptionProperties(factory, encrptionProps);
        }catch (Exception e){
            e.printStackTrace();
            errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        if(errors.size() > 0){
            return errors;
        }
            
        try{
            switch(mode){
                case LOAD_ENCRYPTED_FILE:
                    {
                        FormFile ff = eForm.getTheFile();
                        File workFile = handleUpload(ff,archEncDir);
                        handleDecryptingAndUnpackaging(workFile,encrptionProps,toProcessDir,manEncDir,keystorepass,keypass);
                        handleLoading(toProcessDir,archToProcessDir,manDecryptDir,integrationEjb,tradingEjb);
                        break;
                    }
                case LOAD_UNENCRYPTED_FILE:
                    {
                        FormFile ff = eForm.getTheFile();
                        File workFile = handleUpload(ff,toProcessDir);
                        handleLoading(toProcessDir,archToProcessDir,manDecryptDir,integrationEjb,tradingEjb);
                        break;
                    }
                case RUN_LOADER_ONLY:
                    {
                        handleLoading(toProcessDir,archToProcessDir,manDecryptDir,integrationEjb,tradingEjb);
                        break;
                    }
                default:
                    {
                        throw new IllegalStateException("mode: "+ mode + " not supported");
                    }
            }   
        }catch (Exception e){
            e.printStackTrace();
            errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Retrieves a list of all the files and puts them into a 
     *object that the presentation layer can display.
     */
    public static ActionErrors initRequest(HttpServletResponse response,HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            APIAccess factory = (APIAccess)session.getAttribute(Constants.APIACCESS);
            if (factory == null) {
                throw new Exception("Without APIAccess.");
            }
            PropertyService propServ = factory.getPropertyServiceAPI();

            //load some of properties that the various utility methods need
            File archEncDir = new File(propServ.getProperty("loader.archiveDirectoryEncrypted"));
            File toProcessDir = new File(propServ.getProperty("loader.toProcessDirectoryDecrypted"));
            File manEncDir = new File(propServ.getProperty("loader.manualInterventionDirectoryEncrypted"));
            File manDecryptDir = new File(propServ.getProperty("loader.manualInterventionDirectoryDecrypted"));
            File archToProcessDir = new File(propServ.getProperty("loader.archiveToProcessDirectoryDecrypted"));

            session.setAttribute("archiveDirectoryEncrypted",archEncDir.listFiles());
            session.setAttribute("archiveToProcessDirectoryDecrypted", archToProcessDir.listFiles());
            session.setAttribute("toProcessDirectoryDecrypted",toProcessDir.listFiles());
            session.setAttribute("manualInterventionDirectoryEncrypted",manEncDir.listFiles());
            session.setAttribute("manualInterventionDirectoryDecrypted",manDecryptDir.listFiles());
        }catch(Exception e){
            e.printStackTrace();
            errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    /**
     *Spools the requested file to the client.  The file must be in the seesion so the user
     *cannot request arbitary files off of the server.
     */
    public static ActionErrors downloadFile(HttpServletResponse response,HttpServletRequest request, ActionForm pForm){
        ActionErrors errors = new ActionErrors();
        try{
            HttpSession session = request.getSession();
            try{
                int index = Integer.parseInt((String) request.getParameter("index"));
                File[] fileList = (File[]) session.getAttribute((String) request.getParameter("type"));
                if(index > fileList.length){
                    errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError","index must be valid"));
                    return errors;
                }
                response.setHeader("content-disposition","inline; filename="+fileList[index].getName());
                OutputStream out = response.getOutputStream();
                InputStream in = new FileInputStream(fileList[index]);                
                IOUtilities.copyStream(in,out);
                
            }catch(NumberFormatException e){
                errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError","index must be numeric"));
            }
        }catch(Exception e){
            errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError",e.getMessage()));
        }
        return errors;
    }
    
    //utility method to create a directory and add an action error if it failed
    private static void createDirs(ActionErrors errors, File theFile){
        if (!theFile.exists() && !theFile.isDirectory()){
            if(!theFile.mkdirs()){
                String lDirCreateErrorMsg = "could not create directory::" + theFile.getAbsolutePath();
                errors.add("EstoreClientLogic",new ActionError("error.simpleGenericError",lDirCreateErrorMsg));
            }
        }
    }
}
