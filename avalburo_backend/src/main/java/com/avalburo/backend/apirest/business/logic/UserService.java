package com.avalburo.backend.apirest.business.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avalburo.backend.apirest.dao.UserDao;
import com.avalburo.backend.apirest.dto.RequestUserDto;
import com.avalburo.backend.apirest.entities.User;
import com.avalburo.backend.apirest.interfaces.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>)userDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findById(int userId) {
		return (User)userDao.findById(userId);
	}

	@Override
	public User save(RequestUserDto requestUserDto) {
		User userCreate = new User();
		userCreate.setFullName(requestUserDto.getFullName());
		userCreate.setPhotoUrl(requestUserDto.getPhotoUrl());
		return (User)userDao.save(userCreate);
	}
}
