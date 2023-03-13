package com.avalburo.backend.apirest.business.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avalburo.backend.apirest.dao.MessageDao;
import com.avalburo.backend.apirest.dao.UserDao;
import com.avalburo.backend.apirest.dto.RequestMessageDto;
import com.avalburo.backend.apirest.entities.Message;
import com.avalburo.backend.apirest.interfaces.IMessageService;

@Service
public class MessageService implements IMessageService {

	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public List<Message> findAll(int userId) {
		return (List<Message>)messageDao.findAll(userId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Message findById(int messageId) {
		return (Message)messageDao.findById(messageId);
	}

	@Override
	public Message save(RequestMessageDto requestMessageDto) {
		Message messageCreate = new Message();
		messageCreate.setComment(requestMessageDto.getComment());
		messageCreate.setPoints(requestMessageDto.getPoint());
		messageCreate.setUser(userDao.findById(requestMessageDto.getUserId()));
		return (Message)messageDao.save(messageCreate);
	}

	@Override
	public Message saveResponse(int messageId, RequestMessageDto requestResponseDto) {
		Message messageCreate = new Message();
		messageCreate.setComment(requestResponseDto.getComment());
		messageCreate.setPoints(requestResponseDto.getPoint());
		messageCreate.setUser(userDao.findById(requestResponseDto.getUserId()));
		messageCreate.setResponseId(requestResponseDto.getResponseId());
		return (Message)messageDao.saveResponse(messageId, messageCreate);
	}

}
