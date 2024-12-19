package com.example.nagoyameshi.event;

import org.springframework.context.ApplicationEvent;

import com.example.nagoyameshi.entity.User;

import lombok.Getter;

@Getter
public class PasswordResetEvent extends ApplicationEvent {
	private User user;
	private String requestUrl;
	
	public PasswordResetEvent(Object source, User user, String requesturl) {
		super(source);
		
		this.user = user;
		this.requestUrl = requesturl;
	}

}
