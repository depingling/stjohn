/*
 * ProfileViewContainer.java
 *
 * Created on August 11, 2003, 3:14 PM
 */

package com.cleanwise.view.utils;
import org.apache.struts.upload.FormFile;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.Comparator;
import java.util.Collections;
/**
 *
 * @author  bstevens
 */
public class ProfileViewContainer {

    /**
     *Returns a "clone" of this ProfileViewContainer without the detail.  The individual elements
     *are not truly cloned.
     */
    public ProfileViewContainer cloneNoDetail(){
        return cloneNoDetail(this);
    }

    private ProfileViewContainer cloneNoDetail(ProfileViewContainer pProfileViewContainer){
        ProfileViewContainer newCon = new ProfileViewContainer();
        ProfileView newProf = ProfileView.createValue();
        newCon.setProfileView(newProf);
        newProf.setProfileData(pProfileViewContainer.getProfileView().getProfileData());
        newProf.setProfileMetaDataVector(pProfileViewContainer.getProfileView().getProfileMetaDataVector());
        newProf.setProfileDetailDataVector(new ProfileDetailDataVector());
        newCon.setChildren(new ArrayList());
        if(pProfileViewContainer.getChildren() != null){
            Iterator it = pProfileViewContainer.getChildren().iterator();
            while(it.hasNext()){
                ProfileViewContainer child = (ProfileViewContainer) it.next();
                newCon.getChildren().add(cloneNoDetail(child));
            }
        }
        return newCon;
    }



    /** Holds value of property profileView. */
    private ProfileView profileView;

    /** Holds value of property imageUploadFile. */
    private FormFile imageUploadFile;

    /** Holds value of property children. */
    private ArrayList children = new ArrayList();

    /** Holds value of property profileOrderString. */
    private String profileOrderString;

    /** Holds value of property errorMessage. */
    private String errorMessage;

    /** Holds value of property detailValues. */
    private String[] detailValues;

    /** Creates a new instance of ProfileViewContainer */
    private ProfileViewContainer() {
    }

    /**
     *Syncs up the detailValues array with the detailDataVector of the profileView property
     */
    private void syncProfileViewDetailDataVector(int pBusId){
        //if this is a multiple choices question type then read in the
        //array of detail values
        if(detailValues != null &&
           RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICES.equals
           (getProfileView().getProfileData().getProfileQuestionTypeCd())){
            int currLoopVal = 0;
            for(int i=0;i<detailValues.length;i++){
                ProfileDetailData det = getProfileView().
                    getProfileDetailDataVectorElement(i);
                det.setValue(detailValues[i]);
                if ( det.getLoopValue() > 0 ){
                    currLoopVal = det.getLoopValue();
                }
                det.setLoopValue(currLoopVal);
                det.setBusEntityId(pBusId);
            }

            //clear out the rest
            for(int i=detailValues.length,len=getProfileView().getProfileDetailDataVector().size();i<len;i++){
                getProfileView().getProfileDetailDataVectorElement(i).setValue(null);
            }
        }
    }

    /**
     *Syncs up the detailDataVector of the profileView property with the detailValues array
     */
    private void syncDetailValues(){

        if(RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICES.equals
           (getProfileView().getProfileData().getProfileQuestionTypeCd())){
            int numbValues = getProfileView().getProfileDetailDataVector().size();
            setDetailValues(new String[numbValues]);
            Iterator it = getProfileView().getProfileDetailDataVector().iterator();
            int i = 0;
            while(it.hasNext()){
                ProfileDetailData det = (ProfileDetailData) it.next();
                getDetailValues()[i] = det.getValue();
                i++;
            }
        }
    }

    /**
     *Returns a representation of the current tree as a profile view
     */
    public ProfileView toProfileView(int pBusId){
        syncProfileViewDetailDataVector(pBusId);

        if(children == null){
            return profileView;
        }

        Iterator it = this.children.iterator();
        while(it.hasNext()){
            ProfileViewContainer c = (ProfileViewContainer) it.next();
            profileView.getChildren().add(c.toProfileView(pBusId));
        }
        return profileView;
    }


