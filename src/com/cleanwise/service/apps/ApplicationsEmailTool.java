/*
 * ApplicationsEmailTool.java
 *
 * Created on March 4, 2003, 9:59 PM
 */

package com.cleanwise.service.apps;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.SearchTerm;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.eventsys.FileAttach;
import com.cleanwise.service.api.session.Event;
import com.cleanwise.service.api.session.Order;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.Attachment;
import com.cleanwise.service.api.value.EventData;
import com.cleanwise.service.api.value.EventEmailData;
import com.cleanwise.service.api.value.EventEmailDataView;
import com.cleanwise.service.api.value.EventEmailView;
import com.cleanwise.service.api.value.InboundEmailData;
import com.cleanwise.service.api.value.OrderPropertyData;
import com.cleanwise.service.api.value.UserInfoData;
import com.cleanwise.service.api.value.UserInfoDataVector;
import com.cleanwise.view.utils.Constants;

/**
 *Utility class for email functionality, including reading emails, creating attachments, and
 *sending email.  This class is invoked by the event system to send emails, but can also be invoked 
 *to send email without going through the EJB.  This is necessary when sending attachments which 
 *cannot be synchronized.  There is a way around this which would be to send up the raw byte[] data 
 *and then in the email tool tell it the type that it is (application/octet-stream), however the 
 *current version of the javax/mail package does not seem to correctly support this.  Never the less 
 *transferring large reports back and forth to the EJb container may not the best way to go anyway.
 * @author  bstevens
 */

public class ApplicationsEmailTool {
	
	private static final String EMAIL_JNDI_NAME = "java:Mail";
    private static final String ATTACHMENT = "attachment";
    private static final String INLINE = "inline";
    private static final String POP3 = "pop3"; // default protocol for e-mail reading
	private static final Logger log = Logger.getLogger(ApplicationsEmailTool.class);
    private static final int DEFAULT_ATTEMPT_FOR_EMAIL = 1;

    /** Creates a new instance of ApplicationsEmailTool */
    public ApplicationsEmailTool() {
    }

    /**
     *Provides command line support to send out an email message with attachments.  Arguments should take
     *the form of:
     *  subject to message atachment_1 attahcment_2 attachment_n
     *
     *If you wish to overide any of the following properties use -Dproperty_name=property_value
     *on the command line.  For example if you wish to use a different host other than localhost as
     *the smtp server:
     *java -Dmail.host=my_server
     *
     *The following can be overidden:
     *mail.transport.protocol defaults to: smtp
     *mail.host defaults to: localhost
     *mail.from defaults to: user name of user logged in (as determined by System property user.name)
     */
    public static void main(String[] args){
        if (args.length < 3){
            log.info("Usage:");
            log.info("java " + ApplicationsEmailTool.class.getName() + " subject to \"Message\" attachment1 attachment2 ...");
            return;
        }
        String lSubject = args[0];
        String lTo = args[1];
        String lMessage = args[2];
        if((lTo==null || lTo.trim().length() == 0)){
            log.info("Usage:");
            log.info("java " + ApplicationsEmailTool.class.getName() + " subject to \"Message\" attachment1 attachment2 ...");
            return;
        }

        File[] lAttachments = new File[args.length - 3];
        try{
            for(int i=3;i<args.length;i++){
                if(args[i]!=null && args[i].trim().length() > 0){
                    lAttachments[i-3] = new File(args[i]);
                }
            }
        }catch(Exception e){
            log.info("Problem Reading File: " + e.getMessage());
        }
        try{
            sendEmail(lTo, null, lSubject, lMessage, null, Constants.EMAIL_IMPORTANCE_NORMAL, lAttachments);
            log.info("Sent Success");
        }catch(Exception e){
            log.info("Problem Sending Email: " + e.getMessage());
        }
    }

