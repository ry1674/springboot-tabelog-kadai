package com.example.nagoyameshi.form;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	
	private Integer id;
	
	private Restaurant restaurant;
	
	private User user;
	
	private Integer review;
	
	@Size(min = 1, max = 255, message="1文字以上255文字以内で記入してください。")
	private String comment;
}
