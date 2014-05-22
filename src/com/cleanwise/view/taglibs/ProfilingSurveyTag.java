/*
 * AdminProfilingTag.java
 *
 * Created on May 19, 2003, 10:24 AM
 */

package com.cleanwise.view.taglibs;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.struts.action.Action;
import org.apache.struts.util.MessageResources;
import com.cleanwise.view.utils.*;
import com.cleanwise.view.forms.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;

import java.util.*;
import java.text.DateFormat;
import org.apache.struts.action.ActionForm;
import com.cleanwise.view.utils.ClwCustomizer;
import com.cleanwise.view.i18n.ClwI18nUtil;
import java.text.SimpleDateFormat;

/**
 *
 * @author  bstevens
 */
public class ProfilingSurveyTag extends TagSupport{
    JspWriter mOut;
    boolean mAdministrationMode, mShowDebug;
    //int renderHiddenNumberDependantsInt = 1;
    public static final String
        TOKEN_PATH_TO_PROFILE      = "TOK_PATH_TO_PROFILE",
        TOKEN_PATH_TO_PROFILE_META = "TOK_PATH_TO_PROFILE_META",
        TOKEN_NUMBER_LOOP_VALUE = "TOK_NUM_LOOP_VAL",
        TOKEN_QUESTION_IDX      = "TOK_QUESTION_IDX";;

    public static final String TOKEN_CORRECT_VALUES = "TOK_CORR_VALS";
    public static final String TOKEN_OTHER_VALUES = "TOK_OTHER_VALS";

    private static final String SPACE = " ";

    private static final String INPUT_TEXT_TAG_START = "\n<input type=\"text\" name=\"";
    private static final String
        INPUT_TEXT_TAG_MID1 = "\" size=\"60\" value=\"",
        INPUT_TEXT_TAG_MID1_1 = "\" size=\"60\" value=\"",
        INPUT_TEXT_TAG_MID1_2 = "\" size=\"20\" value=\"",
        INPUT_TEXT_TAG_MID1_3 = "\" size=\"8\" value=\"";
    private static final String INPUT_TEXT_TAG_MID2 = "\" ";
    private static final String INPUT_TEXT_TAG_MID3 = " id=\"";
    private static final String INPUT_TEXT_TAG_MID4 = "\" profileTypeCd=\"";
    private static final String INPUT_TEXT_TAG_END = "\">";
    private static final String SELECT_TAG_START = "\n<select name=\"";
    private static final String SELECT_TAG_MULTI_START = "<!-- start 1-->";
    private static final String SELECT_TAG_MID1 = "\" ";
    private static final String SELECT_TAG_MID1a = " profileTypeCd=\"";
    private static final String SELECT_TAG_MID2 = "\">";
    private static final String SELECT_TAG_END = "</select>";
    private static final String OPTION_TAG_START = "<option value=\"";
    private static final String OPTION_TAG_MID1 = "\"";
    private static final String OPTION_TAG_END = "</option>";
    private static final String OPTION_TAG_SELECTED1 = " selected=\"selected\"";
    private static final String OPTION_TAG_SELECTED2 = ">";
    private static final String OPTION_TAG_EMPTY = "<option value=\"\">-Select-</option>";
    private static final String CHECKBOX_TAG_START =
        "\n<br><input type=checkbox name=";
    private static final String CHECKBOX_TAG_MID1 = " ";
    private static final String CHECKBOX_TAG_END = "</input> <!-- end of checkbox --> ";
    private static final String CHECKBOX_TAG_SELECTED1 = " checked ";
    private static final String CHECKBOX_TAG_SELECTED2 = " value=";

    private static final String HIDDEN_TAG_START = "<input type=\"hidden\" name=\"";
    private static final String HIDDEN_TAG_MID1 = "profileView.profileData.profileId\" value=\"";
    private static final String HIDDEN_TAG_END = "\">";
    private static final String FILE_TAG_START = "<input type=\"file\" name=\"";
    private static final String FILE_TAG_END = "\" accept=\"image/jpeg,image/gif\" value=\"\">";
    private static final String IMAGE_TAG_START = "<img src=\"/";
    private static final String IMAGE_TAG_MID1 = "/";
    private static final String IMAGE_TAG_END = "\"/>";

    private static final String[] PROFILE_QUESTION_TYPE_CD =
    {RefCodeNames.PROFILE_QUESTION_TYPE_CD.FREE_FORM_TEXT,
     RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICE,
     RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICES,
     RefCodeNames.PROFILE_QUESTION_TYPE_CD.NUMBER,
     RefCodeNames.PROFILE_QUESTION_TYPE_CD.DATE};
     private static final String[] PROFILE_STATUS_CD =
     {RefCodeNames.PROFILE_STATUS_CD.ACTIVE,RefCodeNames.PROFILE_STATUS_CD.INACTIVE};
     private static final String[] READ_ONLY = {Boolean.TRUE.toString(),Boolean.FALSE.toString()};
     private static final String[] PROFILE_META_TYPE_CD =
     {RefCodeNames.PROFILE_META_TYPE_CD.CHOICE,
      RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE,
      RefCodeNames.PROFILE_META_TYPE_CD.CHOICE_SHOW_CHILDREN,
      RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE_SHOW_CHILDREN
     };
     private static final String[] PROFILE_META_STATUS_CD =
     {RefCodeNames.PROFILE_META_STATUS_CD.ACTIVE,
      RefCodeNames.PROFILE_META_STATUS_CD.INACTIVE
     };

     /** Holds value of property form. */
     private String form;

     /** Holds value of property childPrefix. */
     private String childPrefix;

     /** Holds value of property elementPostfix. */
     private String elementPostfix;

     /** Holds value of property childPostfix. */
     private String childPostfix;

     /** Holds value of property helpTextPrefix. */
     private String helpTextPrefix;

     /** Holds value of property name. */
     private String name;

     /** Holds value of property skipFirst. */
     private String skipFirst;

    private String inputTextPrefix = "<br>";
    private String inputTextPostfix;
    private String showModDate = "false";
    private String showModBy = "false";
    private String textInputWidth = "";

     public ProfilingSurveyTag(){
         super();
     }

     private String getPathToProfile(List pIndexs)throws IOException{
         StringBuffer buf = new StringBuffer();
         buf.append(getName());
         buf.append(".");
         Iterator it = pIndexs.iterator();
         while(it.hasNext()){
             buf.append("childrenElement");
             buf.append("[");
             buf.append(it.next().toString());
             buf.append("]");
             buf.append(".");
         }
         return buf.toString();
     }