    /**
     *Sends an email message with the given attachments, to the designated email address (may be comma
     *seperated list) with the given message and given subject.  The importance is defaulted to normal
     *This method will attempt to obtain the following properties from the System with the corosponding
     *defaults:
     *mail.transport.protocol::smtp
     *mail.host::localhost
     *mail.from::user name of user logged in (as determined by System property user.name)
     *@param pTo comma seperated list of desired recipients
     *@param pSubject subject of email
     *@param pMessage the message body
     */
    public static synchronized void sendEmail(String pTo, String pSubject, String pMessage)
            throws Exception {
        sendEmail(pTo, null, pSubject, pMessage, null, Constants.EMAIL_IMPORTANCE_NORMAL, null);
    }

    /**
     *Sends an email message with the given attachment, to the designated email address (may be comma
     *seperated list) with the given message and given subject.  The importance is defaulted to normal
     *This method will attempt to obtain the following properties from the System with the corosponding
     *defaults:
     *mail.transport.protocol::smtp
     *mail.host::localhost
     *mail.from::user name of user logged in (as determined by System property user.name)
     *@param pTo comma seperated list of desired recipients
     *@param pSubject subject of email
     *@param pMessage the message body
     *@param pAttachment the File to include as an attachment
     */
    public static synchronized void sendEmail(String pTo, String pSubject, String pMessage,
                                              File pAttachment) throws Exception {
        File[] attachments = new File[1];
        attachments[0] = pAttachment;
        sendEmail(pTo, null, pSubject, pMessage, null, Constants.EMAIL_IMPORTANCE_NORMAL, attachments);
    }


    /**
     *Sends an email message with the given attachments, to the designated email address (may be comma
     *seperated list) with the given message and given subject.  This method will attempt to obtain
     *the following properties from the System with the corosponding defaults:
     *mail.transport.protocol::smtp
     *mail.host::localhost
     *mail.from::user name of user logged in (as determined by System property user.name)
     *@param pTo comma seperated list of desired recipients
     *@param pSubject subject of email
     *@param pMessage the message body
     *@param pAttachments array of Files to include as attachments
     *@param pImportance the string value of the importance.  @see IMPORTANCE_HIGH, @see IMPORTANCE_NORMAL, @see IMPORTANCE_LOW
     */
    public static synchronized void sendEmail(String pTo, String pSubject, String pMessage,
                                              File[] pAttachments, String pImportance)
        throws Exception {
        sendEmail(pTo, null, pSubject, pMessage, pImportance, null, pAttachments);
    }

    public static synchronized void sendEmail(String pTo, String pCc, String pSubject, String pMessage,
                                              String pFrom, String pImportance) throws Exception {
        sendEmail(pTo, pCc, pSubject, pMessage, pFrom, pImportance, null);
    }

