/*
 * StoreVendorInvoiceForm.java
 *
 * Created on July 6, 2005, 2:58 PM
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.value.AddressDataVector;
import com.cleanwise.service.api.value.BusEntityTerrViewVector;
import com.cleanwise.service.api.value.CostCenterDataVector;
import com.cleanwise.service.api.value.DistributorData;
import com.cleanwise.service.api.value.InvoiceDistDataVector;
import com.cleanwise.service.api.value.OrderItemDescDataVector;
import com.cleanwise.service.api.value.OrderPropertyDataVector;
import com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView;
import java.math.BigDecimal;
import java.util.TreeSet;


/**
 *
 * @author bstevens
 */
public class StoreVendorInvoiceDetailForm extends StorePortalBaseForm{

    private PurchaseOrderStatusDescDataView invoice;
    private String voucher;
    private String totalAmountS;
    private String totalFreightCostS;
    private String totalTaxCostS;
    private String newErpPoNum;
    private int orderRoutedWarningCount;
    private int minimumOrderWarningCount;
    private int frieghtOnBackorderedWarningCount;
    private int frieghtOnFreeTerritoryWarningCount;
    private int freightOverOrderFreightForDistWarningCount;
    private String receivingSystemInvoiceCd;
    private int nextInvoiceInList;
    private int prevInvoiceInList;
    private boolean doNotAllowInvoiceEdits = false;
    private boolean poScreen;
    private CostCenterDataVector costCenterDataVector;
	private boolean requireErpAccountNumber = false;
	private String totalDiscountS;
	private String rejectedInvEmail;
    
    


    private static final TreeSet updateableInvoiceStatusCds = new TreeSet();
    static{
        updateableInvoiceStatusCds.add(RefCodeNames.INVOICE_STATUS_CD.DIST_SHIPPED);
        updateableInvoiceStatusCds.add(RefCodeNames.INVOICE_STATUS_CD.ERP_REJECTED);
        updateableInvoiceStatusCds.add(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR);
        updateableInvoiceStatusCds.add(RefCodeNames.INVOICE_STATUS_CD.MANUAL_INVOICE_RELEASE);
        updateableInvoiceStatusCds.add(RefCodeNames.INVOICE_STATUS_CD.PENDING);
        updateableInvoiceStatusCds.add(RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW);
        updateableInvoiceStatusCds.add(RefCodeNames.INVOICE_STATUS_CD.REJECTED);
    }

    /**
     *If the status code of the current invoice is one that is in a state such that it can be updated return true.
     *Invoice must exist for it to be considered as true.
     */
    public boolean isUpdateableInvoiceStatus(){
        if(getInvoice() == null || getInvoice().getInvoiceDist() == null){
            return false;
        }
        String stat = getInvoice().getInvoiceDist().getInvoiceStatusCd();
        if(stat == null){
            return true;
        }
        return updateableInvoiceStatusCds.contains(stat);
    }


    /**
     * Holds value of property invoiceItems.
     */
    private OrderItemDescDataVector invoiceItems;

    /**
     * Holds value of property invoiceDateS.
     */
    private String invoiceDateS;

    /**
     * Holds value of property vendorMinimumOrder.
     */
    private BigDecimal vendorMinimumOrder;

    /**
     * Holds value of property notes.
     */
    private OrderPropertyDataVector notes;

    /** Creates a new instance of StoreVendorInvoiceForm */
    public StoreVendorInvoiceDetailForm() {
    }

    /**
     * Getter for property invoice.
     * @return Value of property invoice.
     */
    public PurchaseOrderStatusDescDataView getInvoice() {

        return this.invoice;
    }

    /**
     * Setter for property invoice.
     * @param invoice New value of property invoice.
     */
    public void setInvoice(PurchaseOrderStatusDescDataView invoice) {

        this.invoice = invoice;
    }

    /**
     * Getter for property newErpPoNum.
     * @return Value of property newErpPoNum.
     */
    public String getNewErpPoNum() {
        return newErpPoNum;
    }

