package com.mikeyeom.memo.user.service;

import org.springframework.stereotype.Service;

import com.mikeyeom.memo.commom.MD5HashingEncoder;
import com.mikeyeom.memo.domain.User;
import com.mikeyeom.memo.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
//	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public boolean addUser(
			String loginId
			, String password
			, String name
			, String email) {
		
		String encodingPassword = MD5HashingEncoder.encode(password);
		
		int count = userRepository.insertUser(loginId, encodingPassword, name, email);
		
		if(count == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public User getUser(String loginId, String password) {
		
		String encodingPassword = MD5HashingEncoder .encode(password);
		
		return userRepository.selectUser(loginId, encodingPassword);
	}
}