    public static synchronized void sendEmail(String pTo, String pCc, String pSubject, String pMessage,
                                               String pFrom, String pImportance, File[] pAttachments)
            throws Exception {
        /*
         *Creates a multipart MimeMessage with one part being the file to attach
         *if it is supplied, and the other part being the message text.
         */
    	log.info("TOADDRESS ====== "+pTo+" CCADRRESS ======== "+pCc);
		String host = System.getProperty("mail.host","localhost");
    	
		//get a mail session.  First try to use JNDI to retrieve a session, and if that fails
		//then use the default instance of the Session class.
		Session lSession = null;
    	try {
            lSession = getSession();
    	}
    	catch (Exception e) {
    		Properties lMailProps = new Properties();
    		String protocol = System.getProperty("mail.transport.protocol","smtp");
    		lMailProps.setProperty("mail.transport.protocol",protocol);
    		lMailProps.setProperty("mail.host",host);
	        lSession = Session.getDefaultInstance(lMailProps,null);
        }
    	
    	//create a message from the session
        MimeMessage msg = new MimeMessage(lSession);

        //populate the recipients list from the value passed in
        StringTokenizer tok = new StringTokenizer(pTo,",;");
        ArrayList<InternetAddress> toList = new ArrayList<InternetAddress>();
        while(tok.hasMoreElements()){
            toList.add(new InternetAddress(tok.nextElement().toString()));
        }
        InternetAddress[] to = new InternetAddress[toList.size()];
        to = (InternetAddress[]) toList.toArray(to);
        msg.setRecipients(MimeMessage.RecipientType.TO,to);

        //populate the CC list from the value passed in
        if (pCc != null && pCc.length() > 0) {
          tok = new StringTokenizer(pCc, ",;");
          toList = new ArrayList<InternetAddress>();
          while (tok.hasMoreElements()) {
            toList.add(new InternetAddress(tok.nextElement().toString()));
          }
          InternetAddress[] cc = new InternetAddress[toList.size()];
          cc = (InternetAddress[]) toList.toArray(cc);
          msg.setRecipients(MimeMessage.RecipientType.CC, cc);
        }

        //determine and populate the "From" value
		String mailFrom = pFrom;
		//if no from value was passed in, see if a value was specified for the mail session
		//(set in mail-services.xml)
		if (mailFrom == null || mailFrom.trim().length()==0 || mailFrom.indexOf("@")<0) {
			mailFrom = lSession.getProperty("mail.from");
		}
		//if no value was set in the mail session, see if a value was specified as a system
		//property.  If not, default to a value we build.
		if (mailFrom == null || mailFrom.trim().length()==0 || mailFrom.indexOf("@")<0) {
			String user = System.getProperty("user.name");
			if(user==null || user.trim().length()==0) {
				user = "noreply";
			}
			if(user.indexOf("@")<0){
				user = user+"@"+host;
			}
			mailFrom = System.getProperty("mail.from",user);
		}
        InternetAddress fromAddr = new InternetAddress(mailFrom);
        msg.setFrom(fromAddr);

        //determine and populate the Importance value
        if (pImportance == null || pImportance.length() == 0 ||
          !(pImportance.equals(Constants.EMAIL_IMPORTANCE_HIGH) ||
            pImportance.equals(Constants.EMAIL_IMPORTANCE_NORMAL) ||
            pImportance.equals(Constants.EMAIL_IMPORTANCE_LOW))){
          pImportance = Constants.EMAIL_IMPORTANCE_NORMAL;
        }
        msg.setHeader("Importance", pImportance);

        //populate the subject
        msg.setSubject(pSubject,"UTF-8");

        //populate the contents, depending upon if there are attachments or not
        if(pAttachments == null || pAttachments.length == 0) {
            if(pMessage.toLowerCase().indexOf("<html")>=0) {
            	msg.setContent(pMessage,Constants.EMAIL_FORMAT_HTML + "; charset=UTF-8");
            }
            else {
            	msg.setText(pMessage, "UTF-8");
            }
        }
        else {
            BodyPart msgTxt = new MimeBodyPart();
            Multipart parts = new MimeMultipart();
            parts.addBodyPart(msgTxt);
            msg.setContent(parts);
            //if this message is html then set it as such, otherwise assume a text message
            if(pMessage == null){
                pMessage = "";
            }
            if(pMessage.toLowerCase().indexOf("<html")>=0){
            	//below does not work with international character sets.  Needs to be fixed.
                msgTxt.setContent(pMessage,"text/html; charset=UTF-8");
            }else{
                msgTxt.setText(pMessage);
            }

            for(int i=0;i<pAttachments.length;i++) {
            	BodyPart msgFile = new MimeBodyPart();
            	parts.addBodyPart(msgFile);
            	DataSource lAttachSource = new FileDataSource(pAttachments[i]);
            	DataHandler lAttach = new DataHandler(lAttachSource);
            	msgFile.setDataHandler(lAttach);
            	msgFile.setFileName(pAttachments[i].getName());
            }
        }

        //send the message
        Transport.send(msg);
    }

