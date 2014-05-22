/**
 * Title: MessageDto 
 * Description: This is a data transfer object holding message data.
 */

package com.cleanwise.service.api.dto;

import java.io.Serializable;
import java.util.Date;

public class MessageDto implements Serializable {
	
	private static final long serialVersionUID = 97822198811148158L;
	private int _id;
	private String _author;
	private Date _datePosted;
	private String _title;
	private String _abstract;
	private String _content;
	
	/**
	 * @return the id
	 */
	public int getId() {
		return _id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		_id = id;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return _author;
	}
	
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		_author = author;
	}
	
	/**
	 * @return the datePosted
	 */
	public Date getDatePosted() {
		return _datePosted;
	}
	
	/**
	 * @param datePosted the datePosted to set
	 */
	public void setDatePosted(Date datePosted) {
		_datePosted = datePosted;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return _title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		_title = title;
	}
	
	/**
	 * @return the abstract
	 */
	public String getAbstract() {
		return _abstract;
	}
	
	/**
	 * @param abstract1 the abstract to set
	 */
	public void setAbstract(String abstractText) {
		_abstract = abstractText;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return _content;
	}
	
	/**
	 * @param contents the content to set
	 */
	public void setContent(String content) {
		_content = content;
	}

}
