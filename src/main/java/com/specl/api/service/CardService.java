package com.specl.api.service;

import java.util.List;
import java.util.Map;

import com.specl.model.BatchCard;
import com.specl.model.BatchCardLog;
import com.specl.model.Card;
import com.specl.model.MemberCard;
import com.specl.model.MemberCardFlow;

public interface CardService {

    // 判断批次是否重复
    public abstract Integer getCountByNo(BatchCard batchCard);

    // 新建批次
    public BatchCard addBatchCard(BatchCard batchCard);

    // 新增卡
    public boolean addCard(BatchCard batchCard, String[] cardNo, String[] password);

    // 新增日志
    public void addBatchCardLog(BatchCardLog batchCardLog);

    // 查询卡
    public Card getCardById(int cardId);

    // 更新卡
    public void updateCardByBatchNo(Card card);
    public void updateCardById(Card card);

    // 根据batchNo查询
    public List<Card> getCarddByBatchNo(String batchNo);

    // 根据betchId查询
    public BatchCard getBatchCard(String betchNo);

    // 更新batchCard
    public void updateBatchCard(BatchCard bathCard);

    // 根据card_id查询
    public MemberCard getMemberCard(int CardId);

    // 更新memberCard
    public void updateMemberCard(MemberCard memberCard);
    public void updateMemberCardByCardId(MemberCard memberCard);

    // 根据batchNo查询MemberCard
    public List<MemberCard> getdMemberCardByBatchCard(String batchNo);

  

    // 获得日志列表
    public List<BatchCardLog> getLogList(BatchCardLog batchCardLog);

    public List<MemberCard> getMemberCardByMemberId(MemberCard mcard);
    
    public List getMemberCardFlowByCardId(Map map);
    
    public MemberCard getMemberCardByCardId(MemberCard mcard);
    
    public List getMemberCarMayUseList(Map map);
    public MemberCard getMemberCardByMemberIdAndCardId(Map map);

    public void addMemberCardFlow(MemberCardFlow mcf);
    public MemberCard getMemberCardByCarNo(Map map);

    public  BatchCard getCountActive(String batchNo);

    public  void updateCardByIsActive(Card card);

    public  void updateMemberCardByIsActive(String batchNo);
    
  
    //获得用户礼品卡的可用金额 
    public int getMemberCardUserMoney(int memberId);
    
    public int getMemberCardMoneyLatestOutTime(int memeberId);
    
    public void invalidCard(BatchCard bc);

}