    /**
     *Debugs the profile.  This method is provided soly for trouble shooting
     */
    public static void debugProfileView(ProfileViewContainer prof,int pLvl){
        StringBuffer buf = new StringBuffer();
        for(int i=0;i<pLvl;i++){
            buf.append(">>>>");
        }
        buf.append(prof.getProfileView().getProfileData().getShortDesc());
        buf.append("["+prof.getProfileView().getProfileDetailDataVector().size()+"]");
        if(prof.getProfileView().getProfileDetailDataVector().size() > 0){
            buf.append("::"+prof.getProfileView().getProfileDetailDataVectorElement(0).getValue());
            buf.append("::"+prof.getProfileView().getProfileDetailDataVectorElement(0).getProfileDetailId());
        }
        Iterator it = prof.getChildren().iterator();
        while(it.hasNext()){
            debugProfileView((ProfileViewContainer) it.next(), pLvl + 1);
        }
    }

    /**
     *Factory method to create a new ProfileViewContainer object
     */
    public static ProfileViewContainer getProfileViewContainer(ProfileView pProfile){
        ProfileViewContainer retVal = getProfileViewContainerRecurs(pProfile);
        return retVal;
    }

    private static ProfileViewContainer getProfileViewContainerRecurs(ProfileView pProfile){
        ProfileViewContainer container = new ProfileViewContainer();
        container.setChildren(new ArrayList());
        container.setProfileView(pProfile);
        container.setProfileOrderString
            (Integer.toString(pProfile.getProfileData().getProfileOrder()));
        container.syncDetailValues();
        if ( RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICES.equals
             (pProfile.getProfileData().getProfileQuestionTypeCd())){
            pProfile.getChildren().clear();
            return container;
        }

        Iterator it = pProfile.getChildren().iterator();
        while(it.hasNext()){
            ProfileView child = (ProfileView) it.next();
            container.getChildren().add(getProfileViewContainerRecurs(child));
        }
        pProfile.getChildren().clear();
        return container;
    }

    /**checks whether a given questions child needs to be answered based off the
     *detail loop value it is on (pIndex)
     */
    public boolean shouldChildAtIndexBeSet(int pIndex){

        //if the parent is null or the parent is a survey then this is
        //a first level question and should be answered
        if(RefCodeNames.PROFILE_TYPE_CD.SURVEY.equals(getProfileView().getProfileData().getProfileTypeCd())){
            return true;
        }

        if ( RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICES.equals(getProfileView().getProfileData().getProfileQuestionTypeCd())){
            // no children are allowed off of a question
            // where more than one answer is allowe.
            return false;
    } else if (RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICE.equals(getProfileView().getProfileData().getProfileQuestionTypeCd())
        || RefCodeNames.PROFILE_QUESTION_TYPE_CD.MULTIPLE_CHOICES.equals(getProfileView().getProfileData().getProfileQuestionTypeCd())){
            Iterator it = getProfileView().getProfileMetaDataVector().iterator();
            while(it.hasNext()){
                ProfileMetaData meta = (ProfileMetaData) it.next();
                Iterator ansIt = getProfileView().getProfileDetailDataVector().iterator();
                boolean retVal = false;
                //loop through detail data
                while(ansIt.hasNext()){
                    ProfileDetailData ans = (ProfileDetailData) ansIt.next();
                    if(ans.getValue() != null && ans.getValue().equals(meta.getValue())){
                        if(RefCodeNames.PROFILE_META_TYPE_CD.CHOICE_SHOW_CHILDREN.equals(meta.getProfileMetaTypeCd())
                        ||RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE_SHOW_CHILDREN.equals(meta.getProfileMetaTypeCd())){
                            return true;
                        }
                    }
                }
                //loop through the array data
                if(getDetailValues() != null){
                    for(int i=0;i<getDetailValues().length;i++){
                        if(getDetailValues()[i].equals(meta.getValue())){
                            if(RefCodeNames.PROFILE_META_TYPE_CD.CHOICE_SHOW_CHILDREN.equals(meta.getProfileMetaTypeCd())
                            ||RefCodeNames.PROFILE_META_TYPE_CD.OTHER_CHOICE_SHOW_CHILDREN.equals(meta.getProfileMetaTypeCd())){
                                return true;
                            }
                        }
                    }
                }
            }
            return false;

        }else if (RefCodeNames.PROFILE_QUESTION_TYPE_CD.FREE_FORM_TEXT.equals(getProfileView().getProfileData().getProfileQuestionTypeCd())){
            if(Utility.isSet(getProfileView().getProfileDetailDataVectorElement(0).getValue())){
                return true;
            }else{
                return false;
            }
        }else if (RefCodeNames.PROFILE_QUESTION_TYPE_CD.NUMBER.equals(getProfileView().getProfileData().getProfileQuestionTypeCd())){
            //we have to make sure that x number of child questions have values, where x is the value
            //of the answer of this parent question
            int answerInt = 0;
            try{
                answerInt =
                Integer.parseInt(getProfileView().getProfileDetailDataVectorElement(0).getValue());
                if(pIndex <= answerInt){
                    return true;
                }else{
                    return false;
                }
            }catch(Exception e){
                //This is delt with elsewere
                return false;
            }

          }else if (RefCodeNames.PROFILE_QUESTION_TYPE_CD.DATE.equals(getProfileView().getProfileData().getProfileQuestionTypeCd())){
              if(Utility.isSet(getProfileView().getProfileDetailDataVectorElement(0).getValue())){
                  return true;
              }else{
                  return false;
              }
        }
        return false;
    }

