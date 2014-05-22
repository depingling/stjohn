package com.cleanwise.service.api.session;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.util.Iterator;


import com.cleanwise.service.api.value.NoteData;
import com.cleanwise.service.api.value.NoteDataVector;


public class NoteBeanTest {
	private static final Logger log = Logger.getLogger(NoteBeanTest.class);

	@BeforeMethod
	public void setUp() throws Exception {
		
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
		
	}
	
	private NoteData createTestNote(int num, String locale, String title){
		NoteData note = new NoteData();
		note.setTitle(title);
		note.setLocaleCd(locale);
		note.setNoteId(num);
		return note;
	}
	
	@Test
	public void testFindNotesByLocale() {
		NoteDataVector testNotes = new NoteDataVector();
		testNotes.add(createTestNote(1,"en_US","1"));
		testNotes.add(createTestNote(2,"de_DE","2"));
		testNotes.add(createTestNote(3,"en_FRED","3"));
		testNotes.add(createTestNote(4,"en_USXX","4"));
		testNotes.add(createTestNote(5,"en_CA","5"));
		testNotes.add(createTestNote(6,"en","6"));
		testNotes.add(createTestNote(7,"de","7"));
		testNotes.add(createTestNote(8,"IT","8"));
		testNotes.add(createTestNote(9,"","9"));
		testNotes.add(createTestNote(10,"it","10"));
		
		
		NoteBean noteBean = new NoteBean();
		
		try{
			//test non-standard locale:
			//notes 4, 6, and 9 should be returned
			log.info("Testing NON Standard user Locale!");
			String userLocale = "en_USXX";
			NoteDataVector results = (NoteDataVector) noteBean.findNotesByLocale(testNotes, userLocale);
			log.info(getNoteListDebugInfo(results));
			AssertJUnit.assertTrue("List of notes is not only 4, 6 and 9: "+getNoteListDebugInfo(results),results.size() == 3 && 
					verifyNotesListContains(results,4)&& 
					verifyNotesListContains(results,6)&& 
					verifyNotesListContains(results,9));
			

			//notes 9 should be returned
			log.info("Testing NO user locale (edge case)");
			userLocale = "";
			results = (NoteDataVector) noteBean.findNotesByLocale(testNotes, userLocale);
			log.info(getNoteListDebugInfo(results));
			AssertJUnit.assertTrue("List of notes is not only 9: "+getNoteListDebugInfo(results),results.size() == 1 && 
					verifyNotesListContains(results,9));
				
			
			//notes 1,6 and 9 should be returned
			log.info("Testing standard user locale");
			userLocale = "en_US";
			results = (NoteDataVector) noteBean.findNotesByLocale(testNotes, userLocale);
			log.info(getNoteListDebugInfo(results));
			AssertJUnit.assertTrue("List of notes is not only 1, 6 and 9: "+getNoteListDebugInfo(results),results.size() == 3 && 
					verifyNotesListContains(results,1)&& 
					verifyNotesListContains(results,6)&& 
					verifyNotesListContains(results,9));
			
			//notes 8 and 9 should be returned
			log.info("Testing standard user locale with only country and default match");
			userLocale = "ja_IT";
			results = (NoteDataVector) noteBean.findNotesByLocale(testNotes, userLocale);
			log.info(getNoteListDebugInfo(results));
			AssertJUnit.assertTrue("List of notes is not only 8 and 9: "+getNoteListDebugInfo(results),results.size() == 2 && 
					verifyNotesListContains(results,8)&& 
					verifyNotesListContains(results,9));
			
			
			//test title overriding
			Iterator<NoteData> it = testNotes.iterator();
			while(it.hasNext()){
				it.next().setTitle("title");
			}
			//notes 1 should be returned
			log.info("Testing exact match for same title");
			userLocale = "en_US";
			results = (NoteDataVector) noteBean.findNotesByLocale(testNotes, userLocale);
			log.info(getNoteListDebugInfo(results));
			AssertJUnit.assertTrue("List of notes is not only 1: "+getNoteListDebugInfo(results),results.size() == 1 && 
					verifyNotesListContains(results,1));
			
			//notes 1 should be returned
			log.info("Testing exact match");
			userLocale = "it_IT";
			results = (NoteDataVector) noteBean.findNotesByLocale(testNotes, userLocale);
			log.info(getNoteListDebugInfo(results));
			AssertJUnit.assertTrue("List of notes is not only 8: "+getNoteListDebugInfo(results),results.size() == 1 && 
					verifyNotesListContains(results,8));
			
			//notes 1 should be returned
			log.info("Testing same title for default");
			userLocale = "aa_AA";
			results = (NoteDataVector) noteBean.findNotesByLocale(testNotes, userLocale);
			log.info(getNoteListDebugInfo(results));
			AssertJUnit.assertTrue("List of notes is not only 9: "+getNoteListDebugInfo(results),results.size() == 1 && 
					verifyNotesListContains(results,9));
			
			//notes 1 should be returned
			log.info("Testing same title language match");
			userLocale = "en_GB";
			results = (NoteDataVector) noteBean.findNotesByLocale(testNotes, userLocale);
			log.info(getNoteListDebugInfo(results));
			AssertJUnit.assertTrue("List of notes is not only 6: "+getNoteListDebugInfo(results),results.size() == 1 && 
					verifyNotesListContains(results,6));
		}catch(Exception e){
			e.printStackTrace();
			AssertJUnit.assertTrue("Caught exception: "+e.getMessage(), false);
		}
	}
	
	/**
	 * Returns the notes passed in as a string suitable for debbuging messages and errors.
	 */
	private String getNoteListDebugInfo(NoteDataVector notes){
		String str = "Size = "+notes.size();
		Iterator<NoteData> it = notes.iterator();
		while(it.hasNext()){
			NoteData aNote = it.next();
			str = str + "["+aNote.getNoteId()+"-"+aNote.getLocaleCd()+"]";
			
		}
		return str;
	}
	
	/**
	 * Returns true if the id specified is in the list of notes that is passed in.
	 */
	private boolean verifyNotesListContains(NoteDataVector notes, int id){
		Iterator<NoteData> it = notes.iterator();
		while(it.hasNext()){
			NoteData aNote = it.next();
			if(aNote.getNoteId() == id){
				return true;
			}
		}
		return false;
	}
	
}
