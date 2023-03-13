package com.avalburo.backend.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avalburo.backend.apirest.dto.RequestUserDto;
import com.avalburo.backend.apirest.dto.ResponseDto;
import com.avalburo.backend.apirest.dto.ResponseListDto;
import com.avalburo.backend.apirest.entities.User;
import com.avalburo.backend.apirest.interfaces.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private String messageFile = ", por favor revise el archivo *.json de almacenamiento";
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("/findAll")
	public ResponseListDto<User> findAll(){
		try {
			List<User> usersFound = userService.findAll();
			return new ResponseListDto<User>(200, null, usersFound, usersFound.size());
		} catch (Exception e) {
			return new ResponseListDto<User>(409, "Error para obtener lista de usuarios".concat(messageFile), null, 0);
		}
	}

	@PostMapping("/save")
	public ResponseDto<User> save(@RequestBody RequestUserDto requestUser){
		try {
			User usersCreated = userService.save(requestUser);
			return new ResponseDto<User>(200, null, usersCreated);
		} catch (Exception e) {
			return new ResponseDto<User>(409, "Error para obtener lista de usuarios".concat(messageFile), null);
		}
	}
}
