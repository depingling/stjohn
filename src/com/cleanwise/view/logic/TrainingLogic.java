package com.cleanwise.view.logic;

import java.util.List;
import java.util.Vector;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Properties;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.TrainingForm;
import com.cleanwise.view.forms.NoteMgrForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.rmi.RemoteException;
import java.sql.Blob;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.io.*;
import java.net.*;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;


import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class TrainingLogic {

    public static ActionErrors init(HttpServletRequest request,
			    TrainingForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      String section = request.getParameter("section");
      if(section!=null && !"how".equals(section)) {
        return ae;
      }
      //Get current Account
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
      AccountData accountD = appUser.getUserAccount();
      int accountId = accountD.getBusEntity().getBusEntityId();

      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      Note noteEjb = factory.getNoteAPI();

      int howToCleanTopicId = pForm.getHowToCleanTopicId();
      if(howToCleanTopicId<=0) {
        PropertyDataVector topicDV =
           noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);
        for(Iterator iter=topicDV.iterator(); iter.hasNext();) {
          PropertyData pD = (PropertyData) iter.next();
          if("How to Clean".equalsIgnoreCase(pD.getValue())) {
            howToCleanTopicId = pD.getPropertyId();
            break;
          }
        }
      }
      if(howToCleanTopicId<=0) {
        return ae;
      }
      pForm.setHowToCleanTopicId(howToCleanTopicId);

      //Get account notes for the topic
      NoteViewVector noteVwV =
            noteEjb.getNoteTitles(howToCleanTopicId, accountId, new ArrayList(), false);
      noteVwV = sortByTitle(noteVwV);

      pForm.setNoteList(noteVwV);

      return ae;
    }




    private static NoteViewVector sortByTitle(NoteViewVector pNoteVwV)
    {
      if(pNoteVwV==null || pNoteVwV.size()<=1){
        return pNoteVwV;
      }
      Object[] nVwA = pNoteVwV.toArray();
      for(int ii=0; ii<nVwA.length-1; ii++){
        boolean exitFl = true;
        for(int jj=0; jj<nVwA.length-ii-1; jj++){
          NoteView nVw1 = (NoteView) nVwA[jj];
          NoteView nVw2 = (NoteView) nVwA[jj+1];
          String t1 = nVw1.getTitle();
          if(t1==null) t1 = "";
          String t2 = nVw2.getTitle();
          int comp = t1.compareToIgnoreCase(t2);
          if(comp>0){
            nVwA[jj] = nVw2;
            nVwA[jj+1] = nVw1;
            exitFl = false;
          }
        }
        if(exitFl) break;
      }
      NoteViewVector nVwV = new NoteViewVector();
      for(int ii=0; ii<nVwA.length; ii++){
        NoteView nVw = (NoteView) nVwA[ii];
        nVwV.add(nVw);
      }
      return nVwV;

    }

    //-------------------------------------------------------------------------------------
    public static ActionErrors readNote(HttpServletRequest request, TrainingForm pForm)
    throws Exception
    {
      ActionErrors ae = new ActionErrors();
      String noteIdS = request.getParameter("noteId");
      if(!Utility.isSet(noteIdS)) {
        String mess = "No document requested";
        ae.add("error",new ActionError("error.simpleGenericError",mess));
        return ae;
      }
      int noteId = 0;
      try{
        noteId = Integer.parseInt(noteIdS);
      } catch(Exception exc) {
        String mess = "Wrong document id format: "+noteIdS;
        ae.add("error",new ActionError("error.simpleGenericError",mess));
        return ae;
      }
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      Note noteEjb = factory.getNoteAPI();
      NoteJoinView njVw = null;
      try {
        njVw = noteEjb.getNote(noteId);
      } catch (Exception exc) {}
      if(njVw==null){
        String mess = "Can't read requested document. Id: "+noteId;
        ae.add("error",new ActionError("error.simpleGenericError",mess));
        return ae;
      }
      pForm.setNote(njVw);
      return ae;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors downloadAttachment(HttpServletRequest request,
                HttpServletResponse response,
			    TrainingForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      String fileName = request.getParameter("file");
      String noteIdS = request.getParameter("noteId");
      int noteId = Integer.parseInt(noteIdS);
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
      AccountData accountD = appUser.getUserAccount();

      NoteJoinView njVw = accountD.getNote(noteId);
      NoteAttachmentDataVector naDV = njVw.getNoteAttachment();

      for(Iterator iter=naDV.iterator(); iter.hasNext();) {
        NoteAttachmentData naD = (NoteAttachmentData) iter.next();
        String fn = naD.getFileName();
        if(fn.equals(fileName)) {
          //Get my name
          String serverName = naD.getServerName();
          boolean localFl = true;
          if(Utility.isSet(serverName)) {
            try {
              InetAddress myInetAddress = InetAddress.getLocalHost();
              String myHostName = myInetAddress.getCanonicalHostName();
              if(!serverName.equalsIgnoreCase(myHostName)) {
                localFl = false;
              }
            } catch (UnknownHostException exc) {}
          }
          byte[] fileCont = null;
          String realFileName = noteId+"_"+fileName;
          if(localFl) { //local access
            String serverFileName = ClwCustomizer.getServerDir()+
                            "/xsuite/notes/"+
                            realFileName;

            // this is the absolute path where we will be writing

            //retrieve the file data
//            ByteArrayInputStream baos = new ByteArrayInputStream();
            File ioFile = new File(serverFileName);
            InputStream is = new FileInputStream(ioFile);
            int len = is.available();
            fileCont = new byte[len];
            is.read(fileCont);
            is.close();
          } else { //ftp request
            FTPClient ftp = ftpConnect(serverName);

            // set up passive ASCII transfers
            ftp.setConnectMode(FTPConnectMode.PASV);
            if(fileName.toLowerCase().endsWith(".txt")) {
               ftp.setType(FTPTransferType.ASCII);
            } else {
               ftp.setType(FTPTransferType.BINARY);
            }
            // get file from server
            fileCont = ftp.get(realFileName);

            // Shut down client
            ftp.quit();

          }

          //response.setContentType("image/gif");
          response.setContentType("application/octet-stream");
          //response.setHeader("extension", "gif");
          response.setHeader("Content-disposition", "attachment; filename="+fileName);

          OutputStream outputStream = response.getOutputStream();
          outputStream.write(fileCont);
          outputStream.flush();
          outputStream.close();
        }
      }
      return ae;
    }

    public static ActionErrors readAttachment(HttpServletRequest request,
            HttpServletResponse response,
		    TrainingForm pForm)
    			throws Exception {
  ActionErrors ae = new ActionErrors();
  HttpSession session = request.getSession();
  APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
  if (null == factory) {
	  throw new Exception("No APIAccess.");
  }
  String fileName = request.getParameter("file");
  String noteIdS = request.getParameter("noteId");
  int noteId = Integer.parseInt(noteIdS);
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
  AccountData accountD = appUser.getUserAccount();

  NoteJoinView njVw = accountD.getNote(noteId);
  NoteAttachmentDataVector naDV = njVw.getNoteAttachment();

  boolean foundF1 = false;
  for(Iterator iter=naDV.iterator(); iter.hasNext();) {
    NoteAttachmentData naD = (NoteAttachmentData) iter.next();
    String fn = naD.getFileName();
//    if(fn.equals(fileName)) {
      //Get my name
//      String serverName = naD.getServerName();
//      boolean localFl = true;
	  byte[] attachment = naD.getBinaryData();
//      if(Utility.isSet(serverName)) {
//        try {
//          InetAddress myInetAddress = InetAddress.getLocalHost();
//          String myHostName = myInetAddress.getCanonicalHostName();
//          if(!serverName.equalsIgnoreCase(myHostName)) {
//            localFl = false;
//          }
//        } catch (UnknownHostException exc) {}
//      }
//      byte[] fileCont = null;
//      String realFileName = noteId+"_"+fileName;
//      if(localFl) { //local access
//        String serverFileName = ClwCustomizer.getServerDir()+
//                        "/xsuite/notes/"+
//                        realFileName;
//
//        // this is the absolute path where we will be writing
//
//        //retrieve the file data
////        ByteArrayInputStream baos = new ByteArrayInputStream();
//        File ioFile = new File(serverFileName);
//        InputStream is = new FileInputStream(ioFile);
//        int len = is.available();
//        fileCont = new byte[len];
//        is.read(fileCont);
//        is.close();
//      } else { //ftp request
//        FTPClient ftp = ftpConnect(serverName);
//
//        // set up passive ASCII transfers
//        ftp.setConnectMode(FTPConnectMode.PASV);
//        if(fileName.toLowerCase().endsWith(".txt")) {
//           ftp.setType(FTPTransferType.ASCII);
//        } else {
//           ftp.setType(FTPTransferType.BINARY);
//        }
//        // get file from server
//        fileCont = ftp.get(realFileName);
//
//        // Shut down client
//        ftp.quit();
//
//      }
	    if((attachment!=null) && (fn.equals(fileName))) {
		      foundF1 = true;
		      response.setContentType("application/octet-stream");
		      response.setHeader("Content-disposition", "attachment; filename="+fileName);

	          OutputStream outputStream = response.getOutputStream();
                      outputStream.write(attachment);
		      outputStream.flush();
		      outputStream.close();
		 }
	  }
	  if (!foundF1) {
	      String message = "Can't find attachment file";
	      ae.add("error", new ActionError("error.simpleGenericError", message));
	  }
	  return ae;
	}
//      //response.setContentType("image/gif");
//      response.setContentType("application/octet-stream");
//      //response.setHeader("extension", "gif");
//      response.setHeader("Content-disposition", "attachment; filename="+fileName);
//
//      OutputStream outputStream = response.getOutputStream();
//      outputStream.write(fileCont);
//      outputStream.flush();
//      outputStream.close();
//    }
//  }
//  return ae;
//}

    private static int readFromBlob(Blob blob, OutputStream out) throws RemoteException {
        int read;
		try {
			InputStream in = blob.getBinaryStream();
			int length = -1;
			read = 0;
			int bBufLen = (int) blob.length();
			byte[] buf = new byte[bBufLen];
			while ((length = in.read(buf)) != -1) {
			    out.write(buf, 0, length);
			    read += length;
			}
			in.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RemoteException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RemoteException();
		}
        return read;
    }

    //Ftp connect
    private static FTPClient ftpConnect(String pServerName)
    throws Exception
    {
      //Properties clweanwiseProp = Utility.loadProperties("cleanwise.default.properties"); //YKR25
      String ftpUser = System.getProperty("notes.ftp.user"); //YKR25
      String ftpPassword = System.getProperty("notes.ftp.password"); //YKR25

      FTPClient ftp = new FTPClient(pServerName);
      //FTPMessageCollector listener = new FTPMessageCollector();
      //ftp.setMessageListener(listener);

      // login
      ftp.login(ftpUser, ftpPassword);
      return ftp;
    }

}
