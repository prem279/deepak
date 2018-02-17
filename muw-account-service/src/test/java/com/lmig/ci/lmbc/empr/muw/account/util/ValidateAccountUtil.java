package com.lmig.ci.lmbc.empr.muw.account.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class ValidateAccountUtil {

	private static List<String> ignoredFields = new ArrayList<String>();

	public static void compareAccount(JsonNode sourceJson, JsonNode destJson) {
		if (null == sourceJson && null == destJson) {
			return;
		}

		initializeIgnoredFields();

		assertTrue("employerId", destJson.get("employerId").asInt() > 0);

		Iterator<Map.Entry<String, JsonNode>> it = sourceJson.fields();
		while (it.hasNext()) {
			Map.Entry<String, JsonNode> node = it.next();
			String key = node.getKey();
			if (!isFieldIgnored(key)) {
				assertEquals(key, isNullOrEmptyString(sourceJson.get(key)), isNullOrEmptyString(destJson.get(key)));
			}
		}

		JsonNode srcProducts = sourceJson.get("products");
		JsonNode destProducts = destJson.get("products");
		compareProducts(srcProducts, destProducts);

		JsonNode srcPref = sourceJson.get("preference");
		JsonNode destPref = destJson.get("preference");
		comparePreferences(srcPref, destPref);

	}

	public static void compareProducts(JsonNode srcProducts, JsonNode destProducts) {
		Iterator<JsonNode> srcProductsIt = srcProducts.elements();
		Iterator<JsonNode> destProductsIt = destProducts.elements();
		while (srcProductsIt.hasNext() && destProductsIt.hasNext()) {
			JsonNode srcProduct = srcProductsIt.next();
			JsonNode destProduct = destProductsIt.next();
			compareProduct(srcProduct, destProduct);
		}

		assertTrue(!srcProductsIt.hasNext() && !destProductsIt.hasNext());
	}

	public static void compareProduct(JsonNode srcProduct, JsonNode destProduct) {
		assertTrue("productId", destProduct.get("productId").asInt() > 0);

		Iterator<Map.Entry<String, JsonNode>> it = srcProduct.fields();
		while (it.hasNext()) {
			Map.Entry<String, JsonNode> node = it.next();
			String key = node.getKey();
			if (key.equals("labels")) {
				Iterator<JsonNode> srcLabelsIt = node.getValue().iterator();
				Iterator<JsonNode> destLabelsIt = destProduct.get(key).elements();
				while (srcLabelsIt.hasNext() && destLabelsIt.hasNext()) {
					JsonNode srcLabel = srcLabelsIt.next();
					JsonNode destLabel = destLabelsIt.next();
					compareLabel(srcLabel, destLabel);
				}
			}
			if (!isFieldIgnored(key)) {
				assertEquals(key, isNullOrEmptyString(srcProduct.get(key)), isNullOrEmptyString(destProduct.get(key)));
			}
		}

	}

	public static void comparePreferences(JsonNode srcPref, JsonNode destPref) {
		assertTrue("employerPreferencesId", destPref.get("employerPreferencesId").asInt() > 0);

		Iterator<Map.Entry<String, JsonNode>> it = srcPref.fields();
		while (it.hasNext()) {
			Map.Entry<String, JsonNode> node = it.next();
			String key = node.getKey();

			if (key.equals("employeeLabels") || key.equals("spouseLabels") || key.equals("employeeIdLabels")) {
				Iterator<JsonNode> srcLabelsIt = node.getValue().iterator();
				Iterator<JsonNode> destLabelsIt = destPref.get(key).elements();
				while (srcLabelsIt.hasNext() && destLabelsIt.hasNext()) {
					JsonNode srcLabel = srcLabelsIt.next();
					JsonNode destLabel = destLabelsIt.next();
					compareLabel(srcLabel, destLabel);
				}
			}

			if (!isFieldIgnored(key)) {
				assertEquals(key, isNullOrEmptyString(srcPref.get(key)), isNullOrEmptyString(destPref.get(key)));
			}
		}

	}

	public static void compareLabel(JsonNode srcLabel, JsonNode destLabel) {
		assertTrue("labelId", destLabel.get("labelId").asInt() > 0);

		Iterator<Map.Entry<String, JsonNode>> it = srcLabel.fields();
		while (it.hasNext()) {
			Map.Entry<String, JsonNode> node = it.next();
			String key = node.getKey();
			if (!isFieldIgnored(key)) {
				assertEquals(key, isNullOrEmptyString(srcLabel.get(key)), isNullOrEmptyString(destLabel.get(key)));
			}
		}

	}

	private static String isNullOrEmptyString(JsonNode jsonNode) {
		if (jsonNode == null || jsonNode.isNull() || "".equals(jsonNode.asText())) {
			return null;
		}
		return jsonNode.asText();
	}

	private static void initializeIgnoredFields() {
		ignoredFields.add("employerId");
		ignoredFields.add("productId");
		ignoredFields.add("employerPreferencesId");
		ignoredFields.add("rowEffectiveDatetime");
		ignoredFields.add("rowExpirationDatetime");
		ignoredFields.add("createDatetime");
		ignoredFields.add("lastUpdateDatetime");
		ignoredFields.add("lastUpdateUserIdNum");
		ignoredFields.add("createUserIdNum");
		ignoredFields.add("productSelected");
		ignoredFields.add("concurrencyQuantity");
		ignoredFields.add("employerStakeholderLedgerNumber");
		ignoredFields.add("employerStakeholderSerialNumber");

	}

	private static boolean isFieldIgnored(String field) {
		return ignoredFields.contains(field);
	}
}
