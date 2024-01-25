package com.coex.backend.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.coex.backend.model.User;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
	List<User> findByEmail(String email);
}
