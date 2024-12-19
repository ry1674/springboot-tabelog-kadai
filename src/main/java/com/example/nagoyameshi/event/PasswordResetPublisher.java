package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.User;

@Component
public class PasswordResetPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;

	public PasswordResetPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	public void publishPasswordResetEvent(User user, String requestUrl) {
		applicationEventPublisher.publishEvent(new PasswordResetEvent(this, user, requestUrl));
	}
}
