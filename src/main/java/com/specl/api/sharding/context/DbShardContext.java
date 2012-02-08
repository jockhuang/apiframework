package com.specl.api.sharding.context;

import org.apache.log4j.Logger;

import com.specl.constant.DataShardTypeEnum;
import com.specl.constant.ShardTypeEnum;
import com.specl.constants.Constants;



public class DbShardContext {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DbShardContext.class);
	
	private DataShardTypeEnum dataShardType;
	private ShardTypeEnum dbShardType;
	private String shardKey;
	private String accessType;

	public DbShardContext() {
		this.dbShardType = null;
		this.shardKey = null;
		this.accessType = null;
	}

	public DbShardContext(DataShardTypeEnum dataShardType,ShardTypeEnum dbShardType,String accessType, String shardKey) {
		this.dataShardType = dataShardType;
		this.dbShardType = dbShardType;
		this.shardKey = shardKey;
		this.accessType = accessType;
	}

	public String getDataSourceId(){

		return computeDataSource();
	}

	private String computeDataSource() {

		Integer sourceId = -1;      
		
		if(dbShardType != null){ 
			switch (dbShardType) {
			case mod:
				sourceId = DataSourceComputer.modSource(dataShardType, Constants.SHARD_DB, shardKey);
				break;
			case hash:
				sourceId = DataSourceComputer.hashSource(dataShardType,	Constants.SHARD_DB, shardKey);
				break;
			}
		}
		
		return sourceName(sourceId);
	}

	private String sourceName(Integer sourceId){		
		String sourceName = dataShardType.name();
		sourceName = sourceName.concat("_");
		sourceName = sourceName.concat(accessType);
		sourceName = sourceName.concat("_");
		sourceName = sourceName.concat(sourceId.toString());
		return sourceName;
	}

	public DataShardTypeEnum getDataShardType() {
		return dataShardType;
	}

	public ShardTypeEnum getDbShardType() {
		return dbShardType;
	}

	public String getShardKey() {
		return shardKey;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setDataShardType(DataShardTypeEnum dataShardType) {
		this.dataShardType = dataShardType;
	}

	public void setDbShardType(ShardTypeEnum dbShardType) {
		this.dbShardType = dbShardType;
	}

	public void setShardKey(String shardKey) {
		this.shardKey = shardKey;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	
}
