/**
 * Specl.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.specl.api.dao;

import java.util.List;

import com.specl.model.Board;

/**
 * 
 * @author mengxiaonan
 */
public interface BoardDao {

    public Integer addBoard(Board board);
    
    public Integer updateBoard(Board board);
    
    public Integer deleteBoard(int id);
    
    public Board getBoard(int id);
    
    public List<Board> getBoardList();
}
