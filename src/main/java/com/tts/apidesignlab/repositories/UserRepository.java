package com.tts.apidesignlab.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.apidesignlab.models.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
	List<User> findByState(String state);
	
	Optional<User> findById(Long id);
	
	@Override
	List<User> findAll();
}
