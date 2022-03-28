package com.fastenal.myapp.repository;

import com.fastenal.myapp.dto.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.username = ?1")
    public Users findByName(String username);


}
