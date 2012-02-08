/**
 * Specl.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.specl.api.controller.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.specl.api.controller.BaseController;
import com.specl.api.service.CardService;
import com.specl.model.BatchCard;
import com.specl.model.BatchCardLog;
import com.specl.model.Card;
import com.specl.model.MemberCard;
import com.specl.model.MemberCardFlow;
import com.specl.utils.JsonUtil;

/**
 * 
 * @author mengxiaonan
 */
@Controller
@Repository("/card")
public class CardController extends BaseController {

    private static final Logger logger = Logger.getLogger(CardController.class);

    @Resource
    private CardService cardService;
    
    

    // 判断批次是否重复
    @RequestMapping(value = "/getCountByNo")
    @ResponseBody
    public Map<String, Object> getCountByNo(String jsonStr, HttpServletRequest request) throws Exception {
        BatchCard batchCard = new BatchCard();
        String batchNo = JsonUtil.jsonToObject(jsonStr, "batchNo", String.class);
        batchCard.setBatchNo(batchNo);

        int count = cardService.getCountByNo(batchCard);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        if (count > 0) {
            modelMap.put("data", false);
        } else {
            modelMap.put("data", true);
        }

        return modelMap;
    }

    // 新增批次
    @RequestMapping(value = "/addBatchCard")
    @ResponseBody
    public Map<String, Object> addBatchCard(String jsonStr, HttpServletRequest request) throws Exception {
        BatchCard batchCard = JsonUtil.jsonToObject(jsonStr, "BatchCard", BatchCard.class);

        batchCard = cardService.addBatchCard(batchCard);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        if (batchCard != null) {
            modelMap.put("data", true);
        } else {
            modelMap.put("data", false);
        }

        return modelMap;
    }

    // 新增卡
    @RequestMapping(value = "/addCard")
    @ResponseBody
    public Map<String, Object> addCard(String jsonStr, HttpServletRequest request) throws Exception {
        BatchCard batchCard = JsonUtil.jsonToObject(jsonStr, "BatchCard", BatchCard.class);
        String[] cardNo = JsonUtil.jsonToArray(jsonStr, "cardNo", String.class);
        String[] password = JsonUtil.jsonToArray(jsonStr, "password", String.class);

        boolean flag = cardService.addCard(batchCard, cardNo, password);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", flag);

        return modelMap;
    }

    // 新增日志
    @RequestMapping(value = "/addBatchCardLog")
    @ResponseBody
    public Map<String, Object> addBatchCardLog(String jsonStr, HttpServletRequest request) throws Exception {
        BatchCardLog batchCardLog = JsonUtil.jsonToObject(jsonStr, "BatchCardLog", BatchCardLog.class);

        cardService.addBatchCardLog(batchCardLog);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);

