package com.example.nagoyameshi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
 	public User findByEmail(String email);
 	public Optional<User> findById(Integer userId);
 	public Page<User> findByNameLikeOrFuriganaLikeOrEmailLike(String nameKeyword, String furiganaKeyword, String emailKeyword, Pageable pageable);

}
