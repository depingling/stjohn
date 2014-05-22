
/* DO NOT EDIT - Generated code from XSL file ViewObject.xsl */

package com.cleanwise.service.api.value;

/**
 * Title:        ProfileView
 * Description:  This is a ViewObject class for UI.
 * Purpose:      
 * Copyright:    Copyright (c) 2001
 * Company:      CleanWise, Inc.
 * @author       Generated Code from XSL file ViewObject.xsl
 */

import com.cleanwise.service.api.framework.*;
import java.lang.*;
import java.util.*;
import java.io.*;
import org.w3c.dom.*;




/**
 * <code>ProfileView</code> is a ViewObject class for UI.
 */
public class ProfileView
extends ValueObject
{
   
    private static final long serialVersionUID = 5482849225911592736L;
    private ProfileData mProfileData;
    private ProfileMetaDataVector mProfileMetaDataVector;
    private ProfileViewVector mChildren;
    private ProfileDetailDataVector mProfileDetailDataVector;

    /**
     * Constructor.
     */
    public ProfileView ()
    {
                mProfileData = ProfileData.createValue();
        
                mProfileMetaDataVector = new ProfileMetaDataVector();
        
                mChildren = new ProfileViewVector();
        
                mProfileDetailDataVector = new ProfileDetailDataVector();
        
    }

    /**
     * Constructor. 
     */
    public ProfileView(ProfileData parm1, ProfileMetaDataVector parm2, ProfileViewVector parm3, ProfileDetailDataVector parm4)
    {
        mProfileData = parm1;
        mProfileMetaDataVector = parm2;
        mChildren = parm3;
        mProfileDetailDataVector = parm4;
        
    }

    /**
     * Creates a new ProfileView
     *
     * @return
     *  Newly initialized ProfileView object.
     */
    public static ProfileView createValue () 
    {
        ProfileView valueView = new ProfileView();

        return valueView;
    }

    /**
     * Returns a String representation of the value object
     *
     * @return
     *  The String representation of this ProfileView object
     */
    public String toString()
    {
        return "[" + "ProfileData=" + mProfileData + ", ProfileMetaDataVector=" + mProfileMetaDataVector + ", Children=" + mChildren + ", ProfileDetailDataVector=" + mProfileDetailDataVector + "]";
    }

    /**
     * Converts value object to XML representation.
     *
     * @return Element.
     */
    public Element toXml(Document doc) {
        Element root = doc.createElement("Profile");
	root.setAttribute("Id", String.valueOf(mProfileData));

	Element node;

        node = doc.createElement("ProfileMetaDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileMetaDataVector)));
        root.appendChild(node);

        node = doc.createElement("Children");
        node.appendChild(doc.createTextNode(String.valueOf(mChildren)));
        root.appendChild(node);

        node = doc.createElement("ProfileDetailDataVector");
        node.appendChild(doc.createTextNode(String.valueOf(mProfileDetailDataVector)));
        root.appendChild(node);

        return root;
    }

    /**
    * Creates a copy of the object
    * @return a copy of this object
    */
    public ProfileView copy()  {
      ProfileView obj = new ProfileView();
      obj.setProfileData(mProfileData);
      obj.setProfileMetaDataVector(mProfileMetaDataVector);
      obj.setChildren(mChildren);
      obj.setProfileDetailDataVector(mProfileDetailDataVector);
      
      return obj;
    }

    
    /**
     * Sets the ProfileData property.
     *
     * @param pProfileData
     *  ProfileData to use to update the property.
     */
    public void setProfileData(ProfileData pProfileData){
        this.mProfileData = pProfileData;
    }
    /**
     * Retrieves the ProfileData property.
     *
     * @return
     *  ProfileData containing the ProfileData property.
     */
    public ProfileData getProfileData(){
        return mProfileData;
    }


    /**
     * Sets the ProfileMetaDataVector property.
     *
     * @param pProfileMetaDataVector
     *  ProfileMetaDataVector to use to update the property.
     */
    public void setProfileMetaDataVector(ProfileMetaDataVector pProfileMetaDataVector){
        this.mProfileMetaDataVector = pProfileMetaDataVector;
    }
    /**
     * Retrieves the ProfileMetaDataVector property.
     *
     * @return
     *  ProfileMetaDataVector containing the ProfileMetaDataVector property.
     */
    public ProfileMetaDataVector getProfileMetaDataVector(){
        return mProfileMetaDataVector;
    }


        /**
        *Index accessible getter for property ProfileMetaDataVector.  This has the same effect as calling:
        *<code >
        *getProfileMetaDataVector().get(pIndex);
        *</code>
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *<code>
        *ProfileMetaDataVectorElement[pIndex]
        *</code>
        *Syntax.
        *@param int the index you wish to access.
        *@returns ProfileMetaData at the specified index.
        */
        public ProfileMetaData getProfileMetaDataVectorElement(int pIndex){
          while(pIndex >= mProfileMetaDataVector.size()){
                mProfileMetaDataVector.add(ProfileMetaData.createValue());
          }
          return (ProfileMetaData) mProfileMetaDataVector.get(pIndex);
        }

        /**
        *Index accessible setter for property ProfileMetaDataVector.  This has the same effect as calling:
        *<code >
        *getProfileMetaDataVector().add(pIndex,pProfileMetaDataVector);
        *</code>
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *<code>
        *ProfileMetaDataVectorElement[pIndex]
        *</code>
        *Syntax.
        *@param int the index you wish to access.
        *@param ProfileMetaData new value of property.
        */
        public void setProfileMetaDataVectorElement(int pIndex,ProfileMetaData pProfileMetaDataVector){
          while(pIndex > mProfileMetaDataVector.size()){
                mProfileMetaDataVector.add(ProfileMetaData.createValue());
          }
          mProfileMetaDataVector.add(pIndex,pProfileMetaDataVector);
        }

    /**
     * Sets the Children property.
     *
     * @param pChildren
     *  ProfileViewVector to use to update the property.
     */
    public void setChildren(ProfileViewVector pChildren){
        this.mChildren = pChildren;
    }
    /**
     * Retrieves the Children property.
     *
     * @return
     *  ProfileViewVector containing the Children property.
     */
    public ProfileViewVector getChildren(){
        return mChildren;
    }


        /**
        *Index accessible getter for property Children.  This has the same effect as calling:
        *<code >
        *getChildren().get(pIndex);
        *</code>
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *<code>
        *ChildrenElement[pIndex]
        *</code>
        *Syntax.
        *@param int the index you wish to access.
        *@returns ProfileView at the specified index.
        */
        public ProfileView getChildrenElement(int pIndex){
          while(pIndex >= mChildren.size()){
                mChildren.add(ProfileView.createValue());
          }
          return (ProfileView) mChildren.get(pIndex);
        }

        /**
        *Index accessible setter for property Children.  This has the same effect as calling:
        *<code >
        *getChildren().add(pIndex,pChildren);
        *</code>
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *<code>
        *ChildrenElement[pIndex]
        *</code>
        *Syntax.
        *@param int the index you wish to access.
        *@param ProfileView new value of property.
        */
        public void setChildrenElement(int pIndex,ProfileView pChildren){
          while(pIndex > mChildren.size()){
                mChildren.add(ProfileView.createValue());
          }
          mChildren.add(pIndex,pChildren);
        }

    /**
     * Sets the ProfileDetailDataVector property.
     *
     * @param pProfileDetailDataVector
     *  ProfileDetailDataVector to use to update the property.
     */
    public void setProfileDetailDataVector(ProfileDetailDataVector pProfileDetailDataVector){
        this.mProfileDetailDataVector = pProfileDetailDataVector;
    }
    /**
     * Retrieves the ProfileDetailDataVector property.
     *
     * @return
     *  ProfileDetailDataVector containing the ProfileDetailDataVector property.
     */
    public ProfileDetailDataVector getProfileDetailDataVector(){
        return mProfileDetailDataVector;
    }


        /**
        *Index accessible getter for property ProfileDetailDataVector.  This has the same effect as calling:
        *<code >
        *getProfileDetailDataVector().get(pIndex);
        *</code>
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *<code>
        *ProfileDetailDataVectorElement[pIndex]
        *</code>
        *Syntax.
        *@param int the index you wish to access.
        *@returns ProfileDetailData at the specified index.
        */
        public ProfileDetailData getProfileDetailDataVectorElement(int pIndex){
          while(pIndex >= mProfileDetailDataVector.size()){
                mProfileDetailDataVector.add(ProfileDetailData.createValue());
          }
          return (ProfileDetailData) mProfileDetailDataVector.get(pIndex);
        }

        /**
        *Index accessible setter for property ProfileDetailDataVector.  This has the same effect as calling:
        *<code >
        *getProfileDetailDataVector().add(pIndex,pProfileDetailDataVector);
        *</code>
        *But will create all necessary empty return types such that an array out of bounds exception
        *is not thrown, and this method can be utilized from jsp using the:
        *<code>
        *ProfileDetailDataVectorElement[pIndex]
        *</code>
        *Syntax.
        *@param int the index you wish to access.
        *@param ProfileDetailData new value of property.
        */
        public void setProfileDetailDataVectorElement(int pIndex,ProfileDetailData pProfileDetailDataVector){
          while(pIndex > mProfileDetailDataVector.size()){
                mProfileDetailDataVector.add(ProfileDetailData.createValue());
          }
          mProfileDetailDataVector.add(pIndex,pProfileDetailDataVector);
        }

    
}