     private void renderSelectInput(List pIndexs,String pPathToProfile,
                                    String[] pValues,
                                    String pType, String[] pOptions,
                                    boolean pMulti, ProfileViewContainer pProf
                                    )
         throws IOException{

         if(isReadOnlyBool()){
             throw new RuntimeException("Attemting to render input tag in read only mode");
         }
         //if(!mAdministrationMode){mOut.write("\n<br>");}
         if(!mAdministrationMode){mOut.write(getInputTextPrefix());}

         mOut.write(HIDDEN_TAG_START);
         mOut.write(getPathToProfile(pIndexs));
         mOut.write(HIDDEN_TAG_MID1);
         mOut.write(Integer.toString(pProf.getProfileView().getProfileData().getProfileId()));
         mOut.write(HIDDEN_TAG_END + "\n");

         if(!pMulti){

             mOut.write(SELECT_TAG_START);
             //detailValues
             mOut.write(pPathToProfile);
             if(!mAdministrationMode ){
                 mOut.write("value");
             }
             mOut.write(SELECT_TAG_MID1);
             if(getFormElementJavascript()!=null){
                 mOut.write(getFormElementJavascript());
             }
         }

         mOut.write(SPACE + "\n\n" );

         //get relevent meta data into their own Lists
         ArrayList otherVals = new ArrayList();
         Iterator it = pProf.getProfileView().getProfileMetaDataVector().iterator();
         ArrayList corectVals = new ArrayList();
         while(it.hasNext()){
             ProfileMetaData meta = (ProfileMetaData) it.next();
             if(RefCodeNames.PROFILE_META_TYPE_CD.CHOICE_SHOW_CHILDREN.equals(meta.getProfileMetaTypeCd())
                || RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE_SHOW_CHILDREN.equals(meta.getProfileMetaTypeCd())
                )
             {
                 corectVals.add(meta.getValue());
             }
             if ( pMulti == false )
             {
             if (RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE_SHOW_CHILDREN.equals(meta.getProfileMetaTypeCd()) ||
             RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE.equals(meta.getProfileMetaTypeCd())){
                 otherVals.add(meta.getValue());
             }
             }
         }

         if(pMulti == false &&
            getFormSelectElementParentOnlyJavascript() != null
            && (pProf.isHasChildren() || (!(otherVals.isEmpty()))))
         {
             StringBuffer ele = new StringBuffer(getFormSelectElementParentOnlyJavascript());

             //create a string buffer of the "correct" values, i.e. values that will show this
             //profile questions child
             it = corectVals.iterator();
             StringBuffer corBuf = new StringBuffer();
             while(it.hasNext()){
                 corBuf.append("'");
                 corBuf.append(it.next());
                 corBuf.append("'");
                 if(it.hasNext()){
                     corBuf.append(",");
                 }
             }

             //create a string buffer of the values that will show the "Other" text entry box
             it = otherVals.iterator();
             StringBuffer othBuf = new StringBuffer();
             while(it.hasNext()){
                 othBuf.append("'");
                 othBuf.append(it.next());
                 othBuf.append("'");
                 if(it.hasNext()){
                     othBuf.append(",");
                 }
             }

             if(othBuf.length() == 0){
                 othBuf.append("null");
             }
             if(corBuf.length() == 0){
                 corBuf.append("null");
             }

             Utility.replaceString(ele,TOKEN_CORRECT_VALUES,corBuf.toString());
             Utility.replaceString(ele,TOKEN_OTHER_VALUES, othBuf.toString());
             mOut.write(ele.toString());
         }
         if (!pMulti )
         {
             mOut.write(SELECT_TAG_MID1a);
             safeWrite(pType);
             mOut.write(SELECT_TAG_MID2);
             mOut.write(OPTION_TAG_EMPTY);
         }

         for(int i=0;i<pOptions.length;i++){
             if ( pMulti )
             {
                 mOut.write(CHECKBOX_TAG_START);
             }
             else
             {
                 mOut.write(OPTION_TAG_START);
             }
             if ( pMulti )
             {
                 mOut.write("\"" + pPathToProfile + "\" ");

                 mOut.write(CHECKBOX_TAG_MID1);
             }
             else
             {
                 safeWrite(pOptions[i]);
                 mOut.write(OPTION_TAG_MID1);
             }

             for(int j=0;j<pValues.length;j++){
                 if(pValues[j]!= null && pValues[j].equals(pOptions[i])){
                     if ( pMulti )
                     {
                         mOut.write(CHECKBOX_TAG_SELECTED1);
                     }
                     else
                     {
                         mOut.write(OPTION_TAG_SELECTED1);
                         break;
                     }
                 }
             }

             if ( pMulti )
             {
                 mOut.write(CHECKBOX_TAG_SELECTED2);
                 mOut.write("\"" + pOptions[i] + "\" >");

             }
             else
             {
                 mOut.write(OPTION_TAG_SELECTED2);
             }
             safeWrite(pOptions[i]);
             if ( pMulti )
             {
                 mOut.write(CHECKBOX_TAG_END);
             }
             else
             {
                 mOut.write(OPTION_TAG_END);
             }
         }

         if ( !pMulti )
         {
             mOut.write(SELECT_TAG_END);
         }

         if(!mAdministrationMode){
             //render the "other" text box
             if(!(otherVals.isEmpty())){
                 mOut.write(INPUT_TEXT_TAG_START);
                 mOut.write(pPathToProfile);
                 mOut.write("shortDesc");
                 mOut.write(INPUT_TEXT_TAG_MID1);
                 safeWrite(pProf.getProfileView().getProfileDetailDataVectorElement(0).getShortDesc());
                 mOut.write(INPUT_TEXT_TAG_MID2);
                 mOut.write(SPACE);
                 boolean renderOther = false;
                 for(int j=0;j<pValues.length;j++){
                     if(pValues[j]!= null && otherVals.contains(pValues[j])){
                         renderOther=true;
                     }
                 }
                 if(!renderOther){
                     if(pMulti == false && getOtherStyle() != null){
                         mOut.write("style='");
                         mOut.write(getOtherStyle());
                         mOut.write("'");
                         mOut.write(SPACE);
                     }
                 }
                 mOut.write(INPUT_TEXT_TAG_MID3);
                 mOut.write(pPathToProfile);
                 mOut.write("shortDesc");
                 mOut.write(INPUT_TEXT_TAG_MID4);
                 //leave type code blank
                 mOut.write(INPUT_TEXT_TAG_END);
             }
         }
         if(!mAdministrationMode){mOut.write(getInputTextPostfix());}
     }

     private void renderTextInput(List pIndexs,String pPathToProfile, String pValue, String pType, ProfileViewContainer pProf)throws IOException{

         if(isReadOnlyBool()){
             throw new RuntimeException("Attemting to render input tag in read only mode");
         }
        // if(!mAdministrationMode){             mOut.write("<br>");         }
         if(!mAdministrationMode){             mOut.write(getInputTextPrefix());         }

         mOut.write(HIDDEN_TAG_START);
         mOut.write(getPathToProfile(pIndexs));
         mOut.write(HIDDEN_TAG_MID1);
         mOut.write(Integer.toString(pProf.getProfileView().getProfileData().getProfileId()));
         mOut.write(HIDDEN_TAG_END);

         mOut.write(INPUT_TEXT_TAG_START);
         mOut.write(pPathToProfile);


         if ( pProf.getProfileView().getProfileData().
         getProfileQuestionTypeCd().equals
         (RefCodeNames.PROFILE_QUESTION_TYPE_CD.FREE_FORM_TEXT)) {
             if (Utility.isSet(getTextInputWidth())) {
               mOut.write("\" size=\"" + getTextInputWidth() + "\" value=\"");
             } else {
               mOut.write(INPUT_TEXT_TAG_MID1_1);
             }
         } else if (pProf.getProfileView().getProfileData().
           getProfileQuestionTypeCd().equals(RefCodeNames.PROFILE_QUESTION_TYPE_CD.DATE)) {
             mOut.write(INPUT_TEXT_TAG_MID1_2);
         } else if (pProf.getProfileView().getProfileData().
           getProfileQuestionTypeCd().equals(RefCodeNames.PROFILE_QUESTION_TYPE_CD.NUMBER)) {
            mOut.write(INPUT_TEXT_TAG_MID1_3);
         } else {
             mOut.write(INPUT_TEXT_TAG_MID1);
         }

         safeWrite(pValue);
         mOut.write(INPUT_TEXT_TAG_MID2);
         if(getFormElementJavascript()!=null){
             mOut.write(getFormElementJavascript());
         }
         mOut.write(SPACE);
         if(getFormElementParentOnlyJavascript() != null && pProf.isHasChildren()){
             mOut.write(getFormElementParentOnlyJavascript());
         }
         mOut.write(INPUT_TEXT_TAG_MID3);
         mOut.write(pPathToProfile);
         mOut.write(INPUT_TEXT_TAG_MID4);
         safeWrite(pType);
         mOut.write(INPUT_TEXT_TAG_END);
         if(!mAdministrationMode){             mOut.write(getInputTextPostfix());         }
     }

