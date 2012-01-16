/**
 * Specl.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.specl.api.dao.release;


import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.specl.api.dao.BoardDao;
import com.specl.model.Board;

/**
 * 
 * @author mengxiaonan
 */

public class BoardDaoRelease extends SqlMapClientDaoSupport implements BoardDao {

    //添加公告
    public Integer addBoard(Board board){
        int id = (Integer)getSqlMapClientTemplate().insert("board.addBoard" , board);
        
        if(id>0){
            return id;
        }else{
            return 0;
        }
    }
    
    //编辑公告
    public Integer updateBoard(Board board){
        int affectRow = getSqlMapClientTemplate().update("board.updateBoard",board);
        
        return affectRow;
    }
    
    //删除公告
    public Integer deleteBoard(int id){
        int affectRows = getSqlMapClientTemplate().delete("board.deleteById",id);
        
        return affectRows;
    }
    
    //查看公告
    public Board getBoard(int id){
        return (Board)getSqlMapClientTemplate().queryForObject("board.getBoardById", id);
    }
    
    //列表
    public List<Board> getBoardList(){
       
        List list = getSqlMapClientTemplate().queryForList("board.getBoard");
        
        return list;
    }
}
