package com.avalburo.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avalburo.backend.apirest.dto.RequestMessageDto;
import com.avalburo.backend.apirest.dto.ResponseDto;
import com.avalburo.backend.apirest.dto.ResponseListDto;
import com.avalburo.backend.apirest.entities.Message;
import com.avalburo.backend.apirest.interfaces.IMessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageRestController {

	private String messageFile = ", por favor revise el archivo *.json de almacenamiento";
	
	@Autowired
	private IMessageService messageService;
	
	@GetMapping("/{userId}/findAll")
	public ResponseListDto<Message> findAll(@PathVariable int userId){
		try {
			List<Message> usersFound = messageService.findAll(userId);
			return new ResponseListDto<Message>(200, null, usersFound, usersFound.size());
		} catch (Exception e) {
			return new ResponseListDto<Message>(409, "Error para obtener lista de usuarios".concat(messageFile), null, 0);
		}
	}

	@PostMapping("/save")
	public ResponseDto<Message> save(@RequestBody RequestMessageDto requestMessage){
		try {
			Message messagesCreated = messageService.save(requestMessage);
			return new ResponseDto<Message>(200, null, messagesCreated);
		} catch (Exception e) {
			return new ResponseDto<Message>(409, "Error para obtener lista de mensajes del foro".concat(messageFile), null);
		}
	}

	@PostMapping("/{messageId}/saveResponse")
	public ResponseDto<Message> saveResponse(@PathVariable int messageId, @RequestBody RequestMessageDto requestMessage){
		try {
			Message messagesCreated = messageService.saveResponse(messageId, requestMessage);
			return new ResponseDto<Message>(200, null, messagesCreated);
		} catch (Exception e) {
			return new ResponseDto<Message>(409, "Error para obtener lista de mensajes del foro".concat(messageFile), null);
		}
	}
}
