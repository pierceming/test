package cn.itcast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.domain.User;
import cn.itcast.mapper.UserMapper;
import cn.itcast.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getOne(Long id) {
		return userMapper.getOne(id);
	}

	@Override
	public List<User> findAll() {
		return userMapper.findAll();
	}

	@Override
	public void add(User user) {
		userMapper.add(user);
	}

	@Override
	public void delete(Long id) {
		userMapper.delete(id);
	}
	
}