    /**
     * Setter for property newErpPoNum.
     * @param newErpPoNum New value of property newErpPoNum.
     */
    public void setNewErpPoNum(String newErpPoNum) {
        this.newErpPoNum = newErpPoNum;
    }

    /**
     * Getter for property voucher.
     * @return Value of property voucher.
     */
    public String getVoucher() {

        return this.voucher;
    }

    /**
     * Setter for property voucher.
     * @param voucher New value of property voucher.
     */
    public void setVoucher(String voucher) {

        this.voucher = voucher;
    }

    /**
     * Getter for property totalAmountS.
     * @return Value of property totalAmountS.
     */
    public String getTotalAmountS() {

        return this.totalAmountS;
    }

    /**
     * Setter for property totalAmountS.
     * @param totalAmountS New value of property totalAmountS.
     */
    public void setTotalAmountS(String totalAmountS) {

        this.totalAmountS = totalAmountS;
    }

    /**
     * Getter for property totalFreightCostS.
     * @return Value of property totalFreightCostS.
     */
    public String getTotalFreightCostS() {

        return this.totalFreightCostS;
    }

    /**
     * Setter for property totalFreightCostS.
     * @param totalFreightCostS New value of property totalFreightCostS.
     */
    public void setTotalFreightCostS(String totalFreightCostS) {

        this.totalFreightCostS = totalFreightCostS;
    }

    /**
     * Getter for property totalTaxCostS.
     * @return Value of property totalTaxCostS.
     */
    public String getTotalTaxCostS() {

        return this.totalTaxCostS;
    }

    /**
     * Setter for property totalTaxCostS.
     * @param totalTaxCostS New value of property totalTaxCostS.
     */
    public void setTotalTaxCostS(String totalTaxCostS) {

        this.totalTaxCostS = totalTaxCostS;
    }

    /**
     * Getter for property invoiceItems.
     * @return Value of property invoiceItems.
     */
    public OrderItemDescDataVector getInvoiceItems() {

        return this.invoiceItems;
    }

    /**
     * Setter for property invoiceItems.
     * @param invoiceItems New value of property invoiceItems.
     */
    public void setInvoiceItems(OrderItemDescDataVector invoiceItems) {

        this.invoiceItems = invoiceItems;
    }

    /**
     *Indexed get
     */
    /*public OrderItemDescData getInvoiceItem(int idx) {
        if (this.invoiceItems == null) {
            this.invoiceItems = new OrderItemDescDataVector();
        }
        while (idx >= this.invoiceItems.size()) {
            this.invoiceItems.add(OrderItemDescData.createValue());
        }

        return (OrderItemDescData) this.invoiceItems.get(idx);
    }*/


    /**
     * Getter for property invoiceDateS.
     * @return Value of property invoiceDateS.
     */
    public String getInvoiceDateS() {

        return this.invoiceDateS;
    }

    /**
     * Setter for property invoiceDateS.
     * @param invoiceDateS New value of property invoiceDateS.
     */
    public void setInvoiceDateS(String invoiceDateS) {

        this.invoiceDateS = invoiceDateS;
    }

    /**
     * Getter for property vendorMinimumOrder.
     * @return Value of property vendorMinimumOrder.
     */
    public BigDecimal getVendorMinimumOrder() {

        return this.vendorMinimumOrder;
    }

    /**
     * Setter for property vendorMinimumOrder.
     * @param vendorMinimumOrder New value of property vendorMinimumOrder.
     */
    public void setVendorMinimumOrder(BigDecimal vendorMinimumOrder) {

        this.vendorMinimumOrder = vendorMinimumOrder;
    }

    /**
     * Getter for property notes.
     * @return Value of property notes.
     */
    public OrderPropertyDataVector getNotes() {

        return this.notes;
    }

    /**
     * Setter for property notes.
     * @param notes New value of property notes.
     */
    public void setNotes(OrderPropertyDataVector notes) {

        this.notes = notes;
    }

