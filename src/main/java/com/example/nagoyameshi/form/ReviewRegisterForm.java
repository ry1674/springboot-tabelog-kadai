package com.example.nagoyameshi.form;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewRegisterForm {
	private Integer restaurantid;
	
	private Integer userId;
	
	private Integer review;
	
	@Size(min = 1, max = 255, message="1文字以上255文字以内で記入してください。")
	private String comment;
}
