package com.cleanwise.view.logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.cleanwise.view.forms.AccountMgrDetailForm;
import com.cleanwise.view.forms.StoreAccountMgrDetailForm;
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
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Connection;
//import javax.sql.rowset.serial.SerialBlob;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import org.apache.log4j.Category;

public class StoreAccountMgrNoteLogic {

	private static final Category log = Category.getInstance(IntegrationServicesBean.class);

    private final static String className = "StoreAccountMgrNoteLogic";
    
    public static ActionErrors init(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();


      //Get current Account
      StoreAccountMgrDetailForm accountForm =
              (StoreAccountMgrDetailForm) session.getAttribute("ACCOUNT_DETAIL_FORM");
      String accountIdS=accountForm.getId();
      int accountId = 0;
      try{
        accountId = Integer.parseInt(accountIdS);
      }catch(NumberFormatException exc) {
        ae.add("error",new ActionError("error.systemError","Wrong account number format: "+accountIdS));
        return ae;
      }
      int accountIdPrev = pForm.getBusEntityId();
      pForm.setBusEntityId(accountId);
      String accountName = accountForm.getName();
      pForm.setBusEntityName(accountName);


      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();
      int topicId = pForm.getTopicId();
      if(topicId<=0) {
        PropertyDataVector topicDV =
         noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);
         pForm.setTopics(topicDV);
         if(topicDV.size()==1) {
           PropertyData pD = (PropertyData) topicDV.get(0);
           topicId = pD.getPropertyId();
           pForm.setTopicId(topicId);
           pForm.setTopicName(pD.getValue());
         } else {
           pForm.setTopicName("");
         }
      }
      if(topicId>0) {
        if(accountIdPrev != accountId) {
          NoteViewVector noteVwV =
            noteEjb.getNoteTitles(topicId, accountId, new ArrayList(), false);
          pForm.setNoteList(noteVwV);
          sortByRating(pForm);
        }

      }
      pForm.setTopicToEdit(null);

