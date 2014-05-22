package com.cleanwise.service.api.value;

import java.util.Date;

import com.cleanwise.service.api.eventsys.FileAttach;

public class EventEmailDataView extends EventEmailData implements Cloneable{

	private static final long serialVersionUID = -227746950381844664L;
	private FileAttach[] fileAttachments;

    public FileAttach[] getFileAttachments() {
        return fileAttachments;
    }

    public void setAttachments(FileAttach[] attachments) {
        this.fileAttachments = attachments;
    }

    public EventEmailDataView() {
      super(0, 0, "", "", "", "", "", "",  new Date(), "", new Date(),"",new byte[0], "", new byte[0], "", "", "", "");
        setAttachments(new FileAttach[0]);
    }
}
