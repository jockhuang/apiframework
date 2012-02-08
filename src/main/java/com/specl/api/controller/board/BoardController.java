/**
 * Specl.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.specl.api.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.specl.api.controller.BaseController;
import com.specl.api.service.BoardService;
import com.specl.model.Board;
import com.specl.utils.JsonUtil;

/**
 * 
 * @author mengxiaonan
 */
@Controller
@Repository("/board")
public class BoardController extends BaseController{

    private static final Logger logger = Logger.getLogger(BoardController.class);

    @Resource
    private BoardService boardService;

    // 添加公告
    @RequestMapping("/addBoard")
    public Map<String, Object> addBoard(String jsonStr, HttpServletRequest request) throws Exception {
        Board board = JsonUtil.jsonToObject(jsonStr, "board", Board.class);
        List<Board> list = JsonUtil.jsonToList(jsonStr, "list", Board.class);
        boardService.addBoard(board, list);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        return modelMap;
    }

    // 编辑公告
    @RequestMapping("/updateBoard")
    public Map<String, Object> updateBoard(String jsonStr, HttpServletRequest request) throws Exception {
        Board board = JsonUtil.jsonToObject(jsonStr, "board", Board.class);

        int id = 0;
        id = boardService.updateBoard(board);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        if (id > 0) {
            modelMap.put("data", false);
        } else {
            modelMap.put("data", true);
        }

        return modelMap;
    }

    // 删除公告
    @RequestMapping(value = "/deleteBoard")
    public Map<String, Object> deleteBoard(String jsonStr, HttpServletRequest request) throws Exception {
        int boardId = JsonUtil.jsonToObject(jsonStr, "boardId", Integer.class);

        int id = 0;
        id = boardService.deleteBoard(boardId);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        if (id > 0) {
            modelMap.put("data", false);
        } else {
            modelMap.put("data", true);
        }

        return modelMap;
    }

    // 查看公告
    @RequestMapping(value = "/getBoard")
    public Map<String, Object> getBoard(String jsonStr, HttpServletRequest request) throws Exception {
        logger.info("jsonStr="+jsonStr);
        int boardId = JsonUtil.jsonToObject(jsonStr, "boardId", Integer.class);

        Board board = new Board();
        board = boardService.getBoard(boardId);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", board);

        return modelMap;
    }

    // 查看列表
    @RequestMapping(value = "/getBoardList")
    public Map<String, Object> getBoardList(String jsonStr, HttpServletRequest request) throws Exception {

        List<Board> boardList = boardService.getBoardList();

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", boardList);

        return modelMap;
    }
}
