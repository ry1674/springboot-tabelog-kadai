package com.example.nagoyameshi.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	private final RestaurantRepository restaurantRepository;
	private final ReviewService reviewService;
	
	public ReviewController(ReviewRepository reviewRepository, RestaurantRepository restaurantRepository, ReviewService reviewService) {
		this.reviewRepository = reviewRepository;
		this.restaurantRepository = restaurantRepository;
		this.reviewService = reviewService;
	}
	
	@GetMapping("/{id}")
	public String index(@PathVariable(name = "id") Integer id, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		Page<Review> reviewPage = reviewRepository.findAllByRestaurantOrderByCreatedAtDesc(restaurant, pageable);
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("reviewPage", reviewPage);
		
		return "review/index";
	}
	
	@GetMapping("/{id}/register")
	public String register(@PathVariable(name = "id") Integer id, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		model.addAttribute("restaurant", restaurant);
		
		return "review/register";
	}
	
	@PostMapping("/{id}/create")
	public String create(@PathVariable(name = "id") Integer id, @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		if(bindingResult.hasErrors()) {
			Restaurant restaurant = restaurantRepository.getReferenceById(id);
			model.addAttribute("restaurant", restaurant);
			return "review/register";
		}
		
		User user = userDetailsImpl.getUser();
		Restaurant restaurant = restaurantRepository.getReferenceById(id);

		reviewService.create(reviewRegisterForm, restaurant, user);
		
		redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");
		
		return "redirect:/restaurants/{id}";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id") Integer id, Model model) { 
		Review review = reviewRepository.getReferenceById(id);
		Restaurant restaurant = review.getRestaurant();
		ReviewEditForm reviewEditForm = new ReviewEditForm(review.getId(), restaurant, review.getUser(), review.getReview(), review.getComment());
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("reviewEditForm", reviewEditForm);
		
		return "review/edit";
	}
	
	@PostMapping("/{id}/update")
	public String update(@PathVariable(name = "id") Integer id, @ModelAttribute @Validated ReviewEditForm reviewEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder builder, Model model) {
	    Review review = reviewRepository.getReferenceById(id);
		Restaurant restaurant = review.getRestaurant();

		if(bindingResult.hasErrors()) {
			model.addAttribute("restaurant", restaurant);
			model.addAttribute("reviewEditForm", reviewEditForm);
			return "review/edit";
		}
		
		reviewService.update(reviewEditForm);
	    redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");

	    URI location = builder.path("/restaurants/{restaurantId}").buildAndExpand(review.getRestaurant().getId()).toUri();
	    return "redirect:" + location.toString();
	}
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, UriComponentsBuilder builder) {
		Review review = reviewRepository.getReferenceById(id);
		URI location = builder.path("/restaurants/{restaurantId}").buildAndExpand(review.getRestaurant().getId()).toUri();
		
		reviewRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");
		return "redirect:" + location.toString();
	}
}
