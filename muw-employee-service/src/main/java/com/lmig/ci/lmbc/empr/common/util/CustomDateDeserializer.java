/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Feb 3, 2016
 */

package com.lmig.ci.lmbc.empr.common.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Custom Jackson deserializer for transforming a JSON object (using the ISO
 * 8601 date formatwith optional time) to a JSR310 LocalDate object.
 */
public class CustomDateDeserializer extends JsonDeserializer<Date> {

	public static final CustomDateDeserializer INSTANCE = new CustomDateDeserializer();

	private CustomDateDeserializer() {
	}

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String date = parser.getText();
		try {
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
