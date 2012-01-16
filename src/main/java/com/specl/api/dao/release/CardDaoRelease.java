/**
 * Specl.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.specl.api.dao.release;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.specl.api.dao.CardDao;
import com.specl.model.BatchCard;
import com.specl.model.BatchCardLog;
import com.specl.model.Card;
import com.specl.model.MemberCard;
import com.specl.model.MemberCardFlow;
import com.specl.status.CardIsActive;
import com.specl.status.CardStatus;
import com.specl.status.MemberCardStatus;
import com.specl.utils.DateUtil;
import com.specl.utils.MyMD5Util;

/**
 * 
 * @author mengxiaonan
 */
public class CardDaoRelease extends SqlMapClientDaoSupport implements CardDao {

    // 判断批次是否重复
    /**
     * @see com.specl.api.dao.release.CardDao#getCountByNo(com.specl.model.BatchCard)
     */
    public Integer getCountByNo(BatchCard batchCard) {
        int count = (Integer) getSqlMapClientTemplate().queryForObject("BatchCard.selectCountByNo", batchCard);
        return count;
    }

    // 添加批次
    public BatchCard addBatchCard(BatchCard batchCard) {
        int batchId = (Integer) getSqlMapClientTemplate().insert("BatchCard.addBatchCard", batchCard);
        if (batchId > 0) {
            batchCard.setBatchId(batchId);
            batchCard = (BatchCard) getSqlMapClientTemplate().queryForObject("BatchCard.getBatchCardById", batchCard);
        }

        return batchCard;
    }

