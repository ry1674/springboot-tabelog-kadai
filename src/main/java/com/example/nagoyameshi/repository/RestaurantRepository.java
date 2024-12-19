package com.example.nagoyameshi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
	public Page<Restaurant> findByNameLike(String keyword, Pageable pageable);
	
	public Page<Restaurant> findByNameLikeOrderByCreatedAtDesc(String keyword, Pageable pageable);
	public Page<Restaurant> findByNameLikeOrderByMinPriceAsc(String keyword, Pageable pageable);
	public Page<Restaurant> findByNameLikeOrderByMaxPriceDesc(String keyword, Pageable pageable);
	
	public Page<Restaurant> findByMaxPriceLessThanEqualAndMinPriceGreaterThanEqualOrderByCreatedAtDesc(Integer maxPrice, Integer minPrice, Pageable pageable);
	public Page<Restaurant> findByMaxPriceLessThanEqualAndMinPriceGreaterThanEqualOrderByMinPriceAsc(Integer maxPrice, Integer minPrice, Pageable pageable);
	public Page<Restaurant> findByMaxPriceLessThanEqualAndMinPriceGreaterThanEqualOrderByMaxPriceDesc(Integer maxPrice, Integer minPrice, Pageable pageable);
	
//	カテゴリ検索を追加
	public Page<Restaurant> findByCategoryOrderByCreatedAtDesc(Category category, Pageable pageable);
	public Page<Restaurant> findByCategoryOrderByMinPriceAsc(Category category, Pageable pageable);
	public Page<Restaurant> findByCategoryOrderByMaxPriceDesc(Category category, Pageable pageable);
	
    public Page<Restaurant> findAllByOrderByCreatedAtDesc(Pageable pageable);
    public Page<Restaurant> findAllByOrderByMinPriceAsc(Pageable pageable); 
    public Page<Restaurant> findAllByOrderByMaxPriceDesc(Pageable pageable); 

    public List<Restaurant> findTop6ByOrderByCreatedAtDesc();	
    
    public List<Restaurant> findByCategory(Category category);
}
