/**
 * Specl.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.specl.api.service.release;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.specl.api.client.ApiClientHandler;
import com.specl.api.dao.BoardDao;
import com.specl.api.dao.MailDao;
import com.specl.api.service.BoardService;
import com.specl.api.sharding.context.DbContextHolder;
import com.specl.api.sharding.context.DbShardContext;
import com.specl.constant.DataShardTypeEnum;
import com.specl.constant.ShardTypeEnum;
import com.specl.constants.Constants;
import com.specl.model.Board;
import com.specl.model.Mail;

/**
 * 
 * @author mengxiaonan
 */

public class BoardServiceRelease  implements BoardService {
    private static final Logger logger = Logger.getLogger(BoardServiceRelease.class);
    @Resource
    private BoardDao boardDao;
    @Resource
    private MailDao mailDao;
    
    
    //添加公告
    @Transactional(value = "jtaTx")
    public void addBoard(Board board , List<Board> list){
        logger.info(board);
        if (board!=null && StringUtils.trimToNull(board.getTitle())!=null && StringUtils.trimToNull(board.getUrl())!=null){
            this.switchToMerchantDb(true, "1");
            
        	boardDao.addBoard(board);
//            int i=2;
//            int j=0;
//            int k = i/j;
            this.switchToOrderDb(true, "1");
            Mail mail = new Mail();
            mail.setTitle("hello");
            mail.setContent("you're so nice");
            mailDao.addMail(mail);
        }
        if (list!=null){
            for (Board bo:list){
                updateBoard(bo);
            }
        }
    }
    
    //查看公告
    public Board getBoard(Integer id){
        return boardDao.getBoard(id);
    }
    
    //删除公告
    public Integer deleteBoard(Integer id){
        return boardDao.deleteBoard(id);
    }
    
    //编辑公告
    public Integer updateBoard(Board board){
        return boardDao.updateBoard(board);
    }
    
    //列表
    public List<Board> getBoardList(){
        return boardDao.getBoardList();
    }
	protected void switchToMerchantDb(boolean writeAble, String value){
		String writeOrRead = "";
		if(writeAble){
			writeOrRead = Constants.WRITE_DB;
		}else{
			writeOrRead = Constants.READ_DB;
		}
		DbShardContext dbShardContext = new DbShardContext(DataShardTypeEnum.merchant, ShardTypeEnum.mod, writeOrRead, value);
		DbContextHolder.setDbShardContext(dbShardContext);
	}
	protected void switchToOrderDb(boolean writeAble, String value){
		String writeOrRead = "";
		if(writeAble){
			writeOrRead = Constants.WRITE_DB;
		}else{
			writeOrRead = Constants.READ_DB;
		}
		DbShardContext dbShardContext = new DbShardContext(DataShardTypeEnum.order, ShardTypeEnum.mod, writeOrRead, value);
		DbContextHolder.setDbShardContext(dbShardContext);
	}
}
