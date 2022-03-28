package com.fastenal.myapp.repository;

import com.fastenal.myapp.dto.Courses;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Courses, Integer> {

    @Query("SELECT u FROM Courses u WHERE u.name = ?1")
    public Courses findByNames(String name);
}
