package cn.itcast.service;

import java.util.List;

import cn.itcast.domain.User;

public interface UserService {
	
	public User getOne(Long id);
	
	public List<User> findAll();
	
	public void add(User user);
	
	public void delete(Long id);
}
