package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.NoteDataAccess;
import com.cleanwise.service.api.session.*;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.view.i18n.ClwI18nUtil;
import com.cleanwise.view.utils.*;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.rmi.*;

/**
 * Title:        MaintenanceMgrLogic
 * Description:  logic manager for the asset processing.
 * Purpose:      execute operation for the asset processing
 * Copyright:    Copyright (c) 2006
 * Company:      CleanWise, Inc.
 * Date:         02.01.2007
 * Time:         10:00:58
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class MaintenanceMgrLogic {

    public static final String UI_FRAME_FORM = "UiFrameForm";
    public static final String UI_FRAME_DETAIL_FORM = "UiFrameDetailForm";
    public static final String NewsTopicName = "INTERSTITIAL_MESSAGE";
    public static final String FAQTopicName = "FAQ";


    private static ActionErrors checkRequest(HttpServletRequest request, ActionForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        APIAccess factory = (APIAccess) request.getSession().getAttribute(Constants.APIACCESS);

        if (form == null) {
            ae.add("error", new ActionError("error.systemError", "Form not initialized"));
            return ae;
        }
        if (factory == null) {
            ae.add("error", new ActionError("error.systemError", "No Ejb access"));
            return ae;
        }
        if (appUser == null) {
            ae.add("error", new ActionError("error.systemError", "No user info"));
            return ae;
        }
        return ae;

    }


    public static ActionErrors init(HttpServletRequest request, ActionForm pForm) throws Exception {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();
        UiFrameForm form = (UiFrameForm)pForm;

        ae.add(checkRequest(request, form));
        if (ae.size() > 0) {
            return ae;
        }

        APIAccess factory = new APIAccess();
        ListService listServiceEJB = factory.getListServiceAPI();

        RefCdDataVector statusCds = listServiceEJB.getRefCodesCollection("FRAME_STATUS_CD");
        session.setAttribute("uiframe.status.vector", statusCds);

        RefCdDataVector frameTypeCds = listServiceEJB.getRefCodesCollection("FRAME_TYPE_CD");
        session.setAttribute("uiframe.type.vector", frameTypeCds);

        RefCdDataVector slotTypeCds = listServiceEJB.getRefCodesCollection("SLOT_TYPE_CD");
        session.setAttribute("uislot.type.vector", slotTypeCds);

        session.setAttribute(UI_FRAME_FORM, form);

        form.setMode2("viewArticles");

        reInitFramesList(session);

        //initNewsList(session, form);
        return ae;
    }

    public static ActionErrors initNews(HttpServletRequest request, ActionForm pForm) throws Exception {
      ActionErrors ae= init(request, pForm);
      HttpSession session = request.getSession();
      if (ae.size() > 0){
        return ae;
      }
      ae=initNewsList(session, pForm);
      return ae;
    }

    public static ActionErrors initFAQ(HttpServletRequest request, ActionForm pForm) throws Exception {
      ActionErrors ae= init(request, pForm);
      HttpSession session = request.getSession();
      if (ae.size() > 0){
        return ae;
      }
      ae = initFAQList(session, pForm);
      return ae;
    }

    private static ActionErrors initNewsList(HttpSession session, ActionForm pForm) throws Exception {
      ActionErrors ae = new ActionErrors();
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
        UiFrameForm form = (UiFrameForm) pForm;
        Note noteEjb = factory.getNoteAPI();
        int topicId = form.getTopicId();

        PropertyDataVector propDV = null;
        //---------
        try {
          propDV = noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);

          form.setNoteTopicData(null);

          Iterator iter = propDV.iterator();
          while (iter.hasNext()) {
            PropertyData propItem = (PropertyData) iter.next();
            if (propItem.getValue().equals(NewsTopicName)) {
              form.setNoteTopicData(propItem);
              break;
            }
          }
          if (form.getNoteTopicData() == null) {
            ae.add("error",
                   new ActionError("error.systemError",
                NewsTopicName + " topic is not is not found for user. It will be created with first FAQ."));
          }
          DBCriteria dbCrit = new DBCriteria();
          dbCrit.addEqualTo(NoteDataAccess.BUS_ENTITY_ID, appUser.getSite().getAccountId());
          int storeId = appUser.getUserStore().getStoreId();

          NoteJoinViewVector jVV = noteEjb.getNotesForUser(appUser.getUser().getUserId(), dbCrit, NewsTopicName, storeId);
          form.setNoteJoinViewVector(jVV);
          form.setMode2("viewArticles");
        }
        catch (RemoteException ex) {
          ex.printStackTrace();
        }
      return ae;
    }

    private static void reInitFramesList(HttpSession session) throws Exception {
        UiFrameForm form = (UiFrameForm)session.getAttribute(UI_FRAME_FORM);
        if (form == null) {
            form = new UiFrameForm();
        }

        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        int accountId = appUser.getSite().getAccountBusEntity().getBusEntityId();

        APIAccess factory = new APIAccess();
        UiFrame frBean = factory.getUiFrameAPI();
        UiFrameViewVector frames = frBean.getFrames(accountId);
        form.setFrames(frames);
        UiFrameViewVector templates = frBean.getTemplates(accountId);
        form.setTemplates(templates);
        form.setMode("viewFrames");

        session.setAttribute(UI_FRAME_FORM, form);

    }


    public static ActionErrors createNew(HttpServletRequest request, ActionForm pForm) throws Exception {

        UiFrameForm form = (UiFrameForm)pForm;
        ActionErrors ae;
        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }
        form.setMode("viewTemplates");
        return ae;
    }


    public static ActionErrors createNewBySelectedTemplate(HttpServletRequest request, ActionForm pForm) throws Exception {
        UiFrameDetailForm form = (UiFrameDetailForm)pForm;
        HttpSession session = request.getSession();
        ActionErrors ae;
        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        int templateId = 0;
        try {
            String templateIdStr = request.getParameter("selectedTemplateId");
            templateId = Integer.parseInt(templateIdStr);
        } catch (Exception e) {
            templateId = -1;
        }
        if (templateId <= 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noFrameTemplateSelected", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        // get template by id (may be simply from form?
        APIAccess factory = new APIAccess();
        UiFrame frBean = factory.getUiFrameAPI();
        UiFrameData template = frBean.getFrame(templateId);
        UiFrameView templateView = frBean.getFrameView(templateId);

        //init template as new frame
        template.setParentUiFrameId(template.getUiFrameId());
        template.setUiFrameId(0);
        UiFrameSlotViewVector templateSlots = frBean.getFrameSlots(templateId);

        // init slots as new slots
        Iterator i = templateSlots.iterator();
        while (i.hasNext()) {
            UiFrameSlotView slot = (UiFrameSlotView)i.next();
            slot.setUiFrameSlotId(0);
            slot.setUrl("https://");
        }

        form.setFrameData(template);
        form.setSlots(templateSlots);
        form.initTextValues();

        session.setAttribute(UI_FRAME_DETAIL_FORM, form);
        return ae;
    }


    public static ActionErrors getFrameDetail(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        UiFrameDetailForm detailForm = (UiFrameDetailForm)pForm;

        int frameId = 0;
        try {
            String frameIdStr = request.getParameter("selectedFrameId");
            frameId = Integer.parseInt(frameIdStr);
        } catch (Exception e) {
            frameId = -1;
        }
        if (frameId <= 0) {
            String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noFrameTemplateSelected", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }

        // get frame by id
        APIAccess factory = new APIAccess();
        UiFrame frBean = factory.getUiFrameAPI();
        UiFrameData frame = frBean.getFrame(frameId);
        UiFrameSlotViewVector slots = frBean.getFrameSlots(frameId);

        detailForm.setFrameData(frame);
        detailForm.setSlots(slots);
        detailForm.initTextValues();

        session.setAttribute(UI_FRAME_DETAIL_FORM, detailForm);
        return ae;
    }

    public static ActionErrors getTemplateDetail(HttpServletRequest request, ActionForm pForm) throws Exception {

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        UiFrameForm form = (UiFrameForm)pForm;

        int templateId = form.getSelectedTemplateId();
        if (templateId <= 0) {
          String errorMess = ClwI18nUtil.getMessage(request, "shop.errors.noTemplateSelected", null);
          ae.add("error", new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }

        // get frame by id
        APIAccess factory = new APIAccess();
        UiFrame frBean = factory.getUiFrameAPI();
        UiFrameData template = frBean.getFrame(templateId);
        UiFrameSlotViewVector templateSlots = frBean.getFrameSlots(templateId);

        UiFrameDetailForm detailForm = new UiFrameDetailForm();
        detailForm.setFrameData(template);
        detailForm.setSlots(templateSlots);
        detailForm.initTextValues();

        session.setAttribute(UI_FRAME_DETAIL_FORM, detailForm);
        return ae;
    }



    private static ActionErrors checkFormAttribute(HttpServletRequest request, UiFrameDetailForm form) {
        ActionErrors ae = new ActionErrors();

        if (!Utility.isSet(form.getFrameData().getShortDesc())) {
            ae.add("ShortDesc", new ActionError("variable.empty.error", "Short Description"));
        }

        return ae;
    }

    public static ActionErrors preview(HttpServletRequest request, ActionForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UiFrameDetailForm form = (UiFrameDetailForm)pForm;
        setImageSlots(form);
        session.setAttribute("current.homepage.frame", form.getFrame());
        return ae;
    }

    public static ActionErrors previewArticle(HttpServletRequest request, ActionForm pForm) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        UiFrameForm form = (UiFrameForm)pForm;
//        session.setAttribute("current.homepage.frame", form.getFrame());
        form.setMode2("previewArticle");

        return ae;
    }

    public static ActionErrors update(HttpServletRequest request, ActionForm pForm) throws Exception {
        ActionErrors ae;
        HttpSession session = request.getSession();
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        String userName = appUser.getUserName();
        UiFrameDetailForm form = (UiFrameDetailForm)pForm;
        UiFrameView frameView = form.getFrame();

        UiFrameData frame = frameView.getFrameData();
        Date today = new Date();
        if (frame.getUiFrameId() <= 0) {
            frame.setAddDate(today);
            frame.setAddBy(userName);
        }
        frame.setModDate(today);
        frame.setModBy(userName);
        frame.setFrameTypeCd(RefCodeNames.FRAME_TYPE_CD.ACCOUNT_UI_FRAME);
        setImageSlots(form);

        UiFrameSlotViewVector slots = form.getSlots();

        Iterator i = slots.iterator();
        int j = 0;
        while (i.hasNext()) {
            UiFrameSlotView slot = (UiFrameSlotView)i.next();
            if (slot.getUiFrameSlotId() <= 0) {
                slot.setAddDate(today);
                slot.setAddBy(userName);
            }
            slot.setModDate(today);
            slot.setModBy(userName);
            j++;
        }
        ae = checkFormAttribute(request, form);
        if (ae.size() > 0) {
          return ae;
        }

        //update frame
        APIAccess factory = new APIAccess();
        UiFrame frBean = factory.getUiFrameAPI();
        frBean.updateFrame(frameView);

        reInitFramesList(session);

        return ae;
    }

    public static ActionErrors addNews(HttpServletRequest request, ActionForm pForm) throws Exception {
//      UiFrameForm form = (UiFrameForm)pForm;
//      form.setTopicName("");
//      form.setTopicId(0);
      return addNewArticle(request, pForm, RefCodeNames.NOTE_TYPE_CD.NEWS_NOTE);
    }

    public static ActionErrors addFAQ(HttpServletRequest request, ActionForm pForm) throws Exception {
 //     UiFrameForm form = (UiFrameForm)pForm;
      return addNewArticle(request, pForm, RefCodeNames.NOTE_TYPE_CD.ACCOUNT_NOTE);
    }

    public static ActionErrors addNewArticle(HttpServletRequest request, ActionForm pForm, String pNoteTypeCd) throws Exception {

        UiFrameForm form = (UiFrameForm)pForm;

        ActionErrors ae;
        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }
        form.setMode2("editArticle");
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            String errorMess = ClwI18nUtil.getMessage(request, "news.admin.errors.noApi", null);
            throw new Exception(errorMess);
        } else {
            form.setTopicName("");
            form.setTopicId(0);
            form.setLocaleCd("");
            //NoteJoinView nJVw = new NoteJoinView();

          NoteJoinView njVw = NoteJoinView.createValue();
          NoteData noteD = NoteData.createValue();
          noteD.setPropertyId(form.getTopicId());
          noteD.setBusEntityId(form.getBusEntityId());
          noteD.setNoteTypeCd(pNoteTypeCd);
          noteD.setTitle("");

          noteD.setEffDate(Constants.getCurrentDate());
          noteD.setExpDate(Constants.getCurrentDate());
          noteD.setCounter(0);
          noteD.setForcedEachLogin("false");
          njVw.setNote(noteD);
          NoteTextDataVector noteTextDV = new NoteTextDataVector();
          njVw.setNoteText(noteTextDV);
          form.setNote(njVw);

          ae = initParagraph(request, form);
          if(ae.size()>0) {
            return ae;
          }
          return ae;
        }
    }

    public static ActionErrors cancelEditing(HttpServletRequest request, ActionForm pForm) throws Exception {

        UiFrameForm form = (UiFrameForm)pForm;
        ActionErrors ae;
        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }
        if (form.getMode2().equals("editArticle")) {
            form.setMode2("viewArticles");
        } else {
            form.setMode2("editArticle");
        }

//        HttpSession session = request.getSession();
//        ae = initParagraph(request, form);
//        if(ae.size()>0) {
//            return ae;
//        }
        return ae;
    }

    public static ActionErrors deleteNews(HttpServletRequest request, ActionForm pForm) throws Exception {
      HttpSession session = request.getSession();
      ActionErrors ae = deleteSelected(request, pForm);
      if (ae.size()> 0){
        return ae;
      }
      UiFrameForm form = (UiFrameForm)pForm;
      initNewsList(session, form);
      ae = initParagraph(request, form);
      return ae;
    }

    public static ActionErrors deleteFAQ(HttpServletRequest request, ActionForm pForm) throws Exception {
      HttpSession session = request.getSession();
      ActionErrors ae = deleteSelected(request, pForm);
      if (ae.size()> 0){
        return ae;
      }
      UiFrameForm form = (UiFrameForm)pForm;
      initFAQList(session, form);
      ae = initParagraph(request, form);
      return ae;
    }

    public static ActionErrors deleteSelected(HttpServletRequest request, ActionForm pForm) throws Exception {

        UiFrameForm form = (UiFrameForm)pForm;
        ActionErrors ae;
        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }
        form.setMode2("viewArticles");
        HttpSession session = request.getSession();


        int[] selectedArticles = form.getSelectorBox();
        int[] selIds = new int[selectedArticles.length];
        if(selectedArticles==null || selectedArticles.length==0){
              String errorMess = ClwI18nUtil.getMessage(request, "mgr.errors.noItemsSelectedForDeletion", null);
              ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            String errorMess = ClwI18nUtil.getMessage(request, "news.admin.errors.noApi", null);
            throw new Exception(errorMess);
        }
        NoteJoinViewVector jVV = form.getNoteJoinViewVector();
        for(int k=0; k < selectedArticles.length; k++) {
//            NoteJoinView selectedArticle = (NoteJoinView) jVV.get(k);
//            selIds[k] = selectedArticle.getNote().getNoteId();
            NoteJoinView selectedArticle = (NoteJoinView) jVV.get(selectedArticles[k]);
            selIds[k] = selectedArticle.getNote().getNoteId();
        }

        Note noteEjb = factory.getNoteAPI();
//        NoteJoinView note = form.getNote();
        int noteId = 0;
        noteEjb.deleteArticles(noteId, selIds);

        /*   initNewsList(session, form);
           ae = initParagraph(request, form);
        if(ae.size()>0) {
            return ae;
        } */
        if (ae.size() > 0) {
            return ae;
        }else{
        	form.setConfirmMessage(ClwI18nUtil.getMessage(request,
                    "mgr.news.actionMessage.itemsDeleted", null));
        }
        return ae;
    }

    public static ActionErrors editArticle(HttpServletRequest request, ActionForm pForm) throws Exception {

        UiFrameForm form = (UiFrameForm)pForm;
        ActionErrors ae;
        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }
        ae = initParagraph(request, form);
        if(ae.size()>0) {
          return ae;
        }
        form.setMode2("editArticle");
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
        Note noteEjb = factory.getNoteAPI();
        String articleIdS = request.getParameter("selectedArticleId");
        int noteTextId = Integer.parseInt(articleIdS);

        NoteJoinView nJV = noteEjb.getNote(noteTextId);

        if(nJV == null) {
            String errorMess = ClwI18nUtil.getMessage(request, "news.admin.errors.ArticleNotFound", null);
            ae.add("error", new ActionError("error.simpleGenericError", errorMess));
            return ae;
        }
        NoteTextDataVector articleNoteTextDataVector = nJV.getNoteText();
        NoteTextData nTD = NoteTextData.createValue();
        if (articleNoteTextDataVector != null) {
            nTD = (NoteTextData) articleNoteTextDataVector.get(0);
        }
        form.setParagraph(nTD);
        form.setNote(nJV);
        if (nJV.getNote().getCounter() > 0) {
            form.setForcedR(true);
        } else {
            form.setForcedR(false);
        }
        if(Utility.isTrue(nJV.getNote().getForcedEachLogin())){
        	form.setForcedEachLogin(true);
        	form.setForcedR(true);
        }else{
        	form.setForcedEachLogin(false);
        }
        form.setTopicId(nTD.getNoteId());
        form.setTopicName(nJV.getNote().getTitle());
        form.setLocaleCd(nJV.getNote().getLocaleCd());

        if(ae.size()>0) {
            return ae;
        }
        return ae;

    }

    private static ActionErrors initParagraph(HttpServletRequest request, UiFrameForm pForm)
        throws Exception {

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

    public static ActionErrors saveArticle(HttpServletRequest request, ActionForm pForm) throws Exception {

        UiFrameForm form = (UiFrameForm)pForm;
        ActionErrors ae;
        ae = checkRequest(request, form);
        if (ae.size() > 0) {
            return ae;
        }

        form.setMode2("editArticle");
        HttpSession session = request.getSession();
        APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
        if (null == factory) {
            String errorMess = ClwI18nUtil.getMessage(request, "news.admin.errors.noApi", null);
            throw new Exception(errorMess);
        }
        CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
        UserData userD = appUser.getUser();
        String userName = userD.getUserName();

        Note noteEjb = factory.getNoteAPI();
        NoteJoinView njVw = form.getNote();

        njVw.getNote().setTitle(form.getTopicName());
        njVw.getNote().setLocaleCd(form.getLocaleCd());
        NoteJoinView njVwInterface = NoteJoinView.createValue();
        NoteData noteD = njVw.getNote();
        //Set accountId
       int accountId = appUser.getUserAccount().getAccountId();
       noteD.setBusEntityId(accountId);

       //Set Note Topic property ID
       PropertyData pD = form.getNoteTopicData();
        if (pD != null) {
          noteD.setPropertyId(pD.getPropertyId());
        }
        else {
          PropertyData p = PropertyData.createValue();
          p = noteEjb.addNoteTopic(NewsTopicName,
                                   RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC,
                                   userName);
          noteD.setPropertyId(p.getPropertyId());
          noteD.setNoteId(0);
       }
       // set newsEffDate
        String newsEffDateS = form.getNewsEffDate();
        if(Utility.isSet(newsEffDateS)){
           Date eDate = Utility.parseDate(newsEffDateS);
           if(eDate==null) {
               String errorMess = ClwI18nUtil.getMessage(request, "news.admin.errors.invalidPostedDate", null);
               //errorMess = errorMess + ": " + newsEffDateS;
               ae.add("error",
                       new ActionError("error.simpleGenericError",errorMess));
               //return ae;
           }
           noteD.setEffDate(eDate);
        }
        // set newsExpDate
        String newsExpDateS = form.getNewsExpDate();
        if(Utility.isSet(newsExpDateS)){
            Date xDate = Utility.parseDate(newsExpDateS);
            if(xDate==null) {
                String errorMess = ClwI18nUtil.getMessage(request, "news.admin.errors.invalidEndDate", null);
                //errorMess = errorMess + ": " + newsExpDateS;
                ae.add("error",
                        new ActionError("error.simpleGenericError",errorMess));
                //return ae;
            }
            noteD.setExpDate(xDate);
        }
        
        if((noteD.getExpDate()).before(noteD.getEffDate())){
        	String errorMess = ClwI18nUtil.getMessage(request, "news.admin.errors.endDateBeforePostedDate", null);
            ae.add("error",new ActionError("error.simpleGenericError",errorMess));
        }
        
        noteD.setCounter(form.getNote().getNote().getCounter());
        
        boolean isForcedEachLogin = form.getForcedEachLogin();
        if(isForcedEachLogin){
        	noteD.setForcedEachLogin("true");
        	noteD.setCounter(1);
        }else{
        	noteD.setForcedEachLogin("false");
        }
        
        // Set Note Title
        String title = form.getTopicName();
        if(!Utility.isSet(title)){
          String mess = ClwI18nUtil.getMessage(request, "news.admin.errors.EmptyArticleTitle", null);
          ae.add("error",new ActionError("error.simpleGenericError",mess));
          //return ae;
        }
        noteD.setTitle(title);

        njVwInterface.setNote(noteD);
        NoteTextDataVector ntDV = new NoteTextDataVector();
        njVwInterface.setNoteText(ntDV);

        NoteTextData ntD = form.getParagraph();
        String noteText = ntD.getNoteText();
        if(Utility.isSet(noteText)){
            ntDV.add(ntD);
        } else {
            if(noteD.getNoteId()<=0){
                String mess = ClwI18nUtil.getMessage(request, "news.admin.errors.EmptyContent", null);
                ae.add("error",new ActionError("error.simpleGenericError",mess));
                //return ae;
            }
        }
        if(ae.size()>0){
        	return ae;
        }
        njVwInterface = noteEjb.saveNote(njVwInterface,userName);
        form.setNote(njVwInterface);
//        topicDV = noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.NEWS_NOTE_TOPIC);
        initNewsList(session, form);

        return ae;
    }

///=======
    private static void setImageSlots(UiFrameDetailForm form) {
        UiFrameSlotViewVector slots = form.getSlots(); //form.getImageSlots();
        Iterator i = slots.iterator();
        int j = 0;
        while (i.hasNext()) {
            UiFrameSlotView slot = (UiFrameSlotView)i.next();
            if (slot.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.IMAGE)) {
                FormFile slotFile = form.getImageSlotFile(j);
                if (slotFile != null && !slotFile.getFileName().equals("")) {
                    try {
                        String imageFileName = "slot-" + String.valueOf(form.getFrameData().getBusEntityId()) + "-" +
                                String.valueOf(System.currentTimeMillis()) + "-" + slotFile.getFileName();
                        slot.setImageData(slotFile.getFileData());
                        slot.setValue(imageFileName);
                    } catch (Exception e ) {
                        e.printStackTrace();
                    }
                }
            } else {
                slot.setValue(form.getTextValue(j));
                slot.setImageData(new byte[0]);
            }
            j++;
         }
    }

