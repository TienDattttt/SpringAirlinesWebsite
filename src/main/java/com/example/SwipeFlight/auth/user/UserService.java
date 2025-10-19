package com.example.SwipeFlight.auth.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void insertUser(User user) throws IllegalArgumentException {
		userRepository.insertUser(user);
	}

	public User getUserByUserName(String userName) throws IllegalArgumentException {
		return userRepository.getUserByUserName(userName);
	}

	public void updateLastSeen(String userName) throws IllegalArgumentException {
		userRepository.updateLastSeen(userName);
	}

	public BindingResult validateUserRegistration(User user, BindingResult result) {
		String userName = user.getUserName();
		String email = user.getEmail();
		String password = user.getPassword();

		// user name
		if (userName.isEmpty())
			result.rejectValue("userName", "error.user", "Tên đăng nhập không được để trống.");
		if (userRepository.getUserByUserName(userName) != null)
			result.rejectValue("userName", "error.user", "Tên người dùng đã được sử dụng.");

		// email
		if (userRepository.getUserByEmail(email) != null)
			result.rejectValue("email", "error.user", "Email đã tồn tại trong hệ thống.");
		if (email.isEmpty())
			result.rejectValue("email", "error.user", "Email không được để trống.");
		else {
			// pattern validity
			String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(email);
			if (!matcher.matches())
				result.rejectValue("email", "error.email", "Email không phù hợp.");
		}

		// password
		if (password.isEmpty())
			result.rejectValue("password", "error.user", "Mật khẩu không được để trống.");

		return result;
	}

	public BindingResult validateUserLogin(User user, BindingResult result) {
		String userName = user.getUserName();
		String password = user.getPassword();

		if (userName.isEmpty())
			result.rejectValue("userName", "error.user", "Trường này là bắt buộc.");
		if (password.isEmpty())
			result.rejectValue("password", "error.user", "Trường này là bắt buộc.");
		if (userRepository.getUserByUsernameAndPassword(userName, password) == null)
			result.reject("error.object", "Tên người dùng hoặc mật khẩu không đúng.");

		return result;
	}
}
