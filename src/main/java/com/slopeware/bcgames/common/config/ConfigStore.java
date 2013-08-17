package com.slopeware.bcgames.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public abstract class ConfigStore<ConfigType extends Config> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(ConfigStore.class);
	
	private static final String jsonConfigFile = "/config.json";
	private static final String propertiesFile = "/config.properties";
	
	private ObjectMapper mapper = new ObjectMapper();
	private ConfigType config;
	private Properties properties;

	public ConfigStore() { }
	
	@PostConstruct
	public void init() {
		// Load the application config JSON file
		try {
			InputStream appConfigStream = ConfigStore.class.getResourceAsStream(jsonConfigFile);
			config = mapper.readValue(appConfigStream, getConfigType());
		} catch (JsonParseException e) {
			log.error("Cannot parse config file: " + jsonConfigFile, e);
		} catch (JsonMappingException e) {
			log.error("Cannot map config file: " + jsonConfigFile, e);
		} catch (IOException e) {
			log.error("Cannot load config file: " + jsonConfigFile, e);
		}
		
		// Load the bitcoin config properties file
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(config.getToken());
		InputStream bcConfigStream = getConfigType().getResourceAsStream(propertiesFile);
		properties = new EncryptableProperties(encryptor);
		try {
			properties.load(bcConfigStream);
		} catch (IOException e) {
			log.error("Cannot load bitcoin properties file: " + propertiesFile, e);
		}
	}
	
	public ConfigType getConfig() {
		return config;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public String getProperty(String key) {
			return properties.getProperty(key);
	}
	
	protected abstract Class<ConfigType> getConfigType();
}
