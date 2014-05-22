package com.cleanwise.service.apps.dataexchange.xpedx;

import com.cleanwise.service.api.value.XpedxCatalogItemView;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;

import org.apache.log4j.Logger;


public class IXMasterData implements Serializable {

    private static final Logger log = Logger.getLogger(IXMasterData.class);

    List<MasterCustomerData> mMasterCustomerDataList;

    private List<Line<XpedxCatalogItemView>> mGoodLines;
    private List<Line<XpedxCatalogItemView>> mBadLines;
    private List<Line<XpedxCatalogItemView>> mInboundLines;

    private ArrayList<String> mErrors;

    public IXMasterData() {
        mMasterCustomerDataList = new ArrayList<MasterCustomerData>();
        this.mGoodLines = new ArrayList<Line<XpedxCatalogItemView>>();
        this.mBadLines = new ArrayList<Line<XpedxCatalogItemView>>();
        this.mErrors = new ArrayList<String>();
        this.mInboundLines = new ArrayList<Line<XpedxCatalogItemView>>();
    }

    public List<MasterCustomerData> getMasterCustomerDataList() {
        return mMasterCustomerDataList;
    }

    public void setMasterCustomerDataList(List<MasterCustomerData> pMasterCustomerDataList) {
        this.mMasterCustomerDataList = pMasterCustomerDataList;
    }

    public void add(MasterCustomerData pData) {
        this.mMasterCustomerDataList.add(pData);
    }

    public void addGoodLine(int pLine, XpedxCatalogItemView pItem) {
        mGoodLines.add(new GoodLine<XpedxCatalogItemView>(pLine, pItem));
    }

    public void addBadLine(int pLine, XpedxCatalogItemView pItem, List<String> pLineErrors) {
        mBadLines.add(new BadLine<XpedxCatalogItemView>(pLine, pItem, pLineErrors));
    }

    public void addInboundLine(int pLine, XpedxCatalogItemView pItem) {
        mInboundLines.add(new GoodLine<XpedxCatalogItemView>(pLine, pItem));
    }

    public boolean hasErrors() {
        return !mErrors.isEmpty();
    }

    public List<Line<XpedxCatalogItemView>> getGoodLines() {
        return mGoodLines;
    }

    public List<Line<XpedxCatalogItemView>> getBadLines() {
        return mBadLines;
    }

    public List<String> getErrors() {
        return mErrors;
    }

    public List<Line<XpedxCatalogItemView>> getInboundLines() {
        return mInboundLines;
    }

    public void setInboundLines(List<Line<XpedxCatalogItemView>> pInboundLines) {
        this.mInboundLines = pInboundLines;
    }

    public boolean isProcessed(CatalogReference pCatalogReference) {

        List<Line<XpedxCatalogItemView>> goodLines = getGoodLines();
        List<Line<XpedxCatalogItemView>> badLines = getBadLines();
        List<Line<XpedxCatalogItemView>> inboundLines = getInboundLines();

        List<Line<XpedxCatalogItemView>> badCatalogLines = filter(badLines, pCatalogReference);

        if (!badCatalogLines.isEmpty()) {
            log.info("isProcessed()=> BAD CATALOG ITEMS FOUND. CATALOG " + pCatalogReference);
            return false;
        } else {
            List<Line<XpedxCatalogItemView>> inboundCatalogLines = filter(inboundLines, pCatalogReference);
            List<Line<XpedxCatalogItemView>> goodCatalogLines = filter(goodLines, pCatalogReference);
            return containsAll(inboundCatalogLines, goodCatalogLines);
        }

    }

    public boolean isProcessed(AccountReference pAccountReference) {

        List<Line<XpedxCatalogItemView>> goodLines = getGoodLines();
        List<Line<XpedxCatalogItemView>> badLines = getBadLines();
        List<Line<XpedxCatalogItemView>> inboundLines = getInboundLines();

        List<Line<XpedxCatalogItemView>> badCatalogLines = filter(badLines, pAccountReference);

        if (!badCatalogLines.isEmpty()) {
            log.info("isProcessed()=> BAD ACCOUNT ITEMS FOUND. ACCOUNT " + pAccountReference);
            return false;
        } else {
            List<Line<XpedxCatalogItemView>> inboundCatalogLines = filter(inboundLines, pAccountReference);
            List<Line<XpedxCatalogItemView>> goodCatalogLines = filter(goodLines, pAccountReference);
            return containsAll(inboundCatalogLines, goodCatalogLines);
        }

    }

    private boolean containsAll(List<Line<XpedxCatalogItemView>> pLines1, List<Line<XpedxCatalogItemView>> pLines2) {

        Set<Integer> lines1Set = new HashSet<Integer>();
        for (Line line : pLines1) {
            lines1Set.add(line.getLine());
        }

        Set<Integer> lines2Set = new HashSet<Integer>();
        for (Line line : pLines2) {
            lines2Set.add(line.getLine());
        }

        return lines1Set.containsAll(lines2Set);

    }

    private List<Line<XpedxCatalogItemView>> filter(List<Line<XpedxCatalogItemView>> pLines, CatalogReference pReference) {
        List<Line<XpedxCatalogItemView>> rList = new ArrayList<Line<XpedxCatalogItemView>>();
        for (Line<XpedxCatalogItemView> line : pLines) {
            XpedxCatalogItemView item = line.getItem();
            CatalogReference catalogReference = new CatalogReference(item.getAccountNumber(), item.getCatalogID());
            if (pReference.equals(catalogReference)) {
                rList.add(line);
            }
        }
        return rList;
    }


    private List<Line<XpedxCatalogItemView>> filter(List<Line<XpedxCatalogItemView>> pLines, AccountReference pReference) {
        List<Line<XpedxCatalogItemView>> rList = new ArrayList<Line<XpedxCatalogItemView>>();
        for (Line<XpedxCatalogItemView> line : pLines) {
            XpedxCatalogItemView item = line.getItem();
            AccountReference catalogReference = new AccountReference(item.getAccountNumber());
            if (pReference.equals(catalogReference)) {
                rList.add(line);
            }
        }
        return rList;
    }

}
