package com.cleanwise.service.api.session;

/**
 * Title:        Note
 * Description:  Remote Interface for Note Stateless Session Bean
 * Purpose: Provides access to the services for managing notes
 * @author       Yuriy Kupershmidt
 */

import javax.ejb.*;
import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.DataNotFoundException;
import com.cleanwise.service.api.util.MultipleDataException;
import com.cleanwise.service.api.util.DuplicateNameException;
import com.cleanwise.service.api.util.DBCriteria;

/**
 * Remote interface for the <code>Note</code> stateless session bean.
 *
 */
public interface Note extends javax.ejb.EJBObject
{
    /** Gets all notes topics
     * @param pNoteTypeCd type of notes
     * @return a set of PropertyData objcects
     * @throws RemoteException
     */
    public PropertyDataVector getNoteTopics(String pNoteTypeCd)
    throws RemoteException;

    public NoteDataVector getNotes(String pNoteTypeCd) throws RemoteException;

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
    throws RemoteException;


    /** Gets note title and text data
     * @param pNoteId note id
     * @return NoteJoinVeiw object
     * @throws RemoteException
     */
    public NoteJoinView getNote(int pNoteId)
    throws RemoteException;

    /** Gets site Crc notes
     * @param pSiteId site id
     * @return NoteJoinVeiwVector object ordered in reversal order (noteId desc, noteTextId desc)
     * @throws RemoteException
     */
    public NoteJoinViewVector getSiteCrcNotes(int pSiteId)
    throws RemoteException;

    /** Deletes note from database
     * @param pNoteId note id
     * @throws RemoteException
     */
    public void deleteNote(int pNoteId)
    throws RemoteException;

    public void deleteArticles(int pNoteId, int[] pArticles)
        throws RemoteException;

    /** Deletes note text from database. If text has more than one page, removes all pages
     * @param pNoteTextId note text id
     * @throws RemoteException
     */
    public void deleteNoteText(int pNoteTextId)
    throws RemoteException;

    /** Updates or inserts note
     * @param pNoteJoin note with text
     * @param pUser user login name
     * @return NoteJoinVeiw object
     * @throws RemoteException
     */
    public NoteJoinView saveNote(NoteJoinView pNoteJoin, String pUser)
    throws RemoteException;

    /** Updates or inserts note
     * @param pNoteJoin note with text
     * @param pUser user login name
     * @param pDescFl returns records in descending order if true
     * @return NoteJoinVeiw object
     * @throws RemoteException
     */
    public NoteJoinView saveNote(NoteJoinView pNoteJoin, String pUser, boolean pDescFl)
    throws RemoteException;

    public void saveNoteAttachment(int noteId, String attFileName, byte[] data) throws RemoteException;

    /** Counts number of notes for the topic
     * @param pTopicId  property id
     * @return number of notes to the topic
     * @throws RemoteException
     */
    public int getNumnberOfNotes(int pTopicId)
    throws RemoteException;

    /** Removes topic from property table
     * @param pTopicId  property id
     * @throws RemoteException
     */
    public void deleteNoteTopic(int pTopicId)
    throws RemoteException;

    /** Adds topic
     * @param pTopicName  topic name
     * @param pTopicType note type
     * @param pUser user login name
     * @throws RemoteException
     */
    public PropertyData addNoteTopic(String pTopicName, String pTopicType, String pUser)
    throws RemoteException;

    /** Updates existing topic
     * @param pTopic  topic to update
     * @param pUser user login name
     * @throws RemoteException
     */
    public void updateNoteTopic(PropertyData pTopic, String pUser)
    throws RemoteException;

    /** Deletes note attachments
     * @param pNoteId note id
     * @param pFileNames attachment file names
     * @throws RemoteException
     */
    public void deleteNoteAttachments(int pNoteId, String[] pFileNames)
            throws RemoteException;

    /**
     * Get not read Notes for User
     * @param userId
     * @return
     * @throws RemoteException
     */
    public NoteJoinViewVector getActualNotesForUser(int userId)
            throws RemoteException;
     /**
     * Get All Notes for User
     * @param userId
     * @return
     * @throws RemoteException
     */
    public NoteJoinViewVector getNotesForUser(int userId, int storeId)
             throws RemoteException;
      /**
     * Increase Counter value for Note
     * @param noteId
     * @param userId
     * @return true if increased false if it is impossible
     * @throws RemoteException
     */
    public boolean setReadToNote(int noteId, int userId)
              throws RemoteException;
   /** Gets actual note title and text data
     * @param pNoteId note id
     * @param userId
    * @return NoteJoinVeiw object
     * @throws RemoteException
     */
    public NoteJoinView getActualNote(int pNoteId, int userId)
            throws RemoteException;
    /**
     * Get All Notes for User by DBCriteria
     * @param userId
     * @param noteDbCrit
     * @return NoteJoinViewVector
     * @throws RemoteException
     */
    public NoteJoinViewVector getNotesForUser(int userId, DBCriteria noteDbCrit, int storeId)
            throws RemoteException;

    /**
     * Return true if user has notes
     * @param userId
     * @return
     * @throws RemoteException
     */
    public boolean hasUserNotes(int userId, int storeId)
            throws RemoteException;

    public NoteViewVector getNoteTitlesForUser(int userId, String topicValue,  DBCriteria dbCrit, int rowsToReturn)
        throws RemoteException;

    public NoteJoinViewVector getNoteJoinViewForUser(int userId, String topicValue, DBCriteria dbCrit) 
            throws RemoteException;

    public NoteJoinViewVector getNotesForUserExcludeDuplicateTitles(int userId, DBCriteria noteDbCrit, int storeId)
        throws RemoteException;
    public NoteJoinViewVector getNotesForUser(int userId, DBCriteria noteDbCrit, String pTopicName, int storeId)
        throws RemoteException;
    public List findNotesByLocale( List noteDV, String pLocaleCd) throws RemoteException;
}