    private int mCurrentTopLevelQuestionIdx = 0;
     private String tokenize(String toTokenize,String pPathToProfile,int pMetaIndex, int pLoopIdx,int pLevel,List pIndexs){
         if(mTokenizeBool){
             StringBuffer ele = new StringBuffer(toTokenize);
             //this order is important
             Utility.replaceString(ele,TOKEN_PATH_TO_PROFILE_META,pPathToProfile + "profileMetaDataVectorElement["+pMetaIndex+"]");
             Utility.replaceString(ele,TOKEN_PATH_TO_PROFILE,pPathToProfile);
             if(pLevel > 0){
                 Utility.replaceString
                     (ele,TOKEN_QUESTION_IDX,"<b>"
                      + mCurrentTopLevelQuestionIdx + "</b>");
                 Utility.replaceString
                     (ele,TOKEN_NUMBER_LOOP_VALUE,
                      "<b>" + Integer.toString(pLoopIdx + 1) + "</b>" );
             }else{
                 Integer currQuestionNum = (Integer) pIndexs.get(pIndexs.size()-1);
                 Utility.replaceString(ele,TOKEN_QUESTION_IDX+".", " "); //replace the question token with blank as this is the top level (no looping)
                 Utility.replaceString
                     (ele,TOKEN_NUMBER_LOOP_VALUE,
                      Integer.toString(currQuestionNum.intValue() + 1));
             }

             return ele.toString();
         }
         return toTokenize;
     }

     private void renderTokenizedString(String toTokenize,String pPathToProfile,int pMetaIndex, int pLoopIdx,int pLevel,List pIndexs) throws IOException{
         if(toTokenize!=null){
             mOut.write(tokenize(toTokenize, pPathToProfile, pMetaIndex,pLoopIdx,pLevel,pIndexs));
         }
     }


     /**
      *Renders the image assiociated with this profile view if one exists.
      */
     private void renderImageDisplay(String pPathToProfile,ProfileMetaDataVector pMetaVec) throws IOException{
         Iterator it = pMetaVec.iterator();
         while(it.hasNext()){
             ProfileMetaData meta = (ProfileMetaData) it.next();
             if(RefCodeNames.PROFILE_META_TYPE_CD.IMAGE.equals(meta.getProfileMetaTypeCd())){
                 mOut.write(IMAGE_TAG_START);
                 mOut.write(ClwCustomizer.getStoreDir());
                 mOut.write(IMAGE_TAG_MID1);
                 mOut.write(meta.getValue());
                 mOut.write(IMAGE_TAG_END);
                 break;
             }
         }

         if (mAdministrationMode){
             mOut.write(FILE_TAG_START);
             mOut.write(pPathToProfile);
             mOut.write(FILE_TAG_END);
         }
     }

     /**
      *"write" the specified string to the output stream, if it is null nothing is written
      */
     private void safeWrite(String pStr) throws IOException{
         if(pStr != null){
             mOut.write(pStr);
         }
     }


     /**
      *Determins by the meta data type cd if it is a multiple choice answer
      */
     private boolean isQuestionChoice(ProfileMetaData pMeta){
         if(RefCodeNames.PROFILE_META_TYPE_CD.CHOICE.equals(pMeta.getProfileMetaTypeCd())
         || RefCodeNames.PROFILE_META_TYPE_CD.CHOICE_SHOW_CHILDREN.equals(pMeta.getProfileMetaTypeCd())
         || RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE.equals(pMeta.getProfileMetaTypeCd())
         || RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE_SHOW_CHILDREN.equals(pMeta.getProfileMetaTypeCd())
         ){
             return true;
         }
         return false;
     }

     /**
      *Converts a meta data vector into an array of the question choice values
      */
     private String[] getChoiceStringArrayFromMeta(ProfileMetaDataVector pMeta){
         ArrayList retArray = new ArrayList();
         Iterator it = pMeta.iterator();
         while(it.hasNext()){
             ProfileMetaData data = (ProfileMetaData) it.next();
             if(isQuestionChoice(data)){
                 retArray.add(data.getValue());
             }
         }
         String[] retArrayA = new String[retArray.size()];
         return (String[]) retArray.toArray(retArrayA);
     }

     //removes any entrys from the vector that are not "set"
     private void removeEmptyDetailData(ProfileDetailDataVector pDetail){
         if (pDetail == null){
             return;
         }
         Iterator it = pDetail.iterator();
         while(it.hasNext()){
             ProfileDetailData o = (ProfileDetailData) it.next();
             if(o == null || !Utility.isSet(o.getValue())){
                 it.remove();
             }
         }
     }

