/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on 26 Aug 2016
 */

package com.lmig.ci.lmbc.empr.common.util;

import java.io.IOException;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author n0256907
 *
 */
public class JsonParserUtil {

	private static ObjectMapper mapper = new ObjectMapper();
	public static final String currentRecord = "CURRENTRECORD";
	public static final String previousRecord = "PREVIOUSRECORD";
	public static final String referenceRecord = "REFERENCEDATARECORD";

	/*
	 * Converts the given object into Json Node
	 */
	public static JsonNode convertObjectToJson(Object obj) {
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		return mapper.convertValue(obj, JsonNode.class);
	}

	/*
	 * converts the given object into JsonString
	 */
	public static String convertObjectToJsonString(Object obj) throws JsonProcessingException {

		return getJsonAsString(obj);
	}
	/*
	 * creates a Json node by combining 2 objects. Just adds them. Nothing fancy
	 */

	public static String mergeObjectsToJsonString(Object previousObj, Object currentObj)
			throws JsonProcessingException {

		JSONObject combinedObject = new JSONObject();
		combinedObject.put(previousRecord, convertObjectToJson(previousObj));
		combinedObject.put(currentRecord, convertObjectToJson(currentObj));
		return getJsonAsString(combinedObject);
	}

	/*
	 * creates a Json node by giving object a name -- in this case as the method
	 * put the word currentRecord as parent name
	 */
	public static String getJsonStringForCurrentRecord(Object currentObj) throws JsonProcessingException {

		JSONObject currentJsonObject = new JSONObject();
		currentJsonObject.put(currentRecord, convertObjectToJson(currentObj));
		return getJsonAsString(currentJsonObject);
	}

	/*
	 * creates a Json node by giving object a name -- in this case as the method
	 * put the word previousRecord as parent name
	 */
	public static String getJsonStringForPreviousRecord(Object currentObj) throws JsonProcessingException {

		JSONObject currentJsonObject = new JSONObject();
		currentJsonObject.put(previousRecord, convertObjectToJson(currentObj));
		return getJsonAsString(currentJsonObject);
	}
	/*
	 * creates a Json node by combining 2 objects. Just adds them. Nothing fancy
	 */

	public static String mergeObjectsToJsonString(Object previousObj, Object currentObj,Object referenceDataObj)
			throws JsonProcessingException {

		JSONObject combinedObject = new JSONObject();
		combinedObject.put(previousRecord, convertObjectToJson(previousObj));
		combinedObject.put(currentRecord, convertObjectToJson(currentObj));
		combinedObject.put(referenceRecord, convertObjectToJson(referenceDataObj));
		return getJsonAsString(combinedObject);
	}

	/*
	 * creates a Json node by giving object a name -- in this case as the method
	 * put the word currentRecord as parent name
	 */
	public static String getJsonStringForCurrentRecord(Object currentObj,Object referenceDataObj) throws JsonProcessingException {

		JSONObject currentJsonObject = new JSONObject();
		currentJsonObject.put(currentRecord, convertObjectToJson(currentObj));
		currentJsonObject.put(referenceRecord, convertObjectToJson(referenceDataObj));
		return getJsonAsString(currentJsonObject);
	}

	/*
	 * creates a Json node by giving object a name -- in this case as the method
	 * put the word previousRecord as parent name
	 */
	public static String getJsonStringForPreviousRecord(Object currentObj,Object referenceDataObj) throws JsonProcessingException {

		JSONObject currentJsonObject = new JSONObject();
		currentJsonObject.put(previousRecord, convertObjectToJson(currentObj));
		currentJsonObject.put(referenceRecord, convertObjectToJson(referenceDataObj));
		return getJsonAsString(currentJsonObject);
	}
	
	/*
	 * Convert the JsonObject into a String.
	 */
	public static String getJsonAsString(Object jsonObject) throws JsonProcessingException {
		String str = mapper.writeValueAsString(jsonObject);
		return str;
	}
	
	public static JsonNode  stringtoJsonNode(String jsonStr)  throws IOException {
		return mapper.readTree(jsonStr.getBytes());
	}
}