    public static String getEmailContacts(UserInfoDataVector contactUsers) {
        Iterator it = contactUsers.iterator();
        StringBuffer emailContacts = new StringBuffer();
        int i = 0;
        while (it.hasNext()) {

            UserInfoData userInfo = (UserInfoData) it.next();
            String address = userInfo.getEmailData().getEmailAddress();
            if (address != null) {
                emailContacts.append(((i) > 0 ? "," : ""));
                emailContacts.append(address);
            }
            i++;
        }
        return emailContacts.toString();
    }

    /**
     *Returns the email address of the local user.
     */
    public static InternetAddress getLocalEmailAddress()
        throws java.rmi.RemoteException {
        try{
            Session session = getSession();
            return InternetAddress.getLocalAddress(session);
        }catch(Exception e){
            try{
                return new InternetAddress(System.getProperty("user.name"));
            }catch(Exception e2){
                throw new RemoteException(e.getMessage());
            }
        }
    }

    public ArrayList<InboundEmailData> readEmails(String protocol,
                                                  String host,
                                                  String user,
                                                  String password,
                                                  SearchTerm searchTerm,
                                                  boolean clear) throws NoSuchProviderException,MessagingException,IOException{

        log.info("readEmails => BEGIN.");
        ArrayList<InboundEmailData> emails = new ArrayList<InboundEmailData>();


        //parameters of connection and the server address
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props);

        //default setting
        if(!Utility.isSet(protocol)){
            protocol = POP3;
        }

        log.info("readEmails => protocol: " + protocol + " ,host:" + host + " ,user:" + user + " ,password:xxx ");

        //the abstract class "store" for connection with a server
        // and access to folders with letters and etc.
        Store store = session.getStore(protocol);

        // connection
        log.info("readEmails => connecting to " + host + ".");
        store.connect(host, user, password);

        log.info("readEmails => getting INBOX folder.");
        Folder folder = store.getDefaultFolder().getFolder("INBOX");
        if(!folder.exists()){
             throw new MessagingException("No INBOX folder found...this should not happen broken compatibility with this mail server maybe");
        }
        folder.open(Folder.READ_WRITE);

        log.info("readEmails => searching messages in a folder :" + folder + ".");

        // get all messages or search if search term was set
        Message messages[];
        if (searchTerm != null) {
            log.debug("search term specified filtering");
            messages = folder.search(searchTerm);
        } else {
            messages = folder.getMessages();
        }

        log.info("readEmails => " + messages.length + " messages found.");

        // read if messages was found
        if (messages.length > 0) {
            log.info("readEmails => reading...");
            for (Message aMessage : messages) {
                InboundEmailData eData = new InboundEmailData();
                eData.setFrom(MimeUtility.decodeText(Utility.strNN(aMessage.getFrom()[0].toString())));
                eData.setSubject(MimeUtility.decodeText(Utility.strNN(aMessage.getSubject())));
                log.info("found message: "+eData.getSubject());
                read(aMessage, eData);
                aMessage.setFlag(Flags.Flag.DELETED, true);
                emails.add(eData);
            }
            log.info("readEmails => Ok!");
        }

        folder.close(clear);



        log.info("readEmails => END.");