     private void renderProfileView
         (ProfileViewContainer pParent,
          ProfileViewContainer pProfileViewContainer,
          List pIndexs, int pLevel, boolean altPrefixBranch,
          int detailLoopVal) throws IOException{

         ProfileView theView = pProfileViewContainer.getProfileView();
         //if we have an empty branch here just return, if it has
         //other data filled in throw exception
         if(theView.getProfileData() == null){
             if(theView.getChildren() != null ||
                theView.getProfileDetailDataVector() != null){
                 throw new IllegalStateException("Broken link, null profile data with populated detail and/or children");
             }
             return;
         }

         removeEmptyDetailData(theView.getProfileDetailDataVector());
         if(pLevel >= 0){
             String lAltPathToProfile = getPathToProfile(pIndexs);
             String lPathToProfile = lAltPathToProfile + "profileView.";

             mOut.write("\n\n <!-- " + lPathToProfile
                        + " mCurrentTopLevelQuestionIdx="
                        + mCurrentTopLevelQuestionIdx
                        + "-->\n\n");


             if(pLevel >= 1){
                 int lastIndex = ((Integer)pIndexs.get(pIndexs.size() - 1)).intValue();

                 //if the element prefix stuff is set render it or the
                 //alt.  If only one is set the getters and setters
                 //will make sure that a null is not returned, but
                 //they may be equal.
                 if(getElementPrefix() != null){
                     //if this is not an altprefix branch (i.e. the
                     //parent was rendered with an alt prefix) and
                     //there is a value render the element prefix
                     if (!altPrefixBranch && Utility.isSet(theView.getProfileDetailDataVectorElement(0).getValue())){
                         mOut.write(tokenize(getElementPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs));
                     }else{
                         if(!altPrefixBranch && pParent!=null && pParent.shouldChildAtIndexBeSet(detailLoopVal + 1 )){
                             altPrefixBranch = true;
                             mOut.write(tokenize(getElementPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs));
                         }else{
                             altPrefixBranch = true;
                             mOut.write(tokenize(getAltChildElementPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs));
                         }
                     }
                 }
             }
             else
             {
                 mCurrentTopLevelQuestionIdx++;
                 renderTokenizedString(getElementPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
             }

             if(getChildSeperator() != null){
                 String cs = tokenize(getChildSeperator(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 for(int i=0;i<pLevel;i++){
                     mOut.write(cs);
                 }
             }

             renderTokenizedString(getChildPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);

             mOut.write("\n<!-- getChildPrefix()=" + getChildPrefix()
                        + " lPathToProfile=" + lPathToProfile
                        + " detailLoopVal=" + detailLoopVal
                        + " pLevel=" + pLevel
                        + " pIndexs=" + pIndexs
                        + " -->\n");

             mOut.write(HIDDEN_TAG_START);
             mOut.write(lAltPathToProfile);
             mOut.write("loopValue");
             mOut.write("\" value=\"");
             mOut.write(Integer.toString(detailLoopVal));
             mOut.write(HIDDEN_TAG_END + "\n\n");
             mOut.write("\n\n <span class='survey_question'> ");

       if(mShowDebug){
     mOut.write
         ("{ ID=" +
          theView.getProfileData().getProfileId()
          + " #=" +
          theView.getProfileData().getProfileOrder()
          + " } <br>");
       }

             if(mAdministrationMode){
                 renderTextInput(pIndexs,lPathToProfile + "profileData.shortDesc",theView.getProfileData().getShortDesc(),theView.getProfileData().getProfileQuestionTypeCd(),pProfileViewContainer);
             }else{
                 renderTokenizedString(theView.getProfileData().getShortDesc(),lAltPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 //safeWrite(theView.getProfileData().getShortDesc());
             }

             if(mAdministrationMode){
                 renderTokenizedString(getProfileOrderPrefix(),lAltPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 renderTextInput(pIndexs,lAltPathToProfile + "profileOrderString",pProfileViewContainer.getProfileOrderString(),theView.getProfileData().getProfileQuestionTypeCd(),pProfileViewContainer);
             }

             if(mAdministrationMode){
                 renderTokenizedString(getHelpTextPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 renderTextInput(pIndexs,lPathToProfile + "profileData.helpText",theView.getProfileData().getHelpText(),theView.getProfileData().getProfileQuestionTypeCd(),pProfileViewContainer);
                 renderTokenizedString(getHelpTextPostFix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
             }else if(isReadOnlyBool()){
                 //do nothing
             }else{
                 //if not in admin mode only render the help text if
                 //there is help text

                 if(Utility.isSet(theView.getProfileData().getHelpText())){
                     renderTokenizedString(getHelpTextPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                     safeWrite(theView.getProfileData().getHelpText());
                     renderTokenizedString(getHelpTextPostFix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 }
             }

             mOut.write("\n\n </span> ");

             renderTokenizedString(getProfileQuestionTypeCdPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
             if(mAdministrationMode){
                 renderSelectInput(pIndexs,lPathToProfile + "profileData.profileQuestionTypeCd",new String[]{theView.getProfileData().getProfileQuestionTypeCd()},theView.getProfileData().getProfileQuestionTypeCd(),PROFILE_QUESTION_TYPE_CD, false,pProfileViewContainer);
             }


             if(mAdministrationMode){
                 renderTokenizedString(getProfileStatusCdPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 renderSelectInput(pIndexs,lPathToProfile + "profileData.profileStatusCd",new String[]{theView.getProfileData().getProfileStatusCd()},theView.getProfileData().getProfileQuestionTypeCd(),PROFILE_STATUS_CD, false,pProfileViewContainer);
             }//else don't render


             if(mAdministrationMode){
                 renderTokenizedString(getReadOnlyPrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 renderSelectInput(pIndexs,lPathToProfile + "profileData.readOnly",new String[]{theView.getProfileData().getReadOnly()},theView.getProfileData().getProfileQuestionTypeCd(),READ_ONLY, false,pProfileViewContainer);
             }//else don't render

             //render the image if it is avaliable, do this for admin mode and none admin mode
             renderImageDisplay(lAltPathToProfile+"imageUploadFile",theView.getProfileMetaDataVector());

             if(mAdministrationMode){
                 Iterator it = theView.getProfileMetaDataVector().iterator();
                 int i = 0;
                 while (it.hasNext()){
                     ProfileMetaData meta = (ProfileMetaData) it.next();
                     if(isQuestionChoice(meta)){
                         renderTokenizedString(getProfileMetaPrefix(),lPathToProfile,i,detailLoopVal,pLevel,pIndexs);
                         renderTextInput(pIndexs,lPathToProfile + "profileMetaDataVectorElement["+i+"].value",meta.getValue(),null,pProfileViewContainer);
                         renderTokenizedString(getMetaHelpPrefix(),lPathToProfile,i,detailLoopVal,pLevel,pIndexs);
                         renderTextInput(pIndexs,lPathToProfile + "profileMetaDataVectorElement["+i+"].helpText",meta.getHelpText(),null,pProfileViewContainer);
                         renderTokenizedString(getMetaTypeCdPrefix(),lPathToProfile,i,detailLoopVal,pLevel,pIndexs);
                         renderSelectInput(pIndexs,lPathToProfile + "profileMetaDataVectorElement["+i+"].profileMetaTypeCd",new String[]{meta.getProfileMetaTypeCd()},null,PROFILE_META_TYPE_CD, false,pProfileViewContainer);
                         renderTokenizedString(getProfileMetaStatusCdPrefix(),lPathToProfile,i,detailLoopVal,pLevel,pIndexs);
                         renderSelectInput(pIndexs,lPathToProfile + "profileMetaDataVectorElement["+i+"].profileMetaStatusCd",new String[]{meta.getProfileMetaStatusCd()},null,PROFILE_META_STATUS_CD, false,pProfileViewContainer);
                         i++;
                     }else if(RefCodeNames.PROFILE_META_TYPE_CD.CHOICE_SHOW_CHILDREN.equals(meta.getProfileMetaTypeCd())){
                         //XXXrender somethig to allow file uploads!!
                     }
                 }
             }


             //render the answer for this question
             Date modDate = null;
             if(!mAdministrationMode){


                 if(Boolean.TRUE.equals(theView.getProfileData().getReadOnly()) || isReadOnlyBool()){
                     Iterator it = theView.getProfileDetailDataVector().iterator();
                     mOut.write(SPACE);
                     while(it.hasNext()){
                         ProfileDetailData dd = (ProfileDetailData) it.next();
                         safeWrite(dd.getValue());
                         if(it.hasNext()){
                            mOut.write(",");
                            mOut.write(SPACE);
                         }
                     }
                 }else{
                     String lQType = theView.getProfileData().getProfileQuestionTypeCd();
                     String[] values;
                     if(theView.getProfileDetailDataVector() != null){
                         values = new String[theView.getProfileDetailDataVector().size()];
                     }else{
                         values = new String[0];
                     }
                     Iterator it = theView.getProfileDetailDataVector().iterator();
                     int i=0;
                     while(it.hasNext()){
                         ProfileDetailData det = (ProfileDetailData) it.next();
                         values[i]=det.getValue();
                         modDate = det.getModDate();
                         i++;
                     }

                     if(RefCodeNames.PROFILE_QUESTION_TYPE_CD.FREE_FORM_TEXT.equals(lQType)
                        || RefCodeNames.PROFILE_QUESTION_TYPE_CD.NUMBER.equals(lQType)){
                         String value;
                         if(values.length ==0){
                             value = "";
                         }else{
                             value = values[0];
                         }
                         renderTextInput(pIndexs,lPathToProfile + "profileDetailDataVectorElement["+0+"].value",value,theView.getProfileData().getProfileQuestionTypeCd(),pProfileViewContainer);
                     }else if(RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICE.equals(lQType)){
                         renderSelectInput(pIndexs, lPathToProfile + "profileDetailDataVectorElement["+0+"].", values,theView.getProfileData().getProfileQuestionTypeCd(), getChoiceStringArrayFromMeta(theView.getProfileMetaDataVector()), false,pProfileViewContainer);
                     }else if(RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICES.equals(lQType)){

                         renderSelectInput
                             (pIndexs,
                              lAltPathToProfile +
                              "detailValues",
                              values,
                              theView.getProfileData().getProfileQuestionTypeCd(),
                              getChoiceStringArrayFromMeta
                              (theView.getProfileMetaDataVector()),
                              true,pProfileViewContainer
                              );
                      } else   if( RefCodeNames.PROFILE_QUESTION_TYPE_CD.DATE.equals(lQType)){
                          String value;
                          if(values.length == 0){
                               value = "";
                          }else{
                            try {
                              HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
                              SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                              sdf.setLenient(false);
                              value = ClwI18nUtil.formatDateInp(request, sdf.parse(values[0]));

                              //value = ClwI18nUtil.formatDateInp(request, ClwI18nUtil.parseDateInp(request,values[0]));
                            } catch (Exception e) {
                              value = values[0];
                            }
                           }
                           renderTextInput(pIndexs,lPathToProfile + "profileDetailDataVectorElement["+0+"].value",value,theView.getProfileData().getProfileQuestionTypeCd(),pProfileViewContainer);
                           String promptText = getDatePromptText();
                           if (promptText != null) {
                             promptText = promptText.replaceFirst("formElementId", lPathToProfile + "profileDetailDataVectorElement["+0+"].value");
                           }
                           renderTokenizedString(promptText,lPathToProfile,i,detailLoopVal,pLevel,pIndexs);
                     }
                 }
             }

             // show mod by and mod date if need (NewXpedx)
             if (Utility.isTrue(getShowModDate()) && modDate != null) {
                 mOut.write("<td nowrap><nobr>");
                 HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
                 SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                 //String value = sdf.format(theView.getProfileData().getModDate()) ;//ClwI18nUtil.formatDate(request, theView.getProfileData().getModDate(), DateFormat.SHORT);
                 if(modDate!=null) {
                     String value = sdf.format(modDate);
                     mOut.write("&nbsp;&nbsp;&nbsp;"+ClwI18nUtil.getMessage(request, "newxpdex.storeProfile.specialItems.updatedOn", null) + " ");
                     renderTokenizedString(value,lAltPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 }
                 mOut.write("&nbsp;</nobr></td>");
             }

             if(getChildPostfix() != null){
                 String cp = tokenize(getChildPostfix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 for(int i=0;i<pLevel;i++){
                     mOut.write(cp);
                 }
             }

             if(Utility.isSet(pProfileViewContainer.getErrorMessage())){
                 renderTokenizedString(getErrorMessgePrefix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
                 mOut.write(pProfileViewContainer.getErrorMessage());
                 renderTokenizedString(getErrorMessgePostfix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
             }
             renderTokenizedString(getElementPostfix(),lPathToProfile,0,detailLoopVal,pLevel,pIndexs);
             mOut.write("\n"); //useful for debugging output
         }
         int lRepeat = 0;
         if(isReadOnlyBool()){}

         if(isReadOnlyBool()){
             lRepeat = 0;
         }else{
             if(!mAdministrationMode && RefCodeNames.PROFILE_QUESTION_TYPE_CD.NUMBER.equals(theView.getProfileData().getProfileQuestionTypeCd())){
                 lRepeat = getRenderHiddenNumberDependants();
             }
         }
         if(RefCodeNames.PROFILE_QUESTION_TYPE_CD.NUMBER.equals(theView.getProfileData().getProfileTypeCd())){
             ProfileDetailData det = theView.getProfileDetailDataVectorElement(0);
             if(det != null && Utility.isSet(det.getValue())){
                 try{
                     Integer ansInt = new Integer(det.getValue());
                     if(lRepeat < ansInt.intValue()){
                         lRepeat = ansInt.intValue();
                     }
                 }catch(RuntimeException e){}
             }
         }
         //first loop through our children and find out how many unique kids we have and break them out
         //into lists for easy access
         pProfileViewContainer.shallowSort();
         HashMap uniqueChildren = new HashMap();
         ArrayList keys = new ArrayList();
         Iterator it = pProfileViewContainer.getChildren().iterator();
         int greatestUniqueKids = 0; //will hold the number of the greatest number of children types
         while(it.hasNext()){
             ProfileViewContainer kid = (ProfileViewContainer) it.next();
             Integer key = new Integer(kid.getProfileView().getProfileData().getProfileId());
             ArrayList kidList = (ArrayList) uniqueChildren.get(key);
             if(kidList == null){
                 if(!keys.contains(key)){
                     keys.add(key);
                 }
                 kidList = new ArrayList();
                 uniqueChildren.put(key,kidList);
             }
             kidList.add(kid);
             int size = kidList.size();
             if(greatestUniqueKids < size){
                 greatestUniqueKids = size;
             }
         }

         //ArrayList keys = new ArrayList(keysSet);
         //java.util.Collections.sort(keys,ProfileViewContainer.PROFILE_ORDER_COMPARE);

         //reconstruct the children list in a way sutible for display.  Make sure all of the unique children
         //are in equal proportion and add in any blank entries based off the lRepeat value
         ArrayList newChildrenList = new ArrayList();
         for(int i=0;i<greatestUniqueKids + lRepeat;i++){
             it = keys.iterator();
             while(it.hasNext()){
                 Integer key = (Integer) it.next();
                 ArrayList kidList = (ArrayList) uniqueChildren.get(key);
                 ProfileViewContainer kid;
                 if(kidList.size() > i){
                     kid = (ProfileViewContainer) kidList.get(i);
                 }else{
                     kid = ((ProfileViewContainer) kidList.get(0)).cloneNoDetail();
                 }
                 newChildrenList.add(kid);
             }
         }

         //reset the children list to our brand new correctly ordered list, which should be of a length:
         //(<repeat value> + <greatest number of already answered child questions>) * <number of unique child questions>
         pProfileViewContainer.setChildren(newChildrenList);

         //now loop through the children and render them recursively
         int numberOfUniqueChildren = keys.size();
         it = pProfileViewContainer.getChildren().iterator();
         int ct=0;
         while(it.hasNext()){
             ProfileViewContainer kid = (ProfileViewContainer) it.next();
             List newIndexs = new ArrayList();
             newIndexs.addAll(pIndexs);
             newIndexs.add(new Integer(ct));
             int detailLoopValue = ct / numberOfUniqueChildren;
             renderProfileView(pProfileViewContainer,kid,newIndexs,pLevel + 1,altPrefixBranch,detailLoopValue);
             ct++;
         }
     }


     /**
      * Renders a profile for editing by an administrator type user.
      * No authentication is done however!
      * @exception JspException if a JSP exception has occurred
      */
     public int doStartTag() throws JspException {
         if(isReadOnlyBool() && mAdministrationMode){
            throw new JspException("Cannot be simultaniously in admin mode and readonly mode.");
         }
         mCurrentTopLevelQuestionIdx = 0;

         try{
             HttpSession session = pageContext.getSession();
             mOut = pageContext.getOut();
             //get the form starting with pageScope, then request, finally session
             ActionForm form = (ActionForm) pageContext.getAttribute(getForm());
             if(form == null){
                 form = (ActionForm) pageContext.getAttribute(getForm(),pageContext.REQUEST_SCOPE);
             }
             if(form == null){
                 form = (ActionForm) pageContext.getAttribute(getForm(),pageContext.SESSION_SCOPE);
             }
             if(form == null){
                 throw new JspException("Could not find form: " + getForm() + " in page, request or session scope");
             }

             ProfileViewContainer profile = null;
             boolean supported = false;
             if(form instanceof SiteMgrDetailForm){
                 supported = true;
                 profile = ((SiteMgrDetailForm)form).getProfile();
             }else if(form instanceof StoreSiteMgrForm){
                 supported = true;
                 profile = ((StoreSiteMgrForm)form).getProfile();
             }else if(form instanceof ProfilingMgrSurveyForm){
                 supported = true;
                 profile = ((ProfilingMgrSurveyForm)form).getProfile();
             }else if(form instanceof CustomerProfilingForm){
                 supported = true;
                 profile = ((CustomerProfilingForm) form).getProfile();
             } else if (form instanceof SiteShoppingControlForm) {
                 supported = true;
                 profile = ((SiteShoppingControlForm)form).getProfile();
             } else if (form instanceof StoreProfilingMgrSurveyForm) {
                 supported = true;
                 profile = ((StoreProfilingMgrSurveyForm)form).getProfile();
             }

             if(profile == null){
                 if(!supported){
                     String className;
                     if(form == null){
                         className = null;
                     }else{
                         className = form.getClass().getName();
                     }
                     throw new JspException("Form: " + getForm() + "[" + className  + "] was not of a supported type (class).");
                 }else{
                     throw new JspException("\"Profile\" property of form " + getForm() + " was null");
                 }
             }

             if(isUseCloneBool()){
                 profile = profile.cloneNoDetail();
             }
             if(isSkipFirstBool()){
                 renderProfileView(null,profile,new ArrayList(),-1,false,0);
             }else{
                 renderProfileView(null,profile,new ArrayList(),0,false,0);
             }


         }catch(Exception e){
             e.printStackTrace();
             throw new JspException(e.getMessage());
         }
         return SKIP_BODY;
     }

     private boolean mSkipFirstBool = false;

     private boolean mTokenizeBool = false;

     /** Holds value of property childSeperator. */
     private String childSeperator;

     /** Holds value of property tokenize. */
     private String tokenize;

     /** Holds value of property elementPrefix. */
     private String elementPrefix;

     /** Holds value of property profileQuestionTypeCdPrefix. */
     private String profileQuestionTypeCdPrefix;

     /** Holds value of property profileStatusCdPrefix. */
     private String profileStatusCdPrefix;

     /** Holds value of property readOnlyPrefix. */
     private String readOnlyPrefix;

     /** Holds value of property profileMetaPrefix. */
     private String profileMetaPrefix;

     /** Holds value of property profileOrderPrefix. */
     private String profileOrderPrefix;

     /** Holds value of property metaHelpPrefix. */
     private String metaHelpPrefix;

     /** Holds value of property metaTypeCdPrefix. */
     private String metaTypeCdPrefix;

     /** Holds value of property admin. */
     private String admin;

     /** Holds value of property debug. */
     private String debug;

     /** Holds value of property altChildElementPrefix. */
     private String altChildElementPrefix;

     /** Holds value of property formElementJavascript. */
     private String formElementJavascript;

     /** Holds value of property renderHiddenNumberDependants. */
     private int renderHiddenNumberDependants;

     /** Holds value of property helpTextPostFix. */
     private String helpTextPostFix;

     /** Holds value of property profileMetaStatusCdPrefix. */
     private String profileMetaStatusCdPrefix;

     /** Holds value of property formElementParentOnlyJavascript. */
     private String formElementParentOnlyJavascript;

     /** Holds value of property formSelectElementParentOnlyJavascript. */
     private String formSelectElementParentOnlyJavascript;

     /** Holds value of property errorMessgePrefix. */
     private String errorMessgePrefix;

     /** Holds value of property errorMessgePostfix. */
     private String errorMessgePostfix;

     /** Holds value of property otherStyle. */
     private String otherStyle;

     /** Holds value of property useClone. */
     private String useClone;

     /** Holds value of property useCloneBool. */
     private boolean useCloneBool;

     /** Holds value of property readOnly. */
     private String readOnly;

     /** Holds value of property readOnlyBool. */
     private boolean readOnlyBool;

     private boolean isSkipFirstBool(){
         return mSkipFirstBool;
     }

     private boolean isTokenizeBool(){
         return mTokenizeBool;
     }

     private String datePromptText;


     /**
      *preforms end tag processing
      */
     public int doEndTag() throws JspException {
         return SKIP_BODY;
     }


     /** Getter for property form.
      * @return Value of property form.
      *
      */
     public String getForm() {
         return this.form;
     }

     /** Setter for property form.
      * @param form New value of property form.
      *
      */
     public void setForm(String form) {
         this.form = form;
     }

     /** Getter for property allPrefix.
      * @return Value of property allPrefix.
      *
      */
     public String getChildPrefix() {
         if(this.childPrefix == null){
             this.childPrefix = this.altChildElementPrefix;
         }
         return this.childPrefix;
     }

     /** Setter for property allPrefix.
      * @param allPrefix New value of property allPrefix.
      *
      */
     public void setChildPrefix(String childPrefix) {
         this.childPrefix = childPrefix;
     }

     /** Getter for property allPostfix.
      * @return Value of property allPostfix.
      *
      */
     public String getElementPostfix() {
         return this.elementPostfix;
     }

     /** Setter for property allPostfix.
      * @param allPostfix New value of property allPostfix.
      *
      */
     public void setElementPostfix(String elementPostfix) {
         this.elementPostfix = elementPostfix;
     }

     /** Getter for property childPostfix.
      * @return Value of property childPostfix.
      *
      */
     public String getChildPostfix() {
         return this.childPostfix;
     }

     /** Setter for property childPostfix.
      * @param childPostfix New value of property childPostfix.
      *
      */
     public void setChildPostfix(String childPostfix) {
         this.childPostfix = childPostfix;
     }

     /** Getter for property helpTextPrefix.
      * @return Value of property helpTextPrefix.
      *
      */
     public String getHelpTextPrefix() {
         return this.helpTextPrefix;
     }

     /** Setter for property helpTextPrefix.
      * @param helpTextPrefix New value of property helpTextPrefix.
      *
      */
     public void setHelpTextPrefix(String helpTextPrefix) {
         this.helpTextPrefix = helpTextPrefix;
     }

     /** Getter for property name.
      * @return Value of property name.
      *
      */
     public String getName() {
         return this.name;
     }

     /** Setter for property name.
      * @param name New value of property name.
      *
      */
     public void setName(String name) {
         this.name = name;
     }

     /** Getter for property skipFirst.
      * @return Value of property skipFirst.
      *
      */
     public String getSkipFirst() {
         return this.skipFirst;
     }

     /** Setter for property skipFirst.
      * @param skipFirst New value of property skipFirst.
      *
      */
     public void setSkipFirst(String skipFirst) {
         if(Utility.isTrue(skipFirst)){
             mSkipFirstBool = true;
         }else{
             mSkipFirstBool = false;
         }
         this.skipFirst = skipFirst;
     }

     /** Getter for property childSeperator.
      * @return Value of property childSeperator.
      *
      */
     public String getChildSeperator() {
         return this.childSeperator;
     }

     /** Setter for property childSeperator.
      * @param childSeperator New value of property childSeperator.
      *
      */
     public void setChildSeperator(String childSeperator) {
         this.childSeperator = childSeperator;
     }

     /** Getter for property tokenize.
      * @return Value of property tokenize.
      *
      */
     public String getTokenize() {
         return this.tokenize;
     }

     /** Setter for property tokenize.
      * @param tokenize New value of property tokenize.
      *
      */
     public void setTokenize(String tokenize) {
         if(Utility.isTrue(tokenize)){
             mTokenizeBool = true;
         }else{
             mTokenizeBool = false;
         }
         this.tokenize = tokenize;
     }

     /** Getter for property elementPrefix.
      * @return Value of property elementPrefix.
      *
      */
     public String getElementPrefix() {
         return this.elementPrefix;
     }

     /** Setter for property elementPrefix.
      * @param elementPrefix New value of property elementPrefix.
      *
      */
     public void setElementPrefix(String elementPrefix) {
         this.elementPrefix = elementPrefix;
     }

     /** Getter for property profileQuestionTypeCdPrefix.
      * @return Value of property profileQuestionTypeCdPrefix.
      *
      */
     public String getProfileQuestionTypeCdPrefix() {
         return this.profileQuestionTypeCdPrefix;
     }

     /** Setter for property profileQuestionTypeCdPrefix.
      * @param profileQuestionTypeCdPrefix New value of property profileQuestionTypeCdPrefix.
      *
      */
     public void setProfileQuestionTypeCdPrefix(String profileQuestionTypeCdPrefix) {
         this.profileQuestionTypeCdPrefix = profileQuestionTypeCdPrefix;
     }

     /** Getter for property profileStatusCdPrefix.
      * @return Value of property profileStatusCdPrefix.
      *
      */
     public String getProfileStatusCdPrefix() {
         return this.profileStatusCdPrefix;
     }

     /** Setter for property profileStatusCdPrefix.
      * @param profileStatusCdPrefix New value of property profileStatusCdPrefix.
      *
      */
     public void setProfileStatusCdPrefix(String profileStatusCdPrefix) {
         this.profileStatusCdPrefix = profileStatusCdPrefix;
     }

     /** Getter for property readOnlyPrefix.
      * @return Value of property readOnlyPrefix.
      *
      */
     public String getReadOnlyPrefix() {
         return this.readOnlyPrefix;
     }

     /** Setter for property readOnlyPrefix.
      * @param readOnlyPrefix New value of property readOnlyPrefix.
      *
      */
     public void setReadOnlyPrefix(String readOnlyPrefix) {
         this.readOnlyPrefix = readOnlyPrefix;
     }

     /** Getter for property profileMetaPrefix.
      * @return Value of property profileMetaPrefix.
      *
      */
     public String getProfileMetaPrefix() {
         return this.profileMetaPrefix;
     }

     /** Setter for property profileMetaPrefix.
      * @param profileMetaPrefix New value of property profileMetaPrefix.
      *
      */
     public void setProfileMetaPrefix(String profileMetaPrefix) {
         this.profileMetaPrefix = profileMetaPrefix;
     }

     /** Getter for property profileOrderStringPrefix.
      * @return Value of property profileOrderStringPrefix.
      *
      */
     public String getProfileOrderPrefix() {
         return this.profileOrderPrefix;
     }

     /** Setter for property profileOrderStringPrefix.
      * @param profileOrderStringPrefix New value of property profileOrderStringPrefix.
      *
      */
     public void setProfileOrderPrefix(String profileOrderPrefix) {
         this.profileOrderPrefix = profileOrderPrefix;
     }

     /** Getter for property metaHelpPrefix.
      * @return Value of property metaHelpPrefix.
      *
      */
     public String getMetaHelpPrefix() {
         return this.metaHelpPrefix;
     }

     /** Setter for property metaHelpPrefix.
      * @param metaHelpPrefix New value of property metaHelpPrefix.
      *
      */
     public void setMetaHelpPrefix(String metaHelpPrefix) {
         this.metaHelpPrefix = metaHelpPrefix;
     }

     /** Getter for property metaTypeCdPrefix.
      * @return Value of property metaTypeCdPrefix.
      *
      */
     public String getMetaTypeCdPrefix() {
         return this.metaTypeCdPrefix;
     }

     /** Setter for property metaTypeCdPrefix.
      * @param metaTypeCdPrefix New value of property metaTypeCdPrefix.
      *
      */
     public void setMetaTypeCdPrefix(String metaTypeCdPrefix) {
         this.metaTypeCdPrefix = metaTypeCdPrefix;
     }

     /** Getter for property admin.
      * @return Value of property admin.
      *
      */
     public String getAdmin() {
         return this.admin;
     }

     /** Setter for property admin.
      * @param admin New value of property admin.
      *
      */
     public void setAdmin(String admin) {
         if(Utility.isTrue(admin)){
             mAdministrationMode = true;
         }else{
             mAdministrationMode = false;
         }
         this.admin = admin;
     }

     public void setDebug(String v) {
         if(Utility.isTrue(v)){
             mShowDebug = true;
         }else{
             mShowDebug = false;
         }
         this.debug = v;
     }


     /** Getter for property altChildElementPrefix.
      * @return Value of property altChildElementPrefix.
      *
      */
     public String getAltChildElementPrefix() {
         if(this.altChildElementPrefix == null){
             this.altChildElementPrefix = this.elementPrefix;
         }
         return this.altChildElementPrefix;
     }

     /** Setter for property altChildElementPrefix.
      * @param altChildElementPrefix New value of property altChildElementPrefix.
      *
      */
     public void setAltChildElementPrefix(String altChildElementPrefix) {
         this.altChildElementPrefix = altChildElementPrefix;
     }

     /** Getter for property formElementJavascript.
      * @return Value of property formElementJavascript.
      *
      */
     public String getFormElementJavascript() {
         return this.formElementJavascript;
     }

     /** Setter for property formElementJavascript.
      * @param formElementJavascript New value of property formElementJavascript.
      *
      */
     public void setFormElementJavascript(String formElementJavascript) {
         this.formElementJavascript = formElementJavascript;
     }

     /** Getter for property renderHiddenNumberDependants.
      * @return Value of property renderHiddenNumberDependants.
      *
      */
     public int getRenderHiddenNumberDependants() {
         return this.renderHiddenNumberDependants;
     }

     /** Setter for property renderHiddenNumberDependants.
      * @param renderHiddenNumberDependants New value of property renderHiddenNumberDependants.
      *
      */
     public void setRenderHiddenNumberDependants(int pRenderHiddenNumberDependants) {
         renderHiddenNumberDependants = pRenderHiddenNumberDependants;
     }

     /** Getter for property helpTextPostFix.
      * @return Value of property helpTextPostFix.
      *
      */
     public String getHelpTextPostFix() {
         return this.helpTextPostFix;
     }

     /** Setter for property helpTextPostFix.
      * @param helpTextPostFix New value of property helpTextPostFix.
      *
      */
     public void setHelpTextPostFix(String helpTextPostFix) {
         this.helpTextPostFix = helpTextPostFix;
     }

     /** Getter for property metaStatusCdPrefix.
      * @return Value of property metaStatusCdPrefix.
      *
      */
     public String getProfileMetaStatusCdPrefix() {
         return this.profileMetaStatusCdPrefix;
     }

     /** Setter for property metaStatusCdPrefix.
      * @param metaStatusCdPrefix New value of property metaStatusCdPrefix.
      *
      */
     public void setProfileMetaStatusCdPrefix(String profileMetaStatusCdPrefix) {
         this.profileMetaStatusCdPrefix = profileMetaStatusCdPrefix;
     }

     /** Getter for property formElementParentOnlyJavascript.
      * @return Value of property formElementParentOnlyJavascript.
      *
      */
     public String getFormElementParentOnlyJavascript() {
         return this.formElementParentOnlyJavascript;
     }

     /** Setter for property formElementParentOnlyJavascript.
      * @param formElementParentOnlyJavascript New value of property formElementParentOnlyJavascript.
      *
      */
     public void setFormElementParentOnlyJavascript(String formElementParentOnlyJavascript) {
         this.formElementParentOnlyJavascript = formElementParentOnlyJavascript;
     }

     /** Getter for property formSelectElementParentOnlyJavascript.
      * @return Value of property formSelectElementParentOnlyJavascript.
      *
      */
     public String getFormSelectElementParentOnlyJavascript() {
         return this.formSelectElementParentOnlyJavascript;
     }

     /** Setter for property formSelectElementParentOnlyJavascript.
      * @param formSelectElementParentOnlyJavascript New value of property formSelectElementParentOnlyJavascript.
      *
      */
     public void setFormSelectElementParentOnlyJavascript(String formSelectElementParentOnlyJavascript) {
         this.formSelectElementParentOnlyJavascript = formSelectElementParentOnlyJavascript;
     }

     /** Getter for property errorMessgePrefix.
      * @return Value of property errorMessgePrefix.
      *
      */
     public String getErrorMessgePrefix() {
         return this.errorMessgePrefix;
     }

     /** Setter for property errorMessgePrefix.
      * @param errorMessgePrefix New value of property errorMessgePrefix.
      *
      */
     public void setErrorMessgePrefix(String errorMessgePrefix) {
         this.errorMessgePrefix = errorMessgePrefix;
     }

     /** Getter for property errorMessgePostfix.
      * @return Value of property errorMessgePostfix.
      *
      */
     public String getErrorMessgePostfix() {
         return this.errorMessgePostfix;
     }

     /** Setter for property errorMessgePostfix.
      * @param errorMessgePostfix New value of property errorMessgePostfix.
      *
      */
     public void setErrorMessgePostfix(String errorMessgePostfix) {
         this.errorMessgePostfix = errorMessgePostfix;
     }

     /** Getter for property otherStyle.
      * @return Value of property otherStyle.
      *
      */
     public String getOtherStyle() {
         return this.otherStyle;
     }

     /** Setter for property otherStyle.
      * @param otherStyle New value of property otherStyle.
      *
      */
     public void setOtherStyle(String otherStyle) {
         this.otherStyle = otherStyle;
     }

     /** Getter for property useClone.
      * @return Value of property useClone.
      *
      */
     public String getUseClone() {
         return this.useClone;
     }

     /** Setter for property useClone.
      * @param useClone New value of property useClone.
      *
      */
     public void setUseClone(String useClone) {
         if(Utility.isTrue(useClone)){
             this.useCloneBool = true;
         }else{
             this.useCloneBool = false;
         }
         this.useClone = useClone;
     }

     /** Getter for property useCloneBool.
      * @return Value of property useCloneBool.
      *
      */
     public boolean isUseCloneBool() {
         return this.useCloneBool;
     }

     /** Setter for property useCloneBool.
      * @param useCloneBool New value of property useCloneBool.
      *
      */
     public void setUseCloneBool(boolean useCloneBool) {
         this.useCloneBool = useCloneBool;
     }

     /** Getter for property readOnly.
      * @return Value of property readOnly.
      *
      */
     public String getReadOnly() {
         return this.readOnly;
     }

     /** Setter for property readOnly.
      * @param readOnly New value of property readOnly.
      *
      */
     public void setReadOnly(String readOnly) {
         if(Utility.isTrue(readOnly)){
             this.readOnlyBool = true;
         }else{
             this.readOnlyBool = false;
         }
         this.readOnly = readOnly;
     }

     /** Getter for property readOnlyBool.
      * @return Value of property readOnlyBool.
      *
      */
     public boolean isReadOnlyBool() {
         return this.readOnlyBool;
     }

     /** Setter for property readOnlyBool.
      * @param readOnlyBool New value of property readOnlyBool.
      *
      */
     public void setReadOnlyBool(boolean readOnlyBool) {
         this.readOnlyBool = readOnlyBool;
     }

     public String getDatePromptText() {
       return this.datePromptText;
     }

     public void setDatePromptText(String v) {
       this.datePromptText = v;
     }

/// for xpedx
     public String getInputTextPrefix() {
         return inputTextPrefix;
     }

     public void setInputTextPrefix(String v) {
         inputTextPrefix = v;
     }

    public String getInputTextPostfix() {
        return inputTextPostfix;
    }

    public void setInputTextPostfix(String v) {
        inputTextPostfix = v;
    }

    public String getShowModDate() {
        return showModDate;
    }

    public void setShowModDate(String v) {
        showModDate = v;
    }

    public String getShowModBy() {
        return showModBy;
    }

    public void setShowModBy(String v) {
        showModBy = v;
    }

    public String getTextInputWidth() {
        return textInputWidth;
    }

    public void setTextInputWidth(String v) {
        textInputWidth = v;
    }
}
