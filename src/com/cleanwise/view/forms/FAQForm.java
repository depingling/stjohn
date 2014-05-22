package com.cleanwise.view.forms;

import org.apache.struts.action.*;
import com.cleanwise.service.api.value.*;

public class FAQForm extends ActionForm {

  private NoteJoinViewVector faqViewVector;

  public NoteJoinViewVector getFaqViewVector() {
    return faqViewVector;
  }

  public void setFaqViewVector(NoteJoinViewVector faqViewVector) {
    this.faqViewVector = faqViewVector;
  }
}