    /**
     * Holds value of property distributor.
     */
    private DistributorData distributor;

    /**
     * Getter for property distributor.
     * @return Value of property distributor.
     */
    public DistributorData getDistributor() {

        return this.distributor;
    }

    /**
     * Setter for property distributor.
     * @param distributor New value of property distributor.
     */
    public void setDistributor(DistributorData distributor) {

        this.distributor = distributor;
    }

    /**
     * Holds value of property remitToAddresses.
     */
    private AddressDataVector remitToAddresses;

    /**
     * Getter for property remitToAddresses.
     * @return Value of property remitToAddresses.
     */
    public AddressDataVector getRemitToAddresses() {

        return this.remitToAddresses;
    }

    /**
     * Setter for property remitToAddresses.
     * @param remitToAddresses New value of property remitToAddresses.
     */
    public void setRemitToAddresses(AddressDataVector remitToAddresses) {

        this.remitToAddresses = remitToAddresses;
    }

    /** Getter for property warningCount.
     * @return Value of property warningCount.
     *
     */
    public int getOrderRoutedWarningCount() {
        return this.orderRoutedWarningCount;
    }

    /** Setter for property warningCount.
     * @param warningCount New value of property warningCount.
     *
     */
    public void setOrderRoutedWarningCount(int orderRoutedWarningCount) {
        this.orderRoutedWarningCount = orderRoutedWarningCount;
    }

    /** Getter for property minimumOrderWarningCount.
     * @return Value of property minimumOrderWarningCount.
     *
     */
    public int getMinimumOrderWarningCount() {
        return this.minimumOrderWarningCount;
    }

    /** Setter for property minimumOrderWarningCount.
     * @param minimumOrderWarningCount New value of property minimumOrderWarningCount.
     *
     */
    public void setMinimumOrderWarningCount(int minimumOrderWarningCount) {
        this.minimumOrderWarningCount = minimumOrderWarningCount;
    }

    /** Getter for property frieghtOnBackorderedWarningCount.
     * @return Value of property frieghtOnBackorderedWarningCount.
     *
     */
    public int getFrieghtOnBackorderedWarningCount() {
        return this.frieghtOnBackorderedWarningCount;
    }

    /** Setter for property frieghtOnBackorderedWarningCount.
     * @param frieghtOnBackorderedWarningCount New value of property frieghtOnBackorderedWarningCount.
     *
     */
    public void setFrieghtOnBackorderedWarningCount(int frieghtOnBackorderedWarningCount) {
        this.frieghtOnBackorderedWarningCount = frieghtOnBackorderedWarningCount;
    }

    /** Getter for property frieghtOnFreeTerritoryWarningCount.
     * @return Value of property frieghtOnFreeTerritoryWarningCount.
     *
     */
    public int getFrieghtOnFreeTerritoryWarningCount() {
        return this.frieghtOnFreeTerritoryWarningCount;
    }

    /** Setter for property frieghtOnFreeTerritoryWarningCount.
     * @param frieghtOnFreeTerritoryWarningCount New value of property frieghtOnFreeTerritoryWarningCount.
     *
     */
    public void setFrieghtOnFreeTerritoryWarningCount(int frieghtOnFreeTerritoryWarningCount) {
        this.frieghtOnFreeTerritoryWarningCount = frieghtOnFreeTerritoryWarningCount;
    }

    /**
     * Holds value of property existingIvoices.
     */
    private InvoiceDistDataVector existingIvoices;

    /**
     * Getter for property existingIvoices.
     * @return Value of property existingIvoices.
     */
    public InvoiceDistDataVector getExistingIvoices() {

        return this.existingIvoices;
    }

    /**
     * Setter for property existingIvoices.
     * @param existingIvoices New value of property existingIvoices.
     */
    public void setExistingIvoices(InvoiceDistDataVector existingIvoices) {

        this.existingIvoices = existingIvoices;
    }

