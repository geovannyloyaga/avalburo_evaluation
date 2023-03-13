package com.avalburo.backend.apirest.interfaces;

import java.util.List;

import com.avalburo.backend.apirest.dto.RequestUserDto;
import com.avalburo.backend.apirest.entities.User;

public interface IUserService {

	public List<User> findAll();
	
	public User findById(int userId);
	
	public User save(RequestUserDto user);
}
