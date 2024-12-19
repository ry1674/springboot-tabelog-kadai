package com.example.nagoyameshi.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.service.RestaurantService;


@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	private final FavoriteRepository favoriteRepository;
	private final RestaurantService restaurantService;
	
	public AdminRestaurantController(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, RestaurantService restaurantService, FavoriteRepository favoriteRepository) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.favoriteRepository = favoriteRepository;
		this.restaurantService = restaurantService;
	}
	
	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
		Page<Restaurant> restaurantPage;
		
		if(keyword != null && !keyword.isEmpty()) {
			restaurantPage = restaurantRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			restaurantPage = restaurantRepository.findAll(pageable);
		}
		
		model.addAttribute("restaurantPage", restaurantPage);
		model.addAttribute("keyword", keyword);
		
		return "admin/restaurants/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		
		model.addAttribute("restaurant", restaurant);
		
		return "admin/restaurants/show";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		List<Category> categorieslist = categoryRepository.findAll();
		model.addAttribute("categorieslist", categorieslist);
		model.addAttribute("restaurantRegisterForm", new RestaurantRegisterForm());
		return "admin/restaurants/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated RestaurantRegisterForm restaurantRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if(bindingResult.hasErrors()) {
			List<Category> categorieslist = categoryRepository.findAll();
			model.addAttribute("categorieslist", categorieslist);

			return "admin/restaurants/register";
		}
		
		restaurantService.create(restaurantRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");
		
		return "redirect:/admin/restaurants";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id") Integer id, Model model) {
		List<Category> categorieslist = categoryRepository.findAll();
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		String imageName = restaurant.getImageName();
		
		String openTime = restaurant.getOpenTime().toString();
		String closeTime = restaurant.getCloseTime().toString();
		String closedDays = restaurant.getClosedDay();
		String[] closedDaysArray = closedDays.split(",");
		List<String> closedDaysList = Arrays.asList(closedDaysArray);
		
		RestaurantEditForm restaurantEditForm = new RestaurantEditForm(restaurant.getId(), restaurant.getCategory(), restaurant.getName(), null, restaurant.getDescription(), restaurant.getMinPrice(), restaurant.getMaxPrice(), openTime, closeTime, restaurant.getPostalCode(), restaurant.getAddress(), restaurant.getPhoneNumber(), closedDaysList);

		String closedDay = restaurant.getClosedDay();
	    String[] closedDayArray = closedDay.split(",");
	    List<String> closedDayList =  Arrays.asList(closedDayArray);

		
		model.addAttribute("categorieslist", categorieslist);
		model.addAttribute("imageName", imageName);
		model.addAttribute("restaurantEditForm", restaurantEditForm);
		model.addAttribute("closedDayList", closedDayList);
		
		return "admin/restaurants/edit";
	}
	
	@PostMapping("/{id}/update")
	public String update(@PathVariable(name = "id") Integer id, @ModelAttribute @Validated RestaurantEditForm restaurantEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if(bindingResult.hasErrors()) {
			Restaurant restaurant = restaurantRepository.getReferenceById(id);
			String closedDay = restaurant.getClosedDay();
		    String[] closedDayArray = closedDay.split(",");
		    List<String> closedDayList =  Arrays.asList(closedDayArray);
			List<Category> categorieslist = categoryRepository.findAll();
			
			model.addAttribute("closedDayList", closedDayList);
			model.addAttribute("categorieslist", categorieslist);
			
			return "admin/restaurants/edit";
		}
		
		restaurantService.update(restaurantEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");
		
		return "redirect:/admin/restaurants";
	}
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
//		restaurantがユーザーにお気に入り登録されていたら削除できない。
		List<Favorite> favoriteList = favoriteRepository.findByRestaurant(restaurant);
		if(!favoriteList.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "選択された店舗はユーザーがお気に入り登録しているため、削除できません。");
			return "redirect:/admin/restaurants";
		}
		
		restaurantRepository.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
		
		return "redirect:/admin/restaurants";
	}
}
