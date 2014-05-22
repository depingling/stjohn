package com.cleanwise.view.logic;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.NoteDataAccess;
import com.cleanwise.service.api.session.Note;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.view.forms.UserNoteMgrForm;
import com.cleanwise.view.utils.CleanwiseUser;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.utils.Constants;
import com.cleanwise.view.utils.SelectableObjects;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.apache.struts.action.ActionErrors;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.XMLSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Title:
 * Description:
 * Purpose:
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         04.12.2007
 * Time:         11:22:01
 *
 * @author Evgeny Vlasov, TrinitySoft, Inc.
 */
public class UserNoteMgrLogic {
    public static String className = "UserNoteMgrLogic";

    public static ActionErrors init(HttpServletRequest request, UserNoteMgrForm form) {
        HttpSession session = request.getSession();
        ActionErrors ae = new ActionErrors();

        SelectableObjects currentSearchResult = null;
        if (form != null) {
            currentSearchResult = form.getSearchResult();
        }

        form = new UserNoteMgrForm();
        form.setSearchResult(currentSearchResult);
        session.setAttribute("USER_NOTE_MGR_FORM", form);
        return ae;
    }



    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
     */
    private static void error(String message, Exception e) {
        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

    }

    public static ActionErrors search(HttpServletRequest request, UserNoteMgrForm form) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (form != null) {
            APIAccess factory = APIAccess.getAPIAccess();
            Note noteBean = factory.getNoteAPI();
            CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

            //Create criteria for searching
            DBCriteria dbCrit = new DBCriteria();
            if ((form.getSearchField()!=null)&&(form.getSearchField().trim().length()>0)){
                if(RefCodeNames.SEARCH_TYPE_CD.BEGINS.equals(form.getSearchType())){
                    dbCrit.addLikeIgnoreCase(NoteDataAccess.TITLE, form.getSearchField().trim() + '%');
                } else {
                    dbCrit.addLikeIgnoreCase(NoteDataAccess.TITLE, '%' + form.getSearchField().trim() + '%');
                }
            }

            if((form.getEffDate()!=null)&&(form.getEffDate().trim().length()>0)){

            }

            if((form.getExpDate()!=null)&&(form.getExpDate().trim().length()>0)){

            }
            int storeId = user.getUserStore().getStoreId();

            NoteJoinViewVector result = noteBean.getNotesForUser(user.getUser().getUserId(), dbCrit, storeId);

            form.setSearchResult(new SelectableObjects(new NoteJoinViewVector(), result, null));
            session.setAttribute("USER_NOTE_MGR_FORM", form);
        }

