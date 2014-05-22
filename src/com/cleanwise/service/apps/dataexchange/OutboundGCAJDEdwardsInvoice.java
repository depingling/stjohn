package com.cleanwise.service.apps.dataexchange;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.value.ElectronicTransactionData;
import com.cleanwise.service.api.value.InvoiceAbstractionDetailView;
import com.cleanwise.service.api.value.InvoiceAbstractionView;
import com.cleanwise.service.api.value.InvoiceCustDetailRequestData;
import com.cleanwise.service.api.value.OutboundEDIRequestData;
import com.cleanwise.service.api.value.PropertyData;

public class OutboundGCAJDEdwardsInvoice extends InterchangeOutboundSuper implements OutboundTransaction{
	protected Logger log = Logger.getLogger(this.getClass());
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private static final String BLANK_RECORD = " "; //defines a blank record as GCA wants to see it
	private static final BigDecimal ZERO = new BigDecimal(0);

	private static final String RESALE_ACCOUNT_CD="5500";
	private static final String RESALE_SUBSIDIARY_CD="40";
	private static final String FREIGHT_SUBSIDIARY_CD="14";
	private static final String SALES_TAX_SUBSIDIARY_CD="15";

	private String mCurrBatch = null;
	private String mCurrCompany = null;

	private Date today;
	private int mTransactionNumber;

	public void buildInterchangeHeader()
	throws Exception
	{
		super.buildInterchangeHeader();
		mTransactionNumber = 1;
		today = new Date();
	}

	public ElectronicTransactionData createTransactionObject() {
		transactionD = super.createTransactionObject();
		transactionD.setSetControlNumber(mTransactionNumber);
		return transactionD;
	}

	public String getFileExtension()throws Exception{//may be left unimplemented (return null) and the default will be used
		return ".edi";
	}

	/**
	 * Generates a new batch number
	 * XXX left unimplemented
	 */
	private String generateNewBatch() throws Exception{
		log.info("GENNNERATING BATCH");
		((OutboundTranslate)getTranslator()).incrementGroupControlNum(getProfile());
		int i = this.getProfile().getGroupControlNum();
		String toReturn = ""+i;
		log.info("toReturn = "+toReturn);
		return toReturn;
	}

	/**
	 * Converts the date to something that GCA wants to see.
	 */
	private String formatDate(Date pDate){
		return dateFormat.format(pDate);
	}

	private String formatAmountNumber(BigDecimal pNum){
		if(pNum == null){
			pNum = ZERO;
		}
		pNum.setScale(2,BigDecimal.ROUND_DOWN);
		return pNum.toString();
	}

	/**
	 * Formats the lines as JDEdwards has a peculiar way of wanting to see them
	 */
	private String formatLineNumber(int lineNum){
		return lineNum + ".000"; //All the line number have three decimals for e.g. 1.000 
	}

	/**
	 * Extract the 2 digit century from the given date
	 */
	private String get2DigitCenturyCode(Date pDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(pDate);
		int year = cal.get(Calendar.YEAR);
		String yearS = Integer.toString(year);
		return yearS.substring(0,2);
	}

	/**
	 * Formats the BU number with a padded 12 digit string:
	 * ex: "        5555"
	 */
	private String formatBUNumber(String bu){
		return Utility.padLeft(bu,' ',12);
	}

