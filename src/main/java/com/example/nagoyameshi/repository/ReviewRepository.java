package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	public List<Review> findTop6ByRestaurantOrderByCreatedAtDesc(Restaurant restaurant);
	
	public Page<Review> findAllByRestaurantOrderByCreatedAtDesc(Restaurant restaurant, Pageable pageable);
	
	public Review findByuserAndRestaurant(User user, Restaurant restaurant);
}




	
