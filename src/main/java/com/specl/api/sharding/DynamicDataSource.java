package com.specl.api.sharding;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.specl.api.sharding.context.DbContextHolder;
import com.specl.api.sharding.context.DbShardContext;

public class  DynamicDataSource extends AbstractRoutingDataSource {

	private static Logger logger = Logger.getLogger(DynamicDataSource.class);
	
	@Override
	protected Object determineCurrentLookupKey() {	
		
		String sourceName = "merchant_rw_0";
		DbShardContext context = DbContextHolder.getDbShardContext();
		if (context != null){
			sourceName = context.getDataSourceId();
		}
		return sourceName;
	}

}
