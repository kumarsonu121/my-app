package com.fastenal.myapp.repository;

import com.fastenal.myapp.dto.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {

    @Query("SELECT u FROM Admin u WHERE u.username = ?1")
    public Admin findByName(String username);
}
