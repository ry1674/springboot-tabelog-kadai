package com.example.nagoyameshi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReservationService;

@Controller
public class ReservationController {
	private final ReservationRepository reservationRepository;
	private final RestaurantRepository restaurantRepository;
	private final ReservationService reservationService;
	
	public ReservationController(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, ReservationService reservationService) {
		this.reservationRepository = reservationRepository;
		this.restaurantRepository = restaurantRepository;
		this.reservationService = reservationService;
	}
	
//	予約一覧画面へ
	@GetMapping("/reservations")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
		User user = userDetailsImpl.getUser();
		Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
		
		model.addAttribute("currentDateTime", LocalDateTime.now());
		model.addAttribute("reservationPage", reservationPage);
		
		return "reservations/index";
	}
	
//	予約内容をチェックしエラーがなければ、確認画面へ。エラーがあれば、やり直させる。
	@GetMapping("/restaurants/{id}/reservations/input")
	public String input(@PathVariable(name = "id") Integer id,
						@ModelAttribute @Validated ReservationInputForm reservationInputForm,
						BindingResult bindingResult,
						RedirectAttributes redirectAttributes,
						Model model)
	{
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("restaurant", restaurant);
			model.addAttribute("errorMessage", "予約内容に不備があります。");
			return "restaurants/show";
		}
	
		String reservationDateTime = reservationInputForm.getReservationDateTime();
		try {
		    // String型のreservationDateTimeをDate型へ変更する
		    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		    Date dateReservationDateTime = format.parse(reservationDateTime);

		    // 現在から2時間前の日時を取得する
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.MINUTE, -120);
		    Date twoHoursAgo = calendar.getTime();

    		// 予約時間が2時間以内の場合予約不可を返す
		    if (dateReservationDateTime.before(twoHoursAgo)) {
		        model.addAttribute("restaurant", restaurant);
		        model.addAttribute("errorMessage", "当日の予約は2時間前までにしてください。");
		        return "restaurants/show";
		    }
		    		
//	        reservationDateTimeをDB用のフォーマットへ変更する
            String strReservationDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(dateReservationDateTime);
            reservationInputForm.setReservationDateTime(strReservationDateTime);
    		
		} catch(ParseException e) {
            e.printStackTrace();
		}
				
		redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);
		return "redirect:/restaurants/{id}/reservations/confirm";
	}
	
//	予約内容確認画面
	@GetMapping("/restaurants/{id}/reservations/confirm")
	public String confirm(@PathVariable(name = "id") Integer id,
						  @ModelAttribute ReservationInputForm reservationInputForm,
						  @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						  Model model)
	{
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		User user = userDetailsImpl.getUser();
		
		String reservationDateTime = reservationInputForm.getReservationDateTime();

		ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(restaurant.getId(), user.getId(), reservationDateTime, reservationInputForm.getNumberOfPeople());
		
		
		
		model.addAttribute("restaurant", restaurant);
		model.addAttribute("reservationRegisterForm", reservationRegisterForm);
		
		return "reservations/confirm";
	}
	
//	予約作成
	@PostMapping("/restaurants/{id}/reservations/create")
	public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {
		reservationService.create(reservationRegisterForm);
		return "redirect:/reservations?reserved";
	}
	
//	予約を取り消す
	@PostMapping("/reservations/{id}/cancel")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {

		reservationRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "予約をキャンセルしました。");
		
		return "redirect:/reservations";

	}
}
