package com.cleanwise.service.api.session;

/**
 * Title:        NoteBean
 * Description:  Bean implementation for Note Stateless Session Bean
 * Purpose:      Provides access to the methods for establishing and maintaining notes.
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Yuriy Kupershmidt, CleanWise, Inc.
 */

import javax.ejb.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.rmi.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;

import org.apache.log4j.Category;

import oracle.sql.BLOB;

import com.cleanwise.service.api.framework.*;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DBCriteria;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.DBAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.APIAccess;
import com.cleanwise.view.utils.Constants;

import java.sql.*;


public class NoteBean extends ApplicationServicesAPI
{

	private static final Category log = Category.getInstance(NoteBean.class);
    /**
     *
     */
    public NoteBean() {}

    /**
     *
     */
    public void ejbCreate() throws CreateException, RemoteException {}

    /** Gets all notes topics
     * @param pNoteTypeCd type of notes
     * @return a set of PropertyData objcects
     * @throws RemoteException
     */
    public PropertyDataVector getNoteTopics(String pNoteTypeCd)
            throws RemoteException
    {
        Connection conn = null;
        try {
            ArrayList topicAL = new ArrayList();
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD, pNoteTypeCd);
            dbc.addOrderBy(PropertyDataAccess.CLW_VALUE);
            PropertyDataVector propertyDV = PropertyDataAccess.select(conn,dbc);
            return propertyDV;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    public NoteDataVector getNotes(String pNoteTypeCd) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(NoteDataAccess.NOTE_TYPE_CD, pNoteTypeCd);
            //dbc.addOrderBy(NoteDataAccess.TITLE);
            NoteDataVector notesDataVector = NoteDataAccess.select(conn, dbc);
            return notesDataVector;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Gets a notes, using key words as filter
     * @param pTopicId note topic property id
     * @param pBusEntityId bus enitity id
     * @param pKeyWords a set of keywords (any of keywords is enough to pick the note)
     * @param pTextAlsoFl a flag to expand selection on note text (not only title)
     * @return a set of NoteView objects
     * @throws RemoteException
     */
    public NoteViewVector getNoteTitles(int pTopicId, int pBusEntityId,
                                        ArrayList pKeyWords, boolean pTextAlsoFl)
            throws RemoteException
    {
        Connection conn = null;
        try {
            NoteViewVector noteVwV = new NoteViewVector();
            conn = getConnection();
            PropertyData propD = PropertyDataAccess.select(conn,pTopicId);
            String topic = propD.getValue();
            DBCriteria dbc = new DBCriteria();

            IdVector noteKwIdV = new IdVector();
            IdVector allIdV = new IdVector();
            if(pKeyWords!=null && pKeyWords.size()>0) {
                for(Iterator iter = pKeyWords.iterator(); iter.hasNext();){
                    dbc = new DBCriteria();
                    String kw = (String) iter.next();
                    dbc.addEqualTo(NoteDataAccess.PROPERTY_ID,pTopicId);
                    dbc.addEqualTo(NoteDataAccess.BUS_ENTITY_ID, pBusEntityId);
                    String noteIdCond =
                            NoteDataAccess.getSqlSelectIdOnly(NoteDataAccess.NOTE_ID,dbc);
                    dbc.addContainsIgnoreCase(NoteDataAccess.TITLE, kw);
                    dbc.addOrderBy(NoteDataAccess.NOTE_ID);
                    IdVector noteIdV =
                            NoteDataAccess.selectIdOnly(conn,NoteDataAccess.NOTE_ID,dbc);
                    if(pTextAlsoFl) {
                        dbc = new DBCriteria();
                        dbc.addOneOf(NoteTextDataAccess.NOTE_ID,noteIdCond);
                        dbc.addContainsIgnoreCase(NoteTextDataAccess.NOTE_TEXT, kw);
                        dbc.addOrderBy(NoteTextDataAccess.NOTE_ID);
                        IdVector noteIdV1 =
                                NoteTextDataAccess.selectIdOnly(conn,NoteTextDataAccess.NOTE_ID,dbc);
                        IdVector mergIdV = mergeOrderedLists(noteIdV, noteIdV1);
                        noteKwIdV.add(mergIdV);
                        allIdV.addAll(mergIdV);
                    } else {
                        noteKwIdV.add(noteIdV);
                        allIdV.addAll(noteIdV);
                    }
                }

                dbc = new DBCriteria();
                dbc.addOneOf(NoteDataAccess.NOTE_ID, allIdV);
                dbc.addOrderBy(NoteDataAccess.NOTE_ID);
                NoteDataVector noteDV = NoteDataAccess.select(conn,dbc);

                for(Iterator iter = noteDV.iterator(); iter.hasNext();) {
                    NoteData nD = (NoteData) iter.next();
                    NoteView nVw = NoteView.createValue();
                    noteVwV.add(nVw);
                    int noteId = nD.getNoteId();
                    nVw.setNoteId(noteId);
                    nVw.setPropertyId(pTopicId);
                    nVw.setBusEntityId(nD.getBusEntityId());
                    nVw.setTopic(topic);
                    nVw.setTitle(nD.getTitle());
                    nVw.setModDate(nD.getEffDate());
                    nVw.setSearchRate(0);
                    nVw.setKeyWords(new ArrayList());
                }

                for(Iterator iter = noteKwIdV.iterator(), kwIter = pKeyWords.iterator();
                    iter.hasNext(); ) {
                    IdVector kwIdV = (IdVector) iter.next();
                    String keyWord = (String)  kwIter.next();
                    for(Iterator iterKw = kwIdV.iterator(),noteIter = noteVwV.iterator();
                        iterKw.hasNext();) {
                        Integer idKwI = (Integer) iterKw.next();
                        while(noteIter.hasNext()) {
                            NoteView nVw = (NoteView) noteIter.next();
                            if(idKwI.intValue()==nVw.getNoteId()) {
                                nVw.setSearchRate(nVw.getSearchRate()+1);
                                nVw.getKeyWords().add(keyWord);
                                break;
                            }
                        }
                    }

                }
            } else {
                dbc = new DBCriteria();
                dbc.addEqualTo(NoteDataAccess.PROPERTY_ID,pTopicId);
                dbc.addEqualTo(NoteDataAccess.BUS_ENTITY_ID, pBusEntityId);
                NoteDataVector noteDV = NoteDataAccess.select(conn,dbc);
                for(Iterator iter = noteDV.iterator(); iter.hasNext();) {
                    NoteData nD = (NoteData) iter.next();
                    NoteView nVw = NoteView.createValue();
                    noteVwV.add(nVw);
                    int noteId = nD.getNoteId();
                    nVw.setNoteId(noteId);
                    nVw.setPropertyId(pTopicId);
                    nVw.setBusEntityId(nD.getBusEntityId());
                    nVw.setTopic(topic);
                    nVw.setTitle(nD.getTitle());
                    nVw.setModDate(nD.getEffDate());
                    nVw.setSearchRate(0);
                }

            }

            return noteVwV;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    private IdVector mergeOrderedLists(List pMainL, List pAddL)
    {
        IdVector resultL = new IdVector();
        if(pMainL==null) pMainL = new IdVector();
        if(pAddL==null) pAddL = new IdVector();
        Iterator iter = pMainL.iterator();
        Iterator iter1 = pAddL.iterator();
        Integer ival = (iter.hasNext())? (Integer) iter.next() : null ;
        Integer ival1 = (iter1.hasNext())? (Integer) iter1.next() : null ;

        while (ival!=null || ival1 !=null) {
            if(ival!=null && ival1 !=null) {
                int comp = ival.compareTo(ival1);
                if(comp==0) {
                    resultL.add(ival);
                    ival = (iter.hasNext())? (Integer) iter.next() : null ;
                    ival1 = (iter1.hasNext())? (Integer) iter1.next() : null ;
                } else if(comp<0) {
                    resultL.add(ival);
                    ival = (iter.hasNext())? (Integer) iter.next() : null ;
                } else {
                    resultL.add(ival1);
                    ival1 = (iter1.hasNext())? (Integer) iter1.next() : null ;
                }
            } else if(ival!=null) {
                resultL.add(ival);
                ival = (iter.hasNext())? (Integer) iter.next() : null ;
            } else {
                resultL.add(ival1);
                ival1 = (iter1.hasNext())? (Integer) iter1.next() : null ;
            }
        }
        return resultL;
    }

    /**
     * Get not read Notes for User
     * @param userId
     * @return
     * @throws RemoteException
     */
    public NoteJoinViewVector getActualNotesForUser(int userId) throws RemoteException
    {

        Connection conn = null;
        NoteJoinViewVector noteJoinViewVector = new NoteJoinViewVector();

        try {

            conn = getConnection();
            UserData userData = UserDataAccess.select(conn, userId);
            APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(UserAssocDataAccess.USER_ID,userId);
            dbc.addEqualTo(UserAssocDataAccess.USER_ASSOC_CD,
                    RefCodeNames.USER_ASSOC_CD.SITE);
            String userSiteReq = UserAssocDataAccess.getSqlSelectIdOnly(UserAssocDataAccess.BUS_ENTITY_ID, dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityAssocDataAccess.BUS_ENTITY1_ID,userSiteReq);
            dbc.addEqualTo(BusEntityAssocDataAccess.BUS_ENTITY_ASSOC_CD,
                    RefCodeNames.BUS_ENTITY_ASSOC_CD.SITE_ACCOUNT);

            String userAcctReq = BusEntityAssocDataAccess.getSqlSelectIdOnly(BusEntityAssocDataAccess.BUS_ENTITY2_ID,dbc);

            dbc = new DBCriteria();
            dbc.addOneOf(BusEntityDataAccess.BUS_ENTITY_ID,userAcctReq);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_STATUS_CD,
                    RefCodeNames.BUS_ENTITY_STATUS_CD.ACTIVE);
            dbc.addEqualTo(BusEntityDataAccess.BUS_ENTITY_TYPE_CD,
                    RefCodeNames.BUS_ENTITY_TYPE_CD.ACCOUNT);
            log.info("accountIdSql: "+ BusEntityDataAccess.getSqlSelectIdOnly("*",dbc));

            IdVector accountIds = BusEntityDataAccess.selectIdOnly(conn,BusEntityDataAccess.BUS_ENTITY_ID,dbc);

            DBCriteria dbCritTopic = new DBCriteria();
            dbCritTopic.addEqualTo(PropertyDataAccess.CLW_VALUE, "INTERSTITIAL_MESSAGE");
            IdVector topicIdVector = PropertyDataAccess.selectIdOnly(conn, dbCritTopic);

            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addOneOf(NoteDataAccess.BUS_ENTITY_ID, accountIds);
            dbCrit.addOneOf(NoteDataAccess.PROPERTY_ID, topicIdVector);
            java.util.Date currDate = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date currDayDate = sdf.parse(sdf.format(currDate));
            dbCrit.addLessOrEqual(NoteDataAccess.EFF_DATE,currDayDate);
            dbCrit.addGreaterOrEqual(NoteDataAccess.EXP_DATE,currDayDate);
            dbCrit.addGreaterThan(NoteDataAccess.COUNTER,0);
            dbCrit.addIsNotNull(NoteDataAccess.TITLE);
            
            dbCrit.addOrderBy(NoteDataAccess.LOCALE_CD, true);
            dbCrit.addOrderBy(NoteDataAccess.TITLE);
            dbCrit.addOrderBy(NoteDataAccess.NOTE_ID);

            log.info("atviveNotesSql: "+ NoteDataAccess.getSqlSelectIdOnly("*",dbCrit));

            NoteDataVector noteDV = NoteDataAccess.select(conn, dbCrit);

            if(noteDV.size()==0) {
                return noteJoinViewVector;
            }
            //-----find Notes according to user's locale or Default
            noteDV = (NoteDataVector)findNotesByLocale( noteDV, userData.getPrefLocaleCd() ) ;
            //------------------------------------------------------

            NoteDataVector distinctNoteDV = new NoteDataVector();
            List<String> uniqueTitleNames = new LinkedList<String>();
            StringBuffer sb = new StringBuffer();
            String prevTitle = "";
            for(Iterator iter=noteDV.iterator(); iter.hasNext();) {
                NoteData nD = (NoteData) iter.next();
                String title = nD.getTitle();
                if(!uniqueTitleNames.contains(title)) {
                	uniqueTitleNames.add(title);
                    distinctNoteDV.add(nD);
                    if (sb.length() == 0) {
                        //sb.append(DBAccess.toQuoted(title));
                        sb.append("?");
                    } else {
                        sb.append(" ,?");
                        //sb.append(DBAccess.toQuoted(title));
                    }
                    prevTitle = title;
                }
            }

          if(Utility.isSet(sb.toString())){
            String getUserNotesSql =
              "SELECT una.note_id, una.note_counter, n.title " +
              " FROM clw_user_note_activity una JOIN clw_note n "+
              " ON una.note_id = n.note_id "+
              " AND n.title IN ("+sb.toString()+")" +
              " WHERE una.user_id = ?"+ //userId +
              " ORDER BY n.title ";
            log.info("userNoteActivitySql: "+ getUserNotesSql);

            PreparedStatement stmt = conn.prepareStatement(getUserNotesSql);
            int i = 1;
            prevTitle = "";
            for(Iterator iter=noteDV.iterator(); iter.hasNext();) {
                NoteData nD = (NoteData) iter.next();
                String title = nD.getTitle();
                if(!prevTitle.equals(title)) {
                    stmt.setString(i++, title);
                    prevTitle = title;
                }
            }
            stmt.setInt(i++, userId);
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int noteId = rs.getInt("note_id");
                int counter = rs.getInt("note_counter");
                String title = rs.getString("title");
                for(Iterator iter=distinctNoteDV.iterator(); iter.hasNext();) {
                    NoteData nD = (NoteData) iter.next();
                    if(title.equals(nD.getTitle())) {
                        if(counter<=0 && !nD.getForcedEachLogin().equalsIgnoreCase("true")) {
                            iter.remove();
                        } else {
                            nD.setNoteId(noteId);
                        }
                        break;
                    }
                }
            }
            rs.close();
            stmt.close();

            for(Iterator iter=distinctNoteDV.iterator(); iter.hasNext();) {
                NoteData nD = (NoteData) iter.next();
                int noteId = nD.getNoteId();
                NoteJoinView noteJoinView = getActualNote(noteId, userId);
                if((noteJoinView!=null)&&(noteJoinView.getNote()!=null)){
                    noteJoinViewVector.add(noteJoinView);
                }

            }
          }

            return noteJoinViewVector;

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /**
     * Get All Notes for User by DBCriteria, sort be add date and note id
     * @param userId
     * @param noteDbCrit
     * @return NoteJoinViewVector
     * @throws RemoteException
     */
    public NoteJoinViewVector getNotesForUser(int userId, DBCriteria noteDbCrit, int storeId ) throws RemoteException
    {
      return getNotesForUser( userId,  noteDbCrit, "INTERSTITIAL_MESSAGE", storeId);
    }
    /**
     * Get All Notes for User by DBCriteria, sort be add date and note id
     * @param userId
     * @param noteDbCrit
     * @return NoteJoinViewVector
     * @throws RemoteException
     */
    public NoteJoinViewVector getNotesForUser(int userId, DBCriteria noteDbCrit, String pTopicName, int storeId) throws RemoteException
    {

        Connection conn = null;
        NoteJoinViewVector noteJoinViewVector = new NoteJoinViewVector();

        try {

            conn = getConnection();
            APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();
//            IdVector userSiteIds = Utility.toIdVector(userBean.getSiteCollection(userData.getUserId()));
            Account accountBean = factory.getAccountAPI();
            //IdVector accountIds = new IdVector();
            IdVector accountIds = userBean.getUserAccountIds(userId, storeId, false);


            DBCriteria dbCritTopic = new DBCriteria();
            dbCritTopic.addLike(PropertyDataAccess.CLW_VALUE, pTopicName);
            IdVector topicIdVector = PropertyDataAccess.selectIdOnly(conn, dbCritTopic);

            noteDbCrit.addOneOf(NoteDataAccess.PROPERTY_ID, topicIdVector);
            noteDbCrit.addOneOf(NoteDataAccess.BUS_ENTITY_ID, accountIds);

            noteDbCrit.addOrderBy(NoteDataAccess.NOTE_ID, false);

            IdVector noteIds = NoteDataAccess.selectIdOnly(conn, noteDbCrit);

            Iterator noteIterator = noteIds.iterator();
            while(noteIterator.hasNext()){
                Integer noteId = (Integer) noteIterator.next();
                NoteJoinView noteJoinView = getNote(noteId.intValue());
                noteJoinViewVector.add(noteJoinView);
            }

            return noteJoinViewVector;

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /**
     * Return true if user has notes
     * @param userId
     * @return
     * @throws RemoteException
     */
    public boolean hasUserNotes(int userId, int storeId) throws RemoteException
    {
        Connection conn = null;
        try {

            conn = getConnection();
            APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();
            //IdVector userSiteIds = Utility.toIdVector(userBean.getSiteCollection(userData.getUserId()));
            IdVector accountIds = userBean.getUserAccountIds(userId, storeId, false);
            DBCriteria dbCritTopic = new DBCriteria();
            dbCritTopic.addLike(PropertyDataAccess.CLW_VALUE, "INTERSTITIAL_MESSAGE");
            IdVector topicIdVector = PropertyDataAccess.selectIdOnly(conn, dbCritTopic);

            DBCriteria dbCrit = new DBCriteria();
            if (topicIdVector != null && !topicIdVector.isEmpty()){
            	dbCrit.addOneOf(NoteDataAccess.PROPERTY_ID, topicIdVector);
            }
            dbCrit.addOneOf(NoteDataAccess.BUS_ENTITY_ID, accountIds);
//            dbCrit.addOrderBy(NoteDataAccess.ADD_DATE);
            dbCrit.addOrderBy(NoteDataAccess.NOTE_ID);

            IdVector noteIds = NoteDataAccess.selectIdOnly(conn, dbCrit);

            if(noteIds.isEmpty()){
                return false;
            }

            Iterator noteIterator = noteIds.iterator();
            return noteIterator.hasNext();

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }
    /**
     * Get All Notes for User
     * @param userId
     * @return
     * @throws RemoteException
     */
    public NoteJoinViewVector getNotesForUser(int userId, int storeId) throws RemoteException
    {

        Connection conn = null;
        NoteJoinViewVector noteJoinViewVector = new NoteJoinViewVector();

        try {

            conn = getConnection();
            UserData userData = UserDataAccess.select(conn, userId);
            APIAccess factory = new APIAccess();
            User userBean = factory.getUserAPI();
            IdVector userSiteIds = Utility.toIdVector(userBean.getSiteCollection(userData.getUserId()));
            Account accountBean = factory.getAccountAPI();
            IdVector accountIds = new IdVector();

            Iterator siteIterator = userSiteIds.iterator();
            while(siteIterator.hasNext()){
                Integer siteId = (Integer)siteIterator.next();
                accountIds.add(new Integer(accountBean.getAccountIdForSite(siteId.intValue())));
            }

            DBCriteria dbCrit = new DBCriteria();
            dbCrit.addOneOf(NoteDataAccess.BUS_ENTITY_ID, accountIds);
            dbCrit.addOrderBy(NoteDataAccess.ADD_DATE);
            dbCrit.addOrderBy(NoteDataAccess.NOTE_ID);

            IdVector noteIds = NoteDataAccess.selectIdOnly(conn, dbCrit);

            Iterator noteIterator = noteIds.iterator();
            while(noteIterator.hasNext()){
                Integer noteId = (Integer) noteIterator.next();
                NoteJoinView noteJoinView = getNote(noteId.intValue());
                noteJoinViewVector.add(noteJoinView);
            }

            return noteJoinViewVector;

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /**
     * Increase Counter value for Note
     * @param noteId
     * @param userId
     * @return true if increased false if it is impossible
     * @throws RemoteException
     */
    public boolean setReadToNote(int noteId, int userId) throws RemoteException
    {

        Connection conn = null;

        try {
            conn=getConnection();

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(NoteTextDataAccess.NOTE_ID, noteId);

            IdVector noteIds = NoteDataAccess.selectIdOnly(conn, dbc);

            Iterator noteIterator = noteIds.iterator();
            while(noteIterator.hasNext()){
                Integer curNoteId = (Integer) noteIterator.next();
                NoteData noteData = NoteDataAccess.select(conn, curNoteId.intValue());
                
                boolean isForcedEachLogin = false;
                if(Utility.isTrue(noteData.getForcedEachLogin())){
                	isForcedEachLogin=true;
                }
                
                if(!isForcedEachLogin && noteData.getCounter()>0){

                    DBCriteria userNoteAcivityCrit = new DBCriteria();
                    userNoteAcivityCrit.addEqualTo(UserNoteActivityDataAccess.NOTE_ID, noteData.getNoteId());
                    userNoteAcivityCrit.addEqualTo(UserNoteActivityDataAccess.USER_ID, userId);

                    UserNoteActivityDataVector userNoteActivityDataVector = UserNoteActivityDataAccess.select(conn, userNoteAcivityCrit);
                    Iterator usActIterator = userNoteActivityDataVector.iterator();
                    boolean updated = false;

                    while (usActIterator.hasNext()){

                        UserNoteActivityData userNoteActivityData = (UserNoteActivityData) usActIterator.next();

                        if(userNoteActivityData.getNoteCounter()>0){
                            userNoteActivityData.setNoteCounter(userNoteActivityData.getNoteCounter()-1);
                            UserNoteActivityDataAccess.update(conn, userNoteActivityData);
                        }
                        updated = true;
                    }

                    if(!updated){
                        UserNoteActivityData userNoteActivityData = UserNoteActivityData.createValue();
                        userNoteActivityData.setNoteId(noteData.getNoteId());
                        userNoteActivityData.setUserId(userId);
                        userNoteActivityData.setNoteCounter(noteData.getCounter()-1);
                       
                        UserNoteActivityDataAccess.insert(conn, userNoteActivityData);
                    }
                    
                    if(!isForcedEachLogin){
                    	noteData.setCounter(noteData.getCounter()-1);
                    }

                }else{
                    return false;
                }

            }

            return true;

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }
    /** Gets actual note title and text data
     * @param pNoteId note id
     * @param userId
     * @return NoteJoinVeiw object
     * @throws RemoteException
     */
    public NoteJoinView getActualNote(int pNoteId, int userId)
            throws RemoteException
    {
        Connection conn = null;
        NoteJoinView njVw = NoteJoinView.createValue();

        try {
            conn = getConnection();
            NoteData nD = NoteDataAccess.select(conn, pNoteId);
            java.util.Date date = new java.util.Date();

            if(nD.getCounter()>0
                    &&(nD.getExpDate().after(date))
                    &&(nD.getEffDate().before(date))){

                DBCriteria userNoteAcivityCrit = new DBCriteria();
                userNoteAcivityCrit.addEqualTo(UserNoteActivityDataAccess.NOTE_ID, nD.getNoteId());
                userNoteAcivityCrit.addEqualTo(UserNoteActivityDataAccess.USER_ID, userId);

                UserNoteActivityDataVector userNoteActivityDataVector = UserNoteActivityDataAccess.select(conn, userNoteAcivityCrit);
                Iterator usActIterator = userNoteActivityDataVector.iterator();
                boolean exists = false;
                boolean actual = false;

                while (usActIterator.hasNext()){

                    UserNoteActivityData userNoteActivityData = (UserNoteActivityData) usActIterator.next();

                    if(userNoteActivityData.getNoteCounter()>0){
                        actual = true;
                    }
                    exists = true;
                }

                if((!exists)||(exists&&actual)||nD.getForcedEachLogin().equalsIgnoreCase("true")){

                    DBCriteria dbc = new DBCriteria();
                    dbc.addEqualTo(NoteTextDataAccess.NOTE_ID,pNoteId);
                    dbc.addOrderBy(NoteTextDataAccess.SEQ_NUM);
                    dbc.addOrderBy(NoteTextDataAccess.PAGE_NUM);
                    NoteTextDataVector ntDV = NoteTextDataAccess.select(conn,dbc);
                    NoteTextDataVector ntDV1 = new NoteTextDataVector();
                    int prevSeq = -1;
                    NoteTextData noteTextD = null;

                    for (Iterator iter = ntDV.iterator();  iter.hasNext(); ) {
                        NoteTextData ntD = (NoteTextData) iter.next();
                        int seq = ntD.getSeqNum();
                        if(seq!=prevSeq) {
                            prevSeq = seq;
                            if(noteTextD!=null) {
                                ntDV1.add(noteTextD);
                            }
                            noteTextD = ntD;
                        } else {
                            String paragraph = noteTextD.getNoteText();
                            String paragraph1 = ntD.getNoteText();
                            if(paragraph==null){
                                paragraph = "";
                            }
                            if(paragraph1!=null){
                                paragraph += paragraph1;
                            }
                            noteTextD.setNoteText(paragraph);
                        }
                    }

                    if(noteTextD!=null) {
                        ntDV1.add(noteTextD);
                    }

                    njVw.setNote(nD);
                    njVw.setNoteText(ntDV1);

                    //Get note Attachment
                    dbc = new DBCriteria();
                    dbc.addEqualTo(NoteAttachmentDataAccess.NOTE_ID, pNoteId);
                    dbc.addOrderBy(NoteAttachmentDataAccess.FILE_NAME);
                    NoteAttachmentDataVector naDV = NoteAttachmentDataAccess.select(conn,dbc);
                    njVw.setNoteAttachment(naDV);

                } else {
                    return null;
                }
            }else{
                return null;
            }

            return njVw;

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }
    /** Gets note title and text data
     * @param pNoteId note id
     * @return NoteJoinVeiw object
     * @throws RemoteException
     */
    public NoteJoinView getNote(int pNoteId)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            NoteData nD = NoteDataAccess.select(conn,pNoteId);

            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(NoteTextDataAccess.NOTE_ID,pNoteId);
            dbc.addOrderBy(NoteTextDataAccess.SEQ_NUM);
            dbc.addOrderBy(NoteTextDataAccess.PAGE_NUM);
            NoteTextDataVector ntDV = NoteTextDataAccess.select(conn,dbc);
            NoteTextDataVector ntDV1 = new NoteTextDataVector();
            int prevSeq = -1;
            NoteTextData noteTextD = null;
            for (Iterator iter = ntDV.iterator();  iter.hasNext(); ) {
                NoteTextData ntD = (NoteTextData) iter.next();
                int seq = ntD.getSeqNum();
                if(seq!=prevSeq) {
                    prevSeq = seq;
                    if(noteTextD!=null) {
                        ntDV1.add(noteTextD);
                    }
                    noteTextD = ntD;
                } else {
                    String paragraph = noteTextD.getNoteText();
                    String paragraph1 = ntD.getNoteText();
                    if(paragraph==null) paragraph = "";
                    if(paragraph1!=null) paragraph += paragraph1;
                    noteTextD.setNoteText(paragraph);
                }
            }
            if(noteTextD!=null) {
                ntDV1.add(noteTextD);
            }
            NoteJoinView njVw = NoteJoinView.createValue();
            njVw.setNote(nD);
            njVw.setNoteText(ntDV1);

            //Get note Attachment
            dbc = new DBCriteria();
            dbc.addEqualTo(NoteAttachmentDataAccess.NOTE_ID,pNoteId);
            dbc.addOrderBy(NoteAttachmentDataAccess.FILE_NAME);
            NoteAttachmentDataVector naDV = NoteAttachmentDataAccess.select(conn,dbc);
            njVw.setNoteAttachment(naDV);
            return njVw;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Gets site Crc notes
     * @param pSiteId site id
     * @return NoteJoinVeiwVector object ordered in reversal order (noteId desc, noteTextId desc)
     * @throws RemoteException
     */
    public NoteJoinViewVector getSiteCrcNotes(int pSiteId)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            NoteJoinViewVector noteJoinVwV = new NoteJoinViewVector();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,
                    RefCodeNames.PROPERTY_TYPE_CD.SITE_NOTE_TOPIC);
            dbc.addEqualTo(PropertyDataAccess.PROPERTY_STATUS_CD,
                    RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            dbc.addEqualTo(PropertyDataAccess.CLW_VALUE,"CRC Site Notes");
            IdVector  topicIdV =
                    PropertyDataAccess.selectIdOnly(conn,PropertyDataAccess.PROPERTY_ID, dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(NoteDataAccess.BUS_ENTITY_ID,pSiteId);
            dbc.addOneOf(NoteDataAccess.PROPERTY_ID,topicIdV);
            dbc.addOrderBy(NoteDataAccess.NOTE_ID,false);
            NoteDataVector noteDV = NoteDataAccess.select(conn,dbc);
            if(noteDV.size()==0) {
                return noteJoinVwV;
            }

            IdVector noteIdV = new IdVector();
            for(Iterator iter = noteDV.iterator(); iter.hasNext();) {
                NoteData nD = (NoteData) iter.next();
                int noteId = nD.getNoteId();
                noteIdV.add(new Integer(noteId));
            }

            dbc = new DBCriteria();
            dbc.addOneOf(NoteTextDataAccess.NOTE_ID,noteIdV);
            dbc.addOrderBy(NoteTextDataAccess.NOTE_ID,false);
            dbc.addOrderBy(NoteTextDataAccess.SEQ_NUM,false);
            dbc.addOrderBy(NoteTextDataAccess.PAGE_NUM);
            NoteTextDataVector ntDV = NoteTextDataAccess.select(conn,dbc);
            NoteTextDataVector ntDV1 = new NoteTextDataVector();
            int prevSeq = -1;
            int prevNoteId = -1;
            NoteTextData noteTextD = null;
            for (Iterator iter = ntDV.iterator();  iter.hasNext(); ) {
                NoteTextData ntD = (NoteTextData) iter.next();
                int noteId = ntD.getNoteId();
                int seq = ntD.getSeqNum();

                if(seq!=prevSeq || noteId!=prevNoteId ) {
                    prevSeq = seq;
                    if(noteTextD!=null) {
                        ntDV1.add(noteTextD);
                    }
                    noteTextD = ntD;
                } else {
                    String paragraph = noteTextD.getNoteText();
                    String paragraph1 = ntD.getNoteText();
                    if(paragraph==null) paragraph = "";
                    if(paragraph1!=null) paragraph += paragraph1;
                    noteTextD.setNoteText(paragraph);
                }
            }
            if(noteTextD!=null) {
                ntDV1.add(noteTextD);
            }


            noteTextD = null;
            for(Iterator iter = noteDV.iterator(),iter1 = ntDV1.iterator();
                iter.hasNext();) {
                NoteData nD = (NoteData) iter.next();
                NoteJoinView njVw = NoteJoinView.createValue();
                noteJoinVwV.add(njVw);
                njVw.setNote(nD);
                NoteTextDataVector noteTextDV = new NoteTextDataVector();
                njVw.setNoteText(noteTextDV);
                int noteId = nD.getNoteId();
                noteIdV.add(new Integer(noteId));
                while(noteTextD!=null || iter1.hasNext()){
                    if(noteTextD==null) {
                        noteTextD = (NoteTextData) iter1.next();
                    }
                    int nId = noteTextD.getNoteId();
                    if(nId==noteId) {
                        noteTextDV.add(noteTextD);
                        noteTextD = null;
                        continue;
                    }
                    if(nId>noteId) {//should never happen
                        noteTextD = null;
                        continue;
                    }
                    break;
                }
            }
            return noteJoinVwV;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Deletes note from database
     * @param pNoteId note id
     * @throws RemoteException
     */
    public void deleteNote(int pNoteId)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(NoteTextDataAccess.NOTE_ID,pNoteId);
            NoteTextDataAccess.remove(conn,dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(NoteAttachmentDataAccess.NOTE_ID,pNoteId);
            NoteAttachmentDataAccess.remove(conn,dbc);

            dbc = new DBCriteria();
            dbc.addEqualTo(UserNoteActivityDataAccess.NOTE_ID,pNoteId);
            UserNoteActivityDataAccess.remove(conn,dbc);

            NoteDataAccess.remove(conn,pNoteId);

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Deletes note text from database. If text has more than one page, removes all pages
     * @param pNoteTextId note text id
     * @throws RemoteException
     */
    public void deleteNoteText(int pNoteTextId)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            NoteTextData ntD = NoteTextDataAccess.select(conn, pNoteTextId);
            int noteId = ntD.getNoteId();
            int seqNum = ntD.getSeqNum();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(NoteTextDataAccess.NOTE_ID,noteId);
            dbc.addEqualTo(NoteTextDataAccess.SEQ_NUM,seqNum);
            NoteTextDataAccess.remove(conn,dbc);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Updates or inserts note
     * @param pNoteJoin note with text
     * @param pUser user login name
     * @return NoteJoinVeiw object
     * @throws RemoteException
     */
    public NoteJoinView saveNote(NoteJoinView pNoteJoin, String pUser)
            throws RemoteException {
        return saveNote(pNoteJoin, pUser, false);
    }

    /** Updates or inserts note
     * @param pNoteJoin note with text
     * @param pUser user login name
     * @param pDescFl returns records in descending order if true
     * @return NoteJoinVeiw object
     * @throws RemoteException
     */
    public NoteJoinView saveNote(NoteJoinView pNoteJoin, String pUser, boolean pDescFl)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc;
            NoteData nD = pNoteJoin.getNote();
            int noteId = 0;
            if(nD!=null) {
                noteId = nD.getNoteId();
                if(noteId==0) {
                    nD.setAddBy(pUser);
                    nD.setModBy(pUser);
                    nD = NoteDataAccess.insert(conn,nD);
                    noteId = nD.getNoteId();
                } else {
                    nD.setModBy(pUser);
                    NoteDataAccess.update(conn,nD);
                }
            }
            NoteTextDataVector noteTextDV = pNoteJoin.getNoteText();
            for(Iterator iter = noteTextDV.iterator(); iter.hasNext();) {
                NoteTextData ntD = (NoteTextData) iter.next();
                String userFirstName = ntD.getUserFirstName();
                String userLastName = ntD.getUserLastName();
                if(noteId==0) noteId = ntD.getNoteId();
                if(noteId==0) {
                    String errorMess = "Can't add note text. No note id provided";
                    throw new Exception(errorMess);
                }
                String noteText = ntD.getNoteText();
                if(noteText==null || noteText.trim().length()==0) {
                    noteText="";
                }
                int seqNum = ntD.getSeqNum();
                NoteTextDataVector ntDV = null;
                if(seqNum>0) {
                    dbc = new DBCriteria();
                    dbc.addEqualTo(NoteTextDataAccess.NOTE_ID,noteId);
                    dbc.addEqualTo(NoteTextDataAccess.SEQ_NUM,seqNum);
                    dbc.addOrderBy(NoteTextDataAccess.PAGE_NUM);
                    ntDV = NoteTextDataAccess.select(conn,dbc);
                } else {
                    String sql =
                            "select max(SEQ_NUM) "+
                                    " from CLW_NOTE_TEXT "+
                                    " where NOTE_ID = "+noteId;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    rs.next();
                    int maxNum = rs.getInt(1);
                    rs.close();
                    stmt.close();
                    seqNum = maxNum+1;
                    ntDV = new NoteTextDataVector();
                }
                Iterator iterBase = ntDV.iterator();
                for(int page=0; noteText.length()>0; page++) {
                    String ttt = null;
                    if(noteText.length()>4000){
                        ttt = noteText.substring(0,4000);
                        noteText = noteText.substring(4000);
                    } else {
                        ttt = noteText;
                        noteText = "";
                    }
                    if(iterBase.hasNext()){
                        ntD = (NoteTextData) iterBase.next();
                        ntD.setPageNum(page);
                        ntD.setNoteText(ttt);
                        ntD.setModBy(pUser);
                        NoteTextDataAccess.update(conn, ntD);
                    } else {
                        ntD = NoteTextData.createValue();
                        ntD.setUserFirstName(userFirstName);
                        ntD.setUserLastName(userLastName);
                        ntD.setNoteId(noteId);
                        ntD.setSeqNum(seqNum);
                        ntD.setPageNum(page);
                        ntD.setNoteText(ttt);
                        ntD.setAddBy(pUser);
                        ntD.setModBy(pUser);
                        ntD = NoteTextDataAccess.insert(conn, ntD);
                    }
                }
                while(iterBase.hasNext()){
                    ntD = (NoteTextData) iterBase.next();
                    int id = ntD.getNoteTextId();
                    NoteTextDataAccess.remove(conn,id);
                }
            }
            dbc = new DBCriteria();
            dbc.addEqualTo(NoteTextDataAccess.NOTE_ID,noteId);
            dbc.addOrderBy(NoteTextDataAccess.SEQ_NUM,!pDescFl);
            dbc.addOrderBy(NoteTextDataAccess.PAGE_NUM);
            NoteTextDataVector ntDV = NoteTextDataAccess.select(conn,dbc);
            int prevSeqNum = -1;
            NoteTextData prevNtD = null;
            for(Iterator iter=ntDV.iterator(); iter.hasNext();){
                NoteTextData ntD = (NoteTextData) iter.next();
                int seqNum = ntD.getSeqNum();
                if(seqNum==prevSeqNum) {
                    String noteText = prevNtD.getNoteText()+ntD.getNoteText();
                    prevNtD.setNoteText(noteText);
                    iter.remove();
                } else {
                    prevNtD = ntD;
                }
            }

            NoteJoinView njVw = NoteJoinView.createValue();
            njVw.setNote(nD);
            njVw.setNoteText(ntDV);
            njVw.setNoteAttachment(pNoteJoin.getNoteAttachment());
            saveNoteAttachments(conn, njVw, pUser);
            return njVw;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    private void saveNoteAttachments(Connection pCon, NoteJoinView pNoteJoin, String pUser)
            throws Exception {
        NoteAttachmentDataVector newAttachmentDV = pNoteJoin.getNoteAttachment();
        int noteId = pNoteJoin.getNote().getNoteId();
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(NoteAttachmentDataAccess.NOTE_ID, noteId);
        NoteAttachmentDataVector naDV = NoteAttachmentDataAccess.select(pCon,dbc);
        pNoteJoin.setNoteAttachment(naDV);
        if(newAttachmentDV==null || newAttachmentDV.size()==0) {
            return;
        }
        for(Iterator iter = newAttachmentDV.iterator(); iter.hasNext(); ) {
            NoteAttachmentData newNoteAttachD = (NoteAttachmentData) iter.next();
            String fileName = newNoteAttachD.getFileName();
            boolean foundFl = false;
            for(Iterator iter1 = naDV.iterator(); iter1.hasNext();) {
                NoteAttachmentData naD = (NoteAttachmentData) iter1.next();
                String fn = naD.getFileName();
                if(fileName.equals(fn)) {
                    foundFl = true;
                    naD.setModBy(pUser);
                    NoteAttachmentDataAccess.update(pCon,naD);
                    break;
                }
            }
            if(!foundFl){
                newNoteAttachD.setNoteId(noteId);
                newNoteAttachD.setAddBy(pUser);
                newNoteAttachD.setModBy(pUser);
                newNoteAttachD = NoteAttachmentDataAccess.insert(pCon,newNoteAttachD);
                naDV.add(newNoteAttachD);
            }
        }

    }

    public void saveNoteAttachment(int noteId, String attFileName, byte[] data) throws RemoteException {
        Connection pCon = null;
        try {
            pCon = getConnection();
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (NamingException e1) {
            e1.printStackTrace();
        }
        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(NoteAttachmentDataAccess.NOTE_ID, noteId);
        dbc.addEqualTo(NoteAttachmentDataAccess.FILE_NAME, attFileName);
        try {
            InputStream attachmStream = new ByteArrayInputStream(data);
            String sql = "select binary_data, file_name from CLW_NOTE_ATTACHMENT " +
                    " where note_id = ?  and file_name = ? for update ";

            PreparedStatement stmt = pCon.prepareStatement(sql);
            stmt.setInt(1, noteId);
            stmt.setString(2, attFileName);

            ResultSet rs = stmt.executeQuery();
            if (false) {
            	
            	BLOB blob = null;
                byte[] blobValueBytes = null;
                OutputStream outputStream = null;
                if (ORACLE.equals(databaseName)) {
                   blob = (BLOB) rs.getBlob(1); //Oracle DB
                   outputStream = blob.getBinaryOutputStream();
                } else {
                   blobValueBytes = rs.getBytes(1);  //Enterprise DB (Postgres DB)
                   outputStream.write(blobValueBytes);
                }            	
            	/*** old code
            	 * 
                BLOB blob = (BLOB) rs.getBlob(1);
                OutputStream outputStream = blob.getBinaryOutputStream();
                
                ***/
                int bytesRead = 0;
                try {
                    while((bytesRead = attachmStream.read()) != -1) {
                        outputStream.write(bytesRead);
                    }
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String aaa = rs.getString(2);
                rs.close();
                stmt.close();
//	        try {
//				outputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

                String updateBlobSql =
                        "update CLW_NOTE_ATTACHMENT set binary_data=? where note_id = ? and file_name = ?";

                PreparedStatement pstmt = pCon.prepareStatement(updateBlobSql);
                if (ORACLE.equals(databaseName)) {
                    pstmt.setBlob(1, blob);
                } else {
                	pstmt.setBytes(1, blobValueBytes);
                }
                pstmt.setInt(2, noteId);
                pstmt.setString(3, attFileName);
                pstmt.executeUpdate();
                pstmt.close();
            }

            NoteAttachmentDataVector noteAttachmDV = NoteAttachmentDataAccess.select(pCon, dbc);
            PreparedStatement pStmt = pCon.prepareStatement("UPDATE CLW_NOTE_ATTACHMENT SET binary_data=? WHERE note_id=? AND file_name=?");
            int attachmentFileLength = (int) data.length;
            pStmt.setBinaryStream(1, attachmStream, attachmentFileLength);
            pStmt.setInt(2, noteId);
            pStmt.setString(3, attFileName);
            int res = pStmt.executeUpdate();
            pStmt.close();
            attachmStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pCon.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    protected int setByteToOracleBlob(BLOB blob, byte[] data) throws Exception {

        OutputStream out = blob.setBinaryStream(0);
        out.write(data);
        out.flush();
        out.close();

        return data.length;

    }

    /** Counts number of notes for the topic
     * @param pTopicId  property id
     * @return number of notes to the topic
     * @throws RemoteException
     */
    public int getNumnberOfNotes(int pTopicId)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(NoteDataAccess.PROPERTY_ID, pTopicId);
            IdVector noteIdV = NoteDataAccess.selectIdOnly(conn,dbc);
            int cnt = noteIdV.size();
            return cnt;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Removes topic from property table
     * @param pTopicId  property id
     * @throws RemoteException
     */
    public void deleteNoteTopic(int pTopicId)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            PropertyDataAccess.remove(conn,pTopicId);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Adds topic
     * @param pTopicName  topic name
     * @param pTopicType note type
     * @param pUser user login name
     * @throws RemoteException
     */
    public PropertyData addNoteTopic(String pTopicName, String pTopicType, String pUser)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(PropertyDataAccess.PROPERTY_TYPE_CD,pTopicType);
            dbc.addEqualTo(PropertyDataAccess.CLW_VALUE,pTopicName);
            PropertyDataVector topicDV = PropertyDataAccess.select(conn,dbc);
            if(topicDV.size()>0) {
                return (PropertyData) topicDV.get(0);
            }
            PropertyData propD = PropertyData.createValue();
            propD.setValue(pTopicName);
            propD.setShortDesc(pTopicType);
            propD.setPropertyTypeCd(pTopicType);
            propD.setPropertyStatusCd(RefCodeNames.PROPERTY_STATUS_CD.ACTIVE);
            propD.setModBy(pUser);
            propD.setAddBy(pUser);
            propD = PropertyDataAccess.insert(conn, propD);
            return propD;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }
    /** Updates existing topic
     * @param pTopic  topic to update
     * @param pUser user login name
     * @throws RemoteException
     */
    public void updateNoteTopic(PropertyData pTopic, String pUser)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            int topicId = pTopic.getPropertyId();
            pTopic.setModBy(pUser);
            PropertyDataAccess.update(conn,pTopic);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Gets note attachemsts
     * @param pNoteId note id
     * @return set NoteAttachmentData obejects
     * @throws RemoteException
     */
    public NoteAttachmentDataVector getNoteAttachments(int pNoteId)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            dbc.addEqualTo(NoteAttachmentDataAccess.NOTE_ID,pNoteId);
            dbc.addOrderBy(NoteAttachmentDataAccess.FILE_NAME);
            NoteAttachmentDataVector naDV = NoteAttachmentDataAccess.select(conn,dbc);
            return naDV;
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /** Deletes note attachments
     * @param pNoteId note id
     * @param pFileNames attachment file names
     * @throws RemoteException
     */
    public void deleteNoteAttachments(int pNoteId, String[] pFileNames)
            throws RemoteException
    {
        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();
            LinkedList fileNameLL = new LinkedList();
            if(pFileNames==null || pFileNames.length==0){
                return;
            }
            for(int ii=0; ii<pFileNames.length; ii++) {
                fileNameLL.add(pFileNames[ii]);
            }
            dbc.addEqualTo(NoteAttachmentDataAccess.NOTE_ID,pNoteId);
            dbc.addOneOf(NoteAttachmentDataAccess.FILE_NAME,fileNameLL);
            NoteAttachmentDataAccess.remove(conn,dbc);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    public void deleteArticles(int pNoteId, int[] pArticles)
        throws RemoteException {

        Connection conn = null;
        try {
            conn = getConnection();
            DBCriteria dbc = new DBCriteria();

            LinkedList fileNameLL = new LinkedList();
            if(pArticles==null || pArticles.length==0){
                return;
            }
            for(int ii=0; ii<pArticles.length; ii++) {
                fileNameLL.add(pArticles[ii]);
                deleteNoteTopic(pArticles[ii]);
                deleteNote(pArticles[ii]);
            }
//            dbc.addEqualTo(NoteAttachmentDataAccess.NOTE_ID,pNoteId);
//            dbc.addOneOf(NoteAttachmentDataAccess.FILE_NAME,fileNameLL);
//            NoteAttachmentDataAccess.remove(conn,dbc);
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    /**
 * Get All Notes Titles for User according to db Criteria and topic Value
 * This method is most likely only used for the xpedx news jsp. This logic needs to be refactored.
 * @param userId
 * @param topicValue
 * @param dbCrit
 * @return
 * @throws RemoteException
 */
public NoteViewVector getNoteTitlesForUser(int userId, String topicValue, DBCriteria dbCrit, int rowsToReturn) throws RemoteException
{

    Connection conn = null;
    NoteViewVector noteVwV = new NoteViewVector();
    try {
        conn = getConnection();

        dbCrit = createUserNotesCriteria(conn, userId, topicValue, dbCrit);
        NoteDataVector noteDV = NoteDataAccess.select(conn, dbCrit, rowsToReturn);

        //-----find Notes according to user's locale or Default
        UserData userData = UserDataAccess.select(conn, userId);
        noteDV = (NoteDataVector)findNotesByLocale( noteDV, userData.getPrefLocaleCd() ) ;
        //------------------------------------------------------

        for (Iterator iter = noteDV.iterator(); iter.hasNext(); ) {
          NoteData nD = (NoteData) iter.next();
            NoteView nVw = NoteView.createValue();
            nVw.setNoteId(nD.getNoteId());
            nVw.setTitle(nD.getTitle());
            nVw.setModDate(nD.getEffDate());
            noteVwV.add(nVw);
        }
        
         return noteVwV;

    } catch (Exception exc) {
        exc.printStackTrace();
        throw new RemoteException(exc.getMessage());
    } finally {
        try {
            conn.close();
        }catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        }
    }
}

    public NoteJoinViewVector getNoteJoinViewForUser(int userId, String topicValue, DBCriteria dbCrit) throws RemoteException
    {

        Connection conn = null;
        NoteJoinViewVector noteJoinViewVector = new NoteJoinViewVector();
        try {
            conn = getConnection();
            dbCrit = createUserNotesCriteria(conn, userId, topicValue, dbCrit);

            IdVector noteIds = NoteDataAccess.selectIdOnly(conn, dbCrit);

            Iterator noteIterator = noteIds.iterator();
            while(noteIterator.hasNext()){
                Integer noteId = (Integer) noteIterator.next();
                NoteJoinView noteJoinView = getNote(noteId.intValue());
                noteJoinViewVector.add(noteJoinView);
            }

            //-----find Notes according to user's locale or Default
            UserData userData = UserDataAccess.select(conn, userId);
            noteJoinViewVector = (NoteJoinViewVector) findNotesByLocale(noteJoinViewVector,  userData.getPrefLocaleCd());
            //------------------------------------------------------
            
            return noteJoinViewVector;

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new RemoteException(exc.getMessage());
        } finally {
            try {
                conn.close();
            }catch (Exception exc) {
                exc.printStackTrace();
                throw new RemoteException(exc.getMessage());
            }
        }
    }

    private DBCriteria createUserNotesCriteria(Connection conn, int userId, String topicValue, DBCriteria dbCrit) throws Exception
    {
        UserData userData = UserDataAccess.select(conn, userId);
        APIAccess factory = new APIAccess();
        User userBean = factory.getUserAPI();
        IdVector userSiteIds = Utility.toIdVector(userBean.getSiteCollection(userData.getUserId()));
        Account accountBean = factory.getAccountAPI();
        IdVector accountIds = new IdVector();

        Iterator siteIterator = userSiteIds.iterator();
        while(siteIterator.hasNext()){
            Integer siteId = (Integer)siteIterator.next();
            Integer accountId = new Integer(accountBean.getAccountIdForSite(siteId.intValue()));
            if (!accountIds.contains(accountId)){
              accountIds.add(accountId);
            }
        }

        DBCriteria dbCritTopic = new DBCriteria();
        dbCritTopic.addEqualTo(PropertyDataAccess.CLW_VALUE, topicValue);
        IdVector topicIdVector = PropertyDataAccess.selectIdOnly(conn, dbCritTopic);

        if (dbCrit == null){
          dbCrit = new DBCriteria();
        }

        if (topicIdVector != null){
         dbCrit.addOneOf(NoteDataAccess.PROPERTY_ID, topicIdVector);
        }

        dbCrit.addOneOf(NoteDataAccess.BUS_ENTITY_ID, accountIds);

        DBCriteria dbCritLocale = new DBCriteria();
        dbCritLocale.addEqualTo(NoteDataAccess.LOCALE_CD, userData.getPrefLocaleCd());
        DBCriteria dbCritLocale2 = new DBCriteria();
        dbCritLocale2.addIsNull(NoteDataAccess.LOCALE_CD);
        dbCritLocale.addOrCriteria(dbCritLocale2);
        dbCrit.addCondition("("+ dbCritLocale.getWhereClause()+")");
        dbCrit.addOrderBy(NoteDataAccess.ADD_DATE, false);        

        return dbCrit;
    }


    public NoteJoinViewVector getNotesForUserExcludeDuplicateTitles(int userId, DBCriteria noteDbCrit, int storeId)
    throws RemoteException {
        NoteJoinViewVector buffer = getNotesForUser(userId, noteDbCrit, storeId);
        NoteJoinViewVector result = new NoteJoinViewVector();
        Set existTitle = new HashSet();
        for (int i = 0; buffer != null && i < buffer.size(); i++) {
            NoteJoinView item = (NoteJoinView) buffer.get(i);
            String title = item.getNote().getTitle();
            if (existTitle.contains(title) == false) {
                result.add(item);
                existTitle.add(title);
            }
        }
        return result;
    }


    
    /**
     * Filters the list of notes according to the locale code of the user and the locale code of the note.
     * The logic will return any note that does not specify a locale; or if the locale of the note specifies
     * only one or the other of the country or the language than it will return it if it matches the users country
     * or language.  If the note specifies both language and country it has to match the language and country 
     * of the user
     * @param noteDV the list of notes to be filtered.
     * @param userLocaleCd the users local code.
     * @return the filtered list of NoteData objects or NoteJoinView objects (returns what was passed in)
     * @throws RemoteException
     */
	public List findNotesByLocale(List noteDV, String userLocaleCd)
			throws RemoteException {
		List retNoteDV = null;
		Map noteMap = new HashMap();

		if (noteDV instanceof NoteDataVector) {
			retNoteDV = new NoteDataVector();

		} else if (noteDV instanceof NoteJoinViewVector) {
			retNoteDV = new NoteJoinViewVector();
		}
		log.debug("user's LocaleCd =" + userLocaleCd);

		NoteLocaleSpecificityComp locComp = new NoteLocaleSpecificityComp();
		for (int i = 0; i < noteDV.size(); i++) {
			Object obj = noteDV.get(i);
			String noteLocaleCd = null;
			String noteTitle = null;
			if (obj instanceof NoteData) {
				noteLocaleCd = ((NoteData) obj).getLocaleCd();
				noteTitle = ((NoteData) obj).getTitle();
			} else if (obj instanceof NoteJoinView) {
				noteLocaleCd = ((NoteJoinView) obj).getNote().getLocaleCd();
				noteTitle = ((NoteJoinView) obj).getNote().getTitle();
			}
			log.debug("noteLocaleCd =" + noteLocaleCd);
			if (!Utility.isSet(noteLocaleCd)) {
				log.debug("Note locale not set; adding it to the list");
				Object existNote = noteMap.get(noteTitle);
				if(existNote == null){noteMap.put(noteTitle,obj);}
				else{noteMap.put(noteTitle,(locComp.compare(existNote, obj)>0) ? existNote : obj);}
				
					
			} else if (Utility.isSet(userLocaleCd)) {
				log.debug("users locale is set");

				Locale userLocale = Utility.parseLocaleCodeV2(userLocaleCd);
				Locale noteLocale = Utility.parseLocaleCodeV2(noteLocaleCd);

				log.debug("Note Country="+noteLocale.getCountry());
				log.debug("Note Language="+noteLocale.getLanguage());
				if (Utility.isSet(noteLocale.getLanguage())
						&& Utility.isSet(noteLocale.getCountry())) {
					// both lang and country are set
					if (noteLocale.getLanguage().equals(
							userLocale.getLanguage())
							&& noteLocale.getCountry().equals(
									userLocale.getCountry())) {
						log.debug("Note locale matched language and country; added to list!..");
						Object existNote = noteMap.get(noteTitle);
						if(existNote == null){noteMap.put(noteTitle,obj);}
						else{noteMap.put(noteTitle,(locComp.compare(existNote, obj)>0) ? existNote : obj);}
					}
				} else if (Utility.isSet(noteLocale.getLanguage())) {
					// language is set
					log.debug("Note locale matched language; added to list!..");
					if (noteLocale.getLanguage().equals(
							userLocale.getLanguage())) {
						Object existNote = noteMap.get(noteTitle);
						if(existNote == null){noteMap.put(noteTitle,obj);}
						else{noteMap.put(noteTitle,(locComp.compare(existNote, obj)>0) ? existNote : obj);}
					}
				} else if (Utility.isSet(noteLocale.getCountry())) {
					// country is set
					log.debug("Note country matched language; added to list!..");
					if (noteLocale.getCountry().equals(userLocale.getCountry())) {
						Object existNote = noteMap.get(noteTitle);
						if(existNote == null){noteMap.put(noteTitle,obj);}
						else{noteMap.put(noteTitle,(locComp.compare(existNote, obj)>0) ? existNote : obj);}
					}
				}
			}
		}
		

		retNoteDV.addAll(noteMap.values());
		log.debug("retNoteDV.size() =" + retNoteDV.size());
		return retNoteDV;
	}
	
	/**
	 * Will compare two NoteData or NoteJoinView objects for which
	 * locale is more specific.  So en_US is more specific than en
	 * and en is more specific than "".  Country is deemed more specific
	 * than language as well but there was no requirement for this.  This
	 * was done for the sake of consistency.
	 */
	private class NoteLocaleSpecificityComp implements Comparator{
		
		public int compare(Object note1, Object note2){
			String localeStr1;
			String localeStr2;
			if (note1 instanceof NoteData) {
				localeStr1 = ((NoteData) note1).getLocaleCd();
			} else if (note1 instanceof NoteJoinView) {
				localeStr1 = ((NoteJoinView) note1).getNote().getLocaleCd();
			} else {
				throw new ClassCastException("Unknown class.  Must be NoteDate or NoteJoinView");
			}
			
			if (note2 instanceof NoteData) {
				localeStr2 = ((NoteData) note2).getLocaleCd();
			} else if (note2 instanceof NoteJoinView) {
				localeStr2 = ((NoteJoinView) note2).getNote().getLocaleCd();
			}else {
				throw new ClassCastException("Unknown class.  Must be NoteDate or NoteJoinView");
			}

			Locale locale1 = Utility.parseLocaleCodeV2(localeStr1);
			Locale locale2 = Utility.parseLocaleCodeV2(localeStr2);
			int rankVal1 = 0;
			int rankVal2 = 0;
			if(Utility.isSet(locale1.getCountry())){
				rankVal1 = rankVal1+5;//for consistent results use diff rank values
			}
			if(Utility.isSet(locale1.getLanguage())){
				rankVal1++;
			}
			
			if(Utility.isSet(locale2.getCountry())){
				rankVal2 = rankVal2+5;//for consistent results use diff rank values
			}
			if(Utility.isSet(locale2.getLanguage())){
				rankVal2++;
			}
			return rankVal1 - rankVal2;

		}
	}

}
