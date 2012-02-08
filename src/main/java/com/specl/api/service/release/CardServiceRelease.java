/**
 * Specl.com Inc.
 * Copyright (c) 2010-2010 All Rights Reserved.
 */
package com.specl.api.service.release;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.specl.api.dao.CardDao;
import com.specl.api.service.CardService;
import com.specl.constant.DataShardTypeEnum;
import com.specl.constants.Constants;
import com.specl.model.BatchCard;
import com.specl.model.BatchCardLog;
import com.specl.model.Card;
import com.specl.model.MemberCard;
import com.specl.model.MemberCardFlow;
import com.specl.status.AccountType;
import com.specl.status.BatchCardLogStatus;
import com.specl.status.BatchCardLogType;
import com.specl.status.CardIsActive;
import com.specl.status.CardStatus;
import com.specl.status.MemberCardStatus;
import com.specl.utils.CardUtil;
import com.specl.utils.DateUtil;
import com.specl.utils.MyMD5Util;

/**
 * 
 * @author mengxiaonan
 */
public class CardServiceRelease  implements CardService {

    @Resource
    private CardDao cardDao;

    // 判断批次是否重复 @author mengxiaonan
    /**
     * @see com.specl.api.service.release.CardService#getCountByNo(com.specl.model.BatchCard)
     */
    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public Integer getCountByNo(BatchCard batchCard) {
        return cardDao.getCountByNo(batchCard);
    }

    // 新增批次 @author mengxiaonan
    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public BatchCard addBatchCard(BatchCard batchCard) {
        return cardDao.addBatchCard(batchCard);
    }

    // 新增卡 @author mengxiaonan
    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public boolean addCard(BatchCard batchCard, String[] cardNo, String[] password) {
        // 判断批次是否重复
        while (true) {
            if (cardDao.getCountByNo(batchCard) == 0) {
                break;
            } else {
                batchCard.setBatchNo(CardUtil.getBatchNo());
            }
        }
        // 新增批次
        batchCard = cardDao.addBatchCard(batchCard);
        // 增加日志
        BatchCardLog bcl = new BatchCardLog();
        bcl.setBatchId(batchCard.getBatchId());
        bcl.setOperator(batchCard.getOperator());
        bcl.setOperatorId(batchCard.getOperatorId());
        bcl.setTime(new Date());
        bcl.setStatus(BatchCardLogStatus.CREATE.getIndex());
        bcl.setType(BatchCardLogType.BATCHCARDLOG.getIndex());
        cardDao.addBatchCardLog(bcl);

        if (batchCard != null) {
            return cardDao.addCard(batchCard, cardNo, password);
        } else {
            return false;
        }
    }

    // 新增日志 @author mengxiaonan
    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public void addBatchCardLog(BatchCardLog batchCardLog) {
        cardDao.addBatchCardLog(batchCardLog);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public Card getCardById(int cardId) {

        return cardDao.getCardById(cardId);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public void updateCardByBatchNo(Card card) {
        cardDao.updateCardByBatchNo(card);

    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public void updateCardById(Card card) {
        cardDao.updateCardById(card);

    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public List<Card> getCarddByBatchNo(String batchNo) {

        return cardDao.getCarddByBatchNo(batchNo);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public BatchCard getBatchCard(String betchNo) {

        return cardDao.getBatchCard(betchNo);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public void updateBatchCard(BatchCard bathCard) {
        cardDao.updateBatchCard(bathCard);

    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public MemberCard getMemberCard(int CardId) {
        // TODO Auto-generated method stub
        return cardDao.getMemberCard(CardId);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public void updateMemberCard(MemberCard memberCard) {
        cardDao.updateMemberCard(memberCard);

    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public void updateMemberCardByCardId(MemberCard memberCard) {
        cardDao.updateMemberCardByCardId(memberCard);

    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public List<MemberCard> getdMemberCardByBatchCard(String batchNo) {

        return cardDao.getdMemberCardByBatchCard(batchNo);
    }

   
    // 获得日志列表 @author mengxiaonan
    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public List<BatchCardLog> getLogList(BatchCardLog batchCardLog) {
        return cardDao.getLogList(batchCardLog);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public List<MemberCard> getMemberCardByMemberId(MemberCard mcard) {
        return cardDao.getMemberCardByMemberId(mcard);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public List getMemberCardFlowByCardId(Map map) {
        return cardDao.getMemberCardFlowByCardId(map);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public MemberCard getMemberCardByCardId(MemberCard mcard) {
        return cardDao.getMemberCardByCardId(mcard);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public List getMemberCarMayUseList(Map map) {
        return cardDao.getMemberCarMayUseList(map);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member,readOrWrite = Constants.WRITE_DB)
    public MemberCard getMemberCardByMemberIdAndCardId(Map map) {
        MemberCard mc = cardDao.getMemberCardByMemberIdAndCardId(map);
        //不再判断卡的使用条件了
//        if (mc != null) {
//            Integer orderMoney = (Integer) map.get("money");
//            if (orderMoney != null) {
//                if (orderMoney < mc.getUsingCondition()) {
//                    mc = null;
//                }
//            }
//
//        }
        return mc;
    }

   

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public void addMemberCardFlow(MemberCardFlow mcf) {
        cardDao.addMemberCardFlow(mcf);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public MemberCard getMemberCardByCarNo(Map map) {
        return cardDao.getMemberCardByCarNo(map);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public BatchCard getCountActive(String batchNo) {
       
        return cardDao.getCountActive(batchNo);
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public void updateCardByIsActive(Card card) {
        cardDao.updateCardByIsActive(card);
        cardDao.updateCardByNonActive(card);
        
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order)
    public void updateMemberCardByIsActive(String batchNo) {
        cardDao.updateMemberCardByIsActive(batchNo);
        
    }

    public CardDao getCardDao() {
        return cardDao;
    }

    public void setCardDao(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    
    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member , readOrWrite = Constants.WRITE_DB) 
    public int getMemberCardUserMoney(int memberId) {
       Map map=new HashMap();
       map.put("memberId", memberId);
       map.put("status", MemberCardStatus.ENABLEUSE.getIndex());
       List list=cardDao.getMemberCarMayUseList(map);
       int mayUseMoney=0;
       if(list!=null && list.size()>0){
           for(int i=0;i<list.size();i++){
               MemberCard mc=(MemberCard)list.get(i);
               mayUseMoney+=mc.getBalanceMoney();
           }
       }
       return mayUseMoney;
    }

    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.member)
    public int getMemberCardMoneyLatestOutTime(int memeberId) {
        Integer money=cardDao.getMemberCardMoneyLatestOutTime(memeberId);
       
        return money;
    }

   //作废卡
    @Transactional(value = "singleTx", dataShardType = DataShardTypeEnum.order,readOrWrite = Constants.WRITE_DB)
    public void invalidCard(BatchCard bc) {
        //作废批次
        cardDao.updateBatchCard(bc);
        //作废单张卡
        Card card = new Card();
        card.setBatchNo(bc.getBatchNo());
        card.setStatus(CardStatus.INVALID.getIndex());
        cardDao.updateCardByBatchNoNoPostpone(card);
    }
   

    public static void main(String[] str){
        Date now=new Date();
        String d1=DateUtil.formatDate(now,"yyyy-MM-dd");
        String d2=d1+" 23:59:59";
        Date n1=DateUtil.stringDate(d2);
        Date et=DateUtil.getNewDate(n1, 12, 3);
        System.out.println("d1--------"+d1);
        System.out.println("d2--------"+d2);
        System.out.println("n1--------"+n1);
        System.out.println("et--------"+et);
    }
}
