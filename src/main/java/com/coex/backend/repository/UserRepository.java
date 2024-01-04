package com.coex.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coex.backend.model.User;



@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
