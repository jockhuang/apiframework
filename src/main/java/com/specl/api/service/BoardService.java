/**
 * Specl.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.specl.api.service;

import java.util.List;

import com.specl.model.Board;

/**
 * 
 * @author mengxiaonan
 */
public interface BoardService {

    public void addBoard(Board board, List<Board> list);
    
    public Board getBoard(Integer id);
    
    public Integer deleteBoard(Integer id);
    
    public Integer updateBoard(Board board);
    
    public List<Board> getBoardList();
}
