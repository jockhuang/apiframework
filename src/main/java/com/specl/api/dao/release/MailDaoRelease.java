package com.specl.api.dao.release;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.specl.api.dao.MailDao;
import com.specl.model.Board;
import com.specl.model.Mail;


public class MailDaoRelease extends SqlMapClientDaoSupport implements MailDao{
	public Integer addMail(Mail mail){
        int id = (Integer)getSqlMapClientTemplate().insert("mail.addMail" , mail);
        if(id>0){
            return id;
        }else{
            return 0;
        }
	}
	
	
     public Integer updateMail(Mail mail){
        int affectRow = getSqlMapClientTemplate().update("mail.updateMail",mail);
        
        return affectRow;
    }
    
    public Integer deleteBoard(int id){
        int affectRows = getSqlMapClientTemplate().delete("mail.deleteById",id);
        
        return affectRows;
    }

}