	/**Accounts payable*/
	private void createF04Record(RecordInformation recInfo,int lineNum)
	throws IOException{
		OutboundEDIRequestData req=recInfo.req;
		BigDecimal amount=recInfo.amount;
		String desc=recInfo.desc;
		String buNum = Utility.getPropertyValue(req.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
		buNum = formatBUNumber(buNum);
		List aRow = new ArrayList();
		InvoiceAbstractionView inv = req.getInvoiceData();
		aRow.add("1"); //record type
		aRow.add("CLEANWISE"); //VLEDUS_EdiUserId1
		aRow.add(BLANK_RECORD); //VLEDTY_EDIType_2
		aRow.add("0"); //VLEDSQ_EDISeq_3
		aRow.add(Integer.toString(mTransactionNumber)); //VLEDTN_EdiTransactNumber_4
		aRow.add(BLANK_RECORD); //VLEDCT_EdiDocumentType_5
		aRow.add(formatLineNumber(lineNum)); //VLEDLN_EdiLineNumber_6
		aRow.add(BLANK_RECORD); //VLEDTS_EdiTransSet_7
		aRow.add(BLANK_RECORD); //VLEDFT_EdiTranslationFormat_8
		aRow.add("0"); //VLEDDT_EdiTransmissionDate_9
		aRow.add(BLANK_RECORD); //VLEDER_EdiSendRcvIndicator_10
		aRow.add("0"); //VLEDDL_EdiDetailLinesProcess_11
		aRow.add("0"); //VLEDSP_EdiSuccessfullyProcess_12
		aRow.add("A"); //VLEDTC_EdiTransactionAction_13
		aRow.add("V"); //VLEDTR_EdiTransactionType_14
		aRow.add(mCurrBatch); //VLEDBT_EdiBatchNumber_15
		aRow.add(BLANK_RECORD); //VLEDGL_EdiCreateGl_16
		aRow.add("1"); //VLEDDH_BatchFileDiscountHandlin_17
		aRow.add("0"); //VLEDAN_UserAddressNumber_18
		aRow.add(mCurrCompany); //VLKCO_CompanyKey_19
		aRow.add("0"); //VLDOC_DocVoucherInvoiceE_20
		aRow.add(BLANK_RECORD); //VLDCT_DocumentType_21
		aRow.add(BLANK_RECORD); //VLSFX_DocumentPayItem_22
		aRow.add("0"); //VLSFXE_PayItemExtensionNumber_23
		aRow.add(BLANK_RECORD); //VLDCTA_DocumentTypeAdjusting_24
		if(!Utility.isSet(req.getDistributorCustomerReferenceCode())){
			throw new RuntimeException("Distributor company ref code was not set for dist id: "+req.getDistributorId());
		}
		aRow.add(req.getDistributorCustomerReferenceCode()); //VLAN8_AddressNumber_25   //xxx Needs to be GCA Vendor Code
		aRow.add(req.getDistributorCustomerReferenceCode()); //VLPYE_PayeeAddressNumber_26   //xxx Needs to be GCA Vendor Code
		aRow.add("0"); //VLSNTO_AddressNumberSentTo_27
		aRow.add(formatDate(inv.getInvoiceDate())); //VLDIVJ_DateInvoiceJ_28
		aRow.add(formatDate(inv.getInvoiceDate())); //VLDSVJ_DateServiceCurrency_29
		aRow.add("0"); //VLDDJ_DateDueJulian_30
		aRow.add("0"); //VLDDNJ_DateDiscountDueJulian_31
		aRow.add(formatDate(today)); //VLDGJ_DateForGLandVoucherJULIA_32
		aRow.add("0"); //VLFY_FiscalYear1_33
		aRow.add("0"); //VLCTRY_Century_34
		aRow.add("0"); //VLPN_PeriodNoGeneralLedge_35
		aRow.add(this.mCurrCompany); //VLCO_Company_36 --1
		aRow.add("0"); //VLICU_BatchNumber_37
		aRow.add("V"); //VLICUT_BatchType_38
		aRow.add("0"); //VLDICJ_DateBatchJulian_39
		aRow.add("Y"); //VLBALJ_BalancedJournalEntries_40
		aRow.add("A"); //VLPST_PayStatusCode_41
		aRow.add(formatAmountNumber(amount)); //VLAG_AmountGross_42 
		aRow.add(formatAmountNumber(amount)); //VLAAP_AmountOpen_43
		aRow.add("0"); //VLADSC_AmtDiscountAvailable_44
		aRow.add("0"); //VLADSA_AmountDiscountTaken_45
		aRow.add("0"); //VLATXA_AmountTaxable_46 
		aRow.add("0"); //VLATXN_AmountTaxExempt_47
		aRow.add("0"); //VLSTAM_AmtTax2_48
		aRow.add(BLANK_RECORD); //VLTXA1_TaxArea1_49
		aRow.add(BLANK_RECORD); //VLEXR1_TaxExplanationCode1_50
		aRow.add("D"); //VLCRRM_CurrencyMode_51
		aRow.add(BLANK_RECORD); //VLCRCD_CurrencyCodeFrom_52
		aRow.add("0"); //VLCRR_CurrencyConverRateOv_53
		aRow.add("0"); //VLACR_AmountCurrency_54
		aRow.add("0"); //VLFAP_AmountForeignOpen_55
		aRow.add("0"); //VLCDS_ForeignDiscountAvail_56
		aRow.add("0"); //VLCDSA_ForeignDiscountTaken_57
		aRow.add("0"); //VLCTXA_ForeignTaxableAmount_58
		aRow.add("0"); //VLCTXN_ForeignTaxExempt_59
		aRow.add("0"); //VLCTAM_ForeignTaxAmount_60
		aRow.add(BLANK_RECORD); //VLGLC_GlClass_61
		aRow.add(BLANK_RECORD); //VLGLBA_GlBankAccount_62
		aRow.add(BLANK_RECORD); //VLPOST_GLPostedCode_63
		aRow.add("2"); //VLAM_AccountModeGL_64 
		aRow.add(BLANK_RECORD); //VLAID2_AccountId2_65
		aRow.add(buNum); //VLMCU_CostCenter_66
		aRow.add(BLANK_RECORD); //VLOBJ_ObjectAccount_67
		aRow.add(BLANK_RECORD); //VLSUB_Subsidiary_68
		aRow.add(BLANK_RECORD); //VLSBLT_SubledgerType_69
		aRow.add(BLANK_RECORD); //VLSBL_Subledger_70
		aRow.add(BLANK_RECORD); //VLBAID_BankTransitShortId_71
		aRow.add(BLANK_RECORD); //VLPTC_PaymentTermsCode01_72
		aRow.add(BLANK_RECORD); //VLVOD_VoidFlag_73
		aRow.add(BLANK_RECORD); //VLOKCO_CompanyKeyOriginal_74
		aRow.add(BLANK_RECORD); //VLODCT_OriginalDocumentType_75
		aRow.add("0"); //VLODOC_OriginalDocumentNo_76
		aRow.add(BLANK_RECORD); //VLOSFX_OriginalDocPayItem_77
		aRow.add(BLANK_RECORD); //VLCRC_CheckRoutingCode_78
		aRow.add(inv.getInvoiceNum()); //VLVINV_VendorInvoiceNumber_79
		aRow.add(BLANK_RECORD); //VLPKCO_CompanyKeyPurchase_80
		aRow.add(BLANK_RECORD); //VLPO_PurchaseOrder_81
		aRow.add(BLANK_RECORD); //VLPDCT_DocumentTypePurchase_82
		aRow.add(formatLineNumber(lineNum)); //VLLNID_LineNumber_83
		aRow.add(BLANK_RECORD); //VLSFXO_OrderSuffix_84
		aRow.add("0"); //VLOPSQ_SequenceNoOperations_85
		aRow.add(BLANK_RECORD); //VLVR01_Reference1_86
		aRow.add(BLANK_RECORD); //VLUNIT_UnitNo_87
		aRow.add(BLANK_RECORD); //VLMCU2_CostCenter2_88
		if(desc == null){
			desc = BLANK_RECORD;
		}
		if(desc.length() > 30){
			desc = desc.substring(0,30);
		}
		aRow.add(desc); //VLRMK_NameRemark_89
		aRow.add(BLANK_RECORD); //VLRF_FrequencyRecurring_90
		aRow.add("0"); //VLDRF_RecurFrequencyOfPaym_91
		aRow.add(BLANK_RECORD); //VLCTL_APChecksControlField_92
		aRow.add(BLANK_RECORD); //VLFNLP_FinalPayment_93
		aRow.add("0"); //VLU_Units_94
		aRow.add(BLANK_RECORD); //VLUM_UnitOfMeasure_95
		aRow.add(BLANK_RECORD); //VLPYIN_PaymentInstrument_96
		aRow.add(BLANK_RECORD); //VLTXA3_TaxRateArea3Withholding_97
		aRow.add(BLANK_RECORD); //VLEXR3_TaxExplCode3Withhholding_98
		aRow.add(BLANK_RECORD); //VLRP1_ArApMiscCode1_99
		aRow.add(BLANK_RECORD); //VLRP2_ArApMiscCode2_100
		aRow.add(BLANK_RECORD); //VLRP3_ArApMiscCode3_101
		aRow.add(BLANK_RECORD); //VLAC07_ReportCodeAddBook007_102
		aRow.add(BLANK_RECORD); //VLTNN_FlagFor1099_103
		aRow.add(BLANK_RECORD); //VLDMCD_DomesticEntryWMultCurrency_104
		aRow.add("0"); //VLITM_IdentifierShortItem_105
		aRow.add("0"); //VLHCRR_HistoricalCurrencyConver_106
		aRow.add("0"); //VLHDGJ_HistoricalDateJulian_107
		aRow.add(BLANK_RECORD); //VLURC1_UserReserveCode_108
		aRow.add("0"); //VLURDT_UserReservedDate_109
		aRow.add("0"); //VLURAT_UserReservedAmount_110
		aRow.add("0"); //VLURAB_UserReservedNumber_111
		aRow.add(BLANK_RECORD); //VLURRF_UserReservedReference_112
		aRow.add(BLANK_RECORD); //VLTORG_TransactionOriginator_113
		aRow.add(BLANK_RECORD); //VLUSER_UserId_114
		aRow.add(BLANK_RECORD); //VLPID_ProgramId_115
		aRow.add(BLANK_RECORD); //VLUPMJ_DateUpdated_116
		aRow.add(BLANK_RECORD); //VLUPMT_TimeLastUpdated_117
		aRow.add(BLANK_RECORD); //VLJOBN_WorkStationId_118
		aRow.add("0"); //VLDIM_DateInvoiceMo_119
		aRow.add("0"); //VLDID_DateInvoiceDaA_120
		aRow.add("0"); //VLDIY_DateInvoiceYr_121
		aRow.add("0"); //VLDI#_DateInvoiceCtry_122
		aRow.add("0"); //VLDSVM_DteServiceShipProdMo_123
		aRow.add("0"); //VLDSVD_DteServiceShipProdDa_124
		aRow.add("0"); //VLDSVY_DteServiceShipProdYr_125
		aRow.add("0"); //VLDSV#_DteServiceShipProdCt_126
		aRow.add("0"); //VLDDM_DateDueMo_127
		aRow.add("0"); //VLDDD_DateDueDa_128
		aRow.add("0"); //VLDDY_DateDueYr_129
		aRow.add("0"); //VLDD#_DateDueCtry_130
		aRow.add("0"); //VLDDNM_DiscountDueMonth_131
		aRow.add("0"); //VLDDND_DiscountDueDays_132
		aRow.add("0"); //VLDDNY_DiscountDueYear_133
		aRow.add("0"); //VLDDN#_DateDueNetDaysCtry_134
		aRow.add("0"); //VLDGM_DtForGLAndVouchMo_135
		aRow.add("0"); //VLDGD_DtForGLAndVouchDa_136
		aRow.add("0"); //VLDGY_DtForGLAndVouchYr_137
		aRow.add("0"); //VLDG#_DtForGLAndVouchCt_138
		aRow.add("0"); //VLDICM_DateBatch1Mo_139
		aRow.add("0"); //VLDICD_DateBatch1Da_140
		aRow.add("0"); //VLDICY_DateBatch1Yr_141
		aRow.add("0"); //VLDIC#_DateBatch1Ctry_142
		aRow.add("0"); //VLHDGM_HistoricalDateMo_143
		aRow.add("0"); //VLHDGD_HistoricalDateDa_144
		aRow.add("0"); //VLHDGY_HistoricalDateYr_145
		aRow.add("0"); //VLHDG#_HistoricalDateCtry_146
		aRow.add("0"); //VLDOCM_DocMatchingCheckOr_147
		writeRowToOutputStream(aRow, '"');
		translator.writeOutputStream("\n");
	}

	private String nullSafe(String str){
		if(str == null){
			return BLANK_RECORD;
		}else{
			return str;
		}
	}

	/**General Ledger*/
	private void createF09Record(RecordInformation recInfo,int lineNum)
	throws IOException{
		OutboundEDIRequestData req=recInfo.req;
		BigDecimal amount=recInfo.amount;
		String account=recInfo.account;
		String subAccount=recInfo.subAccount;
		String desc=recInfo.desc;
		String buNum = Utility.getPropertyValue(req.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
		buNum = formatBUNumber(buNum);
		List aRow = new ArrayList();
		InvoiceAbstractionView inv = req.getInvoiceData();
		aRow.add("2"); //record type
		aRow.add("CLEANWISE"); //VNEDUS_EDI - User ID_1
		aRow.add(BLANK_RECORD); //VNEDTY_Type Record_2
		aRow.add("0"); //VNEDSQ_Record Sequence_3
		aRow.add(Integer.toString(mTransactionNumber)); //VNEDTN_EDI - Transaction Number_4
		aRow.add(BLANK_RECORD); //VNEDCT_EDI - Document Type_5
		aRow.add(formatLineNumber(lineNum)); //VNEDLN_EDI - Line Number_6  -- JDEdwards wants a 3 digit .000 on the end of the line
		aRow.add(BLANK_RECORD); //VNEDTS_EDI - Transaction Set Number_7
		aRow.add(BLANK_RECORD); //VNEDFT_EDI - Translation Format_8
		aRow.add("0"); //VNEDDT_EDI - Transmission Date_9
		aRow.add(BLANK_RECORD); //VNEDER_EDI - Send/Receive Indicator_10
		aRow.add("0"); //VNEDDL_EDI - Detail Lines Processed_11
		aRow.add("0"); //VNEDSP_EDI - Successfully Processed_12
		aRow.add("A"); //VNEDTC_EDI - Transaction Action_13
		aRow.add("V"); //VNEDTR_EDI - Transaction Type_14
		aRow.add(mCurrBatch); //VNEDBT_EDI - Batch Number_15
		aRow.add(BLANK_RECORD); //VNEDGL_Batch File Create G/L Record_16
		aRow.add("0"); //VNEDAN_User Address Number_17
		aRow.add(BLANK_RECORD); //VNKCO_Document Company_18
		aRow.add(BLANK_RECORD); //VNDCT_Document Type_19
		aRow.add("0"); //VNDOC_Document (Voucher, Invoice, etc.)_20
		aRow.add(formatDate(today)); //VNDGJ_Date - For G/L (and Voucher) - Julian_21
		aRow.add("0"); //VNJELN_Journal Entry Line Number_22
		aRow.add(BLANK_RECORD); //VNEXTL_Line Extension Code_23
		aRow.add(BLANK_RECORD); //VNPOST_G/L Posted Code_24
		aRow.add("0"); //VNICU_Batch Number_25
		aRow.add("V"); //VNICUT_Batch Type_26
		aRow.add("0"); //VNDICJ_Date - Batch (Julian)_27
		aRow.add("0"); //VNDSYJ_Date - Batch System Date_28
		aRow.add("0"); //VNTICU_Batch Time_29
		aRow.add(this.mCurrCompany); //VNCO_Company_30
		aRow.add(recInfo.getCompoundAccount(buNum)); //VNANI_Account Number - Input (Mode Unknown)_31 (was 1000.6400.10) //XXX
		aRow.add("2"); //VNAM_Account Mode - G/L_32  
		aRow.add("0"); //VNAID_Account ID_33 (was 7427699)
		aRow.add(buNum); //VNMCU_Business Unit_34
		aRow.add(nullSafe(account)); //VNOBJ_Object Account_35 
		aRow.add(nullSafe(subAccount)); //VNSUB_Subsidiary_36  
		aRow.add(BLANK_RECORD); //VNSBL_Subledger - G/L_37
		aRow.add(BLANK_RECORD); //VNSBLT_Subledger Type_38
		aRow.add("AA"); //VNLT_Ledger Type_39
		aRow.add("0"); //VNPN_Period Number - General Ledger_40
		aRow.add(get2DigitCenturyCode(inv.getInvoiceDate())); //VNCTRY_Century_41
		aRow.add("0"); //VNFY_Fiscal Year_42
		aRow.add(BLANK_RECORD); //VNFQ_Fiscal Quarter (Obsolete)_43
		aRow.add(BLANK_RECORD); //VNCRCD_Currency Code - From_44
		aRow.add("0"); //VNCRR_Currency Conversion Rate - Spot Rate_45
		aRow.add("0"); //VNHCRR_Historical Currency Conversion Rate_46
		aRow.add("0"); //VNHDGJ_Historical Date - Julian_47
		aRow.add(formatAmountNumber(amount)); //VNAA_Amount_48
		aRow.add("0"); //VNU_Units_49
		aRow.add(BLANK_RECORD); //VNUM_Unit of Measure_50
		aRow.add(BLANK_RECORD); //VNGLC_G/L Offset_51
		aRow.add(BLANK_RECORD); //VNRE_Reverse or Void (R/V)_52
		if(desc == null){
			desc = BLANK_RECORD;
		}
		if(desc.length() > 30){
			desc = desc.substring(0,30);
		}
		aRow.add(req.getDistributorName()); //VNEXA_Name - Alpha Explanation_53
		aRow.add(inv.getErpPoNum()); //VNEXR_Name - Remark Explanation_54
		aRow.add(BLANK_RECORD); //VNR1_Reference 1 - JE, Voucher, Invoice, etc._55
		aRow.add(BLANK_RECORD); //VNR2_Reference 2_56
		aRow.add(BLANK_RECORD); //VNR3_Reference 3 - Account Reconciliation_57
		aRow.add(BLANK_RECORD); //VNSFX_Document Pay Item_58
		aRow.add("0"); //VNODOC_Document - Original_59
		aRow.add(BLANK_RECORD); //VNODCT_Document Type - Original_60
		aRow.add(BLANK_RECORD); //VNOSFX_Document Pay Item - Original_61
		aRow.add(BLANK_RECORD); //VNPKCO_Document Company (Purchase Order)_62
		aRow.add(BLANK_RECORD); //VNOKCO_Document Company (Original Order)_63
		aRow.add(BLANK_RECORD); //VNPDCT_Document Type - Purchase Order_64
		aRow.add("0"); //VNAN8_Address Number_65  --10940
		aRow.add(BLANK_RECORD); //VNCN_Payment Number_66
		aRow.add("0"); //VNDKJ_Date - Check - Julian_67
		aRow.add("0"); //VNDKC_Date - Check Cleared_68
		aRow.add(BLANK_RECORD); //VNASID_Serial Number_69
		aRow.add(BLANK_RECORD); //VNBRE_Batch Rear End Posted Code_70
		aRow.add(BLANK_RECORD); //VNRCND_Reconciled Code_71
		aRow.add(BLANK_RECORD); //VNSUMM_Summarized Code_72
		aRow.add(BLANK_RECORD); //VNPRGE_Purge Code_73
		aRow.add(BLANK_RECORD); //VNTNN_Flag for 1099_74
		aRow.add(BLANK_RECORD); //VNALT1_G/L Posting Code - Alternate 1_75
		aRow.add(BLANK_RECORD); //VNALT2_G/L Posting Code - Alternate 2_76
		aRow.add(BLANK_RECORD); //VNALT3_G/L Posting Code - Alternate 3_77
		aRow.add(BLANK_RECORD); //VNALT4_G/L Posting Code - Alternate 4_78
		aRow.add(BLANK_RECORD); //VNALT5_G/L Posting Code - Alternate 5_79
		aRow.add(BLANK_RECORD); //VNALT6_G/L Posting Code - Cash Basis Acct_80
		aRow.add(BLANK_RECORD); //VNALT7_Commitment Relief Flag_81
		aRow.add(BLANK_RECORD); //VNALT8_Billing Control_82
		aRow.add(BLANK_RECORD); //VNALT9_Currency Update_83
		aRow.add(BLANK_RECORD); //VNALT0_G/L Posting Code - Alternate 0_84
		aRow.add(BLANK_RECORD); //VNALTT_G/L Posting Code - Alternate T_85
		aRow.add(BLANK_RECORD); //VNALTU_G/L Posting Code - Alternate U_86
		aRow.add(BLANK_RECORD); //VNALTV_Stocked Inventory Commitment_87
		aRow.add(BLANK_RECORD); //VNALTW_G/L Posting Code - Alternate W_88
		aRow.add(BLANK_RECORD); //VNALTX_Consumption Tax Cross Reference_89
		aRow.add(BLANK_RECORD); //VNALTZ_G/L Posting Code - Account Debit Balance_90
		aRow.add(BLANK_RECORD); //VNDLNA_Delete Not Allowed_91
		aRow.add(BLANK_RECORD); //VNCFF1_Client Free Form - Alternate 1_92
		aRow.add(BLANK_RECORD); //VNCFF2_Client Free Form - Alternate 2_93
		aRow.add(BLANK_RECORD); //VNASM_Lease Cost Ledger Posted Code_94
		aRow.add(BLANK_RECORD); //VNBC_Bill Code_95
		aRow.add(inv.getInvoiceNum()); //VNVINV_Supplier Invoice Number_96
		aRow.add(formatDate(inv.getInvoiceDate())); //VNIVD_Date - Invoice_97
		aRow.add(BLANK_RECORD); //VNWR01_Categories - Work Order 01_98
		aRow.add(BLANK_RECORD); //VNPO_Purchase Order_99
		aRow.add(BLANK_RECORD); //VNPSFX_Purchase Order Suffix_100
		aRow.add(BLANK_RECORD); //VNDCTO_Order Type_101
		aRow.add("0"); //VNLNID_Line Number_102
		aRow.add("0"); //VNWY_Fiscal Year - Weekly_103
		aRow.add("0"); //VNWN_Fiscal Period - Weekly_104
		aRow.add(BLANK_RECORD); //VNFNLP_Closed Item - As Of Processing_105
		aRow.add("0"); //VNOPSQ_Sequence Number - Operations_106
		aRow.add(BLANK_RECORD); //VNJBCD_Job Type (Craft) Code_107
		aRow.add(BLANK_RECORD); //VNJBST_Job Step_108
		aRow.add(BLANK_RECORD); //VNHMCU_Business Unit - Home_109
		aRow.add("0"); //VNDOI_DOI Sub_110
		aRow.add(BLANK_RECORD); //VNALID_Outsider Lease or Well ID_111
		aRow.add(BLANK_RECORD); //VNALTY_ID Type_112
		aRow.add(formatDate(inv.getInvoiceDate())); //VNDSVJ_Date - Service/Tax_113
		aRow.add(BLANK_RECORD); //VNTORG_Transaction Originator_114
		aRow.add("0"); //VNREG#_Registration Number_115
		aRow.add("0"); //VNPYID_Payment ID (Internal)_116
		aRow.add("CLEANWISE"); //VNUSER_User ID_117
		aRow.add(BLANK_RECORD); //VNPID_Program ID_118
		aRow.add(BLANK_RECORD); //VNJOBN_Work Station ID_119
		aRow.add(BLANK_RECORD); //VNUPMJ_Date - Updated_120
		aRow.add(BLANK_RECORD); //VNUPMT_Time - Last Updated_121
		aRow.add(BLANK_RECORD); //VNCRRM_Currency Mode-Foreign or Domestic Entry_122
		aRow.add("0"); //VNACR_Amount - Currency_123
		aRow.add("0"); //VNDGM_Date - For G/L (and Voucher) - MO_124
		aRow.add("0"); //VNDGD_Date - For G/L (and Voucher) - DA_125
		aRow.add("0"); //VNDGY_Date - For G/L (and Voucher) - YR_126
		aRow.add("0"); //VNDG#_Date - For G/L (and Voucher) - CTRY_127
		aRow.add("0"); //VNDICM_Date - Batch - MO_128
		aRow.add("0"); //VNDICD_Date - Batch - DA_129
		aRow.add("0"); //VNDICY_Date - Batch - YR_130
		aRow.add("0"); //VNDIC#_Date - Batch - CTRY_131
		aRow.add("0"); //VNDSYM_Date - Batch System Date - MO_132
		aRow.add("0"); //VNDSYD_Date - Batch System Date - DA_133
		aRow.add("0"); //VNDSYY_Date - Batch System Date - YR_134
		aRow.add("0"); //VNDSY#_Date - Batch System Date - CTRY_135
		aRow.add("0"); //VNDKM_Date - Check - MO_136
		aRow.add("0"); //VNDKD_Date - Check - DA_137
		aRow.add("0"); //VNDKY_Date - Check - YR_138
		aRow.add("0"); //VNDK#_Date - Check - CTRY_139
		aRow.add("0"); //VNDSVM_Date - Service/Tax - MO_140
		aRow.add("0"); //VNDSVD_Date - Service/Tax - DA_141
		aRow.add("0"); //VNDSVY_Date - Service/Tax - YR_142
		aRow.add("0"); //VNDSV#_Date - Service/Tax - CTRY_143
		aRow.add("0"); //VNHDGM_Historical Date - MO_144
		aRow.add("0"); //VNHDGD_Historical Date - DA_145
		aRow.add("0"); //VNHDGY_Historical Date - YR_146
		aRow.add("0"); //VNHDG#_Historical Date - CTRY_147
		aRow.add("0"); //VNDKCM_Date - Check Cleared - MO_148
		aRow.add("0"); //VNDKCD_Date - Check Cleared - DA_149
		aRow.add("0"); //VNDKCY_Date - Check Cleared - YR_150
		aRow.add("0"); //VNDKC#_Date - Check Cleared - CTRY_151
		aRow.add("0"); //VNIVDM_Date - Invoice - MO_152
		aRow.add("0"); //VNIVDD_Date - Invoice - DA_153
		aRow.add("0"); //VNIVDY_Date - Invoice - YR_154
		aRow.add("0"); //VNIVD#_Date - Invoice - CTRY_155
		writeRowToOutputStream(aRow, '"');
		translator.writeOutputStream("\n");
	}

	private AccountCodeDef getErpAcctCd(String init, String defaultAccount,String defaultSubsidary){
		AccountCodeDef acctDef = new AccountCodeDef();
		//then it is a resale order!! 
		if(currOutboundReq.getInvoiceData().getErpPoNum().endsWith("R")){
			acctDef.account = RESALE_ACCOUNT_CD;
			acctDef.subsidiary = RESALE_SUBSIDIARY_CD;
			return acctDef;
		}
		acctDef.account = defaultAccount;
		acctDef.subsidiary = defaultSubsidary;
		StringBuffer acctCdBuf;
		if(init==null){
			acctCdBuf = new StringBuffer();
		}else{
			acctCdBuf = new StringBuffer(init);
		}
		String scratch = Utility.getFirstToken(acctCdBuf);

		if(Utility.isSet(scratch)){
			acctDef.account = scratch;
		}
		scratch = Utility.getFirstToken(acctCdBuf);
		if(Utility.isSet(scratch)){
			acctDef.subsidiary = scratch;
		}
		return acctDef;
	}

	public void buildInterchangeContent() throws Exception {
		Collections.sort(getTransactionsToProcess(),COMPANY_COMPARATOR);
		super.buildInterchangeContent();
	}
	public void buildTransactionContent()
	throws Exception {
		currOrder = currOutboundReq.getOrderD();
		InvoiceAbstractionView inv = currOutboundReq.getInvoiceData();
		log.info("Processing invoice: "+inv.getInvoiceNum());

		String recCompany = getCompanyCode(currOutboundReq);
		if(mCurrCompany == null || !mCurrCompany.equals(recCompany)){
			mCurrBatch = generateNewBatch();
			mCurrCompany = recCompany;
			mTransactionNumber = 1;
		}

		List idd = currOutboundReq.getInvoiceDetailDV();
		String defaultErpAcctCd = "6310";
		if(idd != null){
			Iterator iddIt = idd.iterator();
			while(iddIt.hasNext()){
				Object o = iddIt.next();
				InvoiceCustDetailRequestData detReq = (InvoiceCustDetailRequestData) o;
				InvoiceAbstractionDetailView aLine = detReq.getInvoiceDetailD();

				//Gets the account GL code
				String[] erpAcctDef = getSubAccount(aLine.getCustContractPrice(),detReq.getStoreCategory(),aLine.getERPAccountCode());
				AccountCodeDef actDef = getErpAcctCd(aLine.getERPAccountCode(),erpAcctDef[0],erpAcctDef[1]);
				defaultErpAcctCd=actDef.account;
				addRecordInformation(new RecordInformation(currOutboundReq,aLine.getLineTotal(),actDef.account,actDef.subsidiary,normalizeString(currOutboundReq.getInvoiceData().getInvoiceNum())));
			}
		}
		AccountCodeDef actDef = getErpAcctCd("",defaultErpAcctCd,FREIGHT_SUBSIDIARY_CD);
		//now add in freight
		//lineCount++;
		addRecordInformation(new RecordInformation(currOutboundReq, inv.getFreight(),actDef.account,actDef.subsidiary,"Freight for "+currOutboundReq.getInvoiceData().getInvoiceNum()));
		//now add in misc charges
		//lineCount++;
		addRecordInformation(new RecordInformation(currOutboundReq, inv.getMiscCharges(),actDef.account,actDef.subsidiary,"Misc Charges for "+currOutboundReq.getInvoiceData().getInvoiceNum()));
		//now add in the sales tax
		//lineCount++;
		actDef = getErpAcctCd("",defaultErpAcctCd,SALES_TAX_SUBSIDIARY_CD);
		addRecordInformation(new RecordInformation(currOutboundReq, inv.getSalesTax(),actDef.account,actDef.subsidiary,"Tax for "+currOutboundReq.getInvoiceData().getInvoiceNum()));

		createRecords();

		mTransactionNumber++;
		inv.markAcknowledged();
		appendIntegrationRequest(inv);
	}

	private void createRecords() throws IOException{
		Iterator it = invoiceAccountMap.keySet().iterator();
		int counter = 0;
		while(it.hasNext()){
			counter++;
			Object key = it.next();
			RecordInformation ri = (RecordInformation) invoiceAccountMap.get(key);
			createF04Record(ri,counter);
			createF09Record(ri,counter);
		}
		invoiceAccountMap.clear();
	}

	HashMap invoiceAccountMap = new HashMap();
	private void addRecordInformation(RecordInformation ri){
		//some fields (like freight) may be null instead of zero
		if(ri.amount == null || ri.amount.compareTo(ZERO) == 0){
			return;
		}
		String key = ri.getKey();
		RecordInformation existing = (RecordInformation) invoiceAccountMap.get(key);
		if(existing == null){
			invoiceAccountMap.put(key,ri);
		}else{
			existing.amount = Utility.addAmt(existing.amount,ri.amount);
		}
	}

	/**
	 * Normalizes a string to alpha numeric values only
	 */
	private String normalizeString(String pVal){
		StringBuffer pBuf = new StringBuffer();
		char[] ca = pVal.toCharArray();
		for(int i=0;i<ca.length;i++){
			if(Character.isLetterOrDigit(ca[i])){
				pBuf.append(ca[i]);
			}
		}
		return pBuf.toString();
	}

	/**
	 * Retrieves the company code for the site out of the OutboundEDIRequestData.
	 * This is defined as a site field code containing the word "company" case insensitive
	 */
	private static String getCompanyCode(OutboundEDIRequestData pReq){
		Iterator it = pReq.getSiteProperties().iterator();
		while(it.hasNext()){
			PropertyData p = (PropertyData) it.next();
			if(RefCodeNames.PROPERTY_TYPE_CD.SITE_FIELD_CD.equals(p.getPropertyTypeCd())){
				String field = p.getShortDesc();
				if(field == null){
					continue;
				}
				field = field.toLowerCase();
				if(field.indexOf("company") >= 0){
					return p.getValue();
				}
			}
		}

		//throw an exception and let it bubble out to be delt with
		throw new RuntimeException(pReq.getInvoiceData().getInvoiceNum()+" had no company code!");
	}

	/**
	 * Copied from GCAViewLogic
	 */
	private static String[] getSubAccount(BigDecimal unitPrice, String categoryName, String erpAccount){
		String[] toReturn = new String[2];
		toReturn[0] = null;
		toReturn[1] = null;
		if("Vacuums".equalsIgnoreCase(categoryName) && unitPrice.compareTo(new BigDecimal(200)) >= 0){
			//capex...for now do nothing need to know sub account
			erpAccount = "2030";
		}else if("Vacuums".equalsIgnoreCase(categoryName)){
			erpAccount = "6310";
		}
		if(categoryName != null){
			categoryName = categoryName.toLowerCase();
		}
		String capExCheck = (String) CATEGORY_SUB_ACCOUNT_MAP.get(categoryName);
		if("2030".equals(capExCheck)){
			erpAccount = capExCheck;
		}
		if(!Utility.isSet(erpAccount)){
			toReturn[0] = erpAccount;
			return toReturn;
		}else if(erpAccount.indexOf("6310") < 0){
			toReturn[0] = erpAccount;
			return toReturn;
		}
		toReturn[0] = erpAccount;
		toReturn[1] = (String) CATEGORY_SUB_ACCOUNT_MAP.get(categoryName);
		return toReturn;
	}

	private static final HashMap CATEGORY_SUB_ACCOUNT_MAP = new HashMap();
	// this is hard coded and also duplicated in file InvoiceDistGCAGeneralLedgerLogic and 
	// GCAViewLogic. Need to update all three place if changed	
	static{
		CATEGORY_SUB_ACCOUNT_MAP.put("specialty foodservice chemicals","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("floor equipment","2030");
		CATEGORY_SUB_ACCOUNT_MAP.put("concrete care","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("floor and window tools","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("waste receptacles","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("safety supplies","10"); // not a top category any more, will leave it as it is
		CATEGORY_SUB_ACCOUNT_MAP.put("glass cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("mops and handles","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("uniforms","(It's own account)");
		CATEGORY_SUB_ACCOUNT_MAP.put("insecticides","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("facial tissue","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("sanitizers","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("vacuums","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("general cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("spray bottles","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("towels","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("miscellaneous accessories","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("mopping equipment","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("wipers","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("floor care","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("chemical management systems","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("drain cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("hand soap and dispensers","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("toilet tissue","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("laundry","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("carpet care","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("sweepers","2030");
		CATEGORY_SUB_ACCOUNT_MAP.put("dusting accessories","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("matting","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("tape and dispensers","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("polishes","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("disinfectants","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("brooms and brushes","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("window cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("floor pads","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("feminine hygiene products","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("kitchen cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("janitor carts","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("deodorants and dispensers","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("washroom cleaners","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("carpet extractors","2030");
		CATEGORY_SUB_ACCOUNT_MAP.put("liners","20");
		CATEGORY_SUB_ACCOUNT_MAP.put("spin bonnets","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("hand pads and sponges","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("cups, plates, napkins, & plastic utensils","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("seat covers and dispensers","30");
		CATEGORY_SUB_ACCOUNT_MAP.put("industrial cleaners/degreasers","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("vacuum accessories","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("dishwashing","50");
		CATEGORY_SUB_ACCOUNT_MAP.put("clean room supplies","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("lighting","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("safety - required","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("safety supplies (other)","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("gloves and glasses","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("shoes and booties","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("other mandatory safety","10");
		CATEGORY_SUB_ACCOUNT_MAP.put("material handling","10");
	}
	private static final Comparator COMPANY_COMPARATOR = new Comparator() {
		public int compare(Object po1, Object po2){
			OutboundEDIRequestData o1 = (OutboundEDIRequestData) po1;
			OutboundEDIRequestData o2 = (OutboundEDIRequestData) po2;

			String s1 = getCompanyCode(o1);
			String s2 = getCompanyCode(o2);
			if(s1==null) s1="";
			if(s2==null) s2="";
			int comp = s1.compareTo(s2);
			return comp;
		}
	};

	private class AccountCodeDef{
		String account;
		String subsidiary;
	}

	private class RecordInformation{
		OutboundEDIRequestData req;
		BigDecimal amount;
		String account;
		String subAccount;
		String desc;
		private RecordInformation(OutboundEDIRequestData pReq,BigDecimal pAmount, 
				String pAccount,String pSubAccount,String pDesc){
			req=pReq;
			amount=pAmount;
			account=pAccount;
			subAccount=pSubAccount;
			desc=pDesc;
		}

		private String getKey(){
			return account+"."+subAccount;
		}

		private String getCompoundAccount(String buNum){
			StringBuffer buf = new StringBuffer();
			if(!Utility.isSet(buNum)){
				throw new RuntimeException("BU Number (site budget ref number) is requiered!");
			}
			buf.append(buNum);
			if(account!=null){
				buf.append("."+account);
				if(subAccount != null){
					buf.append("."+subAccount);
				}
			}
			return buf.toString();
		}
	}
}