    private ProfileDetailData mCachedLastProfileDetailData;

    /** Holds value of property loopValue. */
    private int loopValue;

    /**
     *Returns the greatest (most recently) modified profileDetailData object of this
     *profileViewContainer and all of its relatives from this piece of the branch.  This
     *will returned the cahced value, and if any changes have been made this will be incorrect.
     *If there is no cached value it will cache the value the first time.
     *@see setupLastModifiedDetailData
     */
    public ProfileDetailData getLastModifiedDetailData(){
        try{
            if(mCachedLastProfileDetailData == null){
                setupLastModifiedDetailData();
            }
            return mCachedLastProfileDetailData;
        }catch(RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *Resets the cached value of the lastModifiedDetailData property.  This can be called if the cached
     *value is out of sync for any reason
     */
    public void setupLastModifiedDetailData(){
        mCachedLastProfileDetailData = calcLastModifiedDetailData();
    }

    //from the 2 passed in detailData objects returns the one that has the greatest modifed date
    private ProfileDetailData whichDetailDataModifiedLast(ProfileDetailData a, ProfileDetailData b){
        if(a == null){
            return b;
        }else if(b == null){
            return a;
        }else{
            if(a.getModDate() == null){
                return b;
            }else if (b.getModDate() == null){
                return a;
            }else{
                //at this point we have 2 none null dates to compare
                if(a.getModDate().before(b.getModDate())){
                    return b;
                }else{
                    return a;
                }
            }
        }
        //return a;
    }

    /**
     *Returns the greatest (most recently) modified profileDetailData object of this
     *profileViewContainer and all of its relatives from this piece of the branch
     */
    private ProfileDetailData calcLastModifiedDetailData(){
        if(getProfileView().getProfileDetailDataVector() == null){
            return null;
        }
        Iterator it = getProfileView().getProfileDetailDataVector().iterator();
        ProfileDetailData max = null;
        while(it.hasNext()){
            ProfileDetailData det = (ProfileDetailData) it.next();
            max = whichDetailDataModifiedLast(max, det);
        }
        //now do the children
        if(getProfileView().getChildren() == null){
            return max;
        }
        it = getChildren().iterator();
        while(it.hasNext()){
            ProfileViewContainer child = (ProfileViewContainer) it.next();
            ProfileDetailData kidMax = child.getLastModifiedDetailData();
            max = whichDetailDataModifiedLast(max, kidMax);
        }
        return max;
    }


    /** Getter for property profileView.
     * @return Value of property profileView.
     *
     */
    public ProfileView getProfileView() {
        return this.profileView;
    }


    /** Setter for property profileView.
     * @param profileView New value of property profileView.
     *
     */
    public void setProfileView(ProfileView profileView) {
        this.profileView = profileView;
    }

    /** Getter for property imageUploadFile.
     * @return Value of property imageUploadFile.
     *
     */
    public FormFile getImageUploadFile() {
        return this.imageUploadFile;
    }

    /** Setter for property imageUploadFile.
     * @param imageUploadFile New value of property imageUploadFile.
     *
     */
    public void setImageUploadFile(FormFile imageUploadFile) {
        this.imageUploadFile = imageUploadFile;
    }

    /** Getter for property children.
     * @return Value of property children.
     *
     */
    public ArrayList getChildren() {
        return this.children;
    }

    /** Setter for property children.
     * @param children New value of property children.
     *
     */
    public void setChildren(ArrayList children) {
        this.children = children;
    }

    /**
     *Gets a ProfileViewContainer element at the specified index
     */
    public ProfileViewContainer getChildrenElement(int pIndex){

        while(pIndex >= this.children.size()){
            ProfileViewContainer newCont = new ProfileViewContainer();
            newCont.setProfileView(ProfileView.createValue());
            this.children.add(newCont);
        }
        return (ProfileViewContainer) this.children.get(pIndex);
    }

    /** Getter for property profileOrderString.
     * @return Value of property profileOrderString.
     *
     */
    public String getProfileOrderString() {
        return this.profileOrderString;
    }

    /** Setter for property profileOrderString.
     * @param profileOrderString New value of property profileOrderString.
     *
     */
    public void setProfileOrderString(String profileOrderString) {
        this.profileOrderString = profileOrderString;
    }


    /**
     *Returns true if this profileViewContainer has children
     */
    public boolean isHasChildren(){
        return this.getChildren().size() > 0;
    }

    /** Getter for property errorMessage.
     * @return Value of property errorMessage.
     *
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /** Setter for property errorMessage.
     * @param errorMessage New value of property errorMessage.
     *
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /** Getter for property detailValues.
     * @return Value of property detailValues.
     *
     */
    public String[] getDetailValues() {
        return this.detailValues;
    }

    /** Setter for property detailValues.
     * @param detailValues New value of property detailValues.
     *
     */
    public void setDetailValues(String[] detailValues) {
        this.detailValues = detailValues;
    }

    /** Getter for property loopValue.
     * @return Value of property loopValue.
     *
     */
    public int getLoopValue() {
        return this.loopValue;
    }

    /** Setter for property loopValue.
     * @param loopValue New value of property loopValue.
     *
     */
    public void setLoopValue(int loopValue) {
        this.loopValue = loopValue;
    }

    /**
     *Sorts the data for UI presention, recurses through all the children
     */
    public void deepSort(){
        Collections.sort(getChildren(),PROFILE_ORDER_COMPARE);
        Iterator it = getChildren().iterator();
        while(it.hasNext()){
            ProfileViewContainer p = (ProfileViewContainer) it.next();
            p.deepSort();
        }
    }

    /**
     *Sorts the data for UI presenttion, does not recurse through children
     */
    public void shallowSort(){
        Collections.sort(getChildren(),PROFILE_ORDER_COMPARE);
    }

    public static final Comparator PROFILE_ORDER_COMPARE = new Comparator() {
        public int compare(Object o1, Object o2)
        {
            int id1 = ((ProfileViewContainer)o1).getProfileView().getProfileData().getProfileOrder();
            int id2 = ((ProfileViewContainer)o2).getProfileView().getProfileData().getProfileOrder();
            return id1 - id2;
        }
    };
}