      return ae;
    }

    //-------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------
    private static ArrayList parseKeyWords(String pKeyWord)
    {
      if(!Utility.isSet(pKeyWord)){
        return null;
      }

      ArrayList kwAL = new ArrayList();
      StringTokenizer tok = new StringTokenizer(pKeyWord,"\"");
      int cnt = tok.countTokens();
      String[] substrA = new String[cnt];
      for(int ii=0; tok.hasMoreTokens(); ii++) {
        String ss = tok.nextToken();
        substrA[ii] = ss;
      }

      boolean flag = (pKeyWord.charAt(0)=='"')? true:false;
      String wrkStr;
      for(int ii=0; ii<substrA.length; ii++) {
         wrkStr = substrA[ii];
         if(flag) {
           if(wrkStr.trim().length()>0) {
             kwAL.add(wrkStr.trim());
           }
           flag = false;
         } else {
           wrkStr = wrkStr.trim();
           StringTokenizer tok1 = new StringTokenizer(wrkStr," ");
           while(tok1.hasMoreTokens()) {
             String ss = tok1.nextToken();
             ss = ss.trim();
             if(ss.length()>0) {
               kwAL.add(ss);
             }
           }
           flag = true;
         }
      }

      return kwAL;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors addTopic(HttpServletRequest request,
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

      String topicName = pForm.getTopicName();
      if(!Utility.isSet(topicName)) {
        String mess = "Empty topic name";
        ae.add("error",new ActionError("error.simpleGenericError",mess));
        return ae;
      }

      PropertyDataVector topicDV =
         noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);

      for(Iterator iter = topicDV.iterator(); iter.hasNext(); ){
        PropertyData pD = (PropertyData) iter.next();
        String tn = pD.getValue();
        if(topicName.equalsIgnoreCase(tn)) {
          String mess = "Topic already exists: "+tn;
          ae.add("error",new ActionError("error.simpleGenericError",mess));
          return ae;
        }
      }

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
      UserData userD = appUser.getUser();
      String userName = userD.getUserName();
      noteEjb.addNoteTopic(topicName,
              RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC, userName);

      topicDV =
       noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);
      pForm.setTopics(topicDV);
      pForm.setTopicId(0);
      pForm.setTopicName("");
      return ae;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors editTopic(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      String topicIdS = request.getParameter("topicToEditId");
      int topicId = Integer.parseInt(topicIdS);
      PropertyDataVector topicDV = pForm.getTopics();
      boolean foundFl = false;
      for(Iterator iter = topicDV.iterator(); iter.hasNext(); ){
        PropertyData pD = (PropertyData) iter.next();
        String tn = pD.getValue();
        if(pD.getPropertyId()==topicId) {
          foundFl = true;
          pForm.setTopicToEdit(pD);
          break;
        }
      }
      if(!foundFl){
        String mess = "Topic not found: "+topicId;
        throw new Exception(mess);
      }

      return ae;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors updateTopic(HttpServletRequest request,
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
      PropertyData topicToEdit = pForm.getTopicToEdit();
      int topicId = topicToEdit.getPropertyId();
      String topicName = topicToEdit.getValue();
      if(!Utility.isSet(topicName)) {
        String mess = "Empty topic name";
        ae.add("error",new ActionError("error.simpleGenericError",mess));
        return ae;
      }

      PropertyDataVector topicDV = pForm.getTopics();

      for(Iterator iter = topicDV.iterator(); iter.hasNext(); ){
        PropertyData pD = (PropertyData) iter.next();
        String tn = pD.getValue();
        if(topicName.equalsIgnoreCase(tn) && pD.getPropertyId()!=topicId) {
          String mess = "Topic already exists: "+tn;
          ae.add("error",new ActionError("error.simpleGenericError",mess));
          return ae;
        }
      }

      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
      UserData userD = appUser.getUser();
      String userName = userD.getUserName();
      noteEjb.updateNoteTopic(topicToEdit, userName);
      pForm.setTopicToEdit(null);
      return ae;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors deleteTopic(HttpServletRequest request,
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

      String topicIdS = request.getParameter("topicId");
      int topicId = Integer.parseInt(topicIdS);

      int cnt = noteEjb.getNumnberOfNotes(topicId);
      if(cnt>0) {
        String mess = "Can't delete. There are "+cnt+" notes for the topic";
        ae.add("error",new ActionError("error.simpleGenericError",mess));
        return ae;
      }

      noteEjb.deleteNoteTopic(topicId);
      pForm.setTopicId(0);
      PropertyDataVector topicDV =
       noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);
      pForm.setTopics(topicDV);

      return ae;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors searchNotes(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      int accountId = pForm.getBusEntityId();
      int topicId = pForm.getTopicId();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();

      String keyWord = pForm.getKeyWord();
      ArrayList kwAL = parseKeyWords(keyWord);
      NoteViewVector noteVwV =
            noteEjb.getNoteTitles(topicId, accountId, kwAL, pForm.getTextAlsoFl());
      pForm.setNoteList(noteVwV);
      sortByRating(pForm);

      return ae;
    }


    public static ActionErrors allNotes(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
      pForm.setKeyWord("");
      pForm.setTextAlsoFl(false);
	  HttpSession session = request.getSession();
      int accountId = pForm.getBusEntityId();
      int topicId = pForm.getTopicId();
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      Note noteEjb = factory.getNoteAPI();

      NoteViewVector noteVwV =
            noteEjb.getNoteTitles(topicId, accountId, new ArrayList(), false);
      pForm.setNoteList(noteVwV);
      sortByRating(pForm);

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
      noteD.setNoteTypeCd(RefCodeNames.NOTE_TYPE_CD.ACCOUNT_NOTE);
      noteD.setTitle("");

      noteD.setEffDate(Constants.getCurrentDate());
      noteD.setExpDate(Constants.getCurrentDate());
//      noteD.setCounter(0);

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

      int cntr = pForm.getNote().getNote().getCounter();
      if ((cntr > 999) || (cntr < 0)) {
    	  String mess = "Counter must be no less than 0 and no greater than 999";
    	  ae.add("error", new ActionError("error.simpleGenericError", mess));
    	  return ae;
      }
      NoteJoinView njVw = pForm.getNote();
      njVw.setNote(njVw.getNote());

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
            String mess = "Can't find attachment file: "+fileName;
            ae.add("error",new ActionError("error.simpleGenericError",mess));
            return ae;
          }
          catch(IOException exc) {
            exc.printStackTrace();
            String mess = "Can't read attachment file: "+fileName;
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
		} else {
            ntDV.add(ntD);
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



//        String attachServer =
//                   uploadAttachment(factory, realFileName,attachFileCont);

        NoteAttachmentData naD = NoteAttachmentData.createValue();
        naD.setNoteId(noteId);
//        naD.setServerName(attachServer);
        naD.setFileName(attachFileName);
        newNoteAttachDV.add(naD);
        njVwInterface.setNoteAttachment(newNoteAttachDV);
        njVwInterface = noteEjb.saveNote(njVwInterface,userName);

        noteEjb.saveNoteAttachment(noteId, attachFileName, attachFileCont);

//        Blob blob = new EmptyBlob();

//        blob = new SerialBlob(imageAsBytes);
//        public static final byte[] EMPTY_BYTES = new byte[]{0};
//        byte[] EMPTY_BYTES = new byte[]{0};

//        public static final Blob EMPTY_BLOB = createBlob(EMPTY_BYTES);

//        SerializableBlob serializableBlob = new SerializableBlob();

        BlobImpl blobImpl = new BlobImpl(attachFileCont);
        SerializableBlob serializableBlob = new SerializableBlob(blobImpl);
        Blob blob = (Blob) serializableBlob;
//        pForm.setAttachedFileList()
//        SerializableBlob serializableBlob = (new StoreAccountMgrNoteLogic()).SerializableBlob( new BlobImpl( attachFileCont ) );

//        blob = serializableBlob.createBlob.
//        Blob blob. = new Blob();
//        OutputStream out = blob.setBinaryStream(0);
//        out.write(attachFileCont);
//        out.flush();
//        out.close();
        NoteAttachmentData newNoteAttachmData = null;
        for(Iterator iteror = newNoteAttachDV.iterator(); iteror.hasNext();) {
        	newNoteAttachmData = (NoteAttachmentData) iteror.next();
        	if (attachFileName.equalsIgnoreCase(newNoteAttachmData.getFileName())) {
        		newNoteAttachmData.setBinaryData(attachFileCont);
        	}
        }
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
            log.info("AccountMgrNoteLogic.delNotes DDDDDDDDDDDDDDDDDDDDD njVw: "+njVw);
            noteEjb.deleteNote(id);
//            ae = delAllAttachmentFiles(request,pForm);
            if(ae.size()>0) {
              return ae;
            }
            nVwV.remove(ii);
            pForm.setNote(null);
            break;
          } //if
        } //for jj
      } //for ii
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
      if (noteId != 0) {
         note = noteEjb.getNote(noteId);
      } else {
    	  String mess = "Note does not exist. Nothing to delete";
    	  ae.add("error", new ActionError("error.simpleGenericError", mess));
    	  return ae;  
      }
      pForm.setNote(note);
      ae = initParagraph(request,pForm);
      if(ae.size()>0) {
         return ae;
      }
      return ae;
    }

    //--------------------------------------------------------------------------------------
    public static ActionErrors moveToSelectTopic(HttpServletRequest request,
			    NoteMgrForm pForm)
	throws Exception
    {
      ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
      pForm.setTopicId(0);
      pForm.setTopicName("");
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
      } else if("searchRating".equals(sortField)){
        sortByRating(pForm);
      }

      return ae;
    }
    private static void sortByRating(NoteMgrForm pForm)
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
          int r1 = nVw1.getSearchRate();
          int r2 = nVw2.getSearchRate();
          if(r1<r2){
            nVwA[jj] = nVw2;
            nVwA[jj+1] = nVw1;
            exitFl = false;
          } else if(r1==r2){
            Date d1 = nVw1.getModDate();
            Date d2 = nVw2.getModDate();
            int comp = d1.compareTo(d2);
            if(comp<0){
              nVwA[jj] = nVw2;
              nVwA[jj+1] = nVw1;
              exitFl = false;
            }
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
    //Properties clweanwiseProp = Utility.loadProperties("cleanwise.default.properties");  YKR25
    if(Utility.isSet(myHostName) &&
       (!Utility.isSet(noteFileServer) || noteFileServer.equalsIgnoreCase(myHostName))) {
        File dir = new File(serverFileName);
        dir.getParentFile().mkdirs();
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

    public static ActionErrors readAttachment(HttpServletRequest request,
            HttpServletResponse response,
		    NoteMgrForm pForm)
	    throws Exception {
	  ActionErrors ae = new ActionErrors();
	  HttpSession session = request.getSession();
	  APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
	  if (null == factory) {
		  throw new Exception("No APIAccess.");
	  }

	  String fileName = request.getParameter("file");
	  NoteJoinView njVw = pForm.getNote();
	  NoteAttachmentDataVector naDV = njVw.getNoteAttachment();

	  boolean foundF1 = false;
	  for(Iterator iter=naDV.iterator(); iter.hasNext();) {
	    NoteAttachmentData naD = (NoteAttachmentData) iter.next();
	    String fn = naD.getFileName();
	    byte[] attachment = naD.getBinaryData();


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
//      InetAddress myInetAddress = InetAddress.getLocalHost();
//      String myHostName = myInetAddress.getCanonicalHostName();
//
//      String localDir = ClwCustomizer.getServerDir()+
//                                 "/xsuite/notes/";
//
//      NoteAttachmentDataVector naDV = note.getNoteAttachment();
//      FTPClient ftp = null;
//      for(Iterator iter=naDV.iterator(); iter.hasNext();) {
//        NoteAttachmentData naD = (NoteAttachmentData) iter.next();
//        String fileName = naD.getFileName();
//        boolean foundFl = false;
//        for(int ii=0; ii<noteAttachA.length; ii++) {
//          if(fileName.equals(noteAttachA[ii])) {
//            foundFl = true;
//            break;
//          }
//        }
//        if(foundFl) {
//          String realFileName = noteId+"_"+fileName;
//          String serverName = naD.getServerName();
//          if(myHostName.equalsIgnoreCase(serverName)){
//            File fn = new File(localDir+realFileName);
//            fn.delete();
//          } else {
//            if(ftp==null){
//              ftp = ftpConnect(naD.getServerName());
//            }
//            ftp.delete(realFileName);
//          }
//        }
//      }
//      if(ftp!=null) {
//        ftp.quit();
//      }
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
//	  HttpSession session = request.getSession();
//      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
//      if (null == factory) {
//         throw new Exception("No APIAccess.");
//      }
//      Note noteEjb = factory.getNoteAPI();

//      NoteJoinView note = pForm.getNote();
//      int noteId = note.getNote().getNoteId();

      //Delete files
//      InetAddress myInetAddress = InetAddress.getLocalHost();
//      String myHostName = myInetAddress.getCanonicalHostName();
//
//      String localDir = ClwCustomizer.getServerDir()+
//                                 "/xsuite/notes/";
//
//      NoteAttachmentDataVector naDV = note.getNoteAttachment();
//      FTPClient ftp = null;
//      for(Iterator iter=naDV.iterator(); iter.hasNext();) {
//        NoteAttachmentData naD = (NoteAttachmentData) iter.next();
//        String fileName = naD.getFileName();
//        String realFileName = noteId+"_"+fileName;
//        String serverName = naD.getServerName();
//        if(myHostName.equalsIgnoreCase(serverName)){
//          File fn = new File(localDir+realFileName);
//          fn.delete();
//        } else {
//          if(ftp==null){
//            ftp = ftpConnect(naD.getServerName());
//          }
//          ftp.delete(realFileName);
//        }
//      }
//      if(ftp!=null) {
//        ftp.quit();
//      }
      return ae;
    }

    //Ftp connect
    private static FTPClient ftpConnect(String pServerName)
    throws Exception
    {
      // Properties clweanwiseProp = Utility.loadProperties("cleanwise.default.properties"); //YKR25
      String ftpUser = System.getProperty("notes.ftp.user"); //YKR25
      String ftpPassword = System.getProperty("notes.ftp.password"); //YKR25

      FTPClient ftp = new FTPClient(pServerName);
      //FTPMessageCollector listener = new FTPMessageCollector();
      //ftp.setMessageListener(listener);

      // login
      ftp.login(ftpUser, ftpPassword);
      return ftp;
    }

    static class BlobImpl implements Blob {

    	private InputStream stream;
    	private int length;
    	private boolean needsReset = false;

    	public BlobImpl(byte[] bytes) {
    		this.stream = new ByteArrayInputStream(bytes);
    		this.length = bytes.length;
    	}

    	public BlobImpl(InputStream stream, int length) {
    		this.stream = stream;
    		this.length = length;
    	}

    	/**
    	 * @see java.sql.Blob#length()
    	 */
    	public long length() throws SQLException {
    		return length;
    	}

    	/**
    	 * @see java.sql.Blob#truncate(long)
    	 */
    	public void truncate(long pos) throws SQLException {
    		excep();
    	}

    	/**
    	 * @see java.sql.Blob#getBytes(long, int)
    	 */
    	public byte[] getBytes(long pos, int len) throws SQLException {
    		excep(); return null;
    	}

    	/**
    	 * @see java.sql.Blob#setBytes(long, byte[])
    	 */
    	public int setBytes(long pos, byte[] bytes) throws SQLException {
    		excep(); return 0;
    	}

    	/**
    	 * @see java.sql.Blob#setBytes(long, byte[], int, int)
    	 */
    	public int setBytes(long pos, byte[] bytes, int i, int j)
    	throws SQLException {
    		excep(); return 0;
    	}

    	/**
    	 * @see java.sql.Blob#position(byte[], long)
    	 */
    	public long position(byte[] bytes, long pos) throws SQLException {
    		excep(); return 0;
    	}

    	/**
    	 * @see java.sql.Blob#getBinaryStream()
    	 */
    	public InputStream getBinaryStream() throws SQLException {
    		try {
    			if (needsReset) stream.reset();
    		}
    		catch (IOException ioe) {
    			throw new SQLException("could not reset reader");
    		}
    		needsReset = true;
    		return stream;
    	}

    	/**
    	 * @see java.sql.Blob#setBinaryStream(long)
    	 */
    	public OutputStream setBinaryStream(long pos) throws SQLException {
    		excep(); return null;
    	}

    	/**
    	 * @see java.sql.Blob#position(Blob, long)
    	 */
    	public long position(Blob blob, long pos) throws SQLException {
    		excep(); return 0;
    	}

    	private static void excep() {
    		throw new UnsupportedOperationException("Blob may not be manipulated from creating session");
    	}

         public InputStream getBinaryStream(long a, long b) { // to comply with java.sql.Blob
            return null;
        }

        public void free() { // to comply with java.sql.Blob

        }

    }

    static class SerializableBlob implements Serializable, Blob {

    	private transient final Blob blob;

    	public SerializableBlob(Blob blob) {
    		this.blob = blob;
    	}

    	public Blob getWrappedBlob() {
    		if ( blob==null ) {
    			throw new IllegalStateException("Blobs may not be accessed after serialization");
    		}
    		else {
    			return blob;
    		}
    	}

    	public long length() throws SQLException {
    		return getWrappedBlob().length();
    	}

    	public byte[] getBytes(long pos, int length) throws SQLException {
    		return getWrappedBlob().getBytes(pos, length);
    	}

    	public InputStream getBinaryStream() throws SQLException {
    		return getWrappedBlob().getBinaryStream();
    	}

    	public long position(byte[] pattern, long start) throws SQLException {
    		return getWrappedBlob().position(pattern, start);
    	}

    	public long position(Blob pattern, long start) throws SQLException {
    		return getWrappedBlob().position(pattern, start);
    	}

    	public int setBytes(long pos, byte[] bytes) throws SQLException {
    		return getWrappedBlob().setBytes(pos, bytes);
    	}

    	public int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException {
    		return getWrappedBlob().setBytes(pos, bytes, offset, len);
    	}

    	public OutputStream setBinaryStream(long pos) throws SQLException {
    		return getWrappedBlob().setBinaryStream(pos);
    	}

    	public void truncate(long len) throws SQLException {
    		getWrappedBlob().truncate(len);
    	}

        public InputStream getBinaryStream(long a, long b) { // to comply with java.sql.Blob
            return null;
        }
        public void free() { // to comply with java.sql.Blob

        }

    }

}
