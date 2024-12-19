package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;

@Service
public class FavoriteService {
	private FavoriteRepository favoriteRepository;
	
	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}
	
	@Transactional
	public void create(Restaurant restauant, User user) {
		Favorite favorite = new Favorite();
		
		favorite.setUser(user);
		favorite.setRestaurant(restauant);
		favorite.setFavorite(2);
		
		favoriteRepository.save(favorite);
	}
	
	@Transactional
	public void update(Restaurant restaurant, User user) {
		Favorite favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
		favorite.setFavorite(2);
		
		favoriteRepository.save(favorite);
	}

	@Transactional
	public void booleanDelete(User user, Restaurant restaurant) {
		Favorite favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
		
		favorite.setFavorite(1);
				
		favoriteRepository.save(favorite);
	}
	
}
