package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReviewRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;

	public ReviewService(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Transactional
	public void create(ReviewRegisterForm reviewRegisterForm, Restaurant restaurant, User user) {
		Review review = new Review();
		
		review.setRestaurant(restaurant);
		review.setUser(user);
		review.setReview(reviewRegisterForm.getReview());
		review.setComment(reviewRegisterForm.getComment());
		
		reviewRepository.save(review);
	}
	
	@Transactional
	public void update(ReviewEditForm reviewEditForm) {
		Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
		
		review.setReview(reviewEditForm.getReview());
		review.setComment(reviewEditForm.getComment());
		
		reviewRepository.save(review);
	}
	
}
