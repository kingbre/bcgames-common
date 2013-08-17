package com.slopeware.bcgames.common.config;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
	private static final Logger log = LoggerFactory.getLogger(Config.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	protected String token;
	
	public Config() { }
	
	public Config(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonGenerationException e) {
			log.error("Cannot generate JSON from object", e);
		} catch (JsonMappingException e) {
			log.error("Cannot map JSON from object", e);
		} catch (IOException e) {
			log.error("Cannot write JSON from object", e);
		}
		return null;
	}
}
