package com.cleanwise.view.utils;

import com.cleanwise.service.api.util.Utility;
import com.cleanwise.view.i18n.ClwI18nUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 */
public class BreadCrumbNavigator {

    public static String className = "BreadCrumbNavigator";

    BreadCrumbContainer container;
    ArrayList cursor;
    BreadCrumbKeyFactory keyFactory;
    int userId;

    public BreadCrumbNavigator() {
        this.container = new BreadCrumbContainer();
        this.cursor = new ArrayList();
        this.keyFactory = new BreadCrumbKeyFactory();
        this.userId = 0;
    }

    public BreadCrumbNavigator(int userId) {
        this.container = new BreadCrumbContainer();
        this.cursor = new ArrayList();
        this.keyFactory = new BreadCrumbKeyFactory();
        this.userId = userId;

    }

    public void set(String display,
                    String href,
                    String message,
                    String shortLinkId,
                    String method) {

        //log("set => display: " + display);
        //log("set => url: " + href);

        if (Utility.isSet(display)) {

            BreadCrumbContainer container = getContainer();
            String key = (String) getKeyFactory().get(display);
            HashMap descriptor = container.getDescriptor();
            //log("set => key: " + key);
            String sKey = "";

            if (Utility.isSet(shortLinkId)) {
                sKey = container.getSKey(shortLinkId);
                if(sKey==null){
                    sKey = key;
                }
            } else {
                BreadCrumbItem parent = getItem(getCursor());
                if (parent != null) {
                    if (parent.getKey().indexOf(key) < 0) {
                        sKey = parent.getKey() + "#" + key;
                    } else {
                        StringTokenizer st = new StringTokenizer(parent.getKey(), "#");
                        while (st.hasMoreElements()) {
                            String el = (String) st.nextElement();
                            //log("set=> el:" + el);
                            if (Utility.isSet(sKey)) {
                                sKey += "#";
                            }
                            sKey += el;
                            if (el.equals(key)) {
                                break;
                            }
                        }
                    }
                } else {
                    sKey = key;
                }
            }

            ArrayList itemPos = (ArrayList) descriptor.get(sKey);
            container.createSID(sKey);
            String sid = (String) container.getSidMap().get(sKey);

            //log("set => sKey : " + sKey);
           // log("set => sid: " + sid);
            //log("set => itemPos : " + itemPos);
            //log("set => cursor : " + getCursor());
            //log("set => descriptor: " + descriptor);

            String origHref = origHref(href, sid);

            //log("set => href: " + href+" origHref: "+origHref);

            if (itemPos != null) {
                BreadCrumbItem cursorItem = getItem(itemPos);
                if (this.userId!=43208) {
                    removeChilds(cursorItem);
                } else if (//Utility.isSet(shortLinkId) ||
                        !origHref(cursorItem.getHref(), sid).equals(origHref)){
                    removeChilds(cursorItem);
                }

                href = addShortLinkId(origHref, sid);
                if(!"POST".equalsIgnoreCase(method)){
                    cursorItem.setHref(href);
                }
                setCursor(new ArrayList(itemPos));
                container.setDescriptor(descriptor);
            } else {

                href=addShortLinkId(href, sid);

                ArrayList cursor = getCursor();
                BreadCrumbItem newItem = new BreadCrumbItem(display, Utility.strNN(href), sKey, message);

                BreadCrumbItem currsorItem = getItem(cursor);
                if (currsorItem == null) {
                    cursor.add(new Integer(0));
                    descriptor.put(sKey, new ArrayList(cursor));
                    container.getItems().add(newItem);
                } else {
                    if (currsorItem.getChilds().size() > 0) {
                        currsorItem.getChilds().remove(currsorItem.getChilds().size() - 1);
                    } 
                    currsorItem.getChilds().add(newItem);
                    cursor.add(new Integer(currsorItem.getChilds().size() - 1));
                    descriptor.put(newItem.getKey(), new ArrayList(cursor));
                }

                container.setDescriptor(descriptor);
                setCursor(cursor);

            }
            setContainer(container);
            //log("set => End/descriptor: " + descriptor + ",currsor:" + getCursor());

        }
    }

    private String origHref(String href, String sid) {
        if (Utility.isSet(href)) {
            if (href.indexOf("&shortLinkId=" + sid) >= 0) {
                href = href.replaceAll("&shortLinkId=" + sid, "");
                return href;
            } else if (href.indexOf("?shortLinkId=" + sid) >= 0) {
                return href.substring(0, href.length() - 1);
            }
        }

        return href;
    }

    private String addShortLinkId(String href, String sid) {
        if (Utility.isSet(href)) {
            if (href.indexOf("?") < 0) {
                href = href + "?" + "shortLinkId=" + sid;
                return href;
            } else if (href.indexOf("shortLinkId=" + sid) < 0) {
                href = href + "&" + "shortLinkId=" + sid;
                return href;
            }
        }
        return href;
    }

