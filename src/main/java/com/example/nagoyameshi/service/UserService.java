package com.example.nagoyameshi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Role;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.PasswordEditForm;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.form.UserEditForm;
import com.example.nagoyameshi.repository.RoleRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
//	新規ユーザー登録
	@Transactional
	public User create(SignupForm signupForm) {
		User user = new User();
		Role role = roleRepository.findByName("ROLE_GENERAL");
		
		user.setName(signupForm.getName());
		user.setFurigana(signupForm.getFurigana());
		user.setPostalCode(signupForm.getPostalCode());
		user.setAddress(signupForm.getAddress());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.setRole(role);
		user.setEnabled(false);

		return userRepository.save(user);
	}
	
//	ユーザー情報編集
	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getId());
		
		user.setName(userEditForm.getName());
		user.setFurigana(userEditForm.getFurigana());
		user.setPostalCode(userEditForm.getPostalCode());
		user.setAddress(userEditForm.getAddress());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setEmail(userEditForm.getEmail());
		
		userRepository.save(user);
	}
	
//	パスワード変更
	@Transactional
	public void newpassword(PasswordEditForm passwordEditForm, User user) {
	    user.setPassword(passwordEncoder.encode(passwordEditForm.getPassword()));
	    userRepository.save(user);
	}
		
//	StripeCustomerIdをDBに保存する。
	@Transactional
	public void saveStripeCustomerId(User user, String customerId) {
		user.setStripeCustomerId(customerId);
		userRepository.save(user);
	}
	
//	Roleを更新する
	public void updateRole(User user, String role) {
		Role roleName = roleRepository.findByName(role);
		user.setRole(roleName);
		userRepository.save(user);
	}
	
//	認証情報のロールを更新する
	public void refreshAuthenticationByRole(String newRole) {
//		現在の認証情報を取得する
		Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
		
//		新しい認証情報を作成する
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		simpleGrantedAuthorities.add(new SimpleGrantedAuthority(newRole));
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(currentAuthentication.getPrincipal(), currentAuthentication.getCredentials(), simpleGrantedAuthorities);

//      認証情報を更新する
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
	}
	
//	メールアドレスが登録済かどうかをチェックする
	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	
//	パスワードをパスワード（確認用）の入力値が一致するかどうかをチェックする
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}
	
//	ユーザーを有効にする
    @Transactional
    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
    
//  メールアドレスが変更されたかどうかチェックする
    public boolean isEmailChanged(UserEditForm userEditForm) {
    	User currentUser = userRepository.getReferenceById(userEditForm.getId());
    	return !userEditForm.getEmail().equals(currentUser.getEmail());
    }
}
