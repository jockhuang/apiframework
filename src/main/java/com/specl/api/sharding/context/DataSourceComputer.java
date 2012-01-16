package com.specl.api.sharding.context;

import org.apache.log4j.Logger;

import com.specl.constant.DataShardTypeEnum;
import com.specl.constants.Constants;

public class DataSourceComputer {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DataSourceComputer.class);
	
	public static Integer modSource(DataShardTypeEnum datashardType, int sourceType,String dbShardKey) {
		DbShardPropertiesLoader dbShardConfig = DbShardPropertiesLoader.getInstance();
		String key = datashardType.name();
		if(sourceType==Constants.SHARD_DB){
			key = key.concat(".db.mod.value");
		}
		if(sourceType == Constants.SHARD_TABLE){
			key = key.concat(".table.mod.value");
		}
		Integer modValue = dbShardConfig.getInt(key);		
		Integer sourceId = new Integer(dbShardKey)/modValue;
		return sourceId;
	}

	public static Integer hashSource(DataShardTypeEnum datashardType,int sourceType, String dbShardKey) {
		DbShardPropertiesLoader dbShardConfig = DbShardPropertiesLoader.getInstance();
		String key = datashardType.name();
		if(sourceType==Constants.SHARD_DB){
			key = key.concat(".db.hash.count");
		}
		if(sourceType == Constants.SHARD_TABLE){
			key = key.concat(".table.hash.count");
		}
		Integer shardCount = dbShardConfig.getInt(key);		
		Integer hashCode = Math.abs(dbShardKey.hashCode());
		Integer sourceId = hashCode % shardCount;
		return sourceId;
	}
}