        return emails;

    }

    private String read(Part p, InboundEmailData eData) throws MessagingException, IOException {

    	log.info("Content type="+p.getContentType());
        if (p.isMimeType("text/*")) {
        	log.info("in text/*");
        	log.info(p.getClass().getName());
            String s = (String) p.getContent();
            eData.setText(s);
            log.info(s);
            try{
	            log.info("filename="+p.getFileName());
	            log.info("description="+p.getDescription());
	            log.info("disposition="+p.getDisposition());
	            if(p.getInputStream() != null){
	            	log.info("Input stream was NOT null");
	            	try{
	            		log.info(IOUtilities.loadStream(p.getInputStream()));
	            	}catch(Exception e){}
	            	
	            	try{
	                    String data = IOUtilities.loadStream(p.getDataHandler().getInputStream());
	                    log.info(data);
	            	}catch(Exception e){log.info("Caught error: "+e.getMessage());log.error("Error",e);}
	            	
	            	try{
	            		try{
		            		Enumeration headers = p.getAllHeaders();
		            		while(headers.hasMoreElements()){
		            			Header aHeader = (Header) headers.nextElement();
		            			log.info(aHeader.getName()+"::"+aHeader.getValue());
		            			
		            		}
	            		}catch(Exception e){
	            			log.info("Problem writting header debug messages");
	            		}
	            	}catch(Exception e){}
	            }else{
	            	log.info("Input stream was null");
	            }
	            
            }catch(Exception e){}
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
        	log.info("in multipart/alternative");
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;

            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                log.info("Body Part Content type="+bp.getContentType());
                if (bp.isMimeType(Constants.EMAIL_FORMAT_PLAIN_TEXT)) {
                    if (text == null) {
                        read(bp, eData);
                        text = eData.getText();
                    }
                } else if (bp.isMimeType(Constants.EMAIL_FORMAT_HTML)) {
                    read(bp, eData);
                    if (eData.getText() != null) {
                        return eData.getText();
                    }
                } else {
                    read(bp, eData);
                    return eData.getText();
                }
            }

            eData.setText(text);
            return eData.getText();

        } else if (p.isMimeType("multipart/*")) {
        	log.info("in multipart/*");

            Multipart mp = (Multipart) p.getContent();

            for (int i = 0; i < mp.getCount(); i++) {

                BodyPart bodyPart = mp.getBodyPart(i);
                //read(bodyPart, eData);
                
                log.info("Body Part Content type="+bodyPart.getContentType());
                if (isAttachment(bodyPart)) {

                    String fileName = bodyPart.getFileName();
                    //if there is no file name mentioned try the description.  This has happened in particular for
                    //inline attachments
                    if(fileName== null){
                       fileName = bodyPart.getDescription();
                    } 

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    BufferedInputStream bis = new BufferedInputStream(bodyPart.getInputStream());

                    int j;
                    do {
                        j = bis.read();
                        if (j != -1) {
                            baos.write(j);
                        }
                    } while (j != -1);

                    Attachment attachment = new Attachment();
                    attachment.setData(baos.toByteArray());
                    baos.flush();
                    baos.close();
                    attachment.setContentType(bodyPart.getContentType());
                    if (fileName != null) {
                        attachment.setFileName(MimeUtility.decodeText(fileName));
                    }else{
                    	fileName = "unknown";
                    }
                    log.info("fileName="+fileName);
                    
                    eData.getAttachments().add(attachment);
                    log.info("Adding attachmnet: "+eData.getAttachments().size()+" of size: "+attachment.getData().length);
                }  else {
                    if(eData.getText()==null){
                        eData.setText(read(bodyPart, eData));
                    }
                }
            }
            return eData.getText();
        }

        return null;
    }
    
    private static boolean isAttachment(BodyPart bodyPart) {
        if (bodyPart != null) {
            try {
            	log.info("BodyPart dispostion="+bodyPart.getDisposition());
                return 
                (ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || 
                		INLINE.equalsIgnoreCase(bodyPart.getDisposition()));
            } catch (MessagingException e) {
                return false;
            }
        } else {
            return false;
        }
    }
    
    private static Session getSession() throws Exception {
        Session returnValue = (Session) PortableRemoteObject.narrow(new InitialContext().lookup(ApplicationsEmailTool.EMAIL_JNDI_NAME), Session.class);
        return returnValue;
    }

    public File[] fromAttachsToFiles(FileAttach[] attachments) throws Exception {
        if (attachments != null) {
            File[] attachs = new File[attachments.length];
            for (int i = 0; i < attachments.length; i++) {
                log.info("fromAttachsToFiles => attachments["+i+"].getName(): "+attachments[i].getName());
                attachs[i]=attachments[i].fromAttachToFile();
            }
            return attachs;
        } else {
            return new File[0];
        }
    }

    public FileAttach[] fromFilesToAttachs(File[] files) throws IOException {
        FileAttach[] fileAttaches = new FileAttach[files.length];
        for(int i = 0; i<files.length; i++){
            File file = files[i];
            byte[] byteData = FileAttach.fromFileToByte(file);
            FileAttach fileAttache = new FileAttach(file.getName(), byteData, "", byteData.length);
            fileAttaches[i] = fileAttache;
        }

        return fileAttaches;
    }

    public FileAttach[] getFilesFromBytes(byte[] blob) throws SQLException {

        FileAttach[] attachments;

        try{
            attachments = (FileAttach[]) bytesToObject(blob);
        }catch(ClassCastException e){
            e.printStackTrace();
            attachments = new FileAttach[0];
        }
        return attachments;

    }

    private Object bytesToObject(byte[] pBytes) {
        Object obj = null;
        if(pBytes == null || pBytes.length==0){
        	return obj;
        }
        java.io.ByteArrayInputStream iStream = new java.io.ByteArrayInputStream(pBytes);
        try {
            java.io.ObjectInputStream is = new java.io.ObjectInputStream(iStream);
            obj = is.readObject();
            is.close();
            iStream.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return obj;
    }
    
    public EventEmailData prepareEventEmailData(String toAddress, String ccAddress, String subject, String message, File[]  attachments, String addBy) {

        FileAttach[] fileAttach;
        if(attachments!=null){
            try {
                fileAttach = new ApplicationsEmailTool().fromFilesToAttachs(attachments);
            } catch (IOException e) {
                e.printStackTrace();
                fileAttach = new FileAttach[0];
            }
        } else{
            fileAttach = new FileAttach[0];
        }

        EventEmailDataView eventEmailData = new EventEmailDataView();
        eventEmailData.setCcAddress(ccAddress);
        eventEmailData.setAttachments(fileAttach);
        eventEmailData.setToAddress(toAddress);
        eventEmailData.setSubject(subject);
        eventEmailData.setText(message);
        eventEmailData.setEventId(-1);
        eventEmailData.setEmailStatusCd(Event.STATUS_READY);
        eventEmailData.setModBy(addBy);
        eventEmailData.setAddBy(addBy);

        return eventEmailData;
    }

    public void createEvent(EventEmailDataView emailData) throws Exception {
        createEvent(emailData, DEFAULT_ATTEMPT_FOR_EMAIL, null);
    }

    public void createEvent(EventEmailDataView emailData, Integer orderId, String processName, String user) throws Exception {

        Order orderEjb = new APIAccess().getOrderAPI();
        OrderPropertyData prop = orderEjb.getEventOrderProperty(orderId.intValue(), processName);
        if (prop == null || prop.getValue() == null || prop.getValue().trim().length() == 0) {

            EventData eventData = createEvent(emailData, DEFAULT_ATTEMPT_FOR_EMAIL, user);
            log.info("createEvent() => For order ID=" + orderId.intValue() + " Event has been created by CleanWise at "+Calendar.getInstance().getTime());
            OrderPropertyData note = OrderPropertyData.createValue();
            note.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.EVENT);
            note.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
            note.setShortDesc(processName);
            note.setValue(String.valueOf(eventData.getEventId()));
            note.setOrderId(orderId.intValue());
            orderEjb.addNote(note);

        } else  {
            log.info("createEvent() => Order duplicated");
        }
    }
    
    private EventData createEvent(EventEmailDataView emailData, int attempt, String user) throws Exception {

        log.info("createEvent() => BEGIN");
        APIAccess factory = new APIAccess();
        Event eventEjb = factory.getEventAPI();
        EventData eventData = EventData.createValue();
        eventData.setStatus(Event.STATUS_READY);
        eventData.setType(Event.TYPE_EMAIL);
        eventData.setAttempt(attempt);
        EventEmailView eev = new EventEmailView(eventData, emailData); 
        eventEjb.addEventEmail(eev, user);
        return eventData;
    }

}