///>>>>>>> 1.3
    private static ActionErrors initFAQList (HttpSession session, ActionForm pForm) throws Exception {
      ActionErrors ae = new ActionErrors();
     // String topicName = "FAQ";
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
      APIAccess factory = (APIAccess)  session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
         throw new Exception("No APIAccess.");
      }
      UiFrameForm form = (UiFrameForm) pForm;
      form.setNoteTopicName(FAQTopicName);
      /**-----------------  FAQLogic ------------- */
      NoteJoinViewVector faqViewV = null;
      PropertyDataVector propDV = null;
      Note noteBean = factory.getNoteAPI();

    //Create criteria for searching
      DBCriteria dbCrit = new DBCriteria();
      try {
        propDV=noteBean.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC);

        form.setNoteTopicData(null);

        Iterator iter = propDV.iterator();
        while (iter.hasNext()) {
          PropertyData propItem = (PropertyData) iter.next();
          if (propItem.getValue().equals(FAQTopicName)) {
            //form.setNoteTopicId(propItem.getPropertyId());
            form.setNoteTopicData(propItem);
            break;
          }
        }
        if (form.getNoteTopicData()==null) {
          ae.add("error", new ActionError("error.systemError", "FAQ topic is not is not found for user. It will be created with first FAQ."));
          //return ae;
        }
        int storeId = appUser.getUserStore().getStoreId();
        faqViewV = noteBean.getNotesForUser(appUser.getUser().getUserId(), dbCrit, FAQTopicName, storeId);
        form.setNoteJoinViewVector(faqViewV);

        int[] selIds = form.getSelectorBox();
        form.setSelectorBox(new int[selIds.length]);
        form.setMode2("viewArticles");
      }
      catch (Exception exc) {
        exc.printStackTrace();
      }
      /*----------------------------------------*/
      return ae;
    }

    public static ActionErrors saveFAQ(HttpServletRequest request, ActionForm pForm) throws Exception {

      UiFrameForm form = (UiFrameForm) pForm;
      ActionErrors ae;
      ae = checkRequest(request, form);
      if (ae.size() > 0) {
        return ae;
      }
      //boolean exists = false;
      form.setMode2("editArticle");
      HttpSession session = request.getSession();
      APIAccess factory = (APIAccess) session.getAttribute(Constants.APIACCESS);
      if (null == factory) {
        String errorMess = ClwI18nUtil.getMessage(request, "news.admin.errors.noApi", null);
        throw new Exception(errorMess);
      }
      CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
      UserData userD = appUser.getUser();
      String userName = userD.getUserName();

      Note noteEjb = factory.getNoteAPI();

      NoteJoinView njVw = form.getNote();
      NoteData noteD = njVw.getNote();

     //Set accountId
      int accountId = appUser.getUserAccount().getAccountId();
      noteD.setBusEntityId(accountId);

      //Set Note Topic property ID
      PropertyData pD = form.getNoteTopicData();

       if (pD != null) {
         //noteEjb.updateNoteTopic(pD, userName);
         noteD.setPropertyId(pD.getPropertyId());
       }
       else {
         PropertyData p = PropertyData.createValue();
         p = noteEjb.addNoteTopic(FAQTopicName,
                                  RefCodeNames.PROPERTY_TYPE_CD.ACCOUNT_NOTE_TOPIC,
                                  userName);
         noteD.setPropertyId(p.getPropertyId());
         noteD.setNoteId(0);
      }
      // Set Note Title
      String title = form.getTopicName();
      if (!Utility.isSet(title)) {
        String mess = ClwI18nUtil.getMessage(request,
                                             "news.admin.errors.EmptyArticleTitle", null);
                                             ae.add("error", new ActionError("error.simpleGenericError", mess));
        //return ae;
      }
      noteD.setTitle(title);
      noteD.setLocaleCd(form.getLocaleCd());
      

      // Set Post Date
      String effDateS = form.getNewsEffDate();
      if (Utility.isSet(effDateS)) {
        Date eDate = Utility.parseDate(effDateS);
        if (eDate == null) {
          String errorMess = ClwI18nUtil.getMessage(request,
              "news.admin.errors.invalidPostedDate", null);
          //errorMess = errorMess + ": " + effDateS;
          ae.add("error",
                 new ActionError("error.simpleGenericError", errorMess));
          //return ae;
        }
        noteD.setEffDate(eDate);
      }
      /*
      String newsExpDateS = form.getNewsExpDate();
      if (Utility.isSet(newsExpDateS)) {
        Date xDate = Utility.parseDate(newsExpDateS);
        if (xDate == null) {
          String errorMess = ClwI18nUtil.getMessage(request,
              "news.admin.errors.wrongExpiryDateFormat", null);
          errorMess = errorMess + ": " + newsExpDateS;
          ae.add("error",
                 new ActionError("error.simpleGenericError", errorMess));
          return ae;
        }
        njVw.getNote().setExpDate(xDate);
      }
      njVw.getNote().setCounter(form.getNote().getNote().getCounter());


      String topicName = form.getTopicName();
      if (!Utility.isSet(topicName)) {
        String mess = ClwI18nUtil.getMessage(request,
            "news.admin.errors.EmptyArticleTitle", null);
        ae.add("error", new ActionError("error.simpleGenericError", mess));
        return ae;
      }
      */

      NoteTextDataVector ntDV = new NoteTextDataVector();

      NoteTextData ntD = form.getParagraph();
      String noteText = ntD.getNoteText();
      if (Utility.isSet(noteText)) {
        ntDV.add(ntD);
      } else {
        if (noteD.getNoteId() <= 0) {
          String mess = ClwI18nUtil.getMessage(request, "news.admin.errors.EmptyContent", null);
          ae.add("error", new ActionError("error.simpleGenericError", mess));
          //return ae;
        }
      }
      if(ae.size()>0){
      	return ae;
      }
      NoteJoinView njVwInterface = NoteJoinView.createValue();
      njVwInterface.setNote(noteD);
      njVwInterface.setNoteText(ntDV);

      njVwInterface = noteEjb.saveNote(njVwInterface, userName);
      form.setNote(njVwInterface);
      //topicDV = noteEjb.getNoteTopics(RefCodeNames.PROPERTY_TYPE_CD.NEWS_NOTE_TOPIC);
      initFAQList(session, form);

      //form.setMode2("viewArticles");
      return ae;
    }

}
