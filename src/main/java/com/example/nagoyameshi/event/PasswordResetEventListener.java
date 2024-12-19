package com.example.nagoyameshi.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.repository.VerificationTokenRepository;
import com.example.nagoyameshi.service.VerificationTokenService;

@Component
public class PasswordResetEventListener {
	private final VerificationTokenRepository verificationTokenRepository;
	private final VerificationTokenService verificationTokenService;
	private final JavaMailSender javaMailSender;

	public PasswordResetEventListener(VerificationTokenRepository verificationTokenRepository, VerificationTokenService verificationTokenService, JavaMailSender javaMailSender) {
		this.verificationTokenRepository = verificationTokenRepository;
		this.verificationTokenService = verificationTokenService;
		this.javaMailSender = javaMailSender;
	}
	
	@EventListener
	private void onPasswordResetEvent(PasswordResetEvent passwordResetEvent) {
		User user = passwordResetEvent.getUser();
		VerificationToken verificationToken = verificationTokenRepository.findByUserId(user.getId());
		
		if(verificationToken == null) {
			String token = UUID.randomUUID().toString();
			verificationTokenService.create(user, token);
			
	        String recipientAddress = user.getEmail();
	        String subject = "メール認証";
	        String confirmationUrl = passwordResetEvent.getRequestUrl() + "/verify?token=" + token;
	        String message = "以下のリンクをクリックしてメールアドレスを再設定してください。";

	        SimpleMailMessage mailMessage = new SimpleMailMessage(); 
	        mailMessage.setTo(recipientAddress);
	        mailMessage.setSubject(subject);
	        mailMessage.setText(message + "\n" + confirmationUrl);
	        javaMailSender.send(mailMessage);			
		} else if(verificationToken.getUser() == user) {
			String token = UUID.randomUUID().toString();
			verificationTokenService.update(user, token);
			
	        String recipientAddress = user.getEmail();
	        String subject = "メール認証";
	        String confirmationUrl = passwordResetEvent.getRequestUrl() + "/verify?token=" + token;
	        String message = "以下のリンクをクリックしてメールアドレスを再設定してください。";

	        SimpleMailMessage mailMessage = new SimpleMailMessage(); 
	        mailMessage.setTo(recipientAddress);
	        mailMessage.setSubject(subject);
	        mailMessage.setText(message + "\n" + confirmationUrl);
	        javaMailSender.send(mailMessage);
		}
	}
}