    // 新增卡
    public boolean addCard(BatchCard batchCard, String[] cardNo, String[] password) {
        boolean flag = false;

        for (int i = 0; i < cardNo.length; i++) {
            Card card = new Card();
            card.setBatchNo(batchCard.getBatchNo());
            card.setCardNo(batchCard.getBatchNo() + cardNo[i]);
            card.setPassword(MyMD5Util.MD5(password[i]));
            card.setCreateTime(batchCard.getCreateTime());
            card.setMoney(Long.valueOf(batchCard.getMoney()));
            card.setIsActive(CardIsActive.UNACTIVE.getIndex());
            card.setEndTime(batchCard.getEndTime());
            card.setCardType(batchCard.getCardType());
            card.setStatus(CardStatus.NOTACITVE.getIndex());//新增卡片状态未激活
            card.setUsingCondition(batchCard.getUsingCondition());
            try {
                int cardId = (Integer) getSqlMapClientTemplate().insert("Card.addCard", card);
                if (cardId > 0) {
                    flag = true;
                }
            } catch (Exception e) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    // 添加日志
    public void addBatchCardLog(BatchCardLog batchCardLog) {
        getSqlMapClientTemplate().insert("BatchCardLog.addBatchCardLog", batchCardLog);
    }

    // 根据卡Id查询
    public Card getCardById(int cardId) {
        Object obj = getSqlMapClientTemplate().queryForObject("Card.getCard", cardId);
        Card card = null;
        if (obj != null) {
            if (obj instanceof Card) {
                card = (Card) obj;
            }
        }
        return card;
    }

    // 更新卡信息

    public Card updateCardByBatchNo(Card card) {
       int affectRow = getSqlMapClientTemplate().update("Card.updateCardByBatchNo", card);
       if(affectRow == 1){
           card = (Card) getSqlMapClientTemplate().queryForObject("Card.getCard",card.getCardId());
       }

       return card;
    }

    // 根据批次号查询单卡
    public List<Card> getCarddByBatchNo(String batchNo) {
        List<Card> listCard = getSqlMapClientTemplate().queryForList("Card.getCardBybatchNo", batchNo);
        return listCard;
    }

    public BatchCard getBatchCard(String betchNo) {

        Object obj = getSqlMapClientTemplate().queryForObject("BatchCard.getBatchCard", betchNo);
        BatchCard bcard = null;
        if (obj != null) {
            if (obj instanceof BatchCard) {
                bcard = (BatchCard) obj;
            }
        }
        return bcard;
    }

    public MemberCard getMemberCard(int CardId) {
        Object obj = getSqlMapClientTemplate().queryForObject("MemberCard.getMemberByCardId",CardId);
        MemberCard mc = null;
        if (obj != null) {
            if (obj instanceof MemberCard) {
                mc = (MemberCard) obj;
            }
        }

        return mc;
    }

    public void updateBatchCard(BatchCard bathCard) {
        getSqlMapClientTemplate().update("BatchCard.updateBatchCard", bathCard);

    }

    public void updateMemberCard(MemberCard memberCard) {
        getSqlMapClientTemplate().update("MemberCard.updateMemberCard", memberCard);

    }

    public void updateMemberCardByCardId(MemberCard memberCard) {
        getSqlMapClientTemplate().update("MemberCard.updateMemberCardByCardId", memberCard);

    }

    public List<MemberCard> getdMemberCardByBatchCard(String batchNo) {

        return getSqlMapClientTemplate().queryForList("MemberCard.getMemberBybatchNo", batchNo);
    }

    // 激活时，修改card表中的相关信息 @author mengxiaonan
    public Card updateCardById(Card card) {
        int affectRow = getSqlMapClientTemplate().update("Card.updateCardById", card);
        if(affectRow == 1){
            card = (Card) getSqlMapClientTemplate().queryForObject("Card.getCard",card.getCardId());
    }

        return card;
    }

    // 激动时，修改批次中的激活和未激活的数量 @author mengxiaonan
    public BatchCard updateBatchCardByNum(BatchCard batchCard) {
        int affectRow = getSqlMapClientTemplate().update("BatchCard.updateBatchCardNum", batchCard);
        if (affectRow == 1) {
            batchCard = (BatchCard) getSqlMapClientTemplate().queryForObject("BatchCard.getBatchCardById", batchCard);
        }

        return batchCard;
    }

    // 激活时，向member_card表中插入一条数据 @author mengxiaonan
    public MemberCard addMemberCard(MemberCard memberCard) {
        getSqlMapClientTemplate().insert("MemberCard.addMemberCard", memberCard);
            memberCard = (MemberCard) getSqlMapClientTemplate().queryForObject("MemberCard.getMemberById", memberCard);

        return memberCard;
    }

    // 获得日志列表 @author mengxiaonan
    public List<BatchCardLog> getLogList(BatchCardLog batchCardLog) {
        return getSqlMapClientTemplate().queryForList("BatchCardLog.getLogList", batchCardLog);
    }

    // 根据卡号查询卡片信息
    public Card getCardByCardNo(Card card) {
        return (Card) getSqlMapClientTemplate().queryForObject("Card.getCardByCardNo", card);
    }

    @Override
    public List<MemberCard> getMemberCardByMemberId(MemberCard mcard) {
       return getSqlMapClientTemplate().queryForList("MemberCard.getMemberCardByMemberId", mcard);
}

    @Override
    public List getMemberCardFlowByCardId(Map map) {
       return getSqlMapClientTemplate().queryForList("memberCardFlow.getMemberCardFlowByCardId", map);
    }

    @Override
    public MemberCard getMemberCardByCardId(MemberCard mcard) {
       return (MemberCard)getSqlMapClientTemplate().queryForObject("MemberCard.getMemberCardByCardId", mcard);
    }

    @Override
    public List getMemberCarMayUseList(Map map) {
        map.put("status", MemberCardStatus.ENABLEUSE.getIndex());
        return getSqlMapClientTemplate().queryForList("MemberCard.getMemberCarMayUseList", map);
    }

    @Override
    public MemberCard getMemberCardByMemberIdAndCardId(Map map) {
        map.put("status", MemberCardStatus.ENABLEUSE.getIndex());
        return (MemberCard)getSqlMapClientTemplate().queryForObject("MemberCard.getMemberCardByMemberIdAndCardId", map);
    }

    @Override
    public int updateMemberCardMoney(Map map) {
        return getSqlMapClientTemplate().update("MemberCard.updateMemberCardMoney", map);
    }

    @Override
    public void addMemberCardFlow(MemberCardFlow mcf) {
        getSqlMapClientTemplate().insert("memberCardFlow.insert", mcf);
    }

    @Override
    public MemberCard getMemberCardByCarNo(Map map) {
        return (MemberCard)getSqlMapClientTemplate().queryForObject("MemberCard.getMemberCardByCarNo", "map");
    }

    public BatchCard getCountActive(String batchNo) {
        // TODO Auto-generated method stub
        return (BatchCard) getSqlMapClientTemplate().queryForObject("BatchCard.getCountActive",batchNo);
    }

    @Override
    public void updateCardByIsActive(Card card) {
        getSqlMapClientTemplate().update("Card.updateCardByIsActive",card);
        
    }

    @Override
    public void updateMemberCardByIsActive(String batchNo) {
        Map map=new HashMap();
        map.put("batchNo", batchNo);
        map.put("status", MemberCardStatus.ENABLEUSE.getIndex());
       getSqlMapClientTemplate().update("MemberCard.updateMemberCardByIsActive",batchNo);
        
    }

   
    public void updateCardByNonActive(Card card) {
        getSqlMapClientTemplate().update("Card.updateCardByNonActive",card);
        
    }

    @Override
    public List getMemberCardByMoney(Map map) {
        return getSqlMapClientTemplate().queryForList("MemberCard.getMemberCardByMoney", map);
    }

    @Override
    public int getMemberCardMoneyLatestOutTime(int memberId) {
        Map map=new HashMap();
        map.put("status", MemberCardStatus.ENABLEUSE.getIndex());
        map.put("memberId", memberId);
        Date now=new Date();
        Date s=DateUtil.getSpecDate(now, -30);
        String endTime=DateUtil.formatDate(s, "yyyy-MM-dd hh:mm:ss");//"2011-04-29 00:00:00"
        map.put("endTime",endTime );
        Integer result=(Integer)getSqlMapClientTemplate().queryForObject("MemberCard.getMemberCardMoneyLatestOutTime", map);
        if(result==null){
            result=0;
        }
        return result;
    }

    public void updateCardByBatchNoNoPostpone(Card card){
        getSqlMapClientTemplate().update("Card.updateCardByBatchNoNoPostpone" , card);
    }
}
