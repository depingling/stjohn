package com.cleanwise.service.api.util.synchronizer;

import java.util.ArrayList;

public class TcsLoaderSpecification {

	class TcsLoaderFieldSpec {
		public String fieldName;
		public ForObject requiredFor;
		public ForObject uniqueFor = ForObject.NONE;

		public TcsLoaderFieldSpec(String fieldName, ForObject requiredFor) {
			super();
			this.fieldName = fieldName;
			this.requiredFor = requiredFor;
		}

		public TcsLoaderFieldSpec(String fieldName, ForObject requiredFor,
				ForObject uniqueFor) {
			this(fieldName, requiredFor);
			this.uniqueFor = uniqueFor;
		}
	}

	public enum ObjectType {
		MasterAsset, MasterItem
	}

	private enum ForObject {
		ASSET_ITEM, ASSET, ITEM, NONE
	}

	private ObjectType objectType;

	public TcsLoaderSpecification(ObjectType objectType) {
		super();
		this.objectType = objectType;
	}

	private TcsLoaderFieldSpec[] fieldSpec = new TcsLoaderFieldSpec[] {
			new TcsLoaderFieldSpec("VERSION_NUMBER", ForObject.ASSET_ITEM),
			new TcsLoaderFieldSpec("ACTION", ForObject.ASSET_ITEM),
			new TcsLoaderFieldSpec("ASSET", ForObject.ASSET_ITEM),
			new TcsLoaderFieldSpec("STORE_ID", ForObject.ASSET_ITEM),
			new TcsLoaderFieldSpec("STORE_NAME", ForObject.NONE),
			new TcsLoaderFieldSpec("DIST_SKU", ForObject.ITEM),
			new TcsLoaderFieldSpec("MFG_SKU", ForObject.ASSET_ITEM,
					ForObject.ASSET_ITEM),
			new TcsLoaderFieldSpec("MANUFACTURER", ForObject.ASSET_ITEM,
					ForObject.ASSET_ITEM),
			new TcsLoaderFieldSpec("DISTRIBUTOR", ForObject.ITEM),
			new TcsLoaderFieldSpec("PACK", ForObject.ITEM),
			new TcsLoaderFieldSpec("UOM", ForObject.ITEM, ForObject.ITEM),
			new TcsLoaderFieldSpec("CATEGORY_NAME", ForObject.ASSET_ITEM),
			new TcsLoaderFieldSpec("SUB_CAT_1", ForObject.NONE),
			new TcsLoaderFieldSpec("SUB_CAT_2", ForObject.NONE),
			new TcsLoaderFieldSpec("SUB_CAT_3", ForObject.NONE),
			new TcsLoaderFieldSpec("MULTI_PRODUCT_NAME", ForObject.NONE),
			new TcsLoaderFieldSpec("ITEM_SIZE", ForObject.NONE),
			new TcsLoaderFieldSpec("LONG_DESCRIPTION", ForObject.ASSET_ITEM),
			new TcsLoaderFieldSpec("SHORT_DESCRIPTION", ForObject.ITEM),
			new TcsLoaderFieldSpec("PRODUCT_UPC", ForObject.NONE),
			new TcsLoaderFieldSpec("PACK_UPC", ForObject.NONE),
			new TcsLoaderFieldSpec("UNSPSC_CODE", ForObject.NONE),
			new TcsLoaderFieldSpec("COLOR", ForObject.NONE),
			new TcsLoaderFieldSpec("SHIPPING_WEIGHT", ForObject.NONE),
			new TcsLoaderFieldSpec("WEIGHT_UNIT", ForObject.NONE),
			new TcsLoaderFieldSpec("NSN", ForObject.NONE),
			new TcsLoaderFieldSpec("SHIPPING_CUBIC_SIZE", ForObject.NONE),
			new TcsLoaderFieldSpec("HAZMAT", ForObject.NONE), // this is required field in the TCS_Assets_Loader_Specifications_V2.docx
			new TcsLoaderFieldSpec("CERTIFIED_COMPANIES", ForObject.NONE),
			new TcsLoaderFieldSpec("IMAGE", ForObject.NONE),
			new TcsLoaderFieldSpec("MSDS", ForObject.NONE),
			new TcsLoaderFieldSpec("SPECIFICATION", ForObject.NONE),
			new TcsLoaderFieldSpec("ASSET_NAME", ForObject.ASSET),
			new TcsLoaderFieldSpec("MODEL_NUMBER", ForObject.NONE),
			new TcsLoaderFieldSpec("ASSOC_DOC_1", ForObject.NONE),
			new TcsLoaderFieldSpec("ASSOC_DOC_2", ForObject.NONE),
			new TcsLoaderFieldSpec("ASSOC_DOC_3", ForObject.NONE)

	};

	public String[] getRequiredFields() {
		ArrayList<String> requiredFields = new ArrayList<String>();
		for (int i = 0; i < fieldSpec.length; i++) {
			if (this.objectType.equals(ObjectType.MasterAsset)) {
				if (fieldSpec[i].requiredFor.equals(ForObject.ASSET)
						|| fieldSpec[i].requiredFor
								.equals(ForObject.ASSET_ITEM)) {
					requiredFields.add(fieldSpec[i].fieldName);
				}
			} else if (this.objectType.equals(ObjectType.MasterItem)) {
				if (fieldSpec[i].requiredFor.equals(ForObject.ITEM)
						|| fieldSpec[i].requiredFor
								.equals(ForObject.ASSET_ITEM)) {
					requiredFields.add(fieldSpec[i].fieldName);
				}
			}
		}
		return (String[]) requiredFields.toArray(new String[] {});
	}

	public String[] getUniqueFields() {
		ArrayList<String> uniqueFields = new ArrayList<String>();
		for (int i = 0; i < fieldSpec.length; i++) {
			if (this.objectType.equals(ObjectType.MasterAsset)) {
				if (fieldSpec[i].uniqueFor.equals(ForObject.ASSET)
						|| fieldSpec[i].uniqueFor.equals(ForObject.ASSET_ITEM)) {
					uniqueFields.add(fieldSpec[i].fieldName);
				}
			} else if (this.objectType.equals(ObjectType.MasterItem)) {
				if (fieldSpec[i].uniqueFor.equals(ForObject.ITEM)
						|| fieldSpec[i].uniqueFor.equals(ForObject.ASSET_ITEM)) {
					uniqueFields.add(fieldSpec[i].fieldName);
				}
			}
		}
		return (String[]) uniqueFields.toArray(new String[] {});
	}

}
