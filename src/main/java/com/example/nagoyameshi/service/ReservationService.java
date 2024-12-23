package com.example.nagoyameshi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final RestaurantRepository restaurantRepository;
	private final UserRepository userRepository;

	public ReservationService(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void create(ReservationRegisterForm reservationRegisterForm) {
		Reservation reservation = new Reservation();
		Restaurant restaurant = restaurantRepository.getReferenceById(reservationRegisterForm.getRestaurantId());
		User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
		
		String str = reservationRegisterForm.getReservationDateTime();
		LocalDateTime reservationDateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
				
		reservation.setRestaurant(restaurant);
		reservation.setUser(user);
		reservation.setReservationDateTime(reservationDateTime);
		reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());

		reservationRepository.save(reservation);
	}
	
//	予約時間が現在から2時間前以内ならTureを返す
	public boolean beforTowHoursAgo(String reservationTime) {
		try {
		    // String型のreservationDateTimeをDate型へ変更する
		    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		    Date dateReservationTime = format.parse(reservationTime);
		    
		    // 現在から2時間前の日時を取得する
		    Calendar calendar = Calendar.getInstance();
		    calendar.add(Calendar.MINUTE, -120);
		    Date twoHoursAgo = calendar.getTime();

		    if(dateReservationTime.before(twoHoursAgo)) {
		    	return true;
		    }
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;   
	}
	
//	String型の予約時間をDB用に変換する
	public String dateFormat(String reservationDateTime) {
		try {
		    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		    Date dateReservationDateTime = format.parse(reservationDateTime);
	        String strReservationDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateReservationDateTime);
	        return strReservationDateTime;
		} catch(ParseException e) {
			e.printStackTrace();
		}
		String s = "失敗";
		return s;
	}
	
}
