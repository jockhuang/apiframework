package com.specl.api.sharding.context;

import org.apache.log4j.Logger;

public class DbContextHolder {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DbContextHolder.class);	
	
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal dbContextHolder  = new ThreadLocal();
	
	@SuppressWarnings("unchecked")
	public static void setDbShardContext(DbShardContext dbShardContext) {
		dbContextHolder.set(dbShardContext);
	}

	public static DbShardContext getDbShardContext() {
		return (DbShardContext) dbContextHolder.get();
	}

	public static void clearDbShardContext() {
		dbContextHolder.remove();
	}
}
