/**
 * 
 */
package com.cleanwise.service.api.dto;

import java.io.Serializable;

/**
 * @author ssharma
 *
 */
public class CategoryStructureDto implements Serializable {
	
	private int level1;
	private int level2;
	private int level3;
	private int level4;
	private int lowestLevel;
	private String level1Name;
	private String level2Name;
	private String level3Name;
	private String level4Name;
	private String lowestLevelName;

	/**
	 * @return the level1
	 */
	public final int getLevel1() {
		return level1;
	}
	/**
	 * @param level1 the level1 to set
	 */
	public final void setLevel1(int level1) {
		this.level1 = level1;
	}
	/**
	 * @return the level2
	 */
	public final int getLevel2() {
		return level2;
	}
	/**
	 * @param level2 the level2 to set
	 */
	public final void setLevel2(int level2) {
		this.level2 = level2;
	}
	/**
	 * @return the level3
	 */
	public final int getLevel3() {
		return level3;
	}
	/**
	 * @param level3 the level3 to set
	 */
	public final void setLevel3(int level3) {
		this.level3 = level3;
	}
	/**
	 * @return the level4
	 */
	public final int getLevel4() {
		return level4;
	}
	/**
	 * @param level4 the level4 to set
	 */
	public final void setLevel4(int level4) {
		this.level4 = level4;
	}
	/**
	 * @return the lowestLevel
	 */
	public final int getLowestLevel() {
		return lowestLevel;
	}
	/**
	 * @param lowestLevel the lowestLevel to set
	 */
	public final void setLowestLevel(int lowestLevel) {
		this.lowestLevel = lowestLevel;
	}
	/**
	 * @return the level1Name
	 */
	public final String getLevel1Name() {
		return level1Name;
	}
	/**
	 * @param level1Name the level1Name to set
	 */
	public final void setLevel1Name(String level1Name) {
		this.level1Name = level1Name;
	}
	/**
	 * @return the level2Name
	 */
	public final String getLevel2Name() {
		return level2Name;
	}
	/**
	 * @param level2Name the level2Name to set
	 */
	public final void setLevel2Name(String level2Name) {
		this.level2Name = level2Name;
	}
	/**
	 * @return the level3Name
	 */
	public final String getLevel3Name() {
		return level3Name;
	}
	/**
	 * @param level3Name the level3Name to set
	 */
	public final void setLevel3Name(String level3Name) {
		this.level3Name = level3Name;
	}
	/**
	 * @return the level4Name
	 */
	public final String getLevel4Name() {
		return level4Name;
	}
	/**
	 * @param level4Name the level4Name to set
	 */
	public final void setLevel4Name(String level4Name) {
		this.level4Name = level4Name;
	}
	/**
	 * @return the lowestLevelName
	 */
	public final String getLowestLevelName() {
		return lowestLevelName;
	}
	/**
	 * @param lowestLevelName the lowestLevelName to set
	 */
	public final void setLowestLevelName(String lowestLevelName) {
		this.lowestLevelName = lowestLevelName;
	}
	
}
