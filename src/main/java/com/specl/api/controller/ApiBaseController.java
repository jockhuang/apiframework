package com.specl.api.controller;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.specl.api.codec.Request;
import com.specl.api.codec.Result;
import com.specl.api.controller.board.BoardController;
import com.specl.api.server.RunMain;

import com.specl.utils.JsonUtil;

import com.specl.model.Board;


public class ApiBaseController extends AbstractController {
    private static final Logger logger = Logger.getLogger(ApiBaseController.class);
    public ApiBaseController(IoSession session) {
        super(session);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Result processRequest(Request data) {
        // TODO Auto-generated method stub
        logger.debug(data.getMethod());

        String method = data.getMethod();
        String servicename = method.substring(0,method.lastIndexOf("/"));
        logger.debug("request:service->"+servicename+";method->"+method);
        BaseController service = (BaseController) RunMain.getContext().getBean(servicename);
        
        Object param = data.getParam();
        logger.debug(param);
        Result r = new Result();
        try {
            Method m = RunMain.getMethod(method);
            logger.debug("method:"+m);

            String json = JsonUtil.toJson(m.invoke(service, param,null));
            r.setJson(json);

//            Object obj = m.invoke(service, param,null);
//            HashMap<String,Object> map =(HashMap<String,Object>)obj;
//            Board board = (Board)map.get("data");
//            logger.info("boardId="+board.getBoardId()+",title="+board.getTitle()+",url="+board.getUrl());

        } catch (Exception e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("result", 0);
            map.put("errorCode", 0);
            map.put("errorMessage", e.getCause().getMessage());
            try {
                r.setJson(JsonUtil.toJson(map));
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
           
        }

        logger.debug(r.getJson());
        return r;

    }

   
}
