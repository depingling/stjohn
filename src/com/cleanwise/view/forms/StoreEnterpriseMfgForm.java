package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.IdVector;
import com.cleanwise.service.api.value.PairViewVector;

import java.util.HashMap;


public class StoreEnterpriseMfgForm extends StorePortalBaseForm {

    HashMap<Integer, String> pEnterpriseMfgNames;
    HashMap<Integer, String> pManagedMfgNames;

    IdVector pEnterpriseMfgIds;
    IdVector pManagedMfgIds;

    PairViewVector pLinkedMfgIds;

    String pSelectedEnterpriseMfg;
    String pSelectedManagedMfg;

    String[] pSelectedMfgLinks;

    public HashMap<Integer, String> getEnterpriseMfgNames() {
        return pEnterpriseMfgNames;
    }

    public void setEnterpriseMfgNames(HashMap<Integer, String> enterpriseMfgNames) {
        this.pEnterpriseMfgNames = enterpriseMfgNames;
    }

    public HashMap<Integer, String> getManagedMfgNames() {
        return pManagedMfgNames;
    }

    public void setManagedMfgNames(HashMap<Integer, String> managedMfgNames) {
        this.pManagedMfgNames = managedMfgNames;
    }

    public IdVector getEnterpriseMfgIds() {
        return pEnterpriseMfgIds;
    }

    public void setEnterpriseMfgIds(IdVector enterpriseMfgIds) {
        this.pEnterpriseMfgIds = enterpriseMfgIds;
    }

    public IdVector getManagedMfgIds() {
        return pManagedMfgIds;
    }

    public void setManagedMfgIds(IdVector managedMfgIds) {
        this.pManagedMfgIds = managedMfgIds;
    }

    public PairViewVector getLinkedMfgIds() {
        return pLinkedMfgIds;
    }

    public void setLinkedMfgIds(PairViewVector linkedMfgIds) {
        this.pLinkedMfgIds = linkedMfgIds;
    }


    public HashMap<Integer, String> getpEnterpriseMfgNames() {
        return pEnterpriseMfgNames;
    }


    public String getSelectedEnterpriseMfg() {
        return pSelectedEnterpriseMfg;
    }

    public void setSelectedEnterpriseMfg(String selectedEnterpriseMfg) {
        this.pSelectedEnterpriseMfg = selectedEnterpriseMfg;
    }

    public String getSelectedManagedMfg() {
        return pSelectedManagedMfg;
    }

    public void setSelectedManagedMfg(String selectedManagedMfg) {
        this.pSelectedManagedMfg = selectedManagedMfg;
    }

    public String[] getSelectedMfgLinks() {
        return pSelectedMfgLinks;
    }

    public void setSelectedMfgLinks(String[] selectedMfgLinks) {
        this.pSelectedMfgLinks = selectedMfgLinks;
    }
}
