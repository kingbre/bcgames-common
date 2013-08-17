package com.slopeware.bcgames.common.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonObject {
	private static final Logger log = LoggerFactory.getLogger(JsonObject.class);
	
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonGenerationException e) {
			log.error("JSON generation exception", e);
		} catch (JsonMappingException e) {
			log.error("JSON mapping exception", e);
		} catch (IOException e) {
			log.error("IO exception", e);
		}
		return null;
	}
}
