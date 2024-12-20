package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.event.PasswordResetPublisher;
import com.example.nagoyameshi.form.PasswordEditForm;
import com.example.nagoyameshi.form.PasswordResetSendForm;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.service.UserService;
import com.example.nagoyameshi.service.VerificationTokenService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class PasswordController {
	private final UserRepository userRepository;
	private final UserService userService;
	private final PasswordResetPublisher passwordResetPublisher;
    private final VerificationTokenService verificationTokenService;

	
	public PasswordController(UserRepository userRepository, UserService userService, PasswordResetPublisher passwordResetPublisher, VerificationTokenService verificationTokenService) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.passwordResetPublisher = passwordResetPublisher;
		this.verificationTokenService = verificationTokenService;
	}
	
	@GetMapping("/password/reset")
	public String reset(Model model) {
		model.addAttribute("passwordResetSendForm", new PasswordResetSendForm());
		return "password/reset";
	}
	
	@PostMapping("/password/reset")
	public String send(@ModelAttribute @Validated PasswordResetSendForm passwordResetSendForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest, Model model) {		
		//		メールアドレスが存在しなければ、BindingResultオブジェクトにエラー内容を追加する。
		if(!userService.isEmailRegistered(passwordResetSendForm.getEmail())) {
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "入力されたメールアドレスと一致するユーザーは登録されていません。");
            bindingResult.addError(fieldError);
		}
		
        if(bindingResult.hasErrors()) {
					return "password/reset";
        }
        
        User passwordResetUser = userRepository.findByEmail(passwordResetSendForm.getEmail());
        String requestUrl = new String(httpServletRequest.getRequestURL());
        passwordResetPublisher.publishPasswordResetEvent(passwordResetUser, requestUrl);
        model.addAttribute("successMessage", "ご入力いただいたメールアドレスにパスワード再設定URLを送信しました。メールに記載されているリンクをクリックし、パスワードを再設定してください。このページは閉じてください。");        
        
        return "password/result";
	}
	
	@GetMapping("/password/reset/verify")
	public String verify(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if(verificationToken == null) {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
			return "password/result";
		}
		
		model.addAttribute("token", token);
		model.addAttribute("passwordEditForm", new PasswordEditForm());
		return "password/edit";
	}
	
	@PostMapping("/password/edit")
	public String newpassword(@RequestParam(name = "token") String token, @ModelAttribute @Validated PasswordEditForm passwordEditForm, BindingResult bindingResult, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
		
		if(!userService.isSamePassword(passwordEditForm.getPassword(), passwordEditForm.getPasswordConfirmation())) {
    		FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
    		bindingResult.addError(fieldError);
    	}
		
		if(bindingResult.hasErrors()) {
			System.out.println("パスワードが違う");
			model.addAttribute("token", token);
			return "password/edit";
		}

		User user = verificationToken.getUser();
		userService.newpassword(passwordEditForm, user);
        model.addAttribute("successMessage", "パスワードを再設定しました。新しいパスワードでログインしてください。");        
		return "auth/login";
	}
}
