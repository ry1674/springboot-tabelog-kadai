package com.example.nagoyameshi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	private final ReviewRepository reviewRepository;
	private final FavoriteRepository favoriteRepository;
	private final FavoriteService favoriteService;
	
	public RestaurantController(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository, FavoriteRepository favoriteRepository, FavoriteService favoriteService) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.reviewRepository = reviewRepository;
		this.favoriteRepository = favoriteRepository;
		this.favoriteService = favoriteService;
	}
	
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
						@RequestParam(name = "maxPrice", required = false) Integer maxPrice,
						@RequestParam(name = "minPrice", required = false) Integer minPrice,
						@RequestParam(name = "category", required = false) Category category,
                        @RequestParam(name = "order", required = false) String order,
						@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
						Model model)
	{
		List<Category> categorieslist = categoryRepository.findAll();
		List<Review> reviewsList = reviewRepository.findAll();
		
		Page<Restaurant> restaurantPage;
		
		if(keyword != null && !keyword.isEmpty()) {
			if(order != null && order.equals("priceAsc")) {
				restaurantPage = restaurantRepository.findByNameLikeOrderByMinPriceAsc("%" + keyword + "%", pageable);
			} else if (order != null && order.equals("priceDesc")) {
				restaurantPage = restaurantRepository.findByNameLikeOrderByMaxPriceDesc("%" + keyword + "%", pageable);
			} else {
				restaurantPage = restaurantRepository.findByNameLikeOrderByCreatedAtDesc("%" + keyword + "%", pageable);
			}		
		} else if(maxPrice != null && minPrice != null) {
			if (order != null && order.equals("priceAsc")) {
				restaurantPage = restaurantRepository.findByMaxPriceLessThanEqualAndMinPriceGreaterThanEqualOrderByMinPriceAsc(maxPrice, minPrice, pageable);
			} else if (order != null && order.equals("priceDesc")) {
				restaurantPage = restaurantRepository.findByMaxPriceLessThanEqualAndMinPriceGreaterThanEqualOrderByMaxPriceDesc(maxPrice, minPrice, pageable);
			} else {
				restaurantPage = restaurantRepository.findByMaxPriceLessThanEqualAndMinPriceGreaterThanEqualOrderByCreatedAtDesc(maxPrice, minPrice, pageable);				
			}
		} else if(category != null ) {
			if(order != null && order.equals("priceAsc")) {
				restaurantPage = restaurantRepository.findByCategoryOrderByMinPriceAsc(category, pageable);
			} else if (order != null && order.equals("priceDesc")) {
				restaurantPage = restaurantRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
			} else {
				restaurantPage = restaurantRepository.findByCategoryOrderByCreatedAtDesc(category, pageable);
			}
		} else {
			if (order != null && order.equals("priceAsc")) {
				restaurantPage = restaurantRepository.findAllByOrderByMinPriceAsc(pageable);
			} else if (order != null && order.equals("priceDesc")) {
				restaurantPage = restaurantRepository.findAllByOrderByMaxPriceDesc(pageable);
			} else {
				restaurantPage = restaurantRepository.findAllByOrderByCreatedAtDesc(pageable);
			}
		}
		
		model.addAttribute("categorieslist", categorieslist);
		model.addAttribute("reviewsList", reviewsList);
		model.addAttribute("restaurantPage", restaurantPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("category", category);
		model.addAttribute("maxPrice", maxPrice);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("order", order);
		
		return "restaurants/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		List<Review> reviewsList = reviewRepository.findTop6ByRestaurantOrderByCreatedAtDesc(restaurant);
		
//		ログイン済であれば投稿済のレビューを渡す
		Review review;
		if(userDetailsImpl != null) {
			User user = userDetailsImpl.getUser();
			review = reviewRepository.findByuserAndRestaurant(user, restaurant);
			model.addAttribute("review", review);
		}
//		ログイン済であればお気に入りを渡す
		Favorite favorite;
		if(userDetailsImpl != null) {
			User user = userDetailsImpl.getUser();
			favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
			model.addAttribute("favorite", favorite);
		}
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("reviewsList", reviewsList);
		model.addAttribute("reservationInputForm", new ReservationInputForm());
		
		return "restaurants/show";
	}
	
//	お気に入り登録
	@GetMapping("/{id}/create")
	public String create(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes) {
		
		if(userDetailsImpl != null) {
			Restaurant restaurant = restaurantRepository.getReferenceById(id);
			User user = userDetailsImpl.getUser();
			Favorite favorite = favoriteRepository.findByUserAndRestaurant(user, restaurant);
			if(favorite != null) {
				favoriteService.update(restaurant, user);
			} else if(favorite == null) {
				favoriteService.create(restaurant, user);
			}
		}

		return "redirect:/restaurants/{id}";
	}
	
//	お気に入り解除
	@GetMapping("/{id}/delete") //お気に入りID
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, UriComponentsBuilder builder) {
		Favorite favorite =  favoriteRepository.getReferenceById(id);
		URI location = builder.path("/restaurants/{restaurantId}").buildAndExpand(favorite.getRestaurant().getId()).toUri();
		
		favoriteService.booleanDelete(favorite.getUser(), favorite.getRestaurant());
		
		return "redirect:" + location.toString();
	}
}