    /**
     * Holds value of property distributorTerritory.
     */
    private BusEntityTerrViewVector distributorTerritory;

    /**
     * Getter for property distributorTerritory.
     * @return Value of property distributorTerritory.
     */
    public BusEntityTerrViewVector getDistributorTerritory() {

        return this.distributorTerritory;
    }

    /**
     * Setter for property distributorTerritory.
     * @param distributorTerritory New value of property distributorTerritory.
     */
    public void setDistributorTerritory(BusEntityTerrViewVector distributorTerritory) {

        this.distributorTerritory = distributorTerritory;
    }

    /**
     * Holds value of property frieghtOverMaxCountWarningCount.
     */
    private int frieghtOverMaxCountWarningCount;

    /**
     * Getter for property freightOverMaxCount.
     * @return Value of property freightOverMaxCount.
     */
    public int getFrieghtOverMaxCountWarningCount()   {

        return this.frieghtOverMaxCountWarningCount;
    }

    /**
     * Setter for property freightOverMaxCount.
     * @param freightOverMaxCount New value of property freightOverMaxCount.
     */
    public void setFrieghtOverMaxCountWarningCount(int frieghtOverMaxCountWarningCount)   {

        this.frieghtOverMaxCountWarningCount = frieghtOverMaxCountWarningCount;
    }

    /**
     * Holds value of property newNote.
     */
    private String newNote;

    /**
     * Getter for property newNote.
     * @return Value of property newNote.
     */
    public String getNewNote() {

        return this.newNote;
    }

    /**
     * Setter for property newNote.
     * @param newNote New value of property newNote.
     */
    public void setNewNote(String newNote) {

        this.newNote = newNote;
    }

    /**
     * Holds value of property totalReceivedCost.
     */
    private BigDecimal totalReceivedCost;

    /**
     * Getter for property totalReceivedCost.
     * @return Value of property totalReceivedCost.
     */
    public BigDecimal getTotalReceivedCost() {

        return this.totalReceivedCost;
    }

    /**
     * Setter for property totalReceivedCost.
     * @param totalReceivedCost New value of property totalReceivedCost.
     */
    public void setTotalReceivedCost(BigDecimal totalReceivedCost) {

        this.totalReceivedCost = totalReceivedCost;
    }

    /**
     * Holds value of property subTotalReceivedCost.
     */
    private BigDecimal subTotalReceivedCost;

    /**
     * Getter for property subTotalReceivedCost.
     * @return Value of property subTotalReceivedCost.
     */
    public BigDecimal getSubTotalReceivedCost() {

        return this.subTotalReceivedCost;
    }

    /**
     * Setter for property subTotalReceivedCost.
     * @param subTotalReceivedCost New value of property subTotalReceivedCost.
     */
    public void setSubTotalReceivedCost(BigDecimal subTotalReceivedCost) {

        this.subTotalReceivedCost = subTotalReceivedCost;
    }

    /**
     * Holds value of property calculatedSalesTax.
     */
    private BigDecimal calculatedSalesTax;

    /**
     * Getter for property calculatedSalesTax.
     * @return Value of property calculatedSalesTax.
     */
    public BigDecimal getCalculatedSalesTax() {

        return this.calculatedSalesTax;
    }

    /**
     * Setter for property calculatedSalesTax.
     * @param calculatedSalesTax New value of property calculatedSalesTax.
     */
    public void setCalculatedSalesTax(BigDecimal calculatedSalesTax) {

        this.calculatedSalesTax = calculatedSalesTax;
    }

    private BigDecimal calculatedTotal;
    public BigDecimal getCalculatedTotal() {

        return this.calculatedTotal;
    }
    public void setCalculatedTotal(BigDecimal val) {

        calculatedTotal = val;
    }

    private String actionOveride;

    /**
     * Getter for property actionOveride.
     * @return Value of property actionOveride.
     */
    public String getActionOveride() {

        return this.actionOveride;
    }

