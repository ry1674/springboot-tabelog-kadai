package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {
	private final FavoriteRepository favoriteRepository;

	public FavoriteController(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}
	
	@GetMapping
	public String index(@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		Page<Favorite> favoritePage = favoriteRepository.findByUserAndFavoriteOrderByCreatedAtDesc(user, 2, pageable);
		
		model.addAttribute("favoritePage", favoritePage);
		
		return "favorite/index";
	}
}
