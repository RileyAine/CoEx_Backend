package com.coex.backend.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.coex.backend.model.User;

public interface UserRepository extends ElasticsearchRepository<User, String> {
	Optional<User> findByEmail(String email);
}
