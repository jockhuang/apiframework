package com.specl.api.dao;

import java.util.List;
import java.util.Map;

import com.specl.model.BatchCard;
import com.specl.model.BatchCardLog;
import com.specl.model.Card;
import com.specl.model.MemberCard;
import com.specl.model.MemberCardFlow;

public interface CardDao {

    // 判断批次是否重复
    public abstract Integer getCountByNo(BatchCard batchCard);

    // 添加批次
    public BatchCard addBatchCard(BatchCard batchCard);

    // 新增卡
    public boolean addCard(BatchCard batchCard, String[] cardNo, String[] password);

    // 新增日志
    public void addBatchCardLog(BatchCardLog batchCardLog);

    // 根据carID查询卡
    public Card getCardById(int cardId);

    // 更新卡
    public Card updateCardByBatchNo(Card card);

    // 根据batchNo查询
    public List<Card> getCarddByBatchNo(String batchNo);

    // 根据betchId查询
    public BatchCard getBatchCard(String betchNo);

    // 更新batchCard
    public void updateBatchCard(BatchCard bathCard);

    // 根据card_id查询MemberCard
    public MemberCard getMemberCard(int CardId);

    // 更新memberCard
    public void updateMemberCard(MemberCard memberCard);

    // 更新memberCardge根据CardId
    public void updateMemberCardByCardId(MemberCard memberCard);

    // 根据batchNo查询MemberCard
    public List<MemberCard> getdMemberCardByBatchCard(String batchNo);

    // 激活时，修改card表中的相关信息
    public Card updateCardById(Card card);

    // 激动时，修改批次中的激活和未激活的数量
    public BatchCard updateBatchCardByNum(BatchCard batchCard);

    // 激活时，向member_card表中插入一条数据
    public MemberCard addMemberCard(MemberCard memberCard);

    // 获得日志列表
    public List<BatchCardLog> getLogList(BatchCardLog batchCardLog);

    // 根据卡号查询卡片信息
    public Card getCardByCardNo(Card card);
    //查询会员的所有卡片
    public List<MemberCard> getMemberCardByMemberId(MemberCard mcard);
    //查询卡片消费记录
    public List getMemberCardFlowByCardId(Map map);
    //根据cardId\memberId查询卡片信息
    public MemberCard getMemberCardByCardId(MemberCard mcard);
    //查询会员可用的卡片
    public List getMemberCarMayUseList(Map map);
    //确认一张卡片是否可用 memberId\cardId
    public MemberCard getMemberCardByMemberIdAndCardId(Map map);
    
    public int updateMemberCardMoney(Map map);
    
    public void addMemberCardFlow(MemberCardFlow mcf);
    
    public MemberCard getMemberCardByCarNo(Map map);

    public  BatchCard getCountActive(String batchNo);

    public  void updateCardByIsActive(Card card);

    public  void updateMemberCardByIsActive(String batchNo);
    
    public void updateCardByNonActive(Card card);
    
    public List getMemberCardByMoney(Map map);
    //返回近一个月到期的卡的金额
    public int getMemberCardMoneyLatestOutTime(int memberId);
    
    public void updateCardByBatchNoNoPostpone(Card card);
}
