package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Title: UiFrameForm Description: Form bean for the asset management in the
 * USERPORTAL. Purpose: Holds data for the asset management Copyright: Copyright
 * (c) 2006 Company: CleanWise, Inc. Date: 29.12.2006 Time: 13:43:34
 * 
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class UiFrameDetailForm extends ActionForm {

	private UiFrameView frame = UiFrameView.createValue();

	private List<FormFile> imageSlotFiles = new ArrayList<FormFile>();
	private List<String> textValues = new LinkedList<String>();

	public UiFrameData getFrameData() {
		return frame.getFrameData();
	}

	public void setFrameData(UiFrameData v) {
		frame.setFrameData(v);
	}

	public UiFrameSlotViewVector getSlots() {
		UiFrameSlotViewVector result = frame.getFrameSlotViewVector();
		if (result == null) {
			result = new UiFrameSlotViewVector();
		}
		return result;
	}

	public void setSlots(UiFrameSlotViewVector v) {
		frame.setFrameSlotViewVector(v);
	}

	public String getTextSlotValue(int i) {
		UiFrameSlotView slot = getSlotByType(RefCodeNames.SLOT_TYPE_CD.HTML_TEXT, i);
		if (slot != null) {
			return slot.getValue();
		} else {
			return "";
		}
	}

	public void setTextSlotValue(int i, String v) {
		UiFrameSlotView slot = getSlotByType(RefCodeNames.SLOT_TYPE_CD.HTML_TEXT, i);
		if (slot != null) {
			slot.setValue(v);
		}
	}

	public String getImageSlotValue(int i) {
		UiFrameSlotView slot = getSlotByType(RefCodeNames.SLOT_TYPE_CD.IMAGE, i);
		if (slot != null && slot.getValue() != null) {
			return slot.getValue();
		} else {
			return "";
		}
	}

	public void setImageSlotValue(int i, String v) {
		UiFrameSlotView slot = getSlotByType(RefCodeNames.SLOT_TYPE_CD.IMAGE, i);
		if (slot != null) {
			slot.setValue(v);
		}
	}

	public String getTextSlotNewWin(int i) {
		UiFrameSlotView slot = getSlotByType(
				RefCodeNames.SLOT_TYPE_CD.HTML_TEXT, i);
		if (Utility.isTrue(slot.getUrlTargetBlank(), false)) {
			return "1";
		} else {
			return "0";
		}
	}

	public void setTextSlotNewWin(int i, String v) {
		UiFrameSlotView slot = getSlotByType(
				RefCodeNames.SLOT_TYPE_CD.HTML_TEXT, i);
		if (slot != null) {
			if (Utility.isTrue(v, false)) {
				slot.setUrlTargetBlank("true");
			} else {
				slot.setUrlTargetBlank("false");
			}
		}
	}

	public String getImageSlotNewWin(int i) {
		UiFrameSlotView slot = getSlotByType(RefCodeNames.SLOT_TYPE_CD.IMAGE, i);
		if (Utility.isTrue(slot.getUrlTargetBlank(), false)) {
			return "1";
		} else {
			return "0";
		}
	}

	public void setImageSlotNewWin(int i, String v) {
		UiFrameSlotView slot = getSlotByType(RefCodeNames.SLOT_TYPE_CD.IMAGE, i);
		if (slot != null) {
			if (Utility.isTrue(v, false)) {
				slot.setUrlTargetBlank("true");
			} else {
				slot.setUrlTargetBlank("false");
			}
		}
	}

	public String getTextSlotUrl(int i) {
		UiFrameSlotView slot = getSlotByType(
				RefCodeNames.SLOT_TYPE_CD.HTML_TEXT, i);
		if (slot != null && slot.getUrl() != null) {
			return slot.getUrl();
		} else {
			return "";
		}
	}

	public void setTextSlotUrl(int i, String v) {
		UiFrameSlotView slot = getSlotByType(
				RefCodeNames.SLOT_TYPE_CD.HTML_TEXT, i);
		if (slot != null) {
			slot.setUrl(v);
		}
	}

	public String getImageSlotUrl(int i) {
		UiFrameSlotView slot = getSlotByType(RefCodeNames.SLOT_TYPE_CD.IMAGE, i);
		if (slot != null && slot.getUrl() != null) {
			return slot.getUrl();
		} else {
			return "";
		}
	}

	public void setImageSlotUrl(int i, String v) {
		UiFrameSlotView slot = getSlotByType(RefCodeNames.SLOT_TYPE_CD.IMAGE, i);
		if (slot != null) {
			slot.setUrl(v);
		}
	}

	public UiFrameSlotViewVector getSlotsByType(String pSlotType) {
		return frame.getSlotsByType(pSlotType);
	}

	public UiFrameSlotView getSlotByType(String pSlotType, int i) {
		return frame.getSlotByType(pSlotType, i);
	}

	public UiFrameSlotViewVector getImageSlots() {
		return getSlotsByType(RefCodeNames.SLOT_TYPE_CD.IMAGE);
	}

	public UiFrameSlotViewVector getTextSlots() {
		return getSlotsByType(RefCodeNames.SLOT_TYPE_CD.HTML_TEXT);
	}

	public FormFile getImageSlotFile(int i) {
		if (imageSlotFiles.size() > i) {
			Iterator<FormFile> iter = imageSlotFiles.iterator();
			while (iter.hasNext()) {
				FormFile ff = iter.next();
				if (ff == null)
					iter.remove();
			}
			return (FormFile) imageSlotFiles.get(i);
		} else {
			return null;
		}
	}

	public void setImageSlotFile(int i, FormFile v) {
		while (imageSlotFiles.size() < i) {
			imageSlotFiles.add(null);
		}
	
		imageSlotFiles.add(i, v);
	}

	public void setFrame(UiFrameView v) {
		frame = v;
	}

	public UiFrameView getFrame() {
		return frame;
	}

	// ----------------------
	public String getSlotValue(int i) {
		UiFrameSlotView slot = (UiFrameSlotView) getSlots().get(i);
		if (slot != null && slot.getValue() != null) {
			return slot.getValue();
		} else {
			return "";
		}
	}

	public void setSlotValue(int i, String v) {
		UiFrameSlotView slot = (UiFrameSlotView) getSlots().get(i);
		if (slot != null) {// &&
							// slot.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.HTML_TEXT))
							// {
			slot.setValue(v);
		}
	}

	public String getSlotType(int i) {
		UiFrameSlotView slot = (UiFrameSlotView) getSlots().get(i);
		if (slot != null) {
			return slot.getSlotTypeCd();
		} else {
			return "";
		}
	}

	public void setSlotType(int i, String v) {
		UiFrameSlotView slot = (UiFrameSlotView) getSlots().get(i);
		if (slot != null) {
			slot.setSlotTypeCd(v);
		}
	}

	public String getSlotNewWin(int i) {
		UiFrameSlotView slot = (UiFrameSlotView) getSlots().get(i);
		if (Utility.isTrue(slot.getUrlTargetBlank(), false)) {
			return "1";
		} else {
			return "0";
		}
	}

	public void setSlotNewWin(int i, String v) {
		UiFrameSlotView slot = (UiFrameSlotView) getSlots().get(i);
		if (slot != null) {
			if (Utility.isTrue(v, false)) {
				slot.setUrlTargetBlank("true");
			} else {
				slot.setUrlTargetBlank("false");
			}
		}
	}

	public String getSlotUrl(int i) {
		UiFrameSlotView slot = (UiFrameSlotView) getSlots().get(i);
		if (slot != null && slot.getUrl() != null) {
			return slot.getUrl();
		} else {
			return "";
		}
	}

	public void setSlotUrl(int i, String v) {
		UiFrameSlotView slot = (UiFrameSlotView) getSlots().get(i);
		if (slot != null) {
			slot.setUrl(v);
		}
	}

	public String getTextValue(int i) {
		if (textValues.size() > i) {
			return (String) textValues.get(i);
		} else {
			return null;
		}
	}

	public void setTextValue(int i, String v) {
		while (textValues.size() < i) {
			textValues.add(null);
		}
		if (textValues.size() > i) {
			textValues.remove(i);
		}
		textValues.add(i, v);
	}

	public void initTextValues() {
		UiFrameSlotViewVector slots = getSlots();
		textValues = new LinkedList<String>();
		if (slots != null) {
			for (int i = 0; i < slots.size(); i++) {
				UiFrameSlotView slot = (UiFrameSlotView) slots.get(i);
				if(slot.getSlotTypeCd().equals(RefCodeNames.SLOT_TYPE_CD.HTML_TEXT))
					setTextValue(i, slot.getValue());
			}
		}
	}
	
}
