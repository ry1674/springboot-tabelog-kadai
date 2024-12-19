package com.example.nagoyameshi.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantEditForm {
	@NotNull
	private Integer id;
	
	@NotNull(message = "カテゴリを入力してください。")
	private Category category;

	@NotBlank(message = "店舗名を入力してください。")
	private String name;
	
    private MultipartFile imageFile;
	
    @NotBlank(message= "説明を入力してください。")
	private String description;
	
    @NotNull(message = "料金を入力してください。")
	private Integer minPrice;
	
    @NotNull(message = "料金を入力してください。")
	private Integer maxPrice;
	
    @NotBlank(message = "開店時間を入力してください。")
	private String openTime;
	
    @NotBlank(message = "閉店時間を入力してください。")
	private String closeTime;
	
	@NotBlank(message = "郵便番号を入力してください。")
	private String postalCode;
	
	@NotBlank(message = "住所を入力してください。")
	private String address;
	
	@NotBlank(message = "電話番号を入力してください。")
	private String phoneNumber;
	
	@NotEmpty(message = "定休日を入力してください。")
	private List<String> closedDay;	
	
    public List<String> getClosedDays() {
    	return closedDay;
    }
}
