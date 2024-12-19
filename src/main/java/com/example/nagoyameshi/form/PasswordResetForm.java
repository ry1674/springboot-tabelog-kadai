package com.example.nagoyameshi.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetForm {	
	@NotBlank(message = "パスワードを入力してください。")
	@Length(min = 8, message = "パスワードは８文字以上で入力してください。")
	private String password;
	
	@NotBlank(message = "パスワード（確認用）を入力してください。")
	private String passwordConfirmation;
}
