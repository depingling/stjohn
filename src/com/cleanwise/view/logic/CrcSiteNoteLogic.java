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
import com.cleanwise.view.forms.SiteMgrDetailForm;
import com.cleanwise.view.forms.NoteMgrForm;
import com.cleanwise.service.api.*;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.SearchCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.rmi.RemoteException;
import java.math.BigDecimal;
import java.io.*;
import java.net.*;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;


import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class CrcSiteNoteLogic {

    public static ActionErrors init(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();

    
      //Get current site 
      SiteMgrDetailForm siteForm = 
              (SiteMgrDetailForm) session.getAttribute("CRC_SITE_DETAIL_FORM");
      int siteId = siteForm.getIntId();
      int siteIdPrev = pForm.getBusEntityId();
      pForm.setBusEntityId(siteId);
      String siteName = siteForm.getName();
      pForm.setBusEntityName(siteName);
    

      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();
      int topicId = pForm.getTopicId();
      if(topicId<=0) {
        PropertyDataVector topicDV = 
         noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.SITE_NOTE_TOPIC);
         pForm.setTopics(topicDV);
         String topicName = "CRC Site Notes";
         boolean foundFl = false;
         for(Iterator iter = topicDV.iterator(); iter.hasNext();) {
           PropertyData pD = (PropertyData) iter.next();
           if(topicName.equals(pD.getValue())) {
             topicId = pD.getPropertyId();
             pForm.setTopicId(topicId);
             pForm.setTopicName(topicName);
             foundFl = true;
           }
         }
         if(!foundFl) {
          String mess = "Site notes are not configured";
          ae.add("error",new ActionError("error.simpleGenericError",mess));
          return ae;
         }
      }

      if(siteIdPrev != siteId) {
        NoteViewVector noteVwV = 
            noteEjb.getNoteTitles(topicId, siteId, new ArrayList(), false);
        pForm.setNoteList(noteVwV);
        sortByModDate(pForm);
      }
      pForm.setTopicToEdit(null);
      return ae;
    }
    
    //-------------------------------------------------------------------------
    /*
    public static ActionErrors loadTopic(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      
    
      String topicIdS = request.getParameter("topicId");
      int topicId = Integer.parseInt(topicIdS);
      PropertyDataVector topics = pForm.getTopics();
      boolean foundFl = false;
      for(Iterator iter = topics.iterator(); iter.hasNext(); ) {
        PropertyData pD = (PropertyData) iter.next();   
        int tId = pD.getPropertyId();
        if(tId == topicId) {
          pForm.setTopicId(topicId);
          pForm.setTopicName(pD.getValue());
          foundFl = true;
          break;
        }
      }
      
      if(!foundFl) {
         throw new Exception("No topic. Probably expired page used");
      }
    
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();
      String keyWord = pForm.getKeyWord();
      ArrayList kwAL = parseKeyWords(keyWord);
      NoteViewVector noteVwV = 
            noteEjb.getNoteTitles(topicId, pForm.getBusEntityId(), kwAL, pForm.getTextAlsoFl());
      pForm.setNoteList(noteVwV);
      sortByRating(pForm);
      return ae;
    }
*/

    public static ActionErrors allNotes(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
      pForm.setKeyWord("");
      pForm.setTextAlsoFl(false);
	  HttpSession session = request.getSession();
      int siteId = pForm.getBusEntityId();
      int topicId = pForm.getTopicId();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();

      NoteViewVector noteVwV = 
            noteEjb.getNoteTitles(topicId, siteId, new ArrayList(), false);
      pForm.setNoteList(noteVwV);
      sortByModDate(pForm);
      
      return ae;
    }

    public static ActionErrors readNote(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();
      
      String noteIdS = request.getParameter("noteId");
      int noteId = Integer.parseInt(noteIdS);
      
      NoteJoinView note = noteEjb.getNote(noteId);
      pForm.setNote(note);
      ae = initParagraph(request,pForm);
      if(ae.size()>0) {
         return ae;
      }
      return ae;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors editNote(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      String noteTextIdS = (String) request.getParameter("noteTextId");
      int noteTextId = Integer.parseInt(noteTextIdS);
      NoteTextDataVector noteTextDV = pForm.getNote().getNoteText();
      boolean foundFl = false;
      for(Iterator iter = noteTextDV.iterator(); iter.hasNext(); ){
        NoteTextData ntD = (NoteTextData) iter.next();
        int ntId = ntD.getNoteTextId();
        if(ntId==noteTextId) {
          pForm.setParagraph(ntD);
          foundFl = true;
        }
      }
      if(!foundFl) {
        String mess = "Note not found";
        ae.add("error",new ActionError("error.simpleGenericError",mess));
        return ae;
      }

      return ae;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors addNote(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      NoteJoinView njVw = NoteJoinView.createValue();
      NoteData noteD = NoteData.createValue();
      noteD.setPropertyId(pForm.getTopicId());
      noteD.setBusEntityId(pForm.getBusEntityId());
      noteD.setNoteTypeCd(RefCodeNames.NOTE_TYPE_CD.SITE_NOTE);
      noteD.setTitle("");
      njVw.setNote(noteD);
      
      
      NoteTextDataVector noteTextDV = new NoteTextDataVector();
      //noteTextDV.add(noteTextD);
      njVw.setNoteText(noteTextDV);
      pForm.setNote(njVw);
      
      ae = initParagraph(request,pForm);
      if(ae.size()>0) {
        return ae;
      }

      return ae;
    }

    //--------------------------------------------------------------------------------------
    private static ActionErrors initParagraph(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
      UserData userD = appUser.getUser();
      String firstName = userD.getFirstName();
      String lastName = userD.getLastName();
      String userName = userD.getUserName();

      NoteTextData noteTextD = NoteTextData.createValue();
      noteTextD.setUserFirstName(firstName);
      noteTextD.setUserLastName(lastName);
      noteTextD.setSeqNum(0);
      noteTextD.setPageNum(0);
      noteTextD.setNoteText("");
      pForm.setParagraph(noteTextD);
      
      return ae;      
    }
    //--------------------------------------------------------------------------------------
    public static ActionErrors saveNote(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      
      NoteJoinView njVw = pForm.getNote();
      
      FormFile addAttachFile =(FormFile) pForm.getAttachFile();
      byte[] attachFileCont = null;
      String fileName = null;
      if(addAttachFile!=null) {
        fileName = addAttachFile.getFileName();        
        if(Utility.isSet(fileName)) {
          fileName = fileName.replace('\\', '/');
          try {
            attachFileCont = addAttachFile.getFileData(); 
            org.apache.struts.upload.DiskFile nullFile = 
                                new org.apache.struts.upload.DiskFile("");
            pForm.setAttachFile(nullFile);
            if(ae.size()>0) {
              return ae;
            }
          }
          catch (FileNotFoundException exc){
            exc.printStackTrace();
            String mess = "Can't find attachmet file: "+fileName;
            ae.add("error",new ActionError("error.simpleGenericError",mess));
            return ae;
          }
          catch(IOException exc) {
            exc.printStackTrace();
            String mess = "Can't read attachmet file: "+fileName;
            ae.add("error",new ActionError("error.simpleGenericError",mess));
            return ae;
          }
          finally{
            addAttachFile.destroy();
          }
        }
      }
      NoteJoinView njVwInterface = NoteJoinView.createValue();
      NoteData noteD = njVw.getNote();
      String title  = noteD.getTitle();
      if(!Utility.isSet(title)){
        String mess = "Empty note title";
        ae.add("error",new ActionError("error.simpleGenericError",mess));
        return ae;
      }
      njVwInterface.setNote(njVw.getNote());
      NoteTextDataVector ntDV = new NoteTextDataVector();
      njVwInterface.setNoteText(ntDV);
      NoteTextData ntD = pForm.getParagraph();
      String noteText = ntD.getNoteText();
      if(Utility.isSet(noteText)){
        ntDV.add(ntD);
      } else {
        if(noteD.getNoteId()<=0){
          String mess = "Empty note";
          ae.add("error",new ActionError("error.simpleGenericError",mess));
          return ae;
        }
      }
      NoteAttachmentDataVector newNoteAttachDV = new NoteAttachmentDataVector();
      
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
      UserData userD = appUser.getUser();
      String userName = userD.getUserName();

      Note noteEjb = factory.getNoteAPI();
      njVwInterface = noteEjb.saveNote(njVwInterface,userName);
      
      //Copy attachment file
      if(Utility.isSet(fileName)) {
        String  attachFileName = fileName;
        int ind = attachFileName.lastIndexOf("/");
        if(ind>=0) attachFileName = attachFileName.substring(ind+1);
       
        int noteId = njVwInterface.getNote().getNoteId();
        String realFileName = noteId+"_"+attachFileName;
        String attachServer = 
                   uploadAttachment(factory, realFileName,attachFileCont);          

        NoteAttachmentData naD = NoteAttachmentData.createValue();
        naD.setNoteId(noteId);
        naD.setServerName(attachServer);
        naD.setFileName(attachFileName);
        newNoteAttachDV.add(naD); 
        njVwInterface.setNoteAttachment(newNoteAttachDV);
        njVwInterface = noteEjb.saveNote(njVwInterface,userName);
      }

      //
      pForm.setNote(njVwInterface);
      NoteViewVector nVwV = pForm.getNoteList();
      if(nVwV==null){
        nVwV = new NoteViewVector();
      }
      
      
      
      noteD = njVwInterface.getNote();
      int noteId = noteD.getNoteId();
      NoteView nVw = null;
      boolean foundFl = false;
      for(Iterator iter = nVwV.iterator(); iter.hasNext(); ){
        nVw = (NoteView) iter.next();
        if(nVw.getNoteId()==noteId){
          foundFl = true;
          break;
        }
      }
      if(foundFl){
        nVw.setModDate(noteD.getModDate());        
        nVw.setTitle(noteD.getTitle());
      } else {
        nVw = NoteView.createValue();
        nVw.setNoteId(noteD.getNoteId());
        nVw.setTopic(pForm.getTopicName());
        nVw.setModDate(noteD.getModDate());
        nVw.setPropertyId(pForm.getTopicId());
        nVw.setBusEntityId(noteD.getBusEntityId());
        nVw.setTitle(noteD.getTitle());
        nVwV.add(0,nVw);
      }
      initParagraph(request,pForm);
      return ae;
    }
    
    public static ActionErrors delNotes(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      int[] noteIdA = pForm.getSelectorBox();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();
      NoteViewVector nVwV = pForm.getNoteList();
      for(int ii = 0; ii<nVwV.size(); ii++){
        NoteView nVw = (NoteView) nVwV.get(ii);
        int id = nVw.getNoteId();
        for(int jj=0; jj<noteIdA.length; jj++) {
          if(id==noteIdA[jj]) {
            NoteJoinView njVw = noteEjb.getNote(id);
            pForm.setNote(njVw);
            noteEjb.deleteNote(id);
            ae = delAllAttachmentFiles(request,pForm);
            if(ae.size()>0) {
              return ae;
            }
            nVwV.remove(ii);
            pForm.setNote(null);
            break;
          }
        }
      }
      return ae;
    }
    

    public static ActionErrors delParagraphs(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      int[] noteTextIdA = pForm.getSelectorBox();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();
      
      for(int ii=0; ii<noteTextIdA.length; ii++) {
        noteEjb.deleteNoteText(noteTextIdA[ii]);
      }
      NoteJoinView note = pForm.getNote();
      int noteId = note.getNote().getNoteId();
      note = noteEjb.getNote(noteId);
      pForm.setNote(note);
      ae = initParagraph(request,pForm);
      if(ae.size()>0) {
         return ae;
      }
      return ae;
    }
   
    
    public static ActionErrors sortNotes(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
      String sortField = request.getParameter("field");
      if("noteTitle".equals(sortField)){
        sortByTitle(pForm);
      } else if("modifyDate".equals(sortField)){
        sortByModDate(pForm);
      }
      
      return ae;
    }
        
    private static void sortByModDate(NoteMgrForm pForm) 
    {
      NoteViewVector nVwV = pForm.getNoteList();
      if(nVwV==null || nVwV.size()<=1){
        return;
      }
      Object[] nVwA = nVwV.toArray();
      for(int ii=0; ii<nVwA.length-1; ii++){
        boolean exitFl = true;
        for(int jj=0; jj<nVwA.length-ii-1; jj++){
          NoteView nVw1 = (NoteView) nVwA[jj];
          NoteView nVw2 = (NoteView) nVwA[jj+1];
          Date d1 = nVw1.getModDate();
          Date d2 = nVw2.getModDate();
          int comp = d1.compareTo(d2);
          if(comp<0){
            nVwA[jj] = nVw2;
            nVwA[jj+1] = nVw1;
            exitFl = false;
          } 
        }
        if(exitFl) break;
      }
      nVwV = new NoteViewVector();
      for(int ii=0; ii<nVwA.length; ii++){
        NoteView nVw = (NoteView) nVwA[ii];
        nVwV.add(nVw);
      }      
      pForm.setNoteList(nVwV);
     
    }

    private static void sortByTitle(NoteMgrForm pForm) 
    {
      NoteViewVector nVwV = pForm.getNoteList();
      if(nVwV==null || nVwV.size()<=1){
        return;
      }
      Object[] nVwA = nVwV.toArray();
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
      nVwV = new NoteViewVector();
      for(int ii=0; ii<nVwA.length; ii++){
        NoteView nVw = (NoteView) nVwA[ii];
        nVwV.add(nVw);
      }      
      pForm.setNoteList(nVwV);
     
    }

    public static String uploadAttachment(APIAccess pFactory, 
                     String pRealFileName,
					 byte[] pFileCont)
    throws IOException,APIServiceAccessException,Exception
    {

	// this is the path to be saved in the database
	String localDir = ClwCustomizer.getServerDir()+
	                             "/xsuite/notes/";

	// this is the absolute path where we will be writing
	String serverFileName = localDir+pRealFileName;

    //retrieve the file data
    //Get host name
    String returnStr = null;
    String myHostName = null;
    try {
      InetAddress myInetAddress = InetAddress.getLocalHost();
      myHostName = myInetAddress.getCanonicalHostName();
    } catch (UnknownHostException exc) {}
    
    PropertyService propertyServEjb = pFactory.getPropertyServiceAPI();
    String noteFileServer = null;
    try {
      noteFileServer = propertyServEjb.getProperty(Constants.NOTE_FILE_SERVER);
    } catch (DataNotFoundException exc) {
      exc.printStackTrace();
    }
    //Properties clweanwiseProp = Utility.loadProperties("cleanwise.default.properties"); YKR25
    if(Utility.isSet(myHostName) && 
       (!Utility.isSet(noteFileServer) || noteFileServer.equalsIgnoreCase(myHostName))) {
        OutputStream bos = new FileOutputStream(serverFileName);
        int bytesWritten = 0;
        bos.write(pFileCont);
        bos.close();
        return myHostName;
    }  else {
      //try to Ftp file
      try {
          FTPClient ftp = ftpConnect(noteFileServer);
          // set up passive ASCII transfers
          ftp.setConnectMode(FTPConnectMode.PASV);
          if(pRealFileName.toLowerCase().endsWith(".txt")) {
             ftp.setType(FTPTransferType.ASCII);
          } else {
             ftp.setType(FTPTransferType.BINARY);
          }

          // copy file to server 
          String ftpFileName = pRealFileName;
          int ind = pRealFileName.indexOf('/');
          if(ind>=0) ftpFileName = ftpFileName.substring(ind+1);
          ftp.put(pFileCont, ftpFileName);

          // Shut down client                
          ftp.quit();

          return noteFileServer;
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
    return null;
    }

    
    //--------------------------------------------------------------------------------------
    public static ActionErrors downloadAttachment(HttpServletRequest request,
                HttpServletResponse response,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      
      String fileName = request.getParameter("file");
      
      NoteJoinView njVw = pForm.getNote();
      int noteId = njVw.getNote().getNoteId();
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
    
    public static ActionErrors delSelectedAttachments(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      String[] noteAttachA = pForm.getAttachSelectBox();
      if(noteAttachA==null || noteAttachA.length==0){
        return ae;
      }
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();
      
      NoteJoinView note = pForm.getNote();
      int noteId = note.getNote().getNoteId();
      noteEjb.deleteNoteAttachments(noteId,noteAttachA);

      //Delete files
      InetAddress myInetAddress = InetAddress.getLocalHost();
      String myHostName = myInetAddress.getCanonicalHostName();

      String localDir = ClwCustomizer.getServerDir()+
                                 "/xsuite/notes/";

      NoteAttachmentDataVector naDV = note.getNoteAttachment();
      FTPClient ftp = null;
      for(Iterator iter=naDV.iterator(); iter.hasNext();) {
        NoteAttachmentData naD = (NoteAttachmentData) iter.next();
        String fileName = naD.getFileName();
        boolean foundFl = false;
        for(int ii=0; ii<noteAttachA.length; ii++) {
          if(fileName.equals(noteAttachA[ii])) {
            foundFl = true;
            break;
          }
        }
        if(foundFl) {
          String realFileName = noteId+"_"+fileName;
          String serverName = naD.getServerName();
          if(myHostName.equalsIgnoreCase(serverName)){
            File fn = new File(localDir+realFileName);
            fn.delete();
          } else {
            if(ftp==null){
              ftp = ftpConnect(naD.getServerName());
            }
            ftp.delete(realFileName);              
          }
        }
      }
      if(ftp!=null) {
        ftp.quit();
      }
      note = noteEjb.getNote(noteId);
      pForm.setNote(note);
      ae = initParagraph(request,pForm);
      if(ae.size()>0) {
         return ae;
      }
      return ae;
    }

    public static ActionErrors delAllAttachmentFiles(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();
      
      NoteJoinView note = pForm.getNote();
      int noteId = note.getNote().getNoteId();

      //Delete files
      InetAddress myInetAddress = InetAddress.getLocalHost();
      String myHostName = myInetAddress.getCanonicalHostName();

      String localDir = ClwCustomizer.getServerDir()+
                                 "/xsuite/notes/";

      NoteAttachmentDataVector naDV = note.getNoteAttachment();
      FTPClient ftp = null;
      for(Iterator iter=naDV.iterator(); iter.hasNext();) {
        NoteAttachmentData naD = (NoteAttachmentData) iter.next();
        String fileName = naD.getFileName();
        String realFileName = noteId+"_"+fileName;
        String serverName = naD.getServerName();
        if(myHostName.equalsIgnoreCase(serverName)){
          File fn = new File(localDir+realFileName);
          fn.delete();
        } else {
          if(ftp==null){
            ftp = ftpConnect(naD.getServerName());
          }
          ftp.delete(realFileName);
        }
      }
      if(ftp!=null) {
        ftp.quit();
      }
      return ae;
    }

    //Ftp connect
    private static FTPClient ftpConnect(String pServerName) 
    throws Exception 
    {
      //Properties clweanwiseProp = Utility.loadProperties("cleanwise.default.properties"); YKR25
      String ftpUser = System.getProperty("notes.ftp.user");
      String ftpPassword = System.getProperty("notes.ftp.password");

      FTPClient ftp = new FTPClient(pServerName);
      //FTPMessageCollector listener = new FTPMessageCollector();
      //ftp.setMessageListener(listener);

      // login
      ftp.login(ftpUser, ftpPassword);
      return ftp;
    }
    
}