        return modelMap;
    }

    // 根据cardId查询
    @RequestMapping(value = "/getCardByCardId")
    @ResponseBody
    public Map<String, Object> getCardByCardId(String jsonStr, HttpServletRequest request) throws Exception {
        int cardId = JsonUtil.jsonToObject(jsonStr, "cardId", Integer.class);
        Card card = cardService.getCardById(cardId);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", card);
        return modelMap;
    }

    // 根据batchNo查询
    @RequestMapping(value = "/getCardByBatchNo")
    @ResponseBody
    public Map<String, Object> getCardByBatchNo(String jsonStr, HttpServletRequest request) throws Exception {
        String batchNo = JsonUtil.jsonToObject(jsonStr, "batchNo", String.class);
        List<Card> batchlist = cardService.getCarddByBatchNo(batchNo);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", batchlist);
        return modelMap;
    }

    // 根据batchNo更新card
    @RequestMapping(value = "/updateCardByBatchNo")
    @ResponseBody
    public Map<String, Object> updateCardByBatchNo(String jsonStr, HttpServletRequest request) throws Exception {
        Card card = JsonUtil.jsonToObject(jsonStr, "card", Card.class);
        cardService.updateCardByBatchNo(card);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 0);
        return modelMap;
    }

    // 根据batchId更新card
    @RequestMapping(value = "/updateCardById")
    @ResponseBody
    public Map<String, Object> updateCardById(String jsonStr, HttpServletRequest request) throws Exception {
        Card card = JsonUtil.jsonToObject(jsonStr, "card", Card.class);
        cardService.updateCardById(card);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 0);
        return modelMap;
    }

    // 根据bathCardId查询
    @RequestMapping(value = "/getBatchCard")
    @ResponseBody
    public Map<String, Object> getBatchCard(String jsonStr, HttpServletRequest request) throws Exception {
        String betchNo = JsonUtil.jsonToObject(jsonStr, "betchNo", String.class);
        BatchCard bc = cardService.getBatchCard(betchNo);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", bc);
        return modelMap;
    }

    // 更新batchCard
    @RequestMapping(value = "/updateBatchCard")
    @ResponseBody
    public Map<String, Object> updateBatchCard(String jsonStr, HttpServletRequest request) throws Exception {
        BatchCard bathCard = JsonUtil.jsonToObject(jsonStr, "batchCard", BatchCard.class);
        cardService.updateBatchCard(bathCard);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 0);
        return modelMap;
    }

    // 根据CardId查询MemberCard
    @RequestMapping(value = "/getMemberCard")
    @ResponseBody
    public Map<String, Object> getMemberCard(String jsonStr, HttpServletRequest request) throws Exception {
        Integer CardId = JsonUtil.jsonToObject(jsonStr, "CardId", Integer.class);
        MemberCard mc = cardService.getMemberCard(CardId);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", mc);
        return modelMap;
    }

    // 根据BatchCardNo查询MemberCard
    @RequestMapping(value = "/getdMemberCardByBatchCard")
    @ResponseBody
    public Map<String, Object> getdMemberCardByBatchCard(String jsonStr, HttpServletRequest request) throws Exception {
        String batchNo = JsonUtil.jsonToObject(jsonStr, "batchNo", String.class);
        List<MemberCard> list = cardService.getdMemberCardByBatchCard(batchNo);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", list);
        return modelMap;
    }

    // 更新MemberCard
    @RequestMapping(value = "/updateMemberCardByCardId")
    @ResponseBody
    public Map<String, Object> updateMemberCardByCardId(String jsonStr, HttpServletRequest request) throws Exception {
        MemberCard mc = JsonUtil.jsonToObject(jsonStr, "memberCard", MemberCard.class);
        cardService.updateMemberCardByCardId(mc);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 0);
        return modelMap;
    }

    // 更新MemberCard
    @RequestMapping(value = "/updateMemberCard")
    @ResponseBody
    public Map<String, Object> updateMemberCard(String jsonStr, HttpServletRequest request) throws Exception {
        MemberCard mc = JsonUtil.jsonToObject(jsonStr, "memberCard", MemberCard.class);
        cardService.updateMemberCard(mc);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 0);
        return modelMap;
    }

    

    // 获得日志列表 @author mengxiaonan
    /*
     * 传入BatchCardLog对象，该对象包含几个值 batch_id:批次号 或者 卡号 type：日志类型，是批次日志 还是卡日志
     */
    @RequestMapping(value = "/getLogList")
    @ResponseBody
    public Map<String, Object> getLogList(String jsonStr, HttpServletRequest request) throws Exception {
        BatchCardLog bcl = JsonUtil.jsonToObject(jsonStr, "BatchCardLog", BatchCardLog.class);
        List<BatchCardLog> bclList = cardService.getLogList(bcl);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", bclList);
        return modelMap;
    }
    
    
    //获得会员所拥有的会员卡
    /**
     * memberId
     */
    @RequestMapping(value = "/getMemberCardByMemberId")
    @ResponseBody
    public Map<String, Object> getMemberCardByMemberId(String jsonStr, HttpServletRequest request) throws Exception {
        MemberCard mc = JsonUtil.jsonToObject(jsonStr, MemberCard.class);
        List<MemberCard> mcList = cardService.getMemberCardByMemberId(mc);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", mcList);
        return modelMap;
}
    
    
    @RequestMapping(value = "/getMemberCardFlowByCardId")
    @ResponseBody
    public Map<String, Object> getMemberCardFlowByCardId(String jsonStr, HttpServletRequest request) throws Exception {
        Map mc = JsonUtil.jsonToObject(jsonStr, Map.class);
        List mcList = cardService.getMemberCardFlowByCardId(mc);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", mcList);
        return modelMap;
    }
    
    @RequestMapping(value = "/getMemberCardByCardId")
    @ResponseBody
    public Map<String, Object> getMemberCardByCardId(String jsonStr, HttpServletRequest request) throws Exception {
        MemberCard mc = JsonUtil.jsonToObject(jsonStr, MemberCard.class);
        mc = cardService.getMemberCardByCardId(mc);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", mc);
        return modelMap;
    }
    


    @RequestMapping(value = "/getMemberCardByMemberIdAndCardId")
    @ResponseBody
    public Map<String, Object> getMemberCardByMemberIdAndCardId(String jsonStr, HttpServletRequest request) throws Exception {
        Map mc = JsonUtil.jsonToObject(jsonStr, Map.class);
        MemberCard memberCard = cardService.getMemberCardByMemberIdAndCardId(mc);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", memberCard);
        return modelMap;
    }

    @RequestMapping(value = "/getMemberCarMayUseList")
    @ResponseBody
    public Map<String, Object> getMemberCarMayUseList(String jsonStr, HttpServletRequest request) throws Exception {
        Map mc = JsonUtil.jsonToObject(jsonStr, Map.class);
        List list = cardService.getMemberCarMayUseList(mc);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", list);
        return modelMap;
    }
    
   
    
    @RequestMapping(value = "/addCardFlow")
    @ResponseBody
    public Map<String, Object> addCardFlow(String jsonStr, HttpServletRequest request) throws Exception {
        MemberCardFlow mcf = JsonUtil.jsonToObject(jsonStr, MemberCardFlow.class);
        cardService.addMemberCardFlow(mcf);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 1);
        return modelMap;
    }
    
    @RequestMapping(value = "/getMemberCardByCarNo")
    @ResponseBody
    public Map<String, Object> getMemberCardByCarNo(String jsonStr, HttpServletRequest request) throws Exception {
        Map mc = JsonUtil.jsonToObject(jsonStr, Map.class);
        MemberCard memberCard = cardService.getMemberCardByCarNo(mc);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", memberCard);
        return modelMap;
    }
    
    @RequestMapping(value = "/getCountActive")
    @ResponseBody
    public Map<String, Object> getCountActive(String jsonStr, HttpServletRequest request) throws Exception {
        String batchNo  = JsonUtil.jsonToObject(jsonStr, "batchNo", String.class);
        BatchCard bc = cardService.getCountActive(batchNo);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", bc);
        return modelMap;
    }
    //如果已经激活 而且未过期 设置状态为可用 
    @RequestMapping(value = "/updateCardByIsActive")
    @ResponseBody
    public Map<String, Object> updateCardByIsActive(String jsonStr, HttpServletRequest request) throws Exception {
        Card card  = JsonUtil.jsonToObject(jsonStr, "card", Card.class);
        cardService.updateCardByIsActive(card);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 0);
        return modelMap;
    }
    
    //如果已经激活 而且未过期 设置状态为可用 
    @RequestMapping(value = "/updateMemberCardByIsActive")
    @ResponseBody
    public Map<String, Object> updateMemberCardByIsActive(String jsonStr, HttpServletRequest request) throws Exception {
        String batchNo  = JsonUtil.jsonToObject(jsonStr, "batchNo", String.class);
        cardService.updateMemberCardByIsActive(batchNo);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 0);
        return modelMap;
    }
    
  //查询用户礼品卡金额
    @RequestMapping(value = "/getMemberCardUserMoney")
    @ResponseBody
    public Map<String, Object> getMemberCardUserMoney(String jsonStr, HttpServletRequest request) throws Exception {
        int memberId = JsonUtil.jsonToObject(jsonStr, "memberId", Integer.class);
        Map map = new HashMap();
        int userMoney = cardService.getMemberCardUserMoney(memberId);
        map.put("userMoney", userMoney);
        int latestMoney = cardService.getMemberCardMoneyLatestOutTime(memberId);
        map.put("latestMoney", latestMoney);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", map);
        return modelMap;
    }
    
    //作废
    @RequestMapping(value = "/invalidCard")
    @ResponseBody
    public Map<String, Object> invalidCard(String jsonStr, HttpServletRequest request) throws Exception {
        
        BatchCard bc  = JsonUtil.jsonToObject(jsonStr, "batchCard", BatchCard.class);
        
        cardService.invalidCard(bc);
        
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("result", 1);
        modelMap.put("data", 0);
        return modelMap;
    }
    
}
