package com.specl.api.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Transactional;

import com.specl.api.sharding.context.DbContextHolder;
import com.specl.api.sharding.context.DbShardContext;
import com.specl.constant.DataShardTypeEnum;
import com.specl.constant.ErrorCode;
import com.specl.constant.ShardTypeEnum;
import com.specl.constants.Constants;
import com.specl.exception.SpeclException;

@Aspect
@Order(1)
public class DataSourceInterceptor{
	
	Logger logger = Logger.getLogger(DataSourceInterceptor.class);
	
	@Before("@annotation(Transactional)")
	public void preServiceMethod(JoinPoint jp,Transactional Transactional) throws Exception {
		String transactionManagerName = Transactional.value();
		if(!"jtaTx".equals(transactionManagerName)){
			DataShardTypeEnum dataShardType = Transactional.dataShardType();
			ShardTypeEnum shardType = Transactional.shardType();
			String readOrWrite = Transactional.readOrWrite();
			String shardKey = Transactional.shardKey();
			boolean shardFlag = Transactional.shardFlag();
			String methodName =jp.getSignature().getName();
			if(readOrWrite.equals(Constants.READ_DB)){
				readOrWrite = filter(readOrWrite, methodName);
			}
			if(shardFlag){
				Object[] parameters = jp.getArgs();
				if(parameters[0] instanceof String){
					shardKey = (String)parameters[0];
				}else{
					throw new SpeclException("the method's frist parameter should be a String class", ErrorCode.EC300);
				}
			}
			
			DbShardContext dbShardContext = new DbShardContext(dataShardType, shardType, readOrWrite, shardKey);
			DbContextHolder.setDbShardContext(dbShardContext);
		}
	}
	
	private String filter(String readOrWrite, String methodName){
		
		if(methodName.toLowerCase().startsWith("add")){
			return Constants.WRITE_DB;
		}
		
		if(methodName.toLowerCase().startsWith("save")){
			return Constants.WRITE_DB;
		}
		
		if(methodName.toLowerCase().startsWith("update")){
			return Constants.WRITE_DB;
		}
		
		if(methodName.toLowerCase().startsWith("delete")){
			return Constants.WRITE_DB;
		}
		
		if(methodName.toLowerCase().startsWith("remove")){
			return Constants.WRITE_DB;
		}
		
		if(methodName.toLowerCase().startsWith("create")){
			return Constants.WRITE_DB;
		}
		
		return Constants.READ_DB;
	}
}
