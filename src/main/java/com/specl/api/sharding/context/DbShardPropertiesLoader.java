package com.specl.api.sharding.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.specl.utils.PropertyUtil;
import com.specl.utils.Resources;


public class DbShardPropertiesLoader {

	private static Logger logger = Logger.getLogger(PropertyUtil.class);

	private final static String CONFIG_FILE = "db-shard-conf.properties";

	private static Properties config;

	private static DbShardPropertiesLoader instance = null;

	private DbShardPropertiesLoader() {
		File file = null;
		try {
			file = Resources.getResourceAsFile(CONFIG_FILE);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (file == null || !file.exists()) {
			logger.error(String.format(
					"..........%s  file  not exist.........", CONFIG_FILE));
			System.exit(1);
		}
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			config = new Properties();
			config.load(is);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static DbShardPropertiesLoader getInstance() {
		synchronized (DbShardPropertiesLoader.class) {
			if (instance == null) {
				instance = new DbShardPropertiesLoader();
			}
		}
		return instance;
	}

	public Properties getProperties() {
		return config;
	}

	public String get(String key) {
		return config.getProperty(key);
	}

	public int getInt(String key) {
		return Integer.parseInt(get(key));
	}

	/**
	 * Return default value if can not find the key
	 */
	public int getInt(String key, int defaultValue) {
		String value = get(key);

		if (value == null || value.length() <= 0) {
			logger.warn("can not find value for key=" + key
					+ ", use the default value=" + defaultValue);
			return defaultValue;
		}

		return getInt(key);
	}

	public boolean getBoolean(String key) {
		return Boolean.valueOf(get(key));
	}

	public synchronized static void reload() {
		if (instance != null) {
			instance = null;
		}
		instance = new DbShardPropertiesLoader();
	}
}