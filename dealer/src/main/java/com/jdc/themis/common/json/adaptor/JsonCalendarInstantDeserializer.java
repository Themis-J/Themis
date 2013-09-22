package com.jdc.themis.common.json.adaptor;

import java.io.IOException;

import javax.time.Instant;
import javax.time.calendar.ZonedDateTime;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonCalendarInstantDeserializer extends JsonDeserializer<Instant>
{

	@Override
	public Instant deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return ZonedDateTime.parse(jp.nextTextValue()).toInstant();
	}

}