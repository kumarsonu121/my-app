package com.fastenal.myapp.repository;

import com.fastenal.myapp.dto.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {

    @Query("SELECT u FROM Cart u WHERE u.c_name = ?1")
    public Cart findByName(String c_name);
}
