package com.cleanwise.service.api.util;

import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Date;
import java.math.BigDecimal;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.*;
import com.cleanwise.service.api.dao.*;



        public class InventoryOrderQtyLogOperator {
            private HashMap qtyLog;

            public InventoryOrderQtyLogOperator(HashMap qtyLog) {
                this.qtyLog = qtyLog;
            }

            public InventoryOrderQtyLogOperator() {
                this.qtyLog = new HashMap();
            }

            public InventoryOrderQtyLogOperator(Collection orderQtyLogCollection) {
                init(orderQtyLogCollection);
            }

            private void init(Collection orderQtyLogCollection) {
                this.qtyLog = new HashMap();
                if (orderQtyLogCollection != null) {
                    Iterator it = orderQtyLogCollection.iterator();
                    while (it.hasNext()) {
                        InventoryOrderQtyData log = (InventoryOrderQtyData) it.next();
                        this.qtyLog.put(new Integer(log.getItemId()), log);
                    }
                }
            }



            public InventoryOrderQtyData getLog(int itemId) {
                return (InventoryOrderQtyData) this.qtyLog.get(new Integer(itemId));
            }
			
            public Collection values() {			     
                return qtyLog.values();
            }

            public void put(int itemId,InventoryOrderQtyData data){
                this.qtyLog.put(new Integer(itemId),data);
            }

            public void addTo(int itemId, String receiver, Object value) {
                InventoryOrderQtyData log = getItemLog(new Integer(itemId),true);
                if (InventoryOrderQtyDataAccess.AUTO_ORDER_APPLIED.equals(receiver)) {
                    log.setAutoOrderApplied((String) value);
                } else if (InventoryOrderQtyDataAccess.AUTO_ORDER_FACTOR.equals(receiver)) {
                    log.setAutoOrderFactor((BigDecimal) value);
                } else if (InventoryOrderQtyDataAccess.ENABLE_AUTO_ORDER.equals(receiver)) {
                    log.setEnableAutoOrder((String) value);
                } else if (InventoryOrderQtyDataAccess.BUS_ENTITY_ID.equals(receiver)) {
                    log.setBusEntityId(((Integer) value).intValue());
                } else if (InventoryOrderQtyDataAccess.CUTOFF_DATE.equals(receiver)) {
                    log.setCutoffDate((Date) value);
                } else if (InventoryOrderQtyDataAccess.INVENTORY_ORDER_QTY_ID.equals(receiver)) {
                    log.setInventoryOrderQtyId(((Integer) value).intValue());
                } else if (InventoryOrderQtyDataAccess.ORDER_ID.equals(receiver)) {
                    log.setOrderId(((Integer) value).intValue());
                } else if (InventoryOrderQtyDataAccess.ITEM_ID.equals(receiver)) {
                    log.setItemId(((Integer) value).intValue());
                } else if (InventoryOrderQtyDataAccess.ITEM_TYPE.equals(receiver)) {
                    log.setItemType((String) value);
                } else if (InventoryOrderQtyDataAccess.INVENTORY_QTY.equals(receiver)) {
                    log.setInventoryQty((String) value);
                } else if (InventoryOrderQtyDataAccess.ORDER_QTY.equals(receiver)) {
                    log.setOrderQty(((Integer) value).intValue());
                } else if (InventoryOrderQtyDataAccess.PAR.equals(receiver)) {
                    log.setPar(((Integer) value).intValue());
                } else if (InventoryOrderQtyDataAccess.QTY_ON_HAND.equals(receiver)) {
                    log.setQtyOnHand((String) value);
                } else if (InventoryOrderQtyDataAccess.ADD_BY.equals(receiver)) {
                    log.setAddBy((String) value);
                } else if (InventoryOrderQtyDataAccess.ADD_DATE.equals(receiver)) {
                    log.setAddDate((Date) value);
                } else if (InventoryOrderQtyDataAccess.MOD_DATE.equals(receiver)) {
                    log.setModDate((Date) value);
                } else if (InventoryOrderQtyDataAccess.MOD_BY.equals(receiver)) {
                    log.setModBy((String) value);
                }else if (InventoryOrderQtyDataAccess.PRICE.equals(receiver)) {
                    log.setPrice((BigDecimal) value);
                }else if (InventoryOrderQtyDataAccess.CATEGORY.equals(receiver)) {
                    log.setCategory((String) value);
                }else if (InventoryOrderQtyDataAccess.COST_CENTER.equals(receiver)) {
                    log.setCostCenter((String) value);
                }else if (InventoryOrderQtyDataAccess.DIST_ITEM_NUM.equals(receiver)) {
                    log.setDistItemNum((String) value);
                }
            }


            private InventoryOrderQtyData getItemLog(Integer id){
                return getItemLog(id,false);
            }

            private InventoryOrderQtyData getItemLog(Integer id,boolean createInNotExist) {
                InventoryOrderQtyData log = (InventoryOrderQtyData) this.qtyLog.get(id);
                if(log == null && createInNotExist){
                    log = InventoryOrderQtyData.createValue();
                }
                return log;
            }



            public HashMap getLog() {
                return  qtyLog;
            }



            public InventoryOrderQtyData getItemLog(int id) {
                return getItemLog(new Integer(id));
            }



            public void addTo(ShoppingCartItemDataVector items, String receiver, Object value) {
                if (items != null && !items.isEmpty()) {
                    Iterator it = items.iterator();
                    while (it.hasNext()) {
                        int itemId = ((ShoppingCartItemData) (it.next())).getItemId();
                        addTo(itemId, receiver, value);
                    }
                }
            }



            public void addTo(IdVector itemIds, String receiver, Object value) {
                if (itemIds != null && !itemIds.isEmpty()) {
                    Iterator it = itemIds.iterator();
                    while (it.hasNext()) {
                        int itemId = ((Integer) it.next()).intValue();
                        addTo(itemId, receiver, value);
                    }
                }
            }



            public void addTo(OrderItemDataVector items, String receiver, Object value) {
                if (items != null && !items.isEmpty()) {
                    Iterator it = items.iterator();
                    while (it.hasNext()) {
                        int itemId = ((OrderItemData) (it.next())).getItemId();
                        addTo(itemId, receiver, value);
                    }
                }
            }
        }