    /**
     * Setter for property actionOveride.
     * @param actionOveride New value of property actionOveride.
     */
    public void setActionOveride(String actionOveride) {

        this.actionOveride = actionOveride;
    }

    /**
     * Holds value of property lineToDelete.
     */
    private String lineToDelete;

    /**
     * Getter for property lineToDelete.
     * @return Value of property lineToDelete.
     */
    public String getLineToDelete() {

        return this.lineToDelete;
    }

    /**
     * Setter for property lineToDelete.
     * @param lineToDelete New value of property lineToDelete.
     */
    public void setLineToDelete(String lineToDelete) {

        this.lineToDelete = lineToDelete;
    }

    /**
     * Holds value of property totalMiscChargesS.
     */
    private String totalMiscChargesS;
	private Object costCenterDataVecto;

    /**
     * Getter for property totalMiscChargesS.
     * @return Value of property totalMiscChargesS.
     */
    public String getTotalMiscChargesS() {

        return this.totalMiscChargesS;
    }

    /**
     * Setter for property totalMiscChargesS.
     * @param totalMiscChargesS New value of property totalMiscChargesS.
     */
    public void setTotalMiscChargesS(String totalMiscChargesS) {

        this.totalMiscChargesS = totalMiscChargesS;
    }

  public String getReceivingSystemInvoiceCd() {
    return receivingSystemInvoiceCd;
  }

  public void setReceivingSystemInvoiceCd(String receivingSystemInvoiceCd) {
    this.receivingSystemInvoiceCd = receivingSystemInvoiceCd;
  }

  public int getFreightOverOrderFreightForDistWarningCount() {
    return freightOverOrderFreightForDistWarningCount;
  }

  public void setFreightOverOrderFreightForDistWarningCount(
      int freightOverOrderFreightForDistWarningCount) {
    this.freightOverOrderFreightForDistWarningCount = freightOverOrderFreightForDistWarningCount;
  }

public int getNextInvoiceInList() {
	return nextInvoiceInList;
}

public void setNextInvoiceInList(int nextInvoiceInList) {
	this.nextInvoiceInList = nextInvoiceInList;
}

public int getPrevInvoiceInList() {
	return prevInvoiceInList;
}

public void setPrevInvoiceInList(int prevInvoiceInList) {
	this.prevInvoiceInList = prevInvoiceInList;
}

    public void setDoNotAllowInvoiceEdits(boolean doNotAllowInvoiceEdits) {
        this.doNotAllowInvoiceEdits = doNotAllowInvoiceEdits;
    }

    public boolean isDoNotAllowInvoiceEdits() {
        return doNotAllowInvoiceEdits;
    }

    public void setPoScreen(boolean poScreen) {
        this.poScreen = poScreen;
    }

    public boolean isPoScreen() {
        return poScreen;
    }

	public void setCostCenterDataVector(CostCenterDataVector costCenterDataVector) {
		this.costCenterDataVector = costCenterDataVector;
	}
	
	public CostCenterDataVector getCostCenterDataVector() {
		return this.costCenterDataVector;
		
	}

	public boolean isRequireErpAccountNumber() {
    	return requireErpAccountNumber;
    }

	public void setRequireErpAccountNumber(boolean requireErpAccountNumber) {
	    this.requireErpAccountNumber = requireErpAccountNumber;
	    
    }

	/**
	 * @return the totalDiscountS
	 */
	public String getTotalDiscountS() {
		return totalDiscountS;
	}

	/**
	 * @param totalDiscountS the totalDiscountS to set
	 */
	public void setTotalDiscountS(String totalDiscountS) {
		this.totalDiscountS = totalDiscountS;
	}

	/**
	 * @return the rejectedInvEmail
	 */
	public String getRejectedInvEmail() {
		return rejectedInvEmail;
	}

	/**
	 * @param rejectedInvEmail the rejectedInvEmail to set
	 */
	public void setRejectedInvEmail(String rejectedInvEmail) {
		this.rejectedInvEmail = rejectedInvEmail;
	}
	
	
	
}
