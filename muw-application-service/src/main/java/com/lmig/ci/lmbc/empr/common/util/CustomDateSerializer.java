/*
 * Copyright (C) 2016, Liberty Mutual Group
 *
 * Created on Feb 3, 2016
 */

package com.lmig.ci.lmbc.empr.common.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Custom Jackson deserializer for transforming a JSON object (using the ISO
 * 8601 date formatwith optional time) to a JSR310 LocalDate object.
 * 
 * Specifically using UTC as the zone so there is no offset
 */

@Component
public class CustomDateSerializer extends JsonSerializer<Date> {

	public static final CustomDateSerializer INSTANCE = new CustomDateSerializer();

	private CustomDateSerializer() {
	}

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String dateString = df.format(value);		
		gen.writeString(dateString);		
	}
}
