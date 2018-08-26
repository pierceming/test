package cn.itcast.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.itcast.domain.User;
import cn.itcast.service.UserService;

@RestController
@RequestMapping("/user")
public class WebController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getOne/{id}")
	public User getOne(@PathVariable Long id) {
		return userService.getOne(id);
	}

	@RequestMapping("/findAll")
	public List<User> findAll() {
		return userService.findAll();
	}

	@PostMapping("/add")
	public void add(@RequestBody User user) {
		userService.add(user);
	}

	@RequestMapping("/delete")
	public void delete(Long id) {
		userService.delete(id);
	}
}
