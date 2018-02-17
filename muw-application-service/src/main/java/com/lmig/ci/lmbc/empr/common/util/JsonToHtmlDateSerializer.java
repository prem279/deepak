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

@Component
public class JsonToHtmlDateSerializer extends JsonSerializer<Date> {

	public static final JsonToHtmlDateSerializer INSTANCE = new JsonToHtmlDateSerializer();

	private JsonToHtmlDateSerializer() {
	}

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(value);		
		gen.writeString(dateString);
		
		System.out.println("******************************************************************************************************************************************"
				+ "*********************"+dateString);
		
	}
}