        return ae;
    }

    public static ActionErrors initRead(HttpServletRequest request, UserNoteMgrForm form) throws Exception {
        boolean isPrev = true;
        boolean isNext = true;

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (form != null) {
            APIAccess factory = APIAccess.getAPIAccess();
            Note noteBean = factory.getNoteAPI();
            IdVector noteIds = new IdVector();

            String userNoteIdStr = request.getParameter("noteId");
            if(Utility.isSet(userNoteIdStr)){
                noteIds.add(new Integer(userNoteIdStr));
            } else if(form.getSearchResult()!=null && !form.getSearchResult().getNewlySelected().isEmpty()) {
                List selected = form.getSearchResult().getNewlySelected();
                noteIds = getNoteIds(selected);
            }

            NoteJoinViewVector notes = getNotes(noteBean, noteIds);
            form.setAllNoteForRead(notes);

            if (!notes.isEmpty()) {
                form.setNoteForRead((NoteJoinView) notes.get(0));
            }

            {
                form.setReadedNote(new NoteJoinViewVector());
                isPrev = false;
            }

            if((form.getAllNoteForRead() == null) || (form.getAllNoteForRead().size()==1)){
                isNext = false;
            }

            session.setAttribute("USER_NOTE_MGR_FORM", form);
        }

        return ae;
    }

    private static NoteJoinViewVector getNotes(Note noteBean, IdVector noteIds) throws Exception {
        NoteJoinViewVector result = new NoteJoinViewVector();
        Iterator it = noteIds.iterator();
        while (it.hasNext()) {
            NoteJoinView note = noteBean.getNote(((Integer) it.next()).intValue());
            result.add(note);
        }
        return result;
    }

    private static IdVector getNoteIds(List vector) {
        IdVector ids = new IdVector();
        if (vector != null && !vector.isEmpty()) {
            Iterator it = vector.iterator();
            while (it.hasNext()) {
                NoteJoinView note = (NoteJoinView) it.next();
                ids.add(new Integer(note.getNote().getNoteId()));
            }
        }
        return ids;
    }

    public static ActionErrors nextRead(HttpServletRequest request, UserNoteMgrForm form, HttpServletResponse response) throws Exception {
        boolean isNext = true;
        boolean isPrev = true;

        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        if (form != null) {

            if ((form.getAllNoteForRead() != null) && (!form.getAllNoteForRead().isEmpty())) {

                if (form.getAllNoteForRead().remove(form.getNoteForRead())) {

                    NoteJoinViewVector readedNote = form.getReadedNote();
                    if (readedNote == null) {
                        readedNote = new NoteJoinViewVector();
                    }

                    readedNote.add(form.getNoteForRead());
                    form.setReadedNote(readedNote);

                    if(!form.getAllNoteForRead().isEmpty()){
                        form.setNoteForRead((NoteJoinView) form.getAllNoteForRead().get(0));
                    }
                }
            }

            if((form.getReadedNote() == null) || (form.getReadedNote().isEmpty())){
                isPrev = false;
            }

            if((form.getAllNoteForRead() == null) || (form.getAllNoteForRead().size()==1)){
                isNext = false;
            }

            session.setAttribute("USER_NOTE_MGR_FORM", form);

            if ("async".equals(request.getParameter("requestType"))) {
                Element xml = createXmlResponse(form.getNoteForRead(), isNext, isPrev);
                response.setContentType("application/xml");
                response.setHeader("Cache-Control", "no-cache");
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(xml);
            }
        }
        return ae;
    }

    public static ActionErrors prevRead(HttpServletRequest request, UserNoteMgrForm form, HttpServletResponse response) throws Exception {
        ActionErrors ae = new ActionErrors();
        HttpSession session = request.getSession();

        boolean isNext = true;
        boolean isPrev = true;

        if (form != null) {
            if (form.getReadedNote() != null && !form.getReadedNote().isEmpty()) {

                NoteJoinView prevNote;
                //get the last readed note and delete that from readed notes massive
                prevNote = (NoteJoinView) form.getReadedNote().remove(form.getReadedNote().size() - 1);

                if(form.getNoteForRead().equals(prevNote)){
                    if (prevNote != null) {
                        NoteJoinViewVector allNoteForRead = form.getAllNoteForRead();
                        if (allNoteForRead == null) {
                            allNoteForRead = new NoteJoinViewVector();
                        }
                        allNoteForRead.add(prevNote);
                        form.setAllNoteForRead(allNoteForRead);
                    }
                    prevNote = (NoteJoinView) form.getReadedNote().remove(form.getReadedNote().size() - 1);
                }

                if (prevNote != null) {
                    NoteJoinViewVector allNoteForRead = form.getAllNoteForRead();
                    if (allNoteForRead == null) {
                        allNoteForRead = new NoteJoinViewVector();
                    }

                    allNoteForRead.add(0, prevNote);
                    form.setAllNoteForRead(allNoteForRead);
                    form.setNoteForRead(prevNote);
                }
            }

            if((form.getReadedNote() == null) || (form.getReadedNote().isEmpty())){
                isPrev = false;
            }

            if((form.getAllNoteForRead() == null) || (form.getAllNoteForRead().size()==1)){
                isNext = false;
            }


            session.setAttribute("USER_NOTE_MGR_FORM", form);

            if ("async".equals(request.getParameter("requestType"))) {
                Element xml = createXmlResponse(form.getNoteForRead(), isNext, isPrev);
                response.setContentType("application/xml");
                response.setHeader("Cache-Control", "no-cache");
                OutputFormat format = new OutputFormat( Method.XML, "UTF-8", true );
                XMLSerializer serializer = new XMLSerializer(response.getWriter(), format);
                serializer.serialize(xml);
            }
        }

        return ae;
    }

    public static Element createXmlResponse(NoteJoinView noteForRead, boolean isNext, boolean isPrev) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;

        try {
        	docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
        }
        Document doc = docBuilder.getDOMImplementation().createDocument("", "Note", null);

        Element root = doc.getDocumentElement();
        if (noteForRead != null) {

            root.setAttribute("Id", String.valueOf(noteForRead.getNote().getNoteId()));
            root.setAttribute("isnext", String.valueOf(isNext));
            root.setAttribute("isprev", String.valueOf(isPrev));
            Element node;

            node = doc.createElement("Title");
            node.appendChild(doc.createTextNode(String.valueOf(noteForRead.getNote().getTitle())));
            root.appendChild(node);
            if (noteForRead.getNoteText() != null && !noteForRead.getNoteText().isEmpty()) {
                Iterator it = noteForRead.getNoteText().iterator();
                while (it.hasNext()) {
                    node = doc.createElement("Text");
                    node.appendChild(doc.createTextNode(String.valueOf(((NoteTextData) it.next()).getNoteText())));
                    root.appendChild(node);
                }
            }

            if (noteForRead.getNoteAttachment() != null && !noteForRead.getNoteAttachment().isEmpty()) {
                Iterator it = noteForRead.getNoteAttachment().iterator();
                while (it.hasNext()) {
                    NoteAttachmentData noteImg = (NoteAttachmentData) it.next();
                    String path = ClwCustomizer.getTemplateImgRelativePath();
                    String tempFile = IOUtilities.convertToTempFile(noteImg.getBinaryData(), noteImg.getFileName());
                    if (Utility.isSet(tempFile)) {
                        if (Utility.isSet(path)) {
                            path += tempFile;
                            node = doc.createElement("Image");
                            node.appendChild(doc.createTextNode(path.replace((char) 92, '/')));
                            root.appendChild(node);
                        }
                    }
                }
            }
        }
        return root;
    }
}
