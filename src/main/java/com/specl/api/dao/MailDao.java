package com.specl.api.dao;

import com.specl.model.Mail;

public interface MailDao {
	public Integer addMail(Mail mail);
	public Integer updateMail(Mail mail);
	public Integer deleteBoard(int id);
}
