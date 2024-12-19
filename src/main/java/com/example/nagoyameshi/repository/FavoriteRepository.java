package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	public Page<Favorite> findByUserAndFavoriteOrderByCreatedAtDesc(User user, Integer favorite, Pageable pageable);

	public Favorite findByUserAndRestaurant(User user, Restaurant restaurant);
	public List<Favorite> findByRestaurant(Restaurant restaurant);
	
}
