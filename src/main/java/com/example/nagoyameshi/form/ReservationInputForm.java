package com.example.nagoyameshi.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {
	@NotBlank(message = "日時を選択してください")
	private String reservationDateTime;
	
	@NotNull(message = "宿泊人数を入力してください。")
	@Min(value = 1, message  = "人数は１人以上を入力してください。")
	private Integer numberOfPeople;
	
}