    private void removeChilds(BreadCrumbItem currsorItem) {
        Iterator it = currsorItem.getChilds().iterator();
        while (it.hasNext()) {

            BreadCrumbItem item = (BreadCrumbItem) it.next();
            removeChilds(item);

            //Object descO = getContainer().getDescriptor().remove(item.getKey());
            //Object sidO  = getContainer().getSidMap().remove(item.getKey());
            it.remove();
            //log("removeChilds => removed child  descO: " + descO);
            //log("removeChilds => removed child  sidO: " + sidO);
        }

    }

    public BreadCrumbItem getItem(ArrayList cursor) {

        BreadCrumbItem item = null;
        if (!cursor.isEmpty()) {
            item = (BreadCrumbNavigator.BreadCrumbItem) getContainer().getItems().get(((Integer) cursor.get(0)).intValue());
            for (int i = 1; i < cursor.size(); i++) {
                item = getItem(item, ((Integer) cursor.get(i)).intValue());
            }
        }
        return item;
    }

    private BreadCrumbItem getItem(BreadCrumbItem item, int num) {
        return (BreadCrumbItem) item.getChilds().get(num);
    }

    public void set(HttpServletRequest request, String path) {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            String name = getDisplay(path, request.getParameter("display"));
            String queryString = buildQueryString(request);
            set(name, queryString, ClwI18nUtil.getMessageOrNull(request, (String) getKeyFactory().get(name)), getShortLinkId(request),request.getMethod());
        } else if ("POST".equalsIgnoreCase(request.getMethod())){
            String name = getDisplay(path, request.getParameter("display"));
            //log("set => POST:"+name);
            set(name,null, ClwI18nUtil.getMessageOrNull(request, (String) getKeyFactory().get(name)), getShortLinkId(request),request.getMethod());
        }
    }

    private String getShortLinkId(HttpServletRequest request) {
        return request.getParameter("shortLinkId");
    }

    private String buildQueryString(HttpServletRequest request) {
        if(request.getRequestURL()!=null){
            String queryString = request.getRequestURL().toString();
            if (Utility.isSet(request.getQueryString())) {
                queryString += "?" + request.getQueryString();
            }
            return queryString;
        } else {
            return "#";
        }
    }

    public String getDisplay(String path, String parameter) {
        //log("getDisplay => path:" + path);
        if (parameter == null || path.indexOf("templator.jsp")<0) {
            int pageNameBegIdx = path.lastIndexOf("/");
            int pageNameEndIdx = path.lastIndexOf(".");
            if (pageNameBegIdx >= 0 && pageNameEndIdx > 0) {
                return path.substring(pageNameBegIdx + 1, pageNameEndIdx);
            } else {
                return path;
            }

        } else {
            return parameter;
        }
    }


    public ArrayList getCursor() {
        return cursor;
    }

    public void setCursor(ArrayList cursor) {
        this.cursor = cursor;
    }

    public BreadCrumbKeyFactory getKeyFactory() {
        return keyFactory;
    }

    public void setKeyFactory(BreadCrumbKeyFactory keyFactory) {
        this.keyFactory = keyFactory;
    }

    public BreadCrumbContainer getContainer() {
        return container;
    }

    public void setContainer(BreadCrumbContainer container) {
        this.container = container;
    }

    public class BreadCrumbContainer {

        BreadCrumbItemVector items;
        HashMap descriptor;
        HashMap sidMap;
        int seq;

        public BreadCrumbContainer() {
            items = new BreadCrumbItemVector();
            descriptor = new HashMap();
            sidMap = new HashMap();
            seq = 0;
        }

        public HashMap getDescriptor() {
            return descriptor;
        }

        public void setDescriptor(HashMap descriptor) {
            this.descriptor = descriptor;
        }

        public BreadCrumbItemVector getItems() {
            return items;
        }

        public void setItems(BreadCrumbItemVector items) {
            this.items = items;
        }

        public void createSID(String key) {
            if (!sidMap.containsKey(key)) {
                sidMap.put(key, Integer.toString(seq++));
            }
        }

        public String getSID(String key) {
            return (String) sidMap.get(key);
        }

        public String getSKey(String sid) {
            if (sidMap.containsValue(sid)) {
                Iterator it = sidMap.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    if (sidMap.get(key).equals(sid)) {
                        return key;
                    }
                }
            }
            return null;
        }

        public HashMap getSidMap() {
            return sidMap;
        }
    }

    public class BreadCrumbItemVector extends ArrayList {
    }


    public class BreadCrumbItem {

        String href;
        String name;
        String key;
        String message;
        BreadCrumbItemVector childs;

        public BreadCrumbItem(String name, String href, String key, String message) {
            this.name = name;
            this.href = href;
            this.key = key;
            this.message = message;
            this.childs = new BreadCrumbItemVector();
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }


        public BreadCrumbItemVector getChilds() {
            return childs;
        }

        public void setChilds(BreadCrumbItemVector childs) {
            this.childs = childs;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String toString() {
            return "name=" + name + ",href=" + href + ",key=" + key + ",message=" + message + " childs.size:" + childs.size();
        }
    }

    public class BreadCrumbKeyFactory extends HashMap {


        public BreadCrumbKeyFactory() {
            super();

            this.put("msbSites", "ROOT");

            //asset
            this.put("t_userAssetSearch", "userAssets.text.asset");
            this.put("userAssets", "userAssets.text.asset");
            this.put("userAssetProfile", "userAssets.shop.toolbar.header");
            this.put("t_userAssetProfile", "userAssets.shop.toolbar.header");
            this.put("t_userAssetContentDetail", "userAssets.text.toolbar.assetDoc");
            this.put("userAssetContentDetail", "userAssets.text.toolbar.assetDoc");
            this.put("userAssetWarrantyConfig","userAssets.text.assetConfigureWarranties");
            this.put("t_userAssetWarrantiesConfig","userAssets.text.assetConfigureWarranties");
            this.put("userMasterAssetProfile","userAssets.shop.toolbar.masterAsset");
            this.put("t_userMasterAssetProfile","userAssets.shop.toolbar.masterAsset");
            this.put("userAssetCategoryProfile","userAssets.shop.toolbar.assetCategory");
            this.put("t_userAssetCategoryProfile","userAssets.shop.toolbar.assetCategory");
            this.put("t_userMasterAssetSearch","userAssets.text.assetMasterAsset");
            this.put("t_userAssetCategorySearch","userAssets.text.assetCategory");
            //warranty
            this.put("t_userWarrantyDetail", "userWarranty.text.toolbar.warrantyDetails");
            this.put("userWarrantyDetail", "userWarranty.text.toolbar.warrantyDetails");
            this.put("t_userWarrantySearch", "userWarranty.text.toolbar.warranty");
            this.put("t_userWarrantyNoteDetail","userWarranty.text.toolbar.warrantyNotes");
            this.put("t_userAssetWarranty","userWarranty.text.toolbar.assetWarranty");
            this.put("t_userAssetWarrantyDetail","userWarranty.text.toolbar.assetWarrantyDetail");
            this.put("t_userWarrantyDocsDetail","userWarranty.text.toolbar.warrantyDocs");
            this.put("userWarrantyContentDetail","userWarranty.text.toolbar.warrantyDocs");
            //work order
            this.put("t_userWorkOrder", "userWorkOrder.text.findWorkOrder");
            this.put("t_userWorkOrderDetail", "global.label.workOrderSummary");
            this.put("userWorkOrderDetail", "global.label.workOrderSummary");
            this.put("t_userPendingWorkOrders","userWorkOrder.text.pendingWorkOrders");
            this.put("t_userWorkOrderNoteDetail", "userWorkOrder.text.toolbar.workOrderNotes");
            this.put("t_userWorkOrderItemDetail","userWorkOrder.text.toolbar.workOrderItems");
            this.put("userWorkOrderItemDetail","userWorkOrder.text.toolbar.workOrderItems");
            this.put("t_userWorkOrderContentDetail","userWorkOrder.text.toolbar.workOrderContent");
            this.put("userWorkOrderContentDeatail","userWorkOrder.text.toolbar.workOrderContent");
            this.put("t_userWorkOrderScheduler","userWorkOrder.text.scheduler");
            this.put("userWorkOrderScheduler","userWorkOrder.text.scheduler");
            this.put("userPartsOrder","userWorkOrder.text.toolbar.partsOrder");
            this.put("t_userPartsOrder","userWorkOrder.text.toolbar.partsOrder");
            //service provider portal
            this.put("serviceProviderHome","userWorkOrder.text.findWorkOrder");
            this.put("serviceProviderWorkOrder","userWorkOrder.text.findWorkOrder");
            this.put("serviceProviderWorkOrderDetail","global.label.workOrderSummary");
            this.put("serviceProviderWorkOrderItem","userWorkOrder.text.toolbar.workOrderItems");
            this.put("serviceProviderPartsOrder","userWorkOrder.text.toolbar.partsOrder");
            this.put("serviceProviderWorkOrderContent","userWorkOrder.text.toolbar.workOrderContent");
            this.put("serviceProviderWorkOrderNote","userWorkOrder.text.toolbar.workOrderNotes");
        }

        public Object get(Object key) {
            Object val = super.get(key);
            if (val == null) {
                return key;
            }
            return val;
        }
    }

}
